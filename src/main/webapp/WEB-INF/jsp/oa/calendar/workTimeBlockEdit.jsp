<%-- 
    Document   : [WorkTimeBlock]编辑页
    Created on : 2017-1-7, 10:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[WorkTimeBlock]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${workTimeBlock.settingId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="settingId" class="mini-hidden" value="${workTimeBlock.settingId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[WorkTimeBlock]基本信息</caption>

					<tr>
						<th>设定名称 ：</th>
						<td><input name="settingName" value="${workTimeBlock.settingName}" class="mini-textbox" vtype="maxLength:128" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>时间段组合json组合 ：</th>
						<td><textarea name="timeIntervals" class="mini-textarea" vtype="maxLength:1024" style="width: 90%">${workTimeBlock.timeIntervals}</textarea></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/calendar/workTimeBlock" entityName="com.redxun.oa.calendar.entity.WorkTimeBlock" />
</body>
</html>