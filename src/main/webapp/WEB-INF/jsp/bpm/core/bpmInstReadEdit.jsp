<%-- 
    Document   : [BpmInstRead]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmInstRead]编辑</title>
   <%@include file="/commons/edit.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${bpmInstRead.readId}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="readId" class="mini-hidden" value="${bpmInstRead.readId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[BpmInstRead]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="instId" value="${bpmInstRead.instId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="userId" value="${bpmInstRead.userId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="state" value="${bpmInstRead.state}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="depId" value="${bpmInstRead.depId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="bpm/core/bpmInstRead"
       entityName="com.redxun.bpm.core.entity.BpmInstRead" />
    </body>
</html>