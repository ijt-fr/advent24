package com.advent.day17.memoization;

import java.util.concurrent.ExecutionException;

import com.advent.day17.SevenBitComputer.Combo;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class Adv {

    private LoadingCache<Key, Long> memoizationCache = CacheBuilder.newBuilder().maximumSize(0).build(cacheLoader());

    private CacheLoader<Key, Long> cacheLoader() {
        return new CacheLoader<>() {
            @Override
            public Long load(Key key) {
                return key.a() >> key.combo();
            }
        };
    }

    public Long get(long a, long combo) throws ExecutionException {
        return memoizationCache.get(new Key(a, combo));
    }

    private record Key(long a, long combo) {
    }
}
