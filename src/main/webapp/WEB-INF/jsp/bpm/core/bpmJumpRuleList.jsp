<%-- 
    Document   : [流程跳转规则]列表页
    Created on : 2018-04-10 13:44:42
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[流程跳转规则]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create" plain="true" onclick="addRule()">增加</a>
                     <a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
                     <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
                 </td>
             </tr>
         </table>
     </div>
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/bpm/core/bpmJumpRule/listData.do?Q_ACTDEF_ID__S_EQ=${param.actDefId}&Q_SOL_ID__S_EQ=${param.solId}&Q_NODE_ID__S_EQ=${param.nodeId}" idField="id"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div field="name"  sortField="NAME_"  width="120" headerAlign="center" allowSort="true">规则名</div>
				<div field="targetName"  sortField="TARGET_NAME_"  width="120" headerAlign="center" allowSort="true">目标节点</div>
				<div field="sn"  sortField="SN_"  width="120" headerAlign="center" allowSort="true">顺序</div>
				<div field="description"  sortField="DESCRIPTION_"  width="120" headerAlign="center" allowSort="true">描述</div>
			</div>
		</div>
	</div>


	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmJumpRule" winHeight="450"
		winWidth="700" entityTitle="流程跳转规则" baseUrl="bpm/core/bpmJumpRule" />
		
	<script type="text/javascript">
		var actDefId="${param.actDefId}";
		var solId="${param.solId}";
		var nodeId="${param.nodeId}";
		var grid=mini.get("datagrid1");
		
		function addRule(){
			var url="${ctxPath}/bpm/core/bpmJumpRule/edit.do?actDefId="+actDefId+"&solId="+ solId+"&nodeId="+nodeId;
			_OpenWindow({
				url: url,
		        title: "添加流程跳转规则", width: "800", height: "600",
		        ondestroy: function(action) {
		        	if(action!="ok") return;
		        	grid.load();
		        }
			}); 
			
		}
	</script>
</body>
</html>