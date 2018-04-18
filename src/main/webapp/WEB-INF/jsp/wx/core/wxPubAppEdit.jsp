
<%-- 
    Document   : [公众号管理]编辑页
    Created on : 2017-06-29 16:57:29
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[公众号管理]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="wxPubApp.id" />
	<div id="p1" class="form-outer shadowBox90" style="padding-top: 8px;">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="id" name="id" class="mini-hidden" value="${wxPubApp.id}" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<tr>
						<th>
							<span class="starBox">
								app ID<span class="star">*</span>
							</span>
						</th>
						<td>
								<input name="appId" id="appId" value="${wxPubApp.appId}"
							class="mini-textbox"   style="width: 90%" required="true" onvaluechanged="changeAppId"/>
						</td>
						<th>管理者微信号</th>
						<td>
								<input name="wxNo" value="${wxPubApp.wxNo}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="starBox">
								密钥(appsecret)<span class="star">*</span>
							</span>
						</th>
						<td>
								<input name="secret" value="${wxPubApp.secret}"
							class="mini-textbox"   style="width: 90%" required="true"/>
						</td>
						<th>
							<span class="starBox">
								类　　型<span class="star">*</span>
							</span>
						</th>
						<td>
							<input  name="type" value="${wxPubApp.type}" class="mini-radiobuttonlist" style="width: 90%" required="true" textField="text" valueField="id" data="[{ 'id': 'PUBLIC', 'text': '公众号' },{ 'id': 'SUBSCRIBE', 'text': '订阅号' }]" />
						</td>
					</tr>
					<tr>
						<th>接口消息地址</th>
						<td>
							<input name="interfaceUrl" id="interfaceUrl" value="${wxPubApp.interfaceUrl}"
							class="mini-textbox"   style="width: 90%"  required="true" allowInput="false"/>
						</td>
						<th>
							<span class="starBox">
								是否认证<span class="star">*</span>
							</span>
						</th>
						<td>
						<input  name="authed" value="${wxPubApp.authed}"  class="mini-radiobuttonlist" style="width: 90%" required="true" textField="text" valueField="id" data="[{ 'id': 'YES', 'text': '是' },{ 'id': 'NO', 'text': '否' }]" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="starBox">
								token<span class="star">*</span>
							</span>
						</th>
						<td>
								<input name="TOKEN" value="${wxPubApp.TOKEN}"
							class="mini-textbox"   style="width: 90%"  required="true"/>
						</td>
						<th>js安全域名</th>
						<td>
								<input name="jsDomain" value="${wxPubApp.jsDomain}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>名　　称</th>
						<td>
								<input name="name" value="${wxPubApp.name}"
							class="mini-textbox"   style="width: 90%" />
						</td>
						<th>别　　名</th>
						<td>
								<input name="alias" value="${wxPubApp.alias}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<c:if test="${!empty wxPubApp.id}">
					<tr>
						<th>相关配置</th>
						<td colspan="3">
						<a class="mini-button" iconCls="icon-save" plain="true" onclick="subscribeConfig()">关注回复配置</a>
						<a class="mini-button" iconCls="icon-read" plain="true" onclick="keyWordReplyConfig()">关键词回复配置</a>
						<a class="mini-button" iconCls="icon-btn-mgr" plain="true" onclick="menuConfig()">菜单配置</a>
						</td>
					</tr>
					</c:if>
					
					
					<tr>
						<th>描　　述</th>
						<td colspan="3">
								<textarea name="description" value="${wxPubApp.description}"
							class="mini-textarea"   style="width: 90%" ></textarea>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="wx/core/wxPubApp"
		entityName="com.redxun.wx.core.entity.WxPubApp" />
		<script type="text/javascript">
		addBody();
		var appId=mini.get("appId");
		var interfaceUrl=mini.get("interfaceUrl");
		var publicAddress="${publicAddress}";
		function subscribeConfig(){
			_OpenWindow({
				url : '${ctxPath}/wx/core/wxPubApp/welcome.do?pubId=${wxPubApp.id}',
				title : "关注回复配置",
				width : 800,
				height : 400,
				onload : function() {
				},	
				ondestroy : function(action) {
					 if (action == 'ok'){
						 var iframe = this.getIFrameEl().contentWindow;
				               }
				}

			});	
		}
		
		function menuConfig(){
			_OpenWindow({
				url : '${ctxPath}/wx/core/wxPubApp/menu.do?pubId=${wxPubApp.id}',
				title : "菜单配置",
				width : 800,
				height : 400,
				onload : function() {
				},	
				ondestroy : function(action) {
					 if (action == 'ok'){
						 var iframe = this.getIFrameEl().contentWindow;
				               }
				}

			});	

		}
		initInterfaceUrl();
		
		function initInterfaceUrl(){
			var appIdValue=appId.getValue();
			interfaceUrl.setValue(publicAddress+"${ctxPath}/pubReceive/"+appIdValue+".do");
		}
		
		function changeAppId(){
			var appIdValue=appId.getValue();
			interfaceUrl.setValue(publicAddress+"${ctxPath}/pubReceive/"+appIdValue+".do");
		}
		
		function keyWordReplyConfig(){
			_OpenWindow({
				url : '${ctxPath}/wx/core/wxKeyWordReply/list.do?pubId=${wxPubApp.id}',
				title : "菜单配置",
				width : 800,
				height : 400,
				max:true,
				showModel:false,
				onload : function() {
				},	
				ondestroy : function(action) {
					 if (action == 'ok'){
						 var iframe = this.getIFrameEl().contentWindow;
				               }
				}

			});
		}
		</script>
</body>
</html>