var num=0,
	tabUl=$("#min_title_list"),
	hide_nav=$("#Hui-tabNav"),
	n=0;
function tabNavallwidth(){
	var $tabNav = hide_nav.find(".acrossTab"),
		$tabNavWp = hide_nav.find(".top_tab_box"),
		$tabNavmore =hide_nav.find(".Hui-tabNav-more"),
		taballwidth=tabUl.innerWidth();
		
	if (!$tabNav[0]){return;}

	var w = $tabNavWp.width() - 25;
	if(taballwidth>w){
		$tabNavmore.show();
	}
	else{
		n=0;
		$tabNavmore.hide();
		tabUl.css("left",0);
		tabUl.css("right",'');
	}
}

/*菜单导航*/
function Hui_admin_tab(obj){
	
	var	menuId=$(obj).attr('menu-id'),
		href = $(obj).attr('data-href'),
		title = $(obj).attr("data-title"),
		showType=$(obj).attr("showType");
	showTabPage({
		menuId:menuId,
		url:href,
		title:title,
		showType:showType
	});
}


function showTabPage(config){
	var topWindow = $(window.parent.document),
		show_navLi = topWindow.find("#min_title_list li"),
		iframe_box = topWindow.find("#iframe_box");
	
	var bStop,bStopIndex;
	if(!config.title || config.title=="" ){
		return false;
	}

	show_navLi.each(function() {
		if( $(this).find('span').attr("menuid") == config.menuId ){//判断是否已经打开
			bStop=true;
			bStopIndex=show_navLi.index($(this));
			return false;
		}
	});
	if(bStop){
		show_navLi.removeClass("active").eq(bStopIndex).addClass("active");
		show_navLi.children("span").removeClass("activeSpan").eq(bStopIndex).addClass("activeSpan");
		iframe_box.find(".show_iframe").hide().eq(bStopIndex).show();
		return false;
	}else if(config.showType=='URL' && config.url == ''){
		return false;
	}else if(config.showType=='NEW_WIN'){
   	 _OpenWindow({
			title : config.title,
			max : true,
			height : 500,
			width : 800,
			url : __rootPath + config.url
		});
    }else if(config.showType=='FUNS' || config.showType=='FUN'){
    	bStop=true;
    	url = __rootPath + '/ui/view/menuView/menuPanel.do?menuId='+config.menuId;
    	creatIframe( url , config.title , config.menuId)
    }else if(!bStop){
    	if(config.url  && config.url.indexOf('http')==-1){
    		config.url=__rootPath+config.url;
    	}
    	creatIframe(config.url,config.title,config.menuId);
		min_titleList();
	}
}

/*最新tab标题栏列表*/
function min_titleList(){
	var topWindow = $(window.parent.document),
		show_nav = topWindow.find("#min_title_list"),
		aLi = show_nav.find("li");
}
/*创建iframe*/
function creatIframe(href,titleName,menuId){
	var topWindow=$(window.parent.document),
		show_nav=topWindow.find('#min_title_list'),
		iframe_box=topWindow.find('#iframe_box'),
		iframeBox=iframe_box.find('.show_iframe'),
		$tabNav = topWindow.find(".acrossTab"),
		$tabNavWp = topWindow.find(".top_tab_box"),
		$tabNavmore =topWindow.find(".Hui-tabNav-more");
	
	//新增选项卡样式
	show_nav.find('li').removeClass("active");
	show_nav.find('span').removeClass("activeSpan");
	//oncontextmenu="rightClick.call(this);
	show_nav.append('<li class="active">\
			<span data-href="'+href+'" title="'+titleName+'" class="activeSpan" menuId="'+menuId+'">'+titleName+'</span>\
			<i><img src="'+__rootPath+'/styles/images/index/index_Close01.png""></i></li>');
	if('function'==typeof $('#min_title_list li').contextMenu){
		$("#min_title_list li").contextMenu('Huiadminmenu', {
			bindings: {
				'closethis': function(t) {
					var $t = $(t);				
					if($t.find("i")){
						$t.find("i").trigger("click");
					}
				},
				'closeall': function(t) {
					$("#min_title_list li i").trigger("click");
				},
			}
		});
	}	
	var taballwidth=tabUl.innerWidth();
	
	var w = $tabNavWp.width();
	if(taballwidth+25>w){
		$tabNavmore.show();}
	else{
		n=0;
		$tabNavmore.hide();
		$tabNav.css({left:0});
	}
	iframeBox.hide();
	
	
	//console.log(href);
	iframe_box.append('<div class="show_iframe" style="display:block;"><div class="loading"></div><iframe frameborder="0" src='+href+'></iframe></div>');
	var showBox=iframe_box.find('.show_iframe:visible');
	var taballwidth=tabUl.innerWidth();
	var top_tab_box_w=$(".top_tab_box").innerWidth();
	if(taballwidth>top_tab_box_w){
		fixedR();
	}
}

