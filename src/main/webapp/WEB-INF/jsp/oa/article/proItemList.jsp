<%-- 
    Document   : [项目]列表页
    Created on : 2017-09-29 14:38:27
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[项目]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <!-- <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create"  onclick="add()">增加</a>
                     <a class="mini-button" iconCls="icon-edit"  onclick="edit()">编辑</a>
                     <a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
                     <a class="mini-button" iconCls="icon-search" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel"  onclick="clearForm()">清空查询</a>
                 </td>
             </tr>
              <tr>
                  <td style="white-space:nowrap;padding:5px;" class="search-form" >
                     <ul>
						<li><span>项目名:</span><input class="mini-textbox" name="Q_NAME__S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div> -->
     
     
     
     <div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-create"  onclick="add()">增加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-edit"  onclick="edit()">编辑</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox">
			<form id="searchForm" class="search-form" style="display: none;">						
				<ul>
					<li>
						<span>项目名:</span><input class="mini-textbox" name="Q_NAME__S_LK">
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
			url="${ctxPath}/oa/article/proItem/mylist.do" idField="ID"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="name"  sortField="NAME_"  width="120" headerAlign="center" allowSort="true">项目名</div>
				
				<div field="version"  sortField="VERSION_"  width="120" headerAlign="center" allowSort="true">版本</div>
				<div field="genSrc"  sortField="GEN_SRC_"  width="120" headerAlign="center" allowSort="true">文档生成路径</div>
				<div field="createTime"  sortField="CREATE_TIME_" dateformat="yyyy-MM-dd" width="120" headerAlign="center" allowSort="true">创建时间</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var uid=record._uid;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>';
					s+='<span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>';
					s+='<span class="icon-flow-design" title="撰写文档" onclick="writeRow(\'' + uid + '\')"></span>';
					s+='<span class="icon-move-in" title="下载文档" onclick="exportArticle(\'' + pkId + '\')"></span>';
					s+='<span class="icon-formAdd" title="下载PDF" onclick="downloadPDF(\'' + pkId + '\')"></span>'; 
					s+='<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
					
			return s;
		}
		
		function writeRow(uid){
			var row=grid.getRowByUID(uid);
			top['index'].showTabPage({
				title : '文档编写-'+ row.name,
				showType:'url',
				iconCls:'icon-doc',
				menuId : "proArticle"+row.pkId,
				url : '/oa/article/proArticle/view.do?pkId=' + row.pkId
			});
		}
		
		function downloadPDF(pkId){
			mini.prompt("不输入或者取消则不加密", "请添加PDF密码",
		            function (action, value) {
		                if (action == "ok") {
		                	window.location.href='${ctxPath}/oa/article/proArticle/parseHtml2Pdf.do?itemId='+pkId+"&password="+value;
		                } else {
		                	window.location.href='${ctxPath}/oa/article/proArticle/parseHtml2Pdf.do?itemId='+pkId;
		                }
		            }
		        );
			
		}
		
		function exportArticle(pkId){
			window.location.href='${ctxPath}/oa/article/proItem/exportArticle.do?pkId='+pkId;
		}
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.article.entity.ProItem" winHeight="450"
		winWidth="700" entityTitle="项目" baseUrl="oa/article/proItem" />
</body>
</html>