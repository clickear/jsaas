<%-- 
    Document   : 流程定义模型列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>流程定义模型列表管理</title>
<%@include file="/commons/dynamic.jspf"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>

</head>
<body>

	<redxun:toolbar entityName="com.redxun.bpm.activiti.entity.ActReModel" />

	<div class="mini-fit" style="height: 100px;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/bpm/activiti/actReModel/listData.do" idField="id" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="80" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="name" width="120" headerAlign="center" allowSort="true">名称</div>
				<div field="key" width="120" headerAlign="center" allowSort="true">标识键</div>
				<div field="version" width="50" headerAlign="center" allowSort="true">版本</div>
				<div field="deploymentId" width="80" headerAlign="center" allowSort="true">发布ID</div>
				<div field="editorSourceValueId" width="80" headerAlign="center" allowSort="true">编辑器源资源ID</div>
				<div field="editorSourceExtraValueId" width="80" headerAlign="center" allowSort="true">编辑器附加资源ID</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
        	//编辑
	        function onActionRenderer(e) {
	            var record = e.record;
	            var uid = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + uid + '\')"></span>'
	           	        + ' <span class="icon-flow-design" title="设计" onclick="designRow(\'' + uid + '\')"></span>'
	           	     	+ ' <span class="icon-flow-deploy" title="发布" onclick="deployModel(\'' + uid + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
	            return s;
	        }
        	
        	function designRow(uid){
        		_OpenWindow({
        			width:800,
        			height:600,
        			max:true,
        			url:__rootPath+'/process-editor/modeler.jsp?modelId='+uid,
        			title:'流程建模设计',
        			ondestroy:function(action){
        				if(action!='ok')return;;
        				grid.load();
        			}
        		});
        	}
        	
        	function deployModel(uid){
        		_SubmitJson({
        			url:__rootPath+'/bpm/activiti/actReModel/deployModel.do?modelId='+uid,
        			method:'POST'
        		});
        	}
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.activiti.entity.ActReModel" 
	winHeight="450" winWidth="700" entityTitle="流程定义模型" baseUrl="bpm/activiti/actReModel" />
</body>
</html>