<%-- 
    Document   : LDAP用户编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>LDAP用户编辑</title>
		<%@include file="/commons/edit.jsp"%>
        <link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${sysLdapCn.sysLdapUserId}"/>
       <div id="p1" class="form-outer2">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="sysLdapUserId" class="mini-hidden" value="${sysLdapCn.sysLdapUserId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>LDAP用户基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			组织单元（主键）
										 														 				：
										 		</th>
												<td>
																										<input name="sysLdapOuId" value="${sysLdapCn.sysLdapOuId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			用户ID
										 														 				：
										 		</th>
												<td>
																										<input name="userId" value="${sysLdapCn.userId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			账户
										 														 				：
										 		</th>
												<td>
																										<input name="userAccount" value="${sysLdapCn.userAccount}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			用户编号
										 														 				：
										 		</th>
												<td>
																										<input name="userCode" value="${sysLdapCn.userCode}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			名称
										 														 				：
										 		</th>
												<td>
																										<input name="name" value="${sysLdapCn.name}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			电话
										 														 				：
										 		</th>
												<td>
																										<input name="tel" value="${sysLdapCn.tel}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			邮件
										 														 				：
										 		</th>
												<td>
																											 <textarea name="mail" class="mini-textarea" vtype="maxLength:512" style="width:90%"
														 														 >${sysLdapCn.mail}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			USN_CREATED
            
            
										 														 				：
										 		</th>
												<td>
																										<input name="usnCreated" value="${sysLdapCn.usnCreated}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			USN_CHANGED
            
										 														 				：
										 		</th>
												<td>
																										<input name="usnChanged" value="${sysLdapCn.usnChanged}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			LDAP创建时间
										 														 				：
										 		</th>
												<td>
																										<input name="whenCreated" value="${sysLdapCn.whenCreated}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			LDAP更新时间
            
										 														 				：
										 		</th>
												<td>
																										<input name="whenChanged" value="${sysLdapCn.whenChanged}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			状态
										 														 				：
										 		</th>
												<td>
																										<input name="status" value="${sysLdapCn.status}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			用户主要名称
										 														 				：
										 		</th>
												<td>
																											 <textarea name="userPrincipalName" class="mini-textarea" vtype="maxLength:512" style="width:90%"
														 														 >${sysLdapCn.userPrincipalName}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			区分名
            
										 														 				：
										 		</th>
												<td>
																											 <textarea name="dn" class="mini-textarea" vtype="maxLength:512" style="width:90%"
														 														 >${sysLdapCn.dn}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			对象类型
            
            
										 														 				：
										 		</th>
												<td>
																											 <textarea name="oc" class="mini-textarea" vtype="maxLength:512" style="width:90%"
														 														 >${sysLdapCn.oc}
														 </textarea>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="sys/ldap/sysLdapCn"
       entityName="com.redxun.sys.ldap.entity.SysLdapCn" />
    </body>
</html>