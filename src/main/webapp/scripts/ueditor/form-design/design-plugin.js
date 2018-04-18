//基础目录
UE.FormDesignBaseUrl = 'form-design';
//配置扩展的控件
var extPlugins=[{
            	 name:'mini-textbox',
            	 text:'文本框',
            	 tag:'input',
            	 popHeight:420,
            	 popWidth:800
             },
             {
            	 name:'mini-textarea',
            	 text:'多行文本框',
            	 tag:'textarea',
            	 popHeight:320,
            	 popWidth:800
             },
             {
            	 name:'mini-checkbox',
            	 text:'复选框',
            	 tag:'input',
            	 popHeight:320,
            	 popWidth:800
             },
             {
            	 name:'mini-radiobuttonlist',
            	 text:'单选按钮列表',
            	 tag:'input',
            	 popHeight:420,
            	 popWidth:850
             },
             {
            	 name:'mini-checkboxlist',
            	 text:'复选按钮列表',
            	 tag:'input',
            	 popHeight:420,
            	 popWidth:850
             },
             {
            	 name:'mini-combobox',
            	 text:'下拉列表',
            	 tag:'input',
            	 popHeight:420,
            	 popWidth:850
             },
             {
            	 name:'mini-datepicker',
            	 text:'日期选择框',
            	 tag:'input',
            	 popHeight:320,
            	 popWidth:800
             },
             {
            	 name:'mini-time',
            	 text:'时间输入框',
            	 tag:'input',
            	 popHeight:340,
            	 popWidth:800
             },
             {
            	 name:'mini-month',
            	 text:'月份输入框',
            	 tag:'input',
            	 popHeight:340,
            	 popWidth:800
             },
             {
            	 name:'mini-spinner',
            	 text:'数字输入框',
            	 tag:'input',
            	 popHeight:360,
            	 popWidth:800
             },
             {
            	 name:'mini-ueditor',
            	 text:'富文本',
            	 tag:'textarea',
            	 popHeight:360,
            	 popWidth:800
             },
             {
            	 name:'mini-user',
            	 text:'用户选择框',
            	 tag:'input',
            	 popHeight:260,
            	 popWidth:800
             },
             {
            	 name:'mini-group',
            	 text:'用户组选择框',
            	 tag:'input',
            	 popHeight:360,
            	 popWidth:800
             },
             {
            	 name:'upload-panel',
            	 text:'附件上传',
            	 tag:'input',
            	 popHeight:260,
            	 popWidth:800
             },{
            	 name:'rx-grid',
            	 text:'明细表格',
            	 tag:'div',
            	 popHeight:500,
            	 popWidth:1024
             },{
            	 name:'mini-hidden',
            	 text:'隐藏域',
            	 tag:'input',
            	 popHeight:250,
            	 popWidth:800 
             },{
            	 name:'mini-buttonedit',
            	 text:'按钮输入框',
            	 tag:'input',
            	 popHeight:480,
              	 popWidth:800
             },{
            	 name:'mini-dep',
            	 text:'部门',
            	 tag:'input',
            	 popHeight:250,
            	 popWidth:800 
               },
               {
              	 name:'mini-treeselect',
              	 text:'下拉树选择控件',
              	 tag:'input',
              	 popHeight:420,
              	 popWidth:1000
                 },{
                  	 name:'mini-button',
                  	 text:'自定义按钮',
                  	 tag:'a',
                  	 popHeight:480,
                  	 popWidth:800
                },{
                  	 name:'mini-customtable',
                  	 text:'自定义表单',
                  	 tag:'input',
                  	 popHeight:480,
                  	 popWidth:800
                },
                {
                 	 name:'mini-fieldset',
                 	 text:'边框',
                 	 tag:'input',
                 	 popHeight:480,
                 	 popWidth:800
               },
               {
               	 name:'mini-relatedsolution',
               	 text:'关联流程',
               	 tag:'input',
               	 popHeight:480,
               	 popWidth:800
             },
                {
                	 name:'mini-textboxlist',
                 	 text:'文本盒子列表',
                 	 tag:'input',
                 	 popHeight:480,
                 	 popWidth:800
                },
               {
              	 	name:'mini-checkhilist',
	               	 text:'节点审批历史列表',
	               	 tag:'input',
	               	 popHeight:280,
	               	 popWidth:550
              },{
              	 name:'mini-nodeopinion',
               	 text:'意见控件',
               	 tag:'textarea',
               	 popHeight:200,
               	 popWidth:600
              },{
               	 	name:'mini-img',
                	text:'上传图片控件',
                	tag:'img',
                	popHeight:380,
                	popWidth:650
               },
               {
              	 	name:'mini-div',
              	 	text:'DIV容器',
              	 	tag:'div',
              	 	popHeight:380,
              	 	popWidth:650
              },
              {
            	 	name:'mini-condition-div',
            	 	text:'条件DIV容器',
            	 	tag:'div',
            	 	popHeight:380,
            	 	popWidth:780
              },
              {
            	 	name:'mini-office',
            	 	text:'office控件',
            	 	tag:'div',
            	 	popHeight:380,
            	 	popWidth:780
              }
             ];

