<%@page contentType="text/html" pageEncoding="UTF-8" deferredSyntaxAllowedAsLiteral="true"%>
<!DOCTYPE html>
<html>
<head>
	<title>流程全局事件或节点接口调用配置--ProcessCall接口调用</title>
	<%@include file="/commons/edit.jsp"%>
	<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
	<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
	<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
</head>
<body>

	<div id="toolbar1" class="mini-toolbar" >
		<a class="mini-button" plain="true" iconCls="icon-save" onclick="CloseWindow('ok')">保存</a>
		<a class="mini-button" plain="true" iconCls="icon-close" onclick="CloseWindow()">关闭</a>		
	</div>
	
	<table class="table-detail column_2_m"  cellspacing="1" cellpadding="0" style="width:100%;">
		<caption>ProcessCall接口调用</caption>
		
		<tr>
			<th>Spring Bean Id</th>
			<td>
				<input id="class" class="mini-textbox" name="class" style="width:90%"/>
			</td>
		</tr>
		<tr>
			<th>说明</th>
			<td>
				扩展实现com.redxun.bpm.activiti.listener.call.ProcessCall接口的类，
				<br/>并且加至Spring容器中。
			</td>
		</tr>
	</table>
		
	<script type="text/javascript">
		mini.parse();
		
		//获得脚本的配置 
		function getData(){
			return mini.get('class').getValue();
		}
		
		function setData(script){
			mini.get('class').setValue(script);
		}
	</script>
</body>
</html>
