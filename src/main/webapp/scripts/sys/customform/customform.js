function onSaveForm(){
	setting.action="save";
	_SubmitForm('form-panel');
}

function startFlow(){
	setting.action="startFlow";
	_SubmitForm('form-panel');
}

function openFlowChart(){
	openFlowChartDialog({solId:solId})
}



function loadButtons(){
	var commonButtons=[{name:"打印",icon:"icon-print",method:"onPrint"},
			{name:"保存",icon:"icon-save",method:"onSaveForm"}];
	//是否能启动流程。
	if(canStartFlow){
		var flowBtns=[{name:"启动流程",icon:"icon-start",method:"startFlow"},
		              {name:"流程图",icon:"icon-flow",method:"openFlowChart"}];
		merageButtons(commonButtons,flowBtns);
	}
	
	var buttons=mini.decode(buttonDef);
	buttons.push({name:'关闭',icon:'icon-close',method:'CloseWindow("cancel")'});
	merageButtons(commonButtons,buttons);
	
	var html=baiduTemplate('buttonTemplate',{buttons:commonButtons});
	$("#toptoolbar").html(html);
}

function merageButtons(target,buttons){
	for(var i=0;i<buttons.length;i++){
		var btn=buttons[i];
		target.push(btn);
	}
}


function _SubmitForm(formId){
	//若有自定义函数，则调用页面本身的自定义函数
	if(isExitsFunction('selfSaveData')){
		selfSaveData.call();
		return;
	}
	var form = new mini.Form("#"+formId); 
    form.validate();
    
    var isValid=true;
    
    $('.mini-datagrid',$("#" +formId)).each(function(){
		var name=$(this).attr('id');
		var grid=mini.get(name);
		grid.validate();
		if(!grid.isValid()){
			isValid=false;
			return false;
		}
	});
    
    if (!isValid || !form.isValid())  return;
    
    var formData=_GetFormJsonMini(formId);
    var url=__rootPath + $("#" + formId).attr("action");
 
   //若定义了handleFormData函数，需要先调用 
   if(isExitsFunction('handleFormData')){
    	var result=handleFormData(formData);
    	if(result==false) return;
    }
   
    var data={formData:mini.encode(formData)};
    
    
    
    if(isExitsFunction('handleData')){
    	handleData(data);
    }
   
    var config={
    	url:url,
    	method:'POST',
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
    
    config.postJson=true;
    
    OfficeControls.save(function(){
    	_SubmitJson(config);
    });
}