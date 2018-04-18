<%-- 
    Document   : [BpmFormAtt]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[BpmFormAtt]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<redxun:toolbar entityName="com.redxun.bpm.bm.entity.BpmFormAtt" />

	<div class="mini-fit" style="height: 100px;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/bpm/bm/bpmFormAtt/listData.do" idField="attId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="fmId" width="120" headerAlign="center" allowSort="true">所属模型ID</div>
				<div field="title" width="120" headerAlign="center" allowSort="true">属性标签</div>
				<div field="key" width="120" headerAlign="center" allowSort="true">属性标识</div>
				<div field="dataType" width="120" headerAlign="center" allowSort="true">属性数据类型</div>
				<div field="type" width="120" headerAlign="center" allowSort="true">属性类型</div>
				<div field="defVal" width="120" headerAlign="center" allowSort="true">默认值</div>
				<div field="required" width="120" headerAlign="center" allowSort="true">是否必须</div>
				<div field="len" width="120" headerAlign="center" allowSort="true">长度</div>
				<div field="prec" width="120" headerAlign="center" allowSort="true">精度(小数位)</div>
				<div field="sn" width="120" headerAlign="center" allowSort="true">序号</div>
				<div field="groupTitle" width="120" headerAlign="center" allowSort="true">分组标题</div>
				<div field="remark" width="120" headerAlign="center" allowSort="true">属性帮助描述</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
        	//编辑
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
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.bm.entity.BpmFormAtt" winHeight="450" winWidth="700" entityTitle="[BpmFormAtt]" baseUrl="bpm/bm/bpmFormAtt" />
</body>
</html>