
<%-- 
    Document   : [微信卡券]编辑页
    Created on : 2017-08-24 14:26:26
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[微信卡券]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="wxTicket.id" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${wxTicket.id}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>[微信卡券]基本信息</caption>
					<tr>
						<th>公众号ID</th>
						<td>
							
								<input name="pubId" value="${wxTicket.pubId}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>卡券类型</th>
						<td>
							
								<input name="cardType" value="${wxTicket.cardType}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>卡券的商户logo</th>
						<td>
							
								<input name="logoUrl" value="${wxTicket.logoUrl}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>码　型</th>
						<td>
							
								<input name="codeType" value="${wxTicket.codeType}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>商户名字</th>
						<td>
							
								<input name="brandName" value="${wxTicket.brandName}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>卡券名</th>
						<td>
							
								<input name="title" value="${wxTicket.title}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>券颜色</th>
						<td>
							
								<input name="color" value="${wxTicket.color}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>卡券使用提醒</th>
						<td>
							
								<input name="notice" value="${wxTicket.notice}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>卡券使用说明</th>
						<td>
							
								<input name="description" value="${wxTicket.description}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>商品信息</th>
						<td>
							
								<input name="sku" value="${wxTicket.sku}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>使用日期</th>
						<td>
							
								<input name="dateInfo" value="${wxTicket.dateInfo}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>基础非必须信息</th>
						<td>
							
								<input name="baseInfo" value="${wxTicket.baseInfo}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>高级非必填信息</th>
						<td>
							
								<input name="advancedInfo" value="${wxTicket.advancedInfo}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>专用配置</th>
						<td>
							
								<input name="specialConfig" value="${wxTicket.specialConfig}"
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
	var pkId = ${wxTicket.id};
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/wx/core/wxTicket/getJson.do",
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
        	url:"${ctxPath}/wx/core/wxTicket/save.do",
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