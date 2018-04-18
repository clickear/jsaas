<script type="text/javascript">
	function show(msgId){
		top['index']._OpenWindow({
					url : __rootPath+"/oa/info/infInbox/recPortalGet.do?pkId=" + msgId,
					width : 500,
					height : 350,
					title : "消息内容",
					ondestroy : function(action) {
						}
	        	});
	}
</script>
<div class="panel-body">
	<ul>
		<#list result as item>
		    <li class="commute"><!-- 这里是内部图标 -->
		        <p><a href="javascript:show('${item.msgId}')">${item.content}</a></p>
		        <span>${item.createTime?string('yyyy-MM-dd')}</span>
		    </li>
		</#list>
	</ul>
</div>
