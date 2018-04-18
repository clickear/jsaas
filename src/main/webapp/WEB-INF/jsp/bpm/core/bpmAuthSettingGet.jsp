<%-- 
    Document   : [BpmAuthSetting]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmAuthSetting]明细</title>
    <%@include file="/commons/get.jsp"%>
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>

<!-- 		<div style="height: 1px"></div> -->
        <div class="paddingTop">
        	<div id="form1" class="form-outer shadowBox">
	             <div>
	                    <table style="width:100%" class="table-detail column_1" cellpadding="0" cellspacing="1">
	                    	<caption>[BpmAuthSetting]基本信息</caption>
	                      	<tr>
							 		<th>
							 			授权名称
							 		</th>
		                            <td>
		                                ${bpmAuthSetting.name}
		                            </td>
							</tr>
						    <tr>
							 		<th>
							 			是否允许
							 		</th>
		                            <td>
		                                ${bpmAuthSetting.enable}
		                            </td>
							</tr>
			         </table>
	                 </div>
		            <div>
						 <table class="table-detail column_2" cellpadding="0" cellspacing="1">
						 	<caption>更新信息</caption>
							<tr>
								<th>创   建   人</th>
								<td><rxc:userLabel userId="${bpmAuthSetting.createBy}"/></td>
								<th>创建时间</th>
								<td><fmt:formatDate value="${bpmAuthSetting.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
							<tr>
								<th>更   新   人</th>
								<td><rxc:userLabel userId="${bpmAuthSetting.updateBy}"/></td>
								<th>更新时间</th>
								<td><fmt:formatDate value="${bpmAuthSetting.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
						</table>
		        	</div>
	        	</div>
        
        
        </div>
        <rx:detailScript baseUrl="bpm/core/bpmAuthSetting" 
        entityName="com.redxun.bpm.core.entity.BpmAuthSetting"
        formId="form1"/>
        <script type="text/javascript">
        	addBody();
        </script>
        
        
    </body>
</html>