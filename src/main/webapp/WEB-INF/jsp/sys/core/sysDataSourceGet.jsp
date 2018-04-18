
<%-- 
    Document   : [数据源定义管理]明细页
    Created on : 2017-02-07 09:03:54
    Author     : ray
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[数据源定义管理]明细</title>
        <%@include file="/commons/get.jsp" %>
        <style>.form-title li{width: 25%;}</style>
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>
		<div class="form-title">
			<h1>基本信息</h1>
			<ul>
				<li>
					<h2>创  建  人：<rxc:userLabel userId="${sysDataSource.createBy}"/></h2>
				</li>
				<li>
					<h2>更  新  人：<fmt:formatDate value="${sysDataSource.createTime}" pattern="yyyy-MM-dd HH:mm" /></h2>
				</li>
				<li>
					<h2>创建时间：<rxc:userLabel userId="${sysDataSource.updateBy}"/></h2>
				</li>
				<li>
					<h2>更新时间：<fmt:formatDate value="${sysDataSource.updateTime}" pattern="yyyy-MM-dd HH:mm" /></h2>
				</li>
			</ul>
		</div>
        <div id="form1" class="form-outer shadowBox">
                   <table style="width:100%" class="table-detail column_1" cellpadding="0" cellspacing="1">
                   	<caption>[数据源定义管理]基本信息</caption>
					<tr>
						<th>数据源名称</th>
						<td>${sysDataSource.name}</td>
					</tr>
					<tr>
						<th>别　　名</th>
						<td>${sysDataSource.alias}</td>
					</tr>
					<tr>
						<th>是否使用</th>
						<td>${sysDataSource.enabled}</td>
					</tr>
					<tr>
						<th>数据源设定</th>
						<td>${sysDataSource.setting}</td>
					</tr>
					<tr>
						<th>数据库类型</th>
						<td>${sysDataSource.dbType}</td>
					</tr>
					<tr>
						<th>启动时初始化</th>
						<td>${sysDataSource.initOnStart}</td>
					</tr>
					<tr>
						<th>创建时间</th>
						<td>${sysDataSource.createTime}</td>
					</tr>
					<tr>
						<th>创  建  人</th>
						<td>${sysDataSource.createBy}</td>
					</tr>
					<tr>
						<th>更  新  人</th>
						<td>${sysDataSource.updateBy}</td>
					</tr>
					<tr>
						<th>更新时间</th>
						<td>${sysDataSource.updateTime}</td>
					</tr>
				</table>
        </div>
        <rx:detailScript baseUrl="sys/core/sysDataSource" 
        entityName="com.redxun.sys.core.entity.SysDataSource"
        formId="form1"/>
        
        <script type="text/javascript">
        	addBody();
        </script>
    </body>
</html>