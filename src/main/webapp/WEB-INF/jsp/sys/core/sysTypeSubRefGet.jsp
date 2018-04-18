
<%-- 
    Document   : [机构--子系统关系]明细页
    Created on : 2017-09-13 15:57:55
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[机构--子系统关系]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                	<caption>[机构--子系统关系]基本信息</caption>
					<tr>
						<th>机构类型ID：</th>
						<td>
							${sysTypeSubRef.instTypeId}
						</td>
					</tr>
					<tr>
						<th>子系统ID：</th>
						<td>
							${sysTypeSubRef.subSysId}
						</td>
					</tr>
					<tr>
						<th>租用用户Id：</th>
						<td>
							${sysTypeSubRef.tenantId}
						</td>
					</tr>
					<tr>
						<th>创建人ID：</th>
						<td>
							${sysTypeSubRef.createBy}
						</td>
					</tr>
					<tr>
						<th>创建时间：</th>
						<td>
							${sysTypeSubRef.createTime}
						</td>
					</tr>
					<tr>
						<th>更新人ID：</th>
						<td>
							${sysTypeSubRef.updateBy}
						</td>
					</tr>
					<tr>
						<th>更新时间：</th>
						<td>
							${sysTypeSubRef.updateTime}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="sys/core/sysTypeSubRef" 
        entityName="com.redxun.sys.core.entity.SysTypeSubRef"
        formId="form1"/>
        
        <script type="text/javascript">
		mini.parse();
		var form = new mini.Form("#form1");
		var pkId = ${sysTypeSubRef.id};
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/sys/core/sysTypeSubRef/getJson.do",
				data:{ids:pkId},
				success:function (json) {
					form.setData(json);
				}					
			});
		})
		</script>
    </body>
</html>