<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html>
<html 
>
<head>
	<title>业务流程解决方案管理-流程定义节点配置</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

	<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="mini-toolbar mini-toolbar-bottom">
        <table style="width:100%;">
              <tr>
                  <td style="width:100%;">
                      <a class="mini-button" iconCls="icon-ok" plain="true" onclick="select">选择</a>
					  &nbsp;选择行或双击选择方法行来返回对应的接口调用示例
                  </td>
              </tr>
    	</table>
    </div>
    <div class="mini-fit form-outer" style="background: #fff;">
		<div 
			id="scriptGrid" 
			class="mini-treegrid" 
			style="height:100%;width:100%"
           	url="${ctxPath}/bpm/core/bpmScript/getConfigTree.do" 
           	onrowdblclick="onGridDbclick"
           	showTreeIcon="true" 
           	treeColumn="title" 
           	expandOnLoad="true" 
           	resultAsTree="false" 
           	idField="id" 
           	parentField="parentId"
           	allowAlternating="true"
     	>
			<div property="columns">
				<div type="indexcolumn" width="28">序号</div>
				<div field="title" name="title" width="160" headerAlign="center" >方法描述</div>
				<div field="name"  width="200" headerAlign="center" >接口名</div>
				<div field="example" width="180" headerAlign="center" >示例</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		mini.parse();
		
		var grid=mini.get('scriptGrid');
		
		function select(){
			var row=grid.getSelected();
			if(!row){
				alert('请选择行！');
				return;
			}
			CloseWindow('ok');
		}
		
		function onGridDbclick(e){
			var grid=e.sender;
			var record=e.record;
			if(record.type=='method'){
				CloseWindow('ok');
			}
		}
		
		function getSelectedRow(){
			return grid.getSelected();
		}
	</script>
</body>
</html>