<%-- 
    Document   : 资产信息列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>资产信息列表管理</title>
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

	<redxun:toolbar entityName="com.redxun.oa.product.entity.OaAssets" />

	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/oa/product/oaAssets/listData.do" idField="assId"
			multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">#</div>
				<div field="code" width="120" headerAlign="center" allowSort="true">资产编号</div>
				<div field="name" width="120" headerAlign="center" allowSort="true">资产名称</div>
				<div field="status" width="120" headerAlign="center" allowSort="true">状态</div>
				<div field="desc" width="120" headerAlign="center" allowSort="true">描述</div>
				
			</div>
		</div>
	</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.product.entity.OaAssets" winHeight="650" winWidth="750" entityTitle="资产信息" baseUrl="oa/product/oaAssets" />
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
	            var status = record.status;
	            
	            if(field=='name'){
	            	e.cellHtml= '<a href="javascript:detailRow(\'' + record.pkId + '\')">'+record.name+'</a>';
	            }
	            if(field=='status'){
	            	if(status=='BRANDNEW'){
	            		e.cellHtml='全新';
	            	}else if(status=='SECONDHAND'){
	            		e.cellHtml='旧货';
	            	}else if(status='DISPOSABLE'){
	            		e.cellHtml='一次性';
	            	}
	            }

	        });
	        
	        grid.on('update',function(){
	        	_LoadUserInfo();
	        });
        </script>

</body>
</html>