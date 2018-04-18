<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>下拉树选择控件-mini-treeselect</title>
<%@include file="/commons/mini.jsp"%>
</head>
<body>
	<div class="form-outer">
			<form id="miniForm">
				<input class="mini-hidden" name="datafield" value="data" />
				<table class="table-detail column_2_m" cellspacing="0" cellpadding="1">
					<tbody>
					<tr>
						<th>字段备注<span class="star">*</span></th>
						<td><input id="label" class="mini-textbox" name="label" required="true" vtype="maxLength:100,chinese" style="width: 90%" emptytext="请输入字段备注" onblur="getPinyin" /></td>
						<th>字段标识<span class="star">*</span></th>
						<td>
							<input id="name" name="name" class="mini-textbox" required="true" onvalidation="onEnglishAndNumberValidation"/>
						</td>
					</tr>
					<tr>
						<th >长　度</th>
						<td colspan="3">
							<input id="length" required="true" name="length" class="mini-textbox"  value="50" vtype="max:4000"  style="width:60px" />
						</td>
					</tr>
					<tr>
						<th >值来源</th>
						<td colspan="3" >
							<div id="from" name="from" class="mini-radiobuttonlist" onvaluechanged="fromChanged" required="true" data="[{id:'url',text:'URL'},{id:'custom',text:'自定义SQL'}]" value="url"></div>
						</td>
					</tr>
					<tr id="url_row" style="">
						<th >JSON URL</th>
						<td><input id="url" class="mini-textbox" name="url" required="true" /></td>
						<td colspan="2">文本字段:<input id="url_textfield" class="mini-textbox" name="url_textfield" style="width: 80px" required="true">&nbsp;-&nbsp;数值字段:<input id="url_valuefield" class="mini-textbox" name="url_valuefield" required="true" style="width: 80px">&nbsp;-&nbsp;父节点字段:<input id="url_parentfield"  class="mini-textbox" name="url_parentfield" required="true" style="width: 80px">
						</td>
					</tr>
					<tr id="selfSQL_row" style="display: none">
						<th >自定SQL设置</th>
						<td colspan="3" style="padding: 5px;" ><input id="sql" name="sql" value="${sysDbSql.key}"  text="${sysDbSql.sql}"  style="width: 250px;" class="mini-buttonedit" onbuttonclick="onButtonEdit_sql" />&nbsp;&nbsp;&nbsp;&nbsp;文本字段:<input id="sql_textfield" name="sql_textfield" class="mini-combobox" style="width: 100px;"  data=""  valueField="fieldName"  textField="comment"  required="true" allowInput="false" enabled="false" /> &nbsp;-&nbsp;数值字段:<input id="sql_valuefield" name="sql_valuefield" class="mini-combobox" style="width: 100px;" valueField="fieldName"  textField="comment"  value="" required="true"
							allowInput="false" enabled="false" />&nbsp;-&nbsp;父节点字段:<input id="sql_parentfield" name="sql_parentfield" class="mini-combobox" style="width: 100px;" valueField="fieldName"  textField="comment"  value="" required="true" allowInput="false" enabled="false" /></td>
					</tr>
					<tr>
						<th>必　填<span class="star">*</span></th>
						<td><input class="mini-checkbox" name="required" id="required" required="true" />是</td>
						<th>级联选择</th>
						<td><input class="mini-checkbox" name="checkrecursive" id="checkrecursive" />是</td>
					</tr>
					<tr>
						<th>可以选择父节点</th>
						<td><input name="showfoldercheckbox" class="mini-checkbox" id="showfoldercheckbox" />是</td>
						<th>自动选择父节点</th>
						<td><input name="autocheckparent" class="mini-checkbox" id="autocheckparent" />是</td>
					</tr>
					<tr>
						<th>展开树</th>
						<td><input name="expandonload" class="mini-checkbox" id="expandonload" onvaluechanged="expandChange" />是</td>
						<th id="expand_settings_label" style="display: none;word-break: break-all;">展开设置</th>
						<td id="expand_settings_value" style="display: none;">
							<div id="expandChoose" name="expandChoose" class="mini-radiobuttonlist" onvaluechanged="setExpand" required="true" data="[{id:'true',text:'展开所有'},{id:'false',text:'自定义'}]" value="true"></div> <input id="expandSetting" class="mini-textbox" name="expandSetting" required="true" style="width: 30%" visible="false" />
						(注：0展开第一级节点，以此类推。)
						</td>
					</tr>
					<tr>
						<th>多　选</th>
						<td><input name="multiselect" class="mini-checkbox" id="multiselect" />是</td>
						<th>默认值</th>
						<td><input class="mini-textbox" name="value" style="width: 90%" /></td>
					</tr>
 					<tr>
						<th>
							下拉树选择框宽度
						</th>
						<td colspan="3">
							<input id="popupwidth" name="popupwidth" class="mini-spinner" style="width:80px" value="200" minValue="0" maxValue="1200"/>
							
							<input id="pwunit" name="pwunit" class="mini-combobox" style="width:50px" onvaluechanged="changeMinMaxWidth"
							data="[{'id':'px','text':'px'},{'id':'%','text':'%'}]" textField="text" valueField="id"
						    value="px"  required="true" allowInput="false" />

							&nbsp;&nbsp;高:<input id="popupheight" name="popupheight" class="mini-spinner" style="width:80px" value="300" minValue="0" maxValue="1200"/>
							<input id="phunit" name="phunit" class="mini-combobox" style="width:50px" onvaluechanged="changeMinMaxHeight"
							data="[{'id':'px','text':'px'},{'id':'%','text':'%'}]" textField="text" valueField="id"
						    value="px"  required="true" allowInput="false" />
						    
						</td>
					</tr>
					<tr>
						<th>控件长</th>
						<td colspan="3"><input id="mwidth" name="mwidth" class="mini-spinner" style="width: 80px" value="0" minValue="0" maxValue="1200" /> <input id="wunit" name="wunit" class="mini-combobox" style="width: 50px" onvaluechanged="changeMinMaxWidth" data="[{'id':'px','text':'px'},{'id':'%','text':'%'}]" textField="text" valueField="id" value="px" required="true" allowInput="false" /> &nbsp;&nbsp;高:<input id="mheight" name="mheight" class="mini-spinner" style="width: 80px" value="0"
							minValue="0" maxValue="1200" /> <input id="hunit" name="hunit" class="mini-combobox" style="width: 50px" onvaluechanged="changeMinMaxHeight" data="[{'id':'px','text':'px'},{'id':'%','text':'%'}]" textField="text" valueField="id" value="px" required="true" allowInput="false" /></td>
					</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	
	/*点击选择自定义SQL对话框时间处理*/
	function onButtonEdit_sql(e) {
		var btnEdit = this;
		
		var callBack=function (data){
			btnEdit.setValue(data.key);   //设置自定义SQL的Key
			btnEdit.setText(data.name);
			_SubmitJson({                             //根据SQK的KEY获取SQL的列名和列头
				url:"${ctxPath}/sys/db/sysSqlCustomQuery/listColumnByKeyForJson.do",
				data:{
					key:data.key
				},
				showMsg:false,
				method:'POST',
				success:function(result){
					var data=result.data;
					var text=mini.get("sql_textfield");
					var value=mini.get("sql_valuefield");
					var parent=mini.get("sql_parentfield");
					text.setEnabled(true);   //设置下拉控件为可用状态
					value.setEnabled(true);
					parent.setEnabled(true);
					text.setData();          //每次选择不同SQL，清空下拉框的数据
					value.setData();
					parent.setData();
					text.setData(data.resultField);      //设置下拉框的数据
					value.setData(data.resultField);
					parent.setData(data.resultField);
				}
			});
		}
		openCustomQueryDialog(callBack);
		
		
		
	}
	
		function createElement(type, name){     
		    var element = null;     
		    try {        
		        element = document.createElement('<'+type+' name="'+name+'" type="checkbox">');     
		    } catch (e) {}   
		    if(element==null) {     
		        element = document.createElement(type);     
		        element.name = name;     
		    } 
		    return element;     
		}
		
		mini.parse();
		var form=new mini.Form('miniForm');
		var grid=mini.get('props');
		//编辑的控件的值
		var oNode = null,
		thePlugins = 'mini-treeselect';
		var pluginLabel="${param['titleName']}";
		/*数据来源点击事件处理*/
		function fromChanged(e){
			var val=mini.get('from').getValue();
			if(val=='url'){  //URL来源
				$("#url_row").css('display','');
				$("#selfSQL_row").css('display','none');
			}else if(val=='custom'){ //自定义SQL来源
				$("#url_row").css('display','none');
				$("#selfSQL_row").css('display','');
			}
		}
		/*是否展开树点击事件处理*/
		function expandChange(e){
			var val=mini.get('expandonload').checked;
			if(val==true){//展开树
				$("#expand_settings_label").css('display','');
				$("#expand_settings_value").css('display','');
			}else {//不展开树
				$("#expand_settings_label").css('display','none');
				$("#expand_settings_value").css('display','none');
			}
		}
		
		/*自定义展开树输入框显示控制*/
		function setExpand(e){
			var choose=mini.get('expandChoose').getValue();
			var val=mini.get('expandSetting');
			if(choose=='true'){//全部展开，隐藏输入框
				val.setVisible(false);
			}else if(choose=='false'){//自定义展开树的层数，显示输入框
				val.setVisible(true);
			}
			
		}
		
		window.onload = function() {
			//若控件已经存在，则设置回调其值
		    if( UE.plugins[thePlugins].editdom ){
		        //
		    	oNode = UE.plugins[thePlugins].editdom;
		        //获得字段名称
		        var formData={};
		        var attrs=oNode.attributes;
		        
		        var expandAttr="";
		        for(var i=0;i<attrs.length;i++){
		        	if(attrs[i].name=='expandonload'){  //如果是设置是否展开树
		        		expandAttr=attrs[i].value;
		        		if(expandAttr!="false"){  //如果不为不展开的时候
		        			formData[attrs[i].name]="true";   //设置TRUE
		        			continue;
		        		}
		        	}
		        	if(attrs[i].name=='popupwidth'){    //如果是下拉框的宽度属性
		        		if(attrs[i].value.indexOf("%")>-1){   //如果以百分比为单位
		        			formData[attrs[i].name]=attrs[i].value.substring(0,attrs[i].value.indexOf("%") );  //去除数值后面的"%"号
		        			continue;
		        		}
		        	}
		        	if(attrs[i].name=='popupheight'){//如果是下拉框的高度属性
		        		if(attrs[i].value.indexOf("%")>-1){ //如果以百分比为单位
		        			formData[attrs[i].name]=attrs[i].value.substring(0,attrs[i].value.indexOf("%") ); //去除数值后面的"%"号
		        			continue;
		        		}
		        	}
		        	formData[attrs[i].name]=attrs[i].value;
		        }
	        	if(formData['from']=='custom'){     //自定义SQL的KEY属性
	        		var btnEdit=mini.get("sql");
					btnEdit.setValue(formData['sql']);  //设置KEY属性
					_SubmitJson({     //根据sql的key 获取列名
						url:"${ctxPath}/sys/db/sysSqlCustomQuery/listColumnByKeyForJson.do",
						data:{
							key:formData['sql']
						},
						showMsg:false,
						method:'POST',
						success:function(result){
							btnEdit.setText(result.data.name);
							var data=result.data;
							var text=mini.get("sql_textfield");
							var value=mini.get("sql_valuefield");
							var parent=mini.get("sql_parentfield");
							text.setEnabled(true);          //设置下拉框为可用状态
							value.setEnabled(true);
							parent.setEnabled(true);
							text.setData(data.resultField);    //设置下拉框数据
							value.setData(data.resultField);
							parent.setData(data.resultField);
						}
					});
	        	} 
	        	
		        form.setData(formData);
		        fromChanged();//触发数据来源事件处理

        		if(expandAttr=="true"){  //如果设置为展开树
    				$("#expand_settings_label").css('display','');    //设置展开树属性的可见性
    				$("#expand_settings_value").css('display','');
    				var expandChoose=mini.get('expandChoose');   
    				expandChoose.setValue(expandAttr);   //设置展开树为true
        		}else if(expandAttr=="false"){         //不展开树
    				$("#expand_settings_label").css('display','none');   //设置展开树设置为不可见
    				$("#expand_settings_value").css('display','none');
        		}else{    //自定义展开树
    				$("#expand_settings_label").css('display','');           //设置展开树属性的可见性
    				$("#expand_settings_value").css('display','');
    				var expandChoose=mini.get('expandChoose');
    				expandChoose.setValue("false");            //设置为自定义展开的选项
    				var val=mini.get('expandSetting');
    				val.setVisible(true);               //设置自定义展开树输入框的可见性为TRUE
    				var expandSetting=mini.get("expandSetting");
    				expandSetting.setValue(expandAttr);    //设置自定义展开树的值
        		}
        		
		    }
		    else{
		    	var data=_GetFormJson("miniForm");
		    	var array=getFormData(data);
		    	initPluginSetting(array);
		    }
		};
		//取消按钮
		dialog.oncancel = function () {
		    if( UE.plugins[thePlugins].editdom ) {
		        delete UE.plugins[thePlugins].editdom;
		    }
		};
		//确认
		dialog.onok = function (){
			form.validate();
	        if (form.isValid() == false) {
	            return false;
	        }
	        
	        var formData=form.getData();
	        var isCreate=false;
		    //控件尚未存在，则创建新的控件，否则进行更新
		    if( !oNode ) {
		        try {
		            oNode = createElement('input',name);
		            oNode.setAttribute('class','mini-treeselect rxc');
		            //需要设置该属性，否则没有办法其编辑及删除的弹出菜单
		            oNode.setAttribute('plugins',thePlugins);
		        } catch (e) {
		            try {
		                editor.execCommand('error');
		            } catch ( e ) {
		                alert('控件异常，请联系技术支持');
		            }
		            return false;
		        }
		        isCreate=true;
		    }
		    
		    /*设置控件宽度和高度*/
		    var style="";
            if(formData.mwidth!=0){
            	style+="width:"+formData.mwidth+formData.wunit;
            }
            if(formData.mheight!=0){
            	if(style!=""){
            		style+=";";
            	}
            	style+="height:"+formData.mheight+formData.hunit;
            }
            oNode.setAttribute('style',style);
		    
		  	var isExpand=true;
		  	var from=formData['from'];
		  	if(from=='custom'){
		  		from="sql";
		  	}
		    for(var key in formData){
 		    	if(key=='expandonload'){
		    		var expand=formData[key];  //设置展开树的值
		    		if(expand=='false'){    //如果为不展开树
		    			isExpand=false;
		    		}
		    	}
            	oNode.setAttribute(key,formData[key]);
            	
            	if(key=="name"){
            		oNode.setAttribute("textName",formData[key] +"_name");
            	}
            }

		    
		    var from=mini.get('from').getValue();
            //数据来源判断
			 if(from=='url'){//来自url
  		    	oNode.setAttribute('sql_textfield','');        //设置自定义SQL来源的相关属性为空
  		    	oNode.setAttribute('sql_valuefield','');
  		    	oNode.setAttribute('sql_parentfield','');
  	    		oNode.setAttribute('sql','');
  	    		oNode.setAttribute('textfield',oNode.getAttribute('url_textfield'));
  		    	oNode.setAttribute('valuefield',oNode.getAttribute('url_valuefield'));
  		    	oNode.setAttribute('parentfield',oNode.getAttribute('url_parentfield'));
  	    	}else if(from=='custom'){   //来自自定义SQL
  	    		oNode.setAttribute('url','');             //设置url来源的相关属性为空
  		    	oNode.setAttribute('url_textfield','');
  		    	oNode.setAttribute('url_valuefield','');
  		    	oNode.setAttribute('url_parentfield','');
  	    		oNode.setAttribute('textfield',oNode.getAttribute('sql_textfield'));
  		    	oNode.setAttribute('valuefield',oNode.getAttribute('sql_valuefield'));
  		    	oNode.setAttribute('parentfield',oNode.getAttribute('sql_parentfield'));
  	    	}
		    
    		if(isExpand){ //如果展开树
    			var expandChoose=oNode.getAttribute('expandChoose');
    			if(expandChoose=='true'){    //如果为展开全部
    				oNode.setAttribute('expandonload',expandChoose);
    			}else if(expandChoose=='false'){  //自定义展开树
    				oNode.setAttribute('expandonload',oNode.getAttribute('expandSetting'));
    			}
    		}
    		oNode.removeAttribute('expandChoose');    //去除两个无用的属性
    		oNode.removeAttribute('expandSetting');
    		if(formData.popupwidth!=0){      //如果下拉框的宽度不为0  
    			if(formData.pwunit=="%"){ //如果单位为百分比
    				oNode.setAttribute('popupwidth',oNode.getAttribute('popupwidth')+'%');  //设置下拉宽度为百分比做单位
    			}
    		}
    		
    		if(formData.popupheight!=0){   //如果下拉框的高度不为0  
    			if(formData.phunit=="%"){   //如果单位为百分比
    				oNode.setAttribute('popupheight',oNode.getAttribute('popupheight')+'%');//设置下拉高度为百分比做单位
    			}
    		}
            if(isCreate){
	            editor.execCommand('insertHtml',oNode.outerHTML);
            }else{
            	delete UE.plugins[thePlugins].editdom;
            }
            	
		};
		
		
	</script>
</body>
</html>
