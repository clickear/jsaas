<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
    <head>
        <title>编辑手机表单</title>
        <%@include file="/commons/edit.jsp" %>
        <script type="text/javascript" src="${ctxPath}/scripts/flow/form/bpmMobileForm.js"></script>
        <script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/ueditor-fd-config-mobile.js"></script>
		<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/ueditor-fd.all.js"> </script>
		<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/lang/zh-cn/zh-cn.js"></script>
		<style>
			.column_2 ul li{
				float: left;
			}
			.table-detail .mini-textbox{
				width: 150px
			}
		</style>
    </head>
    <body  > 
         <div id="toolbar1" class="mini-toolbar" >
		    <table style="width:100%;">
		        <tr>
		            <td style="width:100%;" id="toolbarBody">
		               	 <a class="mini-button" iconCls="icon-save" plain="true" onclick="onOk()">保存</a>
		                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a>
		            </td>
		        </tr>
		    </table>
		</div>
       
         <form id="form1" method="post" >
              <table class="table-detail column_2" cellspacing="1" cellpadding="0">
				<tr>
					<td>
						<ul>
							<li><input  name="boDefId" class="mini-hidden"   value="${boDefId}"/></li>
							<li>
				 				<span>名称：</span>
				 				<input name="name" class="mini-textbox" emptyText="请输入表单名称" required="true" value="${form.name }"/>
				 			</li>
				 			<li>
				 				<span>别名:</span>
				 				<input name="alias" class="mini-textbox" emptyText="请输入别名" required="true" value="${form.alias }"/>
				 			</li>
			 			</ul>
			 		</td>
			 	</tr>
			 	<tr>
					<td>
						<script id="formHtml" name="formHtml" type="text/plain" style="width:100%;height:500px;">${form.formHtml}</script>
					</td>
				</tr>
			</table>
         </form>
       
       <script type="text/javascript">
		var templateView = UE.getEditor('formHtml');
		var boDefId='${boDefId}';
		var templates='${templates}';
		
		templateView.ready(function() {
			var url=__rootPath +"//bpm/form/bpmMobileForm/generateHtml.do";
			var params={boDefId:boDefId,templates:templates};
			$.post(url,params,function(data){
				templateView.setContent(data); //编辑器家在完成后，让编辑器拿到焦点
			})
		});
		
		
		$("#formHtml").height($("body").height()-170);
	   </script>
       
        
      <rx:formScript formId="form1" 
       baseUrl="bpm/core/bpmMobileForm"
       entityName="com.redxun.bpm.form.entity.BpmMobileForm" />
    </body>
</html>