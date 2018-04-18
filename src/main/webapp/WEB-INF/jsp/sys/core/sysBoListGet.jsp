
<%-- 
    Document   : [单据数据列表]明细页
    Created on : 2017-05-21 12:11:18
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[单据数据列表]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>[单据数据列表]基本信息</caption>
						<tr>
							<th>解决方案ID：</th>
							<td>
								${sysBoList.solId}
							</td>
						</tr>
						<tr>
							<th>名称：</th>
							<td>
								${sysBoList.name}
							</td>
						</tr>
						<tr>
							<th>标识键：</th>
							<td>
								${sysBoList.key}
							</td>
						</tr>
						<tr>
							<th>描述：</th>
							<td>
								${sysBoList.descp}
							</td>
						</tr>
						<tr>
							<th>是否显示左树：</th>
							<td>
								${sysBoList.isLeftTree}
							</td>
						</tr>
						
						<tr>
							<th>左树字段映射：</th>
							<td>
								${sysBoList.leftTreeJson}
							</td>
						</tr>
						<tr>
							<th>SQL语句：</th>
							<td>
								${sysBoList.sql}
							</td>
						</tr>
					
						<tr>
							<th>列的JSON：</th>
							<td>
								${sysBoList.colsJson}
							</td>
						</tr>
						<tr>
							<th>列表显示模板：</th>
							<td>
								${sysBoList.listHtml}
							</td>
						</tr>
						<tr>
							<th>搜索条件HTML：</th>
							<td>
								${sysBoList.searchHtml}
							</td>
						</tr>
						<tr>
							<th>绑定流程方案：</th>
							<td>
								${sysBoList.bpmSolId}
							</td>
						</tr>
						<tr>
							<th>绑定表单方案：</th>
							<td>
								${sysBoList.formSolId}
							</td>
						</tr>
						<tr>
							<th>头部按钮配置：</th>
							<td>
								${sysBoList.topBtnsJson}
							</td>
						</tr>
						<tr>
							<th>管理列按钮配置：</th>
							<td>
								${sysBoList.mgrBtnsJson}
							</td>
						</tr>
						<tr>
							<th>是否对话框：</th>
							<td>
								${sysBoList.isDialog}
							</td>
						</tr>
						<tr>
							<th>返回映射：</th>
							<td>
								${sysBoList.retrunDataMap}
							</td>
						</tr>
						<tr>
							<th>是否分页：</th>
							<td>
								${sysBoList.isPage}
							</td>
						</tr>
						<tr>
							<th>是否允许导出：</th>
							<td>
								${sysBoList.isExport}
							</td>
						</tr>
						<tr>
							<th>租户ID：</th>
							<td>
								${sysBoList.tenantId}
							</td>
						</tr>
						<tr>
							<th>创建人：</th>
							<td>
								${sysBoList.createBy}
							</td>
						</tr>
						<tr>
							<th>创建时间：</th>
							<td>
								${sysBoList.createTime}
							</td>
						</tr>
						<tr>
							<th>更新人：</th>
							<td>
								${sysBoList.updateBy}
							</td>
						</tr>
						<tr>
							<th>更新时间：</th>
							<td>
								${sysBoList.updateTime}
							</td>
						</tr>
					</table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${sysBoList.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${sysBoList.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${sysBoList.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${sysBoList.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="sys/core/sysBoList" 
        entityName="com.redxun.sys.core.entity.SysBoList"
        formId="form1"/>
    </body>
</html>