<%-- 
    Document   : [BpmMobileForm]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>手机表单明细</title>
        <%@include file="/commons/get.jsp" %>
        <style type="text/css">
        	.form-title{
        		background: transparent;
        		min-width: inherit;
        		box-shadow: none;
        		margin-bottom: 0;
        		padding: 4px 0;
        	}
        	
        	.Template{
        		padding: 0 !important;
        	}
        	
        	.Template .caption{
        		margin: 0;
        	}
        	
        	.Template .form-title{
        		text-indent: 8px;
        	}
        	
        </style>
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>
        <div class="paddingTop">
        	<div id="form1" class="form-outer shadowBox">
	             <div style="padding:5px;">
	                    <table   class="table-detail column_1" cellpadding="0" cellspacing="1">
	                    	<caption>手机表单明细基本信息</caption>
	
							<tr>
						 		<th>手机表单名称</th>
	                            <td>${bpmMobileForm.name}</td>
							</tr>
							<tr>
						 		<th>手机表单名称</th>
	                            <td>${bpmMobileForm.alias}</td>
							</tr>
							<tr>
						 		<th>手机表单对应的模版</th>
	                            <td class="Template">${bpmMobileForm.formHtml}</td>
							</tr>
	                      </table>
					
						 <table class="table-detail column_2" cellpadding="0" cellspacing="1">
						 	<caption>更新信息</caption>
							<tr>
								<th>创  建  人</th>
								<td><rxc:userLabel userId="${bpmMobileForm.createBy}"/></td>
								<th>创建时间</th>
								<td><fmt:formatDate value="${bpmMobileForm.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
							<tr>
								<th>更  新  人</th>
								<td><rxc:userLabel userId="${bpmMobileForm.updateBy}"/></td>
								<th>更新时间</th>
								<td><fmt:formatDate value="${bpmMobileForm.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
						</table>
	        	</div>
        </div>
        <rx:detailScript baseUrl="bpm/core/bpmMobileForm" 
        entityName="com.redxun.bpm.core.entity.BpmMobileForm"
        formId="form1"/>
        
        <script type="text/javascript">
        	addBody();
        </script>
    </body>
</html>