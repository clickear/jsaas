<%@page import="com.zhuozhengsoft.pageoffice.OpenModeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.zhuozhengsoft.pageoffice.PageOfficeCtrl"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
//设置服务器页面
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
//添加自定义按钮
poCtrl.addCustomToolButton("保存","Save",1);
//设置保存页面
poCtrl.setSaveFilePage("SaveFile.jsp");
//打开Word文档
poCtrl.webOpen(request.getContextPath()+"/doc/test.doc",OpenModeType.docNormalEdit,"张佚名");
poCtrl.setTagId("PageOfficeCtrl1");//此行必需

%>

<!DOCTYPE html>
<html>
<head>
<title>Page Office</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<h2>Page Office</h2>
	 <div style="height:500px;width:auto;">
		 <po:PageOfficeCtrl id="PageOfficeCtrl1">
	     </po:PageOfficeCtrl>
     </div>
</body>
</html>