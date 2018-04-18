<%-- 
    Document   : [planTask]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划编辑</title>
<%@include file="/commons/edit.jsp"%>
<style type="text/css">
 html body .assignId .mini-buttonedit-icon
    {
        background:url(${ctxPath}/styles/icons/preview.png) no-repeat 50% 50%;
    }
 html body .ownerId .mini-buttonedit-icon
    {
        background:url(${ctxPath}/styles/icons/icon-transmit.png) no-repeat 50% 50%;
    }
 html body .exeId .mini-buttonedit-icon
    {
        background:url(${ctxPath}/styles/icons/user.png) no-repeat 50% 50%;
    }
</style>
 <script type="text/javascript">
var data2=[
 { "id": "未开始", "text": "未开始" },
 { "id": "执行中", "text": "执行中" },
 { "id": "延迟", "text": "延迟" },
 { "id": "暂停", "text": "暂停" },
 { "id": "中止", "text": "中止" },
 { "id": "完成", "text": "完成" }];
 </script> 
</head>

<body>
	 <rx:toolbar toolbarId="toolbar1" pkId="${planTask.planId}" excludeButtons="remove" hideRemove="true" hideRecordNav="true">
	   <div class="self-toolbar">
	     <a class="mini-button" iconCls="icon-reload" plain="true" onclick="location.reload();">刷新</a>
	
	   </div>
	</rx:toolbar>
	
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="planId" class="mini-hidden"
					value="${planTask.planId}" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<caption>任务基本信息</caption>

					<tr style="display: none;">
						<th>项目ID </th>
						<td colspan="3"><input name="projectId" value="${planTask.projectId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr style="display: none;">
						<th>需求ID </th>
						<td colspan="3"><input name="reqId" value="${planTask.reqId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />
						</td>
					</tr>

					<tr>
						<th>任务标题 <span class="star">*</span> 
						</th>
						<td colspan="3"><input name="subject" value="${planTask.subject}"
							class="mini-textbox" vtype="maxLength:128" style="width: 90%"
							required="true" emptyText="请输入计划标题" />

						</td>
					</tr>

					<tr>
						<th>任务内容 </th>
						<td colspan="3"><textarea name="content" class="mini-textarea" vtype="maxLength:65535" style="width: 90%">${planTask.content}</textarea></td>
					</tr>

					<tr>
						<th>开始时间 <span class="star">*</span> 
						</th>
						<td><input id="pstartTime" name="pstartTime" value="${planTask.pstartTime}" showTime="true" allowInput="false" class="mini-datepicker" value="date"  format="yyyy-MM-dd HH:mm:ss"  timeFormat="HH:mm:ss"/></td>

					
						<th>结束时间 </th>
						<td><input id="pendTime" name="pendTime" value="${planTask.pendTime}" showTime="true" allowInput="false" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss"  timeFormat="HH:mm:ss"/></td>
					</tr>

					<%-- <tr>
						<th>实际开始时间 </th>
						<td><input id="startTime" name="startTime" value="${planTask.startTime}" showTime="true" allowInput="false" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss"  timeFormat="HH:mm:ss"/></td>
					
						<th>实际结束时间 </th>
						<td> <input id="endTime" name="endTime" value="${planTask.endTime}" showTime="true" allowInput="false" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss"  timeFormat="HH:mm:ss"/>
						</td>
					</tr> --%>
					<tr>
					<th>人员选择</th>
						<td><input id="assignId" name="assignId" value="${planTask.assignId}" text="${assign}" class="mini-buttonedit assignId"
							emptyText="请选择 分配人" allowInput="false" onbuttonclick="onSelectUser('assignId')" /></td>
						<td><input id="ownerId" name="ownerId" value="${planTask.ownerId}" text="${owner}" class="mini-buttonedit ownerId" emptyText="请选择 所属人"
							allowInput="false" onbuttonclick="onSelectUser('ownerId')" /></td>
						<td><input id="exeId" name="exeId" value="${planTask.exeId}" text="${exe}" class="mini-buttonedit exeId" emptyText="请选择 执行人"
							allowInput="false" onbuttonclick="onSelectUser('exeId')" /></td>
					</tr>
					 <tr>
						<th>状态 </th>
						<td colspan="3">
    <div name="status" id="status" class="mini-radiobuttonlist" repeatItems="1" repeatLayout="table" repeatDirection="vertical"
    textField="text" valueField="id" value="${planTask.status}"   data="data2" >
						</td>	
					</tr> 
				</table> </div>
			</div>
		</form>
		
	</div>
	<rx:formScript formId="form1" baseUrl="oa/pro/planTask"  entityName="com.redxun.oa.pro.entity.planTask"/>
	<script type="text/javascript">
	mini.parse();
	var pstartTime=mini.get("pstartTime");
	var pendTime=mini.get("pendTime");
	var startTime=mini.get("startTime");
	var endTime=mini.get("endTime");
	function onSelectUser(where){
		_TenantUserDlg(tenantId,true,function(user){
			var reporId=mini.get(where);
			
			reporId.setValue(user.userId);
			reporId.setText(user.fullname);
			
		});
	}
	//如果为完成状态则不允许修改
	if(${!empty planTask.status}){
		if("${planTask.status}"=="完成"){
			mini.get("status").disable();
		}
	}
	
	
	//双击打开人员选择
	$(function(){
		$("#assignId").dblclick(function(){
			onSelectUser('assignId');
		});
		$("#ownerId").dblclick(function(){
			onSelectUser('ownerId');
		});
		$("#exeId").dblclick(function(){
			onSelectUser('exeId');
		});
		
	})
	
	//重写了saveData方法
	function selfSaveData(){
		
		form.validate();
        if (!form.isValid()) {//验证表格
            return;
        }
        var formData=$("#form1").serializeArray();
        //加上租用Id
        if(tenantId!=''){
	        formData.push({
	        	name:'tenantId',
	        	value:tenantId
	        });
        }
        if(pendTime.getValue()){//如果有计划结束时间,,则不能前于开始时间
        	if(pstartTime.getValue()>pendTime.getValue()){
        		alert("计划结束时间不能早于开始时间");
            	return;
            }
        }
        /* if(endTime.getValue()){//如果有结束时间,,则不能前于开始时间
        	if(startTime.getValue()){if(startTime.getValue()>endTime.getValue()){
        		alert("实际结束时间不能早于开始时间");
            	return;
            }}
        	
        } */
        
        _SubmitJson({
        	url:"${ctxPath}/oa/pro/planTask/save.do",
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
        		CloseWindow('ok');
        	}
        });
	}
	</script>
</body>
</html>