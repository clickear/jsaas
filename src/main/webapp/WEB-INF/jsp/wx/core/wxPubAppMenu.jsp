
<%-- 
    Document   : [公众号菜单]编辑页
    Created on : 2017-06-29 16:57:29
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>公众号菜单</title>
<%@include file="/commons/edit.jsp"%>

</head>

<body>
 <div class="mini-toolbar" >
    <a class="mini-button" iconCls="icon-save" plain="true" onclick="saveMenus(false)">保存</a>
    <a class="mini-button" iconCls="icon-save" plain="true" onclick="saveMenus(true)">生成</a>
    <a class="mini-button" iconCls="icon-create" plain="true" onclick="addMenu()">新增一级菜单</a>
    <a class="mini-button" iconCls="icon-create" plain="true" onclick="addSubMenu()">新增子菜单</a>
    <a class="mini-button" iconCls="icon-remove" plain="true" onclick="deleteMenu()">删除</a>
     </div>
<div class="mini-fit">
<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
    <div region="east"  showSplit="false" showHeader="false" width="400">
      <div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
							<table class="table-detail column_1" cellspacing="1" cellpadding="0">
								<tr class="menuType" style="display: none;">
									<th>菜单类型</th>
									<td><input id="menuType"  class="mini-combobox" style="width: 150px;" textField="text" valueField="id"
										data="[{'text':'推送事件','id':'click'},{'text':'跳转网页','id':'view'},{'text':'扫码','id':'scancode_push'},{'text':'扫码接收中','id':'scancode_waitmsg'},{'text':'调用相机','id':'pic_sysphoto'},{'text':'调用相机或相册','id':'pic_photo_or_album'},{'text':'调用微信相册','id':'pic_weixin'},{'text':'地理位置','id':'location_select'}]"
										showNullItem="false" allowInput="false" onvaluechanged="changeType" /></td>
								</tr>
								
								<tr class="menuKey" style="display: none;">
									<th>菜单键值：</th>
									<td>
									<input id="menuKey" name="menuKey" class="mini-textbox" emptyText="输入键值"  width="250" onvaluechanged="changeKey" />
									</td>
								</tr>
								
								<tr class="viewUrl" style="display: none;">
									<th>填写url</th>
									<td><input id="menuView" name="menuView" class="mini-textbox" emptyText="请输入url"  width="200" onvaluechanged="changeView" />
									 <!-- <a class="mini-button" iconCls="icon-save" plain="true" onclick="chooseUrl()">选择</a> --> 
									</td>
								</tr>

							</table>
						</div>
		</form>
	</div>
    </div>
    <div  region="center"  >
				<div id="treegrid1" class="mini-treegrid" style="width: 100%; height: 100%; " showTreeLines="true" onnodeselect="selectMenu" allowLeafDropIn="false" allowDrag="true" allowDrop="true"
					showTreeIcon="true" url="${ctxPath}/wx/core/wxPubApp/getMenus.do?pubId=${wxPubApp.id}" allowCellEdit="true" ondrawnode="drawMenu" cellEditAction="celldblclick" 
					expandOnLoad="true"  treeColumn="name" idField="key" parentField="parent" resultAsTree="false" allowCellSelect="true" allowCellValid="true" allowDrag="true" allowLeafDropIn="false" onbeforedrop="beforeDrop">
					<div property="columns">
						<div type="indexcolumn">序号</div>
						<div name="name" field="name" width="100">
							菜单名<input name="editorName" property="editor" class="mini-textbox" maxlength="7" />
						</div>
					</div>
				</div>
			</div>
</div>
</div>
	
<script type="text/javascript">
mini.parse();
var treeGrid=mini.get("treegrid1");
var menuType=mini.get("menuType");
var menuKey=mini.get("menuKey");
var menuView=mini.get("menuView");

