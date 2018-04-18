<%-- 
    Document   : [BpmFormInst]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[BpmFormInst]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<redxun:toolbar entityName="com.redxun.bpm.bm.entity.BpmFormInst" />

	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/bpm/bm/bpmFormInst/listData.do" idField="formInstId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="subject" width="120" headerAlign="center" allowSort="true">实例标题</div>
				<div field="instId" width="120" headerAlign="center" allowSort="true">流程实例ID</div>
				<div field="actInstId" width="120" headerAlign="center" allowSort="true">ACT实例ID</div>
				<div field="actDefId" width="120" headerAlign="center" allowSort="true">ACT定义ID</div>
				<div field="defId" width="120" headerAlign="center" allowSort="true">流程定义ID</div>
				<div field="solId" width="120" headerAlign="center" allowSort="true">解决方案ID</div>
				<div field="fmId" width="120" headerAlign="center" allowSort="true">数据模型ID</div>
				<div field="fmViewId" width="120" headerAlign="center" allowSort="true">表单视图ID</div>
				<div field="status" width="120" headerAlign="center" allowSort="true">状态</div>
				<div field="jsonData" width="120" headerAlign="center" allowSort="true">数据JSON</div>
				<div field="isPersist" width="120" headerAlign="center" allowSort="true">是否持久化</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var uid = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + uid + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
	            return s;
	        }
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.bm.entity.BpmFormInst" winHeight="450" winWidth="700" entityTitle="[BpmFormInst]" baseUrl="bpm/bm/bpmFormInst" />
</body>
</html>