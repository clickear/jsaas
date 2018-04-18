<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<title>关系类型列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	
	<div id="toolbar1" class="mini-toolbar">
	    <table style="width:100%;">
	        <tr>
	        <td style="width:100%;">
	        	管理与【${osDimension.name}】的关系
	        	<span class="separator"></span>
	            <a class="mini-button" iconCls="icon-add" plain="true" onclick="addRelType()">添加</a>
	            <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
	            <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="onRefresh()">刷新</a>
	            <span class="separator"></span>
	        </td>
	        </tr>
	    </table>
	</div>
	<div class="mini-fit rx-grid-fit" style="height: 100px;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" 
			url="${ctxPath}/sys/org/osDimension/listRelsByDimId.do?dimId=${osDimension.dimId}" 
			idField="id" multiSelect="true" allowAlternating="true" showPager="false">
			<div property="columns">
				<div type="checkcolumn" width="30"></div>
				<div name="action" cellCls="actionIcons" width="32" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="name" width="140" headerAlign="center" >关系名</div>
				<div field="key" width="140" headerAlign="center" >关系标识</div>
				<div field="relType" width="120" headerAlign="center" renderer="onRelTypeRenderer">关系类型</div>
				<div field="constType" width="120" headerAlign="center" renderer="onConstTypeRenderer" >关系约束类型</div>
				<div field="party1" width="120" headerAlign="center" >关系当前方名称</div>
				<div field="party2" width="120" headerAlign="center" >关系关联方名称</div>
			</div>
		</div>
	</div>
	
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.org.entity.OsRelType" 
	tenantId="${param['tenantId']}"
	winHeight="450" winWidth="700" entityTitle="维度" baseUrl="sys/org/osRelType" />
	
	<script type="text/javascript">
		mini.parse();
		var grid=mini.get('datagrid1');
		var dimId='${osDimension.dimId}';
		grid.load();
		function onActionRenderer(e){
			var record=e.record;
			var pk=record.pkId;
			 var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pk + '\')"></span>'
     	 	+ ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pk + '\')"></span>'
     		+ ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pk + '\')"></span>';
     		return s;
		}
		
		function onRefresh(){
			grid.load();
		}
		
		//添加关系
		function addRelType(){
			_OpenWindow({
				title:'关系管理',
				url:__rootPath+'/sys/org/osRelType/group.do?dimId='+dimId,
				height:500,
				width:1024,
				ondestroy:function(action){
					if(action=='ok'){
						grid.load();
					}
				}
			});
		}
		
		//编辑关系
		function editRow(pk){
			_OpenWindow({
				title:'关系管理',
				url:__rootPath+'/sys/org/osRelType/group.do?id='+pk,
				height:400,
				width:800,
				ondestroy:function(action){
					if(action=='ok'){
						grid.load();
					}
				}
			});
		}
		//删除关系
		function delRow(pk){
			_SubmitJson({
				url:__rootPath+'/sys/org/osRelType/del.do?ids='+pk,
				method:'POST',
				success:function(){
					grid.load();
				}
			});
		}
		
		function onRelTypeRenderer(e) {
            var record = e.record;
            var relType = record.relType;
           var arr = [ {'key' : 'USER-USER','value' : '用户与用户关系'}, 
   		               {'key' : 'GROUP-GROUP','value' : '用户组与用户组关系'},
   		               {'key' : 'USER-GROUP','value' : '用户与用户组关系'}, 
		               {'key' : 'GROUP-USER','value' : '用户组与用户关系'}];
   		return $.formatItemValue(arr,relType);
        }
		
		function onConstTypeRenderer(e) {
			var record = e.record;
            var constType = record.constType;
           var arr = [ {'key' : 'ONE-ONE','value' : '一对一'}, 
   		               {'key' : 'ONE-MANY','value' : '一对多'},
   		               {'key' : 'MANY-MANY','value' : '多对多'}, 
		               {'key' : 'MANY-ONE','value' : '多对一'}];
   		return $.formatItemValue(arr,constType);
		}
		
	</script>
</body>
</html>