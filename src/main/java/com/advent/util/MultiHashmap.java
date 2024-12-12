package com.advent.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MultiHashmap<K, V> extends HashMap<K, Set<V>> {

    public Set<V> add(K key, V value) {
        Set<V> set = get(key);
        if (set == null) {
            set = new HashSet<>();
            set.add(value);
            put(key, set);
        } else {
            set.add(value);
        }
        return set;
    }
}
