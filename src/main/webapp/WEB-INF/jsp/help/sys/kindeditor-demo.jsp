<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	 <title>富文本控件</title>
        <%@include file="/commons/get.jsp"%>
	   <script src="${ctxPath}/scripts/kindeditor/kindeditor-min.js" type="text/javascript"></script>
</head>
<body>
	<h2>富文本控件</h2>
	
        <!--body-->
        <textarea id="ke" name="content" style="width:100%;height:250px;visibility:hidden;">
            <a href="http://www.kindsoft.net" target="_blank">http://www.kindsoft.net</a>

        </textarea>
        <div style="text-align:center;padding:8px;">
            <input type="button" value="setText" onclick="setText();"/>
            <input type="button" value="getText" onclick="getText();"/>
        </div>
	
	
	<p>描述：
		<br/>
			使用kindeditor进行设计及开发
		<br/>
	</p>
	
	<script type="text/javascript">
	 mini.parse();
	 var editorId = "ke";	
	 setTimeout(function () {
	        editor = KindEditor.create('#' + editorId, {
	            resizeType: 1,
	            uploadJson: 'kindeditor/upload_json.ashx',
	            fileManagerJson: 'kindeditor/file_manager_json.ashx',
	            allowPreviewEmoticons: false,
	            allowImageUpload: true
	        });
	    }, 1);
	</script>
</body>
</html>
