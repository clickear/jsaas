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
	<rx:toolbar toolbarId="toolbar1" pkId="${oaComBook.comId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="comId" class="mini-hidden" value="${oaComBook.comId}" />
				<input id="catId" name="catId" class="mini-hidden" value="${catId}" />
				<input id="groupId" name="groupId" class="mini-hidden"  />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>通讯录基本信息</caption>

					<tr>
						<th><span class="star">*</span> 通讯录分类：</th>
						<td><input id="comgroupId" name="comgroupId" value="${treeName}" class="mini-textbox" vtype="maxLength:64" readonly="readonly" style="width: 90%" required="true" emptyText="请输入" /></td>
					</tr>
					<tr>
						<th><span class="star">*</span>是否内部员工 ：</th>
						<td><ui:radioBoolean id="isEmployee" name="isEmployee" value="${oaComBook.isEmployee}" required="true" onValueChanged="changeEmp()" /></td>
					</tr>

					<tr id="name">
						<th><span class="star">*</span> 姓名：</th>
						<td><input name="name" id="userName" value="${oaComBook.name}" class="mini-textbox" vtype="maxLength:64" style="width: 40%" required="true" emptyText="请输入" /> 
						<a id="addPerson" class="mini-button" style="margin-left: 10px;" iconCls="icon-addMsgPerson" onclick="addPerson()">选择内部用户</a></td>
					</tr>

					<tr id="maindep">
						<th><span class="star">*</span> 主部门：</th>
						<td><input id="userDep" name="maindep" value="${oaComBook.maindep}" readonly="readonly" class="mini-textbox" vtype="maxLength:64" style="width: 50%" required="true" emptyText="请输入" /></td>
					</tr>

					<tr id="mobile">
						<th>主手机号：</th>
						<td><input id="userMobile"  name="mobile" value="${oaComBook.mobile}" class="mini-textbox" vtype="maxLength:64" style="width: 90%"  required="true"/></td>
					</tr>

					<tr id="mobile2">
						<th>副手机号：</th>
						<td><input id="userMobile2" name="mobile2" value="${oaComBook.mobile2}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr id="phone">
						<th>固话：</th>
						<td><input id="userPhone" name="phone" value="${oaComBook.phone}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr id="email">
						<th>邮件：</th>
						<td><input id="userEmail" name="email" value="${oaComBook.email}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr id="qq">
						<th>QQ：</th>
						<td><input id="userQQ" name="qq" value="${oaComBook.qq}" class="mini-textbox" vtype="maxLength:32" style="width: 90%" /></td>
					</tr>

					<tr id="remark">
						<th>备注：</th>
						<td><input name="remark" value="${oaComBook.remark}" class="mini-textarea" vtype="maxLength:500" style="width: 90%" /></td>
					</tr>

					<tr id="editmore">
						<td colspan="2"><a class="mini-button" style="margin-left: 10px;" iconCls="icon-addMsgPerson" onclick="editMore()">填写更多</a></td>
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
		
 		var remark = mini.get("remark");
		var qq = mini.get("qq");
		var email = mini.get("email");
		var phone = mini.get("phone");
		var mobile = mini.get("mobile");
		var mobile2 = mini.get("mobile2");
		var userName = mini.get("userName");
		var maindep = mini.get("userDep");
		
		$(function() {
			$("#remark").hide();
			$("#qq").hide();
			$("#email").hide();
			$("#phone").hide();
			$("#mobile").hide();
			$("#mobile2").hide();
			$("#name").hide();
			$("#maindep").hide();
			$("#editmore").hide();
		});
		
		function changeEmp() {
			var isEmployee = mini.get("isEmployee").getValue();
			if (isEmployee == "YES") {
				$("#name").show();
				$("#maindep").show();
				$("#editmore").show();
				$("#remark").show();
				$("#addPerson").show();
				$("#qq").hide();
				$("#email").hide();
				$("#phone").hide();
				$("#mobile").show();
				$("#mobile2").hide();
				userName.set({
					allowInput:false
				});
				form.clear();
				tree.setValue(treeName);
				catId.setValue("${catId}");
				employee.setValue("YES");
			} else if (isEmployee == "NO") {
				$("#name").show();
				$("#maindep").hide();
				$("#editmore").show();
				$("#remark").show();
				$("#addPerson").hide();
				$("#qq").hide();
				$("#email").hide();
				$("#phone").hide();
				$("#mobile").show();
				$("#mobile2").hide();
				userName.set({
					allowInput:true
				});
				form.clear();
				tree.setValue(treeName);
				employee.setValue("NO");
				catId.setValue("${catId}");
				maindep.setValue("外部通讯录");
			}
		}
		
		function addPerson(){
			var userName=mini.get('userName');
			var userDep=mini.get('userDep');
			var userQQ=mini.get('userQQ');
			var userMobile=mini.get('userMobile');
			var userEmail=mini.get('userEmail');
			var groupId = mini.get('groupId');
			_UserDlg(true,function(user){
				//userName.setValue(user.userId);
				//userName.setText(user.fullname);
				userName.setValue(user.fullname);
				_SubmitJson({
					url : __rootPath + '/oa/company/oaComBook/getMainDep.do',
					method : 'POST',
					data : {
						userId : user.userId
					},
					showMsg:false,
					success : function(result) {
						console.log(result.data);
						groupId.setValue(result.data.depId);
						userDep.setValue(result.data.mainDep);
						userQQ.setValue(result.data.user.qq);
						userMobile.setValue(result.data.user.mobile);
						userEmail.setValue(result.data.user.email);						
						return;
					}
				});
			});
		}
		
		function editMore(){
			$("#editmore").hide();
			$("#qq").show();
			$("#email").show();
			$("#phone").show();
			$("#mobile").show();
			$("#mobile2").show();
		}
	</script>
	<rx:formScript formId="form1" baseUrl="oa/company/oaComBook" entityName="com.redxun.oa.company.entity.OaComBook" />


</body>
</html>