<%-- 
    Document   : Portal页面的我的消息的Get页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>短消息内容</title>
<%@include file="/commons/dynamic.jspf"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
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
	<div style="font-size: 15px;">
		来自:&nbsp;&nbsp;<span style="color: green;">${infInnerMsg.sender}</span> &nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 12px; color: red;">${infInnerMsg.createTime}</span>
	</div>
	<hr />
	<div id="subjectPart">
		<div style="color: #c75f3e; float: left; margin-top: 0px;">关联信息：</div>
		<div id="subject" style="float: left;">${infInnerMsg.linkMsg}</div>
	</div>
	<div style="color: #712704; height: 200px; background: #fefef2; clear: both;">${infInnerMsg.content}</div>
	<div style="text-align: center;">
		<a class="mini-button" iconCls="icon-close" plain="true" onclick="closeMsg()">关闭</a>  <a class="mini-button" iconCls="icon-remove" plain="true" onclick="delMsg()">删除</a>
	</div>

	<script type="text/javascript">
	//删除消息
    function delMsg(){
    	 if (confirm("确定当前记录？")) {
             var pkId="${infInnerMsg.msgId}";
             _SubmitJson({
					url : "${ctxPath}/oa/info/infInbox/delMsg.do",
					data:{
						pkId:pkId
					},
					method:"POST",
					success: function() {
                        //top['main'].grid.load();
                        CloseWindow(); 
                     },
				});
         }
    }
    //关闭页面
	function closeMsg(){
    	//将未读标记为已读
		var pkId='${infInnerMsg.msgId}';
		_SubmitJson({
			url : "${ctxPath}/oa/info/infInbox/updateStatus.do",
			showMsg : false,
			showProcessTips:false,
			data : {
				pkId : pkId
			},
			method : "POST",
			success : function() {
			}
		});
		CloseWindow();
	}
	
	//关联信息
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
</body>
</html>