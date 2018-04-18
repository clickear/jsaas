<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html >
<html>
<head>
<title>文本框-mini-textbox</title>
<%@include file="/commons/mini.jsp"%>
</head>
<body>
	<div class="form-outer">
			<form id="miniForm">
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="1">
					<tr>
						<th width="150">
							<span class="starBox">
								字段备注<span class="star">*</span>
							</span>		
						</th>
						<td width="300">
							<input id="label" class="mini-textbox" name="label" required="true" vtype="maxLength:100,chinese"  style="width:90%" emptytext="请输入字段备注" onblur="getPinyin" />
						</td>
						<th width="150">
							<span class="starBox">
								字段标识<span class="star">*</span>
							</span>
						</th>
						<td width="300">
							<input id="name" name="name" class="mini-textbox" required="true" onvalidation="onEnglishAndNumberValidation"/>
						</td>
					</tr>
					<tr>
						<th>数据类型</th>
						<td>
							<input class="mini-combobox" name="datatype" onvaluechanged="changeDataType" value="varchar" data="[{'id':'varchar',text:'字符串'},{'id':'number',text:'数字'}]"><!--,{id:'date',text:'日期'}-->
						</td>
						<th>长　　度</th>
						<td>
							<span id="spanLength">
								<input  id="length" class="mini-spinner" name="length" maxValue="4000" minValue="1" value="50" style="width:80px">
							</span>
							
							<span id="spanDecimal">
								精　　度
								<input id="decimal"  class="mini-spinner" name="decimal" minValue="0" maxValue="8" value="0" style="width:80px">
							</div>
						</td>
					</tr>
