$(function(){
	var activeList = -1,TimeVal;
	$(document).keydown(function(event){
		if(event.ctrlKey && event.shiftKey && event.keyCode === 70){
			$('.search').css('left',0)
			$('.searchVal').focus();
			event.preventDefault();
		};
		
		var isFocus = $('.searchVal').is(':focus'),
			listLen = $('.listBox li').length;
		if(isFocus){
			if(event.keyCode === 38 && activeList > 0){
				event.preventDefault();
				activeList--;
				$('.listBox li').removeClass('activeSe').eq(activeList).addClass('activeSe');
		    }else if(event.keyCode === 40 && (activeList+1)<listLen){
		    	event.preventDefault();
		    	activeList++;
				$('.listBox li').removeClass('activeSe').eq(activeList).addClass('activeSe');
		    }
		};
		if(event.keyCode === 13 && listLen){
			activeList = -1;
			if($('.listBox li[class=activeSe]').length){
				$('.listBox li[class=activeSe]').trigger("click");
			}else{
				$('.listBox li').each(function(){
					if($(this).text() === $('.searchVal').val()){
						$(this).trigger("click");
					}
				});
			}
		}
	});

	$('.searchVal')
		.bind('input propertychange', function() {  
			$('.listBox').html('');
			var searchValue = $('.searchVal').val();
			if(!searchValue){
				$('.listBox').html('');
				return;
			}
			function getKey(Data){
				var Val = Data,Html='';
				for(var item=0; item<Val.length ; item++){
					if(Val[item].children){
						getKey(Val[item].children);
						continue;
					}
					if(Val[item].text.indexOf(searchValue) != -1){
						Html += "<li menuId="+Val[item].id+" showType="+Val[item].showType+" title="+Val[item].text+" url="+Val[item].url+" iconCls="+Val[item].iconCls+">"+Val[item].text+"</li>"
					}
			    }
				$('.listBox').append(Html)
			};
			getKey(menuData);
		})
		.blur(function(){
			TimeVal = setTimeout(function(){
				$('.search').css('left',-255);
			},4000);
		})
		.focus(function(){
			clearTimeout(TimeVal);
		});
	
	$('.listBox').on('click','li',function(){
		var dataObj = {
			"menuId":$(this).attr('menuId'),
			"showType":$(this).attr('showType'),
			"title":$(this).attr('title'),
			"url":$(this).attr('url'),
			"iconCls":$(this).attr('iconCls')
		};
		showTabPage(dataObj);
		$('.listBox').html('');
		$('.searchVal').val('');
		$('.searchVal').blur();
	});
});

//进入全屏
function requestFullScreen(element) {
	var requestMethod = element.requestFullScreen || element.webkitRequestFullScreen || element.mozRequestFullScreen || element.msRequestFullScreen;
	if (requestMethod) {
		requestMethod.call(element);
	}else if (typeof window.ActiveXObject !== "undefined") {
		var wscript = new ActiveXObject("WScript.Shell");
		if (wscript !== null) {
			wscript.SendKeys("{F11}");
		}
	}
};
//退出全屏
function exitFull() {
	var exitMethod = document.exitFullscreen || document.mozCancelFullScreen || document.webkitExitFullscreen || document.webkitExitFullscreen; 
	if (exitMethod) {
		exitMethod.call(document);
	}
	else if (typeof window.ActiveXObject !== "undefined") {
		var wscript = new ActiveXObject("WScript.Shell");
		if (wscript !== null) {
			wscript.SendKeys("{F11}");
		}
	}
};