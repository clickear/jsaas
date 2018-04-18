<%--
	//用户组选择器
	//Author:csx
	//Description:若传入关系类型，则显示该关系类型的另一方用户组，否则显示全部关系的用户
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>知识分类选择框</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
	.mini-layout-region{
		background: transparent;
	}
	#west .mini-layout-region-body{
		background: #fff;
	}
</style>
</head>
<body>

	<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;">
		<div region="south" showSplit="false" showHeader="false" height="40" showSplitIcon="false">
			<div class="mini-toolbar">
				<table style="width: 100%; padding: 0;">
					<tr>
						<td align="center"><a class="mini-button" iconCls="icon-ok" onclick="onOk()">确定</a> 
						<span style="display: inline-block; width: 25px;"></span> <a class="mini-button" iconCls="icon-cancel" onclick="onCancel()">取消</a></td>
					</tr>
				</table>
			</div>
		</div>
		<div 
			title="知识分类" 
			region="west" 
			width="200"
			showSplitIcon="true" 
	    	showCollapseButton="false"
	    	showProxy="false"
		>
			<div class="mini-fit">
				<div 
					id="tree" 
					class="mini-tree" 
					url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=${param['cat']}" 
					style="width: 100%; height: 100%; padding: 5px;" 
					showTreeIcon="true" 
					textField="name" 
					idField="treeId" 
					parentField="parentId" 
					resultAsTree="false" 
					expandOnNodeClick="false" 
					expandOnLoad="2" 
					contextMenu="#treeMenu" 
					onnodeselect="onNodeSelect" 
					ondrawnode="" 
					onload=""
				></div>
			</div>
		</div>
		<div region="center" bodyStyle="padding:2px;" showCollapseButton="false">
			<div class="titleBar mini-toolbar">
					<div class="searchBox">
						<form id="searchForm" class="text-distance" style="display: none;">						
							<ul>
								<li>
									<input class="mini-textbox" id="name" name="name" emptyText="请输入名称" /> 
									<input class="mini-textbox" id="key" name="key" emptyText="请输入标识Key" />
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
					id="groupGrid" 
					class="mini-treegrid" 
					style="width: 100%; height: 100%;" 
					showTreeIcon="true" 
					resultAsTree="false" 
					treeColumn="name" 
					idField="treeId" 
					parentField="parentId" 
					allowResize="true" 
					expandOnLoad="true" 
					allowRowSelect="true" 
					allowAlternating="true" 
					<c:choose>
						<c:when test="${param['single']=='true'}">
							multiSelect="false"
						</c:when>
						<c:otherwise>
							multiSelect="true"
						</c:otherwise>
					</c:choose>
				>
					<div property="columns">
						<div type="checkcolumn" width="50"></div>
						<div field="name" name="name" displayfield="name" width="120" headerAlign="center" allowSort="true">名称</div>
						<div field="key" width="120" headerAlign="center" allowSort="true">标识Key</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end of the region center -->
	</div>

	<script type="text/javascript">
		mini.parse();
		var groupGrid=mini.get("groupGrid");
		var tenantId="${param['tenantId']}";
		var searchForm=mini.get("searchForm");
		var tree = mini.get('tree');
		var mynodes = tree.getChildNodes(tree.getRootNode());
		var firstPage;
		if (mynodes.length > 0) {
			firstPage = mynodes[0].treeId;//第一个节点的Id
			groupGrid.setUrl("${ctxPath}/kms/core/kdDoc/searchType.do?docTypeId="+firstPage+"&tenantId="+tenantId);
		} else {//如果没有tree，访问list1，给一个空的列表防止报错
			groupGrid.setUrl("${ctxPath}/sys/core/sysReport/listBlank.do");
		}
		
		//groupGrid.load();
		function onCancel(){
			CloseWindow('cancel');
		}
		
		function onOk(){
			CloseWindow('ok');
		}
		
		function onClear(){
			$("#searchForm")[0].reset();
			groupGrid.setUrl("${ctxPath}/kms/core/kdDoc/searchType.do?tenantId="+tenantId);
		}
		
		//搜索
		function onSearch(){
			var formData=$("#searchForm").serializeArray();
			var data=jQuery.param(formData);
			groupGrid.setUrl("${ctxPath}/kms/core/kdDoc/searchType.do?tenantId="+tenantId+"&"+data);
		}

		//返回选择分类信息
		function getGroups(){
			var groups=groupGrid.getSelecteds();
			return groups;
		} 
		//点击左边列表时
		function onNodeSelect(e){
			var node=e.node;
			$("#docTypeId").val(node.treeId);
			groupGrid.setUrl("${ctxPath}/kms/core/kdDoc/searchType.do?docTypeId="+node.treeId+"&tenantId="+tenantId);
			//groupGrid.load();
		}
	</script>
</body>
</html>