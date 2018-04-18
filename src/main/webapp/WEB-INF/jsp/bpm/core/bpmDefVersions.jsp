<%-- 
    Document   : 业务模型管理历史版本
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>流程历史版本</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
		<div class="mini-fit" style="border:0;">
	        <div class="mini-fit form-outer">
				<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" 
				url="${ctxPath}/bpm/core/bpmDef/listByMainDefId.do?mainDefId=${param['mainDefId']}" idField="defId" multiSelect="true" showColumnsMenu="true" 
				sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
					<div property="columns">
						<div type="checkcolumn" width="20"></div>
						<div name="action" cellCls="actionIcons" width="30" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
						<div field="subject" width="140" headerAlign="center" allowSort="true">名称</div>
						<div field="key" width="140" headerAlign="center" allowSort="true">标识键</div>
						<div field="isMain" width="80" headerAlign="center" allowSort="true" renderer="onIsMainRenderer">是否为主版本</div>
						<div field="version" width="80" headerAlign="center" allowSort="true">版本号</div>
						<div field="status" width="60" headerAlign="center" allowSort="true" renderer="onStatusRenderer">状态</div>
					</div>
				</div>
			</div>
        </div>
	
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmDef" winHeight="400" winWidth="900" 
	entityTitle="流程定义" baseUrl="bpm/core/bpmDef" />
	
	<script type="text/javascript">
	//编辑
    function onActionRenderer(e) {
        var record = e.record;
        var uid = record.pkId;
        var modelId=record.modelId;
        var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + uid + '\')"></span>'
                + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\')"></span>'
                + ' <span class="icon-flow-design" title="设计" onclick="designRow(\'' + modelId + '\')"></span>'
                + ' <span class="icon-main" title="设置为主版本" onclick="setMainRow(\'' + uid + '\')"></span>'
                + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
        return s;
    }
	
	function setMainRow(pkId){
		_SubmitJson({
			url:__rootPath+'/bpm/core/bpmDef/setMain.do',
			data:{
				defId:pkId
			},
			success:function(text){
				grid.load();
			}
		});
	}
	
	function designRow(modelId){
		_OpenWindow({
			width:800,
			height:600,
			max:true,
			url:__rootPath+'/process-editor/modeler.jsp?modelId='+modelId,
			title:'流程建模设计',
			ondestroy:function(action){
				if(action!='ok')return;;
				grid.load();
			}
		});
	}
	
	  function onIsMainRenderer(e) {
          var record = e.record;
          var isMain = record.isMain;
           var arr = [{'key' : 'YES', 'value' : '是','css' : 'green'}, 
  			        {'key' : 'NO','value' : '否','css' : 'orange'}];
  			return $.formatItemValue(arr,isMain);
      }
	
    function onStatusRenderer(e) {
        var record = e.record;
        var status = record.status;
        var arr = [
		            {'key' : 'DEPLOYED', 'value' : '发布','css' : 'green'}, 
		            {'key' : 'INIT','value' : '创建','css' : 'orange'} ];
		return $.formatItemValue(arr,status);
    }
	
	</script>
</body>
</html>

