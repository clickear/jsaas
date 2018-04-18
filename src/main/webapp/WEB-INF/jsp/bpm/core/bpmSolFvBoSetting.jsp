<%-- 
    Document   : tab权限页面
    Created on : 2015-11-21, 10:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>节点Bo设置页面</title>
<%@include file="/commons/edit.jsp"%>
<script type="text/javascript" src="${ctxPath}/scripts/common/baiduTemplate.js"></script>
<script src='${ctxPath}/scripts/qtip2/jquery.qtip.js'></script>
<link href="${ctxPath}/scripts/qtip2/jquery.qtip.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
<style type="text/css">
.table-detail > tbody > tr > th {
	text-align: left;
	padding:5px;
}

.table-detail > tbody > tr > td{
	width:25% !important;
	padding:5px;
}

.tipMsg{
}

.tipMsg span{
	color:red;
}

.CodeMirror{
	height: 200px !important;
}
</style>
</head>
<body>
		<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom" >
		    <table>
		        <tr>
		      		<td style="width:100%;">
						<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveSetting">保存</a> 
						<a class="mini-button" iconCls="icon-refresh" plain="true" onclick="reset">重置</a> 
						<a class="mini-button" iconCls="icon-cancel" plain="true" onclick="oncancel">关闭</a>
					</td>
				</tr>
			</table>
		</div>
		
		<div class="mini-fit" >
			<div id="tabs1" class="mini-tabs" activeIndex="0" style="width: 100%;" plain="false">
				<div title="表单数据模型">
					<c:forEach items="${sysBoEntList}" var="sysBoEnt">
						<table class="table-detail column_2_m" cellspacing="1" cellpadding="0" tableName="${sysBoEnt.name}" boDefId="${sysBoEnt.boDefId}">
							<caption>${sysBoEnt.comment}-(${sysBoEnt.name})</caption>
							<tr>
								<th>字段名</th>
								<th>备注</th>
								<th style="width:150px">初始化设定</th>
								<th style="width:150px">保存设定</th>
							</tr>
							<c:forEach items="${sysBoEnt.sysBoAttrs}" var="att">
							<tr>
								<td>${att.name}</td>
								<td>${att.comment}</td>
								<td>
									<a class="mini-button" iconCls="icon-edit"  onclick="openSetting('init','${sysBoEnt.name}','${att.name}','${att.isSingle}','${sysBoEnt.boDefId}')">设定</a>
									<span class="tipMsg" id="init_${sysBoEnt.name}_${att.name}">
										<span>❂</span>
										<a class="mini-button" iconCls="icon-refresh" plain="true" onclick="clearSetting"></a>
									</span>
								</td>
								<td>
									<a class="mini-button" iconCls="icon-edit" onclick="openSetting('save','${sysBoEnt.name}','${att.name}','${att.isSingle}','${sysBoEnt.boDefId}')">设定</a>
									<span style="display: none;" class="tipMsg" id="save_${sysBoEnt.name}_${att.name}">
										<span>❂</span>
										<a class="mini-button" iconCls="icon-refresh" plain="true" onclick="clearSetting"></a>
									</span>
								</td>
							</tr>
							</c:forEach>
						</table>
					</c:forEach>
				</div>
				<div title="初始化脚本">
					<span>请在以下输入框输入Groovy脚本。<a href="#">帮助</a></span>
					<textarea id="initScript"  style="width: 99%;height:180px;">${initScript}</textarea>
				</div>
				<div title="保存脚本">
					<span>请在以下输入框输入Groovy脚本。<a href="#">帮助</a></span>
					<textarea id="saveScript"  style="width: 99%;height:180px;">${saveScript}</textarea>
				</div>
			</div>
	</div>
	<div id="boAttSettings" style="display: none">${boAttSettings}</div>
	

	<script type="text/javascript">
	     mini.parse();
	     
	     var solId='${param.solId}';
	     var nodeId='${param.nodeId}';
	     var actDefId='${param.actDefId}';
	     
	     //字段JSON参数配置
	     var boAttSettingsText=$("#boAttSettings").text();
	     var boAttSettings=(boAttSettingsText=='')?[]:mini.decode(boAttSettingsText);//此页面初始化时的init数据
	     
	     function appendContent(editor,tabName,content){
    		var doc = editor.getDoc();
    		var cursor = doc.getCursor(); // gets the line number in the cursor position
   			doc.replaceRange(content, cursor); // adds a new line
	 	} 
	     
	     function showTipMsg(){
	    	 $(".tipMsg").css("display","none");
	    	 for(var i=0;i<boAttSettings.length;i++){
	    		 var json=boAttSettings[i];
	    		 var initJSON=json.init;
	    		 var saveJSON=json.save;
	    		 
	    		 initTipMsg(initJSON,"init");
	    		 initTipMsg(saveJSON,"save");
	    	 }
	     }
	     
	     function initTipMsg(json,type){
	    	 for(var tabName in json){
	    		 var attrs=json[tabName];
	    		 for(var field in attrs){
	    			 if(!attrs[field]) continue;
	    			 var id=type +"_" + tabName +"_" + field;
	    			 $("#" + id).css("display","inline");
	    		 }
	    	 }
	     }
	     
	     function clearSetting(e){
	    	 var sender=e.sender;
	    	 var el=sender.el;
	    	 var parent=el.parentNode;
	    	 var id=parent.getAttribute("id");
	    	 
	    	 var aryTmp=id.split("_");
	    	 var type=aryTmp[0];
	    	 var table=aryTmp[1];
	    	 var field=aryTmp[2];
	    	 
	    	 for(var i=0;i<boAttSettings.length;i++){
	    		 var json=boAttSettings[i];
	    		 var tableJson=json[type];
	    		 if(!tableJson) continue;
	    		 for(var tabName in tableJson){
	    			 if(tabName!=table) continue;
	    			 var tabObj=tableJson[tabName];
	    			 if(tabObj[field]){
	    				 delete tabObj[field];
	    			 }
	    		 }
	    	 }
	    	 
	    	 $("#" + id).css("display","none");
	     }
	     
	     
	     
	     $(function(){
	    	 showTipMsg();
	    	 
	    	 initEditors();
	     })
	     
	     var saveScriptEditor;
	     var initScriptEditor;
	     function initEditors(){
	    	 saveScriptEditor= CodeMirror.fromTextArea(document.getElementById('saveScript'), {
		 	        matchBrackets: true,
		 	        lineNumbers: true,
		 	        mode: "text/x-groovy"
		 	 });
		     initScriptEditor= CodeMirror.fromTextArea(document.getElementById('initScript'), {
		 	        matchBrackets: true,
		 	        lineNumbers: true,
		 	        mode: "text/x-groovy"
		 	 }); 

		     //初始化编辑器。
		     initEditor(saveScriptEditor);
		     initEditor(initScriptEditor);
	     }

	     
	     
	     function initEditor(editor){
	    	 var doc = editor.getDoc();
	    	 var script = editor.getValue();
	    	 if(!script){
	    		 script=" ";
	    	 }
	    	 doc.setValue(script);	 
	     }
	     
		  
	     function oncancel(){
	    	 CloseWindow();
	     }		
	    
	    function openSetting(type,tableName,field,isSingle,boDefId){
	    	var boFieldConf=null;
	    	for(var i=0;i<boAttSettings.length;i++){
	    		if(boAttSettings[i].boDefId==boDefId){
	    			boFieldConf=boAttSettings[i];
	    		}
	    	}
	    	if(!boFieldConf){
	    		boFieldConf={boDefId:boDefId};
	    		boAttSettings.push(boFieldConf);
	    	}
	    	var url=__rootPath+'/bpm/core/bpmBo/initSettingValue.do?isSingle='+isSingle+"&boDefId="+boDefId+"&tableName="+tableName;
	    	 _OpenWindow({
	    		title:"业务模型数据",
				url:url,
				width:800,
				height:450,
				onload:function(){
	            	var iframe = this.getIFrameEl().contentWindow;
	            	if(!boFieldConf[type]){
	            		boFieldConf[type]={};
	            	}
	            	if(!boFieldConf[type][tableName]){
	            		boFieldConf[type][tableName]={};
	            	}
	            	if(!boFieldConf[type][tableName][field]){
	            		boFieldConf[type][tableName][field]={};
	            	}
	            	
	            	var data=boFieldConf[type][tableName][field];
	            	data.field=field;
	            	iframe.initData(data);
		        },
				ondestroy:function(action){
					if(action!="ok") return; 
					var iframe = this.getIFrameEl();
		            var fieldConfig = iframe.contentWindow.getConfig();
		            boFieldConf[type][tableName][field]=fieldConfig;
		            showTipMsg();
				}
			}); 
	    }
	    
	    var postData={};
	    
	    function getDataConf(){
	    	return postData;
	    }
	    
 
	    function saveSetting(){
	    	postData.initScript=initScriptEditor.getValue();
	    	postData.saveScript=saveScriptEditor.getValue();
	    	postData.boAttSettings=boAttSettings;
	    	_SubmitJson({
	    		url:"${ctxPath}/bpm/core/bpmSolFv/saveBoSetting.do",
	    		data:{"postData":mini.encode(postData),solId:solId,actDefId:actDefId,nodeId:nodeId},
	    		success:function (result){
	    			if(result.success){
	    				CloseWindow('ok');
	    			}else{
	    				CloseWindow('false');
	    			}
	    		}
	    	});
	    }
	    
	 
	    function reset(){
	    	boAttSettings=[];
	    	initScriptEditor.setValue('');
	    	saveScriptEditor.setValue('');
	    	
	    	showTipMsg();
	    }
    
    
	</script>
</body>
</html>