<%@page pageEncoding="UTF-8" %>
<%
	//String maxSize=request.getParameter("maxSize");
	String fileExt=request.getParameter("fileExt");
    String recordId=request.getParameter("recordId");
    String entityName=request.getParameter("entityName");
        
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<title>文件上传</title>
<!-- production -->
<%@include file="/commons/dynamic.jspf"%>
<link rel="stylesheet" href="${ctxPath}/scripts/jquery/ui/css/cupertino/jquery-ui-1.10.4.custom.min.css" type="text/css" />
<link rel="stylesheet" href="${ctxPath}/scripts/upload/jquery.ui.plupload/css/jquery.ui.plupload.css" type="text/css" />

<script src="${ctxPath}/scripts/jquery//ui/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/js/jquery-ui-1.10.4.custom.js"></script>

<!-- production -->
<script type="text/javascript" src="${ctxPath}/scripts/upload/plupload.full.min.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/upload/jquery.ui.plupload/jquery.ui.plupload.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/upload/i18n/zh_CN.js"></script>
<link href="${ctxPath}/scripts/miniui/themes/default/miniui.css" rel="stylesheet" type="text/css"/>
<link href="${ctxPath}/scripts/miniui/themes/icons.css" rel="stylesheet" type="text/css" />
 <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
</head>
<body style="font: 13px Verdana; background: #eee; color: #333">
<div id="uploader">
    <p>您的浏览器不支持Flash或Silverlight或HTML5.</p>
</div>
<div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;border:solid 1px #ccc;border-top: none;" borderStyle="border:0;">
	<table style="width:100%;">
		<tr>
			<td style="text-align:center;">
				<a class="mini-button" href="javascript:void(0)" onclick="onOk()"><span class="mini-button-icon-text mini-button-icon-text  icon-ok" style="">确定</span></a>
			    <span style="display:inline-block;width:25px;"></span>
			    <a class="mini-button" href="javascript:void(0)" onclick="onCancel()"><span class="mini-button-text  mini-button-icon-text  icon-close" style="">取消</span></a>
		    </td>
	    </tr>
    </table>
</div>
<script type="text/javascript">
// Initialize the widget when the DOM is ready
var entityName=null;
var recordId=null;
var serverFile;
var files =[];
function getFiles(){
    //var files = $('#uploader').plupload('getFiles');  // 取得上传队列
    //console.log(serverFile);
    return files;
}
function CloseWindow(action) {
    if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
    else window.close();
}

function onOk() {
    CloseWindow("ok");

}
function onCancel() {
    CloseWindow("cancel");
}

var filelist=[];
$(function() {
   $("#uploader").plupload({
        runtimes : 'html5,flash,silverlight,html4',
        url : '${ctxPath}/sys/core/file/upload.do',
        <c:if test="${types!='Image'}">
        max_file_size : '100mb',
        chunk_size: '100mb',
        </c:if>
        /*
        resize : {
            width : 200,
            height : 260,
            quality : 90,
            crop: true // crop to exact dimensions
        },*/
        filters : {
        			mime_types : [
                    <c:forEach var="item" items="${typeMap}" varStatus="i">                    	
                        <c:if test="${i.count > 1}">,</c:if>                        
                        {title : "${item.key}", extensions : "${item.value}"}
                    </c:forEach>
                    ]
        },
        autostart : true,
        rename: true,
        sortable: true,
        dragdrop: true,
        <c:if test="${onlyOne =='true'}"> 
        multi_selection:false,
        </c:if>
        views: {
            list: true//,
            //thumbs: true, // Show thumbs
            //active: 'thumbs'
        },
        flash_swf_url : '${ctxPath}/scritps/plupload/scripts/Moxie.swf',
        silverlight_xap_url : '${ctxPath}/scripts/plupload/scripts/Moxie.xap',
        multipart_params:{
            entityName:'<%=entityName%>',
            recordId:'<%=recordId%>',
            from:'${from}',
            types:'${types}'
        },
        init:{
        	/*FilesAdded: function(up, files) {
    		},
    		FilesRemoved: function(up, files) {
            },*/
            FileUploaded: function(up, file, info) {
            	serverFile=$.parseJSON(info.response).data[0];
            	files = files.concat(serverFile);
            	//file.id=serverFile.fileId;
            	//file.path=serverFile.path;
            },
            <c:if test="${onlyOne =='true'}">            
            FilesAdded:function(up, files) {
            	if (up.files.length > 1) {
                	alert("只能上传一个文件");
                	up.removeFile(up.files[0]);
                	}
                }
            </c:if>

        }
    });
   
});

</script>
</body>
</html>
