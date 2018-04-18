<%-- 
    Document   : LDAP部门编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>LDAP部门编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${sysLdapOu.sysLdapOuId}" />
	<div id="p1" class="form-outer2">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="sysLdapOuId" class="mini-hidden"
					value="${sysLdapOu.sysLdapOuId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>LDAP部门基本信息</caption>

					<tr>
						<th>用户组ID ：</th>
						<td><input name="groupId" value="${sysLdapOu.groupId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>


					<tr>
						<th>状态 ：</th>
						<td><input name="status" value="${sysLdapOu.status}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>



					<tr>
						<th>组织单元名称 ：</th>
						<td><textarea name="name" class="mini-textarea"
								vtype="maxLength:512" style="width: 90%">${sysLdapOu.name}
														 </textarea></td>
					</tr>



					<tr>
						<th>USN_CREATED ：</th>
						<td><input name="usnCreated" value="${sysLdapOu.usnCreated}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>USN_CHANGED ：</th>
						<td><input name="usnChanged" value="${sysLdapOu.usnChanged}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>
					<!-- 


					<tr>
						<th>序号 ：</th>
						<td><input name="sn" value="${sysLdapOu.sn}"
							class="mini-textbox" vtype="maxLength:10" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>层次 ：</th>
						<td><input name="depth" value="${sysLdapOu.depth}"
							class="mini-textbox" vtype="maxLength:10" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>路径 ：</th>
						<td><textarea name="path" class="mini-textarea"
								vtype="maxLength:1024" style="width: 90%">${sysLdapOu.path}
														 </textarea></td>
					</tr>

					<tr>
						<th>父目录 ：</th>
						<td><input name="parentId" value="${sysLdapOu.parentId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>
					
					
<tr>
						<th>组织单元 ：</th>
						<td><textarea name="ou" class="mini-textarea"
								vtype="maxLength:512" style="width: 90%">${sysLdapOu.ou}
														 </textarea></td>
					</tr>
					
					
<tr>
						<th>区分名 ：</th>
						<td><textarea name="dn" class="mini-textarea"
								vtype="maxLength:512" style="width: 90%">${sysLdapOu.dn}
														 </textarea></td>
					</tr>

					<tr>
						<th>对象类型 ：</th>
						<td><textarea name="oc" class="mini-textarea"
								vtype="maxLength:512" style="width: 90%">${sysLdapOu.oc}
														 </textarea></td>
					</tr>
					
					<tr>
						<th>LDAP创建时间 ：</th>
						<td><input name="whenCreated"
							value="${sysLdapOu.whenCreated}" class="mini-textbox"
							vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>LDAP更新时间 ：</th>
						<td><input name="whenChanged"
							value="${sysLdapOu.whenChanged}" class="mini-textbox"
							vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>
					
					-->
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/ldap/sysLdapOu"
		entityName="com.redxun.sys.ldap.entity.SysLdapOu" />
</body>
</html>