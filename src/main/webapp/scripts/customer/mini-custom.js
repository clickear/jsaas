ParamsJson={is_Template_loaded:false,editor_js_loaded:false,related_Template_loaded:false};

/**
 * 加载模版。
 */
function includeFormTemplate(){
	if(ParamsJson.is_Template_loaded) return;
	var url=__rootPath +"/scripts/customer/template.html";
	var fileContent=$.getFile(url);
	var jqTemplate=$("#formTemplate");
	if(jqTemplate.length==0){
		$("body").append(fileContent);
		ParamsJson.is_Template_loaded=true;
	}
}

//设置左分隔符为 <#
baidu.template.LEFT_DELIMITER='<#';
//设置右分隔符为 #>
baidu.template.RIGHT_DELIMITER='#>';

var baiduTemplate=baidu.template;


if (!window.UserControl) window.UserControl = {};


/**
 * 附件控件
 * <div id="file1" name="file1" class="upload-panel" isone="false" sizelimit="50"
        style="width:auto;" isDown="false" isPrint="false" readOnly="true"
   value=''></div> 
   获取数据：
   mini.get("file1").getValue();
   设置数据：
   var val=[{"fileId":"1","fileName":"a.doc","totalBytes":22016},{"fileId":"2","fileName":"1.png","totalBytes":130476}];
   mini.get("file1").setValue(val);
   
   isDown:是否允许下载
   isPrint:是否显示打印
   readOnly:是否只读
 */
UserControl.UploadPanel = function () {
    UserControl.UploadPanel.superclass.constructor.call(this);
	this.initComponents();
	this.bindEvents()
}

mini.extend(UserControl.UploadPanel, mini.Panel, {
    uiCls: 'upload-panel',
    formField:true,
    files:{},
    readOnly:false,
    required:false,
    initComponents: function () {
    	//加载模版到页面。
    	includeFormTemplate();
    	var html="<a name='upload' class='mini-button' iconCls='icon-upload' >上传</a>" +
    			"<a name='clean' class='mini-button' iconCls='icon-clear' >清空</a>";
    	
    	this.clearData();
    	
    	this.set({
    		showHeader:false,
    		showToolbar:true,
    		showFooter:false,
    		toolbar:html
    	});    
    },
    
    bindEvents: function () {
        this.uploadBtn = mini.getByName('upload', this);
        this.cleanBtn = mini.getByName('clean',this);
        var self_ = this;
        
       
       
        this.uploadBtn.on('click', function (e) {
        	 var gridRow=getGridByEditor(self_);
            
        	_UploadDialogShowFile({
				from:'SELF',
				types:self_.fileType,
				single:false,
				onlyOne:self_.isone,
				sizelimit:self_.sizelimit,
				showMgr:false,
				callback:function(files){
					self_.setUploadFile(files,gridRow);
				}
			});
        });
        this.cleanBtn.on('click', function (e) {
        	that.cleanFile();
        });   
        
    },
    set: function (kv) {
        var value = kv.value;
        delete kv.value;
        UserControl.UploadPanel.superclass.set.call(this,kv);
        if (!mini.isNull(value)) {
            this.setValue(value);
        }
    },
    /**
     * 返回数据。
     */
    getValue: function () {
    	var tmp=this.getFiles();
    	if(tmp.length==0) return "";
        return mini.encode(tmp);
    },
    
    /**
     * 转换文件
     */
    convertFile:function(upFile){
    
    	var obj={fileId:upFile.fileId,fileName:upFile.fileName,totalBytes:upFile.totalBytes};
    	return obj;
    },
    /**
     * 设置上传文件。
     */
    setUploadFile:function(upFiles,gridRow){
    	var tmpfiles=this.getFiles();
    	for(var i=0;i<upFiles.length;i++){
    		var upFile=upFiles[i];
    		var file=this.convertFile(upFile);
    		var fileName=file.fileName;
    		if(!this.isFileExist(fileName)){
    			tmpfiles.push(file);
    		}
    	}
    	//在子表中的修复。
    	if(gridRow){
    		var grid=gridRow.grid;
    		var row=gridRow.row;
    		var obj={};
    		obj[this.name]=this.getValue();
    		grid.updateRow(row,obj);
    	}
    	
    	this.displayFile();
    },
    
    
    /**
     * 显示文件。
     */
    displayFile:function(){
    	
    	this.getBodyEl().innerHTML="";
    	var html=this.getHtmls();
    	this.set({
    		body:html
    	});
    	this.bindRemove();
    	
    	
    },
    /**
     * 处理单个文件删除。
     */
    bindRemove:function(){
    	var parent=$(this.el);
    	var buttons=$(".removeFile", parent);
    	var self_=this;
    	for(var i=0;i<buttons.length;i++){
    		var btn=$(buttons[i]);
    		btn.bind("click",function(e){
    			var el=$(e.currentTarget);
    			var parentLi=$(e.currentTarget).closest("li");
    			var fileId=parentLi.attr("id").replace("li_","");
    			var files=self_.getFiles();
    			for(var j=0;j<files.length;j++){
    				if(fileId==files[j].fileId){
    					files.splice(j,1);
    				}
    			}
    			//加载。
    			self_.displayFile();
    		})
    		
    	}
    },
    /**
     * 判断文件是否存在。
     */
    isFileExist:function(fileName){
    	var tmpfiles=this.getFiles();
    	for(var j=0;j<tmpfiles.length;j++){
			if(fileName==tmpfiles[j].fileName){
				return true;
			}
		}
    	return false;
    },
    /**
     * 清除数据。
     */
    clearData:function(){
    	this.files[this.uid]=[];
    },
    /**
     * 设置数据。
     */
    setData:function(val){
    	this.files[this.uid]=val;
    },
    /**
     * 获取这个控件的数据。
     */
    getFiles:function(){
    	var tmp=this.files[this.uid];
    	return tmp;
    },
    /**
     * 清除文件
     */
    cleanFile:function(){
    	this.clearData();
    	var body=this.getBodyEl();
    	body.innerHTML="";
    },
    /**
     * 返回HTML
     */
    getHtmls:function(){
    	var tmpfiles=this.getFiles();
    	if(!tmpfiles || tmpfiles.length==0) return "";
    	var write=!this.readOnly;
    	var data={"list":tmpfiles,isDown:this.isDown,isPrint:this.isPrint,
    			ctxPath:__rootPath,write:write,enableOpenOffice:_enable_openOffice};
    	
    	var html=baiduTemplate('uploadFile',data);
    	return html;
    },
    setIsDown: function (value) {
        this.isDown=value;
    },
    setIsPrint: function (value) {
        this.isPrint=value;
    },
    setIsone: function (value) {
        this.isone=value;
    },
    setSizelimit:function (value) {
        this.sizelimit=value;
    },
    setFileType:function (value) {
        this.fileType=value;
    },
    /**
     * 是否只读。
     */
    setReadOnly: function (value) {
    	this.readOnly=value;
    	if(!value) return;
    	this.displayFile();
    	
		this.set({
			showHeader:false,
    		showFooter:false,
    		showToolbar:false,
    		toolbar:""
		})
    },
    /**
     * 设置数据。
     */
    setValue:function(val){
    	if(!val) val="[]";
    	if((typeof val)=="string"){
    		this.setData(mini.decode(val));
    	}
    	else{
    		this.setData(val);
    	}
    	this.displayFile();
    },
    getAttrs: function (el) {
        var attrs = UserControl.UploadPanel.superclass.getAttrs.call(this, el);
        mini._ParseBool(el, attrs,
            ["isPrint","isDown","readOnly","isone"]
        );
        mini._ParseString(el, attrs,
                ["sizelimit","fileType","value"]
            );
        return attrs;
    }
});

