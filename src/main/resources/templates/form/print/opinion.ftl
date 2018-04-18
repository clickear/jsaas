<table class="form-detail column-two blue">
		<#list opinions as opinion>	
		<tr>		
			<th >${opinion.label}</th>		
			<td ><#noparse>${render(opinion.</#noparse>${opinion.name})}</td>		
		</tr>
		</#list>
</table>


