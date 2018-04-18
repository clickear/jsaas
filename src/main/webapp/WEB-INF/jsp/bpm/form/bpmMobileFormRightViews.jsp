<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@include file="/commons/list.jsp"%>
		<script src="${ctxPath}/scripts/share/dialog.js" type="text/javascript"></script>
		<title>流程方案字段权限</title>
	</head>
	<body>
		<div class="mini-toolbar">
			<table style="width:100%">
				<tr>
					<td>
						<a class="mini-button" iconCls="icon-add" onclick="addRow">添加</a>
						<a class="mini-button" iconCls="icon-remove" onclick="delRowGrid('formGrid')">删除</a>
						<a class="mini-button" iconCls="icon-remove-all" onclick="delAll('formGrid')">清空</a>
						<a class="mini-button" iconCls="icon-up" plain="true" onclick="upRowGrid('formGrid')">向上</a>
		            	<a class="mini-button" iconCls="icon-down" plain="true" onclick="downRowGrid('formGrid')">向下</a>
		            	<span class="separator"></span>
		            	<a class="mini-button" iconCls="icon-ok" onclick="CloseWindow('ok')">确定</a>
		            	<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
					</td>
				</tr>
			</table>
		</div>
		<div class="mini-fit">
			
			<div id="formGrid" class="mini-datagrid" style="width:100%;height:100%;" 
	        allowResize="true" pageSize="20" showPager="false"
	        allowCellEdit="true" allowCellSelect="true" multiSelect="true" 
	        editNextOnEnterKey="true"  editNextRowCell="true">
		        <div property="columns">
		            <div type="indexcolumn">序号</div>
		            <div type="checkcolumn"></div>
		            <div name="formAlias"  displayfield="formName" headerAlign="center" allowSort="true" width="150">表单
		                <input property="editor" class="mini-buttonedit" onclick="onSelectForm()" style="width:100%;" minWidth="200" />
		            </div>
		            <div type="checkboxcolumn" field="isAll" trueValue="true" falseValue="false" width="60" headerAlign="center">全部</div>          
		            <div type="checkboxcolumn" field="attendUsers" trueValue="true" falseValue="false" width="60" headerAlign="center">含审批人</div>
		            <div type="checkboxcolumn" field="startUser" trueValue="true" falseValue="false" width="60" headerAlign="center">含经办人</div>
		            
		            <div field="userIds" displayfield="userNames" width="200">用户
		                <input property="editor" class="mini-buttonedit" onclick="onSelectUser()" style="width:100%;"/>
		            </div>  
		            <div field="groupIds" displayfield="groupNames" width="200">用户组
		                <input property="editor" class="mini-buttonedit" onclick="onSelectGroup()" style="width:100%;"/>
		            </div>
		                                    
		        </div>
		    </div>
		</div>
		<script type="text/javascript">
			mini.parse();
			var formGrid=mini.get('formGrid');
			var bodefId='${param.boDefIds}';
			
			function addRow(e){
				var row={isAll:true};
				addRowGrid('formGrid',row);
			}
						

			function onSelectGroup(){
				formGrid.cancelEdit();
				var row=formGrid.getSelected();
				_GroupDlg(false,function(groups){
					var groupIds=[];
					var groupNames=[];
					for(var i=0;i<groups.length;i++){
						groupIds.push(groups[i].groupId);
						groupNames.push(groups[i].name);
					}
					formGrid.updateRow(row,{groupIds:groupIds.join(','),groupNames:groupNames.join(',')});
				});
			}
			
			function setGridData(data){
				formGrid.setData(mini.decode(data));
			}
				
			function onSelectUser(){
				formGrid.cancelEdit();
				var row=formGrid.getSelected();
				_UserDlg(false,function(users){
					var userIds=[];
					var userNames=[];
					for(var i=0;i<users.length;i++){
						userIds.push(users[i].userId);
						userNames.push(users[i].fullname);
					}
					formGrid.updateRow(row,{userIds:userIds.join(','),userNames:userNames.join(',')});
				});
			}
			
			function getCondForms(){
				
				var forms=formGrid.getData();
				for(var i=forms.length-1;i>=0;i--){
					var form=forms[i];
					if(!form.formAlias) {
						forms.splice(i,1);
					}
				}
				return mini.encode(forms);
				
			}
			
			function onSelectForm(){
				formGrid.cancelEdit();
				var row=formGrid.getSelected();
				var conf={single:true,bodefId:bodefId,callBack:function(formView){
					if(!formView){
						formGrid.updateRow(row, {
	                        formAlias: '',
	                        formName:''
	                    });
					}else{
						formGrid.updateRow(row, {
	                        formAlias: formView.alias,
	                        formName:formView.name
	                    });	
					}
				}};
				openBpmMobileFormDialog(conf);
			}
		</script>
	</body>
	<body>