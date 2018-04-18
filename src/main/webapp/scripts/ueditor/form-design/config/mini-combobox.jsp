<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html >
<html>
<head>
<title>下拉列表</title>
<%@include file="/commons/mini.jsp"%>
</head>
<body>
	<div class="form-outer">
			<form id="miniForm">
				<input class="mini-hidden" name="dataField" value="data" />
				<table class="table-detail column_2_m" cellpadding="1" cellspacing="0">
					<tr>
						<th >字段备注<span class="star">*</span></th>
						<td >
							<input id="label" class="mini-textbox" name="label" required="true" vtype="maxLength:100"  style="width:90%" emptytext="请输入字段备注" onblur="getPinyin" />
						</td>
						<th >字段标识<span class="star">*</span></th>
						<td >
							<input id="name" name="name" class="mini-textbox" required="true" onvalidation="onEnglishAndNumberValidation"/>
						</td>
					</tr>
					<tr>
						<th>字符长度<span class="star">*</span></th>
						<td colspan="3">
							<input id="length" class="mini-textbox" value="50" name="length" required="true" vtype="maxLength:20"  style="width:60px" emptytext="输入长度"   />
						</td>
					</tr>
					<tr>
						<th >值来源</th>
						<td colspan="3" >
							<div id="from" name="from" class="mini-radiobuttonlist" onvaluechanged="fromChanged" required="true"
							data="[{id:'self',text:'自定义'},{id:'url',text:'URL'},{id:'dic',text:'数据字典'},{id:'sql',text:'自定义SQL'}]" value="self"></div>
						</td>
					</tr>
					<tr id="url_row" style="display:none">
						<th >
							JSON URL
						</th>
						<td  colspan="2">
							<input id="url" class="mini-textbox" name="url" required="true" style="width:99%"/>
						</td>
						<td >
						文本字段<input id="url_textfield" class="mini-textbox" name="url_textfield" style="width:80px" required="true">&nbsp;-&nbsp;数值字段:<input id="url_valuefield" class="mini-textbox" name="url_valuefield" required="true" style="width:80px">
						</td>
					</tr>
					<tr id="dic_row" style="display:none">
						<th >
							数据字典项
						</th>
						<td  colspan="3">
							<input id="dicKey" name="dickey" class="mini-treeselect" url="${ctxPath}/sys/core/sysTree/listDicTree.do"  style="width:80%"
						        textField="name" valueField="pkId" parentField="parentId" 
						        showFolderCheckBox="false" onnodeclick="setTreeKey(e)"/>
						</td>
					</tr>
					<tr id="selfSQL_row" style="display: none">
						<th >自定SQL设置</th>
						<td colspan="3" style="padding: 5px;"  >
							父控件:
							<input id="sql_parent" name="sql_parent" class="mini-combobox" style="width: 100px;"  textField="text" valueField="id" value="" showNullItem="true" /> 
							<input id="sql" name="sql" style="width: 100px;"    class="mini-buttonedit" onbuttonclick="onButtonEdit_sql" />
							文本:<input id="sql_textfield" name="sql_textfield" class="mini-combobox"    
							 valueField="fieldName"  textField="comment"  required="true" allowInput="false"   /> 
							 数值:<input id="sql_valuefield" name="sql_valuefield" class="mini-combobox"  
							  valueField="fieldName"  textField="comment"   required="true" allowInput="false"   />
							 输入参数:<input id="sql_params" name="sql_params" class="mini-combobox"  
							  valueField="fieldName"  textField="comment"   required="false" allowInput="false"   />
							  
							</td>
					</tr>										
					<tr id="self_row" style="display:none">
						<th >选　项</th>
						<td colspan="3" style="padding:5px;">
							<div class="mini-toolbar" >
							    <table style="width:100%;">
							        <tr>
								        <td style="width:100%;">
								            <a class="mini-button" iconCls="icon-add" plain="true" onclick="addRow">添加</a>
								            <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeRow">删除</a>
								            <span class="separator"></span>
								            <a class="mini-button" iconCls="icon-up" plain="true" onclick="upRow">向上</a>
								            <a class="mini-button" iconCls="icon-down" plain="true" onclick="downRow">向下</a>
								        </td>
							        </tr>
							    </table>
							</div>
							<div id="props" class="mini-datagrid" style="width:100%;height:150px;"   showPager="false"
								allowCellEdit="true" allowCellSelect="true">
							    <div property="columns">
							        <div type="indexcolumn">序号</div>                
							        <div field="key" width="120" headerAlign="center">标识键
							         <input property="editor" class="mini-textbox" style="width:100%;" minWidth="120" />
							        </div>    
							        <div field="name" width="120" headerAlign="center">名称
							        	<input property="editor" class="mini-textbox" style="width:100%;" minWidth="120" />
							        </div>    
							    </div>
							</div>
						</td>
					</tr>
					<tr>
						<th >必　填<span class="star">*</span></th>
						<td >
							<input class="mini-checkbox" name="required" id="required"/>是
						</td>
						<th >
							默认值
						</th>
						<td >
							<input class="mini-textbox" name="defaultvalue" style="width:90%"/>
						</td>
					</tr>
				
					<tr>
						<th >
							控件长
						</th>
						<td colspan="3" >
							<input id="mwidth" name="mwidth" class="mini-spinner" style="width:80px" value="0" minValue="0" maxValue="1200"/>
							
							<input id="wunit" name="wunit" class="mini-combobox" style="width:50px" onvaluechanged="changeMinMaxWidth"
							data="[{'id':'px','text':'px'},{'id':'%','text':'%'}]" textField="text" valueField="id"
						    value="px"  required="true" allowInput="false" />

							&nbsp;&nbsp;高:<input id="mheight" name="mheight" class="mini-spinner" style="width:80px" value="0" minValue="0" maxValue="1200"/>
							<input id="hunit" name="hunit" class="mini-combobox" style="width:50px" onvaluechanged="changeMinMaxHeight"
							data="[{'id':'px','text':'px'},{'id':'%','text':'%'}]" textField="text" valueField="id"
						    value="px"  required="true" allowInput="false" />
						    
						</td>
					</tr>
				</table>
			</form>
	</div>
	<script type="text/javascript">
		mini.parse();
		var form=new mini.Form('miniForm');
		var grid=mini.get('props');
		//编辑的控件的值
		var oNode = null,
		thePlugins = 'mini-combobox';
		var pluginLabel="${param['titleName']}"; 
		/*点击选择自定义SQL对话框时间处理*/
		function onButtonEdit_sql(e) {
			var btnEdit = this;
			var callBack=function (data){
				btnEdit.setValue(data.key);   //设置自定义SQL的Key
				btnEdit.setText(data.name);
				
				loadSqlFields(data.key);
			}
			openCustomQueryDialog(callBack);
		}
		
		function loadSqlFields(key,callBack){
			_SubmitJson({                             //根据SQK的KEY获取SQL的列名和列头
				url:"${ctxPath}/sys/db/sysSqlCustomQuery/listColumnByKeyForJson.do",
				data:{
					key:key
				},
				showMsg:false,
				method:'POST',
				success:function(result){
					var data=result.data;
					var text=mini.get("sql_textfield");
					var value=mini.get("sql_valuefield");
					var params=mini.get("sql_params");
					
					text.setEnabled(true);   //设置下拉控件为可用状态
					value.setEnabled(true);
					text.setData();          //每次选择不同SQL，清空下拉框的数据
					value.setData();
					text.setData(data.resultField);      //设置下拉框的数据
					value.setData(data.resultField);
					
					var conditionJson=getParams(data);
					params.setData(conditionJson);
					
					callBack(data);
				}
			});
		}
		
		/**
		获取输入参数。
		*/
		function getParams(data){
			var aryJson=[];
			if(!data.whereField) return aryJson;
			var fields=mini.decode(data.whereField);
			for(var i=0;i<fields.length;i++){
				var field=fields[i];
				if(field.valueSource=="param"){
					aryJson.push(field);
				}
			}
			return aryJson;
		}
		
		/*数据来源点击事件处理*/
		function fromChanged(e){
			var val=mini.get('from').getValue();
			if(val=='self'){
				$("#self_row").css('display','');
				$("#url_row").css('display','none');
				$("#dic_row").css('display','none');
				$("#selfSQL_row").css('display','none');
			}else if(val=='url'){
				$("#self_row").css('display','none');
				$("#url_row").css('display','');
				$("#dic_row").css('display','none');
				$("#selfSQL_row").css('display','none');
			}else if(val=='dic'){
				$("#self_row").css('display','none');
				$("#url_row").css('display','none');
				$("#dic_row").css('display','');
				$("#selfSQL_row").css('display','none');
			}else if(val=='sql'){
				$("#self_row").css('display','none');
				$("#url_row").css('display','none');
				$("#dic_row").css('display','none');
				$("#selfSQL_row").css('display','');
			}
		}
		
		//添加行
		function addRow(){
			grid.addRow({});
		}
		
		function removeRow(){
			var selecteds=grid.getSelecteds();
			if(selecteds.length>0 && confirm('确定要删除？')){
				grid.removeRows(selecteds);
			}
		}
		
		function upRow() {
            var row = grid.getSelected();
            if (row) {
                var index = grid.indexOf(row);
                grid.moveRow(row, index - 1);
            }
        }
        function downRow() {
            var row = grid.getSelected();
            if (row) {
                var index = grid.indexOf(row);
                grid.moveRow(row, index + 2);
            }
        }
		
		window.onload = function() {
			var curEl=UE.plugins[thePlugins].editdom ;
			try{
				//获取下拉控件
				var aryCombo=getMetaData(editor);
				var objParent=mini.get("sql_parent");
				objParent.setData(aryCombo);
			}catch(e){
				
			}
			
			//若控件已经存在，则设置回调其值
		    if( curEl ){
		        //
		    	oNode = UE.plugins[thePlugins].editdom;
		        //获得字段名称
		        var formData={};
		        var attrs=oNode.attributes;
		        
		        for(var i=0;i<attrs.length;i++){
		        	if(attrs[i].name=='data'){
		        		grid.setData(mini.decode(attrs[i].value));	
		        	}else{
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
		        }
		      
		        if(formData['from']=='sql'){     //自定义SQL的KEY属性
		        	var btnEdit=mini.get("sql");
					btnEdit.setValue(formData['sql']);  //设置KEY属性
					
					loadSqlFields(formData['sql'],function(data){
						btnEdit.setText(data.name);
						//设置值
						form.setData(formData);
					});
					
	        	} 
		        
		        if(formData['from']=='dic'){
					_SubmitJson({     
						url:"${ctxPath}/sys/core/sysTree/getByCatKeyAndKey.do",
						data:{
							key:formData['dickey']
						},
						showMsg:false,
						method:'POST',
						success:function(result){
							var selectKey=formData['dickey'];
							mini.get("dicKey").setValue(result.data.key);
							mini.get("dicKey").setText(result.data.name);
						}
					});
	        	}
		        
		        form.setData(formData);
		    }
		    else{
		    	var data=_GetFormJson("miniForm");
		    	var array=getFormData(data);
		    	initPluginSetting(array);
		    }
		    fromChanged();//触发数据来源事件处理
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
	     
	        var isCreate=false;
	        
	        var formData=form.getData();
	        
	        //创新新控件
	        if( !oNode ) {
	        	isCreate=true;
		        try {
		            oNode = createElement('input',name);
		            oNode.setAttribute('class','mini-combobox rxc');
		            //需要设置该属性，否则没有办法其编辑及删除的弹出菜单
		            oNode.setAttribute('plugins',thePlugins);
		        }catch(e){
		        	alert('出错了，请联系管理员！');
		        	return;
		        }
	        }
	        //更新控件Attributes
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
            
            for(var key in formData){
            	oNode.setAttribute(key,formData[key]);
            	
            	if(key=="name"){
            		oNode.setAttribute("textName",formData[key] +"_name");
            	}
            }
            var from=mini.get('from').getValue();
	    	//数据来源判断
            if(from=='self'){//来自自定义
  		    	oNode.setAttribute('textfield','name');
  		    	oNode.setAttribute('valuefield','key');  	
  		    	oNode.removeAttribute('dickey');
  		    	oNode.removeAttribute('url');          //设置url来源的相关属性为空
  		    	oNode.removeAttribute('url_textfield');
  		    	oNode.removeAttribute('url_valuefield');
  		    	oNode.removeAttribute('sql_textfield');     //设置自定义SQL来源的相关属性为空
  		    	oNode.removeAttribute('sql_valuefield');
  		    	oNode.removeAttribute('sql');
  		    	var gridData=grid.getData();
  		    	oNode.setAttribute('data',mini.encode(gridData));
  	    	}else if(from=='url'){//来自url
  	    		oNode.removeAttribute('data');
  	    		oNode.removeAttribute('dickey');
  		    	oNode.removeAttribute('sql_textfield');             //设置自定义SQL来源的相关属性为空
  		    	oNode.removeAttribute('sql_valuefield');
  	    		oNode.removeAttribute('sql');
  	    		oNode.setAttribute('textfield',oNode.getAttribute('url_textfield'));
  		    	oNode.setAttribute('valuefield',oNode.getAttribute('url_valuefield'));
  	    	}else if(from=='dic'){//来自数据字典
  	    		oNode.removeAttribute('data');
  	    		oNode.removeAttribute('url');             //设置url来源的相关属性为空
  		    	oNode.removeAttribute('url_textfield');
  		    	oNode.removeAttribute('url_valuefield');
  		    	oNode.removeAttribute('sql_textfield');            //设置自定义SQL来源的相关属性为空
  		    	oNode.removeAttribute('sql_valuefield');
  	    		oNode.removeAttribute('sql');
  	    		oNode.setAttribute('textfield','name');
  		    	oNode.setAttribute('valuefield','key');
  	    	}else if(from=='sql'){//来自自定义SQL
  	    		oNode.removeAttribute('data');
  	    		oNode.removeAttribute('url');         //设置url来源的相关属性为空
  		    	oNode.removeAttribute('url_textfield');
  		    	oNode.removeAttribute('url_valuefield');
  	    		oNode.removeAttribute('dickey');
  	    		oNode.setAttribute('textfield',oNode.getAttribute('sql_textfield'));
  		    	oNode.setAttribute('valuefield',oNode.getAttribute('sql_valuefield'));
  		    	
  		    	var sqlParent=oNode.getAttribute('sql_parent');
  		    	var json={};
  		    	var jsonSql={};
  		    	json.sql=jsonSql;
  		    	if(sqlParent){
  		    		jsonSql.param=oNode.getAttribute('sql_params');
  	  		    	jsonSql.parent=oNode.getAttribute('sql_parent');
  	  		    	jsonSql.sql=oNode.getAttribute('sql');
  	  		    	
  	  		    	oNode.setAttribute('data-options',mini.encode(json));	
  		    	}
  		    	else{
  		    		jsonSql.sql=oNode.getAttribute('sql');
  		    		oNode.setAttribute('data-options',mini.encode(json));	
  		    	}
  		    	
  	    	}
	    	
	    	if(from!='self'){
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
	    	}else{
	    		oNode.removeAttribute('popupwidth');
	    		oNode.removeAttribute('popupheight');
	    	}
	    	 if(isCreate){
	    		 //创建选项
	    		 editor.execCommand('insertHtml',oNode.outerHTML);
		     }else{
		         delete UE.plugins[thePlugins].editdom;
		     }
		};
		
		function setTreeKey(e){
			var node=e.node;
			e.sender.setValue(node.key);
			e.sender.setText(node.name);
		}
		
		
		function getMetaData(editor){
			var el=editor.selection.getStart();
			
			var container=$(editor.getContent());
			var elObj=$(el);
			var grid=elObj.closest(".rx-grid");
			var isMain=grid.length==0;
			var aryJson=[];
			if(isMain){
				var els=$("[plugins='mini-combobox']:not(div.rx-grid [plugins='mini-combobox'])",container);
				getJson(els,aryJson);
			}
			else{
				var subWindow=elObj.closest("div.popup-window-d");
				var els;
				if(subWindow.length>0){
					els=$("[plugins='mini-combobox']",subWindow);
				}
				else{
					els=$("[plugins='mini-combobox']",grid);	
				}
				getJson(els,aryJson);
			}
			return aryJson;
			
		}
		
		function getJson(els,aryJson){
			els.each(function(){
				var obj= $(this);
				var label=obj.attr("label");
				var name=obj.attr("name");
				var fieldObj={id: name, text: label};  
				aryJson.push(fieldObj);
			});
		}
	</script>
</body>
</html>
