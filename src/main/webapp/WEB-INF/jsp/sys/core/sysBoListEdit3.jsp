<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[系统自定义业务管理列表]编辑3</title>
<%@include file="/commons/edit.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/xml/xml.js"></script>
<script src="${ctxPath}/scripts/codemirror/addon/mode/multiplex.js"></script>
</head>
<body>
	
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom">
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;" id="toolbarBody">
	               	<a class="mini-button" iconCls="icon-save"  plain="true" onclick="onSave">保存</a>
					<a class="mini-button" iconCls="icon-preview"  plain="true" onclick="onPreview">预览</a>
					<a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a>
	                
	            </td>
	        </tr>
	    </table>
    </div>
	<div class="mini-fit">
		<div id="htmlTab" class="mini-tabs" activeIndex="0"  style="width:100%;height:100%;">
		    <div title="PC列表代码">
				<textarea id="listHtml" name="listHtml" ><c:out value="${sysBoList.listHtml}" escapeXml="true"/></textarea>        
		    </div>
		    <div title="手机列表代码"   >
		        <textarea id="mobileListHtml" name="mobileListHtml"  ><c:out value="${sysBoList.mobileHtml}" escapeXml="true"/></textarea>
		    </div>
		</div>
		
			
	</div>

	<script type="text/javascript">
		mini.parse();
		function onPre(){
			location.href=__rootPath+'/sys/core/sysBoList/edit2.do?id=${param.id}';
		}
		
		CodeMirror.defineMode("selfHtml", function(config) {
			  return CodeMirror.multiplexingMode(
			    CodeMirror.getMode(config, "text/html"),
			    {open: "<<", close: ">>",
			     mode: CodeMirror.getMode(config, "text/plain"),
			     delimStyle: "delimit"}
			  );
		});
		
		var editor = CodeMirror.fromTextArea(document.getElementById("listHtml"), {
			  mode: "selfHtml",
			  lineNumbers: true,
			  lineWrapping: true
		});
		editor.setSize('auto','100%');
		var mobileEditor = CodeMirror.fromTextArea(document.getElementById("mobileListHtml"), {
			  mode: "selfHtml",
			  lineNumbers: true,
			  lineWrapping: true
		});
		mobileEditor.setSize('auto','100%');
		
		var tab=mini.get("htmlTab");
		
		tab.on("activechanged",function(e){
			mobileEditor.refresh();
		});
		
		

		
		function onPreview(){
			var key='${sysBoList.key}';
			var isShare = '${sysBoList.isShare}';
			if(isShare=="YES"){
				_OpenWindow({
					title:'${sysBoList.name}-预览',
					height:400,
					width:800,
					max:true,
					url:__rootPath+'/sys/core/sysBoList/share/'+key+'/list.do'
				});
			}else if(isShare=="NO"){
				_OpenWindow({
					title:'${sysBoList.name}-预览',
					height:400,
					width:800,
					max:true,
					url:__rootPath+'/sys/core/sysBoList/'+key+'/list.do'
				});
			}
			
		}
		
		function onSave(){
			var id='${sysBoList.id}';
			var html=editor.getValue();
			var mobileHtml=mobileEditor.getValue();
			_SubmitJson({
				url:__rootPath+'/sys/core/sysBoList/saveHtml.do',
				method:'POST',
				data:{
					id:id,
					html:html,
					mobileHtml:mobileHtml
				},
				success:function(result){
				}
			});
		}
	</script>
</body>
</html>