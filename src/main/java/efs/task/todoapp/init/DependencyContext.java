package efs.task.todoapp.init;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DependencyContext {

    public static final Map<String, Bean> BEAN_MAP = new HashMap<>();
    public static final Map<MappingRecord, Method> MAPPING_MAP = new HashMap<>();

}
