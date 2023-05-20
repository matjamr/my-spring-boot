package efs.task.todoapp.init.http;

import com.sun.net.httpserver.HttpExchange;
import efs.task.todoapp.init.annotationExecutors.annotations.Principal;
import efs.task.todoapp.init.commons.error.HttpStatusError;
import efs.task.todoapp.init.commons.http.HttpStatus;
import efs.task.todoapp.service.UserService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static efs.task.todoapp.init.DependencyContext.BEAN_MAP;
import static java.util.Objects.isNull;

public class PrincipalExecutor extends ParameterExecutor {

    @Override
    boolean shouldExecute(HttpExchange httpExchange, Method method) {
        return findAnnotation(method, Principal.class).isPresent();
    }

    @Override
    List<Object> execute(HttpExchange httpExchange, Method methodA) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {

        if(!httpExchange.getRequestHeaders().containsKey("auth")) {
            throw new HttpStatusError("", HttpStatus.BAD_REQUEST);
        }

        String baseAuth = httpExchange.getRequestHeaders().get("auth")
                .stream()
                .findFirst()
                .get();

        var userServiceObj = BEAN_MAP.get(UserService.class).getInstance();

        var authorizedUser = Arrays.stream(BEAN_MAP.get(UserService.class).getClass_().getMethods())
                .filter(method -> method.getName().equals("verifyUser"))
                .findFirst()
                .orElseThrow(()-> new HttpStatusError("", HttpStatus.UNAUTHORIZED));

        var aa = authorizedUser.invoke(userServiceObj, baseAuth);

        if(isNull(aa)) {
            throw new HttpStatusError("", HttpStatus.UNAUTHORIZED);
        }

        return List.of(aa);
    }

}
