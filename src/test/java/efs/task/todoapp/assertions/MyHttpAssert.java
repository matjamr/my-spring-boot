package efs.task.todoapp.assertions;

import efs.task.todoapp.init.commons.http.HttpStatus;
import org.assertj.core.api.AbstractAssert;

import java.net.http.HttpResponse;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyHttpAssert<T> extends AbstractAssert<MyHttpAssert<T>, HttpResponse<T>> {

    public MyHttpAssert(HttpResponse<T> actual) {
        super(actual, MyHttpAssert.class);
    }

    public static <M>MyHttpAssert<M> assertThat(HttpResponse<M> actual) {
        return new MyHttpAssert<M>(actual);
    }

    public MyHttpAssert<T> hasStatusCode(HttpStatus httpStatus) {
        if(httpStatus.getStatusCode() != actual.statusCode()) {
            failWithMessage("Expected http status to be %s but was %s",
                    httpStatus.getStatusCode(), actual.statusCode());
        }

        return this;
    }

    public MyHttpAssert<T> expect(Function<HttpResponse<T>, Boolean> supplier) {
        assertTrue(supplier.apply(actual));

        return this;
    }
}
