
<%-- 
    Document   : [office模板]明细页
    Created on : 2018-01-28 11:11:47
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[office模板]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                	<caption>[office模板]基本信息</caption>
					<tr>
						<th>名称：</th>
						<td>
							${sysOfficeTemplate.name}
						</td>
					</tr>
					<tr>
						<th>类型：</th>
						<td>
							<c:choose>
								<c:when test="${sysOfficeTemplate.type=='red'}">套红模板</c:when>
								<c:otherwise>普通模板</c:otherwise>
							</c:choose>
							
						</td>
					</tr>
					
					<tr>
						<th>文件名：</th>
						<td>
							<a  href="#" onclick="_openDoc('${sysOfficeTemplate.docId}');">${sysOfficeTemplate.docName}</a>
						</td>
					</tr>
					<tr>
						<th>描述：</th>
						<td>
							${sysOfficeTemplate.description}
						</td>
					</tr>
				
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="sys/core/sysOfficeTemplate" 
        entityName="com.redxun.sys.core.entity.SysOfficeTemplate"
        formId="form1"/>
        
        <script type="text/javascript">
		mini.parse();
		var form = new mini.Form("#form1");
		var pkId = '${sysOfficeTemplate.id}';
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/sys/core/sysOfficeTemplate/getJson.do",
				data:{ids:pkId},
				success:function (json) {
					form.setData(json);
				}					
			});
		})
		</script>
    </body>
</html>