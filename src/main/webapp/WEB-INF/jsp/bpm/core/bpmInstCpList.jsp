<%-- 
    Document   : [BpmInstCp]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmInstCp]列表管理</title>
   <%@include file="/commons/list.jsp"%>
    </head>
    <body>
        
        <redxun:toolbar entityName="com.redxun.bpm.core.entity.BpmInstCp"/>
        
        <div class="mini-fit" style="height:100%;">
            <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"
                 url="${ctxPath}/bpm/core/bpmInstCp/listData.do"  idField="id" multiSelect="true" showColumnsMenu="true"
                 sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
                <div property="columns">
                    <div type="checkcolumn" width="20"></div>
                    <div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>        
	                 											 																		<div field="userId" width="120" headerAlign="center" allowSort="true">${column.remarks}</div>
																	 																		<div field="groupId" width="120" headerAlign="center" allowSort="true">${column.remarks}</div>
																	 																		<div field="ccId" width="120" headerAlign="center" allowSort="true">${column.remarks}</div>
																	 																							 																							 																							 																							 																							 																		<div field="isRead" width="120" headerAlign="center" allowSort="true">${column.remarks}</div>
																	                  </div>
            </div>
        </div>
        
        <script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        </script>
        <script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
        <redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmInstCp" 
        	winHeight="450" winWidth="700"
          	entityTitle="[BpmInstCp]" baseUrl="bpm/core/bpmInstCp"/>
    </body>
</html>