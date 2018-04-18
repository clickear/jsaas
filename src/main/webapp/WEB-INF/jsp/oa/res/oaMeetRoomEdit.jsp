<%-- 
    Document   : [OaMeetRoom]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[OaMeetRoom]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${oaMeetRoom.roomId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="roomId" class="mini-hidden"
					value="${oaMeetRoom.roomId}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>会议室基本信息</caption>
					<tr>
						<th>会议室名称
							<span class="star">*</span> 
						</th>
						<td>
							<input name="name" value="${oaMeetRoom.name}"
							class="mini-textbox" vtype="maxLength:100" style="width: 90%"
							required="true" emptyText="请输入会议室名称" />
						</td>
					</tr>
					<tr>
						<th>会议室地址 </th>
						<td><input name="location" value="${oaMeetRoom.location}"
							class="mini-textbox" vtype="maxLength:255" style="width: 90%" />

						</td>
					</tr>
					<tr>
						<th>会议室描述 </th>
						<td><textarea name="descp" class="mini-textarea" vtype="maxLength:65535" style="width: 90%">${oaMeetRoom.descp}</textarea>
						</td>
						
						<%-- <ui:UEditor height="150" isMini="true"
								name="descp" id="descp">${oaMeetRoom.descp}</ui:UEditor></td> --%>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/res/oaMeetRoom"
		entityName="com.redxun.oa.res.entity.OaMeetRoom" />
</body>
</html>