<%-- 
    Document   : 内部消息组消息页面
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>组消息列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-edit" plain="true" onclick="sendMsg()">发送信息</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-mailUp" plain="true" target='_self' href='${ctxPath}/oa/info/infInbox/send.do'>已发信息</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-mailDown" plain="true" target='_self' href='${ctxPath}/oa/info/infInbox/receive.do'>已收信息</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-reload" plain="true" onclick="clearSearch()">刷新</a>
			</li>
			<li></li>
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox">
			<form id="searchForm" class="search-form" style="display: none;">						
				<ul>
					<li>
						<span class="text">发件人：</span> <input name="Q_sender_S_LK" class="mini-textbox" />
					</li>
					<li>
						<span class="text">时间：</span><input name="Q_startTime_D_GT" class="mini-datepicker" value="" format="yyyy-MM-dd" />
					</li>
					<li>
						<span class="text">到</span><input name="Q_endTime_D_LT" class="mini-datepicker" value="" format="yyyy-MM-dd" />
						<input type="hidden" name="crsf_token" class="mini-hidden"  value="${sessionScope.crsf_token}"/> 
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
			url="${ctxPath}/oa/info/infInbox/groupedMsg.do" 
			idField="msgId" 
			multiSelect="true" 
			showColumnsMenu="true" 
			onrowdblclick="onRowDblClick" 
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20" 
			ondrawcell="onDrawCell" 
			allowAlternating="true" 
			pagerButtons="#pagerButtons"
		>
			<div property="columns">
				<div type="checkcolumn" width="20" allowSort="false"></div>
				<div name="status" field="status" cellCls="actionIcons" width="22" allowSort="false" headerAlign="center" align="center" cellStyle="padding:0;">状态</div>
				<div name="action" cellCls="actionIcons" width="22" allowSort="false" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="content" headerAlign="center" width="250" allowSort="false" allowSort="true">消息内容</div>
				<div field="category" width="50" headerAlign="center" allowSort="false" allowSort="true">消息分类</div>
				<div field="sender" width="50" headerAlign="center" allowSort="true">发送人名</div>
				<div field="createTime" width="50" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm">收到时间</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");
		grid.load();
		//清空搜索框
		function clearSearch1(e) {
			var form = new mini.Form('searchForm');
			form.clear();
		}
		//搜索
		function search1(e) {
			var button = e.sender;
			var el = button.getEl();
			var form = $(el).parents('form');

			if (form != null) {
				var formData = form.serializeArray();
				var data = {};
				//加到查询过滤器中
				data.filter = mini.encode(formData);

				data.pageIndex = grid.getPageIndex();
				data.pageSize = grid.getPageSize();
				data.sortField = grid.getSortField();
				data.sortOrder = grid.getSortOrder();
				grid.load(data);
			}

		}
		//功能按钮列
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="查看" onclick="readMsg(\'' + pkId + '\')"></span>';
			return s;
		}
		
		//删除消息
		function delMsg(pkId) {
			_SubmitJson({
				url : "${ctxPath}/oa/info/infInbox/delMsg.do",
				data : {
					pkId : pkId
				},
				method : "POST",
				success : function() {
				}
			});
		}
		
		//判断是否已读,来显示未读已读图标
		function onDrawCell(e) {
			var record = e.record;
			var isRead = record.isRead;
			if (e.field == "status") {
				if (isRead == "yes") {
					e.cellHtml = '<span class="icon-readedMsg" title="已读"></span>';
				} else if (isRead == "no") {
					e.cellHtml = '<span class="icon-newMsg" title="未读"></span>';
				} else if (isRead == "group") {
					e.cellHtml = '<span class="icon-group" title="组消息"></span>';
				}
			}
		}

		//打开发送消息页面
		function sendMsg() {
			_OpenWindow({
				url : "${ctxPath}/oa/info/infInnerMsg/send.do",
				title : "发送新消息",
				width : 600,
				height : 350,
				iconCls:'icon-edit',
				allowResize : false,
				showMaxButton:false
			//max:true,
			});
		}
		
		//查看消息
		function readMsg(pkId) {
			_OpenWindow({
				url : "${ctxPath}/oa/info/infInbox/recGet.do?isGroup=yes&pkId=" + pkId,
				width : 500,
				height : 350,
				title : "消息内容",
				ondestroy : function(action) {
					var pageIndex = grid.getPageIndex();
					var pageSize = grid.getPageSize();
					grid.load({
						pageIndex : pageIndex,
						pageSize : pageSize
					});
				}
			});
		}
		
		//双击触发查看消息
		function onRowDblClick(e) {
			var record = e.record;
			var pkId = record.pkId;
			_OpenWindow({
				url : "${ctxPath}/oa/info/infInbox/recGet.do?isGroup=yes&pkId=" + pkId,
				width : 500,
				height : 308,
				title : "消息内容",
				ondestroy : function(action) {
					var pageIndex = grid.getPageIndex();
					var pageSize = grid.getPageSize();
					grid.load({
						pageIndex : pageIndex,
						pageSize : pageSize
					});
				}
			});
		}
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.info.entity.InfInnerMsg" winHeight="450" winWidth="700" entityTitle="消息" baseUrl="oa/info/infInnerMsg" />
</body>
</html>