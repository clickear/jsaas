<%-- 
    Document   : 外部邮箱账号配置列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>外部邮箱账号配置列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>

<body>
	<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;" >

		<div region="west" showSplit="true" showHeader="false" width="200" class="mini-toolbar-icons">
			<div id="toolbar1" class="mini-toolbar" style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
				<table style="width: 100%;" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width: 100%;" class="mini-button-icons">
							<a class="mini-button" iconCls="icon-add" plain="true" onclick="addConfig" tooltip="添加账号配置"></a> 
							<a class="mini-button" iconCls="icon-edit" plain="true" onclick="editConfig" tooltip="编辑账号配置"></a> 
							<a class="mini-button" iconCls="icon-remove" plain="true" onclick="delConfig" tooltip="删除账号配置"></a> 
							<a class="mini-button" iconCls=" icon-mailUp" plain="true" onclick="getMail" tooltip="收取选中账号的邮件"></a> 
							<a class="mini-button" iconCls="icon-writemail" plain="true"onclick="writeMail" tooltip="写邮件"></a> 
							<a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refresh" tooltip="刷新"></a>
						</td>
					</tr>
				</table>
			</div>

			<div class="mini-fit rx-grid-fit">
				<div id="leftTree" class="mini-outlooktree" url="${ctxPath}/oa/mail/mailFolder/getAllFolder.do" style="width: 100%; height: 100%; padding: 5px;" showTreeIcon="true" textField="name" idField="folderId" parentField="parentId" resultAsTree="false" showArrow="false" expandOnNodeClick="true" expandOnLoad="false" contextMenu="#treeMenu" imgPath="${ctxPath}/upload/icons/" onNodeClick="onNodeClick" ondrawnode="draw" onload="treeOnload" onactivechanged="treeActiveChange"></div>
			</div>

			<ul id="treeMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
				<li name="brother" iconCls="icon-add" onclick="addBrotherFolder">添加同级文件夹</li>
				<li name="children" iconCls="icon-add" onclick="addChildernFolder">添加子文件夹</li>
				<li name="edit" iconCls="icon-edit" onclick="editFolder">编辑文件夹</li>
				<li name="remove" iconCls="icon-remove" onclick="delFolder">删除文件夹</li>
			</ul>
		</div>




		<div title="center" region="center" bodyStyle="overflow:hidden;" style="border: 0;">
			<div id="mainTabs" class="mini-tabs bg-toolbar" activeIndex="0" style="width: 100%; height: 100%;" onactivechanged="onTabsActiveChanged"></div>
		</div>
	</div>


	<ul id="contextMenu" class="mini-contextmenu" url="${ctxPath}/oa/mail/mailConfig/refreshAccountList.do" onItemclick="getMailByCId(e)" idField="configId" textField="account">
