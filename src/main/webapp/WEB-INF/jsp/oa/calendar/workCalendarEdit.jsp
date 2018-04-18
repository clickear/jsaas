<%-- 
    Document   : [WorkCalendar]编辑页
    Created on : 2017-1-7, 10:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[WorkCalendar]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${workCalendar.calenderId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="calenderId" class="mini-hidden" value="${workCalendar.calenderId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">

					<tr>
						<th>设定ID <span class="star">*</span> ：
						</th>
						<td><input name="settingId" value="${workCalendar.settingId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%"
							required="true" emptyText="请输入设定ID" /></td>
					</tr>

					<tr>
						<th>开始时间 <span class="star">*</span> ：
						</th>
						<td><input name="startTime" value="${workCalendar.startTime}" class="mini-textbox" vtype="maxLength:19" style="width: 90%"
							required="true" emptyText="请输入开始时间" /></td>
					</tr>

					<tr>
						<th>结束时间 <span class="star">*</span> ：
						</th>
						<td><input name="endTime" value="${workCalendar.endTime}" class="mini-textbox" vtype="maxLength:19" style="width: 90%"
							required="true" emptyText="请输入结束时间" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/calendar/workCalendar" entityName="com.redxun.oa.calendar.entity.WorkCalendar" />
</body>
</html>