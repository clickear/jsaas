<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI" %>
<!DOCTYPE html>
<html>
<head>
<title>下拉列表</title>
<%@include file="/commons/edit.jsp"%>
<script src="${ctxPath}/scripts/share/dialog.js"></script>
</head>
<body>
	<div class="mini-toolbar">
		<a class="mini-button" iconCls="icon-save" onclick="CloseWindow('ok')">保存</a>
		<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
	</div>
	<div style="width:100%;text-align: center">
		<div style="margin-left:auto;margin-right: auto;padding:5px;">
			<form id="miniForm">
				<table class="table-detail column_2_m" cellpadding="1" cellspacing="0">
					<tr>
						<th width="120">值来源</th>
						<td colspan="3" width="85%">
							<div id="from" name="from" class="mini-radiobuttonlist" onvaluechanged="fromChanged" required="true"
							data="[{id:'self',text:'自定义'},{id:'url',text:'URL'},{id:'dic',text:'数据字典'},{id:'sql',text:'自定义SQL'}]" value="self"></div>
						</td>
					</tr>
					<tr id="url_row" style="display:none">
						<th width="15%">
							JSON URL
						</th>
						<td width="35%" colspan="2">
							<input id="url" class="mini-textbox" name="url" required="true" style="width:90%"/>
						</td>
						<td width="35%">
						&nbsp;文本字段:<input id="url_textfield" class="mini-textbox" name="url_textfield" style="width:80px" required="true">&nbsp;
						<br/>
						&nbsp;数值字段:<input id="url_valuefield" class="mini-textbox" name="url_valuefield" required="true" style="width:80px">
						</td>
					</tr>
					<tr id="dic_row" style="display:none">
						<th width="15%">
							数据字典项
						</th>
						<td width="85%" colspan="3">
							<input id="dicKey" name="dickey" class="mini-treeselect" url="${ctxPath}/sys/core/sysTree/listDicTree.do"  style="width:450px"
						        textField="name" valueField="pkId" parentField="parentId" 
						        showFolderCheckBox="false" onnodeclick="setTreeKey(e)"/>
						</td>
					</tr>
					<tr id="selfSQL_row" style="display: none">
						<th width="15%">自定SQL设置</th>
						<td colspan="3" style="padding: 5px;"><input id="sql" name="sql" style="width: 250px;" class="mini-buttonedit" onbuttonclick="onButtonEdit_sql" />&nbsp;&nbsp;&nbsp;&nbsp;文本字段:<input id="sql_textfield" name="sql_textfield" class="mini-combobox" style="width: 100px;"  data=""  valueField="fieldName"  textField="comment"  required="true" allowInput="false" enabled="false" /> &nbsp;-&nbsp;数值字段:<input id="sql_valuefield" name="sql_valuefield" class="mini-combobox" style="width: 100px;" valueField="fieldName"  textField="comment"  value="" required="true"
							allowInput="false" enabled="false" /></td>
					</tr>										
					<tr id="self_row" style="display:none">
						<th width="15%">选项</th>
						<td colspan="3" style="padding:5px;" width="85%">
							<div class="mini-toolbar" >
							    <table style="width:100%;">
							        <tr>
								        <td style="width:100%;">
								            <a class="mini-button" iconCls="icon-add" plain="true" onclick="addRow">添加</a>
								            <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeRow">删除</a>
								            <span class="separator"></span>
								            <a class="mini-button" iconCls="icon-up" plain="true" onclick="upRow">向上</a>
								            <a class="mini-button" iconCls="icon-down" plain="true" onclick="downRow">向下</a>
								        </td>
							        </tr>
							    </table>
							</div>
							<div id="props" class="mini-datagrid" style="width:100%;min-height:100px;" height="auto" showPager="false"
								allowCellEdit="true" allowCellSelect="true">
							    <div property="columns">
							        <div type="indexcolumn"></div>                
							        <div field="key" width="120" headerAlign="center">标识键
							         <input property="editor" class="mini-textbox" style="width:100%;" minWidth="120" />
							        </div>    
							        <div field="name" width="120" headerAlign="center">名称
							        	<input property="editor" class="mini-textbox" style="width:100%;" minWidth="120" />
							        </div>    
							    </div>
							</div>
						</td>
					</tr>
					<tr>
						<th>是否必填</th>
						<td>
							<input class="mini-checkbox" name="required" trueValue="true" falseValue="false" value="false">
						</td>
						<th>显示字段</th>
						<td>
							<input class="mini-combobox" name="fieldLabel" id="fieldLabel" textField="header" valueField="field" />
						</td>
					</tr>
				</table>
			</form>
			</div>
	</div>
	<script type="text/javascript">

		
			mini.parse();
			
			var form=new mini.Form('miniForm');
			var grid=mini.get('props');
			function setData(data){
				
				form.setData(data);
				mini.get('dicKey').setText(data.dicKeyName);
				mini.get('sql').setText(data.sqlName);
				mini.get('sql_textfield').setText(data.sql_textfieldName);
				mini.get('sql_valuefield').setText(data.sql_valuefieldName);
			
				grid.setData(data.selOptions);
				
				fromChanged();
			}
			function getData(){
				var data=form.getData();
				data.selOptions=grid.getData();
				data.dicKeyName=mini.get('dicKey').getText();
				data.sqlName=mini.get('sql').getText();
				data.sql_textfieldName=mini.get('sql_textfield').getText();
				data.sql_valuefieldName=mini.get('sql_valuefield').getText();

				return data;
			}
			
			function setColumnFields(fields){
				mini.get('fieldLabel').setData(fields);
			}
			
		/*点击选择自定义SQL对话框时间处理*/
		function onButtonEdit_sql(e) {
			var btnEdit = this;
			var callBack=function (data){
				btnEdit.setValue(data.key);   //设置自定义SQL的Key
				btnEdit.setText(data.name);
				_SubmitJson({                             //根据SQK的KEY获取SQL的列名和列头
					url:"${ctxPath}/sys/db/sysSqlCustomQuery/listColumnByKeyForJson.do",
					data:{
						key:data.key
					},
					showMsg:false,
					method:'POST',
					success:function(result){
						var data=result.data;
						var text=mini.get("sql_textfield");
						var value=mini.get("sql_valuefield");
						text.setEnabled(true);   //设置下拉控件为可用状态
						value.setEnabled(true);
						text.setData();          //每次选择不同SQL，清空下拉框的数据
						value.setData();
						text.setData(data.resultField);      //设置下拉框的数据
						value.setData(data.resultField);
					}
				});
			}
			openCustomQueryDialog(callBack);
		}
		
		/*数据来源点击事件处理*/
		function fromChanged(){
			var val=mini.get('from').getValue();
			if(val=='self'){
				$("#self_row").css('display','');
				$("#url_row").css('display','none');
				$("#dic_row").css('display','none');
				$("#selfSQL_row").css('display','none');
			}else if(val=='url'){
				$("#self_row").css('display','none');
				$("#url_row").css('display','');
				$("#dic_row").css('display','none');
				$("#selfSQL_row").css('display','none');
			}else if(val=='dic'){
				$("#self_row").css('display','none');
				$("#url_row").css('display','none');
				$("#dic_row").css('display','');
				$("#selfSQL_row").css('display','none');
			}else if(val=='sql'){
				$("#self_row").css('display','none');
				$("#url_row").css('display','none');
				$("#dic_row").css('display','none');
				$("#selfSQL_row").css('display','');
			}
		}
		
		//添加行
		function addRow(){
			grid.addRow({});
		}
		
		function removeRow(){
			var selecteds=grid.getSelecteds();
			if(selecteds.length>0 && confirm('确定要删除？')){
				grid.removeRows(selecteds);
			}
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
		
		function setTreeKey(e){
			var node=e.node;
			e.sender.setValue(node.key);
			e.sender.setText(node.name);
		}
	</script>
</body>
</html>
