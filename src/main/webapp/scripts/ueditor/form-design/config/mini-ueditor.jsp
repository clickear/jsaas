<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html >
<html>
<head>
<title>富文本框-mini-ueditor</title>
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
						<th>数据类型</th>
						<td>
							<input class="mini-combobox"  name="datatype"  style="width:150px;" textField="text" valueField="id" onvaluechanged="changeDataType"
							data="[{id:'varchar',text:'字符串'},{id:'clob',text:'大文本'}]" value="varchar" showNullItem="true" allowInput="false"/> 
						</td>
						<th >长　度</th>
						<td >
							<input id="length" name="length" class="mini-textbox"  value="200" vtype="max:4000"  style="width:60px" />
						</td>
					</tr>
					<tr>
						<th>必　填<span class="star">*</span></th>
						<td colspan="3">
							<input class="mini-checkbox" name="required" id="required"/>是
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
		mini.parse();
		var form=new mini.Form('miniForm');
		//编辑的控件的值
		var oNode = null,
		thePlugins = 'mini-ueditor';
		//alert("${param['titleName']}");
		var pluginLabel="${param['titleName']}";  
		window.onload = function() {
			//若控件已经存在，则设置回调其值
		    if( UE.plugins[thePlugins].editdom ){
		    	oNode = UE.plugins[thePlugins].editdom;
		        //获得字段名称
		        var formData={};
		        var attrs=oNode.attributes;
		        
		        for(var i=0;i<attrs.length;i++){
		        	formData[attrs[i].name]=attrs[i].value;
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
		            oNode = createElement('textarea',name);
		            //需要设置该属性，否则没有办法其编辑及删除的弹出菜单
		            oNode.setAttribute('plugins',thePlugins);
		        }catch(e){
		        	alert('创建组件出错，请联系管理员！');
		        	return;
		        }
	        }
	        oNode.setAttribute('class','mini-ueditor  rxc');
	        //更新控件Attributes
	        var style="";
            if(formData.mwidth!=0){
            	style+="width:"+formData.mwidth+formData.wunit;
            }
            else{
            	style+="width:100%";
            }
            if(style!=""){
        		style+=";";
        	}
            if(formData.mheight!=0){
            	style+="height:"+formData.mheight+formData.hunit;
            }
            else{
            	style+="height:300px;";
            }
            oNode.setAttribute('style',style);
            
            for(var key in formData){
            	oNode.setAttribute(key,formData[key]);
            }
	    	
    	 	if(isCreate){
	        	editor.execCommand('insertHtml',oNode.outerHTML);
	     	}else{
	        	delete UE.plugins[thePlugins].editdom;
	     	}
		};
		
		
		/**
		* 数据类型改变
		*/
		function changeDataType(e){
			var val=e.sender.value;
			handDataType(val);
		}
		
		/**
		* 数据类型。
		*/
		function handDataType(val){
			var objLength=$("#length");
			switch(val){
				case "String":
					objLength.show();
					break;
				case "Clob":
					objLength.hide();
					break;
			}
		}
		
	</script>
</body>
</html>
