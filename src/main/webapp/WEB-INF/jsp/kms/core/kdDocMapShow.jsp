<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识详情</title>
<%@include file="/commons/dynamic.jspf" %>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="${ctxPath}/scripts/kms/starDemo/css/application.css">
<script type="text/javascript" src="${ctxPath}/scripts/kms/starDemo/lib/jquery.raty.min.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/kms/starDemo/lib/jquery.raty.js"></script>
<link rel="stylesheet" href="${ctxPath}/scripts/cut/map/style.css" type="text/css" media="all" />
<script type="text/javascript" src="${ctxPath}/scripts/cut/map/jquery-ui-1.9.2.custom.min.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/cut/map/jquery-notes_1.0.8.js"></script>
<link type="text/css" rel="stylesheet" href="${ctxPath}/scripts/cut/pic/normalize.css">
<script type="text/javascript" src="${ctxPath}/scripts/cut/pic/panzoom.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/cut/pic/jquery.mousewheel.js"></script>

<style>
.topButton {
	display: inline-block;
	line-height: 25px;
	height: 25px;
	width: 70px;
	background-color: #00BFFF;
	color: #fff;
	text-align: center;
	cursor: pointer;
	font-size: 9px;
	vertical-align: middle;
	border: 1px solid #0492C1;
	margin-right: 4px;
}

ul {
	display: block;
	list-style-type: disc;
	-webkit-margin-before: 1em;
	-webkit-margin-after: 1em;
	-webkit-margin-start: 0px;
	-webkit-margin-end: 0px;
	-webkit-padding-start: 40px;
}

.myli {
	list-style-type: none;
	border-bottom: 1px solid #dcdcdc;
	margin-bottom: 16px;
	padding-bottom: 20px;
}

