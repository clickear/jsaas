<%-- 
    Document   : [BpmTask]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmTask]编辑</title>
       <%@include file="/commons/edit.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${bpmTask.id}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="id" class="mini-hidden" value="${bpmTask.id}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[BpmTask]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			修正号
										 														 				：
										 		</th>
												<td>
																										<input name="rev" value="${bpmTask.rev}" class="mini-textbox" vtype="maxLength:10" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			执行ID
										 														 				：
										 		</th>
												<td>
																										<input name="executionId" value="${bpmTask.executionId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			流程实例ID
										 														 				：
										 		</th>
												<td>
																										<input name="procInstId" value="${bpmTask.procInstId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			流程定义ID
										 														 				：
										 		</th>
												<td>
																										<input name="procDefId" value="${bpmTask.procDefId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			任务名称
										 														 				：
										 		</th>
												<td>
																										<input name="name" value="${bpmTask.name}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			父任务ID
										 														 				：
										 		</th>
												<td>
																										<input name="parentTaskId" value="${bpmTask.parentTaskId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			任务描述
										 														 				：
										 		</th>
												<td>
																											 <textarea name="description" class="mini-textarea" vtype="maxLength:4000" style="width:90%"
														 														 >${bpmTask.description}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			任务定义Key
										 														 				：
										 		</th>
												<td>
																										<input name="taskDefKey" value="${bpmTask.taskDefKey}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			任务所属人
										 														 				：
										 		</th>
												<td>
																										<input name="owner" value="${bpmTask.owner}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			任务执行人
										 														 				：
										 		</th>
												<td>
																										<input name="assignee" value="${bpmTask.assignee}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			代理状态
										 														 				：
										 		</th>
												<td>
																										<input name="delegation" value="${bpmTask.delegation}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			优先级
										 														 				：
										 		</th>
												<td>
																										<input name="priority" value="${bpmTask.priority}" class="mini-textbox" vtype="maxLength:10" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																																																																
																																	<tr>
												<th>
										 			到期时间
										 														 				：
										 		</th>
												<td>
																										<input name="dueDate" value="${bpmTask.dueDate}" class="mini-textbox" vtype="maxLength:19" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			分类
										 														 				：
										 		</th>
												<td>
																										<input name="category" value="${bpmTask.category}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			挂起状态
										 														 				：
										 		</th>
												<td>
																										<input name="suspensionState" value="${bpmTask.suspensionState}" class="mini-textbox" vtype="maxLength:10" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																																																																
																																	<tr>
												<th>
										 			表单Key
										 														 				：
										 		</th>
												<td>
																										<input name="formKey" value="${bpmTask.formKey}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																												/>
																										
												</td>
											</tr>
																										                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" baseUrl="bpm/core/bpmTask" />
    </body>
</html>