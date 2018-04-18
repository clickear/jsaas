<script type="text/javascript">
	function show(newId,subject){
		top['index']._OpenWindow({
				url : __rootPath+"/oa/info/insNews/get.do?permit=no&pkId=" + newId,
				title : subject,
				width : 800,
				height : 800,
			//max:true,
		});		
	}
</script>
<div class="panel-body">
	<ul>
		<#list result as item>
		    <li class="doc"><!-- 这里是内部图标 -->
		        <p><a href="javascript:show('${item.newId}','${item.subject}')">${item.subject}</a></p>
		        <span>${item.createTime?string('yyyy-MM-dd')}</span>
		    </li>
		</#list>
	</ul>
</div>
