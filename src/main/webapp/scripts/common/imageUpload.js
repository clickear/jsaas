function ShowImageUploadDialog(config){
    mini.open({
        allowResize: true, //允许尺寸调节
        allowDrag: true, //允许拖拽位置
        showCloseButton: true, //显示关闭按钮
        showMaxButton: true, //显示最大化按钮
        showModal: true,
        //只允许上传图片，具体的图片格式配置在config/fileTypeConfig.xml
        url: "${ctxPath}/sys/core/sysFile/dialog.do?from=APPLICATION&type=Image",
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