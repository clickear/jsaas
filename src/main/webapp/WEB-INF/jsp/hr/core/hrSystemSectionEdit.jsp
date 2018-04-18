<%-- 
    Document   : [HrSystemSection]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>[HrSystemSection]编辑</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
        <%@include file="/commons/dynamic.jspf" %>
        <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
        <script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
        <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
        <link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${hrSystemSection.systemSectionId}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="systemSectionId" class="mini-hidden" value="${hrSystemSection.systemSectionId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[HrSystemSection]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			班次ID
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="sectionId" value="${hrSystemSection.sectionId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																													required="true" emptyText="请输入班次ID"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			班制ID
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="systemId" value="${hrSystemSection.systemId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																													required="true" emptyText="请输入班制ID"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			
										 														 				：
										 		</th>
												<td>
																										<input name="workday" value="${hrSystemSection.workday}" class="mini-textbox" vtype="maxLength:10" style="width:90%"
																												/>
																										
												</td>
											</tr>
																										                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="hr/core/hrSystemSection"
       entityName="com.redxun.hr.core.entity.HrSystemSection" />
    </body>
</html>