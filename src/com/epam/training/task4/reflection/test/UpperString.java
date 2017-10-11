package com.epam.training.task4.reflection.test;

import com.epam.training.task4.reflection.cache.Cache;
import com.epam.training.task4.reflection.cache.InjectCache;
import com.epam.training.task4.reflection.cache.TypeCache;

public class UpperString {

    @InjectCache(name = TypeCache.UPPER_CASE)
    private Cache<Integer, String> cache2;

    public String get(Integer key) {
        return cache2.get(key);
    }

    public void put(Integer key, String value) {
        cache2.put(key, value);
    }
}
