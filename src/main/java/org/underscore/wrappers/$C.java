package org.underscore.wrappers;

import org.underscore.functors.*;

import java.util.*;

import static org.underscore.$.$;

/**
 * User: alexogar
 * Date: 2/24/13
 * Time: 1:25 PM
 */
public class $C<T> extends AbstractCollection<T>{

    private final Collection<T> internal;

    public $C() {
        this(new ArrayList<>());
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

    public $C<T> each(EachNextVisitor<T> visitor) {
        int i = 0;
        for (T t : internal) {
            $O<Boolean> called = $(false);
            visitor.visit(t,()->{called.set(true);});
            if (!called.get()){
                break;
            }
        }
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

    public <MEMO> MEMO reduce(MEMO memo, ReducePairVisitor<MEMO,T> visitor){
        $O<MEMO> work = $(memo);
        each((T t)->{
            work.set(visitor.visit(work.get(),t));
        });

        return work.get();
    }

    public T find(Matcher<T> matcher) {

        $O<T> result = new $O<>();

        each((T t,Next f)-> {
            if (!matcher.match(t)) {
                f.call();
            } else {
                result.set(t);
            }
        });


        return result.get();
    }

    public $C<T> filter(Matcher<T> matcher) {

        $C<T> filtered = new $C<>();

        each((T t)->{
            if (matcher.match(t)){
                filtered.add(t);
            }
        });

        return filtered;
    }

    public T[] array() {
        return (T[]) internal.toArray();
    }

    @Override
    public Iterator<T> iterator() {
        return internal.iterator();
    }

    @Override
    public boolean add(T t) {
        return internal.add(t);
    }

    @Override
    public int size() {
        return internal.size();
    }
}
