<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <table style="width:300px">
	<tr>
		<th width="80">节点名称:</th>
		<td width="170">
			${taskNodeUser.nodeText}
		</td>
	</tr>
	<tr>
		<th>节点处理人:
			<c:choose>
				<c:when test="${taskNodeUser.multiInstance=='parallel'}"><div>(并行会签)</div></c:when>
				<c:when test="${taskNodeUser.multiInstance=='sequential'}"><div>(串行会签)</div></c:when>
			</c:choose>
			
		</th>
		<td>
			${taskNodeUser.userFullnames}
		</td>
	</tr>
</table> --%>

<ul class="tip-ul">
	<li>
		<h1>节点名称：</h1>
		<h2>${taskNodeUser.nodeText}</h2>
	</li>
	<li>
		<h1>
			处  理  人：
			<c:choose>
				<c:when test="${taskNodeUser.multiInstance=='parallel'}">(并行会签)</c:when>
				<c:when test="${taskNodeUser.multiInstance=='sequential'}">(串行会签)</c:when>
			</c:choose>
		</h1>
		<h2>
			${taskNodeUser.userFullnames}
		</h2>
	</li>
</ul>
