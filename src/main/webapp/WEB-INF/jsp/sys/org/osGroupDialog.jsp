<%--
	//用户组选择器
	//Author:csx
	//Description:若传入关系类型，则显示该关系类型的另一方用户组，否则显示全部关系的用户
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <title>用户组选择框</title>
	<%@include file="/commons/list.jsp"%>
	<style>
		.mini-layout-region{
			background: transparent;
		}
		#west{
			background: #fff;
		}
	</style>
</head>
<body>

	<div id="layout1" class="mini-layout" style="width:100%;height:100%;" >
		 <div region="south" showSplit="false" showHeader="false" height="40" showSplitIcon="false"  
			style="width:100%" bodyStyle="border:0;text-align:center;padding-top:5px;">
			     <a class="mini-button" iconCls="icon-ok"   onclick="onOk()">确定</a>
				 <a class="mini-button" iconCls="icon-cancel"  onclick="onCancel()">取消</a>
				 
		 </div>
		 
		 <c:if test="${empty param['showDimId'] or param['showDimId']=='false'}">
			 <div title="组织维度" region="west" width="140"  showSplitIcon="true" showHeader="false">
			 	<div 
			 		id="dimTree" 
			 		class="mini-tree"  
			 		url="${ctxPath}/sys/org/osDimension/jsonAll.do?tenantId=${param['tenantId']}&excludeAdmin=${param['excludeAdmin']}" 
			 		style="width:100%;" 
					showTreeIcon="true"  
					resultAsTree="false" 
					textField="name" 
					idField="dimId"  
					expandOnLoad="true"
	                onnodeclick="dimNodeClick" 
	                allowAlternating="true"
                >
		        </div>
			 </div>
		 </c:if>
		 <div 
		 	region="center" 
		 	style="padding:0;margin:0;" 
		 	<c:if test="${param['showDimId']!='1'}"> 
		 		title="用户组"  
		 		showHeader="false" 
	 		</c:if> 
	 		showCollapseButton="false"
 		>
			<%-- <div class="mini-toolbar" >
				<form id="searchForm" style="padding:0;margin:0;">
				    <table style="width:100%;">
				        <tr>
				            <td style="width:100%;" id="toolbarBody">
				            	 <input class="mini-hidden" id="dimId" name="dimId" value="${param['showDimId']}"/>
				            	 <input class="mini-textbox" id="name" name="name" emptyText="请输入名称" onenter="onSearch"/>
				            	 <input class="mini-textbox"  id="key" name="key" emptyText="请输入标识Key" onenter="onSearch"/>
				                 <a class="mini-button" iconCls="icon-search" onclick="onSearch">查询</a>
				                 <a class="mini-button" iconCls="icon-cancel"  onclick="onClear">清空</a>
				            </td>
				        </tr>
				    </table>
			    </form>
			</div> --%>
			
			
			<div class="titleBar mini-toolbar">
					<div class="searchBox">
						<form id="searchForm" class="text-distance" style="display: none;">						
							<ul>
								<li>
									<input class="mini-hidden" id="dimId" name="dimId" value="${param['showDimId']}"/>
								</li>
								<li>
									<input class="mini-textbox" id="name" name="name" emptyText="请输入名称" onenter="onSearch"/>
								</li>
								<li>
									<input class="mini-textbox"  id="key" name="key" emptyText="请输入标识Key" onenter="onSearch"/>
								</li>
								<li>
									<span class="text">状态</span>
									<input 
										class="mini-combobox" 
										name="Q_STATUS__S_EQ" 
										showNullItem="true"  
										emptyText="请选择..."
										data="[{id:'DEPLOYED',text:'发布'},{id:'CREATED',text:'创建'}]"
									/>
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
					style="width:100%;height:100%;"     
			        showTreeIcon="true"  
			        onbeforeload="onBeforeGridTreeLoad"
			        resultAsTree="false" 
			        treeColumn="name" 
			        idField="groupId" 
			        parentField="parentId" 
			        allowResize="true" 
			        onlyCheckSelection="true"
			        allowRowSelect="true" 
			        allowUnselect="true"   
			        onbeforeload="userLoaded"
			        allowAlternating="true"
					url="${ctxPath}/sys/org/sysOrg/search.do?showDimId=${param['showDimId']}
					&tenantId=${param['tenantId']}&excludeAdmin=${param['excludeAdmin']}&config=${param['config']}" 
					<c:choose>
						<c:when test="${param['single']=='true'}">
							multiSelect="false"
						</c:when>
						<c:otherwise>
							multiSelect="true" onselect="selectGroup(e)"
						</c:otherwise>
					</c:choose>
				 >
					<div property="columns">
						<div type="checkcolumn" width="40"></div>
						<div field="name" name="name" displayfield="name" width="180" headerAlign="center" allowSort="true">名称</div>
						<div field="key" width="130" headerAlign="center" allowSort="true">标识Key</div>
					</div>
				</div>
			</div>
		</div><!-- end of the region center -->
		
		<c:if test="${param['single']==false}">
		
		<div region="east" title="选中用户组列表"   width="250" showHeader="false" showCollapseButton="false">
			<div class="mini-toolbar mini-toolbar-one" >
				<a class="mini-button" iconCls="icon-remove" onclick="removeSelectedGroup">移除</a>
				<a class="mini-button" iconCls="icon-trash"  onclick="clearGroup">清空所有</a>
			</div>
			<div class="mini-fit form-outer4">
				<div id="selectedGroupGrid" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="" 
						idField="groupId" multiSelect="true" showColumnsMenu="true" allowAlternating="true" showPager="false" onrowdblclick="removeGroup(e)">
						<div property="columns">
							<div type="checkcolumn" width="30"></div>
							<div field="name" name="name" displayfield="name" width="120" headerAlign="center" allowSort="true">名称</div>
						</div>
				</div>
			</div>
		</div><!-- end of the region east -->
		</c:if>
	</div>
	
	<script type="text/javascript">
		mini.parse();
		var groupGrid=mini.get("groupGrid");
		var dimTree=mini.get("dimTree");
		var tenantId="${param['tenantId']}";
		var showDimId="${param['showDimId']}";
		var searchForm=mini.get("searchForm");
		var selectedGroupGrid=mini.get("selectedGroupGrid");
		var dialogUrl = __rootPath + "/sys/org/sysOrg/search.do?showDimId=${param['showDimId']}&tenantId=${param['tenantId']}&excludeAdmin=${param['excludeAdmin']}&config=${param['config']}";
		var selectedRecord;		
		function onCancel(){
			CloseWindow('cancel');
		}
		
		function onBeforeGridTreeLoad(e){
			
			var tree = e.sender;    //树控件
	        var node = e.node;      //当前节点
	        var params = e.params;  //参数对象

	        //可以传递自定义的属性
	        params.parentId = node.groupId; //后台：request对象获取"myField"
		}
		
		function onOk(){
			<c:if test="${param['reDim']=='true'}">
			if(dimTree && (!dimTree.getSelectedNode())){
				alert('请选择维度！');
				return false;
			}
			</c:if>
			CloseWindow('ok');
		}
		
		var isSingle="${param['single']}";
		if(isSingle=='false'){
			selectedGroupGrid.on("drawcell", function (e) {
	            var record = e.record,
	            
		        //column = e.column,
		        field = e.field,
		        value = e.value;
	          
	            if(field=='name'){
	            	e.cellHtml=value+'('+record.key+')';
	            }
			});
		}
		
		function onClear(){
			$("#searchForm")[0].reset();
			groupGrid.setUrl(dialogUrl);
			
		}
		
		function removeSelectedGroup(){
			var rows=selectedGroupGrid.getSelecteds();
			selectedGroupGrid.removeRows(rows,false);
		}
		
		function clearGroup(){
			selectedGroupGrid.clearRows();
		}
		
		function userLoaded(e){
			groupGrid.deselectAll(false);
		}
		
		
		
		function selectGroup(e){
			var record=e.record;
			var group=selectedGroupGrid.findRow(function(row){
			    if(row.groupId == record.groupId){
			    	return true;
			    }
			});
			if(group) return;
				
			selectedGroupGrid.addRow($.clone(record));
		}
		
		function removeGroup(e){
			var row=e.row;
			selectedGroupGrid.removeRow(row);
		}
		
		//搜索
		function onSearch(){
			var formData=$("#searchForm").serializeArray();
			var data=jQuery.param(formData);
			groupGrid.setUrl("${ctxPath}/sys/org/sysOrg/search.do?tenantId="+tenantId+"&"+data);
		}

		//返回选择用户信息
		function getGroups(){
			var groups=null; 
			if(isSingle=='false')
				groups=selectedGroupGrid.getData();
			else
				groups=groupGrid.getSelecteds();
			return groups;
		}

		//获得选择的维度
		function getSelectedDim(){
			var node = dimTree.getSelectedNode();
            return node;
		}
		
		function dimNodeClick(e){			
			var node=e.node;
			$("#dimId").val(node.dimId);
			groupGrid.setUrl("${ctxPath}/sys/org/sysOrg/search.do?dimId="+node.dimId+"&tenantId="+tenantId);
		}
		
		
	</script>
</body>
</html>