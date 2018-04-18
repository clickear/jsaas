<%-- 
    Document   : [BpmFormAtt]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[BpmFormAtt]明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0" cellspacing="1">
				<caption>[BpmFormAtt]基本信息</caption>
				<tr>
					<th>所属模型ID：</th>
					<td>${bpmFormAtt.fmId}</td>
				</tr>
				<tr>
					<th>属性标签：</th>
					<td>${bpmFormAtt.title}</td>
				</tr>
				<tr>
					<th>属性标识：</th>
					<td>${bpmFormAtt.key}</td>
				</tr>
				<tr>
					<th>属性数据类型：</th>
					<td>${bpmFormAtt.dataType}</td>
				</tr>
				<tr>
					<th>属性类型：</th>
					<td>${bpmFormAtt.type}</td>
				</tr>
				<tr>
					<th>默认值：</th>
					<td>${bpmFormAtt.defVal}</td>
				</tr>
				<tr>
					<th>是否必须：</th>
					<td>${bpmFormAtt.required}</td>
				</tr>
				<tr>
					<th>长度：</th>
					<td>${bpmFormAtt.len}</td>
				</tr>
				<tr>
					<th>精度(小数位)：</th>
					<td>${bpmFormAtt.prec}</td>
				</tr>
				<tr>
					<th>序号：</th>
					<td>${bpmFormAtt.sn}</td>
				</tr>
				<tr>
					<th>分组标题：</th>
					<td>${bpmFormAtt.groupTitle}</td>
				</tr>
				<tr>
					<th>属性帮助描述：</th>
					<td>${bpmFormAtt.remark}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${bpmFormAtt.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${bpmFormAtt.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${bpmFormAtt.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${bpmFormAtt.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="bpm/bm/bpmFormAtt" formId="form1" />
</body>
</html>