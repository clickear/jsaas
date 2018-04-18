<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<c:choose>
	<c:when test="${fn:length(bpmNodeJumps)>0 }">
		<c:forEach items="${bpmNodeJumps}" var="nodeJump">
			<table class="table-view" style="width:250px">
				<tr>
					<th width="80">审批人</th>
					<td width="170">
						<c:choose>
							<c:when test="${not empty nodeJump.ownerId && (nodeJump.ownerId!=nodeJump.handlerId)}">
								<rxc:userLabel userId="${nodeJump.handlerId}"/>(代:<rxc:userLabel userId="${nodeJump.ownerId}"/>)
							</c:when>
							<c:when test="${not empty nodeJump.handlerId}">
								<rxc:userLabel userId="${nodeJump.handlerId}"/>
							</c:when>
							<c:otherwise>
								无
							</c:otherwise>
						</c:choose>
						
					
					</td>
				</tr>
				<tr>
					<th>审批时间</th>
					<td><fmt:formatDate value="${nodeJump.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
				</tr>
				<tr>
					<th>审批状态</th>
					<td>${nodeJump.checkStatusText}</td>
				</tr>
				<tr>
					<th>审批意见</th>
					<td>${nodeJump.remark}</td>
				</tr>
			</table>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div><font color="red">暂无审批记录</font></div>
	</c:otherwise>
</c:choose>
