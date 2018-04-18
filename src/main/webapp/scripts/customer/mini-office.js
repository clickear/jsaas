/**
 * office 控件。
 * 表单提交统一使用此方法进行提交,中间为回调函数,即在office控件保存完毕后调用回调函数。
 * OfficeControls.save(function(){})
 * 
 */
var OfficeControls={
		controls:{},
		amount:0,
		success:0,
		fail:0,
		callBack:null
};

/**
 * 添加控件。
 */
OfficeControls.addControl=function(control){
	this.controls[control.getName()]=control;
	this.amount++;
}

/**
 * 控件数量减1.
 */
OfficeControls.decreaseAmount=function(){
	this.amount--;
}

/**
 * 获取控件。
 */
OfficeControls.getControl=function(name){
	return this.controls[name];
}

/**
 * 成功的数量
 */
OfficeControls.successInc=function(){
	this.success++;
}

/**
 * 失败的次数
 */
OfficeControls.failInc=function(){
	this.fail++;
}

/**
 * 重置数量。
 */
OfficeControls.reset=function(){
	this.fail=0;
	this.success=0;
}

/**
 * 保存后调用回调方法。
 */
OfficeControls.save=function(callBack){
	//没有office控件的情况，直接回调。
	if(this.amount==0){
		if(callBack){
			callBack();
		}
		return;
	}
	var type=getBrowserType();
	//chrome
	if(type==1){
		this.callBack=callBack;
	}
	for(var key in this.controls){
		var control=this.controls[key];
		control.save();
	}
	//ie 的情况。
	if(type==0 ){
		//上传完成
		var complete=OfficeControls.success+OfficeControls.fail;
		if(!OfficeControls.fail){
			OfficeControls.callBack();
			OfficeControls.reset();
		}
		else{
			alert("office 文件保存失败!")
		}
	}
}



/**
 * office 控件用法：
 * <div class="mini-office"   style="height:600px;width:100%" 
    	readonly="false" name="office"  value="{type:'docx',id:'2400000001241017'}" ></div>
 * readonly:是否只读
 * value:{type:'docx',id:'文档ID'}
 * version:是否支持版本
 */

if (!window.UserControl) window.UserControl = {};
/**
 * office控件
 */
UserControl.OfficeControl = function () {
    UserControl.OfficeControl.superclass.constructor.call(this);
	this.initComponents();
	this.bindEvents();
}

