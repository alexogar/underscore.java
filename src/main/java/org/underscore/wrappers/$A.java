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

    /**
     * Constructor with 'varargs' initializer.
     *
     * @param initial initial values
     */
    public $A(T... initial) {
        this.internal = initial;
    }

    /**
     * Constructor with <code>Collection</code> initializer.
     *
     * @param initial initial values
     */
    public $A(Collection<T> initial) {
        this.internal = (T[]) initial.toArray();
    }

    /**
     * Flattens a nested arrays (the nesting can be to any depth), creates new array and doesn't change internal state.
     * <p/>
     * Example:
     * $A(new String[]{"1"}, "2", new String[]{"3"}).flatten() => ["1", "2", "3"]
     *
     * @return flattened array
     */
    public T[] flatten() {
        List<T> result = new ArrayList<>();
        $.each(this.internal, (T o) -> {
            if (o.getClass().isArray()) {
                List<T> nested = Arrays.asList($A((T[]) o).flatten());
                result.addAll(nested);
            } else {
                result.add(o);
            }
        });
        return (T[]) result.toArray();
    }

    /**
     * Returns first element, or throws an exception if size of array is less than 1.
     *
     * @return first element
     * @throws ArrayIndexOutOfBoundsException if size of array is less that 1
     */
    public T first() {
        return internal[0];
    }

    /**
     * Returns first 'n' elements of the array.
     *
     * @param n amount of elements to return
     * @return first elements
     */
    public $A<T> first(int n) {
        if (n > internal.length || n <= 0) {
            throw new IllegalArgumentException("Invalid input parameter [" + n +
                    "] for an array of size [" + internal.length + "]");
        }
        return $A(Arrays.copyOfRange(internal, 0, n));
    }

    /**
     * Returns internal array.
     *
     * @return internal array
     */
    public T[] array() {
        return this.internal;
    }

    /**
     * Returns first 'n' elements of the array
     *
     * @param array original array
     * @param n     amount of first elements
     * @param <T>   elements type
     * @return first elements
     */
    @IncludeInMain
    public static <T> $A<T> first(T[] array, int n) {
        return $A(array).first(n);
    }

    /**
     * Returns first 'n' elements of the collection. Order is managed by collection's iterator.
     *
     * @param collection original collection
     * @param n          amount of first elements
     * @param <T>        elements type
     * @return first elements
     */
    @IncludeInMain
    public static <T> $A<T> first(Collection<T> collection, int n) {
        return $A(collection).first(n);
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
    public static <T> $A<T> $A(Collection<T> values) {
        return new $A(values);
    }

    @IncludeInMain
    public static Object[] flatten(Object[] array) {
        return $A(array).flatten();
    }


}
