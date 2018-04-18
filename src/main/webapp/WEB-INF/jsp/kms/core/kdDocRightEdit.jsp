<%-- 
    Document   : [KdDocRight]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>[KdDocRight]编辑</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
        <%@include file="/commons/dynamic.jspf" %>
        <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
        <script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
        <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
        <link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${kdDocRight.rightId}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="rightId" class="mini-hidden" value="${kdDocRight.rightId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[KdDocRight]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			文档ID
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="docId" value="${kdDocRight.docId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																													required="true" emptyText="请输入文档ID"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			用户ID
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="userId" value="${kdDocRight.userId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																													required="true" emptyText="请输入用户ID"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			组ID
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="groupId" value="${kdDocRight.groupId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																													required="true" emptyText="请输入组ID"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			READ=可读
            EDIT=可编辑
            PRINT=打印
            DOWN_FILE=可下载附件
										 														 				：
										 		</th>
												<td>
																										<input name="right" value="${kdDocRight.right}" class="mini-textbox" vtype="maxLength:60" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="kms/core/kdDocRight"
       entityName="com.redxun.kms.core.entity.KdDocRight" />
    </body>
</html>