<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建信息</title>
<%@include file="/commons/dynamic.jspf" %>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/common/form.js" type="text/javascript"></script>

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
	padding: 8px;
	border-style: solid;
	font-weight: normal;
	text-align: right;
	font-size: 12px;
	color: #888;
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

	<!--步骤图  -->
	<div style="margin-left: 50px; margin-top: 10px;">
		<a style="margin-left: 10px; line-height: 50px; height: 50px; width: 150px; background-color: #1FC1FF;" class="topButton">1 填写基本内容</a> <a style="margin-left: 10px; line-height: 50px; height: 50px; width: 150px; background-color: #909090;" class="topButton">2 填写内容</a> <a style="margin-left: 10px; line-height: 50px; height: 50px; width: 150px; background-color: #909090;" class="topButton">3 完善属性</a> <a style="margin-left: 10px; line-height: 50px; height: 50px; width: 150px; background-color: #909090;" class="topButton">4 权限及流程</a>
	</div>

	<!--基本信息栏  -->
	<div style="float: right; margin-top: 10px; margin-right: 10%; background: #E9F0F1; height: 140px; width: 300px;">
		<div style="position: relative; padding: 0 20px; height: 35px; line-height: 35px; font-weight: bold; font-size: 16px; font-family: '微软雅黑'; color: #fff; background: rgba(58, 194, 239, 0.91); cursor: pointer; border-bottom: none">基本信息</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			创建者 ：<span style="color: #EA5252;"> ${userName}</span>
		</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">地图状态 ：草稿</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">创建时间 ：<fmt:formatDate value="${nowTime}" type="both"/></div>
	</div>
	<div style="height: 50px;"></div>

	<!-- 填写表单  -->
	<div style="margin-left: 100px;">
		<form id="form1" method="post">
			<input id="docId" name="docId" class="mini-hidden" value="${param['docId']}" />
			<input id="docType" name="docType" class="mini-hidden" value="KD_MAP" />
			<table class="gridtable" cellspacing="1" cellpadding="0">
				<tr>
					<th>所属分类 ：</th>
					<td style="width: 300px"><textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px; width: 150px;" text="${kdDoc.sysTree.name}" value="${kdDoc.treeId}" id="treeId" name="treeId" required="true"></textarea><a class="mini-button" style="margin-left: 10px;" onclick="selectGroup()">选择分类</a></td>
				</tr>

				<tr>
					<th>地图标题 <span class="star"></span> ：
					</th>
					<td><input name="subject" value="${kdDoc.subject}" class="mini-textbox" vtype="maxLength:128" style="width: 90%" required="true" emptyText="请输入文档标题" /></td>
				</tr>
				<tr>
					<th>作者类型 <span class="star"></span> ：
					</th>
					<td><div name="authorType" class="mini-radiobuttonlist" value="${kdDoc.authorType}" repeatItems="1" textField="text" valueField="id" data="[{id:'INNER',text:'内部'},{id:'OUTER',text:'外部'}]" onValueChanged="changAuthorType"></div>
				</tr>
				<tr>
					<th>作者 <span class="star"></span> ：
					</th>
					<td><input name="author" value="${kdDoc.author}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true" emptyText="请输入作者" /></td>
				</tr>
				<tr id="dep">
					<th>所属部门 <span class="star"></span> ：
					</th>
					<td><input id="belongDepid" name="belongDepid" value="${kdDoc.belongDepid}" text="${depName}" allowInput="false" style="width: 250px;" class="mini-buttonedit" onbuttonclick="selectDepartment" /></td>
				</tr>
				<tr id="pos">
					<th>所属岗位 <span class="star"></span> ：
					</th>
					<td><input id="authorPos" name="authorPos" value="${kdDoc.authorPos}" text="${jobName}" style="width: 250px;" allowInput="false" class="mini-buttonedit" onbuttonclick="selectJob" /></td>
				</tr>


			</table>
		</form>
	</div>
	<hr style="margin-top: 30px;">

	<!-- 上下一步 -->
	<div style="margin-top: 20px; margin-left: 40%;">
		<a class="topButton" style="background-color: #909090; height: 40px; width: 90px; line-height: 40px;">上一步</a> <a class="topButton" style="height: 40px; width: 90px; line-height: 40px; text-decoration: none;" onclick="savePost1()">下一步</a>
	</div>
	<script type="text/javascript">	
	var form = new mini.Form("form1");
	var kdDocId = "${param['docId']}";
	var tenantId = "${param['tenantId']}";
		function savePost1(){
			form.validate();
			if (!form.isValid()) {
				return;
			}
			var formData = $("#form1").serializeArray();
			$.ajax({
				   type: "POST",
				   url : __rootPath + '/kms/core/kdDoc/saveNew1.do',
				   async:false,
					data : formData,
					success : function(result) {
						var docId = result;
						window.location.href="${ctxPath}/kms/core/kdDoc/mapNew2.do?docId="+docId;
					}
				});
		}	
	
	
		//部门选择
		function selectDepartment(){
			var infDepartment = mini.get('belongDepid');
			infDepartment.setValue("");
			infDepartment.setText("");
			_GroupSingleDim(true,"1",function(users) {
				var uIds = [];
				var uNames = [];
				uIds.push(users.groupId);
				uNames.push(users.name);
				if (infDepartment.getValue() != '') {
					uIds.unshift(infDepartment.getValue().split(','));
				}
				if (infDepartment.getText() != '') {
					uNames.unshift(infDepartment.getText().split(','));
				}
				infDepartment.setValue(uIds.join(','));
				infDepartment.setText(uNames.join(','));
			});
		}
		
		//岗位选择
		function selectJob(){
			var infJob = mini.get('authorPos');
			infJob.setValue("");
			infJob.setText("");
			_GroupSingleDim(true,"3", function(users) {
				var uIds = [];
				var uNames = [];
				uIds.push(users.groupId);
				uNames.push(users.name);
				if (infJob.getValue() != '') {
					uIds.unshift(infJob.getValue().split(','));
				}
				if (infJob.getText() != '') {
					uNames.unshift(infJob.getText().split(','));
				}
				infJob.setValue(uIds.join(','));
				infJob.setText(uNames.join(','));
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
			var title = '地图分类选择';
			_OpenWindow({
				iconCls : 'icon-group',
				url : __rootPath + '/kms/core/kdDoc/groupDialog.do?single=' + single +'&cat=CAT_KMS_KDMAP'+ '&tenantId=' + tenantId,
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
		
		function changAuthorType(e){
			var authorType = this.getValue();
			if(authorType == "OUTER"){
				$("#dep").hide();
				$("#pos").hide();
			}else{
				$("#dep").show();
				$("#pos").show();
			}
		}
	</script>
</body>
</html>