<%-- 
    Document   : [OaComBook]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>通讯录编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="self-toolbar">
		<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveRight">保存</a> 
		<a class="mini-button" iconCls="icon-close" plain="true" onclick="close">关闭</a>
	</div>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="comId" class="mini-hidden" value="${oaComBook.comId}" /> 
				<input id="catId" name="catId" class="mini-hidden" value="${catId}" /> 
				<input id="groupId" name="groupId" class="mini-hidden" value="${groupId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>通讯录基本信息</caption>

					<tr>
						<th><span class="star">*</span> 通讯录分类：</th>
						<td><input id="comgroupId" name="comgroupId" value="${treeName}" class="mini-textbox" vtype="maxLength:64" readonly="readonly" style="width: 90%" required="true" emptyText="请输入" /></td>
					</tr>

					<tr id="name">
						<th><span class="star">*</span> 姓名：</th>
						<td><input name="name" id="userName" value="${oaComBook.name}" class="mini-textbox" vtype="maxLength:64" style="width: 40%" required="true" emptyText="请输入" /></td>
					</tr>

					<tr id="maindep">
						<th><span class="star">*</span> 主部门：</th>
						<td><input id="userDep" name="maindep" value="${oaComBook.depName}" readonly="readonly" class="mini-textbox" vtype="maxLength:64" style="width: 50%" required="true" emptyText="请输入" /></td>
					</tr>
					<tr>
						<th style="vertical-align: top;">可阅读的</th>
						<td><div style="float: left; width: 500px;">
								<textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px;" text="${readName}" value="${readId}" id="readable" name="readable" width="500px" height="100px"></textarea>
							</div> 
							<a class="mini-button" style="width: 110px; margin-left: 7px; margin-top: 15px;" iconCls="icon-addMsgPerson" onclick="addPerson(readable)">新增用户</a><br /> 
							<a class="mini-button" style="width: 110px; margin-left: 10px; margin-top: 5px;" iconCls="icon-addMsgGroup" onclick="addGroup(readable)">新增用户组</a><br /> 
							<a class="mini-button" style="width: 110px; margin-left: 10px; margin-top: 5px;" iconCls="icon-cancel" onclick="clearAll()">清空用户</a>
							<div style="clear: both; color: #945151;">（如果为空则默认所有人都可以查看此人通讯录）</div></td>
					</tr>

				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		mini.parse();
		var form = mini.get("form1");
		var tree = mini.get("comgroupId");
		var employee = mini.get("isEmployee");
		var catId = mini.get("catId");
		var treeName = "${treeName}";
		var isEmp = "${oaComBook.isEmployee}";

		var remark = mini.get("remark");
		var qq = mini.get("qq");
		var email = mini.get("email");
		var phone = mini.get("phone");
		var mobile = mini.get("mobile");
		var mobile2 = mini.get("mobile2");
		var userName = mini.get("userName");
		var maindep = mini.get("userDep");

		$(function() {
			if (isEmp == "YES") {
				userName.set({
					allowInput : false
				});
			} else if (isEmp == "NO") {
				$("#maindep").hide();
			}
		});
		
		function close(){
			CloseWindow('ok');
		}
		
		function saveRight(){
			form.validate();
			if (!form.isValid()) {
				return;
			}
			var formData = $("#form1").serializeArray();
			console.log(formData);
			 $.ajax({
				type : "POST",
				url : __rootPath + '/oa/company/oaComBook/saveRights.do',
				async : false,
				data : formData,
				success : function(result) {
					CloseWindow('ok');
				}
			}); 
		}
		
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
		
		function clearAll(){
			var infUser = mini.get("readable");
			infUser.setValue();
			infUser.setText();
		}
	</script>
	<rx:formScript formId="form1" baseUrl="oa/company/oaComBook" entityName="com.redxun.oa.company.entity.OaComBook" />


</body>
</html>