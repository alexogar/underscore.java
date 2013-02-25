package org.underscore.functors;

/**
 * User: alexogar
 * Date: 2/23/13
 * Time: 10:08 PM
 */
public interface EachPairVisitor<K,V> {
    void visit(K key, V value);
}
