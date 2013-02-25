package org.underscore.wrappers;

import org.underscore.functors.EachPairVisitor;
import org.underscore.functors.EachVisitor;
import org.underscore.functors.TransformVisitor;

import java.util.*;

/**
 * User: alexogar
 * Date: 2/24/13
 * Time: 1:25 PM
 */
public class $C<T> {

    private final Collection<T> internal;

    public $C() {
        internal = new ArrayList<>();
    }

    public $C(T[] arr) {
        if (arr == null) {
            internal = new ArrayList<>();
            return;
        }
        internal = new ArrayList<>(Arrays.asList(arr));
    }

    public $C(Collection<T> it) {
        if (it == null) {
            internal = new ArrayList<>();
            return;
        }
        internal = it;
    }

    public $C<T> each(EachVisitor<T> visitor) {
        for (T e : internal) visitor.visit(e);
        return this;
    }

    public $C<T> each(EachPairVisitor<T, Integer> visitor) {
        int i = 0;
        for (T t : internal) {
            visitor.visit(t, i);
            i++;
        }
        return this;
    }

    public <E> $C<E> map(TransformVisitor<T,E> visitor){
        List<E> list = new ArrayList<>();
        each((T f)->{list.add(visitor.visit(f));});
        return new $C<>(list);
    }

    public T[] array() {
        return (T[]) internal.toArray();
    }
}
