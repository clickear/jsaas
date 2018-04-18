
/**
 * 获取表单数据。
 * 数据格式如下:
 * 	{
 * 		result:true,
 * 		data:""
 * 	}
 * 
 * @returns 
 */
function getFormData(formType,formId,needValid){
	var form = new mini.Form("#"+formId); 
	var rtnJson={result:true,data:{}};
	if(formType=="ONLINE-DESIGN"){
		if(needValid){
			form.validate();
			if(!form.isValid()){
				rtnJson.result=false;
				return rtnJson;
			}
			var frm=$("#" + formId);
			var isValid=true;
			
			$('.mini-datagrid',frm).each(function(){
				var name=$(this).attr('id');
				var grid=mini.get(name);
				grid.validate();
				if(!grid.isValid()){
					isValid=false;
					return false;
				}
			});
			if(!isValid){
				rtnJson.result=false;
				return rtnJson;
			}
		}
		var modelJson = _GetFormJsonMini(formId);
		
		
		rtnJson.data=modelJson;
		return rtnJson;
	}else{
		var frameWindow=document.getElementById('formFrame').contentWindow;
		if(needValid){
			if(frameWindow.isValid && (!frameWindow.isValid())){
				rtnJson.result=false;
				return rtnJson;
			}
		}
		if(frameWindow.getData){
			rtnJson.data=frameWindow.getData();
		}
		return rtnJson;
	}
}

function getBoFormDataByType(formType,needValid){
	if(formType=="SEL-DEV"){
		var rtnJson={result:true,data:{}};
		var frameWindow=document.getElementById('formFrame').contentWindow;
		if(needValid){
			if(frameWindow.isValid && (!frameWindow.isValid())){
				rtnJson.result=false;
				return rtnJson;
			}
		}
		if(frameWindow.getData){
			rtnJson.data=frameWindow.getData();
		}
		return rtnJson;
	}else{//formType=="ONLINE-DESIGN"
		return getBoFormData(needValid);
	}
}

function getBoFormData(needValid){
	var forms=$('.form-model');
	var boDatas=[];
	var isValid=true;
	forms.each(function(){
		var formId=$(this).attr('id');
		var boDefId=$(this).attr('boDefId');
		var formKey=$(this).attr('formKey');
		var form=new mini.Form(formId);
		if(needValid){
			form.validate();
			if(!form.isValid()){
				isValid=false;
				return false;
			}
			var frm=$("#" + formId);
			$('.mini-datagrid',frm).each(function(){
				var name=$(this).attr('id');
				if($("#"+name).is(":hidden")) return true;
				var grid=mini.get(name);
				grid.validate();
				if(grid.required&&grid.getData().length<1){
					isValid=false;
					return false;
				}
				if(!grid.isValid()){
					isValid=false;
					return false;
				}
			});
		}
		
		var modelJson = _GetFormJsonMini(formId);
		boDatas.push({
			boDefId:boDefId,
			formKey:formKey,
			data:modelJson});
	});
	
	return {
		data:{bos:boDatas},
		result:isValid
	}
}

/**
 * 表单是否必填。
 * @returns {Boolean}
 */
function hasUserConfig(){
	//获得节点的必需人员配置
	var nodeUserMustConfig=mini.get('nodeUserMustConfig').getValue();
	//获得节点的人员映射
	var nodeUserIds=mini.get('nodeUserIds').getValue();
	if(!nodeUserMustConfig) return true;
	
	if(nodeUserIds=='') return false;
	
	var userConfs=mini.decode(nodeUserIds);
	var nodeIds=nodeUserMustConfig.split(',');
	for(var i=0;i<nodeIds.length;i++){
		for(var j=0;i<userConfs.length;i++){
			//没有配置对应的人员
			if(userConfs[i].nodeId==nodeIds[i] && userConfs[i].userIds==''){
				return false;
			}
		}
	}
	return true;
}


/**
 * 加载用户。
 */
function initUser(conf){
	var aryParams=[];
	for(var key in conf){
		if(conf[key]){
			aryParams.push(key +"=" +conf[key]);
		}
	}
	var tmp=aryParams.join("&");
	$("area[type='userTask']").each(function(){
	 	var nodeId=$(this).attr('id');
		$(this).qtip({
			content: {
                text: function(event, api) {
                    $.ajax({
                        url: __rootPath+"/bpm/core/bpmTask/calUsers.do?nodeId="+nodeId+"&" + tmp
                    })
                    .then(function(content) {
                        api.set('content.text', content);
                    }, function(xhr, status, error) {
                        api.set('content.text', status + ': ' + error);
                    });
                    return '正在加载...'; 
                }
            },
            position: {
                target: 'mouse', // Position it where the click was...
                adjust: { mouse: false } // ...but don't follow the mouse
            }
	    });
 });
}

/**
 * iframe 高度随内容自动变化。
 * @param obj
 */
