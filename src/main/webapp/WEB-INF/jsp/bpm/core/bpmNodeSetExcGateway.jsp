<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<title>流程节点的分支网关的配置页</title>
	<%@include file="/commons/list.jsp"%>
	<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />

		<!-- code codemirror start -->	
	<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
	<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
	<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
	<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
	<!-- code minitor end -->
</head>
<body>
	<div id="toolbar1" class="mini-toolbar" >
	    <table style="width:100%;height:20px;">
	        <tr>
       			<td style="width:100%;">
					<a class="mini-button" plain="true" iconCls="icon-save" onclick="saveConfig">保存</a>
					<a class="mini-button" plain="true" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
			<div id="baseTab" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" >
			    <div title="基本设置"  iconCls="icon-mgr" style="width:100%;">
					<form id="configForm" >
				       	<table class="table-detail column_2" cellspacing="1" cellpadding="0" style="width:100%;">
				       	  <caption>跳转分支配置</caption>
				       	  <colgroup> 
							   <col width="120"/> 
							   <col/> 
						  </colgroup>
				       	  <c:if test="${fn:length(preConfigVars)>0}">
				       	  	<tr>
				       	  		<td colspan="2">
				       	  			<a class="mini-menubutton "  iconCls="icon-var"  menu="#preVarsMenu" >前置流程变量</a>
				       	  			<ul id="preVarsMenu" style="display:none" class="mini-menu">
			                  			<c:forEach items="${preConfigVars}" var="var">
					       	  				<li iconCls="icon-var">${var.name}:${var.key}(${var.type})</li>
					       	  			</c:forEach>
			                  		</ul>
				       	  		</td>
				       	  	</tr>
				       	  </c:if>
				       	  
				       	  <c:forEach items="${seqMap}" var="item">
				       	  <c:set var="actDef" value="${item.value}"/>
			              <tr>
			                  <th>${actDef.destNodeName}</th>
			                  <td style="padding:6px;">
			                  		
			                  
				                  <input class="mini-hidden" name="nodeId" value="${actDef.destNodeId}"/>
				                  <textarea class="mini-textarea" id="config_${actDef.destNodeId}" name="config" style="width:80%;height:50px;" required="true">${actDef.conditionExpression}</textarea>
				                  <a class="mini-button" iconCls="icon-mgr" plain="true" onclick="showExpressionEditor('${param.solId}','${param.actDefId}','${actDef.destNodeId}')">设计</a>
			                  </td>
			              </tr>
			              </c:forEach>
				 		</table>
				 	</form>
				</div>
				<div title="事件脚本配置" iconCls="icon-script" style="width:100%;">
				<ul id="popVarsMenu" style="display:none" class="mini-menu">
					 			<c:forEach items="${configVars}" var="var">
						 			<li iconCls="icon-var"  onclick="appendScript('${var.key}')">${var.name}[${var.key} (${var.type})] </li>
						 		</c:forEach>
						    </ul>
			       <div class="mini-toolbar" style="margin-bottom:2px;padding:10px;">
				 		<a class="mini-menubutton "  iconCls="icon-var"  menu="#popVarsMenu" >插入流程变量</a>
				 		<a class="mini-button" iconCls="icon-formAdd" plain="true" onclick="showFormFieldDlg()">从表单中添加</a>
				 		 <a class="mini-button" iconCls="icon-script" href="javascript:showScriptLibary()">常用脚本</a>
			 		</div>
			       	<div id="eventTabs" class="mini-tabs" style="width:100%;height:90%" onactivechanged="activeCodeMirror">
					    <c:forEach items="${eventConfigs}" var="event">
					    	<div name="${event.eventKey}" title="[${event.eventName}]-事件脚本" iconCls="icon-script">
					    		<div style="width:100%;border-bottom: solid 1px #ccc">在此编写Groovy脚本，<a href="#">帮助</a> &nbsp;
					    			&nbsp;SQL执行数据源:<input class="mini-buttonedit" name="dbAlias" id="dbAlias_${event.eventKey}" onbuttonclick="selDataSource" value="${event.dbAlias}" text="${event.dbAlias}" showClose="true" oncloseclick="_OnButtonEditClear">
					    		 </div>
					    		<textarea id="${event.eventKey}" class="textarea" name="script" style="width:90%;height:100%">${event.script}</textarea>
					    	</div>
					    </c:forEach>
					</div>
			    </div>
			</div>
		</div>
	
	<script type="text/javascript">
		mini.parse();
		var solId="${param['solId']}";
		var nodeType="${param['nodeType']}";
		var nodeId="${param['nodeId']}";
		var actDefId="${param.actDefId}";
		var eventEditors=[];
		//事件编辑器
		var eventKeys=mini.decode('${eventKeys}');
		
		var expWindow=mini.get('expWindow');
		
		
		//在当前活动的tab下的加入脚本内容
		function appendScript(scriptText){
			var tab = mini.get('eventTabs').getActiveTab();
			 for(var i=0;i<eventEditors.length;i++){
           	 if(eventEditors[i].key==tab.name){
           		var editor=eventEditors[i].editor;
           		var doc = editor.getDoc();
           		var cursor = doc.getCursor(); // gets the line number in the cursor position
          		doc.replaceRange(scriptText, cursor); // adds a new line
           		return;	 
           	 }
            }
		}
		
		$(window).resize(function(){
	    	setTimeout('mini.layout();',500);
	    });
		
		//显示编辑器
		function showExpressionEditor(solId,actDefId,destNodeId){
			_OpenWindow({
				title:'公式设计器',
				iconCls:'icon-formula',
				url:__rootPath+'/bpm/core/bpmSolution/expEditor.do?solId='+solId+'&nodeId='+nodeId+'&actDefId='+actDefId,
				width:800,
				height:450,
				ondestroy:function(action){
					if(action!='ok') return;
					var win=this.getIFrameEl().contentWindow;
					var content=win.getFormula();
					mini.get('config_'+destNodeId).setValue(content);
				}
			});
		}
		
		for(var i=0;i<eventKeys.length;i++){
			var editor = CodeMirror.fromTextArea(document.getElementById(eventKeys[i]), {
		        //lineNumbers: true,
		        matchBrackets: true,
		        mode: "text/x-groovy"
		      });
			
			//设置自适应宽度及高度
			//editor.setSize('auto', 'auto');
			eventEditors.push({
				key:eventKeys[i],
				editor:editor
			});
		}
		//显示脚本库
		function showScriptLibary(){
			_OpenWindow({
				title:'脚本库',
				iconCls:'icon-script',
				url:__rootPath+'/bpm/core/bpmScript/libary.do',
				height:450,
				width:800,
				onload:function(){
					
				},
				ondestroy:function(action){
					if(action!='ok'){
						return;
					}
					var win=this.getIFrameEl().contentWindow;
					var row=win.getSelectedRow();
					
					if(row){
						 appendScript(row.example);
					}
				}
			});
		}
		function activeCodeMirror(e){
			 var tabs = e.sender;
             var tab = tabs.getActiveTab();
             for(var i=0;i<eventEditors.length;i++){
            	 if(eventEditors[i].key==tab.name){
            		 eventEditors[i].editor.setSize('auto','auto');
            		 eventEditors[i].editor.focus();
            		return;	 
            	 }
             }
		}
		//保存配置信息
		function saveConfig(){
			var form=new mini.Form('configForm');
			form.validate();
			if(!form.isValid()){
				return;
			}
			var nodeConfigs=[];
			$("textarea[name='config']").each(function(){
				var nodeId=$(this).parent().parent().siblings('input[name="nodeId"]').val();
				var val=this.value;
				nodeConfigs.push({nodeId:nodeId,condition:val});
			});

			var configs={conditions:nodeConfigs};
			var events=[];
			var eventTab=mini.get('eventTabs');
			for(var i=0;i<eventEditors.length;i++){
				var tab=eventTab.getTab(eventEditors[i].key);
				var key=tab.name;
				var eventName=tab.title;
				events.push({
					eventKey:key,
					eventName:eventName,
					script:eventEditors[i].editor.getValue()
				});
			}
			
			var configJson={
				configs:configs,
				events:events
			};
			
			_SubmitJson({
				url:__rootPath+'/bpm/core/bpmNodeSet/saveNodeConfig.do',
				method:'POST',
				data:{
					solId:solId,
					nodeType:nodeType,
					nodeId:nodeId,
					actDefId:actDefId,
					configJson:mini.encode(configJson)
				},
				success:function(text){
					CloseWindow();
				}
			});
		}
		
		function showFormFieldDlg(){
			var nodeId='${param.nodeId}';
			var actDefId='${param.actDefId}';
			var solId='${param.solId}';
			_OpenWindow({
				title:'选择表单字段',
				height:450,
				width:680,
				url:__rootPath+'/bpm/core/bpmSolution/modelFieldsDlg.do?solId='+solId+'&nodeId='+nodeId+'&actDefId='+actDefId,
				ondestroy:function(action){
					if(action!='ok') return;
					var iframe=this.getIFrameEl();
					var fields=iframe.contentWindow.getSelectedFields();
					for(var i=0;i<fields.length;i++){
						var f=fields[i].formField.replace('.','_');
						var field= "\${"+  f + "}";
						appendScript( field);
					}
				}
			});
		}
		
		function selDataSource(e){
			var btn=e.sender;
			_OpenWindow({
				title:'数据源选择',
				height:480,
				width:700,
				url:__rootPath+'/sys/core/sysDatasource/dialog.do',
				ondestroy:function(action){
					if(action!='ok') return;
					var iframe=this.getIFrameEl();
					var data=iframe.contentWindow.GetData();
					btn.setText(data.alias);
					btn.setValue(data.alias);
				}
			});
		}
	</script>
</body>
</html>
