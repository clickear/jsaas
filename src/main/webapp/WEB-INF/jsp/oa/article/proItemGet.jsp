
<%-- 
    Document   : [项目]明细页
    Created on : 2017-09-29 14:38:27
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[项目]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>
		<div class="heightBox"></div>
        <div id="form1" class="form-outer shadowBox90">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
                	<caption>[项目]基本信息</caption>
					<tr>
						<th>项  目  名</th>
						<td>
							${proItem.name}
						</td>
					</tr>
					<tr>
						<th>描　　述</th>
						<td>
							${proItem.desc}
						</td>
					</tr>
					<tr>
						<th>版　　本</th>
						<td>
							${proItem.version}
						</td>
					</tr>
					<tr>
						<th>文档生成路径</th>
						<td>
							${proItem.genSrc}
						</td>
					</tr>
					<tr>
						<th>租用机构ID</th>
						<td>
							${proItem.tenantId}
						</td>
					</tr>
					<tr>
						<th>更新时间</th>
						<td>
							${proItem.updateTime}
						</td>
					</tr>
					<tr>
						<th>更新人ID</th>
						<td>
							${proItem.updateBy}
						</td>
					</tr>
					<tr>
						<th>创建时间</th>
						<td>
							${proItem.createTime}
						</td>
					</tr>
					<tr>
						<th>创建人ID</th>
						<td>
							${proItem.createBy}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="oa/article/proItem" 
        entityName="com.redxun.oa.article.entity.ProItem"
        formId="form1"/>
        
        <script type="text/javascript">
        addBody();
		mini.parse();
		var form = new mini.Form("#form1");
		var pkId = ${proItem.ID};
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/oa/article/proItem/getJson.do",
				data:{ids:pkId},
				success:function (json) {
					form.setData(json);
				}					
			});
		})
		</script>
    </body>
</html>