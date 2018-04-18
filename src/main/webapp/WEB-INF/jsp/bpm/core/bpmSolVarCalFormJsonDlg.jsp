<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<html>
<head>
<title>业务流程解决方案的人员计算来自表单值选择</title>
<%@include file="/commons/list.jsp" %>
</head>
<body>
	<div  class="vform-margin">
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
		<div>
			&nbsp;说明：左边为表单变量运算后的最终用户类型，并且把计算后的值作为流程任务的审批者。
		</div>
		<form id="vform" class="form-outer2">
			<table class="table-detail column_3" cellpadding="0" cellspacing="1" style="width:100%">
				<thead>
					<tr>
						<th >类型</th>
						<th >表单选择</th>
						<th >表单字段</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width:30%;">
							<div id="varType" class="mini-radiobuttonlist" name="varType" data="[{id:'user',text:'用户'},{id:'org',text:'用户组'}]" required="true"></div>
						</td>
						<td style="width:40%;">
							<input id="boDefId" class="mini-combobox" name="boDefId" url="${ctxPath}/bpm/core/bpmSolution/boDefFields.do?solId=${param.solId}" onvaluechanged="onBoChanged"
							valueField="id" textField="name" popupHeight="150" style="width:90%;margin-top: 2px;" required="true"/>
							
						</td>
						<td style="width:40%;">
							<input id="varKey" class="mini-combobox" name="varKey"  
							valueField="key" textField="name" popupHeight="150" style="width:90%;margin-top: 2px;" required="true"/>
							
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
		
		function onBoChanged(){
			var boDefId = mini.get("boDefId");
			var varKey = mini.get("varKey");
			var id = boDefId.getValue();

			varKey.setValue("");            
            var url = "${ctxPath}/bpm/core/bpmSolution/modelFields.do?boDefId="+id;
            varKey.setUrl(url);
		}
		
		function setData(config){
			form.setData(mini.decode(config));
		}
		
		function getConfigJson(){
			var varType=mini.get("varType").getValue();
			var boDefId = mini.get("boDefId").getValue();
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
			
			var configDescp=varTypeText+"值来自表单字段["+varText+"]("+varKey+")";
			
			return{
				config:{
					varType:varType,
					boDefId:boDefId,
					varKey:varKey
				},
				configDescp:configDescp
			};
		}
	</script>
</body>
</html>