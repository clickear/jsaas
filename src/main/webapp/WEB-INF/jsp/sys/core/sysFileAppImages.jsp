<%-- 
	//功能：展示应用的图片，以供选择
	//作者：csx
	//创建日期:2015-05-06
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/edit.jsp"%>
<link href="${ctxPath}/styles/file-list.css" rel="stylesheet" type="text/css" />
<style type="text/css">
html,body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
/* 	overflow: hidden; */
}
</style>
<title>应用图片</title>
</head>
<body>

	<ul id="contextMenu" class="mini-contextmenu">
		<li iconCls="icon-preview" onclick="onItemOpen">查看</li>
		<li iconCls="icon-remove" onclick="onItemRemove">删除</li>
	</ul>
	
	<div class="mini-toolbar topBtn" >
		<a class="mini-button" iconCls="icon-upload"  onclick="upload()">上传</a> 
		<a class="mini-button" iconCls="icon-refresh"  onclick="refresh()">刷新</a> 
		<a class="mini-button" iconCls="icon-select"  onclick="CloseWindow('ok')">选择</a>
<!-- 		<a class="mini-button" iconCls="icon-close" onclick="CloseWindow('cancel')">关闭</a> -->
	</div>
	<div>
		<div id="filesDataView">
			<ul id="filesView" class="filesView">
				<c:forEach var="file" items="${imageFiles}">
					<li class="file-block p_top">
						<a class="file" href="javascript:void(0)" onclick="clickImgFile(this,${single})" hideFocus title="${file.fileName}"> 
								<c:choose>
									<c:when test="${file.from=='APPLICATION'}">
										<c:choose>
											<c:when test="${not empty file.thumbnail}">
												<img class="file-image" src="${ctxPath}/${file.thumbnail}" />
											</c:when>
											<c:otherwise>
												<img class="file-image" src="${ctxPath}/${file.path}" />
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${not empty file.thumbnail}">
												<span class="imageBox">
													<img class="file-image" src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${file.fileId}" />
												</span>
											</c:when>
											<c:otherwise>
												<span class="imageBox">
													<img class="file-image" src="${ctxPath}/sys/core/file/imageView.do?fileId=${file.fileId}" />
												</span>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
								<span class="file-ext"> ${fn:substring(file.fileName,0,15)} </span>
						</a>
						<c:choose>
							<c:when test="${single==true}">
								<input type="radio" name="imgFile" value="${file.fileId}" fileName="${file.fileName}" filePath="${file.path}" alt="选择该文件"/>
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="imgFile" value="${file.fileId}" fileName="${file.fileName}" filePath="${file.path}" alt="选择该文件"/>
							</c:otherwise>
						</c:choose>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<script type="text/javascript">
   	    mini.parse();
   	    
   	    var from='<c:out value="${from}" />';
   	    
   	    //当前选择图片
   	   	var curDiv=null;
		window.onload = function () {
		   $(".file-block").bind("contextmenu", function (e) {
			   curDiv=$(e.target);
		       var menu = mini.get("contextMenu");
		       menu.showAtPos(e.pageX, e.pageY);
		       return false;
		   });
		};
   	 
	   function ShowImageUploadDialog(config){
		   _UploadDialogShowFile({
				from:'SELF',
				types:'Image',
				single:false,
				showMgr:false,
				callback:function(files){
					config.callback.call(this,files);
				}
			});
		   
		}
	   
	   function clickImgFile(link,single){
		   var rck=$(link).siblings("input[name='imgFile']");
		   rck.attr('checked',true);
		  if(single){
			  CloseWindow('ok');
		  }
	   }
	   	//上传
	   	function upload(){
	   		ShowImageUploadDialog({callback:function(){
	   			window.location.reload();
	   		}});
	   	}
	   	//更新
	   	function refresh(){
	   		window.location.reload();
	   	}
	   	//获得选择的图片
	   	function getImages(){
	   		var imgs=[];
	   		$("input[name='imgFile']:checked").each(function(){
	   		    var ic=$(this);
	   			var img={
	   		    	fileId:ic.val(),
	   		    	fileName:ic.attr('fileName'),
	   		    	filePath:ic.attr('filePath')
	   		    };
	   		 	imgs.push(img);
	   		  });
	   		return imgs;
	   		//return $("input[name='imgFile']:checked").val();	
	   	}
	   	
	   	//获得选择的单个图片
	   	function getImage(){
	   		var imgs=getImages();
	   		if(imgs.length==0) return null;
	   		else return imgs[0];
	   	}
	   	//删除图片
	   	function onItemRemove(e){
	   		var imgFile=curDiv.parent().siblings()[0];
	   		var fileId=imgFile.value;
	   		$.ajax({
                url: "${ctxPath}/sys/core/sysFile/delImageFile.do?fileId="+fileId,
                success: function (text) {
                	window.location.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
	   	}
	   	//打开图片
	   	function onItemOpen(e){
	   		var imgFile=curDiv.parent().siblings()[0];
	   		var fileId=imgFile.value;
	   		mini.open({
		        allowResize: true, //允许尺寸调节
		        allowDrag: true, //允许拖拽位置
		        showCloseButton: true, //显示关闭按钮
		        showMaxButton: true, //显示最大化按钮
		        showModal: true,
		        //只允许上传图片，具体的图片格式配置在config/fileTypeConfig.xml
		        url: "${ctxPath}/sys/core/sysFile/imgPreview.do?fileId="+fileId,
		        title: "图片预览", width: 600, height: 420,
		        onload: function() {
		        },
		        ondestroy: function(action) {
		        }
		    });
	   	}
	   	
	   	addBody();

   </script>
</body>
</html>