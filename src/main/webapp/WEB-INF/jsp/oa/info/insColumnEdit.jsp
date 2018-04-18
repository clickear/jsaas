<%-- 
    Document   : 栏目编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>栏目编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${insColumn.colId}" />
	<div id="p1" class="form-outer2">
		<form id="form1" method="post">
			<div>
				<input id="pkId" name="colId" class="mini-hidden" value="${insColumn.colId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>栏目基本信息</caption>
					<tr>
						<th>栏目名称 <span class="star">*</span> ：
						</th>
						<td><input name="name" value="${insColumn.name}" class="mini-textbox" vtype="maxLength:80" required="true" emptyText="请输入栏目名称" style="width: 80%" /></td>
						<th>栏目Key <span class="star">*</span> ：
						</th>
						<td><input name="key" value="${insColumn.key}" class="mini-textbox" vtype="maxLength:50" required="true" emptyText="请输入栏目Key" style="width: 80%" /></td>
					</tr>
					<%-- <tr>
						<th>是否为公共 <span class="star">*</span> ：
						</th>
						<td><ui:radioBoolean name="isPublic" value="${insColumn.isPublic}" required="true" emptyText="请填写是否公共" /></td>
						<th>是否允许评论 ：</th>
						<td><ui:radioBoolean name="allowCmt" value="${insColumn.allowCmt}" required="true" /></td>
					</tr> --%>
					<tr>
						<th>是否启用 <span class="star">*</span> ：
						</th>
						<td><ui:radioStatus name="enabled" value="${insColumn.enabled}" emptyText="请输入是否启用" required="true" /></td>
						<th>是否允许关闭 ：</th>
						<td><ui:radioBoolean name="allowClose" value="${insColumn.allowClose}" required="true" /></td>
					</tr>
					<tr>
						<th>信息栏目类型 ：</th>
						<td><input name="colType" class="mini-combobox" style="width: 150px;" value="${typeId}" text="${typeName}" textField="name" valueField="typeId" emptyText="请选择..." url="${ctxPath}/oa/info/insColType/getAll.do" required="true" allowInput="true" showNullItem="true" nullItemText="请选择..." />
						<a class="mini-button" iconCls="icon-add" onclick="addColType">增加类型</a>
						</td>
						<th>每页记录数 ：</th>
						<td><input name="numsOfPage" value="${insColumn.numsOfPage}" class="mini-spinner" minValue="1" maxValue="1000" vtype="maxLength:10" /></td>
					</tr>
<%-- 					<tr id="comment">
						<th>是否允许评论 ：</th>
						<td><ui:radioBoolean name="allowCmt" value="${insColumn.allowCmt}" required="true" /></td>
					</tr> --%>
					<%-- <tr>
						<th>栏目的展示方式</th>
						<td ><ui:miniCombo value="${insColumn.showType}" name="showType" data="[{id:'SCROLL-TEXT',text:'滚动文字'},{id:'LIST',text:'文字列表'},{id:'ROTATION-IMG',text:'轮换图片'}]" /></td>
						<th>是否长期 ：</th>
						<td><ui:radioBoolean name="isLongValid" id="timeValid" value="${insColumn.isLongValid}" required="true" onValueChanged="change"/></td>
					</tr> --%>
					<%-- <tr id="shortValid">
						<th>有效开始时间 ：</th>
						<td><input name="startTime" name="startTime" value="${insColumn.startTime}" class="mini-datepicker" showTime="true" style="width: 80%" format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm:ss" vtype="maxLength:19" /></td>
						<th>有效结束时间 ：</th>
						<td><input id="endTime" name="endTime" value="${insColumn.endTime}" class="mini-datepicker" showTime="true" style="width: 80%" format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm:ss" vtype="maxLength:19" /></td>
					</tr> --%>
				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		function addColType(){
			_OpenWindow({
				url : "${ctxPath}/oa/info/insColType/edit.do?",
				title : "新增栏目类型",
				width : 800,
				height : 400,
				ondestroy: function(action) {
		               if (action == 'ok') {
		            	   location.reload();
		               }
		          }
			});
		}
/* 		function onChange(e) {
			if ("新闻、公告" == e.sender.text) {
				$("#comment").show();
			}
			console.log(e.value);
			//console.log($("#endTime").find("input").val("2026-01-30 18:11:35"));
		}
		
		$(function() {
			if('${typeName}'!='新闻、公告'){
				$("#comment").hide();
			}
		}); */
	</script>
	<rx:formScript formId="form1" baseUrl="oa/info/insColumn" entityName="com.redxun.oa.info.entity.InsColumn" />
</body>
</html>