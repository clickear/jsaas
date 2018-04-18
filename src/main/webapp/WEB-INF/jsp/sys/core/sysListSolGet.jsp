
<%-- 
    Document   : [系统列表方案]明细页
    Created on : 2017-05-21 12:11:18
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[系统列表方案]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>[系统列表方案]基本信息</caption>
						<tr>
							<th>标识健：</th>
							<td>
								${sysListSol.key}
							</td>
						</tr>
						<tr>
							<th>名称：</th>
							<td>
								${sysListSol.name}
							</td>
						</tr>
						<tr>
							<th>描述：</th>
							<td>
								${sysListSol.descp}
							</td>
						</tr>
						<tr>
							<th>权限配置：</th>
							<td>
								${sysListSol.rightConfigs}
							</td>
						</tr>
						<tr>
							<th>租户ID：</th>
							<td>
								${sysListSol.tenantId}
							</td>
						</tr>
						<tr>
							<th>创建人：</th>
							<td>
								${sysListSol.createBy}
							</td>
						</tr>
						<tr>
							<th>创建时间：</th>
							<td>
								${sysListSol.createTime}
							</td>
						</tr>
						<tr>
							<th>更新人：</th>
							<td>
								${sysListSol.updateBy}
							</td>
						</tr>
						<tr>
							<th>更新时间：</th>
							<td>
								${sysListSol.updateTime}
							</td>
						</tr>
					</table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${sysListSol.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${sysListSol.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${sysListSol.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${sysListSol.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="sys/core/sysListSol" 
        entityName="com.redxun.sys.core.entity.SysListSol"
        formId="form1"/>
    </body>
</html>