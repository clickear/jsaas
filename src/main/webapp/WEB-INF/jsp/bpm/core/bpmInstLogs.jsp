<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>流程实例的日志</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/bpm/core/bpmLog/listByInstId.do?instId=${param['instId']}" idField="logId" 
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]"
			pageSize="20" allowAlternating="true">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div field="logType" width="100" headerAlign="center" allowSort="true">日志分类</div>
				<div field="opType" width="100" headerAlign="center" allowSort="true">操作类型</div>
				<div field="opContent" width="220" headerAlign="center" allowSort="true">操作内容</div>
				<div field="creatBy" width="80" headerAlign="center" allowSort="true">操作人</div>
				<div field="createTime" width="80" headerAlign="center" allowSort="true" >操作时间</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var grid=mini.get('datagrid1');
		grid.load();
	</script>
	
	
</body>
</html>