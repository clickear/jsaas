<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>关联流程</title>
<%@include file="/commons/mini.jsp"%>
</head>
<body>
	<div class="form-outer">
			<form id="miniForm">
				<table class="table-detail column_2_m" cellspacing="0" cellpadding="1">
					<tr>
						<th class="form-table-th">标题<span class="star">*</span></th>
						<td class="form-table-td">
							<input id="label" class="mini-textbox" name="label" required="true" vtype="maxLength:100"  style="width:90%" emptytext="请输入字段备注" onblur="getPinyin" />
						</td>
						<th class="form-table-th">字段名</th>
						<td class="form-table-td"><input id="name" class="mini-textbox" name="name" required="true" vtype="maxLength:100,chinese" style="width: 80%" emptytext="请输入字段标识"  onvalidation="onEnglishAndNumberValidation"/></td>
					</tr>
					<tr>
						
						<th>选择解决方案</th>
					<td><input id="solution" name="solution" class="mini-buttonedit"   onbuttonclick="chooseSolution" selectOnFocus="true" required="true" /></td>
						<th>单选或多选</th>
						<td><div id="chooseitem" name="chooseitem" class="mini-radiobuttonlist"  textField="text" valueField="id" repeatItems="2" 
						 data="[{'id':'single','text':'单选'},{'id':'multi','text':'多选'}]" value="single"></div></td>
					</tr>
					<tr>
					<th >
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
						    value="%"  required="true" allowInput="false" />
						    
						</td>
					</tr>
				</table>
			</form>
	</div>
	<script type="text/javascript">
		mini.parse();
		var form=new mini.Form('miniForm');
		var solution=mini.get("solution");
		//编辑的控件的值
		var oNode = null,
		thePlugins = 'mini-relatedsolution';
		var pluginLabel="${param['titleName']}";//在哪个td里
		window.onload = function() {
			//若控件已经存在，则设置回调其值
			if( UE.plugins[thePlugins].editdom ){
		        //
		    	oNode = UE.plugins[thePlugins].editdom;
		        //获得字段名称
		        var formData={};
		        var attrs=oNode.attributes;
		        
		        for(var i=0;i<attrs.length;i++){
		        	var obj=attrs[i];
		        	var name=obj.name;
		        	if(name=="solutionname"){
		        		 solution.setText(obj.value);
		        	}else{
		        		formData[name]=obj.value;
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
	        
	        var formData=form.getData();
	        var isCreate=false;
		    //控件尚未存在，则创建新的控件，否则进行更新
		    if( !oNode ) {
		        try {
		            oNode = createElement('input',name);
		            oNode.setAttribute('class','mini-relatedsolution rxc');
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
		  	
		    for(var key in formData){
		    	if(key=="solution"){
		    		console.log(formData[key]);
		    		oNode.setAttribute("solutionName",solution.getText());
		    	}
		    		oNode.setAttribute(key,formData[key]);	
            }
		    
		    var style="";
            style+="width:auto";
            if(formData.mheight!=0){
            	if(style!=""){
            		style+=";";
            	}
            	style+="height:"+formData.mheight+formData.hunit;
            }
            oNode.setAttribute('style',style);
            
            
            
            if(isCreate){
	            editor.execCommand('insertHtml',oNode.outerHTML);
            }else{
            	delete UE.plugins[thePlugins].editdom;
            }
		};
		
		
		function chooseSolution(){
			openBpmSolutionDialog(true,function(solutions){
				var solutionsArray=[];
				var solutionNamesArray=[];
				for(var i=0;i<solutions.length;i++){
					solutionNamesArray.push(solutions[i].name);
					solutionsArray.push(solutions[i].solId);
				}
				solution.setValue(solutionsArray);
				solution.setText(solutionNamesArray);
			});
		}
		
		
		
	</script>
</body>
</html>