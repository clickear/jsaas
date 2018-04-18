<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建信息</title>
<%@include file="/commons/dynamic.jspf" %>
<script src="${ctxPath}/scripts/common/form.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />

<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctxPath}/scripts/cut/map/style.css" type="text/css" media="all" />
<script type="text/javascript" src="${ctxPath}/scripts/cut/map/jquery-ui-1.9.2.custom.min.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/cut/map/jquery-notes_1.0.8.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/ueditor-ext.js"></script>

<link type="text/css" rel="stylesheet" href="${ctxPath}/scripts/cut/pic/normalize.css">
<script type="text/javascript" src="${ctxPath}/scripts/cut/pic/panzoom.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/cut/pic/jquery.mousewheel.js"></script>

<style>
.topButton {
	display: inline-block;
	line-height: 25px;
	height: 25px;
	width: 70px;
	background-color: #00BFFF;
	color: #fff;
	text-align: center;
	cursor: pointer;
	font-size: 9px;
	vertical-align: middle;
	border: 1px solid #0492C1;
	margin: 8px 5px 0 5px;
}

table.gridtable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	border-collapse: collapse;
}

table.gridtable th {
	border-width: 0px;
	padding: 8px;
	border-style: solid;
	font-weight: normal;
	text-align: right;
	font-size: 12px;
	color: #888;
}

