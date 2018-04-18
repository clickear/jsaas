<%-- 
    Document   : 业务表单视图编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务表单视图编辑</title>
<%@include file="/commons/edit.jsp"%>
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>

<script type="text/javascript">
var json=${json};
</script>

<script type="text/javascript">
	var formView=null;
	var hasConflict=false;
	var formViewStatus=false;
	function init(data){
		setFormData(data);
		getEntInfo(data);
		formView=data;
	}
	function setFormViewStatus(status){
			formViewStatus=status;
			if(!formViewStatus)
			$("#genTable").after('<input  class="mini-checkbox" id="deployTheFormView"  text="发布表单"  />');
			mini.parse();
	}
	
	function getEntInfo(data){
		var url= __rootPath+ "/bpm/form/bpmFormView/getBoEntInfo.do";
		$.post(url,data,function(data){
			var hasGenDb=data.hasGenDb;
			var html= baidu.template('boEntTemplate',data);  
			$("#divContent").html(html);
			mini.parse();
			var list=data.list;
			for(var i=0;i<list.length;i++){
				var ent=list[i];
				var key=ent.name +"Grid";
				var grid=mini.get(key);
				grid.setData(ent.sysBoAttrs);
				if(ent.hasConflict){
					hasConflict=true;
				}
			}
			//增加意见显示。
			var opinions=data.opinions;
			if(opinions && opinions.length>0){
				var grid=mini.get("opinionGrid");
				grid.setData(opinions);
			}
			
			var objHasGen=mini.get("genTable");
			if(hasGenDb=="yes"){
				objHasGen.setReadOnly(true);
			}
		});
	}
	
	
	
	function setFormData(data){
		$("#category").text(data.category);
		$("#category").text(data.category);
		$("#name").text(data.name);
		$("#key").text(data.key);	
		$("#descp").text(data.descp);
	}

	
	function onVersionRenderer(e){
		var record = e.record;
		var status = record.status;
		var arr = [ { 'key' : 'new','value' : '新增','css' : 'green'}, 
		            {'key' : 'base', 'value' : '基础','css' : 'red'}, 
		            {'key' : 'diff','value' : '变更','css' : 'orange'}];
		
		return $.formatItemValue(arr,status);
	}
	
	function onContainRenderer(e){
		var record = e.record;
		var contain = record.contain;
		var arr = [ { 'key' : true,'value' : '是','css' : 'red'}, 
		            {'key' : false, 'value' : '否','css' : 'green'}];
		
		return $.formatItemValue(arr,contain);
	}
	
	function onDataTypeRenderer(e){
		var record = e.record;
		var dataType = record.dataType;
		if(dataType=="varchar"){
			return dataType +"("+record.length+")";
		}
		else if(dataType=="number"){
			return dataType +"("+record.length+","+record.decimalLength+")";
		}
		
		return dataType;
	}
	
	function onControlRenderer(e){
		var record = e.record;
		var control = record.control;
		return json[control];
	}
	
	function removeRow(name){
		var grid=mini.get(name +"Grid");
		var rows=grid.getSelecteds();
		grid.removeRows(rows);
	}
	
	function saveFormAndBo(){
		if(hasConflict){
			alert("表单字段有重名,请检查后提交!");
			return;
		}
		var url= __rootPath+ "/bpm/form/bpmFormView/saveFormAndBo.do";
		//
		formView.genTable=mini.get("genTable").getValue();
		var deployTheFormView=mini.get("deployTheFormView");
		if(deployTheFormView){
			if(deployTheFormView.getValue()=="true"){
				formView.deployOrNot="YES";
			}
			
		}
		_SubmitJson({
        	url:url,
        	method:'POST',
        	data:formView,
        	success:function(result){
        		if(result.success){
        			CloseWindow("ok");
        		}
        	}
        });
		
	}
	
	function openFormView(alias){
		var url=__rootPath+ "/bpm/form/bpmFormView/previewByKey/"+alias+".do";
		_OpenWindow({
			url:url,
			height:600,
			width:800,
			title:'表单明细'
		});
	}
