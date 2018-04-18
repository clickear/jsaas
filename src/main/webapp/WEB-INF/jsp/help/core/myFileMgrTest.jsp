<%
	//用户文件附件管理器示例
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户文件附件管理器示例</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <%@include file="/commons/dynamic.jspf" %>
    <link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
    <link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
    <link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
    <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
    <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script> 
    <script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
    <script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
    <script src="${ctxPath}/scripts/jquery/plugins/uploadpanel/jquery-uploadpanel.js" type="text/javascript"></script>
    
</head>
<body>
	<h2>用户文件附件管理器示例</h2>
	<a class="mini-button" type="button" value="showMyFileMgr" onclick="showMyFileMgr">show file mgr</a>
	
	<p>
		附件控件,注意使用file-attach样式
		<input class="mini-textboxlist file-attach" name="abc"  value="1,2,3" text="ALINK,BLINK,CLINK" required="true" style="width:400px;height:80px;"/>
		
		
	<p></p>
		<input id="myPanel" name="files" fname="fileNames" class="upload-panel" fileIds="123,456,5678" fileNames="abc,yywr,文件2" single="false" 
  			allowupload="true" zipdown="true" allowlink="true" style="border:solid 1px #ccc;"/>
    		
    		<p></p>
		
			<div class="mini-panel file-attach" title="文件列表"  style="width:80%;" height="auto"
    			showToolbar="false" showHeader="false" showCloseButton="false" >
    			
			    <a class="mini-button" iconCls="icon-upload" onclick="upload" plain="true">上传</a>
			    
    			<ul class="list-item">
    				<li><a href="#">文件1.png</a><span class="mini-textboxlist-close mini-textboxlist-close-hover"></span></li>
    				<li><a href="#">文件2.docx</a><span class="mini-textboxlist-close"></span></li>
    				<li><a href="#">文件3.txt</a><span class="mini-textboxlist-close"></span></li>
    				<li><a href="#">文件4.ppt</a><span class="mini-textboxlist-close"></span></li>
    				<li><a href="#">文件3.txt</a><span class="mini-textboxlist-close"></span></li>
    				<li><a href="#">文件4.ppt</a><span class="mini-textboxlist-close"></span></li>
    				<li><a href="#">文件3.txt</a><span class="mini-textboxlist-close"></span></li>
    				<li><a href="#">文件4.ppt</a><span class="mini-textboxlist-close"></span></li>
    				<li><a href="#">文件3.txt</a><span class="mini-textboxlist-close"></span></li>
    				<li><a href="#">文件4.ppt</a><span class="mini-textboxlist-close"></span></li>
    			</ul>
    		</div>
    		
    		<br/>
    		
    		<div class="mini-panel file-attach" name="abc" showToolbar="false" height="auto" showHeader="false" showCloseButton="false"  
    				value="1,2,3" text="ALINK,BLINK,CLINK" required="true" style="width:400px;border:solid 1px #ccc;" >
    				<a class="mini-button" iconCls="icon-upload" onclick="alert('upload')" plain="true">上传</a>
    				<ul class="list-item">
	    				<li><a href="#">文件1.png</a><span class="mini-textboxlist-close mini-textboxlist-close-hover"></span></li>
	    				<li><a href="#">文件2.docx</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件1.png</a><span class="mini-textboxlist-close mini-textboxlist-close-hover"></span></li>
	    				<li><a href="#">文件2.docx</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件3.txt</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件4.ppt</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件3.txt</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件4.ppt</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件3.txt</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件4.ppt</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件3.txt</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件4.ppt</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件2.docx</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件3.txt</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件4.ppt</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件3.txt</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件4.ppt</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件3.txt</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件4.ppt</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件3.txt</a><span class="mini-textboxlist-close"></span></li>
	    				<li><a href="#">文件4.ppt</a><span class="mini-textboxlist-close"></span></li>
    				</ul>
    		</div>
    		
    		<br/>
    		
    		
    
	</p>
	
	<script type="text/javascript">
		mini.parse();
		function showMyFileMgr(){
			/*_OpenWindow({
				title:'我的附件管理器',
				height:500,
				width:820,
				url:__rootPath+'/sys/core/sysFile/myMgr.do?dialog=true&single=true',
				ondestroy:function(action){
					
				}
			});*/
			
			_UploadFileDlg({
				from:'SELF',
				types:'Image,Document,Zip,Vedio',
				single:false,
				showMgr:true,
				callback:function(files){
					alert(files.length);
				}
			});
		}
		
		$(function(){			
			$('.upload-panel').uploadPanel();
		});
		
		
	</script>
</body>
</html>