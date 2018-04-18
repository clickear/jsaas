<%-- 
    Document   : [OdDocument]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>收文编辑</title>
<%@include file="/commons/edit.jsp"%>
<%-- <link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" /> --%>
<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.min.js" type="text/javascript"></script>
<script type="text/javascript">
var privacy=[{ id: 'COMMON', text: '普通' }, { id: 'SECURITY', text: '秘密'}, { id: 'MIDDLE-SECURITY', text: '机密'}, { id: 'TOP SECURITY', text: '绝密'}];
var urgent=[{ id: 'COMMON', text: '普通' }, { id: 'URGENT', text: '紧急'}, { id: 'URGENTEST', text: '特急'}, { id: 'MORE_URGENT', text: '特提'}];
var archType=[{ id: '0', text: '发文' }, { id: '1', text: '收文'}];
var docstatus=[{ id: '0', text: '拟稿状态' }, { id: '1', text: '发文状态'},{ id: '2', text: '归档状态'}];
var yesno=[{ id: 'YES', text: '是' }, { id: 'NO', text: '否'}];
</script>

<style type="text/css">
html,body {
	padding: 5;
	margin: 5;
	border: 0;
}

.redfont {
	font-family: cursive;
	font-size: 13pt;
	font-weight: 600;
	font-style: normal;
	color: #515151;
	font-variant: normal;
	line-height:22px;
}

.form-outer {
	border: none;
	border-top: 0;
	padding: 2px;
}

.form-inner {
	padding: 5px;
}

.form-table {
	border-collapse: collapse;
	border: solid 1px #31859B;
	width: 100%;
	margin-top: 6px;
}

form-table td,th {
	padding: 5px;
}

.form-table-td {
	border: solid 1px #31859B;
	padding: 5px;
	text-align: left;
}

.form-table-th {
	border: solid 1px #31859B;
	font-weight: bold;
	padding: 5px;
	white-space: nowrap;
	text-align: right;
}

.table-detail {
	border-collapse: collapse;
	border: solid 1px #31859B;
	width: 100%;
	margin-top: 6px;
}

.table-detail>tbody>tr>th {
	border: solid 1px #31859B;
	text-align: right;
	font-weight: bold;
	padding: 4px;
}

.table-detail>tbody>tr>td {
	border: solid 1px #31859B;
	text-align: left;
	padding: 4px;
	border: solid 1px #31859B;
}

.table-detail .mini-checkboxlist td {
	border: none;
}

.table-detail .mini-radiobuttonlist td {
	border: none;
}

.upload-panel {
	font-size: 9pt;
	font-family: Tahoma, Verdana, 宋体;
	overflow: hidden;
	position: relative;
	outline: none;
	padding: 2px;
	border: solid 1px #ccc;
}

.asLabel .mini-textbox-border,.asLabel .mini-textbox-input,.asLabel .mini-buttonedit-border,.asLabel .mini-buttonedit-input,.asLabel .mini-textboxlist-border
	{
	border: 0;
	background: none;
	cursor: default;
}

.asLabel .mini-buttonedit-button,.asLabel .mini-textboxlist-close {
	display: none;
}

.asLabel .mini-textboxlist-item {
	padding-right: 8px;
}
</style>



