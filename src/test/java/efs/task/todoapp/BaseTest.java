package efs.task.todoapp;

import com.google.gson.Gson;
import efs.task.todoapp.model.pojos.UserDto;
import efs.task.todoapp.util.ToDoServerExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.http.HttpRequest;

@ExtendWith(ToDoServerExtension.class)
public abstract class BaseTest {

    private static final Gson gson = new Gson();
    protected static final int NOT_FOUND = 404;
    protected static final int INVALID_REQUEST = 400;
    protected static final int SUCCESS = 200;

    protected static final String TODO_APP_PATH = "http://localhost:8080/todo/";
    protected static final String USER_APP_PATH = "http://localhost:8080/user";

    protected final String USERNAME_1 = "Andrzej";
    protected final String PASSWORD_1 = "Andrzej_haslo";
    protected final String USERNAME_2 = "Marcin";
    protected final String PASSWORD_2 = "Marcin_haselko";

    protected <T> HttpRequest.BodyPublisher toJson(T object) {
        return HttpRequest.BodyPublishers.ofString(gson.toJson(object));
    }

    protected UserDto buildUser(final String username, final String password) {
        return UserDto.builder()
                .username(username)
                .password(password)
                .build();
    }

}
