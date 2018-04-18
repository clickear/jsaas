<%-- 
    Document   : [日志模块]列表页
    Created on : 2017-09-21 14:38:42
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[日志模块]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <!-- <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create" onclick="add()">增加</a>
                     <a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
                     <a class="mini-button" iconCls="icon-remove"  onclick="remove()">删除</a>
                     <a class="mini-button" iconCls="icon-search" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" onclick="clearForm()">清空查询</a>
                 </td>
             </tr>
              <tr>
                  <td style="white-space:nowrap;padding:5px;" class="search-form" >
                     <ul>
						<li><span>模块:</span><input class="mini-textbox" name="Q_MODULE__S_LK"></li>
						<li><span>子模块:</span><input class="mini-textbox" name="Q_SUB_MODULE_S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div> -->
     
     
     <div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-create" onclick="add()">增加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
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
						<span>模块：</span><input class="mini-textbox" name="Q_MODULE__S_LK">
					</li>
					<li>
						<span>子模块：</span><input class="mini-textbox" name="Q_SUB_MODULE_S_LK">
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
			url="${ctxPath}/sys/log/logModule/listData.do" idField="id"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="module"  sortField="MODULE_"  width="120" headerAlign="center" allowSort="true">模块</div>
				<div field="subModule"  sortField="SUB_MODULE"  width="120" headerAlign="center" allowSort="true">子模块</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s= '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>';
			s +='<span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>';
			s +='<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			if(record.enable!='FALSE'){
				s +='<span class="icon-read" title="禁用" onclick="enableRow(\'' + pkId + '\')"></span>';
			}
			return s;
		}
		var grid=mini.get("datagrid1");
		
		function enableRow(pkId){
			if (!confirm("确定禁用吗？")) return;
			$.ajax({
				type:'post',
				data:{id:pkId},
				url:"${ctxPath}/sys/log/logModule/disableModule.do",
				success:function (result){
					grid.reload();
				}
			});
		}
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.log.entity.LogModule" winHeight="450"
		winWidth="700" entityTitle="日志模块" baseUrl="sys/log/logModule" />
</body>
</html>