<%-- 
    Document   : ShowEdit和PersonShowEdit里的栏目新增页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>栏目编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="toolbar1" class="mini-toolbar" style="padding: 2px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;" id="toolbarBody"><a class="mini-button" iconCls="icon-save" plain="true" onclick="onOk1">保存</a> <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a></td>
			</tr>
		</table>
	</div>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div style="padding: 5px;">
				<input id="pkId" name="colId" class="mini-hidden" value="${insColumn.colId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>栏目基本信息</caption>
					<tr>
						<th>栏目名称 <span class="star">*</span> ：
						</th>
						<td><input name="name" value="${insColumn.name}" class="mini-textbox" vtype="maxLength:80" required="true" emptyText="请输入栏目名称" style="width: 80%" /></td>
						<th>栏目Key <span class="star">*</span> ：
						</th>
						<td><input name="key" value="${insColumn.key}" class="mini-textbox" vtype="maxLength:50" required="true" emptyText="请输入栏目Key" style="width: 80%" /></td>
					</tr>
					<tr>
						<th>是否为公共 <span class="star">*</span> ：
						</th>
						<td><ui:radioBoolean name="isPublic" value="${insColumn.isPublic}" required="true" emptyText="请填写是否公共" /></td>
						<th>是否允许评论 ：</th>
						<td><ui:radioBoolean name="allowCmt" value="${insColumn.allowCmt}" required="true" /></td>
					</tr>
					<tr>
						<th>是否启用 <span class="star">*</span> ：
						</th>
						<td><ui:radioStatus name="enabled" value="${insColumn.enabled}" emptyText="请输入是否启用" required="true" /></td>
						<th>是否允许关闭 ：</th>
						<td><ui:radioBoolean name="allowClose" value="${insColumn.allowClose}" required="true" /></td>
					</tr>

					<tr>
						<th>信息栏目类型 ：</th>
						<td><input name="colType" class="mini-combobox" style="width: 150px;" value="${typeId}" text="${typeName}" textField="name" valueField="typeId" emptyText="请选择..." url="${ctxPath}/oa/info/insColType/getAll.do" required="true" allowInput="true" showNullItem="true" nullItemText="请选择..." /></td>
						<th>每页记录数 ：</th>
						<td><input name="numsOfPage" value="${insColumn.numsOfPage}" class="mini-spinner" minValue="1" maxValue="1000" vtype="maxLength:10" /></td>
					</tr>
					<tr>
						<th>栏目的展示方式</th>
						<td colspan="3"><ui:miniCombo value="${insColumn.showType}" name="showType" data="[{id:'SCROLL-TEXT',text:'滚动文字'},{id:'LIST',text:'文字列表'},{id:'ROTATION-IMG',text:'轮换图片'}]" /></td>
					</tr>

				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	
		//保存
		function onOk1(){
			form.validate();
	        if (form.isValid() == false) {
	            return;
	        }
	        var portId=${portId};
	        var formData = $("#form1").serializeArray();
	        //加上租用Id
	        if(tenantId!=''){
		        formData.push({
		        	name:'tenantId',
		        	value:tenantId
		        });
	        }
	       
	        //将数据传入后台
	        _SubmitJson({
	        	url:__rootPath+'/oa/info/insColumn/saveByPort.do?portId='+portId,
	        	method:'POST',
	        	data:formData,
	        	success:function(result){
	        		//如果存在自定义的函数，则回调
	        		if(typeof(successCallback)!='undefined'){
	        			successCallback.call(this,result);
	        			return;	
	        		}
	        		var pkId=mini.get("pkId").getValue();
	        		//为更新
	        		if (pkId!=''){
	        			CloseWindow('ok');
	        			return;
	        		}
	        		//为添加操作
	    			if(!confirm("成功保存，是否还需要添加栏目？")){
	    				CloseWindow('ok');
	    				return;
	    			}else{
	    				form.clear();
	    				return;		
	    			} 
	        	}
	        });
		}
	</script>
	<rx:formScript formId="form1" baseUrl="oa/info/insColumn" />
</body>
</html>