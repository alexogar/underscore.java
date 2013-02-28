package org.underscore;

import org.junit.Assert;
import org.junit.Test;

public class ReduceTest {
    @Test
    public void testReduce() {
        int result = $.reduce(new Integer[]{1, 2, 3}, 1, (Integer memo, Integer j) -> memo * j);
        Assert.assertEquals(6, result);
    }
}