<!-- 					<tr id="minLength_Tr"> -->
<!-- 						<th>最小长度 -->
<!-- 						</th> -->
<!-- 						<td colspan="3"> -->
<!-- 							<input id="minlen" name="minlen" class="mini-spinner"  minValue="0" maxValue="50" value="0" /> -->
<!-- 						</td> -->
<!-- 					</tr> -->
					<tr id="minNum_Tr">
						<th>最  小  数
						</th>
						<td >
							<input id="minnum" name="minnum" class="mini-textbox" onvalidation="onNumberValidation"/>
						</td>
						<th>最  大  数
						</th>
						<td >
							<input id="maxnum" name="maxnum" class="mini-textbox" onvalidation="onNumberValidation"/>
						</td>
					</tr>
					
					<tr id="vtype_Tr">
						<th>校验规则</th>
						<td colspan="3">
							<input id="validrule" name="validrule" class="mini-combobox"  textField="name" valueField="value" style="width:280px;"
		    				url="${ctxPath}/sys/core/sysDic/getByDicKey.do?dicKey=_FieldValidateRule"  allowInput="false" showNullItem="true" nullItemText="请选择..."/>
		    				<input id="vtype" name="vtype" class="mini-hidden" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="starBox">
								必　　填<span class="star">*</span>
							</span>
						</th>
						<td>
							<input class="mini-checkbox" name="required" id="required" trueValue="true" falseValue="false" />是
						</td>
						<th>
							空文本提示
						</th>
						<td>
							<input class="mini-textbox" name="emptytext" style="width:90%"/>
						</td>
					</tr>
					<tr>
						<th>允许文本输入</th>
						<td>
							<input class="mini-checkbox" name="allowinput" id="allowinput" value="true" trueValue="true" falseValue="false" />是
						</td>
						<th>
							默  认  值
						</th>
						<td>
							<input class="mini-textbox" name="value" style="width:90%"/>
						</td>
					</tr>
					<tr>
						
						<th>格  式  化</th>
						<td colspan="3">
							<input class="mini-textbox" name="format" /> 
							<br/>数字格式如：###.00,#.00,#.000<!--,或日期yyyy-MM-dd或yyyy-MM-dd HH:mm:ss-->
						</td>
					</tr>
					<tr>
						<th>值  来  源</th>
						<td colspan="3"><input class="mini-combobox" name="from" id="from" required="true" data="[{'id':'forminput','text':'手动输入'},{'id':'sequence','text':'系统流水号'},{'id':'scripts','text':'脚本'}]" value="forminput" allowInput="false" onItemclick="fromHanlder(e)" /></td>
						
					</tr>

					<tr id="valueFrom">

						<th>值</th>
						<td colspan="3">
							<div id="valueFromBox_sequence" style="display: none;">
								
								<input id="sequence" name="sequence" class="mini-lookup" style="width: 200px;" textField="name" valueField="seqId" popupWidth="auto" popup="#gridPanel" grid="#datagrid1" multiSelect="false" value="" text="" />

								<div id="gridPanel" class="mini-panel" title="header" iconCls="icon-add" style="width: 480px; height: 280px;" showToolbar="true" showCloseButton="true" showHeader="false" bodyStyle="padding:0" borderStyle="border:0">
									<div property="toolbar" style="padding: 5px; padding-left: 8px; text-align: center;">
										<div style="float: left; padding-bottom: 2px;">
											<span>别名：</span> <input id="keyText" class="mini-textbox" style="width: 160px;" onenter="onSearchClick" /> 
											<a class="mini-button" iconCls="icon-search" onclick="onSearchClick">查询</a>
	                        				<a class="mini-button" iconCls="icon-clear" onclick="onClearClick">清空</a>
										</div>
										<div style="float: right; padding-bottom: 2px;">
										 <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCloseClick">关闭</a>										
									</div>
										<div style="clear: both;"></div>
									</div>
									<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" borderStyle="border:0" showPageSize="false" showPageIndex="true" url="${ctxPath}/sys/core/sysSeqId/getInstAllSeq.do" multiSelect="false" pageSize="15">
										<div property="columns">
											<div type="checkcolumn"></div>
											<div field="name" width="100" headerAlign="center" allowSort="true">名称</div>
											<div field="alias" width="100" headerAlign="center" allowSort="true">别名</div>
										</div>
									</div>
								</div>
							</div>
							<div id="valueFromBox_scripts" style="display: none;">
								<input  id="scripts" class="mini-textarea" style="width: 90%; height: 100px;" name="scripts" />
							</div>
						</td>
					</tr>

					<tr>
						<th>
							控  件  长
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
		var grid1=mini.get("datagrid1");
		grid1.load();
		var form=new mini.Form('miniForm');
		
		//编辑的控件的值
		var oNode = null,
		thePlugins = 'mini-textbox';
		var pluginLabel="${param['titleName']}";
		
		window.onload = function() {
			//若控件已经存在，则设置回调其值
			content=UE.plugins[thePlugins].content;
			parseFields(content);
			
		    if( UE.plugins[thePlugins].editdom ){
		    	oNode = UE.plugins[thePlugins].editdom;
		        //获得字段名称
		        var formData={};
		        var vtypeName;
		        var attrs=oNode.attributes;
		        for(var i=0;i<attrs.length;i++){
			        formData[attrs[i].name]=attrs[i].value;
		        }
		        form.setData(formData);		        
		        initData(formData);
		    }
		    else{
		    	var data=_GetFormJson("miniForm");
		    	var array=getFormData(data);
		    	initPluginSetting(array);
		    	initData(data);
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
		            oNode.setAttribute('class','mini-textbox rxc');
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
		  	setLenValid(oNode,formData);
        	
		  
		  	
		    for(var key in formData){
		    	var val=formData[key];
		    	if(key=="datatype" ){
		    		//设置vtype校验函数
	    			var	vtype=getVType(val,formData);
	    			oNode.setAttribute('vtype',vtype);
	    			oNode.setAttribute('onvalidation','');
		    	}
		    	if(key!="vtype"){
		    		oNode.setAttribute(key,val);	
		    	}
		    	if(key=='validrule'&&val=='onUniqueValidation'){
	    			oNode.setAttribute('onvalidation','onUniqueValidation');
	    			oNode.setAttribute('vtype','');
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
            
            if(isCreate){
	            editor.execCommand('insertHtml',oNode.outerHTML);
            }else{
            	delete UE.plugins[thePlugins].editdom;
            }
            	
		};
		
		function fromHanlder(e){
			var id=e.item.id;
			setFrom(id);
		}
		
		//初始化表单状态
		function initData(formData){
			var dataType=formData.datatype;
			
			setFrom(formData.from);
			handDataType(dataType)
		}
		
		function changeDataType(e){
			var val=e.sender.value;
			handDataType(val);
		}
		
		
		
		
		function setFrom(id){
			if('sequence'==id){
				$("#valueFrom").css('display','');
				$("#valueFromBox_sequence").css('display','');
				$("#valueFromBox_scripts").css('display','none');
				 var sequence = mini.get("sequence");
				 $.ajax({
					 type:"post",
					 url:"${ctxPath}/sys/core/sysSeqId/getNameById.do",
					 data:{id:sequence.getValue()},
					 success:function(result){
						 sequence.setText(result);
					 }
				 });
			}else if('scripts'==id){
				$("#valueFrom").css('display','');
				$("#valueFromBox_sequence").css('display','none');
				$("#valueFromBox_scripts").css('display','');
			}else{
				$("#valueFrom").css('display','none');
				$("#valueFromBox_sequence").css('display','none');
				$("#valueFromBox_scripts").css('display','none');
			}
		}
		
		function onSearchClick(e) {
			var keyText = mini.get("keyText");
            grid1.load({
                key: keyText.value
            });
        }
        function onCloseClick(e) {
            var sequence = mini.get("sequence");
            sequence.hidePopup();
        }
        function onClearClick(e) {
            var sequence = mini.get("sequence");
            sequence.deselectAll();
            grid1.load();
        }
        
        function parseFields(content){
        	var container=$(content);
        	var aryFields=$("input[datatype='number']",container);
        	
        	
        }
        
        function onValuechange(e){
        	var name = mini.get("vtype").getText();
        	var hidName = mini.get("vtype_name");
        	hidName.setValue(name);        	
        }
        
        function getVType(datatype,formData){
        	var vtype=[];
        	var length=formData['length'];
    		if(datatype=='number'){
        		vtype.push('float');
        		var minnum=formData['minnum'];
        		var maxnum=formData['maxnum'];
        		var decimal=formData['decimal'];
        		if(minnum){
            		vtype.push('minnum:'+minnum);
        		}
        		if(maxnum){
            		vtype.push('maxnum:'+maxnum);
        		}
        		if(decimal){
            		vtype.push('decimal:'+decimal);
            		length=length-decimal;	
        		}
        		vtype.push('len:'+length);
        	} else if(datatype=='varchar'){
        		if(formData['validrule']){
        			vtype.push(formData['validrule']);
        		}
        		vtype.push('length:'+length);
        	}
        	return vtype.join(";").toString();
        }
              
        function handlerMinNumVal(val){
			//var objMinLength=$("#minLength_Tr");
			var objMinNum=$("#minNum_Tr");
			var vtype = $("#vtype_Tr");
			switch(val){
				case "varchar":
					//objMinLength.show();
					objMinNum.hide();
					vtype.show();
					break;
				case "date":
					//objMinLength.hide();
					objMinNum.hide();
					vtype.show();
					break;
				case "number":
					//objMinLength.hide();
					objMinNum.show();
					vtype.hide();
					break;
			}
		}
        
        function handlerDefaultVal(val){
			var objLength=mini.get("length");
			var lengthVal=objLength.getValue();
			
			var objDecimal=mini.get("decimal");
			var decimalVal=objDecimal.getValue();
			
// 			var objMinlen=mini.get("minlen");
// 			var minlenVal=objMinlen.getValue();
			
			var objMinNum=mini.get("minnum");
			var minNumVal=objMinNum.getValue();
			switch(val){
				case "varchar":
					if(!lengthVal){
						objLength.setValue(50);
					}
// 					if(!minlenVal){
// 						objMinlen.setValue(0);
// 					}
					break;
				case "number":
					if(!lengthVal){
						objLength.setValue(14);
					}
					else{
						if(lengthVal>22){
							objLength.setValue(14);
						}
					}
					if(!decimalVal){
						objDecimal.setValue(0);
					}
					if(!minNumVal){
						//objMinNum.setValue(0);
					}
					break;
			}
}
		
	</script>
</body>
</html>
