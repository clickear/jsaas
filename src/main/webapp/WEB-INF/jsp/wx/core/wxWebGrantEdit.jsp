
<%-- 
    Document   : [微信网页授权]编辑页
    Created on : 2017-08-18 17:05:42
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>编辑授权url</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="wxWebGrant.id" />
	<div id="p1" class="form-outer shadowBox90" style="padding-top: 8px;">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="id" name="id" class="mini-hidden" value="${wxWebGrant.id}" />
				<input id="pubId"  name="pubId" value="${wxWebGrant.pubId}" class="mini-hidden"  />
				<input id="transformUrl"  name="transformUrl" value="${wxWebGrant.transformUrl}"   class="mini-hidden"  />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<tr>
						<th>链接</th>
						<td><input name="url" value="${wxWebGrant.url}" class="mini-textbox" style="width: 90%" /></td>
					</tr>
					<c:if test="${wxWebGrant.id!=null}">
					<tr>
						<th>转换后的URL</th>
						<td>${wxWebGrant.transformUrl}</td>
					</tr>
					</c:if>
					
					<%-- <tr>
						<th>配置信息：</th>
						<td>
							
								<input name="config" value="${wxWebGrant.config}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr> --%>
				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	addBody();
	mini.parse();
	var form = new mini.Form("#form1");
	
	
	
	
	function onOk(){
		form.validate();
	    if (!form.isValid()) {
	        return;
	    }	        
	    var data=form.getData();
	    console.log(data);
		var config={
        	url:"${ctxPath}/wx/core/wxWebGrant/save.do",
        	method:'POST',
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