</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${odDocument.docId}"  excludeButtons="hideRecordNav,remove,prevRecord,nextRecord,save">
	<div class="self-toolbar">
	 <!-- <a class="mini-button" iconCls="icon-oddraft" plain="true" onclick="draftSave()">暂存</a> -->
	 <a class="mini-button" iconCls="icon-oddraft" plain="true" onclick="receive()">接收</a>
	</div>
	</rx:toolbar>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="docId" class="mini-hidden"  />
				<table class="table-detail column_2" cellspacing="1" cellpadding="0">
					<p align="center" class="redfont" style="font-size: 20pt;margin-bottom: 0px;margin-top: 10px;">收文信息</p>
					<tr>
						<th><div class="redfont">文件标题</div>
						</th>
						<td><input name="subject" value="${odDocument.subject}" class="mini-textbox asLabel" vtype="maxLength:200" readonly="readonly" style="width: 90%"
							required="true" emptyText="请输入文件标题" /></td>
							
						<th><div class="redfont">公文的类型</div></th>
						<td>
							<input  name="docType" class="mini-treeselect asLabel"  popupWidth="200"  url="${ctxPath}/sys/core/sysDic/listByKey.do?key=_GW_"  expandOnLoad="true"
							valueField="dicId" textField="name" readonly="readonly" parentField="parentId" multiSelect="false" onbeforenodeselect="beforenodeselect" showRadioButton="true" showFolderCheckBox="false" value="${odDocument.docType}"  />
						</td>
					
					</tr>
					<tr>
					<th><div class="redfont">发文字号</div></div>
						</th>
						<td><input  id="issueNo" name="issueNo" value="${odDocument.issueNo}" class="mini-textbox asLabel" vtype="maxLength:100" style="width: 90%" readonly="readonly"
							required="true" emptyText="请输入发文字号" /></td>
							<th><div class="redfont">发布日期</div>
						</th>
						<td>
							<input id="issuedDate" name="issuedDate" value="${odDocument.issuedDate}" showTime="true" readonly="readonly" allowInput="false" class="mini-datepicker asLabel" format="yyyy-MM-dd HH:mm:ss"  timeFormat="HH:mm:ss" style="width: 100%" />
							</td>
					</tr>
					<tr>
					<th><div class="redfont">发文单位</div></div>
						</th>
						<td>
						<input id="issueDepId" name="issueDepId" value="${odDocument.issueDepId}" text="${issueDep}" class="mini-buttonedit asLabel"
							emptyText="缺少发文单位" allowInput="false" readonly="readonly"   style="width:80%;"/>
						</td>
						<th ><div class="redfont">公文自编号</div></th>
						<td >${odDocument.selfNo}<%-- <input name="selfNo" value="${odDocument.selfNo}" class="mini-textbox" vtype="maxLength:100" style="width: 100%" /> --%>
						</td>
					</tr>
					<tr id="joinDeps" style="display: none;">
					<th ><div class="redfont">联合发文部门</div></th>
						<td colspan="3"><input id="joinDepIds" name="joinDepIds" value="${odDocument.joinDepIds}" text="${joinDeps}" class="mini-buttonedit asLabel"
							emptyText="缺少联合发文部门" allowInput="false" readonly="readonly" onbuttonclick="onMutiSelectDept('joinDepIds')"  style="width:100%;"/></td>
					
					</tr>
					<tr>
					<th  ><div class="redfont">签　　收</div></th>
						<td   colspan="3">
						</td>
					</tr>
					<tr>
					
					</tr>
					<tr>
					            	<th><div class="redfont">打印份数</div></th>
						<td>${odDocument.printCount}<%-- <input name="printCount" value="${odDocument.printCount}" class="mini-spinner" minValue="0"  maxValue="100000"style="width: 100%" /> --%>
						</td>                 
					
						<th><div class="redfont">秘密等级</div></th>
						<td>${privacy}<%-- <input name="privacyLevel" id="privacyLevel" class="mini-combobox  asLabel" style="width:100%;" textField="text" valueField="id" readonly="readonly"
							data="privacy" value="${odDocument.privacyLevel}" required="false" allowInput="false" /> --%>

						</td>
						
						
					</tr>
					
					<tr>
					<th><div class="redfont">保密期限(年)</div></th>
						<td><input name="secrecyTerm" value="${odDocument.secrecyTerm}" class="mini-spinner  asLabel" minValue="0"  style="width:100%"  readonly="readonly"/></td>
						<th><div class="redfont">紧急程度</div></th>
						<td>${urgent}
              <%-- <input name="urgentLevel" id="urgentLevel" class="mini-combobox  asLabel" style="width: 100%; color: #000000;" textField="text" readonly="readonly"
							valueField="id" data="urgent" value="${odDocument.urgentLevel}" required="false" allowInput="false" /> --%>
						</td>
						
						
					
					</tr>
					
					<tr>
					
						<th><div class="redfont">协办部门</div></th>
						<td>${assDepName}
<%-- <input id="assDepId" name="assDepId" value="${odDocument.assDepId}" text="${assDep}" class="mini-buttonedit"
							emptyText="请选择..." allowInput="false" onbuttonclick="onSelectDept('assDepId')"  style="width: 100%"/> --%>
						</td>
					<th><div class="redfont">承办部门</div></th>
						<td>${takeDepName}
