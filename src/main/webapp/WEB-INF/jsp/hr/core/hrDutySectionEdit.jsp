<%-- 
    Document   : [HrDutySection]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDutySection]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${hrDutySection.sectionId}" />
	<div class="shadowBox90">
	
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="sectionId" class="mini-hidden" value="${hrDutySection.sectionId}" />
				<input name="isGroup" class="mini-hidden" value="NO" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>单班次基本信息</caption>
					<%-- <tr>
						<th>班次种类 <span class="star">*</span> ：
						</th>
						<td>
							<div name="isGroup" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="horizontal" textField="text" valueField="id" value="${hrDutySection.isGroup}" data="[{'id':'NO','text':'单班次'},{'id':'YES','text':'组合班次'}]" ></div>
						</td>
					</tr> --%>

					<tr>
						<th>
							 <span class="starBox">
								班次名称 <span class="star">*</span>
							</span>
						</th>
						<td>
							<input 
								name="sectionName"
								value="${hrDutySection.sectionName}" 
								class="mini-textbox"
								vtype="maxLength:8" 
								style="width: 90%" 
								required="true"
								emptyText="请输入班次名称" 
							/>
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="starBox">
								班次简称 <span class="star">*</span>
							</span> 
						</th>
						<td>
							<input 
								name="sectionShortName"
								value="${hrDutySection.sectionShortName}" 
								class="mini-textbox"
								vtype="maxLength:2" 
								style="width: 90%" 
								required="true"
								emptyText="请输入班次简称" 
							/>
						</td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								开始签到 <span class="star">*</span>
							</span>
						</th>
						<td><input name="startSignIn" value="${hrDutySection.startSignIn}"
							class="mini-textbox" vtype="maxLength:19" style="width: 90%"
							required="true" emptyText="请输入开始签到" />分钟
						
						
						<%-- <input name="startSignIn"
							value="${hrDutySection.startSignIn}" class="mini-textbox"
							vtype="maxLength:19" style="width: 90%" required="true"
							emptyText="请输入开始签到" /> --%></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								上班时间 <span class="star">*</span>
							</span>
						</th>
						<td><%-- <input name="dutyStartTime"
							value="${hrDutySection.dutyStartTime}" class="mini-textbox"
							vtype="maxLength:19" style="width: 90%" required="true"
							emptyText="请输入上班时间" /> --%>
							
							<input name="dutyStartTime" class="mini-timespinner"  format="HH:mm" style="width: 90%" required="true" value="${hrDutySection.dutyStartTime}" />
							</td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								签到结束时间 <span class="star">*</span>
							</span> 
						</th>
						<td><input name="endSignIn" value="${hrDutySection.endSignIn}"
							class="mini-textbox" vtype="maxLength:19" style="width: 90%"
							required="true" emptyText="请输入签到结束时间" />分钟
						
						<%-- <input name="endSignIn"
							value="${hrDutySection.endSignIn}" class="mini-textbox"
							vtype="maxLength:19" style="width: 90%" required="true"
							emptyText="请输入签到结束时间" /> --%></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								早退计时 <span class="star">*</span>
							</span>
						</th>
						<td><input name="earlyOffTime" value="${hrDutySection.earlyOffTime}"
							class="mini-textbox" vtype="maxLength:19" style="width: 90%"
							required="true" emptyText="请输入早退计时" />分钟
						
						<%-- <input name="earlyOffTime"
							value="${hrDutySection.earlyOffTime}" class="mini-textbox"
							vtype="maxLength:19" style="width: 90%" required="true"
							emptyText="请输入早退计时" /> --%></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								下班时间 <span class="star">*</span> 
							</span>
						</th>
						<td><%-- <input name="dutyEndTime"
							value="${hrDutySection.dutyEndTime}" class="mini-textbox"
							vtype="maxLength:19" style="width: 90%" required="true"
							emptyText="请输入下班时间" /> --%>
							<input name="dutyEndTime" class="mini-timespinner"  format="HH:mm" style="width: 90%" required="true" value="${hrDutySection.dutyEndTime}" />
							</td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								签退结束 <span class="star">*</span>
							</span> 
						</th>
						<td><input name="signOutTime" value="${hrDutySection.signOutTime}"
							class="mini-textbox" vtype="maxLength:19" style="width: 90%"
							required="true" emptyText="请输入签退结束" />分钟
						
						<%-- <input name="signOutTime"
							value="${hrDutySection.signOutTime}" class="mini-textbox"
							vtype="maxLength:19" style="width: 90%" required="true"
							emptyText="请输入签退结束" /> --%></td>
					</tr>

					<tr>
						<th>是否跨日 </th>
						<td><%-- <input name="isTwoDay" value="${hrDutySection.isTwoDay}"
							class="mini-textbox" vtype="maxLength:8" style="width: 90%" /> --%>
							<div name="isTwoDay" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="horizontal" textField="text" valueField="id" value="${hrDutySection.isTwoDay}" data="[{'id':'YES','text':'是'},{'id':'NO','text':'否'}]" ></div>    

						</td>
					</tr>
				</table>
			</div>
		</form>
	
	</div>
	<rx:formScript formId="form1" baseUrl="hr/core/hrDutySection"
		entityName="com.redxun.hr.core.entity.HrDutySection" />
		
	<script type="text/javascript">
		addBody();
	</script>
		
</body>
</html>