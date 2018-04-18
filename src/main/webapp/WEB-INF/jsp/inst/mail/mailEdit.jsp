<%-- 
    Document   : [Mail]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[Mail]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/uploadpanel/jquery-uploadpanel.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="toolbar1" class="mini-toolbar" style="padding: 2px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;"><a class="mini-button"
					iconCls="icon-send" plain="true" onclick="sendmail">发送</a> <a
					class="mini-button" iconCls="icon-close" plain="true"
					onclick="close">关闭</a></td>
			</tr>
		</table>
	</div>

	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="mailId" class="mini-hidden"
					value="${mail.mailId}" />
				<table class="table-detail" cellspacing="0" cellpadding="0">
					<caption>邮件信息</caption>


					<tr>
						<th>收件人地址：</th>
						<td colspan="3"><input name="recAddrs" class="mini-textarea"
							vtype="maxLength:65535" style="width: 70%; height: 25px"
							required="true" emptyText="请输入收件人地址" value="${mail.recAddrs}" /></td>
					</tr>

					<tr>
						<th>抄送：</th>
						<td colspan="3"><textarea name="ccAddrs"
								class="mini-textarea" vtype="maxLength:65535"
								style="width: 70%; height: 25px">${mail.ccAddrs}</textarea></td>
					</tr>

					<tr>
						<th>密送：</th>
						<td colspan="3"><textarea name="bccAddrs"
								class="mini-textarea" vtype="maxLength:65535"
								style="width: 70%; height: 25px">${mail.bccAddrs}</textarea></td>
					</tr>

					<tr>
						<th>主题 <span class="star">*</span> ：
						</th>
						<td colspan="3"><textarea name="subject"
								class="mini-textarea" vtype="maxLength:512"
								style="width: 70%; height: 25px;" required="true"
								emptyText="请输入主题">${mail.subject}</textarea></td>
					</tr>

					<tr>
						<th>发件人地址 ：</th>
						<td colspan="3"><textarea name="senderAddrs"
								class="mini-textarea" vtype="maxLength:65535"
								style="width: 70%; height: 25px" required="true"
								emptyText="请输入发件人地址">${mail.mailConfig.mailAccount}</textarea></td>
					</tr>
					<tr>
						<th>附件 ：</th>
						<td colspan="3"><input name="fileIds" class="upload-panel"
							plugins="upload-panel" style="width: 80%" allowupload="true"
							label="附件" fname="fileNames" allowlink="true" zipdown="true"
							mwidth="80" wunit="%" mheight="0" hunit="px" fileIds="${fileIds}"
							fileNames="${fileNames}" /></td>
					</tr>

					<tr>
						<td colspan="4"><ui:UEditor height="300" name="content"
								id="content">${mail.content}</ui:UEditor></td>
					</tr>



				</table>
			</div>
		</form>
	</div>

	<script type="text/javascript">
	function handle(){
		 if (confirm("你的邮件内容已经改变，是否将邮件保存到草稿箱？")) {
				var content = _GetFormJson("form1");
				var data = mini.encode(content);
				_SubmitJson({
					url : __rootPath + "/inst/mail/mail/saveToDraft.do",
					data : {
						data : data,
						configId : '${mail.mailConfig.configId}'
					},
					method : 'POST',
					success : function(text) {
						alert("保存成功");
					}
				});
		 }
	}
	
	
		$(function() {
			$('.upload-panel').each(function() {
				$(this).uploadPanel();
			});
			//parseExtendPlugins();
		});

		mini.parse();
		var form = new mini.Form("#form1");

		function sendmail() {
			var content = _GetFormJson("form1");
			//var data = JSON.stringify(content);
			//alert(data);
			var data=mini.encode(content);
			//alert(data);
			_SubmitJson({
				url : __rootPath + "/inst/mail/mail/sendMail.do",
				data : {
					data : data,
					configId : '${mail.mailConfig.configId}'
				},
				method : 'POST',
				success : function() {
					CloseWindow();
				}
			});
		}

		function close() {
			CloseWindow('ok',handle);
		}
	</script>

	<rx:formScript formId="form1" baseUrl="inst/mail/mail" />
</body>
</html>