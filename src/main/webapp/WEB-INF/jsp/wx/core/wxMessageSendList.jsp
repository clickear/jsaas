<%-- 
    Document   : [WX_MESSAGE_SEND]列表页
    Created on : 2017-07-17 17:58:08
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[WX_MESSAGE_SEND]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <!-- <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create" plain="true" onclick="addMsg()">发送消息</a>
                     <a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
                     <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
                 </td>
             </tr>
              <tr>
                  <td style="white-space:nowrap;padding:5px;" class="search-form" >
                     <ul>
						<li><span>消息类型:</span><input class="mini-textbox" name="Q_MSG_TYPE__S_LK"></li>
						<li><span>发送类型:</span><input class="mini-textbox" name="Q_SEND_TYPE__S_LK"></li>
						<li><span>发送状态:</span><input class="mini-textbox" name="Q_SEND_STATE__S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div> -->
     
     
     <div class="titleBar mini-toolbar" >
     	<ul>
     		<li>
     			<a class="mini-button" iconCls="icon-create" plain="true" onclick="addMsg()">发送消息</a>
     		</li>
     		<li>
     			<a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
     		</li>
     		<li class="clearfix"></li>
     	</ul>
		<div class="searchBox">
			<form id="searchForm" class="search-form" style="display: none;">						
				<ul>
					<li>
						<span>消息类型:</span><input class="mini-textbox" name="Q_MSG_TYPE__S_LK">
					</li>
					<li>
						<span>发送类型:</span><input class="mini-textbox" name="Q_SEND_TYPE__S_LK">
					</li>
					<li>
						<span>发送状态:</span><input class="mini-textbox" name="Q_SEND_STATE__S_LK">
					</li>
					<li class="searchBtnBox">
						<a class="mini-button _search" onclick="searchFrm()" >搜索</a>
						<a class="mini-button _reset" onclick="clearForm()" >清空</a>
					</li>
					<li class="clearfix"></li>
				</ul>
			</form>	
			<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
				<i class="icon-sc-lower"></i>
			</span>
		</div>
     </div>
     
     
     
	
	<div class="mini-fit" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-datagrid" 
			style="width: 100%; height: 100%;" 
			allowResize="false"
			url="${ctxPath}/wx/core/wxMessageSend/listAll.do?pkId=${pkId}" 
			idField="ID"
			multiSelect="true" 
			showColumnsMenu="true" 
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20" 
			allowAlternating="true" 
			pagerButtons="#pagerButtons"
		>
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="msgType"  sortField="MSG_TYPE_"  width="120" headerAlign="center" allowSort="true" renderer="onMsgTypeRenderer" >消息类型</div>
				<div field="sendType"  sortField="SEND_TYPE_"  width="120" headerAlign="center" allowSort="true" renderer="onSendTypeRenderer" >发送类型</div>
				<div field="sendState"  sortField="SEND_STATE_"  width="120" headerAlign="center" allowSort="true" renderer="onSendStateRenderer">发送状态</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
					+'<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
		}
		function onMsgTypeRenderer(e){
			var record=e.record;
			var msgType=record.msgType;
			if("article"==msgType||"multiArticle"==msgType){
				return "图文消息"
			}else if(msgType="thumb"){
				return "缩略图消息";
			}else if(msgType="video"){
				return "视频消息";
			}else if(msgType="voice"){
				return "语音消息";
			}else if(msgType="image"){
				return "图像消息";
			}
		}
		function onSendTypeRenderer(e){
			var record=e.record;
			var sendType=record.sendType;
			if("openId"==sendType){
				return "指定接收者";
			}else if("tag"==sendType){
				return "指定标签";
			}else if("everyone"==sendType){
				return "所有人";
			}
		}
		
		function onSendStateRenderer(e){
			var record=e.record;
			var sendState=record.sendState;
			if(sendState=="0"){
			return "已发送";				
			}
		}
		
		function addMsg(){
			_OpenWindow({
				url : '${ctxPath}/wx/core/wxMessageSend/edit.do?pubId=${pkId}',
				title : "发送消息",
				width : 800,
				height : 400,
				onload : function() {
				},
				ondestroy : function(action) {
					 if (action == 'ok'){
						 var iframe = this.getIFrameEl().contentWindow;
						 /* var nodereceiverValues=iframe.getReceiverValue();
						 var nodereceiverValues=iframe.getReceiverText();
						 receiver.setValue(receiverValue);
						 receiver.setText(receiverText); */
						 CloseWindow('ok');
				               }
				}

			});	
		}
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.wx.core.entity.WxMessageSend" winHeight="450"
		winWidth="700" entityTitle="WX_MESSAGE_SEND" baseUrl="wx/core/wxMessageSend" />
</body>
</html>