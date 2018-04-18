__CreateJSPath = function (js) {
    var scripts = document.getElementsByTagName("script");
    var path = "";
    for (var i = 0, l = scripts.length; i < l; i++) {
        var src = scripts[i].src;
        if (src.indexOf(js) != -1) {
            var ss = src.split(js);
            path = ss[0];
            break;
        }
    }
    var href = location.href;
    href = href.split("#")[0];
    href = href.split("?")[0];
    var ss = href.split("/");
    ss.length = ss.length - 1;
    href = ss.join("/");
    if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1 && path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
        path = href + "/" + path;
    }
    return path;
}

var bootPATH = __CreateJSPath("boot.js");



//debugger
mini_debugger = true;

//miniui
document.write('<script src="' + bootPATH + 'jquery.min.js" type="text/javascript"></sc' + 'ript>');
document.write('<script src="' + bootPATH + 'miniui/miniui.js" type="text/javascript" ></sc' + 'ript>');
document.write('<link href="' + bootPATH + 'miniui/themes/default/miniui.css" rel="stylesheet" type="text/css" />');
//document.write('<link href="' + bootPATH + 'miniui/themes/icons.css" rel="stylesheet" type="text/css" />');
document.write('<link href="'+__rootPath+'/styles/icons.css" rel="stylesheet" type="text/css" />');
document.write('<link href="' + bootPATH + 'miniui/res/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />');
document.write('<link href="' + bootPATH + 'miniui/themes/learun/skin.css" rel="stylesheet" type="text/css" />');

//判断是否是IE8,并添加IE8专用样式表
if(navigator.userAgent.indexOf("MSIE")>0){ 
	if(navigator.userAgent.indexOf("MSIE 8.0")>0 && !window.innerWidth){
		document.write('<link href="' + bootPATH + 'miniui/themes/default/miniuiIE8.css" rel="stylesheet" type="text/css" />');
	}
}



//mode
var mode = getCookie("miniuiMode");
if (mode) {
    document.write('<link href="' + bootPATH + 'miniui/themes/default/' + mode + '-mode.css" rel="stylesheet" type="text/css" />');
}


//skin
var skin = getCookie("miniuiSkin");
if (skin) {
    document.write('<link href="' + bootPATH + 'miniui/themes/' + skin + '/skin.css" rel="stylesheet" type="text/css" />');
}




////////////////////////////////////////////////////////////////////////////////////////
function getCookie(sName) {
    var aCookie = document.cookie.split("; ");
    var lastMatch = null;
    for (var i = 0; i < aCookie.length; i++) {
        var aCrumb = aCookie[i].split("=");
        if (sName == aCrumb[0]) {
            lastMatch = aCrumb;
        }
    }
    if (lastMatch) {
        var v = lastMatch[1];
        if (v === undefined) return v;
        return unescape(v);
    }
    return null;
}

/**
 * 获取浏览器窗口的宽和高。
 * @returns {___anonymous2345_2371}
 */
function getWindowSize(){
	var width=top.document.documentElement.clientWidth;
	var height=top.document.documentElement.clientHeight;
	return {width:width,height:height};
}
