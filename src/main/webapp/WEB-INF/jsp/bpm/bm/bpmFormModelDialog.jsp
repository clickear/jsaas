<%-- 
    Document   : 业务模型选择对话框
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务模型管理列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;" borderStyle="border-left:1px solid #aaa;border-right:1px solid #aaa">
		    <div region="south" showSplit="false" showHeader="false" height="50" showSplitIcon="false"  style="width:100%">
				<div class="mini-toolbar" style="text-align:center;padding-top:8px;border-top:none;border-right:none" bodyStyle="border:0">
				    <a class="mini-button" iconCls="icon-ok" onclick="onOk()">确定</a>
				    <span style="display:inline-block;width:25px;"></span>
				    <a class="mini-button" iconCls="icon-cancel"  onclick="onCancel()">取消</a>
				</div>	 
			 </div>
		    <div title="业务模型分类" region="west" width="180"  showSplitIcon="true" >
		         <ul id="systree" class="mini-tree" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_FORM_MODEL" style="width:100%;" 
					showTreeIcon="true" textField="name" idField="treeId" resultAsTree="false" parentField="parentId" expandOnLoad="true"
	                onnodeclick="treeNodeClick"  >        
	            </ul>
		    </div>
		     
		    <div title="业务模型" region="center" showHeader="true" showCollapseButton="false">
				 <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
		            <form id="searchForm">
			            <table style="width:100%;">
			                <tr>
			                    <td style="width:100%;">
			                    	名称：<input class="mini-textbox" name="Q_name_S_LK"/>&nbsp;标识键:<input class="mini-textbox" name="Q_key_S_LK"/>
			                    	  <a class="mini-button" iconCls="icon-search" onclick="onSearch">查询</a>
					                 <a class="mini-button" iconCls="icon-cancel"  onclick="onClear">清空</a>
			                    </td>
			                  </tr>
			            </table>
		            </form>
                  </div>
		         <div class="mini-fit" style="border:0;">
			        
						<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;"
						 allowResize="false" url="${ctxPath}/bpm/bm/bpmFormModel/search.do" idField="fmId" multiSelect="${multiSelect}"
						 sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
							<div property="columns">
								<div type="checkcolumn" width="20"></div>
								<div field="name" width="140" headerAlign="center" allowSort="true">名称</div>
								<div field="key" width="140" headerAlign="center" allowSort="true">标识键</div>
								<div field="version" width="80" headerAlign="center" allowSort="true">版本号</div>
							</div>
						</div>
					
		        </div>
		    </div><!-- end of the center region  -->
		   </div>
		<script type="text/javascript">
		mini.parse();
		var grid=mini.get('datagrid1');
		var multiSelect=${multiSelect};
		grid.load();
		function onClear(){
			$("#searchForm")[0].reset();
			grid.setUrl("${ctxPath}/bpm/bm/bpmFormModel/search.do");
			grid.load();
		}
		
		//搜索
		function onSearch(){
			var formData=$("#searchForm").serializeArray();
			var data=jQuery.param(formData);
			grid.setUrl("${ctxPath}/bpm/bm/bpmFormModel/search.do?"+data);
			grid.load();
		}
		
		   	
	   	//按分类树查找数据字典
	   	function treeNodeClick(e){
	   		var node=e.node;
	   		grid.setUrl(__rootPath+'/bpm/bm/bpmFormModel/search.do?treeId='+node.treeId);
	   		grid.load();
	   	}
	   	
	   	function getFormModel(){
	   		var formModels=null;
	   		if(multiSelect){
	   			formModels=grid.getSelecteds();
	   		}else{
	   			formModels=grid.getSelected();
	   		}
	   		return formModels;
	   	}
	   	
	   	function onOk(){
	   		CloseWindow('ok');
	   	}
	   	
	   	function onCancel(){
	   		CloseWindow('cancel');
	   	}
    </script>
	
	
</body>
</html>