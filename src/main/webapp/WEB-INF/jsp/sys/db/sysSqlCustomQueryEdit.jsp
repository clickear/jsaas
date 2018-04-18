<%-- 
    Document   : [自定义查询]编辑页
    Created on : 2017-02-21 15:32:09
    Author     : cjx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[自定义查询]编辑</title>
<%@include file="/commons/edit.jsp"%>
<link rel="stylesheet"
	href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/sql/sql.js"></script>
<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
<script src="${ctxPath}/scripts/share/dialog.js"></script>
<style type="text/css">
.mini-tabs-bodys {
	background: #f7f7f7;
	border: none;
}

.mini-toolbar {
	border: none;
}

.mini-tabs-scrollCt {
	border: none;
	border-bottom: 1px solid #ececec;
}

.mini-tabs-position-top .mini-tabs-header {
	margin-top: 0;
}
</style>

</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="sysSqlCustomQuery.id" />
	<form id="form1" method="post">
		<input id="pkId" name="id" class="mini-hidden"
			value="${sysSqlCustomQuery.id}" /> <input id="table" name="table"
			class="mini-hidden" value="${sysSqlCustomQuery.table}" />
		<div style="display: none;">
			<input id="valueDefTextBox" class="mini-textbox" style="width: 100%;"
				minWidth="120" /> <input id="scriptEditor" class="mini-buttonedit"
				onbuttonclick="getScript" allowInput="false" style="width: 100%;" />
			<input id="constantEditor" class="mini-combobox"
				url="${ctxPath}/sys/core/public/getConstantItem.do" valueField="key"
				textField="val" style="width: 100%;" />
		</div>

		<div id="tabs1" class="mini-tabs" activeIndex="0" plain="false"
			buttons="#tabsButtons">
			<div title="基本信息">
				<div class="heightBox"></div>
				<div class="shadowBox" style="padding-top: 8px;">
					<table class="table-detail column_2" cellspacing="1"
						cellpadding="0" style="margin-top: 0;">
						<tr>
							<th>名　　称</th>
							<td><input name="name" value="${sysSqlCustomQuery.name}"
								class="mini-textbox" style="width: 90%" /></td>
						</tr>
						<tr>
							<th>标　　识</th>
							<td><input required="true" name="key"
								value="${sysSqlCustomQuery.key}" class="mini-textbox"
								style="width: 90%" /></td>
						</tr>
						<tr>
							<th>数  据  源</th>
							<td>
								<input name="dsAlias"
									value="${sysSqlCustomQuery.dsAlias}" 
									id="dsAlias"
									style="width: 350px;" 
									text="${sysSqlCustomQuery.dsAlias}"
									class="mini-buttonedit" 
									showClose="true"
									onbuttonclick="onDatasource" 
									oncloseclick="onCloseClick(e)" 
								/>
							</td>
						</tr>
						<tr>
							<th>查询类型</th>
							<td>
								<div 
									class="mini-radiobuttonlist" 
									textField="text"
									valueField="id" 
									value="${sysSqlCustomQuery.sqlBuildType}"
									data="[{id:'table',text:'基于表或视图'},{id:'sql',text:'Groovy Sql'},{id:'freeMakerSql',text:'Freemarker Sql'}]"
									name="sqlBuildType" 
									id="sqlBuildType"
									onvaluechanged="changeQueryType"
								></div>
							</td>
						</tr>
						<tr id="tableTR">
							<th>选择对象</th>
							<td>
								<input 
									name="tableName"
									value="${sysSqlCustomQuery.tableName}" 
									id="tableName"
									style="width: 350px;" 
									text="${sysSqlCustomQuery.tableName}"
									class="mini-buttonedit" 
									showClose="true" 
									onbuttonclick="onTable"
									oncloseclick="onCloseClick(e)" 
								/>
							</td>
						</tr>
						<tr id="sqlTR">
							<th>输入SQL</th>
							<td style="padding: 4px 0 0 0 !important;">
								<div id="conditionDiv">
									支持freemaker语法 条件字段:
									<input 
										id="freemarkColumn"
										class="mini-combobox" 
										showNullItem="true"
										nullItemText="可选条件列头" 
										valueField="fieldName"
										textField="fieldLabel" 
										onvalueChanged="varsFreeChanged" 
									/>
									常量:
									<input 
										id="constantItem" 
										class="mini-combobox"
										showNullItem="true" 
										nullItemText="可用常量"
										url="${ctxPath}/sys/core/public/getConstantItem.do"
										valueField="key" 
										textField="val"
										onvalueChanged="constantFreeChanged" 
									/>
								</div> <textarea id="sql" emptyText="请输入sql" rows="5" cols="100"
									width="500" style="height: 50px;">${sysSqlCustomQuery.sql}</textarea>
							</td>
						</tr>

						<tr>
							<th>是否分页</th>
							<td><input id="isPage" name="isPage"
								value="${sysSqlCustomQuery.isPage}" class="mini-radiobuttonlist"
								valueField="id" textField="text"
								data="[{'id':1,'text':'是'},{'id':0,'text':'否'}]" /></td>
						</tr>
						<tr>
							<th>分页大小</th>
							<td><input name="pageSize"
								value="${sysSqlCustomQuery.pageSize}" minValue="1"
								maxValue="1000" class="mini-spinner" required="true" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div title="返回字段" showCloseButton="false">
				<div class="mini-toolbar"
					style="padding: 2px; text-align: left; border-bottom: none;">
					<a class="mini-button" iconCls="icon-setting" plain="true"
						onclick="selectColumnQuery">列头设置</a> <a class="mini-button"
						iconCls="icon-remove" plain="true"
						onclick="removeRow('gridQuery')">删除</a> <span class="separator"></span>
					<a class="mini-button" iconCls="icon-up" plain="true"
						onclick="upRow('gridQuery')">向上</a> <a class="mini-button"
						iconCls="icon-down" plain="true" onclick="downRow('gridQuery')">向下</a>
				</div>
				<div id="gridQuery" class="mini-datagrid" style="width: 100%;"
					showPager="false" allowCellEdit="true" allowCellSelect="true"
					allowAlternating="true">
					<div property="columns">
						<div type="indexcolumn">序号</div>
						<div field="comment" width="120" headerAlign="center">
							字段注解 <input property="editor" class="mini-textbox" />
						</div>
						<div field="fieldName" width="120" headerAlign="center">
							字段名 <input property="editor" class="mini-textbox" />
						</div>
					</div>
				</div>
			</div>
			<div title="条件的列" showCloseButton="false">
				<div class="mini-toolbar"
					style="padding: 2px; text-align: left; border-bottom: none;">
					<a class="mini-button" iconCls="icon-setting" plain="true"
						onclick="selectColumnWhere">列头设置</a> <a class="mini-button"
						iconCls="icon-remove" plain="true"
						onclick="removeRow('gridWhere')">删除</a> <span class="separator"></span>
					<a class="mini-button" iconCls="icon-up" plain="true"
						onclick="upRow('gridWhere')">向上</a> <a class="mini-button"
						iconCls="icon-down" plain="true" onclick="downRow('gridWhere')">向下</a>
				</div>
				<div id="gridWhere" class="mini-datagrid" style="width: 100%;"
					showPager="false" allowCellEdit="true" allowCellSelect="true"
					oncellbeginedit="gridWhereCellBeginEdit"
					oncellendedit="gridWhereCellEndEdit" allowAlternating="true">
					<div property="columns">
						<div type="indexcolumn" width="40">序号</div>
						<div field="comment" width="120" headerAlign="center">
							字段注解 <input property="editor" class="mini-textbox" />
						</div>
						<div field="fieldName" width="120" headerAlign="center">
							字段名 <input property="editor" class="mini-textbox" />
						</div>
						<div field="columnType" width="60" headerAlign="center">
							数据类型</div>
						<div name="typeOperate" displayField="typeOperate_name"
							valueField="id" textField="text" field="typeOperate"
							vtype="required" width="100" align="center" headerAlign="center">
							操作类型 <input property="editor" class="mini-combobox"
								valueField="id" textField="text" />
						</div>
						<div name="valueSource" field="valueSource" vtype="required"
							width="100" renderer="onvalueSourceRenderer" align="center"
							headerAlign="center" editor="valueSourceEditor">
							值来源 <input property="editor" class="mini-combobox"
								data="valueSource" />
						</div>
						<div name="valueDef" field="valueDef" width="180"
							headerAlign="center">默认值</div>
					</div>
				</div>
				<div id="divWhereJson" style="display: none;">${sysSqlCustomQuery.whereField}</div>
			</div>
			<div title="排序" showCloseButton="false">

				<div class="mini-toolbar"
					style="padding: 2px; text-align: left; border-bottom: none;">
					<a class="mini-button" iconCls="icon-setting" plain="true"
						onclick="selectColumnOrder">列头设置</a> <a class="mini-button"
						iconCls="icon-remove" plain="true"
						onclick="removeRow('gridOrder')">删除</a> <span class="separator"></span>
					<a class="mini-button" iconCls="icon-up" plain="true"
						onclick="upRow('gridOrder')">向上</a> <a class="mini-button"
						iconCls="icon-down" plain="true" onclick="downRow('gridOrder')">向下</a>
				</div>
				<div id="gridOrder" class="mini-datagrid" style="width: 100%;"
					showPager="false" allowCellEdit="true" allowCellSelect="true"
					allowAlternating="true">
					<div property="columns">
						<div type="indexcolumn">序号</div>
						<div field="comment" width="120" headerAlign="center">
							字段注解 <input property="editor" class="mini-textbox" />
						</div>
						<div field="fieldName" width="120" headerAlign="center">
							字段名 <input property="editor" class="mini-textbox" />
						</div>
						<div field="typeOrder" vtype="required" width="100"
							renderer="onTypeOrderRenderer" align="center"
							headerAlign="center">
							排序类型 <input property="editor" class="mini-combobox"
								data="typeOrder" />
						</div>
					</div>
				</div>


				<div id="divOrderFieldJson" style="display: none;">${sysSqlCustomQuery.orderField}</div>
			</div>

			<div title="自定义SQL" showCloseButton="false" name="customSQL">
				<div>
					<div>
						条件字段:<input id="availableColumn" class="mini-combobox"
							showNullItem="true" nullItemText="可选条件列头" valueField="fieldName"
							textField="fieldLabel" onvalueChanged="varsChanged" /> 常量:<input
							id="constantItem" class="mini-combobox" showNullItem="true"
							nullItemText="可用常量"
							url="${ctxPath}/sys/core/public/getConstantItem.do"
							valueField="key" textField="val" onvalueChanged="constantChanged" />
					</div>
					<ul>
						<li>params存放传入参数，为一个Map数据结构。</li>
						<li>可以使用params.containsKey("变量名")判断上下文是否有对应的变量。</li>
					</ul>

					<textarea id="sqlDiy" emptyText="请输入sql" rows="10" cols="100"
						width="500" height="250">${sysSqlCustomQuery.sqlDiy}</textarea>
				</div>
			</div>

		</div>
	</form>

	<rx:formScript formId="form1" baseUrl="sys/db/sysSqlCustomQuery"
		entityName="com.redxun.sys.db.entity.SysSqlCustomQuery" />
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form("form1");
		var id = "${sysSqlCustomQuery.id}";
		var tableType = "${sysSqlCustomQuery.table}";
		var oldTable = "${sysSqlCustomQuery.tableName}";
		var newTable = "${sysSqlCustomQuery.tableName}";
		var changeFlag = true;
		var oldSource = "";
		var tabObject = mini.get("tabs1");

		$(function() {
			if ("${sysSqlCustomQuery.id}" == "") {
				mini.get("isPage").setValue(0);
			}

			initType();
			loadQuery();
		});

		tabObject.on("beforeactivechanged", function(e) {
			if (e.tab.name == "customSQL") {
				var sql = sqlEditor.getValue() || "";
				if (!sql.trim()) {
					alert("请输入SQL!");
					e.cancel = true;
				}
			}
		});

		tabObject.on("activechanged", function(e) {
			if (e.tab.name == "customSQL") {
				editor.refresh();
				var sql = sqlEditor.getValue() || "";
				var tmp = editor.getValue();
				tmp = (tmp || "").trim();
				if (!tmp) {
					sql = "String sql=\"" + sql + "\";\r\n return sql;"
					editor.setValue(sql);
					editor.focus();
				}
			}
		});

		function changeQueryType(e) {
			initType();
			//清除数据
			clearData();
		}

		function clearData() {
			mini.get("gridQuery").setData();
			mini.get("gridWhere").setData();
			mini.get("gridOrder").setData();
		}

		function initType() {
			var type = mini.get("sqlBuildType").getValue();
			var sqlPanel = tabObject.getTab("customSQL");
			var conditionDiv = $("#conditionDiv");

			if (type == "table") {
				$("#tableTR").show();
				$("#sqlTR").hide();
				//sqlPanel.enabled=false;
				toggleCondtionColumns(true);
				toggleTab(false);

			} else if (type == "sql") {
				$("#tableTR").hide();
				$("#sqlTR").show();
				//sqlPanel.enabled=true;
				toggleCondtionColumns(false);
				toggleTab(true);
				conditionDiv.hide();
			} else {
				$("#tableTR").hide();
				$("#sqlTR").show();
				//sqlPanel.enabled=false;
				toggleCondtionColumns(false);
				toggleTab(false);
				conditionDiv.show();
			}
		}

		function toggleTab(show) {
			var sqlPanel = tabObject.getTab("customSQL");
			tabObject.updateTab(sqlPanel, {
				visible : show
			});
		}

		function toggleCondtionColumns(show) {
			var grid = mini.get("gridWhere");
			var aryCol = [ "typeOperate", "valueSource", "valueDef" ];
			for (var i = 0; i < aryCol.length; i++) {
				var col = grid.getColumn(aryCol[i]);
				show ? grid.showColumn(col) : grid.hideColumn(col)
			}
		}

		function textChanged(e) {
			var row = mini.get("gridWhere").getSelected();
			var obj = $("#valueDefTextAreaSpan");
			mini.get("gridWhere").updateRow(row, {
				valueDef : e.value
			});
		}

		var numberOperateJson = [ {
			"id" : "=",
			"text" : "等于"
		}, {
			"id" : "!=",
			"text" : "不等于"
		}, {
			"id" : "<",
			"text" : "小于"
		}, {
			"id" : "<=","text":"小于等于"},{"id":">",
			"text" : "大于"
		}, {
			"id" : ">=",
			"text" : "大于等于"
		}, {
			"id" : "BETWEEN",
			"text" : "between"
		} ];
		var stringOperateJson = [ {
			"id" : "=",
			"text" : "等于"
		}, {
			"id" : "!=",
			"text" : "不等于"
		}, {
			"id" : "LI",
			"text" : " 模糊查询 "
		}, {
			"id" : "LL",
			"text" : " 左模糊查询 "
		}, {
			"id" : "LR",
			"text" : " 右模糊查询 "
		}, {
			"id" : "IN",
			"text" : "in"
		} ];
		var dateOperateJson = [ {
			"id" : "=",
			"text" : "等于"
		}, {
			"id" : "!=",
			"text" : "不等于"
		}, {
			"id" : "<",
			"text" : "小于"
		}, {
			"id" : "<=","text":"小于等于"},{"id":">",
			"text" : "大于"
		}, {
			"id" : ">=",
			"text" : "大于等于"
		}, {
			"id" : "BETWEEN",
			"text" : "between"
		} ];

		function loadQuery() {

			if (!id)
				return;

			var url = "${ctxPath}/sys/db/sysSqlCustomQuery/getById.do?id=" + id;

			$.get(url, function(data) {
				var resultFieldStr = data.resultField;
				var resultField = null;
				if (resultFieldStr != '') {
					resultFieldStr = resultFieldStr.replace(/\n/g, "").replace(
							/\r/g, "");
					resultField = mini.decode(resultFieldStr);
				}
				mini.get('gridQuery').setData(resultField);

				var whereFieldStr = data.whereField;

				var whereField = [];
				if (whereFieldStr != '') {
					whereField = eval(whereFieldStr);
				}

				addFieldLabel(whereField);
				mini.get('gridWhere').setData(whereField);

				var whereData = $.clone(whereField);
				var freemarkColumn = mini.get("freemarkColumn");
				freemarkColumn.setData(whereData);

				var availableColumn = mini.get("availableColumn");
				availableColumn.setData(whereData);

				var orderFieldStr = data.orderField;
				var orderField = null;
				if (orderFieldStr != '') {
					orderField = mini.decode(orderFieldStr);
				}
				mini.get('gridOrder').setData(orderField);
			})
		}

		function addFieldLabel(whereField) {
			for (var i = 0; i < whereField.length; i++) {
				var o = whereField[i];
				o.fieldLabel = o.comment;
			}
		}

		mini
				.get("gridWhere")
				.on(
						"drawcell",
						function(e) {
							var record = e.record, field = e.field, value = e.value;
							if (field == "typeOperate") {
								if (value != ''
										&& (record.columnType == 'varchar' || record.columnType == 'clob'))
									e.cellHtml = getValueByKeyInJsonArray(
											value, stringOperateJson);
								else if (value != ''
										&& record.columnTyp == 'number') {
									e.cellHtml = getValueByKeyInJsonArray(
											value, numberOperateJson);
								} else if (value != '' && value == 'date') {
									e.cellHtml = getValueByKeyInJsonArray(
											value, dateOperateJson);
								}
							}

						});

		function getValueByKeyInJsonArray(key, array) {
			var value = "";
			for (var i = 0; i < array.length; i++) {
				if (array[i].id == key) {
					value = array[i].text;
					break;
				}
			}
			return value;
		}

		function upRow(id) {
			var headGrid = mini.get(id);

			var row = headGrid.getSelected();
			if (row) {
				var index = headGrid.indexOf(row);
				headGrid.moveRow(row, index - 1);
			}
		}
		function downRow(id) {
			var headGrid = mini.get(id);
			var row = headGrid.getSelected();
			if (row) {
				var index = headGrid.indexOf(row);
				headGrid.moveRow(row, index + 2);
			}
		}
		function removeRow(id) {
			var headGrid = mini.get(id);
			var selecteds = headGrid.getSelecteds();
			if (selecteds.length > 0 && confirm('确定要删除？')) {
				headGrid.removeRows(selecteds);
			}
		}

		function varsChanged(e) {
			var val = e.value;
			insertVal(editor, val);
		}

		function constantChanged(e) {
			var val = e.value;
			insertVal(editor, val);
		}

		function CloseWindow(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}
		function onOk(e) {
			CloseWindow("ok");
		}
		function onCancel(e) {
			CloseWindow("cancel");
		}
		//选择数据源
		function onDatasource(e) {
			var btnEdit = this;
			var callBack = function(data) {
				btnEdit.setValue(data.alias);
				btnEdit.setText(data.name);
			}
			openDatasourceDialog(callBack);
		}
		//选择表
		function onTable(e) {
			var btnEdit = this;
			var dsId = mini.get("dsAlias").getValue();
			var url = "${ctxPath}/sys/db/sysDb/tableDialog.do";
			url = url + "?ds=" + dsId;
			mini.open({
				url : url,
				title : "选择表",
				width : 650,
				height : 380,
				ondestroy : function(action) {
					if (action != "ok")
						return;

					var iframe = this.getIFrameEl();
					var data = iframe.contentWindow.GetData();
					data = mini.clone(data); //必须
					if (data) {
						btnEdit.setValue(data.tableName);
						btnEdit.setText(data.tableName);
						tableType = data.type == 'table' ? 1 : 0;

						clearData();
					}

				}
			});
		}
		//选择列
		function selectColumnQuery(e) {
			showDialog(function(data) {
				addData("gridQuery", data);
			})
		}

		function showDialog(callBack) {
			var queryType = mini.get("sqlBuildType").getValue();
			var dsId = mini.get("dsAlias").getValue();
			var query = "";
			if (queryType == "table") {
				var tableName = mini.get("tableName").getValue();
				if (tableName == "") {
					alert("请先选择对象!");
					return;
				}
				query = tableName;
			} else {
				var sql = sqlEditor.getValue();
				if (!sql) {
					alert("请填写SQL!");
					return;
				}
				query = sql;
			}

			var url = "${ctxPath}/sys/db/sysDb/columnQueryDialog.do";
			
			mini.open({
				url : url,
				showMaxButton : true,
				title : "设置列名",
				width : 650,
				height : 380,
				onload : function() {
					var iframe = this.getIFrameEl();
					iframe.contentWindow.loadData(queryType,query,dsId);
				},
				ondestroy : function(action) {
					if (action != "ok")
						return;
					var iframe = this.getIFrameEl();
					var data = iframe.contentWindow.GetData();
					if (callBack) {
						callBack(data);
					}
				}
			});
		}

		function addData(gridId, data) {
			var grid = mini.get(gridId);
			var oldData = grid.getData();

			var addData = mini.decode(data);
			var json = getJson(oldData)

			for (var i = 0; i < addData.length; i++) {
				if (!json[addData[i].fieldName]) {
					var row = addData[i];
					row.comment = row.fieldLabel;
					//DataType//columnType
					var datatype = row.dataType;
					if (datatype == "string") {
						datatype = "varchar";
					}
					row.columnType = datatype;
					oldData.push(row);
				}
			}
			grid.setData(oldData);
		}

		function getJson(oldData) {
			var obj = {};
			for (var j = 0; j < oldData.length; j++) {
				obj[oldData[j].fieldName] = 1;
			}
			return obj;
		}

		//选择列
		function selectColumnWhere(e) {
			showDialog(function(data) {
				addData("gridWhere", data);

				var freemarkColumn = mini.get("freemarkColumn");
				freemarkColumn.setData(data);

				var availableColumn = mini.get("availableColumn");
				availableColumn.setData(data);

			})
		}
		//选择列
		function selectColumnOrder(e) {
			showDialog(function(data) {
				addData("gridOrder", data);
			})
		}

		var valueSource = [ {
			id : "param",
			text : '参数传入'
		}, {
			id : "fixedVar",
			text : '固定值'
		}, {
			id : "script",
			text : '脚本'
		}, {
			id : "constantVar",
			text : '常量'
		} ];
		function onvalueSourceRenderer(e) {
			for (var i = 0, l = valueSource.length; i < l; i++) {
				var g = valueSource[i];
				if (g.id == e.value)
					return g.text;
			}
			return "";
		}
		var typeOrder = [ {
			id : 'ASC',
			text : '升序'
		}, {
			id : 'DESC',
			text : '降序'
		} ];
		function onTypeOrderRenderer(e) {
			for (var i = 0, l = typeOrder.length; i < l; i++) {
				var g = typeOrder[i];
				if (g.id == e.value)
					return g.text;
			}
			return "";
		}

		var editor = null;
		var sqlEditor = null;
		function initCodeMirror() {
			var obj = document.getElementById("sqlDiy");
			editor = CodeMirror.fromTextArea(obj, {
				matchBrackets : true,
				mode : "text/x-groovy"
			});

			var sqlObj = document.getElementById("sql");
			sqlEditor = CodeMirror.fromTextArea(sqlObj, {
				lineNumbers : true,
				matchBrackets : true,
				mode : "text/x-sql"
			});
			sqlEditor.setSize('auto', '100px');
		}

		initCodeMirror();

		//保存
		function selfSaveData() {

			form.validate();
			if (!form.isValid())
				return;

			var dataGridQuery = mini.get('gridQuery').getData();
			var queryData = [];
			for (var i = 0; i < dataGridQuery.length; i++) {
				var obj = {};
				obj.comment = dataGridQuery[i].comment;
				obj.fieldName = dataGridQuery[i].fieldName;
				queryData.push(obj);
			}
			if (queryData.length < 1) {
				alert("查询列必须设置！");
				return;
			}
			//获取自定义查询SQL构建类型
			var type = mini.get("sqlBuildType").getValue();

			var dataGridWhere = mini.get('gridWhere').getData();
			var whereData = [];

			for (var i = 0; i < dataGridWhere.length; i++) {
				var obj = {};
				obj.comment = dataGridWhere[i].comment;
				obj.fieldName = dataGridWhere[i].fieldName;
				obj.columnType = dataGridWhere[i].columnType;
				obj.typeOperate = dataGridWhere[i].typeOperate;
				obj.valueSource = (type == "table") ? dataGridWhere[i].valueSource
						: "param";

				obj.valueDef = dataGridWhere[i].valueDef;
				whereData.push(obj);
			}

			var dataGridOrder = mini.get('gridOrder').getData();
			var orderData = [];
			for (var i = 0; i < dataGridOrder.length; i++) {
				var obj = {};
				obj.comment = dataGridOrder[i].comment;
				obj.fieldName = dataGridOrder[i].fieldName;
				obj.typeOrder = dataGridOrder[i].typeOrder;
				orderData.push(obj);
			}
			var queryField = {
				name : 'resultField',
				value : queryData
			}
			var whereField = {
				name : 'whereField',
				value : whereData
			}
			var orderField = {
				name : 'orderField',
				value : orderData
			}
			var formData = $("#form1").serializeArray();

			if (queryData.length > 0)
				formData.push(queryField);
			if (whereData.length > 0)
				formData.push(whereField);
			if (orderData.length > 0)
				formData.push(orderField);

			for (var i = 0; i < formData.length; i++) {
				if (formData[i]['name'] == 'table') {
					formData[i]['value'] = tableType == 1 ? '1' : '0';
					break;
				}
			}

			if (type == "sql") {
				formData.push({
					name : "sqlDiy",
					value : editor.getValue()
				});
			}
			if (type == "sql" || type == "freeMakerSql") {
				formData.push({
					name : "sql",
					value : sqlEditor.getValue()
				});
			}

			var jsonObj = formToObject(formData);

			var formJson = JSON.stringify(jsonObj);

			var postData = {
				json : formJson
			}
			$.ajax({
				url : "${ctxPath}/sys/db/sysSqlCustomQuery/save.do",
				type : "POST",
				data : postData,
				success : function(result) {
					//进行提示
					_ShowTips({
						msg : result.message
					});
					onOk();
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("出错,错误码=" + XMLHttpRequest.status);
					alert("出错=" + XMLHttpRequest.responseText);
				}
			});
		}

		//单元格开始编辑事件处理
		function gridWhereCellBeginEdit(e) {
			var field = e.field;
			var record = e.record;
			if (field == "typeOperate") {
				if (record.columnType == 'varchar'
						|| record.columnType == 'clob') {
					e.editor.setData(stringOperateJson);
				} else if (record.columnType == 'number') {
					e.editor.setData(numberOperateJson);
				} else if (record.columnType == 'date') {
					e.editor.setData(dateOperateJson);
				}
			} else if (field == 'valueDef') {
				if (record.valueSource == '' || !record.valueSource)
					e.cancel = true;
				else if (record.valueSource == 'param')
					e.cancel = true;
				else if (record.valueSource == 'script')
					e.editor = mini.get("scriptEditor");
				else if (record.valueSource == 'constantVar')
					e.editor = mini.get("constantEditor");
				else
					e.editor = mini.get("valueDefTextBox");
			}
			e.column.editor = e.editor;
			curGrid = e.sender;
		}

		function gridWhereCellEndEdit(e) {
			var field = e.field;
			var record = e.record;
			if (field == "valueSource") {
				if (oldSource != e.value) {
					mini.get("gridWhere").updateRow(e.row, {
						valueDef : ""
					});
				}
				oldSource = e.value;
			}
		}

		//清除控件值
		function onCloseClick(e) {
			var btn = e.sender;
			btn.setText('');
			btn.setValue('');
		}

		function getScript(e) {
			var buttonEdit = e.sender;
			var row = mini.get("gridWhere").getSelected();
			var scriptContent = typeof (row.valueDef) == 'undefined' ? ""
					: row.valueDef;

			_OpenWindow({
				url : __rootPath + "/sys/db/sysDb/scriptDialog.do",
				title : "脚本内容",
				height : "450",
				width : "600",
				onload : function() {
					var iframe = this.getIFrameEl();
					iframe.contentWindow.setScript(scriptContent);
				},
				ondestroy : function(action) {
					if (action != 'ok')
						return;
					var iframe = this.getIFrameEl();
					var data = iframe.contentWindow.getScriptContent();
					buttonEdit.value = data;
					buttonEdit.text = data;
				}
			});
		}

		function constantFreeChanged(e) {
			var val = e.value;
			insertVal(sqlEditor, val);
		}

		function insertVal(editor, val) {
			var doc = editor.getDoc();
			var cursor = doc.getCursor(); // gets the line number in the cursor position
			doc.replaceRange(val, cursor); // adds a new line
		}

		function varsFreeChanged(e) {
			var val = e.value;
			insertVal(sqlEditor, val);
		}
	</script>
</body>
</html>


