<%-- 
    Document   : [HrOvertimeExt]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[HrOvertimeExt]编辑</title>
       <%@include file="/commons/edit.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${hrOvertimeExt.otId}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="otId" class="mini-hidden" value="${hrOvertimeExt.otId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[HrOvertimeExt]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			加班类型
										 														 				：
										 		</th>
												<td>
																										<input name="type" value="${hrOvertimeExt.type}" class="mini-textbox" vtype="maxLength:20" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			标题
										 														 				：
										 		</th>
												<td>
																										<input name="title" value="${hrOvertimeExt.title}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			结算
										 														 				：
										 		</th>
												<td>
																										<input name="pay" value="${hrOvertimeExt.pay}" class="mini-textbox" vtype="maxLength:20" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="hr/core/hrOvertimeExt"
       entityName="com.redxun.hr.core.entity.HrOvertimeExt" />
    </body>
</html>