<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html>
<html>
<head>
<title>人员脚本选择器</title>
<%@include file="/commons/list.jsp" %>
<script type="text/javascript" src="${ctxPath}/scripts/common/baiduTemplate.js"></script>
</head>
<body>
	

						<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 280px;" allowResize="false"
							url="${ctxPath}/bpm/core/bpmGroupScript/listData.do" idField="scriptId" onrowclick="recordRow" multiSelect="true"
							showColumnsMenu="true" sizeList="[5,10]" pageSize="5" allowAlternating="true">
							<div property="columns">
								<div field="className" sortField="CLASS_NAME_" width="120" headerAlign="center" allowSort="true">类名</div>
								<div field="methodName" sortField="METHOD_NAME_" width="120" headerAlign="center" allowSort="true">方法名</div>
								<div field="methodDesc" sortField="METHOD_DESC_" width="120" headerAlign="center" allowSort="true">方法描述</div>
							</div>
						</div>

	<form id="form1" method="post">
		<div id="showInfo" width="100%">
		</div>
	</form>
<div class="mini-toolbar">
    <div style="text-align: center;"><a id="saveButton" class="mini-button" iconCls="icon-ok" onclick="CloseWindow('cancel')">确定</a>
    <a class="mini-button" iconCls="icon-cancel" onclick="CloseWindow('cancel')">取消</a></div>
	</div>
	<script type="text/html" charset="utf-8" id='template_1'>
<table class="table-detail" cellspacing="1" cellpadding="0">
		<caption>配置参数</caption>
<tr>
<th style="text-align: left;">参数描述</th>
<th style="text-align: left;">参数值</th>
</tr>
			<#for(var i=0; i<list.length; i++){ #>
				<tr>
					<td><#= list[i].paraDesc #></td>
					<td><input name="<#= list[i].arg0 #>" class="mini-textbox"  />
			<a class="mini-button" iconCls="icon-edit" onclick="insertInto('<#= list[i].arg0 #>')">来自变量</a> </td>
				</tr>
			<# } #>
</table>
			</script>
	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");
		var clickRecord;
		
		grid.load();
		function recordRow(e) {
			var record = e.record;
			clickRecord=record;
			initHtml();
		}
		
		    
		    function initHtml(){
		    	//设置左分隔符为 <!
		        baidu.template.LEFT_DELIMITER='<#';
		        //设置右分隔符为 <!  
		        baidu.template.RIGHT_DELIMITER='#>';
		        var bt=baidu.template;
		        $("#showInfo").empty();
				$.ajax({
						type : "post",
						url : "${ctxPath}/bpm/core/bpmGroupScript/getScriptParams.do",
						data : {"scriptId" : clickRecord.scriptId},
						success : function(result) {
							var data = {"list" : result};
							var tableHtml = bt('template_1', data);
							$("#showInfo").html(tableHtml);
							mini.parse();	
						}
					}); 
				}
		    
		    function insertInto(EL){
		    	var argEL=mini.getByName(EL);
		    	_OpenWindow({
					title:'人员脚本配置',
					width:750,
					url:__rootPath+'/bpm/core/bpmGroupScript/selectVal.do',
					height:500,
					onload:function(){
						var iframe = this.getIFrameEl().contentWindow;
					},
					ondestroy:function(action){
						if(action!='ok'){return;}
						var iframe = this.getIFrameEl().contentWindow;
						argEL.setValue(iframe.getVal());
					}
				});
		    }
	</script>
</body>
</html>