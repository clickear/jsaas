<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>用户组维度编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div style="width:100%;">
        <div class="mini-toolbar" style="padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="mini-button" iconCls="icon-save" plain="true" onclick="SaveData()">保存</a>
                        <a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>流程模型基本信息</caption>
					<tr>
						<th>名称</th>
						<td>
							<input class="mini-textbox" name="name" required="true" emptyText="名称为必填"/>
						</td>
					</tr>
					<tr>
						<th>描述</th>
						<td>
							<input class="mini-textarea" name="descp" required="true" emptyText="描述为必填"/>
						</td>
					</tr>
				</table>
		</form>
	</div>
	<script type="text/javascript">
		var form=new mini.Form('form1');
		function SaveData(){
			form.validate();
	        if (form.isValid() == false) {
	            return;
	        }
	        
	        var formData=$("#form1").serializeArray();
	        
	        _SubmitJson({
	        	url:__rootPath+"/bpm/def/repModel/newModel.do",
	        	method:'POST',
	        	data:formData,
	        	success:function(result){
	        		alert("success");
	        		CloseWindow();
	        	}
	        });
		}
	</script>

</body>
</html>