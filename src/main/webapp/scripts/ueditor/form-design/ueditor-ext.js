
function getInput(binding){
	var gridInput=binding.gridInput;
	if(!gridInput || gridInput.length==0) return "";
	var params=[];
	for(var i=0;i<gridInput.length;i++){
		var param=gridInput[i];
		if(param.type!="income") continue;
		params.push(param);
	}
	var rtn="";
	for(var i=0;i<params.length;i++){
		var param=params[i];
		var tmp=construtParam(param);
		if(!tmp) continue;
		if(!rtn){ 
			rtn=tmp;
		}
		else{
			rtn+="&" +tmp;
		}
	}
	return rtn;
}

/**
 * 构建参数。
 * @param param
 * @returns
 */
function construtParam(param){
	var mode=param.mode;
	var bindVal=param.bindVal;
	if(!bindVal) return "";
	var val="";
	if(mode=="mapping"){
		val=mini.getByName(bindVal).getValue();
	}
	else{
		var func = new Function(bindVal);
		var val=func();
	}
	if(!val) return "";
	return  param.fieldName +"=" + val;
	
}





function getBtnEditInput(binding,row){
	var gridInput=binding.gridInput;
	if(!gridInput || gridInput.length==0) return "";
	var params=[];
	for(var i=0;i<gridInput.length;i++){
		var param=gridInput[i];
		if(param.type!="income") continue;
		params.push(param);
	}
	var rtn="";
	for(var i=0;i<params.length;i++){
		var param=params[i];
		var tmp=construtBtnEditParam(param,binding,row);
		if(!tmp) continue;
		if(!rtn){ 
			rtn=tmp;
		}
		else{
			rtn+="&" +tmp;
		}
	}
	return rtn;
	
}

/**
 * 构建参数。
 * @param param
 * @returns
 */
function construtBtnEditParam(param,binding,row){
	var mode=param.mode;
	var bindVal=param.bindVal;
	var val="";
	if(mode=="mapping"){
		if(binding.isMain){
			val=mini.getByName(bindVal).getValue();
		}
		else{
			val=row[bindVal];
		}
	}
	else{
		var func = new Function(bindVal);
		var val=func();
	}
	if(!val) return "";
	return param.fieldName +"=" + val;
	
}


var _jsonInit={};
/**
 * 设置表格参数
 */
function setGridConfig(){
	var parent=$("#form-panel");
	var grids=$("div.mini-datagrid,.mini-treegrid",parent);
	
	for(var i=0;i<grids.length;i++){
	   var grid=grids[i];
	   var id=grid.id;
	   if(!id) continue;
	   var gd=mini.get(id);
	   if(!gd)  continue;

	   gd.on('cellendedit',function(e){
		if(e.column.displayField && e.editor.getText && e.editor.getText()){
			e.record[e.column.displayField]=e.editor.getText();
		}
	   });
	   //禁止编辑已添加的数据。
	   gd.on('cellbeginedit',function(e){
		   var sender=e.sender;
		   var dealwith=sender.dealwith;
		   var row=e.record;
		   if(dealwith && dealwith.indexOf("noedit")!=-1 && row.ID_){
			   e.cancel=true;
		   }
	   });
	   //画单元格
	   gd.on("drawcell",function(e){
		   if(window.drawcell){
			   window.drawcell(e);
		   }
	   })
	}
	
	// 初始化表格数据
	$('._initdata').each(function(){
		var gridId="grid_" + $(this).attr('grid');
		var grid=mini.get(gridId);
		var gridData=$(this).html().trim(); 
        try{
        	var json=mini.decode(gridData);
        	_jsonInit[gridId]=json.initData;
        	var json=json.data;
        	if(grid.type=="treegrid"){
        		grid.loadList(json,"ID_","PARENT_ID_");
        	}
        	else{
        		grid.setData(json);
        	}
        	mini.layout();
        }catch(e){
        }
	});
}

/**
 * 将JSON转成树形。
 */
function transToTree(json,id,pid){
	var jsonObj={};
	for(var i=0;i<json.length;i++){
		var o=json[i];
		var key=o[id];
		jsonObj[key]=o;
	}
	for(var key in jsonObj){
		var o=jsonObj[key];
		var obj=jsonObj[o[pid]];
		if(obj){
			obj.children =obj.children ||[];
			obj.children.push(o);
			o.deleteTag=1;
		}
	}
	for(var key in jsonObj){
		var o=jsonObj[key];
		if(o.deleteTag==1){
			delete jsonObj[key]
		}
	}
	var result=[];
	for(var key in jsonObj){
		var o=jsonObj[key];
		result.push(o);
	}
	return result;
}

