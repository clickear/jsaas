<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>新建信息</title>
<%@include file="/commons/edit.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/ueditor-ext.js"></script>
<style>
body{
	background: #fff;
}
.topButton {
	display: inline-block;
	padding:4px 12px;
	background-color: #00BFFF;
	color: #fff;
	text-align: center;
	cursor: pointer;
	font-size: 9px;
	vertical-align: middle;
	margin: 8px 5px 0 5px;
	border-radius: 4px;
}

table.gridtable {
	font-family: "微软雅黑";
	font-size: 11px;
	border-collapse: collapse;
}

table.gridtable th {
	border-width: 0px;
	padding: 8px;
	border-style: solid;
	font-weight: normal;
	font-size: 14px;
	color: #888;
}

table.gridtable th,
table.gridtable td{
	font-size: 14px;
	color: #666;
	border: 1px solid #ececec;
	padding: 6px;
}

.rightC{
	float: right; 
	margin-top: 50px; 
	margin-right: 5%; 
	background: #E9F0F1;
	width: 270px;
	border-radius: 6px;
	overflow: hidden;
	box-shadow: 0px 5px 10px 0px rgba(128, 128, 128, 0.15);
}

.rightC dt{
	position: relative; 
	padding: 0 20px; 
	height: 35px; 
	line-height: 35px; 
	font-weight: bold; 
	font-size: 16px; 
	font-family: '微软雅黑'; 
	color: #fff; 
	background: rgba(58, 194, 239, 0.91); 
	cursor: pointer; 
	border-bottom: none;	
}

.rightC dd{
	margin:0;
	font-size: 12px; 
	padding: 5px 0 5px 10px;
	width: 100%;
	box-sizing: border-box;
}

.contentBox{
	 margin-left: 100px; 
	 margin-right: 40%; 
	 margin-top: 30px; 
	 text-align: center;
}

.contentTitle{
	font-size: 12px; 
	border-bottom-color: #ccc; 
	border-bottom-width: 1px; 
	border-bottom-style: solid;
}

.tableBox{
	padding: 0 !important;
}

.tableBox #edui1{
	border: none;
}

.edui-default .edui-editor-bottomContainer td{
	border-bottom: none;
	border-left: none;
}

