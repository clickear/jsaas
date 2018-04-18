<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>流程实例启动页</title>
<%@include file="/commons/customForm.jsp"%>
	<style>
		body{
			background:#f8f8f8;
		}
		#processForm, .details{
			margin: 0 0  30px 0;
		}
		.mini-tabs-bodys,
		.explain .mini-panel-border{
			border: none;
		}
		.mini-panel-border .mini-grid-row:last-of-type .mini-grid-cell{
			border-bottom: none; 
		}
		
		.shadowBox .customform{
			width: 100% !important;
		}
		
	</style>
</head>
<body>
	<script type="text/javascript">
		indentBody();
	</script>
	<div class="header">
		<div style="position:absolute; top:1px; right:1px;z-index:999;	float: right;text-align: right;">
			<c:if test="${not empty formModels && fn:length(formModels)>0}">
			<a class="button"  onclick="saveDraft()">保存</a> 
			</c:if> 
			<a class="button" onclick="formPrint()">打印</a>
			<c:if test="${not empty param['tmpInstId']}">
			<a class="button" onclick="showLinkData()">关联数据</a>
			</c:if>
<!-- 			<a class="button" onclick="CloseWindow()" style="background-color: red">关闭</a>  -->
		</div>
	</div>

	<c:if test="${processConfig.isShowStartUsers=='true'}">
		<table class="form-table" cellpadding="0" cellspacing="1" style="width: 100%;">
			<tr>
				<th class="form-table-th">下一步</th>
				<th class="form-table-th">审批人</th>
			</tr>
			<c:forEach items="${destNodeUserList}" var="destNodeUser"	varStatus="i">
				<tr>
					<td class="form-table-td" 	style="width: 100px; text-align: center; white-space: nowrap;">
						<c:if test="${startEventConfig.allowPathSelect=='true'}">
							<input type="radio" name="destNodeId" value="${destNodeUser.nodeId}" />
						</c:if> ${destNodeUser.nodeText}
					</td>
					<td class="form-table-td"><c:choose>
							<c:when test="${not empty destNodeUser.taskNodeUser}">
								<c:if test="${destNodeUser.taskNodeUser.nodeType=='userTask'}">
									<input type="hidden" name="nodeId"	value="${destNodeUser.nodeId}" />
									<input class="mini-textboxlist checkusers" name="users" id="users${i.count}"
										text="${destNodeUser.taskNodeUser.userFullnames}"	value="${destNodeUser.taskNodeUser.userIds}"
										style="width: 50%;"
										<c:if test="${startEventConfig.allowNextExecutor!='true'}">readOnly="true"</c:if>
										required="true" />
									<c:if test="${startEventConfig.allowNextExecutor=='true'}">
										<a class="mini-button" iconCls="icon-users" plain="true"	onclick="selectUsers(this)" alt="选择审批人">选择审批人</a>
									</c:if>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:forEach items="${destNodeUser.fllowNodeUserMap}" var="destMap" varStatus="j">
									<c:set var="taskNodeUser" value="${destMap.value}" />
									<c:if test="${taskNodeUser.nodeType=='userTask'}">
											${taskNodeUser.nodeText}
										 <input class="mini-textboxlist checkusers" name="users"	nodeId="${taskNodeUser.nodeId}"
											id="users${i.count}${j.count}"	text="${taskNodeUser.userFullnames}"	value="${taskNodeUser.userIds}"
											<c:if test="${startEventConfig.allowNextExecutor!='true'}">readOnly="true"</c:if>
											required="true" emptyText="请选择用户" />
										<c:if test="${startEventConfig.allowNextExecutor=='true'}">
											<a class="mini-button" iconCls="icon-users" plain="true"	onclick="selectUsers(this)" alt="选择审批人">选择审批人</a>
										</c:if>

										<c:if
											test="${(not empty taskNodeUser.groupIds)&& taskNodeUser.groupIds!=''}">
											<input class="mini-textboxlist checkgroups" name="groups" 	id="groups${i.count}${j.count}"
												text="${taskNodeUser.groupNames}"	value="${taskNodeUser.groupIds}"
												<c:if test="${startEventConfig.allowNextExecutor!='true'}">readOnly="true"</c:if>
												required="true" emptyText="请选择用户组" />
											<c:if test="${startEventConfig.allowNextExecutor=='true'}">
												<a class="mini-button" iconCls="icon-users" plain="true" onclick="selectGroups(this)" alt="选择审批组">选择审批组</a>
											</c:if>
										</c:if>
									</c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	
	<div id="errorMsg" style="margin:0 auto; width: 1080px; padding-top:28px;<c:if test="${formModel.result}">display:none;</c:if> color:red;">
		${formModel.msg}
	</div>
	
	<div class="shadowBox">	
		<form id="processForm">
			<input class="mini-hidden" name="solId" value="${bpmSolution.solId}" type="hidden"/>
			<input class="mini-hidden" name="actDefId" value="${bpmDef.actDefId}" type="hidden"/>
			<input class="mini-hidden" name="formViewId" value="${bpmFormView.viewId}" type="hidden"/> 
			<input class="mini-hidden" name="bpmInstId" value="${bpmInst.instId}" type="hidden"/>
			<input class="mini-hidden" name="nodeUserMustConfig" id="nodeUserMustConfig" value="${processConfig.mustChooseConfig}" type="hidden"/>
			<input class="mini-hidden" name="nodeUserIds"  id="nodeUserIds" type="hidden"/>
