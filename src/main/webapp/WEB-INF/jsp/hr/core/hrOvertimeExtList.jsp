<%-- 
    Document   : [HrOvertimeExt]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>[HrOvertimeExt]列表管理</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
        <%@include file="/commons/dynamic.jspf" %>
        <link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
        <link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
        <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
        <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script> 
        <script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
        <script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
        <!-- script src="${ctxPath}/scripts/printer/LodopFuncs.js" type="text/javascript"></script-->
        
    </head>
    <body>
        
       <%--  <redxun:toolbar entityName="com.redxun.hr.core.entity.HrOvertimeExt"/>--%>
       <div id="toolbar1" class="mini-toolbar" >
			<table style="width:100%;">
				<tr>
					<th style="width:100%;">
						<a class="mini-button" style="" plain="true" iconCls="icon-add" onclick="add">新增</a>
					</th>
				</tr>
			</table>
	  </div>
        <div class="mini-fit rx-grid-fit" style="height:100%;">
            <div id="datagrid2" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"
                 url="${ctxPath}/hr/core/hrErrandsRegister/getByType.do?type=OVERTIME"  idField="erId" multiSelect="true" showColumnsMenu="true"
                 sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true">
                <div property="columns">
                    <div type="checkcolumn" width="20"></div>
                    <div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>        
	                <div field="overtimeType" width="120" headerAlign="center" allowSort="true">加班类型</div>
	                <div field="pay" width="120" headerAlign="center" allowSort="true">结算方式</div>
	                <div field="startTime" width="120" headerAlign="center" allowSort="true">开始时间</div>
	                <div field="endTime" width="120" headerAlign="center" allowSort="true">结束时间</div>
					<div field="reason" width="120" headerAlign="center" allowSort="true">原因</div>
					<div field="status" width="120" headerAlign="center" allowSort="true">状态</div>
					<div field="createTime" width="120" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm">创建时间</div>
				</div>
            </div>
        </div>
        
        <script type="text/javascript">
        mini.parse();
        var grid=mini.get("datagrid2");
        grid.load();
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = /* '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>'
	                    +  */' <span class="icon-detail" title="审批明细" onclick="checkDetail(\'' + record.bpmInstId + '\')"></span>';
	            return s;
	        }
        	
	      //处理添加
	        function add(){
		         //检查是否存在流程配置，若没有，则启用本地的默认配置
		         _ModuleFlowWin({
		          title:'加班申请',
		          moduleKey:'HR_OVERTIME',
		          success:function(){
		           grid.load(); 
		          }
		         });
	        }
			//查看流程审批实例信息
			function checkDetail(bpmInstId){
			         _OpenWindow({
			          title:'审批明细',
			          width:950,
			          height:600,
			          url:__rootPath+'/bpm/core/bpmInst/get.do?actInstId='+bpmInstId
			         });
	        }
			
			/*激活tabs事件处理*/
			function onTabsActiveChanged(e) {
				var tabs = e.sender;
				var tab = tabs.getActiveTab();
				if (tab && tab._nodeid) {

					var node = tree.getNode(tab._nodeid);
					if (node && !tree.isSelectedNode(node)) {
						tree.selectNode(node);
					}
				}
			}
			
			grid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;
	            if(field == "overtimeType"){
	            	if(record.hrOvertimeExts[0].type=="usual")
	            		e.cellHtml="平常加班";
	            	if(record.hrOvertimeExts[0].type=="rest")
	            		e.cellHtml="休息日加班";
	            	if(record.hrOvertimeExts[0].type=="festival")
	            		e.cellHtml="节假日加班";
	            }
	            if(field == "pay"){
	            	if(record.hrOvertimeExts[0].pay=="pay")
	            		e.cellHtml="支付加班费";
	            	if(record.hrOvertimeExts[0].pay=="trans")
	            		e.cellHtml="加班调休";
	            }
	            //格式化日期
	            if (field == "startTime") {
	                if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
	            }
	            if (field == "endTime") {
	                if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
	            }
	            if(field == "status"){
	            	if(value=="NOTAPPROVED")
	            		e.cellHtml="未审批";
	            	if(value=="APPROVED")
		            	e.cellHtml="审批通过";
	            	if(value=="APPROVEDING")
	            		e.cellHtml="审批中";
	            }
	            
	    	});
			/* grid.on('update',function(){
 	        	_LoadUserInfo();
	        }); */
        </script>
        <script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
    </body>
</html>