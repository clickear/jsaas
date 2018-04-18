
<%-- 
    Document   : [INS_MSG_DEF]明细页
    Created on : 2017-09-01 10:40:15
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[INS_MSG_DEF]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
                	<caption>[INS_MSG_DEF]基本信息</caption>
					<tr>
						<th>颜　色</th>
						<td>
							${insMsgDef.color}
						</td>
					</tr>
					<tr>
						<th>更多URl</th>
						<td>
							${insMsgDef.url}
						</td>
					</tr>
					<tr>
						<th>图　标</th>
						<td>
							${insMsgDef.icon}
						</td>
					</tr>
					<tr>
						<th>文　字</th>
						<td>
							${insMsgDef.content}
						</td>
					</tr>
					<tr>
						<th>租用机构ID</th>
						<td>
							${insMsgDef.tenantId}
						</td>
					</tr>
					<tr>
						<th>创建人ID</th>
						<td>
							${insMsgDef.createBy}
						</td>
					</tr>
					<tr>
						<th>创建时间</th>
						<td>
							${insMsgDef.createTime}
						</td>
					</tr>
					<tr>
						<th>更新人ID</th>
						<td>
							${insMsgDef.updateBy}
						</td>
					</tr>
					<tr>
						<th>更新时间</th>
						<td>
							${insMsgDef.updateTime}
						</td>
					</tr>
					<tr>
						<th>数据库名字</th>
						<td>
							${insMsgDef.dsName}
						</td>
					</tr>
					<tr>
						<th>数据库id</th>
						<td>
							${insMsgDef.dsAlias}
						</td>
					</tr>
					<tr>
						<th>SQL语句</th>
						<td>
							${insMsgDef.sqlFunc}
						</td>
					</tr>
					<tr>
						<th>TYPE_</th>
						<td>
							${insMsgDef.type}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="oa/info/insMsgDef" 
        entityName="com.redxun.oa.info.entity.InsMsgDef"
        formId="form1"/>
        
        <script type="text/javascript">
		mini.parse();
		var form = new mini.Form("#form1");
		var pkId = ${insMsgDef.msgId};
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/oa/info/insMsgDef/getJson.do",
				data:{ids:pkId},
				success:function (json) {
					form.setData(json);
				}					
			});
		})
		</script>
    </body>
</html>