<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun" %>
<!DOCTYPE html>
<html>
    <head>
        <title>任务转发</title>
      <%@include file="/commons/edit.jsp"%>
	</head>
<body>
	<div class="mini-toolbar" style="padding:2px;">
	    <table style="width:100%;">
	        <tr>
		        <td style="width:100%;">
		            <a class="mini-button" iconCls="icon-transfer" plain="true" onclick="transferTasks()">转发任务</a>
		            <a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow();">关闭</a>
		        </td>
	        </tr>
	     </table>
	</div>
	
	<div class="form-outer"  style="padding:6px;">
		<form id="transferForm">
			<table class="table-detail column_2_m" cellpadding="0" cellspacing="1" style="width:100%" >
				<tr>
					<th>转发的任务</th>
					<td>
						<input class="mini-textboxlist" id="taskIds" name="taskIds"  required="true" style="width:90%;" text="${taskNames}" value="${taskIds}" readOnly="true"/>
					</td>
				</tr>
				<tr>
					<th>
						转发人
					</th>
					<td>
						<input id="userId" name="userId" class="mini-buttonedit"  onbuttonclick="selectUser"  selectOnFocus="true" required="true"/>
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td>
						<textarea class="mini-textarea" name="remark" style="width:80%"></textarea>
					</td>
				</tr>
				<tr>
					<th>消息通知</th>
					<td>
						<input class="mini-checkboxlist" name="noticeTypes" data="[{id:'outMail',text:'外部邮件'},{id:'mail',text:'内部邮件'},{id:'innerMsg',text:'内部消息'},{id:'shortMsg',text:'短信'},{id:'weixin_transfer',text:'微信'}]"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		mini.parse();
		
		var form=new mini.Form('transferForm');
		
		function transferTasks(){
			form.validate();
			if(!form.isValid()){
				return;
			}
			_SubmitJson({
				method : 'POST',
				url:__rootPath+'/bpm/core/bpmTask/transferTask.do',
				data:form.getData(),
				success:function(text){
					CloseWindow('ok');	
				}
			});
		}
		
		function selectUser(){
			_UserDlg(true,function(user){
				var userEdit=mini.get('userId');
				userEdit.setText(user.fullname);
				userEdit.setValue(user.userId);
			});
		}
	</script>
</body>
</html>