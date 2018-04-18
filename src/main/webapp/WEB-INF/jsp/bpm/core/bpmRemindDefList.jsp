<%-- 
    Document   : [BpmRemindDef]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmRemindDef]列表管理</title>
      <%@include file="/commons/list.jsp"%>
    </head>
    <body>
        
        <redxun:toolbar entityName="com.redxun.bpm.core.entity.BpmRemindDef"/>
        
        <div class="mini-fit" style="height:100%;">
            <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"
                 url="${ctxPath}/bpm/core/bpmRemindDef/listData.do"  idField="id" multiSelect="true" showColumnsMenu="true"
                 sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
                <div property="columns">
                    <div type="checkcolumn" width="20"></div>
                    <div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>        
	                 											 																		<div field="solId" width="120" headerAlign="center" allowSort="true">方案ID</div>
																	 																		<div field="nodeId" width="120" headerAlign="center" allowSort="true">节点ID</div>
																	 																		<div field="name" width="120" headerAlign="center" allowSort="true">名称</div>
																	 																		<div field="action" width="120" headerAlign="center" allowSort="true">到期动作</div>
																	 																		<div field="relNode" width="120" headerAlign="center" allowSort="true">相对节点</div>
																	 																		<div field="event" width="120" headerAlign="center" allowSort="true">事件</div>
																	 																		<div field="dateType" width="120" headerAlign="center" allowSort="true">日期类型</div>
																	 																		<div field="expireDate" width="120" headerAlign="center" allowSort="true">期限</div>
																	 																		<div field="condition" width="120" headerAlign="center" allowSort="true">条件</div>
																	 																		<div field="script" width="120" headerAlign="center" allowSort="true">到期执行脚本</div>
																	 																		<div field="notifyType" width="120" headerAlign="center" allowSort="true">通知类型</div>
																	 																		<div field="htmlTemplate" width="120" headerAlign="center" allowSort="true">HTML模版</div>
																	 																		<div field="textTemplate" width="120" headerAlign="center" allowSort="true">TEXT模版</div>
																	 																		<div field="timeToSend" width="120" headerAlign="center" allowSort="true">开始发送消息时间点</div>
																	 																		<div field="sendTimes" width="120" headerAlign="center" allowSort="true">发送次数</div>
																	 																		<div field="sendInterval" width="120" headerAlign="center" allowSort="true">发送时间间隔</div>
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
        <redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmRemindDef" 
        	winHeight="450" winWidth="700"
          	entityTitle="[BpmRemindDef]" baseUrl="bpm/core/bpmRemindDef"/>
    </body>
</html>