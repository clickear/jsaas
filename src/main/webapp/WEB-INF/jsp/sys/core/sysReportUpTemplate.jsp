<%-- 
    Document   :  报表编辑页
    Created on : 2015-3-21, 0:11:48
     * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 使用范围：
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>报表编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div style="padding-left: 6px; padding-right: 6px;">
		<div class="mini-toolbar" style="margin: 0; padding: 0;">
			<table style="width: 100%;">
				<tr>
					<td style="width: 100%;"><a class="mini-button" iconCls="icon-upload" onclick="uploadTemplate()" plain="true">上传</a> <a class="mini-button" iconCls="icon-close" onclick="CloseWindow()" plain="true">关闭</a> <a class="mini-button" iconCls="icon-help" onclick="showTip()" plain="true">上传帮助</a></td>
				</tr>
			</table>
		</div>
		<form id="uploadForm" method="post" action="${ctxPath}/sys/core/sysReport/uploadTemplate.do" enctype="multipart/form-data">
			<table class="table-detail" style="width: 100%;">
				<tr>
					<th>上传模板文件</th>
					<td><input id="file" type="file" name="bpmnFile" /></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
   		mini.parse();
   		var success='${success}';
    	if(success=='true'){
    	  CloseWindow();
    	}
    	
    	function getPath(){
    		var path = '${path}';
    		return path;
    	}
    	
   		function showTip() {
            mini.showTips({
                content: "如果报表模板中不含有图片,只需上传.jasper模板文件,如果报表模板中含有另外的图片,则需将图片和.jasper文件一起打包成zip格式上传。",
                state: "info",
                x: "center",
                y: "bottom",
                timeout: 10000
            });
        }
   		
		var form=new mini.Form('uploadForm');
    	
    	function uploadTemplate(){
    		form.validate();
    		if(!form.isValid()){
    			return;
    		}
    		var file=document.getElementById('file');
    		if(file.value==''){
    			 mini.showTips({
    	                content: "上传文件为空。",
    	                state: "danger",
    	                x: "center",
    	                y: "bottom",
    	                timeout: 3000
    	            });
    			return;
    		}
    		var str = file.value.split(".");
    		if(str[1]!='zip'&&str[1]!='jasper'){
   			 mini.showTips({
   	                content: "上传文件格式不符。",
   	                state: "danger",
   	                x: "center",
   	                y: "bottom",
   	                timeout: 3000
   	            });
   			return;
   		}
    		//上传文件
    		$("#uploadForm").submit();
    	}
</script>

</body>
</html>