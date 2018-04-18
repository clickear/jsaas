<%-- 
    Document   : 门户编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>门户编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${insPortal.portId}" excludeButtons="remove,prevRecord,nextRecord" >
	<div class="self-toolbar">
	 <a class="mini-button" iconCls="icon-selectAll" plain="true" onclick="selectAll()">全选</a>
	</div>
	</rx:toolbar>
	<div id="p1" class="form-outer2">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="portId" class="mini-hidden" value="${insPortal.portId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>请选择本门户显示的栏目</caption>
						<td><div id="insPortCols" name="insPortCols" class="mini-checkboxlist" repeatItems="4" repeatLayout="table" textField="name" valueField="colId" value="${colId}" url="${ctxPath}/oa/info/insColumn/getByIsClose.do?person=${param['person']}"></div></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
        mini.parse();
        
        function selectAll(){
        	var insPortCols = mini.get("insPortCols");
        	insPortCols.selectAll();
        }
        
    </script>
	<rx:formScript formId="form1" baseUrl="oa/info/insPortal" />
</body>
</html>