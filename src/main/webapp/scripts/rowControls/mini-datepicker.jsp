<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>日期选择</title>
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
				<th>是否必填</th>
				<td>
					<input class="mini-checkbox" name="required" trueValue="true" falseValue="false" value="false">
				</td>
				<th>日期格式</th>
				<td colspan="3">
					<input name="format" class="mini-combobox" style="width:80%"  required="true"
    				 data="[{id:'yyyy-MM-dd',text:'yyyy-MM-dd'},{id:'yyyy-MM-dd HH:mm:ss',text:'yyyy-MM-dd HH:mm:ss'}]"  allowInput="false" showNullItem="true" nullItemText="请选择..."/>
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