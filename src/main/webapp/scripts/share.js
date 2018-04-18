 /**
 * 关闭窗口
 * @returns {unresolved}
 */
function CloseWindow(action,preFun,afterFun) {
	
	if(preFun){
		preFun.call(this);
	}
    if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
    else window.close();
    if(afterFun){
		afterFun.call(this);
	}
    
    if( $('.top_tab_box .active i', parent.document).length ){
    	$('.top_tab_box .active i', parent.document ).trigger("click");
    }
}

//对buttonedit开启清空事件，需要在buttonedit控件中增加showClose="true" 属性，并且oncloseclick="_OnButtonEditClear"
function _OnButtonEditClear(e){
	var s=e.sender;
	s.setValue('');
	s.setText('');
}
/**
 * 上移表格的行
 */
function upRowGrid(gridId) {
	var grid=mini.get(gridId);
    var row = grid.getSelected();
    if (row) {
        var index = grid.indexOf(row);
        grid.moveRow(row, index - 1);
    }
}
/**
 * 下移表格的行
 */
function downRowGrid(gridId) {
	var grid=mini.get(gridId);
	var row = grid.getSelected();
    if (row) {
        var index = grid.indexOf(row);
        grid.moveRow(row, index + 2);
    }
}
/**
 * 添加行
 * @param gridId
 * @param row
 */
function addRowGrid(gridId,row){
	var grid=mini.get(gridId);
	if(!row){
		row={};
	}
	grid.addRow(row);
}
/**
 * 删除选择行
 * @param gridId
 */
function delRowGrid(gridId){
	var grid=mini.get(gridId);
	var selecteds=grid.getSelecteds();
	grid.removeRows(selecteds);
}

/**
 * 清空所有行
 * @param gridId
 */
function delAll(gridId){
	
	var grid=mini.get(gridId);
	grid.setData();
	
}



/**
 * 打开窗口
 * @param config
 *         url 为打开窗口的内部地址
 *         title:窗口标题
 *         height:高
 *         width:宽
 *         onload：加载事件
 *         ondestory:关闭的事件
 */
function _OpenWindow(config){
	
	if(!config.iconCls){
		config.iconCls='icon-window';
	}
	
	if(typeof(config.allowResize)=='undefined'){
		config.allowResize=true;
	}
	
	if(typeof(config.showMaxButton)=='undefined'){
		config.showMaxButton=true;
	}
	if(typeof(config.showModel)=='undefined'){
		config.showModel=true;
	}
	
	var win=mini.open({
		iconCls:config.iconCls,
        allowResize: config.allowResize, //允许尺寸调节
        allowDrag: true, //允许拖拽位置
        showCloseButton: true, //显示关闭按钮
        showMaxButton: config.showMaxButton, //显示最大化按钮
        showModal: config.showModel,
        url: config.url,
        title: config.title, 
        width:config.width, 
        height: config.height,
        onload: function() {
        	if(config.onload){
        		config.onload.call(this);
        	}
        },
        ondestroy:function(action){
        	if(config.ondestroy){
        		config.ondestroy.call(this,action);
        	}
        }
    });
	var flag=false;
	var el = win.getHeaderEl();
	$(el).dblclick(function () {			
		if (!flag) {
			win.max();
			mini.layout();
			flag = true;
		} else {
			win.restore();
			flag = false;
		}
	});

	if(config.max){
		win.max();
		mini.layout();
		flag=true;
	}
}

/**
 * 通用对话框，与_OpenWindow一样，唯一特别是需要传入
 * @param config
 */
function _CommonDialog(conf){
	var config=setConf(conf);
	_OpenWindow({
		title:config.title,
		height:config.height,
		width:config.width,
		url:__rootPath+'/dev/cus/customData/'+config.dialogKey+'/dialog.do',
		onload:function(){
			if(config.onload){
				config.onload.call(this);
			}
		},
		ondestroy:function(action){
			if(action!='ok') return;
			if(config.ondestroy){
				config.ondestroy.call(this);
			}
		}
	});
}

function setConf(conf){
	var dialogKey=conf.dialogKey;
	var url=__rootPath+'/dev/cus/customData/config/'+dialogKey+'.do';
	var obj=$.ajax({
		  url: url,
		  async: false,
		  dataType:"json"
		});
	var json = obj.responseJSON; 
	if(!json){
		json=mini.decode(obj.responseText);
	}
	var config={width:json.width,height:json.height,title:json.name};
	$.extend(config,conf);
	return config;
}

/**
 * 功能说明:
 * 打开自定义对话框。
 * {dialogKey:"dataSourceDialog",ondestroy:function(data){
	    		   var row=data[0];
	    		   btnEdit.setText(row.NAME_);
	    		   btnEdit.setValue(row.ALIAS_);
	},params:"",signle:"true"}
	dialogKey:对话框key。
	ondestroy:返回参数。
	params:对话框参数 格式为:name1=value1&name2=value2
 * 	single : 是否单选 ("true","false")
 * @param conf
 * @returns
 */
function _CommonDialogExt(conf){
	var config=setConf(conf);
	
	var url=__rootPath+'/dev/cus/customData/'+config.dialogKey+'/dialog.do';
	if(conf.params){
		url+="?" + conf.params;
	}
	if(conf.single){
		if(url.indexOf("?")==-1){
			url+="?single=" +conf.single;
		}
		else{
			url+="&single=" +conf.single;
		}

	}

	_OpenWindow({
		title:config.title,
		height:config.height,
		width:config.width,
		url:url,
		onload:function(){
			if(config.onload){
				config.onload.call(this);
			}
		},
		ondestroy:function(action){
			if(action!='ok') return;
			var win=this.getIFrameEl().contentWindow;
			var data=win.getData();
			if(config.ondestroy){
				config.ondestroy(data);
			}
		}
	});
}

/**
 * 自定义对话框控件中的buttonedit控件调用
 * @param e
 */
function _OnMiniDialogShow(e){
	var button=e.sender;
	var dialogalias=button.dialogalias;
	var dialogname=button.dialogname;
	var valueField=button.valueField;
	var textField=button.textField;

	_CommonDialog({
		title:dialogname,
		height:450,
		width:800,
		dialogKey:dialogalias,
		ondestroy:function(action){
			var win=this.getIFrameEl().contentWindow;
			var data=win.getData();
			if(data!=null && data.length>0){
				data=data[0];
				button.setText(data[textField]);
				button.setValue(data[valueField]);
			}
		}
	});
}
/**
 * 获得Grid中的选择中的所有主键ID
 * @param gridId
 * @returns {Array}
 */
function _GetGridIds(gridId){
	var grid=mini.get('#'+gridId);
	 var rows = grid.getSelecteds();
	 var ids = [];
	 if (rows.length > 0) {
	     for (var i = 0, l = rows.length; i < l; i++) {
	         var r = rows[i];
	         ids.push(r.pkId);
	     }
	 }
	 return ids;
}

/**
 * 提交JSON信息
 * @param config
 *         url：必需，handle the url
 *         method:'POST' or 'GET' ,default is 'GET'
 *         data: data of the json ,such as {'field1':1,'feld2':2}
 *         success:成功返回时调用的函数，格式如：function(result){alert(result);}    
 */
function _SubmitJson(config){
	if(!config) return;
	if(!config.url) return;
	if(!config.method)config.method='POST';
	if(!config.data) config.data={};
	
	if(!config.submitTips){
		config.submitTips='正在处理...';
	}

	var msgId=null;
	//显示提交数据的进度条
	if(typeof(config.showProcessTips)=='undefined'){
		config.showProcessTips=true;
	}
	
	if(config.showProcessTips){
		msgId=mini.loading(config.submitTips, "操作信息");
	}
	var showMsg=(config.showMsg==false)?false:true;
	
	
	var options={
	        url: config.url,
	        type: config.method,
	        data: config.data,
	        cache: false,
	        success: function(result,status, xhr) {
	        	var valid=xhr.getResponseHeader("valid");
	        	if(config.showProcessTips  && msgId!=null){
	        		mini.get(msgId).hide();
	        	}
	        	if(valid!='true'){
	        		mini.Cookie.set('enabled',false);
	        	}
	        	if(typeof result=="string"){
	        		//简易判断是否为json。
	        		if(result.startWith("{") && result.endWith("}")){
	        			result=mini.decode(result);
	        		}
	        	}
	        	if(!result) return;
	        	
	        	if(result.success){
	        		if(config.success){
	            		config.success.call(this,result);
	            	}
	        		if(showMsg){
		        		var msg=(result.message!=null)?result.message:'成功执行!';
		        		//显示操作信息
		        		top._ShowTips({
		            		msg:msg
		            	});
		        		//alert(msg);
	        		}
	        	}else{
	        		if(config.fail){
	        			config.fail.call(this,result);
	        		}else if(showMsg){//
		        		try{
		        			top._ShowErr({
		            		content:result.message
		            	});	
		        		}catch(e){
		        			 alert(result.message);
		        		}
	        		}
	        	}
	        },
	        error: function(jqXHR) {
	        	if(config.showProcessTips && msgId!=null){
	        		mini.get(msgId).hide();
	        	}
	        	
	        	if(jqXHR.responseText!='' && jqXHR.responseText!=null){
		        	top['index']._ShowErr({
		        		content:jqXHR.responseText
		        	});
	        	}
	        }
	    };
	//使用json的方式进行提交。
	if(config["postJson"]){
		options.contentType="application/json;charset=utf-8";
		if(options.data){
			options.data=JSON.stringify(options.data);
		}
	}
	$.ajax(options);
}