mini.extend(UserControl.OfficeControl, mini.Panel, {
    uiCls: 'mini-office',
    formField:true,
    name:"",
    value:"",
    readonly:false,
    required:false,
    officeObj:null,
    type:"docx",
    officeId:"",
    userName:"",
    version:true,
    
    initComponents: function () {
    	
    	var html='<div id="toolbar1" class="mini-toolbar" style="padding:2px;"> \
    				<table style="width:100%;">\
    					<tr>	\
    						<td style="width:100%;"> \
            					<a class="mini-button" name="newDocBtn"  plain="true">新建</a>\
            					<a class="mini-button" name="openBtn"  plain="true">打开</a>\
    							<a class="mini-button" name="saveBtn"  plain="true">保存</a>\
    							<a class="mini-button" name="printBtn"  plain="true">打印</a>\
    							<a class="mini-button" name="printSettingBtn"  plain="true">打印设置</a>\
            					<span class="separator"></span>\
					    		<a class="mini-button" name="saveMarkBtn"  plain="true">留痕</a>\
								<a class="mini-button" name="noSaveMarkBtn"  plain="true">不留痕</a>\
								<a class="mini-button" name="clearMarkBtn"  plain="true">清除痕</a>\
    						</td>\
    					</tr> \
    				</table>\
    			</div>';
    	
    	this.set({
    		showHeader:false,
    		showToolbar:true,
    		showFooter:false,
    		toolbar:html
    	}); 
    	
    	this.initUser();
    },
    bindEvents: function () {
    	var newDocBtn = mini.getByName('newDocBtn', this);
        var openBtn = mini.getByName('openBtn', this);
        var saveBtn = mini.getByName('saveBtn', this);
        var printBtn = mini.getByName('printBtn', this);
        var printSettingBtn = mini.getByName('printSettingBtn', this);
        
        var saveMarkBtn = mini.getByName('saveMarkBtn', this);
        var noSaveMarkBtn = mini.getByName('noSaveMarkBtn', this);
        var clearMarkBtn = mini.getByName('clearMarkBtn', this);
        
        
        var self_=this;
        newDocBtn.on('click', function (e) {
        	self_.officeObj.ShowDialog(0);
        	self_.type=self_.getDocExt("type_" +self_.officeObj.DocType);
        	self_.toggleMarkButton();
        });
        
        openBtn.on('click', function (e) {
        	self_.officeObj.ShowDialog(1);
        	self_.type=self_.getDocExt("type_" +self_.officeObj.DocType);
        	
        	self_.toggleMarkButton();
        });
        
        saveBtn.on('click', function (e) {
        	self_.officeObj.ShowDialog(2);
        	self_.type=self_.getDocExt("type_" +self_.officeObj.DocType);
        	
        	self_.toggleMarkButton();
        });
        
        printBtn.on('click', function (e) {
        	self_.officeObj.PrintPreview();
        });
        
        printSettingBtn.on('click', function (e) {
        	self_.officeObj.ShowDialog(5);
        });
        
        
        saveMarkBtn.on('click', function (e) {
        	self_.officeObj.ActiveDocument.TrackRevisions=true;
        });
        
        noSaveMarkBtn.on('click', function (e) {
        	self_.officeObj.ActiveDocument.TrackRevisions=false;
        });
        
        clearMarkBtn.on('click', function (e) {
        	self_.officeObj.ActiveDocument.AcceptAllRevisions();
        });
    },
    getHtml:function(){
    	var type=getBrowserType();
    	var html="";
    	//IE
    	if(type==0){
    		html='<object id="'+this.getName()+'_OCX" classid="clsid:A39F1330-3322-4a1d-9BF0-0BA2BB90E970" \
    			codebase="'+__rootPath+'/iWebOffice/OfficeControl.cab#Version=5,0,3,0" width="100%" height="100%">\
		    		<param name="Menubar" value="0" />\
		    		<param name="Statusbar" value="0" />\
		    		<param name="Titlebar" value="0" />\
		    		<param name="Toolbars" value="1" />\
		    	</object>';
    	}
    	else if(type==1){
    		html='<object id="'+this.getName()+'_OCX" clsid="{A39F1330-3322-4a1d-9BF0-0BA2BB90E970}" \
			 type="application/ntko-plug" codebase="'+__rootPath+'/iWebOffice/OfficeControl.cab#version=5,0,3,0" \
				width="100%" height="100%" ForOnSaveToURL="OnSaveToURL" ForOnDocumentOpened="OnDocumentOpened_'+this.getName()+'" \
				 _titlebar="false" \
				 _menubar="false" \
				 _toolbars="true" \
				 _statusbar="false" \
				 _caption=" NTKO OFFICE文档控件示例演示"> \
				 <span style="color:red">\
				 	不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。\
				 </span>   \
			</object> ';
    	}
    	return html;
    },
    /**
     * 动态插入脚本，用于chrome浏览器打开时回调执行。
     */
    insertScript:function(){
    	var name=this.getName();
    	var newScript = document.createElement('script');
    	var script='function OnDocumentOpened_' + name +'(){ \
    		documentOpened("'+name+'") \
    	}';
    	newScript.innerHTML=script;
    	document.body.appendChild(newScript);
    },
    setName:function(name_){
    	this.name=name_;
    	var bodyHtml=this.getHtml();
    	this.set({
    		body:bodyHtml
    	});
    	// 获取控件。
    	this.officeObj=document.getElementById(this.getName()+'_OCX');
    	
    	var type=getBrowserType();
    	//浏览器为chrome的时候，动态插入脚本。
        if(type==1){
        	//插入脚本
        	this.insertScript();
        }
        //加入到控件管理。
    	OfficeControls.addControl(this);
    	
    },
    getName:function(){
    	return this.name;
    },
    /**
	 * 设置只读。
	 */
	setReadonly:function(val){
		//只读控件数量减1
		if(val){
			OfficeControls.decreaseAmount();
		}
		this.readonly=val;
		this.initDoc();
	},
	getReadonly:function(){
		return this.readonly;
	},
	setRead:function(){
		this.officeObj.SetReadOnly(this.readonly);
	},
    
    getValue:function(){
    	return this.value;
    },
    setValue:function(val){
    	this.updValue(val);
    },
    setUsername:function(val){
    	this.username=val;
    	this.officeObj.WebUserName=val;
    },
    getUsername:function(){
    	return this.username;
    },
    updValue:function(val){
    	var json=eval("(" +val +")");
		this.officeId=json.id;
		this.type=json.type;
    	this.value=val;
    },
    //新建文档
    newDoc:function(){
    	this.officeObj.SetReadOnly(this.readonly);
		try{
			this.officeObj.CreateNew("Word.Document");
		}
		catch(err){
			try{
				this.controlObj.CreateNew("WPS.Document");
			}catch(err){
				alert("newDoc:" +err.name + ": " + err.message);
			}
		}
	},
	toggleMarkButton:function(){
		var visible=(this.type=="docx");
		var saveMarkBtn = mini.getByName('saveMarkBtn', this);
        var noSaveMarkBtn = mini.getByName('noSaveMarkBtn', this);
        var clearMarkBtn = mini.getByName('clearMarkBtn', this);
        saveMarkBtn.setVisible(visible);
        noSaveMarkBtn.setVisible(visible);
        clearMarkBtn.setVisible(visible);
	},
	getFile:function(o){
		var path=__rootPath +"/sys/core/office/download/"+o.fileId+".do";
		var tmp="<a target='_blank' href='" +path +"'>" +o.version +"</a>"; 
		return tmp;
	},
	/**
	 * 加载历史版本。
	 */
	initVersion:function(){
		var urlVer=__rootPath +"/sys/core/office/officever/"+this.officeId+".do";
		var self_=this;
		$.get(urlVer,function(data){
			console.info(data);
			if(!data.supportVer) return;
			var ary=[];
			for(var i=0;i<data.vers.length;i++){
				var o=data.vers[i];
				ary.push(self_.getFile(o));
			}
			var html="版本:" + ary.join(",");
			
			self_.getFooterEl().innerHTML="";
			self_.set({
	    		showFooter:true,
	    		footer:html
	    	}); 
		})
	},
	initUser:function(){
		//userName
		var urlUser=__rootPath +"/sys/core/office/getUser.do";
		var self_=this;
		$.get(urlUser,function(data){
			console.info("initUser:" );
			console.info(data );
			self_.setUsername(data.name);
		});
	},
	//加载文档。
	initDoc:function(){
		var val=this.value;
		//指定了文件。
		if(val){
			var doctype=this.getDocType(this.type)
			var url=__rootPath +"/sys/core/office/office/"+this.officeId+".do";
			try{
				this.officeObj.OpenFromURL(url,null,doctype);
				//初始化版本
				this.initVersion();
			}
			catch(err){
				this.newDoc();
			}
		}
		//新建文档。
		else{
			this.newDoc();
		}
	},
	/*
	 * 保存
	 **/
	save:function(){
		var params="type=" +this.type +"&name=" + this.name +"&ver=" + this.version;
		if(this.officeId){
			params+="&officeId="+ this.officeId;
		}
		var browserType=getBrowserType();
		var url=__rootPath +"/sys/core/office/saveOffice.do";
		//IE的情况
		if(browserType==0){
			
			var result=this.officeObj.SaveToURL(url, "doc",params,"", "", true);
			var json=eval("(" + result + ")");
			if(json.success){
				//获取ID
				this.officeId=json.data.split(",")[0];
				var rtn={id:this.officeId,type:this.type};
				this.value=JSON.stringify(rtn);
				OfficeControls.successInc();
				//初始化版本
				this.initVersion();
			}
			else{
				OfficeControls.failInc();
			}
		}
		// chrome 的情况。
		else if(browserType==1){
			this.officeObj.SaveToURL(url, "doc",params,"", "", true);
		}
	},
	getDocType:function(type){
		var json={
				"docx":"Word.Document","xlsx":"Excel.Sheet","pptx":"PowerPoint.Show",
				"vsdx":"Visio.Drawing","mpp":"MSProject.Project","wps":"WPS.Document"
		}
		var docType="Word.Document";
		if(json[type]){
			docType=json[type];
		}
		return docType;
	},
	/**
	 * 设置是否支持版本
	 */
	setVersion:function(val){
		this.version=val;
	},
	getVersion:function(){
		return this.version;
	},
	/**
	 * 获取扩展名称。
	 */
	getDocExt:function(type){
		var o={"type_1":"docx","type_2":"xlsx","type_3":"pptx","type_4":"vsdx","type_5":"mpp","type_6":"wps"};
		return o[type];
	},
    getAttrs: function (el) {
    	var attrs = UserControl.OfficeControl.superclass.getAttrs.call(this, el);
    	mini._ParseBool(el, attrs,["readonly","version"]);
    	mini._ParseString(el, attrs,["value","name","username"]);
        return attrs;
    }
});

