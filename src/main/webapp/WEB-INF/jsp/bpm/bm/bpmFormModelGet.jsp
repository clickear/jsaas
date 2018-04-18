<%-- 
    Document   : 业务模型明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务模型明细</title>
<%@include file="/commons/get.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.css" />
<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.js"></script>

<style type="text/css">
html,body
{
    width:100%;
    height:100%;
    border:0;
    margin:0;
    padding:0;
    overflow:visible;
}
</style>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div class="mini-fit">
		<div id="tabs1" class="mini-tabs"  style="width:100%;height:100%">
			<div class="form-inner" title="业务模型基本信息">
				<div style="padding: 5px;">
					<table style="width: 100%" class="table-detail" cellpadding="0" cellspacing="1">
						<caption>业务模型基本信息</caption>
						<tr>
							<th>名称：</th>
							<td>${bpmFormModel.name}</td>
							<th>标识键：</th>
							<td>${bpmFormModel.key}</td>
						</tr>
						
						<tr>
							<th>版本号：</th>
							<td>${bpmFormModel.version}</td>
						
							<th>是否主版本：</th>
							<td>${bpmFormModel.isMain}</td>
						</tr>
						
						<tr>
							<th>是否持久化：</th>
							<td>${bpmFormModel.isPersist}</td>
						
							<th>是否公共：</th>
							<td>${bpmFormModel.isPublic}</td>
						</tr>
						<tr>
							<th>所属分类</th>
							<td>${bpmFormModel.sysTree.name}</td>
							<th>状态：</th>
							<td>${bpmFormModel.status}</td>
						</tr>
						<tr>
							<th>描述：</th>
							<td colspan="3">${bpmFormModel.descp}</td>
						</tr>
					</table>
				</div>
				<div style="padding: 5px">
					<table class="table-detail" cellpadding="0" cellspacing="1">
						<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${bpmFormModel.createBy}" /></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${bpmFormModel.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${bpmFormModel.updateBy}" /></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${bpmFormModel.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div title="JSON格式示例" iconCls="icon-script">
		        <pre id="jsonview"></pre>
		    </div>
		    <div title="纯文本格式" iconCls="icon-text">
		        <textarea  class="mini-textarea" id="jsonCode" style="width:100%;height:100%;">${jsonCode}</textarea>
		    </div>
		</div>
	</div>
	
	<rx:detailScript baseUrl="bpm/bm/bpmFormModel" formId="form1" entityName="com.redxun.bpm.bm.entity.BpmFormModel"/>
	
	<script type="text/javascript">
		$(function(){
			var json=mini.get('jsonCode').getValue();
			$("#jsonview").jsonViewer(mini.decode(json));	
		});
	</script>
</body>
</html>