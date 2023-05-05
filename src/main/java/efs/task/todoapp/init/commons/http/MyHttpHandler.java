package efs.task.todoapp.init.commons.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import efs.task.todoapp.init.MappingRecord;
import efs.task.todoapp.init.annotationExecutors.annotations.RequiredBody;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

import static efs.task.todoapp.init.DependencyContext.BEAN_MAP;
import static efs.task.todoapp.init.DependencyContext.MAPPING_MAP;
import static java.util.Objects.isNull;

public class MyHttpHandler implements HttpHandler {

    private static final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        var a = MAPPING_MAP.get(new MappingRecord(
                httpExchange.getRequestURI().getPath(),
                HTTP_METHOD.valueOf(httpExchange.getRequestMethod())
        ));

        try {
            if (isNull(a)) {
                throw new Exception("INVALID USER LOGGED IN");
            }

            Optional<Parameter> parsedReq = Arrays.stream(a.getParameters())
                    .filter(param -> param.isAnnotationPresent(RequiredBody.class))
                    .findFirst();

            Object ret;

            if(parsedReq.isEmpty()) {
                ret = a.invoke(BEAN_MAP.get(a.getDeclaringClass()).getInstance());
            } else {
                var paramClassType = parsedReq.get().getParameterizedType();
                var newaAA = Class.forName(paramClassType.getTypeName());
                var ac = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

                ret = a.invoke(BEAN_MAP.get(a.getDeclaringClass()).getInstance(),
                        gson.fromJson(ac, newaAA));
            }

            var content = a.invoke(BEAN_MAP.get(a.getDeclaringClass()).getInstance(), ret);
            handleResponse(httpExchange, gson.toJson(content), 200);
        } catch (Exception e) {
            handleResponse(httpExchange, e.getMessage(), 400);
        }
    }

    private void handleResponse(HttpExchange httpExchange, Object content, int status)  throws  IOException {

        OutputStream outputStream = httpExchange.getResponseBody();

        httpExchange.sendResponseHeaders(status, ((String) content).length());

        outputStream.write(((String) content).getBytes());
        outputStream.flush();
        outputStream.close();
    }
}

