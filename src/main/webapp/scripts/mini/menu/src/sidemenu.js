var SideMenu = function (element, options) {
    this.element = $(element);
    this.options = $.extend(true, {}, this.options, options);
    this.init();

}
SideMenu.prototype = {

    options: {
        data: null

    },

    init: function () {
        var me = this,
            opt = me.options,
            el = me.element;

        el.addClass('sidemenu');

        var sb = [];
        me._renderItems(sb, opt.data);
        el.html(sb.join(''));
        
//        console.info(sb.join(''));
        

        el.on('click', '.sidemenu-item', function (event) {

            var item = me.getItemByEvent(event);

            this.url = $(this).attr("url");
            
            if (!item.children) {
                showTab(this);
            }
        });

        el.on('mouseenter', '.sidemenu-item', function (event) {

            var jq = $(event.currentTarget);
            //jq.next('ul').show();
            var ul = jq.next('ul');
            if (!ul[0]) return;

            ul.css("top", 0);

            var viewHeight = $(window).height();
            var offset = ul.offset(),
                height = ul.outerHeight();

            if (offset.top + height > viewHeight) {
                offset.top = viewHeight - height;
                ul.offset(offset);
                ul.css("left", "");
            }

        });
    },

    getItemByEvent: function (event) {
        var el = $(event.target).closest('.sidemenu-item');
        var id = el.attr("id");
        return this.getItemById(id);
    },

    getItemById: function (id) {
        var me = this,
            idMap = me._idMap;

        if (!idMap) {
            idMap = me._idMap = {};
            function each(items) {
                for (var i = 0, l = items.length; i < l; i++) {
                    var item = items[i];
                    if (item.children) each(item.children);
                    idMap[item.id] = item;
                }
            }
            each(me.options.data);
        }

        return me._idMap[id];
    },

    _renderItems: function (sb, items) {
        sb[sb.length] = '<ul>';
        for (var i = 0, l = items.length; i < l; i++) {
            var item = items[i];
            this._renderItem(sb, item);
        }
        sb[sb.length] = '</ul>';
    },

    _renderItem: function (sb, item) {
        var me = this,
            children = item.children,
            hasChildren = children && children.length > 0;

        sb[sb.length] = '<li >';

        if (item.url) {      
            sb[sb.length] = '<a id="' + item.id + '"  url="' + item.url + '" class="sidemenu-item" showType="'+item.showType+'" key="'+item.key+'">';
        }
        else {
            sb[sb.length] = '<a id="' + item.id + '" class="sidemenu-item" showType="'+item.showType+'" key="'+item.key+'">';
        }
        sb[sb.length] = '<span class="sidemenu-icon ' + item.iconCls + '"></span>';
        sb[sb.length] = '<span class="sidemenu-text">' + item.text + '</span>';

        if (hasChildren) {
            sb[sb.length] = '<span class="sidemenu-arrow"></span>';
        }
        sb[sb.length] = '</a>';

        if (hasChildren) {
            me._renderItems(sb, children);
        }

        sb[sb.length] = '</li>';
    }

}

$.fn.sidemenu = function (options) {

    var isSTR = typeof options == "string",
        args, ret;

    if (isSTR) {
        args = $.makeArray(arguments)
        args.splice(0, 1);
    }

    var name = "sidemenu",
        type = SideMenu;

    var jq = this.each(function () {
        var ui = $.data(this, name);

        if (!ui) {
            ui = new type(this, options);
            $.data(this, name, ui);
        }
        if (isSTR) {
            ret = ui[options].apply(ui, args);
        }
    });

    return isSTR ? ret : jq;
};
