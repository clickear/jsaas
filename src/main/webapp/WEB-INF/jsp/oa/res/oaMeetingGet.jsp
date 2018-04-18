<%-- 
    Document   : [OaMeeting]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html>
<head>
<title>[OaMeeting]明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
	<div id="tabs1" class="mini-tabs" style="width: 100%; height: 100%" bodyStyle="border:0;">
	<div title="基本信息">
		<div>
			<table style="width: 100%" class="table-detail column_2" cellpadding="0"
				cellspacing="1">
				<caption>会议申请基本信息</caption>
				<tr>
					<th>会议名称</th>
					<td colspan="3">${oaMeeting.name}</td>
				</tr>
				<tr>
					<th>会议概要</th>
					<td colspan="3">${oaMeeting.descp}</td>
				</tr>
				<tr>
					<th>开始时间</th>
					<td><fmt:formatDate value="${oaMeeting.start}" pattern="yyyy-MM-dd HH:mm" /></td>
					<th>结束时间</th>
					<td><fmt:formatDate value="${oaMeeting.end}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>会议室</th>
					<td>${roomName}</td>
					<th>会议地点</th>
					<td>${oaMeeting.location}</td>
				</tr>
				<tr>
					<th>会议参与人</th>
					<td>${fullName}</td>
				</tr>
				<tr>
					<th>会议预算</th>
					<td>${oaMeeting.budget}</td>
					<th>主持人：</th>
					<td>${oaMeeting.hostName}</td>
				</tr>
				<tr>
					<th>会议记录员</th>
					<td>${oaMeeting.recordName}</td>
					<th>会议状态</th>
					<td>${oaMeeting.status}</td>
				</tr>
				<tr>
					<th>重要程度</th>
					<td>${oaMeeting.impDegree}</td>
					<th>审批历史</th>
					<td><a href="#" onclick="showCheckDetail('${oaMeeting.bpmInstId}')">审批明细</a>
					</td>
				</tr>
			</table>
		</div>
		

		
		<div>
			<table class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${oaMeeting.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${oaMeeting.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${oaMeeting.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${oaMeeting.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
		</div>
		<div title="参与人总结">
		<c:forEach items="${meetatt}" var="meetat">
		<table class="table-detail" cellspacing="1" cellpadding="0">
		<c:choose>
		<c:when test="${meetat.status=='PENDING AUDIT'}">
		<caption>${meetat.fullName}于<fmt:formatDate value="${meetat.updateTime}"
							pattern="yyyy-MM-dd HH:mm" />写了会议总结</caption>
			<tr>
			<td colspan="3">${meetat.summary}</td>
			</tr>
			</c:when>

			<c:otherwise>
			<caption>${meetat.fullName}还没提交会议总结</caption>
			</c:otherwise>
		</c:choose>
		</table>
		</c:forEach>
		
		</div>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/res/oaMeeting" entityName="com.redxun.oa.res.entity.OaMeeting" formId="form1" />
		<script type="text/javascript">
		function showCheckDetail(bpmInstId){
			_OpenWindow({
       			title:'审批明细',      			
       			width:800,
       			height:480,
       			url:__rootPath+'/bpm/core/bpmInst/get.do?actInstId='+bpmInstId
       		})
		}
		
	</script>
</body>
</html>