package efs.task.todoapp.assertions;

import efs.task.todoapp.model.entity.TaskEntity;
import efs.task.todoapp.model.pojos.DataDto;
import org.assertj.core.api.AbstractAssert;

import java.net.http.HttpResponse;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskEntityAssert extends AbstractAssert<TaskEntityAssert, TaskEntity> {

    public TaskEntityAssert(TaskEntity actual) {
        super(actual, TaskEntityAssert.class);
    }

    public static TaskEntityAssert assertThat(TaskEntity actual) {
        return new TaskEntityAssert(actual);
    }

    public TaskEntityAssert hasUUID() {
        assertTrue(Pattern.matches("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})", actual.getId().toString()));

        return this;
    }

    public TaskEntityAssert deeplyEquals(DataDto dataDto) {
        assertEquals(actual.getDescription(), dataDto.getDescription());
        assertEquals(actual.getDue(), dataDto.getDue());

        return this;
    }
}
