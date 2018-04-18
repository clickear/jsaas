<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>常用联系人管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;">
		<div title="常用联系人分类" region="west" width="200" showSplitIcon="true" class="layout-border-r">

			<div class="mini-toolbar">
				<table style="width: 100%;" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width: 100%;"><a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshContactType">刷新 </a></td>
					</tr>
				</table>
			</div>
			<div class="mini-fit">
				<ul id="contactType" class="mini-tree" url="${ctxPath}/sys/org/osUser/getContactType.do" style="width: 100%; height: 100%;"
					showTreeIcon="true" textField="contactType" idField="contactType" resultAsTree="false" expandOnLoad="true" onnodeclick="treeNodeClick">
				</ul>
			</div>
		</div>
		<div region="center" showHeader="false" showCollapseButton="false">
			<div class="mini-toolbar">
				<table style="width: 100%;">
					<tr>
						<td style="width: 100%;"><a class="mini-button first" iconCls="icon-create" onclick="addContact()">增加</a>
						</td>
					</tr>
				</table>
			</div>

			<div class="mini-fit" style="height: 100%;">
				<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
					url="${ctxPath}/sys/org/osUser/getContactUserByType.do" idField="id" multiSelect="true" showColumnsMenu="true"
					sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
					<div property="columns">
						<div type="checkcolumn" width="20"></div>
						<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer"
							cellStyle="padding:0;">操作</div>
						<div field="fullname" width="120"  headerAlign="center" allowSort="true">姓名</div>
						<div field="mobile"  width="160" headerAlign="center" allowSort="true">手机</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var contactType = mini.get("contactType");

		var grid = mini.get("datagrid1");
		grid.load();
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;

			var s = '<span class="icon-remove" title="删除联系人" onclick="delContact(\''
					+ pkId + '\')"></span>';

			return s;
		}
		
		function delContact(userId){
			$.ajax({
				url:"${ctxPath}/sys/org/osUser/delContextUser.do",
				type:"post",
				data:{userId:userId},
				success:function(result){
					debugger;
					if(result.success){
						mini.showTips({
				            content: "<b>成功</b> <br/>数据删除成功",
				            state: 'success',
				            x: 'center',
				            y:  'center',
				            timeout: 3000
				        });
						contactType.reload();
						grid.reload();
					}else{
						mini.showTips({
				            content: "<b>失败</b> <br/>数据删除出错",
				            state: 'danger',
				            x: 'center',
				            y:  'center',
				            timeout: 3000
				        });
					}
				}
			});
		}

		function treeNodeClick(e) {
			var node = e.node;
			var contactType = node.contactType;
			grid.setUrl("${ctxPath}/sys/org/osUser/getContactUserByType.do?typeId="+ contactType);
			grid.reload();
		}

		function refreshContactType() {
			contactType.load();
		}
		
		function addContact(){
			_OpenWindow({
				url:__rootPath+'/sys/org/osUser/contactEdit.do?',
				height:450,
				width:1080,
				iconCls:'icon-user-dialog',
				title:'添加联系人',
				ondestroy:function(action){
					if(action=='ok'){
						mini.showTips({
				            content: "<b>成功</b> <br/>添加联系人成功",
				            state: 'success',
				            x: 'center',
				            y:  'center',
				            timeout: 3000
				        });
						contactType.reload();
						grid.reload();
					}
				}
			});
		}
	</script>
</body>
</html>