<%-- 
    Document   : 系统流水号明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<title>系统流水号明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
<%-- 	<rx:toolbar toolbarId="toolbar1" /> --%>
	<div class="heightBox"></div>
	<div id="form1" class="form-outer shadowBox90">
		
			<table class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>系统流水号基本信息</caption>
				<tr>
					<th width="120">名　　称</th>
					<td>${sysSeqId.name}</td>

					<th width="120">别　　名</th>
					<td>${sysSeqId.alias}</td>
				</tr>
				<tr>
					<th>当前日期值</th>
					<td>
						<c:if test="${not empty sysSeqId.curDate }">
							<fmt:formatDate value="${sysSeqId.curDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</c:if>
					</td>
				</tr>
				<tr>
					<th>规　　则</th>
					<td colspan="3">${sysSeqId.rule}</td>
				</tr>
				
				<tr>
					<th>生成方式 </th>
					<td colspan="3">${sysSeqId.genType}</td>
				</tr>
				<tr>
					<th>流水号长度</th>
					<td>${sysSeqId.len}</td>
					<th>当  前  值</th>
					<td>${sysSeqId.curVal}</td>
				</tr>
				<tr>
					<th>初  始  值</th>
					<td>${sysSeqId.initVal}</td>
					<th>步　　长</th>
					<td>${sysSeqId.step}</td>
				</tr>
				<tr>
					<th>备　　注</th>
					<td colspan="3">${sysSeqId.memo}</td>
				</tr>
			</table>
		
			<table class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创  建  人</th>
					<td><rxc:userLabel userId="${sysSeqId.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${sysSeqId.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更  新  人</th>
					<td><rxc:userLabel userId="${sysSeqId.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${sysSeqId.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
	</div>
	<rx:detailScript baseUrl="sys/core/sysSeqId" entityName="com.redxun.sys.core.entity.SysSeqId" formId="form1" />
	<script type="text/javascript">
		addBody();
	</script>
	
</body>
</html>