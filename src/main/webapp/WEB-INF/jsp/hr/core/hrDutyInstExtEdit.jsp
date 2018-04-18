<%-- 
    Document   : [HrDutyInstExt]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[HrDutyInstExt]编辑</title>
       <%@include file="/commons/edit.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${hrDutyInstExt.extId}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="extId" class="mini-hidden" value="${hrDutyInstExt.extId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[HrDutyInstExt]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			开始签到
										 														 				：
										 		</th>
												<td>
																										<input name="startSignIn" value="${hrDutyInstExt.startSignIn}" class="mini-textbox" vtype="maxLength:19" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			上班时间
										 														 				：
										 		</th>
												<td>
																										<input name="dutyStartTime" value="${hrDutyInstExt.dutyStartTime}" class="mini-textbox" vtype="maxLength:20" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			签到结束时间
										 														 				：
										 		</th>
												<td>
																										<input name="endSignIn" value="${hrDutyInstExt.endSignIn}" class="mini-textbox" vtype="maxLength:19" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			早退计时
										 														 				：
										 		</th>
												<td>
																										<input name="earlyOffTime" value="${hrDutyInstExt.earlyOffTime}" class="mini-textbox" vtype="maxLength:19" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			下班时间
										 														 				：
										 		</th>
												<td>
																										<input name="dutyEndTime" value="${hrDutyInstExt.dutyEndTime}" class="mini-textbox" vtype="maxLength:20" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			签退结束
										 														 				：
										 		</th>
												<td>
																										<input name="signOutTime" value="${hrDutyInstExt.signOutTime}" class="mini-textbox" vtype="maxLength:19" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="hr/core/hrDutyInstExt"
       entityName="com.redxun.hr.core.entity.HrDutyInstExt" />
    </body>
</html>