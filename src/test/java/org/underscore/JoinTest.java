package org.underscore;

import junit.framework.Assert;
import org.junit.Test;

import static org.underscore.$.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * User: alexogar
 * Date: 3/7/13
 * Time: 1:38 AM
 */
public class JoinTest {
    private static final Collection<Integer> ITERABLE_ = Arrays.<Integer>asList();
    private static final Collection<Integer> ITERABLE_1 = Arrays.asList(1);
    private static final Collection<Integer> ITERABLE_12 = Arrays.asList(1, 2);
    private static final Collection<Integer> ITERABLE_123 = Arrays.asList(1, 2, 3);
    private static final Collection<Integer> ITERABLE_NULL = Arrays.asList((Integer) null);
    private static final Collection<Integer> ITERABLE_NULL_NULL
            = Arrays.asList((Integer) null, null);
    private static final Collection<Integer> ITERABLE_NULL_1 = Arrays.asList(null, 1);
    private static final Collection<Integer> ITERABLE_1_NULL = Arrays.asList(1, null);
    private static final Collection<Integer> ITERABLE_1_NULL_2 = Arrays.asList(1, null, 2);
    private static final Collection<Integer> ITERABLE_FOUR_NULLS
            = Arrays.asList((Integer) null, null, null, null);

    @Test
    public void simpleTest() {
        Assert.assertEquals("1", $(ITERABLE_1).join());
        Assert.assertEquals("1,2",$(ITERABLE_12).join());
        Assert.assertEquals("1,2,3",$(ITERABLE_123).join());
        Assert.assertEquals("null",$(ITERABLE_NULL).join());
        Assert.assertEquals("1,null",$(ITERABLE_1_NULL).join());
        Assert.assertEquals("1,null,2",$(ITERABLE_1_NULL_2).join());
    }
}
