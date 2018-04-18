<%-- 
    Document   : sql编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>sql编辑</title>
<%@include file="/commons/edit.jsp"%>
<!-- code codemirror start -->
<link rel="stylesheet"
	href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
	<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
	<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
	<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
	<!-- code minitor end -->
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${bpmSqlNode.bpmSqlNodeId}"
		excludeButtons="remove,prevRecord,nextRecord">
	</rx:toolbar>
	<form id="form1" method="post">
		<input id="pkId" name="bpmSqlNodeId" class="mini-hidden"
			value="${bpmSqlNode.bpmSqlNodeId}" />
		<table class="table-detail" cellspacing="1" cellpadding="0">
			<caption>sql基本信息</caption>
			<tr style="display: none;">
				<th>解决方案ID ：</th>
				<td><input name="solId" value="${bpmSqlNode.solId}"
					class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
			</tr>
			<tr style="display: none;">
				<th>节点ID ：</th>
				<td><input name="nodeId" value="${bpmSqlNode.nodeId}"
					class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
			</tr>
			<tr style="display: none;">
				<th>节点名称 ：</th>
				<td>${bpmSqlNode.nodeText}</td>
			</tr>
			<tr>
				<th style="width: 15%"><input type="radio" name="sqlType"
					value="1" />自定义SQL ：</th>
				<td colspan="6"><c:if test="${fn:length(configVars)>0}">
						<div class="mini-toolbar" style="margin-bottom: 2px;">
							流程变量:
							<c:forEach items="${configVars}" var="var">
								<a class="mini-button" iconCls="icon-var" plain="true"
									onclick="appendScript('${var.key}','${var.type}')">${var.name}:${var.key}(${var.type})</a>
							</c:forEach>
						</div>
					</c:if> <textarea id="sql" name="sql" vtype="maxLength:1000">${bpmSqlNode.sql}</textarea></td>
			</tr>
			<tr>
				<th><input type="radio" name="sqlType" value="2" />流程数据输出映射:</th>
				<td>数据源</td>
				<td><input allowInput="false" value="${bpmSqlNode.dsId}"
					text="${bpmSqlNode.dsName}" id="dataSourceId"
					class="mini-buttonedit" onbuttonclick="onDatasource" name="dsId" /></td>
				<td colspan="4"></td>
			</tr>
			<tr>
				<th>表的设置</th>
				<td colspan="6">
					<div id="gridTableId" class="mini-treegrid" width="auto"
						height="auto"
						url="${ctxPath}/bpm/sql/bpmSqlNode/genForTableData.do?id=${bpmSqlNode.bpmSqlNodeId}&solId=${bpmSqlNode.solId}&nodeId=${bpmSqlNode.nodeId}"
						showTreeIcon="true" treeColumn="field" idField="id"
						parentField="parentId" resultAsTree="false" allowResize="true"
						expandOnLoad="true" allowCellEdit="true" allowCellSelect="true"
						showCheckBox="false" oncellbeginedit="OnCellBeginEditForTable">
						<div property="columns">
							<div name="formField" field="formField">表单字段名称</div>
							<div name="columnLabel" field="columnLabel">
								表名称 <input property="editor" class="mini-buttonedit"
									onbuttonclick="selectTable" />
							</div>
							<!-- 
									<div name="fkRefColumn" field="fkRefColumn">
										指向外键的列 <input allowInput="true" property="editor"
											class="mini-combobox" style="width: 100%;" valueField="id"
											textField="name" />
									</div>
									 -->
							<div></div>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<th>列的设置</th>
				<td colspan="6">
					<div id="gridColumnId" class="mini-treegrid" width="auto"
						height="auto"
						url="${ctxPath}/bpm/sql/bpmSqlNode/genData.do?id=${bpmSqlNode.bpmSqlNodeId}&solId=${bpmSqlNode.solId}&nodeId=${bpmSqlNode.nodeId}"
						showTreeIcon="true" treeColumn="formField" idField="id"
						parentField="parentId" resultAsTree="false" allowResize="true"
						expandOnLoad="true" allowCellEdit="true" allowCellSelect="true"
						showCheckBox="false" oncellbeginedit="OnCellBeginEditForColumn"
						multiSelect="true">
						<div property="columns">
							<div type="indexcolumn"></div>
							<div type="checkcolumn" width="20"></div>
							<div name="formField" field="formField" width="160">表单字段名称</div>
							<div name="columnLabel" field="columnLabel">
								表名或列名称 <input allowInput="true" property="editor"
									class="mini-combobox" style="width: 100%;"
									valueField="columnLabel" textField="columnLabel" />
							</div>
							<div name="fkRefTable" field="fkRefTable">
								指向外键的表 <input property="editor" class="mini-buttonedit"
									onbuttonclick="onTable" style="width: 100%;" />
							</div>
							<div name="fkRefColumn" field="fkRefColumn">
								指向外键的列 <input allowInput="true" property="editor"
									class="mini-combobox" style="width: 100%;"
									valueField="columnLabel" textField="columnLabel" />
							</div>
						</div>
					</div>
				</td>
			</tr>
			<tr style="display: none;">
				<th>json数据</th>
				<td colspan="6"><textarea id="jsonData" name="jsonData"
						class="mini-textarea" emptyText="请输入">${bpmSqlNode.jsonData}</textarea></td>
			</tr>
			<tr style="display: none;">
				<th>jsonTable</th>
				<td colspan="6"><textarea id="jsonTable" name="jsonTable"
						class="mini-textarea" emptyText="请输入">${bpmSqlNode.jsonTable}</textarea></td>
			</tr>
		</table>
	</form>
	<rx:formScript formId="form1" baseUrl="bpm/sql/bpmSqlNode"
		entityName="com.redxun.bpm.sql.entity.BpmSqlNode" />
	<script type="text/javascript">
		mini.parse();
		var gridColumn = mini.get("#gridColumnId");
		gridColumn.selectAll(false);
		var gridTable = mini.get("#gridTableId");
		gridTable.selectAll(false);
		var id = '${bpmSqlNode.bpmSqlNodeId}';
		var sqlTypeValue = '${bpmSqlNode.sqlType}';
		//加入脚本内容
		var editor = null;
		$(function() {
			if (id != '') {
				//设置sql类型
				var sqlTypeIsInput = false;
				var sqlType = document.getElementsByName("sqlType");
				for (var i = 0; i < sqlType.length; i++) {
					if (sqlType[i].value == sqlTypeValue) {
						sqlType[i].checked = true;
					}
				}
			}
			//加入脚本内容
			editor = CodeMirror.fromTextArea(document.getElementById("sql"), {
				lineNumbers : true,
				matchBrackets : true,
				mode : "text/x-groovy"
			});
			//设置高度
			editor.setSize(null, "60");
		});
		function appendScript(scriptText, type) {
			scriptText = "\\${" + scriptText + "}";
			var doc = editor.getDoc();
			var cursor = doc.getCursor(); // gets the line number in the cursor position
			doc.replaceRange(scriptText, cursor); // adds a new line
		}
		//自定义保存
		function selfSaveData() {
			var url = "${ctxPath}/bpm/sql/bpmSqlNode/save.do";
			//验证sql类型
			var sqlTypeIsInput = false;
			var isReturn = false;
			var sqlType = document.getElementsByName("sqlType");
			for (var i = 0; i < sqlType.length; i++) {
				if (sqlType[i].checked) {
					sqlTypeIsInput = true;
					var sqlTypeValue = sqlType[i].value;
				}
			}
			if (sqlTypeIsInput) {
			} else {
				mini.alert('请选择自定义sql或流程输出映射！');
				isReturn = true;
				return;
			}
			//取得表单
			var form = new mini.Form("#form1");
			//设置jsonData
			var gridColumnData = gridColumn.getSelecteds();
			mini.get('jsonData').setValue(mini.encode(gridColumnData));
			//设置jsonTable
			var gridTableData = gridTable.getData();
			mini.get('jsonTable').setValue(mini.encode(gridTableData));
			//设置sql
			document.getElementById("sql").value = editor.getValue();
			_SubmitJson({
				url : url,
				method : 'POST',
				data : $('#form1').serialize(),
				// 你的formid
				success : function(text) {
					CloseWindow("cancel");
				}
			});
		}
		//选择表名
		function selectTable(e) {
			//数据源检验
			var dataSource = mini.get("#dataSourceId");
			var dataSourceValue = dataSource.getValue();
			if (dataSourceValue == null | dataSourceValue == '') {
				mini.alert('请选择数据源');
				return;
			}
			var btnEdit = this;
			var row = gridTable.getSelected();
			gridTable.cancelEdit();
			mini.open({
				url : "${ctxPath}/sys/db/sysDb/tableDialog.do?ds=" + dataSourceValue,
				title : "选择表名",
				width : 650,
				height : 380,
				ondestroy : function(action) {
					if (action == "ok") {
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.GetData();
						data = mini.clone(data); //必须
						if (data) {
							gridTable.updateRow(row, {
								name : data.tableName,
								tableName : data.tableName
							});
							//对表格(列)进行更新
							var rowColumn = gridColumn.findRow(function(rowColumn) {
								if (row.field == rowColumn.field) {
									gridColumn.updateRow(rowColumn, {
										name : data.tableName
									});
								}
							});
						}
					}
				}
			});
		}
		//动态设置每列的编辑器（表的选择）
		function OnCellBeginEditForTable(e) {
			//数据源检验
			var dataSource = mini.get("#dataSourceId");
			var dataSourceValue = dataSource.getValue();
			if (dataSourceValue == null | dataSourceValue == '') {
				mini.alert('请选择数据源');
				return;
			}
			var field = e.field;
			var record = e.record;
			var uid = record._uid;
			var pkId = record.pkId;
			var type = record.type;
			var tableName = record.name;
			if (field == 'formField') {
				if (type == 'column_parent' || type == 'column_child') {
					e.editor = null;
				}
			}
			if (field == 'fkRefColumn') {
				if (type == 'table_child') {
					var editor = e.editor;
					if (tableName != null) {
						var url = "${ctxPath}/sys/db/sysDb/findColumnList.do?ds=" + dataSourceValue + "&&tableName=" + tableName;
						editor.setUrl(url);
					} else {
						mini.alert('请选择表');
						e.cancel = true;
					}
				} else {
					e.editor = null;
				}
			}
		}
		//动态设置每列的编辑器（列的选择）
		function OnCellBeginEditForColumn(e) {
			//数据源检验
			var dataSource = mini.get("#dataSourceId");
			var dataSourceValue = dataSource.getValue();
			if (dataSourceValue == null | dataSourceValue == '') {
				mini.alert('请选择数据源');
				return;
			}
			var field = e.field;
			var record = e.record;
			var uid = record._uid;
			var pkId = record.pkId;
			var type = record.type;
			if (field == 'columnLabel') {
				if (type == 'column_parent' || type == 'column_child') {
					var editor = e.editor;
					var parentId = record.parentId;
					//取得父节点的字段名
					var parentTableName = findParentForColumnName(parentId);
					if (parentTableName == null) {
						mini.alert('请选择表');
						e.cancel = true;
						return;
					}
					//取得表名
					var tableName = findTableName(parentTableName);
					if (tableName != null) {
						var url = "${ctxPath}/sys/db/sysDb/listForColumn.do?ds=" + dataSourceValue + "&&tableName=" + tableName;
						editor.setUrl(url);
					} else {
						mini.alert('请选择表');
						e.cancel = true;
					}
				} else {
					e.editor = null;
				}
			}
			if (field == 'fkRefColumn') {
				if (type == 'column_parent' || type == 'column_child') {
					var editor = e.editor;
					//取得表名
					var tableName = record.fkRefTable;
					if (tableName) {
						var url = "${ctxPath}/sys/db/sysDb/listForColumn.do?ds=" + dataSourceValue + "&&tableName=" + tableName;
						editor.setUrl(url);
					} else {
						mini.alert('请选择表');
						e.cancel = true;
					}
				} else {
					e.editor = null;
				}
			}
		}
		//选择数据源
		function onDatasource(e) {
			var btnEdit = this;
			mini.open({
				url : "${ctxPath}/sys/core/sysDatasource/dialog.do",
				title : "选择数据源",
				width : 650,
				height : 380,
				ondestroy : function(action) {
					if (action == "ok") {
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.GetData();
						data = mini.clone(data); //必须
						if (data) {
							btnEdit.setValue(data.sourceId);
							btnEdit.setText(data.name);
						}
					}
				}
			});
		}
		//是否为主键
		var isPks = [{
			id : 'true',
			text : '是'
		}, {
			id : 'false',
			text : '否'
		}];
		//是否为主键
		function onIsPkRenderer(e) {
			var record = e.record;
			var uid = record._uid;
			var pkId = record.pkId;
			for (var i = 0, l = isPks.length; i < l; i++) {
				var g = isPks[i];
				if (g.id == e.value.toString()) {
					return g.text;
				}
			}
			return "";
		}
		//是否为空
		var isNulls = [{
			id : 'true',
			text : "是"
		}, {
			id : 'false',
			text : "否"
		}];
		//是否为空
		function onIsNullRenderer(e) {
			var record = e.record;
			var uid = record._uid;
			var pkId = record.pkId;
			for (var i = 0, len = isNulls.length; i < len; i++) {
				var g = isNulls[i];
				if (g.id == e.value.toString()) {
					return g.text;
				}
			}
			return "";
		}
		//是否为 外键
		var isFks = [{
			id : 'true',
			text : '是'
		}, {
			id : 'false',
			text : '否'
		}];
		//是否为外键
		function onIsFkRenderer(e) {
			var record = e.record;
			var uid = record._uid;
			var pkId = record.pkId;
			var type = record.type;
			if (type == 'column_parent' || type == 'column_child') {
				for (var i = 0, l = isFks.length; i < l; i++) {
					var g = isFks[i];
					if (g.id == e.value.toString()) {
						return g.text;
					}
				}
				return "";
			} else {
				return "";
			}
		}
		//表与列
		var Types = [{
			id : 'table_parent',
			text : '主表'
		}, {
			id : 'table_child',
			text : '从表'
		}, {
			id : 'column_parent',
			text : '列'
		}, {
			id : 'column_child',
			text : '列'
		}];
		//表与列
		function onTypeRenderer(e) {
			var record = e.record;
			var uid = record._uid;
			var pkId = record.pkId;
			for (var i = 0, l = Types.length; i < l; i++) {
				var g = Types[i];
				if (g.id == e.value) {
					return g.text;
				}
			}
			return "";
		}
		//数据类型
		var sqlTypes = [{
			id : 'Int',
			text : '整型'
		}, {
			id : 'String',
			text : '字符串'
		}, {
			id : 'Number',
			text : '数字'
		}, {
			id : 'Date',
			text : '日期'
		}, {
			id : 'Clob',
			text : '大数据'
		}];
		//数据类型
		function onSqlRenderer(e) {
			var record = e.record;
			var uid = record._uid;
			var pkId = record.pkId;
			for (var i = 0, l = sqlTypes.length; i < l; i++) {
				var g = sqlTypes[i];
				if (g.id == e.value) {
					return g.text;
				}
			}
			return "";
		}
		//根据表单字段名获得表名
		function findTableName(columnName) {
			var tableName = null;
			var isExist = false;
			//所有的json,数组
			var dataTable = gridTable.getData();
			for (var i = 0; i < dataTable.length; i++) {
				//所有的json,对象　
				var objectJson = dataTable[i];
				tableName = findJsonForTable(objectJson, columnName);
				if (tableName != null) {
					return tableName;
				}
				for ( var key in dataTable[i]) {
					if (key == 'children') {
						//主表数据,数组
						var children = dataTable[i][key];
						for (var j = 0; j < children.length; j++) {
							//主表数据,对象　　
							objectJson = children[j];
							tableName = findJsonForTable(objectJson, columnName);
							if (tableName != null) {
								return tableName;
							}
						}
					}
				}
			}
			return null;
		}
		//根据表单字段的parentId获得父节点的字段名(遍历json)
		function findJsonForTable(objectJson, columnName) {
			var isExist = false;
			for ( var key in objectJson) {
				if (key == 'name') {
					var value = objectJson[key];
					if (value == columnName) {
						isExist = true;
					}
				}
			}
			for ( var key in objectJson) {
				if (key == 'name') {
					var value = objectJson[key];
					if (isExist) {
						return value;
					}
				}
			}
			return null;
		}
		//根据表单字段的parentId获得父节点的字段名
		function findParentForColumnName(parentId) {
			var parentName = null;
			//所有的json,数组
			var dataColumn = gridColumn.getData();
			//dataColumn.length一般为1
			for (var i = 0; i < dataColumn.length; i++) {
				//所有的json,对象　
				var objectJson = dataColumn[i];
				parentName = findJsonForColumn(objectJson, parentId);
				if (parentName != null) {
					return parentName;
				}
				for ( var key in dataColumn[i]) {
					//主表数据
					if (key == 'children') {
						//主表,数组
						var children = dataColumn[i][key];
						for (var j = 0; j < children.length; j++) {
							for ( var keyChild in children[j]) {
								//主表,对象
								objectJson = children[j];
								parentName = findJsonForColumn(objectJson, parentId);
								if (parentName != null) {
									return parentName;
								}
								//从表的数据
								if (keyChild == 'children') {
									//从表的数据,数组
									var grandson = children[j][keyChild];
									for (var k = 0; k < grandson.length; k++) {
										for ( var keyGrandson in grandson[k]) {
											//从表，对象
											objectJson = grandson[k];
											parentName = findJsonForColumn(objectJson, parentId);
											if (parentName != null) {
												return parentName;
											}
										}
									}
								}
							}
							//从表的列
						}
					}
				}
			}
		}
		//根据表单字段的parentId获得父节点的字段名(遍历json)
		function findJsonForColumn(objectJson, parentId) {
			var isExist = false;
			for ( var key in objectJson) {
				if (key == 'id') {
					var value = objectJson[key];
					if (value == parentId) {
						isExist = true;
						break;
					}
				}
			}
			for ( var key in objectJson) {
				if (key == 'name') {
					var value = objectJson[key];
					if (isExist) {
						return value;
					}
				}
			}
		}
		//选择表名
		function onTable(e) {
			//数据源检验
			var dataSource = mini.get("#dataSourceId");
			var dataSourceValue = dataSource.getValue();
			if (dataSourceValue == null | dataSourceValue == '') {
				//mini.alert('请选择数据源');
				//return;
			}
			var btnEdit = this;
			var row = gridColumn.getSelected();
			gridColumn.cancelEdit();
			mini.open({
				url : "${ctxPath}/sys/db/sysDb/tableDialog.do?ds=" + dataSourceValue,
				title : "选择表名",
				width : 650,
				height : 380,
				ondestroy : function(action) {
					if (action == "ok") {
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.GetData();
						data = mini.clone(data); //必须
						if (data) {
							gridColumn.updateRow(row, {
								fkRefTable : data.tableName
							});
						}
					}
				}
			});
		}
	</script>
</body>
</html>