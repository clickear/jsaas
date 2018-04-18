<%-- 
    Document   : [文章]列表页
    Created on : 2017-09-29 14:39:26
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[文章]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <div class="mini-toolbar" >
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
                  <td style="white-space:nowrap;padding:5px;" class="search-form" >
                     <ul>
						<li><span>所属项目ID:</span><input class="mini-textbox" name="Q_BELONG_PRO_ID__S_LK"></li>
						<li><span>标题:</span><input class="mini-textbox" name="Q_TITLE__S_LK"></li>
						<li><span>AUTHOR_:</span><input class="mini-textbox" name="Q_AUTHOR__S_LK"></li>
						<li><span>概要:</span><input class="mini-textbox" name="Q_OUT_LINE__S_LK"></li>
						<li><span>父ID:</span><input class="mini-textbox" name="Q_PARENT_ID__S_LK"></li>
						<li><span>序号:</span><input class="mini-textbox" name="Q_SN__S_LK"></li>
						<li><span>内容:</span><input class="mini-textbox" name="Q_CONTENT__S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div>
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/oa/article/proArticle/listData.do" idField="id"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="belongProId"  sortField="BELONG_PRO_ID_"  width="120" headerAlign="center" allowSort="true">所属项目ID</div>
				<div field="title"  sortField="TITLE_"  width="120" headerAlign="center" allowSort="true">标题</div>
				<div field="author"  sortField="AUTHOR_"  width="120" headerAlign="center" allowSort="true">AUTHOR_</div>
				<div field="outLine"  sortField="OUT_LINE_"  width="120" headerAlign="center" allowSort="true">概要</div>
				<div field="parentId"  sortField="PARENT_ID_"  width="120" headerAlign="center" allowSort="true">父ID</div>
				<div field="sn"  sortField="SN_"  width="120" headerAlign="center" allowSort="true">序号</div>
				<div field="content"  sortField="CONTENT_"  width="120" headerAlign="center" allowSort="true">内容</div>
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
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.article.entity.ProArticle" winHeight="450"
		winWidth="700" entityTitle="文章" baseUrl="oa/article/proArticle" />
</body>
</html>