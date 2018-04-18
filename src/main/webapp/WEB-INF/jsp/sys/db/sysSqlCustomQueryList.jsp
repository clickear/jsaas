<%-- 
    Document   : [自定义查询]列表页
    Created on : 2017-02-21 15:03:07
    Author     : cjx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[自定义查询]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-create" onclick="add()">增加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
			</li>
			<li>
				<a class="mini-button"  iconCls="icon-remove"  onclick="remove()">删除</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox">
			<form id="searchForm" class="text-distance" style="display: none;">						
				<ul>
					<li>
						<span>名字:</span>
						<input 
							class="mini-textbox"
							name="Q_NAME__S_LK"
						>
					</li>
					<li>
						<span>标识:</span>
						<input 
							class="mini-textbox"
							name="Q_KEY__S_LK"
						>
					</li>
					<li>
						<span>对象名称:</span>
						<input 
							class="mini-textbox"
							name="Q_TABLE_NAME__S_LK"
						>
					</li>
					<li class="searchBtnBox">
						<a class="mini-button _search" onclick="searchForm(this)" >搜索</a>
						<a class="mini-button _reset" onclick="onClearList(this)">清空</a>
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
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/sys/db/sysSqlCustomQuery/listData.do" idField="id"
			multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">操作</div>
				<div field="name" sortField="NAME_" width="120" headerAlign="center"
					allowSort="true">名字</div>
				<div field="key" sortField="KEY_" width="120" headerAlign="center"
					allowSort="true">标识</div>
				<div field="tableName" sortField="TABLE_NAME_" width="120"
					headerAlign="center" allowSort="true">对象名称</div>
				
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			//alert('pkId='+pkId);
			var s = 
			//'<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'+ 
			'<span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>' +
			'<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>' + 
			' <span class="icon-form" title="预览" onclick="preview(\'' + pkId + '\')"></span>'+ 
			' <span class="icon-help" title="帮助" onclick="help(\'' + pkId + '\')"></span>';
			return s;
		}
		//预览
		function preview(pkId) {
			_OpenWindow({
				url : "${ctxPath}/sys/db/sysSqlCustomQuery/preview.do?pkId=" + pkId,
				title : "预览",
				max : true,
				ondestroy : function(action) {
					if (action == 'ok') {
						grid.reload();
					}
				}
			});
		}
		
		function help(pkId){
			_OpenWindow({
				url : "${ctxPath}/sys/db/sysSqlCustomQuery/help.do?pkId=" + pkId,
				title : "帮助",
				width:1000,
				height:375
			});
		}
	</script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.sys.db.entity.SysSqlCustomQuery"
		winHeight="450" winWidth="700" entityTitle="自定义查询"
		baseUrl="sys/db/sysSqlCustomQuery" />
</body>
</html>