function autoHeightOnLoad(obj){
	obj.load(function () {
		var body=$(this).contents().find("body");
		body.css("overflow","auto");
	    var mainheight = body.height() + 100;
	    $(this).height(mainheight);
	});
}


function deleteMyOpinion(data){
	mini.confirm("<b>确定要删除此条意见吗</b>", "确定",
        function (action) {
            if (action != "ok")  return;
            
           	$.ajax({
               	url:__rootPath + "/bpm/core/bpmOpinionLib/del.do",
               	type:'POST',
               	async:false,
               	data:{ids: data},
               	success:function (){
               		mini.get('opinionSelect').load(__rootPath+ "/bpm/core/bpmOpinionLib/getUserText.do");
               		mini.showTips({
	        	            content: "<b>删除成功</b>",
	        	            state: 'success',
	        	            x: 'center',
	        	            y: 'center',
	        	            timeout: 3000
	        	        });
               	}
                });
        });
}	

//保存意见
function saveOpinion(){
	var opText = document.getElementById("opinion").value;
	if(opText==null){
		alert("请勿保存空值");
		return ;
	}
	_SubmitJson({
		url:__rootPath+'/bpm/core/bpmOpinionLib/saveOpinion.do',
		data: {
			opText:opText
		},
		method:'POST',
		success:function(result){
			var url=__rootPath + "/bpm/core/bpmOpinionLib/getUserText.do";
			//重新加载意见列表控件
			mini.get('opinionSelect').load(url);
		}
	});
}


//显示评语
function showOpinion(e){
	var opinionSelect = mini.get("opinionSelect");
	var opText = opinionSelect.getText();
	var text = document.getElementById("opinion");
	text.value = opText;
}



$(function(){
	initFieldSet();
	
});

function initFieldSet() {
	$(".fieldsetContainer").each(function() {
		var fieldSetObj = $(this);
		var divObj = $("div.divContainer", fieldSetObj);
		if (divObj.length == 0) {
			divObj = $("<div class='divContainer'></div>");
			fieldSetObj.append(divObj);
		}
		var childAry = fieldSetObj[0].childNodes;
		var childs = [];
		for (var i = 0; i < childAry.length; i++) {
			var child = $(childAry[i]);
			if (child.is("legend")) {
				child.append("<div class='toggleButton'  title='展开/收缩'  >︾</div>");
			} else {
				childs.push(child);
			}
		}
		for (var i = 0; i < childs.length; i++) {
			divObj.append(childs[i]);
		}

	});
	$(".toggleButton").bind("click", function() {
		$(this).parent().next().toggle();
	});
}


/**
 * 初始化表单状态。
 * r 只读
 * h 隐藏
 * var permission={
 * 	add:{
 * 		field1:"r",
 * 		field1:"h"
 * 		GRID_:{
 * 			table1:{
 * 				field1:"h"
 * 			}
 * 		}
 * 	},
 * 	edit{
 * 		field1:"r",
 * 		field1:"h"
 * 	}
 * }
 */
function initFormElementStatus(){
	if(!window.permission) return ;
	//visible
	var id=mini.getByName("ID_").getValue();
	var rights=null;
	//编辑
	if(id){
		rights=getRights("edit");
	}//添加
	else{
		rights=getRights("add");
	}
	if(!rights) return;
	for(var key in rights){
		if(key=="GRID_") continue;
		var ctl=mini.getByName(key);
		var right=rights[key];
		if(right=="h"){
			ctl.setVisible(false);
		}
		else if(right=="r"){
			if(ctl.setEnabled){
				ctl.setEnabled(false);
			}
			if(ctl.setReadOnly){
				ctl.setReadOnly(true);
			}
		}
	}
	//子表
	var subRights=rights["GRID_"];
	if(!subRights) return;
	for(var table in subRights){
		var grid=mini.get("grid_" + table);
		var tableRight=subRights[table];
		for(var field in tableRight){
			var tmp=tableRight[field];
			if(tmp=="h"){
				grid.hideColumn (grid.getColumn ( field ))
			}
		}
	}
}

function getRights(status){
	return 	window.permission[status];
}

/**
 * 获取附件。
 * @param attachments
 * @returns
 */