mini.regClass(UserControl.UploadPanel, "uploadPanelControl");


/**
 * 用户选择控件。
 * <input id="file1" name="file1" class="mini-user" 
 *       style="width:auto;"  readOnly="true" single="true"
 *  value='' />
 *  
 *  属性：
 *  single ：是否单选
 *  
 */
UserControl.MiniUser = function () {

    UserControl.MiniUser.superclass.constructor.call(this);
    
    this.initComponents();
    this.bindEvents();
}

mini.extend(UserControl.MiniUser, mini.ButtonEdit, {
    uiCls: 'mini-user',
   
    single: true,
    initComponents: function () {
    	this.setShowClose(true);
    	var el=this.getEl();
    	el.className="mini-buttonedit icon-user-button";
    },
    bindEvents: function () {
        this.on('buttonclick', _UserButtonClick);
        this.on("closeclick",_OnButtonEditClear);
    },
    setSingle: function (value) {
    	this.single=value;
    },
    getAttrs: function (el) {
        var attrs = UserControl.MiniUser.superclass.getAttrs.call(this, el);
        mini._ParseBool(el, attrs,["single"]);
        return attrs;
    }
});



mini.regClass(UserControl.MiniUser, "userControl");

/**
 * 用户组选择控件。
 * <div id="group" name="group" class="mini-group" 
        style="width:auto;" single="true" showDim="true" ></div>   
    single:是否为单选
    showDim: 是否显示维度。
    dimId: 维度ID
 */
UserControl.MiniGroup = function () {

    UserControl.MiniGroup.superclass.constructor.call(this);
    
    this.initComponents();
    this.bindEvents();
}

