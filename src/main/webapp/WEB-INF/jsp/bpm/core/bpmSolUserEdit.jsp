<%-- 
    Document   : [BpmSolUser]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmSolUser]编辑</title>
      <%@include file="/commons/edit.jsp"%>  
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${bpmSolUser.id}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="id" class="mini-hidden" value="${bpmSolUser.id}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[BpmSolUser]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			业务流程解决方案ID
										 														 				：
										 		</th>
												<td>
																										<input name="solId" value="${bpmSolUser.solId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			节点ID
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="nodeId" value="${bpmSolUser.nodeId}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																													required="true" emptyText="请输入节点ID"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			节点名称
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="nodeName" value="${bpmSolUser.nodeName}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																													required="true" emptyText="请输入节点名称"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			用户类型
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="userType" value="${bpmSolUser.userType}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																													required="true" emptyText="请输入用户类型"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			节点配置
										 														 				：
										 		</th>
												<td>
																											 <textarea name="config"
								class="mini-textarea" vtype="maxLength:512" style="width: 90%">${bpmSolUser.config}
														 </textarea>

						</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			是否计算用户
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="isCal" value="${bpmSolUser.isCal}" class="mini-textbox" vtype="maxLength:20" style="width:90%"
																													required="true" emptyText="请输入是否计算用户"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			集合的人员运算
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="calLogic" value="${bpmSolUser.calLogic}" class="mini-textbox" vtype="maxLength:20" style="width:90%"
																													required="true" emptyText="请输入集合的人员运算"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			序号
										 														 				：
										 		</th>
												<td>
																										<input name="sn" value="${bpmSolUser.sn}" class="mini-textbox" vtype="maxLength:10" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" baseUrl="bpm/core/bpmSolUser" />
    </body>
</html>