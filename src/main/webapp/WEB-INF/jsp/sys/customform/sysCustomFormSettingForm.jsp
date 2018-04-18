<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[单据表单方案]编辑</title>
<%@include file="/commons/customForm.jsp"%>
<script type="text/javascript" src="${ctxPath }/scripts/sys/customform/customform.js"></script>
<script type="text/javascript">

	var buttonDef='${setting.buttonDef}' || '[]';
	var solId='${setting.solId}';
	var setting={alias:"${setting.alias}"};
	var isTree=${setting.isTree};
	var canStartFlow=${canStartFlow};
	var afterJs=${hasAfterJs};
	
	$(function(){
		loadButtons();
		//解析表单。
		renderMiniHtml({});
		//初始化表单状态
		initFormElementStatus();
	})
	
	/**
	 * 关闭窗口。
	 */
	function closeWin(){
		if(isTree==1){
			//parent.closeWin();
		}
		else{
			CloseWindow('ok');
		}
	}
	
	
	function handleFormData(data){
		${setting.preJsScript}
	}
	
	function handleData(data){
		data.setting=setting;
	}
	
	function successCallback(result){
		if(isTree==1){
			var action=result.data.action;
			var row=result.data.row;
			if(action=="add"){
				tree.addNode(row, "add", selectNode);
			}
			else{
				tree.updateNode(selectNode,{text_:row.text_});
			}
		}
		if(afterJs){
			${setting.afterJsScript}
		}
		else{
			closeWin();	
		}
	}
	
	if(!$('.mini-window',parent.document).length){
		$('.mini-tabs-body:visible', parent.document).addClass('index_box');
		$('.show_iframe:visible', parent.document).addClass('index_box');
	}
	
	function onPrint(){
		printForm({formAlias:"${setting.formAlias}"});
	}
	
</script>
<style>
	*{
		color: #666;
	}
	
/* 	#form-panel{
		background: #fff;
		box-shadow: 0px 5px 10px 0px rgba(179, 179, 179, 0.15);
		margin: 30px auto;
		border-radius:10px;
		overflow: hidden;
		background: #fff;
	} */
	
	.paddingBox{
		padding:6px;
		box-sizing: border-box;
	}
	
	.paddingBox .shadowBox{
		box-shadow: none;
		padding: 0;
		margin-bottom: 0;
		border-radius: 0;
	}
	
	.paddingBox>input{
	    border: 1px solid #ececec;
	    box-sizing: border-box;
	    border-radius: 4px;
	    padding: 4px;
	}
	
	.paddingBox>*,
	.paddingBox .shadowBox>*{
		width: 100% !important;
	}
	
	.mini-toolbar{
		background: transparent;
		border: none;
	}
	
	.topBar .mini-button,
	body #toptoolbar>a:hover.mini-button{
		border-color: #fff;
		background: #fff
	}
</style>
</head>
<body>
	
	<div class="mini-toolbar topBar noPrint">
		<div id="toptoolbar"></div>
	</div>
	
	<script type="text/html;" id="buttonTemplate">
		<#for(var i=0;i<buttons.length;i++){
			var btn=buttons[i];
		#>
		<a class="mini-button" <#if(btn.icon){#> iconCls="<#=btn.icon#>" <#}#> onclick="<#=btn.method#>"><#=btn.name#></a>

		<#}#>
	</script>
	
	
	<div class="customform shadowBox90 form-model" id="form-panel" action="/sys/customform/sysCustomFormSetting/saveData.do">
		${formModel.content}
	</div>

	
	<script type="text/javascript">
		addBody();	
	</script>
	
</body>
</html>