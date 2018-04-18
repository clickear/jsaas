
<%-- 
    Document   : [公众号关键字回复]明细页
    Created on : 2017-08-30 11:39:20
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[公众号关键字回复]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail column_1" cellpadding="0" cellspacing="1">
                	<caption>[公众号关键字回复]基本信息</caption>
					<tr>
						<th>公众号ID</th>
						<td>
							${wxKeyWordReply.pubId}
						</td>
					</tr>
					<tr>
						<th>关键字</th>
						<td>
							${wxKeyWordReply.keyWord}
						</td>
					</tr>
					<tr>
						<th>回复方式</th>
						<td>
							${wxKeyWordReply.replyType}
						</td>
					</tr>
					<tr>
						<th>回复内容</th>
						<td>
							${wxKeyWordReply.replyContent}
						</td>
					</tr>
					<tr>
						<th>创建时间</th>
						<td>
							${wxKeyWordReply.createTime}
						</td>
					</tr>
					<tr>
						<th>创建人ID</th>
						<td>
							${wxKeyWordReply.createBy}
						</td>
					</tr>
					<tr>
						<th>租用机构ID</th>
						<td>
							${wxKeyWordReply.tenantId}
						</td>
					</tr>
					<tr>
						<th>更新时间</th>
						<td>
							${wxKeyWordReply.updateTime}
						</td>
					</tr>
					<tr>
						<th>更新人ID</th>
						<td>
							${wxKeyWordReply.updateBy}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="wx/core/wxKeyWordReply"  entityName="com.redxun.wx.core.entity.WxKeyWordReply"  formId="form1"/>
        
        <script type="text/javascript">
		mini.parse();
		
		</script>
    </body>
</html>