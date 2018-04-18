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
				<th>
					<span cass="starBox">
						字段备注<span class="star">*</span></th>
					</span>
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
					<span cass="starBox">
						字段标识<span class="star">*</span>
					</span>
				</th>
				<td>
					<input id="fieldName" class="mini-combobox" name="fieldName"  allowInput="true"
						textField="header" valueField="field"
						style="width:90%;">
				</td>
				<th>比较类型</th>
				<td>
					<input class="mini-combobox" id="opType" name="opType"  data="opData" textField="fieldOpLabel" valueField="fieldOp">
				</td>
			</tr>
			<tr>
				<th>日期格式</th>
				<td colspan="3">
					<input name="format" class="mini-combobox" style="width:80%"  required="true"
    				 data="[{id:'yyyy-MM-dd',text:'yyyy-MM-dd'},{id:'yyyy-MM-dd HH:mm:ss',text:'yyyy-MM-dd HH:mm:ss'}]"  allowInput="false" showNullItem="true" nullItemText="请选择..."/>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		var opData=[
					{'fieldOp':'EQ','fieldOpLabel':'等于'},
		            {'fieldOp':'LT','fieldOpLabel':'小于'},
		            {'fieldOp':'LET','fieldOpLabel':'小于等于'},
		            {'fieldOp':'GT','fieldOpLabel':'大于'},
		            {'fieldOp':'GET','fieldOpLabel':'大于等于'}];
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