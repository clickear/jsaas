<%-- 
    Document   : 车辆明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>车辆明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail column_1" cellpadding="0"
				cellspacing="1">
				<caption>车辆基本信息</caption>
				<tr>
					<th>车辆类别</th>
					<td>${oaCar.sysDicId}</td>
				</tr>
				<tr>
					<th>车辆名称</th>
					<td>${oaCar.name}</td>
				</tr>
				<tr>
					<th>车牌号</th>
					<td>${oaCar.carNo}</td>
				</tr>
				<tr>
					<th>行驶里程</th>
					<td>${oaCar.travelMiles}</td>
				</tr>
				<tr>
					<th>发动机号</th>
					<td>${oaCar.engineNum}</td>
				</tr>
				<tr>
					<th>车辆识别代号</th>
					<td>${oaCar.frameNo}</td>
				</tr>
				<tr>
					<th>品牌</th>
					<td>${oaCar.brand}</td>
				</tr>
				<tr>
					<th>型号</th>
					<td>${oaCar.model}</td>
				</tr>
				<tr>
					<th>车辆载重</th>
					<td>${oaCar.weight}</td>
				</tr>
				<tr>
					<th>车辆座位</th>
					<td>${oaCar.seats}</td>
				</tr>
				<tr>
					<th>购买日期</th>
					<td><fmt:formatDate value="${oaCar.buyDate}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>购买价格</th>
					<td>${oaCar.price}</td>
				</tr>
				<tr>
					<th>车辆状态</th>
					<td>${oaCar.status}</td>
				</tr>
				<tr>
					<th>年检情况</th>
					<td>${oaCar.annualInsp}</td>
				</tr>
				<tr>
					<th>保险情况</th>
					<td>${oaCar.insurance}</td>
				</tr>
				<tr>
					<th>保养维修情况</th>
					<td>${oaCar.maintens}</td>
				</tr>
				<tr>
					<th>备注信息</th>
					<td>${oaCar.memo}</td>
				</tr>
				<tr>
					<th>车辆照片</th>
					<td><img width="80" height="100" src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${oaCar.photoIds}" /></td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${oaCar.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${oaCar.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${oaCar.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${oaCar.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/res/oaCar"
		entityName="com.redxun.oa.res.entity.OaCar" formId="form1" />
</body>
</html>