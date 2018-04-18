<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>文本框属性</title>
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
						字段备注<span class="star">*</span>
					</span>	
				</th>
				<td>
					<input class="mini-textbox" name="fieldLabel" required="true" vtype="maxLength:100,chinese"  style="width:90%" emptytext="请输入字段备注" />
				</td>
				<th>
					空文本提示
				</th>
				<td>
					<input class="mini-textbox" name="emptytext" style="width:90%"/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="starBox">
						字段标识<span class="star">*</span>
					</span>
				</th>
				<td>
					<input 
						id="fieldName" 
						class="mini-combobox" 
						name="fieldName"  
						allowInput="true"
						textField="header" 
						valueField="field"
						style="width:90%;"
					>
				</td>
				<th>比较类型</th>
				<td>
					<input class="mini-combobox" id="opType" name="opType"  data="opData" textField="fieldOpLabel" valueField="fieldOp">
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
	
	var opData=[
				{'fieldOp':'EQ','fieldOpLabel':'等于'},
	            {'fieldOp':'LK','fieldOpLabel':'%模糊匹配%'},
	            {'fieldOp':'LEK','fieldOpLabel':'%左模糊匹配'},
	            {'fieldOp':'RIK','fieldOpLabel':'右模糊匹配%'}];
		mini.parse();
		
		var form=new mini.Form('miniForm');
		
		function setData(data,fieldDts){
			form.setData(data);
			mini.get('fieldName').setData(fieldDts);
			mini.get('opType').setText(data.fieldOpLabel);
		}
		
		function getData(){
			var formData=form.getData();
			formData.fieldOpLabel=mini.get('opType').getText();
			return formData;
		}
	</script>				
</body>
</html>