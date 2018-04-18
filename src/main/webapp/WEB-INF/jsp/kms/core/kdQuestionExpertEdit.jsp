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
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />

</head>
<body>

	<rx:toolbar toolbarId="toolbar1" pkId="${kdQuestion.queId}" excludeButtons="save">
		<div class="self-toolbar">
			<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveQuestion">保存</a>
		</div>
	</rx:toolbar>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="queId" class="mini-hidden" value="${kdQuestion.queId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>问题基本信息</caption>

					<tr>
						<th width="15%">问题内容 <span class="star">*</span> ：
						</th>
						<td><input name="subject" value="${kdQuestion.subject}" class="mini-textbox" vtype="maxLength:80" style="width: 90%" required="true" emptyText="一句话描述你的问题" /></td>
					</tr>

					<tr>
						<th width="15%" >问题补充（选填） ：</th><td><a id="desc" class="mini-button" plain="true"  iconCls="icon-downarrow" onclick="changeDesc">展开</a></td>
					</tr>
					
					<tr id="questiondesc" style="display: none;">
						<td colspan="2"><div><ui:UEditor height="200" name="question" id="question" >${kdQuestion.question}</ui:UEditor></div></td>
					</tr>

					<tr>
						<th>附件 ：</th>
						<td><input name="fileIds" class="upload-panel" plugins="upload-panel" style="width: 80%" allowupload="true" label="附件" fname="fileNames" allowlink="true" zipdown="true" mwidth="80" wunit="%" mheight="0" hunit="px"
							fileIds="${kdQuestion.fileIds}" fileNames="${fileNames}" /></td>
					</tr>

					<tr>
						<th>分类 ：</th>
						<td><input id="treeId" name="treeId" value="${kdQuestion.treeId}"  text="${kdQuestion.treeId}"  style="width: 250px;" class="mini-buttonedit" onbuttonclick="onButtonEdit" allowInput="false" /></td>
					</tr>

					<tr>
						<th>标签 ：</th>
						<td><input name="tags" value="${kdQuestion.tags}" class="mini-textbox" vtype="maxLength:128" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>悬赏货币 <span class="star">*</span> ：
						</th>
						<td><input id="rewardScore" name="rewardScore" class="mini-combobox" style="width:150px" popwidth="150" onvaluechanged="changeMinMaxWidth"
							data="[{'id':'0','text':'悬赏货币0'},{'id':'5','text':'悬赏货币5'},{'id':'10','text':'悬赏货币10'},{'id':'15','text':'悬赏货币15'},{'id':'20','text':'悬赏货币20'},{'id':'30','text':'悬赏货币30'},{'id':'50','text':'悬赏货币50'},{'id':'80','text':'悬赏货币80'},{'id':'100','text':'悬赏货币100'}]" textField="text" valueField="id"
						    value="0"  required="true" allowInput="false" /></td>
					</tr>
					
					<tr>
						<th>回复人类型 ：</th>
						<td>
						<div id="replyType" name="replyType" class="mini-radiobuttonlist" enabled="false"  required="true" data="[{id:'ALL',text:'所有人'},{id:'PERSON',text:'专家个人'},{id:'DOMAIN',text:'领域专家'}]" value="${kdUser.userType}"></div>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="kms/core/kdQuestion" />
	<script type="text/javascript">
		var desc=mini.get("desc");
		function changeDesc(){
			var de=desc.getIconCls();
			if(de=='icon-uparrow'){
				desc.setIconCls("icon-downarrow");
				desc.setText("展开");
				$("#questiondesc").css('display','none');
			}else{
				desc.setIconCls("icon-uparrow");
				desc.setText("收起");
				$("#questiondesc").css('display','');
			}
		}
		
		$(function() {
			$('.upload-panel').each(function() {
				$(this).uploadPanel();
			});
		});
		
		function saveQuestion(){
			var uId="${param['uId']}";
			form.validate();
	        if (!form.isValid()) {
	            return;
	        }
	        var formData=_GetFormJson("form1");
	        _SubmitJson({
	        	url:"${ctxPath}/kms/core/kdQuestion/saveExpertQuestion.do",
	        	data:{
	        		formData:mini.encode(formData),
	        		uId:uId
	        	},
	        	method:'POST',
	        	success:function(result){
	        		var data=result.data;
	        		window.location.href="${ctxPath}/kms/core/kdQuestion/get.do?pkId="+data.queId;
	        	}
	        });
		}
		
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