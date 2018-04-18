<div class="table-container">
	<table class="table-list blue" >
		<caption>${ent.comment}</caption>
		<thead>
			<tr >		
				<#list ent.sysBoAttrs as attr>			
					<th >${attr.comment}</th>		
				</#list>			
			</tr>
		</thead>
		<tbody>
			<tr rx-list="SUB_${ent.name}">				
				<#list ent.sysBoAttrs as attr>		
					<td >
						<@getField attr=attr />
					</td>		
				</#list>	
			</tr>
		</tbody>
	</table>
</div>