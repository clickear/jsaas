<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<title>流程实例明细页</title>
<%@include file="/commons/customForm.jsp"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<style>
body{
	background: #f7f7f7;
}
.mini-grid-cell{
	border-bottom: none;
}
.mini-grid-columns{
	width: 100%;
}
#checkGrid >.mini-panel-border{
	border: none;
}
#readListTr .mini-panel-border{
	border: none;
}
.form-title li{
	width: 20%;
}

.form-title-running li{
	width: 25%;
}

.form-title-running h2{
	text-align: left;
	text-indent: 6em;
}

.mini-required .mini-textbox-border{
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
}

.mini-grid-row:last-of-type{
	border-bottom:1px solid #ececec;
}

.mini-grid-pager{
	border-top: none;
}

.shadowBox .customform{
	width: 100% !important;
}
		
#moreInfoTab{
	padding: 0;
}

#moreInfoTab .mini-tabs-bodys,
#moreInfoTab .mini-panel-border,
#moreInfoTab .mini-grid-row:last-of-type{
	border: none;
}

.mini-panel-border{
	width: 100%;
}

#processForm>.customform>table{
	width: 100% !important;
}


@media screen and (max-width: 1100px) {
	body{
		width: 1100px;
		overflow-x:auto; 
	}
}

</style>
</head>
<body>
	<div
		<c:choose>
			<c:when test="${bpmInst.status=='RUNNING'}">
		        class="form-title form-title-running"
		    </c:when>
		    <c:when test="${bpmInst.status!='RUNNING'}">
		        class="form-title"
		    </c:when>
	    </c:choose>
	>
		<h1>${bpmInst.subject}</h1>
		<ul>
			<li>
				<h2>单号：${bpmInst.billNo}</h2>
			</li>
			<li>
				<h2>发起人：<rxc:userLabel userId="${bpmInst.createBy}" showDep="true"/></h2>
			</li>
			<li>
				<h2>发起部门：${bpmInst.startDepFull}</h2>
			</li>
			
			<li>
				<h2>发起时间：<fmt:formatDate value="${bpmInst.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></h2>
			</li>
			<li>
				<h2>完成时间：
					<c:if test="${not empty bpmInst.endTime}">
						<fmt:formatDate value="${bpmInst.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</c:if>
				</h2>
			</li>
			<c:if test="${bpmInst.status=='RUNNING'}">
				<li>
					<h2>当前审批环节：${bpmInst.taskNodes}</h2>
				</li>
				<li>
					<h2>当前审批人：${bpmInst.taskNodeUsers}</h2>
				</li>
			</c:if>
			<li class="clearfix"></li>
		</ul>
	</div>

	<div style="margin: auto;padding-bottom: 20px;">
		<div class="shadowBox">
			<form id="processForm">
				<a name="baseInfo" id="baseInfo" class="header-info">基本信息</a>
				<c:if test="${not empty formModels }">
					<c:set var="formModel" value="${formModels[0]}"></c:set>
					<c:choose>
						<c:when test="${formModel.type=='ONLINE-DESIGN' }">
							<div class="customform" style="width: 1080px" id="form-panel">
								<c:choose>
								   <c:when test="${fn:length(formModels)==1}">  
								        ${formModels[0].content}         
								   </c:when>
								   <c:otherwise> 
										<div class="mini-tabs" activeIndex="0"  bodyStyle="padding:0">
											<c:forEach var="model" items="${formModels }">
											    <div title="${model.description}">
											        ${model.content}
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
		<div class="details shadowBox">
			<a name="bpmImage" id="bpmImage" class="header-info">流程处理</a>
			<div class="detailsBox">
				<table class="table-view">
					<tr>
						<td colspan="2">显示审批记录