//treeGrid.setCurrentCell([0,1]);
	/**
	 * 改变菜单类型
	 */
	function changeType(){
		var selectNode=treeGrid.getSelectedNode();//当前选择的节点
		var value=menuType.getValue();
		if(value=='view'){
			$('.viewUrl').show();
			$('.menuKey').hide();
			menuView.setValue(selectNode.url);
		}else{
			menuKey.setValue(selectNode.key);
			$('.viewUrl').hide();
			$('.menuKey').show();
		}
		selectNode.type=value;
	}
	
	/**
	 * 改变键值
	 */
	function changeKey(){
		var selectNode=treeGrid.getSelectedNode();//当前选择的节点
		var value=menuKey.getValue();
		selectNode.key=value;
	}
	
	/**
	 * 改变url值
	 */
	function changeView(){
		var selectNode=treeGrid.getSelectedNode();//当前选择的节点
		var value=menuView.getValue();
		selectNode.url=value;
	}
	/*新增子菜单*/
	function addSubMenu(){
		var selectNode=treeGrid.getSelectedNode();
		if(selectNode==undefined||selectNode.parent!="0"){
			alert("请选择父节点菜单");
			return ;
		}else{
			selectNode.type="sub";//将其设置为父菜单
			var nodes = treeGrid.findNodes(function(node) {//查找父节点下的子节点
				if (node.parent == selectNode.key)
					return true;
			});
		if(nodes.length<5){//数量合法,允许添加
				var newNode={};
				newNode.key=""+ (new Date()).valueOf();
				newNode.parent=selectNode.key;
				newNode.name="";
				treeGrid.addNode(newNode,0,selectNode);
				treeGrid.selectNode(newNode);
		}else{//超额不允许添加
			alert('子菜单不允许超过5个');
		}
		}
	}

	/*新增一级菜单*/
	function addMenu(){
		var menuTypeValue=menuType.getValue();
		var nodes = treeGrid.findNodes(function(node) {
			if (node.parent == "0")
				return true;
		});
		if (nodes.length < 3) {//允许添加
		var newNode={};
		newNode.key=""+ (new Date()).valueOf();
		newNode.parent="0";
		newNode.name="";
		treeGrid.addNode(newNode);
		treeGrid.selectNode(newNode);
		} else {//超额不允许添加
			alert('一级菜单不允许超过3个');
		}
		
	}
	
	function beforeDrop(e){
		var dragNode=e.dragNode;
		var dropNode=e.dropNode;
		var dragAction=e.dragAction;
		console.log(dragNode);
		console.log(dropNode);
		console.log(dragAction);
		if(dragNode.parent!=dropNode.parent){
			e.cancel=true;
			mini.showTips({
	            content: "<b>非法移动</b>",
	            state: 'danger',
	            x: 'center',
	            y: 'center',
	            timeout: 2000
	        });
		}
		
	}

	function deleteMenu(){
		var selectNode=treeGrid.getSelectedNode();//当前选择的节点
		if(selectNode != undefined){
			var parent=selectNode.parent;
			treeGrid.removeNode(selectNode);
			var nodes = treeGrid.findNodes(function(node) {
				if (node.key == parent)
					return true;
			});
			if(nodes.length>0){
				if(nodes[0].children.length==0){
					treeGrid.removeNode(nodes[0]);
				}
			}
		}
	}
	

	function drawMenu(e) {
		var node = e.node;
		if (node.parent == "0") {
			e.iconCls = "icon-suspend";
		} else {
			e.iconCls = "icon-flow";
		}
	}

	function selectMenu(e) {
		var node = e.node;
		if(node.name!=""&&node.type!="sub"){
			$('.menuType').show();
			menuType.setValue(node.type);
			changeType();
		}else if(node.type==undefined){
			menuType.setValue("");
			$('.menuType').show();
			$('.viewUrl').hide();
			$('.menuKey').hide();
		}else{
			$('.menuType').hide();
			$('.viewUrl').hide();
			$('.menuKey').hide();
		}
	}
	
	function saveMenus(submit){
		if(loopValidate()){
			 $.ajax({
					type:"post",
					url:"${ctxPath}/wx/core/wxPubApp/createMenu.do",
					data:{pubId:'${wxPubApp.id}',menus:mini.encode(treeGrid.getData()),submit:submit},
					success:function (result){
						CloseWindow('ok');
					}
				}); 
		}else{
			alert("请将菜单信息补充完整");
			return;
		}
		
		
	}
	
	function chooseUrl(){
		/* _OpenWindow({
			url : '${ctxPath}/你的url.do?pubId=${wxPubApp.id}',
			title : "选择url",
			width : 800,
			height : 400,
			onload : function() {
			},	
			ondestroy : function(action) {
				 if (action == 'ok'){//上个页面CloseWindow('ok')
					 var iframe = this.getIFrameEl().contentWindow;
					 var 后面要填的值=iframe.你那个页面的回调函数();
					 menuView.setValue(后面要填的值);
					 changeView();
			               }
			}
		});	 */
	}
	
	function loopValidate(){
		var list=treeGrid.getList();
		for(var i=0;i<list.length;i++){
			var menu=list[i];
			if(menu.name.length<1||menu.type==""){
				return false;
			}
		}
		return true;
	}
</script>
</body>
</html>