/**
 * 保存表单。
 * @param form
 * @param url
 * @param callBack
 * @param beforeHandler
 */
function _SaveData(formId,url,callBack,beforeHandler) {
	var form = new mini.Form(formId);
	form.validate();
    if (!form.isValid()) {
        return;
    }
    var formData=$("#" + formId).serializeArray();
    if(beforeHandler){
    	beforeHandler(formData);
    }
    var config={
    	url: url,
    	method:'POST',
    	data:formData,
    	success:function(result){
    		callBack(result);
    	}
    }
     
    _SubmitJson(config);
 }

/**
 * 提交表单。
 * @param formId		表单ID
 * @param url			表单地址
 * @param callBack		保存成功后进行处理
 * @param beforeHandler	在保存之前进行处理
 */
function _SaveJson(formId,url,callBack,beforeHandler) {
	var form = new mini.Form(formId);
	form.validate();
    if (!form.isValid()) {
        return;
    }
    var formData=form.getData();
    if(beforeHandler){
    	beforeHandler(formData);
    }
    var config={
    	url: url,
    	method:'POST',
    	data:formData,
    	success:function(result){
    		callBack(result);
    	}
    }
    config.postJson=true;
    _SubmitJson(config);
 }

/**
 * 
 * @param data
 * @param url
 * @param callBack
 * @param beforeHandler
 */
function _SaveDataJson(formId,callBack,beforeHandler) {
	
    if(beforeHandler){
    	var rtn=beforeHandler(data);
    	if(!rtn) return;
    }
    var config={
    	url: url,
    	method:'POST',
    	data:data,
    	success:function(result){
    		callBack(result);
    	}
    }
    config.postJson=true;
    _SubmitJson(config);
 }

/**
 * 操作完成后，展示操作提示
 * @param config
 */
function _ShowTips(config){
	var x,y,width,height;
	if(config==null){
		config={};
	}
	//title=config.title?config.title:'操作提示';
	msg=(config.msg!='' && config.msg!=undefined)?config.msg:'成功操作！';
	x=config.x?config.x:'center';
	y=config.y?config.y:'top';
	width=config.width?config.width:450;
	height=config.height?config.height:100;

	var en=getCookie('enabled');
    if(en=='false'){
		config.msg=config.msg+'<br/>'+__status_tips;
    }
	mini.showTips({
		width:width,
		height:height,
        content: '<h1>提示</h1><h2>'+config.msg+'</h2><i class="icon-tips-Close"></i>',
        state: "success",
        x: x,
        y: y,
        timeout: 4000
    });
}

/**
 * 展示出错信息
 * @param config
 */
function _ShowErr(config){

	var x,y,width,height;

	x=config.x?config.x:'center';
	y=config.y?config.y:'top';
	width=config.width || 1024;
	height=config.height || 'auto';

	mini.showTips({
		 width:width,
		 height:height,
         content: "<span class='icon' onclick='drag(this)'><i class='icon-wxClose' onclick='_close(this)'></i></span><div style='width:"+width+"px;height:"+height+"px;'>"+config.content+"</div>",
         state: "warning",
         x: x,
         y: y,
         timeout: 60000
     });
}

function _close(This){
	$(This).parent().parent().slideUp(300);
}


/**
 * config的参数有：
 * types:Document,Icon,Image,Zip,Vedio
 * from:APP,
 * callback:function(files){
 * 	fileId,path
 * }
 * 
 */
function _FileUploadDlg(config) {
    var url = __rootPath+"/sys/core/sysFile/dialog.do";
    
    _OpenWindow({
        url: url,
        title: "上传文件", width: 600, height: 420,
        onload: function() {
        },
        ondestroy: function(action) {
            if (action == 'ok') {
                var iframe = this.getIFrameEl();
                var files = iframe.contentWindow.getFiles();
                if (config.callback) {
                    config.callback.call(this, files);
                }
            }
        }
    });
}

//用户对话框
function _UserDialog(conf){
	var config={
		single:false
	};
	jQuery.extend(config,conf);
	
	_OpenWindow({
		url:__rootPath+'/sys/org/osUser/dialog.do?single='+config.single +'&dimId='+config.dimId,
		height:450,
		width:1080,
		iconCls:'icon-user-dialog',
		title:'用户选择',
		ondestroy:function(action){
			if(action!='ok')return;
			var iframe = this.getIFrameEl();
            var users = iframe.contentWindow.getUsers();
            if(config.callback){
            	if(config.single && users.length>0){
            		config.callback.call(this,users[0]);
            	}else{
            		config.callback.call(this,users);
            	}
            }
		}
	});
}
/**
 * 用户选择框
 * @param single
 * @param callback 回调函数，返回选择的用户信息，当为单选时，
 * 返回单值，当为多选时，返回多个值
 */
function _UserDlg(single,callback){
	_TenantUserDlg('',single,callback);
}

/**
 * 参数格式如下:
 * {
 * 	single:是否单选
 * 	users:[{fullname:"",userId:""}]
 * 	callback:function(data){
 * 		
 * 	}
 * }
 * @param conf
 */
function _UserDialog(conf){
	var single=conf.single || false;
	var users=conf.users || [];
	
	_OpenWindow({
		url:__rootPath+'/sys/org/osUser/dialog.do?single='+single,
		height:450,
		width:1080,
		iconCls:'icon-user-dialog',
		title:'用户选择',
		onload:function(){
        	var iframe = this.getIFrameEl().contentWindow;
        	iframe.init(users);
        },
		ondestroy:function(action){
			if(action!='ok')return;
			var iframe = this.getIFrameEl();
            var users = iframe.contentWindow.getUsers();
            var callback=conf.callback;
            if(callback){
            	if(single && users.length>0){
            		callback.call(this,users[0]);
            	}else{
            		callback.call(this,users);
            	}
            }
		}
	});
}

/**
 * 按租用机构进行用户选择
 * @param tenantId 当前用户为指定的管理机构下的用户,才可以查询到指定的租用机构下的用户
 * @param single
 * @param callback 回调函数，返回选择的用户信息，当为单选时，
 * 返回单值，当为多选时，返回多个值
 */
function _TenantUserDlg(tenantId,single,callback){
	_OpenWindow({
		url:__rootPath+'/sys/org/osUser/dialog.do?single='+single+'&tenantId='+tenantId,
		height:450,
		width:1080,
		iconCls:'icon-user-dialog',
		title:'用户选择',
		ondestroy:function(action){
			if(action!='ok')return;
			var iframe = this.getIFrameEl();
            var users = iframe.contentWindow.getUsers();
            if(callback){
            	if(single && users.length>0){
            		callback.call(this,users[0]);
            	}else{
            		callback.call(this,users);
            	}
            }
		}
	});
}

/**
 * 部门选择器
 * @param single
 * @param config
 * @param callback
 */
function _DepDlg(single,config,callback){	
	_TenantGroupDlg('',single,config,'1',callback,false);
}

/**
 * 用户组选择框（查询当前租户下的用户）
 * @param single 是否单选择
 * @param callback 回调
 * @param reDim  回调是否需要返回维度ID,可不选择
 */
function _GroupDlg(single,callback,reDim){
	if(!reDim){
		reDim=false;
	}
	_TenantGroupDlg('',single,'','',callback,reDim);
}

/**
 * 显示某一维度下的用户组选择框（查询当前租户下的用户）
 * @param single
 * @param dimId 显示维度下的用户组
 * @param callback
 */
function _GroupSingleDim(single,dimId,callback){
	_TenantGroupDlg('',single,'',dimId,callback,false);
}

/**
 * 去除行政维度下的用户组
 * @param conf
 */
function _GroupCanDlg(conf){
	var config={};
	$.extend(config,conf);
	//去除行政维度下的用户
	conf.excludeAdmin=true;
	conf.showDimId=false;
	_SaasGroupDlg(conf);
}

/**
 * SAAS级的用户组选择框
 * @param conf
 * tenantId 当前用户为指定的管理机构下的用户,才可以查询到指定的租用机构下的用户
 * single  是否单选
 * showDimId 维度Id，传入后，则只显示该维度的用户组
 * callback  callback
 * excludeAdmin 排除行政维度
 * reDim  必须返回维度选择
 */
