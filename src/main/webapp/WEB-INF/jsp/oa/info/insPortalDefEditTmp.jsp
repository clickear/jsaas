﻿<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Bootstrap Layout</title>
	<%@include file="/commons/dynamic.jspf"%>
<%-- 	<link rel="stylesheet" type="text/css" href="${ctxPath }/scripts/layoutit/css/bootstrap-combined.min.css"> --%>	
	<link rel="stylesheet" type="text/css" href="${ctxPath}/scripts/layoutit/css/combined.css">
	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/icons.css">
	<link rel="stylesheet" type="text/css" href="${ctxPath}/scripts/layoutit/css/layoutitIndex.css">
	<link rel="stylesheet" type="text/css" href="${ctxPath }/scripts/layoutit/css/layoutit.css">
	<script type="text/javascript" src="${ctxPath }/scripts/boot.js" ></script>
<%-- 	<script type="text/javascript" src="${ctxPath }/scripts/jquery-1.11.3.js"></script>
 --%>	<script type="text/javascript" src="${ctxPath }/scripts/layoutit/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctxPath }/scripts/layoutit/js/jquery-ui.js"></script>
	<script type="text/javascript" src="${ctxPath }/scripts/layoutit/js/jquery.htmlClean.js"></script>
	<script type="text/javascript" src="${ctxPath }/scripts/common/baiduTemplate.js"></script>
<%-- 	<script type="text/javascript" src="${ctxPath}/scripts/mini/miniui/miniui.js"></script>
 --%>	
	<script type="text/javascript" src="${ctxPath }/scripts/layoutit/js/scripts.js"></script>
	<script type="text/javascript" src="${ctxPath }/scripts/echart/echarts.js"></script>
	
