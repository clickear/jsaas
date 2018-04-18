
<%-- 
    Document   : [WX_MESSAGE_SEND]编辑页
    Created on : 2017-07-17 17:58:08
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>消息发送</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div class="mini-toolbar topBtn">
	    <a class="mini-button" iconCls="icon-ok" id="sendButton" onclick="sendMsg()">发送</a>
	    <a class="mini-button" iconCls="icon-cancel" onclick="CloseWindow('cancel')">取消</a>
	</div>
	<div id="p1" class="form-outer shadowBox90" style="padding-top: 8px;">
		<form id="form1" method="post">
			<div class="form-inner">
				<%-- <input id="pkId" name="ID" class="mini-hidden" value="${wxMessageSend.ID}" /> --%>
				<input id="pubId" name="pubId" class="mini-hidden" value="${wxMessageSend.pubId}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<tr>
						<th>群发对象<br />
						<input id="receiveType" name="receiveType" class="mini-combobox" style="width: 70px;" textField="text" valueField="id" value="tag"
							data="[{'text':'标签','id':'tag'},{'text':'用户','id':'openId'},{'text':'所有人','id':'everyone'}]" showNullItem="false" 
							allowInput="false" onvaluechanged="changeTheType" />
						</th>
						<td><input id="receiver" class="mini-textboxlist" name="receiver" textName="receiverText" style="width: 80%; height: 80px;"
							allowInput="false" valueField="id" textField="text" required="true" /> <a class="mini-button" id="addButton" iconCls="icon-add" plain="true"
							onclick="addReceiver">添加接收者</a></td>
					</tr>
					<tr>
						<th>消息类型</th>
						<td>
							<div id="mediaType" name="mediaType" class="mini-radiobuttonlist" textField="text" valueField="id" value="article"
								data="[{'text':'图片','id':'image'},{'text':'语音','id':'voice'},{'text':'视频','id':'video'},{'text':'缩略图','id':'thumb'},{'text':'图文消息','id':'article'}]"
								onvaluechanged="mediaChange"></div>
						</td>
					</tr>
					<tr>
					<th>选择素材</th>
					<td>
					<input id="meterial" class="mini-textboxlist"
					name="meterial" textName="meterialText"  style="width: 80%;"  allowInput="false"
					valueField="id" textField="text" required='true' />
					<a class="mini-button" id="addMeterialButton" iconCls="icon-add" plain="true"
						onclick="addMeterial">选择素材</a>
					</td>
					</tr>

				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	addBody();
	mini.parse();
	var receiveType=mini.get("receiveType");
	var receiver=mini.get("receiver");
	var addButton=mini.get("addButton");
	var addMeterialButton=mini.get("addMeterialButton");
	var mediaType=mini.get("mediaType");
	var meterial=mini.get("meterial");
	var form=new mini.Form("form1");
	var sendButton=mini.get("sendButton");
	function addReceiver(){
		var receiveTypeValue=receiveType.getValue();
		if(receiveTypeValue=="tag"){
			_OpenWindow({
				url : '${ctxPath}/wx/core/wxSubscribe/TagSelect.do?pubId=${wxMessageSend.pubId}',
				title : "添加接收者",
				width : 600,
				height : 400,
				onload : function() {
				},
				ondestroy : function(action) {
					 if (action == 'ok'){
						 var iframe = this.getIFrameEl().contentWindow;
						 var receiverValues=iframe.getReceiverValue();
						 var receiverTexts=iframe.getReceiverText();
						 receiver.setValue(""+receiverValues);
						 receiver.setText(receiverTexts);
				               }
				}
			});	

		}else if(receiveTypeValue=="openId"){
			_OpenWindow({
				url : '${ctxPath}/wx/core/wxSubscribe/OpenIdSelect.do?pubId=${wxMessageSend.pubId}',
				title : "添加接收者",
				width : 600,
				height : 400,
				onload : function() {
				},
				ondestroy : function(action) {
					 if (action == 'ok'){
						 var iframe = this.getIFrameEl().contentWindow;
						 var receiverValues=iframe.getReceiverValue();
						 var receiverTexts=iframe.getReceiverText();
						 receiver.setValue(""+receiverValues);
						 receiver.setText(receiverTexts);
				               }
				}

			});	
		}
	}
	
	
	function changeTheType(e){
		if(e.value=='everyone'){
			receiver.hide();
			addButton.hide();
			receiver.setRequired("false");
		}else{
			receiver.show();
			addButton.show();
			receiver.setRequired("true");
		}
	}
	
	function mediaChange(e){
		meterial.setValue("");
		meterial.setText("");
	}
	function addMeterial(){
		var mediaTypeValue=mediaType.getValue();
			_OpenWindow({
				url : '${ctxPath}/wx/core/wxMeterial/Select.do?pubId=${wxMessageSend.pubId}&type='+mediaTypeValue,
				title : "添加素材",
				width : 600,
				height : 400,
				onload : function() {
				},
				ondestroy : function(action) {
					 if (action == 'ok'){
						 var iframe = this.getIFrameEl().contentWindow;
						 var meterialValues=iframe.getMeterialValue();
						 var meterialTexts=iframe.getMeterialText();
						 meterial.setValue(""+meterialValues);
						 meterial.setText(meterialTexts);
				               }
				}

			});	
	}
	
	function sendMsg(){
		form.validate();
		if (!form.isValid()) {
			return;
		}
		var messageid =mini.loading("发送中请稍后", "...");
		$.ajax({
			type:'post',
			url:'${ctxPath}/wx/core/wxMessageSend/sendMsg.do',
			data:form.getData(),
			success:function (result){
				if(result.success){
					CloseWindow('ok');
				}else{
					mini.alert(result.errMsg);
				}
				mini.hideMessageBox(messageid);
			}
		});
		}
	</script>
</body>
</html>