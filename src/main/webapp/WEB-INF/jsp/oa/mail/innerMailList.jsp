<%-- 
    Document   : 内部邮件列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>内部邮件列表管理</title>
<%@include file="/commons/list.jsp" %>
<style type="text/css">
	.icon-mailDown:before{
		font-size:22px;
		color:#707070;
	}
	
	.icon-Mailbox:before{
		font-size:22px;
		color:#00e500;
	}
</style>
</head>
<body>
	<div class="titleBar mini-toolbar" >
		<ul>
			<li>
				<a class="mini-button" iconCls="icon-writemail"  onclick="writeMail">写邮件</a>
			</li>
			<li>
				<a id="toDel" class="mini-button" iconCls="icon-remove"  onclick="toDel">刪除</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-trash"  onclick="del">彻底删除</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-transmit"  onclick="transmit">转发</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-transfer"  onclick="moveMailToTargetFolder">移动到</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-refresh"  onclick="refresh">刷新</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox">
			<form id="searchForm" class="search-form" style="display: none;">						
				<ul>
					<li>
						<span class="text">主题：</span>
						<input name="Q_subject_S_LK" class="mini-textbox" /> 
					</li>
					<li>
						<span class="text">发件人：</span>
						<input name="Q_sender_S_LK" class="mini-textbox" />
					</li>
					<li>
						<span class="text">时间：</span>
						<input id="sDate" name="Q_senderTime1_D_GE" class="mini-datepicker" value="" format="yyyy-MM-dd" />
					</li>
					<li>
						<span class="text">到</span>
						<input id="eDate" name="Q_senderTime2_D_LE" class="mini-datepicker" value="" format="yyyy-MM-dd" />
					</li>
					<li class="searchBtnBox">
						<a class="mini-button _search" onclick="searchForm(this)" >搜索</a>
						<a class="mini-button _reset" onclick="onClearList(this)" >清空</a>
					</li>
					<li class="clearfix"></li>
				</ul>
			</form>	
			<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
				<i class="icon-sc-lower"></i>
			</span>
		</div>
     </div>
	<div class="mini-fit rx-grid-fit form-outer5" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-datagrid" 
			style="width: 100%; height: 100%;" 
			allowResize="false" 
			url="${ctxPath}/oa/mail/innerMail/getInnerMailByFolderId.do?folderId=${param['folderId']}" 
			idField="mailId" 
			multiSelect="true" 
			showColumnsMenu="true" 
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20" 
			allowAlternating="true"
			onrowdblclick="dbClickCheck"
		>
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div type="indexcolumn" width="20">序号</div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="urge" width="20" headerAlign="center" allowSort="true" align="center">紧急状态</div>
				<div field="isRead" width="20" headerAlign="center" align="center">阅读状态</div>
				<div field="isReply" width="20" headerAlign="center" align="center">回复状态</div>
				<div field="subject" width="150" headerAlign="center" allowSort="true">邮件标题</div>
				<div field="sender" width="80" headerAlign="center" allowSort="true">发送人</div>
				<div field="senderTime" width="80" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm">发送时间</div>
				
			</div>
		</div>
	</div>

	<!-- 移动邮件窗口 -->
	<div 
		id="moveWindow" 
		title="选择目标目录" 
		class="mini-window" 
		style="width: 300px; height: 250px;" 
		showModal="true" 
		showFooter="true" 
		allowResize="true"
	>
		<ul 
			id="moveTree" 
			class="mini-tree" 
			style="width: 100%;" 
			url="${ctxPath}/oa/mail/mailFolder/getInnerMailFolderOutOfRoot.do" 
			showTreeIcon="true" 
			textField="name" 
			idField="folderId" 
			parentField="parentId" 
			resultAsTree="false" 
			showArrow="false" 
			showTreeLines="true" 
			ondrawnode="draw"
		>
		</ul>
		<div property="footer" style="padding: 5px;">
			<table style="width: 100%">
				<tr>
					<td 
						style="width: 120px; 
						text-align: right;"
					>
						<input 
							type="button" 
							value="确定" 
							onclick="okWindow()" 
						/> 
						<input 
							type="button" 
							value="取消" 
							onclick="hideWindow()" 
						/>
					</td>
				</tr>
			</table>
		</div>
	</div>

	<script type="text/javascript">
        mini.parse();
        var grid=mini.get("datagrid1");
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	           // var pkId = record.pkId; 
	            var uId=record._uid;
	            var s = '<span class="icon-detail" title="明细" onclick="check('+uId+')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="toDel()"></span>';
	            return s;
	        }
        	
        	$(function(){
        		var btnToDel=mini.get("toDel");
        		var isShowDel="${param['isShowDel']}";
        		if(isShowDel=="NO")   //不显示彻底删除按钮
        			btnToDel.setVisible(false);
         		var folderId="${param['folderId']}";
        		var tree=top['mailbox'].tree;
				var node=tree.getNode(folderId);
				var checkNode=null;
				 tree.findNodes(function(f_node){     //查找发件箱节点
					    if(f_node.type =='SENDER-FOLDER'){
					    	checkNode=f_node;
					    	return ;
					    }
					});
				if(node.type=='SENDER-FOLDER'||node.path.indexOf(checkNode.folderId)>-1){  //如果文件夹目录为发件箱目录及其以下的目录
	        		var move=mini.get("moveTree");
					move.setUrl("${ctxPath}/oa/mail/mailFolder/getInnerSenderFolder.do");	   //获取发件箱目录及其子目录
					move.load();	
				}
        	});
        	
        	
        	/*搜索按钮事件处理*/
        	function search(e){
	        	var sd=mini.get("sDate").getValue();
	        	var ed=mini.get("eDate").getValue();
	            var sDate = new Date(sd);
	            var eDate = new Date(ed);
	            if(sDate > eDate){  //结束日期小于开始日期
	            	alert("结束日期不能小于开始日期");
	            	return;
	            }else{
        			 var button = e.sender;
        			 var el=button.getEl();
        			 var form=$(el).parents('form');
        			 
        			 if(form!=null){
        			 	var formData=form.serializeArray();
        			 	var data={};
        			 	//加到查询过滤器中
        				data.filter=mini.encode(formData);
        				data.pageIndex=grid.getPageIndex();  //页码
        				data.pageSize=grid.getPageSize();    //每页的记录数
        			    data.sortField=grid.getSortField();   //排序字段
        			    data.sortOrder=grid.getSortOrder();    //排序顺序
        				grid.load(data);
        			 }
	            }
        	
        	}
        
        	/*绘制datagrid*/
		    grid.on("drawcell", function (e) {
			        field = e.field,
			        value = e.value;
		            var s;
					if(field=='isRead'){  //设置阅读标识图标
						if(value=="NO"){
							s="<i  title='新邮件' class='iconfont icon-Mailbox'></i>";
						}
						else{
							s="<i  title='已读' class='iconfont icon-mailDown'></i>";
						}
						e.cellHtml= s;
					}
					if(field=='isReply'){  //设置回复标识图标
						if(value=="NO"){
							s="<i title='未回复' class='iconfont icon-readedMail'></i>";
						}
						else{
							s="<i title='已回复' class='iconfont icon-readedMail'></i>";
						}
						e.cellHtml= s;
					}
					if(field=='urge'){   //设置重要程度图标
						if(value=="1"){
							s="<i title='一般' class='iconfont icon-normal'></i>";
						}else if(value=="2"){
							s="<i title='重要' class='iconfont icon-important'></i>";
						}
						else{
							s="<i title='非常重要' class='iconfont icon-veryImportant'></i>";
						}
						e.cellHtml= s;
					}
		        });
        	
		    /*写内部邮件*/
		    function writeMail(){
				_OpenWindow({
					url : __rootPath + "/oa/mail/innerMail/edit.do?",
					title : "写邮件",
					height : 600,
					width : 1024,
					max:true
				});
		    }
		    
		    /*将内部邮件移动到垃圾箱*/
		    function toDel(){
		    	
			        var rows = grid.getSelecteds();
			        if (rows.length <= 0) {
			        	alert("请选中至少一封邮件");
			        	return;
			        }
			        //行允许删除
			        if(rowRemoveAllow && !rowRemoveAllow()){
			        	return;
			        }
			        
			        if (!confirm("确定将选中的邮件已至垃圾箱？")) return;
			        
			        var ids = [];
			        for (var i = 0, l = rows.length; i < l; i++) {
			            var r = rows[i];
			            ids.push(r.pkId);
			        }
			        /*将内部邮件移动到垃圾箱*/
			        _SubmitJson({
			        	url:__rootPath+"/oa/mail/innerMail/moveInnerMailToFolder.do",
			        	method:'POST',
			        	data:{
			        		ids: ids.join(','),
			        		target:"del",
			        		nowFolderId:top['mailbox'].tree.getSelectedNode().folderId
			        		},
			        	 success: function(text) {
			                grid.load();
			            }
			        });
		    }
		    
		    var moveWindow = mini.get("moveWindow");
	        var moveTree = mini.get("moveTree");
	        function okWindow() {            
	           var rows = grid.getSelecteds();
	            var targetNode = moveTree.getSelectedNode();   //目标文件夹节点
    	        var ids = [];
    	        for (var i = 0, l = rows.length; i < l; i++) {
    	            var r = rows[i];
    	            ids.push(r.pkId);
    	        }
    	        /*移动内部邮件*/
    	        _SubmitJson({
    	        	url:__rootPath+"/oa/mail/innerMail/moveInnerMailToFolder.do",
    	        	method:'POST',
    	        	data:{
    	        		ids: ids.join(','),
    	        		to:targetNode.folderId,
    	        		target:"all",
    	        	    nowFolderId:top['mailbox'].tree.getSelectedNode().folderId
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

	        function moveMailToTargetFolder(e) {	
		        var rows = grid.getSelecteds();
		        if (rows.length <= 0) {
		        	alert("请选中至少一封邮件");
		        	return;
		        }
	            moveWindow.show();
	        }
	        
	        /*刷新datagrid*/
			function refresh(){
				var pageIndex=grid.getPageIndex();
				var pageSize=grid.getPageSize();
				grid.load({pageIndex:pageIndex,pageSize: pageSize});
			}
			
	        /*绘制移动邮件窗口树的节点图标*/
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
	        
	        /*转发内部邮件*/
	        function transmit(){
		        var rows = grid.getSelecteds();
		        if (rows.length <= 0) {
		        	alert("请选中一封邮件");
		        	return;
		        }
		        if (rows.length > 1) {
		        	alert("不能转发2封以上的邮件！");
		        	return;
		        }
		        var ids = [];
		        for (var i = 0, l = rows.length; i < l; i++) {
		            var r = rows[i];
		            ids.push(r.pkId);
		        }
				_OpenWindow({
					url : __rootPath + "/oa/mail/innerMail/edit.do?pkId="+ids[0]+"&operation=transmit",
					title : "转发邮件",
					height : 820,
					width : 800
				});
	        }
	        
	        /*彻底删除内部邮件*/
	        function del(){
		        var rows = grid.getSelecteds();
		        if (rows.length <= 0) {
		        	alert("请选中至少一封邮件");
		        	return;
		        }
		        //行允许删除
		        if(rowRemoveAllow && !rowRemoveAllow()){
		        	return;
		        }
		        
		        if (!confirm("确定将选中的邮件删除（不可恢复）？")) return;
		        
		        var ids = [];
		        for (var i = 0, l = rows.length; i < l; i++) {
		            var r = rows[i];
		            ids.push(r.pkId);
		        }
		        _SubmitJson({
		        	url:__rootPath+"/oa/mail/mailBox/del.do",
		        	method:'POST',
		        	data:{
		        		ids: ids.join(','),
		        		folderId:top['mailbox'].tree.getSelectedNode().folderId
		        		},
		        	 success: function(text) {
		                grid.load();
		            }
		        });
	        }
	        
	        /*点击图标查看邮件内容*/
			function check(uId) {
				var row=grid.getRowByUID(uId);
				var pkId = row.pkId;
				var folderId="${param['folderId']}";
				if(row.isRead=='YES')
					folderId="";
				_OpenWindow({
					url : "${ctxPath}/oa/mail/innerMail/get.do?pkId=" + pkId+"&folderId="+folderId,
					max:"true",
					title : "邮件内容",
					ondestroy : function(action) {
							var pageIndex=grid.getPageIndex();
							var pageSize=grid.getPageSize();
							grid.load({pageIndex:pageIndex,pageSize: pageSize});
							var tree=top['mailbox'].tree;
							var receiveNode=null;
							 tree.findNodes(function(f_node){  //查找收件箱文件夹节点
								    if(f_node.type =='RECEIVE-FOLDER'){
								    	receiveNode=f_node;
								    	return ;
								    }
								});
							 /*获取收件箱未读邮件数量*/
							_SubmitJson({
										url : "${ctxPath}/oa/mail/innerMail/getgUnreadReceiveFolderInnerMail.do",
										data:{folderId:receiveNode.folderId},
										method:'POST',
										showMsg:false,
										success:function(result){
											var unReadNum=result.data;
											tree.setNodeText(receiveNode, "收件箱("+unReadNum+")");  //设置收件箱未读邮件数量
										}
									});
						}
				});
			}
			
	        /*双击行查看邮件内容*/
			function dbClickCheck(e){
	            var record = e.record;
		        var pkId = record.pkId; 
		    	var folderId="${param['folderId']}";
				if(record.isRead=='YES')
					folderId="";
				_OpenWindow({
					url : "${ctxPath}/oa/mail/innerMail/get.do?pkId=" + pkId+"&folderId="+folderId,
					width : 1000,
					height : 800,
					title : "邮件内容",
					ondestroy : function(action) {
							var pageIndex=grid.getPageIndex();
							var pageSize=grid.getPageSize();
							grid.load({pageIndex:pageIndex,pageSize: pageSize});
							var tree=top['mailbox'].tree;
							var receiveNode=null;
							 tree.findNodes(function(f_node){
								    if(f_node.type =='RECEIVE-FOLDER'){
								    	receiveNode=f_node;
								    	return ;
								    }
								});
							_SubmitJson({
										url : "${ctxPath}/oa/mail/innerMail/getgUnreadReceiveFolderInnerMail.do",
										data:{folderId:receiveNode.folderId},
										method:'POST',
										showMsg:false,
										success:function(result){
											var unReadNum=result.data;
											tree.setNodeText(receiveNode, "收件箱("+unReadNum+")");
										}
									});
						}
				});
			}
		    
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.mail.entity.InnerMail" winHeight="450" winWidth="700" entityTitle="内部邮件" baseUrl="oa/mail/innerMail" />
</body>
</html>