
<%-- 
    Document   : [日志实体]明细页
    Created on : 2017-09-25 14:27:06
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[日志实体]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>
		<div class="heightBox"></div>
        <div id="form1" class="form-outer shadowBox90">
             <div>
             	<table style="width:100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
                	<caption>[日志实体]基本信息</caption>
					<tr>
						<th>所属模块</th>
						<td>
							${logEntity.module}
						</td>
					</tr>
					<tr>
						<th>功　　能</th>
						<td>
							${logEntity.subModule}
						</td>
					</tr>
					<tr>
						<th>操  作  名</th>
						<td>
							${logEntity.action}
						</td>
					</tr>
					<tr>
						<th>操作 IP</th>
						<td>
							${logEntity.ip}
						</td>
					</tr>
					<tr>
						<th>操作目标</th>
						<td>
							${logEntity.target}
						</td>
					</tr>
					<tr>
						<th>租用机构ID</th>
						<td>
							${logEntity.tenantId}
						</td>
					</tr>
					<tr>
						<th>更新时间</th>
						<td>
							${logEntity.updateTime}
						</td>
					</tr>
					<tr>
						<th>更新人ID</th>
						<td>
							${logEntity.updateBy}
						</td>
					</tr>
					<tr>
						<th>创建时间</th>
						<td>
							${logEntity.createTime}
						</td>
					</tr>
					<tr>
						<th>创建人ID</th>
						<td>
							${logEntity.createBy}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="sys/log/logEntity" 
        entityName="com.redxun.sys.log.entity.LogEntity"
        formId="form1"/>
        
        <script type="text/javascript">
        addBody();
		mini.parse();
		var form = new mini.Form("#form1");
		var pkId = ${logEntity.id};
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/sys/log/logEntity/getJson.do",
				data:{ids:pkId},
				success:function (json) {
					form.setData(json);
				}					
			});
		})
		</script>
    </body>
</html>