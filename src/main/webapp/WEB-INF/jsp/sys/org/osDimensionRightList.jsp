<%-- 
    Document   : [维度授权]列表页
    Created on : 2017-05-16 14:12:56
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[维度授权]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create" plain="true" onclick="add()">增加</a>
                     <a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
                     <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
                 </td>
             </tr>
              <tr>
                  <td style="white-space:nowrap;padding:5px;" class="search-form" >
                     <ul>
						<li><span>用户ID:</span><input class="mini-textbox" name="Q_USER_ID__S_LK"></li>
						<li><span>组ID:</span><input class="mini-textbox" name="Q_GROUP_ID__S_LK"></li>
						<li><span>_:</span><input class="mini-textbox" name="Q_DIM_ID_S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div>
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/sys/org/osDimensionRight/listData.do" idField="rightId"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="userId"  sortField="USER_ID_"  width="120" headerAlign="center" allowSort="true">用户ID</div>
				<div field="groupId"  sortField="GROUP_ID_"  width="120" headerAlign="center" allowSort="true">组ID</div>
				<div field="dimId"  sortField="DIM_ID"  width="120" headerAlign="center" allowSort="true">_</div>
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
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.org.entity.OsDimensionRight" winHeight="450"
		winWidth="700" entityTitle="维度授权" baseUrl="sys/org/osDimensionRight" />
</body>
</html>