<!-- 							<input type="checkbox" name="checkbox"  checked="checked"  onclick="showCheckList(this);"/> -->
							<input class="mini-checkbox" checked="checked"  onclick="showCheckList(this);" />
						</td>
					</tr>
					<tr id="checkListTr" >
						<td colspan="2" style="padding: 0">
							<div 
								id="checkGrid" 
								class="mini-datagrid" 
								style="width:100%;"  
								height="auto" 
								allowResize="false" 
								showPager="true" 
								ondrawcell="drawNodeJump" 
								pageSize="50" 
								allowCellWrap="true"
								url="${ctxPath}/bpm/core/bpmNodeJump/listForInst.do?actInstId=${bpmInst.actInstId}&isDown=${isDown}&isPrint=${isPrint}" 
								idField="jumpId" 
								allowAlternating="true" 
							>
								<div property="columns">
									<div type="indexcolumn" width="25">序号</div>
									<div field="createTime" dateFormat="yyyy-MM:dd HH:mm" width="90" headerAlign="center" cellStyle="font-size:18px" >发送时间</div>
									<div field="completeTime" dateFormat="yyyy-MM:dd HH:mm" width="90" headerAlign="center" cellStyle="font-size:18px" >处理时间</div>
									<div field="durationFormat" width="60">停留时间</div>
									<div field="nodeName" width="100" headerAlign="center" >节点名称</div>
									<div field="handlerId" width="50" headerAlign="center" >操作者</div>
									<div field="checkStatusText" width="40" headerAlign="center" >操作</div>
									<div field="remark" width="250" headerAlign="center" >处理意见</div>
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
					<tr>
						<td colspan="2">审批阅读记录
<!-- 							<input type="checkbox" name="checkbox" onclick="showReadList(this);"/> -->
							<input class="mini-checkbox" onclick="showReadList(this);" />
						</td>
					</tr>
					<tr id="readListTr" style="display:none;">
						<td colspan="2" style="padding: 0;">
							<div 
								id="readGrid" 
								class="mini-datagrid" 
								style="width:100%;" 
								height="auto"  
								showPager="true"
								url="${ctxPath}/bpm/core/bpmInstRead/readList.do?instId=${bpmInst.instId}" 
								idField="readId" 
								allowAlternating="true" 
							>
								<div property="columns" border="0">
									<div type="indexcolumn" width="30">序号</div>
									<div field="userName" width="100" headerAlign="center" allowSort="true">用户名</div>
									<div field="depName" width="100" headerAlign="center" allowSort="true">部门名</div>
									<div field="state" width="100" headerAlign="center" allowSort="true">流程状态</div>
									<div field="createTime" dateFormat="yyyy-MM:dd HH:mm:ss" width="100" headerAlign="center" >阅读时间</div>
								</div>
							</div>
							<script type="text/javascript">
						        function showReadList(ck){
						        	if(ck.checked){
						        		$("#readListTr").css('display','');
						        		mini.layout();
						        	}else{
						        		$("#readListTr").css('display','none');
						        	}
						        }
							</script>
						</td>
					</tr>
					
					<tr>
						<td colspan="2" style="border-top:none">
							<span>显示流程图</span>
<!-- 							<input type="checkbox" onclick="showProcessImg(this)"/> -->
							<input class="mini-checkbox" onclick="showProcessImg(this)" />
						</td>
					</tr>
					<tr id="processImageTr" style="display:none;" >
						<td colspan="2">
							<div style="width:1000px;overflow: auto;padding:6px;">
								<img src="${ctxPath}/bpm/activiti/processImage.do?instId=${bpmInst.instId}"  usemap="#imgHref" style="border:0px;"/>
								 <imgArea:imgAreaScript instId="${bpmInst.instId}"></imgArea:imgAreaScript> 
							</div>
						</td>
					</tr>
					<c:if test="${not empty bpmInst.actInstId}">
						<tr>
							<td colspan="2">
								<span>查看更多</span>
