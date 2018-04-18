<%-- 
    Document   : 日志明细页
    Created on : 2015-3-28, 17:42:57
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>日志明细</title>
<%@include file="/commons/get.jsp"%>
<style type="text/css">
div.mar{
margin-bottom: -5px;
}
div.topmar{
margin-top: -5px;
}

.linear{ 
width:100%; 
height:100%; 
FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#CCEEFF,endColorStr=#fafafa); /*IE*/ 
background:-moz-linear-gradient(top,#CCEEFF,#fafafa);/*火狐*/ 
background:-webkit-gradient(linear, 0% 0%, 0% 100%,from(#CCEEFF), to(#fafafa));/*谷歌*/ 
background-image: -webkit-gradient(linear,left bottom,left top,color-start(0, #CCEEFF),color-stop(1, #fafafa));/* Safari & Chrome*/ 
filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#CCEEFF', endColorstr='#fafafa'); /*IE6 & IE7*/ 
-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#CCEEFF', endColorstr='#fafafa')"; /* IE8 */ 
background: -ms-linear-gradient(top, #CCEEFF,  #fafafa);/* IE 10 */
} 
</style>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" hideRecordNav="true" excludeButtons="remove">
		<div class="self-toolbar">
			<a class="mini-button" iconCls="icon-reload" plain="true" onclick="location.reload();">刷新</a>
		</div>
	</rx:toolbar>
	<div style="margin-top: 20px; margin-bottom: 20px;" align="center">
		<a style="font-size: x-large; font-weight: bold;">日志<a style="font-size: xx-small; color: #AA0000;">来自计划:${plan}</a></a>
	</div>
	<div class="mar topmar" style="float: right; margin-top: -30px; padding-right: 3px;">
		<a style="color: #AA0000;">${workLog.status}</a><br />
	</div>
	

<div style="padding: 5px;font-size:x-small;margin-top: -30px;margin-bottom: -10px;"> <div class="mar"><a style="color:#880000 ;"><rxc:userLabel userId="${workLog.createBy}" /></a> 创建于<a style="color:#880000 ;"><fmt:formatDate value="${workLog.createTime}" pattern="yyyy-MM-dd HH:mm" /></a></div>
</div> 
<!-- 描述 -->
	<div id="form1" style="margin-top: 10px;">
			<table style="width: 100%;height: 220px;"  cellpadding="0">
				<tr  class="linear" >
					 <td style="min-height: 50px;border: solid 1px #909AA6;text-align: left;padding: 5px;"><div  align="center" style="font-size:18px; ">${workLog.content}</div></td>
				</tr>
			</table>
		</div>
	<div class="mar topmar" style="float: left; margin-top: 5px; margin-bottom: 0px;">
		<div style="float: left;font-size: x-small; margin: 0; padding-left: 5px;">
			<div class="mar" style="float: left;padding-left: 0px; margin-top: 0px;">
				<div id="pstarttime" style="float: left;">于<a style="color: #AA0000;"><fmt:formatDate value="${workLog.startTime}" pattern="yyyy-MM-dd HH:mm:ss" /></a>开始  &nbsp;</div>
				 <div id="pendtime" style="float: right;">至<a style="color: #AA0000;"><fmt:formatDate value="${workLog.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></a>结束 &nbsp;&nbsp;</div>
				 </div>
		</div>
		<div id="checker" style="padding: 5px;font-size:x-small;"> 审核人:<div class="mar"><a style="color:#880000 ;"><rxc:userLabel userId="${workLog.checker}" /></a></div>
</div> 
	</div>
	<rx:detailScript baseUrl="oa/pro/workLog" formId="form1"  entityName="com.redxun.oa.pro.entity.WorkLog"/>
	<script type="text/javascript">
	$(function(){
		if(${empty workLog.checker})//如果没有审核人就隐藏
		$("#checker").hide();
	});
	</script>
</body>
</html>