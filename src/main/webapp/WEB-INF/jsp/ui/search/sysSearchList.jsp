<%-- 
    Document   : 模块下的高级查询列表管理
    Created on : 2015-4-15, 16:47:04
    Author     : X230
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>模块下的高级查询列表管理</title>
     <%@include file="/commons/list.jsp"%>
    </head>
    <body>
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td> 
                        <a class="mini-button" iconCls="icon-create" plain="true" onclick="createSearchDlg()">添加高级搜索方案</a>
                         <a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a>
                    </td>
                    <td style="text-align:right">
                        模块高级查询方案列表：【${sysModule.title}】
                    </td>
                </tr>
                <tr>
                     <td style="white-space:nowrap;padding:5px;" colspan="2">
                        方案名称
                        <input id="key" name="name" class="mini-textbox" emptyText="请输入方案名称" style="width:150px;" onenter="search"/>
                        <a class="mini-button" onclick="search()">查询</a>
                    </td>
                </tr>
            </table>
        </div>
        <div  class="mini-fit">
            <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"
                 url="${ctxPath}/ui/search/sysSearch/listData.do?entityName=${param.entityName}"  idField="searchId"
                 sizeList="[5,10,20,50,100]" pageSize="20" allowAlternating="true" >
                <div property="columns">
                    <!--<div type="indexcolumn"></div>-->
                    <div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>        
                    <div header="查询方案名称" field="name" allowSort="true"></div>
                    <div field="enabled" type="checkboxcolumn" trueValue="YES" falseValue="NO" width="30" headerAlign="center" allowSort="true">是否启用</div>
                    <div field="isDefault" type="checkboxcolumn" trueValue="YES" falseValue="NO" width="30" headerAlign="center" allowSort="true">是否缺省</div>
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
                var s= '<span class="icon-loadSearch" title="查询" onclick="searchRow(\'' + uid + '\')"></span>'
                        + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\')"></span>'
                        + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
                return s;
            }
            //创建新的搜索条件
            function createSearchDlg(){
            	_OpenWindow({
            		 url: "${ctxPath}/ui/search/sysSearch/edit.do?entityName=${param.entityName}",
                     title: "添加高级查询", 
                     iocnCls:'icon-addSearch',
                     width: 600, 
                     height: 430,
                     ondestroy: function(action) {
                         if(action!='ok')return;
                    	 grid.reload();
                     }
            	});
                
            }
            
            var parentWin=null;
            
            function setData(data){
            	parentWin=data.pWin;
            }
            
            //搜索
            function search(){
                var key=mini.get('key').getValue();
                grid.load({
                    name:key
                });
            }
            
            function searchRow(pkId){
            	parentWin.searchByFilterId(pkId);
            }
            
            function editRow(pkId){
            	
            	_OpenWindow({
            		 	url: "${ctxPath}/ui/search/sysSearch/edit.do?entityName=${param.entityName}&searchId="+pkId,
                     	title: "编辑高级查询", 
	                    iconCls:'icon-edit',
	                    width: 600, 
	                    height: 430,
	                    onload:function(){
	                    	var iframe = this.getIFrameEl();
                            var data = {action:"edit",pWin:window};
                            //取子窗口中数据
                            iframe.contentWindow.setData(data);
	                    },
	                    ondestroy: function(action) {
	                        if(action!='ok')return;
	                   	 	grid.reload();
	                    }
	           	});
            }
            //删除行
            function delRow(pkId){
                if (confirm("确定删除选中记录？")) {
                    $.ajax({
                            url: "${ctxPath}/ui/search/sysSearch/del.do",
                            data:{
                               searchId:pkId
                            },
                            success: function(text) {
                                grid.reload();
                            },
                            error: function() {
                            }
                    });
                }
            }
        </script>
    </body>
</html>
