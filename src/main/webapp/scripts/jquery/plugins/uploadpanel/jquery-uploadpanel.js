/**
 * 定义上传控件面板
 */
;(function(){
		
	var UploadPanel = function(ele) {
	    this.$element = ele;
	    //默认的参数配置
	    //获得的$lement的所有的atts;
	    var defaulAttrs={
	    	fileids:'',
	    	filenames:'',
	    	filebytes:'',
	    	style:'',
	    	id:parseInt(1000*Math.random())
	    };
	    var attrs={};
	    var r = this.$element.get(0);
        if (r) {
            r = r.attributes;
            for (var i in r) {
                var p = r[i];
                if (p!=null  && typeof p.nodeValue !== 'undefined') attrs[p.nodeName] = p.nodeValue;
            }
        }
	    this.attrs = $.extend({}, defaulAttrs,attrs); 
	};
	
	UploadPanel.prototype = {
	    getFileIds: function() {
	        return this.panel.children('input[name="'+this.attrs['name']+'"]').val();
	    },
	    getFileNames:function(){
	    	return this.panel.children('input[name="'+this.attrs['fname']+'"]').val();
	    }, 
	    //上传处理
	    uploadHandler:function(){
	    	var control=this;
	    	var isonlyone =false;
			if(this.attrs.onlyone=='true'){
				isonlyone = true;
			}
	    	_UploadFileDlg({
				from:'SELF',
				types:this.attrs.uploadtype,
				single:false,
				onlyOne:isonlyone,
				showMgr:false,
				callback:function(files){
					var fnames=[];
					var fids=[];
					var fbytes=[];
					var fuids=control.fileIds.val();
					for(var i=0;i<files.length;i++){
						if(fuids.indexOf(files[i].fileId)!=-1){
							continue;
						}
						fids.push(files[i].fileId);
						fnames.push(files[i].fileName);
						fbytes.push(files[i].totalBytes);
					}
					if(fids.length==0){
						return;
					}
					
					control.appendFiles.call(control,fids,fnames,fbytes,"true");
					
					var fileIds=control.fileIds.val();
					if(fileIds!=''){
						//var newFids=control.fileIds.val().split(',').concat(fids);
						//var newFNames=control.fileNames.val().split(',').concat(fnames);
						//var newFBytes = control.fileBytes.val().split(',').concat(fbytes);
						
						var newJsonAry = [];
				    	for(var i=0;i<fids.length;i++){
				    		var obj={};
				    		obj.id=fids[i];
				    		obj.name=fnames[i];
				    		obj.byte=fbytes[i];
				    		newJsonAry.push(obj);
				    		
				    	}
				    	var newJsonString = JSON.stringify(newJsonAry);
						
						//control.fileBytes.val(newFBytes.join(','));
						//control.fileNames.val(newFNames.join(','));
						control.fileIds.val(newJsonString);
					}else{
						//control.fileBytes.val(fbytes.join(','));
						var newJsonAry = [];
				    	for(var i=0;i<fids.length;i++){
				    		var obj={};
				    		obj.id=fids[i];
				    		obj.name=fnames[i];
				    		obj.byte=fbytes[i];
				    		newJsonAry.push(obj);
				    		
				    	}
				    	var newJsonString = JSON.stringify(newJsonAry);
				    	control.fileIds.val(newJsonString);
						//control.fileNames.val(fnames.join(','));
					}
				}
			});
	    },
	    
	    closeFile:function(e){
	    	var control=e.data.control;
	    	var fid=e.data.fid;
	    	control.ul.children('li[id="item_$'+fid+'$"]').remove();
	    	var fIds=control.fileIds.val();
	    	//var fNames=control.fileNames.val();
	    	var ids=fIds.split(',');
	    	//var fnames=fNames.split(',');
	    	
	    	for(var i=0;i<ids.length;i++){
	    		if(ids[i]==fid){
	    			ids.splice(i,1);
	    			//fnames.splice(i,1);
	    			break;
	    		}
	    	}
	    	control.fileIds.val(ids.join(','));
	    	//control.fileNames.val(fnames.join(','));
	    },
	    //清空处理器
	    clearHandler:function(){
	    	var control=this;
	    	control.ul.empty('');
	    	control.fileIds.val('');
	    	//control.fileNames.val('');
	    }, 
	    selectAll:function(ck){
	    	 if(ck.checked){ 
	    		 $(ck).parent().find("ul").find("li").find(".fileCheck").prop("checked",true); 
	    		}else{
	    			$(ck).parent().find("ul").find("li").find(".fileCheck").prop("checked", false); 
	    	} 		    	
	    },
	    downAll:function(ck){
	    	 var obj = $(ck).parent().find("ul").find("li").find(".fileCheck");  
	    	 var fileIds = [];   
	    	 for(k in obj){  
	    		 if(obj[k].checked){
	    			 fileIds.push(obj[k].id);
	    			 }
	    	}  
	    	 if(fileIds!=""){
	    		 url=__rootPath+"/sys/core/file/downloadFiles.do";	
	    		 jQuery.download(url, {fileIds:fileIds}, "post"); 
	    	 }
	    },
	    //插件文件
	    appendFiles:function(fIds,fNames,fBytes,isclear){
	    	//把字段进行做分隔
	    	if(isclear=="true"){
	    		this.clearHandler.call(this);
	    	}
	    	var isDown = true;
	    	var isPrint =true;
	    	if(this.attrs.isdown=='false'){
	    		isDown = false;
	    	}
	    	if(this.attrs.isprint=="false"){
	    		isPrint = false;
	    	}
	    	
	    	for(var i=0;i<fIds.length;i++){
	    		if(!fNames[i] || fNames[i]==''){
	    			continue;
	    		}
	    		var kbs = Math.ceil(fBytes[i]/1024);
	    		var checkBox=$('<input type="checkbox" id="'+fIds[i]+'" name="fileCheckBox"  class="fileCheck" ></input>');
	    		var fileName = $('<span style="color:#4f50f2;width:500px;display:-moz-inline-box;display:inline-block;">' +fNames[i] + '</span><span style="padding-right:25px;color:#aaa;">('+kbs+'KB)</span>');
	    		var downBtn =$('');
	    		var printBtn = $('');
	    		if(isDown){
	    			downBtn = $('<a target="_blank" href="'+__rootPath+'/sys/core/file/previewFile.do?fileId='+fIds[i]+'"><image style="margin-right:15px;cursor:pointer;border:0;"  src="'+__rootPath+'/styles/icons/download.png"  title="下载"/></a>');
	    		}
	    		if(isPrint){
	    			printBtn = $('<a target="_blank" href="'+__rootPath+'/sys/core/file/previewOffice.do?print=true&fileId='+fIds[i]+'"><image style="margin-right:15px;cursor:pointer;border:0;"  src="'+__rootPath+'/styles/icons/printer.png"  title="打印"/></a>');
	    		}
	    		var btn = $('<a target="_blank" href="'+__rootPath+'/sys/core/file/previewOffice.do?fileId='+fIds[i]+'"><image style="margin-right:15px;cursor:pointer;border:0;"  src="'+__rootPath+'/styles/icons/book_open.png"  onclick="" title="查看"/></a>').append(downBtn).append(printBtn);
	    		var li=$('<li id="li_'+fIds[i]+'"></li>').append(checkBox).append(fileName).append(btn);
	    		this.ul.append(li);
	    	}
	    },
	    
	    //下载处理器
	    downHandler:function(){
	    	var control=this;
	    	var fileIds=control.fileIds.val();
	    	if(fileIds!=''){
	    		url=__rootPath+'/sys/core/file/downloadFiles.do';
	    		jQuery.download(url, {fileIds:fileIds}, 'post');
	    	}
	    },
	    //展示
	    render:function(){
	        this.panel=$('<div></div>');

	        if (this.attrs) {
                for (var a in this.attrs) {
                	this.panel.attr(a, this.attrs[a]);
                }
	        }
	        this.readonly=this.attrs['onlyread'];
	        this.$element.replaceWith(this.panel);
	        
	        
	        if(this.readonly!='true'){
		    	//允许上传按钮显示
	        	if(this.attrs.allowupload=='true'){
		    		this.uploadBtn=new mini.Button();
		    		this.uploadBtn.setIconCls('icon-upload');
		    		this.uploadBtn.setText('上传');
		    		this.uploadBtn.setPlain(true);
		    		this.uploadBtn.render(this.panel[0]);
		    		this.uploadBtn.on('click',this.uploadHandler,this);
		    		
		    		this.clearBtn=new mini.Button();
		    		this.clearBtn.setIconCls('icon-remove');
		    		this.clearBtn.setText('清空');
		    		this.clearBtn.setPlain(true);
		    		this.clearBtn.render(this.panel[0]);
		    		this.clearBtn.on('click',this.clearHandler,this);
		    	}
		    	//允许打包下载
/*		    	if(this.attrs.zipdown=='true'){
		    		this.downBtn=new mini.Button();	    		
		    		this.downBtn.setIconCls('icon-download');
		    		this.downBtn.setText('下载');
		    		this.downBtn.setPlain(true);
		    		this.downBtn.render(this.panel[0]);
		    		this.downBtn.on('click',this.downHandler,this);
		    	}*/
	        }

	    	//this.ul=$("<ul class='list-item'></ul>");
	        var rId = this.attrs['id'];
	        
	        if(this.attrs.isdown=='true'){
	        	this.ckSelectAll=$("<input type='checkbox'>全选 ");
	        	this.downloadBtn=$("<a href='javascript:void(0);'  style='margin-left:10px;'>批量下载</a>");
	        }
	    	this.ul=$('<ul id="files" style="padding-left: 0px;"></ul>');
	    	var showfileIds=this.attrs['fileids'];
	    	var showfileNames=this.attrs['filenames'];
	    	var showfileBytes = this.attrs['filebytes'];

	    	//生成文件IDs
	    	
	        if(this.attrs.isdown=='true'){
	        	this.panel.append(this.ckSelectAll);
	        	this.panel.append(this.downloadBtn);
	        }
	    	this.panel.append(this.ul);
	    	//加上rx-control为的是在表单进行存储时，可以有效显示值
	    	//this.fileNames=$("<input class='rx-control' type='hidden' name='"+this.attrs.fname+"'/>");
	    	//this.fileNames.val(this.attrs.filenames);
	    	this.fileIds=$("<input class='rx-control' type='hidden' name='"+this.attrs.name+"'/>");
	    	var fIds=showfileIds.split(",");
	    	var fNames=showfileNames.split(",");
	    	var fBytes = showfileBytes.split(",");
	    	var jsonAry = [];
	    	for(var i=0;i<fIds.length;i++){
	    		var obj={};
	    		obj.id=fIds[i];
	    		obj.name=fNames[i];
	    		obj.byte=fBytes[i];
	    		jsonAry.push(obj);
	    		
	    	}
	    	var jsonString = JSON.stringify(jsonAry);
	    	this.fileIds.val(jsonString);
	    	//this.fileBytes=$("<input class='rx-control' type='hidden' name='"+this.attrs.fbyte+"'/>");
	    	//this.fileBytes.val(this.attrs.filebytes);
	    	this.appendFiles.call(this,fIds,fNames,fBytes,false);
	    	var ctrl=this;


		     if(this.attrs.isdown=='true'){
		    	 this.ckSelectAll.on('click',function(){
			    		var ck=this;
			    		ctrl.selectAll.call(ctrl,ck);
			    	});
		    	 this.downloadBtn.on('click',function(){
		    		 var btn=this;
		    		 ctrl.downAll.call(ctrl,btn);
		    	 });
		     }
	    	
	    	this.panel.append(this.fileNames);
	    	this.panel.append(this.fileIds);
	    	this.panel.append(this.fileBytes);
	    	this.panel.after(this.jsDesp);
	    	return this.panel;
	    }
	};
	//在插件中使用UploadPanel对象
	$.fn.uploadPanel = function(options) {
	    //创建Beautifier的实体
	    var panel = new UploadPanel(this, options);
	    //调用其方法进行展示
	    panel.render();
	    
	    return panel;
	};
})();