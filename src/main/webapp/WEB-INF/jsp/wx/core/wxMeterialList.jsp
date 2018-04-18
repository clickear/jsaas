<%-- 
    Document   : [微信素材]列表页
    Created on : 2017-07-11 16:03:13
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[微信素材]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="searchBox titleBar mini-toolbar" >
		<ul>
	   		<li>
	   			<a class="mini-button" iconCls="icon-create" plain="true" onclick="createMeterial()">新增素材</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除素材</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<form id="searchForm" class="search-form" style="display: none;">						
			<ul>
				<li>
					<span>素材名:</span><input class="mini-textbox" name="Q_NAME__S_LK">
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
	<div class="mini-fit" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-datagrid" 
			style="width: 100%; height: 100%;" 
			allowResize="false"
			url="${ctxPath}/wx/core/wxMeterial/listAll.do?pubId=${pubId}" 
			idField="id"
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
				<div field="termType"  sortField="TERM_TYPE_"  width="120" headerAlign="center" allowSort="true" renderer="onTermTypeRenderer">期限类型</div>
				<div field="mediaType"  sortField="MEDIA_TYPE_"  width="120" headerAlign="center" allowSort="true" renderer="onMediaTypeRenderer">素材类型</div>
				<div field="name"  sortField="NAME_"  width="120" headerAlign="center" allowSort="true">素材名</div>
				<div field="mediaId"  sortField="MEDIA_ID_"  width="120" headerAlign="center" allowSort="true">微信后台指定ID</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>';
			if(record.mediaType!="multiArticle")
			{
				s+='<span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>';
			}
			if(validateMsgGrant(record))
			{
				s+='<span class="icon-sendedOut" title="发送消息" onclick="sendMsg(\'' + record.mediaId + '\')"></span>';
			}
			s+='<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
		}
		/*验证消息是否可以直接发送*/
		function validateMsgGrant(record){
			if(record.termType=="0"){
				return false
			}
			if(record.mediaType=="thumb"){
				return false;
			}
			return true;
		}
		
		function createMeterial(){
			_OpenWindow({
	    		 url: "${ctxPath}/wx/core/wxMeterial/edit.do?pubId=${pubId}",
	            title: "创建素材",
	            width: 900, height: 700,
	            ondestroy: function(action) {
	                if (action == 'ok') {
	                    grid.reload();
	                    mini.showTips({
				            content: "<b>成功</b> <br/>素材保存成功",
				            state: 'success',
				            x: 'center',
				            y: 'center',
				            timeout: 3000
				        });
	                }
	            }
	    	});
		}
		
		function sendMsg(mediaId){
			_OpenWindow({
	    		url: "${ctxPath}/wx/core/wxMessageSend/toSomeone.do?pubId=${pubId}",
	            title: "发送消息",
	            width: 700, height: 200,
	            ondestroy: function(action) {
	                if (action == 'ok') {
	                	var iframe = this.getIFrameEl().contentWindow;
	                	var receiver=iframe.getReceiver();
	                	$.ajax({
	        	        	url:__rootPath+"/wx/core/wxMessageSend/sendAtion.do",
	        	        	type:'POST',
	        	        	data:{ids:receiver,pubId:'${pubId}',mediaId:mediaId},
	        	        	 success: function(text) {
	        	        		 if(text.success){
	        	        			 tree.reload();
	        	        		 }
	        	            }
	        	        });
	                	
	                }
	            }
	    	});
		}
		
		function onMediaTypeRenderer(e){
			var record = e.record;
			var  mediaType=record.mediaType;
			if(mediaType=="multiArticle"){
				return "多素材";
			}else if(mediaType=="article"){
				return "图文素材";
			}else if(mediaType=="thumb"){
				return "缩略图";
			}else if(mediaType=="video"){
				return "视频";
			}else if(mediaType=="voice"){
				return "音频";
			}else if(mediaType=="image"){
				return "图像素材";
			}
		}
		
		
		function onTermTypeRenderer(e){
			var record = e.record;
			var  termType=record.termType;
			if(termType=="1"){
				return "永久";
			}else if(termType=="0"){
				return "临时";
			}
		}
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.wx.core.entity.WxMeterial" winHeight="450"
		winWidth="700" entityTitle="微信素材" baseUrl="wx/core/wxMeterial" />
</body>
</html>