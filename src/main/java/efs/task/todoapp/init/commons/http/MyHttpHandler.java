package efs.task.todoapp.init.commons.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import efs.task.todoapp.init.MappingRecord;
import efs.task.todoapp.init.annotationExecutors.annotations.RequiredBody;
import efs.task.todoapp.init.annotationExecutors.annotations.Response;
import efs.task.todoapp.init.commons.error.HttpStatusError;
import efs.task.todoapp.model.pojos.UserDto;
import efs.task.todoapp.service.UserService;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

        MappingRecord mappingRec = MAPPING_MAP.keySet()
                .stream()
                .filter(mappingRecord -> mappingRecord.path().equals(httpExchange.getRequestURI().getPath()) &&
                        mappingRecord.method().equals(HttpMethod.valueOf(httpExchange.getRequestMethod())))
                .findFirst()
                .orElse(null);

         Method methodFromMapping = MAPPING_MAP.get(mappingRec);

        try {
            if (isNull(methodFromMapping)) {
                throw new HttpStatusError("No mapping found!", HttpStatus.NOT_FOUND);
            }

            final Optional<Parameter> requiredBodyRQ = Arrays.stream(methodFromMapping.getParameters())
                    .filter(param -> param.isAnnotationPresent(RequiredBody.class))
                    .findFirst();

            final Object entityToBeReturned;

            UserDto returnedUser = null;

            // parse security header
            assert mappingRec != null;
            if(mappingRec.isSecured()) {
                if(!httpExchange.getRequestHeaders().containsKey("auth")) {
                    throw new HttpStatusError("", HttpStatus.UNAUTHORIZED);
                }

                String baseAuth = httpExchange.getRequestHeaders().get("auth")
                        .stream()
                        .findFirst()
                        .get();

                var userServiceObj = BEAN_MAP.get(UserService.class).getInstance();
                var tescik = Arrays.stream(BEAN_MAP.get(UserService.class).getClass_().getMethods())
                        .filter(method -> method.getName().equals("verifyUser"))
                        .findFirst()
                        .orElseThrow(()-> new HttpStatusError("UNEXPECTED ERR", HttpStatus.INTERNAL_ERROR));

                returnedUser = (UserDto) tescik.invoke(userServiceObj, baseAuth);
            }

            if(requiredBodyRQ.isEmpty()) {
                entityToBeReturned = methodFromMapping.invoke(BEAN_MAP.get(methodFromMapping.getDeclaringClass()).getInstance(), returnedUser, 3);
            } else {
                Result result = getResult(httpExchange, requiredBodyRQ);

                var jsonBody = gson.fromJson(result.requestBodyContent(), result.dynamicType());

                entityToBeReturned = methodFromMapping.invoke(BEAN_MAP.get(methodFromMapping.getDeclaringClass()).getInstance(),
                        jsonBody, returnedUser, 3);
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
                handleResponse(httpExchange, "", HttpStatus.INTERNAL_ERROR.getStatusCode());
            }
        }
    }

    private static Result getResult(HttpExchange httpExchange, Optional<Parameter> requiredBodyRQ) throws ClassNotFoundException, IOException {
        var paramClassType = requiredBodyRQ.get().getParameterizedType();
        var dynamicType = Class.forName(paramClassType.getTypeName());
        var requestBodyContent = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Result result = new Result(dynamicType, requestBodyContent);
        return result;
    }

    private record Result(Class<?> dynamicType, String requestBodyContent) {
    }

    private void handleResponse(HttpExchange httpExchange, Object content, int status) throws IOException {

        OutputStream outputStream = httpExchange.getResponseBody();

        if(Objects.equals(content, "null")) {
            content = "";
        }
        httpExchange.getResponseHeaders().add("content-type", "application/json");

        httpExchange.sendResponseHeaders(status, ((String) content).length());
        outputStream.write(((String) content).getBytes());

        outputStream.flush();
        outputStream.close();
    }
}

