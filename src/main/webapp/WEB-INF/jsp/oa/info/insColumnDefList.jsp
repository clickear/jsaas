<%-- 
    Document   : [ins_column_def]列表页
    Created on : 2017-08-16 11:39:47
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>栏目列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-create"  onclick="add()">增加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-edit"  onclick="edit(true)">编辑</a>
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
						<span>栏目名：</span><input class="mini-textbox" name="Q_NAME__S_LK">
					</li>
					<li>
						<span>KEY：</span><input class="mini-textbox" name="Q_KEY__S_LK">
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
			url="${ctxPath}/oa/info/insColumnDef/listData.do" idField="colId"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="name"  sortField="NAME_"  width="120" headerAlign="center" allowSort="true">栏目名字</div>
				<div field="key"  sortField="KEY_"  width="120" headerAlign="center" allowSort="true">栏目KEY</div>
				<div field="function"  sortField="FUNCTION_"  width="120" headerAlign="center" allowSort="true">调用方法</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var isNews = record.isNews;
			var name = record.name;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
					+'<span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>'
					+'<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			if(isNews=="YES"){
				s += '<span class="icon-col-news" title="管理公告" onclick="mgrNews(\'' + pkId + '\',\'' + name + '\')"></span>'
			}
			return s;
		}
		
		function mgrNews(colId, name){
			top['index'].showTabFromPage({
				title : name + '-信息公告',
				tabId : 'colNewsMgr_' + colId,
				url : __rootPath + '/oa/info/insNews/byColId.do?colId=' + colId
			});
		}
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.info.entity.InsColumnDef" winHeight="450"
		winWidth="700" entityTitle="自定义栏目" baseUrl="oa/info/insColumnDef" />
</body>
</html>