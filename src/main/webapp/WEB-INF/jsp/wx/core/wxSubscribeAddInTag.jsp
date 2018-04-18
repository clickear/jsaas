
<%-- 
    Document   : [微信关注者]编辑页
    Created on : 2017-06-30 08:51:08
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>加入标签</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div class="mini-fit" style="height: 100%;margin-top: 0;">
		<div id="toolbar1" class="mini-toolbar topBtn" style="padding: 2px;">
			<a class="mini-button" plain="true" iconCls="icon-ok" onclick="joinInTag">确定</a>
			<a class="mini-button" plain="true" iconCls="icon-cancel" onclick="CloseWindow('cancel')">取消</a>
		</div>

		<div 
			id="taglist" 
			class="mini-checkboxlist" 
			repeatItems="4" 
			repeatLayout="table" 
			textField="name" 
			valueField="id"
			url="${ctxPath}/wx/core/wxPubApp/listPubTag.do?pubId=${pubId}" 
			value="${tagIdList}"
		></div>

	</div>
	<script type="text/javascript">
		function joinInTag(){
			var taglist=mini.get("taglist").getValue();
			
			_SubmitJson({
	   			url:__rootPath+'/wx/core/wxSubscribe/joinInTag.do',
	   			type:'post',
	   			data:{pubId:"${pubId}",pkId:"${pkId}",useTag:taglist},
	   			success:function(result){
	   				if(result.success){
						CloseWindow('ok');
					}
	   			}
	   		});
			
		}
	</script>
</body>
</html>