.clearfix:after {
	visibility: hidden;
	display: block;
	font-size: 0;
	content: " ";
	clear: both;
	height: 0;
}
</style>
</head>
<body style="min-width: 1600px;">
	<div id="pureCmmts" style="display: none;">${cmmts}</div>
	<div id="pureRems" style="display: none;">${rems}</div>
	<!-- 上方工具栏 -->
	<div id="topButton" style="float: right; margin-top: 5px;">
		<a class="topButton" onclick="favorite()">收藏</a> <a class="topButton" onclick="remDoc()">推荐</a> 
		<a style="text-decoration: none;" id="edit" class="topButton" href="${ctxPath}/kms/core/kdDoc/mapMgrEdit.do?pkId=${kdDoc.docId}">编辑</a>
		<a id="publish" class="topButton" onclick="publish()">发布</a>  
		<a style="text-decoration: none;" id="newVersion" onclick="newVersion()" class="topButton">新版本</a>
	</div>
	<div style="clear: both;"></div>
	<hr style="height: 4px; border: none; border-top: 2px groove #CFCFCF;" />
	<div style="font-size: 10px; margin-left: 70px;">首页 &gt; 知识地图 &gt; ${treeType}</div>

	<!-- 右边基本信息 -->
	<div style="float: right; margin-top: 50px; margin-right: 5%; background: #E9F0F1; height: 300px; width: 300px;">
		<div style="position: relative; padding: 0 20px; height: 35px; line-height: 35px; font-weight: bold; font-size: 16px; font-family: '微软雅黑'; color: #fff; background: rgba(58, 194, 239, 0.91); cursor: pointer; border-bottom: none">基本信息</div>
		<div>
			<span style="font-style: italic; font-weight: 500; font-size: 30px; margin-left: 10px; margin-top: 10px; color: #FF6C00;">${kdDoc.compScore}</span> <span id="readOnly-demo" style="margin-left: 10px; margin-top: 10px;"></span>
		</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			创建者 ：<span style="color: #EA5252;"> ${createName}</span>
		</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			创建时间 ： <span><fmt:formatDate value="${kdDoc.createTime }" type="both" /></span>
		</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			地图状态 ：<span id="status"></span>
		</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			版本 ：
			<fmt:formatNumber value="${kdDoc.version}" pattern="0.0" />
		</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">最后更新者 ： ${updateName}</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			最后更新时间 ： <span><fmt:formatDate value="${kdDoc.updateTime}" type="both" /></span>
		</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">所属分类： ${treeType}</div>
	</div>

	<!-- 正文部分  -->
	<div style="width: 50%; margin-left: 100px; margin-right: 40%; margin-top: 30px; text-align: center;">
		<h3>
			<span label="Title center" style="font-size: 20px; color: black;">${kdDoc.subject}</span>
		</h3>
		<div style="font-size: 12px; border-bottom-color: #cccccc; border-bottom-width: 2px; border-bottom-style: solid;">
			<span style="font-size: 12px; color: #009221;"><fmt:formatDate value="${kdDoc.createTime}" type="both" /></span> <span style="font-size: 24px;">&nbsp;</span> <span style="font-size: 12px; color: #929292;">作者：</span> <span style="font-size: 12px; color: #EA5252;">${kdDoc.author }</span> <span style="font-size: 24px;">&nbsp;</span> <span style="font-size: 12px; color: #929292;">浏览次数：</span> <span style="font-size: 12px; color: red">${kdDoc.viewTimes }</span>
			<div id="hidebutton" style="text-align: right; margin-bottom: 5px; color: blue;cursor: pointer;" onclick="hide('mapContent','showbutton','hidebutton')">收起正文</div>
			<div id="showbutton" style="text-align: right; margin-bottom: 5px; color: blue;cursor: pointer;" onclick="show('mapContent','showbutton','hidebutton')">展开正文</div>
		</div>
		<div id="mapContent">
			<div id="content" style="margin-left: 5px; margin-right: 5px; min-height: 100px;max-height：300px;text-align: left; vertical-align: top">${kdDoc.content}</div>
			<div id="cover">
				<a href="#" title="">
					<section id="focal">
						<div class="parent">
							<div class="panzoom">
								<img src="${ctxPath}/sys/core/file/imageView.do?fileId=${kdDoc.coverImgId}" alt="" class="jquery-note" />
							</div>
						</div>
	
					</section> <%-- <img src="${ctxPath}/sys/core/file/imageView.do?fileId=${kdDoc.coverImgId}"  alt="" class="jquery-note"/> --%>
				</a>
			</div>
		</div>
	</div>

	<!-- 点评 -->
	<div style="width: 50%; margin-left: 100px; margin-right: 40%; margin-top: 30px; text-align: left;">
		<div style="font-weight: bold; font-size: 15px; color: #3E9CEF; border-bottom-color: #A7D3FB; border-bottom-width: 1px; border-bottom-style: solid;">
			<span style="margin-left: 15px; margin-bottom: 5px;">点评</span> 
			<span id="cmmtHide" style="height: 20px; width: 20px; float: right; background-image: url('/jsaas/styles/icons/arrowUp.png'); background-repeat: no-repeat;cursor: pointer;" onclick="hide('cmmt','cmmtShow','cmmtHide')"></span> 
			<span id="cmmtShow" style="height: 20px; width: 20px; float: right; background-image: url('/jsaas/styles/icons/arrowDown.png'); background-repeat: no-repeat;cursor: pointer;" onclick="show('cmmt','cmmtShow','cmmtHide')"></span>
		</div>
		<div id="cmmt">
			<div id="myCmmt">
				<div style="margin-left: 5px; margin-top: 10px;">
					<span style="font-weight: 600; color: #545454; font-size: 15px;">我的评分</span> <span class="demo">
						<div id="target-div-demo" class="target-demo"></div>
						<div id="target-div-hint" class="hint"></div>
					</span>
				</div>
				<div style="margin-left: 6px; margin-top: 10px; margin-bottom: 10px; width: 900px;">
					<textarea id="cmmtContent" name="content" cols=100 rows=4>写的不错，好评！</textarea>
					<a style="margin-left: 10px; line-height: 60px; height: 60px; width: 100px; background-color: #69C5E8;" class="topButton" onclick="sendCommt()">发布</a>
				</div>
				<div id="notice" name="notice" class="mini-checkbox" readOnly="false" text="通知地图创建者" style="margin-top: 5px; margin-left: 10px;"></div>
			</div>
			<div style="margin-left: 10px; margin-top: 10px;">
				<span style="font-weight: 600; color: #545454; font-size: 15px;">评分信息</span>
			</div>
		    <div style="margin-left: 5px; margin-top: 10px;">
				<ul id="comment" style="padding: 0px; margin: 0px;">
				</ul>
			</div>
		</div>
	</div>

	<!-- 推荐 -->
	<div style="width: 50%; margin-left: 100px; margin-right: 40%; margin-top: 30px; text-align: left;">
		<div style="font-weight: bold; font-size: 15px; color: #3E9CEF; border-bottom-color: #A7D3FB; border-bottom-width: 1px; border-bottom-style: solid;">
			<span style="margin-left: 15px; margin-bottom: 5px;">推荐</span> 
			<span id="remHide" style="height: 20px; width: 20px; float: right; background-image: url('/jsaas/styles/icons/arrowUp.png'); background-repeat: no-repeat;cursor: pointer;" onclick="hide('rem','remShow','remHide')"></span> 
			<span id="remShow" style="height: 20px; width: 20px; float: right; background-image: url('/jsaas/styles/icons/arrowDown.png'); background-repeat: no-repeat;cursor: pointer;" onclick="show('rem','remShow','remHide')"></span>
		</div>
		<div id="rem">
			<div id="myRem">
				<div style="margin-left: 5px; margin-top: 10px;">
					<div style="font-weight: 600; color: #545454; font-size: 15px;">
						推荐对象
						<textarea class="mini-textboxlist" allowInput="false" validateOnChanged="false" style="margin-top: 5px; margin-left: 10px;" id="remReceive" name="remReceive" width="500px"></textarea>
						<a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 5px;" iconCls="icon-addMsgPerson" onclick="addPerson(remReceive)">新增用户</a> 
						<a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 5px;" iconCls="icon-addMsgGroup" onclick="addGroup(remReceive)">新增用户组</a> 
						<a class="mini-button" style="width: 100px; margin-left: 10px; margin-top: 5px;" iconCls="icon-cancel" onclick="clearAll()">清空用户</a>
					</div>
					<span style="font-weight: 600; color: #545454; font-size: 15px; margin-top: 10px;">推荐等级</span> . <span class="demo">
						<div id="remStar" class="target-demo"></div>
						<div id="rem-text" class="hint"></div>
					</span>
				</div>
				<div style="margin-left: 6px; margin-top: 10px; margin-bottom: 10px; width: 900px;">
					<textarea id="remContent" name="remContent" cols=100 rows=4>写的不错，值得一看！</textarea>
					<a style="margin-left: 10px; line-height: 60px; height: 60px; width: 100px; background-color: #69C5E8;" class="topButton" onclick="sendRem()">推荐</a>
				</div>
			</div>
			<div style="margin-left: 10px; margin-top: 10px;">
				<span style="font-weight: 600; color: #545454; font-size: 15px;">推荐信息</span>
			</div>
			<div style="margin-left: 5px; margin-top: 10px;">
				<ul id="remList" style="padding: 0px; margin: 0px;">
				</ul>
			</div>
		</div>
	</div>

	<!-- 阅读记录 -->
	<div style="width: 50%; margin-left: 100px; margin-right: 40%; margin-top: 30px; text-align: left;">
		<div style="font-weight: bold; font-size: 15px; color: #3E9CEF; border-bottom-color: #A7D3FB; border-bottom-width: 1px; border-bottom-style: solid;">
			<span style="margin-left: 15px; margin-bottom: 5px;">阅读记录</span>
			<span id="readHide" style="height: 20px; width: 20px; float: right; background-image: url('/jsaas/styles/icons/arrowUp.png'); background-repeat: no-repeat;cursor: pointer;" onclick="hide('read','readShow','readHide')"></span> 
			<span id="readShow" style="height: 20px; width: 20px; float: right; background-image: url('/jsaas/styles/icons/arrowDown.png'); background-repeat: no-repeat;cursor: pointer;" onclick="show('read','readShow','readHide')"></span>
		</div>
		<div id="read" style="margin-top: 5px; margin-bottom: 10px; height: 310px;">
			<div id="reader" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/kms/core/kdDocRead/getReader.do?docId=${kdDoc.docId}" idField="Id" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="10" allowAlternating="true">
				<div property="columns">
					<div field="name" width="120" headerAlign="center" allowSort="true">用户名</div>
					<div field="status" width="120" headerAlign="center" allowSort="true">地图状态</div>
					<div field="depName" width="120" headerAlign="center" allowSort="true">部门名</div>
					<div field="readTime" width="120" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">发布日期</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 版本 -->
	<div style="width: 50%; margin-left: 100px; margin-right: 40%; margin-top: 30px; text-align: left;">
		<div style="font-weight: bold; font-size: 15px; color: #3E9CEF; border-bottom-color: #A7D3FB; border-bottom-width: 1px; border-bottom-style: solid;">
			<span style="margin-left: 15px; margin-bottom: 5px;">历史版本</span>
			<span id="hisHide" style="height: 20px; width: 20px; float: right; background-image: url('/jsaas/styles/icons/arrowUp.png'); background-repeat: no-repeat;cursor: pointer;" onclick="hide('his','hisShow','hisHide')"></span> 
			<span id="hisShow" style="height: 20px; width: 20px; float: right; background-image: url('/jsaas/styles/icons/arrowDown.png'); background-repeat: no-repeat;cursor: pointer;" onclick="show('his','hisShow','hisHide')"></span>
		</div>
		<div id="his" style="margin-top: 5px; margin-bottom: 10px; height: 150px;">
			<div id="version" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/kms/core/kdDocHis/getVersion.do?docId=${kdDoc.docId}" idField="hisId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="3" allowAlternating="true">
				<div property="columns">
					<div field="subject" width="120" headerAlign="center" allowSort="true">标题名</div>
					<div field="version" width="120" headerAlign="center" allowSort="true">版本号</div>
				</div>
			</div>
		</div>
	</div>


	<script>
        (function() {
          var $section = $('#focal');
          var $panzoom = $section.find('.panzoom').panzoom();
          $panzoom.parent().on('mousewheel.focal', function( e ) {
            e.preventDefault();
            var delta = e.delta || e.originalEvent.wheelDelta;
            var zoomOut = delta ? (delta < 0 ): (e.originalEvent.deltaY > 0);
            $panzoom.panzoom('zoom', zoomOut, {
              increment: 0.1,
              animate: false,
              focal: e
            });
          });
        })();
    </script>

	<script type="text/javascript">
		mini.parse();
		var readerGrid = mini.get("reader");
		var versionGrid = mini.get("version");
		readerGrid.load();
		versionGrid.load();
		var status = "${kdDoc.status}";
		var pkId = "${kdDoc.docId}";
		var kdDocId="${kdDoc.docId}";
		var cmmts = $("#pureCmmts").html();
		var cmmtArr = mini.decode(cmmts);
		var rems = $("#pureRems").html();
		var remArr = mini.decode(rems);

		//阅读记录表内容的显示
		readerGrid.on("drawcell", function(e) {
			field = e.field, value = e.value;
			var s = null;
			if (field == 'status') {
				if (value == "DRAFT") {
					s = "草稿";
				} else if (value == "ISSUED") {
					s = "发布";
				}
				e.cellHtml = s;
			}
		});
		
		$(function() {
		 	$('.jquery-note').jQueryNotes({
		 		allowReload:false,
		 		allowHide:false,
		 		allowCounter:false,
		 		allowEdit:false,
		 		allowDelete:false,
		 		allowAdd:false,
				operator:"${ctxPath}/kms/core/kdDoc/getPoint.do"
			});

		});
		
		//版本表内容的显示
		versionGrid.on("drawcell", function(e) {
			field = e.field, value = e.value;
			var s = null;
			if (field == 'subject') {
				s = '<a href="#" onclick="showHisDoc(\''+ e.record.hisId +'\')">' + value + '&nbsp;</a>';
				e.cellHtml = s;
				//window.open('${ctxPath}/kms/core/kdDoc/show.do?docId=' + pkId, '_blank');
			}
		});

	    //构造点评
		$(function() {
			for (var i = 0; i < cmmtArr.length; i++) {
				$('#comment').append("<li class='clearfix myli'><div style='font-size: 12px; padding-right: 16px; position: relative; text-align: center; width: 36px; float: left;'>头像</div><div class='clearfix' style='height: 50px; color: #888; float: left; width: 750px;'><div style='overflow: hidden; height: 26px;'>" + cmmtArr[i].cmmtName + "<span style='margin-left: 15px;'>" + cmmtArr[i].cmmtTime + "</span><div id='commentStar"+i+"' style='float: right;'></div></div><div style='margin-top: 10px; margin-bottom: 10px; padding-right: 15px;'>" + cmmtArr[i].content + "</div></div></li>");
			}
		});
		//构造推荐
		$(function() {
			for (var i = 0; i < remArr.length; i++) {
				$('#remList').append("<li class='clearfix myli'><div style='font-size: 12px; padding-right: 16px; position: relative; text-align: center; width: 36px; float: left;'>头像</div><div class='clearfix' style='height: 50px; color: #888; float: left; width: 750px;'><div style='overflow: hidden; height: 26px;'><span style='color:blue;'>" + remArr[i].remName + "</span><span style='margin-left: 15px;'>" + remArr[i].remTime + "</span><span style='margin-left: 15px;color: blue;'>被推荐人：" + remArr[i].remTarget + "</span><div id='remStar"+i+"' style='float: right;'></div></div><div style='margin-top: 10px; margin-bottom: 10px; padding-right: 15px;'>" + remArr[i].content + "</div></div></li>");
			}
		});

		//提交评论
		function sendCommt() {
			var content = $("#cmmtContent").val();
			var score = $('#target-div-demo').raty('score');
			var level = $('#target-div-hint').html();
			var isNotice = mini.get("notice");
			if (score == null) {
				mini.showTips({
					content : "尚未评分，请评分。",
					state : "danger",
					x : "center",
					y : "center",
					timeout : 3000
				});
				return;
			}
			_SubmitJson({
				url : "${ctxPath}/kms/core/kdDoc/sendCommt.do",
				data : {
					docId : pkId,
					content : content,
					score : score,
					level : level,
					isNotice : isNotice.checked
				},
				method : 'POST',
				success : function() {
					location.reload();
				}
			});
		}
		//默认隐藏展开按钮
		$(function() {
			$("#showbutton").hide();
			$("#cmmtHide").hide();
			$("#cmmt").hide();
			$("#remHide").hide();
			$("#rem").hide();
			$("#readHide").hide();
			$("#read").hide();
			$("#hisHide").hide();
			$("#his").hide();
		});
		//根据是否点评过显示点评框
		$(function() {
			var isCmmt = '${isCmmt}';
			if (isCmmt == 'true') {
				$("#myCmmt").hide();
			}
		});
		//根据是否是历史版本显示按钮栏
		$(function() {
			var isHis = '${isHis}';
			if (isHis == 'true') {
				$("#topButton").hide();
			}
		});

		//doc状态的显示
		$(function() {
			if (status == "DRAFT") {
				$('#status').html('草稿');
			} else if (status == "PENDING") {
				$('#status').html('待审');
			} else if (status == "ARCHIVED") {
				$('#status').html('归档');
			} else if (status == "ABANDON") {
				$('#status').html('废弃');
			} else if (status == "BACK") {
				$('#status').html('驳回');
			} else if (status == "ISSUED") {
				$('#status').html('发布');
			} else if (status == "OVERDUE") {
				$('#status').html('过期');
			}
		});

		//右上角按钮的显示
		$(function() {
			var isEdit = "${isEdit}";
			if (isEdit == "false") {//判断是否有编辑权限
				$('#edit').hide();
				$('#newVersion').hide();
			}
			if (status == "DRAFT") {
				$('#newVersion').hide();
			} else if (status == "ISSUED") {
				$('#edit').hide();
				$('#publish').hide();
			}

		});

		//打星
		$(function() {
			$.fn.raty.defaults.path = '${ctxPath}/scripts/kms/starDemo/lib/img';
			var score = "${score}";
			$('#readOnly-demo').raty({
				readOnly : true,
				score : score
			});
			for (var i = 0; i < cmmtArr.length; i++) {
				$('#commentStar' + i).raty({
					readOnly : true,
					score : cmmtArr[i].score
				});
			}

			for (var i = 0; i < remArr.length; i++) {
				$('#remStar' + i).raty({
					readOnly : true,
					score : remArr[i].score
				});
			}

			$('#target-div-demo').raty({
				target : '#target-div-hint',
				targetKeep : true
			});
			$('#remStar').raty({
				target : '#rem-text',
				targetKeep : true
			});
		});
		//点击展开
		function show(text,showButton,hideButton) {
			$('#'+ text).show();
			$('#'+ showButton).hide();
			$('#'+ hideButton).show();
		}
		//点击收起
		function hide(text,showButton,hideButton) {
			$('#'+ text).hide();
			$('#'+ showButton).show();
			$('#'+ hideButton).hide();
		}
		
		//点击推荐知识
		function remDoc() {
			_OpenWindow({
				url : "${ctxPath}/kms/core/kdDocRem/show.do?docId=" + pkId,
				title : "推荐地图",
				width : 600,
				height : 355,
				iconCls : 'icon-newMsg',
				allowResize : false
			});
		}

		//点击发布
		function publish() {
			_SubmitJson({
				url : "${ctxPath}/kms/core/kdDoc/publish.do",
				data : {
					docId : pkId,
				},
				method : 'POST',
				success : function() {
					alert("成功发布！");
					location.reload();
				}
			});
		}
		//点击新版本
		function newVersion() {
			$.ajax({
				type : "POST",
				url : "${ctxPath}/kms/core/kdDoc/newVersion.do",
				async : false,
				data : {
					docId : pkId,
				},
				success : function() {
					window.location.href = "${ctxPath}/kms/core/kdDoc/mapMgrEdit.do?pkId=" + pkId;
				}
			});
		}

		//收藏
		function favorite() {
			_SubmitJson({
				url : "${ctxPath}/kms/core/kdDoc/favorite.do",
				data : {
					docId : pkId,
				},
				method : 'POST',
				showMsg : false,
				success : function(result) {
					alert(result.message);
				},

			});
		}

		//推荐的添加用户
		function addPerson(type) {
			var infUser = mini.get(type);
			_UserDlg(false, function(users) {//打开收信人选择页面,返回时获得选择的user的Id和name
				var uIds = [];
				var uNames = [];
				//将返回的选择分别存起来并显示
				for (var i = 0; i < users.length; i++) {
					var flag = true;
					users[i].userId = users[i].userId + "_user";
					var oldValues = infUser.getValue().split(',');
					var oldText = infUser.getText().split(',');
					for (var j = 0; j < oldValues.length; j++) {
						if (oldValues[j] == users[i].userId && oldText[j] == users[i].fullname) {
							flag = false;
							break;
						}
					}
					if (flag == true) {
						uIds.push(users[i].userId);
						uNames.push(users[i].fullname);
					}
				}
				if (infUser.getValue() != '') {
					uIds.unshift(infUser.getValue().split(','));
				}
				if (infUser.getText() != '') {
					uNames.unshift(infUser.getText().split(','));
				}
				infUser.setValue(uIds.join(','));
				infUser.setText(uNames.join(','));
			});
		}
		//推荐中的添加组
		function addGroup(type) {
			var infGroup = mini.get(type);
			_GroupDlg(false, function(groups) {
				var uIds = [];
				var uNames = [];
				for (var i = 0; i < groups.length; i++) {
					var flag = true;
					groups[i].groupId = groups[i].groupId + "_group";
					var oldValues = infGroup.getValue().split(',');
					var oldText = infGroup.getText().split(',');
					for (var j = 0; j < oldValues.length; j++) {
						if (oldValues[j] == groups[i].groupId && oldText[j] == groups[i].name) {
							flag = false;
							break;
						}
					}
					if (flag == true) {
						uIds.push(groups[i].groupId);
						uNames.push(groups[i].name);
					}
				}
				if (infGroup.getValue() != '') {
					uIds.unshift(infGroup.getValue().split(','));
				}
				if (infGroup.getText() != '') {
					uNames.unshift(infGroup.getText().split(','));
				}
				infGroup.setValue(uIds.join(','));
				infGroup.setText(uNames.join(','));
			});
		}

		//提交推荐
		function sendRem() {
			var userId = mini.get("remReceive").getValue();
			var content = $("#remContent").val();
			var score = $('#remStar').raty('score');
			if (userId == "") {
				mini.showTips({
					content : "请选择推荐对象。",
					state : "danger",
					x : "center",
					y : "center",
					timeout : 3000
				});
				return;
			}
			if (score == null) {
				mini.showTips({
					content : "尚未评分，请评分。",
					state : "danger",
					x : "center",
					y : "center",
					timeout : 3000
				});
				return;
			}
			_SubmitJson({
				url : "${ctxPath}/kms/core/kdDocRem/submitRem.do",
				data : {
					docId : pkId,
					userId : userId,
					content : content,
					score : score,
				},
				method : 'POST',
				success : function() {
					location.reload();
				}
			});
		}

		//清楚推荐选择框
		function clearAll() {
			var infUser = mini.get('remReceive');
			infUser.setValue("");
			infUser.setText("");
		}
		
		//点击标题跳转
		function showHisDoc(hisId) {
			var pkId = hisId;
			alert(pkId);
			window.open('${ctxPath}/kms/core/kdDoc/hisShow.do?hisId=' + pkId, '_blank');
		}
		
		function openDocsInNote(docIds){
			_OpenWindow({
				title:'文档列表',
				url:__rootPath+'/kms/core/kdDoc/docsInMapList.do?docIds='+docIds,
				width:350,
				height:250
			});
		}

		function todo() {
			alert("敬请期待！");
		}
	</script>
</body>
</html>