function _SaasGroupDlg(conf){
	var title=conf.title;
	if(!title && conf.showDimId=='1'){
		title='部门选择';
	}
	if(!conf.excludeAdmin){
		conf.excludeAdmin='';
	}
	
	if(!conf.width){
		conf.width=840;
	}
	if(!conf.height){
		conf.height=450;
	}
	_OpenWindow({
		iconCls:'icon-group-dialog',
		url:__rootPath+'/sys/org/osGroup/dialog.do?single='+conf.single+'&reDim='+conf.reDim+'&showDimId='+conf.showDimId+'&tenantId='+conf.tenantId +'&excludeAdmin='+conf.excludeAdmin,
		height:conf.height,
		width:conf.width,
		title:title,
		ondestroy:function(action){
			if(action!='ok')return;
			var iframe = this.getIFrameEl();
            var groups = iframe.contentWindow.getGroups();
            var dim={};
            //需要返回dim
            if(conf.reDim){
            	var dimNode=iframe.contentWindow.getSelectedDim();
	            dim={
            		dimId:dimNode.dimId,
            		dimKey:dimNode.dimKey,
            		name:dimNode.name
	            };
            }
            if(conf.callback){
            	if(conf.single && groups.length==1){
            		conf.callback.call(this,groups[0],dim);
            	}else{
            		conf.callback.call(this,groups,dim);
            	}
            }
		}
	});
}

/**
 * 用户组选择框（查询指定的租户下的用户）
 * @param tenantId 当前用户为指定的管理机构下的用户,才可以查询到指定的租用机构下的用户
 * @param single 是否单选
 * @param config 选择框配置
 * @param showDimId 维度Id，传入后，则只显示该维度的用户组
 * @param callback 
 * @param reDim 必须返回维度选择
 */
function _TenantGroupDlg(tenantId,single,config,showDimId,callback,reDim){
	
	var title='用户组选择';
	if(showDimId=='1'){
		title='部门选择';
		
		
	}
	if(!config){
		config = "";
	}
	
	
	_OpenWindow({
		iconCls:'icon-group-dialog',
		url:__rootPath+'/sys/org/osGroup/dialog.do?single='+single+'&reDim='+reDim+'&showDimId='+showDimId+'&tenantId='+tenantId+'&config=' + config,
		height:480,
		width:930,
		title:title,
		ondestroy:function(action){
			if(action!='ok')return;
			var iframe = this.getIFrameEl();
            var groups = iframe.contentWindow.getGroups();
            var dim={};
            //需要返回dim
            if(reDim){
            	var dimNode=iframe.contentWindow.getSelectedDim();
	            dim={
	            		dimId:dimNode.dimId,
	            		dimKey:dimNode.dimKey,
	            		name:dimNode.name
	            };
            }
            if(callback){
            	if(single && groups.length==1){
            		callback.call(this,groups[0],dim);
            	}else{
            		callback.call(this,groups,dim);
            	}
            }
		}
	});
}

/**
 * 显示图片对框架
 * 
 * @param config
 * {
 * 	  from:来自个人的上传图片（值为：SELF），来自应用程序(值为：APPLICATION)
 *    single:单选择(true),复选(false)
 *    callback:回调函数，当选择ok，则可以通过回调函数的参数获得选择的图片
 * }
 */
function _ImageDlg(config){
	
	if(!config.width) config.width=620;
	
	if(!config.height) config.height=450;
	
	mini.open({
        allowResize: true, //允许尺寸调节
        allowDrag: true, //允许拖拽位置
        showCloseButton: true, //显示关闭按钮
        showMaxButton: true, //显示最大化按钮
        showModal: true,
        //from=SELF代表来自个人的图片，single代表只允许上传一张
        url: __rootPath+"/sys/core/sysFile/appImages.do?from="+config.from+"&single="+config.single,
        title: "选择图片", 
        width: config.width, 
        height: config.height,
        onload: function() {
			
        },
        ondestroy: function(action) {
            if (action != 'ok') return;
            var iframe = this.getIFrameEl();
            var imgs = iframe.contentWindow.getImages();
            if(config && config.callback){
                config.callback.call(this,imgs);
            }
        }
    });	
}

/**
 * 预览原图
 * @param fileId
 */
function _ImageViewDlg(fileId){
	var win=mini.open({
        allowResize: true, //允许尺寸调节
        allowDrag: true, //允许拖拽位置
        showCloseButton: true, //显示关闭按钮
        showMaxButton: true, //显示最大化按钮
        showModal: true,
        //只允许上传图片，具体的图片格式配置在config/fileTypeConfig.xml
        url: __rootPath+"/sys/core/sysFile/imgPreview.do?fileId="+fileId,
        title: "图片预览", width: 600, height: 420,
        onload: function() {
        },
        ondestroy: function(action) {
        }
    });
	win.max();
}

/**
 * 显示个人的对话框
 * @param single 是否为单选图片
 * @param callback 回调函数
 */
function _UserImageDlg(single,callback){
	var config={
		single:single,
		callback:callback,
		from:'SELF'
	};
	_ImageDlg(config);
}

/**
 * 显示应用级别的图片对话框
 * @param single
 * @param callback
 */
function _AppImageDlg(single,callback){
	var config={
		single:single,
		callback:callback,
		from:'APPLICATION'
	};
	_ImageDlg(config);
}
/**
 * 获得业务表单的JSON值
 * @param String
 */
function _GetFormJson(formId){
	var modelJson={};
	var formData=$("#"+formId).serializeArray();
	for(var i=0;i<formData.length;i++){
		modelJson[formData[i].name]=formData[i].value;
	}
	
	var extJson=_GetExtJsons(formId);
	
	$.extend(modelJson,extJson);
	
	
	return modelJson;
}

/**
 * 获取表单数据。
 * @param formId
 * @returns
 */
function _GetFormJsonMini(formId){
	var form = new mini.Form("#"+ formId);            
	var modelJson = form.getData();
	//FORM_OPINION_
	var pre="FORM_OPINION_";
	var opinion={};
	var i=0;
	for(var key in modelJson){
		if(!key.startWith(pre)) continue;
		var name=key.replace(pre,"");
		if(i==0){
			modelJson[pre]={name:name,val:modelJson[key]};
		}
		delete modelJson[key];
		i++;
	}
	
	
	var form=$("#"+formId);

	var extJson=_GetExtJsonsMini(form);
	
	$.extend(modelJson,extJson);
	
	return modelJson;
}

function _GetExtJsonsMini(form){
	var modelJson={};
	
	form.find('.mini-radiobuttonlist').each(function(){
		var cname=$(this).find('tbody>tr>td').children('input[type="hidden"]').attr('name');
		var ctext=$(this).find('tbody>tr>td').find('.mini-radiobuttonlist-item-selected').find("label").text();
		modelJson[cname+'_name']=ctext;
	});
	
	form.find('.mini-checkboxlist').each(function(){
		var cname=$(this).find('tbody>tr>td').children('input[type="hidden"]').attr('name');
		var ctext=[];
		var list=$(this).find('tbody>tr>td>div').find('.mini-checkboxlist-item-selected');
		list.each(function(){
			ctext.add($(this).find("label").text());
		});
		modelJson[cname+'_name']=ctext.join(',');
	});
	
	form.find('.mini-combobox').each(function(){
		var cname=$(this).children('input[type="hidden"]').attr('name');
		if(cname!=''){
			var cbbObj=mini.getByName(cname);
			if(cbbObj && cbbObj.getText){
				var ctext=cbbObj.getText();
				modelJson[cname+'_name']=ctext;
			}
		}
	});
	
	$('.mini-datagrid,.mini-treegrid',form).each(function(){
		var name=this.id;
		var grid=mini.get(name);
		name=name.replace("grid_","");
		modelJson["SUB_" +name]=grid.getData();
	});

	return modelJson;
	
}


function _GetExtJsons(formId){
	var form=$("#"+formId);
	var modelJson={};
	
	var modelJson=_GetExtJsonsMini(form);
	
	form.find('.mini-textboxlist').each(function(){
		var cname=$(this).find('tbody>tr>td').children('input[type="hidden"]').attr('name');
		var ctext=[];
		var list=$(this).find('tbody>tr>td>ul').children('.mini-textboxlist-item');
		list.each(function(){
			ctext.add($(this).text());
		});
		modelJson[cname+'_name']=ctext.join(',');
	});
	
	form.find('.mini-treeselect').each(function(){
		var cname=$(this).children('input[type="hidden"]').attr('name');
		if(cname!=''){
			var tsObj=mini.getByName(cname);
			if(tsObj && tsObj.getText){
				var ctext=tsObj.getText();
				modelJson[cname+'_name']=ctext;
			}
		}
	});
	
	form.find('.mini-buttonedit').each(function(){
		var cname=$(this).children('input[type="hidden"]').attr('name');
		if(cname!=''){
			var tsObj=mini.getByName(cname);
			if(tsObj && tsObj.getText){
				var ctext=tsObj.getText();
				modelJson[cname+'_name']=ctext;
			}
		}
	});
	
	return modelJson;
}

/**
 * 获得表单的所有控件值串，格式为[{name:'a',value:'a'},{name:'b',value:'b'}],并且对value值的特殊符号包括中文进行编码
 * @param formId 表单ID
 * @returns
 */
function _GetFormParams(formId){
	return $("#"+formId).serializeArray();
}

/**
 * 加载用户信息，使用的时候，是需要在页面中带有以下标签，
 * <a class="mini-user" userId="11111"></a>
 * 其则会根据userId来加载处理成fullname
 * @param editable 是否可编辑
 */
