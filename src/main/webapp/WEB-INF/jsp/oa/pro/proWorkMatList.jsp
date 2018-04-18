<%-- 
    Document   : 动态列表页
    Created on : 2015-12-21, 10:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>动态列表管理</title>
<%@include file="/commons/list.jsp"%>
<style>
	.mini-toolbar{
		margin: 0
	}
	.titleBar{
		margin-top: 0;
		margin-bottom: 0;
	}
</style>
</head>
<body>
		<div style="height: 10px;"></div>
        <div id="toolbutton1" class=" form-outer">
        	<redxun:toolbar entityName="com.redxun.oa.pro.entity.ProWorkMat" excludeButtons="popupAddMenu,popupAttachMenu,detail,edit,remove,popupSearchMenu,fieldSearch,popupSettingMenu">
				<div class="self-toolbar">
					<form id="searchForm">
						<a class="mini-button" iconCls="icon-flow-deploy" plain="true" onclick="discuss">评论</a>
						&nbsp;&nbsp;&nbsp;内容<input 
							name="Q_content_S_LK" 
							class="mini-textbox" 
							width="160"
						/>从
						<input
							name="Q_createTime_D_GT" 
							class="mini-datepicker"  
							format="yyyy-MM-dd" 
							showTime="true"  
							width="100" 
							allowInput="false"
						/> 到 
						<input 
							name="Q_createTime_D_LT" 
							class="mini-datepicker"
							format="yyyy-MM-dd"   
							width="100" 
							allowInput="false"
						/> &nbsp; <a class="mini-button" plain="true" onclick="onSearch" iconCls="icon-search" >查询</a>
					</form>
				</div>
			</redxun:toolbar>
	 </div>
	 <div style="height: 10px;"></div>
	<div class="mini-fit form-outer" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-datagrid" 
			style="width: 100%; height: 100%;" 
			allowResize="false" 
			idField="actionId" 
			multiSelect="true" 
			onrowdblclick="openDetail(e)"
			showColumnsMenu="true" 
			ondrawcell="onDrawCell" 
			onupdate="_LoadUserInfo();" 
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20"
			allowAlternating="true" 
		>
					<div property="columns">
						<div name="action" cellCls="actionIcons" width="32" headerAlign="center" align="center" renderer="onActionRenderer"
							cellStyle="padding:0;">操作</div>
						<div id="mycontent" field="content" width="350" headerAlign="center" >评论内容</div>
						<div field="createBy" width="120" headerAlign="center"  >评论人</div>
						<div field="createTime" width="120" headerAlign="center"   renderer="onRenderer">评论时间</div>
					</div>
				</div>
	</div>

	<script type="text/javascript">
	mini.parse();
	var grid = mini.get("datagrid1");
	if(!${empty type}){//如果type有，那就是MYACTION，则加载为"动态"栏目，否则加载为评论栏目
		grid.setUrl("${ctxPath}/oa/pro/proWorkMat/listAct.do?projectId=${thisprojectId}");//动态
	}else{
		if(${empty project.projectId}){//如果没有projectId则查需求的评论
		grid.setUrl("${ctxPath}/oa/pro/proWorkMat/listByProject.do?reqId=${reqMgr.reqId}"+"&type="+"REQ");
	}else{
	grid.setUrl("${ctxPath}/oa/pro/proWorkMat/listByProject.do?projectId=${project.projectId}");//项目栏目
		}}
	grid.load();
	
	if(!${empty noact}){//如果后台传来有noact字段，则禁止一些操作
		$(function(){
			$("#toolbutton1").hide();
			$("#mycontent").text("项目动态");
		});
	}
	function onActionRenderer(e) {
		var record = e.record;
		var pkId = record.pkId;
		var s = '<span class="icon-detail" title="明细" onclick="detailMyRow(\'' + pkId + '\')"></span>';
		s = s + ' <span class="icon-remove" title="删除" onclick="delMyRow(\''+ pkId + '\')"></span>';
		return s;
	}
	//grid显示创建人
	function onDrawCell(e) {
		var tree = e.sender;
		var record = e.record;
		var field = e.field;
		if (field == 'createBy') {//给reporId那列显示成名字
			e.cellHtml = '<a class="mini-user"  userId="'+record.createBy+'"></a>';
		}
	}
	//评论明细
	function detailMyRow(pkId) {
		_OpenWindow({
			url : __rootPath + "/oa/pro/proWorkMat/get.do?pkId=" + pkId,
			title : "评论明细",
			width : 800,
			height : 500,
		});
	}
	//渲染时间
	function onRenderer(e) {
		var value = e.value;
		if (value)
			return mini.formatDate(value, 'yyyy-MM-dd HH:mm:ss');
		return "暂无";
	}

	//删除行
	function delMyRow(pkId) {
		_SubmitJson({
			url : __rootPath + "/oa/pro/proWorkMat/del.do?ids=" + pkId,
			method : 'POST',
			success : function(text) {
				grid.load();
			}
		});
	}
	
	
	//评论项目
	function discuss(){
		var key;
		var type;
		if(${empty reqMgr.reqId}){
			key="${project.projectId}";
			type="PROJECT";
		}else{
			 key="${reqMgr.reqId}";
			type="REQ";
		}
		_OpenWindow({
			url:__rootPath+"/oa/pro/proWorkMat/edit.do?projectId="+key+"&type="+type,
			title:"评论",
			width:700,
			height:380,
			ondestroy:function(action){
				if(action=='ok'){
					grid.load();
				}
			}});
				
	}
	
	//双击打开项目明细
	function openDetail(e){
		e.sender;
		var record=e.record;
		var pkId=record.pkId;
		detailMyRow(pkId);
	}
	
/* 	//按某些字段查找
	function searchForSomeKey(){
		 var key = document.getElementById("key").value;
		 	var grid=mini.get('datagrid1');
		 	var encodekey=encodeURIComponent(key);
			grid.setUrl("${ctxPath}/oa/pro/proWorkMat/listForKey.do?key="+encodekey+"&projectId=${project.projectId}");                                            
			grid.load();
	} */
	
	//查询
    function onSearch(){
    	var formJson=_GetFormParams("searchForm");
        	grid.setUrl("${ctxPath}/oa/pro/proWorkMat/searchTheMat.do?projectId=${project.projectId}&reqId=${reqMgr.reqId}&filter="+mini.encode(formJson));
        	grid.load();
    }
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.pro.entity.ProWorkMat" winHeight="450" winWidth="700"
		entityTitle="动态" baseUrl="oa/pro/proWorkMat" />
</body>
</html>