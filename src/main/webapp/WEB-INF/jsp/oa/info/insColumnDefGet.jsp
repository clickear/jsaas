
<%-- 
    Document   : [ins_column_def]明细页
    Created on : 2017-08-16 11:39:47
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>自定义栏目明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>
		<div class="heightBox"></div>
        <div id="form1" class="form-outer shadowBox90">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
                	<caption>自定义栏目基本信息</caption>
					<tr>
						<th>栏  目  名</th>
						<td>
							${insColumnDef.name}
						</td>
					</tr>
					<tr>
						<th>Key</th>
						<td>
							${insColumnDef.key}
						</td>
					</tr>
					<tr>
						<th>方法或sql</th>
						<td>
							${insColumnDef.function}
						</td>
					</tr>
					<tr>
						<th>租用机构ID</th>
						<td>
							${insColumnDef.tenantId}
						</td>
					</tr>
					<tr>
						<th>创建人ID</th>
						<td>
							${insColumnDef.createBy}
						</td>
					</tr>
					<tr>
						<th>创建时间</th>
						<td>
							${insColumnDef.createTime}
						</td>
					</tr>
					<tr>
						<th>更新人ID</th>
						<td>
							${insColumnDef.updateBy}
						</td>
					</tr>
					<tr>
						<th>更新时间</th>
						<td>
							${insColumnDef.updateTime}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="oa/info/insColumnDef" 
        entityName="com.redxun.oa.info.entity.InsColumnDef"
        formId="form1"/>
        
        <script type="text/javascript">
	        addBody();
			mini.parse();
			var form = new mini.Form("#form1");
			var pkId = ${insColumnDef.colId};
			$(function(){
				$.ajax({
					type:'POST',
					url:"${ctxPath}/oa/info/insColumnDef/getJson.do",
					data:{ids:pkId},
					success:function (json) {
						form.setData(json);
					}					
				});
			})
		</script>
    </body>
</html>