function _LoadUserInfo(editable){
	var uIds=[];
	$('.mini-user').each(function(){
		var uId=$(this).attr('userId');
		if(uId){
			uIds.push(uId);
		}
	});
	if(uIds.length>0){
		$.ajax({
            url:__rootPath+ '/pub/org/user/getUserJsons.do',
            data:{
            	userIds:uIds.join(',')
            },
			type:"POST",
            success: function (jsons) {
                for(var i=0;i<jsons.length;i++){
                	if(editable){
                		$("a.mini-user[userId='"+jsons[i].userId+"']").attr('href','javascript:void(0)').attr('onclick','_ShowUserEditor(\''+jsons[i].userId+'\')').html(jsons[i].fullname);
                	}else{
                		var sex=jsons[i].sex;
                		var html='';

                		$("a.mini-user[userId='"+jsons[i].userId+"']").html(html+jsons[i].fullname);
                	}
                	
                }
            }
        });
	}
}

/**
 * 加载用户信息，使用的时候，是需要在页面中带有以下标签，
 * <span class="mini-taskInfo" actInstId="11111"></span>
 * 其则会根据actInstId来加载处理成任务名称及执行
 */
function _LoadTaskInfo(){
	var uIds=[];
	$('.mini-taskinfo').each(function(){
		var uId=$(this).attr('instId');
		if(uId){
			uIds.push(uId);
		}
	});
	if(uIds.length>0){
		$.ajax({
            url:__rootPath+ '/pub/bpm/getTaskInfos.do',
            data:{
            	instIds:uIds.join(',')
            },
			type:"POST",
            success: function (jsons) {
                for(var i=0;i<jsons.length;i++){
                	var taskInfos="";
                	var taskUserInfos=jsons[i].taskUserInfos;
                	for(var c=0;c<taskUserInfos.length;c++){
                		
                		if(taskUserInfos[c].curUserTask){
                			taskInfos=taskInfos + " <a href='javascript:void(0);' alt='"+taskUserInfos[c].exeFullnames+"' onclick='_handleTask(\""+taskUserInfos[c].taskId+"\")' ";
                			taskInfos = taskInfos + " taskId='"+taskUserInfos[c].taskId+"'>"+taskUserInfos[c].nodeName+"</a>";
                		}else{
                			taskInfos = taskInfos +' ' + taskUserInfos[c].nodeName;
                		}
                		
                	}
                	$("span.mini-taskinfo[instId='"+jsons[i].instId+"']").html(taskInfos);
                }
            }
        });
	}
}

function _handleTask(taskId){
	_OpenWindow({
		url:__rootPath+'/bpm/core/bpmTask/toStart.do?taskId='+taskId,
		title:'待办处理',
		width:800,
		height:400,
		max:true,
		ondestroy:function(action){
			if(action!='ok') return;
		}
	});
}

function _ShowUserEditor(userId){
	_OpenWindow({
		title:'编辑用户信息',
		url:__rootPath+'/sys/org/osUser/edit.do?pkId='+userId,
		height:450,
		width:780,
		onload:function(){
			
		}
	});
}

/**
 * 显示我的文件上传对话框
 * @param config
 * 		  showMgr:true 是否显示管理界面
 *        from：SELF,APPLICATION
 *        types： Image,Document,Zip,Vedio
 *        single： true,false单选或多选
 *        callback： 回调函数
 */
function _UploadFileDlg(config){
	if(config.showMgr){
		_OpenWindow({
			title:'我的附件管理器',
			height:500,
			width:820,
			url:__rootPath+'/sys/core/sysFile/myMgr.do?dialog=true&single='+config.single,
			ondestroy:function(action){
				if (action != 'ok') return;
	            var iframe = this.getIFrameEl();
	            var files = iframe.contentWindow.getFiles();
	            if(config.callback){
	                config.callback.call(this,files);
	            }
			}
		});
	}else{
		_UploadDialogShowFile({
			onlyOne:config.onlyOne,
			from:config.from,
			types:config.types,
			callback:config.callback,
			single:config.single,
			title:config.title
		});
	}
}

/**
 * 流程解决方案对话框
 * @param single
 * @param callback
 */
function _BpmSolutionDialog(single,callback){
	_OpenWindow({
		url:__rootPath+'/bpm/core/bpmSolution/dialog.do?single='+single,
		title:'流程解决方案选择',
		height:600,
		width:800,
		iconCls:'icon-flow',
		ondestroy:function(action){
			if(action!='ok')return;
			var iframe = this.getIFrameEl();
            var sols = iframe.contentWindow.getSolutions();
            if(callback){
            	callback.call(this,sols);
            }
		}
	});
}

/**
 * 上传对话框
 * @param config
 *       from:SELF,APPLICATION
 *       types:Image,Document,Zip,Vedio
 *       callback
 */
function _UploadDialog(config){
 	_OpenWindow({
        //只允许上传图片，具体的图片格式配置在config/fileTypeConfig.xml
        url: __rootPath+"/sys/core/sysFile/webUploader.do?from="+config.from+"&types="+config.types,
        title: "文件上传", 
        width: 400,
        height: 420,
        onload: function() {
			
        },
        ondestroy: function(action) {
            if (action != 'ok') return;
            var iframe = this.getIFrameEl();
            var files = iframe.contentWindow.getFiles();
            
            if(config.callback){
                config.callback.call(this,files);
            }
        }
    });
}

/**
 * 上传对话框
 * @param config
 *       from:SELF,APPLICATION
 *       types:Image,Document,Zip,Vedio
 *       callback
 */
function _UploadDialogShowFile(config){
	if(!config.title) config.title="附件上传";
	var congfigStr = mini.encode(config);
	var url=__rootPath+"/sys/core/sysFile/webUploader.do?config="+congfigStr;
	
	url=encodeURI(url);
	
 	_OpenWindow({
        //只允许上传图片，具体的图片格式配置在config/fileTypeConfig.xml
        url:url,
        title: config.title, 
        width: 600,
        height: 420,
        onload: function() {
			
        },
        ondestroy: function(action) {
            if (action != 'ok') return;
            var iframe = this.getIFrameEl();
            var files = iframe.contentWindow.getFiles();
            if(config.callback){
                config.callback.call(this,files);
            }
        }
    });
}

/**
 * 加载用户组信息，使用的时候，是需要在页面中带有以下标签，
 * <a class="mini-group" groupId="11111"></a>
 * 其则会根据groupId来加载处理成name
 */
function _LoadGroupInfo(){
	var uIds=[];
	$('.mini-group').each(function(){
		var uId=$(this).attr('groupId');
		if(uId){
			uIds.push(uId);
		}
	});
	if(uIds.length>0){
		$.ajax({
            url:__rootPath+ '/pub/org/group/getGroupJsons.do',
            data:{
            	groupIds:uIds.join(',')
            },
            success: function (jsons) {
                for(var i=0;i<jsons.length;i++){
                	$("a.mini-group[groupId='"+jsons[i].groupId+"']").html(jsons[i].name);
                }
            }
        });
	}
}

/**
 * 获得表格的行的主键Id列表，并且用',’分割
 * @param rows
 * @returns
 */
function _GetIds(rows){
	var ids=[];
	for(var i=0;i<rows.length;i++){
		ids.push(rows[i].pkId);
	}
	return ids.join(',');
}
/*
_ModuleFlowWin({
		title:'供应商入库申请',
		modleKey:'CRM_PROVIDER',
		failCall:add,
		success:function(action){
			
		}
	});
*/
/**
 * 用于用户自已经的模块中使用，点击弹出添加流程审批业务功能
 */
function _ModuleFlowWin(conf){
	var width=conf.width?conf.width:780;
	var height=conf.height?conf.height:480;
	$.ajax({
        url:__rootPath+ '/bpm/integrate/bpmModuleBind/getSolByModuleKey.do?moduleKey='+conf.moduleKey,
        success: function (json) {
            if(!json.success){
            	if(conf.failCall){
            		conf.failCall.call(this);
            	}else{
            		alert('流程业务模块没有绑定的流程解决方案：'+conf.moduleKey);
            	}
            }
            _OpenWindow({
            	url:__rootPath+'/bpm/core/bpmInst/start.do?solId='+json.data.solId,
            	title:conf.title,
            	width:width,
            	height:height,
            	ondestroy:function(action){
            		if(action=='ok' && conf.success){
            			conf.success.call(this);
            		}
            	}
            });
        }
    });
}

String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
};
 
String.prototype.ltrim=function(){
    return this.replace(/(^\s*)/g,"");
};

String.prototype.rtrim=function(){
    return this.replace(/(\s*$)/g,"");
};

String.prototype.endWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substring(this.length-str.length)==str)
	  return true;
	else
	  return false;
	return true;
};

/**
 * 将字符串解析为日期。 
 */
