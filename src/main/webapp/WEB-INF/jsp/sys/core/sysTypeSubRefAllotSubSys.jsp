
<%-- 
    Document   : [机构--子系统关系]编辑页
    Created on : 2017-09-13 15:57:55
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[机构--菜单关系]编辑</title>
<%@include file="/commons/edit.jsp"%>
<style type="text/css">
	.mini-grid-rows-view .mini-iconfont:before{font-size:22px;margin:0;line-height: 24px;}
	.mini-grid-rows-content{
		background: #fff;
	}
</style>
</head>
<body>


	<div class="mini-fit">
			<div class="mini-toolbar"  bodyStyle="border:0">
			    <a class="mini-button" iconCls="icon-expand"  onclick="onExpand()">展开</a>
			    <a class="mini-button" iconCls="icon-collapse"  onclick="onCollapse()">收起</a>
			    <a class="mini-button" iconCls="icon-check"  onclick="onCheckAll()">全选</a>
			    <a class="mini-button" iconCls="icon-uncheck"  onclick="onUnCheckAll()">反选</a>
			</div>
			<div class="mini-fit">
				<ul 
					id="menuTree" 
					class="mini-tree" 
					style="width:100%;padding:5px;" 
			        showTreeIcon="true" 
			        textField="name" 
			        idField="menuId" 
			        parentField="parentId" 
			        resultAsTree="false"  
			        showCheckBox="true" 
			        checkRecursive="false" 
			        expandOnLoad="false" 
			        allowSelect="false" 
			        enableHotTrack="false"
		        ></ul>
			 </div>
	    </div>
	</div>
	<div id="toolbar1" class="mini-toolbar" style="padding: 2px;text-align: center;">
		<a class="mini-button" iconCls="icon-ok" plain="true" onclick="onOk()">确定</a>
		<a class="mini-button" iconCls="icon-remove" plain="true" onclick="CloseWindow('cancel')">关闭</a>
	</div>

	<script type="text/javascript">
		mini.parse();
		var typeId="${param['typeId']}";
		console.info('typeId:' + typeId);
		var tree=mini.get('menuTree');

        //异步加载有时有差异，会导致后续的数据设置不上去
       	$.getJSON(
			    "${ctxPath}/sys/core/sysTypeSubRef/getGrantedResourceIds.do?typeId="+typeId,
			    function(result) {
			     	tree.setUrl('${ctxPath}/sys/core/sysTypeSubRef/getAllMenus.do');
			        tree.setValue(result.data);
			        tree.setCheckRecursive(true);
			    }
		);
 		
		function onOk(){
			var menuIds=tree.getValue(true);
			var sysIds=[];
			var allCheckedNodes=tree.getCheckedNodes(true);
			//取得所有一级目录的节点，即子系统
			for(var i=0;i<allCheckedNodes.length;i++){
				var ckNode=allCheckedNodes[i];
				if(ckNode._level==0){
					sysIds.push(ckNode.menuId);
				}
			}
			
			_SubmitJson({
				url:__rootPath+'/sys/core/sysTypeSubRef/saveGrant.do',
				method:'POST',
				data:{
					typeId:typeId,
					//子系统项
					sysIds:sysIds.join(','),
					//包括根节点的菜单项，即包括子系统项
					menuIds:menuIds
				},
				success:function(text){
					CloseWindow('ok');
				}
			});
		}
		
		
		function onCancel(){
			CloseWindow('cancel');
		}
		
		function onExpand(){
			tree.expandAll();
		}
		
		function onCollapse(){
			tree.collapseAll();
		}
		
		function onCheckAll(){
			tree.checkAllNodes();
		}
		
		function onUnCheckAll(){
			tree.uncheckAllNodes();
		}
		
		
		
		
	</script>
</body>
</html>