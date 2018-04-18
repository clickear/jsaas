<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>红迅JSAAS敏捷开发平台</title>
	<%@include file="/commons/dynamic.jspf"%>
	<meta charset="UTF-8">
	<meta content="text/html; http-equiv="Content-Type" charset=utf-8"/>
	<script type="text/javascript" src="${ctxPath}/scripts/mini/boot.js"></script>
	<script type="text/javascript" src="${ctxPath}/scripts/jquery/jquery.cookie.js"></script> 
	<script type="text/javascript" src="${ctxPath}/scripts/mini/menu/src/sidemenu.js"></script>
	<script type="text/javascript" src="${ctxPath}/scripts/share.js"></script>
	<script type="text/javascript" src="${ctxPath}/scripts/indexPublic.js"></script>
	<link rel="stylesheet" href="${ctxPath}/scripts/mini/templates.css"/>
	<link rel="stylesheet" href="${ctxPath}/styles/icons.css"/>
	<link rel="stylesheet" href="${ctxPath}/scripts/mini/menu/src/sidemenu.css"/>
</head>
<body>
    <div class="topbar">
		<div class="title_box">
	    	<i class="icon-index_icon"></i>
	        <a class="logo" href="#">红迅JSAAS敏捷开发平台</a>
			<p>&nbsp;欢迎您，${curUser.depPathNames}&nbsp;${curUser.fullname}</p>
		</div>
        <ul class="top_icon">
			<li>
				<a  class="icon-Personal p_top" title="个人信息"></a>
				<dl style="display:none;">
					<dt></dt>
					<dd class="p_top">
						<p onclick="editInfo()">修改个人信息</p>
					</dd>
					<c:if test="${not empty cookie.switchUser }" >
					<dd class="p_top">
						<p onclick="exitSwitchUser()">退出切换</p>
					</dd>
					</c:if>
					
					<dd class="p_top theme" data-src="index">
						<p>炫黑高雅主题</p>
					</dd>
					<dd class="p_top theme" data-src="index1">
						<p>简约时尚主题</p>
					</dd>
					<dd class="p_top theme" data-src="index2">
						<p>浅蓝经典主题</p>
					</dd>
					<dd class="p_top theme" data-src="index3">
						<p>深蓝经典主题</p>
					</dd>
				</dl>
			</li>
			<li onclick="innerMsgInfo()">
				<a class="icon-News p_top" title="我的消息"></a>
			</li>
			<li onclick="onEmail()">
				<a class="icon-Mailbox p_top" title="内部邮件"></a>
			</li>
			<li onclick="fullCommScreen()" id="Screen">
				<a id="fullscreen" class="icon-Maximization" title="全屏显示"></a>
				<!--a id="commscreen" class="icon-Minimize" style="display:none;"  title="正常显示"></a-->
			</li>
			
			<li onclick="javascript:location.href='${ctxPath}/logout'">
				<a class="icon-SignOut p_top"  title="注销"></a>
			</li>
		</ul>
    </div>
    <div class="sidebar">
    	<div class="sidebar_btn">
    		<span class="sidebar_up iconfont sidebar_btn_up"></span>
    		<span class="sidebar_down iconfont sidebar_btn_down"></span>
    	</div>
    	
    	<div class="search">
			<div>
				<input type="text" placeholder="搜索菜单" class="searchVal" />
			</div>