<!-- 			<div>&nbsp;</div> -->
				<rxTag:processForm formModels="${formModels}"></rxTag:processForm>
				<c:if test="${not empty formModels}">
					<c:set var="formModel" value="${formModels[0]}"></c:set>
				</c:if>
		</form>
		<div>
			<table class="table-view" id="bpmInstCtl" >
				<tr class="atable AToPrint" style="display: none;">
						<th width="120" rowspan="2" style="background-color: #F4F4F4;font-size: 10px;">[附件打印]
						</th>
						<td>
							<input 
								id="printAgroupIds" 
								class="mini-textboxlist" 
								allowInput="false" 
								name="printAgroupIds"  
								style="width:80%;" 
								value="${APrintGroupIds}" 
								text="${APrintGroupNames}"
							/>
							<a class="mini-button" iconCls="icon-grant" onclick="selGroups('printAgroupIds')">用户组</a>
							<a class="mini-button" iconCls="icon-clear" onclick="clearGroups('printAgroupIds')">清空</a>
						</td>
					</tr>
					<tr class="atable AToPrint" style="display: none;">
						<td>
							<input 
								id="printAuserIds" 
								class="mini-textboxlist" 
								allowInput="false" 
								name="printAuserIds"  
								style="width:80%;" 
								value="${APrintUserIds}" 
								text="${APrintUserNames}"
							/>
							<a class="mini-button" iconCls="icon-grant" onclick="selUsers('printAuserIds')">用户</a>
							<a class="mini-button" iconCls="icon-clear" onclick="clearUsers('printAuserIds')">清空</a>
						</td>
					</tr>
					<tr class="atable AToDown" style="display: none;">
						<th width="120" rowspan="2" style="background-color: #F4F4F4;font-size: 10px;">[附件下载]
						</th>
						<td>
							<input 
								id="downAgroupIds" 
								class="mini-textboxlist" 
								allowInput="false" 
								name="downAgroupIds"  
								style="width:80%;" 
								value="${ADownGroupIds}" 
								text="${ADownGroupNames}"
							/>
							<a class="mini-button" iconCls="icon-grant" onclick="selGroups('downAgroupIds')">用户组</a>
							<a class="mini-button" iconCls="icon-clear" onclick="clearGroups('downAgroupIds')">清空</a>
						</td>
					</tr>
						<tr class="atable AToDown" style="display: none;">
						<td>
							<input 
								id="downAuserIds" 
								class="mini-textboxlist" 
								allowInput="false" 
								name="downAuserIds"  
								style="width:80%;" 
								value="${ADownUserIds}" 
								text="${ADownUserNames}"
							/>
							<a class="mini-button" iconCls="icon-grant" onclick="selUsers('downAuserIds')">用户</a>
							<a class="mini-button" iconCls="icon-clear" onclick="clearUsers('downAuserIds')">清空</a>
						</td>
					</tr>
			</table>
		</div>
		<div class="details">
			<div class="detailsBox">
				<table class="table-view form-outer5" id="bpmImage" style="margin-top:12px;">
					<tr>
						<th style="width:100px;" >提交说明</th>
						<td>
							<div>
								<input 
									name="opinionSelect"  
									id="opinionSelect" 
									class="mini-combobox"  
									emptyText="常用处理说明..." 
									style="width:40%;" 
									minWidth="120" 
									url="${ctxPath}/bpm/core/bpmOpinionLib/getUserText.do" 
									valueField="opId" 
									textField="opText"  
									onvaluechanged="showOpinion()"  
									ondrawcell="onDrawCells"
								/>
								<a class="mini-button" iconCls="icon-archives" onclick="saveOpinion()" >保存处理说明</a>
							</div>
							<div>
								<textarea 
									id="opinion" 
									name="opinion"  
									placeholder="处理说明" 
									onpropertychange="if(value.length>999){value=value.substr(0,999);alert('不能超过999字')}"
								>${opinion}</textarea> 
							    <script> 
							    	$(function(){
							    		$("#opinion").autoTextarea({maxHeight:250});
							    	});
							    </script>
						    	<a  class="submitButton" <c:if test="${formModel.result}"> onclick="startProcess()" </c:if> id="submitBtn" >提交</a>	
							</div>
									
						</td>
					</tr>
					<tr>
						<th  style="width:100px;">说明附件</th>
						<td class="customform explain">
							<div 
								id="opFiles" 
								name="opFiles" 
								class="upload-panel"  
								style="width:auto;" 
								isDown="false" 
								isPrint="false" 
								value='${fileIds}' 
								readOnly="false"
							></div>   
						</td>
					</tr> 		
					<tr>
						<th style="width:15%;" >表单说明</th>
						<td colspan="3">
							${formModel.description}
						</td>
					</tr>
					<tr>
						<td colspan="2" style="border-top:none;">
							更多
