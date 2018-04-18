
<%-- 
    Document   : [微信关注者]编辑页
    Created on : 2017-06-30 08:51:08
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>选择接收者</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	
	<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;">
		<div region="south" showSplit="false" showHeader="false" height="34" showSplitIcon="false" style="width: 100%" bodyStyle="border:0">
			<div class="mini-toolbar dialog-footer" style="text-align: center; border: none;">
				<a class="mini-button" iconCls="icon-ok" onclick="CloseWindow('ok');">确定</a> <a class="mini-button" iconCls="icon-cancel"
					onclick="CloseWindow('cancel');">取消</a>
			</div>
		</div>
		<div title="接收者列表" region="center" showHeader="false" showCollapseButton="false">
			<ul id="tree1" class="mini-tree" url="${ctxPath}/wx/core/wxSubscribe/getTag.do?pubId=${param.pubId}" textField="name"
				idField="id" style="width: 300px; padding: 5px;" showTreeIcon="true" resultAsTree="false" onnodeclick="clickNode">
			</ul>
		</div>
	</div>


	<script type="text/javascript">
	
	var recieverValue;
	var recieverText;
	function getReceiverValue(){
		return recieverValue;
	}	
	function getReceiverText(){
		return recieverText;
	}
	function clickNode(e){
		recieverValue=e.record.id;
		recieverText=e.record.name;
	}
	
	</script>
</body>
</html>