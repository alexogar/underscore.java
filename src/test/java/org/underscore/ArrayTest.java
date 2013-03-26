package org.underscore;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.underscore.$.*;

public class ArrayTest {

    @Test
    public void flattenTest() {

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


    }

}
