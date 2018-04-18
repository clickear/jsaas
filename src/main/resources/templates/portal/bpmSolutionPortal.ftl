<script type="text/javascript">
	function onStart(solId,name){
		top['index']._OpenWindow({
			title:name+'-流程启动',
			url:__rootPath+'/bpm/core/bpmInst/start.do?solId='+solId,
			max:true,
			height:400,
			width:800,
		});
	}
</script>
<div class="panel-body">
	<ul>
		<#list result as item>
		    <li class="start">
		        <p><a href="javascript:onStart('${item.solId}','${item.name}')">${item.name}</a></p>
		        <span>${item.createTime?string('yyyy-MM-dd')}</span>
		    </li>
		</#list>
	</ul>
</div>
