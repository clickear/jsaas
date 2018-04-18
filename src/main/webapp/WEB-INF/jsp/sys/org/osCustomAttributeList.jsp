<%-- 
    Document   : [自定义属性]列表页
    Created on : 2017-12-14 14:02:29
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[自定义属性]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;" >
		    <div title="分类" region="west" width="200"  showSplitIcon="true" class="layout-border-r">
		    <div id="toolbar1">
				<table style="width:100%;" cellpadding="0" cellspacing="0">
	                <tr>
	                    <td style="width:100%;">
	                        <a class="mini-button" iconCls="icon-add" plain="true" onclick="createNewTree">新建分类</a>
	                    </td>
	                </tr>
	            </table>           
	        </div>
		    
		         <ul id="systree" class="mini-tree" url="${ctxPath}/sys/customform/sysCustomFormSetting/getTreeByCat.do?catKey=CAT_CUSTOMATTRIBUTE" style="width:100%; height:100%;" 
					showTreeIcon="true" textField="name" idField="treeId" resultAsTree="false" parentField="parentId" expandOnLoad="true"
	                onnodeclick="treeNodeClick"  contextMenu="#treeMenu" >        
	            </ul> 
		    </div>
		    <showHeader="true" showCollapseButton="false">				
				<div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create" plain="true" onclick="add()">增加</a>
                     <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
                 </td>
             </tr>
              <tr>
                  <td  class="search-form" >
                     <ul>
						<li><span>属性名称:</span><input class="mini-textbox" name="Q_ATTRIBUTE_NAME__S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div>
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/sys/org/osCustomAttribute/listData.do" idField="ID"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="50" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="attributeName"  sortField="ATTRIBUTE_NAME_"  width="120" headerAlign="center" allowSort="true">属性名称</div>
				<div field="key"  sortField="KEY_"  width="120" headerAlign="center" allowSort="true">KEY_</div>
				<div field="attributeType"  sortField="ATTRIBUTE_TYPE_"  width="120" headerAlign="center" allowSort="true">属性类型</div>
				<div field="widgetType"  sortField="WIDGET_TYPE_"  width="120" headerAlign="center" allowSort="true">控件类型</div>
			</div>
		</div>
	</div>
			</div>
	<ul id="treeMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
		<li name="remove" iconCls="icon-remove" onclick="onRemoveNode">删除</li>
	</ul>




	<script type="text/javascript">
	mini.parse();
	var tree=mini.get("systree");
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>'
					+'<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
		}
		
		
		function createNewTree(){
    		var node =tree.getSelectedNode();
	   		var parentId=node?node.treeId:0;
	   		_OpenWindow({
	   			title:'添加表单视图分类',
	   			url:__rootPath+'/sys/core/sysTree/edit.do?parentId='+parentId+'&catKey=CAT_CUSTOMATTRIBUTE',
	   			width:720,
	   			height:350,
	   			ondestroy:function(action){
	   				tree.load();
	   			}
	   		});
		}

		
		
		
		function treeNodeClick(e){
			var node=e.node;
			var treeId=node.treeId;
			grid.setUrl("${ctxPath}/sys/org/osCustomAttribute/getAttrsByTreeId.do?treeId="+treeId);
			grid.reload();
		}
		
		function onBeforeOpen(e) {
			var menu = e.sender;
			var node = tree.getSelectedNode();
			if (!node) {
				e.cancel = true;
				return;
			}
		}
		
		function onRemoveNode(e){
			 var node = tree.getSelectedNode();
			 mini.confirm("确认删除?", "提示",
			            function (action) {
			                if (action == "ok") {
			                	_SubmitJson({
			                    	url:"${ctxPath}/sys/core/sysTree/del.do",
			                    	method:'POST',
			                    	data:{ids: node.treeId},
			                    	 success: function(text) {
			                            tree.reload();
			                        }
			                     });	
			                }
			            }
			        );

		}

	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.org.entity.OsCustomAttribute" winHeight="450"
		winWidth="700" entityTitle="自定义属性" baseUrl="sys/org/osCustomAttribute" />
</body>
</html>