
<%-- 
    Document   : [微信用户标签]明细页
    Created on : 2017-06-29 17:55:31
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[微信用户标签]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail column_1" cellpadding="0" cellspacing="1">
                    	<caption>[微信用户标签]基本信息</caption>
						<tr>
							<th>标签名</th>
							<td>
								${wxTagUser.tagId}
							</td>
						</tr>
						<tr>
							<th>用户ID</th>
							<td>
								${wxTagUser.userId}
							</td>
						</tr>
						<tr>
							<th>租用机构ID</th>
							<td>
								${wxTagUser.tenantId}
							</td>
						</tr>
						<tr>
							<th>更新时间</th>
							<td>
								${wxTagUser.updateTime}
							</td>
						</tr>
						<tr>
							<th>更新人ID</th>
							<td>
								${wxTagUser.updateBy}
							</td>
						</tr>
						<tr>
							<th>创建时间</th>
							<td>
								${wxTagUser.createTime}
							</td>
						</tr>
						<tr>
							<th>创建人ID</th>
							<td>
								${wxTagUser.createBy}
							</td>
						</tr>
					</table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail column_2" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${wxTagUser.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${wxTagUser.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${wxTagUser.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${wxTagUser.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="wx/core/wxTagUser" 
        entityName="com.redxun.wx.core.entity.WxTagUser"
        formId="form1"/>
    </body>
</html>