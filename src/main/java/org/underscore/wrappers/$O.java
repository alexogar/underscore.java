package org.underscore.wrappers;

import org.underscore.processor.IncludeInMain;

/**
 * User: alexogar
 * Date: 2/27/13
 * Time: 11:58 PM
 */
public class $O<T> {
    private T reference;

    $O() {
    }

    public $O(T reference) {
        this.reference = reference;
    }

    public void set(T reference) {this.reference = reference; }
    public T get(){return this.reference;}

    @IncludeInMain
    public static <E> $O<E> $(E e) {
        return new $O<>(e);
    }
}
