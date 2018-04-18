<%-- 
    Document   : [BpmRemindDef]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmRemindDef]明细</title>
    <%@include file="/commons/get.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>[BpmRemindDef]基本信息</caption>
                        																														<tr>
						 		<th>
						 			方案ID：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.solId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			节点ID：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.nodeId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			名称：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.name}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			到期动作：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.action}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			相对节点：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.relNode}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			事件：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.event}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			日期类型：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.dateType}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			期限：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.expireDate}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			条件：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.condition}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			到期执行脚本：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.script}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			通知类型：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.notifyType}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			HTML模版：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.htmlTemplate}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			TEXT模版：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.textTemplate}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			开始发送消息时间点：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.timeToSend}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			发送次数：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.sendTimes}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			发送时间间隔：
						 		</th>
	                            <td>
	                                ${bpmRemindDef.sendInterval}
	                            </td>
						</tr>
																																																																																																																																										                     </table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${bpmRemindDef.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${bpmRemindDef.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${bpmRemindDef.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${bpmRemindDef.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="bpm/core/bpmRemindDef" 
        entityName="com.redxun.bpm.core.entity.BpmRemindDef"
        formId="form1"/>
    </body>
</html>