<%-- 
    Document   : 流程代理设置编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>流程代理设置编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${bpmAgent.agentId}" />
	<div class="shadowBox90">
		<div id="p1" class="form-outer">
			<form id="form1" method="post">
					<input id="pkId" name="agentId" class="mini-hidden" value="${bpmAgent.agentId}" />
					<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
						<caption>流程代理设置基本信息</caption>
	
						<tr>
							<th><span class="starBox">代理简述 <span class="star">*</span></span> </th>
							<td>
								<input name="subject" value="${bpmAgent.subject}" class="mini-textbox" vtype="maxLength:100" style="width: 90%" required="true" emptyText="请输入代理简述" />
							</td>
							<th><span class="starBox">代理类型 <span class="star">*</span></span> 
							</th>
							<td>
								<input id="type" name="type" value="${bpmAgent.type}" class="mini-radiobuttonlist" vtype="maxLength:20" 
									data="[{id:'ALL',text:'全部'},{id:'PART',text:'部分'}]" onvaluechanged="typeChange" allowInput="false"
								style="width: 90%" required="true" emptyText="请输入代理类型" />
							</td>
						</tr>
						<tr>
							<th><span class="starBox">代  理  给 <span class="star">*</span></span> </th>
							<td>
								<input name="toUserId" value="${bpmAgent.toUserId}" class="mini-buttonedit icon-user-button" 
								onbuttonclick="selectToUser" text="<cf:userLabel userId='${bpmAgent.toUserId}' />"
								vtype="maxLength:64" style="width: 90%" required="true" emptyText="请输入代理人" />
							</td>
							<th><span class="starBox">状　　态 <span class="star">*</span></span></th>
							<td>
								<ui:radioStatus name="status" value="${bpmAgent.status}" emptyText="请输入状态" required="true"></ui:radioStatus>
							</td>
						</tr>
						<tr>
							<th><span class="starBox">开始时间 <span class="star">*</span></span></th>
							<td>
								<input name="startTime" value="${bpmAgent.startTime}" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm:ss" showTime="true" vtype="maxLength:19" style="width: 90%" required="true" emptyText="请输入开始时间" />
							</td>
							<th><span class="starBox">结束时间 <span class="star">*</span></span></th>
							<td>
								<input name="endTime" value="${bpmAgent.endTime}" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm:ss" showTime="true" vtype="maxLength:19" style="width: 90%" required="true" emptyText="请输入结束时间" />
							</td>
						</tr>
						<tr>
							<th>描　　述 </th>
							<td colspan="3"><textarea name="descp" class="mini-textarea" vtype="maxLength:300" style="width: 90%">${bpmAgent.descp}</textarea></td>
						</tr>
					</table>
				
				
				<div id="solDiv" style="display:none">
					<div class="form-inner">
						<div class="mini-toolbar" style="border-bottom: none">
							<table style="width:100%">
								<tr>
									<td style="width:100%">
										<a class="mini-button" iconCls="icon-add" plain="true" onclick="addSols">添加流程方案</a>
										<a class="mini-button" iconCls="icon-remove" plain="true" onclick="delSols">删除</a>
									</td>
								</tr>
							</table>
						</div>
						<div id="solGrid" class="mini-datagrid" style="width:100%;height:300px"idField="asId"
							allowCellEdit="true" allowCellSelect="true" multiSelect="true" 
							<c:if test="${not empty bpmAgent.agentId }">
							url="${ctxPath}/bpm/core/bpmAgent/getAgentSol.do?agentId=${bpmAgent.agentId}"
							</c:if>
	        				editNextOnEnterKey="true" editNextRowCell="true" showPager="false">
							<div property="columns">
								<div type="indexcolumn" width="20"></div>
								<div type="checkcolumn" width="50"></div>
								<div field="solName" width="200" headerAlign="center" >解决方案名称</div>
								<div field="agentType" width="80" headerAlign="center" >代理类型
									<input property="editor" class="mini-combobox" style="width:100%;" data="[{id:'ALL',text:'全部'},{id:'PART',text:'部分'}]" />
								</div>
								<div field="condition" width="220" headerAlign="center" >代理条件
									 <input property="editor" class="mini-textarea" style="width:200px;" vtype="maxLength:300"/>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/core/bpmAgent" entityName="com.redxun.bpm.core.entity.BpmAgent" />
	<script type="text/javascript">
		var agentId='${bpmAgent.agentId}';
		var solGrid=mini.get('solGrid');
		$(function(){
			if(agentId!=''){
				solGrid.load();
			}
			typeChange();
		});
		function typeChange(){
			var val=mini.get('type').getValue();
			if(val=='PART'){
				$("#solDiv").css("display","");
			}else{
				$("#solDiv").css("display","none");
			}
		}
		
		//添加解决方案
		function addSols(){
			var totals=solGrid.getTotalCount();
			_BpmSolutionDialog(false,function(sols){
				for(var i=0;i<sols.length;i++){
					var find=false;
					for(var j=0;j<totals-1;j++){
						var row=solGrid.getRow(j);
						if(sols[i].solId==row.solId){
							find=true;
							break;
						}
					}
					if(!find){
						solGrid.addRow({
							solId:sols[i].solId,
							solName:sols[i].name
						})
					}
				}
			});
		}
		
		//处理表单的数据
		function handleFormData(formData){
			var val=mini.get('type').getValue();
			if(val=='PART'){
				formData.push({
					name:'sols',
					value:mini.encode(solGrid.getData())
				});
			}
			
			return {
				isValid:true,
				formData:formData
			};
		}
		
		function selectToUser(e){
			var buttonEdit=e.sender;
			_UserDlg(true,function(user){
				buttonEdit.setValue(user.userId);
				buttonEdit.setText(user.fullname);
			});
		}
		
		function delSols(e){
			var sels=solGrid.getSelecteds();
			if(sels.length==0){
				return;
			}
			var asIds=[];
			for(var i=0;i<sels.length;i++){
				if(sels[i].asId){
					asIds.push(sels[i].asId);
				}
			}
			if(asIds.length>0){
				_SubmitJson({
					url:__rootPath+'/bpm/core/bpmAgent/delSols.do',
					data:{
						asIds:asIds.join(',')
					},
					method:'POST',
					success:function(text){
					}
				});
			}
			solGrid.removeRows(sels);
		};
		addBody();
	</script>
</body>
</html>