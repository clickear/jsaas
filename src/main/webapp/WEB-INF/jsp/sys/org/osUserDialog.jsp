<%
	//用户选择框
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>用户选择框</title>
    <%@include file="/commons/list.jsp" %>
    <style type="text/css">
    	#toolbarBody >*{
    		float: left;
    	}
    	
    	#toolbarBody::after{
    		content: '';
    		display: block;
    		clear: both;
    	}
    	
    	#center{
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
			
			<a class="mini-button" iconCls="icon-ok"   onclick="onOk()">确定</a>
			<a class="mini-button" iconCls="icon-cancel"  onclick="onCancel()">取消</a>
			 
		 </div>
		 
		 <div 
		 	title="用户组" 
		 	region="west" 
		 	width="180"  
		 	showSplitIcon="true" 
	    	showCollapseButton="false"
	    	showProxy="false"
		 	bodyStyle="overflow: auto;"
	 	>
		 	<input 
		 		id="dimCombo" 
		 		class="mini-combobox" 
		 		style="width:100%;" 
		 		onvaluechanged="onDimChange" 
			    url="${ctxPath}/sys/org/osDimension/jsonAll.do?containContact=yes&tenantId=<c:out value="${param.tenantId}" />" 
			    textField="name" 
			    valueField="dimId" 
			    value="${adminDim.dimId}" 
			    text="${adminDim.name}"
		    /> 
		 	<div 
		 		id="groupTree" 
		 		class="mini-tree"  
		 		url="${ctxPath}/sys/org/sysOrg/listByDimId.do?tenantId=<c:out value="${param.tenantId}" />&dimId=${adminDim.dimId}" 
		 		style="width:100%;" 
				showTreeIcon="true"  
				resultAsTree="false" 
				textField="name" 
				idField="groupId" 
				parentField="parentId" 
				onbeforeload="onBeforeGridTreeLoad"
	            onnodeclick="groupNodeClick" 
            >
	        </div>
		 </div>
		 
		  <c:if test="${param['single']==false}">
				<div region="east" title="选中用户列表" width="250" showHeader="false" showCollapseButton="false">
					<div class="mini-toolbar mini-toolbar-one">
						<a class="mini-button" iconCls="icon-remove" onclick="removeSelectedUser">移除</a>
						<a class="mini-button" iconCls="icon-trash"  onclick="clearUser">清空所有</a>
					</div>
					<div class="mini-fit form-outer4">
						<div 
							id="selectedUserGrid" 
							class="mini-datagrid" 
							style="width: 100%; height: 100%;" 
							allowResize="false" url="" 
							idField="userId" 
							multiSelect="true" 
							showColumnsMenu="true" 
							allowAlternating="true" 
							showPager="false" 
							onrowdblclick="removeUser(e)"
						>
								<div property="columns">
									<div type="checkcolumn" width="30"></div>
									<div field="fullname" width="120"  headerAlign="center" allowSort="true">姓名</div>
								</div>
						</div>
					</div>
				</div>
		 </c:if>
		 
		 <div region="center" title="用户列表"   showHeader="false" showCollapseButton="false" style=" mini-toolbar-bottom">
				<div class="titleBar mini-toolbar">
					<div class="searchBox">
						<form id="searchForm" class="text-distance" style="display: none;">						
							<ul>
								<li>
									<input class="mini-hidden" id="groupId" name="groupId"/>
								</li>
								<li>
									<input class="mini-textbox" id="fullname" name="fullname" emptyText="请输入姓名" onenter="onSearch"/>
								</li>
								<li>
									<input class="mini-textbox"  id="userNo" name="userNo" emptyText="请输入用户编号" onenter="onSearch"/>
								</li>
								<li>
									<input class="mini-textbox"  id="email" name="email" emptyText="请输入邮箱" onenter="onSearch"/>
								</li>
								<li>
									<input type="hidden" name="crsf_token" class="mini-hidden"  value="${sessionScope.crsf_token}"/>
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
				
				
				
				
				
				
				
				
				<div class="mini-fit rx-grid-fit form-outer4">
					<div 
						id="userGrid" 
						class="mini-datagrid"  
						style="width: 100%; height: 100%;" 
						allowResize="false" 
						url="${ctxPath}/sys/org/osUser/search.do?tenantId=<c:out value="${param.tenantId}" />" 
						idField="userId"  
						onbeforeload="userLoaded"
						<c:choose>
							<c:when test="${param['single']==true}">
								multiSelect="false"
							</c:when>
							<c:otherwise>
								multiSelect="true" onselect="selectUser(e)"
							</c:otherwise>
						</c:choose>
						 showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true">
						<div property="columns"  class="form-outer">
							<div type="checkcolumn" width="25"></div>
							<div field="sex" width="30"></div>
							<div field="fullname" width="120" sortField="FULLNAME_" headerAlign="center" allowSort="true">姓名</div>
							<div field="userNo" width="120"   headerAlign="center" allowSort="false">编号</div>
							<div field="email" width="120" sortField="EMAIL_" headerAlign="center" allowSort="true">邮件</div>
						</div>
					</div>
				</div>
			</div><!-- end of the region center -->
		
		
		 
	</div>

	
	<script type="text/javascript">
		mini.parse();
		var tenantId="<c:out value='${param.tenantId}' />";
		var userGrid=mini.get("#userGrid");
		var groupTree=mini.get("#groupTree");
		var dimCombo=mini.get("#dimCombo");
		var searchForm=mini.get("#searchForm");
		var selectedUserGrid=mini.get("selectedUserGrid");
		
		var selectedRecord;
		
		userGrid.load();
		function onCancel(){
			CloseWindow('cancel');
		}
		
		function onOk(){
			CloseWindow('ok');
		}
		
		function onBeforeGridTreeLoad(e){
	        var node = e.node;      //当前节点
	        var params = e.params;  //参数对象

	        //可以传递自定义的属性
	        params.parentId = node.groupId; //后台：request对象获取"myField"
		}
		
		
		userGrid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;
	          
	            if(field=='sex'){
	            	if(value=='Male'){
	            		e.cellHtml='<img src="${ctxPath}/styles/images/male.png" alt="男性">';
	            	}else{
	            		e.cellHtml='<img src="${ctxPath}/styles/images/female.png" alt="女性">';
	            	}
	            }
		});
		
		
		var isSingle="<c:out value='${param.single}' />";
		if(isSingle=='false'){
			selectedUserGrid.on("drawcell", function (e) {
		        field = e.field,
		        value = e.value;
	          
	            if(field=='sex'){
	            	if(value=='Male'){
	            		e.cellHtml='<img src="${ctxPath}/styles/images/male.png" alt="男性">';
	            	}else{
	            		e.cellHtml='<img src="${ctxPath}/styles/images/female.png" alt="女性">';
	            	}
	            }
			});
		}
		
		function onClear(){
			$("#searchForm")[0].reset();
			userGrid.setUrl("${ctxPath}/sys/org/osUser/search.do?tenantId="+tenantId);
			userGrid.load();
		}
		
		function removeSelectedUser(){
			var rows=selectedUserGrid.getSelecteds();
			selectedUserGrid.removeRows(rows,false);
		}
		
		function clearUser(){
			selectedUserGrid.clearRows();
		}
		
		function userLoaded(e){
			userGrid.deselectAll(false);
		}
		
		function selectUser(e){
			var record=e.record;
			var user=selectedUserGrid.findRow(function(row){
			    if(row.userId == record.userId){
			    	return true;
			    }
			});
			if(user) return;
				
			selectedUserGrid.addRow($.clone(record));
		}
		
		function removeUser(e){
			var row=e.row;
			selectedUserGrid.removeRow(row);
		}
		
		//搜索
		function onSearch(){
			var formData=$("#searchForm").serializeArray();
			var data=jQuery.param(formData);
			userGrid.setUrl("${ctxPath}/sys/org/osUser/search.do?tenantId="+tenantId+"&"+data);
			userGrid.load();
		}
		
		//维度变化时，更改组织架构
		function onDimChange(e){
			var dimId=dimCombo.getValue();
			groupTree.setUrl("${ctxPath}/sys/org/sysOrg/listByDimId.do?tenantId="+tenantId+"&dimId="+dimId);
			groupTree.load();
		}
		
		//返回选择用户信息
		function getUsers(){
			var users=null;
			if(isSingle=='false')
				users=selectedUserGrid.getData();
			else
				users=userGrid.getSelecteds();
			return users;
		}
		
		/**
		*初始化用户。
		*/
		function init(users){
			selectedUserGrid.setData(users);
		}
		
		function groupNodeClick(e){
			var dimValue=dimCombo.getValue();
			var node=e.node;
			var url;
			if(dimValue=='topContacts'){
				url="${ctxPath}/sys/org/osUser/getContactUserByType.do?typeId="+node.groupId;
			}else{
				url="${ctxPath}/sys/org/osUser/search.do?tenantId="+tenantId+"&groupId="+node.groupId;
				$("#groupId").val(node.groupId);	
			}
			userGrid.setUrl(url);
			userGrid.load();
		}
	</script>
</body>
</html>