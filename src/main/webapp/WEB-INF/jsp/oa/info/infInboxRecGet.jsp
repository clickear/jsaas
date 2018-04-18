<%-- 
    Document   : 已收消息页面的Get页面
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<html>
<head>
<title>短消息内容</title>
<%@include file="/commons/list.jsp"%>
<style>
hr {
	border: 1px dashed #ccc;
	border-bottom: 0;
	border-right: 0;
	border-left: 0;
}

ul {
	maring: 0;
	padding: 0;
}

li {
	clear: both;
	list-style: none;
	display: block;
	width: 300px;
	height: 20px;
	line-height: 20px;
	text-indent: 20px;
	background: url(../../../styles/icons/detail.png) no-repeat center left;
}
</style>
</head>
<body>
	<input value="${infInnerMsg.msgId}" hidden=true></input>
	<div style="font-size: 14px; margin-left:10px;">
		来自：<span style="color: green;">${infInnerMsg.sender}</span>
		<span style="font-size: 12px; color: red;">${infInnerMsg.createTime}</span>
	</div>
	
	<div id="subjectPart">
		<div style="float: left; margin-top: 9px;">关联信息：</div>
		<div id="subject" style="float: left;">${infInnerMsg.linkMsg}</div>
	</div>
	<div style="color: #712704; height: 200px; clear: both; padding:10px;font-size:14px;">${infInnerMsg.content}</div>
	<div class="mini-toolbar dialog-footer"style="border: none;text-align:center">
<!-- 		<a class="mini-button" iconCls="icon-close" plain="true" onclick="closeMsg()">关闭</a> -->
		<a class="mini-button" iconCls="icon-prev" plain="true" onclick="preMsg()">上一条</a>
		<a class="mini-button" iconCls="icon-next" plain="true" onclick="nextMsg()">下一条</a>
		<a class="mini-button" iconCls="icon-remove" plain="true" onclick="delMsg()">删除</a>
	</div>

	<script type="text/javascript">
	//删除消息
    function delMsg(){
    	 if (confirm("确定删除当前记录？")) {
             var pkId='${infInnerMsg.msgId}';
             _SubmitJson({
					url : "${ctxPath}/oa/info/infInbox/delMsg.do",
					data:{
						pkId:pkId
					},
					method:"POST",
					success: function() {
                        top['com.redxun.oa.info.entity.InfInnerMsg'].grid.load();
                        CloseWindow(); 
                     },
				});
         }
    }
    //关闭页面
	function closeMsg(){
		CloseWindow();
	}
    //上一条
	function preMsg(){
		var pkId=top['com.redxun.oa.info.entity.InfInnerMsg'].preRecord();
        if(pkId==0) {
        	mini.showTips({
                content: "已经是第一条了。",
                state: "info",
                x: "right",
                y: "top",
                timeout: 1000
            });
        	return ;
        }
        window.location='${ctxPath}/oa/info/infInbox/recGet.do?pkId='+pkId;
	}
	//下一页
	function nextMsg(){
		var pkId=top['com.redxun.oa.info.entity.InfInnerMsg'].nextRecord();
        if(pkId==0){
        	mini.showTips({
                content: "已经是最后一条了。",
                state: "info",
                x: "right",
                y: "top",
                timeout: 1000
            });
        	return ;
        }
        window.location='${ctxPath}/oa/info/infInbox/recGet.do?pkId='+pkId;
	}
	
	$(function(){
		var linkMsg = '${infInnerMsg.linkMsg}';
		var isDoc = $('.subject').attr('isDoc');
		if(linkMsg==""){
			$("#subjectPart").hide();
		}else{
			if(isDoc=="yes"){
				$('.subject').on("click", function() {//绑定新的提交点击事件
					var url = $(this).attr('href1');
					/* _OpenWindow({
						title : '文档详细',
						height : 500,
						width : 780,
						max:true,
						url : url,
					}); */
					window.open(url);
				});
				$("#subjectPart").show();
			}else{
			$('.subject').on("click", function() {//绑定新的提交点击事件
				var url = $(this).attr('href1');
				_OpenWindow({
					title : '任务详细',
					height : 500,
					width : 780,
					//max:true,
					url : url,
				});
			});
			$("#subjectPart").show();
			}
		}
	});
</script>
	<rx:detailScript baseUrl="oa/info/infInnerMsg" formId="form1" entityName="com.redxun.oa.info.entity.InfInnerMsg" />
</body>
</html>