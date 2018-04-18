
<%-- 
    Document   : [私有参数]明细页
    Created on : 2017-06-21 10:30:22
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[私有参数]明细</title>
        <%@include file="/commons/get.jsp" %>
        <style>body{margin-top:-6px;}</style>
    </head>
    <body>
    	<div class="heightBox"></div>
        <div id="form1" class="form-outer shadowBox">
             <div>
                    <table style="width:100%" class="table-detail column_1" cellpadding="0" cellspacing="1">
                    	<caption>[私有参数]基本信息</caption>
						<tr>
							<th>私  有  值</th>
							<td>
								${sysPrivateProperties.priValue}
							</td>
						</tr>
						<tr>
							<th>租用用户Id</th>
							<td>
								${sysPrivateProperties.tenantId}
							</td>
						</tr>
					</table>
                 </div>
	            <div>
					 <table class="table-detail column_2" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创  建  人</th>
							<td><rxc:userLabel userId="${sysPrivateProperties.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${sysPrivateProperties.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更  新  人</th>
							<td><rxc:userLabel userId="${sysPrivateProperties.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${sysPrivateProperties.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="sys/core/sysPrivateProperties" 
        entityName="com.redxun.sys.core.entity.SysPrivateProperties"
        formId="form1"/>
        
        <script type="text/javascript">
        	addBody();
        </script>
    </body>
</html>