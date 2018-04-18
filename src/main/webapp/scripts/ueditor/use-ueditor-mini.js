/**
 * 当在页面中使用了kindeditor,即可以通过引入该文件即可，使用时，只需要在对应的textarea控件中配置样式rich-editor
 * 
 */
$(function(){
	//加载需要的ueditor需要的Js
	var jsArr=[
		__rootPath+'/scripts/ueditor/ueditor-mini.config.js',
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