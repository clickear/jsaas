<%-- 
    Document   : [OdDocument]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>公文编辑</title>
<%@include file="/commons/edit.jsp"%>
<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.min.js" type="text/javascript"></script>
<script type="text/javascript">
var privacy=[{ id: 'COMMON', text: '普通' }, { id: 'SECURITY', text: '秘密'}, { id: 'MIDDLE-SECURITY', text: '机密'}, { id: 'TOP-SECURITY', text: '绝密'}];
var urgent=[{ id: 'COMMON', text: '普通' }, { id: 'URGENT', text: '紧急'}, { id: 'URGENTEST', text: '特急'}, { id: 'MORE_URGENT', text: '特提'}];
var archType=[{ id: '0', text: '发文' }, { id: '1', text: '收文'}];
var docstatus=[{ id: '0', text: '拟稿状态' }, { id: '1', text: '发文状态'},{ id: '2', text: '归档状态'}];
var yesno=[{ id: 'YES', text: '是' }, { id: 'NO', text: '否'}];
</script>
<style type="text/css">
*{
	font-family: '微软雅黑';
}
.redfont {
/* 	font-family: cursive; */
	font-size: 14px;
/* 	font-weight: 600; */
	font-style: normal;
	color: #666;
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
	border: solid 1px #ececec;
 	font-weight: normal;
	padding: 5px;
/* 	white-space: nowrap; */
/* 	text-align: right; */
}

.table-detail {
	border-collapse: collapse;
	border: solid 1px #31859B;
	width: 100%;
	margin-top: 6px;
}

.table-detail>tbody>tr>th {
	border: solid 1px #ececec;
/* 	text-align: right; */
/* 	font-weight: bold; */
	padding: 4px;
}

.table-detail>tbody>tr>td {
	border: solid 1px #ececec;
	text-align: left;
	padding: 4px;
}

.table-detail .mini-checkboxlist td {
	border: none;
}

.table-detail .mini-radiobuttonlist td {
	border: none;
}

.upload-panel {
	font-size: 9pt;
	font-family: "微软雅黑";
	overflow: hidden;
	position: relative;
	outline: none;
	padding: 2px;
	border: solid 1px #ececec;
}

.asLabel .mini-textbox-border,
.asLabel .mini-textbox-input,
.asLabel .mini-buttonedit-border,
.asLabel .mini-buttonedit-input,
.asLabel .mini-textboxlist-border{
	border: 0;
	background: none;
	cursor: default;
}

.asLabel .mini-buttonedit-button,
.asLabel .mini-textboxlist-close {
	display: none;
}

