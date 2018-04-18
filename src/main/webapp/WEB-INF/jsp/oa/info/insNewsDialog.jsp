<%-- 
    Document   : 新闻公告列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新闻公告列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>

</head>
<body>

	<div class="mini-toolbar" style="border-bottom: 0; padding: 0px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;"><a class="mini-button" iconCls="icon-select" plain="true" onclick="CloseWindow('ok');">选择</a> <a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow('cancel');">关闭</a></td>
			</tr>
			<tr>
				<td style="white-space: nowrap; border-top: 1px solid #909aa6; padding: 5px;">
					<form id="searchForm">
						标题 <input class="mini-textbox" id="subject" name="Q_subject_S_LK" emptyText="请输入标题" /> 
						关键字 <input class="mini-textbox" id="keywords" name="Q_keywords_S_LK" emptyText="请输入关键字" /> 
						作者 <input class="mini-textbox" id="author" name="Q_author_S_LK" emptyText="请输入作者" /> 
						<a class="mini-button" iconCls="icon-search" onclick="onSearch">查询</a> 
						<a class="mini-button" iconCls="icon-cancel" onclick="onClear">清空</a>
					</form>
				</td>
			</tr>
		</table>
	</div>

	<div class="mini-fit" style="height: 100px;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/oa/info/insNews/getValids.do" idField="newId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="subject" width="150" headerAlign="center" allowSort="true">标题</div>
				<div field="keywords" width="100" headerAlign="center" allowSort="true">关键字</div>
				<div field="readTimes" width="80" headerAlign="center" allowSort="true">阅读次数</div>
				<div field="author" width="80" headerAlign="center" allowSort="true">作者</div>
				<div field="status" width="80" headerAlign="center" allowSort="true">状态</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
        	//编辑
	        function onActionRenderer(e) {
	            var record = e.record;
	            var uid = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + uid + '\')"></span>';
	            return s;
	        }
        	
        	function getNewsIds(){
        		var nIds=[];
        		var rows=grid.getSelecteds();
        		for(var i=0;i<rows.length;i++){
        			nIds.push(rows[i].newId);
        		}
        		return nIds.join(',');
        	}
        	//编辑搜索功能
        	function onSearch(){
        		var formData=$("#searchForm").serializeArray();
				var filter=mini.encode(formData);
				grid.setUrl(__rootPath+'/oa/info/insNews/getValids.do?filter='+filter);
				grid.load();
        	}
        	function onClear(){
        		$("#searchForm")[0].reset();
        	}
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.info.entity.InsNews" winHeight="550" winWidth="780" entityTitle="新闻公告" baseUrl="oa/info/insNews" />
</body>
</html>