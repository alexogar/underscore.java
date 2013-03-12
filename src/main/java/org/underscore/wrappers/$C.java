package org.underscore.wrappers;

import com.sun.corba.se.impl.orb.ParserTable;
import org.underscore.functors.*;
import org.underscore.processor.IncludeInMain;

import java.io.IOException;
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
     * Constructor with array, will initialize internal ArrayList with array base.
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

    /**
     * Each will iterate collection and call visitor method.
     *
     * @param visitor EachVisitor
     * @return self for chaining
     */
    public $C<T> each(EachVisitor<T> visitor) {
        for (T e : internal) visitor.visit(e);
        return this;
    }

    /**
     * Iterates thought collection backwards, this working only for List implementations.
     * @param visitor
     * @return self for chaining
     */
    public $C<T> eachBack(EachVisitor<T> visitor) {
        reverse().each(visitor);
        return this;
    }

    /**
     * Each implementation with ability to stop iteration.
     * @param visitor EachNextVisitor that will need to call next each iteration to proceed
     * @return self for chaining
     */
    public $C<T> each(EachNextVisitor<T> visitor) {
        //We iterating through original
        for (T t : internal) {
            //Prepare object for checking whether next call was called
            $O<Boolean> called = $O.$(false);
            //Visit element
            visitor.visit(t,()->called.set(true));
            //Check whether next was called in visitor, if not then break
            if (!called.get()){
                break;
            }
        }
        return this;
    }

    /**
     * Each implementation with index also passed along with element
     * @param visitor visitor with element and index
     * @return self for chaining
     */
    public $C<T> each(EachPairVisitor<T, Integer> visitor) {
        int i = 0;
        for (T t : internal) {
            visitor.visit(t, i);
            i++;
        }
        return this;
    }

    /**
     * Reverses entire collection, this will work only for List implementation because they only have order.
     * @return new $C instance with reverted collection
     */
    public $C<T> reverse() {
        if (!isList()) {
            throw new UnsupportedOperationException("Operation is only supported for List implementations");
        }

        List<T> list = new ArrayList<>(internal);
        Collections.reverse(list);
        return new $C<>(list);
    }

    /**
     * List representation transformer
     * @return original if it was list, or new ArrayList if not
     */
    public List<T> list() {
        return isList()? (List<T>) internal :new ArrayList<>(internal);
    }

    /**
     * Will check whether original object was list
     * @return instanceof List
     */
    public boolean isList() {
        return internal instanceof List;
    }

    /**
     * Popular function will produce new Collection applying transform function to each element in original collection.
     * @param visitor transform visitor
     * @param <E> result collection type
     * @return resulted collection
     */
    public <E> $C<E> map(TransformVisitor<T,E> visitor){
        List<E> list = new ArrayList<>();
        each((T f)->{list.add(visitor.visit(f));});
        return new $C<>(list);
    }

    /**
     * Reduce boils down a list of values into a single value.
     * MEMO is the initial state of the reduction, and each successive step of it should be returned by visitor.
     * The visitor is passed two arguments:
     * the memo, then the value.
     *
     * @param memo is the initial state of the reduction
     * @param visitor visitor with memo and element
     * @param <MEMO> reduction base
     * @return memo value
     */
    public <MEMO> MEMO reduce(MEMO memo, ReducePairVisitor<MEMO,T> visitor){
        $O<MEMO> work = $O.$(memo);
        each((T t)->
            work.set(visitor.visit(work.get(),t))
        );

        return work.get();
    }

    /**
     * Reduces list in backward order. see reduce method.
     *
     * @param memo is the initial state of the reduction
     * @param visitor visitor with memo and element
     * @param <MEMO> reduction base
     * @return memo value
     */
    public <MEMO> MEMO reduceRight(MEMO memo, ReducePairVisitor<MEMO,T> visitor) {
        $O<MEMO> work = $O.$(memo);
        eachBack((T t) -> work.set(visitor.visit(work.get(), t)));
        return work.get();
    }

    /**
     * Looks through each value in the list, returning the first one that passes a truth test visitor.
     * The function returns as soon as it finds an acceptable element, and doesn't traverse the entire list.
     *
     * @param matcher test visitor
     * @return found element or null
     */
    public T find(Matcher<T> matcher) {

        $O<T> result = new $O<>();
        //Will each with next original, to not call next when found
        each((T t,Next f)-> {
            if (!matcher.match(t)) {
                f.call();
            } else {
                result.set(t);
            }
        });

        //return result or null
        return result.get();
    }

    /**
     * Looks through each value in the list, returning an array of all the values that pass a truth test matcher.
     * @param matcher test visitor
     * @return new filtered collection wrapper
     */
    public $C<T> filter(Matcher<T> matcher) {

        $C<T> filtered = new $C<>();

        each((T t)->{
            if (matcher.match(t)){
                filtered.add(t);
            }
        });

        return filtered;
    }

    /**
     * Opposite of filter method.
     * @param matcher test visitor
     * @return new filtered collection wrapper
     */
    public $C<T> reject(Matcher<T> matcher) {
        return filter((T t)->!matcher.match(t));
    }

    /**
     * Returns whether all elements of collection matches to matcher functor.
     *
     * @param matcher test visitor
     * @return all of items matches test visitor functor
     */
    public boolean every(Matcher<T> matcher) {
        return isEmpty() || find((T t) -> !matcher.match(t)) == null;
    }

    /**
     * Returns true if all of objects in collections are true after Boolean cast
     * @return true if all of objects true
     */
    public boolean every() {
        return every((T t)->Boolean.valueOf(t.toString()));
    }

    /**
     * Returns true if any of the values in the list pass the iterator truth test.
     * Short-circuits and stops traversing the list if a true element is found.
     *
     * @param matcher test visitor
     * @return true if any matches
     */
    public boolean some(Matcher<T> matcher) {
        return isEmpty() || find(matcher)!=null;
    }

    /**
     * Some of them after cast to Boolean returns true
     * @return true if some of them true
     */
    public boolean some() {
        return some((T t)->Boolean.valueOf(t.toString()));
    }

    /**
     * Joins collection using ',' separator
     * @return joined string
     */
    public String join() {
        return join(",");
    }

    /**
     * Joins collection using passed separator
     * @param separator to use
     * @return joined string
     */
    public String join(String separator) {
        return joinTo(separator,new StringBuilder()).toString();
    }

    /**
     * Append joined string to passed builder with passed separateor
     * @param separator
     * @param builder
     * @return builder
     */
    public StringBuilder joinTo(String separator,StringBuilder builder) {
        try {
            joinTo(separator, (Appendable) builder);
        } catch (IOException impossible) {
            throw new AssertionError();
        }
        return builder;
    }

    /**
     * Join to appendable
     * @param separator to use
     * @param appendable {@link Appendable}
     * @return changed appendable
     * @throws IOException
     */
    public Appendable joinTo(String separator,Appendable appendable) throws IOException {
        Iterator<T> it = internal.iterator();
        if (it.hasNext()) {
            appendable.append($O.str(it.next()));
            while (it.hasNext()) {
                appendable.append(separator);
                appendable.append($O.str(it.next()));
            }
        }
        return appendable;
    }

    public T max() {
        $O<T> result = new $O<>();
        each((T entity)->{
            if (!(entity instanceof Comparable)) {
                throw new IllegalStateException("Should be comparable to find maximum");
            }

            if (result.get()==null) {
                result.set(entity);
            } else {
                Comparable<T> c = (Comparable<T>) entity;

                if (c.compareTo(result.get())>0) {
                    result.set(entity);
                }
            }
        });

        return result.get();
    }

    public T min() {
        $O<T> result = new $O<>();
        each((T entity)->{
            if (!(entity instanceof Comparable)) {
                throw new IllegalStateException("Should be comparable to find maximum");
            }

            if (result.get()==null) {
                result.set(entity);
            } else {
                Comparable<T> c = (Comparable<T>) entity;

                if (c.compareTo(result.get())<0) {
                    result.set(entity);
                }
            }
        });

        return result.get();
    }

    /**
     * Array transformation
     * @return array
     */
    @SuppressWarnings("unchecked")
    public T[] array() {
        return (T[]) internal.toArray();
    }

    @IncludeInMain
    public static <E> String join(Collection<E> collection) {
        return new $C<>(collection).join();
    }

    @IncludeInMain
    public static <E> String join(E[] collection) {
        return new $C<>(collection).join();
    }

    @IncludeInMain
    public static <E> String join(Collection<E> collection,String separator) {
        return new $C<>(collection).join(separator);
    }

    @IncludeInMain
    public static <E> String join(E[] collection,String separator) {
        return new $C<>(collection).join(separator);
    }

    @IncludeInMain
    public static <E> Appendable joinTo(Collection<E> collection,String separator, Appendable appendable) throws IOException {
        return new $C<>(collection).joinTo(separator, appendable);
    }

    @IncludeInMain
    public static <E> Appendable joinTo(E[] collection,String separator, Appendable appendable) throws IOException {
        return new $C<>(collection).joinTo(separator, appendable);
    }

    @IncludeInMain
    public static <E> Appendable joinTo(Collection<E> collection,String separator, StringBuilder appendable) {
        return new $C<>(collection).joinTo(separator, appendable);
    }

    @IncludeInMain
    public static <E> Appendable joinTo(E[] collection,String separator, StringBuilder appendable) {
        return new $C<>(collection).joinTo(separator, appendable);
    }

    @IncludeInMain
    public static <E> E find(Collection<E> collection, Matcher<E> matcher) {
        return new $C<>(collection).find(matcher);
    }

    @IncludeInMain
    public static <E> E find(E[] array, Matcher<E> matcher) {
        return new $C<>(array).find(matcher);
    }

    @IncludeInMain
    public static <E> $C<E> filter(E[] array, Matcher<E> matcher) {
        return new $C<>(array).filter(matcher);
    }

    @IncludeInMain
    public static <E> $C<E> filter(Collection<E> collection, Matcher<E> matcher) {
        return new $C<>(collection).filter(matcher);
    }

    @IncludeInMain
    public static <E> $C<E> reject(E[] array, Matcher<E> matcher) {
        return new $C<>(array).reject(matcher);
    }

    @IncludeInMain
    public static <E> $C<E> reject(Collection<E> collection, Matcher<E> matcher) {
        return new $C<>(collection).reject(matcher);
    }

    @IncludeInMain
    public static <E> boolean every(E[] array, Matcher<E> matcher) {
        return new $C<>(array).every(matcher);
    }

    @IncludeInMain
    public static <E> boolean every(Collection<E> collection, Matcher<E> matcher) {
        return new $C<>(collection).every(matcher);
    }

    @IncludeInMain
    public static <E> boolean every(E[] array) {
        return new $C<>(array).every();
    }

    @IncludeInMain
    public static <E> boolean every(Collection<E> collection) {
        return new $C<>(collection).every();
    }

    @IncludeInMain
    public static <E> boolean some(E[] array, Matcher<E> matcher) {
        return new $C<>(array).some(matcher);
    }

    @IncludeInMain
    public static <E> boolean some(Collection<E> collection, Matcher<E> matcher) {
        return new $C<>(collection).some(matcher);
    }

    @IncludeInMain
    public static <E> boolean some(E[] array) {
        return new $C<>(array).some();
    }

    @IncludeInMain
    public static <E> boolean some(Collection<E> collection) {
        return new $C<>(collection).some();
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
    public static <E> $C<E> $(E first, E second, E... rest) {
        E[] args = (E[]) new Object[rest.length+2];

        args[0]=first;
        args[1]=second;

        System.arraycopy(rest,0,args,2,rest.length);
        return new $C<>(args);
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
