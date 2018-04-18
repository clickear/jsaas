<#import "function.ftl" as func>
<#assign package=model.variables.package>
<#assign class=model.variables.class>
<#assign classVar=model.variables.classVar>
<#assign comment=model.tabComment>
<#assign subtables=model.subTableList>
<#assign pk=func.getPk(model) >
<#assign pkModel=model.pkModel >
<#assign pkVar=func.convertUnderLine(pk) >
<#assign pkType=func.getPkType(model)>
<#assign fkType=func.getFkType(model)>
<#assign system=vars.system>
<#assign domain=vars.domain>
<#assign tableName=model.tableName>
<#assign colList=model.columnList>
<#assign commonList=model.commonList>

<%-- 
    Document   : [${comment}]明细页
    Created on : ${date?string("yyyy-MM-dd HH:mm:ss")}
    Author     : ${vars.developer}
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[${comment}]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
             	<table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                	<caption>[${comment}]基本信息</caption>
					<#list commonList as col>
					<#assign colName=func.convertUnderLine(col.columnName)>
					<tr>
						<th>${col.comment}：</th>
						<td>
							<#noparse>${</#noparse>${classVar}.${colName}<#noparse>}</#noparse>
						</td>
					</tr>
					</#list>
				</table>
             </div>
    	</div>
    	<#if subtables?exists && subtables?size!=0>				
		<#list subtables as subTable>
		<#assign subCols=subTable.columnList>
    	<div id="grid_${subTable.tableName}" class="mini-datagrid" style="width: 100%; height: auto;" allowResize="false"
				idField="id" allowCellEdit="true" allowCellSelect="true" allowSortColumn="false"
				multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
			<div property="columns">
				<#list subCols as subCol>
				<#assign colName=func.convertUnderLine(subCol.columnName)>
				<div field="${colName}"   width="120" headerAlign="center" >${subCol.comment?trim}</div>
				</#list>
			</div>
		</div>
		</#list>
		</#if>
        <rx:detailScript baseUrl="${system}/${package}/${classVar}" 
        entityName="${domain}.${system}.${package}.entity.${class}"
        formId="form1"/>
        
        <script type="text/javascript">
		mini.parse();
		<#if subtables?exists && subtables?size!=0>				
		<#list subtables as subTable>
		var grid${subTable.tableName} = mini.get("grid_${subTable.tableName}");
		</#list>
		</#if>
		var form = new mini.Form("#form1");
		var pkId = <#noparse>'${</#noparse>${classVar}.${pkVar}}';
		$(function(){
			$.ajax({
				type:'POST',
				url:"<#noparse>${ctxPath}</#noparse>/${system}/${package}/${classVar}/getJson.do",
				data:{ids:pkId},
				success:function (json) {
					form.setData(json);
					<#if subtables?exists && subtables?size!=0>				
					<#list subtables as subTable>
					<#assign subClassVar=subTable.variables.classVar>
					grid${subTable.tableName}.setData(json.${subClassVar}s);
					</#list>
					</#if>
				}					
			});
		})
		</script>
    </body>
</html>