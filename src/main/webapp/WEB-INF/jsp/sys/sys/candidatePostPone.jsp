<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
	<%@include file="/commons/list.jsp"%> 
		<title>选人页面</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />

		<link href="${ctxPath}/styles/customform.css" rel="stylesheet" type="text/css" />
		<!-- ztree树引用css -->
		<link rel="stylesheet" href="${ctxPath}/static/huiyu/extend/zTree/3.5/zTreeStyle.css" type="text/css">
		<link rel="stylesheet" href="${ctxPath}/static/huiyu/extend/zTree/3.5/zTreeIcon.css" type="text/css">
		<link rel="stylesheet" href="${ctxPath}/static/ace/css/jquery-ui.css" type="text/css">
		<!-- ztree树引用js -->
		<script type="text/javascript" src="${ctxPath}/static/huiyu/extend/zTree/3.5/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="${ctxPath}/static/huiyu/extend/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>
		<style type="text/css">
			/*右键菜单弹出选中项悬浮背景颜色*/
			.dropdown-menu li a:hover{background-image:url();background-color:#3875d7;}
			.ztree li span.button.icon_root_ico_open{background:url(${ctxPath}/static/huiyu/extend/zTree/3.5/img/house.png) no-repeat scroll 0 0px transparent;}
			.ztree li span.button.icon_root_ico_close{background:url(${ctxPath}/static/huiyu/extend/zTree/3.5/img/house.png) no-repeat scroll 0 0px transparent;}
			.col-xs-1, .col-sm-1, .col-md-1, .col-lg-1, .col-xs-2, .col-sm-2, .col-md-2, .col-lg-2, .col-xs-3, .col-sm-3, .col-md-3, .col-lg-3, .col-xs-4, .col-sm-4, .col-md-4, .col-lg-4, .col-xs-5, .col-sm-5, .col-md-5, .col-lg-5, .col-xs-6, .col-sm-6, .col-md-6, .col-lg-6, .col-xs-7, .col-sm-7, .col-md-7, .col-lg-7, .col-xs-8, .col-sm-8, .col-md-8, .col-lg-8, .col-xs-9, .col-sm-9, .col-md-9, .col-lg-9, .col-xs-10, .col-sm-10, .col-md-10, .col-lg-10, .col-xs-11, .col-sm-11, .col-md-11, .col-lg-11, .col-xs-12, .col-sm-12, .col-md-12, .col-lg-12{position: relative;min-height: 1px;padding-left: 0px;padding-right: 0px;}
			.ui-dialog .ui-dialog-titlebar{padding:0px;}
			.ui-dialog{padding:0px;}
		</style>

	<script type="text/javascript">
		var ztree;
		$(function() {
			//加载机构树
			loadOrgUserTreeData();
		});
			
		function loadOrgUserTreeData(){
			//加载数据树
			$.ajax({
				type:"post",
				url:"${ctxPath}/bpm/business/sysUser/listAllOrgUserTree.do",
				success:function(data){
					//加载数据源
					var zNodes =  eval("("+data+")");
					var setting = {
							check: {  
				                enable: true,  
				                nocheckInherit: false  
				            },  
				            data: {  
				                simpleData: {  
				                    enable: true  
				                }  
				            },
				            callback : { //回调函数  
				            	onCheck: zTreeOnCheck
					        }
						};
						
					  ztree = $.fn.zTree.init($("#orgUserTree"), setting, zNodes);  
                    //设置父节点不被选中
	            	ztree.setting.check.chkboxType = { "Y":"s", "N":"s"};
				}
			});
		}
		
		
		
	/* 	//树复选框事件
		function zTreeOnCheck(event, treeId, treeNode) {
			//获取选中机构用户节点
			var treeObj = $.fn.zTree.getZTreeObj("orgUserTree");
			var orgUserNodes = treeObj.getCheckedNodes(true);
			
			var USER_ID = "";//用户ID集合
			var USER_NAME = "";//用户名称集合
			//循环重新加载选中用户
				
				for(var i=0;i<orgUserNodes.length;i++){
					var node = orgUserNodes[i];
					if(node.type != "user"){
						continue;
					}
					if(!USER_ID){
						USER_ID = node.id;
					}else{
						USER_ID += "," + node.id;
						USER_NAME += "," + node.name;
					}
				}
			console.log("USER_NAME/...." +USER_NAME);
			$("#approvalId").val(USER_NAME);	
			/* $("#USER_NAME").val(USER_NAME);//赋值会议参与人员名称
			$("#USER_ID").val(USER_ID);//赋值会议参与人员ID  
		}
		 */
			//树复选框事件
			function zTreeOnCheck(event, treeId, treeNode) {
				//获取选中机构用户节点
				var treeObj = $.fn.zTree.getZTreeObj("orgUserTree");
				var orgUserNodes = treeObj.getCheckedNodes(true);
				
				var USER_ID = "";//用户ID集合
				var USER_NAME = "";//用户名称集合
				//循环重新加载选中用户
				for(var i=0;i<orgUserNodes.length;i++){
					var node = orgUserNodes[i];
					if(node.type != "user"){
						continue;
					}
					if(!USER_ID){
						USER_ID = node.id;
						USER_NAME = node.name;
					}else{
						USER_ID += "," + node.id;
						USER_NAME += "," + node.name;
					}
				}
				$("#approvalId").html(USER_NAME);	
				
			}
			
		 
		//转到缓办候选人审批
		function saveCandidate() {
			//获取选中机构用户节点
			var treeObj = $.fn.zTree.getZTreeObj("orgUserTree");
			var orgUserNodes = treeObj.getCheckedNodes(true);
			if(orgUserNodes.length>1) {
				mini.alert("候选人只能选一个!!");
				treeObj.checkAllNodes(false);//清除勾选
				$("#approvalId").html("");	
				return false;
			} 
			var USER_ID = "";//用户ID集合
			var USER_NAME = "";//用户名称集合
			for(var i=0;i<orgUserNodes.length;i++){
				var node = orgUserNodes[i];
				if(node.type != "user"){
					continue;
				}
				if(!USER_ID){
					USER_ID = node.id;
					USER_NAME = node.name;
				}else{
					USER_ID += "," + node.id;
					USER_NAME += "," + node.name;
				}
			
			}
			  mini.parse();
              var formJson = new mini.Form("#form1");

            formJson.validate();
            if (formJson.isValid() == false) return;
			  
			var	formJson =  $('#form1').serialize();
				 
		 	$.ajax({
				type: "POST",
				data : formJson,  
				//data : form,  
				url:__rootPath+'/extend/bpmtask/bpmTask/updateCategoryById.do?category=0&myActRuTaskId=${myActRuTaskId}&approvalId='+USER_NAME+'&agentUserId='+USER_ID,
				dataType:'json',
				success: function(data){
					var success = data.success;
					if(success){
						mini.alert(data.message); 
					}
					var url = __rootPath+'/bpm/business/bpmTaskTodo/myList.do';
					//在父页面打开新页面
					parent.location.href=url;
				}
			});    
			
			//=============================================
			/* 	_SubmitJson({
					url:__rootPath+'/extend/bpmtask/bpmTask/updateCategoryById.do?category=0&myActRuTaskId=${myActRuTaskId}&agentUserId='+USER_ID,
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
	   						_ShowTips({state: 'success',msg:"流程处理成功"});
	   						window.setTimeout(function(){
	   							CloseWindow('ok');	
	   						})
	   					}  
	   					
	   				},
	   				fail:function(result){
	 			}
	   			}); 
			 */
/* 			
			//填写审批意见
   	 	_SubmitJson({
   				url:__rootPath+'/extend/bpmtask/bpmTask/updateCategoryById.do',
   				data:postData,
   				method:'POST',
   				success:function(text){
   				//	console.log("-success:function---------------" );
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
   						_ShowTips({state: 'success',msg:"流程处理成功"});
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
   			});   */
			
			/* _SubmitJson({
				url:__rootPath+'/extend/bpmtask/bpmTask/updateCategoryById.do?category=0&taskId=${taskId}&agentUserId='+userId,
	   			success:function(text){
	   				alert();
	   			}
	   		}); */
			
			//window.location.href=__rootPath+'/extend/bpmtask/bpmTask/updateCategoryById.do?category=0&taskId=${taskId}&agentUserId='+userId;
			/* $.ajax({
				type: "POST",
				url: __rootPath+'/extend/bpmtask/bpmTask/updateCategoryById.do?category=0',
				//AGENT_USER_ID_ 代理人(缓办人id)
		    	data:{
		    		taskId:${taskId},
		    		agentUserId:USER_ID
		    		},
				dataType:'json',
				cache: false,
				success: function(data){
					console.log("-成功---------taskId--" +taskId +"USER_I.D..." + USER_ID);
				}
			}); */
			
		}
		
		function onCancel() {
			
			CloseWindow('ok');
			
		}
	</script> 
	
	</head>
	
	<body>

	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<!-- 当前所属机构 -->
							<input type="hidden" id="curOrgId"/>
							<table id="orgTable" border="0" class="table table-striped table-bordered table-hover" style="width:100%;background-color:white;">
								<tr>
									<td style="width:40%;padding-left:0px;padding-right:0px;background-color:white" valign="top">
										<div class="row"  style="padding-top: 1px;">
											<ul id="orgUserTree" class="ztree" style="padding-left: 20px;"></ul>
										</div>
									</td>
		 
								</tr>
				
							</table>
						</div>
		<!-- 意见表单 -->
		<form id="form1"  >
		<input type="hidden"  class="mini-hidden"  name="taaskId" value="${myActRuTaskId}"  style="width: 90%" />
				
		<div class="form-inner">
				
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<tr>
						<th>缓办人：</th>
						<td>
							
								<input name="postponeId" value="${userName}"
							class="mini-textbox"   style="width: 90%"   readonly />
						</td>
						<th>缓办时间：</th>
						<td>
							
								<input name="postponeTime" value="${hyPostponeOpinion.postponeTime}"
							class="mini-datepicker" required="true" format="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
						<th>批准人：</th>
						<td>
							
								<input name="approvalId" id="approvalId" value="${hyPostponeOpinion.approvalId}"
							class="mini-textbox"style="width: 90%"   readonly />
						</td>
						<th>案件名称：</th>
						<td>
							
								<input name="CASENAME" value="${taskName}"
							class="mini-textbox"   style="width: 90%" readonly/>
						</td>
					</tr>
					<tr>
						
					</tr>
					<tr>
						<th>缓办天数：</th>
						<td>
								<input name="postponedDays" required="true" value="${hyPostponeOpinion.postponedDays}"
							class="mini-textbox"  format="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
						<th>缓办原因：</th>
						<td>
							
								<textarea   name="postponeReason" value="${hyPostponeOpinion.postponeReason}"
							class="mini-textarea" vtype="maxLength:200" required="true" emptyText="请输入缓办原因" style="width: 90%" ></textarea>
						</td>
					</tr>
				</table>
				</div>	
				</form>
				
							
		<div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border-left:0;border-bottom:0;border-right:0;">
			
			<a class="mini-button" id="saveCandidate" iconCls="icon-ok"style="width:60px;" onclick="saveCandidate()">确定</a>
			<span style="display:inline-block;width:25px;"></span>
			<a class="mini-button" style="width:60px;" iconCls="icon-close" onclick="onCancel()">取消</a>
		</div>
		
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<rx:formScript formId="form1" baseUrl="extend/bpmtask/bpmTask"
		entityName="com.huiyu.bpm.extend.postponeopinion.entity.HyPostponeOpinion" />
	</body>
	
</html>
