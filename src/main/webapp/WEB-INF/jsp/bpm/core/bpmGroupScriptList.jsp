<%-- 
    Document   : [人员脚本]列表页
    Created on : 2017-06-01 11:33:08
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[人员脚本]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-create"  onclick="openScriptEdit()">增加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-edit"  onclick="editMyRow()">编辑</a>
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
						<span>方法名：</span><input class="mini-textbox" name="Q_METHOD_NAME__S_LK">
					</li>
					<li>
						<span>方法描述：</span><input class="mini-textbox" name="Q_METHOD_DESC__S_LK">
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
		<div 
			id="datagrid1" 
			class="mini-datagrid" 
			style="width: 100%; height: 100%;" 
			allowResize="false"
			url="${ctxPath}/bpm/core/bpmGroupScript/listData.do" 
			idField="scriptId" 
			onrowclick="recordRow"
			multiSelect="true" 
			showColumnsMenu="true" 
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20" 
			allowAlternating="true" 
			pagerButtons="#pagerButtons"
		>
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="className"  sortField="CLASS_NAME_"  width="120" headerAlign="center" allowSort="true">类名</div>
				<div field="classInsName"  sortField="CLASS_INS_NAME_"  width="120" headerAlign="center" allowSort="true">类实例名</div>
				<div field="methodName"  sortField="METHOD_NAME_"  width="120" headerAlign="center" allowSort="true">方法名</div>
				<div field="methodDesc"  sortField="METHOD_DESC_"  width="120" headerAlign="center" allowSort="true">方法描述</div>
				<div field="returnType"  sortField="RETURN_TYPE_"  width="120" headerAlign="center" allowSort="true">返回类型</div>
				<div field="argument"  sortField="ARGUMENT_"  width="120" headerAlign="center" allowSort="true">参数</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	mini.parse();
	var editRecordData;
	var grid=mini.get("datagrid1");
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
					+'<span class="icon-edit" title="编辑" onclick="editMyRow(\'' + pkId + '\')"></span>'
					+'<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
		}
		

		function openScriptEdit(){
			_OpenWindow({
				title:'脚本配置',
				width:750,
				url:__rootPath+'/bpm/core/bpmSolVar/groupScriptDlg.do',
				height:500,
				onload:function(){
					var iframe = this.getIFrameEl().contentWindow;
				},
				ondestroy:function(action){
					var iframe = this.getIFrameEl().contentWindow;
					grid.reload();
					
				}
			});}
		
		function editMyRow(){
			_OpenWindow({
				title:'脚本配置',
				width:750,
				url:__rootPath+'/bpm/core/bpmSolVar/groupScriptDlg.do',
				height:500,
				onload:function(){
					var iframe = this.getIFrameEl().contentWindow;
					//iframe.changeClassName();
					iframe._initData(editRecordData);
					iframe.initMethod(editRecordData)
				},
				ondestroy:function(action){
					var iframe = this.getIFrameEl().contentWindow;
					grid.reload();
				}
			});
		}
		
		function recordRow(e){
			editRecordData=e.record;
		}
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmGroupScript" winHeight="450"
		winWidth="700" entityTitle="人员脚本" baseUrl="bpm/core/bpmGroupScript" />
</body>
</html>