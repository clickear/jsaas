<%-- 
    Document   : [OsRelType]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[OsRelType]明细</title>
<%@include file="/commons/get.jsp"%>
<style>
body{
	background: #f7f7f7;
}

ul,li{padding: 0;margin: 0;list-style: none}
h1,h2,h3{font-weight: normal;margin: 0}

.form-title-running li{
	width: 16.66%;
}

.form-title li{
	width: 25%;
}
</style>
</head>
<body>
<%-- 	<rx:toolbar toolbarId="toolbar1" /> --%>

	<div class="form-title">
		<h1>更新信息</h1>
		<ul>
			<li>
				<h2>创  建  人：<rxc:userLabel userId="${osRelType.createBy}" /></h2>
			</li>
			<li>
				<h2>更  新  人：<rxc:userLabel userId="${osRelType.updateBy}" /></h2>
			</li>
			<li>
				<h2>创建时间：<fmt:formatDate value="${osRelType.createTime}" pattern="yyyy-MM-dd HH:mm" /></h2>
			</li>
			<li>
				<h2>更新时间：<fmt:formatDate value="${osRelType.updateTime}" pattern="yyyy-MM-dd HH:mm" /></h2>
			</li>
		</ul>
	</div>


	<div id="form1" class="form-outer">
		<div class="shadowBox">
			<table style="width: 100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>机构基本信息</caption>
				<tr>
					<th>关  系  名 </th>
					<td>${osRelType.name}</td>
			
					<th><span class="starBox">关系业务主键 <span class="star">*</span></span></th>
					<td>${osRelType.key}</td>
				</tr>
				<tr>
					<th>关系类型 </th>
					<td>${osRelType.relType}</td>
					<th>关系约束类型 </th>
					<td>${osRelType.constType}</td>
				</tr>
				<tr>
					<th>关系当前方名称</th>
					<td>${osRelType.party1}</td>

					<th>关系关联方名称</th>
					<td>${osRelType.party2}</td>
				</tr>
				<tr id="rowDim">
					<th>当前方维度</th>
					<td>
						<c:choose>
							<c:when test="${not empty osRelType.dim1}">
								${osRelType.dim1.name}
							</c:when>
							<c:otherwise>
								无
							</c:otherwise>
						</c:choose>
					</td>
					<th>关联方维度</th>
					<td>
						<c:choose>
							<c:when test="${not empty osRelType.dimId2}">
								${osRelType.dim2.name}
							</c:when>
							<c:otherwise>
								无
							</c:otherwise>
						</c:choose>	
					</td>
				</tr>
				<tr>
					<th>状　　态</th>
					<td>${osRelType.status}</td>
					<th><span class="starBox">是否是双向 <span class="star">*</span></span> 
					</th>
					<td>${osRelType.isTwoWay}</td>
				</tr>
				<tr>
					<th><span class="starBox">是否默认 <span class="star">*</span></span> 
					</th>
					<td>${osRelType.isDefault}</td>
					<th><span class="starBox">是否系统预设 <span class="star">*</span></span> 
					</th>
					<td>${osRelType.isSystem}</td>
				</tr>
				<tr>
					<th>关系备注 </th>
					<td colspan="3">${osRelType.memo}</td>
				</tr>
			</table>
		</div>
		<%-- <div>
			<table class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${osRelType.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${osRelType.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${osRelType.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${osRelType.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div> --%>
	</div>
	<rx:detailScript baseUrl="sys/org/osRelType" formId="form1" />
	<script type="text/javascript">
		$(function(){
			var relType='${osRelType.relType}';
			if(relType=='')return;
			if(relType=='USER-USER'){
				$("#rowDim").css("display","none");
			}else{
				$("#rowDim").css("display","");
			}
		});
	</script>
</body>
</html>