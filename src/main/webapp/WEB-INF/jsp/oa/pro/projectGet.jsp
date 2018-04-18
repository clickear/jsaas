<%-- 
    Document   : 项目明细页
    Created on : 2016-1-18, 17:42:57
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>项目明细</title>
<%@include file="/commons/get.jsp"%>
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<style type="text/css">
*{
	font-family: '微软雅黑';
	color: #666;
}
h1{
	font-size: 18px;
	color: #666;
	text-align: center;
}
.title h2{
	font-size: 12px;
	color: #666;
}

.title h2::after{
	content:'';
	display: block;
	clear: both;
}

h2>span{
	float:left;
	width: 50%;
	text-align: center;
}

.column_2_m{
	width: 100%;
}

.column_2_m th,
.column_2_m td{
	border: 1px solid #ececec;
	font-size: 14px;
	padding: 8px;
}

.content p{
	margin: 0;
}

</style>
</head>
<body>
		<div class="self-toolbar topBtn" style="padding-top: 10px;">
			<a class="mini-button" iconCls="icon-reload" plain="true" onclick="location.reload();">刷新</a>
			<a class="mini-button" iconCls="icon-unlock" plain="true" onclick="payAttention()">关注</a>
		</div>

<%-- 	<rx:toolbar toolbarId="toolbar1" hideRecordNav="true" excludeButtons="remove">

    </rx:toolbar> --%>
	
	<div class="shadowBox90" style="padding-top: 8px; margin-top: 40px;">
		<div class="title">
			<h1>
				<a>
					${project.name}
					<a>${project.type}</a>
				</a>
			</h1>
			<h2>
				 <span>
				 	评论数量：
				 	<a href="javascript:listMat();">${MatNum}</a>
			 	</span>
			 	<span>当前版本：${project.version}</span>
			</h2>
		</div>
		<table class="column_2_m" style="margin-bottom: 40px; border-collapse: collapse;" cellspacing="0" cellpadding="0">
			<tr>
				<th>创&nbsp;&nbsp;建&nbsp;&nbsp;人</th>
				<td><rxc:userLabel userId="${project.createBy}" /></td>
				<th>创&nbsp;&nbsp;时&nbsp;&nbsp;间</th>
				<td><fmt:formatDate value="${project.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>		
			<tr>
				<th>标　　签</th>
				<td>${project.tag}</td>
				<th>编　　号</th>
				<td>${project.proNo}</td>
			</tr>		
			<tr>
				<th>启动时间</th>
				<td><fmt:formatDate value="${project.startTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<th>结束时间</th>
				<td><fmt:formatDate  value="${project.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<th>负&nbsp;&nbsp;责&nbsp;&nbsp;人</th>
				<td>${repor}</td>
				<th>参&nbsp;&nbsp;与&nbsp;&nbsp;人</th>
				<td>${AttPeople}</td>
			</tr>
			<tr>
				<th>参&nbsp;&nbsp;与&nbsp;&nbsp;组</th>
				<td>${AttGroup}</td>
				<th>预计费用</th>
				<td>${project.costs}</td>
			</tr>
			<tr>
				<th>状　　态</th>
				<td>${project.status}</td>
				<th></th>
				<td></td>
			</tr>
			<tr>
				<th>项目内容</th>
				<td colspan="3" class="content">${project.descp}</td>
			</tr>
		</table>
	<!-- tab -->
	<div style="height: 500px;width:100%; ">
		<div id="tabs1" class="mini-tabs" style="width: 100%;height: 100%;" >
		    <div title="评论内容" url="${ctxPath}/oa/pro/proWorkMat/list.do?projectId=${project.projectId}"></div>
			<div title="参与人员" id="tabAtt" url="${ctxPath}/oa/pro/proAttend/list.do?projectId=${project.projectId}"></div>
			<div title="版本控制" id="tabVer" url="${ctxPath}/oa/pro/proVers/list.do?projectId=${project.projectId}"></div>
			<div title="项目需求" id="tabAct" url="${ctxPath}/oa/pro/reqMgr/list.do?projectId=${project.projectId}"></div>
			<div title="动态" id="tabaction" url="${ctxPath}/oa/pro/proWorkMat/listforact.do?type=ACTION&projectId=${project.projectId}&noact=noact"></div>
			<div title="工作计划" id="tabaction" url="${ctxPath}/oa/pro/planTask/list.do?projectId=${project.projectId}"></div>
		</div>
	</div>
	
	</div>
	<rx:detailScript baseUrl="oa/pro/project" formId="form1"  entityName="com.redxun.oa.pro.entity.Project"/>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<script type="text/javascript">
		addBody();
		mini.parse();

	//打开项目的评论列表
	function listMat(){
		_OpenWindow({
    		url: __rootPath+"/oa/pro/proWorkMat/list.do?projectId="+${project.projectId},
            title: "评论列表", 
            width: 800, 
            height: 600,
            ondestroy: function(action) {
            }
    	});	
	}
	
	//按模板生成word
	function generate1(){
		_OpenWindow({
    		url: __rootPath+"/oa/pro/project/generateToWord.do?projectId="+${project.projectId},
            title: "she", width: 800, height: 600,
            ondestroy: function(action) {
            }
    	});	
		
		$.ajax({
            type: "Post",
            url : '${ctxPath}/oa/pro/project/generateToWord.do?projectId='+${project.projectId},
            success: function () {
            }
        }); 
	}
	
	
	//关注项目
	function payAttention(){
		$.ajax({
            type: "Post",
            async: false,
            url : '${ctxPath}/oa/pro/proWorkAtt/checkAttention.do?typePk=${project.projectId}',
            success: function (result) {
            	if(result.success==true){
            		 mini.confirm("确定要关注此项目？", "提示",
            		            function (action) {
            		                if (action == "ok") {
            		                	$.ajax({
            		                        type: "Post",
            		                        url : '${ctxPath}/oa/pro/proWorkAtt/payAttention.do?typePk=${project.projectId}&type=PROJECT',
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
                    content: "<b>提示</b> <br/>您已经关注此项目",
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