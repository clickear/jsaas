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
		<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveUser()">保存</a> 
		<a class="mini-button" iconCls="icon-close" plain="true" onclick="close()">关闭</a>
	</div>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="comId" class="mini-hidden" value="${oaComBook.comId}" /> 
				<input id="catId" name="catId" class="mini-hidden" value="${catId}" /> 
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>通讯录基本信息</caption>

					<tr>
						<th><span class="star">*</span> 通讯录分类：</th>
						<td><input id="comgroupId" name="comgroupId" value="${treeName}" class="mini-textbox" vtype="maxLength:64" readonly="readonly" style="width: 90%" required="true" emptyText="请输入" /></td>
					</tr>

					<%-- 					<tr id="name">
						<th><span class="star">*</span> 姓名：</th>
						<td><input name="name" id="userName" value="${oaComBook.name}" class="mini-textbox" vtype="maxLength:64" style="width: 40%" required="true" emptyText="请输入" /> 
						<a id="addPerson" class="mini-button" style="margin-left: 10px;" iconCls="icon-addMsgPerson" onclick="addPerson()">选择内部用户</a></td>
					</tr> --%>

					<tr>
						<th><span class="star">*</span> 姓名：</th>
						<td><div style="float: left; width: 500px;">
								<textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px;" name="userName" id="userName" width="500px" height="100px"></textarea>
							</div> <a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 15px;" iconCls="icon-addMsgPerson" onclick="addPerson()">新增用户</a><br /> <a class="mini-button" style="width: 100px; margin-left: 14px; margin-top: 5px;" iconCls="icon-cancel" onclick="clearAll()">清空用户</a></td>
					</tr>

				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		mini.parse();
		var catId = "${catId}";

		function addPerson(type,receive) {
			var infUser = mini.get("userName");
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
			});
		}
		
		function close(){
			CloseWindow();
		}
		
		function saveUser(){
			var infUser = mini.get("userName");
			var uIds = infUser.getValue();
			_SubmitJson({
				url: __rootPath + "/oa/company/oaComBook/multiSave.do",
				method: 'POST',
				data:{
					userIds : uIds,
					catId:catId
				},
				success : function(result){
					CloseWindow('ok');
				}
			});
		}
		
		function clearAll() {
			var infUser = mini.get('userName');
			infUser.setValue("");
			infUser.setText("");
		}
	</script>
	<rx:formScript formId="form1" baseUrl="oa/company/oaComBook" entityName="com.redxun.oa.company.entity.OaComBook" />


</body>
</html>