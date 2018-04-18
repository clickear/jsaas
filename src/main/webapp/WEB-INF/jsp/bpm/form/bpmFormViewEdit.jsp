<%-- 
    Document   : 业务表单视图编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<title>业务表单视图编辑</title>
<%@include file="/commons/edit.jsp"%>

<link href="${ctxPath}/scripts/ueditor/form-design/css/toolbars.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/ueditor-fd-config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/ueditor-fd.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/ueditor-form.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/lang/zh-cn/zh-cn.js"></script>
<!-- 引入表单控件 -->
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/design-plugin.js"></script>
<link href="${ctxPath}/scripts/form/tab/css/tab.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/form/tab/PageTab.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/form/tab/FormContainer.js"></script>
<style type="text/css">
html,body
{
    width:100%;
    height:100%;
    border:0;
    margin:0;
    padding:0;
    overflow:auto;
}
pre{
	line-height: 16px;
}

.edui-default .edui-editor{
	border: none !important;
}
</style>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom">
		<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveExit">保存</a>
		<c:if test="${not empty bpmFormView.viewId}">
			<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveNoExit">暂存</a>
		</c:if>
		<a class="mini-button" iconcls="icon-next" plain="true" onclick="nextStep">生成元数据</a> 
				
		<c:if test="${not empty bpmFormView.boDefId && bpmFormView.status=='INIT' }">
			<a class="mini-button" iconCls="icon-select" plain="true" onclick="deploy()">发布</a>
		</c:if>
				
		<c:if test="${not empty bpmFormView.boDefId && bpmFormView.status=='DEPLOYED' }">
			<a class="mini-button" iconcls="icon-select" plain="true" onclick="deployNew()">发布新版</a> 
		</c:if>
		<a class="mini-button" iconcls="icon-preview" plain="true" onclick="preview()">预览</a> 
				
		<c:if test="${not empty bpmFormView.boDefId}">
			<a class="mini-button" iconcls="icon-preview" plain="true" onclick="previewRight()">权限预览</a> 
		</c:if>
		<a class="mini-button" iconcls="icon-tool" plain="true" onclick="showTool">工具面板</a>
