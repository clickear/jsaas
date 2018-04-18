<%-- 
    Document   : 系统模板明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统模板明细</title>
<%@include file="/commons/dynamic.jspf"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" 	type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />


<!-- code codemirror start -->

<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/xml/xml.js"></script>
<script src="${ctxPath}/scripts/codemirror/addon/mode/multiplex.js"></script>


<!-- code minitor end -->
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0"
				cellspacing="1">
				<caption>系统模板基本信息</caption>
				<tr>
					<th>分类：</th>
					<td>${sysTemplate.sysTree.name}</td>
					<th>分类Key：</th>
					<td>${sysTemplate.catKey}</td>
				</tr>
				<tr>
					<th>模板名称：</th>
					<td>${sysTemplate.name}</td>
					<th>是否系统缺省：</th>
					<td>${sysTemplate.isDefault}</td>
				</tr>
				<tr>
					<th>模板KEY：</th>
					<td colspan="3">${sysTemplate.key}</td>
				</tr>
				<tr>
					<th>模板描述：</th>
					<td colspan="3">${sysTemplate.descp}</td>
				</tr>
			</table>
			<br/>
			<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>模板内容</caption>
					<tr>
						<td colspan="2">
						<textarea id="content"
								vtype="maxLength:65535" style="width: 90%;height:400px;" required="true" 
								emptyText="请输入模板内容">${sysTemplate.content}</textarea>
						</td>
					</tr>
				</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${sysTemplate.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${sysTemplate.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${sysTemplate.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${sysTemplate.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="sys/core/sysTemplate" formId="form1" 
		entityName="com.redxun.sys.core.entity.SysTemplate"/>
	<script type="text/javascript">
	CodeMirror.defineMode("mycode", function(config) {
		  return CodeMirror.multiplexingMode(
		    CodeMirror.getMode(config, "text/html"),
		    {open: "<<", close: ">>",
		     mode: CodeMirror.getMode(config, "text/plain"),
		     delimStyle: "delimit"}
		    // .. more multiplexed styles can follow here
		  );
		});
	var editor = CodeMirror.fromTextArea(document.getElementById("content"), {
		  mode: "mycode",
		  lineNumbers: true,
		  lineWrapping: true
	});
	</script>
</body>
</html>