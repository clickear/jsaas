<%-- 
    Document   : [KdDocDir]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>[KdDocDir]编辑</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
        <%@include file="/commons/dynamic.jspf" %>
        <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
        <script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
        <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
        <link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${kdDocDir.dirId}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="dirId" class="mini-hidden" value="${kdDocDir.dirId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[KdDocDir]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			文档ID
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="docId" value="${kdDocDir.docId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																													required="true" emptyText="请输入文档ID"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			标题等级
            1级标题
            2组标题
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="level" value="${kdDocDir.level}" class="mini-textbox" vtype="maxLength:20" style="width:90%"
																													required="true" emptyText="请输入标题等级
            1级标题
            2组标题"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			标题
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="subject" value="${kdDocDir.subject}" class="mini-textbox" vtype="maxLength:120" style="width:90%"
																													required="true" emptyText="请输入标题"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			标题连接锚点
										 														 				：
										 		</th>
												<td>
																										<input name="anchor" value="${kdDocDir.anchor}" class="mini-textbox" vtype="maxLength:255" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			上级目录ID
										 														 				：
										 		</th>
												<td>
																										<input name="parentId" value="${kdDocDir.parentId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="kms/core/kdDocDir"
       entityName="com.redxun.kms.core.entity.KdDocDir" />
    </body>
</html>