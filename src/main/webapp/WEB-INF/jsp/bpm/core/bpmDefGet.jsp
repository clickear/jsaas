<%-- 
    Document   : 流程定义明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>流程定义明细</title>
<%@include file="/commons/get.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.css" />
<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.js"></script>
<script type="text/javascript">
		$(function(){
			var json=$("#jsonview").html();
			$('#jsonview').html('');
			$("#jsonview").jsonViewer(mini.decode(json));	
		});
</script>
<style type="text/css">
	body{
		padding-top: 5px;
		box-sizing: border-box;
	}
	.mini-fit{
		background: #fff;
		margin-top: 0;
	}
	
	#toolbar1 td{text-align: center;}
	.mini-textbox{margin-right:0;}
	#tabs1{
		margin-top: 0 !important;
	}
	.mini-tabs-bodys{
		background: transparent;
	}
	
	.mini-textbox-border{
		border: none;
		background: transparent;
	}
</style>

</head>
<body>
<%-- 	<rx:toolbar toolbarId="toolbar1" pkId="${osUser.userId}" hideRecordNav="true"> --%>

<%-- 	</rx:toolbar> --%>
	<div class="mini-fit" style="padding:6px;">
		<div 
			id="tabs1" 
			class="mini-tabs" 
			style="width:100%;height:100%;padding:12px;" 
			bodyStyle="background-color:white"
		>
			<div title="流程定义基本信息" iconCls="icon-info" bodyStyle="background-color:#f7f7f7">
				<div class="topBtn">
					<c:if test="${not empty bpmDef.actDefId}">
						<a 
							class="mini-button" 
							iconCls="icon-download" 
							href="${ctxPath}/bpm/core/bpmDef/downloadBpmnFile.do?defId=${bpmDef.defId}" 
							target="_blank" 
							plain="true"
						>下载BPMN文件</a>
					</c:if>
				</div>
				<div class="shadowBox" style="margin-top:40px; ">
					<table  class="table-detail column_2_m" cellpadding="0" cellspacing="1">
						<caption>流程定义基本信息</caption>
						<tr>
							<th width="120">标　题</th>
							<td width="200">${bpmDef.subject}</td>
							<th width="120">标识Key</th>
							<td width="200">${bpmDef.key}</td>
						</tr>
						<tr>
							<th>版本号</th>
							<td>${bpmDef.version}</td>
							<th>是否主版本</th>
							<td>${bpmDef.isMain}</td>
						</tr>
						<tr>
							<th>Activiti流程定义ID</th>
							<td>${bpmDef.actDefId}</td>
							<th>ACT流程发布ID</th>
							<td>${bpmDef.actDepId}</td>
						</tr>
						<tr>
							<th>设计模型ID</th>
							<td>${bpmDef.modelId}</td>
							<th>主定义ID</th>
							<td>${bpmDef.mainDefId}</td>
						</tr>
						<tr>
							<th>描　述</th>
							<td colspan="3">${bpmDef.descp}</td>
						</tr>
					</table>
					<div class="heightBox"></div>
					<table class="table-detail column_2_m" cellpadding="0" cellspacing="1">
						<caption>更新信息</caption>
						<tr>
							<th width="120">创建人</th>
							<td width="200"><rxc:userLabel userId="${bpmDef.createBy}" /></td>
							<th width="120">创建时间</th>
							<td width="200"><fmt:formatDate value="${bpmDef.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${bpmDef.updateBy}" /></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${bpmDef.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
				</div>
			</div>
			<c:if test="${not empty bpmDef.actDefId}">
				<div title="流程图" iconCls="icon-flow" >
					 <img src="${ctxPath}/bpm/activiti/processImage.do?actDefId=${bpmDef.actDefId}" />
				</div>
				<div title="Activiti设计源码" iconCls="icon-script" style="background-color:white">
					<textarea 
						class="mini-textarea mini-textarea-margin" 
						id="actDefXml" 
						style="width:100%;height:99%" 
						allowInput="false"
					>${bpmnXml}</textarea>
				</div>
				</c:if>
				<div 
					title="设计器源代码" 
					iconCls="icon-script" 
					style="background-color:white;"
				>
					<div id="jsonview"  style="width:100%;height:100%;padding-left: 14px;box-sizing: border-box;">${editorJson}</div>
				</div>
			</div>
	</div>

	<rx:detailScript baseUrl="bpm/core/bpmDef" formId="form1" entityName="com.redxun.bpm.core.entity.BpmDef"/>
	<script type="text/javascript">
		function writeXml(){
			_SubmitJson({
				url:__rootPath+'/bpm/core/bpmDef/saveXml.do',
				data:{
					defId:'${bpmDef.defId}',
					actDefXml:mini.get('actDefXml').getValue()
				},
				method:'POST',
				success:function(){
					alert('success');
				}
			});
		}
	</script>
</body>
</html>