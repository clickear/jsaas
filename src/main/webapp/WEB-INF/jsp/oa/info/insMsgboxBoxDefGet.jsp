
<%-- 
    Document   : [INS_MSGBOX_BOX_DEF]明细页
    Created on : 2017-09-01 10:58:03
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[INS_MSGBOX_BOX_DEF]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                	<caption>[INS_MSGBOX_BOX_DEF]基本信息</caption>
					<tr>
						<th>SN_：</th>
						<td>
							${insMsgboxBoxDef.sn}
						</td>
					</tr>
					<tr>
						<th>更新时间：</th>
						<td>
							${insMsgboxBoxDef.updateTime}
						</td>
					</tr>
					<tr>
						<th>更新人ID：</th>
						<td>
							${insMsgboxBoxDef.updateBy}
						</td>
					</tr>
					<tr>
						<th>创建时间：</th>
						<td>
							${insMsgboxBoxDef.createTime}
						</td>
					</tr>
					<tr>
						<th>创建人ID：</th>
						<td>
							${insMsgboxBoxDef.createBy}
						</td>
					</tr>
					<tr>
						<th>租用机构ID：</th>
						<td>
							${insMsgboxBoxDef.tenantId}
						</td>
					</tr>
					<tr>
						<th>MSG_ID_：</th>
						<td>
							${insMsgboxBoxDef.msgId}
						</td>
					</tr>
					<tr>
						<th>BOX_ID_：</th>
						<td>
							${insMsgboxBoxDef.boxId}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="oa/info/insMsgboxBoxDef" 
        entityName="com.redxun.oa.info.entity.InsMsgboxBoxDef"
        formId="form1"/>
        
        <script type="text/javascript">
		mini.parse();
		var form = new mini.Form("#form1");
		var pkId = ${insMsgboxBoxDef.id};
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
		</script>
    </body>
</html>