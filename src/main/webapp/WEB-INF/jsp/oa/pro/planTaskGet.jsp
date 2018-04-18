<%-- 
    Document   : [planTask]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>计划明细</title>
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
<div>
	<rx:toolbar toolbarId="toolbar1" hideRecordNav="true" excludeButtons="remove">
       <div class="self-toolbar">
       <a class="mini-button" iconCls="icon-reload" plain="true" onclick="location.reload();">刷新</a>
       <a class="mini-button" iconCls="icon-unlock" plain="true" onclick="payAttention()">关注</a>
       </div>
       </rx:toolbar>
		<div style="margin-top: 20px; margin-bottom: 20px;" align="center">
			<a style="font-size: x-large;font-family:'宋体' !important;color: #666;">标题
			<!-- ${planTask.subject} -->
			<!-- <a style="font-size: xx-small;color:#AA0000;">(计划)</a> --></a>
		</div>
		<div style="margin-top: -10px; margin-bottom: 20px;" align="center">
              <a id="project" style="font-size: xx-small;"> 所属项目
	              <a style="font-size: xx-small;color:#AA0000;">${projectName}</a>
	              <a id="version" style="font-size: xx-small;color:#AA0000;">&nbsp;版本:${planTask.version}&nbsp;</a>
              </a>
              <a id="req" style="font-size: xx-small;"> 所属需求<a style="font-size: xx-small;color:#AA0000;">${reqSubject}</a></a>
		
		</div>
		
		<div class="mar topmar" style="float: right; margin-top: -30px;padding-right: 3px;">
			<a style="color:#AA0000;">${planTask.status}</a><br/>
		</div>
	</div>



<div style="padding: 5px;font-size:x-small;margin-top: -30px;margin-bottom: -10px;">
	<div class="mar"><a style="color:#880000 ;"><rxc:userLabel userId="${planTask.createBy}" />
		</a> 创建于<a style="color:#880000 ;"><fmt:formatDate value="${planTask.createTime}" pattern="yyyy-MM-dd HH:mm" /></a>
	</div>
</div> 
<!-- 描述 -->
	<div id="form1" style="margin-top: 10px;">
			<table style="width: 100%;height: 220px;"  cellpadding="0">
				<tr  class="linear" >
					 <td style="min-height: 50px;border: solid 1px #909AA6;text-align: left;padding: 5px;"><div  align="center" style="font-size:18px; ">${planTask.content}</div></td>
				</tr>
			</table>
		</div>
	<div class="mar topmar" style="float: left; margin-top: 5px; margin-bottom: 0px;">
		<div style="float: left;font-size: x-small; margin: 0; padding-left: 5px;">
			<div class="mar" style="float: left;padding-left: 0px; margin-top: 0px;">
				<div id="pstarttime" style="float: left;">计划于<a style="color: #AA0000;"><fmt:formatDate value="${planTask.pstartTime}" pattern="yyyy-MM-dd HH:mm:ss" /></a>开始  &nbsp;</div>
				 <div id="pendtime" style="float: right;">至<a style="color: #AA0000;"><fmt:formatDate value="${planTask.pendTime}" pattern="yyyy-MM-dd HH:mm:ss" /></a>结束 &nbsp;&nbsp;</div>
				 </div>
			<div class="mar" style="float: right; padding-left: 0px; margin-top: 0px;">
				<div id="starttime" style="float: left;">实际于<a style="color: #AA0000;"><fmt:formatDate value="${planTask.startTime}" pattern="yyyy-MM-dd HH:mm:ss" /></a>开始&nbsp;</div> 
				<div id="endtime" style="float: right;"> 至<a style="color: #AA0000;"><fmt:formatDate value="${planTask.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></a>结束</div>
			</div>
		</div>
	</div>
<div style="float: left;padding: 5px;font-size:x-small;"> <div class="mar">分配人：<a id="assign" style="color:#880000 ;">${assign}</a> 所属人：<a id="owner" style="color:#880000 ;">${owner}</a>执行人：<a id="exe" style="color:#880000 ;">${exe}</a></div>
</div> 
<div style="float: right;padding: 5px;font-size:x-small;">工作日志数量<a href="javascript:listTask();" style="color:#9F79EE;font-size: large;">${tasknum}</a></div>
		
	
	<rx:detailScript baseUrl="oa/pro/planTask" formId="form1"  entityName="com.redxun.oa.pro.entity.planTask"/>
	<script type="text/javascript">
	$(function(){
		if(${empty planTask.pendTime}){//如果没有计划结束时间则隐藏
			$("#pendtime").hide();
		}
		if(${empty planTask.startTime}){//如果没有开始时间则隐藏
			$("#starttime").hide();
		}
		if(${empty planTask.endTime}){//如果没有结束时间则隐藏
			$("#endtime").hide();
		}
		if(${empty projectName}){//如果没有所属项目则隐藏
			$("#project").hide();
		}
		if(${empty reqSubject}){//如果没有所属需求则隐藏
			$("#req").hide();
		}
		if(${empty planTask.version}){//如果没有所属需求则隐藏
			$("#version").hide();
		}
		if(${empty assign}){
			$("#assign").after("<a style='color: #AA0000;'>无</a>");//如果没有则写入一个"无"
		}
		if(${empty owner}){
			$("#owner").after("<a style='color: #AA0000;'>无</a>");//如果没有则写入一个"无"
		}
		if(${empty exe}){
			$("#exe").after("<a style='color: #AA0000;'>无</a>");//如果没有则写入一个"无"
		}
		
	});
	//打开项目的日志列表
	function listTask(){
		_OpenWindow({
    		url: __rootPath+"/oa/pro/workLog/list.do?planId="+${planTask.planId}+"&planTask="+"YES",
            title: "日志列表", width: 800, height: 600,
            ondestroy: function(action) {
            }
    	});	
	}
	
	
	
	//关注任务
	function payAttention(){
		$.ajax({
            type: "Post",
            async: false,
            url : '${ctxPath}/oa/pro/proWorkAtt/checkAttention.do?typePk=${planTask.planId}',
            success: function (result) {
            	if(result.success==true){
            		 mini.confirm("确定要关注此计划 ？", "确定？",
            		            function (action) {
            		                if (action == "ok") {
            		                	$.ajax({
            		                        type: "Post",
            		                        url : '${ctxPath}/oa/pro/proWorkAtt/payAttention.do?typePk=${planTask.planId}&type=PLAN',
            		                        success: function (result) {
            		                        	if(result.success==true){
            		                        		mini.showTips({
            		                                    content: "<b>提示</b> <br/>关注成功",
            		                                    state: 'success',
            		                                    x: 'center',
            		                                    y: 'top',
            		                                    timeout: 3000});
            		                        		}
            		                        }
            		                            }); 
            	                                        }
            		                }
            		             );
            }else{
            	mini.showTips({
                    content: "<b>提示</b> <br/>您已经关注此计划",
                    state: 'warning',
                    x: 'center',
                    y: 'top',
                    timeout: 3000});
                  }
            
                                           }
	});
	}
	</script>
</body>
</html>