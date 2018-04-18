
<%-- 
    Document   : [布局权限设置]明细页
    Created on : 2017-08-28 15:58:17
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[布局权限设置]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                	<caption>[布局权限设置]基本信息</caption>
					<tr>
						<th>LAYOUT_ID_：</th>
						<td>
							${insPortalPermission.layoutId}
						</td>
					</tr>
					<tr>
						<th>TYPE_：</th>
						<td>
							${insPortalPermission.type}
						</td>
					</tr>
					<tr>
						<th>OWNER_ID_：</th>
						<td>
							${insPortalPermission.ownerId}
						</td>
					</tr>
					<tr>
						<th>租用机构ID：</th>
						<td>
							${insPortalPermission.tenantId}
						</td>
					</tr>
					<tr>
						<th>创建人ID：</th>
						<td>
							${insPortalPermission.createBy}
						</td>
					</tr>
					<tr>
						<th>创建时间：</th>
						<td>
							${insPortalPermission.createTime}
						</td>
					</tr>
					<tr>
						<th>更新人ID：</th>
						<td>
							${insPortalPermission.updateBy}
						</td>
					</tr>
					<tr>
						<th>更新时间：</th>
						<td>
							${insPortalPermission.updateTime}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="oa/info/insPortalPermission" 
        entityName="com.redxun.oa.info.entity.InsPortalPermission"
        formId="form1"/>
        
        <script type="text/javascript">
		mini.parse();
		var form = new mini.Form("#form1");
		var pkId = ${insPortalPermission.id};
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/oa/info/insPortalPermission/getJson.do",
				data:{ids:pkId},
				success:function (json) {
					form.setData(json);
				}					
			});
		})
		</script>
    </body>
</html>