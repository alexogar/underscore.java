package org.underscore.wrappers;

import org.underscore.$;
import org.underscore.processor.IncludeInMain;

import java.util.*;

import static org.underscore.$.$;
import static org.underscore.$.intersection;

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
        if (isEmpty(arrays)) return $A();
        final Set<T> internalSet = new LinkedHashSet<>(list());
        $(arrays).each(array -> {
            internalSet.retainAll(array.list());
        });
        return $A((T[]) internalSet.toArray());
    }

    public List<T> list() {
        if (this.internal == null) {
            return null;
        }
        return Arrays.asList(this.internal);
    }

    /**
     * Computes the intersection of all input arrays with internal array.
     *
     * @param arrays input arrays to intersect
     * @return intersectionWith result
     */
    public $A<T> intersectionWith(T[]... arrays) {
        if (isEmpty(arrays)) return $A();
        $A[] wrappers = toWrappers(arrays);
        return intersectionWith(wrappers);
    }

    @IncludeInMain
    private static <A> boolean isEmpty(A[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * Computes the union of all input arrays with internal array.
     *
     * @param arrays
     * @return
     */
    public $A<T> unionWith($A<T>... arrays) {
        if (isEmpty(arrays)) return this;
        final Set<T> internalSet = new LinkedHashSet<>(list());
        $(arrays).each(array -> {
            internalSet.addAll(array.list());
        });
        return $A((T[]) internalSet.toArray());
    }

    /**
     * Computes the union of all input arrays with internal array.
     *
     * @param arrays
     * @return
     */
    public $A<T> unionWith(T[]... arrays) {
        if (isEmpty(arrays)) return this;
        $A[] wrappers = toWrappers(arrays);
        return unionWith(wrappers);
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
     * Returns last element, or throws an exception if size of array is less than 1.
     *
     * @return last element
     * @throws ArrayIndexOutOfBoundsException if size of array is less that 1
     */
    public T last() {
        return internal[internal.length - 1];
    }

    /**
     * Returns last 'N' elements of the array, or throws an exception if size of array is less than 'N' or <= 0.
     *
     * @param n amount of elements to return
     * @return last 'N' elements
     */
    public $A<T> last(int n) {
        checkRangeIsValid(n);
        int length = internal.length;
        return $A(Arrays.copyOfRange(internal, length - n, length));
    }

    private void checkRangeIsValid(int n) {
        if (n > internal.length || n <= 0) {
            throw new IllegalArgumentException("Invalid range [" + n +
                    "] for an array of size [" + internal.length + "]");
        }
    }

    /**
     * Returns first 'N' elements of the array.
     *
     * @param n amount of elements to return
     * @return first elements
     * @throws IllegalArgumentException if 'N' > size of array or 'N' <= 0
     */
    public $A<T> first(int n) {
        checkRangeIsValid(n);
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
     * Returns last 'n' elements of the array
     *
     * @param array original array
     * @param n     amount of last elements
     * @param <T>   elements type
     * @return last elements
     */
    @IncludeInMain
    public static <T> $A<T> last(T[] array, int n) {
        return $A(array).last(n);
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
     * @param <T>    element type
     * @return intersection
     */
    @IncludeInMain
    public static <T> $A<T> intersection($A<T>... arrays) {
        if (isEmpty(arrays)) return $A();
        if (arrays.length == 1) {
            return arrays[0];
        }
        return arrays[0].intersectionWith(Arrays.copyOfRange(arrays, 1, arrays.length));
    }

    /**
     * Returns intersection of input arrays.
     *
     * @param arrays input arrays
     * @param <T>    element type
     * @return intersection
     */
    @IncludeInMain
    public static <T> $A<T> intersection(T[]... arrays) {
        if (isEmpty(arrays)) return $A();
        if (arrays.length == 1) {
            return $A(arrays[0]);
        }
        $A<T>[] wrappers = toWrappers(arrays);
        return intersection(wrappers);
    }

    /**
     * Returns union of input arrays.
     *
     * @param arrays input arrays
     * @param <T>    element type
     * @return union
     */
    @IncludeInMain
    public static <T> $A<T> union($A... arrays) {
        if (isEmpty(arrays)) return $A();
        if (arrays.length == 1) {
            return arrays[0];
        }
        return arrays[0].unionWith(Arrays.copyOfRange(arrays, 1, arrays.length));
    }

    /**
     * Returns union of input arrays.
     *
     * @param arrays input arrays
     * @param <T>    element type
     * @return union
     */
    @IncludeInMain
    public static <T> $A<T> union(T[]... arrays) {
        if (isEmpty(arrays)) return $A();
        if (arrays.length == 1) {
            return $A(arrays[0]);
        }
        $A<T>[] wrappers = toWrappers(arrays);
        return union(wrappers);
    }

    @IncludeInMain
    private static <T> $A<T>[] toWrappers(T[][] arrays) {
        $A<T>[] wrappers = new $A[arrays.length];
        for (int i = 0; i < arrays.length; i++) {
            wrappers[i] = $A(arrays[i]);
        }
        return wrappers;
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