String.prototype.parseDate=function(){
	//yyyy-MM-dd
	var year=1970;
	//yyyy-MM-dd
	var aryDateTime=this.split(" ");
	
	var date=aryDateTime[0];
	var aryTmp=date.split("-");
	var year=parseInt(aryTmp[0]);
	var mon=parseInt(aryTmp[1])-1;
	var day=parseInt(aryTmp[2]);
	var hour=0;
	var minute=0;
	if(aryDateTime.length==2){
		var time=aryDateTime[1];
		var aryTmp=time.split(":");
		hour=parseInt(aryTmp[0]);
		minute=parseInt(aryTmp[1]);
	}
	return new Date(year,mon,day,hour,minute);
}

/**
 * 日期格式化方法。
 * 用法 : new Date().format("yyyy-MM-dd hh:mm:ss");
 * @param format
 * @returns
 */
Date.prototype.format = function(format){ 
	var o = { 
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(), //day 
		"h+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds() //millisecond 
	} 
	
	if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 

	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
	} 
	return format; 
}

/**
 * 获取当前最小时间。
 */
Date.prototype.Min = function(){ 
	this.setHours(0);
	this.setMinutes(0);
	this.setSeconds(0);
	return this;
}

/**
 * 获取最大时间。
 */
Date.prototype.Max = function(){ 
	this.setHours(23);
	this.setMinutes(59);
	this.setSeconds(59);
	return this;
}

/**
 * 在日期上减去具体的时间。
 * unit:时间单位 可能的值为 day,hour,minute
 * amount:数量。
 */
Date.prototype.sub = function(unit,amount){
	this.calc(unit,amount,false);
	return this;
}

/**
 * 在日期上增加时间。
 * unit:时间单位 可能的值为 day,hour,minute
 * amount:数量。
 */
Date.prototype.add = function(unit,amount){
	this.calc(unit,amount,true);
	return this;
}

/**
 * 在日期上增加具体的时间。
 */
Date.prototype.calc = function(unit,amount,add){
	var time=0;
	switch(unit){
		case "day":
			time=24*60*60*1000*amount;
			break;
		case "hour":
			time=60*60*1000*amount;
			break;
		case "minute":
			time=60*1000*amount;
			break;
	}
	if(add){
		this.setTime(this.getTime()+time);
	}
	else{
		this.setTime(this.getTime()-time);
	}
	return this;
}

/**
 * 两个日期相减。
 * @param date 			被减的日期类型数据
 * @param format		返回数据类型(1,分钟,2,小时,乱填或者默认则认为是天数)
 * @returns {Number}
 */
Date.prototype.subtract = function(date,format){
	  var minute= (this.getTime() - date.getTime())/(60 * 1000);
	  var day=parseInt(minute / (24*60));
	  var hour=parseInt(minute / 60);
	  var rtn=0;
	  switch(format){
	    //分钟
	  	case 1:
	  		rtn=minute  ;
	  		break;
	  	//小时
	  	case 2:
	  		rtn=hour ;
	  		break;
	  	//天数
	  	default :
	  		rtn=day +1;
	  }
	  return rtn;
}

/**
 * 日期大小比较。
 * @param date
 * @returns {Number}
 */
Date.prototype.compareTo = function(date){
	var time=this.getTime() - date.getTime();
	if(time>0) {
		return 1;
	}
	else if(time==0) {
		return 0;
	}
	return -1;
	
}

/**
 * 是否为短时期.
 */
Date.prototype.isShortDate=function(){
	if(this.getHours()==0 || this.getMinutes()==0 || this.getSeconds()==0) return true;
	return false;
}




/**
 * 时间计算。
 * @param date
 * @param format 1,返回分钟,2,返回天小时分钟格式。
 * @returns {String}
 */
Date.prototype.diff = function(date,format){
	  format =format ||2;
	  var minute= (this.getTime() - date.getTime())/(60 * 1000);
	  var day=parseInt(minute / (24*60));
	  var rest=minute -day *(24*60);
	  var hour=parseInt(rest / 60);
	  rest=parseInt( rest -hour* 60);
	  if(format==1){
		  return minute;
	  }
	  
	  if(day==0){
		  if(hour==0){
			  return rest +"分钟";
		  }
		  else{
			  return hour +"小时"+ rest +"分钟";
		  }
	  }
	  else{
		  return day +"天" + hour +"小时" + rest +"分钟";
	  }
}


/**
 * icon选择器
 * 用法示例,
 * _IconSelectDlg(function(icon){
			console.log(icon);
		});
 * */
function _IconSelectDlg(callback){
	_OpenWindow({
		url:__rootPath+'/sys/core/file/iconSelectDialog.do',
		height:450,
		width:700,
		iconCls:'icon-window',
		title:'图标选择',
		ondestroy:function(action){
			if(action!='ok')return;
			var iframe = this.getIFrameEl();
            var icon = iframe.contentWindow.getIcon();
            if(callback){
            	callback.call(this,icon);
            }
		}
	});
}


/**
 * 打开关联连接
 */
function _ShowLinkFieldInfo(url,linkType){
	if(linkType=='newWindow'){
		window.open(url);
	}else{
		_OpenWindow({
			title:'关联信息',
			url:url,
			height:500,
			width:800,
			max:true
		});
	}
}
	
/**
 * 自动定义按钮，点击弹出自定义对话框
 * 
 * @param e
 */
function _OnSelDialogShow(e){
	var button=e.sender;
	var binding=button.binding;
	var dialogalias=binding.dialogalias;
	var callback=button.callback;
	var params=getInput(binding);
	var seltype=binding.seltype;
	
	var conf={
			dialogKey:dialogalias,
			params:params,
			ondestroy:function(data){
				setRtnData(data,binding)
				if(callback){
					callback.call(this,data);
				}
			}
	};
	if(seltype){
		if(seltype=="single"){
			conf.single="true";
		}
		else if(seltype=="multi"){
			conf.single="false";
		}
	}
	
	
	_CommonDialogExt(conf);
}

/**
 * 自动编辑按钮，点击弹出自定义对话框
 * 
 * @param e
 */
function _OnEditSelDialogShow(e){
	var button=e.sender,
		binding=button.binding,
		dialogalias=binding.dialogalias,
		dialogname=binding.dialogname,
		returnFields=binding.returnFields,
		isMain = binding.isMain,
		callback=binding.callback,
		
		textField=binding.textField,
		valueField=binding.valueField,
		seltype=binding.seltype,
	
		subGrid,
		params="",
		row;
	if(!isMain){
		var gridName = binding.gridName;
		subGrid = mini.get("grid_"+gridName);
		if(subGrid!=null){
			subGrid.cancelEdit();
		}
		row=subGrid.getSelected();
	}
	
	params=getBtnEditInput(binding,row);
	
	var conf={
			dialogKey:dialogalias,
			params:params,
			ondestroy:function(data){
				var rows=data.rows;
				var single=data.single;
				if(rows==null || rows.length==0) return;
			
				if(isMain){//是否是主表
					bindFields_(returnFields,data);
					if(textField){
						var id=button.getValue();
						var name=button.getText();
						var obj=getPair(data,textField,valueField,id,name);
						button.setText(obj.name);
						button.setValue(obj.id);
						button.doValueChanged();
					}

				}else{//在子表
					var rowData = {};
					var rowObj=rows[0];
					for(var i=0;i<returnFields.length;i++){
						var filed=returnFields[i];
						if(!filed.bindField) continue;
						rowData[filed.bindField] = rowObj[filed.field];
					}				
					
					if(textField){
						var id=button.getValue();
						var name=button.getText();
						var obj=getPair(data,textField,valueField,id,name);
						button.setText(obj.name);
						button.setValue(obj.id);
						
						var name=button.name;
						rowData[name]=obj.id;
						rowData[name +"_name"]=obj.name;
						button.doValueChanged();
					}
					subGrid.updateRow(row,rowData);
					
				}
				//设置回调。
				if(callback){
					eval(callback +"(data)");
				}
			}
	};
	
	if(seltype){
		if(seltype=="single"){
			conf.single="true";
		}
		else if(seltype=="multi"){
			conf.single="false";
		}
	}
	
	_CommonDialogExt(conf);
}

/**
 * 绑定字段。
 * @param returnFields
 * @param data
 * @returns
 */
function bindFields_(returnFields,data){
	var rows=data.rows;
	//获取表单对象。
	var form=getFormByFields(returnFields);
	if(!form) return;
	var formData=form.getData();
	var obj={};
	for(var i=0;i<returnFields.length;i++){
		var field=returnFields[i];
		if(!field.bindField) continue;		
		var aryData=[];
		for(var j=0;j<rows.length;j++){
			var row=rows[j];
			aryData.push(row[field.field]);
		}
		var tmp=aryData.join(",");
		obj[field.bindField]=tmp;
	}
	form.setData($.extend(formData, obj));
	//触发变更事件
	for(var i=0;i<returnFields.length;i++){
		var field=returnFields[i];
		if(!field.bindField) continue;		
		if(field.bindField.endWith("_name"))continue;
		var ctl=mini.getByName(field.bindField);
		if(ctl){
			ctl.doValueChanged();
		}
	}
}


/**
 * 获取主表数据。
 * @param returnFields
 * @returns
 */
