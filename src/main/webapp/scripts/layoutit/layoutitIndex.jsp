﻿﻿<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="title" content="Bootstrap可视化布局系统">
<meta name="description"
	content="Bootstrap可视化布局系统是一款可在线拖放排序、在线编辑的Bootstrap可视化编辑器，通过该工具你可以通过拖放的形式的实现在线编辑基于bootstrap的html模板，并可直接生成模板，生成的模板可以直接下载。">
<meta name="keywords"
	content="bootstrap在线编辑器,bootstrap在线设计,bootstrap可视化工具">
<title>Bootstrap Layout</title>
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath }/scripts/layoutit/css/bootstrap-combined.min.css" rel="stylesheet">
<link href="${ctxPath }/scripts/layoutit/css/layoutit.css" rel="stylesheet">
<link href="${ctxPath }/scripts/layoutit/css/docs.min.css" rel="stylesheet">

<link rel="stylesheet" href="http://apps.bdimg.com/libs/fontawesome/4.2.0/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctxPath }/scripts/layoutit/css/layoutitIndex.css" type="text/css">
<!--[if lt IE 9]>
		<script src="js/html5shiv.js"></script>
	<![endif]-->
<link rel="shortcut icon" href="${ctxPath }/scripts/layoutit/img/favicon.png">
<script type="text/javascript" 	src="${ctxPath }/scripts/layoutit/js/jquery-2.0.0.min.js"></script>
<!--[if lt IE 9]>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
	<![endif]-->
<script type="text/javascript" src="${ctxPath }/scripts/layoutit/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctxPath }/scripts/layoutit/js/jquery-ui.js"></script>
<script type="text/javascript" src="${ctxPath }/scripts/layoutit/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${ctxPath }/scripts/layoutit/js/jquery.htmlClean.js"></script>
<script type="text/javascript" src="${ctxPath }/scripts/layoutit/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${ctxPath }/scripts/layoutit/ckeditor/config.js"></script>
<script type="text/javascript" src="${ctxPath }/scripts/layoutit/js/FileSaver.js"></script>
<script type="text/javascript" src="${ctxPath }/scripts/layoutit/js/blob.js"></script>
<script src="${ctxPath }/scripts/layoutit/js/docs.min.js"></script>
<script type="text/javascript" src="${ctxPath }/scripts/common/baiduTemplate.js"></script>
<script type="text/javascript" src="${ctxPath }/scripts/layoutit/js/scripts.js"></script>


