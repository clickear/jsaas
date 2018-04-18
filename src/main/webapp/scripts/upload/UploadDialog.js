/**
 * 打开简单的通用的上传对话框
 * @param conf
 */
function SimpleUploadDialog(conf){
	if(!conf)conf={};
	//文件扩展名,如jpg,gif,png
	var fileExt=conf.fileExt?conf.fileExt:'*';
	//文件大小
	var maxSize=conf.maxSize?conf.maxSize:"5mb";
	
	var defaultConf={
			URL : __ctx+"/sys/core/sysFile/simpleUploadDlg.mi?fileExt="+fileExt+"&maxSize=" + maxSize,
			Title : "上传文件",
			ShowMaxButton : true,
			ShowMinButton : true,
			Width : 800,
			Height : 330,
			ShowButtonRow:true,
			OKEvent :function(){
				//进行回调
				if(newConfig.callback){
					var files = dlg.innerFrame.contentWindow.getUploadedFiles();
					newConfig.callback.call(this,files);
				}
		        dlg.close();
		    }
	};
	var newConfig=jQuery.extend({},defaultConf,conf);
	var dlg=new top.Dialog(newConfig);
	dlg.show();
}
/**
 * 图片文件上传对话框
 */
function ImageUploadDialog(conf){
	if(!conf)conf={};
	//文件扩展名,如jpg,gif,png
	var defConf={
			Title:'图片上传',
			fileExt:'jpg,gif,png,bmp',
			maxSize:'10mb'
	};
	
	var newConfig=jQuery.extend(defConf,conf);
	SimpleUploadDialog(newConfig);
}