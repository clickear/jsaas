<#function render opinionAry>
	<#assign rtn="" >
	<#if (opinionAry?? && opinionAry?size>0)>
		<#assign rtn>
		<ul>
		<#list opinionAry as opinion>
			<li>${opinion.opinion}  </li>
			<li>审批人: ${opinion.userName} 审批时间:${opinion.createTime}</li>
		</#list>
		</ul>
		</#assign>
	</#if>
	<#return rtn>
</#function> 