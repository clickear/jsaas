
$(function(){
	$.ajax({
		url : __rootPath + "/oa/info/insPortalDef/getPersonalPort.do",
		method : "POST",
		success : function(data) {
			$(".personalPort").html(data);
			
			var card_msg_let =  $(".card_msg ul li").length;
			card_msg(card_msg_let);
			cardResponse();
			//IE7、8样式兼容
			if(navigator.userAgent.indexOf("MSIE")>0){ 
				 if(navigator.userAgent.indexOf("MSIE 8.0")>0 && !window.innerWidth){
					 cardResponse();
				 }else if(navigator.userAgent.indexOf("MSIE 7.0")>0){
					 cardResponse();
				 }
			}
		}
	});
});

function refresh(colId) {
	$.ajax({
		url : __rootPath + "/oa/info/insColumnDef/getColHtml.do?colId="+ colId,
		method : "POST",
		success : function(data) {
			$("div[colid=" + colId + "]")[0].outerHTML = data;
		}
	});
}

/**
 * 打开处理窗口。
 * @param url
 * @param title
 * @param solId
 * @returns
 */
function openUrl(url,title){
	_OpenWindow({
		title : title,
		height : 400,
		width : 780,
		max : true,
		url :url,
		ondestroy : function(action) {
			location.href=location.href+"?time="+new Date().getTime();		
		}
	});
}



function showMore(colId, name, moreUrl) {
	moreUrl = __rootPath + moreUrl;
	mgrNewsRow(colId, name, moreUrl);
}

//打开一个新的页面显示这个栏目的more
function mgrNewsRow(colId, name, moreUrl) {
	top['index'].showTabFromPage({
		title : name,
		tabId : 'colNewsMgr_' + colId,
		url : moreUrl
	});
}

//打开设置门户页面
function setPort() {
	$.ajax({
		url : __rootPath + "/oa/info/insPortalDef/setPersonalPort.do",
		method : "POST",
		success : function(data) {
			_OpenWindow({
				title : '门户配置',
				height : 400,
				width : 780,
				max : true,
				url : __rootPath
						+ '/scripts/layoutit/layoutitIndex.jsp?portId='
						+ data,
				ondestroy : function(action) {
					location.reload();
				}
			});
		}
	})
}
$('.mini-tabs-body-top:eq(0)', parent.document).children().addClass('index_box');

function card_msg(num){
	$(".card_msg ul li").css('width','calc(100% / '+num+' - 20px)');
}


$(document).on("click","#Refresh",function(){
	$(this).parents('.widget-body').siblings().show();
});


var windowW=$(window).width();
window.onresize = function(){
	cardResponse();
}; 



function cardResponse(){
	windowW=$(window).width();
	if(windowW < 1000){
		$('body').attr('class', '').addClass('winW1000 winW1190 winW1260 winW1400 winW1600');
	}else if(windowW < 1190){
		$('body').attr('class', '').addClass('winW1190 winW1260 winW1400 winW1600');
	}else if(windowW < 1260){
		$('body').attr('class', '').addClass('winW1260 winW1400 winW1600');
	}else if(windowW < 1400){
		$('body').attr('class', '').addClass('winW1400 winW1600');
	}else if(windowW < 1600){
		$('body').attr('class', '').addClass('winW1600');
	}else{
		$('body').attr('class', '');
	}
};

