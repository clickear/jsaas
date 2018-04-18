
<%-- 
    Document   : [栏目消息盒子表]编辑页
    Created on : 2017-09-01 11:35:24
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[栏目消息盒子表]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="insMsgboxDef.id" />
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${insMsgboxDef.boxId}" />
				<table class="table-detail column_2" cellspacing="1" cellpadding="0">
					<caption>[栏目消息盒子表]基本信息</caption>
					<tr>
						<th>名字</th>
						<td>
							
								<input name="name" value="${insMsgboxDef.name}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>Key</th>
						<td>
							
								<input name="key" value="${insMsgboxDef.key}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>				
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/info/insMsgboxDef"
		entityName="com.redxun.oa.info.entity.InsMsgboxDef" />
	<script type="text/javascript">
	addBody();
	function onOk(){
		form.validate();
	    if (!form.isValid()) {
	        return;
	    }	        
	    var data=form.getData();
		var config={
        	url:"${ctxPath}/oa/info/insMsgboxDef/save.do",
        	method:'POST',
        	postJson:true,
        	data:data,
        	success:function(result){
        		//如果存在自定义的函数，则回调
        		if(isExitsFunction('successCallback')){
        			successCallback.call(this,result);
        			return;	
        		}
        		
        		CloseWindow('ok');
        	}
        }
	        
		_SubmitJson(config);
	}	
	</script>
</body>
</html>