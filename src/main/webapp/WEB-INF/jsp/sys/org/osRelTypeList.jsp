<%-- 
    Document   : 关系类型列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>关系类型列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<redxun:toolbar entityName="com.redxun.sys.org.entity.OsRelType" excludeButtons="copyAdd"/>
	<div class="mini-fit rx-grid-fit">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/sys/org/osRelType/listData.do?tenantId=${param['tenantId']}" idField="id" 
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="name" width="120" headerAlign="center" allowSort="true">关系名</div>
				<div field="key" width="120" headerAlign="center" allowSort="true">关系业务主键</div>
				<div field="relTypeName" width="120" headerAlign="center" allowSort="true">关系类型</div>
				<div field="party1" width="120" headerAlign="center" allowSort="true">关系当前方名称</div>
				<div field="party2" width="120" headerAlign="center" allowSort="true">关系关联方名称</div>
			</div>
		</div>
	</div>

	
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.org.entity.OsRelType" 
	tenantId="${param['tenantId']}"
	winHeight="450" winWidth="800" entityTitle="关系类型" baseUrl="sys/org/osRelType" />
	<script type="text/javascript">
        	//编辑
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var uid=record._uid;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>';
	            		if(record.relType!='GROUP-USER'){
	            			s=s+ ' <span class="icon-org-set" title="关系定义" onclick="relDef2(\'' + uid + '\')"></span>';
	            		}
	                    s=s + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    s=s + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
        	
        	
        	function relDef2(uid){
        		var row=grid.getRowByUID(uid);
        		top['index'].showTabFromPage({
        			tabId:'relInst_'+row.pkId,
        			title:row.name+'-关系定义',
        			url:'${ctxPath}/sys/org/osRelInst/treeLine.do?relTypeId='+row.pkId
        		});
        	}
      </script>
</body>
</html>