function getFormByFields(returnFields){
	var ctl=null;
	for(var i=0;i<returnFields.length;i++){
		var field=returnFields[i];
		if(!field.bindField) continue;			
		if(field.bindField.endWith("_name"))continue;
		var ctl=mini.getByName(field.bindField);
		if(ctl) break;
	}
	if(!ctl) return null;
	var formId=$(ctl.el).closest(".form-model").attr("id");
	var form = new mini.Form("#"+formId );
	return form;
}

function getNameField(returnFields,fieldName){
	for(var i=0;i<returnFields.length;i++){
		var field=returnFields[i];
		if(!field.bindField) continue;
		var name=fieldName +"_name";
		if(name==field.bindField){
			return field;
		}
	}
	return null;
}


function setRtnData(data,binding){
	var rows=data.rows;
	if(!rows || rows.length==0) return;
	
	var fields=binding.returnFields;
	var gridName=binding.gridName;
	
	//填充主表	
	if(gridName=="main"){
		bindFields_(fields,data)
	}
	//填充子表。
	else{
		var uniquefield=binding.uniquefield;
		var grid=mini.get("grid_" + gridName);
		var obj={};
		for(var i=0;i<fields.length;i++){
			var o=fields[i];
			if(!o.bindField){continue;}
			obj[o.field]=o.bindField;
		}
		var gridData=grid.getData();
		var newAry=[];
		for(var i=0;i<rows.length;i++){
			var row=rows[i];
			var newRow={};
			for(var key  in obj){
				//newKey 绑定字段
				var newKey=obj[key];
				newRow[newKey]=row[key];
			}
			
			if(uniquefield){
				var isExist=false;
				var val=newRow[uniquefield];
				for(var j=0;j<gridData.length;j++){
					var tmp=gridData[j];
					if(tmp[uniquefield]==val){
						isExist=true;
						break;
					}
				}
				if(!isExist){
					newAry.push(newRow);
				}
			}
			else{
				newAry.push(newRow);
			}
		}
		for(var i=0;i<newAry.length;i++){
			gridData.push(newAry[i]);
		}
		grid.setData(gridData);
	}
	
	
}

/**
 * 
 * @param data		{rows:rows,single:true}
 * @param textField
 * @param valueField
 * @param selectId
 * @param slectName
 * @returns
 */
function getPair(data,textField,valueField,selectId,slectName){
	var rows=data.rows;
	var single=data.single;
	if(single){
		var row=rows[0];
		return {id:row[valueField],
			name:row[textField]};
	}
	else{
		var aryId=[];
		var aryName=[];
		if(selectId){
			aryId=selectId.split(",");
			aryName=slectName.split(",");
		}
		
		for(var i=0;i<rows.length;i++){
			var row=rows[i];
			var id=row[valueField];
			if(isExist(aryId,id)) continue;
			aryId.push(row[valueField]);
			aryName.push(row[textField]);
		}
		return {id:aryId.join(","),
				name:aryName.join(",")};
	}
	
	
	
}

function isExist(ary,id){
	for(var i=0;i<ary.length;i++){
		if(ary[i]==id){
			return true;
		}
	}
	return false;
}

/**
 * 用户选择按钮点击事件
 * 
 * @param e
 */
function _UserButtonClick(e){
	var userSel=e.sender;
	var single=userSel.single;
	var name=userSel.name;
	var text_name=name +"_name";
	
	var gridRow=getGridByEditor(userSel);
	
	var flag=single || single=="true"?true:false;
	_UserDlg(flag,function(users){
		if(flag){
			if(window.top!=window.self  || !gridRow){
				userSel.setValue(users.userId);
				userSel.setText(users.fullname);
				userSel.doValueChanged();
			}
			else{
				var grid=gridRow.grid;
				var obj={};
				obj[name]=users.userId;
				obj[text_name]=users.fullname;
				grid.updateRow ( gridRow.row, obj );
			}
			
		}else{
			var uIds=[];
			var uNames=[];
			for(var i=0;i<users.length;i++){
				uIds.push(users[i].userId);
				uNames.push(users[i].fullname);
			}
			
			if(window.top!=window.self || !gridRow){
				userSel.setValue(uIds.join(','));
				userSel.setText(uNames.join(','));
				userSel.doValueChanged();
			}
			else{
				var grid=gridRow.grid;
				var obj={};
				obj[name]=uIds.join(',');
				obj[text_name]=uNames.join(',');
				grid.updateRow ( gridRow.row, obj );
				
			}
			
		}
	});
}
/**
 * 用户组选择按钮点击事件
 * 
 * @param e
 */
function _GroupButtonClick(e){
	var groupSel=e.sender;
	var single=groupSel.single;

	var gridRow=getGridByEditor(groupSel);
	var name=groupSel.name;
	var text_name=name +"_name";
	
	var showDim=groupSel.showDim;

	var dimId=groupSel.dimId;
	var callback=function(groups){
		if(single ){
			if(window.top!=window.self  || !gridRow){
				groupSel.setValue(groups.groupId);
				groupSel.setText(groups.name);
				groupSel.doValueChanged();
			}
			else{
				var grid=gridRow.grid;
				var obj={};
				obj[name]=groups.groupId;
				obj[text_name]=groups.name;
				grid.updateRow ( gridRow.row, obj );
			}
			
		}else{
			var uIds=[];
			var uNames=[];
			for(var i=0;i<groups.length;i++){
				uIds.push(groups[i].groupId);
				uNames.push(groups[i].name);
			}
			
			if(window.top!=window.self  || !gridRow){
				groupSel.setValue(uIds.join(','));
				groupSel.setText(uNames.join(','));
				groupSel.doValueChanged();
			}
			else{
				var grid=gridRow.grid;
				var obj={};
				obj[name]=uIds.join(',');
				obj[text_name]=uNames.join(',');
				grid.updateRow ( gridRow.row, obj );
			}
			
		}
		
	};
	
	if(showDim){
		_GroupDlg(single,callback);
	}else{
		_GroupSingleDim(single,dimId,callback);
	}
}

/**
 * 部门按钮的点击事件
 * 
 * @param e
 */
function _DepButtonClick(e){
	var depSel=e.sender;	
	var single=depSel.single;	
	var config=depSel.config;
	
	if(config){
		config = mini.encode(config);
		config = config.replace(/"([^"]*)"/g, "'$1'");
	}

	
	var gridRow=getGridByEditor(depSel);
	var name=depSel.name;
	var text_name=name +"_name";

	var callback=function(groups){
		if(single){
			if(window.top!=window.self  || !gridRow){
				depSel.setValue(groups.groupId);
				depSel.setText(groups.name);
			}
			else{
				var grid=gridRow.grid;
				var obj={};
				obj[name]=groups.groupId;
				obj[text_name]=groups.name;
				grid.updateRow ( gridRow.row, obj );
			}
		}else{
			var uIds=[];
			var uNames=[];
			for(var i=0;i<groups.length;i++){
				uIds.push(groups[i].groupId);
				uNames.push(groups[i].name);
			}
			
			if(window.top!=window.self  || !gridRow){
				depSel.setValue(uIds.join(','));
				depSel.setText(uNames.join(','));
				depSel.doValueChanged();
			}
			else{
				var grid=gridRow.grid;
				var obj={};
				obj[name]=uIds.join(',');
				obj[text_name]=uNames.join(',');
				grid.updateRow ( gridRow.row, obj );
			}
		}
		
	};
	_DepDlg(single,config,callback);
}



/**
 * 将字符串转换为日期。
 * 日期格式可以为:
 * 2009-09-11
 * 2009-09-11 09:30
 * 2009-09-11 09:30:33
 * @param str
 */
function parseDate(str){
	var myregexp = /(\d{4})-(\d{1,2})-(\d{1,2})\s?((\d{1,2}):(\d{1,2})(:(\d{1,2})){0,1}){0,1}/;
	var match = myregexp.exec(str);
	if(!match) return null;
	str = str.replace(/-/g,"/");
	var date = new Date(str);
	return date;
}


String.prototype.startWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substr(0,str.length)==str)
	  return true;
	else
	  return false;
	return true;
};

//是否存在指定函数 
function isExitsFunction(funcName) {
    try {
        if (typeof(eval(funcName)) == "function") {
            return true;
        }
    } catch(e) {}
    return false;
}
//是否存在指定变量 
function isExitsVariable(variableName) {
    try {
        if (typeof(variableName) == "undefined") {
            //alert("value is undefined"); 
            return false;
        } else {
            //alert("value is true"); 
            return true;
        }
    } catch(e) {}
    return false;
}



//表单数据转成json对象
$.fn.funForm2Object= function() {

	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {

			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			//alert( this.value);
			o[this.name] = this.value || '';
		}
	});
	return o;
};

