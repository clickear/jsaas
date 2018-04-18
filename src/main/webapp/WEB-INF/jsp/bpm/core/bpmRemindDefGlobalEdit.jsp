<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>催办定义编辑</title>
<%@include file="/commons/edit.jsp"%>
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />

</head>
<body>
	
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;" id="toolbarBody">
	               	<a class="mini-button" iconCls="icon-save" plain="true" onclick="onOk">保存</a>
	               	<a class="mini-button" iconCls="icon-add" plain="true" onclick="onAdd">添加</a>
	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a>
	            </td>
	        </tr>
	    </table>
	</div>
	
	
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div id="layout1" class="mini-layout" style="width:100%;height:500px;">
			    <div title="催办配置" region="center"   >
			        <div id="baseTab" class="mini-tabs" activeIndex="0"  >
				      	<div title="基本配置" >
				      		<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
								<tr>
									<th>名　称 </th>
									<td colspan="3">
										<input name="name" 
										class="mini-textbox" vtype="maxLength:50" style="width: 90%" value="${nodeName }催办" />
										<input name="solId" class="mini-hidden" value="${solId }" />
										<input name="id" class="mini-hidden"  />
										<input name="nodeId" class="mini-hidden" value="global_remind_" />
									</td>
								</tr>
								
								<tr>
									<th>使用日历 </th>
									<td>
										<input class="mini-combobox" name="dateType"  style="width:150px;" textField="text" valueField="id" nullItemText="请选择.."
										data="[{id:'calendar',text:'使用日历'},{id:'common',text:'不使用日历'}]"
		     								  showNullItem="true" value="common" />	
									</td>
									
									<th>到期动作 </th>
									<td >
										<input class="mini-combobox" name="action"  style="width:150px;" textField="text" valueField="id" nullItemText="请选择.."
										data="[{id:'none',text:'无动作'},{id:'approve',text:'审批任务'},{id:'back',text:'驳回'},{id:'backToStart',text:'驳回到发起人'},{id:'script',text:'执行脚本'}]"
		     								  showNullItem="true" value="approve"  onValuechanged="changeAction" />
			
									</td>
								</tr>
								<tr>
									<th>期　限 </th>
									<td colspan="3">
										<input name="expireDateDay" class="mini-textbox" vtype="maxLength:50" style="width:50px" />天
										<input name="expireDateHour" class="mini-combobox" style="width:50px;" textField="val" valueField="key" 
    										url="${ctxPath}/commons/data/hours.jsp"  value="0"  allowInput="false"/>小时
										<input name="expireDateMinute" class="mini-combobox" style="width:50px;" textField="val" valueField="key" 
    										url="${ctxPath}/commons/data/minute.jsp" value="0"  allowInput="false"/>分钟
									</td>
								</tr>
								<tr>
									<th>条　件 </th>
									<td colspan="3">
										<textarea name="condition" class="mini-textarea"
											vtype="maxLength:1000" style="width: 90%"></textarea>
									</td>
								</tr>
								<tr id="trScript">
									<th>到期执行脚本 </th>
									<td colspan="3">
										<textarea name="script" class="mini-textarea"
											vtype="maxLength:1000" style="width: 90%"></textarea>
									</td>
								</tr>
							</table>
				      	</div>
				      	<div title="通知配置" >
				      		<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
				      			<tr>
									<th>通知类型 </th>
									<td>
										<div name="notifyType" class="mini-checkboxlist" repeatItems="5" repeatLayout="table" value=""
									        textField="text" valueField="name"  url="${ctxPath}/bpm/core/bpmConfig/getNoticeTypes.do" >
									    </div>
													
									</td>
								</tr>
			
								<tr>
									<th>发送消息时间 </th>
									<td>
										<input name="timeToSendDay" class="mini-textbox" vtype="maxLength:50" style="width:50px" />天
										<input name="timeToSendHour" class="mini-combobox" style="width:50px;" textField="val" valueField="key" 
    										url="${ctxPath}/commons/data/hours.jsp"  value="0"  allowInput="false"/>小时
										<input name="timeToSendMinute" class="mini-combobox" style="width:50px;" textField="val" valueField="key" 
    										url="${ctxPath}/commons/data/minute.jsp" value="0"  allowInput="false"/>分钟
									</td>
								</tr>
			
								<tr>
									<th>发送次数 </th>
									<td>
										<input name="sendTimes" class="mini-spinner"  minValue="1" maxValue="10" />
									</td>
								</tr>
			
								<tr>
									<th>发送时间间隔 </th>
									<td>
										<input name="sendIntervalDay" class="mini-textbox" vtype="maxLength:50" style="width:50px" />天
										<input name="sendIntervalHour" class="mini-combobox" style="width:50px;" textField="val" valueField="key" 
    										url="${ctxPath}/commons/data/hours.jsp"  value="0"  allowInput="false"/>小时
										<input name="sendIntervalMinute" class="mini-combobox" style="width:50px;" textField="val" valueField="key" 
    										url="${ctxPath}/commons/data/minute.jsp" value="0"  allowInput="false"/>分钟
			
									</td>
								</tr>
								
				      		</table>
				      	</div>
				    </div>
			    </div>
			    <div title="配置列表" region="east" width="220" >
			        <div id="gridRemind" class="mini-datagrid" style="width:100%;height:100%;" showPager="false" 
			        url="${ctxPath}/bpm/core/bpmRemindDef/listJson.do?solId=${solId}&nodeId=global_remind_" 
			        onrowclick="rowClick">
						    <div property="columns">
						        <div field="name"   headerAlign="center" allowSort="true">名称</div>    
						        <div name="action" cellCls="actionIcons"  headerAlign="center" align="center" renderer="onActionRenderer" >操作</div>
						    </div>
					</div>
			    </div>
			</div>
			
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/core/bpmRemindDef" entityName="com.redxun.bpm.core.entity.BpmRemindDef" />
		
	<script type="text/javascript">
		var nodeName="${nodeName }";
	
		function loadList(){
			var grid = mini.get('gridRemind');
			grid.load();
		}
		loadList();
		
		function onAdd(){
			mini.getByName("id").setValue("");
		}
		
		function changeAction(e){
			toggleScript(e.value);
		}
		
		function toggleScript(val){
			var obj=$("#trScript");
			if(val=="script"){
				obj.show();
			}
			else{
				obj.hide();
			}
		}
		
	
		function handleFormData(formData){
			var result={isValid:true};
			result.formData=formData;
			
			sendInterval=getData("sendInterval");
			timeToSend=getData("timeToSend");
			expireDate=getData("expireDate");
			
			var send=getMiniute(timeToSend) + getMiniute(sendInterval)* mini.getByName("sendTimes").value;
			var expire=getMiniute(expireDate);
			if(send>expire){
				alert("配置过期时间小于催办时间!");
				result.isValid=false;
				return result;
			}
			
			formData.push({name:"sendInterval",value:sendInterval});
			formData.push({name:"timeToSend",value:timeToSend});
			formData.push({name:"expireDate",value:expireDate});
			formData.push({name:"nodeName",value:nodeName});
			
			return result;
		}
		
		function getMiniute(json){
			var json=eval("("+ json +")");
			var total=parseInt( json.day * 1440) + parseInt( json.hour * 60) + parseInt( json.minute);
			return total;
		}
		
		
		function getData(preName){
			var day=mini.getByName(preName +"Day").value;
			var hour=mini.getByName(preName +"Hour").value;
			var minute=mini.getByName(preName +"Minute").value;
			
			day=(!day)?0:day;
			return JSON.stringify( {day:day,hour:hour,minute:minute});
		}
		
		function setData(row,preName){
			var json=eval("(" + row[preName] + ")");
			var objDay=mini.getByName(preName +"Day");
			var objHour=mini.getByName(preName +"Hour");
			var objMinute=mini.getByName(preName +"Minute");
			objDay.setValue(json.day);
			objHour.setValue(json.hour);
			objMinute.setValue(json.minute);
		}
		
		function successCallback(result){
			loadList();
		}
		
		function onActionRenderer(e) {
            var record = e.record;
            var pkId=record.pkId;
            var s = ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
            return s;
	    }
		
		function delRow(pkId) {
			if (!confirm("确定删除选中记录？")) return;
	        _SubmitJson({
	        	url:__rootPath + "/bpm/core/bpmRemindDef/del.do",
	        	method:'POST',
	        	data:{ids: pkId},
	        	 success: function(text) {
	        		 loadList();
	        		 mini.getByName("id").setValue("");
	            }
	         });
	    }
		
		function rowClick(e){
			var row=e.record;
			for(var key in row){
				var obj=mini.getByName(key);
				if(!obj) continue;
				obj.setValue(row[key]);
			}
			setData(row,"sendInterval");
			setData(row,"timeToSend");
			setData(row,"expireDate");
			
			changeAction(row.action);
		}
		

	</script>
</body>
</html>