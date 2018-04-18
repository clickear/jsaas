<%-- 
    Document   : [BpmSolTemplateRight]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmSolTemplateRight]编辑</title>
        <%@include file="/commons/edit.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${bpmSolTemplateRight.rightId}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="rightId" class="mini-hidden" value="${bpmSolTemplateRight.rightId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[BpmSolTemplateRight]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																										<input name="treeId" value="${bpmSolTemplateRight.treeId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																											 <textarea name="groupIds" class="mini-textarea" vtype="maxLength:512" style="width:90%"
														 														 >${bpmSolTemplateRight.groupIds}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			${column.remarks}
										 														 				：
										 		</th>
												<td>
																											 <textarea name="userIds" class="mini-textarea" vtype="maxLength:512" style="width:90%"
														 														 >${bpmSolTemplateRight.userIds}
														 </textarea>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="bpm/core/bpmSolTemplateRight"
       entityName="com.redxun.bpm.core.entity.BpmSolTemplateRight" />
    </body>
</html>