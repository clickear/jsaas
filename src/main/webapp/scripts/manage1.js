//设置左分隔符为 <!
baidu.template.LEFT_DELIMITER='<#';
//设置右分隔符为 <!  
baidu.template.RIGHT_DELIMITER='#>';
var bt=baidu.template;


function attachMenuEvents(){
	  $('.sidemenu li.firstmenu>p').click(function(){
	        /*$('.sidemenu .secondmenu').hide();*/
	        $(this).parent('li').siblings('li').removeClass('active');
	        $(this).parent('li').toggleClass('active');
	        $(this).parent('li').siblings('li').children('ul').hide();
	        $(this).siblings('ul').toggle();
	        $('.sidemenu .threemenu').hide();
	        Hui_admin_tab($(this));
	    });
	    $('.sidemenu .secondmenu>li>p.hasnext').click(function(){
	        $(this).toggleClass('active');
	        $(this).parent('li').toggleClass('active');
	        $(this).siblings('ul').toggle();
	        Hui_admin_tab($(this));
	    });
	    
	    $('.threemenu>li').click(function(){
	    	Hui_admin_tab($(this));
	    });
}

$(function(){
	initMenu();
});

var menuData;
function initMenu(){
	var url=__rootPath +"/getMenus.do";
	$.get(url,function(data){
		menuData=data;
		var curId= getCurrentSys();
		var curIdx=getActiveMenu(data,curId);
		//构建左侧导航
		buildLeftMemu(data,curIdx);
	});
}

function buildLeftMemu(data,curIdx){
	var menuData=data;
	var data={"list":menuData};
	var menuHtml=bt('leftMenuTemplate',data);
	$(".Hui-aside").html(menuHtml);
	initCols();
	attachMenuEvents();
}

//获取激活的导航。
function getActiveMenu(data,curId){
	if(!curId) return 0;
	for(var i=0;i<data.length;i++){
		var o=data[i];
		if(o.menuId==curId) return i;
	}
	return 0;
}

//构建导航条。
function buildRootMenu(data,curIdx){
	var menuData={"list":data,idx:curIdx};
	var rootHtml=bt('rootTemplate',menuData);
	$("#divRootMenu").html(rootHtml);
}



function getCurrentSys(){
	var id= mini.Cookie.get('SYS_ID_');
	return id;
}

function changeSystem(e){
	var curId= getCurrentSys();
 	var sysId = $(e).attr('id');
 	var name=$(e).attr('name');
 	mini.Cookie.set('SYS_ID_',sysId);
 	
 	if(curId==sysId) return;

 	$(e).siblings().removeClass('active');
 	$(e).addClass('active');
 	
 	var curIdx= getActiveMenu(menuData,sysId);
 	buildLeftMemu(menuData,curIdx);

 }


mini.parse();

var mainTab=mini.get('mainTabs');

function fullclick(){
	var winWidth = $(window).width();
    var winHeight= $(window).height();
    if($(".side").is(":hidden")){
    	$('.content').width(winWidth-5);
    }else{
    	$('.content').width(winWidth-241);
    }
}




function showTabFromPage(config){
	 var bStop = false,
		bStopIndex = 0,
		href =config.url;
		title =config.title;
		topWindow = $(window.parent.document),
		show_navLi = topWindow.find("#min_title_list li"),
		iframe_box = topWindow.find("#iframe_box");
	if(!href||href==""){
		return;
	}if(!title){
		return false;
	}
	if(title==""){
		return false;
	}
	show_navLi.each(function() {
		if($(this).find('span').attr("data-href")==href){
			bStop=true;
			bStopIndex=show_navLi.index($(this));
			return false;
		}
	});
	if(!bStop){
		creatIframe(href,title);
	}
	else{
		show_navLi.removeClass("active").eq(bStopIndex).addClass("active");			
		iframe_box.find(".show_iframe").hide().eq(bStopIndex).show().find("iframe").attr("src",href);
	}	
}
