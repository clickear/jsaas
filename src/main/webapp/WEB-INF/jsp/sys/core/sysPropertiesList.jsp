<%-- 
    Document   : [系统参数]列表页
    Created on : 2017-06-21 11:22:36
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[系统参数]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	  <!-- <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create" plain="true" onclick="add()">增加</a>
                     <a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
                     <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
                 </td>
             </tr>
              <tr>
                  <td class="search-form" >
                     <ul>
						<li><span>名称:</span><input class="mini-textbox" name="Q_NAME__S_LK"></li>
						<li><span>别名:</span><input class="mini-textbox" name="Q_ALIAS__S_LK"></li>
						<li><span>分类:</span><input class="mini-textbox" name="Q_CATEGORY__S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div> --> 
     
      <div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-create" plain="true" onclick="add()">增加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
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
						<span>名称：</span><input class="mini-textbox" name="Q_NAME__S_LK">
					</li>
					<li>
						<span>别名：</span><input class="mini-textbox" name="Q_ALIAS__S_LK">
					</li>
					<li>
						<span>分类：</span><input class="mini-textbox" name="Q_CATEGORY__S_LK">
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
		<div 
			id="datagrid1" 
			class="mini-datagrid" 
			style="width: 100%; height: 100%;" 
			allowResize="false"
			url="${ctxPath}/sys/core/sysProperties/listAll.do" 
			idField="proId" 
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
				<div field="name"  sortField="NAME_"  width="120" headerAlign="center" allowSort="true">名称</div>
				<div field="alias"  sortField="ALIAS_"  width="120" headerAlign="center" allowSort="true">别名</div>
				<div field="global"  sortField="GLOBAL_"  width="120" renderer="onRenderer" headerAlign="center" allowSort="true">是否全局</div>
				<div field="encrypt"  sortField="ENCRYPT_"  width="120"  headerAlign="center" allowSort="true" renderer="onRenderer" >是否加密存储</div>
				<div field="value"  sortField="VALUE_"  width="120" headerAlign="center" renderer="onValRenderer"  allowSort="true">参数值</div>
				<div field="category"  sortField="CATEGORY_"  width="120" headerAlign="center" allowSort="true">分类</div>
				<div field="description"  sortField="DESCRIPTION_"  width="120" headerAlign="center" allowSort="true">描述</div>
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
		
		function onRenderer(e) {
            var record = e.record;
            var field=e.field;
            var val = record[field];
            
            var arr = [ {'key' : 'YES', 'value' : '是','css' : 'green'}, 
			            {'key' : 'NO','value' : '否','css' : 'red'} ];
			
			return $.formatItemValue(arr,val);
        }
		
		function onValRenderer(e){
			var record = e.record;
			var v=record["encrypt"]
			if(v=='YES'){
				return "已加密";
			}
			else{
				return e.value;
			}
		}
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.core.entity.SysProperties" winHeight="450"
		winWidth="700" entityTitle="系统参数" baseUrl="sys/core/sysProperties" />
</body>
</html>