
<%-- 
    Document   : [机构类型]编辑页
    Created on : 2017-07-10 18:35:32
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[机构类型]编辑</title>
<%@include file="/commons/edit.jsp"%>
<style type="text/css">
body{
	background: #f7f7f7;
}

#toolbar1,
.mini-toolbar tr:nth-of-type(1){
	border-color: transparent;
	background: transparent;
}
#toolbarBody a{
	border-color: #fff;
	background: #fff;
}

</style>


</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="sysInstType.id"/>
		<div class="shadowBox">
			<div id="p1" class="form-outer">
				<form id="form1" method="post">
					<input id="pkId" name="id" class="mini-hidden" value="${sysInstType.typeId}" />
					<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
						<caption>机构类型基本信息</caption>
						<tr>
							<th>
								<span class="starBox">
									类型名称<span class="star">*</span>
								</span>			
							</th>
							<td>
								<input name="typeName" value="${sysInstType.typeName}"
								class="mini-textbox"   style="width: 80%" required="required" />
							</td>
							<th>
								<span class="starBox">
									类型编码<span class="star">*</span>
								</span>	
							</th>
							<td>
								<input name="typeCode" value="${sysInstType.typeCode}"
								class="mini-textbox"   style="width: 80%" required="required"/>
							</td>
						</tr>
						<tr>
							<th>是否启用</th>
							<td>
								<ui:radioBoolean  name="enabled" value="${sysInstType.enabled}"/>
							</td>
							<th>系统缺省</th>
							<td>
								<ui:radioBoolean  name="isDefault" value="${sysInstType.isDefault}"/>
							</td>
						</tr>
						<tr>
							<th>首页地址</th>
							<td colspan="3">
								<input  name="homeUrl" class="mini-textbox" value="${sysInstType.homeUrl}" style="width:90%"></input>
							</td>
						</tr>
						<tr>
							<th>描　　述</th>
							<td colspan="3">
								<textarea  name="descp" class="mini-textarea" rows="4" cols="60" style="height:120px;width:90%">${sysInstType.descp}</textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	<rx:formScript formId="form1" baseUrl="sys/core/sysInstType"
		entityName="com.redxun.sys.core.entity.SysInstType" />
		
	<script type="text/javascript">
		addBody();
	</script>		

</body>
</html>