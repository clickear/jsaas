<%-- 
    Document   : 栏目列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>栏目列表管理</title>
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

	<redxun:toolbar entityName="com.redxun.oa.info.entity.InsColumn" excludeButtons="popupSearchMenu,popupAttachMenu,popupSettingMenu" />

	<div class="mini-fit rx-grid-fit" >
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/oa/info/insColumn/listData.do" idField="colId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" ondrawcell="onDrawCell" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div field="action" name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="name" width="160" headerAlign="center" allowSort="true">栏目名称</div>
				<div field="key" width="120" headerAlign="center" allowSort="true">栏目Key</div>
				<div field="enabled" width="60" headerAlign="center" allowSort="true">是否启用</div>
				<div field="colType" width="100" headerAlign="center" allowSort="true">信息栏目类型</div>
			</div>
		</div>
	</div>

	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.info.entity.InsColumn" winHeight="390" winWidth="700" entityTitle="栏目" baseUrl="oa/info/insColumn" />
	<script type="text/javascript">
		//编辑
		
		function onDrawCell(e) {
			var record = e.record;
			var colType = record.colType;
			var uid = record.pkId;
			if (e.field == "action") {
				if (colType != "新闻、公告") {
					e.cellHtml = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + uid + '\')"></span>'  
					+ ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\')"></span>' 
					+ ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
				}
			}
		}
		
		function onActionRenderer(e) {
			var record = e.record;
			var uid = record.pkId;
			var name = record.name;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + uid + '\')"></span>' 
			+ '<span class="icon-col-news" title="管理公告" onclick="mgrNewsRow(\'' + uid + '\',\'' + name + '\')"></span>' 
			+ ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\')"></span>' 
			+ ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
			return s;
		}
		//弹出一个Tab进行管理该栏目下的所有公告
		function mgrNewsRow(colId, name) {
			top['index'].showTabFromPage({
				title : name + '-信息公告',
				tabId : 'colNewsMgr_' + colId,
				url : __rootPath + '/oa/info/insNews/byColId.do?colId=' + colId
			});
		}
		
        grid.on("drawcell", function (e) {
            var record = e.record,
	        field = e.field,
	        value = e.value;
            var enabled = record.enabled;
            if(field=='enabled'){
            	if(enabled=='ENABLED'){
            		e.cellHtml='启用';
            	}else if(enabled=='DISABLED'){
            		e.cellHtml='禁止';
            	}
            }
        });
	</script>
</body>
</html>