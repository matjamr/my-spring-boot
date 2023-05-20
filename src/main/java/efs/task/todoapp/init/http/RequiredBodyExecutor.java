package efs.task.todoapp.init.http;

import com.sun.net.httpserver.HttpExchange;
import efs.task.todoapp.init.annotationExecutors.annotations.RequiredBody;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class RequiredBodyExecutor extends ParameterExecutor {

    @Override
    public boolean shouldExecute(HttpExchange httpExchange, Method method) {
        return findAnnotation(method, RequiredBody.class).isPresent();
    }

    @Override
    public List<Object> execute(HttpExchange httpExchange, Method method) throws IOException, ClassNotFoundException {

        final Optional<Parameter> requiredBodyRQ = findAnnotation(method, RequiredBody.class);
        Result result = getResult(httpExchange, requiredBodyRQ);
        var jsonBody = gson.fromJson(result.requestBodyContent(), result.dynamicType());

        return List.of(jsonBody);
    }

    private Result getResult(HttpExchange httpExchange, Optional<Parameter> requiredBodyRQ) throws ClassNotFoundException, IOException {
        var paramClassType = requiredBodyRQ.get().getParameterizedType();
        var dynamicType = Class.forName(paramClassType.getTypeName());
        var requestBodyContent = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

        return new Result(dynamicType, requestBodyContent);
    }

}
