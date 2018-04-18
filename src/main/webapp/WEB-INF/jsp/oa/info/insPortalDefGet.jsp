
<%-- 
    Document   : [ins_portal_def]明细页
    Created on : 2017-08-15 16:07:14
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>自定义门户明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>
		<div class="heightBox"></div>
        <div id="form1" class="form-outer shadowBox">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail column_1" cellpadding="0" cellspacing="1">
                	<caption>自定义门户基本信息</caption>
					<tr>
						<th>门  户  名</th>
						<td>
							${insPortalDef.name}
						</td>
					</tr>
					<tr>
						<th>Key</th>
						<td>
							${insPortalDef.key}
						</td>
					</tr>
					<tr>
						<th>是否默认</th>
						<td>
							${insPortalDef.isDefault}
						</td>
					</tr>
					<tr>
						<th>优  先  级</th>
						<td>
							${insPortalDef.priority}
						</td>
					</tr>
					<tr>
						<th>租用机构ID</th>
						<td>
							${insPortalDef.tenantId}
						</td>
					</tr>
					<tr>
						<th>创建人ID</th>
						<td>
							${insPortalDef.createBy}
						</td>
					</tr>
					<tr>
						<th>创建时间</th>
						<td>
							${insPortalDef.createTime}
						</td>
					</tr>
					<tr>
						<th>更新人ID</th>
						<td>
							${insPortalDef.updateBy}
						</td>
					</tr>
					<tr>
						<th>更新时间</th>
						<td>
							${insPortalDef.updateTime}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="oa/info/insPortalDef" 
        entityName="com.redxun.oa.info.entity.InsPortalDef"
        formId="form1"/>
        
        <script type="text/javascript">
        addBody();
		mini.parse();
		var form = new mini.Form("#form1");
		var pkId = '${insPortalDef.portId}';
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/oa/info/insPortalDef/getJson.do",
				data:{ids:pkId},
				success:function (json) {
					form.setData(json);
				}					
			});
		})
		</script>
    </body>
</html>