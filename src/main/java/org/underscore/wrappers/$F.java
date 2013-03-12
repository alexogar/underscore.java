package org.underscore.wrappers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * User: alexogar
 * Date: 3/9/13
 * Time: 8:28 PM
 */
public class $F<P,R> implements Function<P,R>{

    private Function<P,R> internal;

    public $F(Runnable runnable) {
        internal = r -> {
            runnable.run();
            return null;
        };
    }

    public $F(Callable<R> callable) {
        internal = (P p) -> {
            R r = null;
            try{r = callable.call();} catch (Exception ignored){}
            return r;
        };
    }

    public $F(Function<P,R> function) {
        internal = function;
    }

    public $F<P,R> memoize() {
        return new $F<>(new Function<P, R>() {

            private Map<P,R> cache = new ConcurrentHashMap<>();

            @Override
            public R apply(P p) {
                if (cache.get(p)==null) {
                    cache.put(p,execute(p));
                }

                return cache.get(p);
            }
        });
    }

    public R execute(P p) {
        return internal.apply(p);
    }

    @Override
    public R apply(P p) {
        return execute(p);
    }
}
