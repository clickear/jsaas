<div class="sperator"></div>
<table class="form-detail column-four blue">
	<caption>${ent.comment}</caption>
	<#assign index=0>
	<#list ent.sysBoAttrs as attr>	
			<#if index % 2 == 0>
			<tr>
			</#if>
				<th>${attr.comment}</th>
				<td>
					<@getField attr=attr />
				</td>
				<#if index % 2 == 0 && !attr_has_next>
					<th></th>
					<td></td>
				</#if>
			<#if index % 2 == 1 || !attr_has_next>
			</tr>
			</#if>
			<#assign index=index+1>
	</#list>
</table>