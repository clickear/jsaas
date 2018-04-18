//UEditor控件扩展的属性

/**
 * 
 * @param type
 * @param name
 * @returns
 */
function createElement(type, name){     
    var element = null;     
    try {        
        element = document.createElement('<'+type+' name="'+name+'">');     
    } catch (e) {}   
    if(element==null) {     
        element = document.createElement(type);     
        element.name = name;     
    } 
    return element;     
}




function fieldChange(){
	var field=mini.getByName('name');

	if(field){
		var node= field.getSelectedNode();

		var label=mini.getByName('label');
		if(node && label && label.getValue()==''){
			label.setValue(node.title);
		}
		
		if(node && node.dataType=='String'){
			var maxlen =mini.getByName('maxlen');
			if(maxlen){
				maxlen.setValue(node.len);
			}
		}
		
	}
}

function changeMinMaxWidth(){
	var mwidth=mini.get('mwidth');
	var wunit=mini.get('wunit');
	if(wunit.getValue()=='%'){
		mwidth.setMinValue(0);
		mwidth.setMaxValue(100);
	}else{
		mwidth.setMinValue(0);
		mwidth.setMaxValue(1200);
	}
}

function changeMinMaxHeight(){
	var mheight=mini.get('mheight');
	var hunit=mini.get('hunit');
	if(hunit.getValue()=='%'){
		mheight.setMinValue(0);
		mheight.setMaxValue(100);
	}else{
		mheight.setMinValue(0);
		mheight.setMaxValue(1200);
	}
}

function initPluginSetting(arrayParam){
	var formData={};
	
	if(arrayParam!=null){
		for(var i=0;i<arrayParam.length;i++){
			formData[arrayParam[i].key]=arrayParam[i].value;
		}
	}
	formData['label']=pluginLabel;
	formData['name']="";
    form.setData(formData);
    
    if(mini.get("label")){
    	var val=mini.get("label").getValue();
    	getPy("name",val);
    }
    
	
	
}


function getPy(name,val){
	if(!val) return; 
	var key=mini.get(name);
	if(!key) return;
	if(key.getValue()) return;
	$.ajax({
		url:__rootPath+'/pub/base/baseService/getPinyin.do',
		method:'POST',
		data:{words:val,isCap:'false',isHead:'true'},
		success:function(result){
			key.setValue(result.data);
		}
	});
	
}

function getPinyin(e){
	var val=e.sender.getValue();
	getPy('name',val);
}

function getFormData(data){
	var array=[];
    for ( var p in data ){
    	var newObj={};
    	newObj.key=p;
    	newObj.value=data[p];
    	array.push(newObj);
  	} 
    return array;
}


//改变字段数据类型
function handDataType(val){
	handlerDecimalVal(val);
	handlerMinNumVal(val);
	handlerDefaultVal(val);
}

/**
 * 数据长度
 * @param val
 */
function handlerDecimalVal(val){
	var objDecimal=$("#spanDecimal");
	var objLength=$("#spanLength");
	switch(val){
		case "varchar":
			objLength.show();
			objDecimal.hide();
			break;
		case "date":
			objLength.hide();
			objDecimal.hide();
			break;
		case "number":
			objLength.show();
			objDecimal.show();
			break;
	}
}

/**
* 数据长度处理
*/
function handlerMinNumVal(val){
	var objMinLength=$("#minLength_Tr");
	var objMinNum=$("#minNum_Tr");
	switch(val){
		case "varchar":
			objMinLength.show();
			objMinNum.hide();
			break;
		case "date":
			objMinLength.hide();
			objMinNum.hide();
			break;
		case "number":
			objMinLength.hide();
			objMinNum.show();
			break;
	}
}

/**
 * 默认值处理。
 * @param val
 */
function handlerDefaultVal(val){
	var objLength=mini.get("length");
	var lengthVal=objLength.getValue();
	
	var objDecimal=mini.get("decimal");
	var decimalVal=objDecimal.getValue();
	
	var objMinlen=mini.get("minlen");
	var minlenVal=objMinlen.getValue();
	
	var objMinNum=mini.get("minNum");
	var minNumVal=objMinNum.getValue();
	switch(val){
		case "varchar":
			if(!lengthVal){
				objLength.setValue(50);
			}
			if(!minlenVal){
				objMinlen.setValue(0);
			}
			break;
		case "number":
			if(!lengthVal){
				objLength.setValue(14);
			}
			else{
				if(lengthVal>22){
					objLength.setValue(14);
				}
			}
			if(!decimalVal){
				objDecimal.setValue(0);
			}
			if(!minNumVal){
				objMinNum.setValue(0);
			}
			break;
	}
}

/**
 * 设置长度验证。
 * @param oNode
 * @param formData
 */
function setLenValid(oNode,formData){
	var type=formData.datatype;
	if(type=="varchar"){
		oNode.setAttribute('vtype','rangeLength:'+formData.minlen+','+formData.length);
	}
	else if(type=="number"){
		oNode.setAttribute('vtype','range:'+formData.minNum+','+(Math.pow(10, formData.length)-1));
	}
}

/**
* 是否复杂控件。
*/
function isComplexControl(plugin){
	var complexControls=["mini-buttonedit","mini-checkboxlist",
		"mini-combobox","mini-dep","mini-group","mini-radiobuttonlist",
		"mini-textboxlist","mini-treeselect","mini-user"];
	for(var i=0;i<complexControls.length;i++){
		var tmp=complexControls[i];
		if(tmp==plugin){
			return true;
		}
	}
	return false;
}


