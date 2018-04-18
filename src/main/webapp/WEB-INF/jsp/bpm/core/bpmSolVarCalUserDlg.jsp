<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<html>
<head>
<title>业务流程解决方案的人员变量选择</title>
<%@include file="/commons/list.jsp" %>
</head>
<body>
	<div class="vform-margin">
		<div class="mini-toolbar mini-toolbar-bottom">
			<table style="width:100%">
				<tr>
					<td>
						<a class="mini-button" iconCls="icon-save" plain="true" onclick="ok">确定</a>
						<a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a>
					</td>
				</tr>
			</table>
		</div>
		<p>
			&nbsp;&nbsp;说明：左边为变量运算后的最终用户类型，并且把计算后的值作为流程任务的审批者。
		</p>
		<form id="vform" class="form-outer2">
			<table class="table-detail column_1" cellpadding="0" cellspacing="1" style="width:100%">
				<thead>
					<tr>
						<th width="200">类型</th>
						<th width="200">变量</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>
							<div id="varType" class="mini-radiobuttonlist" name="varType" data="[{id:'user',text:'用户'},{id:'org',text:'用户组'}]" required="true"></div>
						</th>
						<td>
							<input id="varKey" class="mini-combobox" name="varKey" url="${ctxPath}/bpm/core/bpmSolVar/getBySolIdActDefId.do?solId=${param.solId}&actDefId=${param.actDefId}" 
							valueField="key" textField="name" style="width:90%" required="true"/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<script type="text/javascript">
		mini.parse();
		var form=new mini.Form('vform');
		function ok(){
			form.validate();
			if(form.isValid()){
				CloseWindow('ok');
			}else{
				alert('请选择表单值!');
				return;
			}
		}
		
		function setData(config){
			form.setData(mini.decode(config));
		}
		
		
		function getConfigJson(){
			var varType=mini.get("varType").getValue();
			var varTypeText=(varType=='user')?'用户':'用户组';
			var varKey=mini.get('varKey').getValue();
			var data=mini.get('varKey').getData();
			var varText=null;
			for(var i=0;i<data.length;i++){
				if(varKey==data[i].key){
					varText=data[i].name;
					break;
				}
			}
			
			var configDescp=varTypeText+"值来自变量["+varText+"]("+varKey+")";
			
			return{
				config:{
					varType:varType,
					varKey:varKey
				},
				configDescp:configDescp
			};
		}
	</script>
</body>
</html>