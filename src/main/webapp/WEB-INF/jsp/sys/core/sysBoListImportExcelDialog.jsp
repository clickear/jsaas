
<%-- 
    Document   : 系统自定义业务EXCEL导入页
    Created on : 2017-05-21 12:11:18
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>表单导出Excel</title>
<%@include file="/commons/edit.jsp"%>
<script src="${ctxPath}/scripts/jquery/plugins/jquery.form.js" type="text/javascript"></script>

</head>
<body>

	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom">
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;" id="toolbarBody">
	            	<a class="mini-button" iconCls="icon-down" plain="true" onclick="downTemp">下载上传Excel模板</a>	            
	            	<a class="mini-button" iconCls="icon-upload" plain="true" onclick="onUpload">上传</a>
	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a>
	            </td>
	        </tr>
	    </table>
    </div>  	
   	<form name="hiform" id="hiform" action="${ctxPath}/sys/core/sysBoList/${boKey}/importExcel.do" method="post" enctype="multipart/form-data">
		<table class="table-detail column_2" style="width:100%;">
    	<tr>
   			<th>上传excel文件</th>
   			<td>
   				<input id="importExcel" type="file" name="importExcel"/>
   			</td>
   		</tr>
   		 </table>
		<input id="importboKey" type="hidden" name="boKey">
	</form>
	<form name="downform" id="downform" style="display:none;" action="${ctxPath}/sys/core/sysBoList/downTemp.do" method="post" >
		<input id="downboKey" type="hidden" name="boKey">
	</form>

	<script type="text/javascript">
	    mini.parse();
	    var headerColumns=$('#headerColumns').val(); 		    
	    
        function onUpload(){
        	$("#importboKey").val('${boKey}');
		    //document.getElementById("hiform").submit();
 
            $("#hiform").ajaxSubmit(function(data){
                alert("成功上传"+data+"条数据！"); 
            }); 
        }
        
        function downTemp(){
        	$("#downboKey").val('${boKey}');
	        document.getElementById("downform").submit();
        }
	</script>
</body>
</html>