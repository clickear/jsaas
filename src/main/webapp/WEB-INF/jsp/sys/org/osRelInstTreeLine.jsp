<%-- 
    Document   : 关系类型列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>关系类型实例管理</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<ul id="nodeMenu" class="mini-contextmenu" onbeforeopen="onShowingNodeMenu">        
	    <li iconCls="icon-add" onclick="addRelInst">增加关系</li>
	    <li name="editItem" iconCls="icon-edit" onclick="editInst">更换关系</li>
	    <li name="editItem" iconCls="icon-edit" onclick="editInstRow">编辑人员信息</li>
	    <li name="delItem" iconCls="icon-remove" onclick="delInst">删除关系</li>
	</ul>

	<div id="toolbar1" class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">
		<table style="width:100%;" cellpadding="0" cellspacing="0">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshTree()">刷新关系</a>
                 	 <a class="mini-button" iconCls="icon-group" plain="true" onclick="showRelInstImage()">关系实例图</a>
                 </td>
             </tr>
         </table>           
    </div>
    <div class="mini-fit" style="background-color: white">
         <ul id="relTree" class="mini-tree" url="${ctxPath}/sys/org/osRelInst/listByRelTypeId.do?relTypeId=${param['relTypeId']}" style="width:100%;height:100%;" 
				imgPath="${ctxPath}/upload/icons/"  expandOnLoad="true"
				showTreeIcon="true" textField="name" idField="id" parentField="parentId" resultAsTree="false"  
                contextMenu="#nodeMenu">        
         </ul>
	</div>
	<script type="text/javascript">
		mini.parse();
		var relTree=mini.get('relTree');
		function refreshTree(){
			relTree.load();
		}
		
		function onShowingNodeMenu(e){
			var menu=e.sender;
			var delItem=mini.getByName("delItem", menu);
			var editItem=mini.getByName('editItem',menu);
			var selectNode=relTree.getSelectedNode();
			if(selectNode.id=='0'){
				delItem.hide();
				editItem.hide();
			}else{
				delItem.show();
				editItem.show();
			}
			return false;
		}
		var relType='${osRelType.relType}';
		
		function showRelInstImage(){
    		_OpenWindow({
    			title:'关系类型实例管理（图形）',
    			url:'${ctxPath}/sys/org/osRelInst/line.do?typeId=${osRelType.id}',
    			max:true,
    			iconCls:'icon-group',
    			height:560,
    			width:800
    		});
    	}
		function addRelInst(){
			var selNode=relTree.getSelectedNode();
			
			if(relType=='USER-USER'){
				_UserDlg(false,function(users){
					var userIds=[];
					for(var i=0;i<users.length;i++){
						userIds.push(users[i].userId);
					}
					_SubmitJson({
						url:__rootPath+'/sys/org/osRelInst/saveRelInsts.do',
						method:'POST',
						data:{
							typeId:'${osRelType.id}',
							userIds:userIds.join(','),
							instId:selNode.relationId
						},
						success:function(){
							relTree.load();
						}
					});
				});
			}else if(relType=='GROUP-GROUP'){
				_GroupDlg(false,function(groups){
					var groupIds=[];
					for(var i=0;i<groups.length;i++){
						groupIds.push(groups[i].groupId);
					}
					_SubmitJson({
						url:__rootPath+'/sys/org/osRelInst/saveRelInsts.do',
						method:'POST',
						data:{
							typeId:'${osRelType.id}',
							groupIds:groupIds.join(','),
							instId:selNode.relationId
						},
						success:function(){
							relTree.load();
						}
					});
				});
			}
		}
		//删除实例
		function delInst(){
			var selNode=relTree.getSelectedNode();
			_SubmitJson({
				url:__rootPath+'/sys/org/osRelInst/delInst.do',
				method:'POST',
				data:{
					instId:selNode.relationId
				},
				success:function(){
					relTree.load();
				}
			});
		}
		
		//编辑实例
		function editInst(){
			var selNode=relTree.getSelectedNode();
			
			if(relType=='USER-USER'){
				_UserDlg(true,function(user){
					_SubmitJson({
						url:__rootPath+'/sys/org/osRelInst/updateRelation.do',
						method:'POST',
						data:{
							userId:user.userId,
							instId:selNode.relationId
						},
						success:function(result){
							relTree.load();
						}
					});
				});
			}else if(relType=='GROUP-GROUP'){
				_GroupDlg(true,function(group){
					_SubmitJson({
						url:__rootPath+'/sys/org/osRelInst/updateRelation.do',
						method:'POST',
						data:{
							groupId:group.groupId,
							instId:selNode.relationId
						},
						success:function(result){
							relTree.load();
						}
					});
				});
			}
		}
		
		function editInstRow(){
	   		var node = relTree.getSelectedNode();
	   		var pkId=node.id;
	   		_OpenWindow({
	   			title:'编辑人员节点',
	   			url:__rootPath+'/sys/org/osUser/edit.do?pkId='+pkId,
	   			width:700,
	   			height:450,
	   			ondestroy:function(action){
	   				if(action=='ok'){
	   					relTree.load();
	   				}
	   			}
	   		});
		}
		
	</script>
</body>
</html>