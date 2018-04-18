<%-- 
    Document   : 选中树的节点确定文件夹
    Created on : 2015-11-6, 16:11:48
    Author     : 陈茂昌
--%>
<%@page import="com.redxun.sys.org.controller.SysOrgMgrController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>移动到文档目录</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="mini-fit">
					<ul id="tree1" class="mini-tree"  expandOnLoad="true"
						style="width: 380px; padding: 5px; height: 500px;" showTreeIcon="true"
						textField="name" idField="folderId" value="base"
						expandOnNodeClick="true" parentField="parent" resultAsTree="false"
						ondrawnode="onDrawNode" >
					</ul>
					</div>
    <div class="mini-toolbar dialog-footer"style="border: none;">
        <a class="mini-button" iconCls="icon-ok"  onclick="onOk()">确定</a>
        <a class="mini-button" iconCls="icon-cancel" onclick="myclose()">取消</a>
    </div>

	<script type="text/javascript">
		mini.parse();
		var tree=mini.get('tree1');
		if("${type}"=="PERSONAL")
		{tree.setUrl("${ctxPath}/oa/doc/docFolder/listPersonal.do");  }
		else{
			tree.setUrl("${ctxPath}/oa/doc/docFolder/listCompany.do");  
		}
		tree.load();
		var docId="${param['docId']}";
		//确定按钮
		function onOk() {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			if(node==null){
				alert("请选中文件夹");
			}
			var folderId = node.folderId;
			
			if (confirm("要移动文件吗")) {
				$.ajax({
					type : "Post",
					url : '${ctxPath}/oa/doc/doc/move.do?docId=' + docId
							+ "&folderId=" + folderId,
					data : {},
					beforeSend : function() {

					},
					success : function() {
                     alert("移动成功");
					}
				});
			}
			CloseWindow("ok");
		}

		//设置文件夹图标
		 function onDrawNode(e) {
		        var tree = e.sender;
		        var node = e.node;
		        	e.iconCls='icon-folder';
		    }
		//关闭页面
		function myclose(e){
			 CloseWindow("cancel");
		}
		
	</script>

</body>
</html>