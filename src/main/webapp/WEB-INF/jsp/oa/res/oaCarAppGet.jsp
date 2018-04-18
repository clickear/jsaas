<%-- 
    Document   : 车辆申请明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>车辆申请明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div>
			<table style="width: 100%" class="table-detail column_2" cellpadding="0"
				cellspacing="1">
				<caption>车辆申请基本信息</caption>
				<tr>
					<th>汽车类别</th>
					<td>${oaCarApp.carCat}</td>
				</tr>
				<tr>
					<th>车辆名称</th>
					<td>${oaCarApp.carName}</td>
				</tr>
				<tr>
					<th>起始时间</th>
					<td><fmt:formatDate value="${oaCarApp.startTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>终止时间</th>
					<td><fmt:formatDate value="${oaCarApp.endTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>驾驶员</th>
					<td>${oaCarApp.driver}</td>
				</tr>
				<tr>
					<th>行程类别</th>
					<td>${oaCarApp.category}</td>
				</tr>
				<tr>
					<th>目的地点</th>
					<td>${oaCarApp.destLoc}</td>
				</tr>
				<tr>
					<th>行驶里程</th>
					<td>${oaCarApp.travMiles}</td>
				</tr>
				<tr>
					<th>使用人员</th>
					<td>${oaCarApp.useMans}</td>
				</tr>
				<tr>
					<th>使用说明</th>
					<td>${oaCarApp.memo}</td>
				</tr>
				<tr><th>审批历史</th>
					<td><a href="#" onclick="showCheckDetail('${oaCarApp.bpmInst}')">审批明细</a>
					</td></tr>
			</table>
		</div>
		<div>
			<table class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${oaCarApp.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${oaCarApp.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${oaCarApp.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${oaCarApp.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/res/oaCarApp"
		entityName="com.redxun.oa.res.entity.OaCarApp" formId="form1" />
				<script type="text/javascript">
				function showCheckDetail(bpmInst){
			_OpenWindow({
       			title:'审批明细',
       			width:800,
       			height:480,
       			url:__rootPath+'/bpm/core/bpmInst/get.do?actInstId='+bpmInst
       		})
		}
	</script>
</body>
</html>