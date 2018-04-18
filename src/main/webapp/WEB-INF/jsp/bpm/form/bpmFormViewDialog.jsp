<%-- 
    Document   : 业务视图对话框
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>业务视图对话框</title>
<%@include file="/commons/list.jsp"%>
<style>
	.mini-layout-region{
		background: transparent;
	}
</style>
</head>
<body>
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">	
	    <div region="south" showSplit="false" showHeader="false" height="40" showSplitIcon="false"  
	    	style="width:100%" bodyStyle="border:0;text-align:center;padding-top:5px;">
			
			    <a class="mini-button" iconCls="icon-ok" onclick="CloseWindow('ok');">确定</a>			  
			    <a class="mini-button" iconCls="icon-cancel" onclick="CloseWindow('cancel');">取消</a>
				 
		 </div>
		     
	    <div title="业务视图列表" region="center" showHeader="false" showCollapseButton="false">
		<div class="titleBar mini-toolbar">
			<div class="searchBox">
				<form id="searchForm" class="search-form" style="display: none;">
					<ul>
						<li>
							<span class="text">
								名称：
							</span>
							<input class="mini-textbox" name="Q_NAME__S_LK" />
						</li>
						<li>
							<span class="text">
								标识键:
							</span>
							<input class="mini-textbox" name="Q_KEY__S_LK" />
						</li>
						<li>
							<input class="mini-hidden" name="boDefIds" value="${param.boDefIds}" />
						</li>
						<li class="searchBtnBox">
							<a class="mini-button _search" onclick="searchForm(this)">
								搜索
							</a>
							<a class="mini-button _reset" onclick="onClearList(this)">
								清空
							</a>
						</li>
						<li class="clearfix">
						</li>
					</ul>
				</form>
				<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
					<i class="icon-sc-lower">
					</i>
				</span>
			</div>
		</div>
              
	         <div class="mini-fit rx-grid-fit form-outer5">
		        
					<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;"
					 allowResize="false" url="${ctxPath}/bpm/form/bpmFormView/search.do?boDefIds=${param.boDefIds}" idField="fmId" multiSelect="${multiSelect}"
					 sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
						<div property="columns">
							<div type="checkcolumn" width="20"></div>
							<div field="name" width="140" headerAlign="center" >名称</div>
							<div field="key" width="140" headerAlign="center" >标识键</div>
							<div field="version" width="80" headerAlign="center">版本号</div>
						</div>
					</div>
				
	        </div>
	    </div><!-- end of the center region  -->	
		     
		  
		</div>
		<script type="text/javascript">
		var boDefId="${param.Q_boDefId_S_EQ}";
		mini.parse();
		var grid=mini.get('datagrid1');
		var multiSelect='${multiSelect}';
		grid.load();
		
		   	
	   	//按分类树查找数据字典
	   	function treeNodeClick(e){
	   		var node=e.node;
	   		grid.setUrl(__rootPath+'/bpm/form/bpmFormView/search.do?Q_boDefId_S_EQ='+boDefId+'&treeId='+node.treeId);
	   		grid.load();
	   	}
	   	
	   	function getFormView(){
	   		return grid.getSelecteds();	
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