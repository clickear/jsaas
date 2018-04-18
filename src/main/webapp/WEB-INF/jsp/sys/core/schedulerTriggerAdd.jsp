<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/edit.jsp"%>
<script type="text/javascript" src="${ctxPath}/scripts/sys/scheduler/trigger.js"></script>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar" >
		    <table style="width:100%;">
		        <tr>
		            <td style="width:100%;" id="toolbarBody">
		               	<a class="mini-button" iconCls="icon-save" plain="true" onclick="save">保存</a>
		                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a>
		            </td>
		        </tr>
		    </table>
	</div>
	
	
	<form id="form1" method="post" >
		<table class="table-detail column_2" cellspacing="1" cellpadding="0">
			<tr>
				<th>计划名称</th>
				<td >
					<input required="true" name="name" emptyText="请输入名称" value="" class="mini-textbox"
							vtype="maxLength:50" style="width: 50%" />
					<input id="jobName" name="jobName" class="mini-hidden" value="${param.name}" />
					
				</td>
			</tr>
			<tr>
				<th colspan="2" style="text-align: left; padding-left: 5px;">执行计划的方式</th>
			</tr>
			<tr>
				<th>
					<input type="radio" value="1" name="rdoTimeType" />
					一次
				</th>
				<td align="left">
					开始
					<input  id="txtOnceDate" class="mini-datepicker" 
					format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
					
				</td>
			</tr>
			<tr>
				<th>
					<input type="radio" checked="checked" value="2" name="rdoTimeType" />
					每天
				</th>
				<td align="left">
					<select id="selEveryDay" class="inputText input-wh-1">
						<option value="1">1分钟</option>
						<option value="5">5分钟</option>
						<option value="10">10分钟</option>
						<option value="15">15分钟</option>
						<option value="30">30分钟</option>
						<option value="60">1小时</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<input type="radio" value="3" name="rdoTimeType" />
					每天
				</th>
				<td align="left">
					<select id="txtDayHour" class="inputText input-wh-1">
						<c:forEach begin="0" end="23" step="1" var="tmp">
							<option value="${tmp }">${tmp }时</option>
						</c:forEach>
					</select>
					<select id="txtDayMinute" class="inputText input-wh-1">
						<c:forEach begin="0" end="55" step="5" var="tmp">
							<option value="${tmp }">${tmp }分</option>
						</c:forEach>
						<option value="59">59分</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<input type="radio" value="4" name="rdoTimeType" />
					每周
				</th>
				<td align="left">
					<input type="checkbox" name="chkWeek" value="MON" />
					星期一
					<input type="checkbox" name="chkWeek" value="TUE" />
					星期二
					<input type="checkbox" name="chkWeek" value="WED" />
					星期三
					<input type="checkbox" name="chkWeek" value="THU" />
					星期四
					<input type="checkbox" name="chkWeek" value="FRI" />
					星期五
					<input type="checkbox" name="chkWeek" value="SAT" />
					星期六
					<input type="checkbox" name="chkWeek" value="SUN" />
					星期日
					<br />
					<select id="txtWeekHour" class="inputText input-wh-1">
						<c:forEach begin="0" end="23" step="1" var="tmp">
							<option value="${tmp }">${tmp }时</option>
						</c:forEach>
					</select>
					<select id="txtWeekMinute" class="inputText input-wh-1">
						<c:forEach begin="0" end="55" step="5" var="tmp">
							<option value="${tmp }">${tmp }分</option>
						</c:forEach>
						<option value="59">59分</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<input type="radio" value="5" name="rdoTimeType" />
					每月
				</th>
				<td align="left">
					<c:forEach begin="1" end="31" var="mon">
						<input type="checkbox" name="chkMon" value="${mon}" />${mon}
							</c:forEach>
					<input type="checkbox" name="chkMon" value="L" />
					最后一天
					<br />
					<select id="txtMonHour" class="inputText input-wh-1">
						<c:forEach begin="0" end="23" step="1" var="tmp">
							<option value="${tmp }">${tmp }时</option>
						</c:forEach>
					</select>
					<select id="txtMonMinute" class="inputText input-wh-1">
						<c:forEach begin="0" end="55" step="5" var="tmp">
							<option value="${tmp }">${tmp }分</option>
						</c:forEach>
						<option value="59">59分</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<input type="radio" value="6" name="rdoTimeType" />
					Cron表达式
				</th>
				<td align="left">
					<input type="text" id="txtCronExpression" name="txtCronExpression" class="inputText" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>