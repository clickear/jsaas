<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html >
<html>
<head>
<title>部门选择框选择框-mini-textarea</title>
<%@include file="/commons/mini.jsp"%>
</head>
<body>
	<div class="form-outer">
			<form id="miniForm">
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="1">
					<tr>
						<th>字段备注<span class="star">*</span></th>
						<td>
							<input id="label" class="mini-textbox" name="label" required="true" vtype="maxLength:100"  style="width:90%" emptytext="请输入字段备注" onblur="getPinyin" />
						</td>
						<th>字段标识<span class="star">*</span></th>
						<td>
							<input id="name" name="name" class="mini-textbox" required="true" onvalidation="onEnglishAndNumberValidation"/>
						</td>
					</tr>
					
					<tr>
						<th >长　度</th>
						<td >
							<input id="length" required="true" name="length" class="mini-textbox"  value="50" vtype="max:4000"  style="width:60px" />
						</td>
						<th>最小长度</th>
						<td >
							<input id="minlen" name="minlen" class="mini-textbox"   value="0" vtype="max:4000" />
						</td>
					</tr>
					
					<tr>
						<th>是否单选<span class="star">*</span></th>
						<td>
							<div class="mini-radiobuttonlist" repeatItems="5" repeatLayout="table" required="true"
    						textField="text" valueField="id" value="true" id="single" name="single" data="[{id:'true',text:'是'},{id:'false',text:'否'}]"></div>
						</td>
						<th>必　填<span class="star">*</span></th>
						<td>
							<input class="mini-checkbox" name="required" id="required"/>是
						</td>
					</tr>
					<tr>
						<th>初始显示部门<span class="star">*</span></th>
						<td colspan="3">
							<input class="mini-checkbox" name="initlogindep" id="initlogindep" value="true" trueValue="true" falseValue="false" />是
							<input id="level" name="level" class="mini-combobox" style="width:150px;" textField="name" valueField="level" 
						        url="${ctxPath}/sys/org/osRankType/listByDimId.do?dimId=1" showNullItem="true"/>
							
						</td>
						
					</tr>
					<tr>
					<th>初始部门数据</th>
						<td>
							<input id="refconfig" name="refconfig" class="mini-combobox" style="width:150px;" textField="text" valueField="id" 
						        data="[{id:'specific',text:'指定部门'},{id:'all',text:'所有部门'},{id:'current',text:'当前部门'},{id:'level',text:'用户组级别'}]" showNullItem="true"
						        onvaluechanged="configShow"
						        
						        />
						        
							
						</td>
						<td colspan="2">
							<input id="grouplevel" name="grouplevel" class="mini-combobox" style="width:150px;" textField="name" valueField="level" 
						        url="${ctxPath}/sys/org/osRankType/listByDimId.do?dimId=1" showNullItem="true"/>
						        
						    <input id="groupids" name="groupids" class="mini-buttonedit icon-dep-button" textField="name" valueField="groupId" required="true"  allowInput="false" onbuttonclick="_DepButtonClick"/>    
						</td
			
					<tr>
					<tr>
						<th>
							控件长
						</th>
						<td colspan="3">
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
		var grouplevel = mini.get('grouplevel');
		var groupids = mini.get('groupids');
		grouplevel.hide();
		groupids.hide();
		//编辑的控件的值
		var oNode = null,
		thePlugins = 'mini-dep';
		var pluginLabel="${param['titleName']}";
		window.onload = function() {
			//若控件已经存在，则设置回调其值
		    if( UE.plugins[thePlugins].editdom ){
		        //
		    	oNode = UE.plugins[thePlugins].editdom;
		        //获得字段名称
		        var formData={};
		        var attrs=oNode.attributes;
		        
		       
		        for(var i=0;i<attrs.length;i++){
		        	if(attrs[i].name=='data-options'){
		        		var val=mini.decode(attrs[i].value);
		        		mini.get('single').setValue(val.single);		
		        		//mini.get('refconfig').setValue('all');
		        		if(val.config.type){
		        			var configType = val.config.type;
		        			mini.get('refconfig').setValue(val.config.type);
		        			if(configType=='specific') {
		        				groupids.show();
		        				groupids.setValue(val.config.groupids);	
		        				groupids.setText(val.config.groupidsText);
		        			};
		        			if(configType=='level'){
		        				grouplevel.show();
		        				grouplevel.setValue(val.config.level);	
		        			}
		        		}
		        	}else{
		        		formData[attrs[i].name]=attrs[i].value;
		        	}
		        	
		        }
		        
		        form.setData(formData);
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
	       
	        var isCreate=false;
	        var formData=form.getData();
	        
	        //创新新控件
	        if( !oNode ) {
	        	isCreate=true;
		        try {
		            oNode = createElement('input',name);
		            oNode.setAttribute('class','mini-dep rxc');
		            //需要设置该属性，否则没有办法其编辑及删除的弹出菜单
		            oNode.setAttribute('plugins',thePlugins);
		        }catch(e){
		        	alert('error');
		        	return;
		        }
	        }
	        
	        var single=mini.get('single').getValue();
	        var refconfig = mini.get('refconfig').getValue();
	        var grouplevel = mini.get('grouplevel').getValue();
	        var groupids = mini.get('groupids').getValue();
	        var groupidsText = mini.get('groupids').getText();
	        
	        var config = {type:refconfig, grouplevel:grouplevel, groupids:groupids , groupidsText:groupidsText};
	        
	        var dataOptions={single:single, config:config};
	        oNode.setAttribute('data-options',mini.encode(dataOptions));
	        
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
            oNode.setAttribute('allowinput','false');
            
            for(var key in formData){
            	oNode.setAttribute(key,formData[key]);
            	if(key=="name"){
            		oNode.setAttribute("textName",formData[key] +"_name");
            	}
            }
    	 	if(isCreate){
    	 		
	        	editor.execCommand('insertHtml',oNode.outerHTML);
	     	}else{
	        	delete UE.plugins[thePlugins].editdom;
	     	}
		};
		
		
		
		
		function configShow(e){			
			var levelValue = e.sender.getValue();
			//级别
			if('level'== levelValue){
				grouplevel.show();	
				groupids.hide();
				
			}else if('specific' == levelValue){		//指定部门	
				grouplevel.hide();
				groupids.show();
			}else{
				grouplevel.hide();
				groupids.hide();
				groupids.setValue('');
				grouplevel.setValue('');
			}

		}
		
		
		
		
		
		
		
	
	</script>
</body>
</html>
