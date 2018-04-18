<%-- 
    Document   : [BpmSolUser]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmSolUser]明细</title>
    <%@include file="/commons/get.jsp"%>   
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>[BpmSolUser]基本信息</caption>
                        																														<tr>
						 		<th>
						 			业务流程解决方案ID：
						 		</th>
	                            <td>
	                                ${bpmSolUser.solId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			节点ID：
						 		</th>
	                            <td>
	                                ${bpmSolUser.nodeId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			节点名称：
						 		</th>
	                            <td>
	                                ${bpmSolUser.nodeName}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			用户类型：
						 		</th>
	                            <td>
	                                ${bpmSolUser.userType}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			节点配置：
						 		</th>
	                            <td>
	                                ${bpmSolUser.config}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			是否计算用户：
						 		</th>
	                            <td>
	                                ${bpmSolUser.isCal}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			集合的人员运算：
						 		</th>
	                            <td>
	                                ${bpmSolUser.calLogic}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			序号：
						 		</th>
	                            <td>
	                                ${bpmSolUser.sn}
	                            </td>
						</tr>
																																																																																																																																										                     </table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${bpmSolUser.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${bpmSolUser.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${bpmSolUser.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${bpmSolUser.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="bpm/core/bpmSolUser" formId="form1"/>
    </body>
</html>