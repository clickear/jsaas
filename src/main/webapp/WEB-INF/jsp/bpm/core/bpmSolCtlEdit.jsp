<%-- 
    Document   : [BpmSolCtl]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmSolCtl]编辑</title>
   <%@include file="/commons/edit.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${bpmSolCtl.rightId}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="rightId" class="mini-hidden" value="${bpmSolCtl.rightId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[BpmSolCtl]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="solId" value="${bpmSolCtl.solId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
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
																											 <textarea name="userIds" class="mini-textarea" vtype="maxLength:4000" style="width:90%"
														 															required="true" emptyText="请输入$column.remarks"
														 														 >${bpmSolCtl.userIds}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																											 <textarea name="groupIds" class="mini-textarea" vtype="maxLength:4000" style="width:90%"
														 														 >${bpmSolCtl.groupIds}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="allowStartor" value="${bpmSolCtl.allowStartor}" class="mini-textbox" vtype="maxLength:20" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="allowAttend" value="${bpmSolCtl.allowAttend}" class="mini-textbox" vtype="maxLength:20" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="right" value="${bpmSolCtl.right}" class="mini-textbox" vtype="maxLength:60" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="type" value="${bpmSolCtl.type}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="bpm/core/bpmSolCtl"
       entityName="com.redxun.bpm.core.entity.BpmSolCtl" />
    </body>
</html>