<%-- 
    Document   : fileUploadDlgDemo
    Created on : 2015-4-29, 21:12:15
    Author     : X230
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/commons/dynamic.jspf" %>
            <link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
            <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script> 
        <title>Example File Upload Dialog</title>
        <script type="text/javascript">
            
            var filters=[{title:'zipFile',value:'2'},{title:'images',value:'3'}];
            
            console.log(jQuery.param(filters));
            
            function demo(){
                ShowUploadDialog({
                    callback:function(files){
                        for(var i=0;i<files.length;i++){
                            console.log('id:'+ files[i].id + ' name:'+ files[i].name);
                        }
                    }
                });
            }
            
            function ShowUploadDialog(config){
                mini.open({
                    allowResize: true, //允许尺寸调节
                    allowDrag: true, //允许拖拽位置
                    showCloseButton: true, //显示关闭按钮
                    showMaxButton: true, //显示最大化按钮
                    showModal: true,
                    url: "${ctxPath}/sys/core/sysFile/dialog.do",
                    title: "上传文件", width: 600, height: 420,
                    onload: function() {

                    },
                    ondestroy: function(action) {
                        if (action == 'ok') {
                            var iframe = this.getIFrameEl();
                            var files = iframe.contentWindow.getFiles();
                            if(config.callback){
                                config.callback.call(this,files);
                            }
                        }
                    }
                });
            }
        </script>
    </head>
    <body>
        
        <h1>Hello World!</h1>
        <input type="text" name="upload" onclick="demo()"/>
    </body>
</html>
