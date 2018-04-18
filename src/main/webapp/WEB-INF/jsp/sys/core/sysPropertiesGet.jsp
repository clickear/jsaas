
<%-- 
    Document   : [系统参数]明细页
    Created on : 2017-06-21 11:22:36
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[系统参数]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>
		<div class="heightBox"></div>

        <div id="form1" class="form-outer shadowBox90">
            
                   <table  class="table-detail column_2_m" cellpadding="0" cellspacing="1">
                   	<caption>[系统参数]基本信息</caption>
					<tr>
						<th>名　　称</th>
						<td>
							${sysProperties.name}
						</td>
						<th>别　　名</th>
						<td>
							${sysProperties.alias}
						</td>
					</tr>
					<tr>
						<th>是否全局</th>
						<td>
							${sysProperties.global}
						</td>
						<th>是否加密存储</th>
						<td>
							${sysProperties.encrypt}
						</td>
					</tr>
					<tr>
						<th>参  数  值</th>
						<td>
							${sysProperties.value}
						</td>
						<th>分　　类</th>
						<td>
							${sysProperties.category}
						</td>
					</tr>
					<tr>
						<th>描　　述</th>
						<td>
							${sysProperties.description}
						</td>
						<th>租用Id</th>
						<td>
							${sysProperties.tenantId}
						</td>
					</tr>
				</table>
               
				 <table class="table-detail column_2_m" cellpadding="0" cellspacing="1">
				 	<caption>更新信息</caption>
					<tr>
						<th>创  建  人</th>
						<td><rxc:userLabel userId="${sysProperties.createBy}"/></td>
						<th>创建时间</th>
						<td><fmt:formatDate value="${sysProperties.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
					</tr>
					<tr>
						<th>更  新  人</th>
						<td><rxc:userLabel userId="${sysProperties.updateBy}"/></td>
						<th>更新时间</th>
						<td><fmt:formatDate value="${sysProperties.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
					</tr>
				</table>
	        
        </div>
        <rx:detailScript baseUrl="sys/core/sysProperties" 
        entityName="com.redxun.sys.core.entity.SysProperties"
        formId="form1"/>
        <script type="text/javascript">
        	addBody();
        </script>
        
    </body>
</html>