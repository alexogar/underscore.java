package org.underscore;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.underscore.$.*;

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


}
