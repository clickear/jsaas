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
<%@include file="/commons/dynamic.jspf" %>
<link href="${ctxPath}/styles/css/ditu.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/zszk.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
</head>

<body style="min-width: 1600px;">
	
	<div class="container">
  <div class="side">
    <div id="leftMenu" class="menu">
      
    </div>
    <div class="linkbtn"> <a href="#" onclick="newDoc()" class="fl" style="margin-left: 25%;">我要分享</a></div>
  </div>
  <div class="main">
    <div class="zuixin">
      <div class="daohang">
        <img src="${ctxPath}/styles/images/knowl/a.png" /> <span>最新地图</span> <a href="#" class="more"></a>
      </div>
      <img class="img1" src="${ctxPath}/styles/images/knowl/left.png" width="40" height="40" />
      <img class="img2" src="${ctxPath}/styles/images/knowl/right.png" width="40" height="40" />
      <div class="rollBox">
        <div class="Cont">
          <div class="ScrCont">
            <div class="List1"> 
              <c:forEach items="${newestDoc}" var="newestDoc">
              	<div class="pic"> <a href="${ctxPath}/kms/core/kdDoc/mapShow.do?docId=${newestDoc.docId}" target="_blank"><img src="${ctxPath}${newestDoc.picUrl}" width="215" height="160" title="${newestDoc.summary}" /></a><p>${newestDoc.title}</p> </div> 
		      </c:forEach>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="zuire">
      <div class="daohang"> <img src="${ctxPath}/styles/images/knowl/a.png" /> <span>最热地图</span> <a href="#" class="more"></a> </div>
      <img   class="img1" src="${ctxPath}/styles/images/knowl/left.png" width="40" height="40" /> <img    class="img2" src="${ctxPath}/styles/images/knowl/right.png" width="40" height="40" />
      <div class="rollBox">
        <div class="Cont" >
          <div class="ScrCont">
            <div class="List1"> 
              
             <c:forEach items="${hotestDoc}" var="hotestDoc">
              	<div class="pic"> <a href="${ctxPath}/kms/core/kdDoc/mapShow.do?docId=${hotestDoc.docId}" target="_blank"><img src="${ctxPath}${hotestDoc.picUrl}" width="215" height="160" title="${hotestDoc.summary}" /></a><p>${hotestDoc.title}</p> </div> 
		      </c:forEach>
              <div></div>
            </div>
          </div>
        </div>
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
			for (var i = 0; i < menusArr.length; i++) {
				var secondMenus = menusArr[i].lowerMenu;
				if(secondMenus.length == 0){
					lMenus = lMenus + "<div><p class='cat' id='" + menusArr[i].id + "'><img src='${ctxPath}/styles/images/knowl/menu1.png' />"+ menusArr[i].name +"</p>";
				}else{
					lMenus = lMenus + "<div><p class='cat' id='" + menusArr[i].id + "'><img src='${ctxPath}/styles/images/knowl/menu1.png' />"+ menusArr[i].name +"</p><div class='secondmenu'>";
				}
				for (var j = 0; j < secondMenus.length; j++) {
					var thirdMenus = secondMenus[j].lowerMenu;
					lMenus = lMenus + "<div class='cate cat' id='" + secondMenus[j].id + "'>" + secondMenus[j].name + "</div><ul>";
					for (var k = 0; k < thirdMenus.length; k++) {
						lMenus = lMenus + "<li class='cat' id='" + thirdMenus[k].id + "'>" + thirdMenus[k].name + "</li>";
					}
					lMenus = lMenus + "</ul>";
				}
				lMenus = lMenus + "</div></div>";
			}
			$('#leftMenu').append(lMenus);
			$(".cat").on("click", function() {
				window.location.href = "${ctxPath}/kms/core/kdDoc/mapSearch.do?i=2&treeId=" + $(this).attr("id");
			});
		});
		

		$(".more").click(function(){
			window.location.href="${ctxPath}/kms/core/kdDoc/mapSearch.do?i=2";
		});
		
		//新增地图
		function newDoc() {
			window.open("${ctxPath}/kms/core/kdDoc/mapNew1.do");
		}
	</script>
	<script src="${ctxPath}/scripts/knowl/knowl.js" type="text/javascript"></script>
</body>
</html>