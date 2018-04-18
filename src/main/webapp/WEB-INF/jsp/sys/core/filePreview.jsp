<%-- 
    Document   : filePreview
    Created on : 2015-5-9, 7:26:58
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>文件预览</title>
        <%@include file="/commons/dynamic.jspf" %>
    </head>
    <body>
        <h1>文件预览</h1>
        <c:choose>
            <c:when test="${attch.mineType=='image'}">
                <img src="${ctxPath}/sys/core/file/view.do?fileId=${param.fileId}"/>
            </c:when>
            
        </c:choose>
    </body>
</html>
