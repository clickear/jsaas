<%-- 
    Document   : [微信关注者]列表页
    Created on : 2017-06-30 08:51:08
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[微信关注者]列表管理</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
	#center{
		background: transparent;
	}
</style>
</head>
<body>
	
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;" >
	    <div 
	    	title="用户标签" 
	    	region="west"  
	    	width=220    
	    	showSplitIcon="true"
	    	showCollapseButton="false"
	    	showProxy="false"
    	>
		    <div property="toolbar" class="toolbar-margin">
				<a class="mini-button" iconCls="icon-create" onclick="addTag" plain="true" >添加标签</a>
			</div>
	    
	        <ul 
	         	id="systree" 
	         	class="mini-tree"  
	         	style="width:100%;" 
	         	url="${ctxPath}/wx/core/wxPubApp/listPubTag.do?pubId=${param.pubId}"
				showTreeIcon="true" 
				textField="name" 
				idField="id" 
				resultAsTree="false" 
				expandOnLoad="true" 
                onnodeclick="loadTheGridFromTag"  
                contextMenu="#treeMenu"
            ></ul>
	    </div>
	    <div showHeader="false" showCollapseButton="false"  region="center">
			<!--<div class="mini-toolbar" >
				<table style="width:100%;">
					<tr>
		                 <td style="width:100%;">
		                     <a class="mini-button" iconCls="icon-create" plain="true" onclick="alert(1);">增加</a>
		                     <a class="mini-button" iconCls="icon-edit" plain="true" onclick="alert(1);">编辑</a>
		                     <a class="mini-button" iconCls="icon-remove" plain="true" onclick="alert(1);">删除</a>
		                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchFrm()">查询</a>
		                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
		                 </td>
		             </tr>
				</table> 
			</div>-->
		
			<div class="mini-fit rx-grid-fit" style="height: 100%;">
				<div 
					id="datagrid1" 
					class="mini-datagrid" 
					style="width: 100%; height: 100%;" 
					allowResize="false"
					url="${ctxPath}/wx/core/wxSubscribe/listAll.do?pubId=${param.pubId}" 
					idField="id"
					multiSelect="true" 
					showColumnsMenu="true" 
					sizeList="[5,10,20,50,100,200,500]" 
					pageSize="20" 
					allowAlternating="true" 
					pagerButtons="#pagerButtons"
				>
					<div property="columns">
						<div type="checkcolumn" width="20"></div>
						<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
						<div field="nickName"  sortField="NICK_NAME_"  width="120" headerAlign="center" allowSort="true">名称</div>
						<!-- <div field="subscribe"  sortField="SUBSCRIBE_"  width="120" headerAlign="center" allowSort="true">是否关注</div> -->
						<div field="city"  sortField="CITY_"  width="120" headerAlign="center" allowSort="true">城市</div>
						<!-- <div field="subscribeTime" sortField="SUBSCRIBE_TIME_" dateFormat="yyyy-MM-dd HH:mm:ss" width="120" headerAlign="center" allowSort="true">最后的关注时间</div> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<ul id="treeMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
		<li name="subadd" iconCls="icon-edit" onclick="onEditNode">编辑标签</li>
		<li name="remove" iconCls="icon-remove" onclick="onRemoveNode">删除标签</li>
	</ul>
	

	<script type="text/javascript">
	var grid=mini.get("datagrid1");
	
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>';
					/* +'<span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>' */
					/* +'<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>'; */
				s+='<span class="icon-dimension" title="个人标签" onclick="addInTag(\'' + pkId + '\')"></span>';
			return s;
		}
		
		
		function loadTheGridFromTag(e){
			var node=e.node;
			var tagId=node.id;
			grid.setUrl("${ctxPath}/wx/core/wxSubscribe/listByTagId.do?pubId=${param.pubId}&tagId="+tagId);
			grid.load();
		}
		
		function addInTag(pkId){
			 _OpenWindow({
	               url: "${ctxPath}/wx/core/wxSubscribe/addInTag.do?pubId=${param.pubId}&pkId="+pkId,
	               title: "个人标签", width: 350, height: 550,
	               ondestroy: function(action) {
	                   if (action == 'ok') {
	                       _ShowTips({
		                    	msg:'成功加入标签!'
		                    });
	                       grid.load();
	                   }
	               }
	           });
		}
		
		function onBeforeOpen(e) {
			var menu = e.sender;
			var tree = mini.get("systree");
			var node = tree.getSelectedNode();
			if (!node) {
				e.cancel = true;
				return;
			}
			if (node && node.text == "Base") {
				e.cancel = true;
				e.htmlEvent.preventDefault();
				return;
			}
			////////////////////////////////////////////////////
			
		}
		
		//编辑节点文本（URL）
		function onEditNode(e) {
			var tree = mini.get("systree");
			var node = tree.getSelectedNode();
			var tagId=node.id;
			 _OpenWindow({
				url : __rootPath+"/wx/core/wxTagUser/edit.do?pubId=${param.pubId}&id="+tagId,
				title : "编辑tag",
				width : 500,
				height : 200,
				max:false,
				ondestroy : function(action) {
					if (action == 'ok' && typeof (addCallback) != 'undefined') {
						var iframe = this.getIFrameEl().contentWindow;
						addCallback.call(this, iframe);
					} else if (action == 'ok') {
						tree.reload();
					}
				}
			}); 
			
			
		}
		
		/*删除tag*/
		function onRemoveNode(e){
			var tree = mini.get("systree");
			var node = tree.getSelectedNode();
			var tagId=node.id;
			_SubmitJson({
	        	url:__rootPath+"/wx/core/wxPubApp/deleteTags.do",
	        	type:'POST',
	        	data:{ids: tagId,pubId:'${param.pubId}'},
	        	 success: function(text) {
	        		 if(text.success){
	        			 tree.reload();
	        		 }else{alert("删除失败");}
	            }
	        });
		}

		function addTag(){
			var tree = mini.get("systree");
			_OpenWindow({
				url : __rootPath+"/wx/core/wxTagUser/edit.do?pubId=${param.pubId}",
				title : "添加tag",
				width : 500,
				height : 200,
				max:false,
				ondestroy : function(action) {
					if (action == 'ok' && typeof (addCallback) != 'undefined') {
						var iframe = this.getIFrameEl().contentWindow;
						addCallback.call(this, iframe);
					} else if (action == 'ok') {
						tree.reload();
					}
				}
			}); 
		}
		

	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.wx.core.entity.WxSubscribe" winHeight="450"
		winWidth="700" entityTitle="微信关注者" baseUrl="wx/core/wxSubscribe" />
</body>
</html>