<%-- 		<c:forEach items="${allConfigs}" var="mailConfig" varStatus="a">
			<li id="${mailConfig.configId}" onclick="onItemClick(e)">${mailConfig.account}</li>
		</c:forEach> --%>
	</ul>




	<script type="text/javascript">
		mini.parse();
		top['mail'] = window;
		var leftTree = mini.get("leftTree");
		/*获取树节点名字*/
		function getNodeName() {
			var expand = $("div.mini-outlookbar-expand").find('span.icon-receive').siblings('span.mini-tree-nodetext').html();
			return expand;
		}

		/*设置树节点名字*/
		function setNodeName(str,index) {
			$("div.mini-outlookbar-group").eq(index).find('span.icon-receive').siblings('span.mini-tree-nodetext').html(str);
		}

		/*增加同级文件夹*/
		function addBrotherFolder(e) {
			var tree = mini.get("leftTree");
			var node = tree.getSelected();
				_OpenWindow({
					url : "${ctxPath}" + "/oa/mail/mailFolder/edit.do?parentId="
							+ node.parentId + "&configId=" + node.configId
							+ "&depth=" + node.depth + "&folderId=" + node.folderId,
					title : "新建同级文件夹",
					height : 300,
					width : 600,
					ondestroy : function(action) {
						if (action == 'ok')
							tree.load('${ctxPath}/oa/mail/mailFolder/getAllFolder.do');
					}
				});
		}

		/*增加子文件夹*/
		function addChildernFolder(e) {
			var tree = mini.get("leftTree");
			var node = tree.getSelected();
			_OpenWindow({
				url : "${ctxPath}" + "/oa/mail/mailFolder/edit.do?parentId="
						+ node.folderId + "&configId=" + node.configId
						+ "&depth=" + (node.depth + 1) + "&folderId="
						+ node.folderId,
				title : "新建子文件夹",
				height : 300,
				width : 600,
				ondestroy : function(action) {
					if (action == 'ok')
						tree.load('${ctxPath}/oa/mail/mailFolder/getAllFolder.do');
				}
			});
		}

		/*编辑文件夹*/
		function editFolder(e) {
			var tree = mini.get("leftTree");
			var node = tree.getSelected();
			if (node.type == "RECEIVE-FOLDER" || node.type=="SENDER-FOLDER" || node.type == "DRAFT-FOLDER"|| node.type == "DEL-FOLDER") {
				return;
			}
			_OpenWindow({
				url : __rootPath + "/oa/mail/mailFolder/edit.do?pkId="
						+ node.folderId,
				title : "编辑文件夹",
				height : 300,
				width : 600,
				ondestroy : function(action) {
					if (action == 'ok')
						tree.load('${ctxPath}/oa/mail/mailFolder/getAllFolder.do');
				}
			});
		}
		/*删除文件夹*/
		function delFolder(e) {
			var tree = mini.get("leftTree");
			var tabs = mini.get("mainTabs");
			var node = tree.getSelected();
			if (node) {
				if (confirm("确定删除选中目录?")) {
							_SubmitJson({
								url : "${ctxPath}" + "/oa/mail/mailFolder/del.do",
								data : {
									ids : node.folderId
								},
								method : 'POST',
								success : function(text) {
									tree.findNodes(function(f_node){  
										if(f_node.parentId.indexOf(node.folderId)>-1){
											tabs.removeTab("tab$" +f_node.folderId);    //删除文件夹下面的子文件夹的tabs
										}
									}); 
									tabs.removeTab("tab$" +node.folderId);    //删除文件夹的tabs
									tree.load('${ctxPath}/oa/mail/mailFolder/getAllFolder.do');
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

			var brotherItem = mini.getByName("brother", menu);
			var editItem = mini.getByName("edit", menu);
			var removeItem = mini.getByName("remove", menu);
			brotherItem.show();
			editItem.show();
			removeItem.show();

			if (node.parentId == "0") { //如果是根目录
				brotherItem.hide();
				editItem.hide();
				removeItem.hide();
			}
			if (node.type == "RECEIVE-FOLDER" || node.type=="SENDER-FOLDER" || node.type == "DRAFT-FOLDER"|| node.type == "DEL-FOLDER") {//如果是四个基本目录
				editItem.hide();
				removeItem.hide();
			}
 			var checkNode=null;                      //查找节点
			tree.findNodes(function(f_node){    //查找是否有该节点
				if(f_node.folderId==node.folderId){
					checkNode=f_node;   //如果有
					return;
				}
			}); 
			if(checkNode==null)
				e.cancel=true;
		}

		mini.parse();
		var tree = mini.get("leftTree");

		/*打开标签*/
		function showTab(node) {
			//var delNode={};
			var delNodeArray=[];
			tree.findNodes(function(node){
			    if(node.type=="DEL-FOLDER"){
			    	delNodeArray.push(node);
			    }
			});
			var expand1=$("div.mini-outlookbar-expand");
			var myParent1=$(expand1).parent();
			var childrenList1=$(myParent1).find(".mini-outlookbar-group");
			var index1 = $(childrenList1).index(expand1); 
			
			var isShowDel="YES";
			var delFolderId=delNodeArray[index1].folderId;
			if(node.parentId.indexOf(delFolderId)>-1||node.folderId.indexOf(delFolderId)>-1)  //如果是垃圾箱目录和其下的子目录，则不显示删除按钮
				isShowDel="NO";
			
			var tabs = mini.get("mainTabs");
			var id = "tab$" + node.folderId;
			var tab = tabs.getTab(id);
			if (!tab) {
				tab = {};
				tab._nodeid = node.folderId;
				tab.name = id;
				if (node.type=='RECEIVE-FOLDER') {
					tab.title = node.alias+ "-"+ (node.name).substring(0, (node.name).indexOf("("));
				} else
					tab.title = node.alias+ "-" + node.name;
				tab.showCloseButton = true;
				
				//var item = e.item;
				//var my=item.el;
				var expand=$("div.mini-outlookbar-expand");
				var myParent=$(expand).parent();
				//alert(myParent.html());
				var childrenList=$(myParent).find(".mini-outlookbar-group");
				var index = $(childrenList).index(expand); 
				
				tab.url = "${ctxPath}" + "/oa/mail/outMail/list.do?folderId="
						+ node.folderId + "&configId=" + node.configId+"&isShowDel="+isShowDel+"&i="+index;
				tabs.addTab(tab);
				tabs.activeTab(tab);
			}
			else{
				tabs.activeTab(tab);
				tabs.reloadTab(tab);
			}
			
		}

		/*选择节点时触发*/
		function onNodeClick(e) {
			var node = e.node;
			showTab(node);
		}

		/*激活tabs事件处理*/
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
		function addConfig(e) {
			_OpenWindow({
				title : '添加账号配置',
				url : __rootPath+ "/oa/mail/mailConfig/edit.do",
				width : 720,
				height : 600,
				ondestroy : function(action) {
					if(action=='ok'){
						//tree.load('${ctxPath}/oa/mail/mailFolder/getAllFolder.do');
						refresh();
					}
				}
			});
		}

		/*编辑、删除配置*/
		function editConfig(e) {
			var tree = mini.get("leftTree");
			var node = tree.getSelected();           //选中节点
			var checkNode=null;                      //查找节点
			if(node!=null){
				tree.findNodes(function(f_node){    //查找是否有该节点
					if(f_node.mailConfig.configId==node.configId){
						checkNode=f_node;   //如果有
						return;
					}
				});
				if(checkNode!=null){      //如果该节点存在
					_OpenWindow({
						url : __rootPath + "/oa/mail/mailConfig/edit.do?pkId="
								+ node.configId+"&from=tree",
						title : '编辑账号配置',
						height : 600,
						width : 600,
						ondestroy : function(action) {
							if(action=='ok')
								//tree.load('${ctxPath}/oa/mail/mailFolder/getAllFolder.do');
								refresh();
						}
					});
				}
				else{                  //节点不存在
					alert("请选择一个账号!");
					return;
				}
			}else{   
					alert("请选择一个账号!");
					return;
			}
		}

		/*删除账号配置*/
		function delConfig() {
			var tree = mini.get("leftTree");
			var node = tree.getSelected();
			var checkNode=null;                      //查找节点
 			var tabs = mini.get("mainTabs");
			if (node == null) {
				alert("请选择一个账号!");
				return;
			}
			else{
				tree.findNodes(function(f_node){    //查找是否有该节点
					if(f_node.mailConfig.configId==node.configId){
						checkNode=f_node;   //如果有
						return;
					}
				});
				if(checkNode!=null){      //如果该节点存在
					if (confirm("确定删除选中目录的配置?(你的备份数据也将删除,不可恢复)")) {
						_SubmitJson({
							url:__rootPath+"/oa/mail/mailFolder/getFolderByGonfigId.do",
							data:{
								configId:node.configId
								},
							method:'POST',
							showMsg:false,
							success:function(result){
								var data=result.data;
								for(var i=0;i<data.length;i++)
								{
									tabs.removeTab("tab$" +data[i].folderId);  //删除对应配置的所有文件夹的tabs
								}
								
								_SubmitJson({
									url : __rootPath + "/oa/mail/mailConfig/del.do",
									data : {
										ids : node.configId
									},
									method : 'POST',
									success : function(text) {
										//tree.load('${ctxPath}/oa/mail/mailFolder/getAllFolder.do');
										refresh();
									}
								});
							}
						});
						tree.setActiveIndex(0);
					}
				}
				else{                //节点不存在
					alert("请选择一个账号!");
					return;
				}
			}
		}
		
		/*绘制邮箱账号配置树的节点图标*/
		function draw(e) {
			if (e.node.parentId == "0")
				e.iconCls = "icon-folder";
			if (e.node.type == "RECEIVE-FOLDER")
				e.iconCls = "icon-receive";
			if (e.node.type == "SENDER-FOLDER")
				e.iconCls = "icon-sender";
			if (e.node.type == "DRAFT-FOLDER")
				e.iconCls = "icon-draft";
			if (e.node.type == "DEL-FOLDER")
				e.iconCls = "icon-trash";
			if (e.node.type == "OTHER-FOLDER")
				e.iconCls = "icon-folder";
		}
		
		/*写邮件*/
		function writeMail() {
			var tree = mini.get("leftTree");
			var node = tree.getSelected();
			var checkNode=null;                      //查找节点
			if (node == null) {
				alert("请选择一个账号!");
				return;
			}
			else{
				tree.findNodes(function(f_node){    //查找是否有该节点
					if(f_node.mailConfig.configId==node.configId){
						checkNode=f_node;   //如果有
						return;
					}
				});
				if(checkNode!=null){      //如果该节点存在
					_OpenWindow({
						url : __rootPath + "/oa/mail/outMail/edit.do?configId="
								+ node.configId,
						title : "写邮件",
						height : 800,
						width : 800
					});
				}
				else{                //节点不存在
					alert("请选择一个账号!");
					return;
				}
			}
		}
		
		

		/*收邮件*/
		function getMail(e) {
						var x=e.htmlEvent.clientX;         //当前鼠标位置
						var y=e.htmlEvent.clientY;
						var menu=mini.get("contextMenu");
						menu.showAtPos(x, y);
		}
		
		/*树加载事件处理*/
 		function treeOnload() {
			var tree=mini.get("leftTree");
        	var configIndex=getCookie('_configIndex');
        	if(tree){            		
            	if(configIndex!=null && configIndex!=''){
            		tree.setActiveIndex(parseInt(configIndex));
            	}else{
            		tree.setActiveIndex(0);
            	}
        	}
		}
        
        function treeActiveChange(e){
        	var tree=mini.get("leftTree");
        	if(tree){
        	 	mini.Cookie.set('_configIndex', tree.getActiveIndex());
        	}
        }

		/*contextMenu点击事件处理*/
		/* function onItemClick(e){
	         var item = e.sender;
	         var configId=item.getId();
	         var folderId="";
	         _SubmitJson({
					url : __rootPath + "/oa/mail/outMail/getMail.do",
					data : {
						configId : configId
					},
					submitTips:"正在收取邮件中...",
					method : 'POST',
					success : function(text) {
						_SubmitJson({
							url : "${ctxPath}/oa/mail/mailFolder/getUnreadSum.do",
							showMsg:false,
							data:{
								mailFolderId:folderId,
								configId:configId
							},
							method:"POST",
							success:function(text){
								var str=text.data;
								setNodeName("收件箱("+str+")"); //设置树节点文字
							}
						});
					}
				});
		} */
		
		/*刷新树菜单*/
		function refresh(){
			leftTree.load('${ctxPath}/oa/mail/mailFolder/getAllFolder.do');
			var menu=mini.get("contextMenu");
			menu.load('${ctxPath}/oa/mail/mailConfig/refreshAccountList.do');
		}
		
		function getMailByCId(e){
			var item = e.item;
			var my=item.el;
			var myParent=$(my).parent();
			//alert(myParent.html());
			var childrenList=$(myParent).find(".mini-menuitem");
			var index = $(childrenList).index(my); 
	         var configId=item.configId;
	         var folderId="";
	         _SubmitJson({
					url : __rootPath + "/oa/mail/outMail/getMail.do",
					data : {
						configId : configId
					},
					submitTips:"正在收取邮件中...",
					method : 'POST',
					success : function(text) {
						_SubmitJson({
							url : "${ctxPath}/oa/mail/mailFolder/getUnreadSum.do",
							showMsg:false,
							data:{
								mailFolderId:folderId,
								configId:configId
							},
							method:"POST",
							success:function(text){
								var str=text.data;
								setNodeName("收件箱("+str+")",index); //设置树节点文字
							}
						});
					}
				});
		}
		
		function delTabs(tabsArray){
			var tab=mini.get("mainTabs");
			var tabs=tab.getTabs();
			for(var i=0;i<tabsArray.length;i++){
				var name="tab$"+tabsArray[i].folderId;
				for(var j=0;j<tabs.length;j++){
					if(name==tabs[j].name){
						tab.removeTab(name);
						break;
					}
				}
			}
		}
		
	</script>

	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
</body>
</html>