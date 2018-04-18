<%-- 
    Document   : 业务流程解决方案编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务流程解决方案编辑</title>
<%@include file="/commons/edit.jsp"%>
<style>
	#edui1_iframeholder{
		height: 245px !important;
	}
	
	#edui1{
		width: 100% !important;
	}
	.mini-ueditor-td .mini-panel-viewport>.edui-default{
		overflow: auto !important;
	}
</style>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${bpmSolution.solId}" hideRecordNav="${param['hideNav']}"/>
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
				<input id="pkId" name="solId" class="mini-hidden" value="${bpmSolution.solId}" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<caption>业务流程解决方案基本信息</caption>
					<tr>
						<th>
							<span class="starBox">
								解决方案名称 <span class="star">*</span>
							</span>
						</th>
						<td>
							<input 
								name="name" 
								value="${bpmSolution.name}" 
								class="mini-textbox" 
								vtype="maxLength:100" 
								required="true" 
								emptyText="请输入解决方案名称" 
								style="width:90%"
							/>
						</td>
						<th>
							<span class="starBox">
								标  识  键 <span class="star">*</span>
							</span>
						</th>
						<td>
							<input 
								name="key" 
								value="${bpmSolution.key}" 
								class="mini-textbox" 
								vtype="key,maxLength:100" 
								required="true" 
								emptyText="请输入标识键" 
								style="width:90%"
							/>
						</td>
					</tr>
					<c:if test="${not empty bpmSolution.actDefId}">
					<tr>
						<th>当前启用版本</th>
						<td colspan="3">
							<a href="#" onclick="designDef('${bpmDef.modelId}')">${bpmDef.subject}(版本号:${bpmDef.version})</a>
						</td>
					</tr>
					</c:if>
					<tr>
						<th>流程定义绑定</th>
						<td >
							<input 
								class="mini-buttonedit" 
								name="defKey" 
								value="${bindBpmDef.key}" 
								text="${bindBpmDef.subject}" 
								required="true" 
								onbuttonclick="selectBpm" 
								allowInput="false" 
								style="width:80%"
							/>
						</td>
						<th>流程版本</th>
						<td >
    							<c:if test="${empty bpmSolution.defKey}">
	                      		<input id="bpmDefVersion" class="mini-combobox" style="width:150px;" textField="version" valueField="actDefId" name="actDefId"
    							 required="true"  />
    							</c:if>
    							<c:if test="${not empty bpmSolution.defKey}">
	    							<input 
	    								id="bpmDefVersion" 
	    								class="mini-combobox" 
	    								style="width:150px;" 
	    								textField="version" 
	    								valueField="actDefId" name="actDefId"
	    								url="${ctxPath}/bpm/core/bpmDef/getVersionsByKey.do?defKey=${bpmSolution.defKey}" 
	    								required="true"  
	    								value="${bpmSolution.actDefId}"
    								/>
	    							<a class="mini-button" iconCls="icon-flow-deploy" onclick="enabledVersion()">启用该版本</a>
	    							<a class="mini-button" iconCls="icon-setting" onclick="configSolution()">配置方案</a>
    							</c:if>
						</td>
					</tr>
					
					<tr>
							<th>分　　类</th>
							<td >
								 <input 
								 	id="treeId" 
								 	name="treeId" 
								 	class="mini-treeselect" 
								 	url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_BPM_SOLUTION" 
								 	multiSelect="false" 
								 	textField="name" 
								 	valueField="treeId" 
								 	parentField="parentId"  
								 	required="true" 
								 	value="${bpmSolution.treeId}"
							        showFolderCheckBox="false"  
							        expandOnLoad="true" 
							        popupWidth="350" 
						        />
							</td>
							<th>正　　式</th>
							<td >
								 <input 
								 	name="formal" 
								 	class="mini-combobox"   
								 	textField="text" 
								 	valueField="id" 
								 	emptyText="请选择..."
      								value="${bpmSolution.formal}"  
      								required="true" 
      								data="[{id:'no',text:'测试'},{id:'yes',text:'正式'}]" 
   								/>  
							</td>
					</tr>
					
					<tr>
						<th>解决方案描述</th>
						<td colspan="3">
							<div id="descp" name="descp" class="mini-ueditor"  style="height:360px;width:100%;"  ></div>
						</td>
					</tr>
				</table>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/core/bpmSolution" />
	<script type="text/javascript">
		var bpmSolution=null;
		//返回成功保存的流程定义
		function getJsonData(){
			return bpmSolution;
		};
		//成功回调函数，由formScript标签里定义的saveData进行回调
		function successCallback(responseText){
			var result=mini.decode(responseText);
			if(result.success){
				bpmSolution=result.data;
				CloseWindow('ok');
			}else{
				CloseWindow('cancel');
			};
		};
		function onClearTree(e){
			var tree=e.sender;
			tree.setValue('');
			tree.setText('');
		};
		function designDef(modelId){
    		_OpenWindow({
    			width:800,
    			height:600,
    			max:true,
    			url:__rootPath+'/process-editor/modeler.jsp?modelId='+modelId,
    			title:'流程建模设计',
    			ondestroy:function(action){
    				if(action!='ok')return;;
    				var combo=mini.get('bpmDefVersion');
    				combo.load('${ctxPath}/bpm/core/bpmDef/getVersionsByKey.do?defKey=${bpmDef.key}');
    			}
    		});
    	};
		
		function selectBpm(e){
			var btn=e.sender;
			_OpenWindow({
				title:'流程定义选择',
				height:500,
				width:800,
				url:__rootPath+'/bpm/core/bpmDef/dialog.do?single=true',
				ondestroy:function(action){
					if(action!='ok')return;
					var bpmDef=this.getIFrameEl().contentWindow.getBpmDef();
					if(bpmDef==null)return;
					btn.setValue(bpmDef.key);
					btn.setText(bpmDef.subject);
					var combo=mini.get('bpmDefVersion');
					if(combo){
						combo.load('${ctxPath}/bpm/core/bpmDef/getVersionsByKey.do?defKey='+bpmDef.key);
					}
				}
			});
		};
		
		
		function enabledVersion(){
			var actDefId=mini.get('bpmDefVersion').getValue();
			_SubmitJson({
				url:__rootPath+'/bpm/core/bpmSolution/deploy.do',
				data:{
					solId:'${bpmSolution.solId}',
					actDefId:actDefId
				},success:function(result){
					location.reload();
				}
			});
		};
		//
		function configSolution(){
			var actDefId=mini.get('bpmDefVersion').getValue();
			_OpenWindow({
				iconCls:'icon-mgr',
    			title:'流程解决方案配置',
    			url:'${ctxPath}/bpm/core/bpmSolution/mgr.do?solId=${bpmSolution.solId}&actDefId='+actDefId,
    			width:800,
    			height:450,
    			max:true
			});
		};
		
		function handleFormData(data){
			var result={isValid:true,formData:data};
			var descp=mini.get("descp").getValue();
			data.push({name:"descp",value:descp});
			return result;
		};
		
		addBody();
		
	</script>
</body>
</html>