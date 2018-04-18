
<%-- 
    Document   : [微信应用]明细页
    Created on : 2017-06-04 12:27:36
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[微信应用]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>
	<div class="heightBox"></div>
	<div id="form1" class="form-outer shadowBox90">
		<table style="width:100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
			<caption>
				[微信应用]基本信息
			</caption>
			<tr>
				<th>
					NAME_
				</th>
				<td>
					${wxEntAgent.name}
				</td>
			</tr>
			<tr>
				<th>
					DESCRIPTION_
				</th>
				<td>
					${wxEntAgent.description}
				</td>
			</tr>
			<tr>
				<th>
					信任域名
				</th>
				<td>
					${wxEntAgent.domain}
				</td>
			</tr>
			<tr>
				<th>
					HOME_URL_
				</th>
				<td>
					${wxEntAgent.homeUrl}
				</td>
			</tr>
			<tr>
				<th>
					企业主键
				</th>
				<td>
					${wxEntAgent.entId}
				</td>
			</tr>
			<tr>
				<th>
					企业 ID
				</th>
				<td>
					${wxEntAgent.corpId}
				</td>
			</tr>
			<tr>
				<th>
					应用 ID
				</th>
				<td>
					${wxEntAgent.agentId}
				</td>
			</tr>
			<tr>
				<th>
					密　　钥
				</th>
				<td>
					${wxEntAgent.secret}
				</td>
			</tr>
			<tr>
				<th>
					是否默认
				</th>
				<td>
					<c:choose>
						<c:when test="${wxEntAgent.defaultAgent==1 }">
							是
						</c:when>
						<c:otherwise>
							否
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</div>
        <rx:detailScript baseUrl="wx/ent/wxEntAgent" 
        entityName="com.redxun.wx.ent.entity.WxEntAgent"
        formId="form1"/>
        
	<script type="text/javascript">
		addBody();
	</script>
    </body>
</html>