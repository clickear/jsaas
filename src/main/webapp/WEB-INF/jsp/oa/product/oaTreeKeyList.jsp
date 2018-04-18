<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>产品类型列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet"　type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet"　type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js"　type="text/javascript"></script>

</head>
<body>
	<div id="layout1" class="mini-layout" style="width:100%;height:89%;" borderStyle="border-left:1px solid #aaa;border-right:1px solid #aaa">
	    <div title="参数定义分类" region="west" width="180"  showSplitIcon="true" showProxyText="true" >
	        <div id="toolbar1" class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">
				<table style="width:100%;" cellpadding="0" cellspacing="0">
	                <tr>
	                    <td style="width:100%;">
	                        <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshSysTree()">刷新</a>
	                    </td>
	                </tr>
	            </table>           
	        </div>
	         <ul id="systree" class="mini-tree" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_ASSETS_SORT" style="width:100%;" 
				showTreeIcon="true" textField="name" idField="treeId" resultAsTree="false" parentField="parentId" expandOnLoad="true"
                onnodeclick="treeNodeClick"  contextMenu="#treeMenu">        
            </ul>
	    </div>
<div title="产品类型表单" region="center" showHeader="true" showCollapseButton="false">
	<redxun:toolbar
		entityName="com.redxun.oa.product.entity.OaProductDefParaKey" excludeButtons="popupAddMenu,popupAttachMenu,popupSearchMenu,popupSettingMenu,detail,edit,remove,fieldSearch" />
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/oa/product/oaProductDef/oaTreeKeyList.do" idField="keyId" multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div field="name" width="50" headerAlign="center" allowSort="true">名称</div>
			</div>
		</div>
	</div>
  </div>
   <div title="已选择属性" region="east" width="200"  showCloseButton="false" showSplitIcon="true">
         <div id="selectedList" class="mini-listbox"               
         showCheckBox="true" multiSelect="true" >     
         <div property="columns">
         <div field="name" width="150" headerAlign="center" allowSort="true">类型</div>    
         </div>
     </div>
    </div>
</div>
<div title="south" region="south" showSplit="false" showHeader="false" height="100" style="border:none"  bodyStyle="padding:6px;">
			<div style="clear:both;content: ''"></div>
			<div style="text-align:center;margin-top:12px;padding-top:10px;border-top:solid 1px #ccc">
				<a class="mini-button" iconCls="icon-ok" onclick="onOk()">确定</a> &nbsp;&nbsp; <a class="mini-button" onclick="onCancel()" iconCls="icon-cancel">取消</a>&nbsp;&nbsp;
				<a class="mini-button" iconCls="icon-start" onclick="addSelected()">选择</a>&nbsp;&nbsp;<a class="mini-button" iconCls="icon-remove" onclick="removeSelecteds()">删除</a>&nbsp;&nbsp;
				<a class="mini-button" iconCls="icon-trash" onclick="removeAllSelecteds()">清空</a>
</div>
</div>

	<script type="text/javascript">
			mini.parse();
			var grid=mini.get("datagrid1");
			var selectedList = mini.get("selectedList");
			grid.load();
	      //刷新树
		   	function refreshSysTree(){
		   		var systree=mini.get("systree");
		   		systree.load();
		   	} 	
		   	//按分类树查找数据字典
		   	function treeNodeClick(e){
		   		var node=e.node;
		   		grid.setUrl(__rootPath+'/oa/product/oaProductDef/oaTreeKeyList.do?treeId='+node.treeId);
		   		grid.load();
		   	}
			//取消
			function onCancel(){
				CloseWindow('cancel');
			}
			//确定
			function onOk(){
				CloseWindow('ok');
			}
			
		    function GetData() {
		    	var newList=selectedList.getData();
				return newList;
		    }
		    function addSelected() {
		        
		        var items = grid.getSelecteds();

		        //根据id属性，来甄别要加入选中的记录
		        var idField = grid.getIdField();

		        //把已选中的数据，用key-value缓存，以便进一步快速匹配
		        var idMaps = {};
		        var selecteds = selectedList.getData();
		        for (var i = 0, l = selecteds.length; i < l; i++) {
		            var o = selecteds[i];
		            var id = o[idField];
		            idMaps[id] = o;
		        }

		        //遍历要加入的数组
		        for (var i = items.length - 1; i >= 0; i--) {
		            var o = items[i];
		            var id = o[idField];
		            if (idMaps[id] != null) items.removeAt(i);
		        }

		        selectedList.addItems(items);
		    }

		    function removeSelecteds() {
		        var items = selectedList.getSelecteds();
		        selectedList.removeItems(items);
		    }
		    function removeAllSelecteds() {
		        selectedList.removeAll();
		    }
        </script>
</body>
</html>