<%-- 
    Document   : [HrSectionConfig]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[HrSectionConfig]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${hrSectionConfig.configId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="configId" class="mini-hidden"
					value="${hrSectionConfig.configId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[HrSectionConfig]基本信息</caption>

					<tr>
						<th>上班开始签到时间 ：</th>
						<td><input name="inStartTime"
							value="${hrSectionConfig.inStartTime}" class="mini-textbox"
							vtype="maxLength:19" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>上班结束签到时间 ：</th>
						<td><input name="inEndTime"
							value="${hrSectionConfig.inEndTime}" class="mini-textbox"
							vtype="maxLength:19" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>下班开始签到时间 ：</th>
						<td><input name="outStartTime"
							value="${hrSectionConfig.outStartTime}" class="mini-textbox"
							vtype="maxLength:19" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>下班结束签到时间 ：</th>
						<td><input name="outEndTime"
							value="${hrSectionConfig.outEndTime}" class="mini-textbox"
							vtype="maxLength:19" style="width: 90%" />

						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="hr/core/hrSectionConfig"
		entityName="com.redxun.hr.core.entity.HrSectionConfig" />
</body>
</html>