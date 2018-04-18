<%-- 
    Document   : [日志实体]列表页
    Created on : 2017-09-21 14:43:53
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[日志实体]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-remove"  onclick="remove()">删除</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox">
			<form id="searchForm" class="search-form" style="display: none;">						
				<ul>
					<li>
						<span>所属模块：</span><input class="mini-textbox" name="Q_MODULE__S_LK">
					</li>
					<li>
						<span>功能：</span><input class="mini-textbox" name="Q_SUB_MODULE__S_LK">
					</li>
					<li>
						<span>操作名：</span><input class="mini-textbox" name="Q_ACTION__S_LK">
					</li>
					<li>
						<span>操作IP：</span><input class="mini-textbox" name="Q_IP__S_LK">
					</li>
					<li class="searchBtnBox">
						<a class="mini-button _search" onclick="searchFrm()" >搜索</a>
						<a class="mini-button _reset" onclick="clearForm()">清空</a>
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
			url="${ctxPath}/sys/log/logEntity/myList.do" idField="id"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="module"  sortField="MODULE_"  width="120" headerAlign="center" allowSort="true">所属模块</div>
				<div field="subModule"  sortField="SUB_MODULE_"  width="120" headerAlign="center" allowSort="true">功能</div>
				<div field="action"  sortField="ACTION_"  width="120" headerAlign="center" allowSort="true">操作名</div>
				<div field="ip"  sortField="IP_"  width="120" headerAlign="center" allowSort="true">操作IP</div>
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
					+'<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
		}
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.log.entity.LogEntity" winHeight="450"
		winWidth="700" entityTitle="日志实体" baseUrl="sys/log/logEntity" />
</body>
</html>