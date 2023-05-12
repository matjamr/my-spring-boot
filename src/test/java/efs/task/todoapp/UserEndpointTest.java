package efs.task.todoapp;

import efs.task.todoapp.assertions.MyHttpAssert;
import efs.task.todoapp.init.commons.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

public class UserEndpointTest extends BaseTest {
    private HttpClient httpClient;

    @BeforeEach
    void setUp() {
        httpClient = HttpClient.newHttpClient();
    }

    @Test
    @Timeout(1)
    void validCreateUserRQ_should201() throws IOException, InterruptedException {
        //given
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(USER_APP_PATH))
                .POST(toJson(buildUser(USERNAME_1, PASSWORD_1)))
                .build();

        //when
        var httpResponse = httpClient.send(httpRequest, ofString());

        //then
        MyHttpAssert.assertThat(httpResponse)
                        .hasStatusCode(HttpStatus.CREATED);

    }

    @Test
    @Timeout(1)
    void userDuplicate_should409() throws IOException, InterruptedException {
        //given
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(USER_APP_PATH))
                .POST(toJson(buildUser(USERNAME_1, PASSWORD_1)))
                .build();

        //when
        var httpResponse = httpClient.send(httpRequest, ofString());
        var httpResponse2 = httpClient.send(httpRequest, ofString());

        //then
        MyHttpAssert.assertThat(httpResponse)
                .hasStatusCode(HttpStatus.CREATED);

        MyHttpAssert.assertThat(httpResponse2)
                .hasStatusCode(HttpStatus.ALREADY_EXISTS);

    }

    @Test
    @Timeout(1)
    void invalidData_should400() throws IOException, InterruptedException {
        //given
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(USER_APP_PATH))
                .POST(toJson(buildUser(null, PASSWORD_1)))
                .build();

        var httpRequest2 = HttpRequest.newBuilder()
                .uri(URI.create(USER_APP_PATH))
                .POST(toJson(buildUser(USERNAME_1, null)))
                .build();

        //when
        var httpResponse = httpClient.send(httpRequest, ofString());
        var httpResponse2 = httpClient.send(httpRequest2, ofString());

        //then
        MyHttpAssert.assertThat(httpResponse)
                .hasStatusCode(HttpStatus.BAD_REQUEST);

        MyHttpAssert.assertThat(httpResponse2)
                .hasStatusCode(HttpStatus.BAD_REQUEST);

    }
}
