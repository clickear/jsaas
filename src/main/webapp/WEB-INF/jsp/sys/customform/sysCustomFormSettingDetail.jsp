<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[单据表单方案]编辑</title>
<%@include file="/commons/customForm.jsp"%>
<script type="text/javascript">
$(function(){
	//解析表单。
	renderMiniHtml({});
})

function showInstInfo(instId){
	var url=__rootPath+"/bpm/core/bpmInst/info.do?instId="+instId;
	_OpenWindow({
		url : url,
		max:true,
		title : "流程图实例",
		width : 800,
		height : 600
	});
}
</script>
<style type="text/css">
	.table-view > tbody > tr > td{
		border: none;
	}
	
	.shadowBox .shadowBox,
	.shadowBox .shadowBox90{
		box-shadow:none;
	}
	
</style>

</head>
<body>
	<div class="mini-toolbar topBtn noPrint">
		<div id="toptoolbar">
			<c:if test="${hasInst }">
				<a class="mini-button"  iconCls="icon-detail"   plain="true" onclick="showInstInfo('${instId}')">流程信息</a>
			</c:if>
			<a class="mini-button" iconCls="icon-print" onclick="Print();">打印</a>
		</div>
		<input class="mini-hidden" name="_VIEW_ID_"  id="_VIEW_ID_" value="${viewId}"/>
	</div>
	
	
	<div class="customform shadowBox" id="form-panel" >
		${formModel.content}
		
		<input class='mini-hidden' name='seting_bodefId_' value='${setting.bodefId}'>
		<input class='mini-hidden' name='seting_alias_' value='${setting.alias}'>
	</div>
	<script type="text/javascript">
		addBody();
	</script>
	
</body>
</html>