<%-- 
    Document   : [数据源定义]管理列表页
    Created on : 2017-02-07 09:03:54
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[数据源定义]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<%--  <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create" onclick="add()()">增加</a>
                     <a class="mini-button" iconCls="icon-edit"  onclick="edit()">编辑</a>
                     <a class="mini-button" iconCls="icon-search"  onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel"  onclick="clearForm()">清空查询</a>
                     <a class="mini-button" iconCls="icon-preview" target="_blank"  href="${ctxPath }/druid">监控</a>
                 </td>
             </tr>
              <tr>
                  <td style="white-space:nowrap;padding:5px;" class="search-form"  >
                     <ul>
						<li><span>数据源名称:</span><input class="mini-textbox" name="Q_NAME__S_LK"></li>
						<li><span>别名:</span><input class="mini-textbox" name="Q_ALIAS__S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div> --%>
     <div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-create" onclick="add()()">增加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-edit"  onclick="edit()">编辑</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-preview" target="_blank"  href="${ctxPath }/druid">监控</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox">
			<form id="searchForm" class="search-form" style="display: none;">						
				<ul>
					<li>
						<span>数据源名称:</span><input class="mini-textbox" name="Q_NAME__S_LK">
					</li>
					<li>
						<span>别名:</span><input class="mini-textbox" name="Q_ALIAS__S_LK">
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


	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-datagrid"
			style="width: 100%; height: 100%;" 
			allowResize="false"
			url="${ctxPath}/sys/core/sysDataSource/listData.do" 
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
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="name"  sortField="NAME_"  width="120" headerAlign="center" allowSort="true">数据源名称</div>
				<div field="alias"  sortField="ALIAS_"  width="120" headerAlign="center" allowSort="true">别名</div>
				<div field="enable"  sortField="ENABLE_"  width="120" renderer="enableRender" headerAlign="center" allowSort="true">是否使用</div>
				<div field="dbType"  sortField="DB_TYPE_"  width="120" headerAlign="center" allowSort="true">数据库类型</div>
				<div field="initOnStart"  sortField="INIT_ON_START_" renderer="initOnStartRender"  width="120" headerAlign="center" allowSort="true">启动时初始化</div>
				<div field="createTime" sortField="CREATE_TIME_" dateFormat="yyyy-MM-dd HH:mm:ss" width="120" headerAlign="center" allowSort="true">创建时间</div>
				
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
					+' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>'
					+' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
		}
		
		function initOnStartRender(e){
			var record = e.record;
			var val=record.initOnStart;
			var arr = [ { 'key' : 'yes','value' : '是','css' : 'green'}, 
			            {'key' : 'no', 'value' : '否','css' : 'red'} ];
			
			return $.formatItemValue(arr,val);
		}
		
		function enableRender(e){
			var record = e.record;
			var val=record.enabled;
			var arr = [ { 'key' : 'yes','value' : '启用','css' : 'green'}, 
			            {'key' : 'no', 'value' : '禁止','css' : 'red'} ];
			
			return $.formatItemValue(arr,val);
		}
	</script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.sys.core.entity.SysDataSource" winHeight="450"
		winWidth="700" entityTitle="数据源定义管理" baseUrl="sys/core/sysDataSource" />
</body>
</html>