package org.underscore.wrappers;

import org.underscore.$;
import org.underscore.processor.IncludeInMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Array wrapper and related Utility functions holder
 */
public class $A<T> {

    private T[] internal;

    public $A(T... initial) {
        this.internal = initial;
    }

    public $A(Collection<T> initial) {
        this.internal = (T[]) initial.toArray();
    }

    public T[] flatten() {
        List<T> result = new ArrayList<>();
        $.each(this.internal, (T o) -> {
            if (o.getClass().isArray()) {
                List<T> nested = Arrays.asList($A((T[])o).flatten());
                result.addAll(nested);
            } else {
                result.add(o);
            }
        });
        return (T[])result.toArray();
    }

    public T[] array() {
        return this.internal;
    }


    @IncludeInMain
    public static Object[] flatten($C c) {
        Object[] array = c.array();
        return flatten(array);
    }

    @IncludeInMain
    public static <T> $A<T> $A(T... values) {
        return new $A(values);
    }

    @IncludeInMain
    public static Object[] flatten(Object[] array) {
        return $A(array).flatten();
    }


}
