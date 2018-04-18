<%-- 
    Document   : [Mail]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[Mail]明细</title>
<%@include file="/commons/dynamic.jspf"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />

</head>
<body style="padding: 10px;">
	<div id="toolbar1" class="mini-toolbar" style="padding: 2px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;"><a class="mini-button"
					iconCls="icon-remove" plain="true" onclick="rowToDel()">删除</a> <a
					class="mini-button" iconCls="icon-trash" plain="true"
					onclick="del()">彻底删除</a> <a class="mini-button"
					iconCls="icon-transfer" plain="true">移动到</a> <a class="mini-button"
					iconCls="icon-transmit" plain="true" onclick="transmit()">转发</a> <a
					class="mini-button" iconCls="icon-reply" plain="true"
					onclick="reply()">回复</a> <a class="mini-button" iconCls="icon-prev"
					plain="true" onclick="preRecord">上一条</a> <a class="mini-button"
					iconCls="icon-next" plain="true" onclick="nextRecord">下一条</a><a
					class="mini-button" iconCls="icon-close" plain="true"
					onclick="close">关闭</a></td>
			</tr>
		</table>
	</div>



	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%; height: 100%" class="table-detail"
				cellpadding="0" cellspacing="1">
				<caption>邮件信息</caption>
				<tr>
					<th>发件人地址：</th>
					<td>${mail.senderAddrs}</td>
				</tr>

				<tr>
					<th>收件人地址：</th>
					<td>${mail.recAddrs}</td>
				</tr>


				<tr>
					<th>抄送人地址：</th>
					<td>${mail.ccAddrs}</td>
				</tr>

				<tr>
					<th>暗送人地址：</th>
					<td>${mail.bccAddrs}</td>
				</tr>

				<tr>
					<th>发送日期：</th>
					<td>${mail.sendDate}</td>
				</tr>

				<tr>
					<th style="width: 15%;">主题：</th>
					<td>${mail.subject}</td>
				</tr>

				<tr>
					<th style="width: 15%;">附件：</th>
					<td>
						<ul>
							<c:forEach items="${attach}" var="a">
								<li><a
									href="${ctxPath}/sys/core/file/previewFile.do?fileId=${a.fileId}">${a.fileName}</a></li>
							</c:forEach>
						</ul>
					</td>
				</tr>

			</table>
		</div>
	</div>
	<div class="mini-fit">
		<div id="content" class="mini-panel" title="内容"
			style="width: 100%; height: 100%; border-top: 0; overflow: auto;"
			showToolbar="false" showCloseButton="false" showFooter="false"
			url="${ctxPath}/inst/mail/mail/getMailContentByMailId.do?mailId=${mail.mailId}">
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var grid=top['main'].datagrid1;
		function reply() {
			window.location = __rootPath + "/inst/mail/mail/edit.do?pkId="
					+ '${mail.mailId}' + "&operation=reply";
		}

		function transmit() {
			window.location = __rootPath + "/inst/mail/mail/edit.do?pkId="
					+ '${mail.mailId}' + "&operation=transmit";
		}

		function del() {
			if (!confirm("确定删除邮件？（不可恢复）"))
				return;
			_SubmitJson({
				url : __rootPath + "/inst/mail/mail/delStatus.do",
				data : {
					ids : '${mail.mailId}'
				},
				method : 'POST',
				success : function() {
					CloseWindow();
				}
			});
			grid.load();
		}

		function rowToDel(pkId) {
			if (!confirm("确定将选中的邮件已至垃圾箱？（可恢复）"))
				return;
			_SubmitJson({
				url : "${ctxPath}/inst/mail/mail/moveToDelFolder.do",
				data : {
					tenantId : tenantId,
					ids : pkId
				},
				method : 'POST',
				success : function() {
					grid.load();
				}
			});
		}
		
        function preRecord(){
            var pkId=top['main'].preRecord();
            if(pkId==0) return ;
            window.location.href=__rootPath+'/inst/mail/mail/get.do?pkId='+pkId;
			_SubmitJson({
				url : "${ctxPath}/inst/mail/mail/updateReadFlag.do",
				showMsg:false,
				data:{
					pkId:pkId
				},
				method:"POST",
				success:function(){
					var pageIndex=grid.getPageIndex();
					var pageSize=grid.getPageSize();
					grid.load({pageIndex:pageIndex,pageSize: pageSize});
				}
			});
			var leftTree=top['mail'].leftTree;
			var node=leftTree.getSelected();
			_SubmitJson({
				url : "${ctxPath}/inst/mail/mailFolder/getUnreadSum.do",
				showMsg:false,
				data:{
					mailFolderId:node.folderId
				},
				method:"POST",
				success:function(text){
					var str=text.data;
					top['mail'].setNodeName("收件箱("+str+")");
				}
			});
        }
        
        function nextRecord(){
            var pkId=top['main'].nextRecord();
            if(pkId==0) return ;
            window.location.href=__rootPath+'/inst/mail/mail/get.do?pkId='+pkId;
			_SubmitJson({
				url : "${ctxPath}/inst/mail/mail/updateReadFlag.do",
				showMsg:false,
				data:{
					pkId:pkId
				},
				method:"POST",
				success:function(){
					var pageIndex=grid.getPageIndex();
					var pageSize=grid.getPageSize();
					grid.load({pageIndex:pageIndex,pageSize: pageSize});
				}
			});
			var leftTree=top['mail'].leftTree;
			var node=leftTree.getSelected();
			_SubmitJson({
				url : "${ctxPath}/inst/mail/mailFolder/getUnreadSum.do",
				showMsg:false,
				data:{
					mailFolderId:node.folderId
				},
				method:"POST",
				success:function(text){
					var str=text.data;
					top['mail'].setNodeName("收件箱("+str+")");
				}
			});
        }

		function close() {
			CloseWindow();
		}
	</script>


	<rx:detailScript baseUrl="inst/mail/mail" formId="form1" />
</body>
</html>