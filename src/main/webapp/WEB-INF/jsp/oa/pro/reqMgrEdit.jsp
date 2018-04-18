<%-- 
    Document   : 需求编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>需求编辑</title>
<%@include file="/commons/edit.jsp"%>
<link href="${ctxPath}/scripts/ueditor/form-design/form-def.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/ueditor-ext.js"></script>

<style type="text/css">
	html body .check .mini-buttonedit-icon{
		background:url(${ctxPath}/styles/icons/preview.png) no-repeat 50% 50%;
	}
	html body .rep .mini-buttonedit-icon{	
		background:url(${ctxPath}/styles/icons/icon-transmit.png) no-repeat 50% 50%;
	}
	html body .exe .mini-buttonedit-icon{
		background:url(${ctxPath}/styles/icons/user.png) no-repeat 50% 50%;
	}
	    
	.table-detail1 {
		border-collapse: collapse;
		border: solid 1px #909AA6;
		width: 100%;
		margin-top: 6px;
	}
	
	.table-detail1 th {
		min-height: 25px;
		border: solid 1px #909AA6;
		text-align: right;
		vertical-align: top;
		font-weight: bold;
		min-width: 100px;
		padding: 5px;
	}
	
	.table-detail1 td {
		min-height: 25px;
		border: solid 1px #909AA6;
		text-align: left;
		padding: 5px;
	}
	
	.table-detail1 td.parttype td {
		border: none;
		padding: 2px;
	}
	
	.table-detail1 caption {
		text-align: left;
		height: 34x;
		font-size: 20px;
		padding: 6px;
	}
</style>
</head>
<body>
	
        
        
         <rx:toolbar toolbarId="toolbar1" pkId="${reqMgr.reqId}" excludeButtons="save" hideRemove="true" hideRecordNav="true">
	   <div class="self-toolbar">
	<a class="mini-button" iconCls="icon-save" plain="true" onclick="onOk">保存草稿</a>
	        <a class="mini-button" iconCls="icon-save" plain="true" onclick="submitAndSave">提交保存</a>
	   </div>
	</rx:toolbar>
	
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="reqId" class="mini-hidden" value="${reqMgr.reqId}" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<caption>需求管理</caption>
					<tr>
						<th>标　　题 <span class="star">*</span> </th>
						<td colspan="3"><input name="subject" value="${reqMgr.subject}" class="mini-textbox" vtype="maxLength:128" style="width: 100%"
							required="true" emptyText="请输入标题" /></td>
					</tr>
						
						<th>需求编码 <span class="star">*</span> 
						</th>
						<td><input name="reqCode" value="${reqMgr.reqCode}" class="mini-textbox" vtype="maxLength:50" style="width: 90%" required="true"
							emptyText="请输入需求编码" /></td>
						<th>选择版本  <span class="star">*</span></th>
						<td><input name="version" id="version" class="mini-combobox" style="width: 150px;" textField="version" valueField="version"
							value="${reqMgr.version}" url="${ctxPath}/oa/pro/proVers/verList.do?projectId=${projectId}" /></td>
					<tr>
						<th>审  批  人 </th>
						<td colspan="1">
						 <input id="checkerId" name="checkerId" value="${reqMgr.checkerId}" text="${checker}" 
							class="mini-buttonedit check" emptyText="请选择..."  allowInput="false" onbuttonclick="onSelectUser('checkerId')"/>

						</td>
					
						<th>负  责  人 </th>
						<td colspan="1">
						 <input id="repId" name="repId" value="${reqMgr.repId}" text="${rep}" 
							class="mini-buttonedit rep" emptyText="请选择..."  allowInput="false" onbuttonclick="onSelectUser('repId')"/>

						</td>
					</tr>
					<tr>
						<th>执  行  人 </th>
						<td colspan="3">
						<input id="exeId" name="exeId" value="${reqMgr.exeId}" text="${exe}" 
							class="mini-buttonedit exe" emptyText="请选择..."  allowInput="false" onbuttonclick="onSelectUser('exeId')"/> 

						</td>
					</tr>

					<tr>
						<td colspan="4" class="td-grid"><ui:UEditor height="300"  name="descp"  id="content">${reqMgr.descp}</ui:UEditor></td>
					</tr>


					<!--下面的是隐藏的  -->
					<tr style="display: none;">
						<th>父需求ID </th>
						<td><input name="parentId" value="${reqMgr.parentId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
						<th>层次 </th>
						<td><input name="level" value="${reqMgr.level}" class="mini-textbox" vtype="maxLength:10" style="width: 90%" /></td>
						<th>项目或产品Id <span class="star">*</span> 
						</th>
						<td><input name="projectId" value="${reqMgr.projectId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%"
							required="true" emptyText="请输入项目或产品Id" /></td>
						<td><input name="path" value="${reqMgr.path}" class="mini-textbox" vtype="maxLength:64" style="width: 90%"
							required="true" emptyText="请输入路径" /></td>	
					</tr>


				</table>

			</div>
		</form>
	</div>

	<rx:formScript formId="form1" baseUrl="oa/pro/reqMgr"  entityName="com.redxun.oa.pro.entity.ReqMgr"/>
	<script type="text/javascript">
mini.parse();
//版本列表
$().ready(function () {
	　　　　$.ajax({
	　　　　　　url: '${ctxPath}/oa/pro/proVers/verList.do?projectId=${projectId}',
	　　　　　　type: 'POST',
	　　　　　　success: function (data) {
		},　
	　　　　})	
	});
	
	//选择用户
function onSelectUser(where){
	_TenantUserDlg(tenantId,true,function(user){
		var checkerId=mini.get(where);
		
		checkerId.setValue(user.userId);
		checkerId.setText(user.fullname);
		
	});
}

//审批人负责人执行人
$(function(){
	$("#checkerId").dblclick(function(){
		onSelectUser('checkerId');
	});
	$("#repId").dblclick(function(){
		onSelectUser('repId');
	});
	$("#exeId").dblclick(function(){
		onSelectUser('exeId');
	});
	
})
//提交保存
function submitAndSave(){
	 form.validate();
     if (form.isValid() == false) {
         return;
     }
     var formData=$("#form1").serializeArray();

     _SubmitJson({
     	url:'${ctxPath}/oa/pro/reqMgr/save.do?submitThis=YES',
     	method:'POST',
     	data:formData,
     	success:function(result){
 				CloseWindow('ok');
 				return;
 		
     	}
     });
}
	
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
    	url:"${ctxPath}/oa/pro/reqMgr/save.do?",
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
</body>

</html>