<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>新建信息</title>
<%@include file="/commons/edit.jsp"%>
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/ueditor-ext.js"></script>

<style>
.topButton {
	display: inline-block;
	line-height: 25px;
	height: 25px;
	padding: 0 10px; 
	background-color: #00BFFF;
	color: #fff;
	text-align: center;
	cursor: pointer;
	font-size: 14px;
	margin: 8px 5px 0 5px;
	border-radius: 4px;
}

table.gridtable{
	font-size: 14px;
	border-collapse: collapse;
}

table.gridtable th {
	border-width: 0px;
	padding: 8px;
	font-weight: normal;
	font-size: 14px;
	color: #666;
}

.stepBox{
	padding-left: 70px;
	box-sizing: border-box;
}

.stepBox span{
	float: left;
/* 	width: 60px; */
	padding: 10px;
	border-radius: 4px;
	background: #00BFFF;
	color: #fff;
	margin-right: 8px;
	opacity: .5;
	font-size: 14px;
}

.stepBox .active{
	opacity: 1;
}

.stepBox .complete{
	background: #39C150;
	opacity: 1;
}

.contentBox{
	margin-top: 40px;
	border-bottom: 1px dashed #ccc;
}

.contentBox::after,
.stepBox::after{
	content: '';
	display: block;
	clear: both;
}

.shadowBox{
	float: left;
}
</style>
</head>
<body>
	<div style="float: right;">
		<a class="topButton" onclick="temporary()">暂存</a>
	</div>
	<div style="clear: both;"></div>
<!-- 	<hr style="height: 4px; border: none; border-top: 2px groove #CFCFCF;" /> -->
	<div style="font-size: 10px; margin-left: 70px;">首页 > 知识仓库 ></div>
	<div class="stepBox">
		<span class="complete">1 填写基本内容</span> 
		<span class="complete">2 填写内容</span>
		<span class="complete">3 完善属性</span>
		<span class="active">4权限及流程</span>
	</div>
<div class="contentBox">
	<div style="float: right; margin-top: 10px; margin-right: 10%; background: #E9F0F1; height: 140px; width: 300px;">
		<div style="position: relative; padding: 0 20px; height: 35px; line-height: 35px; font-weight: bold; font-size: 16px; font-family: '微软雅黑'; color: #fff; background: rgba(58, 194, 239, 0.91); cursor: pointer; border-bottom: none">基本信息</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			创建者 ：<span style="color: #EA5252;">${userName}</span>
		</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">文档状态 ：草稿</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">创建时间 ： <fmt:formatDate value="${kdDoc.createTime}" type="both"/></div>
	</div>
<!-- 	<div style="height: 50px;"></div> -->

	<div style="margin-left: 60px; font-size: 18px; color: #0087BB">权限</div>
	<hr style="margin-left: 50px; margin-right: 30%; border: none; border-top: 1px groove #FFFFFF;" />
	<div style="margin-left: 60px;">
		<form id="form1" method="post">
		<input id="docId" name="docId" class="mini-hidden" value="${param['docId']}" />
		<div class="shadowBox90">
			<table style="width: 100%;" class="gridtable" cellspacing="1" cellpadding="0">
				<tr>
					<th style="vertical-align: top;">可阅读的</th>
					<td><div style="float: left; width: 500px;">
							<textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px;" id="readable" name="readable" width="500px" height="100px"></textarea>
						</div> 
						<a class="mini-button" style="width: 110px; margin-left: 10px; margin-top: 15px;" iconCls="icon-addMsgPerson" onclick="addPerson(readable)">新增用户</a>
						<a class="mini-button" style="width: 110px; margin-left: 10px; margin-top: 5px;" iconCls="icon-addMsgGroup" onclick="addGroup(readable)">新增用户组</a>
						<a class="mini-button" style="width: 110px; margin-left: 10px; margin-top: 5px;" iconCls="icon-cancel" onclick="clearAll()">清空用户</a>
						<div style="clear:both;color:#945151;">（如果为空则默认所有人都可以阅读）</div>
					</td>
				</tr>
				<tr>
					<th style="vertical-align: top;">可编辑的</th>
					<td><div style="float: left; width: 500px;">
							<textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px;" id="editable" name="editable" width="500px" height="100px"></textarea>
						</div> 

						<a class="mini-button" style="width: 110px; margin-left: 10px; margin-top: 15px;" iconCls="icon-addMsgPerson" onclick="addPerson(editable)">新增用户</a>
						<a class="mini-button" style="width: 110px; margin-left: 10px; margin-top: 5px;" iconCls="icon-addMsgGroup" onclick="addGroup(editable)">新增用户组</a> 
						<a class="mini-button" style="width: 110px; margin-left: 10px; margin-top: 5px;" iconCls="icon-cancel" onclick="clearAll()">清空用户</a>
						<div style="clear:both;color:#945151;">（如果为空则默认管理员都可以编辑）</div></td>
				</tr>
				<tr>
					<th style="vertical-align: top;">附件权限</th>
					<td><div style="float: left; width: 500px;">
							<textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px;" id="downloadable" name="downloadable" width="500px" height="100px"></textarea>
						</div> 
						<a class="mini-button" style="width: 110px; margin-left: 10px; margin-top: 15px;" iconCls="icon-addMsgPerson" onclick="addPerson(downloadable)">新增用户</a> 
						<a class="mini-button" style="width: 110px; margin-left: 10px; margin-top: 5px;" iconCls="icon-addMsgGroup" onclick="addGroup(downloadable)">新增用户组</a> 
						<a class="mini-button" style="width: 110px; margin-left: 10px; margin-top: 5px;" iconCls="icon-cancel" onclick="clearAll()">清空用户</a>
						<div style="clear:both;color:#945151;">（如果为空则默认所有人都可以下载）<div id="ck1" name="product" class="mini-checkbox" readOnly="false" text="勾选则所有人不可以下载附件" ></div></div></td>
						
				</tr>
			</table>
		</div>
		</form>
	</div>
	</div>
