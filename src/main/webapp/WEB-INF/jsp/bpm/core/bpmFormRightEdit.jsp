
<%-- 
    Document   : [表单权限]编辑页
    Created on : 2018-02-09 15:54:25
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[表单权限]编辑</title>
<%@include file="/commons/edit.jsp"%>
<script type="text/javascript">
	var currentEditor=null;
	var action="save";
	var rightId="";

	$(function(){
		initRight();
	});
	
	function initRight(){
		var url=__rootPath +"/bpm/core/bpmFormRight/getRightJson.do";
		var params={
				actDefId:"${param.actDefId}",
				solId:"${param.solId}",
				nodeId:"${param.nodeId}",
				formAlias:"${param.formAlias}"};
		$.post(url,params,function(data){
			//取得权限ID
			if(data.id){
				rightId=data.id;
			}
			var frm=$("#divForm");
			if(!data.sub) data.sub=[];
			if(!data.opinions) data.opinions=[];
			if(!data.buttons) data.buttons=[];
			var html=baiduTemplate('rightTemplate',data);
			frm.html(html);
			mini.parse();
			//主表字段
			var mainGrid=mini.get("mainGrid");
			mainGrid.setData(data.main);
			//子表字段
			if(data.sub && data.sub.length>0){
				for(var i=0;i<data.sub.length;i++){
					var subTb=data.sub[i];
					var subGrid=mini.get("grid_" +subTb.name);
					subGrid.setData(subTb.fields);

					var subRights=subTb.tbRight;
					var tmp={};
					for(var key in subRights){
						var txt=getDisplay(subRights[key]);	
						tmp[key +"Name"]=txt;
					}
					$.extend(subRights, tmp);
					var form = new mini.Form("#table_"+subTb.name);
					form.setData(subRights);
				}
			}
			//意见
			if(data.opinions && data.opinions.length>0){
				var opinionGrid=mini.get("gridOpinion");
				opinionGrid.setData(data.opinions);
			}
			//按钮权限
			if(data.buttons && data.buttons.length>0){
				var gridButton=mini.get("gridButton");
				gridButton.setData(data.buttons);
			}
		})
	}
	
	
	
	
	function isAllChange(e){
		var obj=e.sender;
		if(obj.checked){
			$("#noneTr").hide();
			$(".row").hide();
		}
		else{
			$("#noneTr").show();
			$(".row").show();
		}
	}
	
	function showEveryOne(){
		$("#everyoneTr").show();
		$("#noneTr").hide();
		$(".row").hide();
		
		var ctl=mini.get("everyone");
		ctl.setChecked(true);
		
		var ctl=mini.get("none");
		ctl.setChecked(false);
		
	}
	
	function showNone(){
		$("#noneTr").show();
		$("#everyoneTr").hide();
		$(".row").hide();
		
		var ctl=mini.get("none");
		ctl.setChecked(true);
		
		var ctlEvery=mini.get("everyone");
		ctlEvery.setChecked(false);
	}
	
	
	function setSetting(val){
		$("#noneTr").show();
		$("#everyoneTr").show();
		var ctlNone=mini.get("none");
		ctlNone.setChecked(false);
		
		var ctlEvery=mini.get("everyone");
		ctlEvery.setChecked(false);
		
		for(var i=0;i<val.length;i++){
			var o=val[i];
			var type=o.type;
			if(type=="everyone" || type=="none") continue;
			
			var ctl=mini.get(type +"Links");
			var ctlInclue=mini.get(type +"Include");
			ctl.setValue(o.val);
			ctlInclue.setChecked(o.include);
			if(type!="script"){
				ctl.setText(o.text);
			}
		}
	}
	
	
	function noneChange(e){
		var obj=e.sender;
		if(obj.checked){
			$("#everyoneTr").hide();
			$(".row").hide();
		}
		else{
			$("#everyoneTr").show();
			$(".row").show();
		}
	}
	
	function onDrawcell(e){
	   var record = e.record, field = e.field,name=e.column.name;
	   if(!name) return;
	   if(name=="action" || name=="chkCol"  || name=="comment" ||name=="name"  ) return ;
	   
	   var val=record[field];
	   if(val.length==0) return;
	   var display=getDisplay(val);
	   e.cellHtml=display;
	}
	
	function getDisplay(val){
		if(!val) return "";
		
		var types={"user":"用户","group":"用户组","subGroup":"子用户组","script":"脚本"};
		var display="";
	   	for(var i=0;i<val.length;i++){
		   var o=val[i];
		   if(o.type=="everyone"){
			   return "所有人";
		   }
		   else if(o.type=="none"){
			   return "无权限";
		   }
		   else{
			   if(o.type=="script"){
				   display+=types[o.type] ;	   
			   }
			   else{
				   display+=types[o.type] +":" + o.text;
			   }
		   }
	   	}
		return display;
	}
	
	function addUsers(e){
		var userLinks=mini.get("userLinks");
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
	
	function setGroups(groupLinks){
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
			groupLinks.setValue(uIds.join(","));
			groupLinks.setText(uNames.join(","));
			
		});
	}

	
	function addGroups(){
		var groupLinks=mini.get("groupLinks");
		setGroups(groupLinks)
	}
	
	
	function addSubGroups(){
		var groupLinks=mini.get("subGroupLinks");
		setGroups(groupLinks)
	}
	
	
	
	function OnCellBeginEdit(e) {
		var record = e.record, field = e.field;
        if (field == "edit" || field == "read" || field == "hide") {
           	var editor= e.column.editor;
           
            var val=record[e.column.field];
            var display=getDisplay(val);
            editor.setText(display);
            editor.setValue(val);
        }
	}
	
	/**构建权限*/
	function getRights(){
		var ctlEvery=mini.get("everyone");
		var ctlNone=mini.get("none");
		var rights=[];
		//所有人
		if(ctlEvery.checked){
			var obj={type:"everyone"};
			rights.push(obj);
			return rights;
		}
		//无权限
		if(ctlNone.checked){
			var obj={type:"none"};
			rights.push(obj);
			return rights;
		}
		//人员，用户组
		var ctlTypes=["user","group","subGroup"];
		for(var i=0;i<ctlTypes.length;i++){
			var type=ctlTypes[i];
			var links=mini.get(type+"Links");
			var ids=links.getValue();
			var names=links.getText();
			var include=mini.get(type +"Include");
			if(ids){
				var obj={type:type,val:ids,text:names,include:include.checked};
				rights.push(obj);
			}	
		}
		//脚本
		var scriptObj=mini.get("script");
		var script=scriptObj.getValue().trim();
		var scriptInclude=mini.get("scriptInclude");
		if(script){
			var obj={type:"script",val:script,text:"脚本",include:scriptInclude.checked};
			rights.push(obj);
		}
		return rights;
	}
	
	

	
	function onSettingSave(){
		if(currentEditor){
			currentEditor.hidePopup();	
			action="save";
		}
	}
	
	function onSettingClose(){
		if(currentEditor){
			currentEditor.hidePopup();	
			action="close";
		}
	}
	
	function showPopup(e){
		var obj=e.sender;
		//设置当前弹出控件
		currentEditor=obj;
		
		var val=obj.getValue();
		if(!val || val.length==0) return;
		setEditor(val);
	}
	
	function hidePopup(e){
		if(action=="save"){
			var editor=e.sender;
			var rights=getRights();
			var display=getDisplay(rights);
			editor.setText(display);
			editor.setValue(rights);
			if(editor.grid){
				setGridRights(editor.grid,editor.field,rights)
			}
			editor.grid=null;
		}
	}
	
	function setGridRights(grid,field,rights){
		var data=grid.getSelecteds();
		if(data.length==0) return;
		for(var i=0;i<data.length;i++){
			var row=data[i];
			var obj={};
			obj[field]=rights;
			grid.updateRow(row,obj);
		}
	}
	
	function setEditor(val){
		for(var i=0;i<val.length;i++){
			var o=val[i];
			if(o.type=="everyone"){
				showEveryOne();
				return;
			}
			else if(o.type=="none"){
				showNone();
				return;
			}
			else{
				setSetting(val);
			}
		}
	}

	function saveRights(){
		var json={};
		var mainGrid=mini.get("mainGrid");
		
		json.main=mainGrid.getData();
		
		var tables=$(".subTable");
		if(tables.length>0){
			json.sub=[];
			tables.each(function(){
				var table=$(this);
				var tableName=table.attr("name");
				var comment=table.attr("comment");
				var obj={name:tableName,comment:comment};
				var form = new mini.Form("#table_"+tableName);    
				obj.tbRight=form.getData();
				obj.fields=mini.get("grid_"+tableName).getData();
				json.sub.push(obj);
			})	
		}
		
		var gridOpinion=mini.get("gridOpinion");
		if(gridOpinion){
			json.opinions=gridOpinion.getData();
		}
		
		var gridButton=mini.get("gridButton");
		if(gridButton){
			json.buttons=gridButton.getData();
		}
		var url=__rootPath+"/bpm/core/bpmFormRight/save.do";
		_SaveData("form1",url,function(data){
			//设定ID
			rightId=data.data;		
			CloseWindow('ok');
		},function(data){
			var jsonStr=JSON.stringify(json);
			var obj={name: "json", value: jsonStr};
			data.push(obj);
			if(rightId){
				var objId={name: "id", value: rightId};
				data.push(objId);
			}
		}) ;
	}
	
	function clearRights(){
		mini.confirm("确认清除设置的权限吗?", "提示信息",  function (action) {
            if (action != "ok")  return;
			var url=__rootPath+"/bpm/core/bpmFormRight/clearRight.do";
			_SaveData("form1",url,function(data){
				CloseWindow('ok');
			}) ;	
		})
		
	}
	
	
	
	function batSetRight(field,gridId){
		var grid=mini.get(gridId);
		var rightEditor=mini.get("rightEditor");
		rightEditor.grid=grid;
		rightEditor.field=field;
		rightEditor.showPopup();
	}
	
	

	
