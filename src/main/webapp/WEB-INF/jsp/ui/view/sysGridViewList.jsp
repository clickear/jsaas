<%-- 
    Document   : sysGridViewList
    Created on : 2015-4-24, 12:17:14
    Author     : X230
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>模块下的视图查询列表管理</title>
        <%@include file="/commons/list.jsp"%>
         <style type="text/css">
	      html, body{
	        	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    	}  
	    </style>
    </head>
    <body>
        <div class="mini-toolbar" style="border:none;padding:0px;">
	            <table style="width:100%;">
	                <tr>
	                    <td> 
	                        <a class="mini-button" iconCls="icon-create" plain="true" onclick="createGridViewDlg()">添加模块视图方案</a>
	                        <a class="mini-button" iconCls="icon-save" plain="true" onclick="batchSave()">批量保存</a>
	                        <span class="separator"></span>
	                        <a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a>
	                    </td>
	                    <td style="text-align:right">
	                        模块视图方案列表：【${sysModule.title}】
	                    </td>
	                </tr>
	                <tr>
	                     <td style="white-space:nowrap;padding:5px;" colspan="2">
	                        方案名称
	                        <input id="key" name="name" class="mini-textbox" emptyText="请输入方案名称" style="width:150px;" onenter="search"/>
	                        <a class="mini-button" iconCls="icon-search" onclick="search()">查询</a>
	                    </td>
	                </tr>
	            </table>
	        </div>
        <div class="mini-fit" style="border:none;padding:0px;">
        	
            <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"
                 url="${ctxPath}/ui/view/sysGridView/listData.do?entityName=${param.entityName}"  idField="gridViewId"
                 sizeList="[5,10,20,50,100]" pageSize="20" allowAlternating="true" 
                 allowCellEdit="true" allowCellSelect="true"  
                 editNextOnEnterKey="true"  editNextRowCell="true"
                 >
                <div property="columns">
                    <!--<div type="indexcolumn"></div>-->
                    <div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>        
                    <div field="name" name="name" allowSort="true">方案名称
                       <input property="editor" class="mini-textbox" style="width:100%;" />
                    </div>
                    <div field="sn" name="sn" allowSort="true">序号
                        <input property="editor" class="mini-spinner"  minValue="0" style="width:100%;"/>
                    </div>
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
                var s= ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\')"></span>'
                        + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
                return s;
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
                var key=mini.get('key').getValue();
                grid.load({
                    name:key
                });
            }
            
            //批量保存
            function batchSave(){
                _SubmitJson({
                	method:'post',
                    url: "${ctxPath}/ui/view/sysGridView/batchUpdate.do",
                    data:{
                       gridData:mini.encode(gridData)
                    },
                    success: function(text) {
                        grid.reload();
                    }
                });
            }
            
            function editRow(pkId){
                _OpenWindow({
                    url: "${ctxPath}/ui/view/sysGridView/edit.do?entityName=${param.entityName}&gdViewId="+pkId,
                    title: "编辑视图方案", 
                    width: 680, 
                    height: 480,
                    ondestroy: function(action) {
                        if(action!='ok')return;
                    	grid.reload();                            
                    }
                });
            }
            //删除行
            function delRow(pkId){
                if (confirm("确定删除选中记录？")) {
                    
                	_SubmitJson({
                		url: "${ctxPath}/ui/view/sysGridView/del.do",
                        data:{
                           gridViewId:pkId
                        },
                        success:function(text){
                        	grid.load();
                        }
                	});
                }
            }
        </script>
    </body>
</html>
