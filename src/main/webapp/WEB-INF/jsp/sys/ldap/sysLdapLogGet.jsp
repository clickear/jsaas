<%-- 
    Document   : LDAP日志明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>LDAP日志明细</title>
		<%@include file="/commons/get.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer2">
             <div>
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>LDAP日志基本信息</caption>
                        																														<tr>
						 		<th>
						 			日志名称：
						 		</th>
	                            <td>
	                                ${sysLdapLog.logName}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			日志内容：
						 		</th>
	                            <td>
	                                ${sysLdapLog.content}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			开始时间：
						 		</th>
	                            <td>
	                                ${sysLdapLog.startTime}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			结束时间：
						 		</th>
	                            <td>
	                                ${sysLdapLog.endTime}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			持续时间：
						 		</th>
	                            <td>
	                                ${sysLdapLog.runTime}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			状态：
						 		</th>
	                            <td>
	                                ${sysLdapLog.status}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			同步类型：
						 		</th>
	                            <td>
	                                ${sysLdapLog.syncType}
	                            </td>
						</tr>
																																																																																																																																										                     </table>
                 </div>
	            <div>
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${sysLdapLog.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${sysLdapLog.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${sysLdapLog.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${sysLdapLog.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="sys/ldap/sysLdapLog" 
        entityName="com.redxun.sys.ldap.entity.SysLdapLog"
        formId="form1"/>
    </body>
</html>