
<%-- 
    Document   : [公众号关键字回复]编辑页
    Created on : 2017-08-30 11:39:20
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[公众号关键字回复]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="wxKeyWordReply.id" />
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${wxKeyWordReply.id}" />
				<input class="mini-hidden" name="pubId" value="${wxKeyWordReply.pubId}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<tr>
						<th>关键字</th>
						<td>
							<input 
								name="keyWord" 
								value="${wxKeyWordReply.keyWord}"
								class="mini-textbox"   
								style="width: 90%" 
								required="true"
							/>
						</td>
					</tr>
					<tr class="replyType" >
									<th>回复类型</th>
									<td>
										<input 
											id="replyType" 
											name="replyType" 
											class="mini-combobox" 
											style="width: 150px;" 
											textField="text" 
											valueField="id" 
											value="${wxKeyWordReply.replyType}"
											data="[{'text':'文本','id':'text'},{'text':'图片','id':'image'},{'text':'语音','id':'voice'},{'text':'视频','id':'video'},{'text':'音乐','id':'music'},{'text':'图文','id':'article'}]"
											showNullItem="false" 
											allowInput="false" 
											onvaluechanged="changeReplyType"  
											required="true"
										/>
									</td>
								</tr>
								
								<tr class="text" style="display: none;">
									<th>文本信息</th>
									<td>
									<textarea name="replyText" id="replyText" class="mini-textarea" emptyText="请输入文本" style="width: 400px;"></textarea>
									</td>
								</tr>
								
								<tr class="music" style="display: none;">
									<th>音乐</th>
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
	<script type="text/javascript">
	mini.parse();
	var replyType=mini.get("replyType");
	var replyText=mini.get("replyText");
	var replyMusic=mini.get("replyMusic");
	var meterial=mini.get("meterial");
	var meterialText=mini.get("meterialText");
	
	
	var form = new mini.Form("#form1");
	function onOk(){
		form.validate();
	    if (!form.isValid()) {
	        return;
	    }	        
	    var data=form.getData();
		var config={
        	url:"${ctxPath}/wx/core/wxKeyWordReply/save.do",
        	method:'POST',
        	postJson:false,
        	data:data,
        	success:function(result){
        		//如果存在自定义的函数，则回调
        		if(isExitsFunction('successCallback')){
        			successCallback.call(this,result);
        			return;	
        		}
        		
        		CloseWindow('ok');
        	}
        }
	        
		_SubmitJson(config);
	}
	
	initData();
	
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
	
	function initData(){
		var replyConfig=mini.decode('${wxKeyWordReply.replyContent}');
		changeReplyType();
		var typeOfReply=replyType.getValue();
		if(typeOfReply=="text"){
			replyText.setValue(replyConfig.replyText);
		}else if(typeOfReply=="music"){
			replyMusic.setValue(replyConfig.replyMusic);
			meterial.setText(replyConfig.meterialText);
			meterial.setValue(replyConfig.meterial);
		}else if(typeOfReply=="image"||typeOfReply=="voice"||typeOfReply=="video"||typeOfReply=="article"){
			meterial.setValue(replyConfig.meterial);
			meterial.setText(replyConfig.meterialText);
			meterialText.setValue(replyConfig.meterialText);
		}
	}
	
	function addMeterial(){
		var replyTypeValue=replyType.getValue();
		if("music"==replyTypeValue){
			replyTypeValue="thumb";
		}
			_OpenWindow({
				url : '${ctxPath}/wx/core/wxMeterial/Select.do?pubId=${wxKeyWordReply.pubId}&type='+replyTypeValue,
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

	</script>
</body>
</html>