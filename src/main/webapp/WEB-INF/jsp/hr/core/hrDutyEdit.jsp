<%-- 
    Document   : [HrDuty]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDuty]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${hrDuty.dutyId}" />
	<div id="p1" class="form-outer shadowBox">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="dutyId" class="mini-hidden" value="${hrDuty.dutyId}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>排班基本信息</caption>
					
					<tr>
						<th>排班名称 </th>
						<td><input name="dutyName" value="${hrDuty.dutyName}" class="mini-textbox" style="width: 90%" required="true" /></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								班制编号<span class="star">*</span> 
							</span>
						</th>
						<td><input id="systemId" name="systemId" value="${hrDuty.hrDutySystem.systemId}" text="${hrDuty.hrDutySystem.name}" class="mini-buttonedit mini-dian" style="width: 90%" required="true" onbuttonclick="onButtonEdit" allowInput="false" /></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								开始时间 <span class="star">*</span> 
							</span>
						</th>
						<td><input name="startTime" value="${hrDuty.startTime}" class="mini-datepicker" style="width: 90%" required="true" emptyText="请输入开始时间" allowInput="false" /></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								结束时间 <span class="star">*</span>
							</span> 
						</th>
						<td><input name="endTime" value="${hrDuty.endTime}" class="mini-datepicker"  style="width: 90%" required="true" emptyText="请输入结束时间" allowInput="false" /></td>
					</tr>
					
					<tr>
						<th>人员类型</th>
						<td><input id="userType" name="userType" value="user" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="horizontal" style="width: 90%" required="true" data="[{'id':'user','text':'用户'},{'id':'department','text':'部门'}]" textField="text" valueField="id" onValuechanged="typeChange(e)" /></td>
					</tr>

					<tr id="user">
						<th>用　　户 </th>
						<td><input id="userId" name="userId" value="${hrDuty.userId}" text="${hrDuty.userName}" class="mini-textboxlist" value="" text=""  valueField="id" textField="text" style="width: 85%" allowInput="false"  /><a class="mini-button" iconCls="icon-add" plain="true" onclick="addPerson"></a></td>
					</tr>

					<tr id="group" style="display:none;">
						<th>部　　门 </th>
						<td><input id="groupId" name="groupId" value="${hrDuty.groupId}" text="${hrDuty.groupName}" class="mini-textboxlist" value="" text=""  valueField="id" textField="text" style="width: 85%" allowInput="false"  /><a class="mini-button" iconCls="icon-add" plain="true" onclick="addDep"></a>
						
						<%-- <input id="groupId" name="groupId" value="${hrDuty.groupId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /> --%></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="hr/core/hrDuty" entityName="com.redxun.hr.core.entity.HrDuty" />
	<script type="text/javascript">
		addBody();
		function onButtonEdit(e){
			_OpenWindow({
				url:__rootPath+"/hr/core/hrDutySystem/detailList.do",
				title:"班制列表",
				height:"350",
				width:"700",
				ondestroy:function(action){
					if(action=='ok'){
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.getData();
						var id=mini.get("systemId");
						id.setValue(data.systemId);
						id.setText(data.name);
					}
				}
			});
		}
		
		$(function(){
			var userValue="${hrDuty.userId}";
			var groupValue="${hrDuty.groupId}";
			if(userValue==''){
				if(groupValue!=''){
					mini.get("userType").setValue("department");
					$("#user").css('display','none');
					$("#group").css('display','');
				}
			}
		});
		
		function typeChange(e){
			var value=e.value;
			if(value=='user'){
				$("#user").css('display','');
				$("#group").css('display','none');
			}else if(value=='department'){
				$("#user").css('display','none');
				$("#group").css('display','');
			}
		}
		
		function addPerson(){
			var user=mini.get("userId");
			var Ids=[];
			var Names=[];
			_UserDlg(false, function(users){  //添加抄送人用户
				for(var i=0;i<users.length;i++){
					Ids.push(users[i].userId);
					Names.push(users[i].fullname);
				}
				user.setValue(Ids.join(','));  //用逗号分隔抄送人Id列表
				user.setText(Names.join(',')); //用逗号分隔抄送人姓名列表
			});
		}
		
		function addDep(){
			var group=mini.get("groupId");
			var Ids=[];
			var Names=[];
			_DepDlg(false, function(groups){  //添加抄送人用户
				for(var i=0;i<groups.length;i++){
					Ids.push(groups[i].groupId);
					Names.push(groups[i].name);
				}
				group.setValue(Ids.join(','));  //用逗号分隔抄送人Id列表
				group.setText(Names.join(',')); //用逗号分隔抄送人姓名列表
			});
		}
	</script>
</body>
</html>