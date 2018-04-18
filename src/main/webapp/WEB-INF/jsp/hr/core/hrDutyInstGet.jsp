<%-- 
    Document   : [HrDutyInst]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDutyInst]明细</title>
 <%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" hideRecordNav="true" excludeButtons="remove" />
	<p>姓名：${hrDutyInst.userName}</p>
	<p>部门：${hrDutyInst.depName}</p>
	<p>日期：${hrDutyInst.date}</p>
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0" cellspacing="1">
				<caption>班次信息</caption>
				
				<tr>
					<th>班次</th>
					<th>简称</th>
					<th>时段</th>
					<th>上班</th>
					<th>下班</th>	
				</tr>

				<tr>
					<td rowspan="
					<c:choose>
						<c:when test="${fn:length(hrDutyInst.hrDutyInstExts)<=1 }">
							2
						</c:when>
						<c:otherwise>
							${fn:length(hrDutyInst.hrDutyInstExts)}
						</c:otherwise>
					</c:choose>">${hrDutyInst.sectionName}（${hrDutyInst.sectionShortName}）<br/><input class="mini-combobox" minWidth="120" url="${ctxPath}/hr/core/hrDutySection/getAllSectionAndRestSection.do" value="---更改---" valueField="sectionId" textField="sectionName" showNullItem="true" nullItemText="---更改---"  /></td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${hrDutyInst.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${hrDutyInst.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${hrDutyInst.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${hrDutyInst.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="hr/core/hrDutyInst" entityName="com.redxun.hr.core.entity.HrDutyInst" formId="form1" />
</body>
</html>