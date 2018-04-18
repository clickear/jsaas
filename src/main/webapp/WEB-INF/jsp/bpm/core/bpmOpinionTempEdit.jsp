
<%-- 
    Document   : [BPM_OPINION_TEMP]编辑页
    Created on : 2017-09-26 18:02:24
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>暂存流程编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="bpmOpinionTemp.id" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${bpmOpinionTemp.id}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[BPM_OPINION_TEMP]基本信息</caption>
					<tr>
						<th>类型(inst,task)：</th>
						<td>
							
								<input name="type" value="${bpmOpinionTemp.type}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>任务或实例ID：</th>
						<td>
							
								<input name="instId" value="${bpmOpinionTemp.instId}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>意见：</th>
						<td>
							
								<input name="opinion" value="${bpmOpinionTemp.opinion}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>附件：</th>
						<td>
							
								<input name="attachment" value="${bpmOpinionTemp.attachment}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/core/bpmOpinionTemp"
		entityName="com.redxun.bpm.core.entity.BpmOpinionTemp" />
	
	<script type="text/javascript">
	mini.parse();
	
	
	

	</script>
</body>
</html>