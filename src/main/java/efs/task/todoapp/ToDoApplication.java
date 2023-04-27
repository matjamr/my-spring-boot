package efs.task.todoapp;

import com.sun.net.httpserver.HttpServer;
import efs.task.todoapp.web.WebServerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ToDoApplication {
    private static final Logger LOGGER = Logger.getLogger(ToDoApplication.class.getName());

    public static void main(String[] args) throws ClassNotFoundException {
//        var application = new ToDoApplication();
//        var server = application.createServer();
////        server.start();
//
//        LOGGER.info("ToDoApplication's server started ...");

//        Class<?> clazz = ClassLoader.getSystemClassLoader()
//                .loadClass("com.baeldung.annotation.scanner.SampleAnnotatedClass");

//        Component classAnnotation = clazz.getAnnotation(Component.class);

    }

    public static Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    private static Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }

    public HttpServer createServer() {
        return WebServerFactory.createServer();
    }
}
