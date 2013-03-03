package org.underscore;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.underscore.$.$;

public class ReduceTest {
    @Test
    public void testReduce() {
        long result = $.reduce(new Integer[]{1, 2, 3}, 1l, (Long memo, Integer j) -> memo * j);
        Assert.assertEquals(6, result);
    }

    @Test
    public void testReduceRight() {
        Integer[] testArray = {1, 2, 3};
        Integer[] expectedArray = {3, 2, 1};
        ArrayList result = $(testArray).reduceRight(
                new ArrayList<Integer>(),
                (ArrayList<Integer> memo, Integer j) -> {
                    memo.add(j);
                    return memo;
                });

        Assert.assertArrayEquals(expectedArray, $(result).array());
    }

}
