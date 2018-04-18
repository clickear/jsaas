<%-- 
    Document   : 请假加班外出出差编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>请假加班外出出差编辑</title>
<%@include file="/commons/edit.jsp"%>
<script type="text/javascript">
	var data2 = [ {
		id : '0',
		text : '加班'
	}, {
		id : '1',
		text : '请假'
	}, {
		id : '2',
		text : '外出'
	}, {
		id : '3',
		text : '出差'
	} ];
	var overwork = [ {
		id : '工作日加班',
		text : '工作日加班'
	}, {
		id : '双休日加班',
		text : '双休日加班'
	}, {
		id : '节假日加班',
		text : '节假日加班'
	} ];
	var forleave = [ {
		id : '病假',
		text : '病假'
	}, {
		id : '事假',
		text : '事假'
	}, {
		id : '年假',
		text : '年假'
	}, {
		id : '产假',
		text : '产假'
	}, {
		id : '婚假',
		text : '婚假'
	}, {
		id : '丧假',
		text : '丧假'
	}, {
		id : '其他',
		text : '其他'
	} ];
	var out = [ {
		id : '外出',
		text : '外出'
	} ];
	var outwork = [ {
		id : '出差',
		text : '出差'
	} ];
</script>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${hrErrandsRegister.erId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="erId" class="mini-hidden"
					value="${hrErrandsRegister.erId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>请假加班外出出差基本信息</caption>
					<tr>
						<th>标识 ：</th>
						<td>
							<%-- <input name="flag" value="${hrErrandsRegister.flag}"
							class="mini-textbox" vtype="maxLength:5" style="width: 90%" /> --%>
							<div name="flag" id="flag" class="mini-radiobuttonlist"
								repeatItems="1" repeatLayout="table" repeatDirection="vertical"
								textField="text" valueField="id"
								value="${hrErrandsRegister.flag}" data="data2"
								onvaluechanged="changeType"></div>
						</td>
					</tr>
					<tr>
						<th>类型 <span class="star">*</span> ：
						</th>
						<td>
							<%-- <input name="type" value="${hrErrandsRegister.type}"
							class="mini-textbox" vtype="maxLength:20" style="width: 90%"
							required="true" emptyText="请输入类型" /> --%> <input name="type"
							id="type" class="mini-combobox"
							style="width: 150px; color: #000000;" data="overwork"
							value="${hrErrandsRegister.type}" required="true"
							allowInput="false" />
						</td>
					</tr>
					<tr>
						<th>开始日期 <span class="star">*</span> ：
						</th>
						<td><input name="startTime"
							value="${hrErrandsRegister.startTime}" class="mini-datepicker"
							vtype="maxLength:19" style="width: 90%" required="true"
							format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm:ss" showTime="true"
							emptyText="请输入开始日期" /></td>
					</tr>
					<tr>
						<th>结束日期 <span class="star">*</span> ：
						</th>
						<td><input name="endTime"
							value="${hrErrandsRegister.endTime}" class="mini-datepicker"
							vtype="maxLength:19" style="width: 90%" required="true"
							format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm:ss" showTime="true"
							emptyText="请输入结束日期" /></td>
					</tr>
					<%-- <tr>
						<th>描述 <span class="star">*</span> ：
						</th>
						<td><textarea name="desc" value="${hrErrandsRegister.desc}"
								class="mini-textarea" vtype="maxLength:500" style="width: 90%"
								required="true" emptyText="请输入描述"></textarea></td>
					</tr> --%>

				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		/* 标识的改变联动改变类型  */
		function changeType(e) {
			var type = mini.getByName("type");
			if (this.getValue() == 0) {
				type.setData("overwork");
			} else if (this.getValue() == 1) {
				type.setData("forleave");
			} else if (this.getValue() == 2) {
				type.setData("out");
			} else {
				type.setData("outwork");
			}
		}
	</script>
	<rx:formScript formId="form1" baseUrl="hr/core/hrErrandsRegister"
		entityName="com.redxun.hr.core.entity.HrErrandsRegister" />
</body>
</html>