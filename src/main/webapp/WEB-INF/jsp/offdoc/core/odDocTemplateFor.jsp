<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[OdDocRec]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>

	 
		<form id="form1" method="post" style="z-index: 1;">
			<div class="form-inner">
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<tr>
					<th>选择套红头模板</th>
						<td align="center"><input id="fileId" class="mini-combobox" style="width: 150px;" textField="name" valueField="fileId"
							url="${ctxPath}/offdoc/core/odDocTemplate/getByTempType.do?tempType=1" showNullItem="false" allowInput="false" /></td>
					</tr>
				</table>
			</div>
		</form>
		<div class="mini-toolbar" style="text-align:center;position: fixed;z-index: 10;" 
        borderStyle="border-left:0;border-bottom:0;border-right:0;">
        <a   class="mini-button" onclick="getTheValue()" iconCls="icon-ok">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="mini-button"  onclick="CloseWindow('cancel');" iconCls="icon-cancel">取消</a>
    </div>
							
	<script type="text/javascript">
		mini.parse();
		var fileId = null;
		function getTheValue() {
			var ELfileValue = mini.get("fileId").getValue();
			if (ELfileValue != null) {
				fileId = ELfileValue;
				CloseWindow("ok");
			} else {
				alert("请填值");
			}

		}
	</script>

</body>
</html>