<%@ page pageEncoding="utf-8"%>
<link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.css" />
<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.js"></script>
<div id="jsonWin" class="mini-window" iconCls="icon-script-white" title="JSON数据" style="width: 550px;height:400px;display: none;" showMaxButton="true" showShadow="true" showToolbar="true" showModal="true" allowResize="true" allowDrag="true">
	<div class=" mini-toolbar-bottom">
		<a class="mini-button" iconCls="icon-close" plain="true" onclick="hiddenJsonWindow">关闭</a>
	</div>
	<div class="mini-fit rx-grid-fit">
		<div class="mini-tabs" style="height: 100%; width: 100%">
			<div title="JSON视图">
				<div id="jsonview" style="height: 100%; width: 100%"></div>
			</div>
			<div title="JSON数据">
				<textarea id="json" class="mini-textarea mini-textarea-margin" style="height: 100%; width: 100%"></textarea>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function viewJson(pkId) {
	var url=__rootPath +"/sys/bo/sysBoDef/getJson.do?pkId=" +pkId;
	$.get(url,function(data){
		var win = mini.get("jsonWin");
		mini.get('json').setValue(mini.encode(data));
		$("#jsonview").jsonViewer(data);
		win.show();	
	})
}

function hiddenJsonWindow() {
	var win = mini.get("jsonWin");
	win.hide();
}
</script>