<%-- 
    Document   : 产品属性编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>产品属性编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet"　type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="toolbar1" class="mini-toolbar" style="padding:2px;" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;">
	                <a class="mini-button" iconCls="icon-save" plain="true" onclick="saveModel()">保存</a>
	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow('cancel')">关闭</a>
	            </td>
	        </tr>
	    </table>
	</div>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="valueId" class="mini-hidden"
					value="${oaProductDefParaValue.valueId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>产品属性基本信息</caption>

					<tr>
						<th>所属分类：</th>
						<td><input id="treeId" name="treeId" class="mini-treeselect" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_ASSETS_SORT" 
						multiSelect="false" textField="name" valueField="treeId" parentField="parentId"  required="true" value="${oaProductDefParaValue.treeId}"
						showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onClearTree" popupWidth="300" style="width:90%"/>

						</td>
						<th>名称 *：</th>
						<td><input id="name" name="name" value="${oaProductDefParaValue.name}" required="true"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr id="numType">
						<th>数字类型 ：</th>
						<td><%-- <input id="number" name="number" value="${oaProductDefParaValue.number}" class="mini-spinner" value="123456.123" minValue="" maxValue="1000000" format="#,0.00" vtype="float" style="width: 150px" /> --%>
						<input id="number" name="number" value="${oaProductDefParaValue.number}" class="mini-spinner" minValue="0" maxValue="10000000" vtype="maxLength:10" />
						</td>
					</tr>

					<tr id="strType">
						<th>字符串类型 ：</th>
						<td><input id="string" name="string" value="${oaProductDefParaValue.string}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" />
						</td>
					</tr>
					<tr id="texType">
						<th>文本类型 ：</th>
						<td colspan="3"><textarea id="text" name="text" height="100px" class="mini-textarea" vtype="maxLength:4000" style="width: 90%">${oaProductDefParaValue.text}</textarea></td>
					</tr>
					<tr id="daType">
						<th>时间类型 ：</th>
						<td>
							<input id="datetime" name="datetime" value="${oaProductDefParaValue.datetime}" showTime="true" allowInput="false" class="mini-datepicker" value="date" format="yyyy-MM-dd HH:mm:ss"  timeFormat="HH:mm:ss" emptyText="请输入时间段" />
						</td>
					</tr>

					<tr>
						<th>描述 ：</th>
						<td colspan="3"><textarea name="desc" class="mini-textarea" vtype="maxLength:256" style="width: 90%">${oaProductDefParaValue.desc}</textarea></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1"
		baseUrl="oa/product/oaProductDefParaValue"
		entityName="com.redxun.oa.product.entity.OaProductDefParaValue" />
<script type="text/javascript">
	mini.parse();
	//记录当前编辑记录行的uid
	var uid=null;
	var form=new mini.Form('form1');
	function getModelJson(){
		return form.getData();
	}
	//父窗口的grid
	var parentGrid=null;
	
	function setMainGrid(grid){
		parentGrid=grid;
	}
	
	function saveModel(){
		form.validate();
		if(!form.isValid()) return;
		var name=mini.get('name').getValue();
		
		if(parentGrid){
			var isKeyExist=false;
			for(var i=0;i<parentGrid.getData().length;i++){
				var row=parentGrid.getData()[i];
				if(row.name==name && (uid==null || uid!=row._uid)){
					isKeyExist=true;
					break;
				}
			}
			if(isKeyExist){
				alert('产品属性名称['+name+']已经存在！');
				return;
			}
		}
		
		CloseWindow('ok');
	}
	
	
/* 	$(function(){dataType
		//默认选择第一项
		changeRow(mini.get('dataType').getValue());
	}); */
	
	function changeRow(dataType){
		if(dataType=='String'){
			$("#numType").css('display','none');
			$("#texType").css('display','none');
			$("#daType").css('display','none');
		}else if(dataType=='Textarea'){
			$("#strType").css('display','none');
			$("#numType").css('display','none');
			$("#daType").css('display','none');
		}else if(dataType=='Number'){
			$("#strType").css('display','none');
			$("#daType").css('display','none');
			$("#texType").css('display','none');
		}else if(dataType=='Date'){
			$("#numType").css('display','none');
			$("#strType").css('display','none');
			$("#texType").css('display','none');
		}
	}
	
 	function setData(data){
		uid=data._uid;
		form.setData(data);
		/* changeRow(data.dataType); */
	} 
	
	function onClearTree(e) {
        var obj = e.sender;
        obj.setText("");
        obj.setValue("");
}
	</script>
</body>
</html>