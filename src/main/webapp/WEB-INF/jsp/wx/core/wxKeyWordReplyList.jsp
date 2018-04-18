<%-- 
    Document   : [公众号关键字回复]列表页
    Created on : 2017-08-30 11:39:20
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[公众号关键字回复]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	  <!-- <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create" plain="true" onclick="addReply()">增加</a>
                     <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
                 </td>
             </tr>
              <tr>
                  <td style="white-space:nowrap;padding:5px;" class="search-form" >
                     <ul>
						<li><span>关键字:</span><input class="mini-textbox" name="Q_KEY_WORD__S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div>  -->
     
     <div class="titleBar mini-toolbar">
		<ul>
			<li>
				<a class="mini-button" iconCls="icon-create" plain="true" onclick="addReply()">增加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox">
			<form id="searchForm" class="text-distance" style="display: none;">						
				<ul>
					<li>
						<span>关键字：</span><input class="mini-textbox" name="Q_KEY_WORD__S_LK">
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
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/wx/core/wxKeyWordReply/myList.do?pubId=${pubId}" idField="id"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="keyWord"  sortField="KEY_WORD_"  width="120" headerAlign="center" allowSort="true">关键字</div>
				<div field="replyType"  sortField="REPLY_TYPE_"  width="120" headerAlign="center" allowSort="true">回复方式</div>
				<!-- <div field="replyContent"  sortField="REPLY_CONTENT_"  width="120" headerAlign="center" allowSort="true">回复内容</div> -->
			</div>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var grid=mini.get("datagrid1");
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
					+'<span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>'
					+'<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
		}
		
		function addReply(){
			_OpenWindow({
				url : '${ctxPath}/wx/core/wxKeyWordReply/edit.do?pubId='+${pubId},
				title : "菜单配置",
				width : 800,
				height : 400,
				max:true,
				showModel:false,
				onload : function() {
				},	
				ondestroy : function(action) {
					 if (action == 'ok'){
						 var iframe = this.getIFrameEl().contentWindow;
						 grid.reload();							 
				               }
				}

			});
		}
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.wx.core.entity.WxKeyWordReply" winHeight="450"
		winWidth="700" entityTitle="公众号关键字回复" baseUrl="wx/core/wxKeyWordReply" />
</body>
</html>