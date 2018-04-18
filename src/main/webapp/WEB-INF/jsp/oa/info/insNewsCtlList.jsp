<%-- 
    Document   : [新闻公告权限表]列表页
    Created on : 2017-11-03 11:47:25
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[新闻公告权限表]列表管理</title>
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
                  <td style="white-space:nowrap;border-top:1px solid #909aa6;padding:5px;" class="search-form" >
                     <ul>
						<li><span>NEWS_ID_:</span><input class="mini-textbox" name="Q_NEWS_ID__S_LK"></li>
						<li><span>USER_ID_:</span><input class="mini-textbox" name="Q_USER_ID__S_LK"></li>
						<li><span>GROUP_ID_:</span><input class="mini-textbox" name="Q_GROUP_ID__S_LK"></li>
						<li><span>RIGHT_:</span><input class="mini-textbox" name="Q_RIGHT__S_LK"></li>
						<li><span>TYPE_:</span><input class="mini-textbox" name="Q_TYPE__S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div>
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/oa/info/insNewsCtl/listData.do" idField="ctlId"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="50" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="newsId"  sortField="NEWS_ID_"  width="120" headerAlign="center" allowSort="true">NEWS_ID_</div>
				<div field="userId"  sortField="USER_ID_"  width="120" headerAlign="center" allowSort="true">USER_ID_</div>
				<div field="groupId"  sortField="GROUP_ID_"  width="120" headerAlign="center" allowSort="true">GROUP_ID_</div>
				<div field="right"  sortField="RIGHT_"  width="120" headerAlign="center" allowSort="true">RIGHT_</div>
				<div field="type"  sortField="TYPE_"  width="120" headerAlign="center" allowSort="true">TYPE_</div>
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
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.info.entity.InsNewsCtl" winHeight="450"
		winWidth="700" entityTitle="新闻公告权限表" baseUrl="oa/info/insNewsCtl" />
</body>
</html>