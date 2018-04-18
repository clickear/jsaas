<%-- 
    Document   : 定时器编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>定时器编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar topBtn">
		<a class="mini-button" iconCls="icon-save" plain="true" onclick="save">保存</a>
		<!-- 		<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a> -->
	</div>

	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<table class="table-detail column_2_m" cellspacing="1"
					cellpadding="0">
					<caption>定时器任务基本信息</caption>
					<tr>
						<th><span class="starBox"> 任务名称<span class=star>*</span>
						</span></th>
						<td><input required="true" name="name" emptyText="请输入名称"
							value="" class="mini-textbox" vtype="maxLength:255"
							style="width: 50%" /></td>
					</tr>
					<tr>
						<th>
							<span class="starBox"> 
								类 　　名<span class=star>*</span>
							</span>
						</th>
						<td><input required="true" name="className" value=""
							class="mini-textbox" vtype="maxLength:255" style="width: 80%"
							emptyText="输入类全路径" /></td>
					</tr>
					<tr>
						<th>任务描述</th>
						<td><textarea name="description" class="mini-textarea"
								emptyText="任务描述" style="width: 80%;"></textarea></td>
					</tr>
				</table>



				<div class="mini-toolbar"
					style="padding: 2px; text-align: left; border-bottom: none; margin-top: 4px;">
					<table style="width: 100%;">
						<tr>
							<td style="width: 100%;"><a class="mini-button"
								iconCls="icon-add" plain="true" onclick="addRow">添加</a> <a
								class="mini-button" iconCls="icon-remove" plain="true"
								onclick="removeRow">删除</a> <span class="separator"></span> <a
								class="mini-button" iconCls="icon-up" plain="true"
								onclick="upRow">向上</a> <a class="mini-button"
								iconCls="icon-down" plain="true" onclick="downRow">向下</a></td>
						</tr>
					</table>
				</div>

				<div id="ruleGrid" class="mini-datagrid"
					style="width: 100%; height: 300px; margin-top: 10px;" height="auto"
					showPager="false" allowCellEdit="true" allowCellSelect="true"
					allowAlternating="true">
					<div property="columns">
						<div type="indexcolumn" width="40">序号</div>
						<div field="type" width="50" headerAlign="center">
							类型 <input property="editor" class="mini-combobox"
								allowInput="true"
								data="[{id:'Boolean',text:'Boolean'},{id:'String',text:'String'},{id:'Integer',text:'Integer'},{id:'Long',text:'Long'},{id:'Float',text:'Float'}]"
								style="width: 100%;" minWidth="120" />
						</div>
						<div field="name" width="120" headerAlign="center">
							参数名 <input property="editor" class="mini-textbox"
								style="width: 100%" required="true" />
						</div>
						<div field="value" width="120" headerAlign="center">
							参数值<input property="editor" class="mini-textbox"
								style="width: 100%" required="true" />
						</div>
					</div>
				</div>

			</div>
		</form>
	</div>


	<script type="text/javascript">
		addBody();
		mini.parse();
		var grid = mini.get('ruleGrid');

		var beforeCallBack = function(formData) {
			var data = grid.getData();
			var json = JSON.stringify(data);
			var obj = {
				name : "parameterJson",
				value : json
			};
			formData.push(obj);
		};

		function save() {
			var url = __rootPath + "/sys/core/scheduler/addJob.do";
			_SaveData("form1", url, function(result) {
				CloseWindow('ok');
			}, beforeCallBack);
		}

		//添加行
		function addRow() {
			grid.addRow({
				type : "String",
				name : "",
				value : ""
			});
		}
		function removeRow() {
			var selecteds = grid.getSelecteds();
			grid.removeRows(selecteds);
		}
		function upRow() {
			var row = grid.getSelected();
			if (row) {
				var index = grid.indexOf(row);
				grid.moveRow(row, index - 1);
			}
		}
		function downRow() {
			var row = grid.getSelected();
			if (row) {
				var index = grid.indexOf(row);
				grid.moveRow(row, index + 2);
			}
		}

		$(function() {
		});

		function onCancel() {
			CloseWindow('cancel');
		}
	</script>
</body>
</html>