//注册所有控件
for(var i=0;i<extPlugins.length;i++){
	registerPlugin(i);	
}


//注册插件
function registerPlugin(index){
	UE.plugins[extPlugins[index].name] = function () {
		
		var pluginName=extPlugins[index].name;
		var pluginText=extPlugins[index].text;
		var pluginTag=extPlugins[index].tag;
		var popWidth=extPlugins[index].popWidth;
		var popHeight=extPlugins[index].popHeight;
	
		var me = this,thePlugins = pluginName;
		me.commands[thePlugins] = {
			//两参数可不配置,ModelId为外部配置的业务实体的Id
			execCommand:function (pluginName) {
				var range = this.selection.getRange();
				var td = UE.dom.domUtils.findParentByTagName( range.startContainer, 'td', true );
				var titleName=$(td).prev().html();
				if(typeof(titleName)=='undefined') titleName='';

				titleName = titleName.replace(/<\/?[^>]*>/g,''); //去除HTML tag
				titleName = titleName.replace(/[ | ]*\n/g,'\n'); //去除行尾空白
				titleName = titleName.replace(/ /ig,'');//去掉多余空行 
				
				var url=this.options.UEDITOR_HOME_URL + UE.FormDesignBaseUrl+'/config/'+pluginName+'.jsp?titleName='+titleName;
				url=encodeURI(url);
				
				var dialog = new UE.ui.Dialog({
					iframeUrl:url,
					name:thePlugins,
					editor:this,
					title: pluginText+'属性设置',
					cssRules:"width:"+popWidth+"px;height:"+popHeight+"px;",
					buttons:[
					{
						className:'edui-okbutton',
						label:'确定',
						onclick:function () {
							dialog.close(true);
						}
					},
					{
						className:'edui-cancelbutton',
						label:'取消',
						onclick:function () {
							dialog.close(false);
						}
					}]
				});
				dialog.render();
				dialog.open();
			}
		};
		
		var popup = new baidu.editor.ui.Popup( {
			editor:this,
			content: '',
			className: 'edui-bubble',
			_edittext: function () {
				  baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
				  me.execCommand(thePlugins);
				  this.hide();
			},
			_formula: function () {
				  me.execCommand("editformula",popup.anchorEl);
				  this.hide();
			},
			_delete:function(){
				if( window.confirm('确认删除该控件吗？') ) {
					baidu.editor.dom.domUtils.remove(this.anchorEl,false);
				}
				this.hide();
			}
		});
		
		popup.render();
		me.addListener( 'mouseover', function( t, evt ) {
			evt = evt || window.event;
			var el = evt.target || evt.srcElement;
			if(!el || !el.getAttribute) return;
	        var mPlugins = el.getAttribute('plugins');
	        if(mPlugins==null) return;
	        
			if ( el.tagName.toLowerCase()==pluginTag && mPlugins==thePlugins) {
				
				var dataType=el.getAttribute("datatype");
				var htmlAry=[];
				htmlAry.push('<nobr>'+pluginText+':');
				htmlAry.push('<span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;');
				if(dataType=="number"){
					htmlAry.push('<span onclick=$$._formula() class="edui-clickable">公式</span>&nbsp;');
				}
				htmlAry.push('<span onclick=$$._delete() class="edui-clickable">删除</span>');
				htmlAry.push('</nobr>');
				
				var html = popup.formatHtml(htmlAry.join(""));
				if ( html ) {
					popup.getDom( 'content' ).innerHTML = html;
					popup.anchorEl = el;
					popup.showAnchor( popup.anchorEl );
					
					setTimeout(function(){
						popup.hide();
					},3000);
				}
				
			}
		});
		
	};
}



UE.plugins["formulaPlugin"] = function () {
    var me = this;
    //创建一个改变图片边框的命令
    me.commands["editformula"] = {
        execCommand:function (pluginName,el) {
        	
        	 baidu.editor.plugins["formulaPlugin"].editdom = el;
        	var dialog = new UE.ui.Dialog({
				iframeUrl:this.options.UEDITOR_HOME_URL + UE.FormDesignBaseUrl+'/config/mini-formula.jsp',
				name:'公式设置',
				editor:me,
				selectEl:el,
				title: '公式设置',
				cssRules:"width:600px;height:400px;",
				buttons:[
				{
					className:'edui-okbutton',
					label:'确定',
					onclick:function () {
						dialog.close(true);
					}
				},
				{
					className:'edui-cancelbutton',
					label:'取消',
					onclick:function () {
						dialog.close(false);
					}
				}]
			});
			dialog.render();
			dialog.open();
        	
        }
    };
};


