<%-- 
    Document   : LDAP日志编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>LDAP日志编辑</title>
		<%@include file="/commons/edit.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${sysLdapLog.logId}"/>
       <div id="p1" class="form-outer2">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="logId" class="mini-hidden" value="${sysLdapLog.logId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>LDAP日志基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			日志名称
										 														 				：
										 		</th>
												<td>
																											 <textarea name="logName" class="mini-textarea" vtype="maxLength:256" style="width:90%"
														 														 >${sysLdapLog.logName}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			日志内容
										 														 				：
										 		</th>
												<td>
																											 <textarea name="content" class="mini-textarea" vtype="maxLength:65535" style="width:90%"
														 														 >${sysLdapLog.content}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			开始时间
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="startTime" value="${sysLdapLog.startTime}" class="mini-textbox" vtype="maxLength:19" style="width:90%"
																													required="true" emptyText="请输入开始时间"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			结束时间
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="endTime" value="${sysLdapLog.endTime}" class="mini-textbox" vtype="maxLength:19" style="width:90%"
																													required="true" emptyText="请输入结束时间"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			持续时间
										 														 				：
										 		</th>
												<td>
																										<input name="runTime" value="${sysLdapLog.runTime}" class="mini-textbox" vtype="maxLength:10" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			状态
										 														 				：
										 		</th>
												<td>
																										<input name="status" value="${sysLdapLog.status}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			同步类型
										 														 				：
										 		</th>
												<td>
																										<input name="syncType" value="${sysLdapLog.syncType}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="sys/ldap/sysLdapLog"
       entityName="com.redxun.sys.ldap.entity.SysLdapLog" />
    </body>
</html>