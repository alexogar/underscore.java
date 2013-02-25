package org.underscore.functors;

/**
 * User: alexogar
 * Date: 2/23/13
 * Time: 10:22 PM
 */
public interface TransformVisitor<F,T> {
    T visit(F f);
}
