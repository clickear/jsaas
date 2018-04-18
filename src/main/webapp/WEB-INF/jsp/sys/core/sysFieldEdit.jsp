<%-- 
    Document   : 系统模块字段编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>系统模块字段编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
		<rx:toolbar toolbarId="toolbar1" pkId="${sysField.fieldId}" hideRecordNav="true"/>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div>
				<%-- <input id="pkId" name="pkId" class="mini-hidden" value="${sysInst.instId}" /> --%>
				<input id="pkId" name="pkId" class="mini-hidden" value="${sysField.fieldId}" />
				<table class="table-detail column_2" cellspacing="1" cellpadding="0">
					<caption>系统模块字段基本信息</caption>
				<tr>
					<th>字段名称<span class="star">*</span> </th>
					<td><input name="attrName" value="${sysField.attrName}" class="mini-textbox" vtype="maxLength:50" required="true" emptyText="请输入字段名称" style="width:80%" allowInput="false" /></td>
					<th>标  题<span class="star">*</span> </th>
					<td><input name="title" value="${sysField.title}" class="mini-textbox" vtype="maxLength:50" required="true" emptyText="请输入标题" style="width:80%" allowInput="false" /></td>
				</tr>
				<tr>
					<th>所属模块<span class="star">*</span> </th>
					<td><input name="moduleId" class="mini-combobox" style="width:150px;" textField="title" valueField="moduleId"   url="${ctxPath}/sys/core/sysModule/getAllModule.do"  value="${sysField.sysModule.moduleId}" allowInput="false" enabled="false"/></td>
					<th>关联模块<span class="star">*</span> </th>
					<td><input name="linkModId" class="mini-combobox" style="width:150px;" textField="title" valueField="moduleId"   url="${ctxPath}/sys/core/sysModule/getAllModule.do"  value="${sysField.linkSysModule.moduleId}" allowInput="false" /></td>
				</tr>
				<tr>
					<th>字段类型<span class="star">*</span> </th>
					<td><input name="fieldType" value="${sysField.fieldType}" class="mini-textbox" vtype="maxLength:50" required="true" emptyText="请输入字段类型" style="width:80%"/></td>

					<th>字段分组</th>
					<td><input name="fieldGroup" value="${sysField.fieldGroup}" class="mini-textbox" vtype="maxLength:50" style="width:80%"/></td>
				</tr>
				<tr>
					<th>字段长度</th>
					<td><input name="fieldLength" value="${sysField.fieldLength}" class="mini-textbox" vtype="maxLength:11" style="width:80%"/></td>
					<th>字段精度</th>
					<td><input name="precision" value="${sysField.precision}" class="mini-textbox" vtype="maxLength:11" style="width:80%"/></td>
				</tr>
				<tr>
					<th>字段序号</th>
					<td><input name="sn" value="${sysField.sn}" class="mini-textbox" vtype="maxLength:11" style="width:80%"/></td>
					<th>跨列数</th>
					<td><input name="colspan" value="${sysField.colspan}" class="mini-textbox" vtype="maxLength:11" style="width:80%"/></td>
				</tr>
				<tr>
					<th>字段分类</th>
					<td><input name="fieldCat" value="${sysField.fieldCat}" class="mini-combobox" data="[{id:'FIELD_COMMON',text:'普通字段'},{id:'FIELD_PK',text:'主键字段'},{id:'FIELD_RELATION',text:'关系字段'}]" /></td>
					<th>关系类型</th>
					<td><input name="relationType" value="${sysField.relationType}" class="mini-combobox" data="[{id:'OneToMany',text:'一对多'},{id:'OneToOne',text:'一对一'},{id:'ManyToOne',text:'多对一'},{id:'ManyToMany',text:'多对多'},{id:'NONE',text:'无关系'}]" /></td>
				</tr>
				<tr>
					<th>是否隐藏</th>
					<td><input name="isHidden" value="${sysField.isHidden}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" /></td>

					<th>是否只读</th>
					<td><input name="isReadable" value="${sysField.isReadable}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" /></td>
				</tr>
				<tr>
					<th>是否必须</th>
					<td><input name="isRequired" value="${sysField.isRequired}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" /></td>
					<th>是否禁用</th>
					<td><input name="isDisabled" value="${sysField.isDisabled}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" /></td>
				</tr>
				<tr>
					<th>是否允许分组</th>
					<td><input name="allowGroup" value="${sysField.allowGroup}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" /></td>
					<th>是否允许排序</th>
					<td><input name="allowSort" value="${sysField.allowSort}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" /></td>
				</tr>
				<tr>
					<th>是否允许统计</th>
					<td><input name="allowSum" value="${sysField.allowSum}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" /></td>
				
					<th>是否缺省显示列</th>
					<td><input name="isDefaultCol" value="${sysField.isDefaultCol}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" /></td>
				</tr>
				<tr>
					<th>是否缺省查询列</th>
					<td><input name="isQueryCol" value="${sysField.isQueryCol}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" /></td>
				
					<th>缺省值</th>
					<td><input name="defValue" value="${sysField.defValue}" class="mini-textbox" vtype="maxLength:50" style="width:80%"/></td>
				</tr>
				
				<tr>
					<th style="vertical-align: middle;">数据库字段公式</th>
					<td>
							<textarea rows="3" cols="50" name="dbFieldFormula" class="mini-textarea"  vtype="maxLength:65535" style="width:80%">${sysField.dbFieldFormula}</textarea>
					</td>
					<th style="vertical-align: middle;">数据库字段名</th>
					<td><input name="dbFieldName" value="${sysField.dbFieldName}" class="mini-textbox" vtype="maxLength:50" style="width:80%" allowInput="false" /></td>
				</tr>
				<tr>
					<th>是否在导航树上展示</th>
					<td><input name="showNavTree" value="${sysField.showNavTree}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" /></td>
					<th>是否允许Excel插入</th>
					<td><input name="allowExcelInsert" value="${sysField.allowExcelInsert}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" /></td>
				</tr>
				<tr>
					<th>是否允许Excel编辑</th>
					<td><input name="allowExcelEdit" value="${sysField.allowExcelEdit}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" /></td>
				
					<th>是否允许有附件</th>
					<td><input name="hasAttach" value="${sysField.hasAttach}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" /></td>
				</tr>
				<tr>
					<th>是否图表分类</th>
					<td><input name="isCharCat" value="${sysField.isCharCat}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" /></td>
					<th>自定义展示</th>		
					<td><input name="renderer" value="${sysField.renderer}" class="mini-textbox" vtype="maxLength:512" style="width:80%" /></td>
				</tr>
				<tr>
					<th>用户定义字段</th>
					<td><input name="isUserDef" value="${sysField.isUserDef}" class="mini-textbox"  vtype="maxLength:6" style="width:80%" /></td>
				
					<th>控件类型</th>
					
					<td><input name="compType" value="${sysField.compType}" class="mini-combobox" style="width:150px;" textField="key" valueField="key"   url="${ctxPath }/sys/core/sysDic/listByTreeKey.do?key=CTL_TYPE_"  value="${sysField.compType}" allowInput="false" /></td>
				</tr>
				<tr>
					<th style="vertical-align: middle;">JSON配置</th>
					<td><textarea rows="10" cols="50" name="jsonConfig" class="mini-textarea"  vtype="maxLength:65535" style="width:80%">${sysField.jsonConfig}</textarea></td>
				
					<th style="vertical-align: middle;">关联字段值新增方式</th>
					<td><input name="linkAddMode" value="${sysField.linkAddMode}" class="mini-textbox"  vtype="maxLength:16" style="width:80%" /></td>
				</tr>
				<tr>
					<th>添加权限</th>
					<td><input name="addRight" value="${sysField.addRight}" class="mini-textbox"  vtype="maxLength:20" style="width:80%" /></td>
				
					<th>编辑权限</th>
					<td><input name="editRight" value="${sysField.editRight}" class="mini-textbox"  vtype="maxLength:20" style="width:80%" /></td>
				</tr>
				<tr>
					<th style="vertical-align: middle;">备  注</th>
					<td colspan="3"><textarea rows="10" cols="100" name="remark" class="mini-textarea"  vtype="maxLength:65535" style="width:80%">${sysField.remark}</textarea></td>
				</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/core/sysField" entityName="com.redxun.sys.core.entity.SysField"  />
</body>
</html>