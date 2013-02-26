package org.underscore;

import org.underscore.functors.*;
import org.underscore.wrappers.$C;
import org.underscore.wrappers.$M;

import java.util.*;

/**
 * User: alexogar
 * Date: 2/23/13
 * Time: 9:22 PM
 */
public final class $ {

    public static <E> $C<E> $(Collection<E> it) {
        return new $C<>(it);
    }

    public static <E> $C<E> $(E[] it) {
        return new $C<>(it);
    }

    public static <K,V> $M<K,V> $(Map<K, V> map) {
        return new $M<>(map);
    }

    public static <E> $C<E> each(E[] it, EachVisitor<E> visitor) {
        return $(it).each(visitor);
    }

    public static <E> $C<E> each(Collection<E> it, EachVisitor<E> visitor) {
        return $(it).each(visitor);
    }

    public static <E> $C<E> each(E[] it, EachPairVisitor<E, Integer> visitor) {
        return $(it).each(visitor);
    }

    public static <E> $C<E> each(Collection<E> it, EachPairVisitor<E, Integer> visitor) {
        return $(it).each(visitor);
    }

    public static <K, V> $M<K,V> each(Map<K, V> map, EachPairVisitor<K, V> visitor) {
        return $(map).each(visitor);
    }

    public static <K, V> $M<K,V> each(Map<K, V> map, EachVisitor<Map.Entry<K, V>> visitor) {
        return $(map).each(visitor);
    }

    public static <F,T> $C<T> map(Collection<F> it, TransformVisitor<F,T> visitor){
        return $(it).map(visitor);
    }

    public static <F,T> $C<T> map(F[] it, TransformVisitor<F,T> visitor) {
        return $(it).map(visitor);
    }

    public static <K,F,T> $M<K,T> map(Map<K, F> map, TransformVisitor<Map.Entry<K,F>,T> visitor) {
        return $(map).map(visitor);
    }

    public static <K,F,T> $M<K,T> map(Map<K, F> map, TransformPairVisitor<K,F,T> visitor) {
        return $(map).map(visitor);
    }

    public static <K> K reduce(K[] it, ReducePairVisitor<K> visitor) {
        return $(it).reduce(visitor);
    }

}
