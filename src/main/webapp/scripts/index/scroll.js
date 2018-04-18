//兼容函数   
function mouseWheel(obj,upfun,downfun){
    if(obj.addEventListener){
        obj.addEventListener("mousewheel",fn);
        obj.addEventListener("DOMMouseScroll",fn);
    }else{
        obj.attachEvent("onmousewheel",fn);
    }
    function fn(e){
        var ev=e||window.event;
        //鼠标滚轮滚动的方向
        //火狐 ev.detail  向上-3  向下3
        //IE chrome      ev.wheelDelta  向上120 向下-120
        var val=ev.detail||ev.wheelDelta;
        if(val==-3||val==120){
            upfun();
        }else if(val==3||val==-120){
            downfun();
        }
        if(ev.preventDefault){
            ev.preventDefault();
        }else{
            ev.returnValue=false;
        }
    }
}

//事件绑定
function bind(obj, evname, fn) {
	if (obj.addEventListener) {
		obj.addEventListener(evname, fn, false);
	} else {
		obj.attachEvent('on' + evname, function() {
			fn.call(obj);
		});
	}
}

//事件取消
function cancelBind(obj,evname,fn){
	if(obj.detachEvent){
		obj.detachEvent(evname,fn);
	}else{
		obj.removeEventlistener(evname,fn,false);
	}
}

/**
 * 检测浏览器版本
 * 	使用例子：
 * 	if( BrowserDetect.version <= 8 && BrowserDetect.browser == "Explorer"){
 *		alert("IE版本小于8，请更新！");
 *	}
 */
var BrowserDetect = {
	      init: function () {
	         this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
	         this.version = this.searchVersion(navigator.userAgent)
	         || this.searchVersion(navigator.appVersion)
	         || "an unknown version";
	      },
	      searchString: function (data) {
	         for (var i=0;i<data.length;i++) {
	            var dataString = data[i].string;
	            var dataProp = data[i].prop;
	            this.versionSearchString = data[i].versionSearch || data[i].identity;
	            if (dataString) {
	               if (dataString.indexOf(data[i].subString) != -1)
	                  return data[i].identity;
	            }
	            else if (dataProp)
	               return data[i].identity;
	         }
	      },
	      searchVersion: function (dataString) {
	         var index = dataString.indexOf(this.versionSearchString);
	         if (index == -1) return;
	         return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
	      },
	      dataBrowser: [
	         {
	            string: navigator.userAgent,
	            subString: "Chrome",
	            identity: "Chrome"
	         },
	         {
	            string: navigator.vendor,
	            subString: "Apple",
	            identity: "Safari",
	            versionSearch: "Version"
	         },
	         {
	            prop: window.opera,
	            identity: "Opera",
	            versionSearch: "Version"
	         },
	         { 
	            string: navigator.userAgent,
	            subString: "Firefox",
	            identity: "Firefox"
	         },
	         {
	            string: navigator.userAgent,
	            subString: "MSIE",
	            identity: "Explorer",
	            versionSearch: "MSIE"
	         },
	         {
	            string: navigator.userAgent,
	            subString: "Gecko",
	            identity: "Mozilla",
	            versionSearch: "rv"
	         },
	         { // for older Netscapes (4-)
	            string: navigator.userAgent,
	            subString: "Mozilla",
	            identity: "Netscape",
	            versionSearch: "Mozilla"
	         }
	      ],
	   };
BrowserDetect.init();