<!-- 								<input type="checkbox" name="moreInfo" onclick="bpmMoreCheck(this)"/> -->
								<input class="mini-checkbox" onclick="bpmMoreCheck(this)" />
							</td>
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
								       		borderStyle="border-bottom:0;" 
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
												<div field="nodeName" width="100" headerAlign="center" allowSort="true">节点名称</div>
												<div field="orginalFullNames" width="100" headerAlign="center" allowSort="true" >默认处理人</div>
												<div field="multipleType" width="100" headerAlign="center" allowSort="true">流转方式</div>
												<div field="toNodeNames" width="100" headerAlign="center" allowSort="true" >流向</div>
												<div field="status" width="100" headerAlign="center" allowSort="true">节点状态</div>
											</div>
										</div>
								    </div>
								    <div title="流转日志" >
								        <div 
								        	id="actListGrid" 
								        	class="mini-datagrid" 
								        	style="width:100%;" 
								        	height="auto" 
								        	borderStyle="border-bottom:0;" 
								        	allowResize="false" 
								        	showPager="false"  
								        	pageSize="100"  
								        	ondrawcell="actGridDrawCell" 
								        	allowSortColumn="false"
											url="${ctxPath}/bpm/core/bpmNodeJump/listForInst.do?actInstId=${bpmInst.actInstId}"  
											idField="jumpId" 
											allowAlternating="true" 
										>
											<div property="columns">
												<div field="createTime" dateFormat="yyyy-MM:dd HH:mm" width="90" headerAlign="center" cellStyle="font-size:18px" >发送时间</div>
												<div field="completeTime" dateFormat="yyyy-MM:dd HH:mm" width="90" headerAlign="center" cellStyle="font-size:18px" >处理时间</div>
												<div field="durationFormat" width="60">停留时间</div>
												<div field="nodeName" width="90" headerAlign="center"  cellStyle="">节点名称</div>
												<div field="handlerId" width="40" headerAlign="center" cellStyle="font-size:10px">操作者</div>
												<div field="checkStatusText" width="60" headerAlign="center"  cellStyle="font-size:10px">操作</div>
												<div field="remark" width="200" headerAlign="center" cellStyle="line-height:10px;">处理意见</div>
											</div>
										</div>
								    </div>
								</div>
							</td>
						</tr>
					</c:if>
				</table>
			</div>
		</div>
	</div>
	<div class="footer">
		<ul>
			<c:if test="${isFromMail=='YES'}">
				<li class="p_top">
					<a class="button" onclick="editDealer()">修改处理人</a>
				</li>
			</c:if>
			
			<li class="p_top">
				<a class="button" onclick="getMessageDetails()" style="margin-top:12px;">留言</a>
			</li>
			<c:if test="${isShowDiscardBtn==true}">
				<li class="p_top">
					<a class="button" onclick="discardInst()" style="margin-top:12px;">作废</a>
				</li>
			</c:if>
			<c:if test="${isFromMe==true}">
				<li class="p_top">
					<a class="button" onclick="recoverInst()" style="margin-top:12px;">撤回</a>
				</li>
			</c:if>
			<li class="p_top">
				<a class="button" onclick="formPrint()" style="margin-top:12px;">打印</a>
			</li>
		</ul>
		
	</div>

	<div style="height:80px;"></div>
	<script type="text/javascript">
	
		var formType="${formModel.type}";
		
		var instId='${bpmInst.instId}';		
		var fileId = "${bpmInst.checkFileId}";
		var userName = "${userName}";
		var WebOffice = document.getElementById('WebOffice');
		
		
		var actGrid ;
		var nodeGrid ;
		
		
		var nodeLoaded = false;
		
		function showProcessImg(obj){
			if(obj.checked){
				$("#processImageTr").css('display','');
			}else{
				$("#processImageTr").css('display','none');
			}
		}
		
		//撤回当前实例
		function recoverInst(){
			_SubmitJson({
				url:__rootPath+'/bpm/core/bpmInst/recoverInst.do',
				data:{
					instId:instId
				},
				success:function(){
					location.reload();
				}
			});
		}
		
		function actGridDrawCell(e){
	        var record = e.record,
	        field = e.field,
	        value = e.value;
	      	var ownerId=record.ownerId;
	        if(field=='handlerId'){
	        	if(ownerId && ownerId!=value){
	        		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>&nbsp;(代替:'+ '<a class="mini-user" iconCls="icon-user" userId="'+ownerId+'"></a>'+')';
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
		
		function bpmMoreCheck(ck){
			if(ck.checked){
				$("#moreInfoTab").css('display','');
				if(nodeLoaded == false){
					nodeGrid.load();
					actGrid.load();
					nodeLoaded =true;
				}
				mini.layout();
			}else{
				$("#moreInfoTab").css('display','none');
			}
		}
		
 

		function saveOffice() {
			WebOffice.WebSave(true);
			fileId = WebOffice.WebGetMsgByName("FILEID");
			WebOffice.WebUrl = "${ctxPath}/iWebOffice/OfficeServer.jsp?fileId="	+ fileId; //处理页地址，这里用的是相对路径
			//alert(fileId);
		}
		
		//上传图片控件
		$(function() {
			$('.header').scrollFix({
				zIndex : 1000
			});
			$(".thisPlayButton").remove();
			
			if(formType=="ONLINE-DESIGN"){
				//解析表单。
				renderMiniHtml({callback:function(){
					//加载表格。
					loadGrid();
					initUser({instId:"${bpmInst.instId}"});
				}});	
			}
			else{
				mini.parse();
				loadGrid();
				initUser({instId:"${bpmInst.instId}"});
				//自动高度
				autoHeightOnLoad($("#formFrame"));
			}
			
			initUser({instId:"${bpmInst.instId}"});
			
		});
		
		function loadGrid(){
			var readGrid=mini.get('readGrid');
			readGrid.load();
			readGrid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;

	            if(field=='state'){
	            	if(value=='DRAFTED'){
	            		e.cellHtml='草稿	';
	            	}else if(value=='RUNNING'){
	            		e.cellHtml='运行';
	            	}else if(value=='SUCCESS_END'){
	            		e.cellHtml='结束';
	            	}
	            }
	        });
			
			
			actGrid = mini.get("actListGrid");
			if(actGrid){
				actGrid.on('update',function(){
			    	_LoadUserInfo();
			    });
		    }
			
			nodeGrid = mini.get("nodeGrid");
			
			//审批历史
			var grid=mini.get('checkGrid');
			grid.load();
			
	        grid.on('update',function(){
	        	_LoadUserInfo();
	        });
			
		}

		function formPrint() {
			var conf={
					instId:"${param['instId']}"
			}
		
			printForm(conf);
			
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
		
		//展示流程图
		function onShowFlowImg() {
			_OpenWindow({
				title : '流程图',
				width : 700,
				height : 400,
				url : __rootPath
						+ '/bpm/core/bpmImage.do?actDefId=${bpmDef.actDefId}&showButton=NO'
			});
		}
		
		function ShowData(){
			_OpenWindow({
				title : '关联数据',
				width : 700,
				height : 400,
				url : __rootPath	+ '/bpm/core/bpmInst/getData.do?instId=' + instId
			});
		}
		
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
					CloseWindow("ok");
				}
			})
		}
		
		function editDealer(){
			var taskId="";
			taskId="${taskId}";
			_UserDlg(true,function(user){
        		_SubmitJson({
	        		url:__rootPath+'/bpm/core/bpmTask/batchClaimUsers.do',
	        		data:{
	        			taskIds:taskId,
	        			userId:user.userId
	        		},
	        		success:function(text){
	        			alert("处理完成");
	        		}
	        	});
        	});
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
		
		
		function showList( This , Id ){
        	/* if(ck.checked){
        		$("#readListTr").css('display','block');
        	}else{
        		$("#readListTr").css('display','none');
        	} */
        	
        	
        }
		
		
		
		
		
	</script>
</body>
</html>