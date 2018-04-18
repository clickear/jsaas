<%-- 
    Document   : [公众号管理]列表页
    Created on : 2017-06-29 16:57:29
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[公众号管理]列表管理</title>
<%@include file="/commons/list.jsp"%>
<style>
	.mini-toolbar{
		background: transparent;
		border-color: transparent;
	}

	.titleBar{
		background: #fff;
	}
</style>
</head>
<body>
	 <div class="titleBar mini-toolbar" >
		<ul>
			<li>
				<a class="mini-button" iconCls="icon-create" onclick="add()">增加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-remove"  onclick="remove()">删除</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox">
			<form id="searchForm" class="search-form" style="display: none;">						
				<ul>
					<li>
						<span>微信号：</span><input class="mini-textbox" name="Q_WX_NO__S_LK">
					</li>
					<li>
						<span>类型：</span><input class="mini-textbox" name="Q_TYPE__S_LK">
					</li>
					<li>
						<span>名称：</span><input class="mini-textbox" name="Q_NAME__S_LK">
					</li>
					<li>
						<span>别名：</span><input class="mini-textbox" name="Q_ALIAS__S_LK">
					</li>
					<li>
						<div class="searchBtnBox">
							<a class="mini-button _search" onclick="searchFrm()">搜索</a>
							<a class="mini-button _reset" onclick="clearForm()">清空</a>
						</div>
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
			url="${ctxPath}/wx/core/wxPubApp/myList.do" 
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
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">相关操作</div>
				<div field="wxNo"  sortField="WX_NO_"  width="100" headerAlign="center" allowSort="true">微信号</div>
				<div field="type"  sortField="TYPE_"  width="50" headerAlign="center" allowSort="true" renderer="onTypeRenderer" >类型</div>
				<div field="authed"  sortField="AUTHED_"  width="50" headerAlign="center" allowSort="true">是否认证</div>
				<!-- <div field="TOKEN"  sortField="TOKEN"  width="120" headerAlign="center" allowSort="true">token</div> -->
				<div field="name"  sortField="NAME_"  width="100" headerAlign="center" allowSort="true">名称</div>
				<!-- <div field="alias"  sortField="ALIAS_"  width="100" headerAlign="center" allowSort="true">别名</div> -->
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
					+'<span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>';
				if(${isSuperAdmin}){
					s+='<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
				}
					
				if(record.appId!=null&&record.secret!=null){
				/* s+=	'<span class="icon-meeting" title="标签管理" onclick="appTag(\'' + pkId + '\')"></span>'; */
				s+=	'<span class="icon-abstain" title="粉丝管理" onclick="appFans(\'' + pkId + '\')"></span>';
				s+=	'<span class="icon-form-template" title="素材管理" onclick="appMeterial(\'' + pkId + '\')"></span>';
				s+=	'<span class="icon-readedMsg" title="消息管理" onclick="msgSend(\'' + pkId + '\')"></span>';
				s+=	'<span class="icon-suspend" title="网页授权管理" onclick="webGrant(\'' + pkId + '\')"></span>';
				s+=	'<span class="icon-File" title="微信卡券" onclick="ticket(\'' + pkId + '\')"></span>';
				/* s+=	'<span class="icon-rank" title="会员卡管理" onclick="memberCard(\'' + pkId + '\')"></span>'; */
				}
			return s;
		}
		
		function webGrant(pkId){
			top.showTabFromPage({
				title : '网页授权',
				tabId : "webGrant"+pkId,
				url : __rootPath + '/wx/core/wxWebGrant/list.do?pubId=' +pkId
			});
		}
		
		function onTypeRenderer(e){
			var record=e.record;
			var type=record.type;
			if(type=="SUBSCRIBE"){
				return "订阅号";
			}else if(type="PUBLIC"){
				return "公众号";
			}
		}
		function appTag(pkId){
			top.showTabFromPage({
				title : '标签管理',
				tabId : "tag"+pkId,
				url : __rootPath + '/wx/core/wxPubApp/tagList.do?pubId=' + pkId
			});
			
		}
		
		function appFans(pkId){
			top.showTabFromPage({
				title : '粉丝管理',
				tabId : "fans"+pkId,
				url : __rootPath + '/wx/core/wxSubscribe/list.do?pubId=' + pkId
			});
			
		}
		
		function appMeterial(pkId){
			top.showTabFromPage({
				title : '素材管理',
				tabId : "meterial"+pkId,
				url : __rootPath + '/wx/core/wxMeterial/list.do?pubId=' + pkId
			});
		}
		
		function msgSend(pkId){
			top.showTabFromPage({
				title : '消息管理',
				tabId : "msg"+pkId,
				url : __rootPath + '/wx/core/wxMessageSend/list.do?pkId=' + pkId
			});
		}
		
		function ticket(pkId){
			top.showTabFromPage({
				title : '卡券管理',
				tabId : "ticket"+pkId,
				url : __rootPath + '/wx/core/wxTicket/list.do?pubId=' + pkId
			});
		}
		
		function memberCard(pkId){
			top.showTabFromPage({
				title : '会员卡管理',
				tabId : "menberCard"+pkId,
				url : __rootPath + '/wx/core/wxTicket/memberCardList.do?pubId=' + pkId
			});
		}
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.wx.core.entity.WxPubApp" winHeight="450"
		winWidth="700" entityTitle="公众号管理" baseUrl="wx/core/wxPubApp" />
</body>
</html>