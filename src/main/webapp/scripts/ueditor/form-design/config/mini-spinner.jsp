<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html >
<html>
<head>
<title>数字输入框-mini-spinner</title>
<%@include file="/commons/mini.jsp"%>
</head>
<body>
	<div class="form-outer">
			<form id="miniForm">
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="1">
					<caption>数字输入框属性配置</caption>
					<tr>
						<th>字段备注<span class="star">*</span></th>
						<td>
							<input id="label" class="mini-textbox" name="label" required="true" vtype="maxLength:100,chinese"  style="width:90%" emptytext="请输入字段备注" onblur="getPinyin"  />
						</td>
						<th>字段标识<span class="star">*</span></th>
						<td>
							<input id="name" name="name" class="mini-textbox" required="true" onvalidation="onEnglishAndNumberValidation"/>
						</td>
					</tr>
					<tr>
						<th>最小值</th>
						<td>
							<input name="minvalue" class="mini-spinner" style="width:80%" value="1" minValue="-100000000000" maxValue="100000000000"/>
						</td>
						
						<th>最大值</th>
						<td>
							<input name="maxvalue" class="mini-spinner" style="width:80%" value="100" minValue="-100000000000" maxValue="100000000000" />
						</td>
					</tr>
					<tr>
						<th>增量值</th>
						<td>
							<input name="increment" class="mini-spinner" style="width:100px" value="1" />
						</td>
						<th>必　填<span class="star">*</span></th>
						<td>
							<input class="mini-checkbox" name="required" id="required"/>是
						</td>
					</tr>
					
					<tr id="fpattern-row">
						<th>格式串</th>
						<td colspan="3">
							<input id="fpattern" class="mini-textbox" name="format" vtype="maxLength:20" />
							如：¥#,0.00 或,###.##
						</td>
					</tr>
				
					<tr>
						<th>允许文本输入</th>
						<td>
							<input class="mini-checkbox" name="allowinput" id="allowinput"/>是
						</td>
						<th>
							默认值
						</th>
						<td>
							<input class="mini-spinner" name="value"  style="width:90%" miniValue="-100000000000" maxValue="100000000000"/>
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
		thePlugins = 'mini-spinner';
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
		        	formData[attrs[i].name]=attrs[i].value;
		        }

		        form.setData(formData);
		        
		        //onFtypeChange();
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
		            oNode.setAttribute('class','mini-spinner rxc');
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
		    
		  	//设置校验规则
		    oNode.setAttribute('vtype','rangeLength:'+formData['minvalue']+','+formData['maxvalue']);
		    for(var key in formData){
            	oNode.setAttribute(key,formData[key]);
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
            
            oNode.setAttribute('datatype',"number");
            
            if(isCreate){
	            editor.execCommand('insertHtml',oNode.outerHTML);
            }else{
            	delete UE.plugins[thePlugins].editdom;
            }
            	
		};
	
	</script>
</body>
</html>
