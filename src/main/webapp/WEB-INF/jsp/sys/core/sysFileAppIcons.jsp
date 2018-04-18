<%-- 
	//功能：展示应用的图标，以供选择
	//作者：csx
	//创建日期:2015-05-06
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/get.jsp"%>
<link href="${ctxPath}/styles/file-list.css" rel="stylesheet" type="text/css" />
<style type="text/css">
html,body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
<title>应用图标</title>
</head>
<body>

	<ul id="contextMenu" class="mini-contextmenu">
		<li iconCls="icon-remove" onclick="onItemRemove">删除</li>
	</ul>
	<div class="mini-toolbar" style="border-bottom: 0; padding-top: 5px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;">
					<a class="mini-button" iconCls="icon-upload" plain="true" onclick="upload()">上传</a> 
					<a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refresh()">刷新</a> 
					<a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeIcons()">删除</a> 
					<!-- a class="mini-button" iconCls="icon-select" plain="true" onclick="checkAll()">全选择</a-->
					<a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow('cancel');">关闭</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit" style="border: solid 1px #909aa6">
		<div id="filesDataView" style="width: 100%; height: 100%; overflow: auto;">
			
			<div class="mini-pager" style="width:100%;background:#ccc;" 
			    totalCount="${page.totalItems}" pageIndex="${page.pageIndex}" pageSize="${page.pageSize}" onpagechanged="onPageChanged" sizeList="[100,200,300]">        
			</div>
			
			<div id="filesView" class="filesView">
				<c:forEach var="file" items="${imageFiles}">
					<div class="icon-block">
						<div class="icon-outer">
							<img src="${ctxPath}/${file.path}" alt="${file.fileName}" onclick="selectIcon(this)" class="icon"/>
							<input type="radio" name="iconFile" value="${file.path}" fileName="${file.newFname}" id="${file.fileId}" alt="" style="visibility:hidden"/>
						</div>
						<!-- input type="checkbox" name="ckIconFile" value="${file.fileId}" class="mini-checkbox"/> -->
					</div>
				</c:forEach>
				
			</div>
		</div>
	</div>
	<script type="text/javascript">
   	    mini.parse();
   	    
   	 	function onPageChanged(e){
   	 	 	//alert(e.pageIndex+":"+e.pageSize);
   	 	 	window.location='${ctxPath}/sys/core/sysFile/appIcons.do?pageIndex='+e.pageIndex +"&pageSize="+e.pageSize;
   	 	}
   	    
   	    //当前选择图片
   	   	var curDiv=null;
		window.onload = function () {
		   $(".icon-block").bind("contextmenu", function (e) {
			   curDiv=$(e.target);
		       var menu = mini.get("contextMenu");
		       menu.showAtPos(e.pageX, e.pageY);
		       return false;
		   });
		};
   	 
	   function ShowImageUploadDialog(config){
		    mini.open({
		        allowResize: true, //允许尺寸调节
		        allowDrag: true, //允许拖拽位置
		        showCloseButton: true, //显示关闭按钮
		        showMaxButton: true, //显示最大化按钮
		        showModal: true,
		        //只允许上传图片，具体的图片格式配置在config/fileTypeConfig.xml
		        url: "${ctxPath}/sys/core/sysFile/dialog.do?from=APPLICATION&types=Icon",
		        title: "图标上传", width: 600, height: 420,
		        onload: function() {
					
		        },
		        ondestroy: function(action) {
		            if (action == 'ok') {
		                var iframe = this.getIFrameEl();
		                var files = iframe.contentWindow.getFiles();
		                if(config.callback){
		                    config.callback.call(this,files);
		                }
		            }
		        }
		    });
		}
	   
	   function checkAll(){
		   $("input[name='ckIconFile']").attr('checked',true);
	   }
	    //删除图标
	   	function removeIcons(){
	    	var fileIds=[];
	   		$("input[name='ckIconFile']:checked").each(function(){
	   			fileIds.push($(this).val());
	   		});
	   		$.ajax({
                url: "${ctxPath}/sys/core/sysFile/delIconFiles.do",
                type:'POST',
                data:{fileIds:fileIds.join(',')},
                success: function (text) {
                	window.location.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
	   	}
	   
	   	//上传
	   	function upload(){
	   		ShowImageUploadDialog();
	   	}
	   	//更新
	   	function refresh(){
	   		window.location.reload();
	   	}
	   	//选择图标
	   	function selectIcon(icon){
	   		$(icon).siblings("input[name='iconFile']").attr('checked',true);
	   		CloseWindow('ok');
	   	}
	   
	   	//选择文件
	   	function getIcons(){
	   		var icons=[];
	   		$("input[name='iconFile']:checked").each(function(){
	   				var ic=$(this);
	   				var icon={
	   					fileId:ic.val(),
	   					fileName:ic.attr('fileName')
	   				};
	   				icons.push(icon);
	   			}
	   		);
	   		return icons;
	   	}
	   	
	   	//删除图片
	   	function onItemRemove(e){
	   		var fileId=curDiv.parent().children('input[name="iconFile"]').attr('id');
	   		
	   		$.ajax({
                url: "${ctxPath}/sys/core/sysFile/delIconFiles.do?fileId="+fileId,
                success: function (text) {
                	window.location.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
	   	}

	   	function setData(config){
	   		
	   	}
   </script>
</body>
</html>