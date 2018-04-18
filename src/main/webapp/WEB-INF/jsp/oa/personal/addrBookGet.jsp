<%-- 
    Document   : 联系人明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>联系人明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1">
		<div class="self-toolbar">
			<a class="mini-button" plain="true" iconCls="icon-edit" onclick="edit">编辑</a>
		</div>
	</rx:toolbar>
	<div id="form1" class="form-outer shadowBox90">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail column_1" cellpadding="0" cellspacing="1">
				<caption>联系人基本信息</caption>
				<tr>
					<th style="width: 10%; vertical-align: middle;">姓　　名</th>
					<td>${addrBook.name}</td>
					<td rowspan="3" width="90" height="90"><img width="90" height="90" src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${addrBook.photoId}" /></td>
				</tr>
				<tr>
					<th style="width: 10%; vertical-align: middle;">生　　日</th>
					<td><fmt:formatDate value="${addrBook.birthday}" pattern="yyyy-MM-dd" /></td>
				</tr>

				<tr>
					<th style="width: 10%; vertical-align: middle;">所属分组</th>
					<td>${groupNames}</td>
				</tr>

				<tr>
					<th style="width: 10%;">工作单位</th>
					<td colspan="2">${addrBook.company}&nbsp;${addrBook.dep}&nbsp;${addrBook.pos}</td>
				</tr>

				<c:forEach items="${workunits}" var="workunit">
					<tr>
						<th style="width: 10%;">其他工作单位 </th>
						<td colspan="2">${workunit.contact}&nbsp;${workunit.ext1}&nbsp;${workunit.ext2}</td>
					</tr>
				</c:forEach>


				<tr>
					<th style="width: 10%;">地　　址</th>
					<td colspan="2">${addrBook.country}&nbsp;${addrBook.sate}&nbsp;${addrBook.city}&nbsp;${addrBook.street}&nbsp;${addrBook.zip}</td>
				</tr>

				<c:forEach items="${addresses}" var="address">
					<tr>
						<th style="width: 10%;">其他地址 </th>
						<td colspan="2">${address.contact}&nbsp;${address.ext1}&nbsp;${address.ext2}&nbsp;${address.ext3}&nbsp;${address.ext4}</td>
					</tr>
				</c:forEach>

				<tr>
					<th style="width: 10%;">主  邮  箱</th>
					<td colspan="2">${addrBook.mail}</td>
				</tr>

				<c:forEach items="${mails}" var="mail">
					<tr>
						<th style="width: 10%;">其他邮箱 </th>
						<td colspan="2">${mail.contact}</td>
					</tr>
				</c:forEach>

				<tr>
					<th style="width: 10%;">主  手  机</th>
					<td colspan="2">${addrBook.mobile}</td>
				</tr>

				<c:forEach items="${mobiles}" var="mobile">
					<tr>
						<th style="width: 10%;">其他手机 </th>
						<td colspan="2">${mobile.contact}</td>
					</tr>
				</c:forEach>

				<tr>
					<th style="width: 10%;">主  电   话</th>
					<td colspan="2">${addrBook.phone}</td>
				</tr>

				<c:forEach items="${phones}" var="phone">
					<tr>
						<th style="width: 10%;">其他电话 </th>
						<td colspan="2">${phone.contact}</td>
					</tr>
				</c:forEach>


				<tr>
					<th style="width: 10%;">主微信号</th>
					<td colspan="2">${addrBook.weixin}</td>
				</tr>

				<c:forEach items="${weixins}" var="weixin">
					<tr>
						<th style="width: 10%;">其他微信号 </th>
						<td colspan="2">${weixin.contact}</td>
					</tr>
				</c:forEach>

				<tr>
					<th style="width: 10%;">QQ</th>
					<td colspan="2">${addrBook.qq}</td>
				</tr>

				<c:forEach items="${qqs}" var="qq">
					<tr>
						<th style="width: 10%;">其他QQ号 </th>
						<td colspan="2">${qq.contact}</td>
					</tr>
				</c:forEach>


			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/personal/addrBook" formId="form1" entityName="com.redxun.oa.personal.entity.AddrBook" />
	<script type="text/javascript">
		var pkId='${addrBook.addrId}';
		/*编辑联系人*/
		function edit(){
			 window.location.href=__rootPath+'/oa/personal/addrBook/edit.do?pkId='+pkId;
		}
	</script>
</body>
</html>