</script>
<script id="boEntTemplate"  type="text/html">
	<#for(var i=0;i<list.length;i++){
		var ent=list[i];
		var name=ent.name;
		var comment=ent.comment;
		var hasConflict=ent.hasConflict;
		var isRef=ent.isRef;
	#>
        <div>
			<#if(isRef==1){#>
				<div class="mini-toolbar" style="border-bottom: none;">
					<span>备注:</span><#=comment#> 
					<span>引用表单:</span><a   href="javascript:void(0);" onclick="openFormView('<#=ent.formAlias#>')"><#=ent.formAlias#></a>
				</div>
			<#}else{#>
				<div class="mini-toolbar" style="border-bottom: none;">
					<span>备注:</span><#=comment#> <span>名称:</span><#=name#>
					<#if(hasConflict){#><span class="red" style="margin-left:20px">字段有冲突</span><#}#>
				</div>
				<div id="<#=name#>Grid" class="mini-datagrid" style="width: 100%; " 
					allowResize="false" showPager="false"  allowAlternating="true" 
					allowCellWrap="true" multiSelect="true">
					<div property="columns">
					
						<div field="name" width="100" headerAlign="center" >属性名</div>
						<div field="comment" width="100" headerAlign="center" >备注</div>
						<div field="dataType" width="100" headerAlign="center" renderer="onDataTypeRenderer" >数据类型</div>
						<div field="control" width="100" renderer="onControlRenderer" headerAlign="left" >控件类型</div>
						<div field="version" renderer="onVersionRenderer" width="30" headerAlign="center" >版本</div>
						<div field="contain" renderer="onContainRenderer" width="30" headerAlign="center" >冲突</div>
					</div>
				</div>	
			<#}#>
			
		</div>
	<#}#>
	<#if(opinions && opinions.length>0){#>
		<div>
			<div class="mini-toolbar" style="border-bottom: none;">
				表单意见
			</div>
			<div id="opinionGrid" class="mini-datagrid" style="width: 100%; " 
					allowResize="false" showPager="false"  allowAlternating="true" 
					allowCellWrap="true" multiSelect="true">
				<div property="columns">
					<div field="name" width="100" headerAlign="center" >名称</div>
					<div field="label" width="100" headerAlign="center" >备注</div>
				</div>
			</div>
		</div>
	<#}#>
</script>
</head>
<body>

	<div id="toolbar1" class="mini-toolbar" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;" id="toolbarBody">
	               	<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveFormAndBo">确定</a>
<!-- 	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a> -->
	                
	                <input name="genTable" class="mini-checkbox" id="genTable"  text="生成物理表" trueValue="yes" value="yes" falseValue="no" />
	            </td>
	        </tr>
	    </table>
	</div>

	
	<div class="shadowBox90" style="padding-top: 8px;">
		<form id="form1" method="post">
			<div>
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0" border="0">
					<tr>
						<th style="width:15%">分　　类 </th>
						<td style="width:35%">
							<input id="pkId" name="viewId" class="mini-hidden" value="" />
							<span id="category"></span>
						</td>
						<th style="width:15%">
							<span class="starBox">
								名　　称 <span class="star">*</span>
							</span>
						</th>
						<td style="width:35%"><span id="name"></span></td>
					</tr>
					<tr>
						<th style="width:15%">
							<span class="starBox">
								标  识  键 <span class="star">*</span>
							</span>
						</th>
						<td style="width:35%"><span id="key"></span></td>
						<th style="width:15%">视图说明 </th>
						<td style="width:35%">
							<span id="descp"></span>
						</td>
					</tr>
				</table>
				<div id="divContent"></div>
			</div>
		</form>
	</div>

	

	<rx:formScript formId="form1" baseUrl="bpm/form/bpmFormView" entityName="com.redxun.bpm.form.entity.BpmFormView" />
	<script type="text/javascript">
		addBody();
	</script>
</body>
</html>