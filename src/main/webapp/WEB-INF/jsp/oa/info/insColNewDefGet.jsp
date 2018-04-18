
<%-- 
    Document   : [ins_col_new_def]明细页
    Created on : 2017-08-25 10:08:04
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[ins_col_new_def]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                	<caption>[ins_col_new_def]基本信息</caption>
					<tr>
						<th>COL_ID_：</th>
						<td>
							${insColNewDef.colId}
						</td>
					</tr>
					<tr>
						<th>NEW_ID_：</th>
						<td>
							${insColNewDef.newId}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="oa/info/insColNewDef" 
        entityName="com.redxun.oa.info.entity.InsColNewDef"
        formId="form1"/>
        
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
		</script>
    </body>
</html>