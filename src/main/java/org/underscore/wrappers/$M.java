package org.underscore.wrappers;

import org.underscore.functors.EachPairVisitor;
import org.underscore.functors.EachVisitor;
import org.underscore.functors.TransformPairVisitor;
import org.underscore.functors.TransformVisitor;
import org.underscore.processor.IncludeInMain;

import java.util.*;

/**
 * User: alexogar
 * Date: 2/25/13
 * Time: 10:14 AM
 */
public class $M<K, V> implements Map<K,V>{
    private final Map<K, V> internal;

    public $M() {
        internal = new HashMap<>();
    }

    public $M(Map<K, V> map) {
        this.internal = map;
    }

    public $M<K, V> each(EachPairVisitor<K, V> visitor) {
        for (Map.Entry<K, V> entry : entrySet()) {
            visitor.visit(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public $M<K, V> each(EachVisitor<Map.Entry<K, V>> visitor) {
        for (Map.Entry<K, V> entry : entrySet()) {
            visitor.visit(entry);
        }
        return this;
    }

    public <T> $M<K, T> map(TransformVisitor<Map.Entry<K, V>, T> visitor) {
        Map<K, T> map = new HashMap<>();
        each((Map.Entry<K, V> entry) -> {
            map.put(entry.getKey(), visitor.visit(entry));
        });
        return new $M<>(map);
    }

    public <T> $M<K, T> map(TransformPairVisitor<K, V, T> visitor) {
        Map<K, T> map = new HashMap<>();
        each((K k, V v) -> {
            map.put(k, visitor.visit(k, v));
        });
        return new $M<>(map);
    }

    public $C<K> keys() {
        return new $C<>(keySet());
    }

    public $C<V> values() {
        return new $C<>(internal.values());
    }

    public $C<Entry<K,V>> entries() {
        return new $C<>(entrySet());
    }

    @Override
    public Set<Entry<K,V>> entrySet() {
        return internal.entrySet();
    }

    @IncludeInMain
    public static <K,V> $M<K,V> $(Map<K, V> map) {
        return new $M<>(map);
    }

    @IncludeInMain
    public static <K, V> $M<K,V> each(Map<K, V> map, EachPairVisitor<K, V> visitor) {
        return $(map).each(visitor);
    }

    @IncludeInMain
    public static <K, V> $M<K,V> each(Map<K, V> map, EachVisitor<Map.Entry<K, V>> visitor) {
        return $(map).each(visitor);
    }

    @IncludeInMain
    public static <K,F,T> $M<K,T> map(Map<K, F> map, TransformVisitor<Map.Entry<K,F>,T> visitor) {
        return $(map).map(visitor);
    }

    @IncludeInMain
    public static <K,F,T> $M<K,T> map(Map<K, F> map, TransformPairVisitor<K,F,T> visitor) {
        return $(map).map(visitor);
    }

    @Override
    public int size() {
        return internal.size();
    }

    @Override
    public boolean isEmpty() {
        return internal.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return internal.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return internal.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return internal.get(key);
    }

    public V put(K key, V value) {
        return internal.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return internal.remove(key);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        internal.putAll(m);
    }

    @Override
    public void clear() {
        internal.clear();
    }

    @Override
    public Set<K> keySet() {
        return internal.keySet();
    }
}
