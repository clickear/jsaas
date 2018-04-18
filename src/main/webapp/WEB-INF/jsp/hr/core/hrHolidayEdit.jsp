<%-- 
    Document   : 假期编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>假期编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${hrHoliday.holidayId}" />
	<div id="p1" class="form-outer shadowBox">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="holidayId" class="mini-hidden"
					value="${hrHoliday.holidayId}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption style="text-align: center; font-size: 20px;">假期基本信息</caption>
					<tr>
						<th>假期名称 <span class="star">*</span> 
						</th>
						<td><input name="name" value="${hrHoliday.name}"
							class="mini-textbox" vtype="maxLength:20" style="width: 90%" /></td>
					</tr>
					<tr>
						<th><c:if test="${hrHoliday.holidayId==null}">开    始</c:if>日期 <span class="star">*</span> 
						</th>
						<td><input id="startDay" name="startDay" value="${hrHoliday.startDay}"
							class="mini-datepicker" vtype="maxLength:19" style="width: 90%" required="true" showPopupOnClick="true" emptyText="请输入开始日期" /></td>
					</tr>
 	
 					<c:if test="${hrHoliday.holidayId==null}">
					<tr>
						<th>结束日期 <span class="star">*</span> 
						</th>
						<td><input id="endDay" name="endDay" value="${hrHoliday.endDay}"
							class="mini-datepicker" vtype="maxLength:19" style="width: 90%"  onvaluechanged="validateTime"
							required="true" showPopupOnClick="true"  emptyText="请输入结束日期" /></td>
					</tr>
					</c:if>

					<tr>
						<th>假期描述 <span class="star">*</span> 
						</th>
						<td><textarea name="desc" value="${hrHoliday.desc}"
								class="mini-textarea" vtype="maxLength:500" 
								style="width: 90%; height: 120px;" emptyText="请输入描述"></textarea></td>
					</tr>
					
					<tr>
						<th>放假用户类型 <span class="star">*</span> 
						</th>
						<td><input id="hType" name="hType" value="user" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="horizontal" style="width: 90%" required="true" data="[{'id':'user','text':'用户'},{'id':'department','text':'部门'},{'id':'system','text':'班制'}]" textField="text" valueField="id" onValuechanged="typeChange(e)" /></td>
					</tr>

					<tr id="user">
						<th>所属用户 </th>
						<td><input id="userId" class="mini-textboxlist"
							name="userId" style="width: 300px;" value="${hrHoliday.userId}"
							text="${hrHoliday.userName}" allowInput="false" required="true" />
							 <a class="mini-button" iconCls="icon-add" plain="true"
							onclick="onSelectUser">选择</a>
						</td>
					</tr>
					
					<tr id="group" style="display: none;">
						<th>所属用户组 </th>
						<td><input id="groupId" class="mini-textboxlist"
							name="groupId" style="width: 300px;" value="${hrHoliday.groupId}"
							text="${hrHoliday.groupName}" allowInput="false" required="true" />
							 <a class="mini-button" iconCls="icon-grant" plain="true"
							onclick="onSelectGroup">选择</a>
						</td>
					</tr>
					
					<tr id="system" style="display: none;">
						<th>班制 </th>
						<td>
							<div id="systemId" name="systemId" class="mini-checkboxlist" repeatItems="3" repeatLayout="table" textField="name" valueField="systemId" value="${hrHoliday.systemId}"  url="${ctxPath}/hr/core/hrDutySystem/getAllSystem.do" ></div>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	addBody();
	mini.parse();
	//终止时间不能小于起始时间
	function validateTime(){ 
	
	    var startDay=mini.get("startDay").getValue();
		var endDay=mini.get("endDay").getValue();
		if(startDay > endDay){
			alert("终止时间不能小于起始时间！");
			return;
		}else{}
	}
	
	$(function(){
		var userId='${hrHoliday.userId}';
		var groupId='${hrHoliday.groupId}';
		var systemId='${hrHoliday.systemId}';
		if(userId!=''){
			mini.get("hType").setValue("user");
			$("#user").css('display','');
			$("#group").css('display','none');
			$("#system").css('display','none');
		}
		if(groupId!=''){
			mini.get("hType").setValue("department");
			$("#user").css('display','none');
			$("#group").css('display','');
			$("#system").css('display','none');	
		}
		if(systemId!=''){
			mini.get("hType").setValue("system");
			$("#user").css('display','none');
			$("#group").css('display','none');
			$("#system").css('display','');
		}
	});
	
	//选所属用户组
	function onSelectGroup(){
		_GroupDlg(false,function(groups){
			var groupId=mini.get("groupId");
			var str1=[] ;
			var str2 =[];
			
			for(var i=0;i<groups.length;i++){
				str1[i] = groups[i].groupId;
				str2[i] = groups[i].name;
			}
			groupId.setValue(str1.toString());
			groupId.setText(str2.toString());
		});
	}
	
	//选所属用户
	function onSelectUser(){
		_UserDlg(false,function(users){
			var userId=mini.get("userId");
			var str1=[] ;
			var str2 =[];
			
			for(var i=0;i<users.length;i++){
				str1[i] = users[i].userId;
				str2[i] = users[i].fullname;
			}
			userId.setValue(str1.toString());
			userId.setText(str2.toString());
		});
	}
	
	function typeChange(e){
		var value=e.value;
		if(value=='user'){
			$("#user").css('display','');
			$("#group").css('display','none');
			$("#system").css('display','none');
		}else if(value=='department'){
			$("#user").css('display','none');
			$("#group").css('display','');
			$("#system").css('display','none');
		}else if(value=='system'){
			$("#user").css('display','none');
			$("#group").css('display','none');
			$("#system").css('display','');
		}
	}
	
	function selfSaveData(){
		var data=_GetFormJson("form1");
		_SubmitJson({
			url:__rootPath+"/hr/core/hrHoliday/saveData.do",
			method:'POST',
			data:{
				data:mini.encode(data),
				hType:mini.get("hType").getValue()
			},
			success:function(result){
				CloseWindow("ok");
			}
		});
	}
	
	

	</script>
	<rx:formScript formId="form1" baseUrl="hr/core/hrHoliday"
		entityName="com.redxun.hr.core.entity.HrHoliday" />
</body>
</html>