package org.underscore.wrappers;

import org.underscore.processor.IncludeInMain;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class $T<R> {

    private Callable<R> runnable;

    public $T(Callable e) {
        runnable = e;
    }

    @IncludeInMain
    public static <T> $T<T> $(Callable<T> t) {
        return new $T(t);
    }

    @IncludeInMain
    public static <T> void schedule(long rate, TimeUnit unit, Callable<T> task) {
        new $T(task).schedule(rate, unit);
    }

    @IncludeInMain
    public static <T> void delay(long delay, TimeUnit unit, Callable<T> task) {
        new $T(task).delay(delay, unit);
    }

    public void delay(long delay, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    public void schedule(long delay, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }
}
