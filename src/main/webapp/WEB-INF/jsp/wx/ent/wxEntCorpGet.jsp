
<%-- 
    Document   : [微信企业配置]明细页
    Created on : 2017-06-04 12:27:36
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[微信企业配置]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail column_1" cellpadding="0" cellspacing="1">
                    	<caption>[微信企业配置]基本信息</caption>
						<tr>
							<th>企业ID</th>
							<td>
								${wxEntCorp.corpId}
							</td>
						</tr>
						<tr>
							<th>通讯录密钥</th>
							<td>
								${wxEntCorp.secret}
							</td>
						</tr>
						<tr>
							<th>是否启用企业微信</th>
							<td>
								${wxEntCorp.enable}
							</td>
						</tr>
						<tr>
							<th>租户ID</th>
							<td>
								${wxEntCorp.tenantId}
							</td>
						</tr>
						<tr>
							<th>创建时间</th>
							<td>
								${wxEntCorp.createTime}
							</td>
						</tr>
						<tr>
							<th>创建人</th>
							<td>
								${wxEntCorp.createBy}
							</td>
						</tr>
						<tr>
							<th>更新时间</th>
							<td>
								${wxEntCorp.updateTime}
							</td>
						</tr>
						<tr>
							<th>更新人</th>
							<td>
								${wxEntCorp.updateBy}
							</td>
						</tr>
					</table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail column_2" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${wxEntCorp.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${wxEntCorp.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${wxEntCorp.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${wxEntCorp.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="wx/ent/wxEntCorp" 
        entityName="com.redxun.wx.ent.entity.WxEntCorp"
        formId="form1"/>
    </body>
</html>