<!-- 	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a> -->
    </div>
	
	<form id="form1" method="post" >
		<ul>
			<li>
				<table class="table-detail" cellspacing="0" cellpadding="0">
					<tr>
						<th style="width:10%">
							<span class="starBox">
								分　　类  <span class="star">*</span>
							</span>
						</th>
						<td style="width:15%">
							<input id="pkId" name="viewId" class="mini-hidden" value="${bpmFormView.viewId}" />
							 <input 
							 	id="treeId" 
							 	name="treeId" 
							 	class="mini-treeselect" 
							 	url="${ctxPath}/sys/core/sysTree/listAllByCatKey.do?catKey=CAT_FORM_VIEW" 
							 	multiSelect="false" 
							 	textField="name" 
							 	valueField="treeId" 
							 	parentField="parentId"  
							 	required="true" 
							 	value="${bpmFormView.treeId}" 
							 	pinyinField="right"
						        showFolderCheckBox="false"  
						        expandOnLoad="true" 
						        showClose="true" 
						        oncloseclick="onClearTree" 
						        popupWidth="300" 
						        style="width:92%"
					        />
						</td>
						<th style="width:10%"> 
							<span class="starBox">
								名　　称 <span class="star">*</span>
							</span>
						</th>
						<td style="width:15%">
							<input 
								name="name" 
								value="${bpmFormView.name}" 
								class="mini-textbox" 
								vtype="maxLength:255" 
								style="width:92%" 
								required="true" 
								emptyText="请输入名称" 
							/>
						</td>
						<th style="width:10%">标   识   键 <span class="star">*</span></th>
						<td style="width:15%">
							<input 
								name="key" 
								value="${bpmFormView.key}" 
								class="mini-textbox" 
								<c:if test="${not empty bpmFormView.boDefId}">readonly="true"</c:if> 
								vtype="key,maxLength:100" 
								style="width:92%" 
								required="true" 
								emptyText="请输入标识键"  
							/>
						</td>
						<th style="width:10%">展示模式 </th>
						<td style="width:15%">
							
							<input name="type" class="mini-hidden"   value="ONLINE-DESIGN" />
						<div 
							name="displayType" 
							id="displayType" 
							class="mini-radiobuttonlist" 
	 						textField="text" 
	 						valueField="id" 
	 						data="[{'id':'first','text':'展示首tab'},{'id':'normal','text':'默认模式'}]" 
	 						value="${bpmFormView.displayType}"   
 						>
						</td>
					</tr>
				</table>
			</li>
			<li>
				<script id="templateView" name="templateView" type="text/plain"></script>
				<div id="pageTabContainer"></div>
			</li>
		</ul>
	</form>
	
	<rx:formScript formId="form1" baseUrl="bpm/form/bpmFormView" entityName="com.redxun.bpm.form.entity.BpmFormView" />
	<script type="text/javascript">
		var tabsIndex=1;//tabs的下标
		mini.layout();
		var templateView = UE.getEditor('templateView',{
			initialFrameHeight: $(document).height()-180,
			initialFrameWidth:$(document).width()-10});
		templateView.addListener("ready", function () {
			//采用后加载方式加载html编辑器的内容
			var pkId=mini.get('pkId').getValue();
			if(pkId.trim()==''){
				initTab("");
				return;
			}
			_SubmitJson({
				url:__rootPath+'/bpm/form/bpmFormView/getTemplateView.do?viewId='+pkId,
				showMsg:false,
				success:function(result){
					initTab(result.data);
				}
			});
		});
		
		function exePlugin(pluginName){
			templateView.execCommand(pluginName);
		}
		function showTool(){
			_OpenWindow({
				title:'工具面板',
				width:800,
				height:500,
				url:__rootPath+'/bpm/panel.do',
			});
		}
		function nextStep(){
			$("#pageList").children("li:first-child").click();//点击第一条tabs触发保存
			formContainer.aryForm[0]=templateView.getContent();
			var form=new mini.Form("form1");
			form.validate();
			if(!form.isValid()) return;
			
			var formData=form.getData();
			
			//保存表单标题和内容。
			var result=formContainer.getResult();
			formData.title=result.title;
			formData.templateView=result.form;
			var objTree=mini.get("treeId");
			var category=objTree.getText();
			formData.category=category;
			formData.treeId=objTree.getValue();
			_OpenWindow({
				url:__rootPath+'/bpm/form/bpmFormView/saveEnt.do',
				title:'生成业务模型并保存',
				height:600,
				width:900,
				onload:function(){
	            	var iframe = this.getIFrameEl().contentWindow;
	            	iframe.init(formData);
	            	iframe.setFormViewStatus(${bpmFormView.status=='DEPLOYED'});
	            },
				ondestroy:function(action){
					//关闭窗口
					if(action=="ok"){
						CloseWindow('ok');
					}
				}
			});
		}

	    function deploy() {
	    	$("#pageList").children("li:first-child").click();//点击第一条tabs触发保存
			formContainer.aryForm[0]=templateView.getContent();
	    	var form = new mini.Form("form1");
	    	form.validate();
	        if (form.isValid() == false) {
	            return;
	        }
	        
	        var formData=$("#form1").serializeArray();
            formData.push({
	        	name:'deploy',
	        	value:true
	        });
            putArrayToFormData(formData);
	        _SubmitJson({
	        	url:__rootPath+"/bpm/form/bpmFormView/save.do",
	        	method:'POST',
	        	data:formData,
	        	success:function(result){
	        		CloseWindow('ok');
	        	}
	        });
	     }
	    
	    function deployNew(){
	    	$("#pageList").children("li:first-child").click();//点击第一条tabs触发保存
			formContainer.aryForm[0]=templateView.getContent();
	    	var form = new mini.Form("form1");
	    	form.validate();
	        if (form.isValid() == false) {
	            return;
	        }
	        
	        var formData=$("#form1").serializeArray();
            formData.push({
	        	name:'deployNew',
	        	value:true
	        });
            putArrayToFormData(formData);
	        _SubmitJson({
	        	url:__rootPath+"/bpm/form/bpmFormView/save.do",
	        	method:'POST',
	        	data:formData,
	        	success:function(result){
	        		CloseWindow('ok');
	        	}
	        });
	    }
	    
	    function previewRight(){
	    	var formViewId='${bpmFormView.viewId}';
	    	preview(formViewId);
	    }
	    
	    //字段权限管理
      	function rightEdit(){
      		_OpenWindow({
				url:__rootPath+'/bpm/form/bpmFormView/rights.do?viewId=${bpmFormView.viewId}',
				title:'表单视图的字段管理--${bpmFormView.name}',
				width:780,
				height:480,
				max:true
			});
      	}
	    
      	
	    //预览
	    function preview(viewId){
	    	$("#pageList").children("li:first-child").click();//点击第一条tabs触发保存
			formContainer.aryForm[0]=templateView.getContent();
	 		if(!viewId){
	 			viewId='';
	 		}
	 		
	 		var result=formContainer.getResult();
	 		
	    	_SubmitJson({
	    		url:__rootPath+'/bpm/form/bpmFormView/parseFormTemplate.do?viewId='+viewId,
	    		method:'POST',
	    		showMsg:false,
	    		data:{
	    			templateHtml:result.form,
	    			json:'{}',
	    			tabsTitle:result.title
	    		},
	    		success:function(result){
	    		
	    			_OpenWindow({
	    	    		url:__rootPath+'/bpm/form/bpmFormView/preview.do',
	    	    		title:'表单预览',
	    	    		width:780,
	    	    		height:400,
	    	    		max:true,
	    	    		onload:function(){
	    	    			 var iframe = this.getIFrameEl();
	    	    			 var content=result.data;
	    	    			 //预留解析参数
	                         iframe.contentWindow.setContent(content,{});
	    	    		}
	    	    	});	
	    		}
	    	});
	    }
		
	
		
		function onClearTree(){
			var treeId=mini.get('treeId');
			treeId.setValue('');
			treeId.setText('');
		}
		
		//生成业务模型HTML
		function genFormHtml(){
			var templateId=mini.get('templateId').getValue();
			
			if(templateId==''){
				alert('请选择模板类型！');
				return;
			}
			var fmId=mini.get('fmId').getValue();
			_SubmitJson({
				url:__rootPath+'/bpm/form/bpmFormView/genTemplateHtml.do?templateId='+templateId + "&fmId="+fmId,
				method:'POST',
				success:function(result){
					templateView.setContent(result.data);
				}
			});
		}
		
		function saveExit(){
			selfSaveData(true);
		}
		
		function saveNoExit(){
			selfSaveData(false);
		}

		function selfSaveData(exit) {
			if(templateView.queryCommandState( 'source' )==1){
				mini.alert("请切换视图,源码模式不允许直接保存");
				return;
			}
			
			$("#pageList").children("li:first-child").click();//点击第一条tabs触发保存
			formContainer.aryForm[0]=templateView.getContent();
			var form=new mini.Form('form1');
	   		if(!validateRight()){
	   			alert("您没有添加权限,请联系管理员");
	        	return;
	   		}
	        form.validate();
	        if (!form.isValid()) {
	            return;
	        }
	        if(!checkTheTitle()){
	        	alert("请使用不重复的tab名字");
	        	return;
	        }
	    	
	        
	        var formData=$("#form1").serializeArray();
	        //加上租用Id
	        if(tenantId!=''){
		        formData.push({
		        	name:'tenantId',
		        	value:tenantId
		        });
	        }
	       
	       //若定义了handleFormData函数，需要先调用 
	       if(isExitsFunction('handleFormData')){
	        	var result=handleFormData(formData);
	        	if(!result.isValid) return;
	        	formData=result.formData;
	        }
	       putArrayToFormData(formData);
	        var config={
	        	url:__rootPath+"/bpm/form/bpmFormView/save.do",
	        	method:'POST',
	        	data:formData,
	        	success:function(result){
	        		//如果存在自定义的函数，则回调
	        		if(isExitsFunction('successCallback')){
	        			successCallback.call(this,result);
	        			return;	
	        		}
	        		if(exit){
	        			CloseWindow('ok');	
	        		}
	        	}
	        }
	        
	        if(result && result["postJson"]){
	        	config.postJson=true;
	        }
	        
	        _SubmitJson(config);
	     }
		/*检查是否有重复title*/
		function checkTheTitle() {
			var titleArray = formContainer.aryTitle;
			if (titleArray.length == unique(titleArray).length) {
				return true;
			} else {
				return false;
			}
		}
		//数组去重
		function unique(arr) {
			var result = [], hash = {};
			for (var i = 0, elem; (elem = arr[i]) != null; i++) {
				if (!hash[elem]) {
					result.push(elem);
					hash[elem] = true;
				}
			}
			return result;
		}

		//将tab的数组(包括title和html内容)存进formData
		function putArrayToFormData(formData) {
			var result = formContainer.getResult();
			var titleArray = result.title;//formContainer.aryTitle.join("#page#");
			var contentArray = result.form;//formContainer.aryForm.join("#page#");
			formData.splice(6, 1);
			formData.push({
				name : 'templateView',
				value : contentArray
			});
			formData.push({
				name : 'title',
				value : titleArray
			});
		}

		var tabTitle = "${bpmFormView.title}";

		//tab控件
		var tabControl;
		//存储数据控件。
		var formContainer;
		//添加tab页面
		function addCallBack() {
			var curPage = tabControl.getCurrentIndex();
			var str = "新页面";
			var idx = curPage - 1;
			formContainer.insertForm(str, "", idx);
			saveTabChange(idx - 1, idx);
		}
		//切换tab之前，返回false即中止切换
		function beforeActive(prePage) {
			
			return 1;
		}
		//点击激活tab时执行。
		function activeCallBack(prePage, currentPage) {
			if (prePage == currentPage)
				return;
			//保存上一个数据。
			saveTabChange(prePage - 1, currentPage - 1);
		}
		//根据索引设置数据
		function setDataByIndex(idx) {
			if (idx == undefined) return;
		
			var obj = formContainer.getForm(idx);
			templateView.setContent(obj.form || "<p/>");
			$("b", tabControl.currentTab).text(obj.title);
		}
		//在删除页面之前的事件，返回false即中止删除操作
		function beforeDell(curPage) {
			return 1;

		}

		//点击删除时回调执行。
		function delCallBack(curPage) {
			console.log(formContainer);
			formContainer.removeForm(curPage - 1);
			var tabPage = tabControl.getCurrentIndex();
			setDataByIndex(tabPage - 1);
		}
		//文本返回
		function txtCallBack() {
			var curPage = tabControl.getCurrentIndex();
			var idx = curPage - 1;
			var title = tabControl.currentTab.text();
			//设置标题
			formContainer.setFormTitle(title, idx);
		}
		//tab切换时保存数据
		function saveTabChange(index, curIndex) {
			var data = templateView.getContent();
			formContainer.setFormHtml(data, index);
			setDataByIndex(curIndex);
		}
		//表单或者标题发生变化是保存数据。
		function saveChange() {
			var index = tabControl.getCurrentIndex() - 1;
			var title = tabControl.currentTab.text();
			var data = templateView.getContent();
			formContainer.setForm(title, data, index);
		}
		
		
		
		//初始化TAB
		function initTab(formData) {
			//var formData = templateView.getContent();

			if (tabTitle == "")
				tabTitle = "页面1";
			formContainer = new FormContainer();
			var aryTitle = tabTitle.split(formContainer.splitor);
			var aryForm = formData.split(formContainer.splitor);
			var aryLen = aryTitle.length;

			//初始化
			formContainer.init(tabTitle, formData);

			tabControl = new PageTab("pageTabContainer", aryLen, {
				addCallBack : addCallBack,
				beforeActive : beforeActive,
				activeCallBack : activeCallBack,
				beforeDell : beforeDell,
				delCallBack : delCallBack,
				txtCallBack : txtCallBack
			});
			tabControl.init(aryTitle);
			if (aryLen >= 1) {
				templateView.setContent(aryForm[0]);
			}
		}
		
		
		function validateRight(){
    		if(${isSuperAdmin}){
    			return true;
    		}
    		var dataArray=mini.get("treeId").getData();
    		for(var node in dataArray){
    			if(dataArray[node].treeId==mini.get("treeId").getValue()){
    				var rightJson=JSON.parse(dataArray[node].right);
    				if(rightJson&&rightJson.add=="true"){
    					return true;
    				}
    			}
    		}
    		return false;
    	}
	</script>
</body>
</html>