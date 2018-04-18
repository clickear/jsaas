<%-- 
    Document   : [BpmInstCc]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmInstCc]明细</title>
       <%@include file="/commons/get.jsp"%> 
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>[BpmInstCc]基本信息</caption>
                        																														<tr>
						 		<th>
						 			${column.remarks}：
						 		</th>
	                            <td>
	                                ${bpmInstCc.instId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			${column.remarks}：
						 		</th>
	                            <td>
	                                ${bpmInstCc.subject}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			${column.remarks}：
						 		</th>
	                            <td>
	                                ${bpmInstCc.nodeId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			${column.remarks}：
						 		</th>
	                            <td>
	                                ${bpmInstCc.nodeName}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			${column.remarks}：
						 		</th>
	                            <td>
	                                ${bpmInstCc.fromUserId}
	                            </td>
						</tr>
																																																																																																																																										                     </table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${bpmInstCc.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${bpmInstCc.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${bpmInstCc.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${bpmInstCc.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="bpm/core/bpmInstCc" 
        entityName="com.redxun.bpm.core.entity.BpmInstCc"
        formId="form1"/>
    </body>
</html>