
<%-- 
    Document   : [微信卡券]明细页
    Created on : 2017-08-24 14:26:26
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[微信卡券]明细</title>
        <%@include file="/commons/get.jsp" %>
   	    <style>
	    	.text{
	    		width: 80%;
	    		font-weight: normal;
	    		font-size: 14px;
				word-break:break-all;
				word-wrap:break-word;
	    	}
	    </style>
    	
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>
		<div class="heightBox"></div>
        <div id="form1" class="form-outer shadowBox90" style="margin-bottom: 40px;">
           	<table style="width:100%" class="table-detail column_1_m" cellpadding="0" cellspacing="1">
              	<caption>[微信卡券]基本信息</caption>
				<tr>
					<th>公众号 ID</th>
					<td>
						${wxTicket.pubId}
					</td>
				</tr>
				<tr>
					<th>卡券类型</th>
					<td>
						${wxTicket.cardType}
					</td>
				</tr>
				<tr>
					<th>卡券的商户logo</th>
					<td>
						<h1 class="text">${wxTicket.logoUrl}</h1>
					</td>
				</tr>
				<tr>
					<th>码　　型</th>
					<td>
						${wxTicket.codeType}
					</td>
				</tr>
				<tr>
					<th>商户名字</th>
					<td>
						${wxTicket.brandName}
					</td>
				</tr>
				<tr>
					<th>卡  券  名</th>
					<td>
						${wxTicket.title}
					</td>
				</tr>
				<tr>
					<th>券  颜  色</th>
					<td>
						${wxTicket.color}
					</td>
				</tr>
				<tr>
					<th>卡券使用提醒</th>
					<td>
						${wxTicket.notice}
					</td>
				</tr>
				<tr>
					<th>卡券使用说明</th>
					<td>
						${wxTicket.description}
					</td>
				</tr>
				<tr>
					<th>商品信息</th>
					<td>
						${wxTicket.sku}
					</td>
				</tr>
				<tr>
					<th>使用日期</th>
					<td>
						<h1 class=text>${wxTicket.dateInfo}</h1>
					</td>
				</tr>
				<tr>
					<th>基础非必须信息</th>
					<td>
						<h1 class="text">${wxTicket.baseInfo}</h1>
					</td>
				</tr>
				<tr>
					<th>高级非必填信息</th>
					<td>
						${wxTicket.advancedInfo}
					</td>
				</tr>
				<tr>
					<th>专用配置</th>
					<td>
						${wxTicket.specialConfig}
					</td>
				</tr>
				<tr>
					<th>租用机构ID</th>
					<td>
						${wxTicket.tenantId}
					</td>
				</tr>
				<tr>
					<th>更新时间</th>
					<td>
						${wxTicket.updateTime}
					</td>
				</tr>
				<tr>
					<th>更新人ID</th>
					<td>
						${wxTicket.updateBy}
					</td>
				</tr>
				<tr>
					<th>创建时间</th>
					<td>
						${wxTicket.createTime}
					</td>
				</tr>
				<tr>
					<th>创建人ID</th>
					<td>
						${wxTicket.createBy}
					</td>
				</tr>
		</table>
           
    	</div>
        <rx:detailScript baseUrl="wx/core/wxTicket" 
        entityName="com.redxun.wx.core.entity.WxTicket"
        formId="form1"/>
        
        <script type="text/javascript">
        addBody();
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
		</script>
    </body>
</html>