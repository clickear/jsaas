<%-- 
    Document   : [BpmRemindHistory]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmRemindHistory]编辑</title>
     <%@include file="/commons/edit.jsp"%>   
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${bpmRemindHistory.id}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="id" class="mini-hidden" value="${bpmRemindHistory.id}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[BpmRemindHistory]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			催办实例ID
										 														 				：
										 		</th>
												<td>
																										<input name="reminderInstId" value="${bpmRemindHistory.reminderInstId}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			租用ID
										 														 				：
										 		</th>
												<td>
																										<input name="tanentId" value="${bpmRemindHistory.tanentId}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			催办类型
										 														 				：
										 		</th>
												<td>
																										<input name="remindType" value="${bpmRemindHistory.remindType}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																													                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="bpm/core/bpmRemindHistory"
       entityName="com.redxun.bpm.core.entity.BpmRemindHistory" />
    </body>
</html>