<%-- <input id="takeDepId" name="takeDepId" value="${odDocument.takeDepId}" text="${takeDep}" class="mini-buttonedit"
							emptyText="请选择..." allowInput="false" onbuttonclick="onSelectDept('takeDepId')"  style="width: 100%"/> --%>
						</td>
					
					</tr>
					
					<tr>
						
					
						
					</tr>
					
					<tr>
					<th><div class="redfont">附　件</div></th>
						<td colspan="3">
						<input name="ffileNames" class="upload-panel" id="fileNames" style="width: 100%" allowupload="false"
						label="附件" fname="attachFiles" allowlink="true" zipdown="true" fileNames="${fileNames}"  fileIds="${fileIds}" style="width: 90%"/>
						
						
					</tr>
					<tr>
					<th><div class="redfont">主题词</div></th>
						<td colspan="3">${odDocument.keywords}<%-- <textarea name="keywords" class="mini-textarea" vtype="maxLength:256" style="width: 100%">${odDocument.keywords}</textarea> --%></td>
						
						</td>
						</tr><tr>
						<th><div class="redfont">内容简介</div></th>
						<td colspan="3">${odDocument.summary}<%-- <textarea name="summary" class="mini-textarea" vtype="maxLength:1024" style="width: 100%">${odDocument.summary}</textarea> --%></td>
					</tr>
					
					
						
					
					<tr style="display: none;">
					<th>发文机关或部门</th>
						<td><input name="issueDepId" value="${odDocument.issueDepId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" />
						</td>
					  <th>主送单位 </th>
						<td><input name="mainDepId" value="${odDocument.mainDepId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" />
						</td>  
					<th>原公文ID </th>
						<td><input name="orgArchId" value="${odDocument.docId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" />
						</td>
					<th>公文状态</th>
						<td><input name="status" value="${odDocument.status} " class="mini-textbox" vtype="maxLength:100" style="width: 90%" />
						</td>
					<%-- <th>抄送单位或部门 </th>
						<td><input name="ccDepId" value="${odDocument.ccDepId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" />
						</td> --%>
						<th>发文人ID </th>
						<td><input name="issueUsrId" value="${odDocument.issueUsrId}" class="mini-textbox" />
						</td>
						<th>发文分类ID </th>
						<td><input name="treeId" value="${odDocument.treeId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
						<th>是否接收公文 </th>
						<td><input name="isDispatchDoc" value="${isDispatchDoc}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
						<th>收发文类型 
						</th>
						<td>
						<div name="archType" id="archType" class="mini-radiobuttonlist" repeatItems="1" repeatLayout="table" repeatDirection="vertical"
    textField="text" valueField="id" value="1"   ><!--收文   -->
							</td>
							
							
							<%-- <th>是否联合发文件 </th>
						<td>
