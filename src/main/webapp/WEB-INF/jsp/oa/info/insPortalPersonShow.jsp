<%-- 
    Document   : 个人门户的显示页面,暂时没用
    Created on : 2015-1-15
    Author     : 朱海威
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>门户编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/portal/Portal.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/scripts/portal/portal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
</script>
<style>
span.mini-tools-more {
	width: 48px;
	height: 14px;
	background: url(${ctxPath}/styles/icons/more.gif) no-repeat;
	/* more按钮的样式 */
}
</style>
<style type="text/css">
a:link,a:visited {
	text-decoration: none; /*超链接无下划线*/
}
</style>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar" style="padding: 2px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;" align="right">
				<a class="mini-button" iconCls="icon-reload" plain="true" onclick="reload()">刷新</a> 
				<span class="separator"></span> 
				<a class="mini-button" iconCls="icon-edit" plain="true" onclick="setPort()">设置门户</a></td>
			</tr>
		</table>
	</div>
	<div>
		<input id="portId" name="portId" class="mini-hidden" value="${insPortal.portId}" />
	</div>
	<script type="text/javascript">
	mini.parse();
    var portId = ${insPortal.portId};
    var portal = new mini.ux.Portal();
  //设置门户的栏目宽
    portal.set({
        style: "width: 100%;height:400px",
        columns: [${colWidths}]
    });
  //将后台的数据分割
   
    portal.render(document.body);
  
  	var portCols='${portalCols}';
  	var portColsArr=mini.decode(portCols);
    //生成每一个栏目
    for(var i=0;i<portColsArr.length;i++){
    	portColsArr[i].column=portColsArr[i].colNum;
    	portColsArr[i].id=portColsArr[i].colId;
    	portColsArr[i].title=portColsArr[i].colName;
    	portColsArr[i].height=portColsArr[i].height;
    	portColsArr[i].url=portColsArr[i].url;
    	portColsArr[i].buttons='more collapse';
    	portColsArr[i].showCollapseButton= true
    	portColsArr[i].onbuttonclick='onbuttonclick';
    	portal.addPanel(portColsArr[i]);
    }
  
    var pans =portal.getPanels();
    
	//刷新
	function reload() {
			location.reload();
	}
	
	//点击more按钮触发的函数
	function onbuttonclick(e) {
        if(e.name=="more"){
        	var name = e.source.title;
        	var colId = e.source.el.id;
        	mgrNewsRow(colId,name);
        }
	}
	
	//打开一个新的页面显示这个栏目的more
	function mgrNewsRow(colId,name){
		top['index'].showTabFromPage({
			title:name+'-信息公告',
			tabId:'colNewsMgr_'+colId,
			url:__rootPath+'/oa/info/insNews/byColId.do?colId='+colId
		});
	}
	
	//设置门户按钮，打开一个新页面设置门户
	function setPort(){
		top['index'].showTabFromPage({
			title:'编辑门户',
			tabId:'portId_'+portId,
			iconCls:'icon-window',
			url:__rootPath+'/oa/info/insPortal/personShowEdit.do?'
		});
	}
</script>
</body>
</html>