//加载审批历史
function loadCheckHisList(){
	var objCheck=mini.get("checkhilist");
	if(objCheck==null) return;

	objCheck.on("drawcell", function (e) {
        field = e.field,
        value = e.value;
        if(field!='jumpType') return;
        
    	if(value=='AGREE'){
    		e.cellHtml='同意';
    	}else if(value=='REFUSE'){
    		e.cellHtml='弃权';
    	}else if(value=='BACK'){
    		e.cellHtml='回退';
    	}else if(value=='ABACK_TO_STARTOR'){
    		e.cellHtml='回退发起人';
    	}else if(value=='UNHANDLE'){
    		e.cellHtml='未开始审批';
    	}
        
    });
	objCheck.load();// 审批历史grid
	
}


function initCheckGrid(){
	var obj=mini.get("checkhilist");
	if(!obj) return;
	
	obj.load();// 审批历史grid
	obj.on("drawcell", function (e) {
        field = e.field,
        value = e.value;
        if(field=='jumpType'){
        	var json={AGREE:"同意",REFUSE:"弃权",BACK:"回退",ABACK_TO_STARTOR:"回退发起人",UNHANDLE:"未开始审批"};
        	e.cellHtml=json[value];
        }
    });
}

var formulaCalc_;
//解析及显示miniUI控件表单
function renderMiniHtml(conf,result){
	mini.parse();
	setGridConfig();
	//公式处理。
	formulaCalc_=new FormCalc("form-panel");
	formulaCalc_.parseFormula();
	
	customQuery=new CustomQuery("form-panel");
	customQuery.parseQuery();
	
	//初始化审批历史
	initCheckGrid();
	//打印表单处理
	tabPrintHandler();
	if(isExitsFunction('_onload')){
		try{
			_onload.call();
		}catch(e){}
	}
	if(conf.callback){
		conf.callback.call(this,result);
	}
}


/**
 * 处理是否打印TAB表单。
 */
function tabPrintHandler(){
	
	$("[name='chkPrint']").each(function(){
		var obj=$(this);
		obj.bind("click",function(){
			var ckObj=$(this);
			var id=ckObj.attr("id");
			var checked=ckObj[0].checked;
			var tabId=id.replace("tab_","form_");
			var form=$("#"+ tabId);
			if(checked){
				form.removeClass("noPrint")
			}
			else{
				form.addClass("noPrint")
			}
		});
	})
}



/**
 * 打印表单。
 * conf配置如下：
 * {
 * 	instId:"",
 * 	solId:"",
 * 	taskId:"",
 * 	formAlias:""
 * }
 * 
 * @param conf
 * @param data	表单数据
 */
function printForm(conf){
	var params="";
	var aryPara=[];
	for(var key in conf){
		aryPara.push(key +"=" + conf[key]);
	}
	var url=__rootPath+'/bpm/form/bpmFormView/print.do?' + aryPara.join("&");
	var windowSize=getWindowSize();
	_OpenWindow({
		title:"打印表单",
		url:url,
		width:windowSize.width || 700,
		height:windowSize.height ||450,
		onload : function() {
			var result=getBoFormData(false);
			var data = mini.encode(result.data);
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(data);
		}
	});
}


function onFileRender(e){
    var record = e.record;
    var files =record[e.field];
    if(!files) return "";
    var s="";
    var aryFile=mini.decode(files);
  	for(var i=0;i<aryFile.length;i++){
  		var file=aryFile[i];
  		s+=getFile(file) ;
  	}
    return s;
}

function onImgRender(e){
    var record = e.record;
    var img =record[e.field];
    if(!img) return "";
    
    var imgJson=mini.decode(img);
    console.info(imgJson);
    //{imgtype:'upload',val:""}
    var url="";
    var val=imgJson.val;
    if(imgJson.imgtype=="upload"){
    	url=__rootPath+'/sys/core/file/previewFile.do?fileId='+ val;
    }
    else{
    	if(val.startWith("http")){
    		url=val;
    	}
    	else{
    		url=__rootPath +val;
    	}
    }
    var str="<img src='"+url+"' style='width:200px;height:auto'/>";
    return str;
}

function getFile(file){
	var fileName=file.fileName;
	var aryDoc=["docx","doc","xlsx","xls","pptx","ppt"];
	var aryImg=["jpg","png","bmp","gif"];
	
	if(isExtend(aryDoc, fileName)) { 
		var str= '<a class="openImg" href="#" onclick="_openDoc(\''+file.fileId+'\');">'+fileName+'</a>';
		return str;
	}
	else if(isExtend(aryImg, fileName)) { 
		var str= '<a class="openImg"  href="#" onclick="_openImg(\''+file.fileId+'\');">'+fileName+'</a>';
		return str;
	}
	else if(fileName.indexOf('pdf')!=-1) { 
		var str= '<a class="openImg" href="#" onclick="_openPdf(\''+file.fileId+'\');">'+fileName+'</a>';
		return str;
	}
	else{
		return '<a class="openImg" target="_blank" href="'+__rootPath+'/sys/core/file/previewFile.do?fileId='+file.fileId+'">'+fileName+'</a>';
	}
}

function isExtend(aryExt,fileName){
	for(var i=0;i<aryExt.length;i++){
		var tmp=aryExt[i];
		if(fileName.indexOf(tmp)!=-1){
			return true;
		}
	}
	return false;
}

