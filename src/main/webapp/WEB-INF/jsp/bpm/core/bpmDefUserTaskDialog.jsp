<%-- 
    Document   : 流程定义对话框
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>流程定义对话框</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;" borderStyle="border-left:1px solid #aaa;border-right:1px solid #aaa">
		<div region="south" showSplit="false" showHeader="false" height="40" showSplitIcon="false"  style="width:100%">
			<div class="mini-toolbar" style="text-align:center;padding-top:8px;border-top:none;border-right:none" bodyStyle="border:0">
			    <a class="mini-button" iconCls="icon-ok"  onclick="onOk()">确定</a>
			    <span style="display:inline-block;width:25px;"></span>
			    <a class="mini-button" iconCls="icon-cancel" onclick="onCancel()">取消</a>
			</div>	 
		 </div>
	    <div title="流程定义分类" region="west" width="180"  showSplitIcon="true" >
	         <ul id="systree" class="mini-tree" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_BPM_DEF" style="width:100%;" 
				showTreeIcon="true" textField="name" idField="treeId" resultAsTree="false" parentField="parentId" expandOnLoad="true"
                onnodeclick="treeNodeClick"  contextMenu="#treeMenu">        
            </ul>
	    </div>
	    <div title="业务流程解决方案" region="center" width="670" showHeader="true" showCollapseButton="false">
			<div id="toolbar1" class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">
				<form id="searchForm">
				    <table style="width:100%;">
				        <tr>
				            <td style="width:100%;" id="toolbarBody">
				            	 <input class="mini-hidden" id="groupId" name="groupId"/>
				            	 <input class="mini-textbox" id="fullname" name="fullname" emptyText="请输入姓名"/>
				            	 <input class="mini-textbox"  id="userNo" name="userNo" emptyText="请输入用户编号"/>
				                 <input class="mini-textbox"  id="email" name="email" emptyText="请输入邮箱"/>
				                 <a class="mini-button" iconCls="icon-search" onclick="onSearch">查询</a>
				                 <a class="mini-button" iconCls="icon-cancel"  onclick="onClear">清空</a>
				            </td>
				        </tr>
				    </table>
				</form>          
	        </div>
		
			<div class="mini-fit" style="height: 100px;">
				<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" 
				url="${ctxPath}/bpm/core/bpmDef/listForDialog.do" idField="defId" multiSelect="${multiSelect}" showColumnsMenu="true" 
				sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" onrowclick="loadNode">
					<div property="columns">
						<div type="checkcolumn" width="40"></div>
						<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
						<div field="subject" width="120" headerAlign="center" allowSort="true">标题</div>
						<div field="key" width="120" headerAlign="center" allowSort="true">标识Key</div>
						<div field="status" width="120" headerAlign="center" allowSort="true">状态</div>
						<div field="version" width="120" headerAlign="center" allowSort="true">版本号</div>
					</div>
				</div>
			</div>
		</div>
		<div title="任务节点" region="east" width="450" showHeader="true" showCollapseButton="false">
			<div class="mini-fit" style="height: 100px;">
				<div id="datagrid2" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" 
				url="" idField="nodeId" multiSelect="false" showColumnsMenu="true" 
                allowAlternating="true">
					<div property="columns">
						<div type="checkcolumn" width="40"></div>
						<div field="nodeId" width="120" headerAlign="center" allowSort="true">节点Id</div>
						<div field="nodeName" width="120" headerAlign="center" allowSort="true">节点名称</div>
						<div field="nodeType" width="120" headerAlign="center" allowSort="true">节点类型</div>
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
				var flag;
				if(mini.get("datagrid2").getSelecteds().length>0)
					flag=true;
				else
					flag=false;
				if(flag)
					CloseWindow('ok');
				else{
					alert("请选择流程定义中的某一个节点！");
					return;
				}
			}
			//返回Selected
			function getBpmDef(){
				var selected=grid.getSelected();
				return selected;
			}
			//获取流程定义节点
			function loadNode(e){
				var record=e.record;
				var nodeGrid=mini.get("datagrid2");
				var defId=record.defId;
				nodeGrid.setUrl("${ctxPath}/bpm/core/bpmSolution/getByDefId.do");
				nodeGrid.load({defId:defId});
			}
			
			function GetData(){
				var nodeGrid=mini.get("datagrid2");
				var row=nodeGrid.getSelected();
				return row;
			}
     </script>
</body>
</html>