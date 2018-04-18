<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html >
<html>
<head>
<title>文本盒子-mini-textboxlist</title>
<%@include file="/commons/mini.jsp"%>
</head>
<body>
	<div class="form-outer">
			<form id="miniForm">
				<table class="table-detail column_2_m" cellspacing="0" cellpadding="1">
					<tr>
						<th >字段备注<span class="star">*</span></th>
						<td >
							<input id="label" class="mini-textbox" name="label" required="true" vtype="maxLength:100"  style="width:90%" emptytext="请输入字段备注" onblur="getPinyin"  />
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
						<th >是否允许输入</th>
						<td >
							<div id="allowinput" name="allowinput" class="mini-checkbox" readOnly="false" text="是">
							</div>
						</td>
						<th >是否使用URL</th>
						<td >
							<div id="isUrl" name="isUrl" class="mini-checkbox" readOnly="false" text="是" onvaluechanged="onValueChanged">
							</div>
						</td>
					</tr>
					<tr id="url_row" style="">
						<th >
							JSON URL
						</th>
						<td  colspan="3">
							<input id="url" class="mini-textbox" name="url" required="true" style="width:99%" enabled="false"/>
						</td>
						
					</tr>
					<tr>
						
						<th >必　填<span class="star">*</span></th>
						<td >
							<input class="mini-checkbox" name="required" id="required"/>是
						</td>
						<td  colspan="2">
						键字段<input id="valuefield" class="mini-textbox" name="valuefield" style="width:50px" required="true" value="id">&nbsp;-&nbsp;值字段:<input id="textfield" class="mini-textbox" name="textfield" required="true" style="width:50px" value="text">
						</td>
						<!-- <th >默认值</th>
						<td >
							键：<input class="mini-textbox" name="defaultKey" style="width:20%"/>值：<input class="mini-textbox" name="defaultValue" style="width:20%"/>
						</td> -->
					</tr>
					
					<tr id="self_row">
						<th >默认值</th>
						<td colspan="3" style="padding:5px;" >
							<div class="mini-toolbar" style="padding:2px;text-align:left;border-bottom: none;">
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
							<div id="props" class="mini-datagrid" style="width:100%;min-height:100px;" height="auto" showPager="false"
								allowCellEdit="true" allowCellSelect="true">
							    <div property="columns">
							        <div type="indexcolumn">序号</div>                
							        <div field="key" width="120" headerAlign="center">键
							         <input property="editor" class="mini-textbox" style="width:100%;" minWidth="120" />
							        </div>    
							        <div field="name" width="120" headerAlign="center">值
							        	<input property="editor" class="mini-textbox" style="width:100%;" minWidth="120" />
							        </div>    
							    </div>
							</div>
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
		thePlugins = 'mini-textboxlist';
		var pluginLabel="${param['titleName']}";
		
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
			//若控件已经存在，则设置回调其值
		    if( UE.plugins[thePlugins].editdom ){
		        //
		    	oNode = UE.plugins[thePlugins].editdom;
		        //获得字段名称
		        var formData={};
		        var attrs=oNode.attributes;
		        
		        var keys="";
		        var values="";
		        for(var i=0;i<attrs.length;i++){
		        	if(attrs[i].name=='value'){
		        		keys=attrs[i].value;
		        	}else if(attrs[i].name=='text'){
		        		values=attrs[i].value;
		        	}else{
		        		formData[attrs[i].name]=attrs[i].value;
		        	}
		        }
		        var keysArray=keys.split(',');
		        var valuesArray=values.split(',');
		        var dataArray=[];
		        for (var i = 0; i < keysArray.length; i++) {
					var dataItem={};
					dataItem['key']=keysArray[i];
					dataItem['name']=valuesArray[i];
					dataArray.push(dataItem);
				}
		        grid.setData(mini.decode(dataArray));	
		        form.setData(formData);
		    }
		    else{
		    	var array=[];
		    	var key={};
		    	var value={};
		    	key.key="valuefield";
		    	key.value="id";
		    	value.key="textfield";
		    	value.value="text";
		    	var data=_GetFormJson("miniForm");
		    	array=getFormData(data);
		    	array.push(key);
		    	array.push(value);
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
	        
	       
	        var isCreate=false;
	        var formData=form.getData();
	        
	        //创新新控件
	        if( !oNode ) {
	        	isCreate=true;
		        try {
		            oNode = createElement('input',name);
		            oNode.setAttribute('class','mini-textboxlist rxc');
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
            
            var gridKeys="";
            var gridValues="";
            var data=grid.getData();
            var gridData=[];
            for(var i=0;i<data.length;i++){
            	if(typeof(data[i].key)=="undefined"||typeof(data[i].name)=="undefined")continue;
            	gridData.push(data[i]);
            }
            for (var i = 0; i < gridData.length; i++) {
				if(i==0){
					gridKeys=gridData[i].key;
					gridValues=gridData[i].name;
					continue;
				}
				gridKeys=gridKeys+","+gridData[i].key;
				gridValues=gridValues+","+gridData[i].name;
			}
		    oNode.setAttribute('value',gridKeys);
		    oNode.setAttribute('text',gridValues);
		    //oNode.setAttribute('readonly','true');
            
	    	 if(isCreate){
	    		 //创建选项
	    		 editor.execCommand('insertHtml',oNode.outerHTML);
		     }else{
		         delete UE.plugins[thePlugins].editdom;
		     }
		};
		
		function onValueChanged(e){
			var value=e.value;
			if(value=='false')
				mini.get("url").setEnabled(false);
			else
				mini.get("url").setEnabled(true);
		}
		
		
	</script>
</body>
</html>
