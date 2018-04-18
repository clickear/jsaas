<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>节点审批历史列表-checkhilist</title>
<%@include file="/commons/mini.jsp"%>
</head>
<body>
	<div class="form-outer">
			<form id="miniForm">
				<table class="table-detail column_2_m" cellspacing="0" cellpadding="1">
					<caption>节点审批历史</caption>
					<tr>
						<th class="form-table-th">字段备注<span class="star">*</span></th>
						<td class="form-table-td">
							<input 
								id="label" 
								class="mini-textbox" 
								name="label" 
								required="true" 
								vtype="maxLength:100"  
								style="width:100%" 
								emptytext="请输入字段备注" 
								onblur="getPinyin" 
							/>
						</td>
						<th class="form-table-th">标　识</th>
						<td class="form-table-td">
							<input 
								id="name" 
								class="mini-textbox" 
								name="name" 
								required="true" 
								vtype="maxLength:100,chinese" 
								style="width: 100%" 
								emptytext="请输入字段标识"  
								onvalidation="onEnglishAndNumberValidation"
							/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	
		function createElement(type, name){     
		    var element = null;     
		    try {        
		        element = document.createElement('<'+type+' name="'+name+'" type="input">');     
		    } catch (e) {}   
		    if(element==null) {     
		        element = document.createElement(type);     
		        element.name = name;     
		    } 
		    return element;     
		}
		
		mini.parse();
		var form=new mini.Form('miniForm');
		//编辑的控件的值
		var oNode = null,
		thePlugins = 'mini-checkhilist';
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
		            oNode.setAttribute('class','mini-checkhilist rxc');
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
		    
		  	
		    for(var key in formData){
            	oNode.setAttribute(key,formData[key]);
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