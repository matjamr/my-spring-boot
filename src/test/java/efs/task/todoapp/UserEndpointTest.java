package efs.task.todoapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.assertj.core.api.Assertions.assertThat;

public class UserEndpointTest extends BaseTest{

    private HttpClient httpClient;

    @BeforeEach
    void setUp() {
        httpClient = HttpClient.newHttpClient();
    }

    @Test
    @Timeout(1)
    void validCreateUserRQ_shouldSuccess() throws IOException, InterruptedException {
        //given
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(USER_APP_PATH))
                .POST(null)
                .build();

        //when
        var httpResponse = httpClient.send(httpRequest, ofString());

        //then
        assertThat(httpResponse.statusCode()).as("Response status code").isEqualTo(SUCCESS);
    }

    @Test
    @Timeout(1)
    void validCreateUseaarRQ_shouldSuccess() throws IOException, InterruptedException {
        //given
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(USER_APP_PATH))
                .POST(null)
                .build();

        //when
        var httpResponse = httpClient.send(httpRequest, ofString());

        //then
        assertThat(httpResponse.statusCode()).as("Response status code").isEqualTo(SUCCESS);
    }
}
