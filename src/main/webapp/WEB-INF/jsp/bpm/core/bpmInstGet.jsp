<%-- 
    Document   : 流程实例明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>流程实例明细</title>
<%@include file="/commons/get.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.css" />
<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.js"></script>
<!-- 处理流程图上的提示 -->
<link rel="stylesheet" type="text/css" href="${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.min.css" />
<script src="${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.min.js" type="text/javascript"></script>
<!-- 加上扩展控件的支持 -->
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/ueditor-ext.js"></script>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" excludeButtons="prevRecord,nextRecord,remove"/>
	<div class="mini-fit">
		<div id="tabs1" class="mini-tabs"  style="width:100%;height:100%;">
			<div title="基本信息"  class=" form-outer2" >
				<div>
					<table style="width: 100%" class="table-detail" cellpadding="0" cellspacing="1">
						<tr>
							<th>标题：</th>
							<td colspan="3">${bpmInst.subject}</td>
						</tr>
						<tr>
							<th width="120">运行状态：</th>
							<td>${bpmInst.status}</td>
							<th width="120">版本：</th>
							<td>${bpmInst.version}</td>
						</tr>
						<tr>
							<th>是否为测试：</th>
							<td>${bpmInst.isTest}</td>
							<th>结束时间：</th>
							<td>
								<c:if test="${not empty bpmInst.endTime }">
									<fmt:formatDate value="${bpmInst.endTime}" pattern="yyyy-MM-dd HH:mm" />
								</c:if>
							</td>
						</tr>
						<tr>
							<th>实例ID</th>
							<td colspan="3">${bpmInst.instId}</td>
						</tr>
					</table>
				</div>
				<div>
					<table class="table-detail" cellpadding="0" cellspacing="1">
						<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${bpmInst.createBy}" /></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${bpmInst.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${bpmInst.updateBy}" /></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${bpmInst.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div title="关联数据"  class=" form-outer2">
				<table class="table-detail" cellpadding="0" cellspacing="1">
					<tr>
						<th style="width:120px">流程定义ID：</th>
						<td>${bpmInst.defId}</td>

						<th>Activiti实例ID：</th>
						<td>${bpmInst.actInstId}</td>
					</tr>
					<tr>
						<th>Activiti定义ID：</th>
						<td>${bpmInst.actDefId}</td>
						<th>解决方案ID：</th>
						<td>${bpmInst.solId}</td>
					</tr>
					<tr>
						<th>业务键ID：</th>
						<td>${bpmInst.busKey}</td>
						<th>业务表单实例ID：</th>
						<td>${bpmInst.formInstId}</td>
					</tr>
				</table>
			</div>
			<c:if test="${bpmInst.isUseBmodel=='NO'}">
			<div title="业务表单"  class=" form-outer2">
				<div class="mini-toolbar">
					<a class="mini-button" iconCls="icon-print" onclick="formPrint()" plain="true">打印</a>
				</div>
				<div id="formView" style="padding:10px;">
					
				</div>
			</div>
			</c:if>
			
			<div title="业务数据"  class=" form-outer2">
				<textarea id="jsonData" style="display:none">${formInst.jsonData}</textarea>
				<div id="jsonview"></div>
			</div>
			
			<div title="流程图"  class=" form-outer2">
				
						<img src="${ctxPath}/bpm/activiti/processImage.do?instId=${bpmInst.instId}" usemap="#imgHref" style="border: 0px;"/>
						<imgArea:imgAreaScript instId="${bpmInst.instId}"></imgArea:imgAreaScript>
				
			</div>
			<c:if test="${not empty bpmInst.actInstId}">
			<div title="流程流转记录" url="${ctxPath}/bpm/core/bpmNodeJump/insts.do?actInstId=${bpmInst.actInstId}"></div>
			</c:if>
		</div>
	</div>
	
	<rx:detailScript baseUrl="bpm/core/bpmInst" entityName="com.redxun.bpm.core.entity.BpmInst" formId="form1" />
	<script type="text/javascript">
		var useBmodel='{bpmInst.useBmodel}';
		
		function formPrint(){
			_OpenWindow({
				url:__rootPath+'/bpm/form/bpmFormView/print.do?solId=${bpmInst.solId}&instId=${bpmInst.instId}',
				title:'表单打印',
				width:600,
				height:450,
				max:true
			});
		}
		
		$(function(){
			if(useBmodel!='YES'){
				//解析动态表单
				reqFormParse({
					solId:'${bpmInst.solId}',
					instId:'${bpmInst.instId}',
					containerId:'formView'
				});
			}
			var json=$("#jsonData").val();
			
			if(json!=''){ 
				$("#jsonview").jsonViewer(mini.decode(json));
			}
			
			$("area[type='userTask']").each(function(){
			 	var nodeId=$(this).attr('id');
			 	//console.log(nodeId);
				$(this).qtip({
					content: {
		                text: function(event, api) {
		                    $.ajax({
		                        url: __rootPath+"/bpm/core/bpmNodeJump/byNodeId.do?actInstId=${bpmInst.actInstId}&nodeId="+nodeId
		                    })
		                    .then(function(content) {
		                        api.set('content.text', content);
		                    }, function(xhr, status, error) {
		                        api.set('content.text', status + ': ' + error);
		                    });
		                    return '正在加载...'; 
		                }
		            },
		            position: {
		                target: 'mouse',
		                adjust: { mouse: false }
		            }
			    });
		 });
			
		});
		
		var list;
		$(function(){
			 $.ajax({
		          url: "${ctxPath}/bpm/core/bpmRuPath/calculateNode.do",
		          data: {"instId":"${bpmInst.instId}","taskId":"${param['taskId']}"}, 
		          success: function (text) {
		        	  list=text;//把返回的json数据传到list
		        	  //var length=text.length;
		        	  cartoon($("[type='startNode']").attr("id"),index);//从开始节点出发
		          }
		      });
		});
	
			
	</script>
</body>
</html>