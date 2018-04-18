<%-- 
    Document   : 资产参数编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>资产参数编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${oaAssParameter.paraId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="paraId" class="mini-hidden"
					value="${oaAssParameter.paraId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>资产参数基本信息</caption>

					<tr>
						<th>参数VALUE主键 ：</th>
						<td><input name="valueId" value="${oaAssParameter.valueId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>参数KEY主键 ：</th>
						<td><input name="keyId" value="${oaAssParameter.keyId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>资产ID ：</th>
						<td><input name="assId" value="${oaAssParameter.assId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>自定义VALUE名 ：</th>
						<td><input name="customValueName"
							value="${oaAssParameter.customValueName}" class="mini-textbox"
							vtype="maxLength:255" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>自定义KEY名 ：</th>
						<td><input name="customKeyName"
							value="${oaAssParameter.customKeyName}" class="mini-textbox"
							vtype="maxLength:255" style="width: 90%" />

						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/product/oaAssParameter"
		entityName="com.redxun.oa.product.entity.OaAssParameter" />
</body>
</html>