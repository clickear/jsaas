<%@page contentType="text/html" pageEncoding="UTF-8" deferredSyntaxAllowedAsLiteral="true"%>
<!DOCTYPE html >
<html >
<head>
	<title>流程实例的配置页</title>
	<%@include file="/commons/edit.jsp"%>
	<!-- code codemirror start -->	
	<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
	<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js" type="text/javascript"></script>
	<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js" type="text/javascript"></script>
	<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js" type="text/javascript"></script>
	<!-- code minitor end -->
	<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/powertips/jquery.powertip.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctxPath}/scripts/jquery/plugins/powertips/jquery.powertip-blue.css" />
	<script type="text/javascript" src="${ctxPath}/scripts/common/baiduTemplate.js"></script>
	<script type="text/javascript">
	//设置左分隔符为 <!
	baidu.template.LEFT_DELIMITER='<!';
	//设置右分隔符为 <!  
	baidu.template.RIGHT_DELIMITER='!>';
	</script>
	<style type="text/css">
		.mini-grid-headerCell .mini-grid-headerCell-inner, 
		.mini-grid-topRightCell .mini-grid-headerCell-inner, 
		.mini-grid-columnproxy .mini-grid-headerCell-inner{
			color: #666;
		}
	
	</style>
</head>
<body>
	<script id="rightTemplate"  type="text/html">
		<table class="form-table" >
			<tr>
				<th>子表名</th>
				<th>权限</th>
			</tr>
		<!for(var key in data){
			var json=data[key];
		!>
			<tr class="trName" tbName="<!=key!>">
				<td class="tbName"><!=key!>(<!=json.comment!>)</td>
				<td class="tbRight">
					<input type="radio" name="<!=key!>" onclick="handType(this)" value="user" <!if(json.type=='user'){!>checked='checked'<!}!> />当前用户
					<input type="radio" name="<!=key!>" onclick="handType(this)" value="group" <!if(json.type=='group'){!>checked='checked'<!}!>/>当前用户组
					<input type="radio" name="<!=key!>" onclick="handType(this)" value="sql" <!if(json.type=='sql'){!>checked='checked'<!}!>/>SQL
					<input type="radio" name="<!=key!>" onclick="handType(this)" value="all" <!if(json.type=='all'){!>checked='checked'<!}!>/>全部
					<div class="sql" <!if(json.type!='sql'){!>style="display:none;"<!}!>>
						<div>常量:<select onchange='changeConstant(this)' style="width:200px;">
								<!for(var i=0;i<vars.length;i++){
									var obj=vars[i];
								!>
									<option value="<!=obj.key!>"><!=obj.val!></option>
								<!}!>
							</select>  字段选择:<input id="fieldTree" onvaluechanged="fieldSelect" class="mini-treeselect" url="<!=ctx!>/sys/bo/sysBoEnt/getTreeByBoDefId.do?boDefId=<!=boDefId!>" 
          expandOnLoad="true"   popupWidth="200"  parentField="pid" textField="text" valueField="id" />
							通用字段:<input id="commonTree" onvaluechanged="fieldSelect" class="mini-treeselect" url="<!=ctx!>/sys/bo/sysBoEnt/getCommonField.do" 
          expandOnLoad="true"   popupWidth="200"  parentField="pid" textField="text" valueField="id" />
</div>
						<textarea tbName="<!=key!>" style="width:600px;height:100px">
						 <!if(json.sql){!><!=json.sql!><!}else{!>select * from w_<!=key!> where REF_ID_=#{fk}  <!}!>
						</textarea>
					<div>
				</td>
			</tr>
		<!}!>
		</table>
	</script>
	<div id="toolbar1" class="mini-toolbar topBtn" >
		<a class="mini-button" plain="true" iconCls="icon-save" onclick="saveConfig">保存</a>
		<a class="mini-button" plain="true" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
	</div>
    <div class="shadowBox90 form-outer" style="padding-top: 8px;">
    	<form id="form1" >
	       	<table class="table-detail column_2_m" cellspacing="1" cellpadding="0" style="width:100%">
	              <caption>基本信息配置</caption>
	              <tr>
	             	 <th>事项标题规则</th>
	                 <td colspan="3">
					    	<input 
					    		id="subRuleEdit" 
					    		name="subRule" 
					    		class="mini-popupedit" 
					    		style="width:60%;" 
					    		textField="name" 
					    		valueField="key" 
					    		popupWidth="auto" 
								showPopupOnClick="true" 
								popup="#varPanel"  
								multiSelect="true" 
								allowInput="true" 
								value="${subRule}" 
								text="${subRule}"
							/>
							<a class="mini-button" plain="true" onclick="clickVar('createUser')" iconCls="icon-property">发起人${startUser}</a>
							<a class="mini-button" plain="true" iconCls="icon-timeinput" onclick="clickVar('createTime')">发起时间${createTime}</a>
	                 </td>
	             </tr>
	             
	             <tr>
	             	 <th>可选人员的节点</th>
	                 <td width="30%">
		                <input 
		                	id="couldSelectNode" 
		                	name="couldSelectNode" 
		                	value="${couldSelectNode}" 
		                	text="${couldSelectNodeName}" 
		                	class="mini-textboxlist" 
		                	showClose="true" 
		                	onvaluechanged="setThisName('couldSelectNode')"   
		                	allowInput="false"
	                	/>
		                <input class="mini-hidden" name="couldSelectNodeName"  id="couldSelectNodeName" value="${couldSelectNodeName}"/>
						<a class="mini-button " plain="true" iconCls="icon-add" onclick="selectNode('couldSelectNode')">选择</a>
						<a class="mini-button " plain="true" iconCls="icon-remove" onclick="clearTheButton('couldSelectNode')">清空</a>
					   </td>
	                 <th width="15%">必选人员的节点</th>
	                 <td width="30%">
		                <input 
		                	id="mustSelectNode" 
		                	name="mustSelectNode" 
		                	value="${mustSelectNode}" 
		                	text="${mustSelectNodeName}" 
		                	class="mini-textboxlist" 
		                	showClose="true" 
		                	onvaluechanged="setThisName('mustSelectNode')"  
		                	allowInput="false"
	                	/> 
		                <input class="mini-hidden" name="mustSelectNodeName"  id="mustSelectNodeName" value="${mustSelectNodeName}"/>
						<a class="mini-button " plain="true" iconCls="icon-add" onclick="selectNode('mustSelectNode')">选择</a>
						<a class="mini-button " plain="true" iconCls="icon-remove" onclick="clearTheButton('mustSelectNode')">清空</a>
					 </td>
	             </tr>
	             <tr>
	             	<th width="15%" >启动前置处理器</th>
	             	<td width="30%">
	             		<input class="east textbox" title="实现ProcessStartPreHandler接口的Spring的BeanId" name="preHandle" value="${preHandle}" style="width:160px"/>
	             	</td>
	           
	             	<th width="15%">启动后置处理器</th>
	             	<td>
	             		<input class="south textbox"  title="实现ProcessStartAfterHandler接口的Spring的BeanId" name="afterHandle" value="${afterHandle}" style="width:160px"/>
	             	</td>
	             </tr>
	              <tr>
	             	<th width="100">结束处理器</th>
	             	<td>
	             		<input class="east textbox" name="processEndHandle" value="${processEndHandle}"  title="实现ProcessEndHandler接口的Spring的BeanId" style="width:160px"/>
	             	</td>
	             	<th>跳过第一个节点</th>
	             	<td>
	             		<div name="isSkipFirst" class="mini-checkbox" checked="${isSkipFirst}" readOnly="false" falseValue-"false" trueValue="true"></div>
	             		<input class="mini-hidden" name="boDefId"  id="boDefId" value="${boDefId}"/>
	             		<input class="mini-hidden" name="dataSaveMode"  id="dataSaveMode" value="${dataSaveMode}"/>
	             	</td>
	             </tr>
	             
	              <tr>
	             	<th>通知配置</th>
	              	<td >
	              		<div 
	              			name="notices" 
	              			class="mini-checkboxlist"  
	              			value="${notices}" 
	              			textField="text" 
	              			valueField="name"  
	              			url="${ctxPath}/bpm/core/bpmConfig/getNoticeTypes.do" 
              			></div>
	              	</td>
	              	<th>跳过配置</th>
	              	<td >
	              		<div 
	              			name="jumpSetting" 
	              			class="mini-checkboxlist"  
	              			value="${jumpSetting}" 
	              			textField="val" 
	              			valueField="key"  
	              			url="${ctxPath}/bpm/core/bpmConfig/getJumpTypes.do" 
              			></div>
	              	</td>
	             </tr>
	             <tr>
	              	<th>子表权限</th>
	              	<td colspan="3">
	              		<input id="tableRightJson" name="tableRightJson" class="mini-hidden"  />
	              		<span id="spanTableRightJson"></span>
	              	</td>
	             </tr> 
	 		</table>
		</form>
    </div>
  	<div class="shadowBox90 form-outer" style="padding-top: 8px;">
  		 <table class="table-detail column_1_m" cellspacing="1" cellpadding="0" style="width:100%">
  		 <caption>事件与接口配置</caption>
  		   <c:forEach items="${eventConfigs}" var="event" varStatus="i">
	  		 <tr>
	  		 	<th>
	  		 		【${event.eventName}】事件调用配置
	  		 	</th>
	  		 	<td style="padding: 0 !important;">
	  		 		<div class="mini-toolbar" style="width:100%">
						<a class="mini-button" iconCls="icon-add" onclick="addRowGrid('eventGrid_${i.index}')">添加</a>
						<a class="mini-button" iconCls="icon-remove" onclick="delRowGrid('eventGrid_${i.index}')">删除</a>
						<a class="mini-button" iconCls="icon-up" onclick="upRowGrid('eventGrid_${i.index}')">上移</a>
						<a class="mini-button" iconCls="icon-down" onclick="downRowGrid('eventGrid_${i.index}')">下移</a>
						<a class="mini-button" iconCls="icon-edit" onclick="configRowGrid('eventGrid_${i.index}')">配置</a>
					</div>
	  		 		<div 
	  		 			id="eventGrid_${i.index}" 
	  		 			data-options="{eventKey:'${event.eventKey}',eventName:'${event.eventName}'}" 
	  		 			class="mini-datagrid"  
	  		 			allowAlternating="true"
	            		allowCellEdit="true" 
	            		allowCellSelect="true" height="auto"
						idField="id" 
						showPager="false" 
						style="width:100%;"
					>
						<div property="columns">
							<div type="indexcolumn" width="20"></div>
							<div type="checkcolumn" width="20"></div>
							
							<div field="jumpType" name="jumpType" displayField="jumpTypeName" 
							 width="100">满足的审批动作
					           <input 
					           		property="editor" 
					           		class="mini-treeselect"  
					           		multiSelect="true" 
					                textField="jumpTypeName" 
					                valueField="jumpType" 
					                parentField="parent" 
					                data="jumpTypeData"
				                />
					        </div>
							<div header="异步调用" width="100" field="async"  type="checkboxcolumn" truevalue="YES" falsevalue="NO"></div>
							<div type="comboboxcolumn" field="callType" name="callType" displayField="callTypeName" width="80" headerAlign="center">接口类型
								<input 
									property="editor" 
									class="mini-combobox" 
									data="[{id:'Script',text:'Groovy脚本'},{id:'Sql',text:'执行SQL'},{id:'ProcessCall',text:'ProcessCall调用接口'}]"
								/>
							</div>	
							<div field="callName" name="config"  width="160" headerAlign="center">调用功能描述
								<input property="editor" class="mini-textbox"/> 
							</div>
						</div>
					</div>
					<textarea id="script_${event.eventKey}" name="script" style="display:none">${event.script}</textarea>
	  		 	</td>
	  		 </tr>
  		 </c:forEach>
  		 </table>
  	</div>

 	<div id="varPanel" class="mini-panel" title="变量选择面板" iconCls="icon-add" style="width:600px;height:250px;" 
        showToolbar="true" showCloseButton="true" showHeader="false" bodyStyle="padding:0;border:0" showFooter="true">
	 	  <div 
	 	  	id="vargrid" 
	 	  	class="mini-datagrid" 
	 	  	style="width:100%;height:100%;" 
            borderStyle="border:0" 
            showPager="false" 
            onrowdblclick="varGridDblClick"
			url="${ctxPath}/bpm/core/bpmSolVar/getBySolIdActDefIdNodeId.do?solId=${param.solId}&actDefId=${param.actDefId}&nodeId=_PROCESS"
		>
	            <div property="columns">
	                <div type="checkcolumn" ></div>
	                <div field="name" width="100" headerAlign="center">变量名称</div> 
	                <div field="formField" width="100" headerAlign="center">表单字段</div>   
	                <div field="key" width="100" headerAlign="center">变量Key</div>
	                <div field="type" width="100" headerAlign="center">类型</div>                
	            </div>
	     </div>
	     <div property="footer">
	        	选择变量选择请双击表格行。
	    </div>
	 </div>
 	<br/>
	
	<script type="text/javascript">
		addBody();
		var solId="${param['solId']}";
		var actDefId="${param['actDefId']}";
		var nodeType="${param['nodeType']}";
		var nodeId="${param['nodeId']}";
		var tableRightJson=<c:choose><c:when test="${empty tableRightJson}">{}</c:when><c:otherwise>${tableRightJson}</c:otherwise></c:choose>;
		var contextVars='${contextVars}';
		var boDefId='${boDefId}';

		//编辑器存储。
		var editorJson={};
		
		mini.parse();
		var vargrid=mini.get('vargrid');
		vargrid.load();
		
		var jumpTypeData=[
			{jumpType:'AGREE',jumpTypeName:'通过'},
			{jumpType:'REFUSE',jumpTypeName:'反对'},
			{jumpType:'BACK',jumpTypeName:'驳回'},
			{jumpType:'BACK_TO_STARTOR',jumpTypeName:'驳回至发起人'},
			{jumpType:'RECOVER',jumpTypeName:'撤回'},
			{jumpType:'TIMEOUT_SKIP',jumpTypeName:'超时跳过'},
			{jumpType:'SKIP',jumpTypeName:'跳过'},
			{jumpType:'ABSTAIN',jumpTypeName:'弃权'},
			{jumpType:'INTERPOSE',jumpTypeName:'干预'}
		];
		//事件Grid
		var eventGrids=[];
		<c:forEach items="${eventConfigs}" var="event" varStatus="i">
			eventGrids.push(mini.get('eventGrid_${i.index}'));
		</c:forEach>
		for(var i=0;i<eventGrids.length;i++){
			var eventKey=eventGrids[i].eventKey;
			var eventName=eventGrids[i].eventName;
			var script=$('#script_'+eventKey).val();
			if(script&&script.trim()){
				var scriptArr=mini.decode(script);
				if(scriptArr instanceof Array){
					eventGrids[i].setData(scriptArr);
				}else{//兼容旧配置
					eventGrids[i].addRow({
						callType:'Script',
						callTypeName:'Groovy脚本',
						callName:'配置调用脚本',
						config:script
					});
				}
			}
		}

		$(function(){
			$('.east').powerTip({ placement: 'e' });
			$('.south').powerTip({ placement: 's' });
		});
		
		var couldSelectNode=mini.get("couldSelectNode");
		var mustSelectNode=mini.get("mustSelectNode");
		function clickVar(pvar){
			var subRuleEdit=mini.get('subRuleEdit');
			var newRule=subRuleEdit.getText();
			newRule=newRule + "\${"+pvar +"}";
			subRuleEdit.setText(newRule);
			subRuleEdit.setValue(newRule);
		}
		
		function varGridDblClick(e){
			var grid=e.sender;
			var record=e.record;
			
			var subRuleEdit=mini.get('subRuleEdit');
			var newRule=subRuleEdit.getText();
			newRule=newRule + "\${"+record.formField +"}";
			subRuleEdit.setText(newRule);
			subRuleEdit.setValue(newRule);
			
			subRuleEdit.hidePopup();
		}
		
				
		//选择节点
		function selectNode(buttonId){
			var elButton=mini.get(buttonId);
			var elButtonName=mini.get(buttonId+"Name")
			_OpenWindow({
				title:'选择节点',
				width:650,
				height:450,
				url:__rootPath+'/bpm/core/bpmSolution/nodeMessageSelect.do?solId='+solId,
				onload:function(){
				},
				ondestroy:function(action){
					if(action!='ok'){return;}
					var iframe = this.getIFrameEl().contentWindow;
					var nodeName=iframe.getNodeName();
					var nodeId=iframe.getNodeId();
					
					elButton.setValue(nodeId);
					elButton.setText(nodeName);
					elButtonName.setValue(nodeName);
				}
			});
			
		}
		//清空elButton的值
		function clearTheButton(buttonId){
			var elButton=mini.get(buttonId);
			var elButtonName=mini.get(buttonId+"Name");
			elButton.setValue("");
			elButton.setText("");
			elButtonName.setValue("");//清空相应的
		}
		//设置中文进去json
		function setThisName(EL){
			var elButton=mini.get(EL+"Name");//获取域里的name控件
			var ancientButton=mini.get(EL)
			var name=ancientButton.getText();
			elButton.setValue(name);
		}
		
		//配置事件接口调用
		function configRowGrid(gridId){
			var grid=mini.get(gridId);
			var row=grid.getSelected();
			if(!row){
				alert('请选择配置行！');
				return;
			}
			if(!row.callType || row.callType==''){
				alert('请选择接口类型！');
				return;
			}

			_OpenWindow({
				title:grid.eventName+'接口调用配置',
				url:__rootPath+'/bpm/core/bpmNodeSet/callConfig'+row.callType+'.do?solId='+solId+'&actDefId='+actDefId+'&nodeId='+nodeId+'&nodeType=_PROCESS',
				height:480,
				width:800,
				onload:function(){
					var win = this.getIFrameEl().contentWindow;
					win.setData(row.script);
				},
				ondestroy:function(action){
					if(action!='ok'){
						return;
					}
					var win = this.getIFrameEl().contentWindow;
					var data=win.getData();
					grid.updateRow(row,{script:data});
				}
			});
		}
		
		
		//保存配置信息
		function saveConfig(){
			var form=new mini.Form('form1');
			form.validate();
			if(!form.isValid()){
				return;
			}
			
			var couldValue=couldSelectNode.getValue();
			var mustValue=mustSelectNode.getValue();
			if(couldValue.length>1&&mustValue.length>1){
				var mustArray=mustValue.split(",");
				var couldArray=couldValue.split(",");
				for(var a=0;a<mustArray.length; a++){
					for(var b=0;b<couldArray.length;b++){
						if(mustArray[a]==couldArray[b]){
							alert("可选和必选请不要选重复节点");
							return;
						}
					}
				}
			}
			
			var configs=_GetFormJson("form1");

			var events=[];
			
			for(var i=0;i<eventGrids.length;i++){
				var key=eventGrids[i].eventKey;
				var eventName=eventGrids[i].eventName;
				events.push({
					eventKey:key,
					eventName:eventName,
					script:mini.encode(eventGrids[i].getData())
				});
			}

			var tabRightJson=getRightJson();
			
			configs.tableRightJson=tabRightJson;
			
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
		
		function getRightHtml(){
			if(!tableRightJson) return "";
			var bt=baidu.template;
			var vars =mini.decode(contextVars);
			var data={data:tableRightJson,vars:vars,boDefId:boDefId,ctx:__rootPath};
			
			var html=bt("rightTemplate",data);
			var parent=$("#spanTableRightJson");
			
			parent.html(html);
			
			$("textarea",parent).each(function(i){
				var tabName=$(this).attr("tbName");
				var editor= CodeMirror.fromTextArea(this, {
			        matchBrackets: true,
			        mode: "text/x-sql"
			      });
				editorJson[tabName]=editor;
			});
			
			$(".CodeMirror",parent).each(function(i){
				$(this).height(150);
			})

			mini.parse();
		}
		//加载权限HTML
		getRightHtml();
		
		function getRightJson(){
			var spanObj=$("#spanTableRightJson");
			var json={};
			$(".trName",spanObj).each(function(i){
				var tr=$(this);
				var tableName=tr.attr("tbName");
				var tbRight=$(".tbRight",tr);
				var val = $('input:radio:checked',tbRight).val();
				var obj={type:val};
				if(val=="sql"){
					var editor=editorJson[tableName];
					obj.sql=editor.getValue();
				}
				json[tableName]=obj;
			});
			return mini.encode (json );
		}
		
		function handType(obj){
			var jqObj=$(obj);
			var parent=$(obj).closest("td");
			var divSql=$(".sql",parent);
			var type=jqObj.val();
			if(type=="sql"){
				divSql.show();
			}
			else{
				divSql.hide();
			}
		}
	</script>
</body>
</html>
