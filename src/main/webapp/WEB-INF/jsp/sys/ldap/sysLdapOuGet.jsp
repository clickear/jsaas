<%-- 
    Document   : LDAP部门明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>LDAP部门明细</title>
		<%@include file="/commons/list.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer2">
             <div>
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>LDAP部门基本信息</caption>
                        	 <tr>
						 		<th>
						 			用户组ID：
						 		</th>
	                            <td>
	                                ${sysLdapOu.groupId}
	                            </td>
						    </tr>
							<tr>
						 		<th>
						 			序号：
						 		</th>
	                            <td>
	                                ${sysLdapOu.sn}
	                            </td>
						</tr>
						<tr>
						 		<th>
						 			层次：
						 		</th>
	                            <td>
	                                ${sysLdapOu.depth}
	                            </td>
						</tr>
						<tr>
						 		<th>
						 			路径：
						 		</th>
	                            <td>
	                                ${sysLdapOu.path}
	                            </td>
						</tr>
						<tr>
						 		<th>
						 			父目录：
						 		</th>
	                            <td>
	                                ${sysLdapOu.parentId}
	                            </td>
						</tr>
						<tr>
						 		<th>
						 			状态：
						 		</th>
	                            <td>
	                                ${sysLdapOu.status}
	                            </td>
						</tr>
						<tr>
						 		<th>
						 			组织单元：
						 		</th>
	                            <td>
	                                ${sysLdapOu.ou}
	                            </td>
						</tr>
						<tr>
						 		<th>
						 			组织单元名称：
						 		</th>
	                            <td>
	                                ${sysLdapOu.name}
	                            </td>
						</tr>
						<tr>
						 		<th>
						 			区分名：
						 		</th>
	                            <td>
	                                ${sysLdapOu.dn}
	                            </td>
						</tr>
						<tr>
						 		<th>
						 			对象类型 ：
						 		</th>
	                            <td>
	                                ${sysLdapOu.oc}
	                            </td>
						</tr>
						<tr>
						 		<th>
						 			USN_CREATED：
						 		</th>
	                            <td>
	                                ${sysLdapOu.usnCreated}
	                            </td>
						</tr>
						<tr>
						 		<th>
						 			USN_CHANGED：
						 		</th>
	                            <td>
	                                ${sysLdapOu.usnChanged}
	                            </td>
						</tr>
						<tr>
						 		<th>
						 			LDAP创建时间 ：
						 		</th>
	                            <td>
	                                ${sysLdapOu.whenCreated}
	                            </td>
						</tr>
						<tr>
						 		<th>
						 			LDAP更新时间 ：
						 		</th>
	                            <td>
	                                ${sysLdapOu.whenChanged}
	                            </td>
						</tr>
																																																																																																																																										                     </table>
                 </div>
	            <div>
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${sysLdapOu.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${sysLdapOu.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${sysLdapOu.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${sysLdapOu.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="sys/ldap/sysLdapOu" 
        entityName="com.redxun.sys.ldap.entity.SysLdapOu"
        formId="form1"/>
    </body>
</html>