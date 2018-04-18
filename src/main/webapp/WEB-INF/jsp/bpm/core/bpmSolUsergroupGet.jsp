<%-- 
    Document   : [BpmSolUsergroup]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmSolUsergroup]明细</title>
     <%@include file="/commons/get.jsp"%>   
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>[BpmSolUsergroup]基本信息</caption>
                        																														<tr>
						 		<th>
						 			名称：
						 		</th>
	                            <td>
	                                ${bpmSolUsergroup.groupName}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			方案ID：
						 		</th>
	                            <td>
	                                ${bpmSolUsergroup.solId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			分组类型(flow,copyto)：
						 		</th>
	                            <td>
	                                ${bpmSolUsergroup.groupType}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			节点ID：
						 		</th>
	                            <td>
	                                ${bpmSolUsergroup.nodeId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			节点名称：
						 		</th>
	                            <td>
	                                ${bpmSolUsergroup.nodeName}
	                            </td>
						</tr>
																																																												<tr>
						 		<th>
						 			配置：
						 		</th>
	                            <td>
	                                ${bpmSolUsergroup.setting}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			序号：
						 		</th>
	                            <td>
	                                ${bpmSolUsergroup.sn}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			：
						 		</th>
	                            <td>
	                                ${bpmSolUsergroup.notifyType}
	                            </td>
						</tr>
																																																																																																																		                     </table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${bpmSolUsergroup.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${bpmSolUsergroup.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${bpmSolUsergroup.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${bpmSolUsergroup.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="bpm/core/bpmSolUsergroup" 
        entityName="com.redxun.bpm.core.entity.BpmSolUsergroup"
        formId="form1"/>
    </body>
</html>