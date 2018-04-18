<%-- 
    Document   : [KdDocTemplate]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>[KdDocTemplate]编辑</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
        <%@include file="/commons/dynamic.jspf" %>
        <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
        <script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
        <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
        <link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${kdDocTemplate.tempId}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="tempId" class="mini-hidden" value="${kdDocTemplate.tempId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[KdDocTemplate]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			模板分类ID
										 														 				：
										 		</th>
												<td>
																										<input name="treeId" value="${kdDocTemplate.treeId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			模板名称
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="name" value="${kdDocTemplate.name}" class="mini-textbox" vtype="maxLength:80" style="width:90%"
																													required="true" emptyText="请输入模板名称"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			模板内容
										 														 				：
										 		</th>
												<td>
																											 <textarea name="content" class="mini-textarea" vtype="maxLength:65535" style="width:90%"
														 														 >${kdDocTemplate.content}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			模板类型 词条模板 文档模板
										 														 				：
										 		</th>
												<td>
																										<input name="type" value="${kdDocTemplate.type}" class="mini-textbox" vtype="maxLength:20" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			模板状态
										 														 				：
										 		</th>
												<td>
																										<input name="status" value="${kdDocTemplate.status}" class="mini-textbox" vtype="maxLength:20" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="kms/core/kdDocTemplate"
       entityName="com.redxun.kms.core.entity.KdDocTemplate" />
    </body>
</html>