<!-- 	<hr style="margin-top: 30px;"> -->
	<div style="margin-top: 20px; margin-left: 40%;">
		<a class="topButton" style="height: 40px; width: 90px; line-height: 40px; text-decoration: none;" href="${ctxPath}/kms/core/kdDoc/new3.do?docId=${param['docId']}">上一步</a> 
		<a class="topButton" style="height: 40px; width: 90px; line-height: 40px;" onclick="savePost4()">完成提交</a>
	</div>
	<script type="text/javascript">
	var form = new mini.Form("form1");
	var kdDocId = "${param['docId']}";
	
	
		//增加个人权限
		function addPerson(type) {
			var infUser = mini.get(type);
			_UserDlg(false, function(users) {//打开收信人选择页面,返回时获得选择的user的Id和name
				var uIds = [];
				var uNames = [];
				//将返回的选择分别存起来并显示
				for (var i = 0; i < users.length; i++) {
					var flag = true;
					users[i].userId = users[i].userId + "_user";
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
			});
		}
		//增加组权限
		function addGroup(type) {
			var infGroup = mini.get(type);
			_GroupDlg(false, function(groups) {
				var uIds = [];
				var uNames = [];
				for (var i = 0; i < groups.length; i++) {
					var flag = true;
					groups[i].groupId = groups[i].groupId + "_group";
					var oldValues = infGroup.getValue().split(',');
					var oldText = infGroup.getText().split(',');
					for(var j=0;j<oldValues.length;j++){
						if(oldValues[j]==groups[i].groupId&&oldText[j]==groups[i].name){
							flag = false;
							break;
						}
					}
					if(flag==true){
					uIds.push(groups[i].groupId);
					uNames.push(groups[i].name);
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
			});
		}
		function savePost4() {
			form.validate();
			if (!form.isValid()) {
				return;
			}
			var formData = $("#form1").serializeArray();
			 $.ajax({
				type : "POST",
				url : __rootPath + '/kms/core/kdDoc/saveNew4.do',
				async : false,
				data : formData,
				success : function(result) {
					var docId = result;
					window.location.href = "${ctxPath}/kms/core/kdDoc/show.do?docId=" + docId;
					//window.location.href = "${ctxPath}/kms/core/kdDoc/new4.do?docId=" + docId;
				}
			}); 
		}
		
		function temporary(){
			form.validate();
			if (!form.isValid()) {
				return;
			}
			var formData = $("#form1").serializeArray();
			 $.ajax({
				type : "POST",
				url : __rootPath + '/kms/core/kdDoc/saveNew4.do',
				async : false,
				data : formData,
				success : function(result) {
					alert("知识文档已暂存，可以在个人中心中继续编辑！");
					CloseWindow();
				}
			}); 
		}
		
	</script>
</body>
</html>