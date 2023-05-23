package efs.task.todoapp.init.http;

import com.sun.net.httpserver.HttpExchange;
import efs.task.todoapp.init.MappingRecord;
import efs.task.todoapp.init.annotationExecutors.annotations.PathVariable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static efs.task.todoapp.init.DependencyContext.MAPPING_MAP;

public class PathVariableExecutor extends ParameterExecutor {

    @Override
    boolean shouldExecute(HttpExchange httpExchange, Method method) {
        return findAnnotation(method, PathVariable.class).isPresent();
    }

    @Override
    List<Object> execute(HttpExchange httpExchange, Method method) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        return List.of(MAPPING_MAP.entrySet()
                .stream()
                .filter(mappingRecordMethodEntry -> mappingRecordMethodEntry.getValue().equals(method))
                .map(Map.Entry::getKey)
                .map(MappingRecord::path)
                .findFirst()
                .map(a -> getPathVariable(httpExchange.getRequestURI().getPath()))
                .orElseThrow(() -> new RuntimeException("Asda")));
    }

    private String getPathVariable(String path) {
        var tmp = path.split("/");
        return tmp[tmp.length - 1];
    }
}
