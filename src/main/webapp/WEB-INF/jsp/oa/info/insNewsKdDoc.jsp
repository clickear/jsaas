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
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/scripts/kms/xuanZhuanMuMa/picStyle.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/kms/xuanZhuanMuMa/stepcarousel.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/kms/homePic/jquery.slideBox.js" type="text/javascript"></script>
<link href="${ctxPath}/scripts/kms/homePic/jquery.slideBox.css" rel="stylesheet" type="text/css" />

<script>
	//图片循环展示
	jQuery(function($) {
		$('#demo1').slideBox();
	});
</script>
</head>

<body>
	<!-- 带图片的循环播放  -->
	<div id="demo1" class="slideBox">
		<ul class="items">
			<c:forEach items="${remDoc}" var="remDoc">
				<li style="width: 700px; height: 250px;"><a href="#" onclick="showDoc('${remDoc.docId}')" target="_blank" title="${remDoc.title}"><img src="${ctxPath}${remDoc.picUrl}"></a></li>
			</c:forEach>
		</ul>
	</div>

	<script type="text/javascript">
		//点击标题跳转
		function showDoc(docId) {
			var pkId = docId;
			window.open('${ctxPath}/kms/core/kdDoc/show.do?docId=' + pkId, '_blank');
		}
	</script>

</body>
</html>