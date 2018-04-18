<table class="table-def column_2">
<tr>
	<td align="center" colspan="4"><h2>[XX]申请表</h2></td>
</tr>
  <#assign index=0>
  <#list controls as control >
  <#if index % 2==0>
  <tr>
  </#if>
	<th class="thead">${control.fieldLabel}</th>
	<td class="tfield">${control.controlHtml}</td>
  <#if index % 2==1>
  </tr>
  </#if>
  <#assign index=index+1>
  </#list>
</table>