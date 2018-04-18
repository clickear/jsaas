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
    Document   : [${comment}]编辑页
    Created on : ${date?string("yyyy-MM-dd HH:mm:ss")}
    Author     : ${vars.developer}
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[${comment}]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${classVar}.id" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="<#noparse>${</#noparse>${classVar}.${pkVar}}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[${comment}]基本信息</caption>
					<#list commonList as col>
					<#assign colName=func.convertUnderLine(col.columnName)>
					<#if func.isExcludeField( colName) >
					<tr>
						<th>${col.comment}：</th>
						<td>
							
							<#if (col.colType=="java.util.Date")>
								<input name="${colName}"  class="mini-datepicker"  format="yyyy-MM-dd" />
							<#else>
								<input name="${colName}"  class="mini-textbox"   style="width: 90%" />
							</#if>
						</td>
					</tr>
					</#if>
					</#list>
				</table>
				<#if subtables?exists && subtables?size!=0>				
					<#list subtables as subTable>
					<#assign subCols=subTable.columnList>
					<div class="mini-toolbar">
				    	<a class="mini-button" iconCls="icon-add" plain="true" onclick="add${subTable.tableName}Row">添加</a>
						<a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove${subTable.tableName}Row">删除</a>
					</div>
					<div id="grid_${subTable.tableName}" class="mini-datagrid" style="width: 100%; height: auto;" allowResize="false"
					idField="id" allowCellEdit="true" allowCellSelect="true" allowSortColumn="false" 
					multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" showPager="false" >
						<div property="columns">
							<div type="checkcolumn" width="20"></div>
							<#list subCols as subCol>
							<#assign colName=func.convertUnderLine(subCol.columnName)>
							<div field="${colName}"  width="120" headerAlign="center" >${subCol.comment?trim}
								<input property="editor" class="mini-textbox" style="width:100%;" minWidth="120" /></div>
							</#list>
						</div>
					</div>
					</#list>
				</#if>
			</div>
		</form>
	</div>
	<#if !(subtables?exists && subtables?size!=0)>				
	<rx:formScript formId="form1" baseUrl="${system}/${package}/${classVar}"
		entityName="${domain}.${system}.${package}.entity.${class}" />
	</#if>
	
	<script type="text/javascript">
	mini.parse();
	<#if subtables?exists && subtables?size!=0>				
	<#list subtables as subTable>
	var grid${subTable.tableName} = mini.get("grid_${subTable.tableName}");
	</#list>
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
		
		<#list subtables as subTable>
	function add${subTable.tableName}Row(){
		var row = {};
		grid${subTable.tableName}.addRow(row);
	}
	
	function remove${subTable.tableName}Row(){
		var selecteds=grid${subTable.tableName}.getSelecteds();
		grid${subTable.tableName}.removeRows(selecteds);
	}
	</#list>
	function onOk(){
		form.validate();
	    if (!form.isValid()) {
	        return;
	    }	        
	    var data=form.getData();
	    <#if subtables?exists && subtables?size!=0>				
		<#list subtables as subTable>
		<#assign subClassVar=subTable.variables.classVar>
		data.${subClassVar}s = removeData( grid${subTable.tableName}.getData());
		</#list>
		</#if>
		var config={
        	url:"<#noparse>${ctxPath}</#noparse>/${system}/${package}/${classVar}/save.do",
        	method:'POST',
        	postJson:true,
        	data:data,
        	success:function(result){
        		//如果存在自定义的函数，则回调
        		if(isExitsFunction('successCallback')){
        			successCallback.call(this,result);
        			return;	
        		}
        		
        		CloseWindow('ok');
        	}
        }
	        
		_SubmitJson(config);
	}	
	
	</#if>
	
	
	function removeData(rows){
			for(var i=0;i<rows.length;i++){
				var row=rows[i];
				delete row.tenantId;
				delete row.updateBy;
				delete row.updateTime;
				delete row.createTime;
				delete row.createBy;
				delete row.pkId;
				
			}
			return rows;
		}
	

	</script>
</body>
</html>