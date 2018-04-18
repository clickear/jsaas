<%-- 
    Document   : 选中树的节点确定文件夹
    Created on : 2016-7-6, 16:11:48
    Author     : 陈茂昌
--%>
<%@page import="com.redxun.sys.org.controller.SysOrgMgrController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>移动到文档目录</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<div class="mini-toolbar">
        <a class="mini-button" onclick="onOk()" iconCls="icon-ok">确定</a>  
        <div id="ckSubMenus" name="ckSubMenus" class="mini-checkbox" text="仅移动子菜单"></div>     
        <a class="mini-button" onclick="myclose()" iconCls="icon-cancel">取消</a>
	</div>
	
	<div class="mini-fit">
		<ul id="tree1" class="mini-tree"  expandOnLoad="false"
			showTreeIcon="true" url="${ctxPath}/sys/core/sysMenu/newMenuList.do"
			textField="name" idField="id" 
			expandOnNodeClick="true" parentField="parent" resultAsTree="false" 
			 >
		</ul>
	</div>		

	<script type="text/javascript">
		mini.parse();
		var tree = mini.get("tree1");
		
        function onChangeInstType(e){
        	var s=e.sender;
        	var val=s.getValue();
        	tree.setUrl('${ctxPath}/sys/core/sysMenu/newMenuList.do?instType='+val);
        }
		
		//关闭页面
		function myclose(e){
			 CloseWindow("cancel");
		}
		var tree=mini.get('tree1');
		
		//确定按钮
		function onOk() {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			if(node==null){
				 _ShowTips({
                 	msg:'请选中要移动的位置'
                 });
				 return;
			}
			var targetId = node.id;
			var menuType=node.menuType;
			if (confirm("要移动菜单吗?")) {
				_SubmitJson({
					url : '${ctxPath}/sys/core/sysMenu/toMoveMenu.do?',
					data : {
						menuId:'${menuId}',
						targetId:targetId,
						menuType:menuType,
						ckSubMenus:mini.get('ckSubMenus').getValue()
					},
					success : function() {
						CloseWindow("ok");
					}
				});
			}
		}

		
		
	</script>


</body>
</html>