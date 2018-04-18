<%-- 
    Document   : 供应商管理明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>供应商管理明细</title>
<%@include file="/commons/dynamic.jspf"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0" cellspacing="1">
				<caption>供应商管理基本信息</caption>
				<tr>
					<th>负责人ID：</th>
					<td colspan="3">
						<rxc:userLabel userId="${crmProvider.chargeId}"></rxc:userLabel>
					</td>
				</tr>
				<tr>
					<th>供应商名：</th>
					<td>${crmProvider.name}</td>
					<th>供应商简称：</th>
					<td>${crmProvider.shortDesc}</td>
				</tr>
				<tr>
					<th>单位级别：</th>
					<td>${crmProvider.cmpLevel}</td>

					<th>单位类型：</th>
					<td>${crmProvider.cmpType}</td>
				</tr>
				<tr>
					<th>信用级别：</th>
					<td>${crmProvider.creditType}</td>

					<th>信用额度：</th>
					<td>${crmProvider.creditLimit}万元</td>
				</tr>
				<tr>
					<th>信用账期：</th>
					<td>${crmProvider.creditPeriod}天</td>

					<th>网站：</th>
					<td>${crmProvider.webSite}</td>
				</tr>
				<tr>
					<th>地址：</th>
					<td>${crmProvider.address}</td>

					<th>邮编：</th>
					<td>${crmProvider.zip}</td>
				</tr>
				<tr>
					<th>联系人名：</th>
					<td>${crmProvider.contactor}</td>

					<th>联系人手机：</th>
					<td>${crmProvider.mobile}</td>
				</tr>
				<tr>
					<th>固定电话：</th>
					<td>${crmProvider.phone}</td>

					<th>微信号：</th>
					<td>${crmProvider.weixin}</td>
				</tr>
				<tr>
					<th>微博号：</th>
					<td>${crmProvider.weibo}</td>
					<th>状态：</th>
					<td>${crmProvider.status}</td>
				<tr>
					<th>备注：</th>
					<td colspan="3">${crmProvider.memo}</td>
				</tr>
				<tr>
					<th>附件IDS：</th>
					<td colspan="3">${crmProvider.addtionFids}</td>
				</tr>
				<c:if test="${not empty crmProvider.actInstId }">
				<tr>
					<th>审批历史：</th>
					<td colspan="3">
						<a href="#" onclick="showCheckDetail('${crmProvider.actInstId}')">审批明细</a>
					</td>
				</tr>
				</c:if>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${crmProvider.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${crmProvider.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${crmProvider.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${crmProvider.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/crm/crmProvider" entityName="com.redxun.oa.crm.entity.CrmProvider" formId="form1" />
	<script type="text/javascript">
		function showCheckDetail(actInstId){
			_OpenWindow({
       			title:'审批明细',
       			width:800,
       			height:480,
       			url:__rootPath+'/bpm/core/bpmInst/get.do?actInstId='+actInstId
       		})
		}
	</script>
</body>
</html>