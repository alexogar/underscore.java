package org.underscore.functors;

/**
 * User: alexogar
 * Date: 2/28/13
 * Time: 12:20 AM
 */
public interface EachNextVisitor<T> {
    void visit(T t, Next next);
}
