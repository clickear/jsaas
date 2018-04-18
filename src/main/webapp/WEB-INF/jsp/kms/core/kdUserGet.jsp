<%-- 
    Document   : [KdUser]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>[KdUser]明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
<%-- 	<rx:toolbar toolbarId="toolbar1" /> --%>
	<div style="height: 40px;"></div>
	<div id="form1" class="form-outer shadowBox90">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>专家基本信息</caption>
				<tr>
					<th width="15%">积　　分</th>
					<td>${kdUser.point}</td>
				</tr>
				<tr>
					<th>等　　级</th>
					<td>${kdUser.grade}</td>
				</tr>
				<tr>
					<th>用户类型</th>
					<td>${kdUser.userType}</td>
				</tr>
				<tr>
					<th>姓　　名</th>
					<td>${kdUser.fullname}</td>
				</tr>
				<tr>
					<th>序　　号</th>
					<td>${kdUser.sn}</td>
				</tr>
				<tr>
					<th>知识领域</th>
					<td>${kdUser.knSysTree.name}</td>
				</tr>
				<tr>
					<th>爱问领域</th>
					<td>${kdUser.reqSysTree.name}</td>
				</tr>
				<tr>
					<th>个性签名</th>
					<td>${kdUser.sign}</td>
				</tr>
				<tr>
					<th>个人简介</th>
					<td>${kdUser.profile}</td>
				</tr>
				<tr>
					<th>性　　别</th>
					<td>${kdUser.sex}</td>
				</tr>
				<tr>
					<th>办公电话</th>
					<td>${kdUser.officePhone}</td>
				</tr>
				<tr>
					<th>手机号码</th>
					<td>${kdUser.mobile}</td>
				</tr>
				<tr>
					<th>电子邮箱</th>
					<td>${kdUser.email}</td>
				</tr>
				<tr>
					<th>头　　像</th>
					<td><img width="80" height="100" src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${kdUser.headId}" /></td>
				</tr>
			</table>
		</div>
	</div>


	<rx:detailScript baseUrl="kms/core/kdUser" entityName="com.redxun.kms.core.entity.KdUser" formId="form1" />
	<script type="text/javascript">
		addBody();
	</script>
	
</body>
</html>