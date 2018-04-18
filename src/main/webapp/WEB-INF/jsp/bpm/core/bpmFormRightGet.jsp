
<%-- 
    Document   : [表单权限]明细页
    Created on : 2018-02-09 15:54:25
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[表单权限]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                	<caption>[表单权限]基本信息</caption>
					<tr>
						<th>方案ID：</th>
						<td>
							${bpmFormRight.solId}
						</td>
					</tr>
					<tr>
						<th>流程定义ID：</th>
						<td>
							${bpmFormRight.actDefId}
						</td>
					</tr>
					<tr>
						<th>节点ID：</th>
						<td>
							${bpmFormRight.nodeId}
						</td>
					</tr>
					<tr>
						<th>表单别名：</th>
						<td>
							${bpmFormRight.formAlias}
						</td>
					</tr>
					<tr>
						<th>权限JSON：</th>
						<td>
							${bpmFormRight.json}
						</td>
					</tr>
					<tr>
						<th>租户ID：</th>
						<td>
							${bpmFormRight.tenantId}
						</td>
					</tr>
					<tr>
						<th>创建时间：</th>
						<td>
							${bpmFormRight.createTime}
						</td>
					</tr>
					<tr>
						<th>创建人：</th>
						<td>
							${bpmFormRight.createBy}
						</td>
					</tr>
					<tr>
						<th>更信人：</th>
						<td>
							${bpmFormRight.updateBy}
						</td>
					</tr>
					<tr>
						<th>更新时间：</th>
						<td>
							${bpmFormRight.updateTime}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="bpm/core/bpmFormRight" 
        entityName="com.redxun.bpm.core.entity.BpmFormRight"
        formId="form1"/>
        
        <script type="text/javascript">
		mini.parse();
		var form = new mini.Form("#form1");
		var pkId = '${bpmFormRight.id}';
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/bpm/core/bpmFormRight/getJson.do",
				data:{ids:pkId},
				success:function (json) {
					form.setData(json);
				}					
			});
		})
		</script>
    </body>
</html>