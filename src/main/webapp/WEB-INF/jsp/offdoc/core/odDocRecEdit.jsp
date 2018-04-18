<%-- 
    Document   : [OdDocRec]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[OdDocRec]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${odDocRec.recId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="recId" class="mini-hidden" value="${odDocRec.recId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[OdDocRec]基本信息</caption>

					<tr>
						<th>收发文ID <span class="star">*</span> ：
						</th>
						<td><input name="docId" value="${odDocRec.docId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true"
							emptyText="请输入收发文ID" /></td>
					</tr>

					<tr>
						<th>收文部门ID <span class="star">*</span> ：
						</th>
						<td><input name="recDepId" value="${odDocRec.recDepId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%"
							required="true" emptyText="请输入收文部门ID" /></td>
					</tr>

					<tr>
						<th>收文单位类型 ：</th>
						<td><input name="recDtype" value="${odDocRec.recDtype}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>是否阅读 ：</th>
						<td><input name="isRead" value="${odDocRec.isRead}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>阅读时间 ：</th>
						<td><input name="readTime" value="${odDocRec.readTime}" class="mini-textbox" vtype="maxLength:19" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>反馈意见 ：</th>
						<td><input name="feedBack" value="${odDocRec.feedBack}" class="mini-textbox" vtype="maxLength:200" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>签收人ID ：</th>
						<td><input name="signUsrId" value="${odDocRec.signUsrId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>签收状态 ：</th>
						<td><input name="signStatus" value="${odDocRec.signStatus}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>签收时间 ：</th>
						<td><input name="signTime" value="${odDocRec.signTime}" class="mini-textbox" vtype="maxLength:19" style="width: 90%" />

						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="offdoc/core/odDocRec" entityName="com.redxun.offdoc.core.entity.OdDocRec" />
</body>
</html>