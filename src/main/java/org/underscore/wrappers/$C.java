package org.underscore.wrappers;

import org.underscore.functors.*;
import org.underscore.processor.IncludeInMain;

import java.util.*;

/**
 * This class will represent wrapper on all Collections specific functions for our super library.
 * It will be suitable for arrays as well.
 */
public class $C<T> extends AbstractCollection<T>{

    private final Collection<T> internal;

    /**
     * Empty constructor will initialize internal ArrayList.
     */
    public $C() {
        this(new ArrayList<>());
    }

    /**
     * Constructor with array, will initalize internal ArrayList with array base.
     * @param arr Array input parameter
     */
    public $C(T[] arr) {
        if (arr == null) {
            internal = new ArrayList<>();
            return;
        }
        internal = new ArrayList<>(Arrays.asList(arr));
    }

    /**
     * Constructor with collection initialization. Passed collection will be used as internal implementation.
     * @param it
     */
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

    public $C<T> eachBack(EachVisitor<T> visitor) {
        reverse().each(visitor);
        return this;
    }

    public $C<T> reverse() {
        if (!isList()) {
            throw new UnsupportedOperationException("Operation is only supported for List implementations");
        }

        List<T> list = new ArrayList<>(internal);
        Collections.reverse(list);
        return new $C<>(list);
    }

    public List<T> list() {
        return isList()? (List<T>) internal :new ArrayList<>(internal);
    }

    public boolean isList() {
        return internal instanceof List;
    }

    public $C<T> each(EachNextVisitor<T> visitor) {
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
