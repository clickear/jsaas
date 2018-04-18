<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>红迅JSAAS敏捷开发平台</title>
<%@include file="/commons/dynamic.jspf"%>
<link rel="shortcut icon" href="${ctxPath}/styles/images/index/icon.ico">
<link rel="stylesheet" href="${ctxPath}/styles/index.css" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/icons.css">
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<%-- <link type="text/css" rel="stylesheet" href="${ctxPath}/styles/css/manage.css" /> --%>
<script type="text/javascript" src="${ctxPath}/scripts/common/baiduTemplate.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/index/scroll.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/index/index.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/manage.js"></script>
<style type="text/css">
	body{
		font-size:16px;
}
	.h5_icon::before{
	color:#4ec0f8;
}
</style>
</head>
<body class="indexBody">
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="message.i18n"/>
<fmt:setLocale value="zh_CN" />

<!--top-->
<div class="top_bg">
	<div class="top_l">
			<div id="top_l_top">
				<img src="${ctxPath}/styles/images/index/index_tap_06.png" alt="" title="编辑个人信息">
				<span>
					<h1>欢迎你</h1>
					<h2>${curUser.fullname}&nbsp;<c:if test="${not empty curDep }">[${curDep.name}]</c:if></h2>
				</span>
				<div class="clearfix"></div>
			</div>
			<div id="top_l_bottom">
				<p>菜单导航</p>
				<img src="${ctxPath}/styles/images/index/index_indent.png" alt="" id="top_l_bottom01">
				<img src="${ctxPath}/styles/images/index/index_indent02.png" alt="" id="top_l_bottom02">
				<div class="clearfix"></div>
			</div>
		<div class="clearfix"></div>
	</div>
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs top_r">
		<div>
			<img src="${ctxPath}/styles/images/index/index_logo.png" alt="">
			<p class="measure"></p><!-- 用于获取宽度 -->
			<div class="top_icon">
				<a onclick="editInfo()" data-title="修改个人信息">
					<h5 class="iconfont icon-Personal"></h5>
				</a>
				<a onclick="innerMsgInfo()" data-title="我的消息">
					<h5 class="iconfont icon-News"></h5>
				</a>
				<a onclick="onEmail()" data-title="内部邮件">
					<h5 class="iconfont icon-Mailbox"></h5>
				</a>
				<a title="全屏显示" id="Screen">
					<h5 class="iconfont icon-Maximization"></h5>
					<h5 class="iconfont icon-Minimize" style="display:none;"></h5>
				</a>
				<a onclick="javascript:location.href='${ctxPath}/logout'" data-title="注销">
					<h5 class="iconfont icon-SignOut"></h5>
				</a>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="top_tab_box">
			<div class="Hui-tabNav-wp">
				<ul id="min_title_list" class="acrossTab cl">
					<li class="active">
					<!-- oncontextmenu="rightClick.call(this); -->
						<span title="首页" class="activespan" data-href="${ctxPath}/scripts/layoutit/layoutitIndex.jsp" >首页</span>
					</li>
				</ul>
				<div class="clearfix"></div>
			</div>
		</div>	
		<div class="Hui-tabNav-more btn-group">
			<a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;">
				<i class="Hui-iconfont"></i>
			</a>
			<a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;">
				<i class="Hui-iconfont"></i>
			</a>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<!-- tab右键 -->
<dl id="rightMenu">
	<dd id="refreshMenu">
		刷新当前
	</dd>
	<dd id="currentMenu">
		关闭当前
	</dd>
	<dd id="allMenu">
		关闭全部
	</dd>
	<dd id="otherMenu">
		关闭其它
	</dd>
</dl>
<!--nav_left-->
<div class="nav_l_bg Hui-aside" id="out"> 
</div>
<!--content-->
<div class="content_bg" id="index_content">
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe"  style="display:block">
			<div style="display:none" class="loading" ></div>
			<iframe id="welcome" scrolling="auto" frameborder="0" src="${ctxPath}/home/welcome.do"></iframe>
		</div>
	</div>
