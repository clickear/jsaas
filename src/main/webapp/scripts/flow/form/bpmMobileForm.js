var boDefId="";

function getTemplates(defId){
	
	var url=__rootPath +"/bpm/form/bpmMobileForm/getTemplatesByBoDef.do?boDefId=" + defId;
	$.post(url,function(data){
		var html= baidu.template('templateList',{list:data});  
		$("#tbody").html(html);
	})
}



function selectBo(){
	openBoDefDialog("",function(action,boDef){
		var txtName= mini.get("viewName");
		txtName.setValue(boDef.name);
		boDefId=boDef.id;
		getTemplates(boDef.id);
	});
}

function getTemplate(){
	var aryTr=$("tr",$("#tbody"));
	var json={};
	aryTr.each(function(){
		var row=$(this);
		var selObj=$("select",row);
		var alias=selObj.attr("alias");
		var type=selObj.attr("type");
		var template=selObj.val();
		var obj={};
		obj[alias]=template;
		if(type=="main"){
			if(!json[type]){
				json[type]=obj;
			}
		}
		else{
			if(!json[type]){
				json[type]=[obj];
			}
			else{
				json[type].push(obj);
			}
		}
	})
	
	var templates=JSON.stringify(json);
	
	return templates;
}

function next(){
	var form = new mini.Form("#form1");
	form.validate();
	if(!form.isValid()) return;
	
	
	var templates=encodeURI( getTemplate());
	var url=__rootPath + "/bpm/form/bpmMobileForm/generate.do?boDefId=" + boDefId +"&templates=" + templates;
	
	_OpenWindow({
		url: url,
        title: "生成手机表单", width: "100%", height:"100%",
        ondestroy: function(action) {
        	CloseWindow('ok');
        }
	});
}
	

function editForm(boDefId){
	var url=__rootPath + "/bpm/form/bpmMobileForm/genTemplate.do?boDefId=" + boDefId;
	_OpenWindow({
		url: url,
        title: "重新生成HTML", width: "600", height: "400",
        ondestroy: function(action) {
        	if(action!="ok") return;
        	var iframe = this.getIFrameEl().contentWindow;
        	var templates=iframe.getTemplate();
        	
    		var urlGen=__rootPath + "/bpm/form/bpmMobileForm/generateHtml.do?boDefId=" + boDefId +"&templates=" + templates ;
    		urlGen=encodeURI(urlGen);
    		$.post(urlGen,function(data){
    			
    			templateView.setContent(data);
    		})
        	
        }
	});
}


