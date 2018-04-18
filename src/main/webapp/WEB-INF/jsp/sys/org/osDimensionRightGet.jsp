
<%-- 
    Document   : [维度授权]明细页
    Created on : 2017-05-16 14:12:56
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[维度授权]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>[维度授权]基本信息</caption>
						<tr>
							<th>用户ID：</th>
							<td>
								${osDimensionRight.userId}
							</td>
						</tr>
						<tr>
							<th>组ID：</th>
							<td>
								${osDimensionRight.groupId}
							</td>
						</tr>
						<tr>
							<th>_：</th>
							<td>
								${osDimensionRight.dimId}
							</td>
						</tr>
						<tr>
							<th>机构ID：</th>
							<td>
								${osDimensionRight.tenantId}
							</td>
						</tr>
						<tr>
							<th>创建人ID：</th>
							<td>
								${osDimensionRight.createBy}
							</td>
						</tr>
						<tr>
							<th>创建时间：</th>
							<td>
								${osDimensionRight.createTime}
							</td>
						</tr>
						<tr>
							<th>更新人ID：</th>
							<td>
								${osDimensionRight.updateBy}
							</td>
						</tr>
						<tr>
							<th>更新时间：</th>
							<td>
								${osDimensionRight.updateTime}
							</td>
						</tr>
					</table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${osDimensionRight.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${osDimensionRight.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${osDimensionRight.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${osDimensionRight.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="sys/org/osDimensionRight" 
        entityName="com.redxun.sys.org.entity.OsDimensionRight"
        formId="form1"/>
    </body>
</html>