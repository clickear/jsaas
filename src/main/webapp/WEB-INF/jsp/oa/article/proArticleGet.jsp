
<%-- 
    Document   : [文章]明细页
    Created on : 2017-09-29 14:39:26
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[文章]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
       
		<div class="mini-toolbar">
    <a class="mini-button" iconCls="icon-edit" plain="true" onclick="openEdit()">编辑</a>
    <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="cancel()">关闭</a>
	</div>
		
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                	<caption>[文章]基本信息</caption>
					<%-- <tr>
						<th>所属项目ID：</th>
						<td>
							${proArticle.belongProId}
						</td>
					</tr> --%>
					<tr>
						<th>标题：</th>
						<td>
							${proArticle.title}
						</td>
						<th>作者：</th>
						<td>
							${proArticle.author}
						</td>
					</tr>
					<tr>
						<th>概要：</th>
						<td>
							${proArticle.outLine}
						</td>
						<th>序号：</th>
						<td>
							${proArticle.sn}
						</td>
					</tr>
					<tr>
						<th>创建人</th>
						<td><rxc:userLabel userId="${proArticle.createBy}" /></td>
						<th>创建时间</th>
						<td><fmt:formatDate value="${proArticle.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
					</tr>
					<tr>
						<th>更新人</th>
						<td><rxc:userLabel userId="${proArticle.updateBy}" /></td>
						<th>更新时间</th>
						<td><fmt:formatDate value="${proArticle.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
					</tr>
					<tr>
						<th>内容：</th>
						<td colspan="3">
							${proArticle.content}
						</td>
					</tr>
			</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="oa/article/proArticle" 
        entityName="com.redxun.oa.article.entity.ProArticle"
        formId="form1"/>
        
        <script type="text/javascript">
		mini.parse();
		var form = new mini.Form("#form1");
		
		function openEdit(){
			window.location.href='${ctxPath}/oa/article/proArticle/edit.do?pkId=${proArticle.id}&parentId=${proArticle.parentId}&itemId=${proArticle.belongProId}';  
		}
		
		function cancel(){
			window.parent.initIframe();
		}
		</script>
    </body>
</html>