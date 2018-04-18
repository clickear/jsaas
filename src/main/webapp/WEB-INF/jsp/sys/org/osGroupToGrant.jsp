<%--
	//用户组的资源授权
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>用户组授权</title>
    <%@include file="/commons/edit.jsp" %> 
</head>
<body>
	<div id="mainLayout" class="mini-layout" style="width:100%;height:100%;" >
		 <div region="south" showSplit="false" showHeader="false" height="40" showSplitIcon="false"  style="width:100%">
			<div class="mini-toolbar" style="text-align:center;border:none" bodyStyle="border:0">
			    <a class="mini-button" iconCls="icon-ok" onclick="onOk()">确定</a>
			    <span style="display:inline-block;width:25px;"></span>
			    <a class="mini-button" iconCls="icon-cancel" onclick="onCancel()">取消</a>
			</div>	 
		 </div>
		<div region="center" title="授权资源"  showHeader="false" showCollapseButton="false">
			<div class="mini-toolbar"  bodyStyle="border:0" style="border:none;border-bottom:solid 1px #aaa " >
			    <a class="mini-button" iconCls="icon-expand"  onclick="onExpand()">展开</a>
			    <a class="mini-button" iconCls="icon-collapse"  onclick="onCollapse()">收起</a>
			    <a class="mini-button" iconCls="icon-check"  onclick="onCheckAll()">全选</a>
			    <a class="mini-button" iconCls="icon-uncheck"  onclick="onUnCheckAll()">反选</a>
			    <a class="mini-button" iconCls="icon-close"  onclick="CloseWindow()">关闭</a>
			</div>
			
			<div class="mini-fit" bodyStyle="padding:2px;">
				<ul id="menuTree" class="mini-tree" style="width:100%;padding:5px;" 
			        showTreeIcon="true" textField="name" idField="menuId" parentField="parentId" resultAsTree="false"  
			        showCheckBox="true" checkRecursive="false" 
			        expandOnLoad="false" 
			        allowSelect="false" enableHotTrack="false">
			    </ul>
		    </div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var groupId="${param['groupId']}";
		var tenantId="${param['tenantId']}";
		
		var tree=mini.get('menuTree');
	
		
			$.getJSON(
				    "${ctxPath}/sys/org/osGroup/getGrantedResourceIds.do?groupId=${param['groupId']}",
				    function(result) {
				       tree.setUrl('${ctxPath}/sys/org/osGroup/getMenusByInstType.do?tenantId=${param['tenantId']}');
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
				url:__rootPath+'/sys/org/osGroup/saveGrant.do',
				method:'POST',
				data:{
					groupId:groupId,
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