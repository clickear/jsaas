<%-- 
    Document   : 项目关注编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[ProWorkAtt]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${proWorkAtt.attId}" excludeButtons="remove">
	<div class="self-toolbar">
	<a class="mini-button" iconCls="icon-reload" plain="true" onclick="location.reload();">刷新</a>
	</div>
	</rx:toolbar>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="attId" class="mini-hidden" value="${proWorkAtt.attId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>关注</caption>

					<tr>
						<th>关注人ID <span class="star">*</span> ：
						</th>
						<td><input name="userId" value="${proWorkAtt.userId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%"
							required="true" emptyText="请输入关注人ID" /></td>
					</tr>

					<tr>
						<th>关注时间 ：</th>
						<td><input name="attTime" value="${proWorkAtt.attTime}" class="mini-textbox" vtype="maxLength:19" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>通知方式 <span class="star">*</span> ：
						</th>
						<td><input name="noticeType" value="${proWorkAtt.noticeType}" class="mini-textbox" vtype="maxLength:50" style="width: 90%"
							required="true" emptyText="请输入通知方式" /></td>
					</tr>

					<tr>
						<th>关注类型 <span class="star">*</span> ：
						</th>
						<td><input name="type" value="${proWorkAtt.type}" class="mini-textbox" vtype="maxLength:50" style="width: 90%" required="true"
							emptyText="请输入关注类型" /></td>
					</tr>

					<tr>
						<th>类型主键ID <span class="star">*</span> ：
						</th>
						<td><input name="typePk" value="${proWorkAtt.typePk}" class="mini-textbox" vtype="maxLength:64" style="width: 90%"
							required="true" emptyText="请输入类型主键ID" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/pro/proWorkAtt"  entityName="com.redxun.oa.pro.entity.ProWorkAtt"/>
	<script type="text/javascript">
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
	    	url:"${ctxPath}/oa/pro/proWorkAtt/save.do?",
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