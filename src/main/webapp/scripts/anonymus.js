/**
 * 显示上传图片对话框
 */
function _UploadSingleImg(config){
	mini.open({
        allowResize: true, //允许尺寸调节
        allowDrag: true, //允许拖拽位置
        showCloseButton: true, //显示关闭按钮
        showMaxButton: true, //显示最大化按钮
        showModal: true,
        url: __rootPath+"/pub/anony/imgUploadDialog.do",
        title: "图片上传", width: 600, height: 420,
        onload: function() {
			
        },
        ondestroy: function(action) {
            if (action == 'ok') {
                var iframe = this.getIFrameEl();
                var files = iframe.contentWindow.getFiles();
                if(config.callback){
                    config.callback.call(this,files);
                }
            }
        }
    });
}