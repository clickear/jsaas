
<%-- 
    Document   : [自定义属性]明细页
    Created on : 2017-12-14 14:02:29
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[自定义属性]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                	<caption>[自定义属性]基本信息</caption>
					<tr>
						<th>属性名称：</th>
						<td>
							${osCustomAttribute.attributeName}
						</td>
					</tr>
					<tr>
						<th>KEY_：</th>
						<td>
							${osCustomAttribute.key}
						</td>
					</tr>
					<tr>
						<th>属性类型：</th>
						<td>
							${osCustomAttribute.attributeType}
						</td>
					</tr>
					<tr>
						<th>分类ID_：</th>
						<td>
							${osCustomAttribute.treeId}
						</td>
					</tr>
					<tr>
						<th>控件类型：</th>
						<td>
							${osCustomAttribute.widgetType}
						</td>
					</tr>
					<tr>
						<th>值来源：</th>
						<td>
							${osCustomAttribute.valueSource}
						</td>
					</tr>
					<tr>
						<th>维度ID：</th>
						<td>
							${osCustomAttribute.dimId}
						</td>
					</tr>
					<tr>
						<th>机构ID：</th>
						<td>
							${osCustomAttribute.tenantId}
						</td>
					</tr>
					<tr>
						<th>创建人ID：</th>
						<td>
							${osCustomAttribute.createBy}
						</td>
					</tr>
					<tr>
						<th>创建时间：</th>
						<td>
							${osCustomAttribute.createTime}
						</td>
					</tr>
					<tr>
						<th>更新人ID：</th>
						<td>
							${osCustomAttribute.updateBy}
						</td>
					</tr>
					<tr>
						<th>更新时间：</th>
						<td>
							${osCustomAttribute.updateTime}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="sys/org/osCustomAttribute" 
        entityName="com.redxun.sys.org.entity.OsCustomAttribute"
        formId="form1"/>
        
        <script type="text/javascript">
		mini.parse();
		var form = new mini.Form("#form1");
		var pkId = '${osCustomAttribute.ID}';
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/sys/org/osCustomAttribute/getJson.do",
				data:{ids:pkId},
				success:function (json) {
					form.setData(json);
				}					
			});
		})
		</script>
    </body>
</html>