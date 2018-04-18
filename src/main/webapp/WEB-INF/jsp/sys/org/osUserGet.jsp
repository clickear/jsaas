<%-- 
    Document   : 用户明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>用户明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
<%-- 	<rx:toolbar toolbarId="toolbar1" hideRecordNav="true"/> --%>
	<div class="heightBox"></div>
	<div id="form1" class="form-outer shadowBox90">
			<table style="width: 100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>基本信息</caption>
				<tr>
					<th>姓　　名  </th>
					<td>${osUser.fullname}</td>
					<th>编　　号 </th>
					<td>${osUser.userNo}</td>
				</tr>
				<tr>
					<th>入职时间 </th>
					<td>
						<fmt:formatDate value="${osUser.entryTime}" pattern="yyyy-MM-dd"/>	
					</td>
					<th>离职时间 </th>
					<td>
						<fmt:formatDate value="${osUser.quitTime}" pattern="yyyy-MM-dd"/>	
					</td>
				</tr>
				<tr>
					<th>状　　态 </th>
					<td>${osUser.status}</td>
					<th>来　　源</th>
					<td>${osUser.from}</td>
				</tr>
				<tr>
					<th>手　　机</th>
					<td>${osUser.mobile}</td>
					<th>邮　　件 </th>
					<td>${osUser.email}</td>
				</tr>
				<tr>
					<th>QQ 号</th>
					<td colspan="3">${osUser.qq}</td>
				</tr>
				<tr>
					<th>地　　址</th>
					<td colspan="3">${osUser.address}</td>
				</tr>
				<tr>
					<th>紧急联系人</th>
					<td>${osUser.urgent}</td>
					<th>紧急联系人手机 </th>
					<td>${osUser.urgentMobile}</td>
				</tr>
				<tr>
					<th>生　　日</th>
					<td>
						<fmt:formatDate value="${osUser.birthday}" pattern="yyyy-MM-dd"/>
					</td>
					<th>性　　别</th>
					<td>${osUser.sex}</td>
				</tr>
				<tr>
					<th>照　　片</th>
					<td colspan="3">
					<img src="${ctxPath}/sys/core/file/imageView.do?thumb=true&view=true&fileId=${osUser.photo}" class="view-img" id="${osUser.photo}"/>
					</td>
				</tr>
			</table>

		<c:if test="${fn:length(accountList)>0}">

				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>绑定的用户账号</caption>
					<c:forEach items="${accountList}" var="account">
					<tr>
						<th width="120">账  号  名</th>
						<td>${account.name}</td>
					</tr>
					</c:forEach>
				</table>

		</c:if>
		<div>
			<table class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创  建  人</th>
					<td><rxc:userLabel userId="${osUser.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${osUser.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更  新  人</th>
					<td><rxc:userLabel userId="${osUser.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${osUser.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="sys/org/osUser" formId="form1" />
	<script type="text/javascript">
		addBody();
       	$(function(){
       		$(".view-img").css('cursor','pointer');
       		$(".view-img").on('click',function(){
       			var fileId=$(this).attr('id');
       			if(fileId=='')return;
       			_ImageViewDlg(fileId);
       		});
       	});
     </script>
</body>
</html>