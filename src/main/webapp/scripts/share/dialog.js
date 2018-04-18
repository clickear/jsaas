
/**
 * 打开数据源对话框。
 * @param callBack
 *  callBack ：数据格式为
 *  {
 *  	alias:"数据源别名",
 *  	name:"数据源显示名称"
 *  }
 */
function openDatasourceDialog(callBack){
	var url=__rootPath+"/sys/core/sysDatasource/dialog.do";
	mini.open({
		url : url,
		title : "选择数据源",
		width : 650,
		height : 480,
		ondestroy : function(action) {
			if(action != "ok") return;
			var iframe = this.getIFrameEl();
			var data = iframe.contentWindow.GetData();
			data = mini.clone(data); //必须
			if (data && callBack) {
				callBack(data);
			}
		}
	});
}


/**
 * 打开自定义查询对话框。
 * @param callBack
 *  callBack ：数据格式为
 *  {
 *  	alias:"数据源别名",
 *  	name:"数据源显示名称"
 *  }
 */
function openCustomQueryDialog(callBack){
	var url=__rootPath+"/sys/db/sysDbSql/dialog.do";
	mini.open({
		url : url,
		title : "选择自定义SQL",
		width : 800,
		height :580,
		ondestroy : function(action) {
			if (action != "ok") return;
			var iframe = this.getIFrameEl();
			var data = iframe.contentWindow.GetData();
			data = mini.clone(data); 
			if (data && callBack) {
				callBack(data);
			}
		}
	});
}

/**
 * 选择解决方案对话框。
 * @param single	是否单选
 * @param callBack	回调函数(参数类型为bpmsolution数组)
 * {name:"",key:"",solId:""}
 */
function openBpmSolutionDialog(single,callBack){
	var strSingle=single?"true":"false";
	_OpenWindow({
		url: __rootPath + "/bpm/core/bpmSolution/dialog.do?single="+strSingle,
        title: "选择方案", width: "800", height: "600",
        ondestroy: function(action) {
        	if(action!="ok") return ;
       		var iframe = this.getIFrameEl().contentWindow;
           	var solutions=iframe.getSolutions();
           	if(callBack){
           		callBack(solutions)
           	}
        }
	});
}

/**
 * bo模型选择。
 * @param supportType db,all,json
 * @param callBack
 * @param multi 是否多选
 */
function openBoDefDialog(supportType,callBack,multi){
	var url=__rootPath+'/sys/bo/sysBoDef/dialog.do';
	if(supportType=="db" || supportType=="all"){
		//url+="?Q_SUPPORT_DB__S_EQ=yes";
	}
	var tmp=(multi)?"true":"false";
	if(url.indexOf("?")==-1){
		url+="?multi=" + tmp;
	}
	else{
		url+="&multi=" + tmp;
	}
	
	_OpenWindow({
		url:url,
		height:450,
		width:800,
		title:'业务模型选择',
		ondestroy:function(action){
			if(action=='cancel')return;
			var iframe = this.getIFrameEl();
            var bodefs = iframe.contentWindow.getBoDefs();
            if(callBack){
            	if(bodefs.length>0){
            		if(tmp=="true"){
            			callBack(action, bodefs);
            		}
            		else{
            			callBack(action, bodefs[0]);
            		}
            	}
            }
            else{
            	alert(bodefs);
            }
		}
	});
}

/**
 * 打开表单选择对话框
 * @param conf
 * single:是否单选
 * callBack:回调方法(参数为formView数组对象)
 * bodefId :bo定义ID
 */
function openBpmFormViewDialog(conf){
	var single=conf.single?"true":"false";
	var bodefId=conf.bodefId;
	var url=__rootPath+'/bpm/form/bpmFormView/dialog.do?single='+ single;
	//如果选择bo定义。
	if(bodefId){
		url+='&boDefIds=' +bodefId;
	}
	_OpenWindow({
		url:url,
		height:400,
		width:780,
		title:'表单视图对话框',
		ondestroy:function(action){
			if(action!='ok')return;
			var formView=this.getIFrameEl().contentWindow.getFormView();
			if(conf.callBack){
				conf.callBack(formView);
			}
		}
	});
}

/**
 * 打开手机对话框。
 * @param conf
 * @returns
 */
function openBpmMobileFormDialog(conf){
	var single=conf.single?"true":"false";
	var bodefId=conf.bodefId;
	var url=__rootPath+'/bpm/form/bpmMobileForm/dialog.do?single='+ single;
	//如果选择bo定义。
	if(bodefId){
		url+='&boDefIds=' +bodefId;
	}
	_OpenWindow({
		url:url,
		height:400,
		width:780,
		title:'手机表单选择对话框',
		ondestroy:function(action){
			if(action!='ok')return;
			var formView=this.getIFrameEl().contentWindow.getFormView();
			if(conf.callBack){
				conf.callBack(formView);
			}
		}
	});
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
		height:340,
		width:506,
		iconCls:'icon-user',
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
 * 通用的buttonedit清除事件处理。
 * @param e
 */
function clearButtonEdit(e){
	var btn=e.sender;
	btn.setValue("");
	btn.setText("");
}

/**
 * 打开流程图对话框。
 * @param conf
 * {
 * 	solId:"",
 * 	actDefId:""
 * }
 */
function openFlowChartDialog(conf){
	var url=__rootPath+"/bpm/core/bpmInst/flowChart.do";
	if(conf.solId){
		url+="?solId="+conf.solId;
	}
	else if(conf.actDefId){
		url+="?actDefId="+conf.actDefId;
	}
	
	
	_OpenWindow({
		url : url,
		title : "流程图对话框",
		width : 650,
		height : 480,
		max:true
	});
}

/**
 * 打开profile对话框。
 * @param conf	
 * conf :{
 * 	onload:初始化函数处理，参数iframe
 * 	onOk: 点击ok处理。
 *  hideRadio:YES 是否隐藏RADIO。
 * }
 * @returns
 */
function openProfileDialog(conf){
	var hideRadio=conf.hideRadio;
	var url=__rootPath + "/sys/core/public/profileDialog.do";
	if(hideRadio=="YES"){
		url+="?hideRadio=YES";
	}
	_OpenWindow({
		url: url,
        title: "权限配置", width: "800", height: "600",
        onload:function(){
        	var iframe = this.getIFrameEl().contentWindow;
        	if(conf.onload){
        		conf.onload(iframe);
        	}
        },
        ondestroy: function(action) {
        	if(action!="ok") return;
        	var iframe = this.getIFrameEl().contentWindow;
        	if(conf.onOk){
        		conf.onOk(iframe.getData());
        	}
        }
	});
}

/**
 * 根据解决方案Id 获取对应的流程节点对话框。
 * @param solId
 * @param callback
 * @returns
 */
function openSolutionNode(actDefId,conf,callback){
	var url=__rootPath + "/bpm/core/bpmSolution/nodeDialog.do?actDefId="+ actDefId;
	url+="&single="+conf.single;
	var end="false";
	if(conf.end){
		end=conf.end;
	}
	var start="false";
	if(conf.start){
		start=conf.start;
	}
	url+="&end=" + end;
	url+="&start=" + start;
	
	_OpenWindow({
		url: url,
        title: "选择流程节点", width: "800", height: "600",
        onload:function(){
        	var iframe = this.getIFrameEl().contentWindow;
        	if(conf.onload){
        		conf.onload(iframe);
        	}
        },
        ondestroy: function(action) {
        	if(action!="ok") return;
        	var iframe = this.getIFrameEl().contentWindow;
        	if(callback){
        		callback(iframe.getData());
        	}
        }
	}); 
}

