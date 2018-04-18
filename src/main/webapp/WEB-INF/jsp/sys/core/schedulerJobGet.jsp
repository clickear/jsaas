<%-- 
    Document   : 定时器编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<title>定时器编辑</title>
<%@include file="/commons/get.jsp"%>

</head>
<body>
	<!-- <div id="toolbar1" class="mini-toolbar mini-toolbar-bottom" >
		    <table style="width:100%;">
		        <tr>
		            <td style="width:100%;" id="toolbarBody">
		                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a>
		            </td>
		        </tr>
		    </table>
	</div> -->
	<div class="heightBox"></div>
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<table class="table-detail column_2" cellspacing="1" cellpadding="0">
					<caption>定时器任务基本信息</caption>
					<tr>
						<th>任务名称 </th>
						<td>${job.name }
						</td>
					</tr>
					<tr>
						<th>类　　名 </th>
						<td>
							${job.className }
							
						</td>
					</tr>
					<tr>
						<th>任务描述 </th>
						<td >
							${job.description }
						</td>
					</tr>
				</table>
				
				<div id="ruleGrid" class="mini-datagrid"
					style="width: 100%; height: 300px;margin-top: 10px;" height="auto"
					showPager="false" allowCellEdit="true" allowCellSelect="true"
					>
					<div property="columns">
						<div type="indexcolumn" width="40">序号</div>
						<div field="type" width="50" headerAlign="center">
							类型 
						</div>
						<div field="name" width="120" headerAlign="center">
							参数名 
						</div>
						<div field="value" width="120" headerAlign="center">
							参数值
						</div>
					</div>
				</div>
				
			</div>
		</form>
	</div>


	<script type="text/javascript">
		addBody();
		mini.parse();
		
		var json=${job.paramJson};
	 	
	 	var grid = mini.get('ruleGrid');
		
	 	grid.setData(json);
		
		function onCancel(){
			CloseWindow('cancel');
		}
		
	</script>
</body>
</html>