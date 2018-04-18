<script src="${ctxPath}/scripts/jquery-1.11.3.js" type="text/javascript"></script>
<link href="${ctxPath}/scripts/kms/homePic/jquery.slideBox.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/kms/homePic/jquery.slideBox.js" type="text/javascript"></script>
<script type="text/javascript">
	function show(docId,title){
		top['index']._OpenWindow({
				url : __rootPath+"/kms/core/kdDoc/show.do?docId=" + docId,
				title : title,
				width : 800,
				height : 800,
				max:true,
		});		
	}
	
	jQuery(function($) {
		$('#demo1').slideBox();
	});
</script>
<div id="demo1" class="panel-body slideBox">
	<ul class="items">
		<#list result as item>
		    <li style="width: 700px; height: 250px;"><!-- 这里是内部图标 -->
		        <p><a href="javascript:show('${item.docId}','${item.title}')" title="${item.title}"><img src="${ctxPath}${item.picUrl}"></a></p>
		    </li>
		</#list>
	</ul>
</div>
