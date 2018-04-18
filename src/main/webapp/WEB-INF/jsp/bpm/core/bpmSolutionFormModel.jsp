<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>业务流程解决方案管理-流程模型配置</title>
<%@include file="/commons/dynamic.jspf"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>

</head>
<body>

	<div class="mini-toolbar" style="padding: 0px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;"><a class="mini-button"
					iconCls="icon-add" plain="true" onclick="seleectBpm()">表单模型配置</a></td>
			</tr>
		</table>
	</div>
	<div class="form-outer">
		<c:forEach items="${formModels}" var="formModel" varStatus="i">
			<table style="width: 100%;">
				<tr>
					<td style="width: 100%;"><a class="mini-button"
						iconCls="icon-mgr" onclick="setMain('${formModel.key}')">设为[${formModel.name}]主模型</a>
					</td>
				</tr>
			</table>
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<tr>
					<th>模型名称</th>
					<td>${formModel.name}</td>
					<th>模型标识Key</th>
					<td>${formModel.key}</td>
				</tr>
				<tr>
					<th>模型版本</th>
					<td>${formModel.version}</td>
					<th>方案主模型</th>
					<td>${formModel.isSolutionMain}</td>
				</tr>
				<tr>
					<th>模型描述</th>
					<td colspan="3">${formModel.descp}</td>
				</tr>
			</table>
			<div style="clear: both; width: 100%; height: 4px;"></div>
			<div id="grid_${i.count}" class="mini-datagrid"
				style="width: 100%; height: 250px;" allowResize="false"
				url="${ctxPath}/bpm/bm/bpmFormAtt/getByFmId.do?fmId=${formModel.fmId}"
				idField="attId" multiSelect="true" showPager="false"
				allowAlternating="true">
				<div property="columns">
					<div field="sn" width="40" headerAlign="left">序号</div>
					<div field="title" width="140" headerAlign="center">名称</div>
					<div field="key" width="140" headerAlign="center">标识键</div>
					<div field="dataType" width="80" headerAlign="center">属性数据类型</div>
					<div field="type" width="60" headerAlign="center">属性类型</div>
					<div field="isRequired" width="60" headerAlign="center">是否必须</div>
				</div>
			</div>
			<div style="clear: both; width: 100%; height: 10px;"></div>
		</c:forEach>
	</div>

	<script type="text/javascript">
		mini.parse();
		var solId = '${bpmSolution.solId}';
		<c:forEach items="${formModels}" var="formModel" varStatus="i">
		mini.get('grid_${i.count}').load();
		</c:forEach>
		function seleectBpm() {
			_OpenWindow({
				title : '选择流程业务模型',
				height : 500,
				width : 800,
				url : __rootPath + '/bpm/bm/bpmFormModel/dialog.do',
				ondestroy : function(action) {
					if (action != 'ok')
						return;
					var formModels = this.getIFrameEl().contentWindow
							.getFormModel();
					if (formModels == null)
						return;
					var modelKeys = [];
					for (var i = 0; i < formModels.length; i++) {
						modelKeys.push(formModels[i].key);
					}
					$
							.ajax({
								url : __rootPath
										+ '/bpm/core/bpmSolution/saveFormModel.do?single=false',
								data : {
									modKeys : modelKeys.join(','),
									solId : solId
								},
								type : 'POST',
								cache : false,
								dataType : 'json',
								success : function(data) {
									window.location.reload();
								}
							});
				}
			});
		}

		//设置为主版本
		function setMain(modKey) {
			_SubmitJson({
				url : __rootPath + '/bpm/core/bpmSolution/setMainFm.do',
				method : 'POST',
				data : {
					solId : solId,
					modKey : modKey
				},
				success : function() {
					window.location.reload();
				}
			});
		}
	</script>
</body>
</html>