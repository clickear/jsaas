<%-- 
    Document   : 显示某个模块下某一记录的所有附件列表
    Created on : 2015-5-6, 13:56:01
    Author     : csx
    
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>模块下的记录的附件管理</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
        <%@include file="/commons/dynamic.jspf"%>
        <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
        <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
        <link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td> 
                        <a class="mini-button" iconCls="icon-upload" plain="true" onclick="newAttach()">上传附件</a>
                        <a class="mini-button" iconCls="icon-download" plain="true" href="${ctxPath}/sys/core/file/downloadRecordFiles.do?recordId=${param.recordId}&entityName=${param.entityName}">下载所有附件</a>
                        <a class="mini-button" iconCls="icon-download" plain="true" onclick="downloadOne()">下载单个附件</a>
                        <a class="mini-button" iconCls="icon-preview" plain="true" onclick="previewAttach()">预览附件</a>
                    </td>
                    <td style="text-align:right">
                        记录：【${entity.identifyLabel}】
                    </td>
                </tr>
                <tr>
                     <td style="white-space:nowrap;padding:5px;" colspan="2">
                        附件名称
                        <input id="fileName" name="fileName" class="mini-textbox" emptyText="附件名称" style="width:150px;" onenter="search"/>
                        附件类型
                        <input id="ext" name="ext" class="mini-textbox" emptyText="附件类型" style="width:150px;" onenter="search"/>
                        <a class="mini-button" onclick="search()">查询</a>
                    </td>
                </tr>
            </table>
        </div>
        <div  style="width:100%;height:280px;">
            <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"
                 multiSelect="true"
                 url="${ctxPath}/sys/core/sysFile/recordListData.do?entityName=${param.entityName}&recordId=${param.recordId}"  idField="gridViewId"
                 sizeList="[5,10,20,50,100]" pageSize="20" allowAlternating="true" 
                 >
                <div property="columns">
                    <!--<div type="indexcolumn"></div>-->
                    <div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>        
                    <div field="fileName" name="fileName" allowSort="true">附件名称</div>
                    <div field="ext" name="ext" allowSort="true">附件类型</div>
                    <div field="createTime" name="createTime" allowSort="true">上传时间</div>
                    <div field="totalBytes" name="totalBytes" allowSort="true">大小</div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            mini.parse();
            var grid = mini.get("datagrid1");
            grid.load();
            //编辑
            function onActionRenderer(e) {
                var record = e.record;
                var uid = record.pkId;
                var s = ' <span class="icon-preview" title="预览" onclick="previewRow(\'' + uid + '\')"></span>'
                        + ' <span class="icon-download" title="下载" onclick="downloadRow(\'' + uid + '\')"></span>'
                        + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
                return s;
            }
            
            function newAttach(){
                ShowUploadDialog({
                    recordId:'${param.recordId}',
                    entityName:'${param.entityName}'
                });
            }
            function downloadRow(fileId){
                window.location.href="${ctxPath}/sys/core/file/downloadOne.do?fileId="+fileId;
            }
            function downloadOne(){
                var row = grid.getSelected();
                if (!row) {
                    alert("请选中一条记录");
                    return;
                }
                var fileId=row.fileId;
                window.location.href="${ctxPath}/sys/core/file/downloadOne.do?fileId="+fileId;
            }
            
            //显示上传对话框
            function ShowUploadDialog(config){
                var url="${ctxPath}/sys/core/sysFile/dialog.do?1=1";
                if(config.recordId){
                    url+='&recordId='+config.recordId;
                }
                if(config.entityName){
                    url+='&entityName='+config.entityName;
                }
                mini.open({
                    allowResize: true, //允许尺寸调节
                    allowDrag: true, //允许拖拽位置
                    showCloseButton: true, //显示关闭按钮
                    showMaxButton: true, //显示最大化按钮
                    showModal: true,
                    url: url,
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
            function previewAttach(){
                var row = grid.getSelected();
                if (!row) {
                    alert("请选中一条记录");
                    return;
                }
                previewRow(row.pkId);
            }
            function previewRow(fileId){
               var t= mini.open({        
                        allowResize: true, //允许尺寸调节
                        allowDrag: true, //允许拖拽位置
                        showCloseButton: true, //显示关闭按钮
                        showMaxButton: true, //显示最大化按钮
                        showModal: true,
                        url: "${ctxPath}/sys/core/sysFile/preview.do?fileId="+fileId +"&entityName=${param.entityName}&recordId=${param.recordId}",
                        title: "预览附件", 
                        width: 600, 
                        height: 400,
                        onload: function() {
                            /*var iframe = this.getIFrameEl();
                            var data = {action:"edit",pWin:window};
                            //取子窗口中数据
                            iframe.contentWindow.setData(data);
                            */
                        },
                        ondestroy: function(action) {
                            grid.reload();
                        }
                    });
                    t.max();
            }
            
            //创建新的搜索条件
            function createGridViewDlg(){
                mini.open({        
                        allowResize: true, //允许尺寸调节
                        allowDrag: true, //允许拖拽位置
                        showCloseButton: true, //显示关闭按钮
                        showMaxButton: true, //显示最大化按钮
                        showModal: true,
                        url: "${ctxPath}/ui/view/sysGridView/edit.do?entityName=${param.entityName}",
                        title: "添加视图方案", 
                        width: 600, 
                        height: 430,
                        onload: function() {
                            /*var iframe = this.getIFrameEl();
                            var data = {action:"edit",pWin:window};
                            //取子窗口中数据
                            iframe.contentWindow.setData(data);
                            */
                        },
                        ondestroy: function(action) {
                            grid.reload();
                        }
                    });
            }
            
            //搜索
            function search(){
                var fileName=mini.get('fileName').getValue();
                var ext=mini.get('ext').getValue();
                grid.load({
                    fileName:fileName,
                    ext:ext
                });
            }
           
            //删除行
            function delRow(pkId){
                if (confirm("确定删除选中记录？")) {
                    $.ajax({
                            url: "${ctxPath}/sys/core/sysFile/del.do",
                            data:{
                               fileId:pkId
                            },
                            success: function(text) {
                                grid.reload();
                                mini.showMessageBox({
                                    showModal: false,
                                    width: 250,
                                    title: "提示",
                                    iconCls: "mini-messagebox-warning",
                                    message: "记录已删除",
                                    timeout: 3000,
                                    x: 'right',
                                    y: 'bottom'
                                });
                            },
                            error: function() {
                            }
                    });
                }
            }
        </script>
    </body>
</html>
