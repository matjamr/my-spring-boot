package efs.task.todoapp.init;

import lombok.Builder;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.util.List;

@Data
@Builder
public
class Bean {
    private Class<?> class_;
    private List<Annotation> annotations;
    private Object instance;
}
