<%-- 
    Document   : 业务表单视图编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>业务表单视图编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%; text-align: left;" id="toolbarBody">
	               	<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveFormRight()">保存</a>	               
<!-- 	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow('cancel');">关闭</a> -->
	                <a class="mini-button" iconCls="icon-clear" plain="true" onclick="reset()">重置</a>
	                <a class="mini-button" iconCls="icon-ok" plain="true" onclick="synchronize()">批量设置</a>	                
	            </td>
	        </tr>
	    </table>
	</div>
	 <div class=" shadowBox90" style="padding-top: 8px;">
		<div id="p1" class="form-outer2">
			<form id="form1" method="post">
				<div class="form-inner">
					<table class="table-detail" cellspacing="1" cellpadding="0">
					<input id="treeId" name="treeId"   class="mini-hidden"   value="${treeId}"/>
					<input id="userName" name="userName"   class="mini-hidden"   value="${userName}"/>
					<input id="groupName" name="groupName"   class="mini-hidden"   value="${groupName}"/>
					<input id="settingId" name="settingId"   class="mini-hidden"  value="${bpmAuthSetting.id}" />
						<tr>
							<th>用　　户</th>
							<td colspan="3">
								<input 
									id="userId" 
									name="userId"   
									class="mini-textboxlist" 
									allowInput="false" 
									style="width: 80%;"  
									value="${userId}" 
									text="${userName}"
								/>
							<a class="mini-button"  onclick="selUser()">选择</a>
							</td>
						</tr>
						<tr>
							<th >用  户  组</th>
							<td colspan="3">
								<input 
									id="groupId" 
									name="groupId"  
									class="mini-textboxlist" 
									allowInput="false" 
									style="width: 80%;" 
									value="${groupId}" 
									text="${groupName}"
								/>
							<a class="mini-button"  onclick="selGroup()">选择</a>
							</td>
						</tr>
						<tr>
							<th>阅读权限</th>
							<td ><input name="read"    class="mini-checkbox"  value="${read}" ></input>
							</td>
							<th>修改权限</th>
							<td ><div name="edit"    class="mini-checkbox"   value="${edit}"></div>
							</td>
						</tr>
						<tr>
							<th>添加权限</th>
							<td ><div name="add"    class="mini-checkbox"  value="${add}" ></div>
							</td>
							<th>删除权限</th>
							<td ><div name="delete"   class="mini-checkbox"    value="${delete}"></div>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	mini.parse();
	var user=mini.get("userId");
	var group=mini.get("groupId");
	var form=new mini.Form("#form1");
	function selGroup(){
		_GroupDlg(false,function(groups){
			var ids=[];
			var names=[];
			for(var i=0;i<groups.length;i++){
				ids.push(groups[i].groupId);
				names.push(groups[i].name);
			}
			group.setValue(ids.join(","));
			group.setText(names.join(","));
			mini.get("groupName").setValue(names.join(","));
		});
	}
	
	function selUser(){
		_UserDlg(false,function(users){
			var ids=[];
			var names=[];
			for(var i=0;i<users.length;i++){
				ids.push(users[i].userId);
				names.push(users[i].fullname);
			}
			user.setValue(ids.join(","));
			user.setText(names.join(","));
			mini.get("userName").setValue(names.join(","));
		});
	}
	
	/*重置表单*/
	function reset(){
		var treeId=mini.get("treeId");
		var treeIdValue=treeId.getValue();
		var settingId=mini.get("settingId");
		var settingIdValue=settingId.getValue();
		form.clear();
		treeId.setValue(treeIdValue);
		settingId.setValue(settingIdValue);
	}
	
	function synchronize(){
		_OpenWindow({
			url:'${ctxPath}/bpm/form/bpmFormView/selectTree.do',
    		title:'选择分类',
			height:700,
			width:600,
			ondestroy:function(action){
				if(action=='ok'){
   					var iframe = this.getIFrameEl().contentWindow;
            		var trees=iframe.getTrees();
            		  $.ajax({
            			type:'post',
            			async:false,
            			url:'${ctxPath}/bpm/form/bpmFormView/saveGrantToOthers.do',
            			data:{"trees":JSON.stringify(trees),"formData":JSON.stringify(form.getData())},
            			success:function(result){
            				location.reload();
            				 mini.showTips({
            			            content: "<b>成功</b> <br/>同步成功",
            			            state: 'success',
            			            x: 'center',
            			            y: 'center',
            			            timeout: 3000
            			        });
            			},
            			error: function(XMLHttpRequest, textStatus, errorThrown) {
            				location.reload();
            			}
            		});  
   				}
			}
		});
		
	}
	
	function saveFormRight(){
		var data=form.getData();
		$.ajax({
			type:'post',
			url:'${ctxPath}/bpm/form/bpmFormView/saveFormViewRight.do',
			data:data,
			success:function (result){
				CloseWindow('ok');
			}
		});
	};
	
	addBody();
	</script>
</body>
</html>