<%
	//用户选择框
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>从属用户选择框</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <%@include file="/commons/dynamic.jspf" %>
    <link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
    <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
    <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
    <script src="${ctxPath}/scripts/share.js" type="text/javascript"></script> 
</head>
<body>
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
		 <div region="south" showSplit="false" showHeader="false" height="40" showSplitIcon="false"  style="width:100%">
			<div class="mini-toolbar" style="text-align:center;padding-top:8px;border-top:none;border-right:none" bodyStyle="border:0">
			    <a class="mini-button" iconCls="icon-ok"  style="width:60px;" onclick="onOk()">确定</a>
			    <span style="display:inline-block;width:25px;"></span>
			    <a class="mini-button" iconCls="icon-cancel" style="width:60px;" onclick="onCancel()">取消</a>
			</div>	 
		 </div>
		 <div title="用户组" region="west" width="180"  showSplitIcon="true" iconCls="icon-group">
		 	<input id="dimCombo" class="mini-combobox" style="width:100%;" onvaluechanged="onDimChange" ondrawcell="onDrawDimension"
			    url="${ctxPath}/sys/org/osDimension/jsonAll.do?tenantId=${param['tenantId']}" textField="name" valueField="dimId" value="${adminDim.dimId}"/> 
		 	<div id="groupTree" class="mini-tree"  url="${ctxPath}/sys/org/sysOrg/listByDimId.do?tenantId=${param['tenantId']}&dimId=${adminDim.dimId}" style="width:100%;" 
					showTreeIcon="true"  resultAsTree="false" textField="name" idField="groupId" parentField="parentId" expandOnLoad="true"
	                onnodeclick="groupNodeClick" >        
	        </div>
		 </div>
		 <div region="center" title="用户列表"  bodyStyle="padding:2px;" showHeader="true" iconCls="icon-user"  showCollapseButton="false">
			<div class="mini-toolbar" style="padding:2px;">
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
			<div class="mini-fit">
				<div id="userGrid" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/sys/org/osUser/search.do?tenantId=${param['tenantId']}" 
					idField="userId" 
					<c:choose>
						<c:when test="${param['single']==true}">
							multiSelect="false"
						</c:when>
						<c:otherwise>
							multiSelect="true"
						</c:otherwise>
					</c:choose> 
					showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
					<div property="columns">
						<div type="checkcolumn" width="50"></div>
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
		var tenantId="${param['tenantId']}";
		var userGrid=mini.get("#userGrid");
		var groupTree=mini.get("#groupTree");
		var dimCombo=mini.get("#dimCombo");
		var searchForm=mini.get("#searchForm");
		userGrid.load();
		function onCancel(){
			CloseWindow('cancel');
		}
		
		function onOk(){
			CloseWindow('ok');
		}
		
		function onDrawDimension(e){
			var item = e.record, field = e.field, value = e.value;
            
            e.cellHtml ='<img src="${ctxPath}/styles/icons/dimension.png"/>'+ '&nbsp;&nbsp;<span style="color:green;">'+value+'</span>';  
		}
		
		userGrid.on("drawcell", function (e) {
	            var record = e.record,
	            
		        //column = e.column,
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
		
		function onClear(){
			$("#searchForm")[0].reset();
			userGrid.setUrl("${ctxPath}/sys/org/osUser/search.do?tenantId="+tenantId);
			userGrid.load();
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
			var users=userGrid.getSelecteds();
			return users;
		}
		
		function groupNodeClick(e){
			var node=e.node;
			$("#groupId").val(node.groupId);
			userGrid.setUrl("${ctxPath}/sys/org/osUser/search.do?tenantId="+tenantId+"&groupId="+node.groupId);
			userGrid.load();
		}
	</script>
</body>
</html>