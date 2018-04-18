<%-- 
    Document   : [微信网页授权]列表页
    Created on : 2017-08-18 17:05:42
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[微信网页授权]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <!-- <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create" plain="true" onclick="addWebGrant()">增加</a>
                     <a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
                     <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
                 </td>
             </tr>
              <tr>
                  <td style="white-space:nowrap;padding:5px;" class="search-form" >
                     <ul>
						<li><span>公众号ID:</span><input class="mini-textbox" name="Q_PUB_ID__S_LK"></li>
						<li><span>链接:</span><input class="mini-textbox" name="Q_URL__S_LK"></li>
						<li><span>转换后的URL:</span><input class="mini-textbox" name="Q_TRANSFORM_URL__S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div> -->
     
     <div class="titleBar mini-toolbar" >
     	<ul>
     		<li>
     			<a class="mini-button" iconCls="icon-create" plain="true" onclick="addWebGrant()">增加</a>
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
						<span>链接：</span><input class="mini-textbox" name="Q_URL__S_LK">
					</li>
					<li>
						<span>转换后的URL：</span><input class="mini-textbox" name="Q_TRANSFORM_URL__S_LK">
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
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/wx/core/wxWebGrant/getPubList.do?pubId=${pubId}" idField="id"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<!-- <div field="pubId"  sortField="PUB_ID_"  width="120" headerAlign="center" allowSort="true">公众号ID</div> -->
				<div field="url"  sortField="URL_"  width="100" headerAlign="center" allowSort="true">链接</div>
				<div field="transformUrl"  sortField="TRANSFORM_URL_"  width="200" headerAlign="center" allowSort="true">转换后的URL</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
					+'<span class="icon-edit" title="编辑" onclick="editMyRow(\'' + pkId + '\',true)"></span>'
					+'<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
		}
		
		function addWebGrant(){
			_OpenWindow({
	    		url: "${ctxPath}/wx/core/wxWebGrant/edit.do?pubId=${pubId}",
	            title: "创建网页授权", width: 600, height: 250,
	            showMaxButton:false,
	            ondestroy: function(action) {
	            	if(action == 'ok' && typeof(addCallback)!='undefined'){
	            		var iframe = this.getIFrameEl().contentWindow;
	            	}else if (action == 'ok') {
	                    grid.reload();
	                }
	            }
	    	});
		}
		
		function editMyRow(pkId){
			_OpenWindow({
	    		url: "${ctxPath}/wx/core/wxWebGrant/edit.do?pkId="+pkId,
	            title: "编辑网页授权", width: 1000, height: 300,
	            showMaxButton:false,
	            ondestroy: function(action) {
	            	if(action == 'ok' && typeof(addCallback)!='undefined'){
	            		var iframe = this.getIFrameEl().contentWindow;
	            	}else if (action == 'ok') {
	                    grid.reload();
	                }
	            }
	    	});
		}
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.wx.core.entity.WxWebGrant" winHeight="450"
		winWidth="700" entityTitle="微信网页授权" baseUrl="wx/core/wxWebGrant" />
</body>
</html>