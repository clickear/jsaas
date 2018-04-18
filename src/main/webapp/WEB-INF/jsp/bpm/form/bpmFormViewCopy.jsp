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
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/ueditor-fd-config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/ueditor-fd.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/ueditor-form.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/lang/zh-cn/zh-cn.js"></script>
<!-- 引入表单控件 -->
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/design-plugin.js"></script>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;" id="toolbarBody">
	               	<a class="mini-button" iconCls="icon-save" plain="true" onclick="newCopy">确定</a>
<!-- 	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a> -->
	                
	            </td>
	        </tr>
	    </table>
	</div>
	
	<div class="form-outer shadowBox90" style="padding-top: 8px;">
	
	<form id="form1" method="post" class="form-outer2">
		
		<table class="table-detail column_2_m" cellspacing="1" cellpadding="0" border="2">
			<tr>
				<th >源名称 </th>
				<td >
					<input class="mini-hidden" value="${view.viewId }" name="viewId" />
					${view.name }
				</td>
				<th >源别名 </th>
				<td >
					${view.key }
				</td>
				
				
			</tr>
			<tr>
				<th >
					<span class="starBox">
						新名称<span class="star">*</span>
					</span>
				</th>
				<td>
					<input class="mini-textbox" value="" name="name" required="true" />
				</td>
				<th >
					<span class="starBox">
						新别名<span class="star">*</span>
					</span>
				</th>
				<td>
					<input class="mini-textbox" value="" name="key" required="true"/>
				</td>
			</tr>
		</table>
			
	</form>
	</div>
	
	<rx:formScript formId="form1" baseUrl="bpm/form/bpmFormView" entityName="com.redxun.bpm.form.entity.BpmFormView" />
	<script type="text/javascript">
		addBody();
		$(function(){
			
		});
		
		function newCopy(){
			var url=__rootPath +"/bpm/form/bpmFormView/copyNew.do";
			_SaveJson("form1",url,function(result){
				if(result.success){
					CloseWindow("ok")
				}
				
			})
		}
	</script>
</body>
</html>