<div name="isJoinIssue" id="isJoinIssue" class="mini-radiobuttonlist" repeatItems="1" repeatLayout="table" repeatDirection="vertical"
    textField="text" valueField="id" value="${odDocument.isJoinIssue}"   data="yesno" >
						</td> --%>
					</tr>
					
					<tr>
					<td colspan="6"><div style="width:auto;height:480px;">
					<OBJECT id="WebOffice" name="WebOffice" width=100% height=800
									classid="clsid:8B23EA28-2009-402F-92C4-59BE0E063499"
									codebase="<%=request.getContextPath()%>/scripts/iweboffice/iWebOffice2009.cab#version=10,1,0,0"> </OBJECT>
					</div></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	mini.parse();
	
	//如果联合发文部门的id为空则隐藏
	$(function(){
		if(${!empty odDocument.joinDepIds}){
			$("#joinDeps").show();
		}
	});
	
	
	var WebOffice=document.getElementById('WebOffice');
	window.onload=function(){
		var type = ".doc";
		var docPath="${odDocument.bodyFilePath}";
		if(docPath.length==0){
			docPath=" ";
		}
		try{
			WebOffice.WebUrl= "<%=request.getContextPath()%>/iWebOffice/docOfficeServer.jsp?docPath="+ docPath; //处理页地址，这里用的是相对路径
			WebOffice.RecordID = "65422345"; //文档编号
			WebOffice.Template = "12345678"; //模板编号
			WebOffice.FileName = "65422345" + type; //文件名称
			WebOffice.FileType = type; //文件类型
			WebOffice.UserName = "kinggrid"; //用户名
			WebOffice.EditType = "0"; //编辑类型 
			WebOffice.PenColor = "#FF0000"; //默认手写笔迹颜色
			WebOffice.PenWidth = "1"; //默认手写笔迹宽度
			WebOffice.Print = "1"; //是否打印批注
			WebOffice.ShowToolBar = "0"; //关闭工具栏
			WebOffice.WebOpen(false);
			WebOffice.OfficeSize = '100%';
			WebOffice.ShowMenu = "0";
		} catch (e) {
			alert("金格控件请使用IE浏览器");
		}
	}
	
	function onSelectDept(blockName){
		_DepDlg(true,function(groups){
			 var groupId = groups.groupId;	
	            var groupName = groups.name;	
	            mini.get(blockName).setValue(groupId);
	            mini.get(blockName).setText(groupName);
	});
	}
	
	
	function beforenodeselect(e) {
        //禁止选中父节点
        if (e.isLeaf == false) e.cancel = true;
    }
	
	//上传附件
	$(function(){
		$('.upload-panel').each(function(){$(this).uploadPanel();});
	});
	
	
	//重写了saveData方法
	function selfSaveData(issave){
		var form = new mini.Form("form1");
		form.validate();
	    if (!form.isValid()) {//验证表格
	        return; }
	    var formData=$("#form1").serializeArray();
	    //加上租用Id
	    if(tenantId!=''){
	        formData.push({
	        	name:'tenantId',
	        	value:tenantId });
	    }
	    try{WebOffice.WebSave(true);}catch(e){
	    	alert("金格控件请使用IE浏览器");
	    }
		var bodyFilePath = WebOffice.WebGetMsgByName("docPath");
		formData.push({
			name : "bodyFilePath",
			value : bodyFilePath
		});
		formData.push({
			name : "odDocFlow",
			value : "REC"
		});
	    
	  
	    _SubmitJson({
	    	url:"${ctxPath}/offdoc/core/odDocument/save.do?",
	    	method:'POST',
	    	data:formData,
	    	success:function(result){
	    		//如果存在自定义的函数，则回调
	    		if(isExitsFunction('successCallback')){
	    			successCallback.call(this,result);
	    			return;	
	    		}
	    		var pkId=mini.get("pkId").getValue();
	    		//为更新
	    		if (pkId!=''){
	    			CloseWindow('ok');
	    			return;
	    		}
	    		CloseWindow('ok');} });
	}
	
	//保存草稿
	/* function draftSave(){
        form.validate();
        if (!form.isValid()) {
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
       var draft="YES";
        _SubmitJson({
        	url:"${ctxPath}/offdoc/core/odDocument/save.do?draft="+draft,
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
        });
} */
	
	//选择部门
	function onSelectDept(blockName){
		_DepDlg(true,function(groups){
			 var groupId = groups.groupId;	
	            var groupName = groups.name;	
	            mini.get(blockName).setValue(groupId);
	            mini.get(blockName).setText(groupName);
	});
	}
	//多选择部门
	function onMutiSelectDept(blockName){
		var groupId="";
		var groupName="";
		_DepDlg(false,function(groups){
			for(var i=0;i<groups.length;i++){
				groupName+=groups[i].name+",";
				groupId+=groups[i].groupId+",";
			}
			groupId=groupId.substring(0,groupId.length-1);
			groupName=groupName.substring(0,groupName.length-1);
           mini.get(blockName).setValue(groupId);
           mini.get(blockName).setText(groupName); 
	});
	}
	//收文
	function receive(){
		$.ajax({
			url:'${ctxPath}/offdoc/core/odDocRec/haveIncoming.do',
			type:'post',
			data:{pkId:'${recId}'},
			success:function(result){
				CloseWindow('ok');
			}
		});
		
	}
	</script>
	<rx:formScript formId="form1" baseUrl="offdoc/core/odDocument" entityName="com.redxun.offdoc.core.entity.OdDocument" />
</body>
</html>