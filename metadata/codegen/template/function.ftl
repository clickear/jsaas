<#--获取查询数据类型-->
<#function getDataType colType start>
<#if (colType=="long") > <#return "L">
<#elseif (colType=="int")><#return "N">
<#elseif (colType=="double")><#return "BD">
<#elseif (colType=="Short")><#return "SN">
<#elseif (colType=="Date" && start=="1")><#return "DL">
<#elseif (colType=="Date" && start=="0")><#return "DG">
<#else><#return "SL"></#if>
</#function>

<#--将字符串 user_id 转换为 类似userId-->
<#function convertUnderLine field>
<#if field?index_of("_")==-1>
<#return field>
</#if>

<#if field?index_of("F_")==0>
	<#assign rtn><#list (field?substring(2))?split("_") as x><#if (x_index==0)><#if x?length==1>${x?upper_case?trim}<#else>${x?lower_case?trim}</#if><#else>${x?lower_case?cap_first?trim}</#if></#list></#assign>
	<#return rtn>
</#if>

<#assign rtn><#list field?split("_") as x><#if (x_index==0)><#if x?length==1>${x?upper_case?trim}<#else>${x?lower_case?trim}</#if><#else>${x?lower_case?cap_first?trim}</#if></#list></#assign>
 <#return rtn>
</#function>


<#--判断是否有子表-->
<#function hasSubTable model>
<#assign subtables=model.subTableList>
<#assign rtn><#if (subtables?exists && subtables?size!=0)>1<#else>0</#if></#assign>
 <#return rtn>
</#function>

<#function getPk model>
<#assign rtn><#if (model.pkModel??) >${model.pkModel.columnName}<#else>id</#if></#assign>
 <#return rtn>
</#function>

<#--获取主键类型-->
<#function getPkType model>
<#list model.columnList as col>
<#if col.isPK>
<#if (col.colType=="Integer")><#assign rtn>"Long"</#assign><#return rtn>
<#else><#assign pkType=col.colType ></#if>
</#if>
</#list>
<#assign rtn>${pkType}</#assign>
<#return rtn>
</#function>

<#--获取外键类型 没有则返回Long-->
<#function getFkType model>
<#assign fk=model.foreignKey>
<#list model.columnList as col>
<#if (col.columnName?lower_case)==(fk?lower_case)>
	<#if (col.colType=="Integer")><#assign rtn>Long</#assign><#return rtn><#else><#assign rtn>${col.colType}</#assign><#return rtn></#if>
</#if>
</#list>
<#assign rtn>Long</#assign><#return rtn>
</#function>

<#function getPkVar model>
<#assign rtn><#if (model.pkModel??) ><#noparse>${</#noparse>${convertUnderLine(model.pkModel.columnName)}<#noparse>}</#noparse><#else>id</#if></#assign>
 <#return rtn>
</#function>


<#function getJdbcType dataType>
<#assign dbtype=dataType?lower_case>
<#assign rtn>
<#if  dbtype?ends_with("int") || (dbtype=="double") || (dbtype=="float") || (dbtype=="decimal") || dbtype?ends_with("number")||dbtype?starts_with("numeric") >
NUMERIC
<#elseif (dbtype?index_of("char")>-1)  >
VARCHAR
<#elseif (dbtype=="date")>
DATE
<#elseif (dbtype?index_of("timestamp")>-1)  || (dbtype=="datetime") >
TIMESTAMP
<#elseif (dbtype?ends_with("text") || dbtype?ends_with("clob")) >
CLOB
</#if></#assign>
 <#return rtn?trim>
</#function>

<#--是否为需要排除的列-->
<#function isExcludeField colName>
<#if colName!="createBy" && colName!="createTime" && colName!="updateBy" && colName!="updateTime" && colName!="tenantId" >
	<#return true>
<#else>
	<#return false>
</#if>
</#function>
