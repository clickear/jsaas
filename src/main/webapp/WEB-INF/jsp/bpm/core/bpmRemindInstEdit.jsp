<%-- 
    Document   : [BpmRemindInst]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmRemindInst]编辑</title>
    <%@include file="/commons/edit.jsp"%>    
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${bpmRemindInst.id}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="id" class="mini-hidden" value="${bpmRemindInst.id}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[BpmRemindInst]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			方案ID
										 														 				：
										 		</th>
												<td>
																										<input name="solId" value="${bpmRemindInst.solId}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			节点ID
										 														 				：
										 		</th>
												<td>
																										<input name="nodeId" value="${bpmRemindInst.nodeId}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			任务ID
										 														 				：
										 		</th>
												<td>
																										<input name="taskId" value="${bpmRemindInst.taskId}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			名称
										 														 				：
										 		</th>
												<td>
																										<input name="name" value="${bpmRemindInst.name}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			到期动作
										 														 				：
										 		</th>
												<td>
																										<input name="action" value="${bpmRemindInst.action}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			期限
										 														 				：
										 		</th>
												<td>
																										<input name="expireDate" value="${bpmRemindInst.expireDate}" class="mini-textbox" vtype="maxLength:19" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			到期执行脚本
										 														 				：
										 		</th>
												<td>
																											 <textarea name="script" class="mini-textarea" vtype="maxLength:1000" style="width:90%"
														 														 >${bpmRemindInst.script}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			通知类型
										 														 				：
										 		</th>
												<td>
																										<input name="notifyType" value="${bpmRemindInst.notifyType}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			HTML模版
										 														 				：
										 		</th>
												<td>
																											 <textarea name="htmlTemplate" class="mini-textarea" vtype="maxLength:500" style="width:90%"
														 														 >${bpmRemindInst.htmlTemplate}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			TEXT模版
										 														 				：
										 		</th>
												<td>
																											 <textarea name="textTemplate" class="mini-textarea" vtype="maxLength:500" style="width:90%"
														 														 >${bpmRemindInst.textTemplate}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			开始发送消息时间点
										 														 				：
										 		</th>
												<td>
																										<input name="timeToSend" value="${bpmRemindInst.timeToSend}" class="mini-textbox" vtype="maxLength:19" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			发送次数
										 														 				：
										 		</th>
												<td>
																										<input name="sendTimes" value="${bpmRemindInst.sendTimes}" class="mini-textbox" vtype="maxLength:10" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			发送时间间隔
										 														 				：
										 		</th>
												<td>
																										<input name="sendInterval" value="${bpmRemindInst.sendInterval}" class="mini-textbox" vtype="maxLength:10" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			状态(2,完成,0,创建,1,进行中)
										 														 				：
										 		</th>
												<td>
																										<input name="status" value="${bpmRemindInst.status}" class="mini-textbox" vtype="maxLength:10" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="bpm/core/bpmRemindInst"
       entityName="com.redxun.bpm.core.entity.BpmRemindInst" />
    </body>
</html>