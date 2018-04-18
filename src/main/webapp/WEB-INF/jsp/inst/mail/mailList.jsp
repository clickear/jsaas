<%-- 
    Document   : [Mail]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[Mail]列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>


</head>
<body>
	<div id="toolbar1" class="mini-toolbar" style="padding: 2px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 30%;">
				<a class="mini-button"
					iconCls="icon-writemail" plain="true" onclick="writeMail">写信</a><a class="mini-button"
					iconCls="icon-remove" plain="true" onclick="todelfolder">刪除</a> <a
					class="mini-button" iconCls="icon-trash" plain="true" onclick="Del">彻底删除</a>
					<a class="mini-button" iconCls="icon-transmit" plain="true" onclick="transmit">转发</a>
					<span class="separater"></span><a class="mini-button"
					iconCls="icon-transfer" plain="true" onclick="onMoveNode">移动到</a>
					<a class="mini-button"
					iconCls="icon-refresh" plain="true" onclick="refresh">刷新</a></td>
			</tr>
			</table>
			<table>
			<tr>
				<td>
					<form id="searchForm">
						主题： <input name="Q_subject_S_LK" class="mini-textbox" />
						 发件人： <input name="Q_senderAddrs_S_LK" class="mini-textbox" /> 
						 时间：<input name="Q_sendDate_D_GT" class="mini-datepicker" value="" format="yyyy-MM-dd" /> 到 <input name="Q_sendDate_D_LT" class="mini-datepicker" value="" format="yyyy-MM-dd" />
							&nbsp;
						<a class="mini-button" onclick="onSearch" iconCls="icon-search">查询</a>
					</form>
					</td>
				</tr>
		</table>
	</div>
	<div class="mini-fit" style="height: 100%;">


		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/inst/mail/mail/getMailByFolderId.do?folderId=${param['folderId']}"
			idField="mailId" multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons"
			showVGridLines="false" onrowdblclick="check">
			<div property="columns">
				<div type="indexcolumn" width="20"></div>
				<div type="checkcolumn" width="20"></div>
				<div field="readFlag" width="20" headerAlign="center"
					allowSort="true" align="center" class="ppp">阅读状态</div>
				<div field="replyFlag" width="20" headerAlign="center"
					allowSort="true" align="center">回复状态</div>
				<div field="subject" width="100" headerAlign="center"
					allowSort="true">主题</div>
				<div field="senderAddrs" width="40" headerAlign="center"
					allowSort="true">发件人</div>
				<div field="recAddrs" width="40" headerAlign="center"
					allowSort="true">收件人</div>
				<div field="sendDate" width="50" headerAlign="center"
					allowSort="true" dateFormat="yyyy-MM-dd HH:mm">时间</div>
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">操作</div>
			</div>
		</div>
	</div>

	<div id="moveWindow" title="选择目标目录" class="mini-window"
		style="width: 300px; height: 250px;" showModal="true"
		showFooter="true" allowResize="true">
		<ul id="moveTree" class="mini-tree" style="width: 100%;"
			url="${ctxPath}/inst/mail/mailFolder/getFolderByGonfigIdOutRoot.do?configId=${param['configId']}"
			showTreeIcon="true" textField="name" idField="folderId"
			parentField="parentId" resultAsTree="false" showArrow="false"
			showTreeLines="true" ondrawnode="draw">
		</ul>
		<div property="footer" style="padding: 5px;">
			<table style="width: 100%">
				<tr>
					<td style="width: 120px; text-align: right;"><input
						type="button" value="确定" onclick="okWindow()" /> <input
						type="button" value="取消" onclick="hideWindow()" /></td>
				</tr>
			</table>
		</div>
	</div>

	<%--  	  <redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.inst.mail.entity.Mail" winHeight="450"
		winWidth="700" entityTitle="[Mail]" baseUrl="inst/mail/mail" />   --%>
	<script type="text/javascript">

		//行功能按钮
		mini.parse();
		var grid=mini.get("datagrid1");
		grid.load();
		top['main']=window;
		var configId="${param['configId']}";
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="查看" onclick="getRow1(\''
					+ pkId
					+ '\')"></span>'
					+ ' <span class="icon-remove" title="删除" onclick="rowToDel(\''
					+ pkId + '\')"></span>';
			return s;
		}
		
		function refresh(){
			grid.load();
		}
		
		function writeMail(){
			var tree = top['mail'].leftTree;
			var node=tree.getSelected();
			if(node==null){
				alert("请选择一个账号!");
				return;
			}
			_OpenWindow({
				url:__rootPath+"/inst/mail/mail/edit.do?configId="+configId,
				title:"写邮件",
				height:800,
				width:800
			});
		}
		
		function transmit(){
	           var rows = grid.getSelecteds();        
	           if (rows.length <= 0) {
	           	alert("请选记录");
	           	return;
	           }
	           
	           var ids = [];
	           for (var i = 0, l = rows.length; i < l; i++) {
	               var r = rows[i];
	               ids.push(r.pkId);
	           }
	           //grid.loading("操作中，请稍后......");
	           if(ids.length==1){
	        	   var pkId=ids[0];
	        	   _OpenWindow({
	        		   url: __rootPath + "/inst/mail/mail/edit.do?pkId="+ pkId + "&operation=transmit",
	   				   width : 1000,
					   height : 800,
					   title : "邮件内容",
				       ondestroy : function(action) {
				    	   
					}
	        	   });

	           }
	           if(ids.length>=2 &&ids.length<=5){
	        	   var temp;
	        	   var fileIds=[];
	           _SubmitJson({
	           	url:__rootPath+"/inst/mail/mail/mailsTransmit.do",
	           	method:'POST',
	           	data:{ids: ids.join(',')},
	           	 success: function(text) {
	                  temp=text.data;
	                 for (i=0;i<temp.length ;i++ ) 
	                  { 
	                	 fileIds[i]=temp[i].fileId;
	                  } 
	  	           _OpenWindow({
	        		   url: __rootPath + "/inst/mail/mail/edit.do?configId="+configId+"&operation=mailsTransmit&fileIds="+fileIds.join(','),
	   				   width : 1000,
					   height : 800,
					   title : "邮件内容",
				       ondestroy : function(action) {
				    	   
				       }
		           });
	               }
	           });
	           }
	           if(ids.length>5){
	        	   alert("转发邮件数量不能大于5！");
	        	   return ;
	           }
	           /*var msgId=mini.loading("正在提交数据...", "操作信息");*/
	           
			
		}
		
		//设置已读和未读图标
	       grid.on("drawcell", function (e) {
		        field = e.field,
		        value = e.value;
	            var s;
				if(field=='readFlag'){
					if(value=="0"){
						s="<img src='${ctxPath}/styles/icons/icon-newmail.png' alt='新邮件' />";
					}
					else{
						s="<img src='${ctxPath}/styles/icons/icon-readmail.png' alt='已读' />";
					}
					e.cellHtml= s;
				}
				if(field=='replyFlag'){
					if(value=="0"){
						s="";
					}
					else{
						s="<img src='${ctxPath}/styles/icons/icon-reply.png' alt='已回复' />";
					}
					e.cellHtml= s;
				}

	        });
	       
	       //彻底删除邮件
	       function Del() {
	           var rows = grid.getSelecteds();
	           if (rows.length <= 0) {
	           	alert("请选中一条记录");
	           	return;
	           }
	           //行允许删除
	           if(rowRemoveAllow && !rowRemoveAllow()){
	           	return;
	           }
	           
	           if (!confirm("确定删除选中记录？")) return;
	           
	           var ids = [];
	           for (var i = 0, l = rows.length; i < l; i++) {
	               var r = rows[i];
	               ids.push(r.pkId);
	           }
	           //grid.loading("操作中，请稍后......");

	           _SubmitJson({
	           	url:__rootPath+"/inst/mail/mail/delStatus.do",
	           	method:'POST',
	           	data:{ids: ids.join(',')},
	           	 success: function(text) {
	                   grid.load();
	               }
	           });
	          
	       }

	     //双击查看邮件  
		function check(e) {
			var record = e.record;
			var pkId = record.pkId;
			_OpenWindow({
				onload:function(){
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
					
				},
				url : "${ctxPath}/inst/mail/mail/get.do?pkId=" + pkId,
				width : 1000,
				height : 800,
				title : "邮件内容",
				ondestroy : function(action) {
				}
			});
		}

	     //图标查看邮件
		function getRow1(pkId) {
			_OpenWindow({
				onload:function(){
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
					
				},
				url : "${ctxPath}/inst/mail/mail/get.do?pkId=" + pkId,
				width : 1000,
				height : 800,
				title : "邮件内容",
				ondestroy : function(action) {
						grid.load();
				}
			});
		}
		
		/*单封邮件移动到垃圾箱*/
		function rowToDel(pkId) {
			if (!confirm("确定将选中的邮件已至垃圾箱？（可恢复）")) return;
			_SubmitJson({
				url : "${ctxPath}/inst/mail/mail/moveToDelFolder.do",
				data:{
					tenantId:tenantId,
					ids:pkId
				},
				method:'POST',
				success:function(){
					grid.load();
				}
			});
		}
		
		/*多封邮件移动到垃圾箱*/
		function todelfolder(){
	        var rows = grid.getSelecteds();
	        if (rows.length <= 0) {
	        	alert("请选中一条记录");
	        	return;
	        }
	        //行允许删除
	        if(rowRemoveAllow && !rowRemoveAllow()){
	        	return;
	        }
	        
	        if (!confirm("确定将选中的邮件已至垃圾箱？（可恢复）")) return;
	        
	        var ids = [];
	        for (var i = 0, l = rows.length; i < l; i++) {
	            var r = rows[i];
	            ids.push(r.pkId);
	        }

	        _SubmitJson({
	        	url:__rootPath+"/inst/mail/mail/moveToDelFolder.do",
	        	method:'POST',
	        	data:{ids: ids.join(',')},
	        	 success: function(text) {
	                grid.load();
	            }
	        });
		}
		
		
	     

	        //var tree = mini.get("leftTree");

	        var moveWindow = mini.get("moveWindow");
	        var moveTree = mini.get("moveTree");
	        function okWindow() {            
	           // var moveNode = tree.getSelected();
	           var rows = grid.getSelecteds();
	            var targetNode = moveTree.getSelectedNode();
    	        var ids = [];
    	        for (var i = 0, l = rows.length; i < l; i++) {
    	            var r = rows[i];
    	            ids.push(r.pkId);
    	        }
    	        _SubmitJson({
    	        	url:__rootPath+"/inst/mail/mail/moveMail.do",
    	        	method:'POST',
    	        	data:{
    	        		ids: ids.join(','),
    	        		to:targetNode.folderId	
    	        	},
    	        	 success: function(text) {
    	                grid.load();
    	            }
    	        });
	                moveWindow.hide();
	        }
	        function hideWindow() {
	            var moveWindow = mini.get("moveWindow");
	            moveWindow.hide();
	        }

	        function onMoveNode(e) {	
		        var rows = grid.getSelecteds();
		        if (rows.length <= 0) {
		        	alert("请选中一条记录");
		        	return;
		        }
		        //行允许删除
		        if(rowRemoveAllow && !rowRemoveAllow()){
		        	return;
		        }
	            if (configId) {
	                moveWindow.show();
	            } 
	        }
	        
	        
	        /*设置树图标*/
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
	        
	        /*根据条件过滤邮件*/
	        function onSearch(){
	        	var formJson=_GetFormParams("searchForm");
		        	grid.setUrl("${ctxPath}/inst/mail/mail/getMailByFolderId.do?folderId=${param['folderId']}&filter="+mini.encode(formJson));
		        	grid.load();

	        }
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>

</body>
</html>