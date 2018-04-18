<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识发布</title>
<%@include file="/commons/dynamic.jspf" %>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="${ctxPath}/scripts/kms/demo/css/application.css">
<script type="text/javascript" src="${ctxPath}/scripts/kms/demo/lib/jquery.raty.min.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/kms/demo/lib/jquery.raty.js"></script>

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
		<a class="topButton" onclick="submitRem()">发布</a> <a class="topButton" style="background-color: #fff; color: #00BFFF;">关闭</a>
	</div>
	<div style="clear: both;"></div>
	<hr style="height: 4px; border: none; border-top: 2px groove #CFCFCF;" />

	<!--  -->
	<div style="margin-left: 100px;">
		<form id="form1" method="post">
			<table class="gridtable" cellspacing="1" cellpadding="0">
				<input id="docId" name="docId" class="mini-hidden" value="${param['docId']}" />
				<tr style="margin-top:5px;">
					<th>发布分类 ：</th>
					<td style="width: 300px"><textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px; width: 150px;" id="treeId" name="treeId"></textarea><a class="mini-button" style="margin-left: 10px;" onclick="selectGroup()">选择分类</a></td>
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
		var level = $('#target-div-hint').html();
		var treeId = mini.get('treeId').getValue();
		if(treeId == ""){
			mini.showTips({
				content : "请选择推荐分类。",
				state : "danger",
				x : "center",
				y : "center",
				timeout : 3000
			});
			return;
		}
		_SubmitJson({
			url : "${ctxPath}/kms/core/kdRem/sumbitRem.do",
			data : {
				docId : docId,
				treeId :treeId,
				reason : reason,
				score : score,
				level : level,
			},
			method : 'POST',
			success : function() {
			}
		}); 
	}
		//分类选择
		function selectGroup() {
			var infGroup = mini.get('treeId');
			infGroup.setValue("");
			infGroup.setText("");
			_TypeDlg(true, function(users) {
				var uIds = [];
				var uNames = [];
				uIds.push(users.treeId);
				uNames.push(users.name);
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
		function _TypeDlg(single, callback, reDim) {
			if (!reDim) {
				reDim = false;
			}
			_TenantTypeDlg('', single, '', callback, reDim);
		}
		function _TenantTypeDlg(tenantId, single, showDimId, callback, reDim) {
			var title = '知识分类选择';
			_OpenWindow({
				iconCls : 'icon-group',
				url : __rootPath + '/kms/core/kdDoc/groupDialog.do?single=' + single + '&tenantId=' + tenantId,
				height : 480,
				width : 680,
				title : title,
				ondestroy : function(action) {
					if (action != 'ok')
						return;
					var iframe = this.getIFrameEl();
					var groups = iframe.contentWindow.getGroups();
					var dim = {};
					//需要返回dim
					if (reDim) {
						var dimNode = iframe.contentWindow.getSelectedDim();
						dim = {
							dimId : dimNode.dimId,
							dimKey : dimNode.dimKey,
							name : dimNode.name
						};
					}
					if (callback) {
						if (single && groups.length > 0) {
							callback.call(this, groups[0], dim);
						} else {
							callback.call(this, groups, dim);
						}
					}
				}
			});
		}
	</script>
</body>
</html>