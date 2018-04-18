
<%-- 
    Document   : [自定义属性]编辑页
    Created on : 2017-12-14 14:02:29
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[自定义属性]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="osCustomAttribute.id" />
	<div id="p1" class="form-outer shadowBox90" style="padding-top:8px;">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${osCustomAttribute.ID}" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<tr>
						<th>名　　称</th>
						<td ><input name="attributeName" value="${osCustomAttribute.attributeName}" class="mini-textbox" style="width: 90%" /></td>
					</tr>
					<tr>
						<th>键　　名</th>
						<td ><input name="key" value="${osCustomAttribute.key}" class="mini-textbox" style="width: 90%" /></td>
					</tr>
					<tr>
						<th>属性类型</th>
						<td >
							<div name="attributeType" id="attributeType" class="mini-radiobuttonlist"
								 textField="text" valueField="id" value="${osCustomAttribute.attributeType}" onvaluechanged="changeAttType"
								data="[{'text':'用户类型','id':'User'},{'text':'用户组类型','id':'Group'}]"></div>
						</td>
					</tr>
					<tr>
						<th>分　　类</th>
						<td ><input class="mini-treeselect" name="treeId" id="treeId"
							url="${ctxPath}/sys/customform/sysCustomFormSetting/getTreeByCat.do?catKey=CAT_CUSTOMATTRIBUTE" multiSelect="false" textField="name"
							valueField="treeId" checkRecursive="false" showFolderCheckBox="false" expandOnLoad="true" value="${osCustomAttribute.treeId}"
							popupWidth="200" /></td>
					</tr>
					<tr id="dimension">
						<th>维　　度</th>
						<td ><input class="mini-treeselect" id="dimId" name="dimId" url="${ctxPath}/sys/org/osDimension/getAllDimansion.do"
							multiSelect="false" textField="name" valueField="dimId" checkRecursive="false" showFolderCheckBox="false" expandOnLoad="true"
							value="${osCustomAttribute.dimId}" popupWidth="200" /></td>
					</tr>
					<tr>
						<th>控件类型</th>
						<td ><input class="mini-combobox" name="widgetType" id="widgetType" style="width: 150px;" textField="text"
							valueField="id" value="${osCustomAttribute.widgetType}" allowInput="false" onvaluechanged="showSource"
							data="[{'text':'文本控件','id':'textbox'},{'text':'数字控件','id':'spinner'},{'text':'时间控件','id':'datepicker'},{'text':'下拉框','id':'combobox'},{'text':'多选框','id':'radiobuttonlist'}]" />
						</td>
					</tr>
					<tr class="datasource" style="display: none;">
						<th>来源类型</th>
						<td><div name="sourceType" id="sourceType" class="mini-radiobuttonlist"
								 textField="text" valueField="id" value="${osCustomAttribute.sourceType}"
								data="[{'text':'url','id':'URL'},{'text':'自定义','id':'CUSTOM'}]" onvaluechanged="changeSourceType">
						</td>
						
					</tr>
					<tr class="datasource" style="display: none;">
					<th>值  来  源</th>
						<td id="sourceTD"></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/org/osCustomAttribute" entityName="com.redxun.sys.org.entity.OsCustomAttribute" />

	<script type="text/javascript">
		addBody();
		mini.parse();
		var widgetType=mini.get("widgetType");
		var sourceType=mini.get("sourceType");
		var valueSource=mini.get("valueSource");
		var attributeType=mini.get("attributeType");
		var dimId=mini.get("dimId");
		
		showSource();
		changeSourceType();
		initGridData();
		changeAttType();
		
		function showSource(){
			var wType=widgetType.getValue();
			if(wType=="combobox"||wType=="radiobuttonlist"){
				$(".datasource").show();
			}else{
				$(".datasource").hide();
			}
		}
		function changeSourceType(){
			var sType=sourceType.getValue();
			if(sType=="URL"){
				$("#sourceTD").html('<input id="valueSource" name="valueSource" value="${osCustomAttribute.valueSource}" class="mini-textbox" style="width: 90%" />');
			}else if(sType=="CUSTOM"){
				$("#sourceTD").html('<div id="toolbar1" class="mini-toolbar" ><a class="mini-button" iconCls="icon-add" plain="true" onclick="addData">添加数据</a><a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeData">删除数据</a></td></div><div id="sourceData" name="sourceData" class="mini-datagrid" style="width: 100%;" allowCellEdit="true" height="auto" allowCellEdit="true" allowCellSelect="true"  idField="groupId" showPager="false"><div property="columns"><div field="text" name="text" width="160">名<input property="editor" class="mini-textbox" required="true" style="width:100%;" minWidth="200" /></div><div field="id" name="id" width="200">值<input property="editor" class="mini-textbox" required="true" style="width:100%;" minWidth="200" /></div></div></div>');
			}
			mini.parse();
		}
		
		function addData(){
			var grid=mini.get("sourceData");
			grid.addRow({});
		}
		function removeData(){
			var grid=mini.get("sourceData");
		    var row = grid.getSelected()
			if (!row) {
				alert('请选择需要删除的行!');
				return;
			}
			grid.removeRow(row); 
		}
		
		//动态设置每列的编辑器
		function changeColumnEditor(e) {
			var field = e.field, rs = e.record;
			if (field == "relTypeName") {
				e.editor = mini.get('relTypeEditor');
			} else if (field == 'partyName1') {
				if (!rs.relTypeName) {
					return;
				}
				if (rs.relTypeCat == 'GROUP-USER') {
					e.editor = mini.get('groupEditor');
				} else {
					e.editor = mini.get('userEditor');
				}
			}
			e.column.editor = e.editor;
		}
		
		function initGridData(){
			var widgetTypeValue=widgetType.getValue();
	        if((widgetTypeValue!='textbox'||widgetTypeValue!='spinner'||widgetTypeValue!='datepicker')&&sourceType.getValue()=="CUSTOM"){
	        	var grid=mini.get("sourceData");
				var gridData=JSON.parse('${osCustomAttribute.valueSource}');
				grid.setData(gridData);
	        }
			
		}
		
		function changeAttType(){
			var attrType=attributeType.getValue();
			if(attrType=="User"){
				$("#dimension").hide();
			}else{
				$("#dimension").show();
			}
		}
		
		function selfSaveData(){
			form.validate();
			if(!specialValidation()||!form.isValid()){
				mini.showTips({
		            content: "<b>注意</b> <br/>表单有内容尚未填写",
		            state: 'danger',
		            x: 'center',
		            y: 'center',
		            timeout: 3000
		        });
				return;
			}
			var formData=form.getData();
		        var widgetTypeValue=widgetType.getValue();
		        if((widgetTypeValue!='textbox'&&widgetTypeValue!='spinner'&&widgetTypeValue!='datepicker')&&sourceType.getValue()=="CUSTOM"){
		        	var grid=mini.get("sourceData");
		        	formData.valueSource=JSON.stringify(grid.getData());
		        }
		        var config={
		        	url:"${ctxPath}/sys/org/osCustomAttribute/save.do",
		        	method:'POST',
		        	data:formData,
		        	success:function(result){
		        		//如果存在自定义的函数，则回调
		        		if(isExitsFunction('successCallback')){
		        			successCallback.call(this,result);
		        			return;	
		        		}
		        		
		        		CloseWindow('ok');
		        	}
		        }
		        _SubmitJson(config);
		}
		
		function specialValidation(){
			var widgetTypeValue=widgetType.getValue();
			if((widgetTypeValue!='textbox'&&widgetTypeValue!='spinner'&&widgetTypeValue!='datepicker')){
				var sourceTypeValue=sourceType.getValue();
				if(sourceTypeValue==""||sourceTypeValue==null){//没选类型
					return false;
				}else if(sourceTypeValue=="URL"){
					if(mini.get("valueSource").getValue()==""||mini.get("valueSource").getValue()==null){//没填URL
						return false
					}
				}else if(sourceTypeValue=="CUSTOM"){
					var grid=mini.get("sourceData");
					if(grid.getData().length<1){//没填grid
						return false
					}
				}
			}
			return true;
		}
		
	</script>
</body>
</html>