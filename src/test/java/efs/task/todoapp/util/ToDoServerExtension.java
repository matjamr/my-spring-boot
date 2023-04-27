package efs.task.todoapp.util;

import com.sun.net.httpserver.HttpServer;
import efs.task.todoapp.ToDoApplication;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;

public class ToDoServerExtension implements Extension, BeforeEachCallback, AfterEachCallback {
    private HttpServer server;

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws IOException {
        var todoApplication = new ToDoApplication();
        server = todoApplication.createServer();
        server.start();
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        server.stop(0);
    }
}
