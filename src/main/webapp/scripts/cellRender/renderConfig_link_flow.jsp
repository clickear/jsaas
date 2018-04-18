<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>关联流程类型的渲染</title>
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
					显示流程标题
				</th>
				<td>
    				<div name="showTitle" class="mini-checkbox" value="true" text="显示标题"></div>
				</td>
				<th>
					显示流程明细连接
				</th>
				<td>
    				<div name="showBpmInstLink" class="mini-checkbox" value="true" text="显示流程明细连接"></div>
				</td>
			</tr>
			<tr>
				<th>
					显示任务
				</th>
				<td>
    				<div name="showTask" class="mini-checkbox" value="true" text="显示任务"></div>
				</td>
				<th>
					显示待办人信息	
				</th>
				<td colspan="3">
    				<div name="showTaskHandler" class="mini-checkbox" value="true" text="显示待办人信息"></div>
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