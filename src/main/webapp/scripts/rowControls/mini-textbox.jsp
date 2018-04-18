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
				<th>字符长度<span class="star">*</span> </th>
				<td>
					<input id="maxlen" name="maxlen" class="mini-spinner"  minValue="0" maxValue="4000" value="0" />
				</td>
				
				<th>是否必填</th>
				<td>
					<input class="mini-checkbox" name="required" trueValue="true" falseValue="false" value="false">
				</td>
			</tr>
			<tr>
				<th>格式化</th>
				<td colspan="3">
					<input class="mini-textbox" name="format" /> 
					<br/>数字格式如：,###.00,#.00,#.000,或日期yyyy-MM-dd或yyyy-MM-dd HH:mm:ss 
				</td>				
			</tr>
			<tr>
					<th>
							默认值
					</th>
					<td>
						<input class="mini-textbox" name="value" style="width:90%"/>
					</td>
				<th>校验规则</th>
				<td>
					<input id="vtype" name="vtype" class="mini-combobox"  textField="name" valueField="value" style="width:280px;"
    				url="${ctxPath}/sys/core/sysDic/getByDicKey.do?dicKey=_FieldValidateRule"  allowInput="false" showNullItem="true" nullItemText="请选择..."/>
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