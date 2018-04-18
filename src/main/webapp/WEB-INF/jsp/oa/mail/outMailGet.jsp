<%-- 
    Document   : 外部邮件明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>外部邮件明细</title>
<%@include file="/commons/get.jsp"%>
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />

</head>
<body>
	
	<div id="toolbar1" class="mini-toolbar" style="padding: 2px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;"><a id="toDel" class="mini-button" iconCls="icon-remove" plain="true" onclick="rowToDel()">删除</a> <a id="del" class="mini-button" iconCls="icon-trash" plain="true" onclick="del()">彻底删除</a> <a class="mini-button" iconCls="icon-transfer" plain="true" onclick="onMoveNode">移动到</a> <a class="mini-button" iconCls="icon-transmit" plain="true" onclick="transmit()">转发</a> <a class="mini-button" iconCls="icon-move" plain="true" onclick="reply()">回复</a> <a id="pre"
					class="mini-button" iconCls="icon-prev" plain="true" onclick="preRecord">上一条</a> <a id="next" class="mini-button" iconCls="icon-next" plain="true" onclick="nextRecord">下一条</a> <a class="mini-button" iconCls="icon-search" plain="true" onclick="fit()">全屏查看邮件</a> <a class="mini-button" iconCls="icon-close" plain="true" onclick="closeWindow">关闭</a></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;padding:5px;border:0">
				<div region="north"  showSplit="false" showHeader="false" height="320" bodyStyle=" margin-left: 5px; margin-right: 5px;" style="border:0">
					<table style="width: 100%;" class="table-detail column_2" cellpadding="0" cellspacing="1" id="headerTable">
						<caption>邮件信息</caption>
						<tr>
							<th width="120">发件人地址</th>
							<td>${mail.senderAddrs}</td>
						</tr>
						<tr>
							<th>收件人地址</th>
							<td>${mail.recAddrs}</td>
						</tr>
						<tr>
							<th>抄送人地址</th>
							<td>${mail.ccAddrs}</td>
						</tr>
				
						<tr>
							<th>暗送人地址</th>
							<td>${mail.bccAddrs}</td>
						</tr>
				
						<tr>
							<th>发送日期</th>
							<td>${mail.sendDate}</td>
						</tr>
				
						<tr>
							<th style="width: 15%;">主　题</th>
							<td>${mail.subject}</td>
						</tr>
				
						<!-- 附件列表 -->
						<tr>
							<th style="width: 15%; vertical-align: middle;">附　件</th>
							<td>
								<ul style="list-style: none;margin:0;padding: 0;">
									<c:forEach items="${attach}" var="a">
										<li style="margin-left: 4px;display: inline;"><a href="${ctxPath}/sys/core/file/previewFile.do?fileId=${a.fileId}">${a.fileName}</a></li>
									</c:forEach>
								</ul>
							</td>
						</tr>
				
					</table>
				</div>
				<div region="center" style="border:0" bodyStyle="padding:5px;border:0;" showSplit="false">
					<div id="content" class="mini-panel" title="内容" style="width: 100%;height:100%; margin:0;padding:0;overflow: auto;" showToolbar="false" showCloseButton="false" showFooter="false" url="${ctxPath}/oa/mail/outMail/getMailContentByMailId.do?mailId=${mail.mailId}"></div>
				</div>
			</div>
	</div>
	<!-- 移动邮件窗口 -->
	<div id="moveWindow" title="选择目标目录" class="mini-window" style="width: 300px; height: 250px;" showModal="true" showFooter="true" allowResize="true">
		<ul id="moveTree" class="mini-tree" style="width: 100%;" url="${ctxPath}/oa/mail/mailFolder/getFolderByGonfigIdOutRoot.do?configId=${mail.mailConfig.configId}" showTreeIcon="true" textField="name" idField="folderId" parentField="parentId" resultAsTree="false" showArrow="false" showTreeLines="true" ondrawnode="draw">
		</ul>
		<div property="footer" style="padding: 5px;">
			<table style="width: 100%">
				<tr>
					<td style="width: 120px; text-align: right;"><input type="button" value="确定" onclick="okWindow()" /> <input type="button" value="取消" onclick="hideWindow()" /></td>
				</tr>
			</table>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var grid=top['main'].datagrid1;
		var layout=mini.get('layout1');
		
		$(function(){
			var pre=mini.get("pre");
			var next=mini.get("next");
			var isHome="${param['isHome']}";
			var del=mini.get("del");
			var toDel=mini.get("toDel");
			if(isHome=='YES'){   //如果是主页，则隐藏上下一条浏览记录，删除和彻底删除按钮
				pre.setVisible(false);
				next.setVisible(false);
				del.setVisible(false);
				toDel.setVisible(false);
			}	
		});
		
		/*回复邮件*/
		function reply() {
			window.location = __rootPath + "/oa/mail/outMail/edit.do?pkId="+ '${mail.mailId}' + "&operation=reply";
		}
		
		/*隐藏邮件信息，显示邮件正文内容*/
		function fit(){
			
			var isVis=layout.isVisibleRegion('north');
			if(isVis){
				 layout.updateRegion("north", { visible: false }); 
			}else{
				layout.updateRegion("north", { visible: true }); 
			}
			mini.layout();
		}

		/*转发邮件*/
		function transmit() {
			window.location = __rootPath + "/oa/mail/outMail/edit.do?pkId="
					+ '${mail.mailId}' + "&operation=transmit";
		}

		/*将邮件状态改成删除状态*/
		function del() {
			if (!confirm("确定删除邮件？（不可恢复）"))
				return;
			_SubmitJson({
				url : __rootPath + "/oa/mail/outMail/delStatus.do",
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

		/*将某行的邮件记录移动到垃圾箱*/
		function rowToDel(pkId) {
			if (!confirm("确定将选中的邮件已至垃圾箱？（可恢复）"))
				return;
			_SubmitJson({
				url : "${ctxPath}/oa/mail/outMail/moveToDelFolder.do",
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
		
		/*上一条记录*/
        function preRecord(){
            var pkId=top['main'].preRecord();
            if(pkId==0) return ;
            window.location.href=__rootPath+'/oa/mail/outMail/get.do?pkId='+pkId;
        }
        
		/*下一条记录*/
        function nextRecord(){
            var pkId=top['main'].nextRecord();
            if(pkId==0) return ;
            window.location.href=__rootPath+'/oa/mail/outMail/get.do?pkId='+pkId;
        }

		/*关闭窗口*/
		function closeWindow() {
			CloseWindow("ok");
		}
		
		  var moveWindow = mini.get("moveWindow");
	        var moveTree = mini.get("moveTree");
	        function okWindow() {            
	            var targetNode = moveTree.getSelectedNode();
  	        _SubmitJson({
  	        	url:__rootPath+"/oa/mail/outMail/moveMail.do",
  	        	method:'POST',
  	        	data:{
  	        		ids: '${mail.mailId}',
  	        		to:targetNode.folderId	
  	        	},
  	        	 success: function(text) {
  	            }
  	        });
	                moveWindow.hide();
	        }
	        function hideWindow() {
	            var moveWindow = mini.get("moveWindow");
	            moveWindow.hide();
	        }

	        /*移动邮件按钮点击事件处理*/
	        function onMoveNode(e) {	
	        	var configId='${mail.mailConfig.configId}';
	            if (configId!='') 
	                moveWindow.show();
	        } 
	        
	        /*绘制移动窗口树的节点图标*/
	        function draw(e){
				if(e.node.parentId=="0")
					e.iconCls="icon-folder";
				if(e.node.type=="RECEIVE-FOLDER")
					e.iconCls="icon-receive";
				if(e.node.type=="SENDER-FOLDER")
					e.iconCls="icon-sender";
				if(e.node.type=="DRAFT-FOLDER")
					e.iconCls="icon-draft";
				if(e.node.type=="DEL-FOLDER")
					e.iconCls="icon-trash";
				if(e.node.type=="OTHER-FOLDER")
					e.iconCls="icon-folder";
	        }
	</script>


	<rx:detailScript baseUrl="oa/mail/mail" formId="form1" />
</body>
</html>