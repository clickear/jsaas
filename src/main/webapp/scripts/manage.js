//设置左分隔符为 <!
baidu.template.LEFT_DELIMITER='<#';
//设置右分隔符为 <!  
baidu.template.RIGHT_DELIMITER='#>';
var bt=baidu.template;

/**
 * Created by Devin on 2016/6/16.
 */

function setSize(){
	 var winWidth = $(window).width();
	 var winHeight= $(window).height();
	 if($('.side').is(':hidden')){
		 $('.content').width(winWidth-5);
	 }else{
		 $('.content').width(winWidth-241);
	 }
	var top=$('#top');
	if(top.css('display')!='none'){
		 $('.iframe').height(winHeight-185);
		 $('.content').height(winHeight-130);
		 $('.split').height(winHeight-130);
		 $('.side').height(winHeight-130);
	}else{
		 $('.iframe').height(winHeight-95);
		 $('.content').height(winHeight-40);
		 $('.split').height(winHeight-40);
		 $('.side').height(winHeight-40);
	}
	mini.layout();
	//mainTab.doLayout();
}

$(function(){
    var winWidth = $(window).width();
    var winHeight= $(window).height();
    $('.side').height(winHeight-130);

    $('.iframe').height(winHeight-185);
    $('.content').height(winHeight-130);
    $('.split').height(winHeight-130);
    $('.content').width(winWidth-241);
   
    mini.layout();
  
    $(window).resize(function(){
    	setTimeout('setSize()',500);
    });

    attachMenuEvents();
    $('.close').click(function(){
        $(this).parent('div').remove();
    });

    /*左右伸缩*/
   $('.side .shs').click(function(){
	   var winWidth = $(window).width();
       $('.side').hide();
       $('.content').width(winWidth-5);
       $('.split').show();
       mainTab.doLayout();
   });
    $('.split').click(function(){
    	var winWidth = $(window).width();
        $(this).hide();
        $('.content').width(winWidth-241);
        $('.side').show();
        mainTab.doLayout();
    });
});

function attachMenuEvents(){
	  $('.sidemenu li.firstmenu>p').click(function(){
	        $(this).parent('li').siblings('li').removeClass('active');
	        $(this).siblings('ul').children('li').removeClass('active');
	        $(this).parent('li').toggleClass('active');
	        $(this).parent('li').siblings('li').children('ul').slideUp(300);
	        $(this).siblings('ul').slideToggle(300);
	        $('.sidemenu .threemenu').slideUp(300);
	        showTab($(this));
	    });
	    $('.sidemenu .secondmenu>li>p').click(function(){
	        $(this).siblings('ul').slideToggle(300);
	        showTab($(this));
	    });
	    
	    $('.threemenu>li').click(function(){
	    	 showTab($(this));
	    });
	    
	    $(".sidemenu p").hover(
    		function(){
    			$(this).addClass('secondActive');
    		},
    		function(){
    			$(this).removeClass('secondActive')
    		}
		);
	    
	    
	    
	    
}

$(function(){
	initMenu();
});

var menuData;


function initMenu(){
	var url=__rootPath +"/getMenus.do";
	$.get(url,function(data){
		menuData=data;
		console.log(menuData);
		var curId= getCurrentSys();
		var curIdx=getActiveMenu(data,curId);
		//构建根导航
		buildRootMenu(data,curIdx);	
		//构建左侧导航
		buildLeftMemu(data,curIdx);
		
		//检测是否需要显示tab滑动按钮
		var tabsbox_W = $('.tabsbox').innerWidth(),
			tabwrap_W = $('.movewrap').innerWidth();
		if( tabsbox_W < tabwrap_W ){
			$('#tabsbox .fr').css('display','block');
		}else{
			$('#tabsbox .fr').css('display','none');
		}
		
	});
}

