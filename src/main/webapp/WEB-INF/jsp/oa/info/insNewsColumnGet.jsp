
<%-- 
    Document   : [公告栏目管理]明细页
    Created on : 2018-04-16 17:38:10
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[公告栏目管理]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                	<caption>[公告栏目管理]基本信息</caption>
					<tr>
						<th>栏目名称：</th>
						<td>
							${insNewsColumn.name}
						</td>
					</tr>
					<tr>
						<th>描述：</th>
						<td>
							${insNewsColumn.description}
						</td>
					</tr>
					<tr>
						<th>用户ID：</th>
						<td>
							${insNewsColumn.createBy}
						</td>
					</tr>
					<tr>
						<th>租户ID：</th>
						<td>
							${insNewsColumn.tenantId}
						</td>
					</tr>
					<tr>
						<th>创建时间：</th>
						<td>
							${insNewsColumn.createTime}
						</td>
					</tr>
					<tr>
						<th>更新时间：</th>
						<td>
							${insNewsColumn.updateTime}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="oa/info/insNewsColumn" 
        entityName="com.redxun.oa.info.entity.InsNewsColumn"
        formId="form1"/>
        
        <script type="text/javascript">
		mini.parse();
		var form = new mini.Form("#form1");
		var pkId = '${insNewsColumn.id}';
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/oa/info/insNewsColumn/getJson.do",
				data:{ids:pkId},
				success:function (json) {
					form.setData(json);
				}					
			});
		})
		</script>
    </body>
</html>