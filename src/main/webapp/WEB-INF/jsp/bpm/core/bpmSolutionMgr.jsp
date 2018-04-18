<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
	<title>业务流程解决方案管理-${bpmSolution.name}</title>
	<%@include file="/commons/edit.jsp"%>
	<!-- 引入ystep样式 -->
	<link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/ystep/css/ystep.css"/>
	<!-- 引入ystep插件 -->
	<script src="${ctxPath}/scripts/jquery/plugins/ystep/js/ystep.js"></script>
	<style type="text/css">
		.mini-tabs-bodys{
			background: #f7f7f7;
		}
		#north{
		
		}
	</style>
</head>
<body>
		<div id="layout1" class="mini-layout" style="width:100%;height:100%;" >
		    <div 
		    	title="north" 
		    	region="north" 
		    	height="120" 
		    	showSplitIcon="false" 
		    	showHeader="false"
		    	class="north-top"
	    	>
		    	<div class="mini-toolbar" >
			        <c:if test="${allowCopyConfig==true}">
                   		<a class="mini-button" iconCls="icon-copy" onclick="configCopy()">复制主版本配置</a>
                   	</c:if>
                    <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refresh()">刷新</a>
                    <span class="separator"></span>
                    <a class="mini-button" iconCls="icon-prev" plain="true" onclick="prevStep()">上一步</a>
                    <a class="mini-button" iconCls="icon-next" plain="true" onclick="nextStep()">下一步</a>
                    
                    <c:if test="${bpmSolution.status!='DEPLOYED'}">
                    	<a id="deployButton" class="mini-button" iconCls="icon-flow-deploy" plain="true" onclick="deploy()">发布</a>
                    </c:if>
			     </div>
		    	<div class="ystep1" style="padding-left:8px;padding-top:2px;"></div>
		    </div>
		    <div region="center" showHeader="false" showSplitIcon="false" style="border:0;" bodyStyle="border:0;">
		    	<div class="mini-fit">
			    	<div id="tabs1" class="mini-tabs"  onactivechanged="onTabsActiveChanged" style="width:100%;height:100%">
					    <div id="step0" title="方案概述" iconCls="icon-info">
					    	<div class="shadowBox" style="margin-top: 40px;">
					    		<div id="form1" class="form-outer" style="border:none;">
									<div class="form-inner">
										<table style="width: 100%" class="table-detail column_2_m" cellpadding="0" cellspacing="1">
											<caption>业务流程解决方案基本信息</caption>
											<tr>
												<th>解决方案名称</th>
												<td>${bpmSolution.name}</td>
												<th>解决方案标识Key</th>
												<td>${bpmSolution.key}</td>
											</tr>
											<tr>
												<th>所属分类</th>
												<td>${bpmSolution.sysTree.name}</td>
												<th>单独使用业务模型</th>
												<td>${bpmSolution.isUseBmodel}</td>
											</tr>
											<tr>
												<th>状　　态</th>
												<td>${bpmSolution.status}</td>
												<th>解决方案ID</th>
												<td>${bpmSolution.solId }</td>
											</tr>
											<tr>
												<th>解决方案描述</th>
												<td colspan="3">${bpmSolution.descp}</td>
											</tr>
										</table>
									</div>
								</div>
					    	</div>
					    </div><!-- end of 方案概述 -->
					    <div 
					    	id="step1" 
					    	title="流程定义" 
					    	iconCls="icon-flow" 
					    	url="${ctxPath}/bpm/core/bpmSolution/bpmDef.do?solId=${bpmSolution.solId}&actDefId=${bpmDef.actDefId}"
				    	></div>
					    <div 
					    	id="step2" 
					    	title="审批表单" 
					    	iconCls="icon-form" 
					    	<c:if test="${isBindFlow==false}">enabled="false"</c:if> 
					    	url="${ctxPath}/bpm/core/bpmSolution/formView.do?solId=${bpmSolution.solId}&actDefId=${bpmDef.actDefId}"
				    	></div>
						
					   	<div 
					   		id="step3" 
					   		title="变量配置" 
					   		iconCls="icon-var" 
					   		<c:if test="${isBindFlow==false}">enabled="false"</c:if> 
					   		url="${ctxPath}/bpm/core/bpmSolution/vars.do?solId=${bpmSolution.solId}&actDefId=${bpmDef.actDefId}"
				   		></div>
					    <div 
					    	id="step4" 
					    	title="审批人员" 
					    	iconCls="icon-user" 
					    	<c:if test="${isBindFlow==false}">enabled="false"</c:if> 
					    	url="${ctxPath}/bpm/core/bpmSolution/user.do?solId=${bpmSolution.solId}&actDefId=${bpmDef.actDefId}"
				    	></div>
					    <div 
					    	id="step5" 
					    	title="节点配置" 
					    	iconCls="icon-node" 
					    	<c:if test="${isBindFlow==false}">enabled="false"</c:if> 
					    	url="${ctxPath}/bpm/core/bpmSolution/nodeSet.do?solId=${bpmSolution.solId}&actDefId=${bpmDef.actDefId}"
				    	></div>
						<div 
							id="step7" 
							title="授权" 
							iconCls="icon-grant" 
							<c:if test="${isBindFlow==false}">enabled="false"</c:if> 
							url="${ctxPath}/bpm/core/bpmSolution/grant.do?solId=${bpmSolution.solId}&actDefId=${bpmDef.actDefId}"
						></div>
					</div>
				</div>
		    </div>
		</div>
	
	<script type="text/javascript">
		mini.parse();
		var solId='${bpmSolution.solId}';
		var isBindFlow='${isBindFlow}';
		var tabs=mini.get('tabs1');
		top['solutionMgrWin']=window;
		var curStep=${bpmSolution.step};
		
		function onCollapse(){
			var layout=mini.get('layout1');
			layout.updateRegion("north", { visible: false });
		}
		//从主版本中拷贝配置
		function configCopy(){
			_SubmitJson({
				url:__rootPath+'/bpm/core/bpmSolution/copyConfig.do',
				data:{
					solId:solId,
					actDefId:'${bpmDef.actDefId}'
				},
				success:function(result){
					location.reload();
				}
			});
		}
		
		$(function(){
			$(".ystep1").loadStep({
			      size: "large",
			      color: "blue",
			      steps: [{
			        title: "初始化",
			        content: "初始化流程解决方案"
			      },{
			        title: "流程定义",
			        content: "进行流程定义的设计，并且进行发布"
			      },
			     
			      {
			        title: "审批表单",
			        content: "绑定或设计跟流程相关的业务展示表单方案"
				  },
				  
			      {
				        title: "变量配置",
				        content: "设置流程各环节的流程需要使用的变量"
				  },
			      {
			        title: "审批人员",
			        content: "绑定流程节点的执行人员"
			      },{
			    	  title:"节点配置",
			    	  content:"设置节点配置参数及绑定流程数据交互配置"
			      }
			      /* ,{
			    	  title:"测试",
			    	  content:"流程模拟测试"
			    	} */,
			    	{
				    	  title:"授权",
				    	  content:"流程授权配置"
				    }
			      ]
			    });
			
			//$(".ystep1").setStep(curStep);
			//把当前步骤前面的tab更新为可用
			for(var i=1;i<=curStep;i++){
				var tab=tabs.getTab(i-1);
				tabs.updateTab(tab,{enabled:true});
			}
			var curTab=tabs.getTab(curStep);
			tabs.activeTab(curTab);
		});
		function refresh(){
			window.location.reload();
		}
		
		function prevStep(){
			var step=$(".ystep1").getStep();
			if(step<=1) return;
			var tab=tabs.getTab(step-1-1);
			tabs.updateTab(tab,{enabled:true});
			tabs.activeTab(tab);
		}
		
		function nextStep(){
			//步骤从1开始，tab的index从0开始
			var step=$(".ystep1").getStep();
			if(step>=2 && isBindFlow=='false'){
				alert('请先绑定流程！');
				return;
			}
			if(step>7) return;
			//检查当前步骤是否已经完成，需要确认检查条件TODO
			
			var tab=tabs.getTab(step+1-1);
			tabs.updateTab(tab,{enabled:true});
			tabs.activeTab(tab);
			/*
			if(step==6){
				mini.get('deployButton').setEnabled(true);
			}*/
		}
		
		function edit(){
			_OpenWindow({
				title:'编辑流程业务解决方案',
				url:__rootPath+'/bpm/core/bpmSolution/edit.do?hideNav=true&pkId='+solId,
				width:850,
				height:630,
				ondestroy:function(action){
					if(action!='ok')return;
					window.location.reload();
				}
			});
		}
		//发布
		function deploy(){
			_SubmitJson({
				url:__rootPath+'/bpm/core/bpmSolution/deploy.do',
				method:'POST',
				data:{
					solId:solId,
					actDefId:'${bpmDef.actDefId}'
				},
				success:function(text){
					window.location.reload();
				}
			});
		}
		
		function onTabsActiveChanged(e) {
            var tabs = e.sender;
           // var tab = tabs.getActiveTab();
            var index=tabs.getActiveIndex();
           
            $(".ystep1").setStep(index+1);
            //更新当前步骤
            _SubmitJson({
				url:__rootPath+'/bpm/core/bpmSolution/updStep.do',
				data:{
					solId:solId,
					step:index
				},
				method:'POST',
				showMsg:false
			});
        }
	</script>
</body>
</html>