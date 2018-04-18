<%-- 
    Document   :  报表参数设计
    Created on : 2015-3-21, 0:11:48
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 使用范围：
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>用户组选择框</title>
<%@include file="/commons/list.jsp"%>
<style>
hr {
	border: 1px dashed #ccc;
	border-bottom: 0;
	border-right: 0;
	border-left: 0;
}

.mybox,.mybox .mini-textbox-input {
	background: #FFF5EE;
}


</style>
</head>
<body>
	<div class="mini-toolbar" style="margin: 0; padding: 0;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;"><a class="mini-button" iconCls="icon-save" onclick="saveInit()" plain="true">保存</a> <a class="mini-button" iconCls="icon-close" onclick="CloseWindow()" plain="true">关闭</a></td>
			</tr>
		</table>
	</div>
	<div style="padding-bottom: 3px;padding-top:7px">
	<h1 style="background: #e6e8eb;">代码编辑</h1>
	<a class="mini-button" iconCls="icon-edit" onclick="showDesigner()">控件设计器</a>
	</div>
	<br>
	
	<textarea class="mini-textarea" class="mini-textarea" vtype="maxLength:65535" style="width: 100%; height: 350px;" borderStyle="border:0" id="htmlCode" name="htmlCode">${paramConfig}</textarea>
	<br>
	<br>
	<hr>
	<h1 style="background: #e6e8eb;">代码预览</h1>
	<div id="resultTd">${paramConfig}</div>
	

	<script type="text/javascript">
		mini.parse();

		function showDesigner(){
			
			var htmlCode=mini.get('htmlCode');
			var val=htmlCode.getValue();
			
			_OpenWindow({
				title:'控件在线设计',
				max:true,
				height:500,
				width:680,
				url:__rootPath+'/sys/core/controlDesinger.do',
				onload:function(){
					var iframe = this.getIFrameEl();
					iframe.contentWindow.setContent(val);
				},
				ondestroy:function(){
					var iframe = this.getIFrameEl();
		            var content = iframe.contentWindow.getContent();
		            //回调结果
		            htmlCode.setValue(content);
		            //展示设计后的效果
		            $("#resultTd").html(content);
		            mini.parse();
				}
			});
		}
		
		function saveInit(){
			var htmlCode = mini.get('htmlCode').getValue();
			var pkId = "${param['pkId']}";
			_SubmitJson({
				url : __rootPath + '/sys/core/sysReport/saveParam.do',
				method : 'POST',
				data : {htmlCode:htmlCode,
						pkId:pkId},
				success : function(result) {
						CloseWindow('ok');
				}
			});
			
		}
	</script>

</body>
</html>