</script>

<script type="text/html;" id="rightTemplate">
			<div id="mainGrid" 
	         	class="mini-datagrid" 
	         	style="width:100%;"     
	            multiSelect="true"
	            idField="name" 
	            allowResize="true" 
	            allowCellValid="true" 
	            allowCellEdit="true" 
	            allowCellSelect="true" 
	            allowAlternating="true"
				showPager="false"
				onDrawcell="onDrawcell"
				oncellbeginedit="OnCellBeginEdit" 

            >
	            <div property="columns">
					<div type="indexcolumn" ></div>
					<div type="checkcolumn" ></div> 
	            	<div name="comment" field="comment" headerAlign="center" width="130">名称</div>
	                <div field="name" name="name" headerAlign="center" width="100">字段Key</div>
	             
                    <div name="edit" field="edit" headerAlign="center" width="110" editor="rightEditor">编辑 </div>
			        <div name="read" field="read" headerAlign="center" width="110" editor="rightEditor">只读</div>
			        <div name="require" field="require" headerAlign="center" width="110"  editor="rightEditor">必填</div>
                       
	            </div>
	        </div>

			<#
				for(var i = 0; i<sub.length;i++){
					var obj=sub[i];
			#>
			<table id="table_<#=obj.name#>" class="subTable" name="<#=obj.name#>" comment="<#=obj.comment#>">
				<tr>
					<th><#=obj.name#>,<#=obj.comment#></th>
				</tr>
				<tr>
					<td>
						添加：<input name="add" textName="addName" class="mini-popupedit"  textField="names" 
							valueField="ids" popupWidth="auto" showClose="true"
							showPopupOnClick="true" onshowpopup="showPopup" onhidepopup="hidePopup"
                			popup="#settingPanel"  multiSelect="true" allowInput="false" oncloseclick="_OnButtonEditClear" />
						编辑：<input name="edit" textName="editName" class="mini-popupedit"  textField="names" showClose="true"
							valueField="ids" popupWidth="auto" 
							showPopupOnClick="true" onshowpopup="showPopup" onhidepopup="hidePopup"
                			popup="#settingPanel"  multiSelect="true" allowInput="false" oncloseclick="_OnButtonEditClear" />
						删除：<input name="del" textName="delName" class="mini-popupedit"  textField="names" showClose="true"
							valueField="ids" popupWidth="auto" 
							showPopupOnClick="true" onshowpopup="showPopup" onhidepopup="hidePopup"
                			popup="#settingPanel"  multiSelect="true" allowInput="false" oncloseclick="_OnButtonEditClear" />
						隐藏:<input name="hidden" textName="hiddenName" class="mini-popupedit"  textField="names" showClose="true"
							valueField="ids" popupWidth="auto" 
							showPopupOnClick="true" onshowpopup="showPopup" onhidepopup="hidePopup"
                			popup="#settingPanel"  multiSelect="true" allowInput="false" oncloseclick="_OnButtonEditClear" />
						必填:<input name="require"  textName="requireName" class="mini-popupedit"  textField="names" showClose="true"
							valueField="ids" popupWidth="auto" 
							showPopupOnClick="true"  onshowpopup="showPopup" onhidepopup="hidePopup"
                			popup="#settingPanel"  multiSelect="true" allowInput="false" oncloseclick="_OnButtonEditClear" />
						编辑已添加:<input name="editExist" textName="editExistName" class="mini-popupedit"  textField="names" showClose="true"
							valueField="ids" popupWidth="auto" 
							showPopupOnClick="true" onshowpopup="showPopup" onhidepopup="hidePopup"
                			popup="#settingPanel"  multiSelect="true" allowInput="false" oncloseclick="_OnButtonEditClear" />
						删除已添加:<input name="delExist" textName="delExistName" class="mini-popupedit"  textField="names" showClose="true"
							valueField="ids" popupWidth="auto" 
							showPopupOnClick="true" onshowpopup="showPopup" onhidepopup="hidePopup"
                			popup="#settingPanel"  multiSelect="true" allowInput="false" oncloseclick="_OnButtonEditClear" />
					</td>
				</tr>
				<tr>
					<td>
						 <a class="mini-button" iconCls="icon-edit" plain="true" onclick="batSetRight('edit','grid_<#=obj.name#>')">编辑</a>
   	 					 <a class="mini-button" iconCls="icon-readonly" plain="true" onclick="batSetRight('read','grid_<#=obj.name#>')">只读</a>
   	    				 <a class="mini-button" iconCls="icon-require" plain="true" onclick="batSetRight('require','grid_<#=obj.name#>')">必填</a>
					</td>
				</tr>
			</table>
			<div id="grid_<#=obj.name#>" 
	         	class="mini-datagrid" 
	         	style="width:100%;"     
	            multiSelect="true"
	            idField="name" 
	            allowResize="true" 
	            allowCellValid="true" 
	            allowCellEdit="true" 
	            allowCellSelect="true" 
	            allowAlternating="true"
				showPager="false"
				onDrawcell="onDrawcell"
				oncellbeginedit="OnCellBeginEdit"
            >
	            <div property="columns">
					<div type="indexcolumn" ></div>
					<div type="checkcolumn" ></div> 
	                <div name="comment" field="comment" headerAlign="center" width="130">名称</div>
	                <div field="name" name="name" headerAlign="center" width="100">字段Key</div>
                    <div name="edit" field="edit" headerAlign="center" width="110"  editor="rightEditor">编辑 </div>
			        <div name="read" field="read" headerAlign="center" width="110"  editor="rightEditor">只读</div>
			        <div name="require" field="require" headerAlign="center" width="110"  editor="rightEditor">必填</div>
	            </div>
	        </div>
			<#}
			#>


			<#if(opinions.length>0){#>
			意见权限:
			<div>
			 	<a class="mini-button" iconCls="icon-edit" plain="true" onclick="batSetRight('edit','gridOpinion')">编辑</a>
   	 			<a class="mini-button" iconCls="icon-readonly" plain="true" onclick="batSetRight('read','gridOpinion')">只读</a>
   	  			<a class="mini-button" iconCls="icon-require" plain="true" onclick="batSetRight('require','gridOpinion')">必填</a>
			</div>
			<div id="gridOpinion" 
	         	class="mini-datagrid" 
	         	style="width:100%;"     
	            multiSelect="true"
	            idField="name" 
	            allowResize="true" 
	            allowCellValid="true" 
	            allowCellEdit="true" 
	            allowCellSelect="true" 
	            allowAlternating="true"
				showPager="false"
				onDrawcell="onDrawcell"
				oncellbeginedit="OnCellBeginEdit"
            >
	            <div property="columns">
					<div type="indexcolumn" ></div>
					<div type="checkcolumn" ></div> 
	            	<div name="comment" field="comment" headerAlign="center" width="130">名称</div>
	                <div field="name" name="name" headerAlign="center" width="100">字段Key</div>
                    <div name="edit" field="edit" headerAlign="center" width="110" editor="rightEditor">编辑 </div>
			        <div name="read" field="read" headerAlign="center" width="110" editor="rightEditor">只读</div>
			        <div name="require" field="require" headerAlign="center" width="110" editor="rightEditor">必填</div>
	            </div>
	        </div>
			<#}
			#>

			<#if(buttons.length>0){#>
			按钮权限:
			<div>
			 	<a class="mini-button" iconCls="icon-readonly" plain="true" onclick="batSetRight('visible','gridButton')">显示</a>
   	 			
			</div>
			<div id="gridButton" 
	         	class="mini-datagrid" 
	         	style="width:100%;"     
	            multiSelect="true"
	            idField="name" 
	            allowResize="true" 
	            allowCellValid="true" 
	            allowCellEdit="true" 
	            allowCellSelect="true" 
	            allowAlternating="true"
				showPager="false"
				onDrawcell="onDrawcell"
				oncellbeginedit="OnCellBeginEdit"
            >
	            <div property="columns">
					<div type="indexcolumn" ></div>
					<div type="checkcolumn" ></div> 
	                <div name="comment" field="comment" headerAlign="center" width="130">名称</div>
	                <div field="name" name="name" headerAlign="center" width="100">字段Key</div>
	                <div name="visible" field="visible" headerAlign="center" width="110" editor="rightEditor">显示</div>
	            </div>
	        </div>
			<#}
			#>

</script>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar topBtn" >
   	 <a class="mini-button" iconCls="icon-save" plain="true" onclick="saveRights">保存</a>
   	 
   	 <a class="mini-button" iconCls="icon-remove" plain="true" onclick="clearRights">重设</a>
   	 
   	 <a class="mini-button" iconCls="icon-edit" plain="true" onclick="batSetRight('edit','mainGrid')">编辑</a>
   	 <a class="mini-button" iconCls="icon-readonly" plain="true" onclick="batSetRight('read','mainGrid')">只读</a>
   	 <a class="mini-button" iconCls="icon-require" plain="true" onclick="batSetRight('require','mainGrid')">必填</a>
   	 
	</div>
	
	<div id="toolbar1" class="mini-toolbar topBtn" >
   	 
	</div>
	
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<input class="mini-hidden" name="actDefId" value="${param.actDefId}" />
			<input class="mini-hidden" name="solId" value="${param.solId}" />
			<input class="mini-hidden" name="nodeId" value="${param.nodeId}" />
			<input class="mini-hidden" name="formAlias" value="${param.formAlias}" />
			<div id="divForm"></div>
		</form>
	</div>
	
	<div style="display:none">
			<input id="rightEditor" class="mini-popupedit"  textField="names" valueField="edit" popupWidth="auto" 
				showPopupOnClick="true" showClose="true" popupHeight="350" 
                popup="#settingPanel"  multiSelect="true" allowInput="false" onshowpopup="showPopup" onhidepopup="hidePopup" oncloseclick="_OnButtonEditClear"/>
	</div>
	
	 <div id="settingPanel" class="mini-panel" title="设置权限" iconCls="icon-group" style="width:550px;height:340px;" visible="false"
        	showToolbar="true" showCloseButton="true" showHeader="true"  >
	        	<div property="toolbar" class="toolbar-margin">   
		            <a class="mini-button" iconCls="icon-save" onclick="onSettingSave()">保存</a>  
		            <a class="mini-button" iconCls="icon-close" onclick="onSettingClose()">关闭</a>    
	        	</div>
	        	<form id="viewForm">
		        	<table class="table-detail column_1" cellpadding="0" cellspacing="1" style="width:100%">
		        		<tr id="everyoneTr">
		        			<th width="80">所有人</th>
		        			<td width="*">
		        				<input class="mini-checkbox" id="everyone" name="everyone"  onvaluechanged="isAllChange" />
		        			</td>
		        		</tr>
		        		<tr id="noneTr">
		        			<th width="80">无权限</th>
		        			<td width="*">
		        				<input class="mini-checkbox" id="none" name="none" onvaluechanged="noneChange"/>
		        			</td>
		        		</tr>
		        		<tr id="userRow" class="row">
		        			<th>用户</th>
		        			<td>
		        				<input id="userLinks" name="userLinks" allowInput="false" class="mini-textboxlist"  style="width:200px;" value="" text="" />
								<a class="mini-button" iconCls="icon-user" plain="true" onclick="addUsers">用户</a>
								<input class="mini-checkbox" id="userInclude" name="userInclude" checked="true" text="包含" />
		        			</td>
		        		</tr>
		        		<tr id="groupRow" class="row">
		        			<th>用户组</th>
		        			<td>
		        				<input id="groupLinks" name="groupLinks" allowInput="false" class="mini-textboxlist"  style="width:200px;" value="" text="" />
								<a class="mini-button" iconCls="icon-group" plain="true" onclick="addGroups">用户组</a>
								<input class="mini-checkbox" id="groupInclude" name="groupInclude" checked="true" text="包含"/>
		        			</td>
		        		</tr>
		        		<tr id="subGroupRow" class="row">
		        			<th>用户组(包括下级)</th>
		        			<td>
		        				<input id="subGroupLinks" name="subGroupLinks" allowInput="false" class="mini-textboxlist"  style="width:200px;" value="" text="" />
								<a class="mini-button" iconCls="icon-group" plain="true" onclick="addSubGroups">用户组</a>
								<input class="mini-checkbox" id="subGroupInclude" name="subGroupInclude" checked="true" text="包含"/>
		        			</td>
		        		</tr>
		        		<tr id="scriptRow" class="row">
		        			<th>脚本</th>
		        			<td>
		        				<textarea class="mini-textarea" id="script" name="script" emptyText="请输入备注" width="80%"></textarea>
								<input class="mini-checkbox"  id="scriptInclude" name="scriptInclude" checked="true" text="包含"/>
		        			</td>
		        		</tr>
		        	</table>
	        	</form>
	    </div>

</body>
</html>