/**
 * 数字转大写。
 * @param n
 * @returns
 */
function toChineseMoney(n) {
        if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
            return "数据非法";
        var unit = "千百拾亿千百拾万千百拾元角分", str = "";
            n += "00";
        var p = n.indexOf('.');
        if (p >= 0)
            n = n.substring(0, p) + n.substr(p+1, 2);
            unit = unit.substr(unit.length - n.length);
        for (var i=0; i < n.length; i++)
            str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
        return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
}

/**
 * 关闭窗口
 * @param e
 */
function onCancel(e){
	CloseWindow('cancel');
}

/**
 * 保存表单。
 * @param formId
 */
function onSave(formId){
	//若有自定义函数，则调用页面本身的自定义函数
	if(isExitsFunction('selfSaveData')){
		selfSaveData.call();
		return;
	}
	var form = new mini.Form("#"+formId); 
    form.validate();
    if (!form.isValid())  return;
    
    var formData=_GetFormJsonMini(formId);
    var url=__rootPath + $("#" + formId).attr("action");
  
   
   //若定义了handleFormData函数，需要先调用 
   if(isExitsFunction('handleFormData')){
    	var result=handleFormData(formData);
    	if(!result) return;
    }
    
    var config={
    	url:url,
    	method:'POST',
    	data:formData,
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
    
    _SubmitJson(config);
}

/**
 * 表格上移
 * @param id
 * @returns
 */
function moveUpRow(id) {
	var headGrid=mini.get(id);

    var row = headGrid.getSelected();
    if (row) {
        var index = headGrid.indexOf(row);
        headGrid.moveRow(row, index - 1);
    }
}

/**
 * 表格下移。
 * @param id
 * @returns
 */
function moveDownRow(id) {
	var headGrid=mini.get(id);
    var row = headGrid.getSelected();
    if (row) {
        var index = headGrid.indexOf(row);
        headGrid.moveRow(row, index + 2);
    }
}



