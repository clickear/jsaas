<%-- 
    Document   : 公司门户的显示页面
    Created on : 2015-1-15
    Author     : 朱海威
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html>
<html>
<head>
<title>门户编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8; IE=9; IE=EDGE;">
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/portal/Portal.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/scripts/portal/portal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	if("${insPortal.key}"=="GLOBAL-COMPANY"||"${insPortal.key}"=="COMPANY"){
		$(function(){
			$(".abc").find("#saveButton").hide();
			$(".abc").find("#editButton").hide();
		});
	}
</script>
<style>
    span.mini-tools-more
    {
        background: url("${ctxPath}/styles/images/index/contenticon.png") no-repeat 0px -95px; width: 40px; height: 16px; display: block; float: left; margin-right: 10px;
    }
    span.mini-tools-refresh {
    	background: url("${ctxPath}/styles/images/index/contenticon.png") no-repeat -82px -95px; width: 15px; height: 16px; display: block; float: left;margin-right: 10px;
    }
    
    span.mini-tools-collapse{
    	background: url("${ctxPath}/styles/images/index/contenticon.png") no-repeat -56px -95px; width: 15px; height: 16px; display: block; float: left;margin-right: 10px;
    }	
</style>
	
</head>
<body>
	
	<div id="toolbar1" class="mini-toolbar mini-toolbar-border-index" style="padding: 2px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;" align="right">
				<a class="mini-button" id="saveButton" iconCls="icon-save" plain="true" onclick="savePortal()"><b>保存</b></a>
				<a class="mini-button" id="editButton" iconCls="icon-edit-in" plain="true" onclick="setPort()">设置门户</a>
					<span class="separator"></span>
				<a class="mini-button" iconCls="icon-reload" plain="true" onclick="reload1()">刷新</a></td> 
			</tr>
		</table>
	</div>
	<div>
		<input id="portId" name="portId" class="mini-hidden" value="${insPortal.portId}" />
	</div>
	<div id="portalConfigJson" style="display:none">${portalCols} </div>
	<script type="text/javascript">    	
	mini.parse();
    var portId = '${insPortal.portId}';
    var portal = new mini.ux.Portal();
    var colWidths = [${colWidths}];
  //设置门户的栏目宽
    portal.set({
        style: "width: 100%;height:450px",
        columns: colWidths
        });
  //将后台的数据分割   
    portal.render(document.body);

  	var portColsArr=jQuery.parseJSON($('#portalConfigJson').html());
    //生成每一个栏目
    for(var i=0;i<portColsArr.length;i++){
    	portColsArr[i].column=portColsArr[i].colNum;
    	portColsArr[i].id=portColsArr[i].colId;
    	portColsArr[i].title=portColsArr[i].colName;
    	if(portColsArr[i].loadType=='TEMPLATE'){
    		portColsArr[i].url=__rootPath+'/oa/info/insPortal/getPortalHtml.do?colId='+portColsArr[i].colId;
    	}else{
    		portColsArr[i].url=portColsArr[i].url;
    	}
    	if(portColsArr[i].iconCls==null){
    		portColsArr[i].iconCls='icon-detail';
    	}

    	portColsArr[i].showCollapseButton= true;
    	portColsArr[i].onbuttonclick='onbuttonclick';
    	portColsArr[i].refreshOnExpand=true;
    	portColsArr[i].moreUrl=portColsArr[i].moreUrl;
    	
    	var buttons=[];
    	if(portColsArr[i].moreUrl!=''){
    		buttons.push('more');
    	}
    	buttons.push('refresh');
    	buttons.push('collapse');
    	portColsArr[i].buttons=buttons;
    	//判断是前台html片段还是后台片段
    	portal.addPanel(portColsArr[i]);
    }
  	
    var pans =portal.getPanels();
  	//刷新
	function reload1() {
			location.reload();
	}
	//点击more按钮触发的函数
	function onbuttonclick(e) {
        if(e.name=="more"){
        	var moreUrl = e.source.moreUrl;
        	var name = e.source.title;
        	var colId = e.source.el.id;
        	mgrNewsRow(colId,name,moreUrl);
        }
        if(e.name=="refresh"){
        	this.reload();
        }
	}
	//打开一个新的页面显示这个栏目的more
	function mgrNewsRow(colId,name,moreUrl){
		top['index'].showTabFromPage({
			title:name,
			tabId:'colNewsMgr_'+colId,
			url:moreUrl
		});
	}
	//打开设置门户页面
	function setPort(){
		top['index'].showTabFromPage({
			title:'编辑门户',
			tabId:'portId_'+portId,
			iconCls:'icon-window',
			url:__rootPath+'/oa/info/insPortal/personShowEdit.do'
		});
	}
	//保存
	function savePortal(){
		var panels = [];
		//将每个栏目遍历存起来
		for(var i=0;i<pans.length;i++){ 
			panels.push({
				id:pans[i].id,
				title:pans[i].title,
				column:pans[i].column,
				sn:i,
				height:pans[i].height
			});
		}
		var data = mini.encode(panels);
		_SubmitJson({
			url : __rootPath + '/oa/info/insPortal/savePortal.do?portId='+ portId ,
			method : 'POST',
			data : {data:data}
		});
	}
</script>
</body>
</html>