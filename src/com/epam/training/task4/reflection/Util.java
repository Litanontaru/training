package com.epam.training.task4.reflection;

import com.epam.training.task4.reflection.cache.Cache;
import com.epam.training.task4.reflection.cache.CacheDeclaration;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Util {

    public static Map<String, Cache> loadClasses(String packageName) throws ReflectionException, ClassNotFoundException, IllegalAccessException {
        Map<String, Cache> cacheMap = new HashMap<>();
        try {
            URL resource = Thread.currentThread()
                    .getContextClassLoader()
                    .getResource(packageName.replace('.', '/'));
            File directory = new File(resource.getFile());


            for (File file : directory.listFiles((dir, name) -> name.endsWith(".class"))) {
                String subName = file.getName().substring(0, file.getName().indexOf(".class"));
                Class cacheClass = Class.forName(packageName + "." + subName);
                CacheDeclaration cacheDeclaration = (CacheDeclaration) cacheClass.getDeclaredAnnotation(CacheDeclaration.class);
                if (cacheDeclaration == null) {
                    continue;
                }
                cacheMap.put(cacheDeclaration.name(), (Cache) cacheClass.newInstance());

            }
        } catch (InstantiationException e) {
            throw new ReflectionException("Error creating instance of class", e);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Check package name", e);
        }
        return cacheMap;
    }
}
