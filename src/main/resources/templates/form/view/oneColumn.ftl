<table class="table-def column_1">
<tr>
	<td colspan="2" align="center"><h2>[XX]申请表</h2></td>
</tr>
  <#list controls as control >
  <tr>
    <th class="thead">${control.fieldLabel}</th>
    <td class="tfield">${control.controlHtml}</td>
  </tr>
  </#list>
</table>