<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html>
<html>
<head>
	<title>流程活动节点的结束节点配置页</title>
	<%@include file="/commons/edit.jsp"%>
		<!-- code codemirror start -->	
	<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
	<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
	<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
	<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
	
</head>
<body>
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom" >
	    <table>
	        <tr>
       			<td style="width:100%;">
					<a class="mini-button" plain="true" iconCls="icon-save" onclick="saveConfig">下一步</a>
					<a class="mini-button" plain="true" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
	       <form id="configForm" >
		       	<table class="table-detail" cellspacing="1" cellpadding="0" style="width:100%">
	              <tr>
	             	<th width="120">选择类型</th>
	             	<td>
	             		<input id="comboType" class="mini-combobox"    textField="text" valueField="id" emptyText="请选择..."
    					 required="true" allowInput="true" showNullItem="true" nullItemText="请选择..."/>   
	             	</td>
	             </tr>
		 		</table>
			</form>
		
	</div>
	<script type="text/javascript">
		var data=${json};
		var comboType=mini.get("comboType");
		comboType.setData(data);
	</script>
</body>
</html>
	