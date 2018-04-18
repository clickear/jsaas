<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html >
<html >
<head>
<title>业务表单预览明细</title>
<script type="text/javascript">
var _enable_openOffice="<%=com.redxun.sys.core.util.SysPropertiesUtil.getGlobalProperty("openoffice")%>";
</script>
<%@include file="/commons/list.jsp"%>
<!-- 加上扩展控件的支持 -->
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jquery-grid.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/ueditor-ext.js"></script>
<link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.css" />
<link href="${ctxPath}/styles/customform.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.js"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.js"	type="text/javascript"></script>
<script src="${ctxPath}/scripts/common/form.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/common/util.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/common/baiduTemplate.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/customer/mini-custom.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/form/FormCalc.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/form/CustomQuery.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/form/customFormUtil.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/customform.css" rel="stylesheet" type="text/css" />
<style type="text/css">
body {
	background: #f7f7f7;
}
#form-panel{
	margin-top: 8px;
}
.customform{
/* 	background: #fff; */
}

.customform{
	width: 100%;
}

.shadowBox90{
	height: auto !important;
}

.shadowBox90 .shadowBox{
	box-shadow: none;
	padding: 0;
	border-radius:0;
	margin-bottom: 20px;
}

.shadowBox90 .shadowBox:last-of-type{
	margin-bottom: 0;
}


</style>


</head>
<body>
	<div class="topBtn noPrint">
		<a class="mini-button" iconCls="icon-script" plain="true" onclick="viewJson">查看JSON</a> 
		<a class="mini-button" iconCls="icon-print" plain="true" onclick="mini.layout();Print()">打印</a> 
	</div>
<br/>
	<div id="content" class="mini-fit rx-grid-fit shadowBox90" >
		<div id="form-panel"  class="customform">
			<div class="form-model" id="formModel1">
			</div>
		</div>
	</div>

	<div 
		id="jsonWin" 
		class="mini-window" 
		iconCls="icon-script" 
		title="JSON数据" 
		style="width: 550px; 
		height: 350px; 
		display: none;" 
		showMaxButton="true" 
		showShadow="true" 
		showToolbar="true" 
		showModal="true" 
		allowResize="true" 
		allowDrag="true"
	>

		
		<div class="mini-fit rx-grid-fit">
			<div class="mini-tabs" style="height: 100%; width: 100%;padding-top:10px;">
				<div title="JSON视图">
					<div id="jsonview" style="height: 100%; width: 100%"></div>
				</div>
				<div title="JSON数据">
					<textarea id="json" class="mini-textarea" style="height: 100%; width: 100%"></textarea>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
	//mini.parse();
	$(window).resize(function() {
		mini.layout();
	});
	var formContent=null;
	function setContent(content,conf) {
		formContent=content;
		var dom = $(content);
		//取得其script的内容，并且加载到预览页面
		dom.filter('script').each(function() {
			if (this.src) {
				var script = document.createElement('script'), i, attrName, attrValue, attrs = this.attributes;
				for (i = 0; i < attrs.length; i++) {
					attrName = attrs[i].name;
					attrValue = attrs[i].value;
					script[attrName] = attrValue;
				}
				document.body.appendChild(script);
			} else {
				$.globalEval(this.text || this.textContent || this.innerHTML || '');
			}
		});
		
		$("#formModel1").append(content);
		
		renderMiniHtml(conf);
		
		initFieldSet();
	}
	//查看json
	function viewJson() {
		var data = _GetFormJsonMini('form-panel');
		var win = mini.get("jsonWin");
		mini.get('json').setValue(mini.encode(data));
		$("#jsonview").jsonViewer(data);
		win.show();
	}

	function hiddenWindow() {
		var win = mini.get("jsonWin");
		win.hide();
	}
	
	
</script>
</body>
</html>