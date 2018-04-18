
<%-- 
    Document   : [BPM_OPINION_TEMP]明细页
    Created on : 2017-09-26 18:02:24
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>暂存流程明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                	<caption>暂存流程基本信息</caption>
					<tr>
						<th>类型(inst,task)：</th>
						<td>
							${bpmOpinionTemp.type}
						</td>
					</tr>
					<tr>
						<th>任务或实例ID：</th>
						<td>
							${bpmOpinionTemp.instId}
						</td>
					</tr>
					<tr>
						<th>意见：</th>
						<td>
							${bpmOpinionTemp.opinion}
						</td>
					</tr>
					<tr>
						<th>附件：</th>
						<td>
							${bpmOpinionTemp.attachment}
						</td>
					</tr>
					<tr>
						<th>创建时间：</th>
						<td>
							${bpmOpinionTemp.createTime}
						</td>
					</tr>
					<tr>
						<th>创建人：</th>
						<td>
							${bpmOpinionTemp.createBy}
						</td>
					</tr>
					<tr>
						<th>更新时间：</th>
						<td>
							${bpmOpinionTemp.updateTime}
						</td>
					</tr>
					<tr>
						<th>更新人：</th>
						<td>
							${bpmOpinionTemp.updateBy}
						</td>
					</tr>
					<tr>
						<th>租户ID：</th>
						<td>
							${bpmOpinionTemp.tenantId}
						</td>
					</tr>
				</table>
             </div>
    	</div>
        <rx:detailScript baseUrl="bpm/core/bpmOpinionTemp" 
        entityName="com.redxun.bpm.core.entity.BpmOpinionTemp"
        formId="form1"/>
        
        <script type="text/javascript">
		mini.parse();
		var form = new mini.Form("#form1");
		var pkId = '${bpmOpinionTemp.id}';
		$(function(){
			$.ajax({
				type:'POST',
				url:"${ctxPath}/bpm/core/bpmOpinionTemp/getJson.do",
				data:{ids:pkId},
				success:function (json) {
					form.setData(json);
				}					
			});
		})
		</script>
    </body>
</html>