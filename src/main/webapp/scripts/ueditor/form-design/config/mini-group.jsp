<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html >
<html>
<head>
<title>用户组选择框-mini-group</title>
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
						<th>字符长度<span class="star">*</span></th>
						<td colspan="3">
							<input id="length" class="mini-textbox" value="50" name="length" required="true" vtype="maxLength:20"  style="width:60px" emptytext="输入长度"   />
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
						<th>显示全部维度</th>
						<td>
							<div class="mini-radiobuttonlist" repeatItems="5" repeatLayout="table"  required="true" onvaluechanged="changeShowDim"
    						textField="text" valueField="id" value="true" id="showdim" name="showdim" data="[{id:'true',text:'是'},{id:'false',text:'否'}]"></div>
						</td>
						
						<th>初始显示用户组<span class="star">*</span></th>
						<td>
							<input class="mini-checkbox" name="initlogingroup" id="initlogingroup" value="true" trueValue="true" falseValue="false" />是
							
							<input id="level" name="level" class="mini-combobox" style="width:150px;" textField="name" valueField="level" 
						        url="${ctxPath}/sys/org/osRankType/listByDimId.do?dimId=1" showNullItem="true"/>
							
						</td>
					</tr>
					<tr id="dimRow" style="display:none">
						<th>维度选择</th>
						<td colspan="3">
							 <input id="dimid" name="dimid" class="mini-combobox" style="width:150px;" textField="name" valueField="dimId" 
						        url="${ctxPath}/sys/org/osDimension/jsonAll.do" onvaluechanged="changeDim"/>
						</td>
					</tr>
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
	
	
		
		function changeShowDim(){
			var showdim=mini.get('showdim').getValue();
			
			if(showdim=='true'){
				$('#dimRow').css('display',"none");
			}else{
				$('#dimRow').css('display',"");
			}
		}
		mini.parse();
		
		function changeDim(e){
			var dimId=e.value;
			var url="${ctxPath}/sys/org/osRankType/listByDimId.do?dimId="+dimId;
			var levelObj=mini.get("level");
			levelObj.setUrl(url);
		}
		
		function initLevel(){
			var showdim=mini.get('showdim').getValue();
			var dimId=1;
			if(showdim!='true'){
				dimId=mini.get("dimid").getValue();
			}
			var url="${ctxPath}/sys/org/osRankType/listByDimId.do?dimId="+dimId;
			var levelObj=mini.get("level");
			levelObj.setUrl(url);
		
		}
		
		
		
		var form=new mini.Form('miniForm');
		//编辑的控件的值
		var oNode = null,
		thePlugins = 'mini-group';
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
		        	if(attrs[i].name=='data-option'){
		        		var val=mini.decode(attrs[i].value);
		        		mini.get('single').setValue(val.single);
		        	}else{
		        		formData[attrs[i].name]=attrs[i].value;
		        	}
		        	
		        }
		       
		        form.setData(formData);
		        
		        //初始level。
		        initLevel();
		        
		        changeShowDim();
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
		            oNode.setAttribute('class','mini-group rxc');
		            //需要设置该属性，否则没有办法其编辑及删除的弹出菜单
		            oNode.setAttribute('plugins',thePlugins);
		        }catch(e){
		        	alert('error');
		        	return;
		        }
	        }
	        
	        var single=mini.get('single').getValue();
	        var showDim=mini.get('showdim').getValue();
	        var dimId=mini.get('dimid').getValue();
	        var dataOptions={single:single,showDim:showDim,dimId:dimId};
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
		function validateLabel(string){
	  		 reg=/^[a-zA-Z0-9_]+$/;     
	         if(!reg.test(string)){   
	        	 mini.showTips({
	                 content: "<b>保存失败</b><br/><b>字段标识只由字母数字组成</b>",
	                 state: 'danger',
	                 x: 'center',
	                 y: 'center',
	                 timeout: 3000
	             });
	             return false;
	         }
	         return true
	  	}
		
		function　legalValidate(){
	  		if(validateLabel(form.getData().name)){
	  			return true;
	  		}else{
	  			return false;
	  		}
	  	}
		
	</script>
</body>
</html>
