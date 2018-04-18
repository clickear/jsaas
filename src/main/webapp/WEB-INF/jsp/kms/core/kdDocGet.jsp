<%-- 
    Document   : [KdDoc]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>知识仓库明细</title>
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
				<caption>知识文档基本信息</caption>
				<tr>
					<th width="25%">所属分类：</th>
					<td width="25%">${kdDoc.treeId}</td>
					<th width="25%">文档标题：</th>
					<td width="25%">${kdDoc.subject}</td>
				</tr>
				<tr>
					<th>词条或知识模板ID：</th>
					<td>${kdDoc.tempId}</td>
					<th>是否精华：</th>
					<td>${kdDoc.isEssence}</td>
				</tr>
				<tr>
					<th>作者：</th>
					<td>${kdDoc.author}</td>
					<th>作者类型：</th>
					<td>${kdDoc.authorType}</td>
				</tr>
				<tr>
					<th>所属岗位：</th>
					<td>${kdDoc.authorPos}</td>
					<th>所属部门ID：</th>
					<td>${kdDoc.belongDepid}</td>
				</tr>
				<tr>
					<th>关键字：</th>
					<td>${kdDoc.keywords}</td>
					<th>标签：</th>
					<td>${kdDoc.tags}</td>
				</tr>
				<tr>
					<th>发布日期：</th>
					<td>${kdDoc.issuedTime}</td>
					<th>浏览次数：</th>
					<td>${kdDoc.viewTimes}</td>
				</tr>
				<tr >
					<th>摘要：</th>
					<td colspan="3">${kdDoc.summary}</td>
				</tr>
				<tr>
					<th>知识内容：</th>
					<td colspan="3">${kdDoc.content}</td>
				</tr>
				<tr>
					<th>综合评分：</th>
					<td>${kdDoc.compScore}</td>
					<th>存放年限：</th>
					<td>${kdDoc.storePeroid}</td>
				</tr>
				<tr>
					<th>审批人ID：</th>
					<td>${kdDoc.approvalId}</td>
					<th>流程实例ID：</th>
					<td>${kdDoc.bpmInstId}</td>
				</tr>
				<tr>
					<th>封面图：</th>
					<td>${kdDoc.coverImgId}</td>
					<th>文档附件：</th>
					<td>${kdDoc.attFileids}</td>
				</tr>
				<tr>
					<th>归档分类：</th>
					<td>${kdDoc.archClass}</td>
					<th>文档状态：</th>
					<td>${kdDoc.status}</td>
				</tr>
				<tr>
					<th>文档类型：</th>
					<td>${kdDoc.docType}</td>
					<th>版本号：</th>
					<td>${kdDoc.version}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${kdDoc.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${kdDoc.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${kdDoc.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${kdDoc.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="kms/core/kdDoc"  entityName="com.redxun.kms.core.entity.KdDoc" formId="form1" />
</body>
</html>