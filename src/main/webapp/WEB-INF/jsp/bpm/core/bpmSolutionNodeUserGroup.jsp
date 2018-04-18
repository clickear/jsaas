<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
	<title>业务流程解决方案管理-抄送人员配置</title>
	<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="mini-toolbar ">
        <a class="mini-button" iconCls="icon-add" onclick="addGroup()"  plain="true">添加</a>
        <a class="mini-button" iconCls="icon-close" onclick="CloseWindow()" plain="true">关闭</a>
	  	<input class="mini-hidden" id="nodeText" value=""/>
	</div>
 	<div class="mini-fit form-outer2">
		<div id="userGrid" class="mini-datagrid" 
	        		height="auto" 
	        		data-options="{nodeId:'${param.nodeId}'}"
	         		url="${ctxPath}/bpm/core/bpmSolUsergroup/getBySolNode.do?actDefId=${param.actDefId}&solId=${param.solId}&nodeId=${param.nodeId}&groupType=${param.groupType}"
					idField="id" showPager="false" style="width:100%;">
			<div property="columns">
				<div type="indexcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="groupName" name="groupName"   width="80" headerAlign="center">组类型</div>
				<div field="displayNotifyType" name="displayNotifyType"   width="160" headerAlign="center" >通知类型</div>
				<div field="sn" name="sn" width="50" headerAlign="center" >序号</div>
			</div>
		</div>
	</div>
<script type="text/javascript">
	var groupType="${groupType}";
	var actDefId="${param.actDefId}";

	mini.parse();
	
	var grid=mini.get('userGrid');
	grid.load();
	
	
	function addGroup(){
		var url="${ctxPath}/bpm/core/bpmSolUsergroup/edit.do?actDefId=${param.actDefId}&solId=${param.solId}&nodeId=${param.nodeId}&groupType=${param.groupType}";
		
		_OpenWindow({
			url:url,
			title:'添加用户组',
			width:780,
			height:480,
			ondestroy:function(action){
				if(action=="ok"){
					grid.load();
				}
			}
		});
	}
	
	function onActionRenderer(e) {
        var record = e.record;
        var uid = record.pkId;
    	var	s= ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\')"></span>';
            s+= ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
        return s;
    }
</script>

<redxun:gridScript gridId="userGrid" entityName="com.redxun.bpm.core.entity.BpmSolUsergroup" 
        	winHeight="500" winWidth="700"
          	entityTitle="用户分组" baseUrl="bpm/core/bpmSolUsergroup"/>
</body>
</html>