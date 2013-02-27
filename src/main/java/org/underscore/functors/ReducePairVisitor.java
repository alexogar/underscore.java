package org.underscore.functors;

public interface ReducePairVisitor<MEMO,V> {
    MEMO visit(MEMO memo, V input);
}
