<%-- 
    Document   : [KdUser]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[KdUser]编辑</title>
<%@include file="/commons/edit.jsp"%>
<style>
	.tips{
		position: absolute;
		width: 100%;
		height: 100%;
		top:0;
		left: 0;
		background: rgba(0,0,0,.5);
		background:#000 \9;
		opacity:.5 \9;
		z-index: 100;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.tips span{
		color: #fff;
		font-size: 24px;
		display: block;
		text-align: center;
		margin-top: -180px;
		margin-top: 0 \9;
	}
</style>
</head>
<body>
	<div class="tips">
		<span>您尚未注册关联知识仓库的账号，请在此处注册！</span>
	</div>
	<rx:toolbar toolbarId="toolbar1" pkId="${kdUser.kuserId}" excludeButtons="save">
		<div class="self-toolbar">
			<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveKDUser">保存</a>
		</div>
	</rx:toolbar>
	<div id="p1" class="form-outer shadowBox">
		<form id="form1" method="post">
			<div class="form-inner">
				<input 
					id="pkId" 
					name="kuserId" 
					class="mini-hidden" 
					value="${kdUser.kuserId}" 
				/> 
				<input 
					id="sn" 
					name="sn" 
					class="mini-hidden" 
					value="${kdUser.sn}" 
				/> 
				<input 
					name="point" 
					value="${kdUser.point}" 
					class="mini-hidden" 
				/> 
				<input 
					name="grade" 
					value="${kdUser.grade}" 
					class="mini-hidden" 
				/>
				<table class="table-detail column_2" cellspacing="1" cellpadding="0">
					<caption>专家基本信息</caption>
					<tr>
						<th width="15%">
							 <span class="starBox">
								用户类型 <span class="star">*</span>
							</span>
						</th>
						<td>
							<input 
								name="userType" 
								value="${kdUser.userType}" 
								valueField="id" 
								textField="text" 
								class="mini-combobox" 
								style="width: 50%" 
								data="[{id:'DOMAIN',text:'领域专家'},{id:'PERSON',text:'个人专家'}]" 
								required="true"
							/></td>
						<th>
							 <span class="starBox">
								姓　　名<span class="star">*</span>
							</span>
						</th>
						<td><input name="fullname" value="${kdUser.fullname}" class="mini-textbox" vtype="maxLength:7" style="width: 50%" required="true"/></td>
					</tr>
					<tr>
						<th>
							 <span class="starBox">
								知识领域 <span class="star">*</span>
							</span>
						</th>
						<td>
							<input 
								id="knSysId" 
								name="knSysId" 
								value="${kdUser.knSysTree.treeId}" 
								text="${kdUser.knSysTree.name}" 
								style="width: 180px;" 
								class="mini-buttonedit" 
								onbuttonclick="onButtonEdit" 
								allowInput="false" 
								required="true"
							/></td>
						<th>
							<span class="starBox">
								爱问领域 <span class="star">*</span>
							</span>
						</th>
						<td>
							<input 
								id="reqSysId" 
								name="reqSysId" 
								value="${kdUser.reqSysTree.treeId}" 
								text="${kdUser.reqSysTree.name}" 
								style="width: 180px;" 
								class="mini-buttonedit" 
								onbuttonclick="onButtonEdit" 
								allowInput="false" 
								required="true"
							/></td>
					</tr>

					<tr>
						<%-- 			<th>序号 ：</th>
						<td><input name="sn" value="${kdUser.sn}" class="mini-textbox" vtype="maxLength:10" style="width: 50%" /></td> --%>
						<th>个性签名 </th>
						<td colspan="3"><input name="sign" value="${kdUser.sign}" class="mini-textbox" vtype="maxLength:80" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>个人简介 </th>
						<td colspan="3"><input name="profile" value="${kdUser.profile}" class="mini-textbox" vtype="maxLength:100" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								性　　别 <span class="star">*</span>
							</span>
								
						</th>
						<td>
							<div 
								id="sex" 
								name="sex" 
								class="mini-radiobuttonlist" 
								data="[{id:'male',text:'男'},{id:'female',text:'女'}]" 
								value="${kdUser.sex}" 
								required="true"
							></div>
						</td>
						<th>办公电话 </th>
						<td><input name="officePhone" value="${kdUser.officePhone}" class="mini-textbox" vtype="maxLength:20" style="width: 50%" /></td>
					</tr>

					<tr>
						<th>手机号码 </th>
						<td><input name="mobile" value="${kdUser.mobile}" class="mini-textbox" vtype="maxLength:16" style="width: 50%" /></td>
						<th>电子邮箱 <span class="star">*</span></th>
						<td><input name="email" value="${kdUser.email}" class="mini-textbox" vtype="email;rangeLength:5,20;" style="width: 50%" required="true"/></td>
					</tr>

					<tr>
						<th>关联用户 </th>
						<td colspan="3">
							<input 
								id="link-user" 
								name="link-user" 
								value="${user.userId}" 
								text="${user.fullname}" 
								style="width: 180px;" 
								class="mini-buttonedit" 
								readonly="readonly"
							/></td>
					</tr>

					<tr>
						<th>头　　像</th>
						<td colspan="3" width="80" height="100">
							<input 
								id="headId" 
								name="headId" 
								value="${kdUser.headId}" 
								class="mini-hidden" 
							/> 
							<img 
								src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${kdUser.headId}" 
								class="upload-file" 
								width="90px" 
								height="90px" 
							/></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="kms/core/kdUser" entityName="com.redxun.kms.core.entity.KdUser" />
<script type="text/javascript">
	
	addBody();
	if(!$('.mini-window',parent.document).length){
		$('.mini-tabs-body:visible', parent.document).addClass('index_box');
		$('.show_iframe:visible', parent.document).addClass('index_box');
	}
	
	setTimeout(function(){
		$('.tips').slideToggle(1000);
	},5000)
	
	
	
    $(function(){
		/**-- 用于上传图片 **/
	   $(".upload-file").on('click',function(){
			var img=this;
			_UserImageDlg(true,
				function(imgs){
					if(imgs.length==0) return;
					$(img).attr('src','${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId='+imgs[0].fileId);
					$(img).siblings('input[type="hidden"]').val(imgs[0].fileId);
					$(img).attr('width','90');      //设置图片大小
					$(img).attr('height','90');
					var id=$(img).siblings('input[type="hidden"]').attr('id');
					mini.get(id).setValue(imgs[0].fileId);
				}
			);
		});	
	});
    
    function saveKDUser(){
    	form.validate();
        if (!form.isValid()) {
            return;
        }
    	var uId=mini.get("link-user").getValue();
    	_SubmitJson({
    		url:"${ctxPath}/kms/core/kdUser/checkKdUser.do",
    		data:{
    			uId:uId
    		},
    		method:'POST',
    		showMsg:false,
    		success:function(result){
    			var data=result.data;
    			var reqTreeId=mini.get("reqSysId").getValue();
				var knTreeId=mini.get("knSysId").getValue();
				var linkUserId=mini.get("link-user").getValue();
    			if(data=='true'){
    				alert(result.message);
    			}
    			else{
    				var formData=_GetFormJson("form1");
    				_SubmitJson({
    					url:"${ctxPath}/kms/core/kdUser/saveKdUserMgr.do",
    					data:{
    						formData:mini.encode(formData),
    						reqTreeId:reqTreeId,
    						knTreeId:knTreeId,
    						uId:linkUserId
    					},
    					method:'POST',
    					success:function(result){
    						CloseWindow('ok');
    					}
    				});
    			}
    		}
    	});
    }
	
	function onButtonEdit(e) {
		var btnEdit = this;
		mini.open({
					url : "${ctxPath}/kms/core/sysTree/dialog.do",
					title : "选择问题分类",
					width : 750,
					height : 380,
					ondestroy : function(action) {
						if (action == "ok") {
							var iframe = this.getIFrameEl();
							var data = iframe.contentWindow.GetData();
							data = mini.clone(data); 
							if (data) {
								btnEdit.setValue(data.treeId);   //设置自定义SQL的Key
								_SubmitJson({
									url:"${ctxPath}/sys/core/sysTree/getNameByPath.do",
									showMsg:false,
									data:{
										path:data.path
									},
									method:'POST',
									success:function(result){
										btnEdit.setText(result.data);
									}
								});
							}
						}
					}
				});
	}
	
	function chooseUser(){
		var user=mini.get("link-user");
		var userId="";
		var userName="";
		_UserDlg(true, function(users){     //添加收件人用户
			userId=users.userId;
			userName=users.fullname;
			user.setValue(userId);   //用逗号分隔收件人Id列表
			user.setText(userName);  //用逗号分隔收件人姓名列表
		});
	}
/* 	function selectGroup() {
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
	} */
/* 	function _LinkUserDlg(single, callback, reDim) {
		if (!reDim) {
			reDim = false;
		}
		_TenantTypeDlg('', single, '', callback, reDim);
	}
	function _TenantTypeDlg(tenantId, single, showDimId, callback, reDim) {
		var title = '从属用户选择';
		_OpenWindow({
			iconCls : 'icon-group',
			url : __rootPath + '/kms/core/kdQuestion/userDialog.do?single=' + single + '&tenantId=' + tenantId,
			height : 480,
			width : 680,
			title : title,
			ondestroy : function(action) {
				if (action != 'ok')
					return;
				var iframe = this.getIFrameEl();
				var users = iframe.contentWindow.getUsers();
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
					if (single && users.length > 0) {
						callback.call(this, users[0], dim);
					} else {
						callback.call(this, users, dim);
					}
				}
			}
		});
	} */
	</script>
</body>
</html>