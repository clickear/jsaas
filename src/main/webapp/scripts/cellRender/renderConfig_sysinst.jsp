<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>机构类型的渲染</title>
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
				<th>
					<span class="starBox">
						是否显示连接<span class="star">*</span>
					</span>	
				</th>
				<td>
    				<div name="showLink" class="mini-checkbox" readOnly="false" text="显示连接"></div>
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
			return formData;
		}
	</script>				
</body>
</html>