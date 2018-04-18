<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html >
<html>
<head>
<title>单选按钮列表</title>
<%@include file="/commons/mini.jsp"%>
</head>
<body>
	<div class="form-outer">
			<form id="miniForm">
				<input class="mini-hidden" name="dataField" value="data" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="1">
					<tr>
						<th>字段备注<span class="star">*</span></th>
						<td  >
							<input id="label" class="mini-textbox" name="label" required="true" vtype="maxLength:100"  style="width:90%" emptytext="请输入字段备注" onblur="getPinyin" />
						</td>
						<th width="15%">字段标识<span class="star">*</span></th>
						<td >
							<input id="name" name="name" class="mini-textbox" required="true" onvalidation="onEnglishAndNumberValidation"/>
						</td>
					</tr>
					<tr>
						<th>字符长度<span class="star">*</span></th>
						<td colspan="3">
							<input id="length" class="mini-textbox" value="20" name="length" required="true" vtype="maxLength:20"  style="width:60px" emptytext="输入长度"   />
						</td>
					</tr>
					<tr>
						<th >值来源</th>
						<td>
							<div id="from" name="from" class="mini-radiobuttonlist" onvaluechanged="fromChanged" required="true"
							data="[{id:'self',text:'自定义'},{id:'url',text:'URL'},{id:'dic',text:'数据字典'},{id:'sql',text:'自定义SQL'}]" value="self"></div>
						</td>
						<th >默认值</th>
						<td >
							<input class="mini-textbox" name="defaultvalue" style="width:80%"/>
						</td>
					</tr>
					<tr id="url_row" style="display:none">
						<th >
							JSON URL
						</th>
						<td  colspan="3">
							<input id="url" class="mini-textbox" name="url" required="true" style="width:50%"/>
						文本字段:<input id="url_textfield" class="mini-textbox" name="url_textfield" style="width:80px" required="true">
						&nbsp;&nbsp;
						数值字段:<input id="url_valuefield" class="mini-textbox" name="url_valuefield" required="true" style="width:80px">
						</td>
					</tr>
					<tr id="dic_row" style="display:none">
						<th >
							数据字典项
						</th>
						<td  colspan="3">
							<input id="dicKey" name="dickey" class="mini-treeselect" url="${ctxPath}/sys/core/sysTree/listDicTree.do"  style="width:50%"
						        textField="name" valueField="pkId" parentField="parentId" showFolderCheckBox="false" onnodeclick="setTreeKey(e)" />
						</td>
					</tr>					
					<tr id="self_row" >
						<th >选　项</th>
						<td colspan="3" style="padding:5px">
							<div class="mini-toolbar">
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
							<div id="props" class="mini-datagrid" showPager="false"
								multiSelect="true"
								allowCellEdit="true" allowCellSelect="true" height="200">
							    <div property="columns" class="columns">
							        <div type="indexcolumn" width="20">序号</div>
							        <div type="checkcolumn" width="20"></div>                
							        <div field="key" headerAlign="center" width="100">标识键
							         <input property="editor" class="mini-textbox" style="width:100%;"  />
							        </div>    
							        <div field="name" headerAlign="center" width="100">名称
							        	<input property="editor" class="mini-textbox" style="width:100%;" />
							        </div>    
							    </div>
							</div>
						</td>
					</tr>
					<tr id="selfSQL_row" style="display: none">
						<th >自定SQL设置</th>
						<td colspan="3" style="padding: 5px;" ><input id="sql" name="sql" value="${sysDbSql.key}"  text="${sysDbSql.sql}"  style="width: 250px;" class="mini-buttonedit" allowInput="false" onbuttonclick="onButtonEdit_sql" />&nbsp;&nbsp;&nbsp;&nbsp;文本字段:<input id="sql_textfield" name="sql_textfield" class="mini-combobox" style="width: 100px;"  data=""  valueField="fieldName"  textField="comment"  required="true" allowInput="false" enabled="false" /> &nbsp;-&nbsp;数值字段:<input id="sql_valuefield" name="sql_valuefield" class="mini-combobox" style="width: 100px;" valueField="fieldName"  textField="comment"  value="" required="true"
							allowInput="false" enabled="false" /></td>
					</tr>
					<tr>
						<th >排版方式</th>
						<td >
							<div name="repeatlayout" class="mini-radiobuttonlist"   
								value="flow" data="[{id:'none',text:'无'},{id:'table',text:'表格'},{id:'flow',text:'流式布局'}]" ></div>
						</td>
						<th >排版方向</th>
						<td >
							<div name="repeatdirection" class="mini-radiobuttonlist" repeatItems="4" repeatLayout="table" repeatDirection="horizontal"
								value="horizontal" data="[{id:'horizontal',text:'横排'},{id:'vertical',text:'纵排'}]" ></div>
						</td>
					</tr>
					<tr>
						<th >每行项数</th>
						<td>
							<input  name="repeatitems" class="mini-spinner"  minValue="1" maxValue="50"  value="5"/>
						</td>
						<th >必　填<span class="star">*</span></th>
						<td >
							<input class="mini-checkbox" name="required" id="required"/>是
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
		thePlugins = 'mini-radiobuttonlist';
		var pluginLabel="${param['titleName']}";
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
						text.setEnabled(true);   //设置下拉控件为可用状态
						value.setEnabled(true);
						text.setData();          //每次选择不同SQL，清空下拉框的数据
						value.setData();
						text.setData(data.resultField);      //设置下拉框的数据
						value.setData(data.resultField);
					}
				});
			}
			openCustomQueryDialog(callBack);
			
		}
		
		
		//添加行
		function addRow(){
			grid.addRow({});
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
			
			//若控件已经存在，则设置回调其值
		    if( UE.plugins[thePlugins].editdom ){
		        //
		    	oNode = UE.plugins[thePlugins].editdom;
		        //获得字段名称
		        var formData={};
		        var attrs=oNode.attributes;
		        
		        for(var i=0;i<attrs.length;i++){
		        	if(attrs[i].name=='data'){
		        		grid.setData(mini.decode(attrs[i].value));	
		        	}else{
		        		formData[attrs[i].name]=attrs[i].value;
		        	}
		        }
		        
		        
		        
	        	if(formData['from']=='sql'){     //自定义SQL的KEY属性
	        		var btnEdit=mini.get("sql");
					btnEdit.setValue(formData['sql']);  //设置KEY属性
					
					
					
					
					_SubmitJson({      //根据sql的Key获取SQL
						url:"${ctxPath}/sys/db/sysSqlCustomQuery/listColumnByKeyForJson.do",
						data:{
							key:formData['sql']
						},
						showMsg:false,
						method:'POST',
						success:function(result){
							btnEdit.setText(result.data.name);  //设置自定义SQL值的显示
							var data=result.data;
							var text=mini.get("sql_textfield");
							var value=mini.get("sql_valuefield");
							text.setEnabled(true);          //设置下拉框为可用状态
							value.setEnabled(true);	
							text.setData(data.resultField);    //设置下拉框数据
							value.setData(data.resultField);
						}
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
		    fromChanged();  //触发数据来源事件处理
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
		            oNode.setAttribute('class','mini-radiobuttonlist rxc');
		            //需要设置该属性，否则没有办法其编辑及删除的弹出菜单
		            oNode.setAttribute('plugins',thePlugins);
		        }catch(e){
		        	alert('error');
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
            
            oNode.setAttribute('defaultvalue',formData.defaultvalue);
            
            //数据来源判断
  	    	if(from=='self'){//来自自定义
  		    	oNode.setAttribute('textfield','name');
  		    	oNode.setAttribute('valuefield','key');  	
  		    	oNode.setAttribute('dickey','');
  		    	oNode.setAttribute('url','');           //设置url来源的相关属性为空
  		    	oNode.setAttribute('url_textfield','');
  		    	oNode.setAttribute('url_valuefield','');
  		    	oNode.setAttribute('sql_textfield','');   //设置自定义SQL来源的相关属性为空
  		    	oNode.setAttribute('sql_valuefield','');
  		    	oNode.setAttribute('sql','');
  		    	var gridData=grid.getData();
  		    	oNode.setAttribute('data',mini.encode(gridData));
  		    	
  	    	}else if(from=='url'){//来自url
  	    		oNode.setAttribute('data','');
  	    		oNode.setAttribute('dickey','');
  		    	oNode.setAttribute('sql_textfield','');        //设置自定义SQL来源的相关属性为空
  		    	oNode.setAttribute('sql_valuefield','');
  	    		oNode.setAttribute('sql','');
  	    		oNode.setAttribute('textfield',oNode.getAttribute('url_textfield'));
  		    	oNode.setAttribute('valuefield',oNode.getAttribute('url_valuefield'));
  	    	}else if(from=='dic'){//来自数据字典
  	    		oNode.setAttribute('data','');
  	    		oNode.setAttribute('url','');        //设置url来源的相关属性为空
  		    	oNode.setAttribute('url_textfield','');
  		    	oNode.setAttribute('url_valuefield','');
  		    	oNode.setAttribute('sql_textfield','');          //设置自定义SQL来源的相关属性为空
  		    	oNode.setAttribute('sql_valuefield','');
  	    		oNode.setAttribute('sql','');
  	    		oNode.setAttribute('textfield','name');
  		    	oNode.setAttribute('valuefield','key');
  	    	}else if(from=='sql'){   //来自自定义SQL
  	    		oNode.setAttribute('data','');
  	    		oNode.setAttribute('url','');             //设置url来源的相关属性为空
  		    	oNode.setAttribute('url_textfield','');
  		    	oNode.setAttribute('url_valuefield','');
  	    		oNode.setAttribute('dickey','');
  	    		oNode.setAttribute('textfield',oNode.getAttribute('sql_textfield'));
  		    	oNode.setAttribute('valuefield',oNode.getAttribute('sql_valuefield'));
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
		
		
		
	</script>
</body>
</html>
