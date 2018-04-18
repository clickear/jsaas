<%-- 
    Document   : 文件编辑页
    Created on : 2015-11-21, 10:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>文件编辑</title>
<%@include file="/commons/edit.jsp"%>
<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.min.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/customer/mini-custom.js" type="text/javascript"></script>
<style type="text/css">
.mini-panel-border,.mini-panel-toolbar {
	border: none;
}

.mini-panel-toolbar,.mini-panel-footer {
	background: #fff;
}
</style>


</head>
<body>
	<div id="toolbar1" class="mini-toolbar" >
    <table style="width:100%;">
        <tr>
            <td style="width:100%;" id="toolbarBody">
               	 <a class="mini-button" iconCls="icon-save" plain="true" onclick="saveContact">保存</a>
               
                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a>
                
            </td>
        </tr>
    </table>
	</div>


	<div class="mini-fit rx-grid-fit">
		<form id="form1" method="post">

				<div title="基本信息">
					<div style="width: 100%;">
						<table class="table-detail column_2_m" cellspacing="1" cellpadding="0" style="height: 100%">
							<caption>文件基本信息</caption>
							<tr>
								<th style="white-space: nowrap;">联系人分类</th>
								<td><input name="lxrfl" id="lxrfl" class="mini-combobox" style="width:150px;" textField="contactType" valueField="contactType" showNullItem="false" allowInput="true" 
								url="${ctxPath}/sys/org/osUser/getContactType.do"></td>
								</tr>
								<tr>
								<th style="white-space: nowrap;">联系人</th>
								<td><input id="lxr" class="mini-textboxlist" name="lxr"   style="width:200px;"   allowInput="false" required="true"  />
							<a class="mini-button" iconCls="icon-add" plain="true" onclick="onSelectUser('lxr')">选择</a></td>
							</tr>

						</table>
					</div>
				</div>
		</form>
	</div>

	<script type="text/javascript">
		mini.parse();
		var lxrfl=mini.get("lxrfl");
		var lxr=mini.get("lxr");
		function onSelectUser(where){
			_TenantUserDlg('${tenantId}',true,function(user){
				var EL=mini.get(where);
				EL.setValue(user.userId);
				EL.setText(user.fullname);
			});
		}
		function saveContact(){
			var lxrflValue=lxrfl.getValue();
			var lxrValue=lxr.getValue();
			var lxrText=lxr.getText();
			$.ajax({
				type:"post",
				url:"${ctxPath}/sys/org/osUser/saveContact.do",
				data:{lxrfl:lxrflValue,lxr:lxrValue,lxr_name:lxrText},
				success:function (result){
					if(result.success){
						CloseWindow('ok');
					}else{
						mini.showTips({
				            content: "<b>失败</b> <br/>数据删除出错",
				            state: 'danger',
				            x: 'center',
				            y:  'center',
				            timeout: 3000
				        });
					}
				}
			});
		}
	</script>
</body>
</html>