hr {
	border: 1px dashed #ccc;
	border-bottom: 0;
	border-right: 0;
	border-left: 0;
}
</style>
</head>
<body>
	<div style="float: right;">
		<a class="topButton" onclick="saveEdit()">暂存</a>
	</div>
	<div style="clear: both;"></div>
	<hr style="height: 4px; border: none; border-top: 2px groove #CFCFCF;" />
	<div style="font-size: 10px; margin-left: 70px;">首页 &gt; 知识地图 &gt;</div>

	<!--基本信息栏  -->
	<div style="float: right; margin-top: 10px; margin-right: 5%; background: #E9F0F1; height: 140px; width: 300px;">
		<div style="position: relative; padding: 0 20px; height: 35px; line-height: 35px; font-weight: bold; font-size: 16px; font-family: '微软雅黑'; color: #fff; background: rgba(58, 194, 239, 0.91); cursor: pointer; border-bottom: none">基本信息</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			创建者 ：<span style="color: #EA5252;"> ${userName }</span>
		</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">地图状态 ：<span id="status"></span></div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">创建时间 ： <fmt:formatDate value="${kdDoc.createTime}" type="both"/></div>
	</div>
	<div style="height: 50px;"></div>

	<!-- 填写表单  -->
	<div style="margin-left: 100px;">
		<form id="form1" method="post">
			<input id="docId" name="docId" class="mini-hidden" value="${kdDoc.docId}" />
			<table class="gridtable" cellspacing="1" cellpadding="0" style="max-width: 1200px;">
				<tr>
					<th>所属分类 ：</th>
					<td style="width: 300px"><textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px; width: 150px;" text="${kdDoc.sysTree.name}" value="${kdDoc.treeId}" id="treeId" name="treeId"></textarea><a class="mini-button" style="margin-left: 10px;" onclick="selectGroup()">选择分类</a></td>
				</tr>

				<tr>
					<th>地图标题 <span class="star"></span> ：
					</th>
					<td><input name="subject" value="${kdDoc.subject}" class="mini-textbox" vtype="maxLength:128" style="width:300px" required="true" emptyText="请输入地图标题" /></td>
				</tr>
				<tr>
					<th>作者类型 <span class="star"></span> ：
					</th>
					<td><div name="authorType" class="mini-radiobuttonlist" value="${kdDoc.authorType}" repeatItems="1" textField="text" valueField="id" data="[{id:'INNER',text:'内部'},{id:'OUTER',text:'外部'}]"></div>
				</tr>
				<tr>
					<th>作者 <span class="star"></span> ：
					</th>
					<td><input name="author" value="${kdDoc.author}" class="mini-textbox" vtype="maxLength:64" style="width:300px" required="true" emptyText="请输入作者" /></td>
				</tr>
				<tr>
					<th>所属部门 <span class="star"></span> ：
					</th>
					<td><input id="belongDepid" name="belongDepid" value="${kdDoc.belongDepid}" text="${depName}" allowInput="false" style="width: 250px;" class="mini-buttonedit" onbuttonclick="selectDepartment" /></td>
				</tr>
				<tr>
					<th>所属岗位 <span class="star"></span> ：
					</th>
					<td><input id="authorPos" name="authorPos" value="${kdDoc.authorPos}" text="${jobName}" style="width: 250px;" allowInput="false" class="mini-buttonedit" onbuttonclick="selectJob" /></td>
				</tr>

				<tr>
					<th style="vertical-align: top;">地图内容 ：</th>
					<td ><ui:UEditor height="300" width="800" name="content" id="content">${kdDoc.content}</ui:UEditor></td>
				</tr>
				<tr>
					<th style="vertical-align: top;min-width: 80px;">热点区域 ：</th>
					<td style="max-width: 50%;">
						<input id="coverImgId" name="coverImgId" value="${kdDoc.coverImgId}" class="mini-hidden" vtype="maxLength:64" /> 
						<div style="margin-right: 50%;">
							<section id="focal">
								<div class="parent" style="border: 1px solid #ccc;">
									<div class="panzoom">
										<img src="${ctxPath}/sys/core/file/imageView.do?fileId=${kdDoc.coverImgId}" alt="" class="jquery-note" />
									</div>
								</div>
							</section>	
						</div>
						<a id="editImg" class="mini-button" plain="true" iconCls="icon-edit" onclick="editImg">更改图片</a>
					</td>
				</tr>
				<tr>
					<th style="vertical-align: top;">摘要 ：</th>
					<td style=""><textarea name="summary" class="mini-textarea" vtype="maxLength:512" style="height:100px;width: 500px;">${kdDoc.summary}</textarea></td>
				</tr>
				<tr>
					<th style="vertical-align: top;">标签：</th>
					<td><input name="tags" value="${kdDoc.tags}" class="mini-textbox" vtype="maxLength:200" style="width:300px" /></td>
				</tr>
				<tr>
					<th style="vertical-align: top;">存放年限 ：</th>
					<td style=""><input id="storePeroid" name="storePeroid" value="${kdDoc.storePeroid}" class="mini-spinner"  minValue="0" maxValue="20" /></td>
				</tr>
				
				<tr>
					<th style="vertical-align: top;">可阅读的</th>
					<td><div style="float: left; width: 500px;">
							<textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px;" text="${readName}" value="${readId}" id="readable" name="readable" width="500px" height="100px"></textarea>
						</div> 
						<input id="readPerson" name="readPerson" class="mini-hidden" value="${userReadId}" /> 
						<input id="readGroup" name="readGroup" class="mini-hidden" value="" /> 
						<a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 15px;" iconCls="icon-addMsgPerson" onclick="addPerson(readable,readPerson)">新增用户</a><br /> 
						<a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 5px;" iconCls="icon-addMsgGroup" onclick="addGroup(readable,readGroup)">新增用户组</a><br /> 
						<a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 5px;" iconCls="icon-cancel" onclick="clearAll()">清空用户</a>
						<div style="clear:both;color:#945151;">（如果为空则默认所有人都可以阅读）</div></td>
				</tr>
				<tr>
					<th style="vertical-align: top;">可编辑的</th>
					<td><div style="float: left; width: 500px;">
							<textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px;" id="editable" name="editable" width="500px" height="100px"></textarea>
						</div> 
						<input id="editPerson" name="editPerson" class="mini-hidden" value="" /> 
						<input id="editGroup" name="editGroup" class="mini-hidden" value="" /> 
						<a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 15px;" iconCls="icon-addMsgPerson" onclick="addPerson(editable,editPerson)">新增用户</a><br /> 
						<a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 5px;" iconCls="icon-addMsgGroup" onclick="addGroup(editable,editGroup)">新增用户组</a><br /> 
						<a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 5px;" iconCls="icon-cancel" onclick="clearAll()">清空用户</a>
						<div style="clear:both;color:#945151;">（如果为空则默认管理员都可以编辑）</div></td>
				</tr>
				<tr>
					<th style="vertical-align: top;">附件权限</th>
					<td><div style="float: left; width: 500px;">
							<textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px;" id="downloadable" name="downloadable" width="500px" height="100px"></textarea>
						</div> 
						<input id="downloadPerson" name="downloadPerson" class="mini-hidden" value="" /> 
						<input id="downloadGroup" name="downloadGroup" class="mini-hidden" value="" /> 
						<a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 15px;" iconCls="icon-addMsgPerson" onclick="addPerson(downloadable,downloadPerson)">新增用户</a><br /> 
						<a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 5px;" iconCls="icon-addMsgGroup" onclick="addGroup(downloadable,downloadGroup)">新增用户组</a><br /> 
						<a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 5px;" iconCls="icon-cancel" onclick="clearAll()">清空用户</a>
						<div style="clear:both;color:#945151;">（如果为空则默认所有人都可以下载）<div id="ck1" name="product" class="mini-checkbox" readOnly="false" text="勾选则所有人不可以下载附件" ></div></div></td>
						
				</tr>
			</table>
		</form>
	</div>
	<hr style="margin-top: 30px;">

	<!-- 上下一步 -->
	<div style="margin-top: 20px; margin-left: 40%;">
		<a class="topButton" style="height: 40px; width: 90px; line-height: 40px; text-decoration: none;" onclick="saveEdit()">完成编辑</a>
	</div>
	<script>
        (function() {
          var $section = $('#focal');
          var $panzoom = $section.find('.panzoom').panzoom();
          $panzoom.parent().on('mousewheel.focal', function( e ) {
            e.preventDefault();
            var delta = e.delta || e.originalEvent.wheelDelta;
            var zoomOut = delta ? (delta < 0 ): (e.originalEvent.deltaY > 0);
            $panzoom.panzoom('zoom', zoomOut, {
              increment: 0.1,
              animate: false,
              focal: e
            });
          });
        })();
    </script>
	
	
	
	<script type="text/javascript">	
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
	/* //封面图片
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
	}); */
	
	$(function() {
	 	$('.jquery-note').jQueryNotes({
	 		allowHide:false,
	 		allowCounter:false,
	 		allowClear:true,
			operator:"${ctxPath}/kms/core/kdDoc/getPoint.do"
		});
	});
	
	function editImg(e){
		/* var fieldVal=mini.get('fieldVal');
    	
			 var button=mini.get("editImg"); */
			 var img=$(".jquery-note").find("img");
			 //alert(img.html());
			_UserImageDlg(true,
				function(imgs){
					if(imgs.length==0) return;
					$(img).attr('src','${ctxPath}/sys/core/file/imageView.do?fileId='+imgs[0].fileId);
					$(img).parent().parent().find("#coverImgId").val(imgs[0].fileId);
				}
			);
	}
	
	//保存编辑
		function saveEdit(){
			form.validate();
			if (!form.isValid()) {
				return;
			}
			var formData = $("#form1").serializeArray();
			$.ajax({
				   type: "POST",
				   url : __rootPath + '/kms/core/kdDoc/saveEdit.do',
				   async:false,
					data : formData,
					success : function(result) {
						var docId = result;
						window.location.href="${ctxPath}/kms/core/kdDoc/mapShow.do?docId=" + docId;
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
		function addPerson(type,receive) {
			var infUser = mini.get(type);
			var readPerson = mini.get(receive);
			_UserDlg(false, function(users) {//打开收信人选择页面,返回时获得选择的user的Id和name
				var uIds = [];
				var uNames = [];
				//将返回的选择分别存起来并显示
				for (var i = 0; i < users.length; i++) {
					var flag = true;
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
				readPerson.setValue(uIds.join(','));
			});
		}
		//权限控制中的添加组
		function addGroup(type,receive) {
			var infGroup = mini.get(type);
			var readGroup = mini.get(receive);
			_GroupDlg(false, function(groups) {
				var uIds = [];
				var uNames = [];
				for (var i = 0; i < groups.length; i++) {
					var flag = true;
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
				readGroup.setValue(uIds.join(','));
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
		
		function openDocsInNote(docIds){
			_OpenWindow({
				title:'文档列表',
				url:__rootPath+'/kms/core/kdDoc/docsInMapList.do?docIds='+docIds,
				width:350,
				height:250
			});
		}
		
		
	</script>
</body>
</html>