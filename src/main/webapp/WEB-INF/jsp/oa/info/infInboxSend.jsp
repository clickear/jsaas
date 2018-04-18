<%-- 
    Document   :已发消息的页面
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>已发消息管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-edit" plain="true" onclick="sendMsg()">发送信息</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-mailDown" plain="true" target='_self' href='${ctxPath}/oa/info/infInbox/receive.do'>已收信息</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-group" plain="true" target='_self' href='${ctxPath}/oa/info/infInbox/groupMsg.do'>组消息</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-reload" plain="true" onclick="clearSearch()">刷新</a>
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
						<span class="text">收件人：</span> <input name="Q_fullname_S_LK" class="mini-textbox" /> 
					</li>
					<li>
						<span class="text">时间：</span><input name="Q_startTime_D_GT" class="mini-datepicker" value="" format="yyyy-MM-dd" />
					</li>
					<li>
						<span class="text">到：</span><input name="Q_endTime_D_LT" class="mini-datepicker" value="" format="yyyy-MM-dd" />
					</li>
					<li class="searchBtnBox">
						<a class="mini-button _search" onclick="search1" >搜索</a>
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
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/oa/info/infInbox/sended.do" idField="recId" multiSelect="true" showColumnsMenu="true" onrowdblclick="onRowDblClick" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20" allowSort="false"></div>
				<div name="action" cellCls="actionIcons" width="22" allowSort="false" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="fullname" width="40" headerAlign="center" allowSort="false" allowSort="true">收信人</div>
				<div field="groupName" width="40" headerAlign="center" allowSort="false" allowSort="true">收信组</div>
				<div field="content" width="250" headerAlign="center" allowSort="false" allowSort="true">消息内容</div>
				<div field="createTime" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm" width="50">发送时间</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">

        	
        //清空查询
		function clearSearch1(e) {
			var form = new mini.Form('searchForm');
			form.clear();
		}
		//查询
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
    	//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.recId;
			var s = '<span class="icon-detail" title="明细" onclick="readMsg(\''+ pkId + '\')"></span>'
					+ ' <span class="icon-emailAdd" title="重发" onclick="reSend(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-remove" title="删除" onclick="delSendMsg(\'' + pkId + '\')"></span>';
			return s;
		}
		//删除消息
		function delSendMsg(pkId) {
			if (confirm("是否删除这条已发消息?")) {
				_SubmitJson({
					url : "${ctxPath}/oa/info/infInbox/delSendMsg.do",
					data : {
						pkId : pkId
					},
					method : "POST",
					success : function() {
						grid.load();
					}
				});
			}
		}
		//重发消息
		function reSend(pkId) {
			_SubmitJson({
				url : "${ctxPath}/oa/info/infInbox/reSend.do",
				data : {
					pkId : pkId
				},
				method : "POST",
				success : function() {
					grid.load();
				}
			});
		}
		//打开发送消息页面
		function sendMsg() {
			_OpenWindow({
				url : "${ctxPath}/oa/info/infInnerMsg/send.do",
				title : "发送新消息",
				width : 600,
				height : 350,
				iconCls:'icon-newMsg',
				allowResize : false,
				showMaxButton:false
			//max:true,
			});
		}
		//查看消息
		function readMsg(pkId) {
			_OpenWindow({
				url : "${ctxPath}/oa/info/infInbox/sendGet.do?pkId=" + pkId,
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
		//双击消息打开查看消息
		function onRowDblClick(e) {
			var record = e.record;
			var pkId = record.recId;
			_OpenWindow({
				url : "${ctxPath}/oa/info/infInbox/sendGet.do?pkId=" + pkId,
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
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.info.entity.InfInbox" winHeight="450" winWidth="700" entityTitle="消息" baseUrl="oa/info/infInbox" />
</body>
</html>