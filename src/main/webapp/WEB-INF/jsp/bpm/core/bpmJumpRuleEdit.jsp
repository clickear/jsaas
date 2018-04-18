
<%-- 
    Document   : [流程跳转规则]编辑页
    Created on : 2018-04-10 13:44:42
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[流程跳转规则]编辑</title>
<%@include file="/commons/edit.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
<style type="text/css">
	.CodeMirror{
		border: 1px solid #eee;
	}
</style>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="bpmJumpRule.id" />
	
	
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${bpmJumpRule.id}" />
				<table class="table-detail column_2" cellspacing="1" cellpadding="0">
					<caption>[流程跳转规则]基本信息</caption>
					<tr>
						<th>规则名：</th>
						<td>
							<input name="name" value="${bpmJumpRule.name}"
							class="mini-textbox"   style="width: 90%" required="true" />
						</td>
						<th>序号：</th>
						<td>
							<input id="sn" name="sn" class="mini-spinner" value="${bpmJumpRule.sn}" minValue="1" maxValue="20" />
						</td>
						
					</tr>
					<tr>
						<th>源节点：</th>
						<td>
							${bpmJumpRule.nodeName}
						</td>
						<th>目标节点：</th>
						<td>
							<input id="target" name="target" textName="targetName" class="mini-buttonedit" 
								emptyText="请输入..."  onbuttonclick="onSelectNode" selectOnFocus="true" 
								value="${bpmJumpRule.target}" text="${bpmJumpRule.targetName}" required="true"/>
							
						</td>
					</tr>
					<tr>
						<th>规则脚本：</th>
						<td colspan="3">
							上下文变量:
							<ul>
								<li>vars 为流程变量。比如变量名称为 days ,在脚本中 编写代码为 vars.days</li>
								<li>cmd 为ProcessNextCmd实例。</li>
								<li>json 为表单数据。</li>
							</ul>
							<textarea name="rule" id="rule" rows="5" cols="60" >${bpmJumpRule.rule}</textarea>
						</td>
					</tr>
				
					<tr>
						<th>描述：</th>
						<td colspan="3">
							<textarea name="description" value="${bpmJumpRule.description}"
							class="mini-textarea"   style="width: 90%" ></textarea>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/core/bpmJumpRule"
		entityName="com.redxun.bpm.core.entity.BpmJumpRule" />
	<script type="text/javascript">
		var nodeId="${bpmJumpRule.nodeId}";
		var nodeName="${bpmJumpRule.nodeName}";
		var solId="${bpmJumpRule.solId}";
		var actDefId="${bpmJumpRule.actdefId}";
		function onSelectNode(){
			var conf={single:"true",end:"true"};
			openSolutionNode(actDefId,conf,function(data){
				var obj=data[0];
				var target=mini.get("target");
				target.setText(obj.name);
				target.setValue(obj.activityId);
				target.doValueChanged();
			});
		}
		
		var editor = null;
		function initCodeMirror() {
			var obj = document.getElementById("rule");
			editor = CodeMirror.fromTextArea(obj, {
				matchBrackets : true,
				mode : "text/x-groovy"
			});
			editor.setSize('auto', '200px');
		}
		
		initCodeMirror() ;
		
		function handleFormData(data){
			for(var i=0;i<data.length;i++){
				var o=data[i];
				var name=o.name;
				if(name=="rule"){
					o.value=editor.getValue();
				}
			}
			data.push({name:"nodeId",value:nodeId});
			data.push({name:"nodeName",value:nodeName});
			data.push({name:"actdefId",value:actDefId});
			data.push({name:"solId",value:solId});
			
			var rtn={formData:data,isValid:true};
			return rtn;
		}

		
	</script>
</body>
</html>