</head>
<body style="min-height: 660px; cursor: auto;" class="edit">
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<div class="nav-collapse collapse">
					<ul class="nav" id="menu-layoutit">
						<li class="divider-vertical"></li>
						<li>
							<div class="btn-group" data-toggle="buttons-radio">
								<button type="button" id="edit" class="btn btn-primary active">
									<i class="icon-edit icon-white"></i>编辑
								</button>
								<button type="button" class="btn btn-primary" id="sourcepreview">
									<i class="icon-eye-open icon-white"></i>预览
								</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary"
									onclick="saveHtml1()">保存</button>
								<button class="btn btn-primary" href="#clear" id="clear">
									<i class="icon-trash icon-white"></i>清空
								</button>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="changeDimension">
			<div class="row-fluid">
				<div class="">
					<div class="sidebar-nav">
						<ul class="nav nav-list accordion-group">
							<li class="nav-header">
								<div class="pull-right popover-info">
									<i class="icon-question-sign "></i>
									<div class="popover fade right">
										<div class="arrow"></div>
										<h3 class="popover-title">帮助</h3>
										<div class="popover-content">
											在这里设置你的栅格布局, 栅格总数默认为12, 用空格分割每列的栅格值, 如果您需要了解更多信息，请访问<a
												target="_blank"
												href="http://www.w3cschool.cc/bootstrap/bootstrap-grid-system.html">Bootstrap网格系统。</a>
										</div>
									</div>
								</div> <i class="icon-plus icon-white"></i>布局系统
							</li>
							<li style="display: list-item;" class="rows" id="estRows">
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important"><i
										class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i
										class="icon-move"></i>拖动</span>
									<div class="preview">
										<input value="1列(12)" type="text">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span12 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important"><i
										class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i
										class="icon-move"></i>拖动</span>
									<div class="preview">
										<input value="2列(6,6)" type="text">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span6 column"></div>
											<div class="span6 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important"><i
										class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i
										class="icon-move"></i>拖动</span>
									<div class="preview">
										<input value="2列(9,3)" type="text">
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
										class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i
										class="icon-move"></i>拖动</span>
									<div class="preview">
										<input value="2列(3,9)" type="text">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span3 column"></div>
											<div class="span9 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important"><i
										class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i
										class="icon-move"></i>拖动</span>
									<div class="preview">
										<input value="2列(8,4)" type="text">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span8 column"></div>
											<div class="span4 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important"><i
										class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i
										class="icon-move"></i>拖动</span>
									<div class="preview">
										<input value="2列(4,8)" type="text">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span4 column"></div>
											<div class="span8 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> 
										<span class="drag label"><i class="icon-move"></i>拖动</span>
									<div class="preview">
										<input value="2列(10,2)" type="text">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span10 column"></div>
											<div class="span2 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important"><i
										class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i
										class="icon-move"></i>拖动</span>
									<div class="preview">
										<input value="2列(2,10)" type="text">
									</div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span2 column"></div>
											<div class="span10 column"></div>
										</div>
									</div>
								</div>
								<div class="lyrow ui-draggable">
									<a href="#close" class="remove label label-important"><i
										class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i
										class="icon-move"></i>拖动</span>
									<div class="preview">
										<input value="3列(4,4,4)" type="text">
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
									<a href="#close" class="remove label label-important"><i
										class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i
										class="icon-move"></i>拖动</span>
									<div class="preview">
										<input value="3列(2,6,4)" type="text">
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
									<a href="#close" class="remove label label-important"><i
										class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i
										class="icon-move"></i>拖动</span>
									<div class="preview">
										<input value="3列(4,6,2)" type="text">
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
									<a href="#close" class="remove label label-important"><i
										class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i
										class="icon-move"></i>拖动</span>
									<div class="preview">
										<input value="3列(5,5,2)" type="text">
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
							<li class="nav-header"><i class="icon-plus icon-white"></i>
								默认栏目
								<div class="pull-right popover-info">
									<i class="icon-question-sign "></i>
									<div class="popover fade right">
										<div class="arrow"></div>
										<h3 class="popover-title">帮助</h3>
										<div class="popover-content">
											这里提供了一系列基本元素样式，你可以通过区块右上角的编辑按钮修改样式设置。如需了解更多信息，请访问<a
												target="_blank"
												href="http://www.w3cschool.cc/bootstrap/bootstrap-css-overview.html">基本CSS。</a>
										</div>
									</div>
								</div></li>

							<li style="display: none;" class="boxes" id="elmBase">
			
							</li>
						</ul>
					</div>
				</div>
				<div class="demo ui-sortable" style="min-height: 304px;">
					<div class="lyrow">
						<a href="#close" class="remove label label-important"><i
							class="icon-remove icon-white"></i>删除</a> <span class="drag label"><i
							class="icon-move"></i>拖动</span>
						<div class="preview">9 3</div>
						<div class="view">
						</div>
					</div>
				</div>
				<div id="download-layout">
					<div class="container-fluid"></div>
				</div>
			</div>
		</div>
		<div class="modal hide fade" role="dialog" id="editorModal">
			<div class="modal-header">
				<a class="close" data-dismiss="modal">×</a>
				<h3>Save your Layout</h3>
			</div>
			<div class="modal-body">
				<p>
					<textarea id="contenteditor"></textarea>
				</p>
			</div>
			<div class="modal-footer">
				<a id="savecontent" class="btn btn-primary" data-dismiss="modal">Save</a>
				<a class="btn" data-dismiss="modal">Cancel</a>
			</div>
		</div>
		<div class="modal hide fade" role="dialog" id="downloadModal">
			<div class="modal-header">
				<a class="close" data-dismiss="modal">×</a>
				<h3>下载</h3>
			</div>
			<div class="modal-body">
				<p>选择你要保存的布局</p>
				<div class="btn-group">
					<button type="button" id="fluidPage" class="active btn btn-info">
						<i class="icon-fullscreen icon-white"></i> 自适应宽度
					</button>
					<button type="button" class="btn btn-info" id="fixedPage">
						<i class="icon-screenshot icon-white"></i> 固定宽度
					</button>
				</div>
				<br> <br>
				<p>
					<textarea></textarea>
				</p>
			</div>
			<div class="modal-footer">
				<a class="btn" data-dismiss="modal" onclick="javascript:saveHtml();">保存</a>
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
			
			/* $.ajax({
				url:__rootPath + "/oa/info/insPortalDef/getPersonalPort.do",
				method:"POST",
				success:function(data){					
					var cardUl = document.getElementById('cardUl');
					if(cardUl)  {cardResponse();}
				}		
			}); */
			
			
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
			var portId = "${param['portId']}";
			var url = __rootPath + "/oa/info/insPortalDef/saveHtml.do?portId="	+ portId;
			var formatSrc = getHtml();
			debugger;
			var editdHtml = $(".demo").html();
			var formObj=$(formatSrc);
			formObj.find("div[class^=colId]").html("");
			var params = {
				formatSrc : formObj[0].outerHTML,
				editHtml : editdHtml
			};
			$.post(url, params, function(data) {
				CloseWindow();
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
	<div style="display: none;">
		<script>
			var _hmt = _hmt || [];
			(function() {
				var hm = document.createElement("script");
				hm.src = "//hm.baidu.com/hm.js?8e2a116daf0104a78d601f40a45c75b4";
				var s = document.getElementsByTagName("script")[0];
				s.parentNode.insertBefore(hm, s);
			})();
		</script>
		<script src="http://s11.cnzz.com/stat.php?id=5578006&web_id=5578006"
			language="JavaScript"></script>
	</div>
	
<script id="ColsTemplate"  type="text/html">
<div class="inner">
  <#for(var i=0;i<list.length;i++){
    var col=list[i];
  #>
		<div class="box box-element ui-draggable">
			<a href="#close" class="remove label label-important"> <i class="icon-remove icon-white"></i>删除	</a> 
			<span class="drag label"><i class="icon-move"></i>拖动</span>
			<div class="preview"><#=col.name#></div>
			<div class="view">
				<div colId="<#=col.colId#>">
				</div>
			</div>
		</div>
		<#}#>
</div>
<div class="scrollbar">
	<div class="scrollbtn"></div>
</div>
</script>
<script>
	window.onresize = function(){
		cardResponse();
	}
	function cardResponse(){
		var cardUl = document.getElementById('cardUl'),
			cardUlWidth = cardUl.offsetWidth,
			cardLi = cardUl.children;
		for(var i=0; i<cardLi.length;i++){
			cardLi[i].style.width=cardUlWidth / 5 - 20 +'px';
		}
	}
	
</script>

</body>
</html>
