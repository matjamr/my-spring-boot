package efs.task.todoapp;

import com.google.gson.Gson;
import efs.task.todoapp.model.pojos.DataDto;
import efs.task.todoapp.model.pojos.UserDto;
import efs.task.todoapp.util.ToDoServerExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Base64;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

@ExtendWith(ToDoServerExtension.class)
public abstract class BaseTest {

    protected HttpClient httpClient;

    private static final Gson gson = new Gson();
    protected static final int NOT_FOUND = 404;
    protected static final int INVALID_REQUEST = 400;
    protected static final int SUCCESS = 200;

    protected static final String TODO_APP_PATH = "http://localhost:8080/task";
    protected static final String USER_APP_PATH = "http://localhost:8080/user";

    protected static final String USERNAME_1 = "Andrzej";
    protected static final String PASSWORD_1 = "Andrzej_haslo";
    protected static final String USERNAME_2 = "Marcin";
    protected static final String PASSWORD_2 = "Marcin_haselko";
    protected static final String DSC_1 = "Kup mleko";
    protected static final String DSC_2 = "Kup mleko2";
    protected static final String DUE_1 = "2021-06-30";
    protected static final String DUE_2 = "2021-06-31";

    protected static <T> HttpRequest.BodyPublisher toJson(T object) {
        return HttpRequest.BodyPublishers.ofString(gson.toJson(object));
    }

    protected <T> T fromJson(String object, Class<T> tClass) {
        return gson.fromJson(object, tClass);
    }

    protected UserDto buildUser(final String username, final String password) {
        return UserDto.builder()
                .username(username)
                .password(password)
                .build();
    }

    protected static DataDto buildData(final String description, final String due) {
        return DataDto.builder()
                .description(description)
                .due(due)
                .build();
    }

    protected void addUser(final String username, final String password) throws IOException, InterruptedException {
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(USER_APP_PATH))
                .POST(toJson(buildUser(USERNAME_1, PASSWORD_1)))
                .build();

        var httpResponse = httpClient.send(httpRequest, ofString());
    }

    protected DataDto addTask(final String dsc, final String due) throws IOException, InterruptedException {
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(TODO_APP_PATH))
                .POST(toJson(buildData(dsc, due)))
                .header("auth", buildAuthHeader(USERNAME_1, PASSWORD_1))
                .build();

        var httpResponse = httpClient.send(httpRequest, ofString());

        return buildData(dsc, due);
    }



    protected static String buildAuthHeader(final String username, final String password) {
        return new String(Base64.getEncoder().encode(username.concat(":").concat(password).getBytes()));
    }

    @BeforeEach
    void setUp() {
        httpClient = HttpClient.newHttpClient();
    }
}
