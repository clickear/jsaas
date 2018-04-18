<%-- 
    Document   : 字段明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>字段明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" hideRecordNav="true"/>
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>字段基本信息</caption>
				
				<tr>
					<th>字段名称</th>
					<td>${sysField.attrName}</td>
					<th>标  题</th>
					<td>${sysField.title}</td>
				</tr>
				<tr>
					<th>字段类型</th>
					<td>${sysField.fieldType}</td>

					<th>字段分组</th>
					<td>${sysField.fieldGroup}</td>
				</tr>
				<tr>
					<th>字段长度</th>
					<td>${sysField.fieldLength}</td>
					<th>字段精度</th>
					<td>${sysField.precision}</td>
				</tr>
				<tr>
					<th>字段序号</th>
					<td>${sysField.sn}</td>
					<th>跨列数</th>
					<td>${sysField.colspan}</td>
				</tr>
				<tr>
					<th>字段分类</th>
					<td>${sysField.fieldCat}</td>
					<th>关系类型</th>
					<td>${sysField.relationType}</td>
				</tr>
				<tr>
					<th>是否隐藏</th>
					<td>${sysField.isHidden}</td>

					<th>是否只读</th>
					<td>${sysField.isReadable}</td>
				</tr>
				<tr>
					<th>是否必须</th>
					<td>${sysField.isRequired}</td>
					<th>是否禁用</th>
					<td>${sysField.isDisabled}</td>
				</tr>
				<tr>
					<th>是否允许分组</th>
					<td>${sysField.allowGroup}</td>
					<th>是否允许排序</th>
					<td>${sysField.allowSort}</td>
				</tr>
				<tr>
					<th>是否允许统计</th>
					<td>${sysField.allowSum}</td>
				
					<th>是否缺省显示列</th>
					<td>${sysField.isDefaultCol}</td>
				</tr>
				<tr>
					<th>是否缺省查询列</th>
					<td>${sysField.isQueryCol}</td>
				
					<th>缺省值</th>
					<td>${sysField.defValue}</td>
				</tr>
				
				<tr>
					
					<th>数据库字段公式</th>
					<td>${sysField.dbFieldFormula}</td>
					<th>数据库字段名</th>
					<td>${sysField.dbFieldName}</td>
				</tr>
				<tr>
					<th>是否在导航树上展示</th>
					<td>${sysField.showNavTree}</td>
					<th>是否允许Excel插入</th>
					<td>${sysField.allowExcelInsert}</td>
				</tr>
				<tr>
					<th>是否允许Excel编辑</th>
					<td>${sysField.allowExcelEdit}</td>
				
					<th>是否允许有附件</th>
					<td>${sysField.hasAttach}</td>
				</tr>
				<tr>
					<th>是否图表分类</th>
					<td>${sysField.isCharCat}</td>
					<th>自定义展示</th>
					<td>${sysField.renderer}</td>
				</tr>
				<tr>
					<th>用户定义字段</th>
					<td>${sysField.isUserDef}</td>
				
					<th>控件类型</th>
					<td>${sysField.compType}</td>
				</tr>
				<tr>
					<th>JSON配置</th>
					<td>${sysField.jsonConfig}</td>
				
					<th>关联字段值新增方式</th>
					<td>${sysField.linkAddMode}</td>
				</tr>
				<tr>
					<th>添加权限</th>
					<td>${sysField.addRight}</td>
				
					<th>编辑权限</th>
					<td>${sysField.editRight}</td>
				</tr>
				<tr>
					<th>模  块</th>
					<td>${sysField.sysModule.title}</td>
					<th>关联模块</th>
					<td>${sysField.linkSysModule.title}</td>
				</tr>
				<tr>
					<th>备  注</th>
					<td colspan="3">${sysField.remark}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${sysField.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${sysField.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${sysField.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${sysField.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="sys/core/sysField" formId="form1"  entityName="com.redxun.sys.core.entity.SysField" />
</body>
</html>