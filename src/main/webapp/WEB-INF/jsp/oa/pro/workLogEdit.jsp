<%-- 
    Document   : 工作任务编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>日志编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	
	<rx:toolbar toolbarId="toolbar1" pkId="${workLog.logId}"  excludeButtons="save" hideRemove="true" hideRecordNav="true">
	<div class="self-toolbar">
	<a id="saving" class="mini-button" iconCls="icon-draft" plain="true" onclick="selfSaveData('DRAFT')">保存草稿</a>
    <a id="pending" class="mini-button" iconCls="icon-save" plain="true" onclick="selfSaveData('PENDING')">提交保存</a>
      
	</div>
	</rx:toolbar>
	<div id="p1" class="form-outer mini-left-form shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="logId" class="mini-hidden" value="${workLog.logId}" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<caption>日志基本信息</caption>

					<tr id="trplanId" >
						<th>计　　划</th>
						<td colspan="3">
							<input 
						       	id="PlanTask" 
						       	name="PlanTask" 
						       	class="mini-treeselect" 
						       	url="${ctxPath}/oa/pro/workLog/getMyPlan.do"  
						       	multiSelect="false"  
						       	valueFromSelect="true"
						        textField="subject" 
						        valueField="planId" 
						        value="${workLog.planTask.planId}"  
						        text="${planSubject}"  
						        allowInput="false" 
						        width="300px"  
						        showRadioButton="true" 
						        showFolderCheckBox="false"  
						        showTreeIcon="false" 
					        />
					        <a  class="mini-button" iconCls="icon-refresh" plain="true" onclick="setValueNull">置空</a>
						</td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								任务内容 
								<span class="star">*</span>
							</span>
						</th>
						<td colspan="3">
							<textarea 
								name="content" 
								class="mini-textarea" 
								vtype="maxLength:1024" 
								style="width: 90%;height: 200px;" 
								required="true" 
								emptyText="请输入任务内容"
							>${workLog.content}</textarea>
						</td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								开始时间 
								<span class="star">*</span>
							</span>
						</th>
						<td>
							<input 
								width="200"  
								id="startTime" 
								name="startTime" 
								value="${workLog.startTime}" 
								showTime="true" 
								allowInput="false" 
								class="mini-datepicker" 
								value="date"  
								required="true" 
								format="yyyy-MM-dd HH:mm:ss" 
								timeFormat="HH:mm:ss" 
								showTime="true"  
								showClearButton="true"
							/>
						</td>
					
						<th>
							<span class="starBox">
								结束时间 <span class="star">*</span>
							</span>
						</th>
						<td>
							<input 
								width="200" 
								id="endTime" 
								name="endTime" 
								value="${workLog.endTime}" 
								allowInput="false" 
								class="mini-datepicker" 
								value="date"  
								required="true" 
								timeFormat="HH:mm:ss" 
								showTime="true" 
								format="yyyy-MM-dd HH:mm:ss"  
								timeFormat="HH:mm:ss" 
								showClearButton="true"
							/>
						</td>
					</tr>
					<%-- <tr>
						<th>审核人 ：</th>
						<td colspan="3">
							<input id="checker" class="mini-textboxlist" name="checker"   style="width:250px;" value="${workLog.checker}" text="${checker}"  allowInput="false" required="true"  />
							<a class="mini-button" iconCls="icon-grant" plain="true" onclick="onSelectUser('checker')">选择</a>
							</td>
					</tr> --%>
				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	$('.mini-tabs-body:visible', parent.document).addClass('index_box');
	$('.show_iframe:visible', parent.document).addClass('index_box');
	
	
	mini.parse();
	
	
	var startTime=mini.get("startTime");
	var endTime=mini.get("endTime");


	//重写了saveData方法
	function selfSaveData(issave){
		var form = new mini.Form("form1");
		form.validate();
        if (!form.isValid()) {//验证表格
            return; }
        var formData=$("#form1").serializeArray();
        //加上租用Id
        if(tenantId!=''){
	        formData.push({
	        	name:'tenantId',
	        	value:tenantId });
        }
        if(endTime.getValue()){//如果有实际结束时间,,则不能前于开始时间
        	if(startTime.getValue()){if(startTime.getValue()>endTime.getValue()){
        		alert("实际结束时间不能早于开始时间");
            	return; }}  }
        _SubmitJson({
        	url:"${ctxPath}/oa/pro/workLog/save.do?type="+issave,
        	method:'POST',
        	data:formData,
        	success:function(result){
        		//如果存在自定义的函数，则回调
        		if(isExitsFunction('successCallback')){
        			successCallback.call(this,result);
        			return;	
        		}
        		var pkId=mini.get("pkId").getValue();
        		//为更新
        		if (pkId!=''){
        			CloseWindow('ok');
        			return;
        		}
        		CloseWindow('ok');} });
	}
	
	
	//选择审核人
	function onSelectUser(where){
		_TenantUserDlg(tenantId,true,function(user){
			var reporId=mini.get(where);
			reporId.setValue(user.userId);
			reporId.setText(user.fullname);
			
		});
	}
	
	
	if("${workLog.status}"=="PENDING"){//提交之后就让提交保存按钮取消
		$(function(){
			$("#pending").hide();
		})
	}
	
	//如果是从plan打开的则隐藏日志的pianId不允许修改
	if(${!empty planTask}){
		$(function(){
			$("#trplanId").hide();
		});
	}
	
	//把planId置为空
	 function setValueNull() {
         var thisplanId = mini.get("PlanTask");
         thisplanId.setValue("");
     }
	
	 addBody();
	</script>
	<rx:formScript formId="form1" baseUrl="oa/pro/workLog"  entityName="com.redxun.oa.pro.entity.WorkLog"/>
	
</body>
</html>