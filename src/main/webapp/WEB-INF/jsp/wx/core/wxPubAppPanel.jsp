<%-- 
    Document   : [公众号管理]列表页
    Created on : 2017-06-29 16:57:29
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[公众号管理]列表管理</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
html,body {
	-moz-user-select: none;
	-khtml-user-select: none;
	user-select: none;
}

body{
	box-sizing:border-box;
	padding: 0 10px 0 0;
}

.container{
	padding: 10px 0 0 10px;
}

.span-icon {
	display: block;
	padding: 5px;
	clear: both;
	margin: 5px;
}

.menuItem{
	float: left;
	display: block;
	width: 120px;
	margin: 5px;
	text-align: center;
	font-size: large;
	padding: 10px;
	cursor: pointer;
 	border: solid 1px #eee; 
 	border-radius: 10px;
}

.mini-panel-title{
	width: 100%;
	text-align: center;
	font-size: 22px !important;
	line-height:34px !important;
	font-family: '宋体' !important;
	font-weight: bold !important;
	color: #fff !important;
}

.mini-panel-body{
	padding: 10px;
}

.mini-panel-viewport .span-icon:before{
	float: none;
	font-size: 48px;
}

.mini-panel-viewport .span-icon{
	margin: 30px 0;
}

.icon-wxClose{
	position: absolute;
	top:8px;
	right: 8px;
}

.menuActive span{
	color: #666;
}

.menuActive{
	background: #4ec0f8 !important;
	
}

.menuActive .span-icon:before,.menuActive span{
	color: #fff;
}

.icon-wxClose{
	transition:.3s;
}

.icon-wxClose:before{
	font-weight: bold;
	font-size: 20px;
	transition:color .3s;
}

.icon-wxClose:hover.icon-wxClose:before{
	color: red;
}

.icon-wxClose:hover{
	transform: rotate(90deg);
}

.mini-panel-border{
	border: none;
}

</style>

</head>
<body>
	<div class="container">
		<div class="mini-clearfix ">

			<div class="mini-panel " title="公众号" width="auto" height="auto" style="margin-bottom: 16px;">
				<c:forEach items="${jsonArray}" var="pubObj">
				<div class="menuItem pubapp p_top" title="双击编辑" id="${pubObj.id}">
					<span 
						title="删除公众号" 
						onclick="toRemoveDiv('${pubObj.id}');"  
						class=" iconfont icon-wxClose"	
					></span>
					<span class="iconfont icon-publicEdit  span-icon"></span> <span>${pubObj.name}</span>
				</div>
				</c:forEach>
				<div class="menuItem pubadd p_top" id="addNewPubApp" >
					<span class="iconfont icon-More  span-icon"></span> <span>[添加]</span>
				</div>
			</div>

			<div class="mini-panel " title="相关功能" id="functionPanel" width="auto" height="auto" style="display: none;">
				<div class="menuItem func p_top"  onclick="appFans()">
					<span class="iconfont icon-fansAD span-icon"></span> <span><a>粉丝</a></span>
				</div>
				<div class="menuItem func p_top"  onclick="appMeterial()">
					<span class="iconfont icon-materialAD span-icon"></span> <span><a>素材</a></span>
				</div>
				<div class="menuItem func p_top"  onclick="msgSend()">
					<span class="iconfont icon-articleAD span-icon"></span> <span><a>消息</a></span>
				</div>
				<div class="menuItem func p_top"  onclick="webGrant()">
					<span class="iconfont icon-link span-icon"></span> <span><a>网页授权</a></span>
				</div>
				<div class="menuItem func p_top"  onclick="ticket()">
					<span class="iconfont icon-card span-icon"></span> <span><a>卡券</a></span>
				</div>
				<!-- <div class="menuItem func p_top"  onclick="memberCard()">
					<span class="iconfont icon-VIP span-icon"></span> <span><a>会员卡</a></span>
				</div> -->
			</div>

		</div>
	</div>


	<script type="text/javascript">
		mini.parse();
		var pkId="";
		
		
		$(function(){
			$("div.pubapp").on('click',function(){
					pkId=$(this).attr('id');
					$("div.pubapp").removeClass('menuActive');
					$(this).addClass('menuActive');
					$("#functionPanel").fadeOut('fast');
					$("#functionPanel").fadeIn();
				
			});
			
			$("div.pubapp").on('dblclick',function(){
				pkId=$(this).attr('id');
				 _OpenWindow({
		    		 url: "${ctxPath}/wx/core/wxPubApp/edit.do?pkId="+pkId,
		            title: "编辑公众号",
		            width: 800, height:500,
		            ondestroy: function(action) {
		                if (action == 'ok') {
		                    location.reload();
		                }
		            }
		    	});
			});
			
			$("div.pubadd").on('click',function(){
				_OpenWindow({
		    		url: "${ctxPath}/wx/core/wxPubApp/edit.do",
		            title: "新增公众号", width: 800, height:500,
		            ondestroy: function(action) {
		            	 if (action == 'ok') {
		                    location.reload();
		                }
		            }
		    	});
			});
		});
		
		
		function toRemoveDiv(id,e){
			e = e || window.event;
			if(e.stopPropagation){
				e.stopPropagation();
			}else{
				e.cancelBubble = true;
			}
			 mini.confirm("确定删除？", "确定？",
			            function (action) {
			                if (action == "ok") {
			                	_SubmitJson({
			        	        	url:"${ctxPath}/wx/core/wxPubApp/del.do",
			        	        	method:'POST',
			        	        	data:{ids: id},
			        	        	 success: function(text) {
			        	        		location.reload();
			        	            }
			        	         });
			                }
			            }
			        );
		}

		
		function webGrant(){
			top.showTabFromPage({
				title : '网页授权',
				tabId : "webGrant"+pkId,
				url : __rootPath + '/wx/core/wxWebGrant/list.do?pubId=' +pkId
			});
		}
		
		
		function appFans(){
			top.showTabFromPage({
				title : '粉丝管理',
				tabId : "fans"+pkId,
				url : __rootPath + '/wx/core/wxSubscribe/list.do?pubId=' + pkId
			});
			
		}
		
		function appMeterial(){
			top.showTabFromPage({
				title : '素材管理',
				tabId : "meterial"+pkId,
				url : __rootPath + '/wx/core/wxMeterial/list.do?pubId=' + pkId
			});
		}
		
		function msgSend(){
			top.showTabFromPage({
				title : '消息管理',
				tabId : "msg"+pkId,
				url : __rootPath + '/wx/core/wxMessageSend/list.do?pkId=' + pkId
			});
		}
		
		function ticket(){
			top.showTabFromPage({
				title : '卡券管理',
				tabId : "ticket"+pkId,
				url : __rootPath + '/wx/core/wxTicket/list.do?pubId=' + pkId
			});
		}
		
		function memberCard(){
			top.showTabFromPage({
				title : '会员卡管理',
				tabId : "menberCard"+pkId,
				url : __rootPath + '/wx/core/wxTicket/memberCardList.do?pubId=' + pkId
			});
		}
	</script>
</body>
</html>