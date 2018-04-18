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
    Document   : [${comment}]列表页
    Created on : ${date?string("yyyy-MM-dd HH:mm:ss")}
    Author     : ${vars.developer}
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[${comment}]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create" plain="true" onclick="add()">增加</a>
                     <a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
                     <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
                 </td>
             </tr>
              <tr>
                  <td class="search-form" >
                     <ul>
					<#list commonList as col>
					<#assign colName=func.convertUnderLine(col.columnName)>
					<#if func.isExcludeField( colName) >
					<#if (col.colType=="java.util.Date")>
						<li>
							<span>${col.comment} 从</span>:<input  name="Q_${col.columnName}_D_GE"  class="mini-datepicker" format="yyyy-MM-dd" style="width:100px"/>
						</li>
						<li>
							<span>至: </span><input  name="Q_${col.columnName}_D_LE" class="mini-datepicker" format="yyyy-MM-dd" style="width:100px" />
						</li>
					<#else>
						<li><span>${col.comment}:</span><input class="mini-textbox" name="Q_${col.columnName}_S_LK"></li>
					</#if>
					</#if>
					</#list>
					</ul>
                  </td>
              </tr>
         </table>
     </div>
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="<#noparse>${</#noparse>ctxPath<#noparse>}</#noparse>/${system}/${package}/${classVar}/listData.do" idField="${pkVar}"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="50" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<#list commonList as col>
				<#assign colName=func.convertUnderLine(col.columnName)>
				<#if func.isExcludeField( colName) >
				<#if (col.colType=="java.util.Date")>
				<div field="${func.convertUnderLine(col.columnName)}" sortField="${col.columnName}" dateFormat="yyyy-MM-dd HH:mm:ss" width="120" headerAlign="center" allowSort="true">${col.comment}</div>
				<#else>
				<div field="${func.convertUnderLine(col.columnName)}"  sortField="${col.columnName}"  width="120" headerAlign="center" allowSort="true">${col.comment}</div>
				</#if>
				</#if>
				</#list>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
					+'<span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>'
					+'<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
		}
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="${domain}.${system}.${package}.entity.${class}" winHeight="450"
		winWidth="700" entityTitle="${comment}" baseUrl="${system}/${package}/${classVar}" />
</body>
</html>