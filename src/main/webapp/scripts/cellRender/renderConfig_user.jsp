<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>用户类型的渲染</title>
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
				<th>部门显示</th>
				<td>
					<div class="mini-radiobuttonlist" name="depShowType" repeatItems="3" repeatLayout="table" repeatDirection="vertical"
					  data="[{id:'none',text:'不显示'},{id:'showName',text:'显示部门名'},{id:'showFullName',text:'显示全部门名'}]"   textField="text" valueField="id" value="none" >
					</div> 
				</td>
				<th>
					用户额外文本	
				</th>
				<td>
					<div name="otherFields" class="mini-checkboxlist" repeatItems="3" repeatLayout="table"
			       		textField="name" 
						valueField="field"
						data="userFields"
				    </div>
				</td>
				
			</tr>
			<tr>
				<th>
					是否显示连接	
				</th>
				<td>
    				<div name="showLink" class="mini-checkbox" readOnly="false" text="显示连接"></div>
				</td>
				<th>
					<span class="starBox">
						显示文本字段<span class="star">*</span>
					</span>
				</th>
				<td>
					<input required="true"
						id="showField" 
						class="mini-combobox" 
						name="showField"  
						allowInput="false"
						textField="name" 
						valueField="field"
						data="userFields"
						style="width:90%;">
				</td>
				
			</tr>
			
		</table>
	</form>
	<script type="text/javascript">
	
		var userFields=[{field:'userId',name:'用户ID'},{field:'fullname',name:'姓名'},
		{field:'userNo',name:'工号'},{field:'mobile',name:'手机'},{field:'email',name:'邮箱'}];
		
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