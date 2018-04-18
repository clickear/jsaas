<%-- 
    Document   : Portal定义列表页
    Created on : 2017-08-15 16:07:14
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>Portal定义列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-create"  onclick="add()">增加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-edit"  onclick="edit()">编辑</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-remove"  onclick="remove()">删除</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox">
			<form id="searchForm" class="search-form" style="display: none;">						
				<ul>
					<li>
						<span>布局名：</span><input class="mini-textbox" name="Q_NAME__S_LK">
					</li>
					<li>
						<span>KEY：</span><input class="mini-textbox" name="Q_KEY__S_LK">
					</li>
					<li class="searchBtnBox">
						<a class="mini-button _search" onclick="searchFrm()" >搜索</a>
						<a class="mini-button _reset" onclick="clearForm()" >清空</a>
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
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/oa/info/insPortalDef/listData.do" idField="protId"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="name"  sortField="NAME_"  width="120" headerAlign="center" allowSort="true">名字</div>
				<div field="key"  sortField="KEY_"  width="120" headerAlign="center" allowSort="true">KEY</div>
				<div field="isDefault"  sortField="IS_DEFAULT_"  width="120" headerAlign="center" allowSort="true" renderer="onIsDefaultRenderer">是否默认</div>
				<div field="priority"  sortField="PRIORITY_"  width="120" headerAlign="center" allowSort="true">优先级</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
					+'<span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>'
					+'<span class="icon-form" title="权限" onclick="PermissionRow(\'' + pkId + '\')"></span>'
					+'<span class="icon-boSet" title="模板编辑" onclick="tempRow(\'' + pkId + '\')"></span>'
					+'<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
		}
		
		function PermissionRow(pkId){
			_OpenWindow({
				title : '编辑权限',
				width : 830,
				height : 360,
				url : __rootPath + '/oa/info/insPortalPermission/edit.do?layoutId=' + pkId
			});
		}
		function tempRow(pkId){
			_OpenWindow({
        		title:'门户配置',
        		height:400,
        		width:780,
        		max:true,
        		url:__rootPath+'/oa/info/insPortalDef/editTmp.do?portId='+pkId,
        		ondestroy:function(action){
        			if(action!='ok') return;
        			grid.load();
        		}
        	});
		}
		
		  function onIsDefaultRenderer(e) {
	            var record = e.record;
	            var isDefault = record.isDefault;
	             var arr = [{'key' : 'YES', 'value' : '是','css' : 'green'}, 
	    			        {'key' : 'NO','value' : '否','css' : 'red'} ];
	    			return $.formatItemValue(arr,isDefault);
	        }
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.info.entity.InsPortalDef" winHeight="360"
		winWidth="830" entityTitle="自定义门户" baseUrl="oa/info/insPortalDef" />
</body>
</html>