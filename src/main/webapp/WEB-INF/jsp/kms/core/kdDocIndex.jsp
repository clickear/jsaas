<%-- 
    Document   : 
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>首页</title>
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/css/ditu.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/zszk.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>

</head>

<body style="min-width: 1600px;">

	<div class="container">
		<div class="side">
			<div id="leftMenu" class="menu"></div>

			<div class="linkbtn">
				<a href="#" class="fl" style="margin-left: 25%;" onclick="newDoc()">我要分享</a>
			</div>
		</div>

		<!-- 右侧图片轮放 -->
		<div class="main">
			<div class="zuixin">
				<div class="daohang">
					<img src="${ctxPath}/styles/images/knowl/a.png" /> <span>最新知识</span> <a href="${ctxPath}/kms/core/kdDoc/search.do?i=1" class="more"></a>
				</div>
				<img class="img1" src="${ctxPath}/styles/images/knowl/left.png" width="40" height="40" /> <img class="img2" src="${ctxPath}/styles/images/knowl/right.png" width="40" height="40" />
				<div class="rollBox">
					<div class="Cont">
						<div class="ScrCont">
							<div class="List1">
								<c:forEach items="${newestDoc}" var="newestDoc">
									<div class="pic">
										<a href="${ctxPath}/kms/core/kdDoc/show.do?docId=${newestDoc.docId}" target="_blank"><img src="${ctxPath}${newestDoc.picUrl}" width="215" height="160" title="${newestDoc.summary}" /></a>
									</div>
								</c:forEach>
								<!-- 图片列表 end -->
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="zuire">
				<div class="daohang">
					<img src="${ctxPath}/styles/images/knowl/a.png" /> <span>最热地图</span> <a href="${ctxPath}/kms/core/kdDoc/search.do?i=1" class="more"></a>
				</div>
				<img class="img1" src="${ctxPath}/styles/images/knowl/left.png" width="40" height="40" /> <img class="img2" src="${ctxPath}/styles/images/knowl/right.png" width="40" height="40" />
				<div class="rollBox">
					<div class="Cont">
						<div class="ScrCont">
							<div class="List1">

								<!-- 图片列表 begin -->
								<c:forEach items="${hotestDoc}" var="hotestDoc">
									<div class="pic">
										<a href="${ctxPath}/kms/core/kdDoc/show.do?docId=${hotestDoc.docId}" target="_blank"><img src="${ctxPath}${hotestDoc.picUrl}" width="215" height="160" title="${hotestDoc.summary}" /></a>
									</div>
								</c:forEach>
								<!-- 图片列表 end -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<%-- <div style="margin: 10px 0 0 350px; background: #fff; border-top: 2px solid #cacaca; height: 250px; width: 1000px;">
		<div class="sidebar_top sidebar_top_tc">精华知识</div>
		<div id="essenceMygallery" class="stepcarousel">
			<div class="belt" id="essenceDisplaycssbelt">
				<c:forEach items="${essenceDoc}" var="essenceDoc">
					<div class="panel">
						<div class="subfeature">
							<a href="#" onclick="showDoc('${essenceDoc.docId}')" target="_blank"><img src="${ctxPath}${essenceDoc.picUrl}" alt="butterflies-are-gross" class="post-image" width="200" height="150" title="${essenceDoc.summary}" /></a>
							<div class="subfeature-txt">
								<h2>
									<a href="#" onclick="showDoc('${essenceDoc.docId}')" target="_blank">${essenceDoc.title}</a>
								</h2>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div> --%>

	<script type="text/javascript">
		//左边目录
		var leftMenu = '${leftMenu}';
		var menusArr = mini.decode(leftMenu);
		$(function() {
			var lMenus = "";
			//lMenus = lMenus + "<div class='sidebar'><div id='allSearch' class='sidebar_top sidebar_top_tc'>分类导航</div><div class='sidebar_con'><dl class='sidebar_item'>";
			for (var i = 0; i < menusArr.length; i++) {
				var secondMenus = menusArr[i].lowerMenu;
				if (secondMenus.length == 0) {
					lMenus = lMenus + "<div ><p class='cat' id='" + menusArr[i].id + "'><img src='${ctxPath}/styles/images/knowl/menu1.png' />" + menusArr[i].name + "</p>";
				} else {
					lMenus = lMenus + "<div ><p class='cat' id='" + menusArr[i].id + "'><img src='${ctxPath}/styles/images/knowl/menu1.png' />" + menusArr[i].name + "</p><div class='secondmenu'>";
				}
				for (var j = 0; j < secondMenus.length; j++) {
					var thirdMenus = secondMenus[j].lowerMenu;
					lMenus = lMenus + "<div class='cate cat' id='" + secondMenus[j].id + "' href='#'>" + secondMenus[j].name + "</div><ul>";
					for (var k = 0; k < thirdMenus.length; k++) {
						lMenus = lMenus + "<li class='cat' id='" + thirdMenus[k].id + "' href='#'>" + thirdMenus[k].name + "</li>";
					}
					lMenus = lMenus + "</ul>";
				}
				lMenus = lMenus + "</div></div>";
			}
			$('#leftMenu').append(lMenus);
			$(".cat").on("click", function() {
				window.location.href = "${ctxPath}/kms/core/kdDoc/search.do?i=1&catId=" + $(this).attr("id");
			});
		});

		//新增知识
		$("#newDoc").click(function() {
			window.open("${ctxPath}/kms/core/kdDoc/new1.do");
		});

		//点击标题跳转
		function showDoc(docId) {
			var pkId = docId;
			window.open('${ctxPath}/kms/core/kdDoc/show.do?docId=' + pkId, '_blank');
		}

		//新增知识
		function newDoc() {
			window.open("${ctxPath}/kms/core/kdDoc/new1.do");
		}
	</script>
	<script src="${ctxPath}/scripts/knowl/knowl.js" type="text/javascript"></script>
</body>
</html>