function getAttachMents(attachments){
	if (!attachments || attachments.length==0) return "";
	var aryFile=[];
	for(var i=0;i<attachments.length;i++){
		var attach= attachments[i];
		var fileId=attach.fileId;
		var type=attach.type;
		var isDown=attach.isDown;
		var isPrint=attach.isPrint;
		var fileName=attach.fileName;
		
		if("xls"==type ||"xlsx"==type){
			aryFile.push("<br><img src='"+__rootPath+"/styles/icons/excel.png' /><a  href='#' onclick='_openDoc(\""+fileId+"\");'><span style='color:#4f50f2;' >"+fileName+"</a></span>");
		}else if("doc"==type||"docx"==type){
			aryFile.push("<br><img src='"+__rootPath+"/styles/icons/doc.png' /><a  href='#' onclick='_openDoc(\""+fileId+"\");'><span style='color:#4f50f2;' >"+fileName+"</span></a>");
		}else if("ppt"==type||"pptx"==type){
			aryFile.push("<br><img src='"+__rootPath+"/styles/icons/ppt.png' /><a  href='#' onclick='_openDoc(\""+fileId+"\");'><span style='color:#4f50f2;' >"+fileName+"</span></a>");
		}else if("pdf"==type){
			aryFile.push("<br><img src='"+__rootPath+"/styles/icons/pdf.png' /><a target='_blank' href='"+__rootPath+"/scripts/PDFShow/web/viewer.html?file="+__rootPath+"/sys/core/file/download/"+fileId+".do'><span style='color:#4f50f2;' >"+fileName+"</span></a>");
		}else if("png"==type||"jpg"==type||"gif"==type||"jpeg"==type||"bmp"==type){
			aryFile.push("<br><img src='"+__rootPath+"/styles/icons/picture.png' /><a target='_blank' href='"+__rootPath+"/sys/core/file/previewOffice.do?fileId="+fileId+"'><span style='color:#4f50f2;' >"+fileName+"</span></a>");
		}else if("txt"==type){
			aryFile.push("<br><img src='"+__rootPath+"/styles/icons/txt.png' /><a target='_blank' href='"+__rootPath+"/sys/core/file/previewOffice.do?fileId="+fileId+"'><span style='color:#4f50f2;' >"+fileName+"</span></a>");
		}
		else{
			aryFile.push("<br><a target='_blank' href='"+__rootPath+"/sys/core/file/download/"+fileId+".do'><span style='color:#4f50f2;' >"+fileName+"</span></a>");
		}
		
		if("true"==isDown){
			aryFile.push("<a target='_blank' href='"+__rootPath+"/sys/core/file/download/"+fileId+".do'><image style='margin-left:15px;cursor:pointer;border:0;' href='"+__rootPath+"/sys/core/file/previewOffice.do?fileId="+fileId+"'  src='"+__rootPath+"/styles/icons/download.png'  /></a>");
		}
	
	}
	return aryFile.join("");
}

/**
 * 对意见表格数据进行处理。
 * @param e
 * @returns
 */
function drawNodeJump(e) {
    var record = e.record,
    field = e.field,
    value = e.value;
  	var ownerId=record.ownerId;
    if(field=='handlerId'){
    	if(ownerId && ownerId!=value){
    		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>&nbsp;代('+ '<a class="mini-user" iconCls="icon-user" userId="'+ownerId+'"></a>)';
    	}else if(value){
    		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
    	}else{
    		e.cellHtml='<span style="color:red">无</span>';
    	}
    } 
    if(field=='remark'){
    	var attachHtml=getAttachMents(record.attachments);
    	e.cellHtml='<span style="line-height:15px;">'+value+ attachHtml+'</span>';
    }
    if(field=='checkStatusText'){
    	e.cellHtml='<span style="line-height:15px;">'+value+'</span>';
    }
    if(field=='nodeName'){
    	e.cellHtml='<span style="line-height:15px;">'+value+'</span>';
    }
}

/**
 * 设置下拉框值。
 * @param alias			自定义查询别名
 * @param params		参数 {param1:val1}
 * @param targetCtl		下拉框控件
 * @param valField		返回值字段
 * @param nameField		返回值名称字段
 * @returns
 */
function setComboxData(alias,params,targetCtl){
	doQuery(alias, params,function(result){
		targetCtl.setData(result.data)
	})
}

/**
 * 启动流程
 * @param uid
 * @returns
 */
function startRow(uid){
	var row=grid.getRowByUID(uid);
//	var type=getBrowserType();
//	if(type==0){
//		_OpenWindow({
//			title:row.name+'-启动流程',
//			url:__rootPath+'/bpm/core/bpmInst/start.do?solId='+row.solId,
//			max:true,
//			height:400,
//			width:800,
//		});	
//	}
//	else{
		var url=__rootPath+'/bpm/core/bpmInst/start.do?solId='+row.solId;
		openNewWindow(url,"startWindow");
//	}
}


/**
 * 审批任务
 * @param uid
 * @returns
 */
function handTask(uid){
	var row=grid.getRowByUID(uid);
//	var type=getBrowserType();
//	if(type==0){
//		 _OpenWindow({
//			title:'审批任务-'+row.description,
//			height:400,
//			width:780,
//			max:true,
//			url:__rootPath+'/bpm/core/bpmTask/toStart.do?taskId='+row.id,
//			ondestroy:function(action){
//				if(action!='ok') return;
//				grid.load();
//			}
//		}); 
//	}
//	else{
		var url=__rootPath+'/bpm/core/bpmTask/toStart.do?taskId='+row.id;
		openNewWindow(url,"handTask");
//	}
}

