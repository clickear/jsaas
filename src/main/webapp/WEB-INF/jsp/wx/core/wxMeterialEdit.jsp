
<%-- 
    Document   : [微信素材]编辑页
    Created on : 2017-07-11 16:03:13
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>素材编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div class="mini-toolbar topBtn">
		<a class="mini-button" iconCls="icon-ok" id="confirmButton" onclick="submitMeterial();">确认</a>
		<a class="mini-button" iconCls="icon-cancel" onclick="CloseWindow('cancel');">取消</a>
	</div>
	
	<div class="shadowBox90" style="padding-top: 8px;">
		<div id="p1" class="form-outer">
			<form id="form1" method="post">
				<div class="form-inner">
					<input id="id" name="id" class="mini-hidden" value="${wxMeterial.id}" />
					<input id="pubId" name="pubId" class="mini-hidden" value="${wxMeterial.pubId}" />
					<input id="mediaId" name="mediaId" class="mini-hidden" value="${wxMeterial.mediaId}" />
					<table class="table-detail column_1" cellspacing="1" cellpadding="0">
						<tr>
							<th>期限类型</th>
							<td>
								<div id="termType" name="termType"  class="mini-radiobuttonlist"  textField="text" valueField="id" value="${wxMeterial.termType}" data="[{'text':'永久','id':'1'},{'text':'临时','id':'0'}]" onvaluechanged="termChange"></div>
							</td>
						</tr>
						<tr>
							<th>素材类型</th>
							<td>
							<div  id="mediaType" name="mediaType"  class="mini-radiobuttonlist"  textField="text" valueField="id" value="${wxMeterial.mediaType}" data="[{'text':'图片','id':'image'},{'text':'语音','id':'voice'},{'text':'视频','id':'video'},{'text':'缩略图','id':'thumb'},{'text':'图文消息','id':'article'}]" onvaluechanged="mediaChange"></div>	
							</td>
						</tr>
						<tr>
							<th>素  材  名</th>
							<td colspan="3">
									<input name="name" value="${wxMeterial.name}" class="mini-textbox"   style="width: 60%" />
							</td>
						</tr>
						<tr id="uploadMeterial" style="display: none;">
						<th>上传文件</th>
						<td colspan="4">
						<input id="fileUpload" name="fileUpload"  class="mini-hidden"   />
						<input id="fileUploadText" name="fileUploadText"   class="mini-textbox"   vtype="filetype" style="width: 60%" allowInput="false"  onclick="openUpload('fileUpload','fileUploadText')" emptyText="点击上传文件"/>
						</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
		
		<div id="article" class="form-outer" style="display:none;">
			<form  method="post" id="articleConfig">
				<div class="form-inner">
					<table class="table-detail column_2" cellspacing="1" cellpadding="0">
						<tr>
							<th>标　　题</th>
							<td>
								<input id="title" name="title"  class="mini-textbox"   style="width: 60%" />
							</td>
							<th>缩  略  图</th>
							<td>
							<input id="thumb_media_id" name="thumb_media_id" class="mini-hidden"  />
								<input id="thumb_media" name="thumb_media" class="mini-hidden"  />
								<input id="thumb_mediaText" name="thumb_mediaText" class="mini-textbox"  style="width: 60%" allowInput="false" onclick="openUpload('thumb_media','thumb_mediaText')" emptyText="点击上传文件"/>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<ui:UEditor height="300" width="99%" name="content" id="content">${ueditorContent}</ui:UEditor>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
	<rx:formScript formId="form1" baseUrl="wx/core/wxMeterial"
		entityName="com.redxun.wx.core.entity.WxMeterial" />
	<script type="text/javascript">
	addBody();
	var termType=mini.get("termType");
	var mediaType=mini.get("mediaType");
	var fileUpload= mini.get("fileUpload");//普通素材文件
	var fileUploadText= mini.get("fileUploadText");//普通素材文件
	var thumb_media=mini.get("thumb_media");//图文素材封面
	var thumb_mediaText=mini.get("thumb_mediaText");
	var title=mini.get("title");//图文素材标题
	var fileTypeMap="";
	
	$(function(){
		if(${!empty wxMeterial.id}){//如果是点编辑进来则初始化原数据
			init();
		}
	});
	
	//初始化
	function init(){
		var artConfig=mini.decode('${wxMeterial.artConfig}');
		if('${wxMeterial.mediaType}'=='article'){//如果是图文消息则把图文消息配置显示出来
			$("#article").show();
			thumb_media.setValue(artConfig.thumb_media);
			thumb_mediaText.setValue(artConfig.thumb_mediaText);
			title.setValue(artConfig.articles[0].title);
		}else{
			$("#uploadMeterial").show();
			fileUpload.setValue(artConfig.fileUpload);
			fileUploadText.setValue(artConfig.fileUploadText);
		}
		if("${wxMeterial.termType}"=="0"){//如果是临时消息则把类型选项的图文选项屏蔽
			mediaType.removeItem(mediaType.getAt(4));
			mediaType.setValue(null);
			mediaType.setValue("${wxMeterial.mediaType}");
		}else{
			mediaType.setValue(null);
			mediaType.setValue("${wxMeterial.mediaType}");
		}		
	}
	
	//临时和永久切换
	function termChange(e){
		var mediaTypeOriginValue = mediaType.getValue();
			if (e.value == '0') {
				mediaType.removeItem(mediaType.getAt(4));
				if (mediaTypeOriginValue != "article") {//控件在增删项目后显示有问题,所以先设置个null再把原值设置回去
					mediaType.setValue(null);
					mediaType.setValue(mediaTypeOriginValue);
				}
				$("#article").hide();
			} else if (mediaType.getCount() == 4) {
				mediaType.addItem({'text' : '图文消息','id' : 'article'});
				if (mediaTypeOriginValue != "article") {//控件在增删项目后显示有问题,所以先设置个null再把原值设置回去
					mediaType.setValue(null);
					mediaType.setValue(mediaTypeOriginValue);
				}
			}
		}

	//类型切换
		function mediaChange(e) {
			if (e.value == 'article') {
				fileTypeMap="Image";
				$("#article").show();
				$("#uploadMeterial").hide();
			} else {
				$("#article").hide();
				$("#uploadMeterial").show();
				
				if("image"==e.value){
					fileTypeMap="Image";
				}else if("voice"==e.value){
					fileTypeMap="Voice";
				}else if("video"==e.value){
					fileTypeMap="Video";
				}else if("thumb"==e.value){
					fileTypeMap="Thumb";
				}
				fileUploadText.validate();//重新验证
			}
		}
	
		
	function submitMeterial(){
		var jsonMain=_GetFormJson("form1");
		var jsonSub=_GetFormJson("articleConfig");
		var dataObj=$.extend(jsonMain,jsonSub);
		var messageid =mini.loading("上传中请稍后", "...");
			$.ajax({
			type:"post",
			url:"${ctxPath}/wx/core/wxMeterial/submitMeterial.do",
			data:dataObj,
			success:function (result){
				if(result.success){
					CloseWindow('ok');
				}else{
					mini.showTips({
			            content: "<b>失败</b> <br/>素材上传失败",
			            state: 'danger',
			            x: 'center',
			            y: 'center',
			            timeout: 3000
			        });
				}
				mini.hideMessageBox(messageid);
			}
		});
	}
	/*上传文件按钮*/
	function openUpload(EL,ELtext){
		var uploadElement=mini.get(EL);
		var uploadElementText=mini.get(ELtext);
		_UploadFileDlg({
			from:'SELF',
			types:fileTypeMap,
			single:true,
			onlyOne:true,
			showMgr:false,
			callback:function(files){
				uploadElement.setValue(files[0].fileId);
				uploadElementText.setValue(files[0].fileName);
			}
		});
	}
	 
	 /*自定义文件类型验证vtype*/
    mini.VTypes["filetype"] = function (v) {
    	var mType=mediaType.getValue();//当时所选择的文件类型image,voice,video,thumb,article
    	var suffix=v.split(".")[1];//所上传的文件后缀
    	if(suffix==undefined){
    		return true;
    	}
    	if(mType=="image"){//*.bmp;*.png;*.jpeg;*.jpg;*.gif
    		if(suffix=="bmp"||suffix=="png"||suffix=="jpeg"||suffix=="jpg"||suffix=="gif"){
    			return true;
    		}
    		 mini.VTypes["filetypeErrorText"] = "请上传*.bmp;*.png;*.jpeg;*.jpg;*.gif类型的文件";
    	}else if(mType=="voice"){//*.mp3;*.wma;*.wav;*.amr
    		if(suffix=="mp3"||suffix=="wma"||suffix=="wav"||suffix=="amr"){
    			return true;
    		}
    		mini.VTypes["filetypeErrorText"] = "请上传*.mp3;*.wma;*.wav;*.amr类型的文件";
    	}else if(mType=="video"){//*mp4
    		if(suffix=="mp4"){
    			return true;	
    		}
    		mini.VTypes["filetypeErrorText"] = "请上传*.mp4类型的文件";
    	}else if(mType=="thumb"){//*.jpg
    		if(suffix=="jpg"){
    			return true;
    		}
    		mini.VTypes["filetypeErrorText"] = "请上传*.jpg类型的文件";
    	}else if(mType=="article"){//*
    		return true;
    		}
    	 return false;
    	}
       
	
	</script>
</body>
</html>