package com.epam.training.task4.reflection;

import com.epam.training.task4.reflection.cache.CacheDeclaration;
import com.epam.training.task4.reflection.cache.TypeCache;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Util {

    public static Map<TypeCache, Class> loadClasses(String packageName) throws ClassNotFoundException {
        URL resource = Thread.currentThread()
                .getContextClassLoader()
                .getResource(packageName.replace('.', '/'));
        File directory = new File(resource.getFile());
        Map<TypeCache, Class> maps = new HashMap<>();

        for (File file : directory.listFiles((dir, name) -> name.endsWith(".class"))) {
                String subName = file.getName().substring(0, file.getName().indexOf(".class"));
                Class cacheClass = Class.forName(packageName + "." + subName);
                CacheDeclaration cacheDeclaration = (CacheDeclaration) cacheClass.getDeclaredAnnotation(CacheDeclaration.class);
                if (cacheDeclaration == null) {
                    continue;
                }
                maps.put(cacheDeclaration.name(), cacheClass);

        }
        return maps;
    }
}
