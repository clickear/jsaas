<%-- 
    Document   : [KdDocHis]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>[KdDocHis]编辑</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
        <%@include file="/commons/dynamic.jspf" %>
        <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
        <script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
        <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
        <link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${kdDocHis.hisId}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="hisId" class="mini-hidden" value="${kdDocHis.hisId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[KdDocHis]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			文档ID
										 														 				：
										 		</th>
												<td>
																										<input name="docId" value="${kdDocHis.docId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			版本号
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="version" value="${kdDocHis.version}" class="mini-textbox" vtype="maxLength:10" style="width:90%"
																													required="true" emptyText="请输入版本号"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			文档内容
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																											 <textarea name="content" class="mini-textarea" vtype="maxLength:2147483647" style="width:90%"
														 															required="true" emptyText="请输入文档内容"
														 														 >${kdDocHis.content}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			文档作者
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="author" value="${kdDocHis.author}" class="mini-textbox" vtype="maxLength:50" style="width:90%"
																													required="true" emptyText="请输入文档作者"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			文档封面
										 														 				：
										 		</th>
												<td>
																										<input name="coverFileId" value="${kdDocHis.coverFileId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			文档附件IDS
										 														 				：
										 		</th>
												<td>
																											 <textarea name="attachIds" class="mini-textarea" vtype="maxLength:512" style="width:90%"
														 														 >${kdDocHis.attachIds}
														 </textarea>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="kms/core/kdDocHis"
       entityName="com.redxun.kms.core.entity.KdDocHis" />
    </body>
</html>