package org.underscore;


import junit.framework.Assert;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * User: alexogar
 * Date: 2/23/13
 * Time: 9:51 PM
 * $.each([1, 2, 3], function(num, i) {
 equal(num, i + 1, 'each iterators provide value and iteration count');
 });

 var answers = [];
 $.each([1, 2, 3], function(num){ answers.push(num * this.multiplier);}, {multiplier : 5});
 equal(answers.join(', '), '5, 10, 15', 'context object property accessed');

 answers = [];
 $.forEach([1, 2, 3], function(num){ answers.push(num); });
 equal(answers.join(', '), '1, 2, 3', 'aliased as "forEach"');

 var answer = null;
 $.each([1, 2, 3], function(num, index, arr){ if ($.include(arr, num)) answer = true; });
 ok(answer, 'can reference the original collection from inside the iterator');

 answers = 0;
 $.each(null, function(){ ++answers; });
 equal(answers, 0, 'handles a null properly');
 */



public class EachTest {

    @Test
    public void eachTest() {
        List<Integer> answers = new ArrayList<Integer>();
        int multiplier = 5;
        $.each(new Integer[]{1, 2, 3}, (Integer i) -> {
            answers.add(i * multiplier);
        });

        Assert.assertEquals("5,10,15", StringUtils.join(answers,","));

        answers.clear();

        $.each(new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }}, (Integer i) -> {
            answers.add(i * multiplier);
        });

        Assert.assertEquals("5,10,15", StringUtils.join(answers,","));

        answers.clear();

        answers.add(0);
        $.each(new HashMap<Integer, Integer>() {{
            put(1, 1);
            put(2, 2);
            put(3, 3);
        }}, (Integer key, Integer value) -> {
            answers.add(answers.remove(0) + key);
        });

        Assert.assertEquals(6, answers.get(0).intValue());
    }

    @Test
    public void eachWithCountTest() {
        $.each(new Integer[]{1, 2, 3}, (Integer i, Integer count) -> {
            Assert.assertEquals(i.intValue(), count + 1);
        });

        $.each(new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }}, (Integer i, Integer count) -> {
            Assert.assertEquals(i.intValue(), count + 1);
        });


        $.each(new HashMap<Integer, Integer>() {{
            put(1, 1);
            put(2, 2);
            put(3, 3);
        }}, (Integer key, Integer value) -> {
            Assert.assertEquals(key, value);
        });
    }

    @Test
    public void eachWithOriginalTest() {
        //TODO: implement
    }

    @Test
    public void eachWithBadArguments() {
        AtomicBoolean called = new AtomicBoolean(false);
        List l = null;
        $.each(l, (Object i) -> {
            called.set(true);
        });

        Assert.assertEquals(false,called.get());

        called.set(false);
        Object[] l1 = null;
        $.each(l1, (Object i) -> {
            called.set(true);
        });

        Assert.assertEquals(false,called.get());
    }
}
