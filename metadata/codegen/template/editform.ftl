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
<script  type="text/javascript">
	function getData(){
		return _GetFormJsonMini("form1");
	}
	
	function isValid(){
		return true;
	}
	
</script>
</head>
<body>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
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
							<input name="${colName}" value="<#noparse>${</#noparse>${classVar}.${colName}<#noparse>}</#noparse>"
						class="mini-datepicker"  format="yyyy-MM-dd" />
						<#else>
							<input name="${colName}" value="<#noparse>${</#noparse>${classVar}.${colName}<#noparse>}</#noparse>"
						class="mini-textbox"   style="width: 90%" />
						</#if>
					</td>
				</tr>
				</#if>
				</#list>
			</table>
		</form>
	</div>
</body>
</html>