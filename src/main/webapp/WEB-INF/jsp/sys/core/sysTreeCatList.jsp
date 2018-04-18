<%-- 
    Document   : 系统树分类列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>系统树分类列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<redxun:toolbar entityName="com.redxun.sys.core.entity.SysTreeCat" />
	<div class="mini-fit rx-grid-fit" style="height: 100px;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/sys/core/sysTreeCat/listData.do" idField="keyVar" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="key" width="120" headerAlign="center" allowSort="true">分类Key</div>
				<div field="name" width="120" headerAlign="center" allowSort="true">分类名称</div>
				<div field="sn" width="120" headerAlign="center" allowSort="true">序号</div>
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
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.core.entity.SysTreeCat" winHeight="320" winWidth="650" entityTitle="系统树分类" baseUrl="sys/core/sysTreeCat" />
</body>
</html>