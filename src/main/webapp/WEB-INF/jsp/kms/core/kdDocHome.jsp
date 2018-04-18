<%-- 
    Document   : 知识系统首页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">
<title>首页</title>
<%@include file="/commons/list.jsp"%>
<link href="${ctxPath}/styles/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/zszk.css" rel="stylesheet" type="text/css" />

<style type="text/css">
.topMenuItemLink{
	text-decoration: none;
	color: #fff;
}
</style>
</head>

<body>
	<div class="nav maincolor">
	        <ul>
	            <li class="active"><a class="topMenuItemLink" href="${ctxPath}/kms/core/kdDoc/home.do?i=0">首页</a></li>
	            <li><a class="topMenuItemLink" href="${ctxPath}/kms/core/kdDoc/index.do?i=1">知识仓库</a></li>
	            <li><a class="topMenuItemLink" href="${ctxPath}/kms/core/kdDoc/mapIndex.do?i=2">知识地图</a></li>
	            <li><a class="topMenuItemLink" href="${ctxPath}/kms/core/kdQuestion/index.do?i=3">知识问答</a></li>
	            <li><a class="topMenuItemLink" href="${ctxPath}/kms/core/kdDoc/personal.do?i=4">个人中心</a></li>
	        </ul>
	</div>
    <script type="text/javascript">
    	$(function(){
    		var index="${param['i']}";
    		$(".nav").find("li").each(function(i){
    			   $(this).removeClass("active");
    		 });
    		$(".nav").find("li").eq(index).addClass("active");
    	});
    </script>

	<div class="container">
		<div class="side">
			<div id="leftMenu" class="menu">
				
			</div>

			<div class="linkbtn">
				<a href="#" class="fl" onclick="newDoc()">我要分享</a> <a href="#" class="fr" onclick="newQue()">我要提问</a>
			</div>
			<div class="rank">
				<ul>
					<li class="active">知识积分排行</li>
					<li>问答积分排行</li>
				</ul>
				<div class="wrap">
					<table>
						<colgroup>
							<col style="width: 25%" />
							<col style="width: 37%" />
							<col style="width: 38%" />
						</colgroup>
						<thead>
							<tr>
								<td>排名</td>
								<td>名字</td>
								<td>积分</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${totalScore}" var="totalScore" varStatus="i">
								<tr>
									<td>${i.index+1}</td>
									<td>${totalScore.fullname }</td>
									<td>${totalScore.docScore }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table>
						<colgroup>
							<col style="width: 25%" />
							<col style="width: 37%" />
							<col style="width: 38%" />
						</colgroup>
						<thead>
							<tr>
								<td>排名</td>
								<td>名字</td>
								<td>积分</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${totalPoint}" var="totalPoint" varStatus="i">
								<tr>
									<td>${i.index+1}</td>
									<td>${totalPoint.fullname }</td>
									<td>${totalPoint.point }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="main">
			<!--放主体内容-->
			<div class="banner">
				<ul>
					<c:forEach items="${remDoc}" var="remDoc">
						<li><a href="#" onclick="showDoc('${remDoc.docId}')" target="_blank" title="${remDoc.title}"><img src="${ctxPath}${remDoc.picUrl}" /></a></li>
					</c:forEach>
				</ul>
				<div class="bannertip">
					<div class="bannerbg"></div>
					<div class="wenzi fl">
						<c:forEach items="${remDoc}" var="remDoc">
							<p>${remDoc.title}</p>
						</c:forEach>
					</div>
					<div class="silder">
						<c:forEach items="${remDoc}" var="remDoc" varStatus="n">
							<c:choose>
								<c:when test="${n.index==0}">
									<div class="active"></div>
								</c:when>
								<c:otherwise>
									<div></div>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="zhishi">
				<div class="daohang daohang1">
					<ul class="module-title market-title clearfix">
						<li class="first">最新知识</li>
						<li>最热知识</li>
					</ul>
					<a href="${ctxPath}/kms/core/kdDoc/search.do?i=1" class="more"></a>
				</div>
				<div class="list list1">
					<ul class="list-mian">
						<c:forEach items="${newdocs}" var="newdocs">
							<li>
								<p class="fl">
									<em class="fl"> <a href="#">${newdocs.sysTree.name}</a>
									</em> <span class="docItem" id="${newdocs.docId}" style="cursor: pointer;">${newdocs.subject}</span>
								</p> <span class="fr"> <span class="bloder">${newdocs.author}</span> <span class="time"><fmt:formatDate value="${newdocs.issuedTime}" pattern="yyyy-MM-dd" /></span>
							</span>
							</li>
						</c:forEach>
					</ul>

					<ul class="list-mian ">
						<c:forEach items="${hotdocs}" var="hotdocs">
							<li>
								<p class="fl">
									<em class="fl"> <a href="#">${hotdocs.sysTree.name}</a>
									</em> <span class="docItem" id="${hotdocs.docId}" style="cursor: pointer;">${hotdocs.subject}</span>
								</p> <span class="fr"> <span class="bloder">${hotdocs.author}</span> <span class="time"><fmt:formatDate value="${hotdocs.issuedTime}" pattern="yyyy-MM-dd" /></span>
							</span>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="wenda">
				<div class="daohang daohang2">
					<ul class="module-title market-title clearfix">
						<li class="first">最新问答</li>
						<li>最热问答</li>
						<li>未解决问答</li>
						<li>最佳问答</li>
					</ul>
					<a href="${ctxPath}/kms/core/kdQuestion/find.do?i=3" class="more"></a>
				</div>
				<div class="list list2">
					<ul class="list-mian ">
						<c:forEach items="${latestQuestions}" var="latestQuestion" varStatus="status">
							<li>
								<p class="fl">
									<em class="fl"> <a href="${ctxPath}/kms/core/kdQuestion/find.do?catId=${latestQuestion.sysTree.treeId}">${latestQuestion.sysTree.name}</a>
									</em> <span class="queItem" id="${latestQuestion.queId}" style="cursor: pointer;">${latestQuestion.subject}</span>
								</p> <span class="fr"> <span class="bloder">(回复数:${latestQuestion.replyCounts})</span> <span class="time"><fmt:formatDate value="${latestQuestion.createTime}" pattern="yyyy-MM-dd" /></span>
							</span>
							</li>
						</c:forEach>
					</ul>
					<ul class="list-mian ">
						<c:forEach items="${hostestQuestions}" var="hostestQuestion" varStatus="status">
							<li>
								<p class="fl">
									<em class="fl"> <a href="${ctxPath}/kms/core/kdQuestion/find.do?catId=${hostestQuestion.sysTree.treeId}">${hostestQuestion.sysTree.name}</a>
									</em> <span class="queItem" id="${hostestQuestion.queId}" style="cursor: pointer;">${hostestQuestion.subject}</span>
								</p> <span class="fr"> <span class="bloder">(回复数:${hostestQuestion.replyCounts})</span> <span class="time"><fmt:formatDate value="${hostestQuestion.createTime}" pattern="yyyy-MM-dd" /></span>
							</span>
							</li>
						</c:forEach>
					</ul>
					<ul class="list-mian ">
						<c:forEach items="${unsolvedQuestions}" var="unsolvedQuestion" varStatus="status">
							<li>
								<p class="fl">
									<em class="fl"> <a href="${ctxPath}/kms/core/kdQuestion/find.do?catId=${unsolvedQuestion.sysTree.treeId}">${unsolvedQuestion.sysTree.name}</a>
									</em> <span class="queItem" id="${unsolvedQuestion.queId}" style="cursor: pointer;">${unsolvedQuestion.subject}</span>
								</p> <span class="fr"> <span class="bloder">(回复数:${unsolvedQuestion.replyCounts})</span> <span class="time"><fmt:formatDate value="${unsolvedQuestion.createTime}" pattern="yyyy-MM-dd" /></span>
							</span>
							</li>
						</c:forEach>
					</ul>
					<ul class="list-mian ">
						<c:forEach items="${bestQuestions}" var="bestQuestion" varStatus="status">
							<li>
								<p class="fl">
									<em class="fl"> <a href="${ctxPath}/kms/core/kdQuestion/find.do?catId=${bestQuestion.sysTree.treeId}">${bestQuestion.sysTree.name}</a>
									</em> <span class="queItem" id="${bestQuestion.queId}" style="cursor: pointer;">${bestQuestion.subject}</span>
								</p> <span class="fr"> <span class="bloder">(回复数:${bestQuestion.replyCounts})</span> <span class="time"><fmt:formatDate value="${bestQuestion.createTime}" pattern="yyyy-MM-dd" /></span>
							</span>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>




	<script type="text/javascript">
		//左边目录
		var leftMenu = '${leftMenu}';
		var menusArr = mini.decode(leftMenu);
		$(function() {
			var lMenus = "";
			//lMenus = lMenus + "<div class='sidebar'><div id='allSearch' class='sidebar_top sidebar_top_tc'>分类导航</div><div class='sidebar_con'><dl class='sidebar_item'>";
			for (var i = 0; i < menusArr.length; i++) {
				var secondMenus = menusArr[i].lowerMenu;
				if(secondMenus.length == 0){
					lMenus = lMenus + "<div><p class='cat' id='" + menusArr[i].id + "'><img src='${ctxPath}/styles/images/knowl/menu1.png' />"+ menusArr[i].name +"</p>";
				}else{
					lMenus = lMenus + "<div><p class='cat' id='" + menusArr[i].id + "'><img src='${ctxPath}/styles/images/knowl/menu1.png' />"+ menusArr[i].name +"</p><div class='secondmenu'>";
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
		
		$(".queItem").click(function(){
			window.open("${ctxPath}/kms/core/kdQuestion/get.do?i=3&pkId="+$(this).attr("id"));   
		});
		
		$(".docItem").click(function(){
			window.open("${ctxPath}/kms/core/kdDoc/show.do?i=1&docId="+$(this).attr("id"));   
		});

		
		//新增知识文档
		function newDoc() {
			window.open("${ctxPath}/kms/core/kdDoc/new1.do");
		}
		//新增知识问答
		function newQue() {
			window.open("${ctxPath}/kms/core/kdQuestion/edit.do?i=3");
		}
	</script>
	<script src="${ctxPath}/scripts/knowl/knowl.js" type="text/javascript"></script>
</body>
</html>