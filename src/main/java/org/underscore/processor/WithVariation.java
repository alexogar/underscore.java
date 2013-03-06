package org.underscore.processor;

import org.underscore.util.VariationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * User: alexogar
 * Date: 3/7/13
 * Time: 1:55 AM
 */
@Target(ElementType.PARAMETER)
public @interface WithVariation {
    VariationType[] types();
    String generic();
}
