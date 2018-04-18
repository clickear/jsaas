<%-- 
    Document   : 公司门户的编辑页面
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
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/scripts/portal/portal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	if("${insPortal.key}"=="GLOBAL-COMPANY"){
		$(function(){
			$(".abc").find(".icon-edit").hide();
		});
	}
</script>
<style>
    span.mini-tools-more
    {
        width:48px;height:14px;
        background:url(${ctxPath}/styles/icons/more.gif) no-repeat;
    }
    </style>
    <style type="text/css">
		a:link,a:visited{
		text-decoration:none;  /*超链接无下划线*/
		}
	</style>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar" style="padding: 2px;">
		<table class="abc" style="width: 100%;">
			<tr>
				<td style="width: 100%;">
				<a class="mini-button" iconCls="icon-save" plain="true" onclick="savePortal()"><b>保存</b></a>
				<a class="mini-button" iconCls="icon-reload" plain="true" onclick="reload()">刷新</a>
				<span class="separator"></span>
				<a class="mini-button" iconCls="icon-add" plain="true" onclick="addCol()">加入栏目</a>
				<a class="mini-button" iconCls="icon-edit" plain="true" onclick="addExistCol()">编辑门户</a>
				</td>  
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
  //设置门户的栏目宽
    portal.set({
        style: "width: 100%;height:400px",
        columns: [${colWidths}]
    });
  //将后台的数据分割
   
   portal.render(document.body);

  	var portColsArr=jQuery.parseJSON($('#portalConfigJson').html());
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
    	portColsArr[i].buttons='more collapse close';
    	portColsArr[i].showCollapseButton= true;
    	portColsArr[i].onbuttonclick='onbuttonclick';
    	portColsArr[i].refreshOnExpand=true;
    	portColsArr[i].moreUrl=portColsArr[i].moreUrl;
    	
    	//判断是前台html片段还是后台片段
    	portal.addPanel(portColsArr[i]);
    	
    }

  //获得所有的栏目数据
    var pans =portal.getPanels();
  
  //点击保存的函数
	function savePortal(){
		var panels = [];
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
			url : __rootPath + '/oa/info/insPortal/savePortal.do?portId='+portId,
			method : 'POST',
			data : {data:data},
			success : function(result) {
				location.reload();
			}
		});
	}
	
	//刷新函数
	function reload() {
			location.reload();
	}
	
	//点击栏目工具条函数
	function onbuttonclick(e) {
		//点击栏目关闭的那个x时触发删除函数
        if (e.name == "close") {
            var colId = e.source.el.id;
            var portId = '${insPortal.portId}';
            _SubmitJson({
    			url : __rootPath + '/oa/info/insPortal/delCol.do',
    			method : 'POST',
    			data : {colId:colId,portId:portId},
    			success : function(result) {
    					location.reload();
    					return;
    				}
    		});
        }
      //点击more按钮触发的函数
        if(e.name=="more"){
        	var moreUrl = e.source.moreUrl;
        	var name = e.source.title;
        	var colId = e.source.el.id;
        	mgrNewsRow(colId,name,moreUrl);
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
	
	//点击新增栏目时的函数
	function addCol(){		
		_OpenWindow({
		   url: __rootPath+"/oa/info/insPortal/addColumn.do?portId="+portId,
           title: "新建本门户栏目",
           width: 750, height: 500,
           ondestroy: function(action) {
               if (action == 'ok') {
            	   location.reload();
               }
           }
   	});
	}
	
	//点击编辑门户的函数
	function addExistCol(){		
		_OpenWindow({
   		   url: __rootPath+"/oa/info/insPortal/editPort.do?portId="+portId,
           title: "编辑门户",
           width: 750, height: 500,
           ondestroy: function(action) {
               if (action == 'ok') {
            	   location.reload();
               }
           }
   	});
	}
	
</script>
</body>
</html>