.asLabel .mini-textboxlist-item {
	padding-right: 8px;
}
</style>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" excludeButtons="hideRecordNav,remove">
		<div class="self-toolbar">
			<a class="mini-button" iconCls="icon-draft" plain="true" onclick="draftSave()">暂存</a>
		</div>
	</rx:toolbar>
	
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">

			<div title="公文信息" name="odDoc">
				<div class="form-inner">
					<input id="pkId" name="docId" class="mini-hidden" value="${odDocument.docId}" />
					<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
						<caption>发文稿纸</caption>
						<tr>
							<th>
								<div class="redfont">
									<span class="starBox">
										文件标题 <span class="star">*</span>
									</span>
								</div></th>
							<td colspan="3"><input name="subject" value="${odDocument.subject}" class="mini-textbox" vtype="maxLength:200"
								style="width: 100%;" required="true" emptyText="请输入文件标题" /></td>
							</td>
						</tr>
						<tr>
							<th>
								<div class="redfont">
									<span class="starBox">	
										发文字号 <span class="star">*</span>
									</span> 
								</div></th>
							<td><input name="issueNo" value="${odDocument.issueNo}" class="mini-textbox" vtype="maxLength:100" style="width: 100%;"
								required="true" emptyText="请输入发文字号" /></td>

							<th><div class="redfont">公文的类型 </div></th>
							<td><input name="docType" class="mini-treeselect" popupWidth="200" url="${ctxPath}/sys/core/sysDic/listByKey.do?key=_GW_"
								expandOnLoad="true" valueField="dicId" textField="name" parentField="parentId" multiSelect="false" style="width: 50%;"
								onbeforenodeselect="beforenodeselect" showRadioButton="true" showFolderCheckBox="false" value="${odDocument.docType}" /></td>
						</tr>
						<tr>
							<th><div class="redfont">发文部门 </div></th>
							<td>${issueDep}<a class="mini-button" iconCls="icon-add" plain="false" onclick="showJoins()" style="float: right;">添加联合发文部门</a></td>
							<th><div class="redfont">打印份数 </div></th>
							<td><input name="printCount" value="${odDocument.printCount}" class="mini-spinner" minValue="0" style="width: 50%" /></td>
						</tr>
						<tr id="joinDeps" style="display: none;">
							<th><div class="redfont">联合发文部门 </div></th>
							<td colspan="3"><input id="joinDepIds" name="joinDepIds" value="${odDocument.joinDepIds}" text="${joinDeps}"
								class="mini-buttonedit" emptyText="请选择..." allowInput="false" onbuttonclick="onMutiSelectDept('joinDepIds')" style="width: 100%;" /></td>

						</tr>
						<tr>

							<th><div class="redfont">紧急程度 </div></th>
							<td><input name="urgentLevel" id="urgentLevel" class="mini-combobox" style="width: 150px; color: #000000;" textField="text"
								valueField="id" data="urgent" value="${odDocument.urgentLevel}" required="false" allowInput="false" /></td>
							<th><div class="redfont">秘密等级 </div></th>
							<td><input name="privacyLevel" id="privacyLevel" class="mini-combobox" style="width: 50%;" textField="text" valueField="id"
								data="privacy" value="${odDocument.privacyLevel}" required="false" allowInput="false" /></td>

						</tr>
						<tr>
							<th ><div class="redfont">签　　发</div></th>
							<td ></td>
							<th ><div class="redfont">会　　签</div></th>
							<td ></td>
						</tr>
						<tr>
							<th ><div class="redfont">审　　核</div></th>
							<td ></td>
							<th ><div class="redfont">复　　核</div></th>
							<td></td>
						</tr>
						<tr>
							<tr>
								<th><div class="redfont">抄送部门 </div></th>
								<td><input id="ccDepId" name="ccDepId" value="${odDocument.ccDepId}" text="${ccDep}" class="mini-buttonedit" emptyText="请选择..."
									allowInput="false" onbuttonclick="onMutiSelectDept('ccDepId')" style="width: 100%;" /></td>
								<th rowspan="2"><div class="redfont">保密期限(年) </div></th>
								<td rowspan="2"><input name="secrecyTerm" value="${odDocument.secrecyTerm}" class="mini-spinner" minValue="0"
									style="width: 50%" /></td>
							</tr>
							<th><div class="redfont">主送单位 </div></th>
							<td><input id="mainDepId" name="mainDepId" value="${odDocument.mainDepId}" text="${mainDep}" class="mini-buttonedit"
								required="true" emptyText="请选择..." allowInput="false" onbuttonclick="onMutiSelectDept('mainDepId')" style="width: 100%;" /></td>

						</tr>
						<tr>
							<th><div class="redfont">附　　件</div></th>
							<td colspan="3"><input name="ffileNames" class="upload-panel" id="fileNames" style="width: 100%" allowupload="true" label="附件"
								fname="attachFiles" allowlink="true" zipdown="true" fileNames="${fileNames}" fileIds="${fileIds}" /></td>


						</tr>
						<tr>
							<th><div class="redfont">主  题  词 </div></th>
							<td colspan="3"><textarea name="keywords" class="mini-textarea" vtype="maxLength:256" style="width: 100%">${odDocument.keywords}</textarea></td>
						</tr>
						<tr>
							<th><div class="redfont">
									内容简介 <br />
								</div></th>
							<td colspan="3"><textarea name="summary" class="mini-textarea" vtype="maxLength:1024" style="width: 100%">${odDocument.summary}</textarea></td>
						</tr>

						<tr style="display: none;">
							<%-- <th>发文单位</th><td><input name="issueDepId" value="${odDocument.issueDepId}" class="mini-textbox asLabel" readonly="readonly" vtype="maxLength:64" style="width: 100%;" /></td> --%>

							<td>是否联合
								<div name="isJoinIssue" id="isJoinIssue" class="mini-radiobuttonlist" repeatItems="1" repeatLayout="table"
									repeatDirection="vertical" textField="text" valueField="id" value="${odDocument.isJoinIssue}" data="yesno">
							</td>
							<%-- <th>发文人ID ：</th>
						<td><input name="issueUsrId" value="${odDocument.issueUsrId}" class="mini-textbox" /></td> --%>
							<th>发文分类ID </th>
							<td><input name="treeId" value="${odDocument.treeId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

								<th>收发文类型<span class="star">*</span> 
							</th>
								<td>
									<div name="archType" id="archType" class="mini-radiobuttonlist" repeatItems="1" repeatLayout="table" repeatDirection="vertical"
										textField="text" valueField="id" value="0" data="archType">
							</td>

								<th>公文状态</th>
								<td><input name="status" value="${odDocument.status}" class="mini-textbox" vtype="maxLength:100" style="width: 90%" /></td> 
						<tr>
							<td colspan="4">
							<OBJECT 
								id="WebOffice" 
								name="WebOffice" 
								width=100%
								classid="clsid:8B23EA28-2009-402F-92C4-59BE0E063499"
								codebase="<%=request.getContextPath()%>/scripts/iweboffice/iWebOffice2009.cab#version=10,1,0,0"> </OBJECT></td>
						</tr>
						
					</table>
				</div>
			</div>


		</form>
	</div>
	<script type="text/javascript">
	mini.parse();
	
