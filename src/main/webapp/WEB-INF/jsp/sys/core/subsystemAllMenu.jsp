<%-- 
    Document   : 选中树的节点确定文件夹
    Created on : 2017-12-23
    Author     : csx
--%>
<%@page import="com.redxun.sys.org.controller.SysOrgMgrController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择菜单节点</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<div class="mini-toolbar">
        <a class="mini-button" onclick="onOk()" iconCls="icon-ok">确定</a>  
        <a class="mini-button" onclick="myclose()" iconCls="icon-cancel">取消</a>
        <c:if test="${param['isTransfer']=='true'}">
        <div id="ckSubMenus" name="ckSubMenus" class="mini-checkbox" text="仅移动子菜单"></div>
        </c:if>
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
		
        
		//关闭页面
		function myclose(e){
			 CloseWindow("cancel");
		}
		var tree=mini.get('tree1');
		
		//确定按钮
		function onOk() {
			var node = tree.getSelectedNode();
			if(node==null){
				 _ShowTips({
                 	msg:'请选节点'
                 });
				 return;
			}
			CloseWindow('ok');
		}
		
		function getSelectMenu(){
			 var node = tree.getSelectedNode();
			 return node;
		}
		
		function getIsMoveChilds(){
			var ck=mini.get('ckSubMenus');
			return ck.getValue();
		}
		
	</script>


</body>
</html>