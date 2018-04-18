<%-- 
    Document   : [SysReport]编辑页
    Created on : 2015-3-21, 0:11:48
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 使用范围：
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
    
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>报表预览</title>
<%@include file="/commons/edit.jsp"%>
<script type="text/javascript">
	
</script>
<style>
</style>
</head>
<body>
	
	<ul id="exportSettingMenu" class="mini-menu" style="display: none;">
		<li iconCls="icon-pdf" onclick="customExport('pdf')">导出为PDF</li>
		<li iconCls="icon-doc" onclick="customExport('doc')">导出为DOC</li>
		<li >
			<span>导出为EXCEL</span>
			<ul >
				<li iconCls="icon-excel" onclick="customExport('pagexls')">分页显示</li>
				<li iconCls="icon-excel" onclick="customExport('xls')">不分页显示</li>
			</ul>
		</li>
	</ul>
	<div id="buttons">
		<span class="separator"></span> 
		<a class="mini-menubutton" iconCls="icon-baobiao-export" plain="true" menu="#exportSettingMenu">导出</a>
		<a class="mini-button" iconCls="icon-printer" plain="true" onclick="printReport()" >打印</a>
		<span class="separator"></span>
		<a id="search" class="mini-button" iconCls="icon-report" plain="true" onclick="searchReport()">生成报表</a> 
	</div>
	<div id="pager1" class="mini-pager" onpagechanged="onPageChanged" style="width: 99%; background: #f0f3f7; border: solid 1px #ccc;" pageSize="1"  showPageSize="false" showPageIndex="true" showPageInfo="false" buttons="#buttons"></div>

	<div id="param">
		<div id="paramConfig" style="background: #F0F3F7;">
			<form id="viewForm">${paramConfig}</form>
		</div>
	</div>

	<iframe name="contentFrame" id="contentFrame"   charset="&quot;utf-8&quot;" frameborder="0" scrolling="no" style="width: 1000px;height:800px;" onload="autoHeight()"></iframe>

	<form name="hiform" id="hiform" action="${ctxPath}/sys/core/sysReport/show.do" method="post" target="contentFrame">
		<input id="dataJson" type="hidden" name="dataJson">
	</form>
	
	

	<script type="text/javascript">
		mini.parse();
		var pager = mini.get("pager1");
		var form = new mini.Form("viewForm");
		var pageIndex = 0;
		var lastPage = 0;
		
		/*自定义导出,类型分pdf,doc,excel,分页excel*/
		function customExport(eType){
			var postObj={'dataJson':mini.encode(form.getData()),'repId':'${repId}','exportType':eType};
			jQuery.download(__rootPath+'/sys/core/sysReport/export.do',postObj, 'post');
		}
		
		/* 报表自适应高度 */
		function autoHeight() {
			var iframe = document.getElementById("contentFrame");
			if (iframe.Document) {//ie自有属性
				iframe.style.height = iframe.Document.documentElement.scrollHeight;
			} else if (iframe.contentDocument) {//ie,firefox,chrome,opera,safari
				iframe.height = iframe.contentDocument.body.offsetHeight;
			}
			pageIndex = iframe.contentWindow.pageIndex;
			lastPage = iframe.contentWindow.lastPageIndex;
			if (lastPage > 1) {
				$("#pager1").show();
			}
			pagination(pageIndex, lastPage);
		}
		//生成报表
		function searchReport() {
			form.validate();
			if (!form.isValid()) {
				return;
			}			
			var postObj={'dataJson':mini.encode(form.getData()),'repId':'${repId}'};
			$("#dataJson").val(mini.encode(postObj));
			document.getElementById("hiform").submit();
		}
		//换页
		function pagination(pageIndex, lastPage) {
			pager.update(pageIndex, 1, lastPage);
		}
		//页面更改时的跳转
		function onPageChanged(e) {
			var postObj={'dataJson':mini.encode(form.getData()),'repId':'${repId}','page':e.pageIndex};
			$("#dataJson").val(mini.encode(postObj));
			document.getElementById("hiform").submit();
		}
		
		//打印表单
		function printReport(){
			document.getElementById('contentFrame').focus();
		    document.getElementById('contentFrame').contentWindow.print();
		}
	</script>
</body>
</html>