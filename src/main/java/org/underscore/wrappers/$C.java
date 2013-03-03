package org.underscore.wrappers;

import org.underscore.functors.*;
import org.underscore.processor.IncludeInMain;

import java.util.*;

/**
 * User: alexogar
 * Date: 2/24/13
 * Time: 1:25 PM
 */
public class $C<T> extends AbstractCollection<T>{

    private final ArrayList<T> internal;

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
        internal = new ArrayList<>(it);
    }

    public $C<T> each(EachVisitor<T> visitor) {
        for (T e : internal) visitor.visit(e);
        return this;
    }

    public $C<T> eachBack(EachVisitor<T> visitor) {
        for(ListIterator<T> it = internal.listIterator(internal.size()); it.hasPrevious();) {
            visitor.visit(it.previous());
        }
        return this;
    }

    public $C<T> each(EachNextVisitor<T> visitor) {
        int i = 0;
        for (T t : internal) {
            $O<Boolean> called = $O.$(false);
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
        $O<MEMO> work = $O.$(memo);
        each((T t)->{
            work.set(visitor.visit(work.get(),t));
        });

        return work.get();
    }

    public <MEMO> MEMO reduceRight(MEMO memo, ReducePairVisitor<MEMO,T> visitor) {
        $O<MEMO> work = $O.$(memo);
        eachBack((T t)-> work.set(visitor.visit(work.get(),t)));
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

    public $C<T> reject(Matcher<T> matcher) {
        return filter((T t)->!matcher.match(t));
    }

    public boolean every(Matcher<T> matcher) {
        return find((T t)->!matcher.match(t))==null;
    }

    public boolean some(Matcher<T> matcher) {
        return find(matcher)!=null;
    }

    public T[] array() {
        return (T[]) internal.toArray();
    }

    @IncludeInMain
    public static <E> $C<E> each(E[] it, EachVisitor<E> visitor) {
        return $(it).each(visitor);
    }

    @IncludeInMain
    public static <E> $C<E> $(Collection<E> it) {
        return new $C<>(it);
    }

    @IncludeInMain
    public static <E> $C<E> $(E[] it) {
        return new $C<>(it);
    }

    @IncludeInMain
    public static <E> $C<E> each(Collection<E> it, EachVisitor<E> visitor) {
        return $(it).each(visitor);
    }

    @IncludeInMain
    public static <E> $C<E> each(E[] it, EachPairVisitor<E, Integer> visitor) {
        return $(it).each(visitor);
    }

    @IncludeInMain
    public static <E> $C<E> each(Collection<E> it, EachPairVisitor<E, Integer> visitor) {
        return $(it).each(visitor);
    }

    @IncludeInMain
    public static <F,T> $C<T> map(Collection<F> it, TransformVisitor<F,T> visitor){
        return $(it).map(visitor);
    }

    @IncludeInMain
    public static <F,T> $C<T> map(F[] it, TransformVisitor<F,T> visitor) {
        return $(it).map(visitor);
    }

    @IncludeInMain
    public static <K,MEMO> MEMO reduce(K[] it, MEMO memo, ReducePairVisitor<MEMO,K> visitor) {
        return $(it).reduce(memo,visitor);
    }

    @IncludeInMain
    public static <K,MEMO> MEMO reduceRight(K[] it, MEMO memo, ReducePairVisitor<MEMO,K> visitor) {
        return $(it).reduce(memo,visitor);
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
