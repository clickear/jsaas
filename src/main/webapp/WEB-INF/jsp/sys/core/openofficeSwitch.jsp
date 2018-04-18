<%-- 
    Document   : [BpmSolUsergroup]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>soffice开关</title>
<%@include file="/commons/edit.jsp"%>
<script src="${ctxPath}/scripts/honeySwitch/honeySwitch.js" type="text/javascript"></script>
<link href="${ctxPath}/scripts/honeySwitch/honeySwitch.css" rel="stylesheet" type="text/css" />

</head>
<body >
<div style="width: 100%;height: 100%;background-image: url('${ctxPath}/styles/images/butonBackguound.jpg');">
<div style="position: absolute; top: 50%; left: 50%; font: menu; font-size: x-large;">
		soffice服务开关<span id="serviceSwitch" class="switch-off" themeColor="#ADD8E6"></span>
	</div></div>
	
	<script type="text/javascript">
		$(function() {
			$.ajax({
				type:"post",
				url:"${ctxPath}/sys/core/openoffice/getConnectStatus.do",
				data:{},
				success:function(result){
					if(result){
						honeySwitch.showOn("#serviceSwitch");
					}else{
						honeySwitch.showOff("#serviceSwitch");
					}
				}
			});
			
			honeySwitch.switchEvent("#serviceSwitch", function() {
				switchSoffice(true);
			}, function() {
				switchSoffice(false);
			});
		});
		
		function switchSoffice(switchParam){
			$.ajax({
				url:__rootPath+"/sys/core/openoffice/switchSoffice.do",
				type:"post",
				async:false,
				data:{switchParam:switchParam},
				success:function(result){
					if(result.success){
						mini.showTips({
				            content: "<b>成功</b> <br/>操作成功",
				            state: 'success',
				            x: 'center',
				            y: 'center',
				            timeout: 3000
				        });
					}else{
						mini.showTips({
				            content: "<b>成功</b> <br/>操作失败",
				            state: 'danger',
				            x: 'center',
				            y: 'center',
				            timeout: 3000
				        });
					}
				}
			})
		}
	</script>
</body>
</html>