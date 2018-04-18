<%-- 
    Document   : [BpmLog]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmLog]编辑</title>
   <%@include file="/commons/edit.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${bpmLog.logId}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="logId" class="mini-hidden" value="${bpmLog.logId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[BpmLog]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			解决方案ID
										 														 				：
										 		</th>
												<td>
																										<input name="solId" value="${bpmLog.solId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			流程实例ID
										 														 				：
										 		</th>
												<td>
																										<input name="instId" value="${bpmLog.instId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			流程任务ID
										 														 				：
										 		</th>
												<td>
																										<input name="taskId" value="${bpmLog.taskId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			日志分类
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="logType" value="${bpmLog.logType}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																													required="true" emptyText="请输入日志分类"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			操作类型
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="opType" value="${bpmLog.opType}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																													required="true" emptyText="请输入操作类型"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			操作内容
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																											 <textarea name="opContent" class="mini-textarea" vtype="maxLength:512" style="width:90%"
														 															required="true" emptyText="请输入操作内容"
														 														 >${bpmLog.opContent}
														 </textarea>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" baseUrl="bpm/core/bpmLog" />
    </body>
</html>