
<%-- 
    Document   : [微信应用]编辑页
    Created on : 2017-06-04 12:27:36
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[微信应用]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom topBtn" >
		<a class="mini-button" iconCls="icon-save" plain="true" onclick="onSaveForm">保存</a>
		<a class="mini-button" iconCls="icon-save" plain="true" onclick="onSaveDeploy">保存并发布</a>
<!-- 		<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a> -->
	</div>
	
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post" action="/wx/ent/wxEntAgent/save.do">
				<input id="pkId" name="id" class="mini-hidden" value="${wxEntAgent.id}" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<caption>企业微信配置信息</caption>
					
					<tr>
						<th>企业ID</th>
						<td>
							<input id="wxEntCorp.corpId"  name="wxEntCorp.corpId" value="${wxEntCorp.corpId}" class="mini-textbox"  width="80%" require="true"/>
						</td>
						<th style="white-space: nowrap;">是否启用企业微信</th>
						<td >
							<div  name="wxEntCorp.enable" class="mini-checkbox" readOnly="false" text="启用" trueValue="1" falseValue="0" value="${wxEntCorp.enable}"></div>
						</td>
					</tr>
					<tr>
						<th >通讯录密钥</th>
						<td colspan="3">
							<input name="wxEntCorp.secret" value="${wxEntCorp.secret}" class="mini-textbox"  width="90%" require="true" />
						</td>
					</tr>
				</table>
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<caption>[微信应用]基本信息</caption>
					<tr>
						<th>密　钥</th>
						<td colspan="3">
							<input id="secret" name="secret" value="${wxEntAgent.secret}" class="mini-textbox"   style="width: 50%" />
							<a class="mini-button" iconCls="icon-next" onclick="loadApp">加载</a>
						</td>
					</tr>
					<tr>
						<th>名　称</th>
						<td>
							<input name="name" value="${wxEntAgent.name}" class="mini-textbox"   style="width: 90%" />
						</td>
					
						<th>备　注</th>
						<td>
							<input name="description" value="${wxEntAgent.description}" class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>信任域名</th>
						<td>
							<input name="domain" value="${wxEntAgent.domain}" class="mini-textbox"   style="width: 90%" />
						</td>
					
						<th>应用主页地址</th>
						<td>
							<input name="homeUrl" value="${wxEntAgent.homeUrl}" class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>应用ID</th>
						<td>
							<input name="agentId" value="${wxEntAgent.agentId}"  class="mini-textbox"   style="width: 90%" allowInput="false" />
						</td>
					
						<th>是否默认</th>
						<td>
							<div  name="defaultAgent" class="mini-checkbox" readOnly="false" text="默认" trueValue="1" falseValue="0" value="${wxEntAgent.defaultAgent}"></div>
						</td>
					</tr>
				</table>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="wx/ent/wxEntAgent"
		entityName="com.redxun.wx.ent.entity.WxEntAgent" />
		
	<script type="text/javascript">
		addBody();
		var action="";
		function onSaveForm(){
			action="save";
			onSave("form1");
		}
		
		function onSaveDeploy(){
			action="saveDeploy";
			onSave("form1");
		}
		
		function handleFormData(formData){
			formData.action=action;
			return true;
		}
	
		function loadApp(){
			var corpId=mini.get("wxEntCorp.corpId").getValue();
			if(!corpId) {
				alert("企业ID为空");
				return;
			}
			var appSecret=mini.get("secret").getValue();
			if(!appSecret) {
				alert("请输入应用的密钥");
				return;
			} 
			var url=__rootPath +"/wx/ent/wxEntAgent/getAppInfo.do?corpId="+corpId+"&secret=" +appSecret;
			$.get(url,function(data){
				if(data.success){
					var json=eval("(" +data.message+")");
					mini.getByName("name").setValue(json.name);
					mini.getByName("description").setValue(json.description);
					mini.getByName("domain").setValue(json.redirect_domain);
					mini.getByName("homeUrl").setValue(json.home_url);
					mini.getByName("agentId").setValue(json.agentid);
				}
				else{
					alert("出错信息:" +data.message);
				}
				
			});
		}
	</script>
</body>
</html>