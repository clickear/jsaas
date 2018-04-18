
<%-- 
    Document   : [新闻公告权限表]明细页
    Created on : 2017-11-03 11:47:25
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[新闻公告权限表]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                	<caption>[新闻公告权限表]基本信息</caption>
					<tr>
						<th>NEWS_ID_：</th>
						<td>
							${insNewsCtl.newsId}
						</td>
					</tr>
					<tr>
						<th>USER_ID_：</th>
						<td>
							${insNewsCtl.userId}
						</td>
					</tr>
					<tr>
						<th>GROUP_ID_：</th>
						<td>
							${insNewsCtl.groupId}
						</td>
					</tr>
					<tr>
						<th>RIGHT_：</th>
						<td>
							${insNewsCtl.right}
						</td>
					</tr>
					<tr>
						<th>TYPE_：</th>
						<td>
							${insNewsCtl.type}
						</td>
					</tr>
					<tr>
						<th>租用机构ID：</th>
						<td>
							${insNewsCtl.tenantId}
						</td>
					</tr>
					<tr>
						<th>创建人ID：</th>
						<td>
							${insNewsCtl.createBy}
						</td>
					</tr>
					<tr>
						<th>创建时间：</th>
						<td>
							${insNewsCtl.createTime}
						</td>
					</tr>
					<tr>
						<th>更新人ID：</th>
						<td>
							${insNewsCtl.updateBy}
						</td>
					</tr>
					<tr>
						<th>更新时间：</th>
						<td>
							${insNewsCtl.updateTime}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="oa/info/insNewsCtl" 
        entityName="com.redxun.oa.info.entity.InsNewsCtl"
        formId="form1"/>
        
        <script type="text/javascript">
		mini.parse();
		var form = new mini.Form("#form1");
		var pkId = '${insNewsCtl.ctlId}';
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/oa/info/insNewsCtl/getJson.do",
				data:{ids:pkId},
				success:function (json) {
					form.setData(json);
				}					
			});
		})
		</script>
    </body>
</html>