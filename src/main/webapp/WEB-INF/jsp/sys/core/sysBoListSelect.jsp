<%-- 
    Document   : [系统自定义业务管理列表]列表页
    Created on : 2017-05-21 12:11:18
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>系统对话框列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="mini-toolbar">
		<table >
             <tr>
                  <td  class="search-form" >
			名称:<input class="mini-textbox"  id="name">标识键:<input class="mini-textbox" id="key">
                  </td>
                  <td >
                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="customSearch()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
                 </td>
              </tr>
         </table>	
	</div>
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/sys/core/sysBoList/listDialogJsons.do" idField="id" onrowclick="clickTheTheRow"
			 showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20">
			<div property="columns">
				<div field="name"  sortField="name"  width="120" headerAlign="center" allowSort="true">名称</div>
				<div field="key"  sortField="key"  width="120" headerAlign="center" allowSort="true">标识键</div>
				<div field="isLeftTree"  sortField="isLeftTree"  width="60" headerAlign="center" allowSort="true">是否显示左树</div>
				<div field="isPage"  sortField="isPage"  width="60" headerAlign="center" allowSort="true">是否分页</div>
			</div>
		</div>
	</div>
	
	<div class="mini-toolbar">
		<div style="text-align: center;">
		<a id="saveButton" class="mini-button" iconCls="icon-ok" onclick="selectAndClose">确定</a>
		 <a class="mini-button" iconCls="icon-cancel"
			onclick="CloseWindow('cancel')">取消</a>
		</div>
	</div>

	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.core.entity.SysBoList" winHeight="450"
		winWidth="700" entityTitle="系统对话框定义" baseUrl="sys/core/sysBoList" />
		
	<script type="text/javascript">
		var selectedRow=null;
		mini.parse();
		function getSelectedRow(){
			return selectedRow;
		}
		
		function selectAndClose(){
			console.log(selectedRow);
			if(selectedRow==null){
				alert("请先选择对话框");
				return;
			}
			CloseWindow('ok');
		}
		
		function clickTheTheRow(e){
			selectedRow=e.record;
			console.log(selectedRow);
		}
		
		function customSearch(){
			var data={};
			data.name=mini.get("name").getValue();
			data.key=mini.get("key").getValue();
			console.log(data);
			grid.load(data);
		}
		
	</script>
</body>
</html>