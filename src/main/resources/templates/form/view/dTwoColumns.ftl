<table class="table-detail column_2">
	<thead>
		<tr>
			<td colspan="4" align="center"><h2>[XX]详细信息</h2></td>
		</tr>
	</thead>
	<tbody>
	  <#assign index=0>
	  <#list controls as control >
	  <#if index % 2==0>
	  <tr>
	  </#if>
		<th class="p15">${control.name}</th>
		<td class="p35"><${control.tag} plugins="${control.editcontrol}" class="rcx ${control.editcontrol}" name="${control.key}" label="${control.name}"  mwidth="0" wunit="px" mheight="0" hunit="px"></td>
	  <#if index % 2==1>
	  </tr>
	  </#if>
	  <#assign index=index+1>
	  </#list>
  </tbody>
</table>
