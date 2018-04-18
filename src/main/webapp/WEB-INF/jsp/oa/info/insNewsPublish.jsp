<%-- 
    Document   : 新闻公告编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>新闻公告编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div id="toolbar" class="mini-toolbar mini-toolbar-bottom">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;" id="toolbarBody">
				<a class="mini-button" iconCls="icon-save" plain="true" onclick="onPublish">发布</a> 
				<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a></td>
			</tr>
		</table>
	</div>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div>
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>新闻发布</caption>
					<tr>
						<th>新闻标题</th>
						<td><input id="pkId" name="newId" class="mini-textboxlist" style="width: 80%;" value="${newId}" text="${newTitle}" readonly/></td>
					</tr>
					<tr>
						<th>发布栏目</th>
						<td>
<%-- 						<input id="issuedColIds" name="issuedColIds" class="mini-treeselect" url="${ctxPath}/sys/db/sysSqlCustomQuery/queryForJson_osuser.do" multiSelect="true" textField="F_LMMC" valueField="ID_" parentField="pid" required="true" checkRecursive="false" style="width: 90%" showFolderCheckBox="false" expandOnLoad="true" showClose="true" oncloseclick="clearIssuedCols" popupWidth="400" value="${selColIds}" /> --%>
					<input id="columnId" name="columnId" class="mini-combobox" url="${ctxPath}/oa/info/insNewsColumn/getJson.do"  textField="name" valueField="id"   style="width: 90%" showFolderCheckBox="false" expandOnLoad="true" showClose="true" showNullItem="true" allowInput="true"  oncloseclick="clearIssuedCols" popupWidth="400" value="${insNews.columnId}" />
						</td>
					</tr>
<%-- 					<tr>
						<th>是否长期 </th>
						<td><ui:radioBoolean name="isLongValid" id="isLongValid" required="true" onValueChanged="change" /></td>
					</tr> --%>
					<tr id="shortValid" >
						<th>发布的有效时间</th>
						<td colspan="3">从<input class="mini-datepicker" id="startTime" name="startTime" required="true" style="width: 130px" />至<input class="mini-datepicker" id="endTime" name="endTime" style="width: 130px" required="true" />
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/info/insNews" />
	<script type="text/javascript">
		var form = new mini.Form("form1");
		function onPublish(){
			form.validate();
			if (!form.isValid()) {
				return;
			}
			var formData = $("#form1").serializeArray();
			console.log(formData);
			_SubmitJson({
				url : __rootPath + '/oa/info/insNews/published.do?multi=yes',
				method : 'POST',
				data : formData,
				success : function(result) {
						CloseWindow('ok');
				}
			});
		}
	
	
	
		$(function() {
			$(".upload-file").on('click', function() {
				var img = this;
				_UserImageDlg(true, function(imgs) {
					if (imgs.length == 0)
						return;
					$(img).attr('src', '${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=' + imgs[0].fileId);
					$(img).siblings('input[type="hidden"]').val(imgs[0].fileId);

				});
			});
		});

		function change(e) {
			if ("NO" == e.sender.getValue()) {
				$("#shortValid").show();
			} else if ("YES" == e.sender.getValue()) {
				$("#shortValid").hide();
				$("#endTime").find("input").val("2026-01-01 00:00:00");
				$("#startTime").find("input").val("2015-01-01 00:00:00");
			}
			//console.log($("#endTime").find("input").val("2026-01-30 18:11:35"));
		}
		$(function() {
			$("#shortValid").hide();

		});

		function changeIsImg(ck) {
			if (ck.checked) {
				$("#imgRow").css("display", "");
			} else {
				$("#imgRow").css("display", "none");
			}
		}
		function clearIssuedCols() {
			var issuedColIds = mini.get("issuedColIds");
			issuedColIds.setValue("");
			issuedColIds.setText("");
		}
	</script>
</body>
</html>