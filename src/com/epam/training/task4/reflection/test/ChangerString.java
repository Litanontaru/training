package com.epam.training.task4.reflection.test;

import com.epam.training.task4.reflection.cache.Cache;
import com.epam.training.task4.reflection.cache.InjectCache;
import com.epam.training.task4.reflection.cache.TypeCache;

public class ChangerString {

    @InjectCache(name = TypeCache.REVERSE)
    private Cache<Integer, String> cache;

    public String get(Integer key) {
        return cache.get(key);
    }

    public void put(Integer key, String value) {
        cache.put(key, value);
    }

}
