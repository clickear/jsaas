<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>按钮-mini-button</title>
<%@include file="/commons/mini.jsp"%>
</head>
<body>
	<div class="form-outer">
			<form id="miniForm">
				<table class="table-detail column_2_m" cellspacing="0" cellpadding="1">
					
					<tr>
						<th>
							<span class="starBox">
								按钮文本 <span class="star">*</span>
							</span>
						</th>
						<td>
							<input id="text" class="mini-textbox" name="text" required="true" vtype="maxLength:100,chinese"  style="width:90%" emptytext="请输入字段备注" onblur="getPinyin" />
						</td>
						<th>
							<span class="starBox">
								按钮名称 <span class="star">*</span>
							</span>
						</th>
						<td>
							<input id="name" name="name" class="mini-textbox" required="true" onvalidation="onEnglishAndNumberValidation"/>
						</td>
					</tr>
					
					<tr>
						<th>按钮样式</th>
						<td >
		    				<input name="iconCls" id="iconCls"  class="mini-buttonedit" onbuttonclick="selectIcon" style="width:75%"/>
		    				<a class="mini-button" id="icnClsBtn"></a>
		    			</td>
						<th>启　　用
						</th>
						<td><input class="mini-checkbox" name="enabled" id="enabled" trueValue=true falseValue="false" checked="true" />是
						</td>
						
					</tr>
					<tr id="checkdlgTr">
						<th>自定义对话框</th>
						<td colspan="3">	
    						<div id="ckselfdlg" name="ckselfdlg" class="mini-checkbox" value="false" readOnly="false" text="是" onvaluechanged="onSelfDlgChanged"></div>
							<span id="bindDlgDiv" style="display:none">
								对话框 : 
								<input class="mini-buttonedit" id="dialogalias" textName="dialogname" allowInput="false" name="dialogalias" onbuttonclick="selectDialog" style="width:60%"/>
								<div name="seltype" id="seltype" class="mini-radiobuttonlist" textField="text"
									valueField="id" value="normal"
									data="[{id:'normal',text:'默认'},{id:'single',text:'单选'},{id:'multi',text:'多选'}]"></div>
								
								<div id="gridInput" class="mini-datagrid"  showPager="false"
								 allowCellEdit="true" allowCellSelect="true" allowSortColumn="false" 
								 oncellbeginedit="gridInputCellBeginEdit" oncellcommitedit="gridInputCellCommitEdit">
								    <div property="columns">
								        <div field="fieldName" displayfield="fieldText"  headerAlign="center" allowSort="true">查询条件
								        </div>    
								        <div field="mode" displayfield="modeName"  headerAlign="center" allowSort="true">方式
								        	 <input property="editor" class="mini-combobox" style="width:100%;"  data="[{id:'mapping',text:'映射'}, {id:'script', text: '脚本'}]"/>       
								        </div>
								        <div field="bindVal"   headerAlign="center" allowSort="true">绑定值
								        </div>    
								    </div>
								</div>
					
							</span>
						</td>
					</tr>
					<tr id="bindDlgTr" style="display:none">
						<th>返回值绑定</th>
						<td colspan="3">
							<div class="mini-toolbar" >
							    <table style="width:100%;">
							        <tr>
								        <td style="width:100%;">
								            <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeRow">删除</a>
								        </td>
							        </tr>
							    </table>
							</div>
							
							表映射 : <input id="table" name="table" class="mini-combobox" textField="comment" valueField="name" onvaluechanged="tableChange" />
							<span id="spanUnique">唯一字段:<input class="mini-combobox" id="uniquefield" name="uniquefield" textField="comment" valueField="name"  value="" showNullItem="true" /></span>  
							<div id="returnFields" class="mini-datagrid" style="width:100%;minHeight:120px" height="auto" showPager="false" 
								allowCellEdit="true" allowCellSelect="true" multiSelect="true" oncellbeginedit="gridReturnBeginEdit">
							    <div property="columns">
							        
							        <div type="checkcolumn" width="20"></div>
							        <div field="field" displayfield="header"  headerAlign="center" allowSort="true">返回字段</div> 
							        <div field="bindField" displayField="bindFieldName" width="120" headerAlign="center">绑定字段
							         	<input property="editor"  class="mini-combobox"  valueField="name" textField="comment" showNullItem="true" />
							        </div>
							    </div>
							</div>
						</td>
					</tr>
					<tr id="eventTr">
						<th>
							按钮事件方法
						</th>
						<td colspan="3" >
							<input name="onclick" class="mini-textbox" vtype="isEnglishAndNumber" style="width:84%"/>
						</td>
					</tr>
				</table>
			</form>
			</div>
	</div>
	<div style="display:none;">
		<textarea id="inputScriptEditor"  class="mini-textarea" emptyText="请输入脚本"></textarea>
		<textarea id="rtnScriptEditor"  class="mini-textarea" emptyText="请输入脚本"></textarea>
		<input id="inputColumnMapEditor" class="mini-combobox"  valueField="name" textField="comment" />
	</div>
	<script type="text/javascript">
		var queryNameMap = [];
	    var isMain = true;
	    var gridName = "main";
	    var metaJson=[];

		function createElement(type, name){     
		    var element = null;     
		    try {        
		        element = document.createElement('a');
		        element.name = name;
		    } catch (e) {}   
		    if(element==null) {     
		        element = document.createElement(type);     
		        element.name = name;     
		    } 
		    return element;     
		}
		
		mini.parse();
		
		function onSelfDlgChanged(){
			var checked=mini.get('ckselfdlg').getValue();
			if(checked=='true'){
				$("#bindDlgDiv").css('display','');
				$("#bindDlgTr").css('display','');
				$("#eventTr").css('display','none');
			}else{
				$("#bindDlgDiv").css('display','none');
				$("#bindDlgTr").css('display','none');
				$("#eventTr").css('display','');
			}
		}
		
		var form=new mini.Form('miniForm');
		//编辑的控件的值
		var oNode = null,
		thePlugins = 'mini-button';
		var pluginLabel="${param['titleName']}";
		var grid=mini.get('returnFields');
		
		function addRow(){
			grid.addRow({});
		}
		
		function removeRow(){
			var selecteds=grid.getSelecteds();
			grid.removeRows(selecteds);
		}
		
		window.onload = function() {
			initMetadata();
			
			
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
		        
		        
		        initUniqueField();
		       	//初始化对话框。
		        initDialog(formData);
		    }
		    else{
		    	$("#checkdlgTr").css('display','none');
		    	var data=_GetFormJson("miniForm");
		    	var array=getFormData(data);
		    	initPluginSetting(array);
		    	
		    	initUniqueField();
		    }
		    onSelfDlgChanged();
		    
		    
		};
		
		function initDialog(formData){
			
			 //设置其返回的字段绑定
	        mini.get('dialogalias').setText(formData.dialogname);
	        mini.get('iconCls').setText(formData.iconcls);
	        mini.get('seltype').setValue(formData.seltype);
	        mini.get('icnClsBtn').setIconCls(formData.iconcls);
	        var gridRtn=mini.get("returnFields");
	        var gridInput=mini.get("gridInput");
	        var dataOptions=mini.decode(formData['data-options']);
	        
	        bindTable(dataOptions.binding.gridName);
	        if(dataOptions.binding.returnFields){
	        	gridRtn.setData(mini.decode(dataOptions.binding.returnFields));
	        }
	        
	        if(dataOptions.binding.gridInput){
	        	gridInput.setData(mini.decode(dataOptions.binding.gridInput));
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
	        
	       
	        var formData=form.getData();
	        var isCreate=false;
		    //控件尚未存在，则创建新的控件，否则进行更新
		    if( !oNode ) {
		        try {
		            oNode = createElement('a',name);
		            oNode.setAttribute('href','#');
		            oNode.setAttribute('class','mini-button rxc');
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
		    
		    
   			$(oNode).html(formData.text);
   			// 设置其数据属性
   			var dataOptions=oNode.getAttribute("data-options") || "{}";
   			var optionsJson=mini.decode(dataOptions);
   			var binding=getDialogBind(formData);
   			optionsJson.binding=binding;   			
   		 	oNode.setAttribute("data-options",mini.encode(optionsJson));
   		 	
   		 	
            if(isCreate){
	            editor.execCommand('insertHtml',oNode.outerHTML);
            }else{
            	delete UE.plugins[thePlugins].editdom;
            }
		};
		
		function getDialogBind(formData){
			var fields=mini.get('returnFields').getData();
			var inputJson=mini.get('gridInput').getData();
			var gridName=mini.get('table').getValue();
			cleanJson(fields);
			tidyJson(inputJson);
			var json = {};
			json.dialogalias = formData.dialogalias;
			json.dialogname = formData.dialogname;
			json.returnFields = fields;
			json.gridInput=inputJson;
			json.isMain = isMain;
			json.seltype = mini.get("seltype").getValue();
			json.gridName = gridName;
			if(gridName!="main"){
				json.uniquefield=formData.uniquefield;
			}
			return json;
		}
		
		function tidyJson(jsonAry){
			for(var k=0;k<jsonAry.length;k++){
				var row=jsonAry[k];
				delete row.isUpdate;
				delete row.pagesize;
				delete row._id;
				delete row._uid;
				delete row._state;
			}
		}
		
		function cleanJson(rows){
			for(var k=0;k<rows.length;k++){
				var row=rows[k];
				delete row.isUpdate;
				delete row.pagesize;
				delete row._id;
				delete row._uid;
				delete row._state;
				delete row.dataType;
				delete row.isReturn;
				delete row.queryDataType;
				delete row.width;
			}
		}
		
		function selectDialog(){
			_OpenWindow({
				url:'${ctxPath}/sys/core/sysBoList/dialog.do',
				height:450,
				width:800,
				title:'选择对话框',
				ondestroy:function(action){
					if(action!='ok') return;
					
					var win=this.getIFrameEl().contentWindow;
					var rs=win.getData();
					if(!rs) return;
					
					var dialogObj=mini.get('dialogalias');
						
					dialogObj.setValue(rs.key);
					dialogObj.setText(rs.name);
					//返回值设定
					var rtnAry=getReturnList(rs);
					
					if(rtnAry.length==0) {
						alert("请设置对话框的返回列!");
						return;
					}
				 
					mini.get('returnFields').setData(rtnAry);
					//绑定参数
					var params=getDialogParams(rs.searchJson);
					mini.get("gridInput").setData(params)
				}
			});
		}
		
		
		
		function initMetadata(){
			metaJson=getMetaData(editor,UE.plugins[thePlugins].editdom);
			isMain = metaJson["isMain"];
		}
		
		//选择图标
       function selectIcon(e){
    	   var btn=e.sender;
    	   _IconSelectDlg(function(icon){
			//grid.updateRow(row,{iconCls:icon});
			btn.setText(icon);
			btn.setValue(icon);
			mini.get('icnClsBtn').setIconCls(icon);
		});
       }
	
	   function tableChange(e){
		  
		   var value=e.value;
		   var spanObj=$("#spanUnique");
		   var uniqueField=mini.get("uniquefield");
		   if(value=="main"){
			   spanObj.hide();
		   }
		   else{
			   var fields=metaJson[value].fields;
			   uniqueField.setData(fields);
			   spanObj.show();
		   }
	   }
	   
	   function initUniqueField(){
		   
		   var spanObj=$("#spanUnique");
		   var uniqueField=mini.get("uniquefield");
		   var table=mini.get("table").getValue();
		   if(!table || table=="main") {
			   spanObj.hide();
		   }else{
			   var val=uniqueField.getValue();
			   var fields=metaJson[table].fields;
			   uniqueField.setData(fields);
			   if(val){
				   uniqueField.setValue(val);
			   }
			   spanObj.show();
		   }
	   }
      

	</script>
</body>
</html>
