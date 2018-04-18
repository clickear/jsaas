/**
 * 当在页面中使用了ueditor,即可以通过引入该文件即可，使用时，只需要在对应的textarea控件中配置样式rich-editor
 * 
 */
$(function(){
	$('textarea.rich-editor').each(function(){
  		var obj=$(this);
  		$(this).replaceWith('<script id="'+obj.attr('id') + '" name="'+ obj.attr('name') + '" style="'+obj.attr('style') +'" rich-type="ueditor" type="text/plain">'+obj.html()+'</script>');
  	});
	
	//加载需要的ueditor需要的Js
	var jsArr=[
		__rootPath+'/scripts/ueditor/ueditor.config.js',
		__rootPath+'/scripts/ueditor/ueditor.all.min.js',
		__rootPath+'/scripts/ueditor/lang/zh-cn/zh-cn.js'
	];
	$.getScripts({
		urls: jsArr,
		  cache: false,  // Default
		  async: false, // Default
		  success: function(response) {
				$("script[rich-type='ueditor']").each(function(){
					var id=$(this).attr('id');
					UE.getEditor(id);
				});
		  }
	});
	
});