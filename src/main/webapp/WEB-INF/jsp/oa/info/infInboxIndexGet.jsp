<%-- 
    Document   : index页面,新消息显示页面
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
		<div style="color:#c75f3e; float: left;  margin-top: 0px;">
			关联信息：
		</div>
		<div id="subject" style="float: left;">${infInnerMsg.linkMsg}</div>
	</div>
	<div style="color: #712704; height: 200px; background: #fefef2; clear: both;">${infInnerMsg.content}</div>
	<div style="text-align: center;">
		<a class="mini-button" iconCls="icon-close" plain="true" onclick="closeMsg()">关闭</a> 
		<a class="mini-button" iconCls="icon-next" plain="true" onclick="nextMsg()">下一条</a> 
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
                        top['main'].grid.load();
                        CloseWindow(); 
                     },
				});
         }
    }
    //关闭消息页面
	function closeMsg(){
    	//将未读改为已读
		 var pkId="${infInnerMsg.msgId}";
			_SubmitJson({
				url : "${ctxPath}/oa/info/infInbox/updateStatus.do",
				showMsg : false,
				data : {
					pkId : pkId
				},
				method : "POST",
				success : function() {
				}
			});
		CloseWindow();
	}
	//下一条新消息
	function nextMsg(){
		var uid="${uId}";//行数一直为1,因为每次读取新消息的第一页的数据,每页只有一个,这里行数不增加是因为每次点击下一条,都重新读取一次未读消息,所以新的消息一直是第一个
		var length = "${length}";//总的新消息长度
        if(uid==length){//判断是否是最后一条
        	mini.showTips({
                content: "已经是最后一条了。",
                state: "info",
                x: "right",
                y: "top",
                timeout: 1000
            });
        	return ;
        }
      //将未读改为已读
        var pkId="${infInnerMsg.msgId}";
		_SubmitJson({
			url : "${ctxPath}/oa/info/infInbox/updateStatus.do",
			showMsg : false,
			data : {
				pkId : pkId
			},
			method : "POST",
			success : function() {
			}
		});
		//打开销一条新消息
        window.location="${ctxPath}/oa/info/infInbox/indexGet.do?&uId="+uid;
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
</body>
</html>