package efs.task.todoapp;

import efs.task.todoapp.util.ToDoServerExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ToDoServerExtension.class)
public abstract class BaseTest {

    protected static final int NOT_FOUND = 404;
    protected static final int INVALID_REQUEST = 400;
    protected static final int SUCCESS = 200;

    protected static final String TODO_APP_PATH = "http://localhost:8080/todo/";
    protected static final String USER_APP_PATH = "http://localhost:8080/user/";

}