</style>
</head>
<body style="min-width: 1600px;">
	<div style="float: right;">
		<a class="topButton" onclick="saveEdit()">完成</a>
	</div>
	<div style="clear: both;"></div>
	<hr style="height: 4px; border: none; border-top: 1px solid #ccc;" />
	<div style="font-size: 10px; margin-left: 70px;">首页 > 知识仓库 ></div>

	<!--基本信息栏  -->
	<dl class="rightC" style="float: right; margin-top: 10px; margin-right: 10%; background: #E9F0F1; height: 140px; width: 300px;">
		<dt>基本信息</dt>
		<dd style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			创建者 ：<span style="color: #EA5252;"> ${userName }</span>
		</dd>
		<dd style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			文档状态 ：<span id="status"></span>
		</dd>
		<dd style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			创建时间 ：
			<fmt:formatDate value="${kdDoc.createTime}" type="both" />
		</dd>
	</dl>
	<div style="height: 50px;"></div>

	<!-- 填写表单  -->
	<div style="margin-left: 100px;">
		<form id="form1" method="post">
			<input id="docId" name="docId" class="mini-hidden" value="${kdDoc.docId}" />
			<table class="gridtable" cellspacing="1" cellpadding="0">
				<tr>
					<th>所属分类 </th>
					<td style="width: 300px">
						<textarea 
							class="mini-textboxlist" 
							allowInput="false" 
							validateOnChanged="false" 
							style="margin-top: 5px; width: 150px;" 
							text="${kdDoc.sysTree.name}" 
							value="${kdDoc.treeId}" 
							id="treeId" 
							name="treeId"
						></textarea>
						<a class="mini-button" style="margin-left: 10px;" onclick="selectGroup()">选择分类</a>
					</td>
				</tr>

				<tr>
					<th>文档标题 <span class="star"></span> 
					</th>
					<td>
						<input 
							name="subject" 
							value="${kdDoc.subject}" 
							class="mini-textbox" 
							vtype="maxLength:128" 
							style="width: 90%" 
							required="true" 
							emptyText="请输入文档标题" 
						/></td>
				</tr>
				<tr>
					<th>作者类型 <span class="star"></span> 
					</th>
					<td>
						<div 
							name="authorType" 
							class="mini-radiobuttonlist" 
							value="${kdDoc.authorType}" 
							repeatItems="1" 
							textField="text" 
							valueField="id" 
							data="[{id:'INNER',text:'内部'},{id:'OUTER',text:'外部'}]"
						></div>
				</tr>
				<tr>
					<th>作者 <span class="star"></span> 
					</th>
					<td>
						<input 
							name="author" 
							value="${kdDoc.author}" 
							class="mini-textbox" 
							vtype="maxLength:64" 
							style="width: 90%" 
							required="true" 
							emptyText="请输入作者" 
						/>
					</td>
				</tr>
				<tr>
					<th>所属部门 <span class="star"></span> 
					</th>
					<td>
						<input 
							id="belongDepid" 
							name="belongDepid" 
							value="${kdDoc.belongDepid}" 
							text="${depName}" 
							allowInput="false" 
							style="width: 250px;" 
							class="mini-buttonedit" 
							onbuttonclick="selectDepartment" 
						/>
					</td>
				</tr>
				<tr>
					<th>所属岗位 <span class="star"></span> 
					</th>
					<td>
						<input 
							id="authorPos" 
							name="authorPos" 
							value="${kdDoc.authorPos}" 
							text="${jobName}" 
							style="width: 250px;" 
							allowInput="false" 
							class="mini-buttonedit" 
							onbuttonclick="selectJob" 
							/>
					</td>
				</tr>

				<tr>
					<th style="vertical-align: top;">知识正文内容 </th>
					<td class="tableBox">
						<ui:UEditor height="300" width="800px" name="content" id="content">${kdDoc.content}</ui:UEditor>
					</td>
				</tr>

				<tr>
					<th style="vertical-align: top;">封面图 </th>
					<td>
						<input 
							name="coverImgId" 
							value="${kdDoc.coverImgId}" 
							class="mini-hidden" 
							vtype="maxLength:64" 
						/> 
						<img src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${kdDoc.coverImgId}" class="upload-file" />
					</td>
					</td>
				</tr>
				<tr>
					<th style="vertical-align: top;">摘要 </th>
					<td style="">
						<textarea 
							name="summary" 
							class="mini-textarea" 
							vtype="maxLength:512" 
							style="height: 100px; width: 500px;"
						>${kdDoc.summary}</textarea>
					</td>
				</tr>
				<tr>
					<th style="vertical-align: top;">标签</th>
					<td><input name="tags" value="${kdDoc.tags}" class="mini-textbox" vtype="maxLength:200" style="width: 100%" /></td>
				</tr>
				<tr>
					<th style="vertical-align: top;">存放年限 </th>
					<td style=""><input id="storePeroid" name="storePeroid" value="${kdDoc.storePeroid}" class="mini-spinner" minValue="0" maxValue="20" /></td>
				</tr>

				<tr>
					<th style="vertical-align: top;">可阅读的</th>
					<td>
						<div style="float: left; width: 500px;">
							<textarea 
								class="mini-textboxlist" 
								allowInput="false" 
								validateOnChanged="false" 
								style="margin-top: 5px;" 
								text="${readName}" 
								value="${readId}" 
								id="readable" 
								name="readable" 
								width="500px" 
								height="100px"
							></textarea>
						</div> 
						<a 
							class="mini-button" 
							style="width: 110px; margin-left: 10px; margin-top: 15px;" 
							iconCls="icon-addMsgPerson" 
							onclick="addPerson(readable)"
						>新增用户</a>
						<br/>
						<a 
							class="mini-button" 
							style="width: 110px; margin-left: 10px; margin-top: 5px;" 
							iconCls="icon-addMsgGroup" 
							onclick="addGroup(readable)"
						>新增用户组</a>
						<br/>
						<a 
							class="mini-button" 
							style="width: 110px; margin-left: 10px; margin-top: 5px;" 
							iconCls="icon-cancel" 
							onclick="clearAll()"
						>清空用户</a>
						<div style="clear: both; color: #945151;">（如果为空则默认所有人都可以阅读）</div></td>
				</tr>
				<tr>
					<th style="vertical-align: top;">可编辑的</th>
					<td>
						<div style="float: left; width: 500px;">
							<textarea 
								class="mini-textboxlist" 
								allowInput="false" 
								validateOnChanged="false" 
								style="margin-top: 5px;" 
								text="${editName}" 
								value="${editId}" 
								id="editable" 
								name="editable" 
								width="500px" 
								height="100px"
							></textarea>
						</div> 
						<a 
							class="mini-button" 
							style="width: 110px; margin-left: 10px; margin-top: 15px;" 
							iconCls="icon-addMsgPerson" 
							onclick="addPerson(editable)"
						>新增用户</a>
						<br/> 
						<a 
							class="mini-button" 
							style="width: 110px; margin-left: 10px; margin-top: 5px;" 
							iconCls="icon-addMsgGroup" 
							onclick="addGroup(editable)"
						>新增用户组</a>
						<br/>
						<a 
							class="mini-button" 
							style="width: 110px; margin-left: 10px; margin-top: 5px;" 
							iconCls="icon-cancel" 
							onclick="clearAll()"
						>清空用户</a>
						<div style="clear: both; color: #945151;">（如果为空则默认管理员都可以编辑）</div></td>
				</tr>
				<tr>
					<th style="vertical-align: top;">附件权限</th>
					<td>
						<div style="float: left; width: 500px;">
							<textarea 
								class="mini-textboxlist" 
								allowInput="false" 
								validateOnChanged="false" 
								style="margin-top: 5px;" 
								text="${downName}" 
								value="${downId}" 
								id="downloadable" 
								name="downloadable" 
								width="500px" 
								height="100px"
							></textarea>
						</div> 
						<a 
							class="mini-button" 
							style="width: 110px; margin-left: 10px; margin-top: 15px;" 
							iconCls="icon-addMsgPerson" 
							onclick="addPerson(downloadable)"
						>新增用户</a>
						<br/>
						<a 
							class="mini-button" 
							style="width: 110px; margin-left: 10px; margin-top: 5px;" 
							iconCls="icon-addMsgGroup" 
							onclick="addGroup(downloadable)"
						>新增用户组</a>
						<br/>
						<a 
							class="mini-button" 
							style="width: 110px; margin-left: 10px; margin-top: 5px;" 
							iconCls="icon-cancel" 
							onclick="clearAll()"
						>清空用户</a>
						<div style="clear: both; color: #945151;">
							（如果为空则默认所有人都可以下载）
							<!-- <div id="ck1" name="product" class="mini-checkbox" readOnly="false" text="勾选则所有人不可以下载附件"></div> -->
						</div></td>
				</tr>
				<tr>
					<th>附件 </th>
					<td>
						<input 
							id="attFileids" 
							name="attFileids" 
							class="upload-panel" 
							plugins="upload-panel" 
							allowupload="true" 
							label="附件" 
							fname="fileNames" 
							allowlink="true" 
							zipdown="true" 
							fileIds="${kdDoc.attFileids}" 
							fileNames="${fileNames}" 
						/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<hr style="margin-top: 30px;">

	<!-- 上下一步 -->
	<div style="margin-top: 20px; margin-left: 40%;">
		<a class="topButton" style="height: 40px; width: 90px; line-height: 40px; text-decoration: none;" onclick="saveEdit()">完成编辑</a>
	</div>
	<script type="text/javascript">
	mini.parse();
	var form = new mini.Form("form1");
	var kdDocId = "${kdDoc.docId}";
	var status = "${kdDoc.status}";
	
	
	//doc状态的显示
	$(function() {
			if (status == "DRAFT") {
				$('#status').html('草稿');
			} else if (status == "PENDING") {
				$('#status').html('待审');
			} else if (status == "ARCHIVED") {
				$('#status').html('归档');
			} else if (status == "ABANDON") {
				$('#status').html('废弃');
			} else if (status == "BACK") {
				$('#status').html('驳回');
			} else if (status == "ISSUED") {
				$('#status').html('发布');
			} else if (status == "OVERDUE") {
				$('#status').html('过期');
			}
		});
	
	//存放年限
	$(function() {
			var time = "${kdDoc.storePeroid}";
			var t = mini.get("storePeroid");
			if (time == "") {
				t.setValue("0");
			}
		});
	
	//封面图片
	$(function(){
		 $(".upload-file").on('click',function(){
			 var img=this;
			_UserImageDlg(true,
				function(imgs){
					if(imgs.length==0) return;
					$(img).attr('src','${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId='+imgs[0].fileId);
					$(img).siblings('input[type="hidden"]').val(imgs[0].fileId);
					
				}
			);
		 });
	});
	
	//保存编辑
		function saveEdit(){
			form.validate();
			if (!form.isValid()) {
				return;
			}
			
			var formData1 = _GetFormJson('form1');
			console.log(formData1);
			var formData = $("#form1").serializeArray();
			console.log(formData);
			$.ajax({
				   type: "POST",
				   url : __rootPath + '/kms/core/kdDoc/saveEdit.do',
				   async:false,
					data : formData,
					success : function(result) {
						var docId = result;
						window.location.href="${ctxPath}/kms/core/kdDoc/show.do?docId=" + docId;
					}
				});
		}	
	
	
		//部门选择
		function selectDepartment(){
			var infDepartment = mini.get('belongDepid');
			infDepartment.setValue("");
			infDepartment.setText("");
			_GroupSingleDim(true,"1",function(users) {
				var uIds = [];
				var uNames = [];
				uIds.push(users.groupId);
				uNames.push(users.name);
				if (infDepartment.getValue() != '') {
					uIds.unshift(infDepartment.getValue().split(','));
				}
				if (infDepartment.getText() != '') {
					uNames.unshift(infDepartment.getText().split(','));
				}
				infDepartment.setValue(uIds.join(','));
				infDepartment.setText(uNames.join(','));
			});
		}
		
		//岗位选择
		function selectJob(){
			var infJob = mini.get('authorPos');
			infJob.setValue("");
			infJob.setText("");
			_GroupSingleDim(true,"3", function(users) {
				var uIds = [];
				var uNames = [];
				uIds.push(users.groupId);
				uNames.push(users.name);
				if (infJob.getValue() != '') {
					uIds.unshift(infJob.getValue().split(','));
				}
				if (infJob.getText() != '') {
					uNames.unshift(infJob.getText().split(','));
				}
				infJob.setValue(uIds.join(','));
				infJob.setText(uNames.join(','));
			});
		}
		//权限控制的添加用户
		function addPerson(type) {
			var infUser = mini.get(type);
			_UserDlg(false, function(users) {//打开收信人选择页面,返回时获得选择的user的Id和name
				var uIds = [];
				var uNames = [];
				//将返回的选择分别存起来并显示
				for (var i = 0; i < users.length; i++) {
					var flag = true;
					users[i].userId = users[i].userId + "_user";
					var oldValues = infUser.getValue().split(',');
					var oldText = infUser.getText().split(',');
					for(var j=0;j<oldValues.length;j++){
						if(oldValues[j]==users[i].userId&&oldText[j]==users[i].fullname){
							flag = false;
							break;
						}
					}
					if(flag==true){
					uIds.push(users[i].userId);
					uNames.push(users[i].fullname);
					}
				}
				if (infUser.getValue() != '') {
					uIds.unshift(infUser.getValue().split(','));
				}
				if (infUser.getText() != '') {
					uNames.unshift(infUser.getText().split(','));
				}
				infUser.setValue(uIds.join(','));
				infUser.setText(uNames.join(','));
			});
		}
		//权限控制中的添加组
		function addGroup(type) {
			var infGroup = mini.get(type);
			_GroupDlg(false, function(groups) {
				var uIds = [];
				var uNames = [];
				for (var i = 0; i < groups.length; i++) {
					var flag = true;
					groups[i].groupId = groups[i].groupId + "_group";
					var oldValues = infGroup.getValue().split(',');
					var oldText = infGroup.getText().split(',');
					for(var j=0;j<oldValues.length;j++){
						if(oldValues[j]==groups[i].groupId&&oldText[j]==groups[i].name){
							flag = false;
							break;
						}
					}
					if(flag==true){
					uIds.push(groups[i].groupId);
					uNames.push(groups[i].name);
					}
				}
				if (infGroup.getValue() != '') {
					uIds.unshift(infGroup.getValue().split(','));
				}
				if (infGroup.getText() != '') {
					uNames.unshift(infGroup.getText().split(','));
				}
				infGroup.setValue(uIds.join(','));
				infGroup.setText(uNames.join(','));
			});
		}
		
		//分类选择
		function selectGroup() {
			var infGroup = mini.get('treeId');
			infGroup.setValue("");
			infGroup.setText("");
			_TypeDlg(true, function(users) {
				var uIds = [];
				var uNames = [];
				uIds.push(users.treeId);
				uNames.push(users.name);
				if (infGroup.getValue() != '') {
					uIds.unshift(infGroup.getValue().split(','));
				}
				if (infGroup.getText() != '') {
					uNames.unshift(infGroup.getText().split(','));
				}
				infGroup.setValue(uIds.join(','));
				infGroup.setText(uNames.join(','));
			});
		}
		function _TypeDlg(single, callback, reDim) {
			if (!reDim) {
				reDim = false;
			}
			_TenantTypeDlg('', single, '', callback, reDim);
		}
		function _TenantTypeDlg(tenantId, single, showDimId, callback, reDim) {
			var title = '知识分类选择';
			_OpenWindow({
				iconCls : 'icon-group',
				url : __rootPath + '/kms/core/kdDoc/groupDialog.do?single=' + single + '&tenantId=' + tenantId,
				height : 480,
				width : 680,
				title : title,
				ondestroy : function(action) {
					if (action != 'ok')
						return;
					var iframe = this.getIFrameEl();
					var groups = iframe.contentWindow.getGroups();
					var dim = {};
					//需要返回dim
					if (reDim) {
						var dimNode = iframe.contentWindow.getSelectedDim();
						dim = {
							dimId : dimNode.dimId,
							dimKey : dimNode.dimKey,
							name : dimNode.name
						};
					}
					if (callback) {
						if (single && groups.length > 0) {
							callback.call(this, groups[0], dim);
						} else {
							callback.call(this, groups, dim);
						}
					}
				}
			});
		}
		
		//上传附件
		$(function() {
			$('.upload-panel').each(function() {
				$(this).uploadPanel();
			});
		});
	</script>
</body>
</html>