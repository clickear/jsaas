<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@include file="/commons/list.jsp"%>
		<title>流程表单字段权限配置</title>
		<style type="text/css">
			.mini-grid-cell-nowrap>span:before{float: left;}
			.mini-grid-rows-view{overflow-x: hidden;}
			.mini-fit>.mini-tree,
			.titleBar{
				width: calc(100% - 24px) !important;
				background: #fff;
				margin: 12px auto;
			}
		</style>
	</head>
	<body>
		<div style="display:none">
			<input 
				id="rightEditor" 
				class="mini-popupedit" 
				style="width:100%;" 
				textField="names" 
				valueField="edit" 
				popupWidth="auto" 
				showPopupOnClick="true" 
                popup="#settingPanel"  
                multiSelect="true" 
                allowInput="false" 
           />
            <div id="dealwithEditor" name="dealwithEditor" class="mini-checkboxlist" textField="text" valueField="id" ></div>
		</div>
		

	    
	    
		<div class="mini-fit ">
			<div class="titleBar mini-toolbar" >
				<ul>
					<li>
						<a class="mini-button" iconCls="icon-save" plain="true" onclick="onSave()">保存</a>
					</li>
					<li>
						<a class="mini-button" iconCls="icon-edit" plain="true" onclick="showRight('edit','viewGrid')">编辑权限</a>
					</li>
					<li>
						<a class="mini-button" iconCls="icon-read" plain="true" onclick="showRight('read','viewGrid')">只读权限</a>
					</li>
					<li>
						<a class="mini-button" iconCls="icon-hide" plain="true" onclick="showRight('hide','viewGrid')">隐藏权限</a>
					</li>
					<li class="clearfix"></li>
				</ul>
			</div>
	         <div 
	         	id="viewGrid" 
	         	class="mini-treegrid" 
	         	style="width:100%;"     
	            showTreeIcon="true" 
	            multiSelect="true"
	            treeColumn="fieldLabel" 
	            idField="rightId" 
	            parentField="parentId" 
	            resultAsTree="false"  
	            allowResize="true" 
	            expandOnLoad="true" 
	            allowCellValid="true" 
	            oncellbeginedit="OnCellBeginEdit" 
	            oncellclick="onCellClick"
	            allowCellEdit="true" 
	            allowCellSelect="true" 
	            ondrawcell="onDrawcell"
	            allowAlternating="true"
            >
	            <div property="columns">
	            	<div name="action" width="120" headerAlign="center" align="center"  cellStyle="padding-left:5px;" renderer="onActionRenderer">操作</div>
	            	<div type="checkcolumn" width="40"></div>
	                <div name="fieldLabel" field="fieldLabel" headerAlign="center" width="130">字段名称
	                	<input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
	                </div>
	                <div field="fieldName" name="fieldName" headerAlign="center" width="100">字段Key</div>
	                <div header="字段权限" headerAlign="center">
	                	 <div property="columns">
                            <div name="edit" field="edit" headerAlign="center" width="110">编辑权限 </div>
			                <div name="read" field="read" headerAlign="center" width="110">读权限</div>
			                <div name="hide" field="hide" headerAlign="center" width="110">隐藏权限</div>
			                <div name="dealwith" field="dealwith" headerAlign="center" width="110">已添加数据处理</div>
                        </div>
	                </div>
	                <div name="sn" field="sn" align="left" width="60">序号
	                	<input property="editor" changeOnMousewheel="false" class="mini-spinner"  minValue="0" maxValue="100000" />
	                </div>
	            </div>
	        </div>
	        <div id="opinionDiv">
		        <div class="mini-toolbar">
		            <table style="width:100%;">
		                <tr>
		                    <td style="width:100%;">
		                  		意见权限      
					            <a class="mini-button" iconCls="icon-edit" plain="true" onclick="showRight('edit','opinionGrid')">编辑权限</a>
					            <a class="mini-button" iconCls="icon-read" plain="true" onclick="showRight('read','opinionGrid')">只读权限</a>
					            <a class="mini-button" iconCls="icon-hide" plain="true" onclick="showRight('hide','opinionGrid')">隐藏权限</a> 
		                    </td>
		                </tr>
		            </table>           
			    </div>
		        <div id="opinionGrid" class="mini-treegrid" style="width:100%;"     
		            showTreeIcon="true" multiSelect="true"
		            treeColumn="fieldLabel" idField="rightId" parentField="parentId" 
		            resultAsTree="false"  
		            allowResize="true" expandOnLoad="true" 
		            allowCellValid="true" oncellbeginedit="OnCellBeginEdit" oncellclick="onCellClick"
		            allowCellEdit="true" allowCellSelect="true" >
		            <div property="columns">
		            	<div name="action" width="120" headerAlign="center" align="center"  cellStyle="padding-left:5px;" renderer="onActionRenderer">#</div>
		            	<div type="checkcolumn" width="40"></div>
		                <div name="fieldLabel" field="fieldLabel" headerAlign="center" width="130">字段名称
		                	<input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
		                </div>
		                <div field="fieldName" name="fieldName" headerAlign="center" width="100">字段Key</div>
		                <div header="字段权限" headerAlign="center">
		                	 <div property="columns">
	                            <div name="edit" field="edit" headerAlign="center" width="110">编辑权限 </div>
				                <div name="read" field="read" headerAlign="center" width="110">读权限</div>
				                <div name="hide" field="hide" headerAlign="center" width="110">隐藏权限</div>
	                        </div>
		                </div>
		                <div name="sn" field="sn" align="left" width="60">序号
		                	<input property="editor" changeOnMousewheel="false" class="mini-spinner"  minValue="0" maxValue="100000" />
		                </div>
		            </div>
		        </div>
	        </div>
	        
	        <div id="buttonDiv">
	        
		        <div class="mini-toolbar mini-toolbar-bottom">
		            <table style="width:100%;">
		                <tr>
		                    <td style="width:100%;">
		                  		按钮权限      
					            <a class="mini-button" iconCls="icon-edit" plain="true" onclick="showRight('edit','buttonGrid')">编辑权限</a>
					            <a class="mini-button" iconCls="icon-hide" plain="true" onclick="showRight('hide','buttonGrid')">隐藏权限</a> 
		                    </td>
		                </tr>
		            </table>           
			    </div>
		        <div id="buttonGrid" class="mini-treegrid" style="width:100%;"     
		            showTreeIcon="true" multiSelect="true"
		            treeColumn="fieldLabel" idField="rightId" parentField="parentId" 
		            resultAsTree="false"  
		            allowResize="true" expandOnLoad="true" 
		            allowCellValid="true" oncellbeginedit="OnCellBeginEdit" oncellclick="onCellClick"
		            allowCellEdit="true" allowCellSelect="true" 
	            >
		            <div property="columns">
		            	<div name="action" width="120" headerAlign="center" align="center"  cellStyle="padding-left:5px;" renderer="onActionRenderer">#</div>
		            	<div type="checkcolumn" width="40"></div>
		                <div name="fieldLabel" field="fieldLabel" headerAlign="center" width="130">名称
		                	<input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
		                </div>
		                <div field="fieldName" name="fieldName" headerAlign="center" width="100">key</div>
		                <div header="按钮权限" headerAlign="center">
		                	 <div property="columns">
	                            <div name="edit" field="edit" headerAlign="center" width="110">编辑权限 </div>
				                <div name="hide" field="hide" headerAlign="center" width="110">隐藏权限</div>
	                        </div>
		                </div>
		                <div name="sn" field="sn" align="left" width="60">序号
		                	<input property="editor" changeOnMousewheel="false" class="mini-spinner"  minValue="0" maxValue="100000" />
		                </div>
		            </div>
		        </div>
	        </div>
	    </div>
	   
		     <div id="settingPanel" class="mini-panel" title="设置权限" iconCls="icon-group" style="width:550px;height:250px;" visible="false"
	        	showToolbar="true" showCloseButton="true" showHeader="true"  >
		        	<div property="toolbar" class="toolbar-margin">   
			            <a class="mini-button" iconCls="icon-save" onclick="onSettingSave()">保存</a>  
			            <a class="mini-button" iconCls="icon-refresh" onclick="onSettingReset()">重置</a>     
			            <a class="mini-button" iconCls="icon-close" onclick="onSettingClose()">关闭</a>    
		        	</div>
		        	<form id="viewForm">
			        	<table class="table-detail column_1" cellpadding="0" cellspacing="1" style="width:100%">
			        		<tr>
			        			<th width="80">全部</th>
			        			<td width="*">
			        				<input class="mini-checkbox" id="isAll" name="isAll" onvaluechanged="isAllChange"/>
			        			</td>
			        		</tr>
			        		<tr id="userRow">
			        			<th>用户</th>
			        			<td>
			        				<input id="userLinks" name="userLinks" allowInput="false" class="mini-textboxlist"  style="width:200px;" value="" text="" />
									<a class="mini-button" iconCls="icon-user" plain="true" onclick="addUsers()">添加用户</a>
									<!-- 
									<input id="createUser" class="mini-checkbox" name="createUser" trueValue="true" falseValue="false">是否含创建用户 -->
			        			</td>
			        		</tr>
			        		<tr id="groupRow">
			        			<th>用户组</th>
			        			<td>
			        				<input id="groupLinks" name="groupLinks" allowInput="false" class="mini-textboxlist"  style="width:200px;" value="" text="" />
									<a class="mini-button" iconCls="icon-group" plain="true" onclick="addGroups()">添加用户组</a>
			        			</td>
			        		</tr>
			        	</table>
		        	</form>
		    </div>

	    <script type="text/javascript">
	    	var dealwithAry=[{id:'nodel',text:'不允许删除'},{id:'noedit',text:'不允许编辑'}];
	    	mini.parse();
	    	
	    	var editor=mini.get("dealwithEditor");
	    	editor.setData(dealwithAry);
	    	
	    	//点击的列
	    	var clickcolumn=null;
	    	var viewGrid=mini.get('viewGrid');
	    	var opinionGrid=mini.get('opinionGrid');
	    	var buttonGrid=mini.get('buttonGrid');
	    	
	    	var rightEditor=mini.get('rightEditor');
	    	var isAll=mini.get('isAll');
	    	var userLinks=mini.get('userLinks');
	    	var groupLinks=mini.get('groupLinks');
	    	//var createUser=mini.get('createUser');
	    	
	    	//在表格中显示弹出窗口
	    	var showInCell=true;
	    	//设置字段名
	    	var setFieldName='edit';
	    	var actDefId='${param.actDefId}';
	    	var nodeId='${param.nodeId}';
	    	var solId='${param.solId}';
	    	var formAlias="${param.formAlias}";
	    	
	    	$(function(){
	    		initRights();
	    	});
	    	
	    	function initRights(){
	    		var url=__rootPath + "/bpm/form/bpmFormView/getRights.do?actDefId="+actDefId
	    				+"&formAlias="+formAlias+"&nodeId="+nodeId +"&solId=" + solId;
	    		
	    		$.get(url,function(data){
	    			viewGrid.setData(data.form);
	    			if(data.opinion && data.opinion.length==0){
	    				$("#opinionDiv").remove();
	    			}
	    			else{
	    				opinionGrid.setData(data.opinion);	
	    			}
	    			if(data.button && data.button.length==0){
	    				$("#buttonDiv").remove();
	    			}
	    			else{
	    				buttonGrid.setData(data.button);	
	    			}
	    			
	    		})
	    	}
	    	
	    	

		    function onActionRenderer(e) {
		    	var grid=e.sender;
		    	var girdId=grid.id;
		    	
	            var record = e.record;
	            var uid = record._uid;
	            var pkId=record.pkId;
	            var s='';
	          
	             s+=' <span class="icon-button icon-copydown" title="复制至下一记录" onclick="copyDownRow(\'' + uid + '\',\''+girdId+'\')"></span>';
	             s+=' <span class="icon-button icon-copyup" title="复制至上一记录" onclick="copyUpRow(\'' + uid + '\',\''+girdId+'\')"></span>';
	             s+=' <span class="icon-button icon-clear" title="清空" onclick="clearRow(\'' + uid + '\',\''+girdId+'\')"></span>';
	            return s;
	        }
		    
		   //动态设置每列的编辑器
			function OnCellBeginEdit(e) {
	            var record = e.record, field = e.field;
	            if (field == "edit" || field == "read" || field == "hide") {
	                var editor=mini.get('rightEditor');
	                e.column.editor=editor;
	                editor.grid=e.sender;
	                e.column.editor.setText(record[e.column.field]);
	                e.column.editor.setValue(record[e.column.field+'Df']);
	            }
	            else if(field=="dealwith"){
	            	if( record.type=="table"){
	            		var editor=mini.get('dealwithEditor');
		            	e.column.editor=editor;
		                editor.grid=e.sender;	
	            	}
	            	else{
	            		e.column.editor=null;
	            	}
	            }
	        }
		   
		   function onDrawcell(e){
			   var record = e.record, field = e.field;
			   if(field!="dealwith") return;
			   var val=record.dealwith;
			   if(!val) return e.cellHtml="";
			   var tmpAry=[];
			   var aryVal=val.split(",");
			   for(var i=0;i<aryVal.length;i++){
				   var key=aryVal[i];
				   var name=getVal(key);
				   if(name) tmpAry.push(name);
			   }
			   e.cellHtml=tmpAry.join(",");
			   
		   }
		   
		   function getVal(key){
			   for(var i=0;i<dealwithAry.length;i++){
				   var o=dealwithAry[i];
				   if(o.id==key) return o.text;
			   }
			   return "";
		   }
		   
		   	
		   
		    function onSettingReset(){
		    	var form=new mini.Form('viewForm');
		    	form.reset();
		    	$("#userRow").css('display','');
				$("#groupRow").css('display','');
		    }
		   
		   
		  	//表格编辑
		  	function onCellClick(e){
		  		clickcolumn=e.column;
		  		showInCell=true;
		  	}
		 
			//添加候选用户 
			function addUsers(){
				
				_UserDlg(false,function(users){
					var uIds=[];
					var uNames=[];
					for(var i=0;i<users.length;i++){
						uIds.push(users[i].userId);
						uNames.push(users[i].fullname);
					}
					if(userLinks.getValue()!=''){
						uIds.unshift(userLinks.getValue().split(','));
					}
					if(userLinks.getText()!=''){
						uNames.unshift(userLinks.getText().split(','));	
					}
					userLinks.setValue(uIds.join(','));
					userLinks.setText(uNames.join(','));
				});
			}
			
			//添加候选用户组
			function addGroups(){
				
				_GroupDlg(false,function(groups){
					var uIds=[];
					var uNames=[];
					for(var i=0;i<groups.length;i++){
						uIds.push(groups[i].groupId);
						uNames.push(groups[i].name);
					}
					if(groupLinks.getValue()!=''){
						uIds.unshift(groupLinks.getValue().split(','));
					}
					if(groupLinks.getText()!=''){
						uNames.unshift(groupLinks.getText().split(','));	
					}
					groupLinks.setValue(uIds.join(','));
					groupLinks.setText(uNames.join(','));
				});
			}
			
			function isAllChange(e){
				if(e.sender.getChecked()){
					$("#userRow").css('display','none');
					$("#groupRow").css('display','none');
				}else{
					$("#userRow").css('display','');
					$("#groupRow").css('display','');
				}
			}
			
			
			function copyDownRow(uid,gridId){
				var grid=mini.get(gridId);
				var row=grid.getRowByUID(uid);
				var rowIndex=grid.indexOf(row);
				var nextRow=grid.getRow(rowIndex+1);
				if (rowIndex == grid.totalCount - 1){
		            return;
				}
			
				grid.updateRow(nextRow,{
					edit:row.edit,
					editDf:row.editDf,
					read:row.read,
					readDf:row.readDf,
					hide:row.hide,
					hideDf:row.hideDf
				});
				
			}
			
			function copyUpRow(uid,gridId){
				var grid=mini.get(gridId);
				var row=grid.getRowByUID(uid);
				var rowIndex=grid.indexOf(row);
				if(rowIndex==0) return;
				var preRow=grid.getRow(rowIndex-1);
				grid.updateRow(preRow,{
					edit:row.edit,
					editDf:row.editDf,
					read:row.read,
					readDf:row.readDf,
					hide:row.hide,
					hideDf:row.hideDf
				});
			}
			
			function clearRow(uid,gridId){
				var grid=mini.get(gridId);
				var row=grid.getRowByUID(uid);
				grid.updateRow(row,{
					edit:'',
					editDf:'',
					read:'',
					readDf:'',
					hide:'',
					hideDf:''
				});
			}
			
			function addData(aryResult,data,type){
				if(data && data.length==0) return;
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					if(!obj.type){
						obj.type=type;	
					}
					
					aryResult.push(obj);
				}
			}
			
			//批量保存
			function onSave(){
				var permission={};
				var data=viewGrid.getData();
				var jsonAry=[];
				addData(jsonAry,data,"form");
				
				var opinionObj=$("#opinionDiv");
				if(opinionObj.length>0){
					var opinions=opinionGrid.getData();
					addData(jsonAry,opinions,"opinion");
				}
				var buttonObj=$("#buttonDiv");
				if(buttonObj.length>0){
					var buttons=buttonGrid.getData();
					addData(jsonAry,buttons,"button");
				}
				var fields=mini.encode(jsonAry);
				
				_SubmitJson({
					method:'POST',
					url:__rootPath+'/bpm/form/bpmFormView/saveRights.do',
					data:{
						formAlias:'${param.formAlias}',
						nodeId:nodeId,
						solId:solId,
						actDefId:actDefId,
						fields:fields
					},
					success:function(text){
						CloseWindow('ok');
					}
				});
			}
			
		
			
			function onSettingClose(){
				var editor=mini.get('rightEditor');
				editor.hidePopup();
			}
			
			function onSettingSave(){
				
				var editor=mini.get('rightEditor');
				var grid=editor.grid;
				
				if(!showInCell){
					setColumnRight(setFieldName,grid);
					editor.hidePopup();
					return;
				}
				
				
				if(clickcolumn==null) return;
				var row=grid.getSelected();
				if(!row){
					return;
				}
				grid.cancelEdit();
				
				var jsonConf={};
				
				var name="";
				jsonConf.userIds=userLinks.getValue();
				jsonConf.unames=userLinks.getText();
				jsonConf.groupIds=groupLinks.getValue();
				jsonConf.gnames=groupLinks.getText();
				//jsonConf.createUser=createUser.getValue();
				if(isAll.getChecked()){
					jsonConf.all=true;
					name="全部";
				}else{
					jsonConf.all=false;
				}
				
				name+=jsonConf.gnames;
				if(jsonConf.gnames!=''){
					name+=';';
				}
				name+=jsonConf.unames;
				if(jsonConf.unames!=''){
					name+=';';
				}
				/*
				if(jsonConf.createUser=='true'){
					name+='(含创建人)';
				}*/
				if(clickcolumn.field=='edit' || clickcolumn.field=='read' || clickcolumn.field=='hide'){
					var fields={};
					fields[clickcolumn.field]=name;
					fields[clickcolumn.field+'Df']=mini.encode(jsonConf);
					grid.updateRow(row, fields);
				}
				
				editor.hidePopup();
			}
			
			//获得权限对话框的配置
			function getPopupConf(fieldName){
				var jsonConf={};

				jsonConf.userIds=userLinks.getValue();
				jsonConf.unames=userLinks.getText();
				jsonConf.groupIds=groupLinks.getValue();
				jsonConf.gnames=groupLinks.getText();
				//jsonConf.createUser=createUser.getValue();
				var name="";
				if(isAll.getChecked()){
					jsonConf.all=true;
					name="全部";
				}else{
					jsonConf.all=false;
				}
				name+=jsonConf.gnames;
				if(jsonConf.gnames!=''){
					name+=';';
				}				
				name+=jsonConf.unames;
				if(jsonConf.unames!=''){
					name+=';';
				}
				/*
				if(jsonConf.createUser=='true'){
					name+='含创建人;';
				}*/
				var rightConf={};
				rightConf[fieldName]=name;
				rightConf[fieldName+'Df']=mini.encode(jsonConf);
				return rightConf;
			}
			
			//显示权限设置窗口
			function showRight(field,gridId){
				showInCell=false;
				setFieldName=field;
				var grid=mini.get(gridId);
				rightEditor.grid=grid;
				rightEditor.showPopup();
			}
			
			function setColumnRight(field,grid){
				var selecteds=grid.getSelecteds();
				if(selecteds.length==0){
					alert('请选择行!');
					return;
				}
				var setConf=getPopupConf(field);
				for(var i=0;i<selecteds.length;i++){
					var row=selecteds[i];
					grid.updateRow(row,setConf);
				}
				rightEditor.showPopup();
			}
		  
	    </script>
	</body>
</html>