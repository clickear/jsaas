
<%-- 
    Document   : [INS_MSGBOX_BOX_DEF]编辑页
    Created on : 2017-09-01 10:58:03
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[INS_MSGBOX_BOX_DEF]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="insMsgboxBoxDef.id" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${insMsgboxBoxDef.id}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[INS_MSGBOX_BOX_DEF]基本信息</caption>
					<tr>
						<th>SN_：</th>
						<td>
							
								<input name="sn" value="${insMsgboxBoxDef.sn}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>MSG_ID_：</th>
						<td>
							
								<input name="msgId" value="${insMsgboxBoxDef.msgId}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>BOX_ID_：</th>
						<td>
							
								<input name="boxId" value="${insMsgboxBoxDef.boxId}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	mini.parse();
	var form = new mini.Form("#form1");
	var pkId = '${insMsgboxBoxDef.id}';
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/oa/info/insMsgboxBoxDef/getJson.do",
				data:{ids:pkId},
				success:function (json) {
					form.setData(json);
				}					
			});
		})
	
	
	
	function onOk(){
		form.validate();
	    if (!form.isValid()) {
	        return;
	    }	        
	    var data=form.getData();
		var config={
        	url:"${ctxPath}/oa/info/insMsgboxBoxDef/save.do",
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