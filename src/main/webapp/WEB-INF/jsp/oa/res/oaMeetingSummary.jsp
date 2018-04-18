<%-- 
    Document   : [OaMeeting]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>会议纪要</title>
<%@include file="/commons/edit.jsp"%>

</head>
<body>
		<div id="toolbar" class="mini-toolbar mini-toolbar-bottom">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;" id="toolbarBody"><a
					class="mini-button" iconCls="icon-save" plain="true"
					onclick="selfSaveData()">保存</a> <a class="mini-button"
					iconCls="icon-close" plain="true" onclick="onCancel">关闭</a></td>
			</tr>
		</table>
	</div>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="meetId" class="mini-hidden"
					value="${oaMeeting.meetId}" />
				<table class="table-detail column_2" cellspacing="1" cellpadding="0">
					<caption>会议申请基本信息</caption>

					<tr>
						<th>会议名称 <span class="star">*</span> 
						</th>
						<td colspan="3">${oaMeeting.name}</td>
					</tr>

					<tr>
						<th>会议地点 <span class="star">*</span> 
						</th>
						<td colspan="3">${roomName}
		   					${oaMeeting.location}
							</td>
					</tr>

					<tr>
					<th>开始时间</th>
					<td><fmt:formatDate value="${oaMeeting.start}" pattern="yyyy-MM-dd HH:mm" /></td>
					<th>结束时间</th>
					<td><fmt:formatDate value="${oaMeeting.end}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
					
					<tr>
						<th>会议参与人员</th>
						<td colspan="3">${fullName}</td>
					</tr>
					<tr>
						<th>会议纪要 </th>
						<td colspan="3"><ui:UEditor height="150" isMini="true"
								name="summary" id="summary">${oaMeeting.summary}</ui:UEditor></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/res/oaMeeting"
		entityName="com.redxun.oa.res.entity.OaMeeting" />
		
	<script type="text/javascript">
	mini.parse();
	$(function() {
		$('.upload-panel').each(function() {
			$(this).uploadPanel();
		});
	});
	
	
	
	 function selfSaveData(){
		 form.validate();
	     if (form.isValid() == false) {
	         return;
	     }
	     var formData=$("#form1").serializeArray();

	     _SubmitJson({
	     	url:'${ctxPath}/oa/res/oaMeeting/summaryUpdate.do?',
	     	method:'POST',
	     	data:formData,
	     	success:function(result){
	 				CloseWindow('ok');
	 				return;
	 		
	     	}
	     });
	}

	</script>
</body>
</html>