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
//设置左分隔符为 <!
baidu.template.LEFT_DELIMITER='<#';
//设置右分隔符为 <!  
baidu.template.RIGHT_DELIMITER='#>';

var json=${json};
var boDefId="${boDefId}";
</script>

<script type="text/javascript">
	$(function(){
		getEntInfo();
	})
	
	
	
	function onActionRenderer(e){
		var record = e.record;
        var uid = record.pkId;	
		var str='<span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
		
		return str;
	}
	
	function delRow(pk){
  		mini.confirm("该操作会删除BO定义的字段,确定清除BO属性吗?", "提示信息", function(action){
  			if(action!="ok") return;
  			var url=__rootPath+'/sys/bo/sysBoDef/removeAttr.do?attrId=' + pk;
  			var conf={
  				url:url,
  				success:function(data){
  					getEntInfo();
  				}
  			}
  			_SubmitJson(conf);
  		});
	}
	
	function removeEnt(entId){
	
		mini.confirm("该操作会删除BO实体,确定删除吗?", "提示信息", function(action){
  			if(action!="ok") return;
  			
  			var url=__rootPath+'/sys/bo/sysBoDef/removeEnt.do?boDefId='+boDefId+'&entId=' + entId  ;
  			var conf={
  				url:url,
  				success:function(data){
  					getEntInfo();
  				}
  			}
  			_SubmitJson(conf);
  		});
	}

	
	function getEntInfo(){
		var url= __rootPath+ "/sys/bo/sysBoDef/getBoJson.do?boDefId=" + boDefId;
		$.post(url,function(data){
			var hasGen=data.hasGen;
			var html= baidu.template('boEntTemplate',data);  
			$("#divContent").html(html);
			mini.parse();
			var list=data.list;
			for(var i=0;i<list.length;i++){
				var ent=list[i];
				var key=ent.name +"Grid";
				var grid=mini.get(key);
				grid.setData(ent.sysBoAttrs);
			}
		});
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
	
	
</script>
<script id="boEntTemplate"  type="text/html">
	<#for(var i=0;i<list.length;i++){
		var ent=list[i];
		var name=ent.name;
		var comment=ent.comment;
		var isRef=ent.isRef;
		var relationType=ent.relationType;
	#>
        <div>
			<#if(isRef==1){#>
				<div class="mini-toolbar" style="border-bottom: none;">
					<span>备注:</span><#=comment#> 
					<span>引用表单:</span><#=ent.formAlias#></a>
					<#if(relationType=='sub'){#>
					<a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeEnt('<#=ent.id#>')">删除</a>
					<#}#>
				</div>
			<#}else{#>
				<div class="mini-toolbar" style="border-bottom: none;">
					<span>备注:</span><#=comment#> <span>名称:</span><#=name#>
					<#if(relationType=='sub'){#>
					<a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeEnt('<#=ent.id#>')">删除</a>
					<#}#>
				</div>
				<div id="<#=name#>Grid" class="mini-datagrid" style="width: 100%; " 
					allowResize="false" showPager="false"  allowAlternating="true" 
					allowCellWrap="true" multiSelect="true">
					<div property="columns">
						<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;" >操作</div>
						<div field="name" width="100" headerAlign="center" >属性名</div>
						<div field="name" width="100" headerAlign="center" >属性名</div>
						<div field="comment" width="100" headerAlign="center" >备注</div>
						<div field="dataType" width="100" headerAlign="center" renderer="onDataTypeRenderer" >数据类型</div>
						<div field="control" width="100" renderer="onControlRenderer" headerAlign="center" >控件类型</div>
					</div>
				</div>	
			<#}#>
			
		</div>
	<#}#>
	
</script>
</head>
<body>

<!-- 	<div id="toolbar1" class="mini-toolbar" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;" id="toolbarBody">
	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a>
	            </td>
	        </tr>
	    </table>
	</div> -->

	
<div class="shadowBox" style="margin-top: 20px;">
	<form id="form1" method="post">
		<div id="divContent"></div>
	</form>
</div>
<script type="text/javascript">
	addBody();
</script>
</body>
</html>