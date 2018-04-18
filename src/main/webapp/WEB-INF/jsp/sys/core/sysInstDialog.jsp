<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>机构选择</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
		 <div region="south" showSplit="false" showHeader="false" height="45" showSplitIcon="false"  style="width:100%" bodyStyle="border:0">
			<div class="mini-toolbar dialog-footer" style="text-align:center;border:none;" >
			     <a class="mini-button" iconCls="icon-ok"   onclick="CloseWindow('ok')">确定</a>
				 <a class="mini-button" iconCls="icon-cancel"  onclick="CloseWindow('cancel')">取消</a>
			</div>	 
		 </div>
		 <div title="机构类型" region="west" width="180"  showSplitIcon="true" showHeader="false" bodyStyle="overflow: auto;">
		 	<div id="typeTree" class="mini-tree" url="${ctxPath}/sys/core/sysInstType/getAllValids.do" style="width:100%;" idField="typeId" textField="typeName"
	            onnodeclick="typeNodeClick" >        
	        </div>
		 </div>
		 <div title="组织机构列表" region="center" showHeader="false" showCollapseButton="false">
		 	<div class="mini-toolbar">
		 		<div id="searchForm">
			 		<input class="mini-textbox" id="instNo" name="instNo" emptyText="机构编码" onenter="search"/>
					<input class="mini-textbox" id="nameCn" name="nameCn" emptyText="机构名称" onenter="search"/>
					<a class="mini-button" onclick="search" iconCls="icon-search">查询</a>
					<a class="mini-button" onclick="onClear" iconCls="icon-clear">清空查询</a>
				</div>
		 	</div>
		 	<div class="mini-fit">
			 	<div id="grid1" class="mini-datagrid"
					style="width: 100%; height: 100%;" idField="id" allowResize="true"
					url="${ctxPath}/sys/core/sysInst/listForDialog.do" multipleSelect="${param['single']}"
					onrowdblclick="onRowDblClick">
					<div property="columns">
						<div type="indexcolumn" width="50">序号</div>
						<div field="instNo" width="120" headerAlign="center" >机构编码</div>
						<div field="nameCn" width="200" headerAlign="center" >机构名称</div>
						<div field="domain" width="200" headerAlign="center"> 域名</div>
						<div field="busLiceNo" width="150" headerAlign="center">营业执照编码</div>
					</div>
				</div>
			</div>
		 </div>
	</div>
	<script type="text/javascript">
		mini.parse();

		var grid = mini.get("grid1");

		grid.load();

		function getData() {
			var row = grid.getSelecteds();
			return row;
		}
		
		function typeNodeClick(e){
			var node=e.node;
			var typeCode=node.typeCode;
			grid.load({
				Q_instType_S_EQ:typeCode
			});
		}
		
		function search() {
			var instNo = mini.get("instNo").getValue();
			var nameCn = mini.get("nameCn").getValue();
			grid.load({
				Q_instNo_S_LK : instNo,
				Q_nameCn_S_LK: nameCn
			});
		}
		
		function onClear(){
			mini.get("instNo").setValue('');
			mini.get("nameCn").setValue('');
			grid.load({
				Q_instNo_S_LK : '',
				Q_nameCn_S_LK: '',
				Q_instType_S_LK:''
			});
		}

		function onRowDblClick(e) {
			CloseWindow('ok');
		}
		
	</script>
</body>
</html>

