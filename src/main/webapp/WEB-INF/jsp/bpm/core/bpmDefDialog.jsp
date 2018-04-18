<%-- 
    Document   : 流程定义对话框
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<title>流程定义对话框</title>
<%@include file="/commons/list.jsp"%>
<!-- script src="${ctxPath}/scripts/printer/LodopFuncs.js" type="text/javascript"></script-->
<style type="text/css">#center{background: transparent;}</style>
</head>
<body>
	<div id="layout1" class="mini-layout"  style="width:100%;height:100%;">
	 
	 	<div region="south" showSplit="false" showHeader="false" height="40" bodyStyle="text-align:center;padding-top:6px;" showSplitIcon="false" >
		     <a class="mini-button" iconCls="icon-ok"  onclick="onOk()">确定</a>
		     <a class="mini-button" iconCls="icon-cancel" onclick="onCancel()">取消</a>
	 	</div>
	    <div title="流程定义分类" region="west" width="170" height="100%" showSplitIcon="true"showHeader="false">
	     	 <ul 
	     	 	id="systree" 
	     	 	class="mini-tree" 
	     	 	url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_BPM_DEF" 
	     	 	style="width:100%; overflow: hidden;" 
		      	showTreeIcon="true" 
		      	textField="name" 
		      	idField="treeId" 
		      	resultAsTree="false" 
		      	parentField="parentId" 
		      	expandOnLoad="true"
	          	onnodeclick="treeNodeClick"  
	          	contextMenu="#treeMenu"
          	></ul>
	    </div>
	    <div  title="业务流程解决方案" region="center" showHeader="false" showCollapseButton="false" >
			<div class="titleBar mini-toolbar">
				<div class="searchBox">
					<form id="searchForm" class="text-distance" style="display: none;">						
						<ul>
							<li>
								<input class="mini-hidden" id="groupId" name="groupId"/>
							</li>
							<li>
								<input class="mini-textbox" name="Q_subject_S_LK" emptyText="请输入标题"/>
							</li>
							<li>
								<input class="mini-textbox"  name="Q_key_S_LK" emptyText="请输入标识"/>
							</li>
							<li>
								<div class="searchBtnBox">
									<a class="mini-button _search" onclick="onSearch">搜索</a>
									<a class="mini-button _reset" onclick="onClear">清空</a>
								</div>
							</li>
							<li class="clearfix"></li>
						</ul>
					</form>	
					<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
						<i class="icon-sc-lower"></i>
					</span>
				</div>
	        </div>
			<div class="mini-fit">
				<div 
					id="datagrid1" 
					class="mini-datagrid" 
					style="width: 100%; height: 100%;" 
					allowResize="false" 
					url="${ctxPath}/bpm/core/bpmDef/listForDialog.do" 
					idField="defId" 
					multiSelect="${multiSelect}" 
					showColumnsMenu="true" 
					sizeList="[5,10,20,50,100,200,500]" 
					pageSize="20" 
					allowAlternating="true" 
				>
					<div property="columns"  >
						<div type="checkcolumn" width="25"></div>
						<div name="action" cellCls="actionIcons" width="35" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
						<div field="subject" width="160" headerAlign="center" allowSort="true">标题</div>
						<div field="key" width="100" headerAlign="center" allowSort="true">标识Key</div>
						<div field="status" width="80" renderer="onStatusRenderer" headerAlign="center" allowSort="true">状态</div>
						<div field="version" width="80" headerAlign="center" allowSort="true">版本号</div>
					</div>
				</div>
			</div>
	    </div>
 </div>
	

	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmDef" winHeight="450" winWidth="700" entityTitle="流程定义管理" baseUrl="bpm/core/bpmDef" />
	<script type="text/javascript">
		var searchForm=mini.get("#searchForm");
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>';
	            return s;
	        }   	
		   	//按分类树查找数据字典
		   	function treeNodeClick(e){
		   		var node=e.node;
		   		grid.setUrl(__rootPath+'/bpm/core/bpmDef/listForDialog.do?treeId='+node.treeId);
		   		grid.load();
		   	}
		   	function onClear(){
				$("#searchForm")[0].reset();
				grid.setUrl("${ctxPath}/bpm/core/bpmDef/listForDialog.do");
				grid.load();
			}
			
			//搜索
			function onSearch(){
				var formData=$("#searchForm").serializeArray();
				var data=jQuery.param(formData);
				grid.setUrl("${ctxPath}/bpm/core/bpmDef/listForDialog.do?"+data);
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
			//返回Selected
			function getBpmDef(){
				var selected=grid.getSelected();
				return selected;
			}
			
			function onStatusRenderer(e) {
	            var record = e.record;
	            var status = record.status;
	            var arr = [ {'key' : 'DEPLOYED', 'value' : '已发布','css' : 'green'}, 
				            {'key' : 'INIT','value' : '草稿','css' : 'red'} ];
				return $.formatItemValue(arr,status);
	            
	        }
     </script>
</body>
</html>