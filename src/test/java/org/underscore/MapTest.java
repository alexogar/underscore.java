package org.underscore;

import org.junit.Assert;
import org.junit.Test;
import org.underscore.wrappers.$C;
import org.underscore.wrappers.$M;

import java.util.HashMap;
import java.util.Map;

/**
 * User: alexogar
 * Date: 2/25/13
 * Time: 10:40 AM
 */

/*var doubled = $$.map([1, 2, 3], function(num){ return num * 2; });
equal(doubled.join(', '), '2, 4, 6', 'doubled numbers');

doubled = $$.collect([1, 2, 3], function(num){ return num * 2; });
equal(doubled.join(', '), '2, 4, 6', 'aliased as "collect"');

var tripled = $$.map([1, 2, 3], function(num){ return num * this.multiplier; }, {multiplier : 3});
equal(tripled.join(', '), '3, 6, 9', 'tripled numbers with context');

var doubled = $$([1, 2, 3]).map(function(num){ return num * 2; });
equal(doubled.join(', '), '2, 4, 6', 'OO-style doubled numbers');

if (document.querySelectorAll) {
        var ids = $$.map(document.querySelectorAll('#map-test *'), function(n){ return n.id; });
deepEqual(ids, ['id1', 'id2'], 'Can use collection methods on NodeLists.');
}

        var ids = $$.map($('#map-test').children(), function(n){ return n.id; });
deepEqual(ids, ['id1', 'id2'], 'Can use collection methods on jQuery Array-likes.');

var ids = $$.map(document.images, function(n){ return n.id; });
ok(ids[0] == 'chart_image', 'can use collection methods on HTMLCollections');

var ifnull = $$.map(null, function(){});
ok($$.isArray(ifnull) && ifnull.length === 0, 'handles a null properly');*/
public class MapTest {
    @Test
    public void testMap() {
        $C<Integer> result = $.map(new Integer[]{1, 2, 3}, (Integer i) -> i * 2);
        Assert.assertArrayEquals(new Integer[]{2, 4, 6}, result.array());

        $C<Integer> result2 = $.$(new Integer[]{1, 2, 3}).map((Integer i) -> i * 2);
        Assert.assertArrayEquals(new Integer[]{2, 4, 6}, result2.array());


        $M<Integer, String> result3=$.map(new HashMap<Integer, Integer>() {{
            put(1, 1);
            put(2, 2);
            put(3, 3);
        }}, (Map.Entry<Integer, Integer> entry) -> "test");

        Assert.assertArrayEquals(new String[]{"test", "test", "test"}, result3.values().array());
    }
}
