<%-- 
    Document   : 栏目类型编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>栏目类型编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
</script>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${insColType.typeId}" />
	<div id="p1" class="form-outer2">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="typeId" class="mini-hidden" value="${insColType.typeId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>栏目类型基本信息</caption>

					<tr>
						<th width="120">栏目名称 <span class="star">*</span> ：
						</th>
						<td><input name="name" value="${insColType.name}" class="mini-textbox" vtype="maxLength:50" style="width: 90%" required="true" emptyText="请输入栏目名称" /></td>
					</tr>

					<tr>
						<th>栏目Key <span class="star">*</span> ：
						</th>
						<td><input name="key" value="${insColType.key}" class="mini-textbox" vtype="maxLength:50" style="width: 90%" required="true" emptyText="请输入栏目Key" /></td>
					</tr>
					<tr>
						<th>栏目加载类型</th>
						<td>
							<div id="loadType" name="loadType" class="mini-radiobuttonlist"  value="${insColType.loadType}" onvaluechanged="changeLoadType"
							    data="[{id:'URL',text:'URL'},{id:'TEMPLATE',text:'模板加载'}]" >
							</div>    
						</td>
					</tr>
					<tr id="urlTr">
						<th>栏目映射URL <span class="star">*</span> ：
						</th>
						<td><input name="url" value="${insColType.url}" class="mini-textbox" vtype="maxLength:200" style="width: 90%" required="true" emptyText="请输入栏目映射URL" /></td>
					</tr>
					<tr id="tempTr">
						<th>栏目显示模板<span class="star">*</span> ：</th>
						<td>
							<input id="tempId" name="tempId" class="mini-combobox" url="${ctxPath}/oa/info/insPortal/getPortalConfigs.do" value="${insColType.tempId}" textfield="templateName" valuefield="templateId"  required="true" emptyText="请选择..."/> 
						</td>
					</tr>
					<tr>
						<th>栏目图标样式</th>
						<td>
							<input name="iconCls" value="${insColType.iconCls}" class="mini-textbox" vtype="maxLength:50" />
						</td>
					</tr>
					<tr>
						<th>栏目更多URL <span class="star">*</span> ：
						</th>
						<td><input name="moreUrl" value="${insColType.moreUrl}" class="mini-textbox" vtype="maxLength:200" style="width: 90%" required="true" emptyText="请输入栏目更多URL" /></td>
					</tr>
					<tr>
						<th>栏目分类描述 ：</th>
						<td><textarea name="memo" class="mini-textarea" vtype="maxLength:512" style="width: 90%">${insColType.memo}</textarea></td>
					</tr>
				</table>
			</div>
		</form>
	</div>


	<rx:formScript formId="form1" baseUrl="oa/info/insColType" entityName="com.redxun.oa.info.entity.InsColType" />
	<script type="text/javascript">
		$(function(){
			changeLoadType();
		});
		
		function changeLoadType(){
			var loadType=mini.get('loadType');
			var val=loadType.getValue();
			if(val=='URL'){
				$("#urlTr").css("display","");
				$("#tempTr").css("display","none");
			}else{
				$("#urlTr").css("display","none");
				$("#tempTr").css("display","");
			}
		}
	</script>
</body>
</html>