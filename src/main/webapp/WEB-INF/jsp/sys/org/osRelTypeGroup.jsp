<%-- 
    Document   : 关系类型编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>用户组类型编辑</title>
<%@include file="/commons/edit.jsp"%>
<style type="text/css">

.shadowBox{
	margin-bottom: 0;
}
</style>

</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${osRelType.id}"/>
		<div class="shadowBox90">
			<div id="p1" class="form-outer">
				<form id="form1" method="post">
					<div>
						<input id="pkId" name="id" class="mini-hidden" value="${osRelType.id}" />
						<input class="mini-hidden" name="relType" value="${osRelType.relType}"/>
						<input class="mini-hidden" name="constType" value="${osRelType.constType}"/>
						<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
							<caption>关系类型基本信息</caption>
							<tr>
								<th><span class="starBox">关  系  名 <span class="star">*</span></span></th>
								<td><input name="name" value="${osRelType.name}" class="mini-textbox" vtype="maxLength:64" required="true" emptyText="请输入关系名" style="width:80%"/></td>
								<th>关系业务标识 <span class="star">*</span></th>
								<td><input name="key" value="${osRelType.key}" class="mini-textbox" vtype="maxLength:64" required="true" emptyText="请输入关系业务标识" style="width:80%"/></td>
							</tr>
							
							<tr>
								<th>关系当前方名称 <span class="star">*</span> </th>
								<td><input name="party1" value="${osRelType.party1}" class="mini-textbox" vtype="maxLength:128" required="true" emptyText="请输入关系当前方名称" style="width:80%"/></td>
								<th>关系关联方名称 <span class="star">*</span> 
								</th>
								<td><input name="party2" value="${osRelType.party2}" class="mini-textbox" vtype="maxLength:128" required="true" emptyText="请输入关系关联方名称" style="width:80%"/></td>
							</tr>
							<tr>
								<th>当前方维度</th>
								<td>
									<input id="dimId1"  name="dimId1" class="mini-combobox" style="width:150px;" textField="name" valueField="dimId" emptyText="请选择..." enabled="false"
		    						 url="${ctxPath}/sys/org/osDimension/jsonAll.do?tenantId=${param['tenantId']}" value="${osRelType.dimId1}" showNullItem="true" nullItemText="请选择..."/> 
								</td>
								<th>关联方维度</th>
								<td>
									<input id="dimId2"  name="dimId2" class="mini-combobox" style="width:150px;" textField="name" valueField="dimId" emptyText="请选择..."
		    						 url="${ctxPath}/sys/org/osDimension/jsonAll.do?tenantId=${param['tenantId']}" value="${osRelType.dimId2}"   showNullItem="true" nullItemText="请选择..."/>
								</td>
							</tr>
							<tr>
								<th><span class="starBox">状　　态<span class="star">*</span></span></th>
								<td>
									<div name="status" class="mini-radiobuttonlist" value="${osRelType.status}" required="true" repeatDirection="vertical" emptyText="请输入状态" 
										  repeatLayout="table" data="[{id:'ENABLED',text:'激活'},{id:'DISABLED',text:'禁用'}]" textField="text" valueField="id" vtype="maxLength:40"></div>
								</td>
								<th><span class="starBox">是否是双向 <span class="star">*</span></span> </th>
								<td>
									<ui:miniRadioList name="isTwoWay" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" value="${osRelType.isTwoWay}" required="true"/>
								</td>
							</tr>
							<tr>
								<th>关系备注 </th>
								<td colspan="3">
									<textarea name="memo" class="mini-textarea" vtype="maxLength:255" style="width: 90%">${osRelType.memo}</textarea>
								</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
		</div>
	<rx:formScript formId="form1" baseUrl="sys/org/osRelType" tenantId="${param['tenantId']}"/>
	
	<script>
		addBody();
	</script>
	
</body>
</html>