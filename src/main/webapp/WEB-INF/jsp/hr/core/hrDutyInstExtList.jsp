<%-- 
    Document   : [HrDutyInstExt]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[HrDutyInstExt]列表管理</title>
       <%@include file="/commons/list.jsp"%>
    </head>
    <body>
        
        <redxun:toolbar entityName="com.redxun.hr.core.entity.HrDutyInstExt"/>
        
        <div class="mini-fit" style="height:100%;">
            <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"
                 url="${ctxPath}/hr/core/hrDutyInstExt/listData.do"  idField="extId" multiSelect="true" showColumnsMenu="true"
                 sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
                <div property="columns">
                    <div type="checkcolumn" width="20"></div>
                    <div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>        
	                 											 																		<div field="startSignIn" width="120" headerAlign="center" allowSort="true">开始签到</div>
																	 																		<div field="dutyStartTime" width="120" headerAlign="center" allowSort="true">上班时间</div>
																	 																		<div field="endSignIn" width="120" headerAlign="center" allowSort="true">签到结束时间</div>
																	 																		<div field="earlyOffTime" width="120" headerAlign="center" allowSort="true">早退计时</div>
																	 																		<div field="dutyEndTime" width="120" headerAlign="center" allowSort="true">下班时间</div>
																	 																		<div field="signOutTime" width="120" headerAlign="center" allowSort="true">签退结束</div>
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
        <redxun:gridScript gridId="datagrid1" entityName="com.redxun.hr.core.entity.HrDutyInstExt" 
        	winHeight="450" winWidth="700"
          	entityTitle="[HrDutyInstExt]" baseUrl="hr/core/hrDutyInstExt"/>
    </body>
</html>