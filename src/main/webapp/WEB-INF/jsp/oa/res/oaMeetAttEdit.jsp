<%-- 
    Document   : [OaMeetAtt]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>编辑我的会议总结</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${oaMeetAtt.attId}" excludeButtons="save" hideRemove="true" hideRecordNav="true" >
	
		   <div class="self-toolbar">
	<a class="mini-button" iconCls="icon-save" plain="true" onclick="onOk">保存草稿</a>
	        <a class="mini-button" iconCls="icon-save" plain="true" onclick="submitAndSave">提交保存</a>
	   </div>
	</rx:toolbar>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="attId" class="mini-hidden"
					value="${oaMeetAtt.attId}" />
				<input name="meetId" class="mini-hidden"
					value="${oaMeetAtt.meetId}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>编写个人会议总结信息</caption>

					<tr>
						<th>会议名称<span class="star">*</span> 
						</th>
						<td>${oaMeetAtt.oaMeeting.name}

						</td>
					</tr>

					<tr>
						<th>用户名 </th>
						<td>${oaMeetAtt.fullName}

						</td>
					</tr>

					<tr>
						<th>会议总结 </th>
						<td>
						<ui:UEditor height="150" isMini="true"
								name="summary" id="summary">${oaMeetAtt.summary}</ui:UEditor>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/res/oaMeetAtt"
		entityName="com.redxun.oa.res.entity.OaMeetAtt" />
	<script type="text/javascript">
		mini.parse();
		//提交保存
		function submitAndSave(){
			 form.validate();
		     if (form.isValid() == false) {
		         return;
		     }
		     var formData=$("#form1").serializeArray();

		     _SubmitJson({
		     	url:'${ctxPath}/oa/res/oaMeetAtt/save.do?submitThis=YES',
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