package org.underscore;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.underscore.wrappers.$A;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.underscore.$.$;
import static org.underscore.$.$A;

public class ArrayTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testFirst() {

        assertEquals("1", $A("1").first());

        assertEquals("1", $A("1", "2", "3").first());

        exception.expect(IndexOutOfBoundsException.class);
        $A().first();

    }

    @Test
    public void testLastN() {

        assertArrayEquals(
                $A("1", "2", "3").array(),
                $A("0", "1", "2", "3")
                        .last(3)
                        .array()
        );

        assertArrayEquals(
                $A("1", "2", "3").array(),
                $.last($A("0", "1", "2", "3").array(), 3).array()
        );

    }

    @Test
    public void testLast() {
        assertEquals($A("1", "2", "3").last(), "3");
    }

    @Test
    public void testLastNFailsWithNegative() {
        exception.expect(IllegalArgumentException.class);
        $A("1").last(-1);
    }

    @Test
    public void testLastNFailsWithOutOfBounds() {
        exception.expect(IllegalArgumentException.class);
        $A("1").last(4);
    }

    @Test
    public void testLastNFailsWithZero() {
        exception.expect(IllegalArgumentException.class);
        $A("1").last(0);
    }

    @Test
    public void testFirstN() {

        assertArrayEquals($A("1").array(),
                $A("1", "2", "3")
                        .first(1)
                        .array());

        assertArrayEquals($A("1", "2").array(),
                $A("1", "2", "3")
                        .first(2)
                        .array());

        exception.expect(IllegalArgumentException.class);
        $A("1").first(2);

    }

    @Test
    public void testFirstNUsingStatic() {
        assertArrayEquals(
                $A("1", "2").array(),
                $.first(Arrays.asList("1", "2", "3"), 2).array()
        );
        assertArrayEquals(
                $A("1", "2").array(),
                $.first(new String[]{"1", "2", "3"}, 2).array()
        );
    }

    @Test
    public void testFirstNFailsWithZero() {
        exception.expect(IllegalArgumentException.class);
        $A("1").first(0);
    }

    @Test
    public void testFirstNFailsWithNegative() {
        exception.expect(IllegalArgumentException.class);
        $A("1").first(-1);
    }

    @Test
    public void testFlatten() {

        assertArrayEquals(
                $A(1, 2, 3).array(),
                $.flatten($A(1, $A(2, 3).array()).array()));

        // two nested arrays
        assertArrayEquals(
                $A(1, 2, 3, 4, 5).array(),
                $.flatten(
                        $A(1, $A(2, 3).array(), $A(4, 5).array()).array()
                ));

        Object[] nested = $A(1, $A(1).array()).array();

        // two level of nesting
        assertArrayEquals(
                $A(1, 1, 1).array(),
                $.flatten($(1, nested).array()));

        // nested array of characters
        assertArrayEquals(
                $A('1', '2', '3').array(),
                $.flatten(
                        $A('1', $A('2').array(), $A('3').array()).array()
                ));

        // plain nested arrays
        assertArrayEquals(
                $A(new String[]{"1"}, "2", new String[]{"3"}).flatten(),
                new String[]{"1", "2", "3"});

    }

    @Test
    public void testIntersectionUsingWrappers() {

        assertArrayEquals(
                $A(1, 2).array(),
                $A(1, 2, 3, 4, 5)
                        .intersectionWith($A(8, 7, 2, 1), $A(6, 5, 2, 1, 4))
                        .array()
        );

        // chained
        assertArrayEquals(
                $A(1, 2).array(),
                $A(1, 2, 3, 4, 5)
                        .intersectionWith($A(8, 7, 2, 1), $A(6, 5, 2, 1, 4))
                        .intersectionWith($A(5, 6, 2, 1))
                        .array()
        );

    }

    @Test
    public void testIntersectionUsingArrays() {

        assertArrayEquals(
                $A(1, 2).array(),
                $A(1, 2, 3, 4, 5)
                        .intersectionWith(new Integer[]{8, 7, 2, 1}, new Integer[]{6, 5, 2, 1, 4})
                        .array()
        );

        assertArrayEquals(
                $A(1, 2).array(),
                $A(1, 2, 3, 4, 5)
                        .intersectionWith(new Integer[]{8, 7, 2, 1}, new Integer[]{6, 5, 2, 1, 4})
                        .intersectionWith(new Integer[]{5, 6, 2, 1})
                        .array()
        );

    }

    @Test
    public void testIntersectionWithStaticCall() {

        assertArrayEquals(
                $A(1, 2).array(),
                $.intersection(
                        $A(1, 2, 3, 4, 5),
                        $A(3, 1, 2, 4, 5),
                        $A(6, 7, 8, 1, 2))
                        .array()
        );

        assertArrayEquals(
                new Integer[]{},
                $.intersection(
                        $A(1, 2, 3),
                        $A(3, 1, 2, 4, 5),
                        $A(6, 7, 8))
                        .array()
        );

        // intersection of plain arrays
        assertArrayEquals(
                $A(1, 2).array(),
                $.intersection(
                        $A(1, 2, 3, 4, 5).array(),
                        $A(3, 1, 2, 4, 5).array(),
                        $A(6, 7, 8, 1, 2).array())
                        .array()
        );
    }

    @Test
    public void testUnionUsingWrappers() {

        assertArrayEquals($A(1, 2, 3, 4).array(),
                $.union($A(1, 2), $A(2, 3), $A(3, 4)).array());

        assertArrayEquals($A(1, 2, 3, 4).array(),
                $.union(
                        $A(1, 2).array(),
                        $A(2, 3).array(),
                        $A(3, 4).array())
                        .array());

    }

    @Test
    public void testUnion() {

        assertArrayEquals($A(1, 2, 3, 4).array(),
                $A(1, 2).unionWith($A(2, 3), $A(3, 4)).array());

        assertArrayEquals($A(1, 2, 3, 4).array(),
                $A(1, 2).unionWith(
                        $A(2, 3).array(),
                        $A(3, 4).array())
                        .array());

    }

    @Test
    public void testUnionWithEmpty() {

        $A<Integer> wrapper = $A(1, 2, 3);

        assertArrayEquals(
                wrapper.unionWith(new Integer[]{}).array(),
                wrapper.array());

    }


}
