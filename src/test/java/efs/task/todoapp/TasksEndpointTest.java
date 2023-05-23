package efs.task.todoapp;

import efs.task.todoapp.assertions.MyHttpAssert;
import efs.task.todoapp.assertions.TaskEntityAssert;
import efs.task.todoapp.init.commons.http.HttpStatus;
import efs.task.todoapp.model.pojos.DataResponseDto;
import efs.task.todoapp.model.pojos.UUIDResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TasksEndpointTest extends BaseTest {

    @Test
    @Timeout(1)
    void validCreateTaskRQ_should201() throws IOException, InterruptedException {
        //given
        addUser(USERNAME_1, PASSWORD_1);

        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(TODO_APP_PATH))
                .POST(toJson(buildData(DSC_1, DUE_1)))
                .header("auth", buildAuthHeader(USERNAME_1, PASSWORD_1))
                .build();

        //when
        var httpResponse = httpClient.send(httpRequest, ofString());

        //then
        MyHttpAssert.assertThat(httpResponse)
                .hasStatusCode(HttpStatus.CREATED)
                .expect((httpResponse_) -> {
                    var response = fromJson(httpResponse_.body(), UUIDResponse.class);
                    assertNotNull(response);
                    assertNotNull(response.getId());
                    assertTrue(Pattern.matches("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})", response.getId()));
                    
                    return null;
                });

    }

    private static Stream<Arguments> invalidAddTaskDataProvider() {
        return Stream.of(
                Arguments.of(HttpRequest.newBuilder()
                        .uri(URI.create(TODO_APP_PATH))
                        .POST(toJson(buildData(DSC_1, DUE_1)))
                        .build(), HttpStatus.BAD_REQUEST),
                Arguments.of(HttpRequest.newBuilder()
                        .uri(URI.create(TODO_APP_PATH))
                        .header("auth", buildAuthHeader(USERNAME_1, PASSWORD_1))
                        .POST(toJson(buildData(null, DUE_1)))
                        .build(), HttpStatus.BAD_REQUEST),
                Arguments.of(HttpRequest.newBuilder()
                        .uri(URI.create(TODO_APP_PATH))
                        .header("auth", buildAuthHeader(USERNAME_1, PASSWORD_1))
                        .POST(toJson(buildData(DSC_1, null)))
                        .build(), HttpStatus.BAD_REQUEST),
                Arguments.of(HttpRequest.newBuilder()
                        .uri(URI.create(TODO_APP_PATH))
                        .header("auth", buildAuthHeader(USERNAME_2, PASSWORD_1))
                        .POST(toJson(buildData(DSC_1, null)))
                        .build(), HttpStatus.UNAUTHORIZED),
                Arguments.of(HttpRequest.newBuilder()
                        .uri(URI.create(TODO_APP_PATH))
                        .header("auth", buildAuthHeader(USERNAME_1, PASSWORD_2))
                        .POST(toJson(buildData(DSC_1, null)))
                        .build(), HttpStatus.UNAUTHORIZED)
        );
    }

    @Timeout(1)
    @ParameterizedTest
    @MethodSource("invalidAddTaskDataProvider")
    void invalidCreateTaskRQ_shouldNotPass(HttpRequest httpRequest, HttpStatus expectedStatus) throws IOException, InterruptedException {
        //given
        addUser(USERNAME_1, PASSWORD_1);

        //when
        var httpResponse = httpClient.send(httpRequest, ofString());

        //then
        MyHttpAssert.assertThat(httpResponse)
                .hasStatusCode(expectedStatus);
    }

    @Test
    @Timeout(1)
    void getTasks_shouldAdd2TasksAndReturnThem() throws IOException, InterruptedException {

        //given
        addUser(USERNAME_1, PASSWORD_1);
        var data1 = addTask(DSC_1, DUE_1);
        var data2 = addTask(DSC_2, DUE_2);


        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(TODO_APP_PATH))
                .GET()
                .header("auth", buildAuthHeader(USERNAME_1, PASSWORD_1))
                .build();

        //when
        var httpResponse = httpClient.send(httpRequest, ofString());

        //then
        MyHttpAssert.assertThat(httpResponse)
                .hasStatusCode(HttpStatus.OK)
                .expect((httpResponse_) -> {

                    var response = fromJson(httpResponse_.body(), DataResponseDto[].class);

                    TaskEntityAssert.assertThat(response[0])
                            .hasUUID()
                            .deeplyEquals(data1);

                    return null;
                });
    }

    private static Stream<Arguments> invalidGetTaskDataProvider() {
        return Stream.of(
                Arguments.of(HttpRequest.newBuilder()
                        .uri(URI.create(TODO_APP_PATH))
                        .POST(toJson(buildData(DSC_1, DUE_1)))
                        .build(), HttpStatus.BAD_REQUEST),
                Arguments.of(HttpRequest.newBuilder()
                        .uri(URI.create(TODO_APP_PATH))
                        .header("auth", buildAuthHeader(USERNAME_2, PASSWORD_1))
                        .POST(toJson(buildData(null, DUE_1)))
                        .build(), HttpStatus.UNAUTHORIZED),
                Arguments.of(HttpRequest.newBuilder()
                        .uri(URI.create(TODO_APP_PATH))
                        .header("auth", buildAuthHeader(USERNAME_1, PASSWORD_2))
                        .POST(toJson(buildData(DSC_1, null)))
                        .build(), HttpStatus.UNAUTHORIZED)
        );
    }

    @Timeout(1)
    @ParameterizedTest
    @MethodSource("invalidGetTaskDataProvider")
    void invalidGetTaskRQ_shouldNotPass(HttpRequest httpRequest, HttpStatus expectedStatus) throws IOException, InterruptedException {
        //given
        addUser(USERNAME_1, PASSWORD_1);
        var data1 = addTask(DSC_1, DUE_1);
        var data2 = addTask(DSC_2, DUE_2);

        //when
        var httpResponse = httpClient.send(httpRequest, ofString());

        //then
        MyHttpAssert.assertThat(httpResponse)
                .hasStatusCode(expectedStatus);
    }

    @Test
    @Timeout(1)
    void getTaskById_shouldAdd2TasksAndReturnFirst() throws IOException, InterruptedException {

        //given
        addUser(USERNAME_1, PASSWORD_1);
        var data2 = addTask(DSC_2, DUE_2);

        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(TODO_APP_PATH))
                .GET()
                .header("auth", buildAuthHeader(USERNAME_1, PASSWORD_1))
                .build();

        var httpResponse = httpClient.send(httpRequest, ofString());
        var response = fromJson(httpResponse.body(), DataResponseDto[].class);


        var httpRequest2 = HttpRequest.newBuilder()
                .uri(URI.create(TODO_APP_PATH.concat("/").concat(response[0].getId().toString())))
                .GET()
                .header("auth", buildAuthHeader(USERNAME_1, PASSWORD_1))
                .build();

        //when
        var httpResponse2 = httpClient.send(httpRequest2, ofString());

        //then
        MyHttpAssert.assertThat(httpResponse2)
                .hasStatusCode(HttpStatus.OK);

        TaskEntityAssert.assertThat(fromJson(httpResponse2.body(), DataResponseDto.class))
                .deeplyEquals(data2)
                .hasUUID();
    }
}
