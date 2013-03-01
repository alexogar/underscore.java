package org.underscore.wrappers;

import org.underscore.functors.EachPairVisitor;
import org.underscore.functors.EachVisitor;
import org.underscore.functors.TransformPairVisitor;
import org.underscore.functors.TransformVisitor;
import org.underscore.processor.IncludeInMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: alexogar
 * Date: 2/25/13
 * Time: 10:14 AM
 */
public class $M<K, V> {
    private final Map<K, V> internal;

    public $M() {
        internal = new HashMap<>();
    }

    public $M(Map<K, V> map) {
        this.internal = map;
    }

    public $M<K, V> each(EachPairVisitor<K, V> visitor) {
        for (Map.Entry<K, V> entry : internal.entrySet()) {
            visitor.visit(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public $M<K, V> each(EachVisitor<Map.Entry<K, V>> visitor) {
        for (Map.Entry<K, V> entry : internal.entrySet()) {
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
        return new $C<>(internal.keySet());
    }

    public $C<V> values() {
        return new $C<>(internal.values());
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
}
