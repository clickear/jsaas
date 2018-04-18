<%-- 
    Document   : 流程任务列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<%@taglib prefix="imgArea" uri="http://www.redxun.cn/imgAreaFun"%>
<!DOCTYPE html >
<html >
<head>
<title>流程任务列表处理页</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script type="text/javascript">
var _enable_openOffice="<%=com.redxun.sys.core.util.SysPropertiesUtil.getGlobalProperty("openoffice")%>";
</script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/customform.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/common/form.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/common/util.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>

<script src="${ctxPath}/scripts/jquery/plugins/jquery-grid.js" type="text/javascript"></script>
<!-- 加上扩展控件的支持 -->
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/ueditor-ext.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/scrollfix/scrollfix.min.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/textarea/jquery-textarea-autoHeight.js"></script>
<link rel="stylesheet" type="text/css" href="${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.min.css" />
<script src="${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.min.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/bpmAnimate.js" type="text/javascript"></script>

<script src="${ctxPath}/scripts/common/baiduTemplate.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/customer/mini-custom.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/form/CustomQuery.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/form/FormCalc.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/form/customFormUtil.js" type="text/javascript"></script>



</head>
<body>
	<li class="header">
			<table style="width: 100%;">
				<tr>
					<td align="right" style="text-align: right">
						<div style="width: 600px; position:absolute; top:1px; right:1px; z-index:9"><iframe width="100%" height="35px" scrolling="no" FRAMEBORDER=0></iframe></div>
						<div style="position:absolute; top:1px; right:1px;z-index:99;">
								<a class="button" onclick="CloseWindow()" style="width:100px;">关闭</a> 
								<c:if test="${isShowDiscardBtn==true}">
									<a class="button" onclick="discardInst()">作废</a>
								</c:if>
								<c:if test="${taskConfig.allowPrint=='true'}">
									<a class="button" onclick="formPrint()" style="width:100px;">打印</a>
								</c:if>
							</div>
						</td>
					</tr>
				</table>
		</li>
		
		
	<div id="errorMsg" style="margin: auto; width: 690px; padding-bottom: 20px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;<c:if test="${empty bpmInst.errors}">display:none;</c:if>color:red">
	${bpmInst.errors}
		<c:if test="${!formModel.result }">${formModel.msg}</c:if>
	</div>	
	<div style="margin:0 auto;width: 1080px; padding-bottom: 20px;">
		<table class="table-view"  id="taskHandle">
			<tr>
				<td colspan="2">显示审批记录
					<input type="checkbox" name="checkbox"  checked="checked" onclick="showCheckList(this);"/>
				</td>
			</tr>
			<tr id="checkListTr" >
				<td colspan="2" style="border-width: 0px; padding: 0px;">
					<div id="checkGrid" class="mini-datagrid" style="width:100%;" height="auto" allowResize="false" showPager="false"  pageSize="50"  allowCellWrap="true"
						bodyStyle="border-bottom: none"
						url="${ctxPath}/bpm/core/bpmNodeJump/listForInst.do?actInstId=${bpmTask.procInstId}&isDown=${isDown}&isPrint=${isPrint}" idField="jumpId" allowAlternating="true" >
						<div property="columns">
							<div field="completeTime" dateFormat="yyyy-MM:dd HH:mm" width="60" headerAlign="center" cellStyle="font-size:18px" >时间</div>
							<div field="nodeName" width="90" headerAlign="center"  cellStyle="">节点名称</div>
							<div field="handlerId" width="40" headerAlign="center" cellStyle="font-size:10px">操作者</div>
							<div field="checkStatusText" width="60" headerAlign="center"  cellStyle="font-size:10px">操作</div>
							<div field="remark" width="210" headerAlign="center" cellStyle="line-height:10px;">处理意见</div>
						</div>
					</div>
					<script type="text/javascript">
				        function showCheckList(ck){
				        	if(ck.checked){
				        		$("#checkListTr").css('display','');
				        	}else{
				        		$("#checkListTr").css('display','none');
				        	}
				        }
					</script>
				</td>
			</tr>
			<!--允许选择执行路径  -->
			<tr >
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
									<input type="radio" name="destNodeId" value="${destNodeUser.nodeId}" class="destNode"/>
									</c:if>
									<div style="border-radius:10px;background: #E8E8E8;" id="DIV${taskNodeUser.nodeId}">${destNodeUser.nodeText}</div>
								</td>
								<td>
									<c:choose>
										<c:when test="${not empty destNodeUser.taskNodeUser}">
											<c:if test="${destNodeUser.taskNodeUser.nodeType=='userTask'}">
												<input type="hidden" name="nodeId" value="${destNodeUser.nodeId}" />
												<input class="mini-checkboxlist checkusers" name="users" data-options="{seqId:'${i.count}'}" id="users${i.count}"  value="${destNodeUser.taskNodeUser.userIds}"
												 textField="text" valueField="id" data='${destNodeUser.taskNodeUser.userIdsAndText}' valuechanged="changeDestUser('users${i.count}')" <c:if test="${taskConfig.allowNextExecutor!='true'}">readOnly="true"</c:if>  required="true" />
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
															<td>
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
							<a  class="submitButton" onclick="doNext()" id="submitBtn">提交</a>	
						 </c:when>
						 <c:otherwise>
						 	<div>
								<input name="opinionSelect"  id="opinionSelect" class="mini-combobox"  emptyText="常用处理意见..." style="width:40%;" minWidth="120" url="${ctxPath}/bpm/core/bpmOpinionLib/getUserText.do" valueField="opId" textField="opText"  onvaluechanged="showOpinion()"  ondrawcell="onDrawCells"/>
								<a class="mini-button" iconCls="icon-archives" onclick="saveOpinion()" >保存处理意见</a>
							</div>
							<div>
								<textarea id="opinion" name="opinion"  placeholder="处理意见" onpropertychange="if(value.length>999){value=value.substr(0,999);alert('不能超过999字')}"></textarea> 
							    <script> 
							    	$(function(){
							    		$("#opinion").autoTextarea({maxHeight:250});
							    	});
							    </script>
						    	<a  class="submitButton" onclick="doNext()" id="submitBtn">提交</a>	
							</div>
						 </c:otherwise>
					</c:choose>
					
								
				</td>
			</tr>
			<tr>
				<th  style="width:15%;">审批意见附件</th>
				<td class="customform" >
				 	<div id="opFiles" name="opFiles" class="upload-panel"  style="width:auto;" isDown="false" isPrint="false" value='' readOnly="false" ></div>   
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
					<input type="checkbox" name="checkbox" onclick="showReadList(this);"/>
				</td>
			</tr>
			<tr id="readListTr" style="display:none;">
				<td colspan="2"  style="border-width: 0px; padding: 0px;">
					<div id="readGrid" class="mini-datagrid" style="width:100%;" height="auto" allowResize="false" showPager="true" ondrawcell="onReadDrawCells"
						url="${ctxPath}/bpm/core/bpmInstRead/readList.do?instId=${bpmInst.instId}" idField="readId" allowAlternating="true" >
						<div property="columns">
							<div type="indexcolumn" width="30"></div>
							<div field="userName" width="100" headerAlign="center" allowSort="true">用户名</div>
							<div field="depName" width="100" headerAlign="center" allowSort="true">部门名</div>
							<div field="state" width="100" headerAlign="center" allowSort="true">流程状态</div>
							<div field="createTime" dateFormat="yyyy-MM:dd HH:mm:ss" width="100" headerAlign="center" >阅读时间</div>
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
				<td colspan="2">更多<input type="checkbox" name="moreInfo" onclick="bpmMoreCheck(this)"/></td>
			</tr>
			<tr>
				<td colspan="2" id="moreInfoTab" style="display:none;">
					<div class="mini-tabs" activeIndex="0"  style="width:100%;height:auto;">
					    <div title="表格">
					       <div id="nodeGrid" class="mini-datagrid" style="width:100%;" height="auto" allowResize="false" showPager="false"  pageSize="100" ondrawcell="onNodeDrawCells" allowSortColumn="false"
								url="${ctxPath}/bpm/core/bpmInst/getActNodeInsts.do?instId=${bpmInst.instId}&excludeGateway=true" idField="nodeId" allowAlternating="true" >
								<div property="columns">
									<div type="indexcolumn" width="30"></div>
									<div field="nodeName" width="100" headerAlign="center" allowSort="true">审批环节</div>
									<div field="orginalFullNames" width="100" headerAlign="center" allowSort="true" >默认处理人</div>
									<div field="multipleType" width="100" headerAlign="center" allowSort="true">流转方式</div>
									<div field="toNodeNames" width="100" headerAlign="center" allowSort="true" >流向</div>
									<div field="status" width="100" headerAlign="center" allowSort="true">审批状态</div>																		
								</div>
							</div>
					    </div>
					    <div title="流转日志" >
					        <div id="actListGrid" class="mini-datagrid" style="width:100%;" height="auto" allowResize="false" showPager="false"  pageSize="100"  ondrawcell="actGridDrawCell" allowSortColumn="false"
								url="${ctxPath}/bpm/core/bpmNodeJump/listForInst.do?actInstId=${bpmTask.procInstId}"  idField="jumpId" allowAlternating="true" >
								<div property="columns">
									<div field="completeTime" dateFormat="yyyy-MM:dd HH:mm" width="60" headerAlign="center" cellStyle="font-size:18px" >时间</div>
									<div field="nodeName" width="90" headerAlign="center"  cellStyle="">审批环节</div>
									<div field="handlerId" width="40" headerAlign="center" cellStyle="font-size:10px">操作者</div>
									<div field="checkStatusText" width="60" headerAlign="center"  cellStyle="font-size:10px">操作</div>
								</div>
							</div>
					    </div>
					</div>
				</td>
			</tr>
		</table>
		<table class="table-view" >
			<tr>
				<td colspan="2">流程图<input type="checkbox" name="processImage" onclick="bpmImageCheck(this)"/></td>
			</tr>
			<tr>
				<td colspan="2" id="processImageTr" style="display:none;">
						<img src="${ctxPath}/bpm/activiti/processImage.do?taskId=${bpmTask.id}"  usemap="#imgHref" style="border:0px;"/>
					    <imgArea:imgAreaScript taskId="${bpmTask.id}"></imgArea:imgAreaScript>
				</td>
			</tr>		
		</table>
	</div>

	<script type="text/javascript">

	
	var formType="${formModel.type}";
	
	var instId='${bpmInst.instId}';
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
    	}else{
    		$("#readListTr").css('display','none');
    	}
    }

	var taskId='${bpmTask.id}';
	var description = '${bpmTask.description}';
	
	function bpmImageCheck(ck){
		if(ck.checked){
			$("#processImageTr").css('display','');
		}else{
			$("#processImageTr").css('display','none');
		}
	}
	
	function bpmMoreCheck(ck){
		if(ck.checked){
			$("#moreInfoTab").css('display','');
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
		mini.parse();
		loadGrid();			
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
	
		grid.on("drawcell", function (e) {
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
            if(field=='remark'){
            	e.cellHtml='<span style="line-height:22px;">'+value+'</span>';
            }
            if(field=='checkStatusText'){
            	e.cellHtml='<span style="line-height:22px;">'+value+'</span>';
            }
            if(field=='nodeName'){
            	e.cellHtml='<span style="line-height:22px;">'+value+'</span>';
            }
        });
        
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
				taskId:"${bpmTask.id}",
		}
		printForm(conf);
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

		var checkDestUserForm;
		try{
			checkDestUserForm=new mini.Form("#destUserForm");
		}catch(e){
			checkDestUserForm=null;
		}
		if(checkDestUserForm){
			checkDestUserForm.validate();
	        if (!checkDestUserForm.isValid()) {
	        	alert('流转人员没有配置！');
	            return;
	        }
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
		
		//为外部表单或内部表单提供处理表单数据的入口
		if(isExitsFunction('_handleFormData')){
			var formData=_handleFormData.call();
			$.extend(data,formData);
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
			jumpType:jumpType.getValue(),
			nextJumpType:nextJumpType
		};
		
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
		
		//若用户选择了使用选择路径跳转的方式，
		if(destNodeId){
			postData.destNodeId=destNodeId;
		}
		//加上目标节点的人员配置
		postData.destNodeUsers=mini.encode(destNodeUsers);
		//获取表单意见对象
		var opinion = document.getElementById("opinion").value;
		postData.opinion=opinion;	
		
           
        var opFiles=mini.get("opFiles");
		//审批附件
		postData.opFiles=opFiles.getValue();
		postData.communicateUserId=mini.get('communicateUserId').getValue();
		
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
  						_ShowTips({state: 'success',msg:"流程处理成功"})
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
	
/* 	function CloseWindow(action,preFun,afterFun) {
		if(preFun){
			preFun.call(this);
		}
	    if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	    else window.close();
	    if(afterFun){
			afterFun.call(this);
		}
	} */
	
</script>
	
</body>
</html>