/* //当填写了联合发文部门时自动更改隐藏域里
function changeRadio(){
	if(mini.get("joinDepIds").getValue().length>0){
		mini.get("isJoinIssue").setValue("YES");
		alert("asd");
	}else{
		mini.get("isJoinIssue").setValue("NO");
	}
	
}
 */
 //显示联合发文部门
function showJoins(){
	$(function(){
		$("#joinDeps").toggle();
	});
}


	/*金格*/
		var WebOffice=document.getElementById('WebOffice');
		window.onload=function(){
			var type = ".${docType}";
			var docPath="${docPath}";
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
				WebOffice.EditType = "1"; //编辑类型 
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

		
		//多选择部门
		function onMutiSelectDept(blockName) {
			var groupId = "";
			var groupName = "";
			_DepDlg(false, function(groups) {
				for (var i = 0; i < groups.length; i++) {
					groupName += groups[i].name + ",";
					groupId += groups[i].groupId + ",";
				}
				groupId = groupId.substring(0, groupId.length - 1);
				groupName = groupName.substring(0, groupName.length - 1);
				mini.get(blockName).setValue(groupId);
				mini.get(blockName).setText(groupName);
				
			});
		}

		function beforenodeselect(e) {
			//禁止选中父节点
			if (e.isLeaf == false)
				e.cancel = true;
		}

		//上传附件
		/* $(function() {
			$('.upload-panel').each(function() {
				$(this).uploadPanel();
			});
		}); */

		//重写了saveData方法
		function selfSaveData() {
			

			var form = new mini.Form("form1");
			form.validate();
			if (!form.isValid()) {//验证表格
				return;
			}
			var formData = $("#form1").serializeArray();
			
			//加上租用Id
			if (tenantId != '') {
				formData.push({
					name : 'tenantId',
					value : tenantId
				});
			}
			if (mini.get("joinDepIds").getValue().length > 0) {
				mini.get("isJoinIssue").setValue("YES");
			} else {
				mini.get("isJoinIssue").setValue("NO");
			}

			//若定义了handleFormData函数，需要先调用 
			if (isExitsFunction('handleFormData')) {
				var result = handleFormData(formData);
				if (!result.isValid)
					return;
				formData = result.formData;
			}

			try{WebOffice.WebSave(true);}catch(e){
				alert("金格控件请使用IE浏览器");
			}
			var docPath = WebOffice.WebGetMsgByName("docPath");
			formData.push({
				name : "docPath",
				value : docPath
			});
			formData.push({
				name : "odDocFlow",
				value : "SEND"
			});
			formData.push({
				name : "status",
				value : "1"
			});
			formData.push({
				name:"ffileNames",
				value:mini.get("fileNames").getValue()
			})
			
			

			_SubmitJson({
				url : "${ctxPath}/offdoc/core/odDocument/save.do",
				method : 'POST',
				data : formData,
				success : function(result) {
					//如果存在自定义的函数，则回调
					if (isExitsFunction('successCallback')) {
						successCallback.call(this, result);
						return;
					}
					var pkId = mini.get("pkId").getValue();
					//为更新
					if (pkId != '') {
						CloseWindow('ok');
						return;
					}
					CloseWindow('ok');
				}
			});
		}

		function draftSave() {
			//填写正确的"是否联合发文"
			if (mini.get("joinDepIds").getValue().length > 0) {
				mini.get("isJoinIssue").setValue("YES");
			} else {
				mini.get("isJoinIssue").setValue("NO");
			}
			form.validate();
			if (!form.isValid()) {
				return;
			}
			try{WebOffice.WebSave(true);}catch(e){
				alert("金格控件请使用IE浏览器");
			}
			var formData = $("#form1").serializeArray();
			formData.push({
				name : "status",
				value : "0"
			});
			var docPath = WebOffice.WebGetMsgByName("docPath");
			formData.push({
				name : "docPath",
				value : docPath
			});
			
			var draft = "YES";
			_SubmitJson({
				url : "${ctxPath}/offdoc/core/odDocument/save.do?draft="+ draft,
				method : 'POST',
				data : formData,
				success : function(result) {
					//如果存在自定义的函数，则回调
					if (isExitsFunction('successCallback')) {
						successCallback.call(this, result);
						return;
					}
					CloseWindow('ok');
				}
			});
		}
	</script>
	<rx:formScript formId="form1" baseUrl="offdoc/core/odDocument" entityName="com.redxun.offdoc.core.entity.OdDocument" />
</body>
</html>