</head>
<body style="min-height: 660px; cursor: auto;" class="edit">
	<ul class="topNav">
		<li id="edit" class="active">
			<span>编辑</span>
		</li>
		<li id="sourcepreview">
			<span>预览</span>
		</li>
		<li onclick="saveHtml1()">
			<span>保存</span>
		</li>
		<li href="#clear" id="clear">
			<span>清空</span>
		</li>
	</ul>
	
	<div class="container-fluid">
		<div class="changeDimension">
			<div class="row-fluid">
				<div class="">
					<div class="sidebar-nav">
						<ul class="nav nav-list accordion-group">
							<li class="nav-header frame activeNav">
								<div class="pull-right popover-info">
									<div class="popover fade right">
										<div class="arrow"></div>
										<h3 class="popover-title">帮助</h3>
										<div class="popover-content">在这里设置你的栅格布局, 栅格总数默认为12, 用空格分割每列的栅格值</div>
									</div>
								</div>
								<span>布局系统</span>
								<span class="spread">
									<span class="horizontal"></span>
									<span class="vertical"></span>
								</span>
							</li>
							<li style="display: list-item;" class="rows" id="estRows">
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important">
										<i class="icon-remove icon-white"></i>删除
									</a> 
									<span class="drag label">拖动</span>
									<div class="preview">
										<input value="1列(12)" type="button">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span12 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a 	href="#close" class="remove label label-important">
										<i class="icon-remove icon-white"></i>删除
									</a>
									<span class="drag label">拖动</span>
									<div class="preview">
										<input value="2列(6,6)" type="button">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span6 column"></div>
											<div class="span6 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important">
										<i class="icon-remove icon-white"></i>删除</a>
										<span class="drag label">拖动</span>
									<div class="preview">
										<input value="2列(9,3)" type="button">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span9 column"></div>
											<div class="span3 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important"><i
										class="icon-remove icon-white"></i>删除</a> 
										<span class="drag label">拖动</span>
									<div class="preview">
										<input value="2列(3,9)" type="button">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span3 column"></div>
											<div class="span9 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important">删除</a>
										<span class="drag label">拖动</span>
									<div class="preview">
										<input value="2列(8,4)" type="button">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span8 column"></div>
											<div class="span4 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important">删除</a> 
										<span class="drag label">拖动</span>
									<div class="preview">
										<input value="2列(4,8)" type="button">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span4 column"></div>
											<div class="span8 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important">删除</a> 
										<span class="drag label">拖动</span>
									<div class="preview">
										<input value="2列(10,2)" type="button">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span10 column"></div>
											<div class="span2 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important">删除</a> 
									<span class="drag label">拖动</span>
									<div class="preview">
										<input value="2列(2,10)" type="button">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span2 column"></div>
											<div class="span10 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important">删除</a> 
									<span class="drag label">拖动</span>
									<div class="preview">
										<input value="3列(4,4,4)" type="button">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span4 column"></div>
											<div class="span4 column"></div>
											<div class="span4 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important">删除</a> 
									<span class="drag label">拖动</span>
									<div class="preview">
										<input value="3列(2,6,4)" type="button">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span2 column"></div>
											<div class="span6 column"></div>
											<div class="span4 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important">删除</a>
									<span class="drag label">拖动</span>
									<div class="preview">
										<input value="3列(4,6,2)" type="button">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span4 column"></div>
											<div class="span6 column"></div>
											<div class="span2 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important">删除</a>
									<span class="drag label">拖动</span>
									<div class="preview">
										<input value="3列(5,5,2)" type="button">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span5 column"></div>
											<div class="span5 column"></div>
											<div class="span2 column"></div>
										</div>
									</div>
								</div>
							</li>
						</ul>
						<ul class="nav nav-list accordion-group">
							<li class="nav-header classification">
								<span>默认栏目</span>
								<span class="spread">
									<span class="horizontal"></span>
									<span class="vertical"></span>
								</span>
							</li>

							<li style="display: none;" class="boxes" id="elmBase"></li>
						</ul>
					</div>
				</div>
				<div class="demo ui-sortable" style="min-height: 304px;">
					${insPortalDef.editHtml}
				</div>
				<div id="download-layout">
					<div class="container-fluid"></div>
				</div>
			</div>
		</div>
	</div>
	<script>
		//设置左分隔符为 <!
		baidu.template.LEFT_DELIMITER='<#';
		//设置右分隔符为 <!  
		baidu.template.RIGHT_DELIMITER='#>';
		var bt=baidu.template;
		$(function(){
			$.ajax({
				url:__rootPath + "/oa/info/insColumnDef/getAllCol.do",
				method:"POST",
				success:function(data){
					buildCols(data);
				}		
			});
			$("#Refresh").click(function(){
				var colId = $("#myTask").attr("colId");
				$.ajax({
					url:__rootPath+"/oa/info/insColumnDef/getColHtml.do?colId="+colId,
					method:"POST",
					success:function(data){
						$("#myTask")[0].outerHTML = data;
					}
				});
			});			
			//$(".demo").html('');
		});
		
		function buildCols(data){
			var menuData=data;
			var data={"list":menuData};
			var menuHtml=bt('ColsTemplate',data);
			$(".boxes").html(menuHtml);
			$(".sidebar-nav .box").draggable({
				connectToSortable: ".column",
				helper: "clone",
				handle: ".drag",
				start: function(e,t) {
					var colId = $(t.helper.context).find("div[colid]").attr("colid");
					$.ajax({
						url:__rootPath+"/oa/info/insColumnDef/getColHtml.do?colId="+colId,
						method:"POST",
						async: false,
						success:function(data){
							$(t.helper.context).find("div[colid]").html(data);							
						}
					});
					
					if (!startdrag) stopsave++;
					startdrag = 1;
				},
				drag: function(e, t) {			
					t.helper.width(400)
				},
				stop: function() {
					handleJsIds();
					if(stopsave>0) stopsave--;
					startdrag = 0;
					var carUl = document.getElementById('cardUl');
					if(carUl!=null) {cardResponse();}
				}
			});
		}
		

		
	
		function saveHtml1() {
			var portId = "${portId}";
			var url = __rootPath + "/oa/info/insPortalDef/saveHtml.do?portId="	+ portId;
			var formatSrc = getHtml();
			var editdHtml = $(".demo").html();
			var formObj=$(formatSrc);
			formObj.find("div[class^=colId]").html("");
			var params = {
				formatSrc : formObj[0].outerHTML,
				editHtml : editdHtml
			};
			$.post(url, params, function(data) {
				CloseWindow('ok');
			})

		}

		function resizeCanvas(size) {

			var containerID = document
					.getElementsByClassName("changeDimension");
			var containerDownload = document.getElementById("download-layout")
					.getElementsByClassName("container-fluid")[0];
			var row = document.getElementsByClassName("demo ui-sortable");
			var container1 = document.getElementsByClassName("container1");
			if (size == "md") {
				$(containerID).width('id', "MD");
				$(row).attr('id', "MD");
				$(container1).attr('id', "MD");
				$(containerDownload).attr('id', "MD");
			}
			if (size == "lg") {
				$(containerID).attr('id', "LG");
				$(row).attr('id', "LG");
				$(container1).attr('id', "LG");
				$(containerDownload).attr('id', "LG");
			}
			if (size == "sm") {
				$(containerID).attr('id', "SM");
				$(row).attr('id', "SM");
				$(container1).attr('id', "SM");
				$(containerDownload).attr('id', "SM");
			}
			if (size == "xs") {
				$(containerID).attr('id', "XS");
				$(row).attr('id', "XS");
				$(container1).attr('id', "XS");
				$(containerDownload).attr('id', "XS");

			}

		}
	</script>
	<!-- <div style="display: none;">
		<script>
			var _hmt = _hmt || [];
			(function() {
				var hm = document.createElement("script");
				hm.src = "//hm.baidu.com/hm.js?8e2a116daf0104a78d601f40a45c75b4";
				var s = document.getElementsByTagName("script")[0];
				s.parentNode.insertBefore(hm, s);
			})();
		</script>
		<script src="http://s11.cnzz.com/stat.php?id=5578006&web_id=5578006" language="JavaScript"></script>
	</div> -->
	
<script id="ColsTemplate"  type="text/html">
<ul class="inner">
  <#for(var i=0;i<list.length;i++){
    var col=list[i];
  #>
		<li class="box box-element ui-draggable">
			<a href="#close" class="remove label label-important">删除</a> 
			<span class="drag label">拖动</span>
			<div class="preview"><#=col.name#></div>
			<div class="view">
				<div colId="<#=col.colId#>"></div>
			</div>
		</li>
		<#}#>
</ul>
<div class="scrollbar">
	<div class="scrollbtn"></div>
</div>
</script>
</body>
</html>
