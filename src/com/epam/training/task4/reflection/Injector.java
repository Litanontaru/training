package com.epam.training.task4.reflection;


import com.epam.training.task4.reflection.cache.CacheDeclaration;
import com.epam.training.task4.reflection.cache.InjectCache;
import com.epam.training.task4.reflection.cache.TypeCache;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class Injector {

    private static Map<TypeCache, Class> classes = loadClasses("com.epam.training.task4.reflection.cache.implementation");

    public static <T> void inject(T instance){
        Class clazz = instance.getClass();

        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        fields.addAll(Arrays.asList(clazz.getFields()));
        fields.addAll(getPrivateFieldsFromSuperclasses(clazz));

        for (Field field : fields) {
            try {
                InjectCache injectCache = field.getDeclaredAnnotation(InjectCache.class);
                if (injectCache == null) {
                    continue;
                }
                Class cacheClass = classes.get(injectCache.name());
                Object cache =  cacheClass.newInstance();
                field.setAccessible(true);
                field.set(instance, cache);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }


    }

    private static Map<TypeCache, Class> loadClasses(String packageName) {
        URL resource = Thread.currentThread()
                .getContextClassLoader()
                .getResource(packageName.replace('.', '/'));
        File directory = new File(resource.getFile());
        Map<TypeCache, Class> maps = new HashMap<>();

        for (File file : directory.listFiles((dir, name) -> name.endsWith(".class"))) {
            try {
                String subName = file.getName().substring(0, file.getName().indexOf(".class"));
                Class cacheClass = Class.forName(packageName + "." + subName);
                CacheDeclaration cacheDeclaration = (CacheDeclaration) cacheClass.getDeclaredAnnotation(CacheDeclaration.class);
                if (cacheDeclaration == null) {
                    continue;
                }
                maps.put(cacheDeclaration.name(), cacheClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return maps;
    }



    private static List<Field> getPrivateFieldsFromSuperclasses(Class instance) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(instance.getDeclaredFields()));
        Class superClass = instance.getSuperclass();
        if (superClass != null) {
            fields.addAll(getPrivateFieldsFromSuperclasses(superClass));
        }
        return fields;
    }
}
