<%-- 
    Document   : [微信应用]列表页
    Created on : 2017-06-04 12:27:36
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[微信应用]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <!-- <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create" onclick="add()">增加</a>
                     <a class="mini-button" iconCls="icon-edit"  onclick="edit()">编辑</a>
                     <a class="mini-button" iconCls="icon-remove"  onclick="remove()">删除</a>
                     <a class="mini-button" iconCls="icon-search"  onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel"  onclick="clearForm()">清空查询</a>
                 </td>
             </tr>
              <tr>
                  <td style="white-space:nowrap;padding:5px;" class="search-form" >
                     <ul>
						<li><span>名称:</span><input class="mini-textbox" name="Q_NAME__S_LK"></li>
						<li><span>描述:</span><input class="mini-textbox" name="Q_DESCRIPTION__S_LK"></li>
						<li><span>应用ID:</span><input class="mini-textbox" name="Q_AGENT_ID__S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div> -->
      <div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-create" onclick="add()">增加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-edit"  onclick="edit()">编辑</a>
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
						<span>名称：</span><input class="mini-textbox" name="Q_NAME__S_LK">
					</li>
					<li>
						<span>描述：</span><input class="mini-textbox" name="Q_DESCRIPTION__S_LK">
					</li>
					<li class="searchBtnBox">
						<a class="mini-button _search" onclick="searchFrm()" >搜索</a>
						<a class="mini-button _reset" onclick="clearForm()">清空</a>
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
			url="${ctxPath}/wx/ent/wxEntAgent/listData.do" idField="id"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="id"  sortField="ID_"  width="120" headerAlign="center" allowSort="true">主键</div>
				<div field="name"  sortField="NAME_"  width="120" headerAlign="center" allowSort="true">名称</div>
				<div field="description"  sortField="DESCRIPTION_"  width="120" headerAlign="center" allowSort="true">备注</div>
				<div field="domain"  sortField="DOMAIN_"  width="120" headerAlign="center" allowSort="true">信任域名</div>
				<div field="homeUrl"  sortField="HOME_URL_"  width="120" headerAlign="center" allowSort="true">主页地址</div>
				<div field="corpId"  sortField="CORP_ID_"  width="120" headerAlign="center" allowSort="true">企业ID</div>
				<div field="agentId"  sortField="AGENT_ID_"  width="120" headerAlign="center" allowSort="true">应用ID</div>
				<div field="defaultAgent"  renderer="onDefaultRenderer" sortField="DEFAULT_AGENT_"  width="120" headerAlign="center" allowSort="true">是否默认</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
					+'<span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>'
					+'<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
		}
		
		function onDefaultRenderer(e) {
            var record = e.record;
            var status = record.defaultAgent;
            
            var arr = [ {'key' : 1, 'value' : '是','css' : 'green'}, 
			            {'key' : 0,'value' : '否','css' : 'orange'} ];
			
			return $.formatItemValue(arr,status);
            
        }
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.wx.ent.entity.WxEntAgent" winHeight="450"
		winWidth="700" entityTitle="微信应用" baseUrl="wx/ent/wxEntAgent" />
</body>
</html>