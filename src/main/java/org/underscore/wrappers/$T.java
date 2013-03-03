package org.underscore.wrappers;

import org.underscore.processor.IncludeInMain;

import java.util.concurrent.TimeUnit;

public class $T {

    private Runnable runnable;

    public $T(Runnable e) {
        runnable = e;
    }

    @IncludeInMain
    public static $T $T(Runnable t) {
        return new $T(t);
    }

    public static <T extends Runnable> $T $(T e) {
        return new $T(e);
    }

    @IncludeInMain
    public static void schedule(long rate, TimeUnit unit, Runnable task) {
        $T(task).schedule(rate, unit);
    }

    @IncludeInMain
    public static void delay(long delay, TimeUnit unit, Runnable task) {
        $T(task).delay(delay, unit);
    }

    public void delay(long delay, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    public void schedule(long delay, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }
}
