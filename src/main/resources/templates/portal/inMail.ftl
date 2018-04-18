<script type="text/javascript">
	function show(mailId,subject){
		top['index']._OpenWindow({
				url : __rootPath+"/oa/mail/innerMail/get.do?pkId=" + mailId +"&folderId=${folderId}&isHome=YES",
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
		    <li class="mail"><!-- 这里是内部图标 -->
		        <p><a href="javascript:show('${item.mailId}','${item.subject}')">${item.subject}</a></p>
		        <span>${item.createTime?string('yyyy-MM-dd')}</span>
		    </li>
		</#list>
	</ul>
</div>
