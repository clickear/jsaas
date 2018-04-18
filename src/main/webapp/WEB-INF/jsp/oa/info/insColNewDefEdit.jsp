
<%-- 
    Document   : [ins_col_new_def]编辑页
    Created on : 2017-08-25 10:08:04
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[ins_col_new_def]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="insColNewDef.id" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${insColNewDef.id}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[ins_col_new_def]基本信息</caption>
					<tr>
						<th>COL_ID_：</th>
						<td>
							
								<input name="colId" value="${insColNewDef.colId}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>NEW_ID_：</th>
						<td>
							
								<input name="newId" value="${insColNewDef.newId}"
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
	var pkId = ${insColNewDef.id};
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/oa/info/insColNewDef/getJson.do",
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
        	url:"${ctxPath}/oa/info/insColNewDef/save.do",
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