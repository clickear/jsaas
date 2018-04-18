<%-- 
    Document   : [BpmInstCc]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmInstCc]编辑</title>
   <%@include file="/commons/edit.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${bpmInstCc.ccId}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="ccId" class="mini-hidden" value="${bpmInstCc.ccId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[BpmInstCc]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="instId" value="${bpmInstCc.instId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																													required="true" emptyText="请输入$column.remarks"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="subject" value="${bpmInstCc.subject}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																													required="true" emptyText="请输入$column.remarks"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="nodeId" value="${bpmInstCc.nodeId}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="nodeName" value="${bpmInstCc.nodeName}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="fromUserId" value="${bpmInstCc.fromUserId}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="bpm/core/bpmInstCc"
       entityName="com.redxun.bpm.core.entity.BpmInstCc" />
    </body>
</html>