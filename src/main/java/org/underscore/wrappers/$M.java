package org.underscore.wrappers;

import org.underscore.functors.EachPairVisitor;
import org.underscore.functors.EachVisitor;
import org.underscore.functors.TransformVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: alexogar
 * Date: 2/25/13
 * Time: 10:14 AM
 */
public class $M<K,V> {
    private final Map<K,V> internal;

    public $M() {
        internal = new HashMap<>();
    }

    public $M(Map<K,V> map) {
        this.internal = map;
    }

    public $M<K,V> each(EachPairVisitor<K, V> visitor) {
        for (Map.Entry<K,V> entry : internal.entrySet()) {
            visitor.visit(entry.getKey(),entry.getValue());
        }
        return this;
    }

    public $M<K, V> each(EachVisitor<Map.Entry<K, V>> visitor) {
        for (Map.Entry<K,V> entry : internal.entrySet()) {
            visitor.visit(entry);
        }
        return this;
    }
}
