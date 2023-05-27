package efs.task.todoapp.init.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import efs.task.todoapp.init.MappingRecord;
import efs.task.todoapp.init.annotationExecutors.annotations.Response;
import efs.task.todoapp.init.commons.error.HttpStatusError;
import efs.task.todoapp.init.commons.http.HttpMethod;
import efs.task.todoapp.init.commons.http.HttpStatus;
import efs.task.todoapp.init.commons.http.HttpUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static efs.task.todoapp.init.DependencyContext.BEAN_MAP;
import static efs.task.todoapp.init.DependencyContext.MAPPING_MAP;
import static java.util.Objects.isNull;

public class MyHttpHandler implements HttpHandler {

    private static final Gson gson = new Gson();

    private static final List<ParameterExecutor> parameterExecutors = List.of(
            new RequiredBodyExecutor(),
            new PrincipalExecutor(),
            new PathVariableExecutor()
    );

    private boolean pathMatches(MappingRecord mappingRecord, String path) {
        if(mappingRecord.hasPathVariable()) {
            return HttpUtils.handlePathVarUri(path).equals(mappingRecord.path());
        }

        return path.equals(mappingRecord.path());
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        MappingRecord mappingRec = MAPPING_MAP.keySet()
                .stream()
                .filter(mappingRecord -> pathMatches(mappingRecord, httpExchange.getRequestURI().getPath()) &&
                        mappingRecord.method().equals(HttpMethod.valueOf(httpExchange.getRequestMethod())))
                .findFirst()
                .orElse(null);

         Method methodFromMapping = MAPPING_MAP.get(mappingRec);

        try {
            if (isNull(methodFromMapping)) {
                throw new HttpStatusError("No mapping found!", HttpStatus.NOT_FOUND);
            }

            // Parse parameters with its annotations via dependency injection
            var parsedParams = parameterExecutors.stream()
                    .filter(parameterExecutor -> parameterExecutor.shouldExecute(httpExchange, methodFromMapping))
                    .map(parameterExecutor -> {
                        try {
                            return parameterExecutor.execute(httpExchange, methodFromMapping);
                        } catch (HttpStatusError error) {
                            throw error;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }).flatMap(Collection::stream)
                    .toArray(Object[]::new);

            Object entityToBeReturned = methodFromMapping.invoke(BEAN_MAP.get(methodFromMapping.getDeclaringClass()).getInstance(), parsedParams);

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

