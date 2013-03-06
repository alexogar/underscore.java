package org.underscore.wrappers;

import org.underscore.processor.IncludeInMain;

import java.util.concurrent.atomic.AtomicReference;

/**
 * User: alexogar
 * Date: 2/27/13
 * Time: 11:58 PM
 */
public class $O<T> extends AtomicReference<T> {

    $O() {
    }

    public $O(T reference) {
        super(reference);
    }

    @IncludeInMain
    public static <E> $O<E> $(E e) {
        return new $O<>(e);
    }

    public CharSequence str() {
        return (this instanceof CharSequence) ? (CharSequence) this : this.toString();
    }

    @IncludeInMain
    public static CharSequence str(Object part) {
        return new $O(part).str();
    }
}
