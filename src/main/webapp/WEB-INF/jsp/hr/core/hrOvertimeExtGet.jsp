<%-- 
    Document   : [HrOvertimeExt]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[HrOvertimeExt]明细</title>
        <%@include file="/commons/get.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>[HrOvertimeExt]基本信息</caption>
                        																														<tr>
						 		<th>
						 			加班类型：
						 		</th>
	                            <td>
	                                ${hrOvertimeExt.type}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			标题：
						 		</th>
	                            <td>
	                                ${hrOvertimeExt.title}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			结算：
						 		</th>
	                            <td>
	                                ${hrOvertimeExt.pay}
	                            </td>
						</tr>
																																																																																																																																										                     </table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${hrOvertimeExt.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${hrOvertimeExt.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${hrOvertimeExt.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${hrOvertimeExt.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="hr/core/hrOvertimeExt" 
        entityName="com.redxun.hr.core.entity.HrOvertimeExt"
        formId="form1"/>
    </body>
</html>