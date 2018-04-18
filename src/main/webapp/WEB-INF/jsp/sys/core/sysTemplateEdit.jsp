<%-- 
    Document   : 系统模板编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统模板编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />

<!-- code codemirror start -->

<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/xml/xml.js"></script>
<script src="${ctxPath}/scripts/codemirror/addon/mode/multiplex.js"></script>


<!-- code minitor end -->

</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${sysTemplate.tempId}" />
	<div id="p1" class="form-outer2">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="tempId" class="mini-hidden"
					value="${sysTemplate.tempId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>系统模板基本信息</caption>

					<tr>
						<th width="120">模板分类：</th>
						<td width="*">
							<input id="treeId" name="treeId" class="mini-treeselect" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_TEMPLATE" 
							 	multiSelect="false" textField="name" valueField="treeId" parentField="parentId"  required="true" value="${sysTemplate.treeId}"
						        showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onClearTree" onvaluechanged="selectTree"
						        popupWidth="300" style="width:300px"/>
						     <input class="mini-hidden" name="catKey" id="catKey" value="${sysTemplate.catKey}"/>
						</td>
				</tr>

					<tr>
						<th>模板名称 <span class="star">*</span> ：
						</th>
						<td><input name="name" value="${sysTemplate.name}" class="mini-textbox" vtype="maxLength:80" style="width: 90%" required="true" emptyText="请输入模板名称" /></td>
					</tr>

					<tr>
						<th>模板KEY <span class="star">*</span> ：
						</th>
						<td><input name="key" value="${sysTemplate.key}" class="mini-textbox" vtype="maxLength:50" style="width: 90%" required="true" emptyText="请输入模板KEY" />
						</td>
					</tr>

					<tr>
						<th>是否系统缺省 <span class="star">*</span> ：
						</th>
						<td>
							<ui:radioBoolean name="isDefault" required="true" emptyText="请输入是否系统缺省" value="${sysTemplate.isDefault}"/>
						</td>
					</tr>
					<tr>
						<th>模板描述 ：</th>
						<td><textarea name="descp" class="mini-textarea" vtype="maxLength:500" style="width: 90%">${sysTemplate.descp}</textarea></td>
					</tr>
				</table>
				<br/>
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>模板内容</caption>
					<tr>
						<td colspan="2">
						
						<textarea id="content"
								vtype="maxLength:65535" style="width: 90%;height:400px;" required="true" 
								emptyText="请输入模板内容">${sysTemplate.content}</textarea></td>
					</tr>
				</table>
				
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/core/sysTemplate" entityName="com.redxun.sys.core.entity.SysTemplate"/>
	<script type="text/javascript">

	CodeMirror.defineMode("mycode", function(config) {
		  return CodeMirror.multiplexingMode(
		    CodeMirror.getMode(config, "text/html"),
		    {open: "<<", close: ">>",
		     mode: CodeMirror.getMode(config, "text/plain"),
		     delimStyle: "delimit"}
		    // .. more multiplexed styles can follow here
		  );
		});
	var editor = CodeMirror.fromTextArea(document.getElementById("content"), {
		  mode: "mycode",
		  lineNumbers: true,
		  lineWrapping: true
	});
	
	function onClearTree(e){
		var tree=e.sender;
		tree.setValue('');
		tree.setText('');
	}
	
	function selectTree(e){
		var tree=e.sender;
		var node=tree.getSelectedNode();
		if(node!=null){
			mini.get('catKey').setValue(node.key);
		}
	}
	
	//提交前取得formData
	function handleFormData(formData){
		formData.push({name:'content',value:editor.getValue()});
		return {
			isValid:true,
			formData:formData
		};
	}
	/*$("#content").val($("#contentCode").html());
	/*
		 var editor = CodeMirror.fromTextArea(document.getElementById("content"), {
	        lineNumbers: true,
	        mode: "application/x-ejs",
	        indentUnit: 4,
	        indentWithTabs: true
      	});*/
	</script>
</body>
</html>