/*关闭iframe*/
function removeIframe(){
	var topWindow = $(window.parent.document),
		iframe = topWindow.find('#iframe_box .show_iframe'),
		tab = topWindow.find(".acrossTab li"),
		showTab = topWindow.find(".acrossTab li.active"),
		i = showTab.index();
	
	
	tab.eq(i-1).addClass("active");
	tab.eq(i).remove();
	iframe.eq(i-1).show();	
	iframe.eq(i).remove();
	tabUl.css('left','0');
	$("#Hui-tabNav").find(".Hui-tabNav-more").hide();
}

/*关闭所有iframe*/
function removeIframeAll(num){
	var topWindow = $(window.parent.document),
		iframe = topWindow.find('#iframe_box .show_iframe'),
		tab = topWindow.find(".acrossTab li");
	for(var i=0;i<tab.length;i++){
		if(tab.eq(i).find("i").length>0){
			tab.eq(i).remove();
			iframe.eq(i).remove();
		}
	}
	iframe.eq(num).show();
	tab.eq(num).addClass("active");
	$(".active span").eq(num).addClass("activeSpan");
}


function removeIframeOther(){
	var topWindow = $(window.parent.document),
		iframe = topWindow.find('#iframe_box .show_iframe'),
		tab = topWindow.find(".acrossTab li"),
		n=$(".active").index(),
		i=0;
	while(i<tab.length){
		i++;
		if(i===n){
			continue;
		}
		tab.eq(i).remove();
		iframe.eq(i).remove();
	}
	tabUl.css('left','0');
	$("#Hui-tabNav").find(".Hui-tabNav-more").hide();
}

function removeIframeCurren(){
	var topWindow = $(window.parent.document),
		iframe = topWindow.find('#iframe_box .show_iframe'),
		tab = topWindow.find(".acrossTab li"),
		i=$(".active").index();
	if(i===0){
		return;
	}
	tab.eq(i).remove();
	iframe.eq(i).remove();
	iframe.eq(i-1).show();
	tab.removeClass("active").eq(i-1).addClass("active");
	$(".active span").addClass("activeSpan");
}
//tab右键功能
	
var rightMenu = document.getElementById('rightMenu'),
	menuBox=document.querySelector('.top_tab_box'),
	tabOul = document.getElementById('min_title_list');
tabOul.oncontextmenu = function( ev ){
	var ev = ev || window.event,
		bStopIndex = null,
		iframe_box=$("#iframe_box"),
		target = ev.target || ev.srcElement;
	if(target.nodeName.toLowerCase() == 'span'){
		var This = $("#min_title_list li").filter(".active");
		$("#min_title_list li").removeClass("active");
		$("#min_title_list li span").removeClass('activeSpan');
		target.className = 'activeSpan';
		target.parentNode.className = 'active';
		var bStopIndex = $("#min_title_list li").filter('.active').index();
		iframe_box.find(".show_iframe").hide().eq(bStopIndex).show();	
		rightMenu.style.display = 'block';
		rightMenu.style.left = ev.clientX + 'px';
		rightMenu.style.top = ev.clientY + 'px';
	}
	    return false;
}

document.onclick = function() {
	rightMenu.style.display = 'none';
};

//关闭全部
$("#allMenu").on('click',function(){
	removeIframeAll(0);
	tabUl.css("right",'');
	tabUl.css("left",0);
	$(".Hui-tabNav-more").hide();
});

//关闭其它
$("#otherMenu").on('click',function(){
	removeIframeOther();
	tabUl.css("right",'');
	tabUl.css("left",0);
	$(".Hui-tabNav-more").hide();
});

//关闭当前
$("#currentMenu").on('click',function(){
	var taballwidth=tabUl.innerWidth(),
		top_tab_box_w=$(".top_tab_box").innerWidth();
	if(top_tab_box_w<taballwidth){
		fixedR();
	}else{
		tabUl.css("right",'');
		tabUl.css("left",0);
		$(".Hui-tabNav-more").hide();
	}
	removeIframeCurren();
});

