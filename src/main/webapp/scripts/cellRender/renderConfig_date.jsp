<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>日期类型的渲染</title>
<%@include file="/commons/edit.jsp"%>

</head>
<body>
	<div class="mini-toolbar">
		<a class="mini-button" iconCls="icon-save" onclick="CloseWindow('ok')">保存</a>
		<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
	</div>
	<form id="miniForm">
		<table class="table-detail column_2_m" cellspacing="1" cellpadding="1">
			<tr>
				<th>格式化</th>
				<td>
					<input name="format" class="mini-combobox" style="width:80%"  required="true"
    				 data="[{id:'yyyy-MM-dd',text:'yyyy-MM-dd'},{id:'yyyy-MM-dd HH:mm:ss',text:'yyyy-MM-dd HH:mm:ss'},{id:'yyyy-MM',text:'yyyy-MM'},{id:'HH:mm',text:'HH:mm'},{id:'HH:mm:ss',text:'HH:mm:ss'}]"  allowInput="false" showNullItem="true" nullItemText="请选择..."/>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
	

		mini.parse();
		
		var form=new mini.Form('miniForm');
		
		function setData(data,fieldDts){
			form.setData(data);
		}
		
		function getData(){
			var formData=form.getData();
			formData.dataType="date";
			return formData;
		}
	</script>				
</body>
</html>