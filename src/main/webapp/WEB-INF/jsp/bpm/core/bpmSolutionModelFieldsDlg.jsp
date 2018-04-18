<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@include file="/commons/edit.jsp"%>
		<title>流程方案表单字段列表</title>
	</head>
	<body>
		
        <div class="mini-toolbar mini-toolbar-bottom">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="mini-button" iconCls="icon-ok" plain="true" onclick="CloseWindow('ok')">选择</a>
		                <a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow();">关闭</a>
                    </td>
                </tr>
            </table>           
        </div>
	  
	    
	    <table class="table-detail" cellpadding="0" cellspacing="1" style="width:100%">
				<tr>
					<th>表单选择</th>
					<td>
						<input id="boDefId" class="mini-combobox" name="boDefId" url="${ctxPath}/bpm/core/bpmSolution/boDefFields.do?solId=${param.solId}" onvaluechanged="onBoChanged"
									valueField="id" textField="name" popupHeight="150" style="width:90%;margin-top: 2px;" required="true"/>
					</td>
				</tr>
		</table>
	    
		<div class="mini-fit rx-grid-fit form-outer">
			 <div id="grid" class="mini-datagrid" style="width:100%;height:100%;"     
	            multiSelect="true"  allowResize="true"  showPager="false" allowAlternating="true"
	            url="${ctxPath}/bpm/core/bpmSolution/modelFields.do?nodeId=${param.nodeId}&solId=${param.solId}&actDefId=${param.actDefId}">
	            <div property="columns">
	            	<div type="indexcolumn" width="50">序号</div>
	            	<div type="checkcolumn" width="40"></div>
	                <div field="name" headerAlign="center" width="160">字段名称</div>
	                <div field="key"  headerAlign="center" width="100">字段Key</div>
	                <div field="type"  headerAlign="center" width="100">类型</div>
	            </div>
	        </div>
	    </div>
	    <script type="text/javascript">
	    	
	    	mini.parse();
	    	var grid=mini.get('grid');
	    	function onDrawGroup(e) {        
	             e.cellHtml = e.value;
	        }
	    	
	    	function getSelectedFields(){
	    		return grid.getSelecteds();
	    	}
	    	
	    	function onBoChanged(){
	    		var boDefId = mini.get("boDefId");
				var id = boDefId.getValue();
				
	            var url = "${ctxPath}/bpm/core/bpmSolution/modelFields.do?boDefId="+id;
	            grid.setUrl(url);
	            grid.load();
			}
		  
	    </script>
	</body>
</html>