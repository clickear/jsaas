<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
    <head>
        <title>新建手机表单</title>
        <%@include file="/commons/edit.jsp" %>
        <script type="text/javascript" src="${ctxPath}/scripts/flow/form/bpmMobileForm.js"></script>
        <script type="text/javascript">
        	var boDefId="${param.boDefId}";
        	$(function(){
        		getTemplates(boDefId);
        	});
        	
        	function generate(){
        		CloseWindow('ok');
        	}
        </script>
    </head>
    <body > 
         <div id="toolbar1" class="mini-toolbar topBtn" >
			<a class="mini-button" iconCls="icon-save" plain="true" onclick="generate()">生成</a>
<!-- 			<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a> -->
		</div>
       <div id="p1" class="form-outer shadowBox90" ng-app="app" >
            <table class="table-detail" cellspacing="1" cellpadding="0">
                <caption>重新生成模版</caption>
				<tbody id="tbody"></tbody>
			</table>
        </div>
        <script id="templateList"  type="text/html">
		<#for(var i=0;i<list.length;i++){
			var obj=list[i];
			var tempAry=obj.template;
		#>
		<tr >
				<th ><#=obj.name#></th>
				<td>
					<select name="template" alias="<#=obj.key#>" type="<#=obj.type#>">
						<#for(var n=0;n<tempAry.length;n++){
							var tmp=tempAry[n];
						#>
							<option value="<#=tmp.alias#>"><#=tmp.name#></option>
						<#}#>
					</select>
				</td>
		</tr>
		<#}#>
		</script>
      <rx:formScript formId="form1" 
       baseUrl="bpm/form/bpmFormTemplate"
       entityName="com.redxun.bpm.form.entity.BpmFormTemplate" />
       <script type="text/javascript">
       		addBody();
       </script>
    </body>
</html>