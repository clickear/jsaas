<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>知识推荐</title>
<%@include file="/commons/list.jsp"%>
<link type="text/css" rel="stylesheet" href="${ctxPath}/scripts/kms/starDemo/css/application.css">
<script type="text/javascript" src="${ctxPath}/scripts/kms/starDemo/lib/jquery.raty.min.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/kms/starDemo/lib/jquery.raty.js"></script>

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
	margin-right: 5px;
	font-weight: 600;
}
</style>
</head>
<body>
	<!-- 上方工具栏 -->
	<div style="float: right; margin-top: 5px; margin-right: 5px;">
		<a class="topButton" onclick="submitRem()">提交</a> <a class="topButton" style="background-color: #fff; color: #00BFFF;" onclick="closeWin()">关闭</a>
	</div>
	<div style="clear: both;"></div>
	<hr style="height: 4px; border: none; border-top: 2px groove #CFCFCF;" />

	<!--  -->
	<div style="margin-left: 100px;">
		<form id="form1" method="post">
			<table class="gridtable" cellspacing="1" cellpadding="0">
				<input id="docId" name="docId" class="mini-hidden" value="${param['docId']}" />
				<tr style="margin-top: 5px;">
					<th>推荐对象 ：</th>
					<td style="width: 400px"><textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px; margin-left: 10px;" id="rem" name="rem" width="300px"></textarea>
				</tr>
				<tr>
					<th></th>
					<td><a class="mini-button" style="width: 110px; margin-left: 10px; margin-top: 5px;" iconCls="icon-addMsgPerson" onclick="addPerson(rem)">新增用户</a> 
					<a class="mini-button" style="width: 110px; margin-left: 10px; margin-top: 5px;" iconCls="icon-addMsgGroup" onclick="addGroup(rem)">新增用户组</a> 
					<a class="mini-button" style="width: 110px; margin-left: 10px; margin-top: 5px;" iconCls="icon-cancel" onclick="clearAll()">清空用户</a></td>
					</td>
				</tr>
				<tr style="margin-top: 15px;">
					<th>推荐等级 ：</th>
					<td style="width: 300px"><span class="demo">
							<div id="target-div-demo" class="target-demo"></div>
							<div id="target-div-hint" class="hint"></div>
					</span></td>
				</tr>
				<tr style="margin-top: 15px;">
					<th>推荐理由 ：</th>
					<td style="width: 300px"><textarea id="reason" name="reason" cols=50 rows=4>这篇章写得不错！</textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		var docId = "${param['docId']}";

		//推荐提交
		function submitRem() {
			var reason = $("#reason").val();
			var score = $('#target-div-demo').raty('score');
			var userId = mini.get("rem").getValue();
			//var isNotice = mini.get("notice");
			if (userId == "") {
				mini.showTips({
					content : "请选择推荐对象。",
					state : "danger",
					x : "center",
					y : "center",
					timeout : 3000
				});
				return;
			}
			if (score == null) {
				mini.showTips({
					content : "尚未评分，请评分。",
					state : "danger",
					x : "center",
					y : "center",
					timeout : 3000
				});
				return;
			} 
			_SubmitJson({
				url : "${ctxPath}/kms/core/kdDocRem/submitRem.do",
				data : {
					docId : docId,
					userId : userId,
					score : score,
					content : reason,
				},
				method : 'POST',
				showMsg : false,
				success : function(result) {
					alert(result.message);
					CloseWindow();
				}
			});
		}
		//打星
		$(function() {
			$.fn.raty.defaults.path = '${ctxPath}/scripts/kms/starDemo/lib/img';
			$('#target-div-demo').raty({
				target : '#target-div-hint',
				targetKeep : true
			});
		});
		

		//推荐的添加用户
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
		//推荐中的添加组
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
		
		function clearAll() {
			var infUser = mini.get('rem');
			infUser.setValue("");
			infUser.setText("");
		}

		function closeWin() {
			CloseWindow();
		}
	</script>
</body>
</html>