//刷新当前页面
$("#refreshMenu").on('click',function(){
	var tabUl = document.getElementById('min_title_list'),
		tabLi = tabUl.children,
		iframe_box = document.getElementById('iframe_box'),
		showIframe = iframe_box.children,
		liDisplay = null,
		thisIframe = null,
	 	thisSrc = null;

	for(var i=0 ;i<tabLi.length; i++){
		liDisplay = showIframe[i].style.display;
		if( liDisplay === 'block' ){
			thisIframe = showIframe[i].children,
			thisIframe[1].src = thisIframe[1].src;
		}
	}

});

/*弹出层*/
/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
/*function layer_show(title,url,w,h){
	if (title == null || title == '') {
		title=false;
	};
	if (url == null || url == '') {
		url="404.html";
	};
	if (w == null || w == '') {
		w=800;
	};
	if (h == null || h == '') {
		h=($(window).height() - 50);
	};
	layer.open({
		type: 2,
		area: [w+'px', h +'px'],
		fix: false, //不固定
		maxmin: true,
		shade:0.4,
		title: title,
		content: url
	});
}*/
/*关闭弹出框口*/
/*function layer_close(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}*/
$(function(){
	$(".nav-toggle").click(function(){
		$(".Hui-aside").slideToggle();
	});
	$(".Hui-aside").on("click",".menu_dropdown dd li a",function(){
		if($(window).width()<768){
			$(".Hui-aside").slideToggle();
		}
	});
	/*左侧菜单*/
	$(".Hui-aside").Huifold({
		titCell:'.menu_dropdown dl dt',
		mainCell:'.menu_dropdown dl dd',
	});
	
	
	/*选项卡导航*/
	$(".Hui-aside").on("click",".menu_dropdown a",function(){
		Hui_admin_tab(this);
		tabNavallwidth();
	});
	
	$(document).on("click","#min_title_list li",function(){
		var bStopIndex=$(this).index(),
			iframe_box=$("#iframe_box");
		$("#min_title_list li").removeClass("active").eq(bStopIndex).addClass("active");
		iframe_box.find(".show_iframe").hide().eq(bStopIndex).show();
	});
	
	
	$(document).on("click","#min_title_list li i",function(ev){
		var ev = ev || event,
			topWindow = $(window.parent.document),
			ul_position_left=tabUl.position().left,
			taballwidth=tabUl.innerWidth(),
			top_tab_box_w=$(".top_tab_box").innerWidth(),
			iframe = topWindow.find('#iframe_box .show_iframe'),
			tab = topWindow.find(".acrossTab li"),
			i=$(this).parent().index();
		if(i===0){
			return;
		}
		ev.stopPropagation();//取消冒泡
		$(this).parent().prev('li').children('span').addClass("activeSpan");
		tab.removeClass("active").eq(i-1).addClass("active");
		tab.eq(i).remove();
		iframe.eq(i).remove();
		iframe.eq(i-1).show();
		if(top_tab_box_w<taballwidth)fixedR();
		if(ul_position_left>0){
			tabUl.css("right",'');
			tabUl.css("left",0);
		}
		tabNavallwidth();
	});
	tabNavallwidth();
//tab移动
	$('#js-tabNav-next').click(function(){
		var ul_position_left=tabUl.position().left,
			taballwidth=tabUl.innerWidth(),
			top_tab_box_w=$(".top_tab_box").innerWidth(),
			overflowAndBox=-ul_position_left+top_tab_box_w,
			disparity=taballwidth-overflowAndBox,
			fixedRight=top_tab_box_w-120,
			ul_position_left_distance=taballwidth-fixedRight;		 
		tabUl.css("right",'');
		tabUl.css("left",ul_position_left);
		if(disparity>100){
			tabUl.stop().animate({'left':ul_position_left-100},500);
		}else if(disparity>0){
			tabUl.stop().animate({'left':-ul_position_left_distance},500);
		}else{
			tabUl.stop().animate({'left':-ul_position_left_distance},500);
		}
	});
	$('#js-tabNav-prev').click(function(){
		var ul_position_left=tabUl.position().left;
		tabUl.css("right",'');
		tabUl.css("left",ul_position_left);
		if(ul_position_left<-100){
			tabUl.stop().animate({'left':ul_position_left+100},500);
		}else if(ul_position_left<0){
			tabUl.stop().animate({'left':0},500);
		}else{
			tabUl.stop().animate({'left':0},500);
		}
	});	
}); 	
function fixedR(){
		var taballwidth=tabUl.innerWidth(),
			top_tab_box_w=$(".top_tab_box").innerWidth(),
			fixedPosition=taballwidth-top_tab_box_w+120;
		tabUl.css("left",'');
		tabUl.css("right",fixedPosition);
	}