<table class="table-detail column_1">
<thead>
<tr>
	<td colspan="2" align="center"><h2>[XX]详细信息</h2></td>
</tr>
</thead>
<tbody>
  <#list controls as control >
  <tr>
    <th class="p35">${control.name}</th>
    <td class="p65">
    	<#if control.tag=="input">
    		<${control.tag} plugins="${control.editcontrol}" class="rcx ${control.editcontrol}" name="${control.key}" label="${control.name}"  mwidth="0" wunit="px" mheight="0" hunit="px" />
    	<#else>
    		<${control.tag} plugins="${control.editcontrol}" class="rcx ${control.editcontrol}" name="${control.key}" label="${control.name}"  mwidth="0" wunit="px" mheight="0" hunit="px" ></${control.tag}>
    	</#if>
    	</td>
  </tr>
  </#list>
  </tbody>
</table>