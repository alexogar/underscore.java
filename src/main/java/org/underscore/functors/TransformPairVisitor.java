package org.underscore.functors;

/**
 * User: alexogar
 * Date: 2/25/13
 * Time: 10:37 PM
 */
public interface TransformPairVisitor<K,V,T> {
    T visit(K key, V value);
}
