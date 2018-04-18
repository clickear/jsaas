<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>重设密码</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div style="height: 1px"></div>
	<div class="shadowBox90" style="margin: 40px auto;">
	    <form id="resetForm"  class="form-outer">
			<input type="hidden" name="accountId" value="${param['accountId']}"/>
			<table id="accountTable" class="table-detail column_1" cellpadding="0" cellspacing="1">
				<caption>重设密码</caption>
				<tr>
					<th>
						<span class="starBox">
							密　　码<span class="star">*</span>
						</span>
					</th>
					<td>
						<input class="mini-password" name="password" id="password" required="true" style="width:60%"/>
					</td>
				</tr>
				<tr>
					<th>
						<span class="starBox">
							确认密码<span class="star">*</span>
						</span>
					</th>
					<td>
						<input class="mini-password" name="rePassword" id="rePassword" required="true" onvalidation="onValidateRepassword" style="width:60%"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="toolbar1" class="mini-toolbar topBtn" style="text-align: center;">
		<a class="mini-button" iconCls="icon-ok"  onclick="onOk()">确定</a>					
		<a class="mini-button" iconCls="icon-cancel"  onclick="onCancel()">取消</a>
	</div>
	
	
		 <script type="text/javascript">
		 	addBody();
		 	mini.parse();
		 	var form=new mini.Form('resetForm');
		 	//OK
		 	function onOk(){
		 		form.validate();
		        if (form.isValid() == false) {
		            return;
		        }
		        
		        var formData=$("#resetForm").serializeArray();
		       
		        _SubmitJson({
		        	url:"${ctxPath}/sys/core/sysAccount/modifyPassword.do",
		        	method:'POST',
		        	data:formData,
		        	success:function(result){
		        		CloseWindow('ok');		
		        	}
		        });
		 	}
		 	//Cancel
		 	function onCancel(){
		 		//$("#resetForm")[0].reset();
		 		CloseWindow('cancel');
		 	}
		 	
		 	function onValidateRepassword(e) {
	    		if (e.isValid) {
	    			var pwd=mini.get('password').getValue();
	    			var rePassword=mini.get('rePassword').getValue();
	    			if (pwd!=rePassword) {
	    				e.errorText = "两次密码不一致!";
	    				e.isValid = false;
	    			}
	    		}
	    	}
		 </script>
</body>
</html>