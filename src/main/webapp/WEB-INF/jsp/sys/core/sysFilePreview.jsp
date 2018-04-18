<%-- 
    Document   : sysFilePreview
    Created on : 2015-5-6, 17:27:52
    Author     : X230
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <title>Panel 标题面板</title>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
   <%@include file="/commons/dynamic.jspf"%>
   <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
   <link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
   <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>       
</head>
<body>
     <div style="border-bottom:0;padding:0px;">
        <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a>
        <a class="mini-button" iconCls="icon-prev" plain="true" onclick="preRecord()">上一条</a>
        <a class="mini-button" iconCls="icon-next" plain="true" onclick="nextRecord()">下一条</a>
        <a class="mini-button" iconCls="icon-remove" plain="true" onclick="onDelete()">删除</a>
     </div>
    <div style="width:100%;height:600px;border:1px solid #909aa6;">
        <c:choose>
            <c:when test="${sysFile.mineType=='Document'}">
                <iframe src="${ctxPath}/sys/core/file/previewDocument.do?fileId=${sysFile.fileId}" id="attachFrame" name="attachFrame" style="width:100%;" height="400" frameborder="0" scrolling="no" marginwidth="0" marginheight="0"></iframe>
            </c:when>
            <c:when test="${sysFile.mineType=='Xml'}">
                <iframe src="${ctxPath}/sys/core/file/previewXml.do?fileId=${sysFile.fileId}" id="attachFrame" name="attachFrame" style="width:100%;" height="400" frameborder="0" scrolling="no" marginwidth="0" marginheight="0"></iframe>
            </c:when>
            <c:when test="${sysFile.mineType=='Html'}">
                <iframe src="${ctxPath}/sys/core/file/previewHtml.do?fileId=${sysFile.fileId}" id="attachFrame" name="attachFrame" style="width:100%;" height="400" frameborder="0" scrolling="no" marginwidth="0" marginheight="0"></iframe>
            </c:when>
            <c:when test="${sysFile.mineType=='Image'}">
                <img src="${ctxPath}/sys/core/file/previewImage.do?fileId=${sysFile.fileId}"/>
            </c:when>
        </c:choose>
    </div>

    <script type="text/javascript">
        mini.parse();
        $(function(){ 
            $("#attachFrame").load(function(){ 
                //$(this).height($(this).contents().find("#content").height() + 40); 
            }); 
        });
    </script>

</body>
</html>