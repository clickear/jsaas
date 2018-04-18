<%--
	//图片上传对话框
--%>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<title>图片上传对话框</title>
<%@include file="/commons/dynamic.jspf"%>
<!-- production -->
<link rel="stylesheet" href="${ctxPath}/scripts/jquery/ui/css/cupertino/jquery-ui-1.10.4.custom.min.css" type="text/css" />
<link rel="stylesheet" href="${ctxPath}/scripts/upload/jquery.ui.plupload/css/jquery.ui.plupload.css" type="text/css" />
<link href="${ctxPath}/scripts/miniui/themes/default/miniui.css" rel="stylesheet" type="text/css"/>
<link href="${ctxPath}/scripts/miniui/themes/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/jquery//ui/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/js/jquery-ui-1.10.4.custom.js"></script>

<!-- production -->
<script type="text/javascript" src="${ctxPath}/scripts/upload/plupload.full.min.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/upload/jquery.ui.plupload/jquery.ui.plupload.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/upload/i18n/zh_CN.js"></script>


</head>
<body style="font: 13px Verdana; background: #eee; color: #333">
<div id="uploader">
    <p>您的浏览器不支持Flash或Silverlight或HTML5.</p>
</div>
<div class="mini-toolbar iconfont" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
    <a class="mini-button icon-ok" style="width:60px;" onclick="onOk()" ><span class="mini-button">确定</span></a>
    <span style="display:inline-block;width:25px;"></span>
    <a class="mini-button icon-cancel" style="width:60px;" onclick="onCancel()"><span class="mini-button">取消</span></a>
</div>
<script type="text/javascript">
// Initialize the widget when the DOM is ready




function getFiles(){
    var files = $('#uploader').plupload('getFiles');  // 取得上传队列
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
var uploader=null;
var filelist=[];
$(function() {
   uploader=$("#uploader").plupload({
        runtimes : 'html5,flash,silverlight,html4',
        url : '${ctxPath}/pub/anony/upload.do',
        max_file_size : '5mb',
        chunk_size: '1mb',
        max_file_count:1,
        /*
        resize : {
            width : 200,
            height : 260,
            quality : 90,
            crop: true // crop to exact dimensions
        },*/
        filters : [
                   {title:"Image",extensions:"png,jpg,jpeg,bmp,gif"}
        ],
        rename: true,
        sortable: true,
        dragdrop: true,
        views: {
            list: false//,
            //thumbs: true, // Show thumbs
            //active: 'thumbs'
        },
        flash_swf_url : '${ctxPath}/scritps/plupload/scripts/Moxie.swf',
        silverlight_xap_url : '${ctxPath}/scripts/plupload/scripts/Moxie.xap',
        multipart_params:{
            from:'SELF',
            types:'Image',
            anonymus:'true'
        },
        init:{
        	/*FilesAdded: function(up, files) {
    		},
    		FilesRemoved: function(up, files) {
            },*/
            FileUploaded: function(up, file, info) {
            	var serverFile=$.parseJSON(info.response).data[0];
            	file.id=serverFile.fileId;
            	file.path=serverFile.path;
            }
        }
    });
});

</script>
</body>
</html>
