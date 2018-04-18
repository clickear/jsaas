
<%-- 
    Document   : 系统自定义业务PDF导出模板
    Created on : 2017-05-21 12:11:18
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>表单导出PDF模板</title>
<%@include file="/commons/edit.jsp"%>
<script type="text/javascript" src="${ctxPath }/scripts/ueditor/ueditor-pdf.config.js"></script>
<script type="text/javascript" src="${ctxPath }/scripts/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="${ctxPath }/scripts/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">

</script>
</head>
<body >

	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom">
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;" id="toolbarBody">
	            	<a class="mini-button" iconCls="icon-prev" plain="true" onclick="reload">重新选择模板</a>
	            	<a class="mini-button" iconCls="icon-save" plain="true" onclick="onSave">保存</a>
	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a>
	                
	            </td>
	        </tr>
	    </table>
    </div>
	<div>
		<script id="dataHtml" type="text/plain">${dataHtml}</script>
	</div>


	<script type="text/javascript">
	    mini.parse();
    	var viewId = "${viewId}";
    	var ueditor ;
    	
    	$(function(){
    		ueditor = UE.getEditor('dataHtml');
    		
    		ueditor.addListener( 'ready', function( editor ) {
    			var height=$(window).height();
        		ueditor.setHeight(height-130);
    		 } );
    		
    	})
    	
    	
    		
        function onSave(){
        	var html = ueditor.getContent();

        	_SubmitJson({
				url:__rootPath+'/bpm/form/bpmFormView/savePDFTemp.do',
				method:'POST',
				data:{
					pdfTemp:html,
					viewId:viewId
				},
				success:function(text){
					CloseWindow();
				}
			});
        }
        
        function reload(){
        	var url=__rootPath + "/bpm/form/bpmFormView/genPdfTemplate.do?reload=yes&viewId="+viewId;
        	_OpenWindow({
        		url: url,
                title: "pdf导出表单", width: "100%", height:"100%",
                ondestroy: function(action) {
                	CloseWindow('ok');
                }
        	});
        }
       
	</script>
</body>
</html>