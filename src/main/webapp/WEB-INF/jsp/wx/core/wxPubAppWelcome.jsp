
<%-- 
    Document   : [公众号菜单]编辑页
    Created on : 2017-06-29 16:57:29
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>关注回复配置</title>
<%@include file="/commons/edit.jsp"%>

</head>

<body>
<div class="mini-toolbar" >
    <a class="mini-button" iconCls="icon-save" plain="true" onclick="saveConfig()">保存</a>
    <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="CloseWindow('cancel')">关闭</a>
     </div>
<div class="mini-fit">
<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
      <div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
							<table class="table-detail column_1" cellspacing="1" cellpadding="0">
								<tr class="replyType" >
									<th>回复类型</th>
									<td><input id="replyType" name="replyType" class="mini-combobox" style="width: 150px;" textField="text" valueField="id"
										data="[{'text':'文本','id':'text'},{'text':'图片','id':'image'},{'text':'语音','id':'voice'},{'text':'视频','id':'video'},{'text':'音乐','id':'music'},{'text':'图文','id':'article'}]"
										showNullItem="false" allowInput="false" onvaluechanged="changeReplyType" /></td>
								</tr>
								
								<tr class="text" style="display: none;">
									<th>文本信息</th>
									<td>
									<textarea name="replyText" id="replyText" class="mini-textarea" emptyText="请输入文本" style="width: 400px;"></textarea>
									</td>
								</tr>
								
								<tr class="music" style="display: none;">
									<th>音　乐</th>
									<td>
									<input class="mini-textbox" name="replyMusic" id="replyMusic" emptyText="请输入音乐外链url" style="width: 400px;"/>
									</td>
								</tr>
								

							<tr class="meterial" style="display: none;">
								<th>选择素材</th>
								<td>
								<input class="mini-hidden"  name="meterialText"  id="meterialText"/>
								<input id="meterial"  class="mini-textboxlist" name="meterial" textName="meterialText" style="width: 80%;"
									allowInput="false" valueField="id" textField="text" required='true' /> <a class="mini-button" id="addMeterialButton"
									iconCls="icon-add" plain="true" onclick="addMeterial">选择素材</a></td>
							</tr>

						</table>
						</div>
		</form>
	</div>
    </div>
   
</div>
	
<script type="text/javascript">
mini.parse();
var replyType=mini.get("replyType");
var replyText=mini.get("replyText");
var replyMusic=mini.get("replyMusic");
var meterial=mini.get("meterial");
var meterialText=mini.get("meterialText");

var form =new mini.Form("form1");


initData();
function addMeterial(){
	var replyTypeValue=replyType.getValue();
	if("music"==replyTypeValue){
		replyTypeValue="thumb";
	}
		_OpenWindow({
			url : '${ctxPath}/wx/core/wxMeterial/Select.do?pubId=${wxPubApp.id}&type='+replyTypeValue,
			title : "添加素材",
			width : 600,
			height : 400,
			onload : function() {
			},
			ondestroy : function(action) {
				 if (action == 'ok'){
					 var iframe = this.getIFrameEl().contentWindow;
					 var meterialValues=iframe.getMeterialValue();
					 var meterialTexts=iframe.getMeterialText();
					 meterial.setValue(""+meterialValues);
					 meterial.setText(meterialTexts);
					 meterialText.setValue(meterialTexts);
			               }
			}

		});	
}

function changeReplyType(){
	var value=replyType.getValue();
	meterial.setValue("");
	meterialText.setValue("");
	replyText.setValue("");
	replyMusic.setValue("");
	if(value=='image'||value=='voice'||value=='video'||value=='article'){//选择了素材类型
		$('.meterial').show();
		$('.text').hide();
		$('.music').hide();
	}else if(value=="text"){
		$('.meterial').hide();
		$('.text').show();
		$('.music').hide();
	}else if(value=="music"){
		$('.meterial').show();
		$('.text').hide();
		$('.music').show();
	}
}


function saveConfig(){
	 $.ajax({
		url:"${ctxPath}/wx/core/wxPubApp/saveReply.do",
		type:"post",
		data:{form:mini.encode(form.getData()),pubId:'${wxPubApp.id}'},
		success:function (result){
			CloseWindow('ok');
		}
	}); 
}

function initData(){
	var replyConfig=mini.decode('${wxPubApp.otherConfig}');
	var typeOfReply=replyConfig.replyType;
	replyType.setValue(typeOfReply);
	changeReplyType();
	if(typeOfReply=="text"){
		replyText.setValue(replyConfig.replyText);
	}else if(typeOfReply=="music"){
		replyMusic.setValue(replyConfig.replyMusic);
	}else if(typeOfReply=="image"||typeOfReply=="voice"||typeOfReply=="video"||typeOfReply=="article"){
		meterial.setValue(replyConfig.meterial);
		meterial.setText(replyConfig.meterialText);
		meterialText.setValue(replyConfig.meterialText);
	}
}
</script>
</body>
</html>