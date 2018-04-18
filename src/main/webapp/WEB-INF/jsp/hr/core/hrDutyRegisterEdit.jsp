<%-- 
    Document   : [HrDutyRegister]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDutyRegister]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${hrDutyRegister.registerId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="registerId" class="mini-hidden"
					value="${hrDutyRegister.registerId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[HrDutyRegister]基本信息</caption>

					<tr>
						<th>登记时间 <span class="star">*</span> ：
						</th>
						<td><input name="registerTime"
							value="${hrDutyRegister.registerTime}" class="mini-textbox"
							vtype="maxLength:19" style="width: 90%" required="true"
							emptyText="请输入登记时间" /></td>
					</tr>

					<tr>
						<th>登记标识 1=正常登记（上班，下班） 2＝迟到 3=早退 4＝休息 5＝旷工 6=放假 <span
							class="star">*</span> ：
						</th>
						<td><input name="regFlag" value="${hrDutyRegister.regFlag}"
							class="mini-textbox" vtype="maxLength:5" style="width: 90%"
							required="true"
							emptyText="请输入登记标识  1=正常登记（上班，下班）   2＝迟到    3=早退   4＝休息   5＝旷工   6=放假" />

						</td>
					</tr>

					<tr>
						<th>迟到或早退分钟 正常上班时为0 <span class="star">*</span> ：
						</th>
						<td><input name="regMins" value="${hrDutyRegister.regMins}"
							class="mini-textbox" vtype="maxLength:19" style="width: 90%"
							required="true" emptyText="请输入迟到或早退分钟 正常上班时为0" />

						</td>
					</tr>

					<tr>
						<th>迟到原因 ：</th>
						<td><input name="reason" value="${hrDutyRegister.reason}"
							class="mini-textbox" vtype="maxLength:128" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>周几 <span class="star">*</span> ：
						</th>
						<td><input name="dayofweek"
							value="${hrDutyRegister.dayofweek}" class="mini-textbox"
							vtype="maxLength:19" style="width: 90%" required="true"
							emptyText="请输入周几" /></td>
					</tr>

					<tr>
						<th>上下班标识 1=签到 2=签退 <span class="star">*</span> ：
						</th>
						<td><input name="inOffFlag"
							value="${hrDutyRegister.inOffFlag}" class="mini-textbox"
							vtype="maxLength:8" style="width: 90%" required="true"
							emptyText="请输入上下班标识 1=签到 2=签退" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="hr/core/hrDutyRegister"
		entityName="com.redxun.hr.core.entity.HrDutyRegister" />
</body>
</html>