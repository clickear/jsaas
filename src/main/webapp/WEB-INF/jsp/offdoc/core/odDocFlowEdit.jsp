<%-- 
    Document   : [OdDocFlow]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[OdDocFlow]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${odDocFlow.schemeId}" />
	<div id="p1" class="form-outer2 shadowBox">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="schemeId" class="mini-hidden" value="${odDocFlow.schemeId}" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<caption>[${depName }]收发文配置信息</caption>
					<tr>
						<th>发文流程方案</th>
						<td>
							<input 
								id="sendSolId" 
								name="sendSolId" 
								value="${odDocFlow.sendSolId}" 
								text="${odDocFlow.sendSolName}" 
								class="mini-buttonedit"
								emptyText="请选择..." 
								allowInput="false" 
								onbuttonclick="onDispatch" 
							/>
							<input id="sendSolName" name="sendSolName" class="mini-hidden" value="${odDocFlow.sendSolName}" />
							<input name="depId" value="${depId}" class="mini-hidden" />

						</td>
						<th>收文流程方案</th>
						<td>
							<input 
								id="recSolId" 
								name="recSolId" 
								value="${odDocFlow.recSolId}" 
								text="${odDocFlow.recSolName}" 
								class="mini-buttonedit"
								emptyText="请选择..." 
								allowInput="false" 
								onbuttonclick="onIncoming" 
							/>
							<input id="recSolName" name="recSolName" class="mini-hidden" value="${odDocFlow.recSolName}" />
						</td>
					</tr>

				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="offdoc/core/odDocFlow" entityName="com.redxun.offdoc.core.entity.OdDocFlow" />
	<script type="text/javascript">
		addBody();
		indentBody();
		mini.parse();
	
	//选择发文流程
	function onDispatch(){//  
		 _OpenWindow({
				url:__rootPath+'/bpm/core/bpmSolution/dialog.do?single='+true,
				title:'流程解决方案选择',
				height:600,
				width:800,
				iconCls:'icon-flow',
				ondestroy:function(action){
					if(action!='ok')return;
					var iframe = this.getIFrameEl();
		            var sols = iframe.contentWindow.getSolutions();
		            mini.get("sendSolId").setValue(sols[0].solId);
		        	mini.get("sendSolId").setText(sols[0].name);
		        	mini.get("sendSolName").setValue(sols[0].name);
				}
			});
		
	}
//选择收文流程
function onIncoming(){// 

	_OpenWindow({
		url:__rootPath+'/bpm/core/bpmSolution/dialog.do?single='+true,
		title:'流程解决方案选择',
		height:600,
		width:800,
		iconCls:'icon-flow',
		ondestroy:function(action){
			if(action!='ok')return;
			var iframe = this.getIFrameEl();
            var sols = iframe.contentWindow.getSolutions();
            mini.get("recSolId").setValue(sols[0].solId);
        	mini.get("recSolId").setText(sols[0].name);
        	mini.get("recSolName").setValue(sols[0].name);
		}
	});
	
}


	
//重写了saveData方法
function selfSaveData(issave){
	var form = new mini.Form("form1");
	form.validate();
    if (!form.isValid()) {//验证表格
        return; }
    var formData=$("#form1").serializeArray();
    //加上租用Id
    if(tenantId!=''){
        formData.push({
        	name:'tenantId',
        	value:tenantId });
    }
    _SubmitJson({
    	url:"${ctxPath}/offdoc/core/odDocFlow/save.do?",
    	method:'POST',
    	data:formData,
    	success:function(result){
    		//如果存在自定义的函数，则回调
    		if(isExitsFunction('successCallback')){
    			successCallback.call(this,result);
    			return;	
    		}
    		var pkId=mini.get("pkId").getValue();
    		//为更新
    		if (pkId!=''){
    			CloseWindow('ok');
    			return;
    		}
    		CloseWindow('ok');} });
}
</script>
</body>
</html>