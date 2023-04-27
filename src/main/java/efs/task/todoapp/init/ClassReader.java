package efs.task.todoapp.init;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassReader {

    public static List<Class<?>> findAnnotatedClasses(String packageName, Class<? extends Annotation> annotationClass) {
        List<Class<?>> annotatedClasses = new ArrayList<>();

        try {
            for (Class<?> clazz : getClasses(packageName)) {
                if (clazz.isAnnotationPresent(annotationClass)) {
                    annotatedClasses.add(clazz);
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return annotatedClasses;
    }

    private static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(".", "/");
        Enumeration<URL> resources = classLoader.getResources(path);
        List<Class<?>> classes = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File file = new File(resource.getFile());
            classes.addAll(findClasses(file, packageName));
        }

        return classes;
    }

    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();

        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                Class<?> clazz = Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6));
                classes.add(clazz);
            }
        }
        return classes;
    }

}