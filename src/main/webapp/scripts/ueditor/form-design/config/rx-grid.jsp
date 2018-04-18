<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html >
<html>
<head>
<title>表格控件-rx-grid</title>
<%@include file="/commons/mini.jsp"%>
</head>
<body>
	<div class="form-outer">

			<form id="miniForm">
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="1">
					<tr>
						<th >
							<span class="starBox">
								备　　注<span class="star">*</span>
							</span>
						</th>
						<td >
							<input 
								id="label" 
								class="mini-textbox" 
								name="label" 
								required="true" 
								vtype="maxLength:100"  
								style="width:90%" 
								emptytext="请输入子表备注" 
								onblur="getPinyin" 
							/>
						</td>
						<th >
							<span class="starBox">
								别　　名<span class="star">*</span>
							</span>
						</th>
						<td >
							<input 
								id="name" 
								name="name" 
								class="mini-textbox" 
								required="true" 
								style="width:90%" 
								emptytext="请输入子表别名" 
								onvalidation="onEnglishAndNumberValidation"
							/>
						</td>
					</tr>
					<tr>
						<th >数据编辑模式</th>
						<td colspan="">
							<div 
								class="mini-radiobuttonlist" 
								id="edittype"  
								name="edittype" 
								value="inline" 
								onvaluechanged="changeEditType" 
								data="[{id:'inline',text:'行内'},{id:'openwindow',text:'弹窗'},{id:'editform',text:'弹出表单'}]"
							></div>
						</td>
						<th >必　　填</th>
						<td colspan="">
							<div id="required" name="required" class="mini-checkbox" ></div>
						</td>
					</tr>
					<tr id="tmprow" style="display:none">
						<th >
							编辑窗口模板
						</th>
						<td >
							<input 
								id="templateid" 
								name="templateid" 
								class="mini-combobox" 
								style="width:60%;" 
								textField="name" 
								valueField="id" 
								emptyText="请选择..." 
								url="${ctxPath}/bpm/form/bpmFormView/getDetailTemplates.do"  
								required="true"  
								showNullItem="true" nullItemText="请选择..."
							/> 
						</td>
						<th >
							弹窗宽与高
						</th>
						<td>
							<input id="pwidth" name="pwidth" class="mini-spinner" style="width:80px" value="780" minValue="0" maxValue="2000"/>px
							<input id="pheight" name="pheight" class="mini-spinner" style="width:80px" value="500" minValue="0" maxValue="1300"/>px
						</td>
					</tr>
					<tr id="tr_form" style="display:none">
						<th>选择的表单</th>
						<td>
							<input class="mini-buttonedit" name="formkey" id="formkey" onbuttonclick="onSelectForm" style="width:80%;"/>
						</td>
						<th>弹出表单长宽</th>
						<td>
							宽：<input id="fwidth" name="fwidth" class="mini-spinner" style="width:80px" value="0" minValue="0" maxValue="1200"/>
							高：<input id="fheight" name="fheight" class="mini-spinner" style="width:80px" value="0" minValue="0" maxValue="1200"/>
						</td>
					</tr>
					<tr>
						<th >表头配置</th>
						<td colspan="3" style="padding:0;">
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
							    </table>
							</div>
							<div style="display:none">
								<input  property="editor"
									id="dateFormat" 
									class="mini-combobox" 
									style="width:80%"  
									required="true"
									textfield="formatName"
									valuefield="format"
			    				 	data="[{format:'yyyy-MM-dd',formatName:'yyyy-MM-dd'},{format:'yyyy-MM-dd HH:mm:ss',formatName:'yyyy-MM-dd HH:mm:ss'}]"  
			    				 	allowInput="false" 
			    				 	showNullItem="true" 
			    				 	nullItemText="请选择..."
		    				 	/>
		    				 	<input id="textFormat" class="mini-textbox"  property="editor"/>
							
							</div>
							<div 
								id="hdgrid" 
								class="mini-datagrid" 
								style="width:100%;height:250px"  
								showPager="false"  
								allowCellEdit="true" 
								allowCellSelect="true" 
								multiSelect="true"
								allowAlternating="true"
							>
							    <div property="columns" class="border-right">
							        <div type="indexcolumn" width="20"></div>
							        <div type="checkcolumn" width="20"></div>
							        <div field="name" width="100" headerAlign="center">字段名称(<span class="star">*</span>)
							        	<input property="editor" class="mini-textbox" style="width:100%;" />
							        </div>              
							        <div field="key" width="100" headerAlign="center">字段Key(<span class="star">*</span>)
							        	<input property="editor" class="mini-textbox" style="width:100%;" />
							        </div>
							        <div field="displayfield" width="80">显示字段
							        	 <input property="editor" class="mini-textbox" style="width:100%;" />
							        </div>
							         <div field="requires" width="50">必填
							        	 <input property="editor" class="mini-checkbox" style="width:100%;" />
							        </div>
							        <div field="editcontrol" displayfield="editcontrol_name" width="80" headerAlign="center">编辑控件
							        	<input property="editor" class="mini-combobox" style="width:100%" data="controlData" valueField="id" textField="text" allowInput="false" showNullItem="true" nullItemText="请选择"/>
							        </div>
							        <div field="width" width="50" headerAlign="center">宽度
							        	<input property="editor" class="mini-spinner"  value="100" style="width:100%;" minValue="100" maxValue="1200"/>
							        </div>
							        <div field="datatype" width="60">数据类型
							        	<input property="editor" class="mini-combobox" value="varchar"  textField="text" valueField="id" data="[{id:'',text:'请选择'},{id:'varchar',text:'字符'},{id:'number',text:'数字'},{id:'date',text:'日期型'}]">
							        </div>
							        <div field="format" width="80" displayfield="format" headerAlign="center">格式化
							        	<input property="editor" class="mini-textbox" style="width:100%;" />
							        </div> 
							        <div field="cellStyle" width="60" headerAlign="center">对齐
							        	<input property="editor" class="mini-combobox" value="text-align:right"  textField="text" valueField="id" data="[{id:'text-align:center',text:'居中'},{id:'text-align:left',text:'居左'},{id:'text-align:right',text:'居右'}]">
							        </div>
							        <div field="vtype" displayfield="vtype_name" width="100" headerAlign="center">验证规则
							        	<input property="editor" class="mini-combobox" value="text-align:right"  textField="name" valueField="value" url="${ctxPath}/sys/core/sysDic/getByDicKey.do?dicKey=_FieldValidateRule">
							        </div>
							    </div>
							</div>
						</td>
					</tr>
					<tr>
						<th>
							树形
						</th>
						<td>
							<input name="treegrid" class="mini-checkbox" style="width:100%;" />
						</td>
						<th>
							显示字段
						</th>
						<td>
							<input id="treecolumn" name="treecolumn" class="mini-combobox"  textField="name" valueField="key" emptyText="请选择..."
    						   showNullItem="true" nullItemText="请选择..."/>  
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
	</div>
	<script type="text/javascript">
		
		mini.parse();
		var form=new mini.Form('miniForm');
		
		var controlData=[{id:'mini-textbox',text:'单行文本',tag:'input',datatype:'varchar',editcontrol:'mini-textbox',editcontrol_name:'单行文本',length:'50'},
		{id:'mini-textarea',text:'多行文本',tag:'textarea',datatype:'varchar',editcontrol:'mini-textarea',editcontrol_name:'多行文本',length:'200'},
		{id:'mini-spinner',text:'数字输入',tag:'input',datatype:'number'},
		{id:'mini-datepicker',text:'日期',tag:'input',datatype:'date'},
		{id:'mini-checkbox',text:'复选框',tag:'input',datatype:'varchar'},
		{id:'mini-month',text:'月份',tag:'input',datatype:'date'},
		{id:'mini-time',text:'时间',tag:'input',datatype:'date'},
		{id:'mini-combobox',text:'下拉选择',tag:'input',datatype:'varchar'},
		{id:'mini-buttonedit',text:'编辑按钮',tag:'input',datatype:'varchar'},
		{id:'mini-user',text:'用户',tag:'input',datatype:'varchar'},
		{id:'mini-group',text:'用户组',tag:'input',datatype:'varchar'},
		{id:'mini-dep',text:'部门',tag:'input',datatype:'varchar'},
		{id:'upload-panel',text:'附件',tag:'input',datatype:'varchar'}];
		//获得控件的配置
		function getControlConfig(controlType){
			for(var i=0;i<controlData.length;i++){
				if(controlData[i].id==controlType){
					return controlData[i];
				}
			}
		}
		
		var grid=mini.get('hdgrid');
		//TODO
		grid.on('cellendedit',function(e){
			var record = e.record, field = e.field;
            var val=e.value;
			
            if(field=='name' && (record.key==undefined || record.key=='')){
            	//自动拼音
           		$.ajax({
           			url:__rootPath+'/pub/base/baseService/getPinyin.do',
           			method:'POST',
           			data:{words:val,isCap:'false',isHead:'true'},
           			success:function(result){
           				grid.updateRow(record,{key:result.data})
           			}
           		});
            	return;
            }
	            
			var edittype=mini.get('edittype').getValue();
			if(e.column.displayField && edittype=='editform'&&e.editor.getText()){
				var newRow=jQuery.extend({},e.record);
				newRow[e.column.displayField]=e.editor.getValue();
				newRow[e.column.field]=e.editor.getValue();
				//TODO 设置其tag
				grid.updateRow(e.record,newRow);
			}
			//设置列头
			setTreeColumn(grid.getData());
		});
		
		grid.on('cellbeginedit',function(e) {
            var record = e.record, field = e.field;
            var val=e.value;
           
            
           	if(field!='format'){
           		return;
           	}
           	if(record.datatype=='date'){
            	e.editor=mini.get('dateFormat');
            }else{
            	e.editor=mini.get('textFormat');
            }
           	
           	e.column.editor=e.editor;
           	
           	//若当前行的名称没有值，则通过拼音获得其值
           
        });
		
		
		//编辑的控件的值
		var oNode = null,
		thePlugins = 'rx-grid';
		var pluginLabel="${param['titleName']}";
		function changeFrom(){
			var val=mini.get('from').getValue();
			if(val=='self'){
				$("#self_row").css('display','');
				$("#url_row").css('display','none');
			}else{
				$("#self_row").css('display','none');
				$("#url_row").css('display','');
			}
		}

		function changeEditType(){
			var edittype=mini.get('edittype').getValue();
			if(edittype=='openwindow'){
				$("#tmprow").css('display','');
				$("#tr_form").css('display','none');
			}else if(edittype=='editform'){
				$("#tmprow").css('display','none');
				$("#tr_form").css('display','');
			}else{
				$("#tmprow").css('display','none');
				$("#tr_form").css('display','none');
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
		        	formData[attrs[i].name]=attrs[i].value;
		        }
		        form.setData(formData);
		        mini.get('formkey').setText(formData.formname);
		        //恢复表格的表头
		        var headers=[];
		        var row=$(oNode).find('table > thead > tr');
		        
		        row.children().each(function(){
		        	
		        	var obj=$(this);
		        	var key=getAttr(obj,'header') ;
		        	if(key){
		        		var name=$(this).html().trim();
			        	var width=getAttr(obj,'width') ;
			        	var format=getAttr(obj,'format') ;
			        	
			        	var cellStlyle=getAttr(obj,'cellStlyle') ;
			        	var displayfield=getAttr(obj,'displayfield') ;
			        	var datatype=getAttr(obj,'datatype');
			        	var editcontrol=getAttr(obj,'editcontrol');
			        	var editcontrolName=getAttr(obj,'editcontrol_name');
			        	var vtype=getAttr(obj,'vtype');
			        	var vtypeName=getAttr(obj,'vtype_name');
			        	var requires=getAttr(obj,'requires');
			        	
			        	if(width==undefined){
			        		width=100;
			        	}
			        	if(datatype=='undefined'){
			        		datatype='';
			        	}
			        	if(requires==undefined){
			        		requires='false';
			        	}
			        	headers.push({key:key,name:name,requires:requires,width:width,format:format,editcontrol:editcontrol,vtype:vtype,vtype_name:vtypeName,
			        		cellStlyle:cellStlyle,displayfield:displayfield,datatype:datatype,editcontrol_name:editcontrolName});	
		        	}
		        	
		        });
		        grid.setData(headers);
		        
		        setTreeColumn(headers);
		        
		    }
		    else{
		    	var data=_GetFormJson("miniForm");
		    	var array=getFormData(data);
		    	initPluginSetting(array);
		    }
			
		    //changeFrom();
		    changeEditType();
		};
		
		function setTreeColumn(headers){
			var treeColumn=mini.get("treecolumn");
	       	var data=$.clone(headers);
	        treeColumn.setData(data);
		}
		
		function getAttr(obj,name){
			return obj.attr(name);
		}
		
		function OnCellBeginEdit(e) {
            var record = e.record, field = e.field;
            var type=record.datatype
           	if(field=='length'){
           		if(type=="Date" || type=="Clob"){
           			e.editor=null;
           		}
            }
            else if(field=='decimal'){
            	if(type=="Date" || type=="Clob" || type=="String"){
           			e.editor=null;
           		}
            }
        }
		
		//添加行
		function addRow(){
			grid.addRow({});
		}
		
		function removeRow(){
			var selecteds=grid.getSelecteds();
			grid.removeRows(selecteds);
			
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
	        var gridData=grid.getData();
	        
	        //创新新控件
	        if( !oNode ) {
	        	isCreate=true;
		      
		            oNode = createElement('div',name);
		            oNode.setAttribute('class','rx-grid rxc grid-d');
		            
		            //需要设置该属性，否则没有办法其编辑及删除的弹出菜单
		            oNode.setAttribute('plugins',thePlugins);
		            
		            var table=$('<table style="width:100%;"></table>');
		          
		            var thead=$("<thead></thead>");
		            var tbody=$("<tbody></tbody>");
		            var hrow=$("<tr></tr>");
		            var brow=$("<tr></tr>");

		            //添加表头
		            for(var i=0;i<gridData.length;i++){
		            	var row=gridData[i];
		            	var format=row.format?row.format:'';
		            	if(!row.displayfield) row.displayfield=''; 
		            	if(!row.datatype)  row.datatype='';
		            	if(!row.cellStyle)  row.cellStyle='';
		            	if(!row.width) row.width='';
		            	if(!row.length) row.length='';
		            	if(!row.decimal) row.decimal='';
		            	if(!row.requires) row.requires='false';
		            	if(!row.editcontrol){
		            		row.editcontrol='mini-textbox';
		            	}
		            	if(row.vtype){
		            		vtype='vtype="'+ row.vtype +'" vtype_name="'+ row.vtype_name +'"';
		            		
		            	}
		            	var td='<th class="header" displayfield="'+row.displayfield+'" datatype="' + row.datatype +'" '
		            		+' width="'+row.width + '" header="'+row.key+'" '+vtype
		            		+' length="'+row.length + '" decimal="'+row.decimal+'" '+'requires="'+row.requires +'" '
		            		+' editcontrol="'+row.editcontrol+'" ' 
		            		+' editcontrol_name="'+row.editcontrol_name+'" ' 
		            		+' cellStyle="'+row.cellStyle+'" format="'+format+'">'+row.name+'</th>';
		            		
		            	var td2=$('<td header="'+row.key+'"></td>');
		            	hrow.append(td);
		            	
		            	var tag=getTag(row.editcontrol);
		            	var contrConf=getControlConfig(row.editcontrol);
		            	
		            	var control='<'+tag+' property="editor" allowinput="true" mwidth="0" wunit="px" mheight="0" hunit="%" plugins="'+row.editcontrol+'"';
		            	if(contrConf){
		            		control+= ' editcontrol="'+row.editcontrol+'" editcontrol_name="'+row.editcontrol_name+'" ';
		            		if(!row.datatype){
		            			row.datatype=contrConf.datatype;
		            		}
		            		if(contrConf.length){
		            			control+= ' length="'+contrConf.length+'"';
		            		}
		            	}
		            	control+=' class="rxc '+row.editcontrol+'" name="'+row.key+'" label="'+row.name+'" datatype="'+row.datatype+'" from="forminput"/>';
		            	td2.append(control);
		            	brow.append(td2);
		            }
		            thead.append(hrow);
		            tbody.append(brow);
		            table.append(thead);
		            table.append(tbody);
		           
		            $(oNode).append(table);
		        
	        }else{//更新表头
	        	//同时需要更新其对应的子表名称
	           $(oNode).attr('name',name);
	           
	            var hrow=$(oNode).find('table >thead > tr:first');
	            var brow=$(oNode).find('table > tbody > tr:first');
	            
	            var nbrow=$("<tr></tr>");
	            //清空旧表头的内容
	            hrow.empty();
	            //添加表头
	            for(var i=0;i<gridData.length;i++){
	            	var row=gridData[i];
	            	var format=(row.format)? row.format: '';
	            	var vtype ="";
	            	if(!row.displayfield){
	            		row.displayfield='';
	            	}
	            	if(!row.cellStyle){
	            		row.cellStyle='';
	            	}
	            	if(!row.requires){
	            		row.requires='false';
	            	}
	            	if(row.vtype){
	            		vtype='vtype="'+ row.vtype +'" vtype_name="'+ row.vtype_name +'"';
	            	}
	            	var td='<th class="header" displayfield="'+row.displayfield+'" editcontrol="'+row.editcontrol +'" '  
	            	+' editcontrol_name="'+row.editcontrol_name+'" '+vtype
	            	+' datatype="'+row.datatype+'" width="'+row.width + '" header="'+row.key+'"requires="'+row.requires+'" cellStyle="'+row.cellStyle+'" format="'+format+'">'+row.name+'</th>';
	            	hrow.append(td);
	            	var td=brow.children('[header="'+row.key+'"]');
	            	var tdHtml=$(td).html();
	            	var control=null;
	            	var th=$('<td header="'+row.key+'"></td>');
	            	var oldEditor=$(tdHtml).attr("plugins");
	            	//
	            	if(oldEditor!=gridData[i].editcontrol){
	            		var tag=getTag(row.editcontrol);
	            		control='<'+tag+' property="editor" allowinput="true" mwidth="0" wunit="px" mheight="0" hunit="%" plugins="'+row.editcontrol
	            		+'" datatype="'+row.datatype+'" class="rxc '+row.editcontrol+'" name="'+row.key+'" label="'+row.name+'"/>';
	            		th.append(control);
	            	}else{
	            		th.append(tdHtml);
	            	}
	            	nbrow.append(th);
	            }
	            //移除旧的行
	            brow.remove();
	            //添加新的行
	            $(oNode).children('table').children('tbody').append(nbrow);
	        }
			
	        //更新控件Attributes
	        var style="";
            if(formData.mwidth!=0){
            	style+="width:"+formData.mwidth+formData.wunit+";";
            }
            if(formData.mheight!=0){
            	style+="height:"+formData.mheight+formData.hunit+";";
            }
            oNode.setAttribute('style',style);
            
            for(var key in formData){
            	oNode.setAttribute(key,formData[key]);
            }
			oNode.setAttribute('formname',mini.get('formkey').getText());
			oNode.setAttribute('data-options','{required:'+mini.get('required').getValue()+'}');
            //弹出窗口
            var edittype=formData.edittype;
            var isWinExist=false;
            
            if(isCreate!=null && $(oNode).children('.mini-window').length>0){
            	isWinExist=true;
            }
            
            if(edittype=='openwindow' && (!isWinExist)){//
            	var pheight=mini.get('pheight').getValue();
            	var pwidth=mini.get('pwidth').getValue();
            	//查找其下弹出窗口，并且加载模板显示控件，允许用户自己填写控件
            	var formWin='<div id="editWindow_'+formData.name+'" class="mini-window popup-window-d" title="编辑'+formData.label+'信息" style="width:'+pwidth+'px;height:'+pheight+'px" showMaxButton="true" showModal="true" allowResize="true" allowDrag="true">';
            	formWin+='<div class="mini-toolbar" > ';   
            	formWin+='<a class="mini-button button-d" iconCls="icon-save" plain="true" onclick="saveFormDetail(\''+formData.name+'\')">保存</a>';
            	formWin+='<a class="mini-button button-d" iconCls="icon-close" plain="true" onclick="closeFormDetail(\''+formData.name+'\')">关闭</a>';
            	formWin+='</div>';
            	
            	formWin+='<div id="editForm_'+formData.name+'" class="form">';
				formWin+='<input class="mini-hidden" type="hidden" name="_uid"/>';
            	var json = $.ajax({
            		  url: "${ctxPath}/bpm/form/bpmFormView/getTemplateHtml.do?templateId="+formData.templateid,
            		  async: false,
            		  data:{
            			 columns:mini.encode(gridData)
            		  }
            	}).responseText; 
            	try{
	            	var html=mini.decode(json).data;
	            	formWin+=html;
            	}catch(ex){
            		alert(ex);
            	}
	            formWin+='</div></div>';
	            $(oNode).append(formWin);
            }else{
            	$(oNode).children('.mini-window').attr('id','editWindow_'+formData.name);
            	$(oNode).children('.mini-window').children('.form').attr('id','editForm_'+formData.name);
            }
            
    	 	if(isCreate){
	        	editor.execCommand('insertHtml',oNode.outerHTML);
	     	}else{
	        	delete UE.plugins[thePlugins].editdom;
	     	}
		}
		
		function getTag(id){
			for(var i=0;i<controlData.length;i++){
				var obj=controlData[i];
				if(obj.id==id){
					return obj.tag;
				}
			}
			return "input";
		}
		
		
		function onSelectForm(e){
			var btn=e.sender;
			var grid = mini.get("hdgrid");
			
			_OpenWindow({
				title:'选择表单',
				url:__rootPath+'/bpm/form/bpmFormView/onlineDialog.do?single=true',
				width:800,
				height:450,
				ondestroy:function(action){
					if(action!='ok') return;
					
					var formView=this.getIFrameEl().contentWindow.getFormView();
					var fv=formView[0];
					btn.setText(fv.name);
					btn.setValue(fv.key);
					
					$.ajax({
						url:__rootPath+'/sys/bo/sysBoEnt/getBoEntByBoDefId.do',
						method:'POST',
						data:{boDefId:fv.boDefId},
						success:function(result){
							var arr = result.sysBoAttrs;
							var name = result.name;
							var jsonData = [];
							for(var i=0;i<arr.length;i++){
								var obj=arr[i];
								var nameObj={};
								nameObj.name=obj.comment;
								nameObj.key=obj.name;
								nameObj.datatype=obj.dataType;
								if(obj.isSingle!="1"){
									nameObj.displayfield=obj.name+"_name";
								}
								jsonData.push(nameObj);
							}
							grid.setData(jsonData);
							
							var bname = mini.get("name");
							bname.setValue(name);
						}
					});
				}
			});
		}
		
	</script>
</body>
</html>
