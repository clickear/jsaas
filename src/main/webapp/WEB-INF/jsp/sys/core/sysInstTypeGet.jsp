
<%-- 
    Document   : [机构类型]明细页
    Created on : 2017-07-10 18:35:32
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[机构类型]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>[机构类型]基本信息</caption>
						<tr>
							<th>类型编码：</th>
							<td>
								${sysInstType.typeCode}
							</td>
						</tr>
						<tr>
							<th>类型名称：</th>
							<td>
								${sysInstType.typeName}
							</td>
						</tr>
						<tr>
							<th>是否启用：</th>
							<td>
								${sysInstType.enabled}
							</td>
						</tr>
						<tr>
							<th>是否系统缺省：</th>
							<td>
								${sysInstType.isDefault}
							</td>
						</tr>
						<tr>
							<th>描述：</th>
							<td>
								${sysInstType.descp}
							</td>
						</tr>
						<tr>
							<th>租用用户Id：</th>
							<td>
								${sysInstType.tenantId}
							</td>
						</tr>
						<tr>
							<th>创建人ID：</th>
							<td>
								${sysInstType.createBy}
							</td>
						</tr>
						<tr>
							<th>创建时间：</th>
							<td>
								${sysInstType.createTime}
							</td>
						</tr>
						<tr>
							<th>更新人ID：</th>
							<td>
								${sysInstType.updateBy}
							</td>
						</tr>
						<tr>
							<th>更新时间：</th>
							<td>
								${sysInstType.updateTime}
							</td>
						</tr>
					</table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${sysInstType.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${sysInstType.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${sysInstType.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${sysInstType.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="sys/core/sysInstType" 
        entityName="com.redxun.sys.core.entity.SysInstType"
        formId="form1"/>
    </body>
</html>