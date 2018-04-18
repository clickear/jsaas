<%-- 
    Document   : 用户编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>用户编辑</title>
<%@include file="/commons/edit.jsp"%>
<style type="text/css">
#relInstGrid .mini-panel-border{
	border: 1px solid #ececec;
	border-bottom: none;
}
</style>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${osUser.userId}" hideRecordNav="true">
		<div class="self-toolbar">
			<c:if test="${empty osUser.userId}">
				<div class="mini-checkbox" checked="true" readOnly="false" text="配置登录账号" onvaluechanged="attachAccountChange"></div>
			</c:if>
		</div>
	</rx:toolbar>
	<div class="shadowBox90">
		<form id="form1" method="post">
			<input type="hidden" name="crsf_token" class="mini-hidden"  value="${sessionScope.crsf_token}"/>
				<div class="form-outer"  id="accountInfo">
					<c:if test="${empty osUser.userId}">
							<input class="mini-hidden" id="isAttachAccount" name="isAttachAccount" value="true" />
							<table class="table-detail column_2_m" cellspacing="1" cellpadding="0" style="width: 100%;" id="accountTable">
								<caption>账号基本信息</caption>
								<tr>
									<th width="120">
										<span class="starBox">
											账　　号<span class="star">*</span>
										</span>
									</th>
									<td colspan="3">
										<input name="userName" onvalidation="onEnglishAndNumberValidation" class="mini-textbox"
									 	vtype="maxLength:50" required="true" emptyText="请输入用户登录账号" style="width:150px" />
									 	<c:if test="${isSaasMode==true}">
									 	@${domain}
									 	</c:if>
									 </td>
								</tr>
								<tr>
									<th width="120">
										<span class="starBox">
											密　　码<span class="star">*</span>
										</span>
									</th>
									<td width="250"><input class="mini-password" name="password" id="password" required="true"  style="width:60%"/></td>
									<th width="120">
										<span class="starBox">
											确认密码<span class="star">*</span>
										</span>
									</th>
									<td width="250">
										<input 
											class="mini-password" 
											name="rePassword" 
											id="rePassword" 
											required="true" 
											onvalidation="onValidateRepassword" 
											style="width: 60%" 
										/>
									</td>
								</tr>
							</table>
					</c:if>
				 
					<input id="pkId" name="userId" class="mini-hidden" value="${osUser.userId}" />
					<!-- 表示把用户加到指定的用户组的关系下 -->
					<input name="groupId" class="mini-hidden" value="${groupId}" /> <input name="relTypeId" class="mini-hidden" value="${relTypeId}" />
					<c:if test="${not empty osUser.pkId}">
						<table class="table-detail" cellspacing="1" cellpadding="0">
							<caption>账号信息</caption>
							<c:forEach items="${accountList}" var="account">
								<tr>
									<th style="width: 120px">账   号   名</th>
									<td style="width: 450px">
										${account.name} &nbsp; 
										<a class="mini-button" iconCls="icon-edit" onclick="editAccountInfo('${account.accountId}')">编辑</a>
										<a class="mini-button" iconCls="icon-modify-pwd" onclick="editAccountPwd('${account.accountId}')">修改密码</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
					</div>
			
			<div class="form-outer" >
							<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
								<caption>用户基本信息</caption>
								<tr>
									<th width="120">
										<span class="starBox">
											姓　　名 <span class="star">*</span>
										</span>
									</th>
									<td><input name="fullname" value="${osUser.fullname}" class="mini-textbox" vtype="maxLength:64" required="true" emptyText="请输入姓名" /></td>
									<th width="120">编　　号 <span class="star">*</span> </th>
									<td><input name="userNo" value="${osUser.userNo}" class="mini-textbox" vtype="maxLength:64" emptyText="请输入编号" required="true" /></td>
								</tr>
								<tr>
									<th width="120">
										<span class="starBox">
											主   部   门<span class="star">*</span>
										</span> 
									</th>
									<td width="300">
										  <input id="mainDepId" name="mainDepId" class="mini-buttonedit icon-dep-button" value="${mainDepId}" text="${mainDepName}" required="true" allowInput="false" onbuttonclick="selectMainDep"/>
									</td>
									
									<th width="120">
										从属部门
									</th>
									<td width="300">
										<input id="canDepIds" name="canDepIds" allowInput="false" class="mini-textboxlist" style="width: 80%;" value="${canDepIds}" text="${canDepNames}" /> <a class="mini-button" iconCls="icon-add" plain="true" onclick="selectCanDeps()">添加</a>
									</td>
								</tr>
								<tr>
									<th width="120">
										其   他   组
									</th>
									<td colspan="3"  width="300">
										<input id="canGroupIds" name="canGroupIds" allowInput="false" class="mini-textboxlist" style="width: 80%;" value="${canGroupIds}" text="${canGroupNames}" /> <a class="mini-button" iconCls="icon-add" plain="true" onclick="selectCanGroups()">添加</a>
									</td>
								</tr>
								<tr>
									<th>
										<span class="starBox">
											状　 　态<span class="star">*</span> 
										</span>
									</th>
									<td>
										<div name="status" class="mini-radiobuttonlist" value="${osUser.status}" required="true" repeatDirection="vertical" required="true" emptyText="请输入状态" repeatLayout="table" data="[{id:'IN_JOB',text:'在职'},{id:'OUT_JOB',text:'离职'}]" textField="text" valueField="id" required="true" vtype="maxLength:40"></div>
									</td>
									<th>性　　别</th>
									<td>
										<div name="sex" class="mini-radiobuttonlist" value="${osUser.sex}" required="true" repeatDirection="vertical" required="true" emptyText="请输入性别" repeatLayout="table" data="[{id:'Male',text:'男'},{id:'Female',text:'女'}]" textField="text" valueField="id" required="true" vtype="maxLength:40"></div>
									</td>
								</tr>
								
								<tr>
									<th>入职时间 </th>
									<td><input name="entryTime" value="${osUser.entryTime}" class="mini-datepicker" vtype="maxLength:19" /></td>
	
									<th>离职时间 </th>
									<td><input name="quitTime" value="${osUser.quitTime}" class="mini-datepicker" vtype="maxLength:19" /></td>
								</tr>
								<tr>
									<th>手　　机 </th>
									<td><input name="mobile" value="${osUser.mobile}" class="mini-textbox" vtype="maxLength:20" /></td>
	
									<th>邮　　件 </th>
									<td><input name="email" value="${osUser.email}" class="mini-textbox" vtype="maxLength:100" /></td>
								</tr>
	
								<tr>
									<th>地　　址 </th>
									<td colspan="3">
										<input name="address" class="mini-textbox" vtype="maxLength:255" value="${osUser.address}" />
									</td>
								</tr>
	
								<tr>
									<th>紧急联系人 </th>
									<td><input name="urgent" value="${osUser.urgent}" class="mini-textbox" vtype="maxLength:64" /></td>
	
									<th>紧急联系人手机 </th>
									<td><input name="urgentMobile" value="${osUser.urgentMobile}" class="mini-textbox" vtype="maxLength:20" /></td>
								</tr>
	
								<tr>
									<th>QQ　号 </th>
									<td><input name="qq" value="${osUser.qq}" class="mini-textbox" vtype="maxLength:20" /></td>
									<th>生　　日</th>
									<td><input name="birthday" value="${osUser.birthday}" class="mini-datepicker" vtype="maxLength:19" /></td>
								</tr>
								<tr>
									<th>照　　片 </th>
									<td colspan="3" style="height:120px;">
										<input id="photo" name="photo" value="${osUser.photo}" class="mini-hidden" /> 
										<img src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${osUser.photo}" class="upload-file" />
									</td>
								</tr>
							</table>
						
						
							<div  class="div-caption" >
								用户关系配置
							</div>
							
							<div style="display: none">
								<input 
									id="relTypeEditor" 
									class="mini-combobox" 
									onvaluechanged="onRelTypeChange" 
									name="relTypeEditor" 
									url="${ctxPath}/sys/org/osRelType/listUserRelsExcludeBelong.do" 
									valueField="name" 
									textField="name" 
								/> 
								<input 
									id="userEditor" 
									class="mini-buttonedit" 
									style="width: 100%" 
									onbuttonclick="showUserEditor" 
									selectOnFocus="true" 
									allowInput="false"
								/> 
								<input 
									id="groupEditor" 
									class="mini-buttonedit" 
									style="width: 100%" 
									onbuttonclick="showGroupEditor" 
									selectOnFocus="true" 
									allowInput="false"
								/>
							</div>
						
							<div id="toolbar1" class="mini-toolbar " >
								<a class="mini-button" iconCls="icon-add" plain="true" onclick="addRelInst">添加关系</a> 
								<a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeRelInst">删除关系</a>
							</div>
							<div 
								id="relInstGrid" 
								class="mini-datagrid" 
								style="width: 100%;" 
								multiSelect="false" 
								height="auto"	
								oncellbeginedit="changeColumnEditor" 
								allowCellEdit="true" 
								allowCellSelect="true" 
								url="${ctxPath}/sys/org/osRelInst/listByUserIdExcludeBelong.do?userId=${osUser.userId}" 
								idField="groupId" 
								showPager="false"
								allowAlternating="true"
							>
								<div property="columns">
									<div type="indexcolumn" width="60">序号</div>
									<div field="relTypeName" name="relTypeName" width="160">关系类型</div>
									<div field="partyName1" name="partyName1" width="200">关系用户或组</div>
									<div field="partyName2" name="partyName2" width="200">用户</div>
								</div>
							</div>
								
					</div>
				
				</div>
			</div>
		</form>
	
	</div>

	<rx:formScript formId="form1" baseUrl="sys/org/osUser" tenantId="${param.tenantId}" />
	<script type="text/javascript">
		addBody();
		var userId = '${osUser.userId}';
		var grid = mini.get('groupGrid');
		var tenantId='<c:out value='${tenantId}' />';
		var relInstGrid = mini.get('relInstGrid');
		var relTypeEditor = mini.get('relTypeEditor');
		if (userId != '') {
			relInstGrid.load();
		}
		
		function editAccountInfo(accountId){
			_OpenWindow({
				title:'编辑用户信息',
				width:620,
				iconCls:'icon-user',
				height:380,
				url:__rootPath+'/sys/core/sysAccount/edit.do?pkId='+accountId
			});
		}
		
		//重设置密码
        function editAccountPwd(pk){
			for(var k=0;k<10;k++){
				console.log(k);
			}
			console.log(k+":后面的");
        	_OpenWindow({
        		title:'重设置密码',
        		url:__rootPath+'/sys/core/sysAccount/resetPwd.do?accountId='+pk,
        		width:560,
        		height:312
        	});
        }
		
		//选择主部门
		function selectMainDep(e){
			var b=e.sender;
			
			_TenantGroupDlg(tenantId,true,'','1',function(g){
				b.setValue(g.groupId);
				b.setText(g.name);
			},false);
			
		}
		
		function selectCanDeps(){
			var canDepIds=mini.get('canDepIds');
			
			_TenantGroupDlg(tenantId,false,'1',function(groups){
				var uIds=[];
				var uNames=[];
				for(var i=0;i<groups.length;i++){
					uIds.push(groups[i].groupId);
					uNames.push(groups[i].name);
				}
				if(canDepIds.getValue()!=''){
					uIds.unshift(canDepIds.getValue().split(','));
				}
				if(canDepIds.getText()!=''){
					uNames.unshift(canDepIds.getText().split(','));	
				}
				canDepIds.setValue(uIds.join(','));
				canDepIds.setText(uNames.join(','));
			},false);
		}
		
		function selectCanGroups(){
			var canGroupIds=mini.get('canGroupIds');
			
			_GroupCanDlg({
				tenantId:tenantId,
				single:false,
				width:900,
				height:500,
				title:'用户组',
				callback:function(groups){
					var uIds=[];
					var uNames=[];
					for(var i=0;i<groups.length;i++){
						uIds.push(groups[i].groupId);
						uNames.push(groups[i].name);
					}
					if(canGroupIds.getValue()!=''){
						uIds.unshift(canGroupIds.getValue().split(','));
					}
					if(canGroupIds.getText()!=''){
						uNames.unshift(canGroupIds.getText().split(','));	
					}
					canGroupIds.setValue(uIds.join(','));
					canGroupIds.setText(uNames.join(','));
				}
			});
		}


		function onRelTypeChange(e) {
			var row = relInstGrid.getSelected();
			var drow = relTypeEditor.getSelected();
			relInstGrid.cancelEdit();

			relInstGrid.updateRow(row, {
				osRelTypeId : drow.id,
				relTypeName : drow.name,
				relTypeCat : drow.relType
			});
		}

		//删除关系
		function removeRelInst() {
			var row = relInstGrid.getSelected()
			if (!row) {
				alert('请选择需要删除的行!');
				return;
			}
			if (userId == '') {
				relInstGrid.removeRow(row);
			} else {
				_SubmitJson({
					url : __rootPath + '/sys/org/osRelInst/del.do?instId=' + row.instId,
					success : function(result) {
						relInstGrid.removeRow(row);
					}
				});
			}
		}

		//动态设置每列的编辑器
		function changeColumnEditor(e) {
			var field = e.field, rs = e.record;
			if (field == "relTypeName") {
				e.editor = mini.get('relTypeEditor');
			} else if (field == 'partyName1') {
				if (!rs.relTypeName) {
					return;
				}
				if (rs.relTypeCat == 'GROUP-USER') {
					e.editor = mini.get('groupEditor');
				} else {
					e.editor = mini.get('userEditor');
				}
			}
			e.column.editor = e.editor;
		}

		function showUserEditor() {
			_UserDlg(true, function(user) {
				var row = relInstGrid.getSelected();
				relInstGrid.cancelEdit();
				relInstGrid.updateRow(row, {
					party1 : user.userId,
					partyName1 : user.fullname,
					partyName2 : '编辑用户'
				});
			});
		}

		function showGroupEditor() {
			_GroupDlg(true, function(group) {
				var row = relInstGrid.getSelected();
				relInstGrid.cancelEdit();
				relInstGrid.updateRow(row, {
					party1 : group.groupId,
					partyName1 : group.name,
					partyName2 : '编辑用户'
				});
			});
		}

		function addRelInst() {
			relInstGrid.addRow({});
		}
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pk = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pk + '\')"></span>' + ' <span class="icon-remove" title="删除" onclick="delBelongRow(\'' + record._uid + '\')"></span>';
			return s;
		}

		function detailRow(groupId) {
			_OpenWindow({
				title : '用户组明细',
				height : 330,
				width : 600,
				url : __rootPath + '/sys/org/osGroup/detail.do?groupId=' + groupId
			});
		}

		function delBelongRow(uid) {
			var row = grid.getRowByUID(uid);
			_SubmitJson({
				url : __rootPath + '/sys/org/osUser/removeBelongRelType.do',
				data : {
					groupId : row.groupId,
					userId : userId
				},
				success : function(text) {
					grid.removeRow(row);
				}
			});
		}

		$(function() {
			/**-- 用于上传图片 **/
			$(".upload-file").on('click', function() {
				var img = this;
				_UserImageDlg(true, function(imgs) {
					if (imgs.length == 0)
						return;
					$(img).attr('src', '${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=' + imgs[0].fileId);
					$(img).siblings('input[type="hidden"]').val(imgs[0].fileId);
				});
			});
		});

		function onValidateRepassword(e) {
			if (e.isValid) {
				var pwd = mini.get('password').getValue();
				var rePassword = mini.get('rePassword').getValue();
				if (pwd != rePassword) {
					e.errorText = "两次密码不一致!";
					e.isValid = false;
				}
			}
		}

		function attachAccountChange(e) {
			var val = this.getChecked();
			var isAttachAccount = mini.get('isAttachAccount');
			isAttachAccount.setValue(val);
			if (val) {
				$("#accountInfo").css('display', '');
			} else {
				$("#accountInfo").css('display', 'none');
			}
		}

		//保存用户 组前进行表单的数据处理，由Tag中的SaveData调用
		function handleFormData(formData) {
			var relInsts=relInstGrid.getData();
			var mainDepId=mini.get('mainDepId').getValue();
			var canDepIds=mini.get('canDepIds').getValue();
			
			if(mainDepId!='' && canDepIds!=''){
				if(canDepIds.indexOf(mainDepId)!=-1){
					alert('主部门不能同时是从属部门！');
					return {
						formData:formData,
						isValid:false
					}
				}
			}
			
			var formArray=relInstGrid.getData();
			for(var i=0;i<formArray.length;i++){
				if(!formArray[i].party1||!formArray[i].osRelTypeId){
					return {
						formData : formData,
						isValid : false
					};
				}
			}
			
			formData.push({
				name : 'relInsts',
				value : mini.encode(relInsts)
			});
			return {
				formData : formData,
				isValid : true
			};
		}

		function addGroup() {
			var data = grid.getData();
			//通过用户 选择对话框选择用户组
			_GroupDlg(false, function(groups,dim) {
				for (var i = 0; i < groups.length; i++) {
					var isFound = false;
					//过滤重复的
					for (var j = 0; j < data.length; j++) {
						if (data[j].groupId == groups[i].groupId) {
							isFound = true;
							break;
						}
					}
					if (!isFound) {
						groups[i].isMain = 'NO';
						groups[i].dimId = dim.dimId;
						grid.addRow(groups[i]);
					}
				}
			},true);			
		}
		
		/* function selfSaveData() {
	        form.validate();
	        if (!form.isValid()) {
	            return;
	        }
	        
	        var formData=$("#form1").serializeArray();
	        //处理扩展控件的名称
	        var extJson=_GetExtJsons("form1");
	        for(var key in extJson){
	        	formData.push({name:key,value:extJson[key]});
	        }
	       
	       
	       //若定义了handleFormData函数，需要先调用 
	       if(isExitsFunction('handleFormData')){
	        	var result=handleFormData(formData);
	        	if(!result.isValid) return;
	        	formData=result.formData;
	        }
	        
	        var config={
	        	url:"${ctxPath}/sys/org/osUser/save.do",
	        	method:'POST',
	        	data:formData,
	        	success:function(result){
	        		//如果存在自定义的函数，则回调
	        		if(isExitsFunction('successCallback')){
	        			successCallback.call(this,result);
	        			return;	
	        		}
	        		
	        		CloseWindow('ok');
	        	}
	        }
	        
	        if(result && result["postJson"]){
	        	config.postJson=true;
	        }
	        
	        _SubmitJson(config);
	     } */

	</script>
	<script src="${ctxPath}/scripts/common/form.js" type="text/javascript"></script>
</body>
</html>