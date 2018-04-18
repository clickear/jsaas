<%-- 
    Document   : 栏目类型编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<html>
<head>
<title>栏目类型编辑</title>
<%@include file="/commons/list.jsp"%>	
</script>
</head>
<body>
	<div id="toolbar" class="mini-toolbar mini-toolbar-bottom">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;" id="toolbarBody">
				<a class="mini-button" iconCls="icon-save" plain="true" onclick="onPublish">保存</a>  
				<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a></td>
			</tr>
		</table>
	</div>
	<div id="p1" class="form-outer2">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="Id" class="mini-hidden" value="${insColNew.id}" />
				<input id="sn" name="sn" class="mini-hidden" value="${insColNew.sn}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>栏目类型基本信息</caption>

					<tr>
						<th width="200">是否长期 ：</th>
						<td><ui:radioBoolean name="isLongValid" id="timeValid" value="${insColNew.isLongValid}" required="true" onValueChanged="change"  /></td>
					</tr>
					<tr id="shortValid">
						<th>发布的有效时间</th>
						<td colspan="3">从<input class="mini-datepicker" value="${insColNew.startTime}" id="startTime" name="startTime" required="true" style="width: 100px" />
						至<input class="mini-datepicker" id="endTime" value="${insColNew.endTime}" name="endTime" style="width: 100px" required="true" />
					</tr>
				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	
	function onPublish(){
		form.validate();
		if (!form.isValid()) {
			return;
		}
		var formData = $("#form1").serializeArray();
		var newId = '${newId}';
		var colId = '${colId}';
		console.log(formData);
		_SubmitJson({
			url : __rootPath + '/oa/info/insColNew/saveTime.do?newId='+newId+'&colId='+colId,
			method : 'POST',
			data : formData,
			success : function(result) {
					CloseWindow('ok');
					return;
			}
		});
	}
	
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
	</script>

	<rx:formScript formId="form1" baseUrl="oa/info/insColNew" />
</body>
</html>