<%-- 
    Document   : 动态编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[ProWorkMat]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
		<form id="form1" method="post" class="form-outer">
			<div class="form-inner">
				<input id="pkId" name="actionId" class="mini-hidden" value="${proWorkMat.actionId}" />
				<table class="table-detail column_2 " cellspacing="1" cellpadding="0">
					<div align="center" style="font-size: x-large;font-weight: bold; ">${mytype}</div>
					<tr>
						<td><textarea name="content" class="mini-textarea" vtype="maxLength:512" style="width: 99%;height: 200px;" required="true" emptyText="请输入评论内容">${proWorkMat.content}</textarea></td>
					</tr>
					<tr style="display: none;">
						<th>类型 <span class="star">*</span> ：
						</th>
						<td><input name="type" value="${proWorkMat.type}" class="mini-textbox" vtype="maxLength:50" style="width: 90%" required="true"
							emptyText="请输入类型" /></td>
					</tr>
					<tr style="display: none;">
						<th>类型主键ID <span class="star">*</span> ：
						</th>
						<td><input name="typePk" value="${proWorkMat.typePk}" class="mini-textbox" vtype="maxLength:64" style="width: 90%"
							required="true" emptyText="请输入类型主键ID" /></td>
					</tr>
					<tr>
					<td colspan="4" align="center"><input name="attach" class="upload-panel" id="attach"
						plugins="upload-panel" style="width: 80%" allowupload="true"
						label="附件" fname="attachName" allowlink="true" zipdown="true"
						mwidth="80" wunit="%" mheight="0" hunit="px"  fileNames="${fileNames}"  fileIds="${fileIds}"/></td>
                        </tr>
				</table>
			</div>
		</form>
	<div class="mini-toolbar dialog-footer" style="border: none;">
	<a class="mini-button" iconCls="icon-save" plain="false" onclick="onOk">保存</a>
	<a class="mini-button" iconCls="icon-close" plain="false" onclick="CloseWindow();">取消</a></div>
	<rx:formScript formId="form1" baseUrl="oa/pro/proWorkMat"  entityName="com.redxun.oa.pro.entity.ProWorkMat"/>
	<script type="text/javascript">
	
	//上传附件
	$(function(){
		$('.upload-panel').each(function(){$(this).uploadPanel();});
	});
	/* function addAttach(){
		 ShowUploadDialog({
	        entityName: "com.redxun.oa.pro.entity.ProWorkMat",
	        recordId: "${proWorkMat.actionId}"
	    }); 
	} */
	//重写了saveData方法
	function selfSaveData(issave){
		var form = new mini.Form("form1");
		form.validate();
	    if (!form.isValid()) {//验证表格
	        return; }
	    var formData=$("#form1").serializeArray();
	    //加上租用Id
	    if(tenantId!=''){
	        formData.push({
	        	name:'tenantId',
	        	value:tenantId });
	    }
	    _SubmitJson({
	    	url:"${ctxPath}/oa/pro/proWorkMat/save.do?",
	    	method:'POST',
	    	data:formData,
	    	success:function(result){
	    		//如果存在自定义的函数，则回调
	    		if(isExitsFunction('successCallback')){
	    			successCallback.call(this,result);
	    			return;	
	    		}
	    		var pkId=mini.get("pkId").getValue();
	    		//为更新
	    		if (pkId!=''){
	    			CloseWindow('ok');
	    			return;
	    		}
	    		CloseWindow('ok');} });
	}

	</script>
	<script type="text/javascript">
	
	mini.parse();
	var account=mini.get("account");
	var psw=mini.get("psw");
	
	</script>
</body>
</html>