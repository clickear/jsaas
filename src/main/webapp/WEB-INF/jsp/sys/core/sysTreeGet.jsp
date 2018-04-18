<%-- 
    Document   : [SysTree]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun" %>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>[SysTree]明细</title>
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
                    	<caption>机构基本信息</caption>
                        																														<tr>
						 		<th>
						 			名称
						 										 				<span class="star">*</span>
						 										 				：
						 		</th>
	                            <td>    
	                                ${sysTree.name}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			路径
						 										 				：
						 		</th>
	                            <td>    
	                                ${sysTree.path}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			层次
						 										 				<span class="star">*</span>
						 										 				：
						 		</th>
	                            <td>    
	                                ${sysTree.depth}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			父节点
						 										 				：
						 		</th>
	                            <td>    
	                                ${sysTree.parentId}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			节点的分类Key
						 										 				<span class="star">*</span>
						 										 				：
						 		</th>
	                            <td>    
	                                ${sysTree.key}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			系统树分类key
						 										 				<span class="star">*</span>
						 										 				：
						 		</th>
	                            <td>    
	                                ${sysTree.catKey}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			序号
						 										 				<span class="star">*</span>
						 										 				：
						 		</th>
	                            <td>    
	                                ${sysTree.sn}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			
						 										 				：
						 		</th>
	                            <td>    
	                                ${sysTree.childs}
	                            </td>
						</tr>
																																																																																																																																																																																				<tr>
						 		<th>
						 			
						 										 				：
						 		</th>
	                            <td>    
	                                ${sysTree.childs}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			是否为子结点
						 										 				：
						 		</th>
	                            <td>    
	                                ${sysTree.isChild}
	                            </td>
						</tr>
																																				<tr>
						 		<th>
						 			数据项展示类型
						 										 				：
						 		</th>
	                            <td>    
	                                ${sysTree.dataShowType}
	                            </td>
						</tr>
																		                     </table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${sysTree.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${sysTree.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${sysTree.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${sysTree.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="sys/core/sysTree" formId="form1"/>
    </body>
</html>