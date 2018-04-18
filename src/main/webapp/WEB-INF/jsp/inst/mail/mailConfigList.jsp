<%-- 
    Document   : [MailConfig]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[MailConfig]列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>

</head>

<body>
	<div id="layout1" class="mini-layout"
		style="width: 100%; height: 100%;">

		<div region="west" showSplit="true" showHeader="false" width="200">
			<div id="toolbar1" class="mini-toolbar ">
				<a class="mini-button"
							iconCls="icon-add" plain="true" onclick="addPanel()"
							tooltip="添加账号配置"></a> <a class="mini-button" iconCls="icon-edit"
							plain="true" onclick="editPanel()" tooltip="编辑账号配置"></a> <a
							class="mini-button" iconCls="icon-remove" plain="true"
							onclick="deletePanel()" tooltip="删除账号配置"></a> <a
							class="mini-button" iconCls="icon-getmail" plain="true"
							onclick="getMail()" tooltip="收取选中账号的邮件"></a> <a
							class="mini-button" iconCls="icon-writemail" plain="true"
							onclick="writeMail()" tooltip="写邮件"></a>
			</div>
			<div class="mini-fit">
				<div id="leftTree" class="mini-outlooktree"
					url="${ctxPath}/inst/mail/mailFolder/getAllFolder.do"
					style="width: 100%; height: 100%; padding: 5px;"
					showTreeIcon="true" textField="name" idField="folderId"
					parentField="parentId" resultAsTree="false" showArrow="false"
					expandOnNodeClick="true" expandOnLoad="false"
					contextMenu="#treeMenu" imgPath="${ctxPath}/upload/icons/"
					onnodeselect="onNodeSelect" ondrawnode="draw"
					onload="treeOnload" onactivechanged="treeActiveChange"></div>
			</div>

			<ul id="treeMenu" class="mini-contextmenu"
				onbeforeopen="onBeforeOpen">
				<li name="recpmail" iconCls="icon-add" onclick="onAddAfter">收取邮件</li>
				<li name="brother" iconCls="icon-add" onclick="onAddAfter">插入同级文件夹</li>
				<li name="children" iconCls="icon-add" onclick="onAddNode">插入子文件夹</li>
				<li name="edit" iconCls="icon-edit" onclick="onEditNode">编辑文件夹</li>
				<li name="remove" iconCls="icon-remove" onclick="onRemoveNode">删除文件夹</li>
			</ul>
		</div>




		<div title="center" region="center" bodyStyle="overflow:hidden;"
			style="border: 0;">
			<div id="mainTabs" class="mini-tabs bg-toolbar" activeIndex="0"
				style="width: 100%; height: 100%;"
				onactivechanged="onTabsActiveChanged"></div>
		</div>
	</div>
	</div>


	<script type="text/javascript">
	mini.parse();
	top['mail']=window;
	var leftTree=mini.get("leftTree");
	
	function getNodeName(){
		var expand=$("div.mini-outlookbar-expand").find('span.icon-receive').siblings('span.mini-tree-nodetext').html();
		return expand;
	}
	
	function setNodeName(str){
		$("div.mini-outlookbar-expand").find('span.icon-receive').siblings('span.mini-tree-nodetext').html(str);
		//alert(expand);
		//console.log(expand);
		//return expand;
	}
	
	/*增加同级目录*/
		function onAddAfter(e) {
			var tree = mini.get("leftTree");
			var node = tree.getSelected();
			_OpenWindow({
				url : "${ctxPath}" + "/inst/mail/mailFolder/edit.do?parentId="+ node.parentId+"&configId="+node.configId+"&depth="+node.depth+"&folderId="+node.folderId,
				title : "新建同级文件夹",
				height : 300,
				width : 600,
				ondestroy : function(action) {
					if(action!='ok') return;
					tree.reload();
				}
			});
		}
	
	/*增加子目录*/
		function onAddNode(e) {
			var tree = mini.get("leftTree");
			var node = tree.getSelected();
			_OpenWindow({
				url : "${ctxPath}" + "/inst/mail/mailFolder/edit.do?parentId="+node.folderId+"&configId="+node.configId+"&depth="+(node.depth+1)+"&folderId="+node.folderId,
				title : "新建子文件夹",
				height : 300,
				width : 600,
				ondestroy : function(action) {
								if(action!='ok') return;
								tree.load();
						}
			});
			/*tree.beginEdit(node);*/
		}
	
	/*编辑文件夹*/
		function onEditNode(e) {
			var tree = mini.get("leftTree");
			var node = tree.getSelected();
			if (node.name == "收件箱" || node.name == "发件箱"
					|| node.name == "草稿箱" || node.name == "垃圾箱") {
				return;
			}
			_OpenWindow({
				url:__rootPath+"/inst/mail/mailFolder/edit.do?pkId="+node.folderId,
				title:"编辑文件夹",
				height:300,
				width:600,
				ondestroy : function() {
					location.reload(true);
				}
			});
		}
	/*删除文件夹*/
		function onRemoveNode(e) {
			var tree = mini.get("leftTree");
			var node = tree.getSelected();
			if (node) {
				if (confirm("确定删除选中目录?")) {
					_SubmitJson({
						url : "${ctxPath}" + "/inst/mail/mailFolder/del.do",
						data : {
							ids : node.folderId
						},
						method : 'POST',
						success : function(text) {
							location.reload(true);
						}
					});

					
				}
			}
		}
	
	  /*右键菜单控制*/
		function onBeforeOpen(e) {
			var menu = e.sender;
			var tree = mini.get("leftTree");
			var node = tree.getSelected();
			//alert(node.folderId);
			if (!node) {
				e.cancel = true;
				return;
			}
			 if (node && node.parentId == "0") {
			     e.cancel = true;
			     //阻止浏览器默认右键菜单
			     e.htmlEvent.preventDefault();
			     return;
			 }

			////////////////////////////////
			var recpmailItem=mini.getByName("recpmail", menu);
			var brotherItem = mini.getByName("brother", menu);
			var editItem = mini.getByName("edit", menu);
			var removeItem = mini.getByName("remove", menu);
			brotherItem.show();
			editItem.show();
			removeItem.show();

			if (node.parentId == "0") {
				brotherItem.hide();
				editItem.hide();
				removeItem.hide();
			}
			if(node.parentId!="0"){
				recpmailItem.hide();
			}
			if (node.name == "垃圾箱" || node.name == "收件箱" || node.name == "发件箱"
					|| node.name == "草稿箱") {
				editItem.hide();
				removeItem.hide();
			}
		}

		mini.parse();
		var tree = mini.get("leftTree");

		/*打开标签*/
		function showTab(node) {
			var tabs = mini.get("mainTabs");

			var id = "tab$" + node.folderId;
			var tab = tabs.getTab(id);
			if (!tab) {
				tab = {};
				tab._nodeid = node.folderId;
				tab.name = id;
				//alert((node.name).indexOf("收件箱"));
				if((node.name).indexOf("收件箱")>=0){
					//alert(node.name)
					tab.title =node.alias+"-"+(node.name).substring(0,(node.name).indexOf("(")) ;
				}
				else
					tab.title =node.alias+"-"+node.name ;
				tab.showCloseButton = true;

				//这里拼接了url，实际项目，应该从后台直接获得完整的url地址
				tab.url = "${ctxPath}" + "/inst/mail/mail/list.do?folderId="+node.folderId+"&configId="+node.configId;
				tabs.addTab(tab);
			}
			tabs.activeTab(tab);
			tabs.reloadTab(tab);
		}

		/*选择节点时触发*/
		function onNodeSelect(e) {
			var node = e.node;
			showTab(node);
		}
		
        function onTabsActiveChanged(e) {
            var tabs = e.sender;
            var tab = tabs.getActiveTab();
            if (tab && tab._nodeid) {

                var node = tree.getNode(tab._nodeid);
                if (node && !tree.isSelectedNode(node)) {
                    tree.selectNode(node);
                }
            }
        }
		
		
		/*添加账号配置*/
		function addPanel(e){
			_OpenWindow({
				title:'添加账号配置',
				url:__rootPath+"/inst/mail/mailConfig/edit.do?userId=1&userName=admin",
				width:720,
				height:600,
				ondestroy:function(action){
					location.reload(true);
				}
			});
		}
		
		/*编辑、删除配置*/
		function editPanel(e){
			var tree = mini.get("leftTree");
			var node=tree.getSelected();
			//alert(node.configId);
			if(node==null){
				alert("请选择一个账号!");
				return;
			}
				_OpenWindow({
					url:__rootPath+"/inst/mail/mailConfig/edit.do?pkId="+node.configId,
					title:'编辑账号配置',
					height:600,
					width:600,
					ondestroy:function(action){
						//location.reload(true);
						if(action=='ok')
							leftTree.load();
					}
				});
		}
		
		function deletePanel(){
			var tree = mini.get("leftTree");
			var node=tree.getSelected();
			if(node==null){
				alert("请选择一个账号!");
				return;
			}
			if (confirm("确定删除选中目录的配置?(你的备份数据也将删除,不可恢复)")) {
				_SubmitJson({
					url:__rootPath+"/inst/mail/mailConfig/del.do",
					data:{
						ids:node.configId
					},
					method:'POST',
					success:function(text){
						location.reload(true);
					}
				});
				}
		}
		
		function getMail(){
			var tree = mini.get("leftTree");
			var node=tree.getSelected();
			if(node==null){
				alert("请选择一个账号!");
				return;
			}
			_SubmitJson({
				url:__rootPath+"/inst/mail/mail/getMail.do",
				data:{
					configId:node.configId
				},
				method:'POST',
				success:function(text){
					//tree.load();
					top['main'].datagrid1.load();
				}
			});
		}
		
		function writeMail(){
			var tree = mini.get("leftTree");
			var node=tree.getSelected();
			if(node==null){
				alert("请选择一个账号!");
				return;
			}
			_OpenWindow({
				url:__rootPath+"/inst/mail/mail/edit.do?configId="+node.configId,
				title:"写邮件",
				height:800,
				width:800
			});
		}
		
		function draw(e){
			if(e.node.parentId=="0")
				e.iconCls="icon-folder";
			if(e.node.type=="RECEIVE-FOLDER")
				e.iconCls="icon-receive";
			if(e.node.type=="SENDER-FOLDER")
				e.iconCls="icon-sender";
			if(e.node.type=="DRAFT-FOLDER")
				e.iconCls="icon-draft";
			if(e.node.type=="DEL-FOLDER")
				e.iconCls="icon-trash";
			if(e.node.type=="OTHER-FOLDER")
				e.iconCls="icon-folder";
		}
		
        //树加载
        function treeOnload(e){
        	var tree=mini.get("leftTree");
        	var actIndex=getCookie('_mailFolderActiveIndex');
        	if(tree){            		
            	if(actIndex!=null && actIndex!=''){
            		tree.setActiveIndex(parseInt(actIndex));
            	}else{
            		tree.setActiveIndex(1);
            	}
        	}
        }
        
        function treeActiveChange(e){
        	if(leftTree){
        	 	mini.Cookie.set('_mailFolderActiveIndex', tree.getActiveIndex());
        	}
        }
			
	</script>

	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<%-- 	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.inst.mail.entity.MailConfig" winHeight="450"
		winWidth="700" entityTitle="[MailConfig]"
		baseUrl="inst/mail/mailConfig" /> --%>
</body>
</html>