<%-- 
    Document   : [自定义查询]列表页
    Created on : 2017-02-16 15:49:07
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[自定义查询]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create" plain="true" onclick="add()()">增加</a>
                     <a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
                     <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
                 </td>
             </tr>
              <tr>
                  <td style="white-space:nowrap;padding:5px;" class="search-form" >
                     <ul>
						<li><span>名称:</span><input class="mini-textbox" name="Q_NAME__S_LK"></li>
						<li><span>别名:</span><input class="mini-textbox" name="Q_ALIAS__D_GE"></li>
					
						
					</ul>
                  </td>
              </tr>
         </table>
     </div>
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/sys/db/sysCustomQuery/listData.do" idField="id"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="name"  sortField="NAME_"  width="120" headerAlign="center" allowSort="true">名称</div>
				<div field="alias"  sortField="ALIAS_"  width="120" headerAlign="center" allowSort="true">别名</div>
				<div field="isMultiSelect"  sortField="IS_MULTI_SELECT_"  width="120" headerAlign="center" allowSort="true">是否多选</div>
				<div field="isPage"  sortField="IS_PAGE_"  width="120" headerAlign="center" allowSort="true">是否分页</div>
				<div field="winWidth"  sortField="WIN_WIDTH_"  width="120" headerAlign="center" allowSort="true">打开窗??宽度</div>
				<div field="winHeight"  sortField="WIN_HEIGHT_"  width="120" headerAlign="center" allowSort="true">打开窗口高度</div>
				<div field="conditionHtml"  sortField="CONDITION_HTML_"  width="120" headerAlign="center" allowSort="true">条件HTML</div>
				<div field="pageSize"  sortField="PAGE_SIZE_"  width="120" headerAlign="center" allowSort="true">分页条数</div>
				<div field="dsId"  sortField="DS_ID_"  width="120" headerAlign="center" allowSort="true">数据源ID</div>
				<div field="dsName"  sortField="DS_NAME_"  width="120" headerAlign="center" allowSort="true">数据源名称</div>
				<div field="sql"  sortField="SQL_"  width="120" headerAlign="center" allowSort="true">SQL语句</div>
				<div field="header"  sortField="HEADER_"  width="120" headerAlign="center" allowSort="true">表头中文名称,以JSON存储</div>
				<div field="tenantId"  sortField="TENANT_ID_"  width="120" headerAlign="center" allowSort="true">租用ID</div>
				<div field="createBy"  sortField="CREATE_BY_"  width="120" headerAlign="center" allowSort="true">创建人ID</div>
				<div field="createTime" sortField="CREATE_TIME_" dateFormat="yyyy-MM-dd HH:mm:ss" width="120" headerAlign="center" allowSort="true">创建时间</div>
				<div field="updateBy"  sortField="UPDATE_BY_"  width="120" headerAlign="center" allowSort="true">更新人ID</div>
				<div field="updateTime" sortField="UPDATE_TIME_" dateFormat="yyyy-MM-dd HH:mm:ss" width="120" headerAlign="center" allowSort="true">更新时间</div>
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
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.db.entity.SysCustomQuery" winHeight="450"
		winWidth="700" entityTitle="自定义查询" baseUrl="sys/db/sysCustomQuery" />
</body>
</html>