mini.extend(UserControl.MiniGroup, mini.ButtonEdit, {
    uiCls: 'mini-group',
    single: true,
    initComponents: function () {
    	this.setShowClose(true);
    	var el=this.getEl();
    	el.className="mini-buttonedit icon-group-button";
    },
    showDim:false,
    dimId:"",
    bindEvents: function () {
        this.on('buttonclick', _GroupButtonClick);
        this.on("closeclick",_OnButtonEditClear);
    },
    setSingle: function (value) {
    	this.single=value;
    },
    setShowDim:function(val){
    	this.showDim=val;
    },
    getShowDim:function(){
    	return this.showDim;
    },
    setDimId:function(val){
    	this.dimId=val;
    },
    getAttrs: function (el) {
        var attrs = UserControl.MiniGroup.superclass.getAttrs.call(this, el);
        mini._ParseString(el, attrs,["dimId"]);
        mini._ParseBool(el, attrs, ["single","showDim"]);
        return attrs;
    }
});

mini.regClass(UserControl.MiniGroup, "groupControl");

/**
 * 部门选择控件。
 * <div id="group" name="group" class="mini-dep" 
        style="width:auto;" single="true" ></div>   
    single:是否为单选按钮。
  
 */
UserControl.MiniDepartMent = function () {

    UserControl.MiniDepartMent.superclass.constructor.call(this);
    
    this.initComponents();
    this.bindEvents();
}

mini.extend(UserControl.MiniDepartMent, mini.ButtonEdit, {
    uiCls: 'mini-dep',
    single: true,
    initComponents: function () {
    	this.setShowClose(true);
    	var el=this.getEl();
    	el.className="mini-buttonedit icon-dep-button";
    },
    bindEvents: function () {
        this.on('buttonclick', _DepButtonClick);
        this.on("closeclick",_OnButtonEditClear);
    },
    setSingle: function (value) {
    	this.single=value;
    },
    
    getAttrs: function (el) {
        var attrs = UserControl.MiniDepartMent.superclass.getAttrs.call(this, el);
        mini._ParseBool(el, attrs, ["single"]);
        return attrs;
    }
});

mini.regClass(UserControl.MiniDepartMent, "depControl");

/**
 * 富文本框。
 * <div id="group" name="group" class="mini-ueditor" 
 *       style="width:auto;" readOnly="true" >初始值设置</div>
 * 属性：
 * readOnly：是否只读	       
 *  
 */
UserControl.MiniUEditor = function () {

    UserControl.MiniUEditor.superclass.constructor.call(this);
    
    this.initComponents();
    this.bindEvents();
}

mini.extend(UserControl.MiniUEditor, mini.Panel, {
    uiCls: 'mini-ueditor',
    formField:true,
    editor:null,
    readOnly:false,
    value:"",
    initComponents: function () {
    	
    	var el=this.getEl();
    	var body=this.getBodyEl();
    	body.id=this.uid +"_body";
	    this.set({
       		showHeader:false,
       		showToolbar:false,
       		showFooter:false
        });
	    
	  //加载UEDITOR.JS。
	    if(!ParamsJson.editor_js_loaded){
	    	var aryUrl=[
	    	         __rootPath+"/scripts/ueditor/ueditor-form.config.js" ,
	    	         __rootPath+"/scripts/ueditor/ueditor.all.min.js",
	    	         __rootPath+"/scripts/ueditor/lang/zh-cn/zh-cn.js" ];
	    	$.getScripts({urls: aryUrl });
	    	ParamsJson.editor_js_loaded=true;
	    }
	   this.loadEditor();
    },    
    /**
     * 加载编辑器。
     */
    loadEditor:function(){
    	var id=this.uid +"_body";
    	var self=this;
    	
    	
		setTimeout(function(){
			
			var bodyEl=self.getBodyEl();
			
    		var val=bodyEl.innerHTML;
			if(self.readOnly){
				$("#" + id).html(val);
	    	}
	    	else{
	    		var bodyEl=self.getBodyEl();
	    		var	parent=$(bodyEl).closest(".mini-panel");
        		var width=parent.width();
        		var height=parent.height();
        		var val=bodyEl.innerHTML;
        		
        		$(".mini-ueditor").parent('td').addClass('mini-ueditor-td');
        		$(bodyEl).css("overflow","hidden");
        		bodyEl.innerHTML="";
        		self.editor= UE.getEditor(id);
        		self.editor.addListener("ready", function () {
        			
        			//调整编辑器高度
        			var editor=$(".edui-editor",parent);
        			var toolBar=$(".edui-editor-toolbarbox",parent);
        			var iframe=$(".edui-editor-iframeholder",parent);
        			var toolBarHeight= 70;
        	
        			editor.width(width-8);
        			editor.height(height - 6);
        			iframe.width(width-8);
        			iframe.height(height-toolBarHeight-40);
        			self.editor.setContent(val);
            	});
	    	}
    	},10);
    },
    
    setReadOnly:function(val){
    	this.readOnly=val;
    },
    setValue:function(val){
    	this.value=val;
    	if(this.editor && this.readOnly !="true"){
    		this.editor.setContent(val);
    	}
    },
    getValue:function(){
    	if(!this.readOnly){
    		this.value= this.editor.getContent();
    	}
    	return this.value;
    },
   
    bindEvents: function () {
    },
    getAttrs: function (el) {
        var attrs = UserControl.MiniUEditor.superclass.getAttrs.call(this, el);
        mini._ParseBool(el, attrs,["readOnly"]);
        return attrs;
    }
    
});

