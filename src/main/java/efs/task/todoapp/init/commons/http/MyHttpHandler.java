package efs.task.todoapp.init.commons.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import efs.task.todoapp.init.MappingRecord;
import efs.task.todoapp.init.annotationExecutors.annotations.RequiredBody;
import efs.task.todoapp.init.annotationExecutors.annotations.Response;
import efs.task.todoapp.init.commons.error.HttpStatusError;
import efs.task.todoapp.model.pojos.UserDto;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static efs.task.todoapp.init.DependencyContext.BEAN_MAP;
import static efs.task.todoapp.init.DependencyContext.MAPPING_MAP;
import static java.util.Objects.isNull;

public class MyHttpHandler implements HttpHandler {

    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        UserDto userDto = new UserDto(null, "aa");
        System.out.println(gson.toJson(userDto));
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        var methodFromMapping = MAPPING_MAP.get(new MappingRecord(
                httpExchange.getRequestURI().getPath(),
                HttpMethod.valueOf(httpExchange.getRequestMethod())
        ));

        try {
            if (isNull(methodFromMapping)) {
                throw new Exception("No mapping found!");
            }

            final Optional<Parameter> requiredBodyRQ = Arrays.stream(methodFromMapping.getParameters())
                    .filter(param -> param.isAnnotationPresent(RequiredBody.class))
                    .findFirst();

            final Object entityToBeReturned;

            if(requiredBodyRQ.isEmpty()) {
                entityToBeReturned = methodFromMapping.invoke(BEAN_MAP.get(methodFromMapping.getDeclaringClass()).getInstance());
            } else {
                var paramClassType = requiredBodyRQ.get().getParameterizedType();
                var dynamicType = Class.forName(paramClassType.getTypeName());
                var requestBodyContent = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

                var test = gson.fromJson(requestBodyContent, dynamicType);

                entityToBeReturned = methodFromMapping.invoke(BEAN_MAP.get(methodFromMapping.getDeclaringClass()).getInstance(),
                        test);
            }

            // Here is thing to get success response successHttpStatus code
            HttpStatus successHttpStatus = HttpStatus.OK;

            if(methodFromMapping.isAnnotationPresent(Response.class)) {
                successHttpStatus = methodFromMapping.getDeclaredAnnotation(Response.class).status();
            }

            handleResponse(httpExchange, gson.toJson(entityToBeReturned), successHttpStatus.getStatusCode());

        } catch (HttpStatusError httpStatusError) {
            handleResponse(httpExchange, httpStatusError.getMessage(), httpStatusError.getHttpStatus().getStatusCode());
        }
        catch (Exception e) {
            if(e instanceof InvocationTargetException eb) {
                if(eb.getTargetException() instanceof HttpStatusError httpErr) {
                    handleResponse(httpExchange,  "", httpErr.getHttpStatus().getStatusCode());
                }
            } else {
                handleResponse(httpExchange, e.getMessage(), HttpStatus.INTERNAL_ERROR.getStatusCode());
            }
        }
    }

    private void handleResponse(HttpExchange httpExchange, Object content, int status) throws IOException {

        OutputStream outputStream = httpExchange.getResponseBody();

        if(Objects.equals(content, "null")) {
            content = "";
        }

        httpExchange.sendResponseHeaders(status, ((String) content).length());
        outputStream.write(((String) content).getBytes());

        outputStream.flush();
        outputStream.close();
    }
}

