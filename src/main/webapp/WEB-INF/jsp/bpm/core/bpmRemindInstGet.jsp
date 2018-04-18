<%-- 
    Document   : [BpmRemindInst]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmRemindInst]明细</title>
     <%@include file="/commons/get.jsp"%> 
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>[BpmRemindInst]基本信息</caption>
                        																														<tr>
						 		<th>
						 			方案ID：
						 		</th>
	                            <td>
	                                ${bpmRemindInst.solId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			节点ID：
						 		</th>
	                            <td>
	                                ${bpmRemindInst.nodeId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			任务ID：
						 		</th>
	                            <td>
	                                ${bpmRemindInst.taskId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			名称：
						 		</th>
	                            <td>
	                                ${bpmRemindInst.name}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			到期动作：
						 		</th>
	                            <td>
	                                ${bpmRemindInst.action}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			期限：
						 		</th>
	                            <td>
	                                ${bpmRemindInst.expireDate}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			到期执行脚本：
						 		</th>
	                            <td>
	                                ${bpmRemindInst.script}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			通知类型：
						 		</th>
	                            <td>
	                                ${bpmRemindInst.notifyType}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			HTML模版：
						 		</th>
	                            <td>
	                                ${bpmRemindInst.htmlTemplate}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			TEXT模版：
						 		</th>
	                            <td>
	                                ${bpmRemindInst.textTemplate}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			开始发送消息时间点：
						 		</th>
	                            <td>
	                                ${bpmRemindInst.timeToSend}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			发送次数：
						 		</th>
	                            <td>
	                                ${bpmRemindInst.sendTimes}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			发送时间间隔：
						 		</th>
	                            <td>
	                                ${bpmRemindInst.sendInterval}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			状态(2,完成,0,创建,1,进行中)：
						 		</th>
	                            <td>
	                                ${bpmRemindInst.status}
	                            </td>
						</tr>
																																																																																																																																										                     </table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${bpmRemindInst.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${bpmRemindInst.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${bpmRemindInst.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${bpmRemindInst.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="bpm/core/bpmRemindInst" 
        entityName="com.redxun.bpm.core.entity.BpmRemindInst"
        formId="form1"/>
    </body>
</html>