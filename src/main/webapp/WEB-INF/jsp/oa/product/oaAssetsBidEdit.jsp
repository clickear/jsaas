<%-- 
    Document   : 资产申请编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>资产申请编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${oaAssetsBid.bidId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="bidId" class="mini-hidden"
					value="${oaAssetsBid.bidId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>资产申请基本信息</caption>

					<tr>
						<th>资产ID ：</th>
						<td><input name="assId" value="${oaAssetsBid.assId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>参数ID(不做关联) ：</th>
						<td><input name="paraId" value="${oaAssetsBid.paraId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>开始时间 <span class="star">*</span> ：
						</th>
						<td><input name="start" value="${oaAssetsBid.start}"
							class="mini-textbox" vtype="maxLength:19" style="width: 90%"
							required="true" emptyText="请输入开始时间" />

						</td>
					</tr>

					<tr>
						<th>结束时间 <span class="star">*</span> ：
						</th>
						<td><input name="end" value="${oaAssetsBid.end}"
							class="mini-textbox" vtype="maxLength:19" style="width: 90%"
							required="true" emptyText="请输入结束时间" />

						</td>
					</tr>

					<tr>
						<th>申请说明 ：</th>
						<td><textarea name="memo" class="mini-textarea"
								vtype="maxLength:65535" style="width: 90%">${oaAssetsBid.memo}
														 </textarea></td>
					</tr>

					<tr>
						<th>申请人员 ：</th>
						<td><input name="useMans" value="${oaAssetsBid.useMans}"
							class="mini-textbox" vtype="maxLength:20" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>状态 ：</th>
						<td><input name="status" value="${oaAssetsBid.status}"
							class="mini-textbox" vtype="maxLength:20" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>流程实例ID ：</th>
						<td><input name="bpmInstId" value="${oaAssetsBid.bpmInstId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/product/oaAssetsBid"
		entityName="com.redxun.oa.product.entity.OaAssetsBid" />
</body>
</html>