<%-- 
    Document   : [BpmSolFv]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmSolFv]编辑</title>
       <%@include file="/commons/edit.jsp"%> 
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${bpmSolFv.id}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="id" class="mini-hidden" value="${bpmSolFv.id}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[BpmSolFv]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			解决方案ID
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="solId" value="${bpmSolFv.solId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																													required="true" emptyText="请输入解决方案ID"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			节点ID
										 														 				：
										 		</th>
												<td>
																										<input name="nodeId" value="${bpmSolFv.nodeId}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			节点名称
										 														 				：
										 		</th>
												<td>
																										<input name="nodeText" value="${bpmSolFv.nodeText}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			表单类型
										 														 				：
										 		</th>
												<td>
																										<input name="formType" value="${bpmSolFv.formType}" class="mini-textbox" vtype="maxLength:30" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			表单地址
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="formUri" value="${bpmSolFv.formUri}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																													required="true" emptyText="请输入表单地址"
																												/>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" baseUrl="bpm/core/bpmSolFv" />
    </body>
</html>