mini.regClass(UserControl.OfficeControl, "officeControl");


/**
 * 用于chrome 进行异步处理。
 * 1.对控件进行返回值处理。
 * 2.计数器加1。
 * @param type
 * @param code
 * @param html
 * @returns
 */
function OnSaveToURL(type,code,html){ 
	var json=eval("(" + html + ")");
	if(json.success){
		var rtn=json.data;
		
		//fileId,type,name
		var aryRtn=rtn.split(",");
		var name=aryRtn[2];
		var control=OfficeControls.getControl(name);
		var val="{id:\""+aryRtn[0]+"\",type:\""+aryRtn[1]+"\"}";
		control.updValue(val);
		OfficeControls.successInc();
		//重新加载版本
		control.initVersion();
	}
	else{
		OfficeControls.failInc();
	}
	
	var complete=OfficeControls.success+OfficeControls.fail;
	//所有的控件都上传完毕。
	if(complete==OfficeControls.amount){
		if(!OfficeControls.fail){
			OfficeControls.callBack();
			OfficeControls.reset();
		}
		else{
			alert("office 文件保存失败!")
		}
	}
}

/**
 * 用于chrome 进行异步处理。
 * @param name
 * @returns
 */
function documentOpened(name){
	var control=OfficeControls.getControl(name);
	control.setRead();
	control.initVersion();
}
