
<%-- 
    Document   : [公众号管理]明细页
    Created on : 2017-06-29 16:57:29
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[公众号管理]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>
		<div class="heightBox"></div>
        <div id="form1" class="form-outer shadowBox90">
             <div>
                    <table style="width:100%" class="table-detail column_2_m" cellpadding="0" cellspacing="1">
                    	<caption>[公众号管理]基本信息</caption>
						<tr>
							<th>微  信  号</th>
							<td>
								${wxPubApp.wxNo}
							</td>
							<th>APP_ID_</th>
							<td>
								${wxPubApp.appId}
							</td>
						</tr>
						<tr>
							<th>密　　钥</th>
							<td>
								${wxPubApp.secret}
							</td>
							<th>类　　型</th>
							<td>
								${wxPubApp.type}
							</td>
						</tr>
						<tr>
							<th>是否认证</th>
							<td>
								${wxPubApp.authed}
							</td>
							<th>接口消息地址</th>
							<td>
								${wxPubApp.interfaceUrl}
							</td>
						</tr>
						<tr>
							<th>token</th>
							<td>
								${wxPubApp.TOKEN}
							</td>
							<th>js安全域名</th>
							<td>
								${wxPubApp.jsDomain}
							</td>
						</tr>
						<tr>
							<th>名　　称</th>
							<td>
								${wxPubApp.name}
							</td>
							<th>别　　名</th>
							<td>
								${wxPubApp.alias}
							</td>
						</tr>
						<tr>
							<th>描　　述</th>
							<td>
								${wxPubApp.description}
							</td>
							<th>租用机构ID</th>
							<td>
								${wxPubApp.tenantId}
							</td>
						</tr>
					</table>
                 </div>
	            <div>
					 <table class="table-detail column_2_m" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创  建  人</th>
							<td><rxc:userLabel userId="${wxPubApp.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${wxPubApp.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更  新  人</th>
							<td><rxc:userLabel userId="${wxPubApp.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${wxPubApp.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="wx/core/wxPubApp" 
        entityName="com.redxun.wx.core.entity.WxPubApp"
        formId="form1"/>
    </body>
</html>