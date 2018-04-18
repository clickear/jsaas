<%-- 
    Document   : LDAP用户明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>LDAP用户明细</title>
		<%@include file="/commons/get.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer2">
             <div>
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>LDAP用户基本信息</caption>
                        																														<tr>
						 		<th>
						 			组织单元（主键）：
						 		</th>
	                            <td>
	                                ${sysLdapCn.sysLdapOuId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			用户ID：
						 		</th>
	                            <td>
	                                ${sysLdapCn.userId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			账户：
						 		</th>
	                            <td>
	                                ${sysLdapCn.userAccount}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			用户编号：
						 		</th>
	                            <td>
	                                ${sysLdapCn.userCode}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			名称：
						 		</th>
	                            <td>
	                                ${sysLdapCn.name}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			电话：
						 		</th>
	                            <td>
	                                ${sysLdapCn.tel}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			邮件：
						 		</th>
	                            <td>
	                                ${sysLdapCn.mail}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			USN_CREATED
            
            ：
						 		</th>
	                            <td>
	                                ${sysLdapCn.usnCreated}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			USN_CHANGED
            ：
						 		</th>
	                            <td>
	                                ${sysLdapCn.usnChanged}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			LDAP创建时间：
						 		</th>
	                            <td>
	                                ${sysLdapCn.whenCreated}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			LDAP更新时间
            ：
						 		</th>
	                            <td>
	                                ${sysLdapCn.whenChanged}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			状态：
						 		</th>
	                            <td>
	                                ${sysLdapCn.status}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			用户主要名称：
						 		</th>
	                            <td>
	                                ${sysLdapCn.userPrincipalName}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			区分名
            ：
						 		</th>
	                            <td>
	                                ${sysLdapCn.dn}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			对象类型
            
            ：
						 		</th>
	                            <td>
	                                ${sysLdapCn.oc}
	                            </td>
						</tr>
																																																																																																																																										                     </table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${sysLdapCn.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${sysLdapCn.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${sysLdapCn.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${sysLdapCn.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="sys/ldap/sysLdapCn" 
        entityName="com.redxun.sys.ldap.entity.SysLdapCn"
        formId="form1"/>
    </body>
</html>