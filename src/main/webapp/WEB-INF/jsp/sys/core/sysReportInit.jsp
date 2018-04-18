<%-- 
    Document   : [SysReport]编辑页
    Created on : 2015-3-21, 0:11:48
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 使用范围：
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[SysReport]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${sysReport.repId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="repId" class="mini-hidden" value="${sysReport.repId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>报表基本信息</caption>

					<tr>
						<th>分类Id ：</th>
						<td><input name="treeId" value="${sysReport.treeId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>数据源ID ：</th>
						<td><input name="sourceId" value="${sysReport.sourceId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>标题 <span class="star">*</span> ：
						</th>
						<td><input name="subject" value="${sysReport.subject}" class="mini-textbox" vtype="maxLength:128" style="width: 90%" required="true" emptyText="请输入标题" /></td>
					</tr>

					<tr>
						<th>标识key ：</th>
						<td><input name="key" value="${sysReport.key}" class="mini-textbox" vtype="maxLength:128" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>描述 <span class="star">*</span> ：
						</th>
						<td><textarea name="descp" class="mini-textarea" vtype="maxLength:500" style="width: 90%" required="true" emptyText="请输入描述">${sysReport.descp}</textarea></td>
					</tr>

					<tr>
						<th>jasper路径 <span class="star">*</span> ：
						</th>
						<td><input name="filePath" value="${sysReport.filePath}" class="mini-textbox" vtype="maxLength:128" style="width: 90%" required="true" emptyText="请输入报表模块的jasper文件的路径" /></td>
					</tr>

					<tr>
						<th>文件ID：</th>
						<td><input name="fileId" value="${sysReport.fileId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>是否缺省：</th>
						<td><input name="defaults" value="${sysReport.defaults}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>报表解析引擎：</th>
						<td><input name="engine" value="${sysReport.engine}" class="mini-textbox" vtype="maxLength:30" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>参数配置 ：</th>
						<td><textarea name="paramConfig" class="mini-textarea" vtype="maxLength:65535" style="width: 90%">${sysReport.paramConfig}</textarea></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/core/sysReport" />
</body>
</html>