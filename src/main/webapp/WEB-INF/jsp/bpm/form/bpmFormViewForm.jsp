<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[自定义表单配置设定]编辑</title>
<%@include file="/commons/customForm.jsp"%>
<script type="text/javascript">

function getData(){
	var formData=_GetFormJsonMini("form-panel");
	return formData;
}

function setData(data){
	var form = new mini.Form("#form-panel"); 
	form.setData(data);
}

function isValid(){
	var form = new mini.Form("#form-panel"); 
    form.validate();
    return  form.isValid();
}

//设置字段为字段标签模式
function setFieldsLabelMode(){
	var form=new mini.Form("#form-panel");
	 var fields = form.getFields();                
     for (var i = 0, l = fields.length; i < l; i++) {
         var c = fields[i];
         if (c.setReadOnly) c.setReadOnly(true);     //只读
         if (c.setIsValid) c.setIsValid(true);      //去除错误提示
         if (c.addCls) c.addCls("asLabel");          //增加asLabel外观
     }
	
}

$(function(){
	//解析表单。
	renderMiniHtml({});
});

function onOk(){
	if(isValid()){
		CloseWindow('ok');
	}
}

</script>
</head>
<body>
	<div id="toptoolbarBg"></div>
	<div id="toptoolbar">
		<a class="mini-button" iconCls="icon-ok" onclick="onOk">确定</a>
		<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
	</div>
	<div class="customform" id="form-panel" >
		${formModel.content}
	</div>
</body>
</html>