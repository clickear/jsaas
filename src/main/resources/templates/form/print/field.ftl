<#macro getField attr>
	<#switch attr.control>
		<#case "upload-panel">
			<#noparse>${util.displayFile(data.</#noparse>${attr.name}<#noparse>)}</#noparse>
		<#break>
		<#case "mini-office">
			<#noparse>${util.displayOffice(data.</#noparse>${attr.name}<#noparse>)}</#noparse>
		<#break>
		<#case "mini-img">
			<#noparse>${util.displayImg(data.</#noparse>${attr.name}<#noparse>)}</#noparse>
		<#break>
		<#case "mini-datepicker">
			<#noparse>${util.displayDate(data.</#noparse>${attr.name}<#noparse>)}</#noparse>
		<#break>
		
		
		<#default>
			<#if (attr.isSingle==1)>
				<#noparse>${data.</#noparse>${attr.name}<#noparse>}</#noparse>
			<#else>
				<#noparse>${data.</#noparse>${attr.name}_name<#noparse>}</#noparse>
			</#if>
	</#switch>
	
</#macro>