/**
 * 取得表单的元数据。
 * @param editor	编辑器
 * @param el		当前点击的元素。
 * @returns
 * 返回数据格式如下：
 * {
 * 	 isMain:true,
	 main:{comment:"备注",fields:[{name:"字段名1",comment:"备注"},{name:"字段名2",comment:"备注"}]},
	 子表1:{comment:"备注",fields:[{name:"字段名1",comment:"备注"},{name:"字段名2",comment:"备注"}]}
   }
    isMain:true表示当前元素在主表中，false表示 在子表中。
 * 
 */
function getMetaData(editor,el){
	var container=$(editor.getContent());
	var elObj=$(el);
	var grid=elObj.closest(".rx-grid");
	//定义全局的变量是否主表。
	var isMain=grid.length==0;
	var metaJson={isMain:isMain};
	
	if(isMain){
		var mainFields=[];
		metaJson["main"]={comment:"主表",fields:mainFields};
		var els=$("[plugins]:not(div.rx-grid [plugins])",container);
		els.each(function(){
			var obj=$(this);
			//排除子表。
			var plugins=obj.attr("plugins");
			
			if(plugins=="rx-grid" || plugins=="mini-button") return true;
			
			var label=obj.attr("label");
			var name=obj.attr("name");
			var fieldObj={name: name, comment: label};   
			mainFields.push(fieldObj);
			if(isComplexControl(plugins)){
				var fieldObj={name: name +"_name", comment: label+"名称"};   
				mainFields.push(fieldObj);
			}
		});
		
		var grids=$(".rx-grid",container);
		grids.each(function(){
			var grid=$(this);
			getGridMetaData(grid,metaJson);
		})
	}
	else{
		getGridMetaData(grid,metaJson);
	}
	
	return metaJson;
	
}
/**
 * 取得表格元数据。
 */
function getGridMetaData(grid,metaJson){
	var gridName=grid.attr("name");
	var gridComment=grid.attr("label");
	var gridFields=[];
	
	$("[plugins]",grid).each(function(){
		var obj= $(this);
		var label=obj.attr("label");
		var name=obj.attr("name");
		var plugins=obj.attr("plugins");
		if(plugins=="mini-button") return true;
		
		var fieldObj={name: name, comment: label};   
		gridFields.push(fieldObj);
		
		if(isComplexControl(plugins)){
			var fieldObj={name: name +"_name", comment: label +"名称"};   
			gridFields.push(fieldObj);
		}
		
		metaJson[gridName]={comment:gridComment,fields:gridFields};
	});
}


/**
 * 输入条件表格点击时处理。
 */
function gridInputCellBeginEdit(e){
	var field=e.field;
	var record=e.record;
	//当点击绑定值字段时处理。
	if(field=='bindVal'){
		var orginEditor=e.editor;
		if(record.mode=="mapping"){
			var editor=mini.get("inputColumnMapEditor");
			editor.grid=e.sender;
			//主表情况
			if(isMain){
				editor.setData(metaJson["main"].fields);
			}
			//子表情况
			else{
				var tableObj=mini.get("table");
				var tablename=tableObj.getValue();
				editor.setData(metaJson[tablename].fields);
			}
			e.editor=editor;
			e.column.editor=e.editor;
		}
		else{
			var editor=mini.get("inputScriptEditor");
			editor.grid=e.sender;
			e.editor=editor;
			e.column.editor=e.editor;
		}
	}
}

//绑定模式发生变化时
function gridInputCellCommitEdit(e){
	var grid=e.sender;
	if(e.field=="mode" && e.oldValue && e.oldValue!=e.value){
		grid.updateRow(e.record,{bindVal:""});
	}
}

/**
 * 取得对话框的输入参数。
 * @param jsonStr
 * @returns
 */
function getDialogParams(jsonStr){
	if(!jsonStr) return [];
	var jsonAry=mini.decode(jsonStr);
	var rtnAry=[];
	for(var i=0;i<jsonAry.length;i++){
		var obj=jsonAry[i];
		if(obj.type && obj.type=="income"){
			obj.mode="mapping";
			obj.modeName="映射";
			rtnAry.push(obj);
		}
	}
	return rtnAry;
}

/**
 * 获取表单对应的表。
 * [{name:"表名",comment:"注解"},{name:"",comment:""}]
 * @param metaJson
 * @returns
 */
function getTables(metaJson){
	var tables=[];
	for(var key  in metaJson){
		if(key=="isMain") continue;
		var table={};
		table.name=key;
		var o=metaJson[key];
		table.comment=o.comment;
		tables.push(table);
	} 
	return tables;
}

function gridReturnBeginEdit(e){
	var editor=e.editor;
	var tableObj=mini.get("table");
	var tablename=tableObj.getValue();
	var fields=metaJson[tablename].fields;
	editor.setData(fields);
}

function bindTable(tbName){

	var comboxTb=mini.get("table");
	var aryTables=getTables(metaJson);
	comboxTb.setData(aryTables);
	if(tbName){
		comboxTb.setValue(tbName);
	}
	else{
		comboxTb.setValue(aryTables[0].name);
	}
}

/**
 * 返回列表数据。
 * @param rs
 * @returns
 */
function getReturnList(rs){
	var rtnJson=mini.decode(rs.fieldsJson);
	var rtnAry=[];
	for(var i=0;i<rtnJson.length;i++){
		var o=rtnJson[i];
		if(o.isReturn=="YES"){
			rtnAry.push(o);
		}
	}
	return rtnAry;
}