mini.regClass(UserControl.MiniUEditor, "mini-editor");

/**
 * 图片控件。
 * <div id="group" name="group" class="mini-img"   style="width:auto;" value="图片ID" >初始值设置</div>
 * 属性：
 * value：{imgtype:'upload',val:""}     
 *    imgtype:
 *    upload: 上传
 *    url:直接填写URL
 * readOnly： 只读
 */
UserControl.MiniImg = function () {

    UserControl.MiniImg.superclass.constructor.call(this);
    
    this.initComponents();
    this.bindEvents();
}

mini.extend(UserControl.MiniImg, mini.Panel, {
    uiCls: 'mini-img',
    formField:true,
    readOnly:false,
    imgtype:"upload",
    value:"",
    initComponents: function () {
    	includeFormTemplate();
    	
    	var data={ctxPath:__rootPath};
    	var html=baiduTemplate('imgFile',data);
    	this.set({
    		showHeader:false,
    		showToolbar:true,
    		showFooter:false,
    		body:html
    	});
    },    
    setReadOnly:function(val){
    	this.readOnly=val;
    },
    setImgtype:function(val){
		if(val=="url"){
			var body=this.getBodyEl();
			var bodyContainer=$(body);
			var showBigPic=$(".showBigPic",bodyContainer);
			showBigPic.remove();
		}
    	this.imgtype=val;
    },
    /**
     * 设置值
     */
    setVal:function(val){
    	var str=mini.encode( {imgtype:this.imgtype,val:val});
    	this.setValue(str);
    },
    setValue:function(val){
    	var gridRow=getGridByEditor(this);
    	this.value=val;
    	if(val){
    		var obj=mini.decode(val);
    		var body=this.getBodyEl();
    		var bodyContainer=$(body);
    		var uploadFile=$(".upload-file",bodyContainer);
    		var imgVal=obj.val;
    		if(obj.imgtype=="upload"){
    			uploadFile.attr('src', __rootPath+'/sys/core/file/imageView.do?thumb=true&fileId=' + imgVal);
    		}
    		else{
    			if(imgVal.startWith("http")){
    				uploadFile.attr('src', imgVal);
    			}
    			else{
    				uploadFile.attr('src', __rootPath + imgVal);
    			}
    		}
    		if(gridRow){
        		var grid=gridRow.grid;
        		var row=gridRow.row;
        		var obj={};
        		obj[this.name]=val;
        		grid.updateRow(row,obj);
        	}
    	}
    },
    getValue:function(){
    	return this.value;
    },
   
    bindEvents: function () {
    	var self=this;
    	setTimeout(function(){
    		
    		
    		var body=self.getBodyEl();
    		var bodyContainer=$(body);
    		
    		var uploadFile=$(".upload-file",bodyContainer);
    		var showBigPic=$(".showBigPic",bodyContainer);
    		var showButton=$(".showButton",bodyContainer);
    		
    		var t;
    		if(!self.readOnly){
    			uploadFile.on('click', function() {
        			var img = this;
        			if(self.imgtype=="upload"){
        				_UserImageDlg(true, function(imgs) {
            				if (imgs.length == 0) return;
            				
            				var json={imgtype:'upload'};
            				json.val= imgs[0].fileId;
            				
            				self.setValue(mini.encode(json));
            			});
        			}
        			else{
        				_OpenWindow({
        					url:__rootPath+'/scripts/ueditor/dialogs/custom/imgurl.jsp',
        					height:200,
        					width:500,
        					title:"填写图片路径",
        					onload: function () {       //弹出页面加载完成
        				        var iframe = this.getIFrameEl(); 
        				        var val=self.getValue();
        				        if(!val) return;
        				        var json=mini.decode(val);
        				        iframe.contentWindow.setUrl(json.val); 
        				    },
        					ondestroy:function(action){
        						if(action!='ok')return;
        						var iframe = this.getIFrameEl();
        			            //获取选中、编辑的结果
        			            var url = iframe.contentWindow.getUrl();
        			            var json={imgtype:'url',val:url};
        			            self.setValue(mini.encode(json));
        					}
        				});
        			}
        			
        		});
    		} 
    		
    		uploadFile.on('mouseover', function() {
    			clearTimeout(t);
    			showBigPic.show();
    		});
    		uploadFile.on('mouseout', function() {
    			t = setTimeout(function(){
    					showBigPic.hide();
    				},500);
    		});
    		showButton.on('click',function(){
    			var fileValue = mini.decode(self.value);
    			var id = fileValue.val;
    			this.setAttribute("href",this.getAttribute("href1")+id);
    		});
    		
    	})
    	
    },
    getAttrs: function (el) {
        var attrs = UserControl.MiniImg.superclass.getAttrs.call(this, el);
        mini._ParseBool(el, attrs,["readOnly"]);
        mini._ParseString(el, attrs,["imgtype","val"]);
        return attrs;
    }
    
});

