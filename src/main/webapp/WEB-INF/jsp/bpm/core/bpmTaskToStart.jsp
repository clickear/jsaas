<%-- 
    Document   : 流程任务列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<title>流程任务处理页</title>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<%@include file="/commons/customForm.jsp"%>
<script type="text/javascript">
	var taskId='${param.taskId}';
</script>
<style>
	body{
		background: #f7f7f7;
	}
	
	.mini-grid-cell{
 		border-bottom: none;
	}
	
	#errorMsg{
		margin: auto; 
		width: 690px; 
		
		white-space:normal;
		/*text-overflow:ellipsis;*/
		/*overflow:hidden*/;
		color:red;
		border:solid 1px red;
		padding:5px;
	}
	
	ul,li{padding: 0;margin: 0;list-style: none}
	h1,h2,h3{font-weight: normal;margin: 0}
	
	.form-title-running li{
		width: 16.66%;
	}
	
	.form-title li{
		width: 50%;
	}
	
	.mini-panel{
		width: 100%;
	}
	
	#edui1{
		margin: 0
	}
	
	.destBox{
		display: inline-block;
		border-radius:4px;
		height:22px; 
		color:#fff;
		padding-left:6px;
		padding-right:4px;
		background: #3FA4F2;
	}
	
	td .mini-panel-border{
		border-left:1px solid #ececec;
		border-right: 1px solid #ececec; 
	}
	
	.mini-required .mini-textbox-border,
	.blue th,
	.blue caption{
		background: transparent;
	}
	
	.customform .mini-grid-columns{
		border: none;
	}
	
	.mini-grid-vscroll{
		display: none;
	}
	
	#processForm .shadowBox{
		box-shadow: none;
		margin-bottom: 20px;
		padding: 0;
	    border-radius: 0;
	}
	
	#processForm .shadowBox:last-of-type{
		margin-bottom: 0;
	}
	
	
