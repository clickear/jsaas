<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>流程授权管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="titleBar mini-toolbar">
		<ul>
			<li>
				<a class="mini-button" iconCls="icon-create"   onclick="add()">增加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-remove"  onclick="remove()">删除</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox">
			<form id="searchForm" class="text-distance" style="display: none;">						
				<ul>
					<li>
						<span class="text">名称：</span><input class="mini-textbox" name="Q_NAME__S_LK"  />
					</li>
					<li class="searchBtnBox">
						<a class="mini-button _search" onclick="searchForm(this)" >搜索</a>
						<a class="mini-button _reset" onclick="onClearList(this)">还原</a>
					</li>
					<li class="clearfix"></li>
				</ul>
			</form>	
			<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
				<i class="icon-sc-lower"></i>
			</span>
		</div>
	</div>
	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-datagrid"
			style="width: 100%; height: 100%;" 
			allowResize="false"
			url="${ctxPath}/bpm/core/bpmAuthSetting/listJson.do" 
			idField="id"
			multiSelect="true" 
			showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20"
			allowAlternating="true" 
			pagerButtons="#pagerButtons"
		>
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div 
					name="action" 
					cellCls="actionIcons" 
					width="22"
					headerAlign="center" 
					align="center" 
					renderer="onActionRenderer"
					cellStyle="padding:0;"
				>操作</div>
				<div field="name" width="120" headerAlign="center" sortField="NAME_" allowSort="true">授权名称</div>
				<div  width="120" headerAlign="center" renderer="onEnableRenderer" >启用</div>
				<div field='createTime' dateFormat="yyyy-MM-dd HH:mm:ss"  width="120" headerAlign="center" allowSort="true" sortField="CREATE_TIME_">创建时间</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		function onClear(){
			$("#searchForm")[0].reset();
			grid.setUrl("${ctxPath}/bpm/core/bpmAuthSetting/listJson.do");
			grid.load();
		}
	
		function onEnableRenderer(e){
			var record = e.record;
			var state = record.enable;
			var arr = [ { 'key' : 'yes','value' : '启用','css' : 'green'}, 
			            {'key' : 'no', 'value' : '禁止','css' : 'red'} ];
			
			return $.formatItemValue(arr,state);
		}	
	
	
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\''
					+ pkId
					+ '\')"></span>'
					+ ' <span class="icon-edit" title="编辑" onclick="editRow(\''
					+ pkId
					+ '\',true)"></span>'
					+ ' <span class="icon-remove" title="删除" onclick="delRow(\''
					+ pkId + '\')"></span>';
			return s;
		}
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.bpm.core.entity.BpmAuthSetting" winHeight="450"
		winWidth="700" entityTitle="权限设定"
		baseUrl="bpm/core/bpmAuthSetting" />
</body>
</html>