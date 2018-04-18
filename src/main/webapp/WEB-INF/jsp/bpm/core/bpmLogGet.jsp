<%-- 
    Document   : [BpmLog]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmLog]明细</title>
    <%@include file="/commons/get.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>[BpmLog]基本信息</caption>
                        																														<tr>
						 		<th>
						 			解决方案ID：
						 		</th>
	                            <td>
	                                ${bpmLog.solId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			流程实例ID：
						 		</th>
	                            <td>
	                                ${bpmLog.instId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			流程任务ID：
						 		</th>
	                            <td>
	                                ${bpmLog.taskId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			日志分类：
						 		</th>
	                            <td>
	                                ${bpmLog.logType}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			操作类型：
						 		</th>
	                            <td>
	                                ${bpmLog.opType}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			操作内容：
						 		</th>
	                            <td>
	                                ${bpmLog.opContent}
	                            </td>
						</tr>
																																																																																																																																										                     </table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${bpmLog.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${bpmLog.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${bpmLog.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${bpmLog.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="bpm/core/bpmLog" formId="form1"/>
    </body>
</html>