<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>复选框</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div class="mini-toolbar">
		<table style="width:100%">
			<tr>
				<td>
					<a class="mini-button" iconCls="icon-save" onclick="CloseWindow('ok')">保存</a>
					<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
				</td>
			</tr>
		</table>
	</div>
	<form id="miniForm">
		<table class="table-detail column_2_m" cellspacing="1" cellpadding="1">
				<tr>
					<th>显示的值<span class="star">*</span></th>
					<td colspan="3">
						选中 <input id="trueVal" name="truevalue" class="mini-textbox"  value="是" required="true"  style="width:50px;" />
						不选中<input id="falseVal" name="falsevalue" class="mini-textbox"  value="否"  required="true" style="width:50px;" />
					</td>
				</tr>
				<tr>
					<th>
						默认值
					</th>
					<td colspan="3">
						<div name="checked" class="mini-checkbox" checked="true" readOnly="false" text="是否选中" ></div>
					</td>
				</tr>
		</table>
	</form>
	<script type="text/javascript">
		
		mini.parse();
		
		var form=new mini.Form('miniForm');
		
		function setData(data){
			form.setData(data);
		
		}
		
		function getData(){
			var formData=form.getData();
		
			return formData;
		}
		
	</script>				
</body>
</html>