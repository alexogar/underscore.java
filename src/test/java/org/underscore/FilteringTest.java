package org.underscore;

import junit.framework.Assert;
import org.underscore.$;
import org.junit.Test;
import org.underscore.wrappers.$C;

/**
 * User: alexogar
 * Date: 3/5/13
 * Time: 10:48 PM
 */
public class FilteringTest {

    @Test
    public void findTest() {
//        var array = [1, 2, 3, 4];
//        strictEqual(_.find(array, function(n) { return n > 2; }), 3, 'should return first found `value`');
//        strictEqual(_.find(array, function() { return false; }), void 0, 'should return `undefined` if `value` is not found');

        Assert.assertEquals("should return first found 'value'", $.find(new Integer[]{1, 2, 3, 4}, (Integer i) -> i > 2).intValue(), 3);
        Assert.assertEquals("should return `undefined` if `value` is not found", $.find(new Integer[]{1, 2, 3, 4}, (Integer i) -> i < 0), null);
    }

    @Test
    public void filterTest() {
//        var evens = _.select([1, 2, 3, 4, 5, 6], function(num){ return num % 2 == 0; });
//        equal(evens.join(', '), '2, 4, 6', 'selected each even number');

        $C<Integer> evens = $.filter(new Integer[]{1, 2, 3, 4, 5, 6}, (Integer i) -> i % 2 == 0);
        Assert.assertEquals("2,4,6", evens.join());
    }

    @Test
    public void rejectTest() {
        $C<Integer> odds = $.reject(new Integer[]{1, 2, 3, 4, 5, 6}, (Integer i) -> i % 2 == 0);
        Assert.assertEquals("1,3,5", odds.join());
    }

    @Test
    public void everyTest() {
//        ok(_.all([], _.identity), 'the empty set');
//        ok(_.all([true, true, true], _.identity), 'all true values');
//        ok(!_.all([true, false, true], _.identity), 'one false value');
//        ok(_.all([0, 10, 28], function(num){ return num % 2 == 0; }), 'even numbers');
//        ok(!_.all([0, 11, 28], function(num){ return num % 2 == 0; }), 'an odd number');
//        ok(_.all([1], _.identity) === true, 'cast to boolean - true');
//        ok(_.all([0], _.identity) === false, 'cast to boolean - false');
//        ok(_.every([true, true, true], _.identity), 'aliased as "every"');
//        ok(!_.all([undefined, undefined, undefined], _.identity), 'works with arrays of undefined');

        Assert.assertEquals($.every(new Integer[]{}, (Integer i) -> true), true);
        Assert.assertEquals($.every(new Boolean[]{true, true, true}, (Boolean b) -> b), true);
        Assert.assertEquals($.every(new Boolean[]{true, false, true}, (Boolean b) -> b), false);
        Assert.assertEquals($.every(new Integer[]{0, 10, 28}, (Integer i) -> i % 2 == 0), true);
        Assert.assertEquals($.every(new Integer[]{0, 11, 28}, (Integer i) -> i % 2 == 0), false);
        Assert.assertEquals($.every(new Integer[]{null, null, null}, (Integer i) -> i == null), true);
    }

    @Test
    public void someTest() {

//        ok(!_.any([]), 'the empty set');
//        ok(!_.any([false, false, false]), 'all false values');
//        ok(_.any([false, false, true]), 'one true value');
//        ok(_.any([null, 0, 'yes', false]), 'a string');
//        ok(!_.any([null, 0, '', false]), 'falsy values');
//        ok(!_.any([1, 11, 29], function(num){ return num % 2 == 0; }), 'all odd numbers');
//        ok(_.any([1, 10, 29], function(num){ return num % 2 == 0; }), 'an even number');
//        ok(_.any([1], _.identity) === true, 'cast to boolean - true');
//        ok(_.any([0], _.identity) === false, 'cast to boolean - false');
//        ok(_.some([false, false, true]), 'aliased as "some"');
//        Array.prototype.some = nativeSome;

        Assert.assertEquals($.some(new Integer[]{}, (Integer i) -> true), true);
        Assert.assertEquals($.some(new Boolean[]{true, true, true}, (Boolean b) -> b), true);
        Assert.assertEquals($.some(new Boolean[]{false, false, true}, (Boolean b) -> b), true);
        Assert.assertEquals($.some(new Integer[]{0, 10, 28}, (Integer i) -> i % 2 == 0), true);
        Assert.assertEquals($.some(new Integer[]{1, 11, 29}, (Integer i) -> i % 2 == 0), false);

    }

}
