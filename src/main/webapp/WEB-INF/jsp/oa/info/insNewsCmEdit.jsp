<%-- 
    Document   : 评论编辑页,暂时没用
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新闻评论编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${insNewsCm.commId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div style="padding: 5px;">
				<input id="pkId" name="commId" class="mini-hidden" value="${insNewsCm.commId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>新闻评论基本信息</caption>

					<tr>
						<th>信息ID <span class="star">*</span> ：
						</th>
						<td><input name="newId" value="${insNewsCm.newId}" class="mini-textbox" vtype="maxLength:64" required="true" emptyText="请输入信息ID" /></td>
					</tr>

					<tr>
						<th>评论人名 <span class="star">*</span> ：
						</th>
						<td><input name="fullName" value="${insNewsCm.fullName}" class="mini-textbox" vtype="maxLength:50" required="true" emptyText="请输入评论人名" /></td>
					</tr>

					<tr>
						<th>评论内容 <span class="star">*</span> ：
						</th>
						<td><textarea name="content" class="mini-textarea" vtype="maxLength:1024" style="width: 90%" required="true" emptyText="请输入评论内容">${insNewsCm.content}
														 </textarea></td>
					</tr>

					<tr>
						<th>赞同与顶 <span class="star">*</span> ：
						</th>
						<td><input name="agreeNums" value="${insNewsCm.agreeNums}" class="mini-textbox" vtype="maxLength:10" required="true" emptyText="请输入赞同与顶" /></td>
					</tr>

					<tr>
						<th>反对与鄙视次数 <span class="star">*</span> ：
						</th>
						<td><input name="refuseNums" value="${insNewsCm.refuseNums}" class="mini-textbox" vtype="maxLength:10" required="true" emptyText="请输入反对与鄙视次数" /></td>
					</tr>

					<tr>
						<th>是否为回复 <span class="star">*</span> ：
						</th>
						<td><input name="isReply" value="${insNewsCm.isReply}" class="mini-textbox" vtype="maxLength:20" required="true" emptyText="请输入是否为回复" /></td>
					</tr>

					<tr>
						<th>回复评论ID ：</th>
						<td><input name="repId" value="${insNewsCm.repId}" class="mini-textbox" vtype="maxLength:64" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/info/insNewsCm" entityName="com.redxun.oa.info.entity.InsNewsCm" />
</body>
</html>