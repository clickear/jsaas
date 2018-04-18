<%-- 
    Document   : 系统模块编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<title>系统模块编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<%-- <rx:toolbar toolbarId="toolbar1" pkId="${sysInst.instId}"/> --%>
	<rx:toolbar toolbarId="toolbar1" pkId="${sysModule.moduleId}"/>
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div>
				<%-- <input id="pkId" name="pkId" class="mini-hidden" value="${sysInst.instId}" /> --%>
				<input id="pkId" name="pkId" class="mini-hidden" value="${sysModule.moduleId}" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<caption>系统模块基本信息</caption>
					<tr>
						<th>
							<span class="starBox">
								模块标题<span class="star">*</span> 
							</span>
						</th>
						<td colspan="3"><input name="title" value="${sysModule.title}" class="mini-textbox" vtype="maxLength:50" required="true" emptyText="请输入模块标题" style="width:80%"/></td>
					</tr>
					<tr>
						<th>映射URL地址 </th>
						<td colspan="3"><input name="reqUrl" value="${sysModule.reqUrl}" class="mini-textbox" vtype="maxLength:150" style="width:80%"/></td>
	<%-- 					<th>icon地址样式 </th>
						<td><input name="iconCls" value="${sysModule.iconCls}" class="mini-textbox" vtype="maxLength:20" style="width:80%"/></td> --%>
					</tr>
					<tr>
						<th>简　　称 </th>
						<td><input name="shortName" value="${sysModule.shortName}" class="mini-textbox" vtype="maxLength:20" style="width:80%"/></td>
						<th>所属子系统 </th>
						<td>
							<input name="sysId" class="mini-combobox" style="width:150px;" textField="name" valueField="sysId"   url="${ctxPath}/sys/core/subsystem/getAllSubSystem.do"  value="${sysModule.subsystem.sysId}" allowInput="false"/>  
						</td>
					</tr>
					<tr>
						<th>表　　名 </th>
						<td><input name="tableName" value="${sysModule.tableName}" class="mini-textbox" vtype="maxLength:50" style="width:80%"/></td>
						<th>实  体  名 </th>
						<td><input name="entityName" value="${sysModule.entityName}" class="mini-textbox" vtype="maxLength:100" style="width:80%"/></td>
					</tr>
					<tr>
						<th>命名空间 </th>
						<td><input name="namespace" value="${sysModule.namespace}" class="mini-textbox" vtype="maxLength:100" style="width:80%"/></td>
						<th>主键字段名 <span class="star">*</span> </th>
						<td><input name="pkField" value="${sysModule.pkField}" class="mini-textbox" vtype="maxLength:50" required="true" emptyText="请输入主键字段名" style="width:80%" allowInput="false" /></td>
					</tr>

					<tr>
						<th>数据库主键字段</th>
						<td><input name="pkDbField" value="${sysModule.pkDbField}" class="mini-textbox" vtype="maxLength:50" style="width:80%" allowInput="false" /></td>
						<th>编码字段名 </th>
						<td><input name="codeField" class="mini-combobox" style="width:150px;" textField="attrName" valueField="attrName"   url="${ctxPath}/sys/core/sysField/getByModuleId.do?moduleId=${sysModule.moduleId}"  value="${sysModule.codeField}" allowInput="false"/></td>
					</tr>
					<tr>
						<th>排序字段名 </th>
						<td><input name="orderField" class="mini-combobox" style="width:150px;" textField="attrName" valueField="attrName"   url="${ctxPath}/sys/core/sysField/getByModuleId.do?moduleId=${sysModule.moduleId}"  value="${sysModule.orderField}" allowInput="false"/></td>
						<th>日期字段 </th>
						<td><input name="dateField" class="mini-combobox" style="width:150px;" textField="attrName" valueField="attrName"   url="${ctxPath}/sys/core/sysField/getByModuleId.do?moduleId=${sysModule.moduleId}"  value="${sysModule.dateField}" allowInput="false"/></td>
					</tr>
					<tr>
						<th>年份字段 </th>
						<td><input name="yearField" class="mini-combobox" style="width:150px;" textField="attrName" valueField="attrName"   url="${ctxPath}/sys/core/sysField/getByModuleId.do?moduleId=${sysModule.moduleId}"  value="${sysModule.yearField}" allowInput="false"/></td>
						<th>月份字段 </th>
						<td><input name="monthField" class="mini-combobox" style="width:150px;" textField="attrName" valueField="attrName"   url="${ctxPath}/sys/core/sysField/getByModuleId.do?moduleId=${sysModule.moduleId}"  value="${sysModule.monthField}" allowInput="false"/></td>
					</tr>
					<tr>
						<th>季度字段 </th>
						<td colspan="3"><input name="sensonField" class="mini-combobox" style="width:150px;" textField="attrName" valueField="attrName"   url="${ctxPath}/sys/core/sysField/getByModuleId.do?moduleId=${sysModule.moduleId}"  value="${sysModule.sensonField}" allowInput="false"/></td>
					</tr>
					<tr>
						<th>文件字段 </th>
						<td><input name="fileField" class="mini-combobox" style="width:150px;" textField="attrName" valueField="attrName"   url="${ctxPath}/sys/core/sysField/getByModuleId.do?moduleId=${sysModule.moduleId}"  value="${sysModule.fileField}" allowInput="false"/></td>
						<th>
							<span class="starBox">
								是否启用 <span class="star">*</span>
							</span>
						</th>
						<td><input name="isEnabled" value="${sysModule.isEnabled}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" required="true" /></td>
					</tr>
					<tr>
						<th>
							<span class="starBox">
								是否审计执行日记 <span class="star">*</span> 
							</span>
						</th>
						<td><input name="allowAudit" value="${sysModule.allowAudit}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" required="true" /></td>
						<th>
							<span class="starBox">
								启动审批 <span class="star">*</span> 
							</span>
						</th>
						<td><input name="allowApprove" value="${sysModule.allowApprove}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" required="true" /></td>
					</tr>
					<tr>
						<th>
							<span class="starBox">
								是否有附件 <span class="star">*</span> 
							</span>
						</th>
						<td><input name="hasAttachs" value="${sysModule.hasAttachs}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" required="true" /></td>
						<th>缺省排序字段 </th>
						<td><input name="defOrderField" class="mini-combobox" style="width:150px;" textField="attrName" valueField="attrName"   url="${ctxPath}/sys/core/sysField/getByModuleId.do?moduleId=${sysModule.moduleId}"  value="${sysModule.defOrderField}" allowInput="false"/></td>
					</tr>
					<tr>
						<th>编码流水键 </th>
						<td><input name="seqCode" value="${sysModule.seqCode}" class="mini-textbox" vtype="maxLength:50" style="width:80%"/></td>

						<th>是否有图表 <span class="star">*</span> 
						</th>
						<td><input name="hasChart" value="${sysModule.hasChart}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" required="true" /></td>
					</tr>
					<tr>
						<th>系统默认</th>
						<td><input name="isDefault" value="${sysModule.isDefault}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" required="true" /></td>
						<th>描　　述 </th>
						<td><input name="descp" value="${sysModule.descp}" class="mini-textbox" vtype="maxLength:50" style="width:80%"/></td>
					</tr>
					<tr>
						<th>帮助内容 </th>
						<td colspan="3">
							<textarea rows="6" cols="80" name="helpHtml" class="mini-textarea" class="mini-textbox" vtype="maxLength:65535" style="width:80%">${sysModule.helpHtml}</textarea>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/core/sysModule" entityName="com.redxun.sys.core.entity.SysModule"  />
	<script type="text/javascript">
		addBody();
	</script>
</body>
</html>