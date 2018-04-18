<%-- 
    Document   : [BO定义]列表页
    Created on : 2017-03-01 23:24:22
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务模型列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="titleBar mini-toolbar" >
		<div class="searchBox">
			<form id="searchForm" class="text-distance" style="display: none;">						
				<ul>
					<li>
						<span class="text">名称：</span><input class="mini-textbox" name="Q_NAME__S_LK">
					</li>
					<li>
						<span class="text">别名：</span><input class="mini-textbox" name="Q_ALAIS__S_LK">
					</li>
					<li>
						<span class="text">支持数据表：</span>
						<input 
							class="mini-combobox"   
							textField="text" 
							valueField="id" 
							name="Q_SUPPORT_DB__S_EQ"
   							data="[{id:'yes',text:'支持'},{id:'no',text:'不支持'}]" 
   							value="" 
   							showNullItem="true" 
						/>
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
	
	
	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-datagrid" 
			style="width: 100%; height: 100%;" 
			allowResize="false"
			url="${ctxPath}/sys/bo/sysBoDef/listData.do" 
			idField="id"
			multiSelect="true" 
			showColumnsMenu="true" 
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20" 
			allowAlternating="true" 
			pagerButtons="#pagerButtons"
		>
			<div property="columns">
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="name"  sortField="NAME_"  width="120" headerAlign="center" allowSort="true">名称</div>
				<div field="alais"  sortField="ALAIS_"  width="120" headerAlign="center" allowSort="true">别名</div>
				<div field="comment"  sortField="COMMENT_"  width="120" headerAlign="center" allowSort="true">备注</div>
				<div field="supportDb" renderer="onDbRenderer"  sortField="SUPPORT_DB_"  width="120" headerAlign="center" allowSort="true">支持数据表</div>
				<div field="createTime" sortField="CREATE_TIME_" dateFormat="yyyy-MM-dd HH:mm:ss" width="120" headerAlign="center" allowSort="true">创建时间</div>
				
			</div>
		</div>
	</div>
	
	
	
	
	<div 
		id="formWin" 
		class="mini-window" 
		iconCls="icon-script" 
		title="关联表单" 
		style="width: 550px; height: 350px; display: none;" 
		showMaxButton="true" 
		showShadow="true" 
		showToolbar="true" 
		showModal="true" 
		allowResize="true" 
		allowDrag="true"
	>

		<div property="toolbar">
			<a class="mini-button" iconCls="icon-remove" plain="true" onclick="hiddenFormWindow">关闭</a>
		</div>
		<div class="mini-fit rx-grid-fit">
			<div class="mini-tabs" style="height: 100%; width: 100%">
				<div title="PC表单">
					<div id="pcGrid" class="mini-datagrid" style="width: 100%; height: 150px" allowResize="false" showPager="false"
												 allowAlternating="true">
						<div property="columns">
							<div field="name" width="140" headerAlign="center">名称</div>
							<div field="alias" width="200" headerAlign="center">别名</div>
						</div>
					</div>
				</div>
				<div title="手机表单">
					<div id="mobileGrid" class="mini-datagrid" style="width: 100%; height: 150px" allowResize="false" showPager="false"
												 allowAlternating="true">
						<div property="columns">
							<div field="name" width="140" headerAlign="center">名称</div>
							<div field="alias" width="200" headerAlign="center">别名</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<%@include file="/WEB-INF/jsp/sys/bo/incJsonView.jsp" %>

	
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.bo.entity.SysBoDef" winHeight="450"
		winWidth="700" entityTitle="BO定义" baseUrl="sys/bo/sysBoDef" />
		
	<script type="text/javascript">
	function onActionRenderer(e) {
        var record = e.record;
        var pkId = record.pkId;
        var s = '<span class="icon-dataStructure" title="数据结构" onclick="viewJson(\'' + pkId + '\')"></span>'
                + ' <span class="icon-associationForm" title="关联表单" onclick="relationForm(\'' + pkId + '\',true)"></span>'
                + ' <span class="icon-setting" title="管理BO" onclick="boManage(\'' + pkId + '\')"></span>';
               
        return s;
    }
	
	function onDbRenderer(e) {
		 var record = e.record;
         var supportDb = record.supportDb;
         
         var arr = [ {'key' : 'yes', 'value' : '是','css' : 'green'}, 
			            {'key' : 'no','value' : '否','css' : 'red'} ];
			
			return $.formatItemValue(arr,supportDb);
    }
	
	function relationForm(pkId){
		var url=__rootPath +"/sys/bo/sysBoDef/getRelForm.do?pkId=" +pkId;
		var pcGrid=mini.get("pcGrid");
		var mobileGrid=mini.get("mobileGrid");
		$.get(url,function(data){
			pcGrid.setData(data.pc);
			mobileGrid.setData(data.mobile);
			var win = mini.get("formWin");
			win.show();
		});
		
	}
	
	function boManage(boDefId){
		_OpenWindow({
			title:'表单编辑管理',
			url:__rootPath+'/sys/bo/sysBoDef/manage.do?boDefId=' + boDefId,
			height:450,
			width:800,
			max:true
		});
	}
	
	
	
	function hiddenFormWindow() {
		var win = mini.get("formWin");
		win.hide();
	}
	
	function onFormRenderer(e) {
        var record = e.record;
        var pkId = record.pkId;
        var s = '<span class="icon-dataStructure" title="数据结构" onclick="viewJson(\'' + pkId + '\')"></span>'
                + ' <span class="icon-associationForm" title="关联表单" onclick="relationForm(\'' + pkId + '\',true)"></span>';
                
        return s;
    }
	
	
	
	</script>
</body>
</html>