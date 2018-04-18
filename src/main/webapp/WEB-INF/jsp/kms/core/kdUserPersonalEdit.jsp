<%-- 
    Document   : [KdQuestion]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>咨询专家</title>

<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/zszk.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/knowl/knowl.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/uploadpanel/jquery-uploadpanel.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/css/kdUserPersonalEdit.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />

</head>
<body>

	<div class="main-container">
		<div class="header">个人资料设置</div>
		<div class="content">
			<div class="detail">
				<div class="setting" style="position: relative;">
					<div class="title">个人资料列表</div>
					<form id="detail-form" class="detail-form">
						<input name="kuserId" value="${kdUser.kuserId}" class="mini-hidden" />
						<table>
							<tr>
								<th>姓名：</th>
								<td><input name="fullname" value="${kdUser.fullname}" class="mini-textbox" vtype="maxLength:50" style="width: 100%" required="true" /></td>
							</tr>

							<tr>
								<th>性别：</th>
								<td><div id="sex" name="sex" class="mini-radiobuttonlist" data="[{id:'male',text:'男'},{id:'female',text:'女'}]" value="${kdUser.sex}"></div></td>
							</tr>

							<tr>
								<th>积分：</th>
								<td>${kdUser.point}</td>
							</tr>

							<tr>
								<th>等级：</th>
								<td>${kdUser.grade}</td>
							</tr>

							<tr>
								<th>知识领域：</th>
								<td><input id="knSysId" name="knSysId" value="${kdUser.knSysTree.treeId}" text="${kdUser.knSysTree.name}" style="width: 180px;" class="mini-buttonedit" onbuttonclick="onButtonEdit" allowInput="false" /></td>
							</tr>

							<tr>
								<th>爱问领域：</th>
								<td><input id="reqSysId" name="reqSysId" value="${kdUser.reqSysTree.treeId}" text="${kdUser.reqSysTree.name}" style="width: 180px;" class="mini-buttonedit" onbuttonclick="onButtonEdit" allowInput="false" /></td>
							</tr>

							<tr>
								<th>个性签名：</th>
								<td><textarea id="sign" name="sign" class="mini-textarea" style="width: 100%">${kdUser.sign}</textarea></td>
							</tr>

							<tr>
								<th>个人简介：</th>
								<td><textarea id="profile" name="profile" class="mini-textarea" style="width: 100%">${kdUser.profile}</textarea></td>
							</tr>

							<tr>
								<th>办公电话：</th>
								<td><input name="officePhone" value="${kdUser.officePhone}" class="mini-textbox" vtype="maxLength:50" style="width: 100%" required="true" /></td>
							</tr>

							<tr>
								<th>手机号码：</th>
								<td><input name="mobile" value="${kdUser.mobile}" class="mini-textbox" vtype="maxLength:11" style="width: 100%" required="true" /></td>
							</tr>

							<tr>
								<th>电子邮箱：</th>
								<td><input name="email" value="${kdUser.email}" class="mini-textbox" style="width: 100%" required="true" /></td>
							</tr>

						</table>
						<div style="position: absolute; top: 55px; right: 250px; z-index: 10;">
							<input id="headId" name="headId" value="${kdUser.headId}" class="mini-hidden" /> <img src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${kdUser.headId}" class="upload-file" width="80px" height="100px" />
						</div>
						<input id="saveInfo" type="button" value="保存" />
					</form>

				</div>
			</div>
		</div>
	</div>



	<%-- <rx:formScript formId="form1" baseUrl="kms/core/kdQuestion" /> --%>
	<script type="text/javascript">
	mini.parse();

	 $(function(){
			/**-- 用于上传图片 **/
		   $(".upload-file").on('click',function(){
				var img=this;
				_UserImageDlg(true,
					function(imgs){
						if(imgs.length==0) return;
						$(img).attr('src','${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId='+imgs[0].fileId);
						$(img).siblings('input[type="hidden"]').val(imgs[0].fileId);
						$(img).attr('width','80');      //设置图片大小
						$(img).attr('height','100');
						var id=$(img).siblings('input[type="hidden"]').attr('id');
						mini.get(id).setValue(imgs[0].fileId);
					}
				);
			});	
		});
	
		
		$(function() {
			/* $('.upload-panel').each(function() {
				$(this).uploadPanel();
			}); */
			
			$("#saveInfo").click(function(){
				var form = new mini.Form("detail-form");
				form.validate();
		        if (!form.isValid()) {
		            return;
		        }
				var userId='${kdUser.kuserId}';
				var formData=_GetFormJson("detail-form");
				var reqTreeId=mini.get("reqSysId").getValue();
				var knTreeId=mini.get("knSysId").getValue();
				//alert(mini.encode(formData));
				_SubmitJson({
					url:"${ctxPath}/kms/core/kdUser/saveKdUser.do",
					data:{
						formData:mini.encode(formData),
						kuserId:userId,
						reqTreeId:reqTreeId,
						knTreeId:knTreeId
					},
					method:'POST',
					success:function(result){
						window.location.href="${ctxPath}/kms/core/kdUser/personalGet.do?i=4&userId="+"${kdUser.user.userId}";
					}
				});
			});
		});
		
		
		
		function onButtonEdit(e) {
			var btnEdit = this;
			mini.open({
						url : "${ctxPath}/kms/core/sysTree/dialog.do",
						title : "选择问题分类",
						width : 650,
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
	</script>
</body>
</html>