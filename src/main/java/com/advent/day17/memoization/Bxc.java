package com.advent.day17.memoization;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class Bxc {

    private LoadingCache<Bxc.Key, Long> memoizationCache = CacheBuilder.newBuilder().maximumSize(0).build(cacheLoader());

    private CacheLoader<Bxc.Key, Long> cacheLoader() {
        return new CacheLoader<>() {
            @Override
            public Long load(Bxc.Key key) {
                return key.b() ^ key.c();
            }
        };
    }

    public Long get(long b, long c) throws ExecutionException {
        return memoizationCache.get(new Key(b, c));
    }

    private record Key(long b, long c) {
    }
}
