<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
    <head>
        <title>新建手机表单</title>
        <%@include file="/commons/edit.jsp" %>
        <script type="text/javascript" src="${ctxPath}/scripts/share/dialog.js"></script>
        <script type="text/javascript" src="${ctxPath}/scripts/sys/bo/BoUtil.js"></script>
        <script type="text/javascript" src="${ctxPath}/scripts/flow/form/bpmMobileForm.js"></script>
    </head>
    <body > 
         <div id="toolbar1" class="mini-toolbar mini-toolbar-bottom" >
		    <a class="mini-button" iconCls="icon-next" onclick="next()">下一步</a>
		</div>
       <div id="p1" class="form-outer2 shadowBox90">
            <form id="form1" method="post" >
                	<div class="form-inner">
                		<input id="pkId" name="id" class="mini-hidden" value="${bpmMobileForm.id}"/>
                        <table class="table-detail" cellspacing="1" cellpadding="0">
                        		<caption>新建手机表单</caption>
								<tr>
									<th>
										<span class="starBox">
							 				选择业务对象<span class="star">*</span>
						 				</span>
							 		</th>
									<td>
										<input id="viewName"  allowInput="false" class="mini-textbox input-60" vtype="maxLength:60" required="true" />
										<a class="mini-button" iconCls="icon-select" onclick="selectBo()">选择</a>
									</td>
								</tr>
								<tbody id="tbody"></tbody>
							</table>
																																																																																																																																																																						                       </table>
                     </div>
            </form>
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