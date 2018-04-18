<%-- 
    Document   : 项目动态
    Created on : 2015-12-21, 10:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>动态列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	
	<div class="mini-fit" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-datagrid" 
			style="width: 100%; height: 100%;" 
			allowResize="false" 
			idField="actionId" 
			multiSelect="true" 
			onrowdblclick="openDetail(e)"
			showColumnsMenu="true"  
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20"
			allowAlternating="true" 
		>
			<div property="columns">
				<div id="mycontent" field="content" width="360" headerAlign="center" >项目动态</div>
				<div field="createTime" width="120" headerAlign="center"   renderer="onRenderer">时间</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	mini.parse();
	var grid = mini.get("datagrid1");
		grid.setUrl("${ctxPath}/oa/pro/proWorkMat/listAct.do?projectId=${thisprojectId}");
	grid.load();
	//渲染时间
	function onRenderer(e) {
		var value = e.value;
		if (value)
			return mini.formatDate(value, 'yyyy-MM-dd HH:mm:ss');
		return "暂无";
	}

	
	
	
	//双击打开项目明细
	function openDetail(e){
		e.sender;
		var record=e.record;
		var pkId=record.pkId;
		detailMyRow(pkId);
	}
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.pro.entity.ProWorkMat" winHeight="450" winWidth="700"
		entityTitle="动态" baseUrl="oa/pro/proWorkMat" />
</body>
</html>