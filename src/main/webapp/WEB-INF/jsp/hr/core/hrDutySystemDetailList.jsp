<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title> 
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;">
		<div region="south" showSplit="false" showHeader="false" height="50" showSplitIcon="false">
			<div class="mini-toolbar">
				<table style="width: 100%; padding: 0;">
					<tr>
						<td align="center"><a class="mini-button" iconCls="icon-ok" onclick="onOk()">确定</a> <span style="display: inline-block; width: 25px;"></span> <a class="mini-button" iconCls="icon-cancel" onclick="onCancel()">取消</a></td>
					</tr>
				</table>
			</div>
		</div>
		<div region="center" bodyStyle="padding:2px;" showCollapseButton="false">
			<div class="mini-toolbar" style="padding: 2px;">
				<form id="searchForm">
					<table style="width: 100%;">
						<tr>
							<td style="width: 100%;" id="toolbarBody"><input class="mini-textbox" id="name" name="name" emptyText="请输入名称" /> <a class="mini-button" iconCls="icon-search" onclick="onSearch">查询</a> <a class="mini-button" iconCls="icon-cancel" onclick="onClear">清空</a></td>
						</tr>
					</table>
				</form>
			</div>
			<div class="mini-fit">
				<div id="systemGrid" class="mini-datagrid" style="width: 100%; height: 100%;" resultAsTree="false" idField="systemId"  allowResize="true" expandOnLoad="true" allowRowSelect="true" url="${ctxPath}/hr/core/hrDutySystem/getAllSystem.do" multiSelect="false">
					<div property="columns">
						<div type="checkcolumn" width="50"></div>
						<div field="name" width="120" headerAlign="center" allowSort="true">名称</div>
						<div field="type" width="120" headerAlign="center" allowSort="true">类型</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end of the region center -->
	</div>
		<script type="text/javascript">
			mini.parse();
			var systemGrid=mini.get("systemGrid");
			systemGrid.load();
			
			function getData(){
				var row =systemGrid.getSelected();
				return row;
			}
			
			function onOk(){
				CloseWindow('ok');
			}
			
			function onCancel(){
				CloseWindow('cancel');
			}
			
			function onClear(){
				$("#searchForm")[0].reset();
				systemGrid.setUrl("${ctxPath}/hr/core/hrDutySystem/getAllSystem.do");
				systemGrid.load();
			}
			
			function onSearch(){
				var formData=$("#searchForm").serializeArray();
				var data=jQuery.param(formData);
				systemGrid.setUrl("${ctxPath}/hr/core/hrDutySystem/search.do?"+data);
				systemGrid.load();
			}
		</script>
</body>

</html>