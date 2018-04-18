
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
	<div class="mini-fit" >
	<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;">
		<div region="south" showSplit="false" showHeader="false" height="34" showSplitIcon="false" style="width: 100%" bodyStyle="border:0">
			<div class="mini-toolbar dialog-footer" style="text-align: center; border: none;">
				<a class="mini-button" iconCls="icon-ok" onclick="CloseWindow('ok');">确定</a> <a class="mini-button" iconCls="icon-cancel"
					onclick="CloseWindow('cancel');">取消</a>
			</div>
		</div>
		<div title="业务视图列表" region="center" showHeader="false" showCollapseButton="false">

			<div style="margin-top: 20px;">
				<a class="mini-button" iconCls="icon-add" plain="true" onclick="addReceiver">添加接收者</a>
				<input id="receiver" class="mini-textboxlist"
					name="receiver" textName="receiverText"  style="width: 80%;height: 80px;"  allowInput="false"
					valueField="id" textField="text" />
			</div>
		</div>
	</div>
	</div>

	<script type="text/javascript">
	mini.parse();
	var receiver=mini.get("receiver");
	
		function addReceiver(){
			_OpenWindow({
				url : '${ctxPath}/wx/core/wxSubscribe/Select.do?pubId=${param.pubId}',
				title : "添加接收者",
				width : 600,
				height : 400,
				onload : function() {
				},
				ondestroy : function(action) {
					 if (action == 'ok'){
						 var iframe = this.getIFrameEl().contentWindow;
						 var nodes=iframe.getNodes();
						 var user=[];
						 var receiverValue="";
						 var receiverText="";
						 for(var i=0;i<nodes.length;i++){
							 if(nodes[i].type=="subscribe"){
							 var tagAndId= nodes[i].id.split("#");
							 var repeat=false;
								 for(var j=0;j<user.length;j++){
									 if(user[j]==tagAndId[1]){//说明之前添加过
										 repeat=true; 
									 }
								 }
								 if(repeat){
									 nodes.splice(i,1);
								 }else{
									 user.push(tagAndId[1]);
								 }
							 }
						 }
						 for (var i=0;i<nodes.length;i++){
							 receiverValue+=nodes[i].id+",";
							 receiverText+=nodes[i].name+",";
						 }
						 if(receiverValue.length>0){
							 receiverValue=receiverValue.substring(0,receiverValue.length-1);
						 }
						 if(receiverText.length>0){
							 receiverText=receiverText.substring(0,receiverText.length-1);
						 }
						 receiver.setValue(receiverValue);
						 receiver.setText(receiverText);
				               }
				}

			});	
		}
		
		function getReceiver(){
			return receiver.getValue();
		}
	
	

		</script>
</body>
</html>