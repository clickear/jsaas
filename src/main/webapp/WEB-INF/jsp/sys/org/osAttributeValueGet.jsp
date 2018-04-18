
<%-- 
    Document   : [人员属性值]明细页
    Created on : 2017-12-14 14:09:43
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[人员属性值]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                	<caption>[人员属性值]基本信息</caption>
					<tr>
						<th>参数值：</th>
						<td>
							${osAttributeValue.value}
						</td>
					</tr>
					<tr>
						<th>目标ID：</th>
						<td>
							${osAttributeValue.targetId}
						</td>
					</tr>
					<tr>
						<th>机构ID：</th>
						<td>
							${osAttributeValue.tenantId}
						</td>
					</tr>
					<tr>
						<th>创建人ID：</th>
						<td>
							${osAttributeValue.createBy}
						</td>
					</tr>
					<tr>
						<th>创建时间：</th>
						<td>
							${osAttributeValue.createTime}
						</td>
					</tr>
					<tr>
						<th>更新人ID：</th>
						<td>
							${osAttributeValue.updateBy}
						</td>
					</tr>
					<tr>
						<th>更新时间：</th>
						<td>
							${osAttributeValue.updateTime}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="sys/org/osAttributeValue" 
        entityName="com.redxun.sys.org.entity.OsAttributeValue"
        formId="form1"/>
        
        <script type="text/javascript">
		mini.parse();
		var form = new mini.Form("#form1");
		var pkId = '${osAttributeValue.id}';
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/sys/org/osAttributeValue/getJson.do",
				data:{ids:pkId},
				success:function (json) {
					form.setData(json);
				}					
			});
		})
		</script>
    </body>
</html>