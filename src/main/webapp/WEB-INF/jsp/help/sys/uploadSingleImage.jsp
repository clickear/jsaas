<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	 <title>上传单个图片示例</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<h2>上传单个图片示例</h2>
	
	<table align="center">
		<tr>
			<td>上传控件</td>
			<td>
				<input name="regCodeFileId" value="${sysInst.regCodeFileId}" class="mini-hidden" />
				<img src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=undefined" class="upload-file"/>
			</td>
		</tr>
	</table>
	
	
	<table align="center">
		<tr>
			<td>上传文件</td>
			<td>
				<input type="button" value="file upload" onclick="uploadFile()" />
				
			</td>
		</tr>
	</table>
	
	<table>
		<tr>
			<td>
				<div id="cbl1" class="mini-checkboxlist" repeatItems="2" repeatLayout="table"
				    textField="text" valueField="id" value="a" 
				    data="[{id:'1',text:'a'},{id:'2',text:'b'}]" >
				</div>
			</td>
		</tr>
	</table>
	
	<p>描述：
		<br/>
		上传单个图片，并且展示其图片略图图，提供删除，预览，重新上传
		<br/>
	</p>
	
	<script type="text/javascript">
		mini.parse();

		$(function(){
			$(".upload-file").on('click',function(){
				var img=this;
				_UserImageDlg(true,
					function(imgs){
						if(imgs.length==0) return;
						$(img).attr('src','${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId='+imgs[0].fileId);
						$(img).siblings("input").val(imgs[0].fileId);
					}
				);
			});	
		});
		
		function uploadFile(){
			_FileUploadDlg({
				from:'APP',
				types:'Document',
				callback:function(files){
					var fileIds=[];
					for(var i=0;i<files.length;i++){
						fileIds.push(files[i].id);
					}
					alert(fileIds.join(','));
				}
			});
		}
	</script>
</body>
</html>
