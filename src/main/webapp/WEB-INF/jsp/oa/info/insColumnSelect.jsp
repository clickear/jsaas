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
<title>栏目列表选择</title>
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
	<div class="mini-toolbar" style="text-align: center; line-height: 30px;" borderStyle="border:0;">
		<label>名称：</label> <input id="key" class="mini-textbox" style="width: 150px;" onenter="onKeyEnter" /> 
		<a class="mini-button" onclick="search1()">查询</a>
	</div>
	<div class="mini-fit" style="height: 100px;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/oa/info/insColumn/listData.do" idField="colId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">

				<div type="checkcolumn" width="20"></div>
				<div field="name" width="160" headerAlign="center" allowSort="true">栏目名称</div>
				<div field="key" width="120" headerAlign="center" allowSort="true">栏目Key</div>
				<div field="enabled" width="60" headerAlign="center" allowSort="true">是否启用</div>
				<div field="colType" width="100" headerAlign="center" allowSort="true">信息栏目类型</div>
			</div>
		</div>
	</div>
	<div class="mini-toolbar" style="text-align: center; padding-top: 8px; padding-bottom: 8px;" borderStyle="border:0;">
		<a class="mini-button" onclick="onOk()">确定</a> <span style="display: inline-block; width: 25px;"></span> <a class="mini-button" onclick="onCancel()">取消</a>
	</div>
	<script type="text/javascript">
			mini.parse();
		    var grid = mini.get("datagrid1");
			//栏目查询
			function search1() {
		        var key = mini.get("key").getValue();
		        grid.setUrl(__rootPath + '/oa/info/insColumn/searchByName.do');
		        grid.load({key:key});
		    }
			
			function GetData() {
	       		var row = grid.getSelecteds();
	        	return row;
	    	}
			//关闭页面时返回action
			function CloseWindow(action) {
				if (window.CloseOwnerWindow)
					return window.CloseOwnerWindow(action);
				else
					window.close();
			}
	
			function onOk() {
				CloseWindow("ok");
			}
			function onCancel() {
				CloseWindow("cancel");
			}
		</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.info.entity.InsColumn" winHeight="390" winWidth="700" entityTitle="栏目" baseUrl="oa/info/insColumn" />
</body>
</html>