function buildLeftMemu(data,curIdx){
	var menuData=data[curIdx].children;
	var data={"list":menuData};
	var menuHtml=bt('leftMenuTemplate',data);
	$("#sidemenu").html(menuHtml).hide().slideToggle(300);
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

var mainTab=mini.get('mainTab');
function fullclick(){
	var winWidth = $(window).width();
    var winHeight= $(window).height();
    if($(".side").is(":hidden")){
    	$('.content').width(winWidth-5);
    }else{
    	$('.content').width(winWidth-241);
    }

    var mainLayout=mini.get('mainLayout');
	var top=$('#top');
	if(top.css('display')!='none'){
		 mainLayout.updateRegion("north", { height: 35 });
		top.css('display','none');
		
		$('.iframe').height(winHeight-95);
		 $('.content').height(winHeight-40);
		 $('.split').height(winHeight-40);
		 $('.side').height(winHeight-40);
		
	}else{
		 mainLayout.updateRegion("north", { height: 85 });
		 top.css('display','');
		
		 $('.iframe').height(winHeight-185);
		 $('.content').height(winHeight-130);
		 $('.split').height(winHeight-130);
		 $('.side').height(winHeight-130);
		
	}
	mainTab.doLayout();
	mini.layout();
}

 function closeTab() {
	 mainTab.removeTab(currentTab);
 }
 function closeAllBut() {
	 mainTab.removeAll(currentTab);
 }
 function closeAll() {
	 mainTab.removeAll();
 }
 function closeAllButFirst() {
//     var but = [currentTab];
//     but.push(mainTab.getTab("first"));
//     mainTab.removeAll(but);
	 mainTab.removeAll(mainTab.getTab(0));
 }

 var currentTab = null;

 function onBeforeOpen(e) {
     currentTab = mainTab.getTabByEvent(e.htmlEvent);
     if (!currentTab) {
         e.cancel = true;
     }
 }
 function onTabsActiveChanged(e){
     mini.layout();
 }

 function showTab(node) {
   	var url=node.attr('url');
    var menuId =  node.attr('menuId');
    var iconCls= node.attr('iconCls');
    
    var title=node.attr('name');
    var showType=node.attr("showType");
    showTabPage({menuId:menuId,showType:showType,title:title,iconCls:iconCls,url:url});
 }
 
 function showTabPage(config){
	 console.log(config);
	 var url=config.url;
     if(url && url.indexOf('http')==-1){
    	 url=__rootPath+config.url;
     }
	 var id = "tab_" + config.menuId;
     var tabs = mini.get("mainTab");
     var tab = tabs.getTab(id);
    
     if (!tab) {
      	tab = {};
         tab.name = id;
         tab.title = config.title;
         tab.iconCls=config.iconCls;
         tab.showCloseButton = true;
     	if(config.showType=='NEW_WIN'){
	        	 _OpenWindow({
					title : config.title,
					max : true,
					height : 500,
					width : 800,
					url : url
				});
	         }else if(config.showType=='FUNS' || config.showType=='FUN'){
	        	 tab.url = __rootPath + '/ui/view/menuView/menuPanel.do?menuId='+config.menuId;
	        	 tabs.addTab(tab);
	        	 tabs.activeTab(tab);
	         } else if(config.url!='' && config.url!=undefined && config.url!='null'){
	            tab.url =url;
	            tabs.addTab(tab);
	            tabs.activeTab(tab);
	         }
     }else{
    	 tab.url=url;
    	 tabs.activeTab(tab);
     } 
  }
 
function showTabFromPage(config){
	var tabs = mini.get("mainTab");
	var id = "tab_" + config.tabId;
    var tab = tabs.getTab(id);
    if (!tab) {
        tab = {};
        tab.name = id;
        tab.title = config.title;
        tab.showCloseButton = true;
        tab.url = config.url;
        tab.iconCls=config.iconCls;
        tabs.addTab(tab);
    }
    tabs.activeTab(tab);
}
 
//点击公司门户
	
function editInfo(){
	showTabFromPage({
		tabId:'editInfo',
		iconCls:'icon-mgr',
		title:'修改个人信息',
		url:__rootPath+'/sys/org/osUser/info.do'
	});
 	
 }
 function onEmail(){
 	showTabFromPage({
 		title:'内部邮件',
 		tabId:'onEmail',
 		iconCls:'icon-newMsg',
 		url:__rootPath+'/oa/mail/mailBox/list.do'
 	});
 }
 	
 function innerMsgInfo(){
 	showTabFromPage({
 		title:'内部消息',
 		tabId:'innerMsgInfo',
 		iconCls:'icon-newMsg',
 		url:__rootPath+'/oa/info/infInbox/receive.do'
 	});
 }

function showNewMsg() {
    _SubmitJson({
		url : __rootPath+"/oa/info/infInnerMsg/count.do",
		showMsg:false,
		method:"POST",
		showProcessTips:false,
		success:function(result){
			newMsg=result.data;
			if(newMsg==0) return;
   			var uid = 1;
   			_OpenWindow({
   					url : __rootPath +"/oa/info/infInbox/indexGet.do?&uId="+uid,
   					width : 500,
   					height : 308,
   					title : "消息内容",
   					ondestroy : function(action) {
   					}
   	        });
		}
	});
}

function newMsg(newMsgCount){
	if(newMsgCount!=0){
    	window.setInterval(function(){
    		$(".app").html('<span style="color:red">消息('+newMsgCount+')</span>');
        	window.setTimeout(function(){
        		$(".app").html('<span >消息('+newMsgCount+')</span>');
        		},1000);
    		}, 2000);

    }else{
    	$("#msg").find("span").append('<span class="mini-button-icon mini-iconfont icon-msg" style=""></span>消息');
    }
}