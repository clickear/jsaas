/**
 * 获得元素的所有属性
 * 
 * // Setter
	$('#element').attrs({
	    'name' : 'newName',
	    'id' : 'newId',
	    'readonly': true
	});
	
	// Getter
	var attrs = $('#element').attrs();
 * 
 * 
 * @param $
 */
;(function($) {
    // Attrs
    $.fn.attrs = function(attrs) {
        var t = $(this);
        if (attrs) {
            // Set attributes
            t.each(function(i, e) {
                var j = $(e);
                for (var attr in attrs) {
                    j.attr(attr, attrs[attr]);
                };
            });
            return t;
        } else {
            // Get attributes
            var a = {},
                r = t.get(0);
            if (r) {
                r = r.attributes;
                for (var i in r) {
                    var p = r[i];
                    if (typeof p.nodeValue !== 'undefined') a[p.nodeName] = p.nodeValue;
                }
            }
            return a;
        }
    };
})(jQuery);