function formToObject(formData){
	var o = {};
	
	$.each(formData, function() {
		if (o[this.name] !== undefined) {

			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}

//表单数据转成json数据
$.fn.funForm2son = function() {
	return JSON.stringify(this.funForm2Object());
};

//var arr = [ { 'key' : 'NORMAL','value' : '启用','css' : 'green'}];
jQuery.extend({
	formatItemValue: function(arr,val) { 
		for(var i=0;i<arr.length;i++){
			var obj=arr[i];
			if(obj.key==val){
				return "<span class='"+obj.css+"'>"+obj.value+"</span>";
			}
		}
		return val;
	},
	/**
	 * 判断对象是否为数组。
	 */
	isArray:function(obj){
		return Object.prototype.toString.call(obj) === '[object Array]'; 
	},
	/**
	 * 克隆对象。
	 */
	clone: function(obj) { 
		var tmp={};
		if($.isArray(obj)){
			tmp=[];
		}
		var temp= $.extend(true,tmp,obj);
		return temp;
	},
	/**
	 * 获取选中的值。
	 */
	getChkValue:function(name){
		var exp="[name='"+name+"']";
		var aryRtn=[];
		$(exp).each(function(i){
			var obj=$(this);
			if( obj.is(":checked")){
				aryRtn.push(obj.val());
			}
		})
		return aryRtn.join(",");
	},
	/**
	 * 读取文件。
	 */
	getFile:function(path){
		var html = $.ajax({
			  type: "GET",
			  url: path,
			  cache:true,
			  async: false
		}).responseText; 
		return html;
	},
	/**
	 * 在textarea 中插入文本。
	 */
	insertText : function(txtarea, content) {
		// IE
		if (document.selection) {
			var theSelection = document.selection.createRange().text;
			if (!theSelection) {
				theSelection = content;
			}
			txtarea.focus();
			if (theSelection.charAt(theSelection.length - 1) == " ") {
				theSelection = theSelection.substring(0,
						theSelection.length - 1);
				document.selection.createRange().text = theSelection
						+ " ";
			} else {
				document.selection.createRange().text = theSelection;
			}
			// Mozilla
		} else if (txtarea.selectionStart || txtarea.selectionStart == '0') {
			var startPos = txtarea.selectionStart;
			var endPos = txtarea.selectionEnd;
			var myText = (txtarea.value).substring(startPos, endPos);
			if (!myText) {
				myText = content;
			}
			if (myText.charAt(myText.length - 1) == " ") { 
				subst = myText.substring(0, (myText.length - 1)) + " ";
			} else {
				subst = myText;
			}
			txtarea.value = txtarea.value.substring(0, startPos)+ subst+ txtarea.value.substring(endPos,txtarea.value.length);
			txtarea.focus();
			var cPos = startPos + (myText.length);
			txtarea.selectionStart = cPos;
			txtarea.selectionEnd = cPos;
			// All others
		} else {
			txtarea.value += content;
			txtarea.focus();
		}
		if (txtarea.createTextRange)
			txtarea.caretPos = document.selection.createRange().duplicate();
	}
});

/**
 * 转换json数据中的日期类型.
 * @param formData
 */
function _ConvertFormData(formData){
	for(var key in formData){
		var val=formData[key];
		if(!val) continue;
		if(val instanceof Date){
			var tmp=val.isShortDate()?val.format("yyyy-MM-dd"):val.format("yyyy-MM-dd hh:mm:ss");
			formData[key]=tmp;
		}
	}
}

/**
 * 用户选择按钮点击事件
 * 
 * @param e
 */
function _RelatedSolutionButtonClick(EL,config){
	var relatedSolutionSel=EL;
	var chooseitem=relatedSolutionSel.chooseitem;
	var flag=chooseitem=="single"?"false":"true";
	_OpenWindow({
		url: __rootPath+"/bpm/core/bpmInst/dialog.do?flag="+flag+"&solId="+relatedSolutionSel.solution,
        title: "选择实例",
        width: 700, height: 600,
        ondestroy: function(action) {
            if (action == 'ok') {
            	var iframe = this.getIFrameEl();
            	var instances=iframe.contentWindow.getInstances();
            	if(config.callback){
                    config.callback.call(this,instances);
                }
            }
        }
	});
}


function _ClearButtonEdit(e){
	var sender=e.sender;
	sender.setValue("");
	sender.setText("");
	sender.doValueChanged();
}



function handClick(btn,callback){
	btn.enabled=false;
	callback();
	btn.enabled=true;
}

(function($){
    $.fn.hoverDelay = function(options){
        var defaults = {
            // 鼠标经过的延时时间
            hoverDuring: 200,
            // 鼠标移出的延时时间
            outDuring: 200,
            // 鼠标经过执行的方法
            hoverEvent: function(){
                // 设置为空函数，绑定的时候由使用者定义
                $.noop();
            },
            // 鼠标移出执行的方法
            outEvent: function(){
                $.noop();    
            }
        };
        var sets = $.extend(defaults,options || {});
        var hoverTimer, outTimer;
        return $(this).each(function(){
            $(this).hover(function(){
                // 清除定时器
                clearTimeout(outTimer);
                hoverTimer = setTimeout(sets.hoverEvent,
                    sets.hoverDuring);
                }, function(){
                    clearTimeout(hoverTimer);
                    outTimer = setTimeout(sets.outEvent,
                        sets.outDuring);
                });    
            });
    }      
})(jQuery);


/**
 * 执行自定义SQL。
 * alias ： 自定义SQL别名
 * params ：自定义SQL的参数{参数名1:参数值1}
 * callBack : 执行自定义SQL的回调函数。
 * sync ： 是否同步执行
 * 
 * 调用方法：
 * doQuery("user",params,function(data){
 * });
 * 
 * @param params
 * @param callBack
 */
function doQuery(alias, params,callBack,async){
	async=async || false;
	var url=__rootPath+"/sys/db/sysSqlCustomQuery/queryForJson_"+alias+".do";
	url=encodeURI(url);
	var config={
		url : url,
		type : "POST",
		async : async,
		success : function(result, status) {				
			if (result && callBack) {
				callBack(result);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("出错,错误码=" + XMLHttpRequest.status);
			alert("出错=" + XMLHttpRequest.responseText);
		}
	}
	if(params){
		var type=typeof params;
		if(type=="object"){
			params=mini.encode(params);
		}
		var data={"params":params};
		config.data= data;
	}
	$.ajax(config);
}

/**
 * 获取浏览器版本。
 * @returns
 */
function getBrowser(){
    var sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    (s = ua.match(/edge\/([\d.]+)/)) ? sys.edge = s[1] :
    (s = ua.match(/rv:([\d.]+)\) like gecko/)) ? sys.ie = s[1] :
    (s = ua.match(/msie ([\d.]+)/)) ? sys.ie = s[1] :
    (s = ua.match(/firefox\/([\d.]+)/)) ? sys.firefox = s[1] :
    (s = ua.match(/chrome\/([\d.]+)/)) ? sys.chrome = s[1] :
    (s = ua.match(/opera.([\d.]+)/)) ? sys.opera = s[1] :
    (s = ua.match(/version\/([\d.]+).*safari/)) ? sys.safari = s[1] : 0;

    if (sys.edge) return { browser : "Edge", version : sys.edge };
    if (sys.ie) return { browser : "IE", version : sys.ie };
    if (sys.firefox) return { browser : "Firefox", version : sys.firefox };
    if (sys.chrome) return { browser : "Chrome", version : sys.chrome };
    if (sys.opera) return { browser : "Opera", version : sys.opera };
    if (sys.safari) return { broswer : "Safari", version : sys.safari };
    
    return { browser : "", version : "0" };
}
/**
 * 获取浏览器版本
 * 0 ：IE
 * 1 : Firefox，Chrome
 * -1: 不支持的浏览器
 */
function getBrowserType(){
	var o=getBrowser();
	if(o.browser=="IE" ){
		return 0;
	}
	else if(o.browser=="Firefox" || o.browser=="Chrome"){
		return 1;
	}
	return -1;
}


/**
 * 在表单中动态插入脚本。
 * @param aryScript	脚本。
 * @param id		容器ID可以不传
 * @returns
 */
function insertScript(aryScript,id){
	if(!aryScript) return;
	var container ;
	if(id){
		container = document.getElementById(id);	
	}
	else{
		container=document.body;
	}
	for(var i=0;i<aryScript.length;i++){
		var obj=aryScript[i];
		var newScript = document.createElement('script');
		newScript.type = 'text/javascript';
		if(obj.type=="src"){
			newScript.setAttribute("src",obj.content) ;
		}
		else{
			newScript.innerHTML = obj.content;
		}
		container.appendChild(newScript);
	}
}

/**
 * 解析html 将html和脚本进行分离。
 * 返回数据格式为:
 *	{html:"html内容",
 * 		script:[
 * 			{type:"src",content:"脚本地址"},
 * 			{type:"script",content:"脚本内容"}
 * 		]
 *	}
 * @param {Object} html
 */
function parseHtml(html){
	var regSrc=/<script\s*?src=('|")(.*?)\1\s*>(.|\n|\r)*?<\/script>/img;
	var match = regSrc.exec(html);
	var aryToReplace=[];
	var rtn=[];
	while (match != null) {
		aryToReplace.push(match[0]);
		var o={type:"src",content:match[2]};
		rtn.push(o);
		match = regSrc.exec(html);
	}
	//替换脚本
	for(var i=0;i<aryToReplace.length;i++){
		html=html.replace(aryToReplace[i],"");
	}
	aryToReplace=[];
	var regScript = /<script(?:\s+[^>]*)?>((.|\n|\r)*?)<\/script>/img;

	var match = regScript.exec(html);
	
	while (match != null) {
		aryToReplace.push(match[0]);
		var o={type:"script",content:match[1]};
		rtn.push(o);
		match = regScript.exec(html);
	}
	//替换脚本
	for(var i=0;i<aryToReplace.length;i++){
		html=html.replace(aryToReplace[i],"");
	}
	return {html:html,script:rtn};
}


/**
 * 弹出新窗口
 * @param url
 * @param name
 * @returns
 */
function openNewWindow(url,name){  
	var fulls = "left=0,screenX=0,top=0,screenY=0,scrollbars=1";    //定义弹出窗口的参数  
	if (window.screen) {  
	   var ah = screen.availHeight - 30;  
	   var aw = screen.availWidth ;  
	   fulls += ",height=" + ah;  
	   fulls += ",innerHeight=" + ah;  
	   fulls += ",width=" + aw;  
	   fulls += ",innerWidth=" + aw;  
	   fulls += ",resizable"  
	} else {  
	   fulls += ",resizable"; // 对于不支持screen属性的浏览器，可以手工进行最大化。 manually  
	}  
	var newWindow=window.open(url,name,fulls);
	return newWindow;
}  

/**
 * 打开office文档。
 * @param fileId
 * @returns
 */
function _openDoc(fileId){
	var url=__rootPath +"/scripts/customer/doc.jsp?fileId=" + fileId;
	openNewWindow(url,"officeDoc");
}  

/**
 * 打开pdf文档。
 * @param fileId
 * @returns
 */
function _openPdf(fileId){
	var url=__rootPath+'/scripts/PDFShow/web/viewer.html?file='+
	__rootPath+'/sys/core/file/download/'+fileId+'.do';
	openNewWindow(url,"officePdf");
}

/**
 * 打开图片。
 * @param fileId
 * @returns
 */
function _openImg(fileId){
	var url=__rootPath+'/scripts/customer/img.jsp?fileId='+fileId;
	openNewWindow(url,"officeIMG");
}


function _ShowTenantInfo(instId){
	_OpenWindow({
		title:'机构信息',
		iconCls:'icon-group',
		height:480,
		width:780,
		url:__rootPath+'/sys/core/sysInst/get.do?pkId='+instId
	});
}

function _ShowUserInfo(userId){
	_OpenWindow({
		title:'用户信息',
		iconCls:'icon-user',
		height:480,
		width:780,
		url:__rootPath+'/sys/org/osUser/get.do?pkId='+userId
	});
}

function _ShowGroupInfo(groupId){
	_OpenWindow({
		title:'用户组信息',
		iconCls:'icon-group',
		height:480,
		width:780,
		url:__rootPath+'/sys/org/osGroup/detail.do?groupId='+groupId
	});
}

/**
 * 显示审批明细
 */
function _ShowBpmInstInfo(instId){
	var url=__rootPath+'/bpm/core/bpmInst/inform.do?instId='+ instId;
	window.open(url);
}

/**
 * 打开列表中外部关联数据窗口
 */
function _ShowCusLink(title,linkType,url){
	if(linkType=='newWindow'){
		window.open(url);
	}else{
		_OpenWindow({
			title:title,
			height:500,
			width:800,
			url:url,
			max:true
		});
	}
}
//交互效果
$(document)
	.on('mouseover','.p_top',function(){
		var p_top = "",
			p_time = "";
		if($(this).attr('data-pos')){
			p_top = $(this).attr('data-pos');
		}else{
			p_top = 2;
		}
		
		if($(this).attr('data-time')){
			p_time = $(this).attr('data-time');
		}else{
			p_time = 200;
		}
		
		$(this).animate( {top: -p_top} , p_time );
	})
	.on('mouseleave','.p_top',function(){
		$(this).stop(true,false).animate( {top:0} , 200 );
	})
	.on('click','.theme',function(){
		var dataSrc = $(this).attr('data-src');
		$.cookie("index",dataSrc,{ expires: 365});
		window.location.href = __rootPath+"/index.do";
	})
	.on('mouseover','.top_icon li',function(){
		$(this).children('dl').stop(false,true).slideDown(300);
	})
	.on('mouseleave','.top_icon li',function(){
		$(this).children('dl').stop(false,true).slideUp(100);
	})
	.on('click' , '.mini-tips .icon-tips-Close' , function(){//提前关闭弹窗
		$(this).parent().slideUp(400);
	})
	.on('click' , '.refreshWelcome' , function(){//刷新首页
		$('#welcome', parent.document ).attr('src',$('#welcome', parent.document ).attr('src'));
	})
	.ready(function (){
		if($('.mini-panel-body .mini-grid-rows-view').length){
			try{
				if(!grid)return;
				grid.on('load',function(e){
					if(e.data.length>14){//大于15条数据展开搜索框
						$('.searchSelectionBtn').trigger("click").addClass('_open').prev('.search-form').show();
						
					}else if(e.data.length == 0 && !$('.listEmpty').length){
						$('.mini-panel-body .mini-grid-rows-view').append("<div class='listEmpty'><img src='"+__rootPath+"/styles/images/index/empty.png'/><span>暂无数据</span></div>");
					}else if($('.listEmpty').length){
						$('.listEmpty').remove();
					}
				})
			}catch(e){
				return;
			}
		}
	}); 

/**
 * 根据编辑器获取表格和行。
 * @param editor
 * @returns
 */
function getGridByEditor(editor) {
    var grids = mini.findControls(function (control) {
        if (control.type == "datagrid") return true;
    })
    for (var i = 0, l = grids.length; i < l; i++) {
        var grid = grids[i];
        var row = grid.getEditorOwnerRow(editor);
        if(row){
        	return {grid:grid,row:row};
        }
    }
    return null;
}


function addBody(){
	$('body').addClass('newBody');
};

function indentBody(){
	if(!$('.mini-window',parent.document).length){
		$('.mini-tabs-body:visible', parent.document).addClass('index_box');
		$('.show_iframe:visible', parent.document).addClass('index_box');
	}
}

/*
 * 
 * 展开收起
 *
 */
function no_search(This,ID){
	$(This).toggleClass("_open");
	$("#"+ID+"").slideToggle(300);
	setTimeout("mini.layout()",300);
}

/*
 * 打印
 */
function Print(){
	$('body').addClass('bodyPrint')
	window.print()
	setTimeout(
		function(){
			$('body').removeClass('bodyPrint');
		}
	,100);
}

/**
*
* @param num
* @param precision
* @param separator
* @returns {*}
*=======================================================
*     formatNumber(10000)="10,000"
*     formatNumber(10000, 2)="10,000.00"
*     formatNumber(10000.123456, 2)="10,000.12"
*     formatNumber(10000.123456, 2, ' ')="10 000.12"
*     formatNumber(.123456, 2, ' ')="0.12"
*     formatNumber(56., 2, ' ')="56.00"
*     formatNumber(56., 0, ' ')="56"
*     formatNumber('56.')="56"
*     formatNumber('56.a')=NaN
*=======================================================
*/
function formatNumber(num, precision, separator) {
   var parts;
   // 判断是否为数字
   if (!isNaN(parseFloat(num)) && isFinite(num)) {
       // 把类似 .5, 5. 之类的数据转化成0.5, 5, 为数据精度处理做准, 至于为什么
       // 不在判断中直接写 if (!isNaN(num = parseFloat(num)) && isFinite(num))
       // 是因为parseFloat有一个奇怪的精度问题, 比如 parseFloat(12312312.1234567119)
       // 的值变成了 12312312.123456713
       num = Number(num);
       // 处理小数点位数
       num = (typeof precision !== 'undefined' ? num.toFixed(precision) : num).toString();
       // 分离数字的小数部分和整数部分
       parts = num.split('.');
       // 整数部分加[separator]分隔, 借用一个著名的正则表达式
       parts[0] = parts[0].toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1' + (separator || ','));

       return parts.join('.');
   }
   return NaN;
}


/**
* 把数字转换成货币的格式
* @param decimals
* @param dec_point
* @param thousands_sep
* @returns {string}
*/
Number.prototype.format=function(decimals, dec_point, thousands_sep){
   var num = (this + '')
       .replace(/[^0-9+\-Ee.]/g, '');
   var n = !isFinite(+num) ? 0 : +num,
       prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
       sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
       dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
       s = '',
       toFixedFix = function(n, prec) {
           var k = Math.pow(10, prec);
           return '' + (Math.round(n * k) / k)
                   .toFixed(prec);
       };
   // Fix for IE parseFloat(0.55).toFixed(0) = 0;
   s = (prec ? toFixedFix(n, prec) : '' + Math.round(n))
       .split('.');
   if (s[0].length > 3) {
       s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
   }
   if ((s[1] || '')
           .length < prec) {
       s[1] = s[1] || '';
       s[1] += new Array(prec - s[1].length + 1)
           .join('0');
   }
   return s.join(dec);
}




