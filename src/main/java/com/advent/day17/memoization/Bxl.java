package com.advent.day17.memoization;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class Bxl {

    private final LoadingCache<Key, Long> memoizationCache = CacheBuilder.newBuilder().maximumSize(0).build(cacheLoader());

    private CacheLoader<Key, Long> cacheLoader() {
        return new CacheLoader<>() {
            @Override
            public Long load(Key key) {
                return key.b() ^ key.operand();
            }
        };
    }

    public Long get(long b, int operand) throws ExecutionException {
        return memoizationCache.get(new Key(b, operand));
    }


    private record Key(long b, int operand) {
    }
}