</div>
<!-- loading -->
<div id="loading">
  <div id="loading-center">
    <div id="loading-center-absolute">
      <div class="object" id="object_one"></div>
      <div class="object" id="object_two"></div>
      <div class="object" id="object_three"></div>
      <div class="object" id="object_four"></div>
    </div>
  </div>
</div>
<script id="leftMenuTemplate"  type="text/html">
<div id="inner">
  <#for(var i=0;i<list.length;i++){
    var menu=list[i];
  #>
		<div class="nav_l_box menu_dropdown bk_2">
			<span>
				<h5 class="<#=menu.iconCls#> iconfont nav_l_icon_l" title="<#=menu.name#>"></h5>
				<h1><#=menu.name#></h1>
				<img src="${ctxPath}/styles/images/index/index_nav_l_icon_01.png" alt="" class="nav_l_icon_r">
				<img src="${ctxPath}/styles/images/index/index_nav_l_icon_02.png" alt="" class="nav_l_icon_r">
				<div class="clearfix"></div>
			</span>

			<#
			if(!menu.children) continue;#>
			<dl>
			<#for(var k=0;k<menu.children.length;k++){      
			var menu2=menu.children[k]; 
			#>
			<dd class="nav_tab">
				<a  data-href="<#if(menu2.url==''||!menu2){#>
				<#}else{#>
					${ctxPath}<#=menu2.url#>
				<#}#>" data-title="<#=menu2.name#>">
					<h5 class="<#=menu2.iconCls#> iconfont" title="<#=menu2.name#>"></h5>
					<p><#=menu2.name#></p>
					
				</a>
				 <#if(menu2.children){#>
                      <ul>
              <#for(var m=0;m<menu2.children.length;m++){     
              var menu3=menu2.children[m]; 
              #>
					<li>
						<a data-href="<#if(menu3.url==''||!menu3){#>
						<#}else{#>
						${ctxPath}<#=menu3.url#><#}#>"
						menuId="<#=menu3.menuId#>" showType="<#=menu.showType#>" data-title="<#=menu3.name#>">
							<p><#=menu3.name#></p>
						</a>

						<#if(menu3.children){#>
							<ul>
							<#for(var g=0;g<menu3.children.length;g++){
							var menu4=menu3.children[g]; 
              				#>
    
								<li>
									<a data-href="${ctxPath}<#=menu4.url#>" menuId="<#=menu4.menuId#>" showType="<#=menu4.showType#>" data-title="<#=menu4.name#>">
										<p><#=menu4.name#></p>
									</a>	
								</li>
							<#}#>
							</ul>
						<#}#>
					</li>
                          <#}#>
                      </ul>
                      <#}#>
			</dd>
			<#}#>
			</dl>
			</div>
		<#}#>
</div>
<div id="scrollbar">
	<div id="scrollbtn"></div>
</div>
</script>
<script type="text/javascript" src="${ctxPath}/scripts/index/H-ui.min.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/index/H-ui.admin.js"></script>
<script type="text/javascript">
    top['index']=window;
    $(function(){
    	$.ajax({
    		url:"${ctxPath}/sys/core/sysInst/isAdmin.do",
    		type:"POST",
    		success:function(result){
    			if(result){
    				$("#welcome").attr("src","${ctxPath}/scripts/layoutit/layoutitIndexShow.jsp");
    			}else{
    				$("#welcome").attr("src","${ctxPath}/home/welcomePartner.do");
    			}
    		}
    	});
    });
    function editInfo(){
    	showTabFromPage({
			tabId:'editInfo',
			iconCls:'icon-mgr',
			title:'修改个人信息',
			url:__rootPath+'/sys/org/osUser/infoEdit.do'
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
</script>


<!-- 
<script type="text/javascript">
	window.onresize = function(){
		var winWidth = document.documentElement.clientWidth;
		document.title = winWidth;
		
	}
</script>
-->






</body>
<!-- 加载页面移除 -->
<script type="text/javascript">
	window.onload = function(){
		setTimeout(function(){
			$("#loading").slideUp(600);	
		},1000);	
		
		var url="${ctxPath}/heartBeat.do"
		window.setInterval(function(){
			$.get(url);
		},20000);
	};
</script>
</html>
