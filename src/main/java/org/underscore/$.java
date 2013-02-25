package org.underscore;

import org.underscore.functors.TransformVisitor;
import org.underscore.functors.EachPairVisitor;
import org.underscore.functors.EachVisitor;
import org.underscore.wrappers.$C;
import org.underscore.wrappers.$M;

import java.util.*;

/**
 * User: alexogar
 * Date: 2/23/13
 * Time: 9:22 PM
 */
public final class $ {
    public static <E> $C<E> each(E[] it, EachVisitor<E> visitor) {
        return new $C<>(it).each(visitor);
    }

    public static <E> $C<E> each(Collection<E> it, EachVisitor<E> visitor) {
        return new $C<>(it).each(visitor);
    }

    public static <E> $C<E> each(E[] it, EachPairVisitor<E, Integer> visitor) {
        return new $C<>(it).each(visitor);
    }

    public static <E> $C<E> each(Collection<E> it, EachPairVisitor<E, Integer> visitor) {
        return new $C<>(it).each(visitor);
    }

    public static <K, V> $M<K,V> each(Map<K, V> map, EachPairVisitor<K, V> visitor) {
        return new $M<>(map).each(visitor);
    }

    public static <K, V> $M<K,V> each(Map<K, V> map, EachVisitor<Map.Entry<K, V>> visitor) {
        return new $M<>(map).each(visitor);
    }

    public static <F,T> $C<T> map(Collection<F> it, TransformVisitor<F,T> visitor){
        return new $C<>(it).map(visitor);
    }

    public static <F,T> $C<T> map(F[] it, TransformVisitor<F,T> visitor) {
        return new $C<>(it).map(visitor);
    }
}
