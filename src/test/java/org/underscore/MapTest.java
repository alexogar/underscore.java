package org.underscore;

import org.junit.Assert;
import org.junit.Test;
import org.underscore.wrappers.$C;

/**
 * User: alexogar
 * Date: 2/25/13
 * Time: 10:40 AM
 */

/*var doubled = _.map([1, 2, 3], function(num){ return num * 2; });
equal(doubled.join(', '), '2, 4, 6', 'doubled numbers');

doubled = _.collect([1, 2, 3], function(num){ return num * 2; });
equal(doubled.join(', '), '2, 4, 6', 'aliased as "collect"');

var tripled = _.map([1, 2, 3], function(num){ return num * this.multiplier; }, {multiplier : 3});
equal(tripled.join(', '), '3, 6, 9', 'tripled numbers with context');

var doubled = _([1, 2, 3]).map(function(num){ return num * 2; });
equal(doubled.join(', '), '2, 4, 6', 'OO-style doubled numbers');

if (document.querySelectorAll) {
        var ids = _.map(document.querySelectorAll('#map-test *'), function(n){ return n.id; });
deepEqual(ids, ['id1', 'id2'], 'Can use collection methods on NodeLists.');
}

        var ids = _.map($('#map-test').children(), function(n){ return n.id; });
deepEqual(ids, ['id1', 'id2'], 'Can use collection methods on jQuery Array-likes.');

var ids = _.map(document.images, function(n){ return n.id; });
ok(ids[0] == 'chart_image', 'can use collection methods on HTMLCollections');

var ifnull = _.map(null, function(){});
ok(_.isArray(ifnull) && ifnull.length === 0, 'handles a null properly');*/
public class MapTest {
    @Test
    public void testMap() {
        $C<Integer> result= $.map(new Integer[]{1,2,3},(Integer i) -> i*2);
        Assert.assertArrayEquals(new Integer[]{2,4,6},result.array());
    }
}
