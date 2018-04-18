<%-- 
    Document   : 供应商管理列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>供应商管理列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
</head>
<body>

	<redxun:toolbar entityName="com.redxun.oa.crm.entity.CrmProvider" />

	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/oa/crm/crmProvider/listData.do" idField="proId"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="name" width="160" headerAlign="center" allowSort="true">供应商名</div>
				<div field="cmpLevel" width="80" headerAlign="center" allowSort="true">单位级别</div>
				<div field="cmpType" width="80" headerAlign="center" allowSort="true">单位类型</div>
				<div field="contactor" width="120" headerAlign="center" allowSort="true">联系人名</div>
				<div field="mobile" width="120" headerAlign="center" allowSort="true">联系人手机</div>
				<div field="status" width="80" headerAlign="center" allowSort="true">状态</div>
			</div>
		</div>
	</div>

	
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.crm.entity.CrmProvider" winHeight="450" winWidth="700" entityTitle="供应商管理"
		baseUrl="oa/crm/crmProvider" />
		
	<script type="text/javascript">
       	//行功能按钮
        function onActionRenderer(e) {
            var record = e.record;
            var pkId = record.pkId;
            
            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
            if(record.actInstId){
            	s+= ' <span class="icon-check" title="审批明细" onclick="checkDetail(\'' + record.actInstId + '\')"></span>';
            }
			return s;
        }
       	//处理添加
       	function _add(){
       		//检查是否存在流程配置，若没有，则启用本地的默认配置
       		_ModuleFlowWin({
       			title:'供应商入库申请',
       			moduleKey:'CRM_PROVIDER',
       			//failCall:add,
       			success:function(){
       				grid.load();	
       			}
       		});
       	}
       	
       	function checkDetail(actInstId){
       		_OpenWindow({
       			title:'审批明细',
       			width:800,
       			height:480,
       			url:__rootPath+'/bpm/core/bpmInst/get.do?actInstId='+actInstId
       		});
       	}
    </script>
</body>
</html>