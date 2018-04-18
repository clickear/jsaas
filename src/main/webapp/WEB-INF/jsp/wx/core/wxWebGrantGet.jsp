
<%-- 
    Document   : [微信网页授权]明细页
    Created on : 2017-08-18 17:05:42
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[微信网页授权]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>
		<div class="heightBox"></div>
        <div id="form1" class="form-outer shadowBox90">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail column_1" cellpadding="0" cellspacing="1">
                	<caption>[微信网页授权]基本信息</caption>
					<tr>
						<th>公众号ID</th>
						<td>
							${wxWebGrant.pubId}
						</td>
					</tr>
					<tr>
						<th>链　　接</th>
						<td>
							${wxWebGrant.url}
						</td>
					</tr>
					<tr>
						<th>转换后的URL</th>
						<td>
							${wxWebGrant.transformUrl}
						</td>
					</tr>
					<tr>
						<th>配置信息</th>
						<td>
							${wxWebGrant.config}
						</td>
					</tr>
					<tr>
						<th>创建时间</th>
						<td>
							${wxWebGrant.createTime}
						</td>
					</tr>
					<tr>
						<th>创建人ID</th>
						<td>
							${wxWebGrant.createBy}
						</td>
					</tr>
					<tr>
						<th>租用机构ID</th>
						<td>
							${wxWebGrant.tenantId}
						</td>
					</tr>
					<tr>
						<th>更新时间</th>
						<td>
							${wxWebGrant.updateTime}
						</td>
					</tr>
					<tr>
						<th>更新人ID</th>
						<td>
							${wxWebGrant.updateBy}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="wx/core/wxWebGrant" 
        entityName="com.redxun.wx.core.entity.WxWebGrant"
        formId="form1"/>
        
        <script type="text/javascript">
			addBody();
		</script>
    </body>
</html>