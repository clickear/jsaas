<%--
	//用户组的资源授权
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 使用范围：
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>发送目标菜单</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div id="mainLayout" class="mini-layout" style="width: 100%; height: 100%;">
		<div region="south" showSplit="false" showHeader="false" height="40" showSplitIcon="false" style="width: 100%">
			<div class="mini-toolbar dialog-footer" style="border: none;">
				<a class="mini-button" iconCls="icon-ok" onclick="onOk()">确定</a> 
				<a class="mini-button" iconCls="icon-cancel" onclick="onCancel()">取消</a>
			</div>
		</div>
		<div region="center" showSplit="false" showHeader="false" showSplitIcon="false" style="width: 200px;">
			<form id="form1" method="post">
				<table class="gridtable" style="border-spacing:10px;margin-top:10px;" cellspacing="1" cellpadding="0" >
					<tr >
						<th >报表菜单名字 ：</th>
						<td style="width: 300px;"><input name="name" class="mini-textbox" vtype="maxLength:128" style="width: 90%" required="true" emptyText="请输入报表菜单名称" /></td>
					</tr>
					<tr >
						<th>报表菜单Key <span class="star"></span> ：
						</th>
						<td><input name="key" class="mini-textbox" vtype="maxLength:128" style="width: 90%" value="${sysReport.key}" required="true" readonly="readonly" /></td>
					</tr>
					<tr >
						<th>报表菜单图标 <span class="star"></span> ：
						</th>
						<td><input property="editor"  name="img" id="img" required="true" class="mini-buttonedit" onbuttonclick="selectIcon"/></td>
					</tr>


				</table>
			</form>
		</div>
		<div region="west" title="菜单列表" showHeader="false" showCollapseButton="false" minWidth="250px">
			<div class="mini-toolbar" bodyStyle="border:0" style="border: none; border-bottom: solid 1px #aaa">
				<a class="mini-button" iconCls="icon-expand" plain="true" onclick="onExpand()">展开</a> <a class="mini-button" iconCls="icon-collapse" plain="true" onclick="onCollapse()">收起</a>
			</div>
			<div class="mini-fit" bodyStyle="padding:2px;">
				<ul id="menuTree" class="mini-tree" url="${ctxPath}/sys/org/osGroup/grantResource.do?tenantId=${param['tenantId']}" style="width: 100%; padding: 5px;" showTreeIcon="true" textField="name" idField="menuId" parentField="parentId" resultAsTree="false" checkRecursive="false" imgPath="${ctxPath}/upload/icons/" expandOnLoad="true" allowSelect="true" enableHotTrack="false">
				</ul>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var tree = mini.get('menuTree');
		var form = new mini.Form("form1");
		var key = "${sysReport.key}";

		function onOk() {
			var menuIds = tree.getValue(true);
			var sysIds = [];
			var node = tree.getSelectedNode();
			if (node == null) {
				mini.showTips({
					content : "您尚未选择菜单！",
					state : "danger",
					x : "center",
					y : "center",
					timeout : 2000
				});
				return;
			}
			
			form.validate();
			if (!form.isValid()) {
				return;
			}

			var formData = form.getData();
			formData.parentId = node.menuId;
			if(node.sysId == null){
				formData.sysId = node.menuId;
			}else{
				formData.sysId = node.sysId;
			}			
			console.log(node);
			formData.url = "/sys/core/sysReport/preview.do?key="+key;
			console.log(formData);
			/* formData.push({
				name:"parentId",
				value:node.menuId,
			});
			formData.push({
				name:"sysId",
				value:node.sysId,
			});
			formData.push({
				name:"url",
				value:node.url,
			}); */
			var json = mini.encode(formData);
			console.log(json);
			$.ajax({
                url: "${ctxPath}/sys/core/sysMenuMgr/saveMenu.do",
                type:"POST",
                data: { data: json },
                success: function (text) {
                    var result=mini.decode(text);
                    //进行提示
                    alert("成功发布报表到菜单目录下！");
                    CloseWindow();
                	/* if(result.data && result.data.menuId){
                		row.menuId=result.data.menuId;
                	}
                	grid.acceptRecord(row); */
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
		}

		function onCancel() {
			CloseWindow('cancel');
		}

		function onExpand() {
			tree.expandAll();
		}

		function onCollapse() {
			tree.collapseAll();
		}

		//选择图标
		function selectIcon(e) {
			var icon = mini.get("img");
			_OpenWindow({
				url : "${ctxPath}/sys/core/sysFile/appIcons.do",
				title : "系统图片",
				width : 620,
				height : 450,
				ondestroy : function(action) {
					if (action == 'ok') {
						var iframe = this.getIFrameEl();
						var imgs = iframe.contentWindow.getIcons();
						if (imgs.length == 0)
							return;
						icon.setValue(imgs[0].fileName);
						icon.setText(imgs[0].fileName);
					}
				}
			});
		}
	</script>
</body>
</html>