<!DOCTYPE html>
<html >
<head>
<title>催办实例管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<div class="mini-toolbar" >
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
                    </td>
                </tr>
	                <tr>
	                    <td style="white-space:nowrap;padding:5px;" colspan="2">
	                    	<span class="label">创建时间从:</span><input name="Q_CREATE_TIME__D_GE" class="mini-datepicker" format="yyyy-MM-dd" style="width:110px"/>
	                    	&nbsp;&nbsp;<span class="label">至</span>&nbsp;&nbsp;
	                    	<input name="Q_CREATE_TIME__D_LE" format="yyyy-MM-dd" class="mini-datepicker" style="width:110px"/>
	                    	状态:
	                    	<input name="Q_STATUS__S_EQ" class="mini-combobox" style="width:150px;" textField="text" valueField="id" 
    data="[{id:'create',text:'创建'},{id:'run',text:'运行'},{id:'finish',text:'完成'}]"   showNullItem="true" allowInput="true"/> 
	                        <a class="mini-button" iconCls="icon-search" onclick="searchForm(this)">查询</a>
	                        <a class="mini-button" iconCls="icon-cancel" onclick="clearSearch()">清空查询</a>
	                    </td>
	                </tr>
            </table>
        </div>
	
	

	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/bpm/core/bpmRemindInst/listJson.do" idField="id"
			multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				
				<div field="solutionName" width="120" headerAlign="center" >方案</div>
				<div field="nodeName" width="120" headerAlign="center">节点</div>
				<div field="taskId" width="120" headerAlign="center" >任务ID</div>
				<div field="name" width="120" headerAlign="center" allowSort="true">名称</div>
				<div field="action" width="120" headerAlign="center" renderer="onActionRenderer">到期动作</div>
				<div field="expireDate" width="120" headerAlign="center" >到期时间</div>
				<div field="notifyType" width="120" headerAlign="center">通知类型</div>
				<div field="timeToSend" width="120" headerAlign="center">开始催办时间</div>
				<div field="sendTimes" width="120" headerAlign="center">发送次数</div>
				<div field="sendInterval" width="120" headerAlign="center" renderer="onSendIntervalRender">催办间隔</div>
				<div field="status" width="120" headerAlign="center" renderer="onStatusRenderer">状态</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		
		function onActionRenderer(e){
			var record = e.record;
			var state = record.action;
			var arr = [ { 'key' : 'none','value' : '无动作','css' : 'green'}, 
			            {'key' : 'approve', 'value' : '审批','css' : 'red'}, 
			            {'key' : 'script','value' : '执行脚本','css' : 'orange'},
			            {'key' : 'backToStart','value' : '驳回到发起人','css' : 'yellow'},
			            {'key' : 'back','value' : '驳回','css' : 'grey'}
			            ];
			
			return $.formatItemValue(arr,state);
		}
		
		function onSendIntervalRender(e){
			var record = e.record;
			var interval = record.sendInterval;
			
			return calcTime(interval);
			
			
		}
		
		function calcTime(total){
			var day=parseInt( total  / 1440);
			var tmp=total % 1440;
			var hour=parseInt( tmp / 60);
			var miniute= tmp % 60;
			if(total>=1440){
				return day +"天" + hour +"时" + miniute + "分";
			}
			else if (total>=60){
				return  hour +"时" + miniute + "分";
			}
			return total +"分";
			
		}
		
		function onStatusRenderer(e){
			var record = e.record;
			var state = record.status;
			var arr = [ { 'key' : 'create','value' : '创建','css' : 'green'}, 
			            {'key' : 'run', 'value' : '运行','css' : 'red'}, 
			            {'key' : 'finish','value' : '完成','css' : 'orange'}
			            ];
			
			return $.formatItemValue(arr,state);
		}
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.bpm.core.entity.BpmRemindInst" winHeight="450"
		winWidth="700" entityTitle="[BpmRemindInst]"
		baseUrl="bpm/core/bpmRemindInst" />
</body>
</html>