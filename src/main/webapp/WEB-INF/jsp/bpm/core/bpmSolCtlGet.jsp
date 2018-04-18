<%-- 
    Document   : [BpmSolCtl]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmSolCtl]明细</title>
     <%@include file="/commons/get.jsp"%>  
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>[BpmSolCtl]基本信息</caption>
                        																														<tr>
						 		<th>
						 			${column.remarks}：
						 		</th>
	                            <td>
	                                ${bpmSolCtl.solId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			${column.remarks}：
						 		</th>
	                            <td>
	                                ${bpmSolCtl.userIds}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			${column.remarks}：
						 		</th>
	                            <td>
	                                ${bpmSolCtl.groupIds}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			${column.remarks}：
						 		</th>
	                            <td>
	                                ${bpmSolCtl.allowStartor}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			${column.remarks}：
						 		</th>
	                            <td>
	                                ${bpmSolCtl.allowAttend}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			${column.remarks}：
						 		</th>
	                            <td>
	                                ${bpmSolCtl.right}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			${column.remarks}：
						 		</th>
	                            <td>
	                                ${bpmSolCtl.type}
	                            </td>
						</tr>
																																																																																																																																										                     </table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${bpmSolCtl.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${bpmSolCtl.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${bpmSolCtl.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${bpmSolCtl.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="bpm/core/bpmSolCtl" 
        entityName="com.redxun.bpm.core.entity.BpmSolCtl"
        formId="form1"/>
    </body>
</html>