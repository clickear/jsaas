<%-- 
    Document   : [KdUserLevel]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[KdUserLevel]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${kdUserLevel.confId}" />
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="confId" class="mini-hidden" value="${kdUserLevel.confId}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>等级信息</caption>

					<tr>
						<th>
							<span class="starBox">
								起   始   值<span class="star">*</span> 
							</span>
						</th>
						<td><input name="startVal" value="${kdUserLevel.startVal}" class="mini-textbox" vtype="maxLength:10" style="width: 90%" required="true" emptyText="请输入起始值" /></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								结   束   值 <span class="star">*</span> 
							</span>
						</th>
						<td><input name="endVal" value="${kdUserLevel.endVal}" class="mini-textbox" vtype="maxLength:10" style="width: 90%" required="true" emptyText="请输入结束值" /></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								等级名称 <span class="star">*</span> 
							</span>
						</th>
						<td><input name="levelName" value="${kdUserLevel.levelName}" class="mini-textbox" vtype="maxLength:5" style="width: 90%" required="true" emptyText="请输入等级名称" /></td>
					</tr>
					
					<tr>
						<th>备　　注 </th>
						<td><textarea name="memo" class="mini-textarea" vtype="maxLength:500" style="width: 90%">${kdUserLevel.memo}</textarea></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="kms/core/kdUserLevel" entityName="com.redxun.kms.core.entity.KdUserLevel" />
	<script type="text/javascript">
		addBody();
	</script>
	
</body>
</html>