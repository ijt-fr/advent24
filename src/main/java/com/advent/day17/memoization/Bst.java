package com.advent.day17.memoization;

import java.util.concurrent.ExecutionException;

import com.advent.day17.SevenBitComputer;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class Bst {

    private LoadingCache<Long, Long> memoizationCache = CacheBuilder.newBuilder().maximumSize(0).build(cacheLoader());

    private CacheLoader<Long, Long> cacheLoader() {
        return new CacheLoader<>() {
            @Override
            public Long load(Long combo) {
                return combo & 7;
            }
        };
    }

    public Long get(long combo) throws ExecutionException {
        return memoizationCache.get(combo);
    }
}