<!-- 			<span>搜</span> -->
			<ul class="listBox"></ul>	
		</div>
    	
    	
    	<div class="sidebar_color"></div>
        <div id="menu1"></div>
    </div>
    <div class="main">
        <div id="mainTab" class="mini-tabs" activeIndex="0" style="height:100%;">
            <div title="首页" iconCls=" icon-home" url="${ctxPath}/scripts/layoutit/layoutitIndexShow.jsp"></div>
        </div>
    </div>
    
    <script type="text/javascript">
    	if(self!=top) top.location=self.location;
    	
    	top['index']=window;
    	function convertArray(data){
    		var aryJson=[];
    		for(var i=0;i<data.length;i++){
    			var obj=data[i];
    			var firstMenu={}
    			convertJson(firstMenu,obj);
    			aryJson.push(firstMenu);
    		}
    		return aryJson;
    	}
    	
    	function convertJson(parent,obj){
    		parent.id=obj.menuId;
    		parent.iconCls=obj.iconCls;
    		parent.url=obj.url;
    		parent.text=obj.name;
    		parent.key=obj.key;
    		parent.showType=obj.showType;
    		var child=obj.children;
    		if(child && child.length>0){
    			parent.children=parent.children ||[];
    			for(var i=0;i<child.length;i++){
    				var menu={};
    				convertJson(menu,child[i]);
    				parent.children.push(menu);	
    			}
    		}
    	}
    	
    	var menuData;
		$(function(){
			var url="${ctxPath}/getMenus.do";
			$.getJSON(url,function(data){
				menuData=convertArray(data);
				$("#menu1").sidemenu({data: menuData});	
			    var sidebarH = $(".sidebar").height();
			    var menuH = $("#menu1").innerHeight()-8;
			    $(".sidebar_btn").hide();
			    if( sidebarH < menuH ){
			    	$(".sidebar_btn").show();
			    }
			    var p = $('#menu1>ul').position().top;
			});
		})
	    
	    function showTab(node) {
	   
	      	var conf={
	      		menuId:node.id,
	      		url:node.url,
	      		showType:$(node).attr('showType'),
	      		iconCls:node.firstChild.className,
	      		title:node.innerText
	      	};
	      	showTabPage(conf)
	    }
	    
	    //var menuId,url,showType,iconCls,title;
	    function showTabPage(config){
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
		         } else if(config.url!='' && config.url!=undefined){
		            tab.url = url;
		            tabs.addTab(tab);
		            tabs.activeTab(tab);
		         }
	        }else{
	        	tab.url=url;
	        	tabs.activeTab(tab);
	        }
		}
		
	    function getTopMainTab(){
	    	return mini.get('mainTab');
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
	    
	    
	    var menu1 = $("#menu1").innerHeight()-8;
	    var sidebarH= '';
	    
	    $(window).resize(function () {
		    var sidebarH = $(".sidebar").height();
		    menu1 = $("#menu1").innerHeight()-8;
		    $(".sidebar_btn").hide();
		    if( sidebarH < menu1 ){
		    	$(".sidebar_btn").show();
		    }else{
		    	$('#menu1>ul').animate( {top:0} , 200 );
		    	x = 0;
		    }
		    
		    $("#Screen").children().eq(1).hide();
			$("#Screen h5:eq(0)").on('click',function(){
				$("#Screen").children().hide().eq(1).show();
				$(this).parent().attr('title','正常显示');
				requestFullScreen(document.documentElement);
			});
			
			$("#Screen h5:eq(1)").on('click',function(){
				$("#Screen").children().hide().eq(0).show();
				$(this).parent().attr('title','全屏显示');
				exitFull();
			});
	    });

	    var x = 0;
	    $('.sidebar_btn_up').click(function(){
	    	p = $('#menu1>ul').position().top;

	    	if( p < 55 ){
	    		x += 100;
	    		$('#menu1>ul').animate( {top:x} , 200 );
	    	}
	    });
	    
	    $('.sidebar_btn_down').click(function(){
 	    	p = $('#menu1>ul').position().top;
 	    	menu1 = $("#menu1").height() +8 +55;
 	    	sidebarH = $(".sidebar").height();

	    	if( p+menu1 > sidebarH ){
		    	x -= 100;
		    	$('#menu1>ul').animate( {top:x} , 200 );	
	    	}else{
	    		$('#menu1>ul').animate( {top:x} , 200 );
	    	}

	    });
	    
	   	var fstate=0;
	   	function fullCommScreen(){
	   		if(fstate==0){
	   			requestFullScreen(document.documentElement);
	   			$("#fullscreen").removeClass("icon-Maximization");
	   			$("#fullscreen").addClass("icon-Minimize");
	   			fstate=1;
	   		}else{
	   			exitFull();
	   			$("#fullscreen").addClass("icon-Maximization");
	   			$("#fullscreen").removeClass("icon-Minimize");
	   			fstate=0;
	   		}
	   	} 
	   	
	   	function exitSwitchUser(){
	   		location.href=__rootPath +"/j_spring_security_exit_user";
	   	}
	</script>
	<script>
	
	</script>
</body>
</html>


