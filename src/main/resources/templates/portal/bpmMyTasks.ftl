<script type="text/javascript">
	function doTask(taskId,taskName){
		top['index']._OpenWindow({
	        		title:'任务办理-'+taskName,
	        		height:600,
	        		width:860,
	        		max:true,
	        		url:__rootPath+'/bpm/core/bpmTask/toStart.do?fromMgr=true&taskId='+taskId,
	        		ondestroy:function(action){
	        			if(action!='ok') return;
	        			location.reload();
	        		}
	       });
	}
</script>
<div class="panel-body">
	<ul>
		<#list result as item>
		    <li class="task">
		        <p><a href="javascript:doTask('${item.id}','${item.description}')">
		        <#if item.description ?length gt 20>
		        ${item.description?substring(0,20)}...
		        <#else>
		        ${item.description}
		        </#if>
		        </p>
		        </a>
		        <span>${item.createTime?string('yyyy-MM-dd')}</span>
		    </li>
		</#list>
	</ul>
</div>
