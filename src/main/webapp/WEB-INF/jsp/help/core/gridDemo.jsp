<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <%@include file="/commons/get.jsp"%>
</head>
</head>
<body>
	<table style="width:100%;">
		<tr>
			<td>
				<div  class="mini-datagrid"
	            		allowCellEdit="true" allowCellSelect="true" height="auto" 
	            		oncellvalidation="onCellValidation" idField="varId" multiSelect="true"
						idField="Id" showPager="false" style="width:100%;min-height:100px;">
						<div property="columns">
							<div type="indexcolumn" width="30"></div>
							<div type="checkcolumn" width="30"></div>
							<div field="name" name="name" width="80" headerAlign="center">变量名
								<input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
							</div>
							<div field="key" name="key" width="80" headerAlign="center">变量Key
								<input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
							</div>
							<div field="type" name="type" width="80" headerAlign="center">类型
								<input property="editor" class="mini-combobox" data="[{id:'String',text:'字符串'},{id:'Number',text:'数字'},{id:'Date',text:'日期'}]" style="width:100%" required="true"/>
							</div>
							<div field="defVal" name="defVal" width="80" headerAlign="center" >缺省值
								<input property="editor" class="mini-textbox" style="width:100%;"/>
							</div>
							<div field="express" name="express" width="120" headerAlign="center" >计算表达式
								<input property="editor" class="mini-textbox" style="width:100%;"/>
							</div>
							<div type="checkboxcolumn" field="isReq" name="isReq" trueValue="YES" falseValue="NO" width="60" headerAlign="center">是否必填</div>
							<div field="sn" name="sn" width="50" headerAlign="center" >序号
								<input property="editor" class="mini-spinner" style="width:100%" minValue="1" maxValue="200"/>
							</div>
						</div>
					</div>
			</td>
		</tr>
	</table>
</body>
</html>