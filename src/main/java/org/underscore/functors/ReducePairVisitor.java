package org.underscore.functors;

public interface ReducePairVisitor<V> {
    V visit(V input1, V input2);
}
