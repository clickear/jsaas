<%-- 
    Document   : 外部邮件列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>外部邮件列表管理</title>
<%@include file="/commons/list.jsp"%>
<!-- script src="${ctxPath}/scripts/printer/LodopFuncs.js" type="text/javascript"></script-->
<style type="text/css">
.icon-mailDown:before{
	font-size:22px;
	color:#707070;
}

.icon-Mailbox:before{
	font-size:22px;
	color:#00e500;
}

</style>
</head>
<body>

	<div id="toolbar1" class="mini-toolbar mini-toolbar-icons" >
		<table style="width: 100%;" class="mini-toolbar-background">
			<tr>
				<td style="width: 30%;">
					<a class="mini-button" iconCls="icon-writemail"  onclick="writeMail">写邮件</a>
					<a id="toDel" class="mini-button" iconCls="icon-remove"  onclick="todelfolder">刪除</a> 
					<a class="mini-button" iconCls="icon-trash"  onclick="Del">彻底删除</a> 
					<a class="mini-button" iconCls="icon-transmit"  onclick="transmit">转发</a> 
					<span class="separater"></span>
					<a class="mini-button" iconCls="icon-transfer"  onclick="onMoveNode">移动到</a>
					<a class="mini-button" iconCls="icon-refresh"  onclick="refresh">刷新</a> 
					<a class="mini-button" onclick="onSearch" iconCls="icon-search">查询</a>
				</td>
			</tr>
		</table>
		
		<table>
			<tr>
				<td>
					<form id="searchForm" class="text-distance">
						<div>
							<span class="text"> 发件人：</span> <input name="Q_senderAddrs_S_LK" class="mini-textbox" />
							<span class="text">主题：</span> <input name="Q_subject_S_LK" class="mini-textbox" /> 				
							<span class="text"> 时间：</span><input id="sDate" name="Q_sendDate_D_GE" class="mini-datepicker" value="" format="yyyy-MM-dd" />到<input id="eDate" name="Q_sendDate_D_LE" class="mini-datepicker" value="" format="yyyy-MM-dd" />
						</div>
					</form>
				</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit rx-grid-fit">


		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/oa/mail/outMail/getMailByFolderId.do?folderId=${param['folderId']}" idField="mailId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" showVGridLines="false" onrowdblclick="check">
			<div property="columns">
			    <div type="checkcolumn" width="20"></div>
				<div type="indexcolumn" width="20" headerAlign="center">序号</div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="readFlag" width="20" headerAlign="center" align="center">阅读状态</div>
				<div field="replyFlag" width="20" headerAlign="center" align="center">回复状态</div>
				<div field="subject" width="100" headerAlign="center" allowSort="true">主题</div>
				<div field="senderAddrs" width="40" headerAlign="center" allowSort="true">发件人</div>
				<div field="recAddrs" width="40" headerAlign="center" allowSort="true">收件人</div>
				<div field="sendDate" width="50" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm">时间</div>
		   </div>
		</div>
	</div>

	<div id="moveWindow" title="选择目标目录" class="mini-window" style="width: 300px; height: 250px;" showModal="true" showFooter="false" allowResize="true">
		<ul id="moveTree" class="mini-tree" style="width: 100%;" url="${ctxPath}/oa/mail/mailFolder/getFolderByGonfigIdOutRoot.do?configId=${param['configId']}" showTreeIcon="true" textField="name" idField="folderId" parentField="parentId" resultAsTree="false" showArrow="false" showTreeLines="true" ondrawnode="draw">
		</ul>
		<div class="mini-toolbar dialog-footer" style="margin-top: 30px; border: none;">
			<table style="width: 100%">
				<tr>
					<td>						
						<a class="mini-button" iconCls="icon-ok" onclick="okWindow()">确定</a>
						<a class="mini-button" iconCls="icon-cancel"onclick="hideWindow()">取消</a>
					</td>
				</tr>
			</table>
		</div>

	</div>

	<script type="text/javascript">
		//行功能按钮
		mini.parse();
		var grid = mini.get("datagrid1");
		grid.load();
		top['main'] = window;
		var configId = "${param['configId']}";
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="查看" onclick="getRow1(\'' + pkId + '\')"></span>' + ' <span class="icon-remove" title="删除" onclick="rowToDel(\'' + pkId + '\')"></span>';
			return s;
		}

		$(function() {
			var btnToDel = mini.get("toDel");
			var isShowDel = "${param['isShowDel']}";
			if (isShowDel == "NO")  //判断是否显示删除按钮
				btnToDel.setVisible(false);
		});

		/*刷新邮件记录*/
		function refresh() {
			var pageIndex = grid.getPageIndex();
			var pageSize = grid.getPageSize();
			grid.load({
				pageIndex : pageIndex,
				pageSize : pageSize
			});
		}

		/*写邮件*/
		function writeMail() {
			var tree = top['mail'].leftTree;
			var node = tree.getSelected();
			if (node == null) {
				alert("请选择一个账号!");
				return;
			}
			_OpenWindow({
				url : __rootPath + "/oa/mail/outMail/edit.do?configId=" + configId,
				title : "写邮件",
				height : 800,
				width : 800
			});
		}

		/*转发邮件*/
		function transmit() {
			var rows = grid.getSelecteds();
			if (rows.length <= 0) {
				alert("请选择记录");
				return;
			}

			var ids = [];
			for (var i = 0, l = rows.length; i < l; i++) {
				var r = rows[i];
				ids.push(r.pkId);
			}
			if (ids.length == 1) {  //转发单封邮件
				var pkId = ids[0];
				_OpenWindow({
					url : __rootPath + "/oa/mail/outMail/edit.do?pkId=" + pkId + "&operation=transmit",
					width : 1000,
					height : 800,
					title : "转发邮件",
					ondestroy : function(action) {

					}
				});

			}
			if (ids.length >= 2 && ids.length <= 5) {//转发多封邮件，生成eml文件再转发
				var temp;
				var fileIds = [];
				_SubmitJson({
					url : __rootPath + "/oa/mail/outMail/mailsTransmit.do",
					method : 'POST',
					showMsg : false,
					data : {
						ids : ids.join(',')
					},
					success : function(text) {
						temp = text.data;
						for (i = 0; i < temp.length; i++) {
							fileIds[i] = temp[i].fileId;
						}
						_OpenWindow({
							url : __rootPath + "/oa/mail/outMail/edit.do?configId=" + configId + "&operation=mailsTransmit&fileIds=" + fileIds.join(','),
							width : 1000,
							height : 800,
							title : "邮件内容",
							ondestroy : function(action) {

							}
						});
					}
				});
			}
			if (ids.length > 5) {//不能转发大于五封邮件
				alert("转发邮件数量不能大于5！");
				return;
			}

		}

		//设置已读和未读图标
		grid.on("drawcell", function(e) {
			field = e.field, value = e.value;
			var s;
			if (field == 'readFlag') {//设置阅读标识
				if (value == "0") {
					s = "<i  title='新邮件' class='iconfont icon-Mailbox'></i>";
				} else {
					s = "<i  title='已读' class='iconfont icon-mailDown'></i>";
				}
				e.cellHtml = s;
			}
			if (field == 'replyFlag') {//设置回复标识
				if (value == "0") {
					s = '<span style="color:orange">未</span>';
				} else {
					s = '<span style="color:green">已</span>';
				}
				e.cellHtml = s;
			}
		});

		//彻底删除邮件
		function Del() {
			var rows = grid.getSelecteds();
			if (rows.length <= 0) {
				alert("请选中一条记录");
				return;
			}
			//行允许删除
			if (rowRemoveAllow && !rowRemoveAllow()) {
				return;
			}

			if (!confirm("确定删除选中记录？"))
				return;

			var ids = [];
			for (var i = 0, l = rows.length; i < l; i++) {
				var r = rows[i];
				ids.push(r.pkId);
			}

			_SubmitJson({
				url : __rootPath + "/oa/mail/outMail/delStatus.do",
				method : 'POST',
				data : {
					ids : ids.join(',')
				},
				success : function(text) {
					top['mail'].refresh();
					grid.load();
				}
			});

		}

		//双击查看邮件  
		function check(e) {
			var record = e.record;
			var pkId = record.pkId;
			var i="${param['i']}";
			_OpenWindow({
				url : "${ctxPath}/oa/mail/outMail/get.do?pkId=" + pkId,
				width : 1000,
				height : 800,
				title : "邮件内容",
				ondestroy : function(action) {
					var pageIndex = grid.getPageIndex();
					var pageSize = grid.getPageSize();
					grid.load({
						pageIndex : pageIndex,
						pageSize : pageSize
					});
					var leftTree = top['mail'].leftTree;
					var node = leftTree.getSelected();
					_SubmitJson({
						url : "${ctxPath}/oa/mail/mailFolder/getUnreadSum.do",
						showMsg : false,
						data : {
							mailFolderId : node.folderId
						},
						method : "POST",
						success : function(text) {
							var str = text.data;
							
							top['mail'].setNodeName("收件箱(" + str + ")",i);
						}
					});
				}
			});
		}

		//图标查看邮件
		function getRow1(pkId) {
			var i="${param['i']}";
			_OpenWindow({
				url : "${ctxPath}/oa/mail/outMail/get.do?pkId=" + pkId,
				width : 1000,
				height : 800,
				title : "邮件内容",
				ondestroy : function(action) {
					var pageIndex = grid.getPageIndex();
					var pageSize = grid.getPageSize();
					grid.load({
						pageIndex : pageIndex,
						pageSize : pageSize
					});
					var leftTree = top['mail'].leftTree;
					var node = leftTree.getSelected();
					_SubmitJson({
						url : "${ctxPath}/oa/mail/mailFolder/getUnreadSum.do",
						showMsg : false,
						data : {
							mailFolderId : node.folderId
						},
						method : "POST",
						success : function(text) {
							var str = text.data;
							top['mail'].setNodeName("收件箱(" + str + ")",i);
						}
					});
				}
			});
		}

		/*单封邮件移动到垃圾箱*/
		function rowToDel(pkId) {
			if (!confirm("确定将选中的邮件已至垃圾箱？（可恢复）"))
				return;
			_SubmitJson({
				url : "${ctxPath}/oa/mail/outMail/moveToDelFolder.do",
				data : {
					ids : pkId
				},
				method : 'POST',
				success : function() {
					top['mail'].refresh();
					grid.load();
				}
			});
		}

		/*多封邮件移动到垃圾箱*/
		function todelfolder() {
			var rows = grid.getSelecteds();
			if (rows.length <= 0) {
				alert("请选中一条记录");
				return;
			}
			//行允许删除
			if (rowRemoveAllow && !rowRemoveAllow()) {
				return;
			}

			if (!confirm("确定将选中的邮件已至垃圾箱？（可恢复）"))
				return;

			var ids = [];
			for (var i = 0, l = rows.length; i < l; i++) {
				var r = rows[i];
				ids.push(r.pkId);
			}

			_SubmitJson({
				url : __rootPath + "/oa/mail/outMail/moveToDelFolder.do",
				method : 'POST',
				data : {
					ids : ids.join(',')
				},
				success : function(text) {
					top['mail'].refresh();
					grid.load();
				}
			});
		}


		var moveWindow = mini.get("moveWindow");
		var moveTree = mini.get("moveTree");
		function okWindow() {
			var rows = grid.getSelecteds();
			var targetNode = moveTree.getSelectedNode();
			var ids = [];
			for (var i = 0, l = rows.length; i < l; i++) {
				var r = rows[i];
				ids.push(r.pkId);
			}
			_SubmitJson({
				url : __rootPath + "/oa/mail/outMail/moveMail.do",
				method : 'POST',
				data : {
					ids : ids.join(','),
					to : targetNode.folderId
				},
				success : function(text) {
					top['mail'].refresh();
					grid.load();
				}
			});
			moveWindow.hide();
		}
		function hideWindow() {
			var moveWindow = mini.get("moveWindow");
			moveWindow.hide();
		}

		/*移动邮件按钮点击事件处理*/
		function onMoveNode(e) {
			var rows = grid.getSelecteds();
			if (rows.length <= 0) {
				alert("请选中一条记录");
				return;
			}
			//行允许删除
			if (rowRemoveAllow && !rowRemoveAllow()) {
				return;
			}
			if (configId) {
				moveWindow.show();
			}
		}

		/*设置移动窗口图标*/
		function draw(e) {
			if (e.node.parentId == "0")
				e.iconCls = "icon-folder";
			if (e.node.type == "RECEIVE-FOLDER")
				e.iconCls = "icon-receive";
			if (e.node.type == "SENDER-FOLDER")
				e.iconCls = "icon-sender";
			if (e.node.type == "DRAFT-FOLDER")
				e.iconCls = "icon-draft";
			if (e.node.type == "DEL-FOLDER")
				e.iconCls = "icon-trash";
			if (e.node.type == "OTHER-FOLDER")
				e.iconCls = "icon-folder";
		}

		/*根据条件过滤邮件*/
		function onSearch(e) {
			var sd = mini.get("sDate").getValue();
			var ed = mini.get("eDate").getValue();
			var sDate = new Date(sd);
			var eDate = new Date(ed);
			if (sDate > eDate) {
				alert("结束日期不能小于开始日期");
				return;
			} else {
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

		}
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>

</body>
</html>