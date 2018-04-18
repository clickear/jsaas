<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建信息</title>
<%@include file="/commons/dynamic.jspf" %>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/ueditor-ext.js"></script>

<style>
.topButton {
	display: inline-block;
	line-height: 25px;
	height: 25px;
	width: 70px;
	background-color: #00BFFF;
	color: #fff;
	text-align: center;
	cursor: pointer;
	font-size: 9px;
	vertical-align: middle;
	border: 1px solid #0492C1;
	margin: 8px 5px 0 5px;
}

table.gridtable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	border-collapse: collapse;
}

table.gridtable th {
	border-width: 0px;
	padding: 5px;
	border-style: solid;
	font-weight: normal;
	text-align: right;
	font-size: 12px;
	color: #888;
}

table.gridtable td {
	padding: 5px;
}

hr {
	border: 1px dashed #ccc;
	border-bottom: 0;
	border-right: 0;
	border-left: 0;
}
</style>
</head>
<body>
	<div style="float: right;">
		<a class="topButton">暂存</a>
	</div>
	<div style="clear: both;"></div>
	<hr style="height: 4px; border: none; border-top: 2px groove #CFCFCF;" />
	<div style="font-size: 10px; margin-left: 70px;">首页 &gt; 知识地图 &gt;</div>
	<div style="margin-left: 50px; margin-top: 10px;">
		<a style="margin-left: 10px; line-height: 50px; height: 50px; width: 150px; background-color: #39C150;" class="topButton">1 填写基本内容</a> <a style="margin-left: 10px; line-height: 50px; height: 50px; width: 150px; background-color: #39C150;" class="topButton">2 填写内容</a> <a style="margin-left: 10px; line-height: 50px; height: 50px; width: 150px; background-color: #39C150;" class="topButton">3 完善属性</a> <a style="margin-left: 10px; line-height: 50px; height: 50px; width: 150px; background-color: #1FC1FF;" class="topButton">4 权限及流程</a>
	</div>

	<div style="float: right; margin-top: 10px; margin-right: 10%; background: #E9F0F1; height: 140px; width: 300px;">
		<div style="position: relative; padding: 0 20px; height: 35px; line-height: 35px; font-weight: bold; font-size: 16px; font-family: '微软雅黑'; color: #fff; background: rgba(58, 194, 239, 0.91); cursor: pointer; border-bottom: none">基本信息</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			创建者 ：<span style="color: #EA5252;">${userName}</span>
		</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">地图状态 ：草稿</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			创建时间 ：
			<fmt:formatDate value="${kdDoc.createTime}" type="both" />
		</div>
	</div>
	<div style="height: 50px;"></div>

	<div style="margin-left: 60px; font-size: 18px; color: #0087BB">权限</div>
	<hr style="margin-left: 50px; margin-right: 30%; border: none; border-top: 1px groove #FFFFFF;" />
	<div style="margin-left: 60px;">
		<form id="form1" method="post">
			<input id="docId" name="docId" class="mini-hidden" value="${param['docId']}" />
			<table class="gridtable" cellspacing="1" cellpadding="0">
				<tr>
					<th style="vertical-align: top;">可阅读的</th>
					<td><div style="float: left; width: 500px;">
							<textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px;" id="readable" name="readable" width="500px" height="100px"></textarea>
						</div> <input id="readPerson" name="readPerson" class="mini-hidden" value="" /> <input id="readGroup" name="readGroup" class="mini-hidden" value="" /> <a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 15px;" iconCls="icon-addMsgPerson" onclick="addPerson(readable,readPerson)">新增用户</a><br /> <a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 5px;" iconCls="icon-addMsgGroup" onclick="addGroup(readable,readGroup)">新增用户组</a><br /> <a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 5px;" iconCls="icon-cancel" onclick="clearAll()">清空用户</a>
						<div style="clear: both; color: #945151;">（如果为空则默认所有人都可以阅读）</div></td>
				</tr>
				<tr>
					<th style="vertical-align: top;">可编辑的</th>
					<td><div style="float: left; width: 500px;">
							<textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px;" id="editable" name="editable" width="500px" height="100px"></textarea>
						</div> <input id="editPerson" name="editPerson" class="mini-hidden" value="" /> <input id="editGroup" name="editGroup" class="mini-hidden" value="" /> <a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 15px;" iconCls="icon-addMsgPerson" onclick="addPerson(editable,editPerson)">新增用户</a><br /> <a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 5px;" iconCls="icon-addMsgGroup" onclick="addGroup(editable,editGroup)">新增用户组</a><br /> <a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 5px;" iconCls="icon-cancel" onclick="clearAll()">清空用户</a>
						<div style="clear: both; color: #945151;">（如果为空则默认管理员都可以编辑）</div></td>
				</tr>
				<tr>
					<th style="vertical-align: top;">附件权限</th>
					<td><div style="float: left; width: 500px;">
							<textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px;" id="downloadable" name="downloadable" width="500px" height="100px"></textarea>
						</div> <input id="downloadPerson" name="downloadPerson" class="mini-hidden" value="" /> <input id="downloadGroup" name="downloadGroup" class="mini-hidden" value="" /> <a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 15px;" iconCls="icon-addMsgPerson" onclick="addPerson(downloadable,downloadPerson)">新增用户</a><br /> <a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 5px;" iconCls="icon-addMsgGroup" onclick="addGroup(downloadable,downloadGroup)">新增用户组</a><br /> <a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 5px;" iconCls="icon-cancel" onclick="clearAll()">清空用户</a>
						<div style="clear: both; color: #945151;">
							（如果为空则默认所有人都可以下载）
							<div id="ck1" name="product" class="mini-checkbox" readOnly="false" text="勾选则所有人不可以下载附件"></div>
						</div></td>

				</tr>
			</table>
		</form>
	</div>
	<hr style="margin-top: 30px;">
	<div style="margin-top: 20px; margin-left: 40%;">
		<a class="topButton" style="height: 40px; width: 90px; line-height: 40px; text-decoration: none;" href="${ctxPath}/kms/core/kdDoc/mapNew3.do?docId=${param['docId']}">上一步</a> <a class="topButton" style="height: 40px; width: 90px; line-height: 40px;" onclick="savePost4()">完成提交</a>
	</div>
	<script type="text/javascript">
	var form = new mini.Form("form1");
	var kdDocId = "${param['docId']}";
		function addPerson(type,receive) {
			var infUser = mini.get(type);
			var readPerson = mini.get(receive);
			//infUser.setValue("");
			//infUser.setText("");
			_UserDlg(false, function(users) {//打开收信人选择页面,返回时获得选择的user的Id和name
				var uIds = [];
				var uNames = [];
				//将返回的选择分别存起来并显示
				for (var i = 0; i < users.length; i++) {
					var flag = true;
					var oldValues = infUser.getValue().split(',');
					var oldText = infUser.getText().split(',');
					for(var j=0;j<oldValues.length;j++){
						if(oldValues[j]==users[i].userId&&oldText[j]==users[i].fullname){
							flag = false;
							break;
						}
					}
					if(flag==true){
					uIds.push(users[i].userId);
					uNames.push(users[i].fullname);
					}
				}
				if (infUser.getValue() != '') {
					uIds.unshift(infUser.getValue().split(','));
				}
				if (infUser.getText() != '') {
					uNames.unshift(infUser.getText().split(','));
				}
				infUser.setValue(uIds.join(','));
				infUser.setText(uNames.join(','));
				readPerson.setValue(uIds.join(','));
			});
		}
		function addGroup(type,receive) {
			var infGroup = mini.get(type);
			var readGroup = mini.get(receive);
			_GroupDlg(false, function(users) {
				var uIds = [];
				var uNames = [];
				for (var i = 0; i < users.length; i++) {
					var flag = true;
					var oldValues = infGroup.getValue().split(',');
					var oldText = infGroup.getText().split(',');
					for(var j=0;j<oldValues.length;j++){
						if(oldValues[j]==users[i].groupId&&oldText[j]==users[i].name){
							flag = false;
							break;
						}
					}
					if(flag==true){
					uIds.push(users[i].groupId);
					uNames.push(users[i].name);
					}
				}
				if (infGroup.getValue() != '') {
					uIds.unshift(infGroup.getValue().split(','));
				}
				if (infGroup.getText() != '') {
					uNames.unshift(infGroup.getText().split(','));
				}
				infGroup.setValue(uIds.join(','));
				infGroup.setText(uNames.join(','));
				readGroup.setValue(uIds.join(','));
			});
		}
		function savePost4() {
			form.validate();
			if (!form.isValid()) {
				return;
			}
			var formData = $("#form1").serializeArray();
			console.log(formData);
			 $.ajax({
				type : "POST",
				url : __rootPath + '/kms/core/kdDoc/saveNew4.do',
				async : false,
				data : formData,
				success : function(result) {
					var docId = result;
					window.location.href = "${ctxPath}/kms/core/kdDoc/mapShow.do?docId=" + docId;
					//window.location.href = "${ctxPath}/kms/core/kdDoc/new4.do?docId=" + docId;
				}
			}); 
		}
	</script>
</body>
</html>