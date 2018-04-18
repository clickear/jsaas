<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath()%>/scripts/boot.js"	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/scripts/share.js"	type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/styles/icons.css"	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/styles/form.css"	rel="stylesheet" type="text/css" />
<title>文档预览</title>
</head>
<body>
	<OBJECT id="WebOffice" name="WebOffice" width=100% height=800
		classid="clsid:8B23EA28-2009-402F-92C4-59BE0E063499"
		codebase="<%=request.getContextPath()%>/scripts/iweboffice/iWebOffice2009.cab#version=10,8,6,13">
	</OBJECT>

	<script type="text/javascript">
		mini.parse();
	</script>
	<script type="text/javascript">
		var fileId = "${fileId}";
		var type = "${type}";
		var fileName="${fileName}";
		var WebOffice=document.getElementById('WebOffice');
		window.onload=function(){
			try{
				WebOffice.WebUrl= "<%=request.getContextPath()%>/iWebOffice/OfficeServer.jsp?fileId=" + ${fileId};  //处理页地址，这里用的是相对路径
				WebOffice.RecordID = "65422345"; //文档编号
				WebOffice.Template = "12345678"; //模板编号
				WebOffice.FileName ="65422345"; //文件名称
				WebOffice.FileType = type; //文件类型
				WebOffice.UserName = "kinggrid"; //用户名
				WebOffice.EditType = "0,1"; //编辑类型 
				WebOffice.PenColor = "#FF0000"; //默认手写笔迹颜色
				WebOffice.PenWidth = "1"; //默认手写笔迹宽度
				WebOffice.Print = "1"; //是否打印批注
				WebOffice.ShowToolBar = "false"; //关闭工具栏

				WebOffice.WebOpen(false);
			}catch(e){
				alert("金格控件请使用IE浏览器"); }
			
		}
	</script>

</body>
</html>