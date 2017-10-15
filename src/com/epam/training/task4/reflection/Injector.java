package com.epam.training.task4.reflection;


import com.epam.training.task4.reflection.cache.CacheDeclaration;
import com.epam.training.task4.reflection.cache.InjectCache;
import com.epam.training.task4.reflection.cache.TypeCache;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class Injector {


    public static <T> void inject(T instance,final Map<TypeCache, Class> classes) throws InjectException {
        Class clazz = instance.getClass();

        List<Field> fields = new ArrayList<>();
        fields.addAll(getAllFieldsIncludesSuperclasses(clazz));

        try {
            for (Field field : fields) {
                InjectCache injectCache = field.getDeclaredAnnotation(InjectCache.class);
                if (injectCache == null) {
                    continue;
                }
                Class cacheClass = classes.get(injectCache.name());
                Object cache = cacheClass.newInstance();
                field.setAccessible(true);
                field.set(instance, cache);
            }
        } catch (IllegalAccessException e) {
            throw new InjectException("Error creating instance of class or setting field",e);
        } catch (InstantiationException e) {
            throw new InjectException("Error creating instance of class",e);
        }


    }

    private static List<Field> getAllFieldsIncludesSuperclasses(Class instance) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(instance.getDeclaredFields()));
        Class superClass = instance.getSuperclass();
        if (superClass != null) {
            fields.addAll(getAllFieldsIncludesSuperclasses(superClass));
        }
        return fields;
    }
}
