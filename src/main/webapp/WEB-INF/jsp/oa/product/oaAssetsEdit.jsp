<%-- 
    Document   : 资产信息编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>资产信息编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/common/form.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var oAStauts=[{ id: 'BRANDNEW', text: '全新' }, { id: 'SECONDHAND', text: '旧货'}, { id: 'DISPOSABLE', text: '一次性'}];
</script>
</head>
<body>
	<div class="mini-toolbar" style="padding:2px;" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;">
	             	<a class="mini-button" iconCls="icon-save" plain="true" onclick="onSave">保存</a>
	             	<span class="separator"></span>
	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onClose">关闭</a>
	            </td>
	        </tr>
	    </table>
	</div>
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="assId" class="mini-hidden"
					value="${oaAssets.assId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>资产信息基本信息</caption>

					<tr>
						<th>产品分类 *：</th>
						<td colspan="3">
						<input id="prodDefId" name="prodDefId" class="mini-combobox" style="width:210px" value="${prodDefId}" text="${prodName}" textField="name" valueField="prodDefId" emptyText="请选择..." 
						url="${ctxPath}/oa/product/oaAssets/getDefAll.do"
						 required="true" allowInput="false" nullItemText="请选择..." onvaluechanged="genvaluechanged" />
						</td>
					</tr>

					<tr>
						<th>资产编号 *：</th>
						<td><input name="code" value="${oaAssets.code}"
							class="mini-textbox" vtype="maxLength:255" style="width: 90%"
							required="true" emptyText="请输入资产编号" />

						</td>
						
						<th>资产名称 *：</th>
						<td><input name="name" value="${oaAssets.name}"
							class="mini-textbox" vtype="maxLength:255" style="width: 90%"
							required="true" emptyText="请输入资产名称" />

						</td>
					</tr>
					
					<tr>
						<th>状态 *：</th>
						<td>
							<input id="status" name="status" required="true" data="oAStauts" value="${oaAssets.status}"
							 class="mini-combobox" textField="text" valueField="id" style="width: 90%" allowInput="false" />
						</td>
					</tr>

					<tr>
						<th>描述 ：</th>
						<td colspan="3"><textarea name="desc" class="mini-textarea" vtype="maxLength:512" style="width: 90%">${oaAssets.desc}</textarea></td>
					</tr>

				</table>
			</div>
		</form>
		<div class="mini-toolbar" style="padding:2px;">
		    <table style="width:100%;">
		        <tr>
		            <td style="width:100%;">
		            	<a class="mini-button" iconCls="icon-add" plain="true" onclick="addRow">自定义添加产品类型属性</a>
		                <span class="separator"></span>
			            <a class="mini-button" iconCls="icon-up" plain="true" onclick="upRow">向上</a>
			            <a class="mini-button" iconCls="icon-down" plain="true" onclick="downRow">向下</a>
		            </td>
		        </tr>
		    </table>
		</div>
		<!-- 字段属性配置 -->
		 <div class="mini-fit" style="border:0;padding:5px;">
			<div id="datagrid1" class="mini-datagrid"style="width: 100%;"
			height="auto" showPager="false" allowCellEdit="false"
			allowCellSelect="true">
				<div property="columns">
					<div field="name" width="80" headerAlign="center">产品类型
						<input property="editor" class="mini-textbox" value="25" style="width:100%;"/>
					</div>
					<div field="number" displayField="numbername" width="200" headerAlign="center" >产品属性
						<input property="editor" class="mini-textbox"  style="width:100%;" />
					</div>
				</div>
			</div>
			<div class="mini-toolbar" style="padding:2px;">自定义模块</div>
			<div id="datagridList" class="mini-datagrid"style="width: 100%;"
			height="auto" showPager="false" allowCellEdit="true"
			allowCellSelect="true">
				<div property="columns">
				<div field="customKeyName" width="80" headerAlign="center" allowSort="true">
				<input property="editor" class="mini-textbox" style="width:100%;"/>
				</div>
				<div field="customValueName" width="200" headerAlign="center" allowSort="true">
				<input property="editor" class="mini-textbox" style="width:100%;"/>
				</div>
				</div>
			</div>
		</div>
	<%-- <rx:formScript formId="form1" baseUrl="oa/product/oaAssets"
		entityName="com.redxun.oa.product.entity.OaAssets" /> --%>
		<script type="text/javascript">
		mini.parse();
		var grid=mini.get('datagrid1');
		var form=new mini.Form('form1');
		var gridlist=mini.get('datagridList');
		
		
   
		
		function addRow() {
            var newRow = { number: "" };
            gridlist.addRow(newRow, -1);
            gridlist.validateRow(newRow);   //加入新行，马上验证新行
        }
	
		//保存
		function onSave(){
			form.validate();
			if(!form.isValid()){
				return;
			}
			var formData=$("#form1").serializeArray();
	        //加上租用Grid的属性
	        var grid1=mini.get('datagrid1');
	    	var data = grid1.getData();
	    	var gridlist=mini.get('datagridList');
	    	var datalist=gridlist.getData();
            var json = mini.encode(data);
            var listjson=mini.encode(datalist);
            formData.push({
            	name:'oldjson',
            	value:json
            });
            formData.push({
            	name:'newjson',
            	value:listjson
            });
	       
	        _SubmitJson({
	        	url:"${ctxPath}/oa/product/oaAssets/save.do",
	        	method:'POST',
	        	data:formData,
	        	success:function(result){
	        		CloseWindow('ok');
	        	}
	        });
		}
		
		//获取到分类模块下的产品类型和产品属性
		function genvaluechanged(e){
			var prodDefId=mini.get("prodDefId").getValue();
			_SubmitJson({
				
				url:__rootPath+"/oa/product/oaAssets/getVKAll.do",
				data:{
					prodDefId:prodDefId
				},
				method:'POST',
				success:function(result){
					var data=result.data;
					var grid=mini.get("datagrid1");
					 grid.setData(data);
				}
			});
		}
		
		
		
		//上移一行
		function upRow() {
            var row = grid.getSelected();
            if (row) {
                var index = grid.indexOf(row);
                grid.moveRow(row, index - 1);
            }
        }
		//下移一行
        function downRow() {
            var row = grid.getSelected();
            if (row) {
                var index = grid.indexOf(row);
                grid.moveRow(row, index + 2);
            }
        }
	
			//关闭
		function onClose(){
		CloseWindow('close');
		}
		</script>
</body>
</html>