<!-- 							<input type="checkbox" name="moreInfo" onclick="bpmMoreCheck(this)"/> -->
							<input class="mini-checkbox" onclick="bpmMoreCheck(this)"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" id="moreInfoTab" style="display:none;" class="customform">
							<div class="mini-tabs" activeIndex="0"  style="width:100%;height:auto;" bodyStyle="border:none;padding:6px;">
							    <div title="高级">
							         <a class="mini-button " plain="true" iconCls="icon-add" onclick="toConfigDealer()">起草节点选择后续处理人</a>
							         <div id="showNodeDealerConfig"></div>
							    </div>
							    <div title="表格">
							       <div 
							       		id="nodeGrid" 
							       		class="mini-datagrid" 
							       		style="width:100%;" 
							       		height="auto" 
							       		allowResize="false" 
							       		showPager="false"  
							       		pageSize="100" 
							       		ondrawcell="onNodeDrawCells" 
							       		allowSortColumn="false"
										url="${ctxPath}/bpm/core/bpmInst/getActNodeInsts.do?solId=${bpmSolution.solId}&excludeGateway=true" 
										idField="nodeId" 
										allowAlternating="true" 
									>
										<div property="columns">
											<div type="indexcolumn" width="30"></div>
											<div field="nodeName" width="100" headerAlign="center" allowSort="true">节点名称</div>
											<div field="orginalFullNames" width="100" headerAlign="center" allowSort="true" >默认处理人</div>
											<div field="multipleType" width="100" headerAlign="center" allowSort="true">流转方式</div>
											<div field="toNodeNames" width="100" headerAlign="center" allowSort="true" >流向</div>
											<div field="status" width="100" headerAlign="center" allowSort="true">节点状态</div>																		
										</div>
									</div>
							    </div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							流  程  图
							<input class="mini-checkbox" onclick="showProcessImg"/>
							<!-- a class="openbutton" href="javascript:;" onclick="$('#processImageDiv').fullScreen(true);">全屏/退出</a -->
						</td>
					</tr>
					<tr id="processImageTr" style="display:none">
						<td colspan="2" style="overflow: auto;" >
							<div style="width:1000 px;overflow:auto;" id="processImageDiv">
								<img id="activitiDiagram"  style="border:0px;"/>
								<imgArea:imgAreaScript actDefId="${bpmDef.actDefId}"></imgArea:imgAreaScript>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	
	</div>

	<div style="height:100px;"></div>

	<script type="text/javascript">
		indentBody();
		var formType="${formModel.type}";
		var instId='${instId}';
		var solId="${bpmSolution.solId}";
		var nodeGrid = mini.get("nodeGrid");
		var nodeLoaded = false;

		function onNodeDrawCells(e){
			var field = e.field, value = e.value;		 
			 if(field=='status'){
	        	if(value=='已执行'){
	        		e.cellHtml='<span style="color:#4fe14d;">已执行</span>';
	        	}else if(value=='未执行'){
	        		e.cellHtml='<span>未执行</span>';
	        	}
	        }
		}
		
		function bpmMoreCheck(ck){
			if(ck.checked){
				$("#moreInfoTab").css('display','');
				var ng=mini.get("nodeGrid")
				if(nodeLoaded == false && ng){
					ng.load();
					nodeLoaded =true;
				}
				mini.layout();
			}else{
				$("#moreInfoTab").css('display','none');
			}
		}
		
		function showProcessImg(e){
			var sender=e.sender;
			if(sender.getChecked()){
				$("#processImageTr").css('display','');
				if($("#activitiDiagram").attr("src")==undefined){
					$("#activitiDiagram").attr("src","${ctxPath}/bpm/activiti/processImage.do?actDefId=${bpmDef.actDefId}");
					$("#activitiDiagram").attr("usemap","#imgHref");	
				}
			}else{
				$("#processImageTr").css('display','none');
			}
		}
		//上传图片控件
		$(function() {
			$('.header').scrollFix({zIndex : 1000});
			$(".thisPlayButton").remove();
			initUser({ solId:solId });
			//解析动态表单
			if(formType=="ONLINE-DESIGN"){
				renderMiniHtml({});	
			}else{
				//自动高度
				autoHeightOnLoad($("#formFrame"));	
			}
		 });
		//显示关联数据
		function showLinkData(){
			_OpenWindow({
				title:'实例关联的数据',
				url:__rootPath+"/bpm/core/bpmInst/showLinkData.do?tmpInstId=${param['tmpInstId']}&instId=${param['instId']}",
				height:400,
				width:800
			})
		}	

		function formPrint() {
			var conf={
				solId:solId,
				instId:instId
			}
			printForm(conf);
		}
		
		function submitValidate(){
			if($("#opinion").val().length<1){
				return false;
			}else{
				return true;
			}
		}
		
		//启动流程
		function startProcess() {
			var boResult=getBoFormDataByType(formType,true);
			
			if (!boResult.result) {
				alert("表单有内容验证不通过!");
				return;
			}
			var nodeUserIds=mini.get('nodeUserIds').getValue();
			var isUserConfig=hasUserConfig();
			if(!isUserConfig){
				alert('请配置节点人员！');
				return;
			}
			//意见
			var opText = document.getElementById("opinion").value;
			//审批附件
			var opfile = mini.get("opFiles").getValue();
			//允许在表单中自定义验证规则
			if (window._selfFormValidate) {
				var result = window._selfFormValidate(boResult.data,true);
				if (!result)  return;
			}
			var postData = {
				solId : solId,
				bpmInstId : instId,
				nodeUserIds:nodeUserIds,
				from:'<c:out value="${from}" />',
				jsonData : mini.encode(boResult.data),
				opinion:opText,
				opFiles:opfile,
				printAuserIds:mini.get('printAuserIds').getValue(),
				printAgroupIds:mini.get('printAgroupIds').getValue(),
				downAgroupIds:mini.get('downAgroupIds').getValue(),
				downAuserIds:mini.get('downAuserIds').getValue()
			};

			var destNodeUsers = [];
			$(".checkusers").each(function() {
				var id = $(this).attr('id');
				//获得目标节点选择的人员
				userIds = mini.get(id).getValue();
				//获得节点Id
				var nodeId = $(this).siblings('input[name="nodeId"]').val();
				//加入目标节点的配置
				destNodeUsers.push({
					nodeId : nodeId,
					userIds : userIds
				});
			});
			var destNodeId = $("input[name='destNodeId']:checked").val();
			//若用户选择了使用选择路径跳转的方式，
			if (destNodeId) {
				postData.destNodeId = destNodeId;
			}
			//加上目标节点的人员配置
			postData.destNodeUsers = mini.encode(destNodeUsers);
			
			OfficeControls.save(function(){
				//重新获取表单值
				boResult=getBoFormDataByType(formType,true);
				postData.jsonData = mini.encode(boResult.data);
				_SubmitJson({
					url : __rootPath + '/bpm/core/bpmInst/startProcess.do',
					method : 'POST',
					data : postData,
					success : function(result) {
						alert('成功启动流程！');
						if(window.afterStart){
							window.afterStart(result);
						}
						else{
							CloseWindow('ok');	
						}
						
					},
					fail:function(result){
						$("#errorMsg").css('display','');
						$("#errorMsg").html(result.message);
						$('#submitBtn').removeAttr('href');//去掉a标签中的href属性
						$('#submitBtn').removeAttr('onclick');
					}
				});	
			})

		}
		//选择用户
		function selectUsers(btn) {

			var id = $(btn.getEl()).siblings('table.mini-textboxlist')[0].id;
			var userBox = mini.get(id);

			_UserDlg(false, function(users) {
				var ids = [];
				var fullnames = [];
				for (var i = 0; i < users.length; i++) {
					ids.push(users[i].userId);
					fullnames.push(users[i].fullname);
				}
				userBox.setValue(ids.join(','));
				userBox.setText(fullnames.join(','));
			});
		}
		
		//保存草稿
		function saveDraft() {		
			OfficeControls.save(function(){
				var boResult=getBoFormDataByType(formType,false);
				//意见
				var opText = document.getElementById("opinion").value;
				//审批附件
				var opfileId = mini.get("opFiles").getValue();
				_SubmitJson({
					url : __rootPath + '/bpm/core/bpmInst/saveDraft.do',
					method : 'POST',
					data : {
						solId : solId,
						bpmInstId : instId,
						jsonData : mini.encode(boResult.data),
						from:'<c:out value="${from}" />',
						opinion:opText,
						opFiles:opfileId
					},
					success : function(result) {
						if(result.data.instId!==''){
							instId=result.data.instId;
						}
						alert(result.message);
						CloseWindow('ok');
					}
				});	
			})
		}
		
		function clearGroups(element){
			var elementSelect=mini.get(element);
			elementSelect.setText('');
			elementSelect.setValue('');
		}
		function clearUsers(element){
			var elementSelect=mini.get(element);
			elementSelect.setText('');
			elementSelect.setValue('');
		}
		
		function selGroups(element){
			var elementSelect=mini.get(element);
			_GroupDlg(false,function(groups){
				var uIds=[];
				var uNames=[];
				for(var i=0;i<groups.length;i++){
					uIds.push(groups[i].groupId);
					uNames.push(groups[i].name);
				}
				if(elementSelect.getValue()!=''){
					uIds.unshift(elementSelect.getValue().split(','));
				}
				if(elementSelect.getText()!=''){
					uNames.unshift(elementSelect.getText().split(','));	
				}
				elementSelect.setValue(uIds.join(','));
				elementSelect.setText(uNames.join(','));
			});
		}
		
		function selUsers(element){
			var elementSelect=mini.get(element);
			_UserDlg(false,function(users){	
				var uIds=[];
				var uNames=[];
				for(var i=0;i<users.length;i++){
					uIds.push(users[i].userId);
					uNames.push(users[i].fullname);
				}
				if(elementSelect.getValue()!=''){
					uIds.unshift(elementSelect.getValue().split(','));
				}
				if(elementSelect.getText()!=''){
					uNames.unshift(elementSelect.getText().split(','));	
				}
				elementSelect.setValue(uIds.join(','));
				elementSelect.setText(uNames.join(','));
			});
		}
		
		$(function(){
			changeReadGrantType();
		});
		/*隐藏或显示阅读权限人员输入*/
		function changeReadGrantType(){
			mini.parse();
			
			if("${alowAttachPrintStartor}"=="true"){
				$('.AToPrint').css("display","");
			}
			if("${alowAttachDownStartor}"=="true"){
				$('.AToDown').css("display","");
			}
			
			if(("${alowAttachPrintStartor}"=="false")&&("${alowAttachDownStartor}"=="false")){
				$('.AttachFunc').css("display","none");
				$('#bpmInstCtl').css("display","none");
				
			}
		}
		
		function onDrawCells(e) {
			var item = e.record, field = e.field, value = e.value;
	        //组织HTML设置给cellHtml
	        var textLength=value.length;
	        var ancientValue=value;
	        if(textLength>15){
	        	textLength=15;	
	        	value=value.substring(0,textLength)+'...';
	        }
	        if(ancientValue!='同意。'&&ancientValue!='拒绝。'){
	        	e.cellHtml = '<span title='+ancientValue+'>'+value+'</span>'+'<span style="color:red;font-size:15px;float:right;width:15px;" title="删除" onclick="deleteMyOpinion(\''+item.opId+'\');event.cancelBubble=true;"> X</span>';
	        }
	        else{
	        	e.cellHtml = '<span title='+ancientValue+'>'+value+'</span>';	
	        }
	    }
		
		
	    
	    //起草人配置后续处理人
		function toConfigDealer(){
	    	mini.parse();
	    	var url=__rootPath+'/bpm/core/bpmNodeSet/bpmNodeCouldMustConfig.do?solId='+solId+"&actDefId=${bpmDef.actDefId}";
			_OpenWindow({
				url:url,
				title:'配置节点处理人',
				width:800,
				height:450,
				ondestroy:function(action){
        			if(action=='ok'){
        				var nodeUserIds=mini.get("nodeUserIds");
        				var iframe = this.getIFrameEl().contentWindow;
    					nodeUserIds.setValue(mini.encode(iframe.getJsonArray()));
    					//console.log(nodeUserIds.getValue());
    					$("#showNodeDealerConfig").html(iframe.getShowMsg()); 
        			}
        		}
			});
		}
	</script>
</body>
</html>