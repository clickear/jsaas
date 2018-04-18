<%-- 
    Document   : [KdDoc]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>知识地图列表管理</title>
<%@include file="/commons/list.jsp"%>
<!-- script src="${ctxPath}/scripts/printer/LodopFuncs.js" type="text/javascript"></script-->
</head>
<body>

	<div class="mini-toolbar" >
			<table style="width:100%;">
		          <tr>
		            <th style="width:100%;">
						<a class="mini-button" iconCls="icon-add" plain="true" onclick="addMap()">添加</a>
						<a class="mini-button" iconCls="icon-detail" plain="true" onclick="detailMap()">查看</a>
						<a class="mini-button" iconCls="icon-edit" plain="true" onclick="editMap()">编辑</a>
						<a class="mini-button" iconCls="icon-remove" plain="true" onclick="delMap()">删除</a>
						<a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refresh">刷新</a>
					</th>
				</tr>
			</table>
		</div>

	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/kms/core/kdDoc/getMapByPath.do?treeId=${param['treeId']}" idField="docId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="sysTree.name" width="50" headerAlign="center" allowSort="true">所属分类</div>
				<div field="subject" width="50" headerAlign="center" allowSort="true">地图标题</div>
				<div field="author" width="50" headerAlign="center" allowSort="true">作者</div>
				<div field="issuedTime" width="50" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">发布日期</div>
				<div field="status" width="50" headerAlign="center" allowSort="true">文档状态</div>
				<div field="version" width="50" headerAlign="center" allowSort="true">版本号</div>
				<div field="createTime" width="50" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailMap(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editMap(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delMap(\'' + pkId + '\')"></span>';
	            return s;
	        }
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.kms.core.entity.KdDoc" winHeight="450" winWidth="700" entityTitle="知识文档" baseUrl="kms/core/kdDoc" />
	<script type="text/javascript">
		function detailMap(pkId){
			_OpenWindow({
				title:'地图信息',
				url:__rootPath+"/kms/core/kdDoc/mapShow.do?docId="+pkId,
				width:1000,
				height:600
			});
		}
		
		function addMap(){
			_OpenWindow({
				title:"新建地图",
				url:__rootPath+"/kms/core/kdDoc/mapNew1.do?",
				width:1000,
				height:600
			});
		}
		
		function editMap(pkId){
			var mapId="";
			if(pkId!=null)
				mapId=pkId;
			else{
				var rows=grid.getSelecteds();
				if(rows.length<=0){
					alert("请选择一个地图！");
					return;
				}
				else if(rows.length>1){
					alert("编辑的地图不能超过一个！");
					return;
				}
				else if(rows.length==1)
					mapId=rows[0].docId;
			}
			_OpenWindow({
				url:"${ctxPath}/kms/core/kdDoc/mapMgrEdit.do?pkId="+mapId,
				title:"编辑地图",
				height:600,
				width:1000,
				ondestroy:function(action){
					if(action=='ok')
						grid.load();
				}
			});
		}
		
		function delMap(pkId){
			var mapId="";
			if(pkId!=null)
				mapId=pkId;
			else{
				var rows=grid.getSelecteds();
				if(rows.length<=0){
					alert("请选择一个地图！");
					return;
				}
				else if(rows.length>1){
					alert("编辑的地图不能超过一个！");
					return;
				}
				else if(rows.length==1)
					mapId=rows[0].docId;
			}
			//alert(mapId);
			_SubmitJson({
				url:"${ctxPath}/kms/core/kdDoc/del.do",
				data:{
					ids:mapId
				},
				method:'POST',
				success:function(result){
					var grid=mini.get("datagrid1");
					grid.load({});
				}
			});
		}
		
		
		
		function refresh(){
			var grid=mini.get("datagrid1");
			grid.load({});
		}
		
	</script>
</body>
</html>