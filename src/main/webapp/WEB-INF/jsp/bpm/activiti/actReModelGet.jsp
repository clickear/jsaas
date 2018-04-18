<%-- 
    Document   : [ActReModel]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun" %>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>[ActReModel]明细</title>
        <%@include file="/commons/dynamic.jspf" %>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
        <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
        <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
        <script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
        <link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>[ActReModel]基本信息</caption>
                        																														<tr>
						 		<th>
						 			修正版本：
						 		</th>
	                            <td>
	                                ${actReModel.rev}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			名称：
						 		</th>
	                            <td>
	                                ${actReModel.name}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			标识键：
						 		</th>
	                            <td>
	                                ${actReModel.key}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			分类：
						 		</th>
	                            <td>
	                                ${actReModel.category}
	                            </td>
						</tr>
																																																																																				<tr>
						 		<th>
						 			版本：
						 		</th>
	                            <td>
	                                ${actReModel.version}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			元数据：
						 		</th>
	                            <td>
	                                ${actReModel.metaInfo}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			发布ID：
						 		</th>
	                            <td>
	                                ${actReModel.deploymentId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			编辑器源资源ID：
						 		</th>
	                            <td>
	                                ${actReModel.editorSourceValueId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			编辑器附加资源ID：
						 		</th>
	                            <td>
	                                ${actReModel.editorSourceExtraValueId}
	                            </td>
						</tr>
																																										                     </table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${actReModel.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${actReModel.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${actReModel.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${actReModel.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="bpm/activiti/actReModel" formId="form1"/>
    </body>
</html>