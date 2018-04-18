
<%-- 
    Document   : [栏目消息盒子表]明细页
    Created on : 2017-09-01 11:35:24
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[栏目消息盒子表]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>
		<div class="heightBox"></div>
        <div id="form1" class="form-outer shadowBox90">
             <div>
             	<table style="width:100%" class="table-detail column_1" cellpadding="0" cellspacing="1">
                	<caption>[栏目消息盒子表]基本信息</caption>
					<tr>
						<th>COL_ID_</th>
						<td>
							${insMsgboxDef.colId}
						</td>
					</tr>
					<tr>
						<th>KEY_</th>
						<td>
							${insMsgboxDef.key}
						</td>
					</tr>
					<tr>
						<th>NAME_</th>
						<td>
							${insMsgboxDef.name}
						</td>
					</tr>
					<tr>
						<th>租用机构ID</th>
						<td>
							${insMsgboxDef.tenantId}
						</td>
					</tr>
					<tr>
						<th>创建人ID</th>
						<td>
							${insMsgboxDef.createBy}
						</td>
					</tr>
					<tr>
						<th>创建时间</th>
						<td>
							${insMsgboxDef.createTime}
						</td>
					</tr>
					<tr>
						<th>更新人ID</th>
						<td>
							${insMsgboxDef.updateBy}
						</td>
					</tr>
					<tr>
						<th>更新时间</th>
						<td>
							${insMsgboxDef.updateTime}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="oa/info/insMsgboxDef" 
        entityName="com.redxun.oa.info.entity.InsMsgboxDef"
        formId="form1"/>
        
        <script type="text/javascript">
	        addBody();
			mini.parse();
			var form = new mini.Form("#form1");
			var pkId = ${insMsgboxDef.boxId};
			$(function(){
				$.ajax({
					type:'POST',
					url:"${ctxPath}/oa/info/insMsgboxDef/getJson.do",
					data:{ids:pkId},
					success:function (json) {
						form.setData(json);
					}					
				});
			})
		</script>
    </body>
</html>