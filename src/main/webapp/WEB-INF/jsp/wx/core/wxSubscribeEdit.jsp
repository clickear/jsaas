
<%-- 
    Document   : [微信关注者]编辑页
    Created on : 2017-06-30 08:51:08
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[微信关注者]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="wxSubscribe.id" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${wxSubscribe.id}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>[微信关注者]基本信息</caption>
					<tr>
						<th>SUBSCRIBE</th>
						<td>
							
								<input name="subscribe" value="${wxSubscribe.subscribe}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>OPENID</th>
						<td>
							
								<input name="openId" value="${wxSubscribe.openId}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>昵　称</th>
						<td>
							
								<input name="nickName" value="${wxSubscribe.nickName}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>语　言</th>
						<td>
							
								<input name="language" value="${wxSubscribe.language}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>城　市</th>
						<td>
							
								<input name="city" value="${wxSubscribe.city}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>省　份</th>
						<td>
							
								<input name="province" value="${wxSubscribe.province}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>国　家</th>
						<td>
							
								<input name="country" value="${wxSubscribe.country}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>绑定ID</th>
						<td>
							
								<input name="unionid" value="${wxSubscribe.unionid}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>最后的关注时间</th>
						<td>
							
								<input name="subscribeTime" value="${wxSubscribe.subscribeTime}"
							class="mini-datepicker"  format="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
						<th>粉丝备注</th>
						<td>
							
								<input name="remark" value="${wxSubscribe.remark}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>用户分组ID</th>
						<td>
							
								<input name="groupid" value="${wxSubscribe.groupid}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>标签ID列表</th>
						<td>
							
								<input name="tagidList" value="${wxSubscribe.tagidList}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="wx/core/wxSubscribe"
		entityName="com.redxun.wx.core.entity.WxSubscribe" />
</body>
</html>