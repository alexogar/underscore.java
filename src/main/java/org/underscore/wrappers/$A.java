package org.underscore.wrappers;

import org.underscore.$;
import org.underscore.processor.IncludeInMain;

import java.util.*;

import static org.underscore.$.$;

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
                List<T> nested = $($A((T[]) o).flatten()).list();
                result.addAll(nested);
            } else {
                result.add(o);
            }
        });
        return (T[]) result.toArray();
    }

    /**
     * Computes the intersection of all input arrays with internal array.
     *
     * @param arrays input arrays to intersect
     * @return intersectionWith result
     */
    public $A<T> intersectionWith($A<T>... arrays) {
        if (arrays == null || arrays.length == 0) {
            return $A();
        }
        T[][] arrayOfArrays = (T[][]) new Object[arrays.length][];
        for (int i = 0; i < arrays.length; i++) {
            arrayOfArrays[i] = arrays[i].array();
        }
        return intersectionWith(arrayOfArrays);
    }

    /**
     * Computes the intersection of all input arrays with internal array.
     *
     * @param arrays input arrays to intersect
     * @return intersectionWith result
     */
    public $A<T> intersectionWith(T[]... arrays) {
        if (arrays == null || arrays.length == 0) {
            return $A();
        }
        final Set<T> internalSet = new LinkedHashSet<>($(array()).list());
        $(arrays).each(array -> {
            internalSet.retainAll($(array).list());
        });
        return $A((T[]) internalSet.toArray());
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

    /**
     * Returns intersection of input arrays.
     *
     * @param arrays input arrays
     * @param <T>
     * @return intersection
     */
    @IncludeInMain
    public static <T> $A<T> intersection($A<T> ... arrays) {
        if (arrays == null || arrays.length == 0) {
            return new $A();
        }
        if (arrays.length == 1) {
            return arrays[0];
        }
        return arrays[0].intersectionWith(Arrays.copyOfRange(arrays, 1, arrays.length - 1));
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