mini.regClass(UserControl.MiniImg, "imgControl");


/**
 * 月份控件。
 */
UserControl.MiniMonth = function () {

    UserControl.MiniMonth.superclass.constructor.call(this);
    
    this.initComponents();
    this.bindEvents();
}

mini.extend(UserControl.MiniMonth, mini.MonthPicker, {
    uiCls: 'mini-month',
    initComponents: function () {
    },
    bindEvents: function () {
      
    }
  
});

mini.regClass(UserControl.MiniMonth, "monthControl");


/**
 * 时间控件
 */
UserControl.MiniTime = function () {

    UserControl.MiniTime.superclass.constructor.call(this);
    
    this.initComponents();
    this.bindEvents();
}

mini.extend(UserControl.MiniTime, mini.TimeSpinner, {
    uiCls: 'mini-time',
    initComponents: function () {
    },
    bindEvents: function () {
    }
  
});

mini.regClass(UserControl.MiniTime, "timeControl");


UserControl.MiniForm = function () {

    UserControl.MiniForm.superclass.constructor.call(this);
    
    this.initComponents();
    this.bindEvents();
}

mini.extend(UserControl.MiniForm, mini.Panel, {
    uiCls: 'mini-form',
    formField:true,
    value:"",
    initComponents: function () {
    },
    bindEvents: function () {
    },
    getValue:function(){
    	var body=this.getBodyEl();
    	var obj={};
    	$("input",$(body)).each(function(){
    		var o=$(this);
    		obj[o.attr("name")]=o.val();
    	})
    	return JSON.stringify(obj);
    },
    setValue:function(val){
    	if(!val) return;
    	this.value=val;
    }
    
  
});

mini.regClass(UserControl.MiniForm, "formControl");


/**
 * <input name="solution" class="mini-relatedsolution" 
 * plugins="mini-relatedsolution" label="solution" solutionname="一个流程" 
 * solution="2400000012319374" chooseitem="single" mwidth="0" wunit="px" mheight="0" hunit="%" style=""/>
 */
UserControl.MiniRelatedSolution = function () {

    UserControl.MiniRelatedSolution.superclass.constructor.call(this);
    this.initComponents();
    this.bindEvents();
}



mini.extend(UserControl.MiniRelatedSolution, mini.Panel, {
    uiCls: 'mini-relatedsolution',
    formField:true,
    files:{},
    readOnly:false,
    required:false,
    initComponents: function () {
    	//加载模版到页面。
    	if(ParamsJson.related_Template_loaded) return;
    	var url=__rootPath +"/scripts/customer/relatedSolutionPanelTemplate.html";
    	var fileContent=$.getFile(url);
    	var jqTemplate=$("#relatedSolutionTemplate");
    	if(jqTemplate.length==0){
    		$("body").append(fileContent);
    		ParamsJson.related_Template_loaded=true;
    	}
    	
    	var html="<a name='relatedInstance' class='mini-button' iconCls='icon-upload' >选择实例</a>" +
    			"<a name='clean' class='mini-button' iconCls='icon-clear' >清空</a>";
    	
    	this.clearData();
    	
    	this.set({
    		showHeader:false,
    		showToolbar:true,
    		showFooter:false,
    		toolbar:html
    	});    
    },
    
    bindEvents: function () {
        this.relatedInstanceBtn = mini.getByName('relatedInstance', this);
        this.cleanBtn = mini.getByName('clean',this);
        var that = this;
       
        this.relatedInstanceBtn.on('click', function (e) {
        	_RelatedSolutionButtonClick(that,{callback:function(insts){
				that.setInstance(insts);
			}});
        });
        this.cleanBtn.on('click', function (e) {
        	that.cleanInst();
        });   
        
    },
    /**
     * 返回数据。
     */
    getValue: function () {
    	var tmp=this.getInsts();
    	if(tmp.length==0) return "";
        return mini.encode(tmp);
    },
    
    /**
     * 转换成实例对象
     */
    convertInstance:function(inst){
    
    	var obj={instId:inst.instId,subject:inst.subject};
    	return obj; 
    },
    /**
     * 设置实例。
     */
    setInstance:function(upFiles){
    	var tmpfiles=this.getInsts();
    	for(var i=0;i<upFiles.length;i++){
    		var upFile=upFiles[i];
    		var file=this.convertInstance(upFile);
    		var subject=file.subject;
    		if(!this.isInstExist(subject )){
    			tmpfiles.push(file);
    		}
    	}
    	this.displayInst();
    },
    
    
    /**
     * 显示实例。
     */
    displayInst:function(){
    	this.getBodyEl().innerHTML="";
    	var html=this.getHtmls();
    	this.set({
    		body:html
    	});
    	this.bindRemove();
    	
    	
    },
    /**
     * 处理单个实例删除。
     */
    bindRemove:function(){
    	var buttons=mini.getsbyName("removeInstance", this);
    	var self_=this;
    	for(var i=0;i<buttons.length;i++){
    		var btn=buttons[i];
    		btn.on('click', function (e) {
    			var btnObj=e.sender;
    			var el=btnObj.el;
    			var parentLi=$(el).closest("li");
    			var instId=parentLi.attr("id").replace("li_","");
    			var files=self_.getInsts();
    			for(var j=0;j<files.length;j++){
    				if(instId==files[j].instId){
    					files.splice(j,1);
    				}
    			}
    			//加载。
    			self_.displayInst();
    	    });   
    	}
    },
    /**
     * 判断实例是否已经选择。
     */
    isInstExist:function(fileName){
    	var tmpfiles=this.getInsts();
    	for(var j=0;j<tmpfiles.length;j++){
			if(fileName==tmpfiles[j].fileName){
				return true;
			}
		}
    	return false;
    },
    /**
     * 清除数据。
     */
    clearData:function(){
    	this.files[this.uid]=[];
    },
    /**
     * 设置数据。
     */
    setData:function(val){
    	this.files[this.uid]=val;
    },
    /**
     * 获取这个控件的数据。
     */
    getInsts:function(){
    	var tmp=this.files[this.uid];
    	return tmp;
    },
    setReadOnly: function (value) {
    	this.readOnly=value;
    	if(!value) return;
    	this.displayInst();
    	
		this.set({
			showHeader:false,
    		showFooter:false,
    		showToolbar:false,
    		toolbar:""
		})
    },
    /**
     * 清除所有实例
     */
    cleanInst:function(){
    	this.clearData();
    	var body=this.getBodyEl();
    	body.innerHTML="";
    },
    /**
     * 返回HTML
     */
    getHtmls:function(){
    	
    	var tmpfiles=this.getInsts();
    	var write=!this.readOnly;
    	
    	var data={"list":tmpfiles,ctxPath:__rootPath,write:write};
    	
    	var html=baiduTemplate('relatedInstanceScript',data);
    	return html;
    },
    /**
     * 设置数据。
     */
    setValue:function(val){
    	if(!val) val="[]";
    	if((typeof val)=="string"){
    		this.setData(mini.decode(val));
    	}
    	else{
    		this.setData(val);
    	}
    	this.displayInst();
    },
    setSingle: function (value) {
    	this.single=value;
    },
    getAttrs: function (el) {
        var attrs = UserControl.MiniRelatedSolution.superclass.getAttrs.call(this, el);
        mini._ParseString(el, attrs,["chooseitem","solution"]);
        return attrs;
    }
});

