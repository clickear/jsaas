<%@page pageEncoding="UTF-8" %>
<%
	//String maxSize=request.getParameter("maxSize");
	String fileExt=request.getParameter("fileExt");
    String recordId=request.getParameter("recordId");
    String entityName=request.getParameter("entityName");
%>
<!DOCTYPE html>
<html>
<head>
<title>文件上传</title>
	<%@include file="/commons/edit.jsp" %>
	 
	<script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/js/jquery-ui-1.10.4.custom.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctxPath}/scripts/webUploader/webuploader.css">
	<script type="text/javascript" src="${ctxPath}/scripts/webUploader/webuploader.nolog.js"></script>
</head>
<body >
	<div id="layout1" class="mini-layout" style="width:100%;height:100%; border:none"  >
	    <div title="south" region="south" bodyStyle="text-align:center;padding-top:5px;" showSplit="false" showHeader="false" height="40" showSplitIcon="false" >
			    <a class="mini-button" iconCls="icon-ok" onclick="onOk">确定</a>
			    <a class="mini-button" iconCls="icon-close" onclick="onCancel">取消</a>
	    </div>
    	<div title="center" region="center"  >
      		<div id="uploader" class="Select-upload"  >
			    <div id="thelist" class="uploader-list">
				    <div class="btnss" style="float: left;">
				        <div id="picker" title="添加文件">╋</div>
				    </div>
			    </div> 
			</div>
    	</div>
</div>

<script type="text/javascript">

var single=${onlyOne =='true'};

mini.parse();

var uploader;
var serverFile;
var files =[];
var json = '${config}';
var configJson = mini.decode(json);
var uploadEnded=true;

function getFiles(){
    return files;
}

function onOk() {
	if($("[data-myfiletype]").length>0){
		mini.showTips({
            content: "<b>提醒</b> <br/>有文件正在上传",
            state:'danger',
            x: 'center',
            y: 'center',
            timeout: 3000
        });
	}else{
		CloseWindow("ok");	
	}
}
function onCancel() {
    CloseWindow("cancel");
}

$(function(){
    //下面是文件上传的js操作
    var $ = jQuery,
    $list = $('#thelist'),
    state = 'pending';
    var json = '${config}';
    var configJson = mini.decode(json);
    
    var from="<c:out value='${from}' />";
    var types="<c:out value='${types}' />";
    
    
    var config={
        // 不压缩image
        resize: false,
        //自动上传
        auto: true,
        // swf文件路径
        swf:  __rootPath+'/scripts/webUploader/Uploader.swf',
        // 文件接收服务端。
        server: __rootPath+'/sys/core/file/upload.do',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick:{
            id:'#picker',
            multiple :true
        },
        formData: {
            belongId:$('#belongId').val(),
            fileSource:$('#fileSource').val(),
            from:from,
            types:types
        },
        fileSingleSizeLimit: configJson.sizelimit*1024*1024,
        
        accept: {
            title: '上传限制',
            extensions: '${typeMap}',
            mimeTypes: ''
        }
    }
    
    if(single){
    	config.fileNumLimit=1;
    }
   
    uploader = WebUploader.create(config);
    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
    	//检查是否有问题
    	if(file.name.indexOf(",")>0){
    		alert("文件名包含非法符号逗号“,”，请修改后重新上传。");
    		up.removeFile(files[0]);
    	}
        var shortName=file.name.length>7?file.name.substring(0,8)+"…":file.name;
        var myfileType;
        var typeArray=file.name.split("\.");
        var thisFileType=typeArray[typeArray.length-1];
        if(thisFileType=="doc"||thisFileType=="docx"){
        	myfileType="word";
        }else if(thisFileType=="ppt"||thisFileType=="pptx"){
        	myfileType="ppt";
        }else if(thisFileType=="ppt"||thisFileType=="pptx"){
        	myfileType="excel";
        }else if(thisFileType=="png"||thisFileType=="jpeg"||thisFileType=="jpg"||thisFileType=="bmp"||thisFileType=="gif"){
        	myfileType="rtf";
        }else{
        	myfileType="file1";
        }
        
        $list.prepend('<div style="float:left;" id="contain' + file.id + '" >' +
        				'<a  href="javascript:void(0);" hidefocus="true">' +
        				'<img id="' + file.id + '"data-myfiletype="'+ myfileType+'"  src="${ctxPath}/styles/filetype/loading.gif" style="display:block;border-radius:5px">' +
        				'<img id="delete_'+file.id+'" src="${ctxPath}/styles/filetype/deleteFile.png" title="点击删除" style="margin-top:-70px;display:none;">' +
        				'<p style="margin-top:-7px;text-align: center;" title="'+file.name+'">' + shortName + '</p>'+
        				'</a></div>');
        
        
        $('#delete_'+ file.id).on('click',  function() {
            if($(this).attr("fileId")!=''){   
            	files.splice($.inArray($(this).attr("fileId"),files),1);
            }
            mini.confirm("确定删除此项？", "",
    	            function (action) {
    	                if (action == "ok") {
    	                	$('#contain'+file.id).remove();
    	                     uploader.removeFile(file);
    	                }
    	            });
           
        });
        $("#"+ file.id).mouseenter(function(){
        	$('#delete_'+ file.id).fadeIn();
        	$('#delete_'+ file.id).css("display","block");
        });
        $('#delete_'+ file.id).mouseleave(function(){
        	$('#delete_'+ file.id).fadeOut();
        });
    });

    
    uploader.on( 'uploadSuccess', function( file,response ) {
    	var uploadFile = response.data[0];
    	files = files.concat(uploadFile);
        $('#delete_'+ file.id).attr("fileId",uploadFile.fileId);
    });
    
    uploader.on( 'uploadError', function( file,reason ) {});
    
    uploader.on("error",function (type){
    	if(type == "F_DUPLICATE"){
    		 mini.alert("请不要重复选择文件！");
       }else if(type == "F_EXCEED_SIZE"){
    	   mini.alert("记录已删除！");("附件大小超出限制("+configJson.sizelimit+"MB)!");
       }else if(type == "Q_EXCEED_NUM_LIMIT"){
    	   mini.alert("只允许上传一个附件");
       }else if(type == "Q_TYPE_DENIED"){
    	   mini.alert("上传文件类型出错，请联系管理员!目前允许的上传类型为:<br/>${typeMap}");
       }
    });
    
 	// 文件上传过程中创建进度条实时显示。
     uploader.on( 'uploadProgress', function( file, percentage ) {
        var $EL = $( '#'+file.id );
        var fileImg=$EL.data("myfiletype");
        $EL.css("background-color","rgba(255,64,64,"+(1-percentage)+")");
    }); 
 	
 	/*单文件上传完成之后修改图标*/
 	uploader.on('uploadComplete',function (file){
		var $EL = $('#' + file.id);
		var fileImg = $EL.data("myfiletype");
		$EL.attr("src", "${ctxPath}/styles/filetype/" + fileImg + ".png");
		$EL.removeAttr("data-myfiletype");
		});

		uploader.on('all', function(type) {
			if (type === 'startUpload') {
				state = 'uploading';
			} else if (type === 'stopUpload') {
				state = 'paused';
			} else if (type === 'uploadFinished') {
				state = 'done';
			}

		});
	});
</script>
</body>
</html>
