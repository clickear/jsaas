<%-- 
    Document   : 选中树的节点确定文件夹
    Created on : 2015-11-6, 16:11:48
    Author     : 陈茂昌
--%>
<%@page import="com.redxun.sys.org.controller.SysOrgMgrController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html >
<html >
<head>
<title>日历测试</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>

</head>
<body>
	<div align="center">
	<form id="form1">
	<table>
	<tr>
	<td>计算方式</td>
	<td><input id="computeWay" name="computeWay" class="mini-combobox" allowInput="false" style="width:150px;" textField="text" valueField="id"  onvaluechanged="changeComputeWay()"
    data="[{ 'id':'time','text':'时长'},{ 'id':'date','text':'时间点'}]" value="time"  required="true" allowInput="true" showNullItem="true" nullItemText="请选择..."/></td>
	<tr>
	<td>选择开始日期</td>
	<td>
	 <input id="startDate" name="startDate" class="mini-datepicker" style="width:160px;" onvaluechanged="validateStart()" nullValue="null"
        format="yyyy-MM-dd H:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showTodayButton="false" showClearButton="false" required="true"/>
	</td>
	</tr>
	</tr>
	<tr class="date">
	<td>选择时长(分钟)</td>
	<td><input id="minuteNum" name="minuteNum"  class="mini-spinner"  minValue="1" maxValue="10000000"  required="true"/></td>
	</tr>
	<tr class="time">
	<td>选择结束日期</td>
	<td>
	 <input id="endDate" name="endDate" class="mini-datepicker" style="width:160px;" onvaluechanged="validateEnd()" nullValue="null"
        format="yyyy-MM-dd H:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showTodayButton="false" showClearButton="false" required="true"/>
	</td>
	</tr>
	</table>
	</form>
	</div>
    
    <div class="mini-toolbar dialog-footer" style="border:none; margin-top: 20px;">
        <a class="mini-button" onclick="doTest()">测试</a>
        <a class="mini-button" onclick="myclose()">取消</a>
    </div>
    <div class="resultArea"></div>

	<script type="text/javascript">
		mini.parse();
		
		$(function(){
			changeComputeWay()
		});
		
		function changeComputeWay(){
			var computeWayValue=mini.get("computeWay").getValue();
			if("time"==computeWayValue){
				$(".date").hide();
				$(".time").show();
			}else if("date"==computeWayValue){
				$(".time").hide();
				$(".date").show();
			}else{
				$(".date").hide();
				$(".time").hide();
			}
		}
		function doTest(){
			 var formData=$("#form1").serializeArray();
			 formData.push({
	            	name:'settingId',
	            	value:${param['settingId']}
	            });
			$.ajax({
				url:"${ctxPath}/oa/calendar/workCalendar/testCalendar.do",
				type:"post",
				data:formData,
				success:function(result){
					$('.resultArea').html(result.result)
				}
			});
		}
		//关闭页面
		function myclose(e){
			 CloseWindow("cancel");
		}
		
		function validateStart(){
			
		}
		
		function validateEnd(){
			
		}
	</script>

</body>
</html>	