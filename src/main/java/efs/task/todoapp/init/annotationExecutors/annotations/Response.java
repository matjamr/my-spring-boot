package efs.task.todoapp.init.annotationExecutors.annotations;

import efs.task.todoapp.init.commons.http.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Response {
    HttpStatus status() default HttpStatus.OK;
}