</style>
</head>
<body id="bpmTaskToStart" class="eject">
	<div class="form-title form-title-running">
		<h1>${bpmInst.subject}</h1>
		<ul>
			<li>
				<h2>单号：${bpmInst.billNo}</h2>
			</li>
			<li>
				<h2>发起人：${bpmInst.startDepFull}<rxc:userLabel userId="${bpmInst.createBy}"/></h2>
			</li>
			<li class="clearfix"></li>
		</ul>
	</div>
	
	<div id="errorMsg" style="<c:if test="${empty bpmInst.errors && allowTask.success}">display:none;</c:if>">
		${bpmInst.errors}${allowTask.message}
		<c:if test="${!formModel.result }">${formModel.msg}</c:if>
	</div>
		
	<div>
		<div class="shadowBox90" style="padding-top: 8px;">
			<form id="processForm">
				<c:if test="${not empty formModels}">
						<c:set var="formModel" value="${formModels[0]}"></c:set>
						<c:choose>
							<c:when test="${formModel.type=='ONLINE-DESIGN'}">
								<div class="customform" style="width: 100%" id="form-panel">
									<c:choose>
									   <c:when test="${fn:length(formModels)==1}">  
									        <div class="form-model" id="formModel1"  boDefId="${formModel.boDefId}" formKey="${formModel.formKey}">
									        	${formModel.content}
									        </div>
									   </c:when>
									   <c:otherwise> 
											<div class="mini-tabs" activeIndex="0" style="width:100%;">
												<c:forEach var="model" items="${formModels}" varStatus="i" >
												    <div title="${model.description}">
												    	<div class="form-model" id="formModel${i.index}" boDefId="${model.boDefId}" formKey="${model.formKey}">
												        	${model.content}
												        </div>
												    </div>
												</c:forEach>
											</div>
									   </c:otherwise>
									</c:choose>
								</div>
							</c:when>
							<c:otherwise>
								<iframe src="${formModel.content}" style="width:100%;" id="formFrame" frameborder="0" ></iframe>
							</c:otherwise>
						</c:choose>
				</c:if>
			</form>
		</div>
		
		<div class="shadowBox90">
			<div class="details">	
				<table class="table-view"  id="taskHandle">
					<tr>
						<td colspan="2" style="height:24px; border: none; padding: 0;">显示审批记录
							<input type="checkbox" class="mini-checkbox" name="checkbox"  checked="checked" onclick="showCheckList(this);"/>
						</td>
					</tr>
					<tr id="checkListTr" >
						<td colspan="2" style="border:none; padding: 0px;">
							<div 
								id="checkGrid" 
								class="mini-datagrid" 
								style="width:100%;" 
								height="auto" 
								allowResize="false" 
								ondrawcell="drawNodeJump" 
								showPager="false"  
								pageSize="50"  
								allowCellWrap="true"
								bodyStyle="border-bottom: none"
								url="${ctxPath}/bpm/core/bpmNodeJump/listForInst.do?actInstId=${bpmTask.procInstId}&isDown=${isDown}&isPrint=${isPrint}" 
								idField="jumpId" 
								allowAlternating="true" 
							>
								<div property="columns">
									<div field="createTime" dateFormat="yyyy-MM-dd HH:mm" width="80" headerAlign="center" cellStyle="font-size:18px" >发送时间</div>
									<div field="completeTime" dateFormat="yyyy-MM-dd HH:mm" width="80" headerAlign="center" cellStyle="font-size:18px" >处理时间</div>
									<div field="durationFormat" width="60">停留时间</div>
									<div field="nodeName" width="90" headerAlign="center"  cellStyle="">审批步骤</div>
									<div field="handlerId" width="70" headerAlign="center" cellStyle="font-size:10px">操作者</div>
									<div field="checkStatusText" width="60" headerAlign="center"  cellStyle="font-size:10px">操作</div>
									<div field="remark"  width="160" headerAlign="center" cellStyle="line-height:10px;">意见</div>
								</div>
							</div>
							<script type="text/javascript">
						        function showCheckList(ck){
						        	if(ck.checked){
						        		$("#checkListTr").css('display','');
						        		mini.layout();
						        	}else{
						        		$("#checkListTr").css('display','none');
						        	}
						        }
							</script>
						</td>
					</tr>
					<!--允许选择执行路径  -->
					<tr>
						<th style="width:15%;">即将流向</th>
						<td>
							<%--配置了节点的跳转 --%>
							<c:if test="${fn:length(destNodeUserList)>0 and isReachEndEvent==false}">
							<form id="destUserForm">
								<table  cellpadding="0"  style="width:100%;" id="nodeUserTable">
									<c:forEach items="${destNodeUserList}" var="destNodeUser" varStatus="i">
									<tr>
										<td style="width:100px;text-align:center;white-space:nowrap;">
											<c:if test="${taskConfig.allowPathSelect=='true'}">
											<div style="display: inline-block;"><input type="radio" name="destNodeId" value="${destNodeUser.nodeId}" class="destNode"/></div>
											</c:if>
											<div class="destBox" id="DIV${taskNodeUser.nodeId}">${destNodeUser.nodeText}</div>
										</td>
										<td style="padding-left:5px;">
											<c:choose>
												<c:when test="${not empty destNodeUser.taskNodeUser}">
													<c:if test="${destNodeUser.taskNodeUser.nodeType=='userTask'}">
														<input type="hidden" name="nodeId" value="${destNodeUser.nodeId}" /> 
														&nbsp;
														<input class="mini-checkboxlist checkusers" name="users" data-options="{seqId:'${i.count}'}" id="users${i.count}"  value="${destNodeUser.taskNodeUser.userIds}"
														 textField="text" valueField="id" data='${destNodeUser.taskNodeUser.userIdsAndText}' valuechanged="changeDestUser('users${i.count}')" <c:if test="${taskConfig.allowNextExecutor!='true'}">reaely="true"</c:if>  required="true" />
														 <c:if test="${taskConfig.allowNextExecutor=='true'}">
														 <div style="display: inline-block; float:left"><a class="mini-button" iconCls="icon-users" plain="true"  onclick="selectUsers('${i.count}')" alt="审批者">审批者</a></div>	
														 </c:if>			
													 </c:if>
												</c:when>
												<c:otherwise>
													<table  style="width:100%">
														<c:forEach items="${destNodeUser.fllowNodeUserMap}" var="destMap" varStatus="j">
															<c:set var="taskNodeUser" value="${destMap.value}"/>
															<c:set var="seqId" value="${i.count}_${j.count}"/>
															<c:if test="${taskNodeUser.nodeType=='userTask'}">
																<tr>
																	<td>${taskNodeUser.nodeText}</td>
																	<td >
																		<input type="hidden" name="nodeId" value="${taskNodeUser.nodeId}" id=""/>
																		
																		<input class="mini-checkboxlist checkusers ${taskNodeUser.nodeId}"  name="users" nodeId="${taskNodeUser.nodeId}" 
																			data-options="{seqId:'${seqId}'}" textField="text" valueField="id" 
																			id="users${seqId}"  value="${taskNodeUser.userIds}" data="${taskNodeUser.userJsons}"
																			<c:if test="${taskConfig.allowNextExecutor!='true'}">readOnly="true"</c:if> required="true" />
																			<c:if test="${taskConfig.allowNextExecutor=='true'}">
																				<a class="mini-button" iconCls="icon-users" plain="true" onclick="selectUsers('${seqId}')" alt="审批者">审批者</a>
																			</c:if>
																		<c:if test="${(not empty taskNodeUser.groupIds)&& taskNodeUser.groupIds!=''}">
																			
																			<input class="mini-checkboxlist checkgroups" name="groups" id="groups${seqId}"  text="${taskNodeUser.groupNames}" value="${taskNodeUser.groupIds}" 
																				readOnly="true" textField="text" valueField="id"
																				required="true" emptyText="请选择用户组"/>
																				<c:if test="${taskConfig.allowNextExecutor=='true'}">
																				<a class="mini-button" iconCls="icon-users" plain="true" onclick="selectGroups('${seqId}')" alt="选择审批组">选择审批组</a>
																				</c:if>
																		</c:if>
																	</td>
																</tr>
															</c:if>
														</c:forEach>
													</table>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									</c:forEach>
								</table>
							</form>
								
							</c:if>
							
						</td><!-- end of 任务处理 -->
					</tr>
					<tr>
						<th style="width:15%;">抄送</th>
						<td>
							<input id="ccUserIds" name="ccUserIds" class="mini-user" style="width:100%;"   single="false" value='' />
						</td>
					</tr>
					<tr>
						<th style="width:15%;">操作</th>
						<td>
							<div class="mini-radiobuttonlist"  name="jumpType" id="jumpType" required="true"
							    textField="text" valueField="id"  onvaluechanged="changeJumpType" 
							    <c:choose>
							    	<c:when test="${not empty bpmTask.rcTaskId}">
							    		value="REPLY_COMMUNICATE" data="[{id:'REPLY_COMMUNICATE',text:'回复沟通'}]">
							    	</c:when>
							    	
							    	<c:when test="${bpmTask.genCmTask=='YES'}">
							    		value="COMMUNICATE" data="[{id:'COMMUNICATE',text:'沟通'},{id:'CANCEL_COMMUNICATE',text:'撤消沟通'}]">
							    	</c:when>
							    	
							    	<c:when test="${taskConfig.allowConfigButtons=='true'}">
							    		value="AGREE" data="${checkButtonsJson}">
							    	</c:when>
							    	<c:otherwise>
							    		<c:choose>
								    		<c:when test="${isShowDiscardBtn==true}">
								    		value="AGREE" data="[{id:'AGREE',text:'通过'},{id:'COMMUNICATE',text:'沟通'}]" >
								    		</c:when>
								    		<c:otherwise>
								    		value="AGREE" data="[{id:'AGREE',text:'通过'},{id:'BACK',text:'驳回(上一节点)'},{id:'BACK_TO_STARTOR',text:'驳回(发起人)'},{id:'COMMUNICATE',text:'沟通'}]" >
								    		</c:otherwise>
							    		</c:choose>
							    	</c:otherwise>
							    </c:choose>
							</div>
						</td>
					</tr>
					<tr id="backTaskTr" style="display:none">
			    		<th width="120">回退任务完成后</th>
			    		<td>
			    			<input class="mini-radiobuttonlist" id="nextJumpType" name="nextJumpType" value="normal" data="[{id:'orgPathReturn',text:'原路返回'},{id:'normal',text:'正常执行'}]" required="true"/>
			    		</td>
			    	</tr>
					<tr id="commTr" style="display:none">
						<th style="width:15%;">
							沟通人
						</th>
						<td>
							<input id="communicateUserId" name="communicateUserId" class="mini-user"  single="false" allowInput="false" style="width:80%;"/>
						</td>
					</tr>
					<c:if test="${not empty bpmNodeSet.nodeCheckTip }">
					<tr>
						<th width="120">流程操作说明</th>
						<td>${bpmNodeSet.nodeCheckTip}</td>
					</tr>
					</c:if>
					<tr id="handleOpTr">
						<th style="width:15%;">处理意见</th>
						<td>
							<c:choose>
								<c:when test="${taskConfig.showOpinion=='yes'}">
									<a  class="submitButton" <c:if test="${allowTask.success}"> onclick="doNext()"</c:if> id="submitBtn">提交</a>	
								 </c:when>
								 <c:otherwise>
								 	<div>
										<input name="opinionSelect"  id="opinionSelect" class="mini-combobox"  emptyText="常用处理意见..." style="width:40%;" minWidth="120" url="${ctxPath}/bpm/core/bpmOpinionLib/getUserText.do" valueField="opId" textField="opText"  onvaluechanged="showOpinion()"  ondrawcell="onDrawCells"/>
										<a class="mini-button" iconCls="icon-archives" onclick="saveOpinion()" >保存</a>
									</div>
									<div>
										<textarea id="opinion" name="opinion"  placeholder="处理意见" onpropertychange="if(value.length>999){value=value.substr(0,999);alert('不能超过999字')}">${opinion}</textarea> 
									    <script> 
									    	$(function(){
									    		$("#opinion").autoTextarea({maxHeight:250});
									    	});
									    </script>
								    	<a  class="submitButton" <c:if test="${allowTask.success}"> onclick="doNext()"</c:if> id="submitBtn">提交</a>	
									</div>
								 </c:otherwise>
							</c:choose>			
						</td>
					</tr>
					<tr>
						<th  style="width:15%;">审批意见附件</th>
						<td class="customform" >
						 	<div id="opFiles" name="opFiles" class="upload-panel"  style="width:auto;" isDown="false" isPrint="false" value='${fileIds}' readOnly="false" ></div>   
						</td>
					</tr> 
					<tr>
						<th style="width:15%;">当前处理人</th>
						<td>${nowUser}</td>
					</tr>
					<tr>
						<th style="width:15%">已经处理人</th>
						<td>${beforeUser}</td>
					</tr>
					<tr>
						<td colspan="2">显示审批阅读记录
							<input type="checkbox" class="mini-checkbox" name="checkbox" onclick="showReadList(this);"/>
						</td>
					</tr>
					<tr id="readListTr" style="display:none;">
						<td colspan="2"  style="border-width: 0px; padding: 0px;">
							<div id="readGrid" class="mini-datagrid" style="width:100%;" height="auto" allowResize="false" showPager="true" ondrawcell="onReadDrawCells"
								url="${ctxPath}/bpm/core/bpmInstRead/readList.do?instId=${bpmInst.instId}" idField="readId" allowAlternating="true" >
								<div property="columns">
									<div type="indexcolumn" width="30">序号</div>
									<div field="userName" width="100" headerAlign="center" allowSort="true">用户名</div>
									<div field="depName" width="100" headerAlign="center" allowSort="true">部门名</div>
									<div field="state" width="100" headerAlign="center" allowSort="true">流程状态</div>
									<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="100" headerAlign="center" >阅读时间</div>
								</div>
							</div>
						</td>
					</tr>
					<c:if test="${not empty formModel.description}">
					<tr id="tableRec"><th style="width:15%;">表单说明</th>
						<td colspan="3" >
							${formModel.description}
						</td>
					</tr>
					</c:if>
					<tr>
						<td colspan="2" style="height:24px;height:24px;">更多<input class="mini-checkbox" type="checkbox" name="moreInfo" onclick="bpmMoreCheck(this)"/></td>
					</tr>
					<tr>
						<td colspan="2" id="moreInfoTab" style="display:none;">
							<div class="mini-tabs" activeIndex="0"  style="width:100%;height:auto;">
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
										url="${ctxPath}/bpm/core/bpmInst/getActNodeInsts.do?instId=${bpmInst.instId}&excludeGateway=true" 
										idField="nodeId" 
										allowAlternating="true" 
									>
										<div property="columns">
											<div type="indexcolumn" width="30">序号</div>
											<div field="nodeName" width="100" headerAlign="center" allowSort="true">审批环节</div>
											<div field="orginalFullNames" width="100" headerAlign="center" allowSort="true" >默认处理人</div>
											<div field="multipleType" width="100" headerAlign="center" allowSort="true">流转方式</div>
											<div field="toNodeNames" width="100" headerAlign="center" allowSort="true" >流向</div>
											<div field="status" width="100" headerAlign="center" allowSort="true">审批状态</div>																		
										</div>
									</div>
							    </div>
							    <div title="流转日志" >
							        <div 
							        	id="actListGrid" 
							        	class="mini-datagrid" 
							        	style="width:100%;" 
							        	height="auto" 
							        	allowResize="false" 
							        	showPager="false"  
							        	pageSize="100"  
							        	ondrawcell="actGridDrawCell" 
							        	allowSortColumn="false"
										url="${ctxPath}/bpm/core/bpmNodeJump/listForInst.do?actInstId=${bpmTask.procInstId}"  
										idField="jumpId" 
										allowAlternating="true" 
									>
										<div property="columns">
											<div field="createTime" dateFormat="yyyy-MM-dd HH:mm" width="60" headerAlign="center" cellStyle="font-size:18px" >发送时间</div>
											<div field="completeTime" dateFormat="yyyy-MM-dd HH:mm" width="60" headerAlign="center" cellStyle="font-size:18px" >处理时间</div>
											<div field="durationFormat" width="60">停留时间</div>
											<div field="nodeName" width="90" headerAlign="center"  cellStyle="">审批环节</div>
											<div field="handlerId" width="40" headerAlign="center" cellStyle="font-size:10px">操作者</div>
											<div field="checkStatusText" width="60" headerAlign="center"  cellStyle="font-size:10px">操作</div>
										</div>
									</div>
							    </div>
							</div>
						</td>
					</tr>
					<tr>
					<td colspan="2">流程图<input type="checkbox" class="mini-checkbox" name="processImage" onclick="bpmImageCheck(this)"/></td>
					</tr>
					<tr>
						<td colspan="2" id="processImageTr" style="display:none;">
							<div style="width:1000px;overflow: auto;padding:6px;">
								<img id="activitiDiagram" style="border:0px;"/>
							    <imgArea:imgAreaScript taskId="${bpmTask.id}"></imgArea:imgAreaScript>
							</div>
						</td>
					</tr>
				
				</table>

			</div>
		</div>
	
	</div>

	<div class="footer">
		
		<ul>
			<li class="p_top">
				<a class="button" onclick="getMessageDetails()" style="margin-top:12px;">留言</a>
			</li>
			<c:if test="${isShowDiscardBtn==true}">
				<li class="p_top">
					<a class="button" onclick="discardInst()" style="margin-top:12px;">作废</a>
				</li>
			</c:if>
			<c:if test="${taskConfig.allowPrint=='true'}">
				<li class="p_top">
					<a class="button" onclick="formPrint()" style="margin-top:12px;">打印</a>
				</li>
			</c:if>
			<li class="p_top">
				<a class="button" onclick="doSaveData()" style="margin-top:12px;">保存</a>
			</li>
			
			<c:if test="${not empty formModel.description}">
				<li class="p_top">
					<a class="button" href="#tableRec">表单<br/>说明</a>
				</li>
			</c:if>	
		</ul>
		
	</div>
	
	
	
	
	
	
	<div style="height:80px;"></div>

	<script type="text/javascript">
	var formType="${formModel.type}";
	var instId='${bpmInst.instId}';
	var description = '${bpmTask.description}';
	
	var readGrid,nodeGrid,actGrid;
	var nodeLoaded = false;
	
	function actGridDrawCell(e){
        var record = e.record,
        field = e.field,
        value = e.value;
      	var ownerId=record.ownerId;
        if(field=='handlerId'){
        	if(ownerId && ownerId!=value){
        		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>&nbsp;(代:'+ '<a class="mini-user" iconCls="icon-user" userId="'+ownerId+'"></a>'+')';
        	}else if(value){
        		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
        	}else{
        		e.cellHtml='<span style="color:red">无</span>';
        	}
        } 
        if(field=='checkStatusText'){
        	e.cellHtml='<span style="line-height:22px;">'+value+'</span>';
        }
        if(field=='nodeName'){
        	e.cellHtml='<span style="line-height:22px;">'+value+'</span>';
        }
	}
	

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
    
	function onReadDrawCells(e){
		 var field = e.field, value = e.value;		 
		 if(field=='state'){
         	if(value=='DRAFTED'){
         		e.cellHtml='草稿';
         	}else if(value=='RUNNING'){
         		e.cellHtml='运行中';
         	}else if(value=='SUCCESS_END'){
         		e.cellHtml='运行完成';
         	}
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
	
	
    function showReadList(ck){
    	if(ck.checked){
    		$("#readListTr").css('display','');
    		mini.layout();
    	}else{
    		$("#readListTr").css('display','none');
    	}
    }
    
    
	
		var fileId = "${bpmInst.checkFileId}";
		var userName = "${userName}";
		
		
		
		
		function bpmImageCheck(ck){
			if(ck.checked){
				$("#processImageTr").css('display','');
				if($("#activitiDiagram").attr("src")==undefined){
					$("#activitiDiagram").attr("src","${ctxPath}/bpm/activiti/processImage.do?taskId=${bpmTask.id}");
					$("#activitiDiagram").attr("usemap","#imgHref");	
				}
			}else{
				$("#processImageTr").css('display','none');
			}
		}
		
		function bpmMoreCheck(ck){
			if(ck.checked){
				$("#moreInfoTab").css('display','');
				mini.layout();
				if(nodeLoaded == false){
					nodeGrid.load();
					actGrid.load();
					nodeLoaded =true;
				}
			}else{
				$("#moreInfoTab").css('display','none');
			}
		}
		
		
		function changeJumpType(e){
			var jumpType=e.sender.getValue();
			//跳转类型
			if(jumpType=='COMMUNICATE'){
				$("#commTr").css('display','');
			}else{
				$("#commTr").css('display','none');
			}
			if(jumpType=="BACK_TO_STARTOR"){
				$("#backTaskTr").css("display","");
			}else{
				$("#backTaskTr").css("display","none");
			}
		}
		
		
		$(function(){
			$(".thisPlayButton").remove();
			
			if(formType=="ONLINE-DESIGN"){
				//解析表单。
				renderMiniHtml({callback:function(){
					//加载表格。
					loadGrid();
					initUser({taskId:"${bpmTask.id}"});	
				}});	
			}
			else{
				mini.parse();
				loadGrid();
				initUser({taskId:"${bpmTask.id}"});	
				//自动高度
				autoHeightOnLoad($("#formFrame"));
				mini.layout();
			}
			
			$('.header').scrollFix({zIndex : 1000});
			
		});
		
		
		function loadGrid(){
			readGrid=mini.get('readGrid');
			nodeGrid = mini.get("nodeGrid");
			actGrid = mini.get("actListGrid");
			readGrid.load();
			
			actGrid.on('update',function(){
		    	_LoadUserInfo();
		    });
			
			
			//审批
			var grid=mini.get('checkGrid');
			grid.load();
		
			
	        
	        grid.on('update',function(){
	        	_LoadUserInfo();
	        });
		}
		
		function doTransfer(){
			_OpenWindow({
        		title:'任务转办',
        		url:__rootPath+'/bpm/core/bpmTask/transfer.do?taskIds=${bpmTask.id}',
        		height:350,
        		width:800,
        		ondestroy:function(action){
        			CloseWindow('ok');
        		}
        	});
		}
		
		function formPrint() {
			var conf={
					taskId:taskId,
			}
			printForm(conf);
		}
		
		//自由跳转
		function freeJumpCheck(e){
			var checked=this.getChecked();
			if(checked){
				$("#nodeJumpTable").css('display','');
				$("#nodeUserTable").css('display','none');
			}else{
				$("#nodeJumpTable").css('display','none');
				$("#nodeUserTable").css('display','');
			}
		}
		
		function onShowHistory(){
			_OpenWindow({
				title:'流程历史-${bpmTask.description}',
				width:700,
				height:400,
				url:__rootPath+'/bpm/core/bpmNodeJump/insts.do?actInstId=${bpmTask.procInstId}'
			});
		}
		
		function onShowFlowImg(){
			_OpenWindow({
				title:'流程图',
				width:700,
				height:400,
				url:__rootPath+'/bpm/core/bpmImage.do?taskId='+taskId
			});
		}
		
		//选择用户
		function selectUsers(seqId){
			//为自由选择用户
			var userBox=null;
			if(seqId==''){
				userBox=mini.get('destNodeUserIds');
			}else{
				userBox=mini.get('users'+seqId);	
			}
			
			var tempVal=userBox.getValue();
			var checkBoxData=userBox.getData();
			_UserDlg(false,function(users){
				var ids=[];
				var fullnames=[];
				for(var i=0;i<users.length;i++){
					var addOrNot=true;
					//如果之前的数据里有,则不添加
					for (var j=0;j<checkBoxData.length;j++){
						if(checkBoxData[j].id==users[i].userId){
							addOrNot=false;
						}
					}
					if(addOrNot){
						checkBoxData.push({id:users[i].userId,text:users[i].fullname});	
						tempVal+=","+users[i].userId;
					}
					
				}
				userBox.loadData(checkBoxData);
				userBox.setValue(tempVal);
			});
		}
		//当文字过高时调整高度
		function resizeTextArea(){
			var opinion=mini.get("opinion");
			opinion.set({style:"overflow-y:visible"});
		}
		
		function closeWin(){
			var win=mini.get('signWindow');
			win.hide();
		}
		function submitValidate(){
			var opinionObj=$("#opinion");
			if(opinionObj.length==0) return true;
			var val=opinionObj.val();
			if(val){
				return true;
			}
			else{
				return false;
			}
		}
		
		function doNext(){
			var token='${bpmTask.token}';
			var boResult=getBoFormDataByType(formType,true);
			
			if (!boResult.result) {
				alert("表单有内容尚未填写");
				return;
			}
			var checkDestUserForm;
			try{
				checkDestUserForm=new mini.Form("#destUserForm");
			}catch(e){
				checkDestUserForm=null;
			}

			
			var jumpType=mini.get('jumpType');
			//检查当前配置的操作按钮中是否存在预先执行的脚本
			var jumpBtnConf=jumpType.getSelected();
			
			if(jumpBtnConf && jumpBtnConf.preExeJs){
				try{
					var result=eval('(function(){'+jumpBtnConf.preExeJs+'})();');
					if(!result){
						alert('表单数据校验不通过！');
						return;
					}
				}catch(e){
					alert('执行脚本'+jumpBtnConf.preExeJs+'有错误！');
					return;
				}
			}
			
			var destNodeUsers=[];
			
			var opinionObj=null;
			
			var bos=boResult.data.bos;
			if(bos){
				for(var i=0;i<bos.length;i++){
					opinionObj=bos[i].data.FORM_OPINION_;
					if(opinionObj){
						delete bos[i].data.FORM_OPINION_;
						break;
					}
				}	
			}
			
			
			//表单验证处理。
			if (window._selfFormValidate) {
				var result = window._selfFormValidate(boResult.data,false);
				if (!result)  return;
			}
			
			if(!submitValidate()){
				alert("请填写意见内容");
				return;
			}
		
			var nextJumpType=mini.get('nextJumpType').getValue();
			//目标节点
			var destNodeId=null;
			
			//查看是否启用了路径跳转，若有，则需要让其选择路径进行跳转
			if(jumpType.getValue()=='AGREE' && $('.destNode').length>0){
				destNodeId=$("input[name='destNodeId']:checked").val();
				if(!destNodeId){
					alert('请选择即将流转的节点！');
					return;
				}
			}else if(jumpType.getValue()=='COMMUNICATE'){
				var cmId=mini.get('communicateUserId').getValue();
				if(cmId==''){
					alert('请选择沟通的人员！');
					return;
				}
			}

			var postData={
				taskId:taskId,
				token:token,
				jumpType:jumpType.getValue(),
				jsonData:mini.encode(boResult.data),
				nextJumpType:nextJumpType
			};
			
			var allowFreeJump=mini.get('allowFreeJump');
			//允许自由跳转
			if(allowFreeJump&& allowFreeJump.getValue()=='true'){
				//是否选择了自由跳转的节点
				destNodeId=mini.get('destNodeId').getValue();
				var destNodeUserIds=mini.get('destNodeUserIds').getValue();
				if(destNodeId=='' || destNodeUserIds=='') return;
				destNodeUsers.push({
					nodeId:destNodeId,
					userIds:destNodeUserIds
				});
			}else{
				$(".checkusers").each(function(){
					var id=$(this).attr('id');
					//获得目标节点选择的人员
					var usrSel=mini.get(id);
					userIds=usrSel.getValue();
					var seqId=usrSel.seqId;
					//获得节点Id
					var nodeId=$(this).prev('input[name="nodeId"]').last().val();
					//加入目标节点的配置
					destNodeUsers.push({
						nodeId:nodeId,
						userIds:userIds
					});
				});
				destNodeId=$("input[name='destNodeId']:checked").val();
			}
			//若用户选择了使用选择路径跳转的方式，
			if(destNodeId){
				postData.destNodeId=destNodeId;
			}
			//加上目标节点的人员配置
			postData.destNodeUsers=mini.encode(destNodeUsers);
			//获取表单意见对象
			if(opinionObj){
				postData.opinion=opinionObj.val;
				postData.opinionName=opinionObj.name;
			}
			else{
				var opinion = document.getElementById("opinion").value;
				postData.opinion=opinion;	
			}
            
            var opFiles=mini.get("opFiles");
			//审批附件
			postData.opFiles=opFiles.getValue();
			postData.communicateUserId=mini.get('communicateUserId').getValue();
			postData.ccUserIds=mini.get("ccUserIds").getValue();
			
			OfficeControls.save(function(){
				var boResult=getBoFormDataByType(formType,true);
				postData.jsonData=mini.encode(boResult.data);
				//填写审批意见
	   			_SubmitJson({
	   				url:__rootPath+'/bpm/core/bpmTask/doNext.do',
	   				data:postData,
	   				method:'POST',
	   				success:function(text){
	   					 var objRef = window.opener;
	   					
	   					if(objRef){
	   						if(objRef.grid){
	   							objRef.grid.reload();	
	   						}
	   						window.setTimeout(function(){
	   							window.close();	
	   						},1000)
	   							
	   					}
	   					else{
	   						_ShowTips({state: 'success',msg:"流程处理成功!"})
	   						window.setTimeout(function(){
	   							CloseWindow('ok');	
	   						})
	   					}
	   					
	   				},
	   				fail:function(result){
						$("#errorMsg").css('display','');
						$("#errorMsg").html(result.message);
						$('#submitBtn').removeAttr('href');//去掉a标签中的href属性
						$('#submitBtn').removeAttr('onclick');
					}
	   			});	
			});
		}
		
		
		//暂存表单数据，只对在线表单可用
		function doSaveData(){
			
			OfficeControls.save(function(){
				
				var boResult=getBoFormDataByType(formType,false);
				
				var postData={
					taskId:taskId,
					jsonData:mini.encode(boResult.data)	
				};
				var opFiles=mini.get("opFiles");
				postData.opFiles=opFiles.getValue();
				var opinion = document.getElementById("opinion").value;
				postData.opinion=opinion;
				
				
				_SubmitJson({
	   				url:__rootPath+'/bpm/core/bpmTask/doSaveData.do',
	   				data:postData,
	   				method:'POST'
	   			});	
			});
		}
		
		//抄送检查
		function instCcCheck(e){
			var ck=e.sender;
			 if(ck.getChecked()){
				 $("#ccTable").css('display','');
			 }else{
				 $("#ccTable").css('display','none');
			 }
		}
		
		//作废
		function discardInst(){			
			if(!confirm('确定要作废该流程吗?')){
				return;
			}
			_SubmitJson({
				url:__rootPath+'/bpm/core/bpmInst/discardInst.do',
				data:{
					instId:instId
				},
				method:'POST',
				success:function(){
					CloseWindow();
					window.opener.grid.reload();
					window.opener.mini.showTips({
   			            content: "<b>成功</b> <br/>流程实例已作废",
   			            state: 'warning',
   			            x: 'center',
   			            y: 'center',
   			            timeout: 3000
   			        });
				}
			})
		}
		
		
		//留言
		function getMessageDetails(){
    		_OpenWindow({
       			title:'流程沟通留言板',
       			height:600,
       			width:600,
       			openType:'NewWin',
       			url:__rootPath+'/bpm/core/bpmMessageBoard/addMessage.do?instId=${bpmInst.instId}',
       			ondestroy: function(action){
       			}
       		});
    	}
		
		//抄送
		function carbonCopy(){
			_OpenWindow({
        		title:'任务抄送',  
        		url:__rootPath+'/bpm/core/bpmInstCp/toCarbonCopy.do?bpmInstId=${bpmInst.instId}&nodeId=${bpmTask.taskDefKey}&nodeName=${bpmTask.name}',
        		height:350,
        		width:800,
        		ondestroy:function(action){
        			if(action=='ok'){
        				window.close();
        			}
        		}
        	});
		}
		
		function exportPDF(){
			var taskId = "${bpmTask.id}";
			//var solId = "${bpmSolution.solId}";
	        window.location.href=__rootPath+'/bpm/form/bpmFormView/exportPdfByTaskId.do?taskId='+taskId;

		}
		
	</script>
	
</body>
</html>