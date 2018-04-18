<%-- 
    Document   : [BpmInstCp]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmInstCp]编辑</title>
  <%@include file="/commons/edit.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${bpmInstCp.id}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="id" class="mini-hidden" value="${bpmInstCp.id}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[BpmInstCp]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="userId" value="${bpmInstCp.userId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="groupId" value="${bpmInstCp.groupId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="ccId" value="${bpmInstCp.ccId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																																																																																																																																																																																																												
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="isRead" value="${bpmInstCp.isRead}" class="mini-textbox" vtype="maxLength:20" style="width:90%"
																												/>
																										
												</td>
											</tr>
																										                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="bpm/core/bpmInstCp"
       entityName="com.redxun.bpm.core.entity.BpmInstCp" />
    </body>
</html>