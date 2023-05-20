package efs.task.todoapp.init.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class ParameterExecutor {

    protected static final Gson gson = new Gson();

    abstract boolean shouldExecute(HttpExchange httpExchange, Method method);
    abstract List<Object> execute(HttpExchange httpExchange, Method method) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException;

    protected record Result(Class<?> dynamicType, String requestBodyContent) {
    }

    protected Optional<Parameter> findAnnotation(Method method, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(method.getParameters())
                .filter(param -> param.isAnnotationPresent(annotationClass))
                .findFirst();
    }

}
