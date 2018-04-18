<%-- 
    Document   : 供应商管理编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>供应商管理编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/uploadpanel/jquery-uploadpanel.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${crmProvider.proId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="proId" class="mini-hidden" value="${crmProvider.proId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>供应商管理基本信息</caption>
					<tr>
					
						<th>负责人ID ：</th>
						<td colspan="3"><input name="chargeId" value="${crmProvider.chargeId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					
					</tr>
					<tr>
						<th width="120">供应商名 <span class="star">*</span> ：</th>
						<td><input name="name" value="${crmProvider.name}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" required="true" emptyText="请输入供应商名" /></td>

						<th width="120">供应商简称 <span class="star">*</span> ：</th>
						<td><input name="shortDesc" value="${crmProvider.shortDesc}" class="mini-textbox" vtype="maxLength:100" style="width: 90%" required="true" emptyText="请输入供应商简称" />
						</td>
					</tr>

					<tr>
						<th>单位级别 ：</th>
						<td>
							<ui:dicCombox name="cmpLevel" dicKey="DWJB" value="${crmProvider.cmpLevel}" />
						</td>
						<th>单位类型 ：</th>
						<td>
							<ui:dicCombox name="cmpType" dicKey="DWLX" value="${crmProvider.cmpType}" />
						</td>
					</tr>

					<tr>
						<th>信用级别 ：</th>
						<td>
							<ui:dicCombox name="creditType" dicKey="XYJB" value="${crmProvider.creditType}" />
						</td>
						<th>信用额度 ：</th>
						<td><input name="creditLimit" value="${crmProvider.creditLimit}" class="mini-textbox" vtype="maxLength:10" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>信用账期 ：</th>
						<td><input name="creditPeriod" value="${crmProvider.creditPeriod}" class="mini-textbox" vtype="maxLength:10" style="width: 90%" /></td>
						<th>网站 ：</th>
						<td><input name="webSite" value="${crmProvider.webSite}" class="mini-textbox" vtype="maxLength:200" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>地址 ：</th>
						<td><input name="address" value="${crmProvider.address}" class="mini-textbox" vtype="maxLength:200" style="width: 90%" /></td>
						<th>邮编 ：</th>
						<td><input name="zip" value="${crmProvider.zip}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>联系人名 ：</th>
						<td><input name="contactor" value="${crmProvider.contactor}" class="mini-textbox" vtype="maxLength:32" style="width: 90%" /></td>
						<th>联系人手机 ：</th>
						<td><input name="mobile" value="${crmProvider.mobile}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>固定电话 ：</th>
						<td><input name="phone" value="${crmProvider.phone}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" /></td>

						<th>微信号 ：</th>
						<td><input name="weixin" value="${crmProvider.weixin}" class="mini-textbox" vtype="maxLength:50" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>微博号 ：</th>
						<td><input name="weibo" value="${crmProvider.weibo}" class="mini-textbox" vtype="maxLength:80" style="width: 90%" /></td>
						<th>状态 ：</th>
						<td><input name="status" value="${crmProvider.status}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>备注 ：</th>
						<td colspan="3">
							<ui:UEditor height="250" name="memo" id="memo">${crmProvider.memo}</ui:UEditor>
						</td>
					</tr>

					<tr>
						<th>附件IDS ：</th>
						<td colspan="3">
							 <input name="addtionFids" class="upload-panel" allowupload="true" allowlink="true" zipdown="true" fileIds="${crmProvider.addtionFids}"/>
            			</td>
					</tr>
					
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/crm/crmProvider" entityName="com.redxun.oa.crm.entity.CrmProvider" />
	
	<script type="text/javascript">
	$('.upload-panel').each(function() {
		$(this).uploadPanel();
	});
	</script>
</body>
</html>