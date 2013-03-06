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

        Assert.assertEquals("should return first found 'value'",$.find(new Integer[]{1,2,3,4}, (Integer i) -> i > 2).intValue(),3);
        Assert.assertEquals("should return `undefined` if `value` is not found",$.find(new Integer[]{1,2,3,4}, (Integer i) -> i < 0),null);
    }

    @Test
    public void filterTest() {
//        var evens = _.select([1, 2, 3, 4, 5, 6], function(num){ return num % 2 == 0; });
//        equal(evens.join(', '), '2, 4, 6', 'selected each even number');

        $C<Integer> evens = $.filter(new Integer[]{1,2,3,4,5,6}, (Integer i) -> i % 2 ==0);

    }

}
