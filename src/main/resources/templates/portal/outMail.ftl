<script type="text/javascript">
	function show(mailId,subject){
		top['index']._OpenWindow({
			url : "${ctxPath}/oa/mail/outMail/get.do?pkId=" + mailId +"&isHome=YES",
			width : 1000,
			height : 800,
			title : "外部邮件",
			ondestroy : function(action) {
			}
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
