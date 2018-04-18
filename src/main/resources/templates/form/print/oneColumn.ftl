<div class="sperator"></div>
<table class="form-detail column-two blue">
      	<caption>${ent.comment}</caption>
		<#list ent.sysBoAttrs as attr>	
			<tr>		
				<th>${attr.comment}</th>		
				<td>
					<@getField attr=attr />
				</td>	
			</tr>	
		</#list>
</table>