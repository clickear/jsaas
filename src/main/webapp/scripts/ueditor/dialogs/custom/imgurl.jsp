
<%-- 
    Document   : [自定义表单配置设定]编辑页
    Created on : 2017-05-16 10:25:38
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[自定义表单配置设定]编辑</title>
<%@include file="/commons/edit.jsp"%>

<script type="text/javascript">
	function onOk(){
		var form = new mini.Form("#p1");   
		form.validate();
		if(!form.isValid()) return;
		CloseWindow("ok");
	} 
	
	function onCancel(){
		CloseWindow("cancel");
	} 
	
	function getUrl(){
		return mini.get("inputUrl").getValue();
	}
	
	function setUrl(val){
		var obj=mini.get("inputUrl");
		obj.setValue(val);
		obj.setText(val);
	}
	
	function clean(e){
		var obj=e.sender;
		obj.setValue("");
		obj.setText("");
	}
	
	
	
	function onButtonEdit(e){
		_UserImageDlg(true, function(imgs) {
			if (imgs.length == 0) return;
			
			var obj=mini.get("inputUrl");
			var url="/sys/core/file/imageView.do?thumb=true&fileId=" +imgs[0].fileId;
			obj.setValue(url);
			obj.setText(url);
		});
	}
	
</script>
</head>
<body>
	<div  class="mini-toolbar mini-toolbar-bottom" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;" id="toolbarBody">
	             	<a class="mini-button" iconCls="icon-save" plain="true" onclick="onOk">确定</a>
                	<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a>
	            </td>
	        </tr>
	    </table>
	</div>
	<div id="p1" class="form-outer">
		
    	<table>
			<tr>
				<th>输入URL</th>
				<td>
					<input id="inputUrl" class="mini-buttonedit" width="400" showClose="true" oncloseclick="clean" onbuttonclick="onButtonEdit" require="true" emptyText="请输入图片URL"/>
					
				</td>
			</tr>
		</table>
	</div>
			    


</body>
</html>