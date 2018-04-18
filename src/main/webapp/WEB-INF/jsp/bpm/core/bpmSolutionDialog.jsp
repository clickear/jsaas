<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>业务流程解决方案选择框</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
	.mini-layout-border>#center{
 		background: transparent;
	}
</style>
</head>
<body>
<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
	    <div 
	    	region="south" 
	    	showSplit="false" 
	    	showHeader="false" 
	    	height="40" 
	    	showSplitIcon="false"  
	    	style="width:100%" 
	    	bodyStyle="border:0;text-align:center;padding-top:5px;"
    	>
			
			    <a class="mini-button" iconCls="icon-ok" onclick="CloseWindow('ok');">确定</a>			  
			    <a class="mini-button" iconCls="icon-cancel" onclick="CloseWindow('cancel');">取消</a>
			 
		 </div>
	    <div 
	    	title="流程方案分类" 
	    	region="west"  
	    	width="200" 
	    	showSplitIcon="true"
	    	showCollapseButton="false"
	    	showProxy="false"
    	>
	         <ul id="systree" class="mini-tree"  style="width:100%; height:100%;" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_BPM_SOLUTION"
				showTreeIcon="true" textField="name" idField="treeId" resultAsTree="false" parentField="parentId" expandOnLoad="true"
                onnodeclick="treeNodeClick">
            </ul>
	    </div>
	    <div title="流程方案列表" region="center" showHeader="false" showCollapseButton="false">
			
	<div class="titleBar mini-toolbar" >
		<div class="searchBox">
			<form id="searchForm" class="text-distance" style="display: none;">						
				<ul>
					<li>
						<input class="mini-textbox"  name="Q_NAME__S_LK" emptyText="流程解决方案名称"/>
		            	<input class="mini-textbox"  name="Q_KEY__S_LK" emptyText="方案标识键"/>
		                <a class="mini-button" iconCls="icon-search" onclick="onSearch">查询</a>
		                <a class="mini-button" iconCls="icon-cancel"  onclick="onClear">清空</a>
					</li>
					<li class="clearfix"></li>
				</ul>
			</form>	
			<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
				<i class="icon-sc-lower"></i>
			</span>
		</div>
     </div>
			
			
			
			
			
			
			
			
			
			
			
			
				 
			<div class="mini-fit" style="height: 100%;">
				<c:choose>
					<c:when test="${param.single=='false'}">
						<c:set var="multiSelect" value="true"/>
					</c:when>
					<c:otherwise>
						<c:set var="multiSelect" value="false"/>
					</c:otherwise>
				</c:choose>
				<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/bpm/core/bpmSolution/solutionList.do" 
				idField="solId" multiSelect="${multiSelect}" 
					sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true">
					<div property="columns">
						<div type="checkcolumn" width="20"></div>
						<div name="action" cellCls="actionIcons" width="23" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding-left:2px;">操作</div>
						<div field="name" sortField="NAME_" width="140" headerAlign="center" allowSort="true">解决方案名称</div>
						<div field="key" sortField="KEY_" width="100" headerAlign="center" allowSort="true">标识键</div>
						<div field="createTime" sortField="CREATE_TIME_" dateFormat="yyyy-MM-dd HH:mm:ss" width="80" headerAlign="center" allowSort="true">创建时间</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmSolution" 
	winHeight="450" winWidth="780" entityTitle="业务流程解决方案" baseUrl="bpm/core/bpmSolution" />
	<script type="text/javascript">
	var searchForm=new mini.Form('searchForm')
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
   		grid.setUrl(__rootPath+'/bpm/core/bpmSolution/solutionList.do?treeId='+node.treeId);
   		grid.load();
   	}
	
	function getSolutions(){
		return grid.getSelecteds();
	}
	
	function onSearch(){
		grid.load(searchForm.getData());
	}
	
	function onClear(){
		searchForm.reset();
	}
	</script>
</body>
</html>