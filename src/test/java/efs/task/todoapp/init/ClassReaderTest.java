package efs.task.todoapp.init;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClassReaderTest {

    @Mock
    private ClassLoader classLoader;

    @DisplayName("Test findAnnotatedClasses method")
    @Test
    public void testFindAnnotatedClasses() throws Exception {
        String packageName = "efs.task.todoapp";
        Class<? extends Annotation> annotationClass = MyAnnotation.class;

        Enumeration<URL> resources = mock(Enumeration.class);
        when(classLoader.getResources(packageName.replace(".", "/"))).thenReturn(resources);

        URL resource = new URL("file:///tmp/MyAnnotatedClass.class");
        when(resources.hasMoreElements()).thenReturn(true, false);
        when(resources.nextElement()).thenReturn(resource);

        File directory = mock(File.class);
        when(directory.exists()).thenReturn(true);
        File[] files = new File[] { new File("MyAnnotatedClass.class") };
        when(directory.listFiles()).thenReturn(files);
        when(classLoader.loadClass(packageName + ".MyAnnotatedClass")).thenReturn((Class) MyAnnotatedClass.class);

        List<Class<?>> result = ClassReader.findAnnotatedClasses(packageName, annotationClass);

        assertEquals(1, result.size());
        assertEquals(MyAnnotatedClass.class, result.get(0));
    }

    private static class MyAnnotatedClass {

        @MyAnnotation
        public void myAnnotatedMethod() {

        }
    }

    private static @interface MyAnnotation {

    }
}