mini.regClass(UserControl.MiniRelatedSolution, "relatedSolutionControl");


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
		callBack:null,
		currentControl:null
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
 * 设置当前的控件。
 */
OfficeControls.setCurrentControl=function(ctl){
	this.currentControl=ctl;
}
/**
 * 获取当前的控件。
 */
OfficeControls.getCurrentControl=function(ctl){
	return this.currentControl;
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
	var browserType=getBrowserType();
	//chrome
	if(browserType==1){
		this.callBack=callBack;
	}
	for(var key in this.controls){
		var control=this.controls[key];
		control.save();
	}
	//ie 的情况。
	if(browserType==0 ){
		//上传完成
		var complete=OfficeControls.success+OfficeControls.fail;
		if(!OfficeControls.fail){
			if(callBack){
				callBack();
			}
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
 * rights:"newDoc,open,save,print,printSetting,saveMark,noSaveMark,clearMark"
 * 
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
    doctype:"docx",
    officeId:"",
    username:"",
    version:true,
    btns:{},
    
    initComponents: function () {
    	
    	var html='<div  class="mini-toolbar office" style="padding:0px;"> \
    				<table style="width:100%;">\
    					<tr>	\
    						<td style="width:100%;"> \
            					<a class="mini-button" name="newDocBtn"  plain="true">新建</a>\
            					<a class="mini-button" name="openBtn"  plain="true">打开</a>\
    							<a class="mini-button" name="saveBtn"  plain="true">保存</a>\
    							<a class="mini-button" name="printBtn"  plain="true">打印</a>\
    							<a class="mini-button" name="printSettingBtn"  plain="true">打印设置</a>\
    							<a class="mini-button" name="fullScreenBtn"  plain="true">全屏</a>\
            					<span class="separator"></span>\
					    		<a class="mini-button" name="saveMarkBtn"  plain="true">留痕</a>\
								<a class="mini-button" name="noSaveMarkBtn"  plain="true">不留痕</a>\
								<a class="mini-button" name="clearMarkBtn"  plain="true">清除痕</a>\
    							<span class="separator"></span>\
    							<a class="mini-button" name="inertTemplateBtn"  plain="true">模板</a>\
    							<a class="mini-button" name="outerRedBtn"  plain="true">套红</a>\
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
        var fullScreenBtn = mini.getByName('fullScreenBtn', this);
        
        var inertTemplateBtn = mini.getByName('inertTemplateBtn', this);
        var outerRedBtn = mini.getByName('outerRedBtn', this);
        //var signStampBtn = mini.getByName('signStampBtn', this);
        
        
        
   
        this.btns.newDoc=newDocBtn;
        this.btns.open=openBtn;
        this.btns.save=saveBtn;
        this.btns.print=printBtn;
        this.btns.printSetting=printSettingBtn;
        
        this.btns.saveMark=saveMarkBtn;
        this.btns.noSaveMark=noSaveMarkBtn;
        this.btns.clearMark=clearMarkBtn;
        this.btns.fullScreenBtn=fullScreenBtn;
        
        this.btns.inertTemplateBtn=inertTemplateBtn;
        this.btns.outerRedBtn=outerRedBtn;
        //this.btns.signStampBtn=signStampBtn;
        

        var self_=this;
        newDocBtn.on('click', function (e) {
        	self_.officeObj.ShowDialog(0);
        	self_.doctype=self_.getDocExt("type_" +self_.officeObj.DocType);
        	self_.toggleMarkButton();
        });
        
        openBtn.on('click', function (e) {
        	self_.officeObj.ShowDialog(1);
        	self_.doctype=self_.getDocExt("type_" +self_.officeObj.DocType);
        	
        	self_.toggleMarkButton();
        });
        
        saveBtn.on('click', function (e) {
        	self_.officeObj.ShowDialog(2);
        	self_.doctype=self_.getDocExt("type_" +self_.officeObj.DocType);
        	
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
        
        fullScreenBtn.on('click', function (e) {
        	self_.officeObj.FullScreenMode =true;
        });
        //插入模板
        inertTemplateBtn.on('click', function (e) {
        	OfficeControls.setCurrentControl(self_);
        	openOfficeTemplateDialog("normal");
        });
        //套红模板
        outerRedBtn.on('click', function (e) {
        	
        	OfficeControls.setCurrentControl(self_);
        	openOfficeTemplateDialog("red");
        });
        //盖章
        /*
        signStampBtn.on('click', function (e) {
        	
        	OfficeControls.setCurrentControl(self_);
        	openOfficeStampDialog();
        });
        */
        
    },
    getHtml:function(){
    	var browserType=getBrowserType();
    	var html="";
    	//IE
    	if(browserType==0){
    		html='<object id="'+this.getName()+'_OCX" classid="clsid:A64E3073-2016-4baf-A89D-FFE1FAA10EC0" \
    			codebase="'+__rootPath+'/iWebOffice/OfficeControl.cab#Version=5,0,3,0" width="100%" height="100%">\
		    		<param name="Menubar" value="0" />\
		    		<param name="Statusbar" value="0" />\
		    		<param name="Titlebar" value="0" />\
		    		<param name="Toolbars" value="1" />\
		    	</object>';
    	}
    	else if(browserType==1){
    		html='<object id="'+this.getName()+'_OCX" clsid="{A64E3073-2016-4baf-A89D-FFE1FAA10EC0}" \
			 type="application/ntko-plug" codebase="'+__rootPath+'/iWebOffice/OfficeControl.cab#version=5,0,3,0" \
				width="100%" height="100%" ForOnSaveToURL="OnSaveToURL" ForOnDocumentOpened="OnDocumentOpened_'+this.getName()+'" \
				ForOnaddTemplateFromUrl="OnAddTemplateFromUrl" \
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
    	}  ';
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
    	//初始化用户
    	this.initUser();
    	
    	var browserType=getBrowserType();
    	//浏览器为chrome的时候，动态插入脚本。
        if(browserType==1){
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
		this.doctype=json.type;
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
		var visible=(this.doctype=="docx");
		var rights=["saveMark","noSaveMark","clearMark"];
        for(var i=0;i<rights.length;i++){
        	this.btns[rights[i]].setVisible(visible);
        }
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
		if(!this.officeId) return;
		var urlVer=__rootPath +"/sys/core/office/officever/"+this.officeId+".do";
		var self_=this;
		$.get(urlVer,function(data){
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
			self_.setUsername(data.name);
		});
	},
	/**
	 * 加载Office文档。
	 */
	loadFile:function(fileId){
		var url=__rootPath +"/sys/core/office/download/"+fileId+".do";
		this.officeObj.OpenFromURL(url);
	},
	loadIeRedTemplate:function(fileId){
		var url=__rootPath +"/sys/core/office/download/"+fileId+".do";
		try{
			var mark="content";
			var doc=this.officeObj.ActiveDocument;
			var curSel=doc.Application.Selection;
			curSel.WholeStory();
			curSel.Cut();
			this.officeObj.AddTemplateFromURL(url);
			if(!doc.BookMarks.Exists(mark)){
				alert("Word 模板中不存在名称为:" + mark+"的书签!");
				return;
			}
			var bookMarkObj=doc.BookMarks(mark);
			var saveRange=bookMarkObj.Range;
			saveRange.Paste();
			doc.BookMarks.Add(mark,saveRange);
			
			
		}
		catch(err){
			alert("错误:" +err.number +":" + err.description);
		}
		
		
	},
	loadChromeRedTemplate:function(fileId){
		var url=__rootPath +"/sys/core/office/download/"+fileId+".do";
		try{
			var mark="content";
			var doc=this.officeObj.ActiveDocument;
			var curSel=doc.Application.Selection;
			curSel.WholeStory();
			curSel.Cut();
			this.officeObj.AddTemplateFromURL(url);
		}
		catch(err){
			alert("错误:" +err.number +":" + err.description);
		}
		
		
	},
	loadChromeRedTemplateComplete:function(doc){
		try{
			var mark="content";
			var doc=this.officeObj.ActiveDocument;
			if(!doc.BookMarks.Exists(mark)){
				alert("Word 模板中不存在名称为:" + mark+"的书签!");
				return;
			}
			var bookMarkObj=doc.BookMarks.item(mark);
			var saveRange=bookMarkObj.Range;
			saveRange.Paste();
		}
		catch(err){
			console.info(err);
		}
	},
	loadRedTemplate:function(fileId){
		
		var browserType=getBrowserType();
    	//浏览器为chrome的时候，动态插入脚本。
        if(browserType==0){
        	//插入脚本
        	this.loadIeRedTemplate(fileId);
        }
        else{
        	this.loadChromeRedTemplate(fileId);
        }
		
	},
	handSign:function(){
		try{
			this.officeObj.DoHandSign2(
					this.username,//手写签名用户名称
					"ntko",//signkey,DoCheckSign(检查印章函数)需要的验证密钥。
					0,//left
					0,//top
					0,//relative,设定签名位置的参照对象.0：表示按照屏幕位置插入，此时，Left,Top属性不起作用。1：光标位置；2：页边距；3：页面距离 4：默认设置栏，段落（为兼容以前版本默认方式）
					100);
		}catch(err){
			alert("insertHandSign:" +err.name + ": " + err.message);
		}
	},
	signStamp:function(fileId){
		var url=__rootPath +"/sys/core/file/download/"+fileId+".do";
		try{
		
			this.officeObj.AddSecSignFromURL(this.username,//签章的用户名
					url,//印章所在服务器相对url
					0,//left
					0,//top
					1,//relative
					2,  //print mode 2
					false,//是是否使用证书，true或者false，
					false //是否锁定印章
					);
			
			console.info(this.username);
			console.info("signcomplete");
		}catch(err){
			console.info(err);
			console.info("signStamp:" +err.name + ": " + err.message);
		}
	},
	//加载文档。
	initDoc:function(){
		var val=this.value;
		//指定了文件。
		if(val){
			var tmp=this.getDocType(this.doctype)
			var url=__rootPath +"/sys/core/office/office/"+this.officeId+".do";
			try{
				this.officeObj.OpenFromURL(url,null,tmp);
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
		var params="type=" +this.doctype +"&name=" + this.name +"&ver=" + this.version;
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
				var rtn={id:this.officeId,type:this.doctype};
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
	 * 设置权限
	 */
	setRights:function(val){
		if(!val) return ;
		if(val=="none"){
			this.set({showToolbar:false}); 
			return;
		}
		for(var key in this.btns){
			console.info(key);
			var btn=this.btns[key];
			var visiable=val.indexOf(key)!=-1;
			btn.setVisible(visiable);
		}
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
    	mini._ParseString(el, attrs,["value","name","username","rights"]);
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

function OnAddTemplateFromUrl(str,doc){
	var ctl=OfficeControls.getCurrentControl();
	ctl.loadChromeRedTemplateComplete(doc);
}


/**
 * 加载模板。
 * @param fileId
 * @returns
 */
function loadOfficeTemplate(fileId,type){
	var ctl=OfficeControls.getCurrentControl();
	if(type=="normal"){
		ctl.loadFile(fileId);
	}
	else{
		ctl.loadRedTemplate(fileId);
	}
}

function openOfficeTemplateDialog(type){
	var url=__rootPath+'/sys/core/sysOfficeTemplate/dialog.do';
	url+="?Q_TYPE__S_EQ="+type;
	var top = (screen.availHeight - 300)/2;  
	var left= (screen.availWidth -500)/2;  
	var newWindow=window.open(url,"sysOfficeTemplate","height=300,width=500,top="+top+",left="+left);
	newWindow.focus();
	return newWindow;
}

function openOfficeStampDialog(){
	var url=__rootPath+'/sys/core/sysStamp/dialog.do';
	var top = (screen.availHeight - 300)/2;  
	var left= (screen.availWidth -500)/2;  
	var newWindow=window.open(url,"sysStamp","height=300,width=500,top="+top+",left="+left);
	newWindow.focus();
	return newWindow;
}


function signStamp(fileId){
	var ctl=OfficeControls.getCurrentControl();
	ctl.signStamp(fileId)
}


