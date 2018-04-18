<%-- 
    Document   : 系统流水号列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<title>系统流水号列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<redxun:toolbar entityName="com.redxun.sys.core.entity.SysSeqId" />

	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width:100%; height:100%;" allowResize="false" url="${ctxPath}/sys/core/sysSeqId/listData.do?tenantId=${tenantId}" idField="seqId"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="name" width="120" headerAlign="center" allowSort="true">名称</div>
				<div field="alias" width="120" headerAlign="center" allowSort="true">别名</div>
				<div field="rule" width="160" headerAlign="center" allowSort="true">规则</div>
				<div field="genType" width="120" headerAlign="center" allowSort="true">生成方式</div>
				<div field="len" width="120" headerAlign="center" allowSort="true">流水号长度</div>
			</div>
		</div>
	</div>
	
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.core.entity.SysSeqId" 
	winHeight="580" winWidth="800" entityTitle="系统流水号" baseUrl="sys/core/sysSeqId" />
	<script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
        	
            grid.on("drawcell", function (e) {
                var record = e.record,
    	        field = e.field,
    	        value = e.value;
                var genType = record.genType;
                if(field=='genType'){
                	if(genType=='DAY'){
                		e.cellHtml='每天';
                	}else if(genType=='WEEK'){
                		e.cellHtml='每周';
                	}else if(genType=='MONTH'){
                		e.cellHtml='每月';
                	}else if(genType=='YEAR'){
                		e.cellHtml='每年';
                	}else if(genType=='AUTO'){
                		e.cellHtml='自动增长';
                	}
                }
            });
        </script>
</body>
</html>