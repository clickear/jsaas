<%-- 
    Document   : [KdQuestion]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>咨询专家</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/zszk.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/knowl/knowl.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/uploadpanel/jquery-uploadpanel.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/css/kdUserPersonalGet.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/scripts/kms/shoufengqin/font-awesome.min.css" rel="stylesheet" />
<link href="${ctxPath}/scripts/kms/shoufengqin/shoufengqinstyle.css" rel="stylesheet" media="screen" type="text/css" />
<script src="${ctxPath}/scripts/kms/shoufengqin/index.js" type="text/javascript"></script>


</head>
<body>
	<ul style="float: left; margin-left: 100px;" id="accordion" class="accordion">
		<li>
			<div class="link">
				<i class="fa"></i>个人资料<i class="fa fa-chevron-down"></i>
			</div>
			<ul class="submenu">
				<li><a href="${ctxPath}/kms/core/kdUser/personalGet.do?i=4&userId=${userId}">我的个人资料</a></li>
			</ul>
		</li>
		<li>
			<div class="link">
				<i class="fa"></i>我的知识文库<i class="fa fa-chevron-down"></i>
			</div>
			<ul class="submenu">
				<li><a href="${ctxPath}/kms/core/kdDoc/personal.do">我的知识文库</a></li>
				<li><a href="${ctxPath}/kms/core/kdQuestion/personal.do?i=4&tab=index&type=ALL">我的知识问答</a></li>
				<li><a href="${ctxPath}/kms/core/kdDoc/mapPersonal.do">我的知识地图</a></li>
				<li><a href="#" onclick="todo()">我的知识积分</a></li>
			</ul>
		</li>
	</ul>
	
	<div class="main-container">
		<div class="left-container">
			<div class="left-box">
				<div class="user-pic">
					<img width="80" height="100" src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${kdUser.headId}" />
				</div>
				<div class="user-name">
					<a class="link">${kdUser.fullname}</a>
				</div>
			</div>
		</div>
		
		<div class="right-box">
			<a class="edit" href="${ctxPath}/kms/core/kdUser/personalEdit.do?i=4&userId=${kdUser.user.userId}">编辑</a>
			<div class="line"></div>
			<div class="profile-box">
				<div class="title">用户名片</div>
				<div class="info-box">
					<div class="value-key">姓名</div>
					<div class="value-data">${kdUser.fullname}</div>
				</div>
				<div class="info-box">
					<div class="value-key">性别</div>
					<div class="value-data"><c:if test="${kdUser.sex=='male'}">男</c:if><c:if test="${kdUser.sex=='female'}">女</c:if></div>
				</div>
				
				<div class="info-box">
					<div class="value-key">用户类型</div>
					<div class="value-data"><c:if test="${kdUser.userType=='DOMAIN'}">领域专家</c:if><c:if test="${kdUser.userType=='PERSON'}">专家个人</c:if></div>
				</div>

				<div class="info-box">
					<div class="value-key">积分</div>
					<div class="value-data">${kdUser.point}</div>
				</div>
				
				<div class="info-box">
					<div class="value-key">等级</div>
					<div class="value-data">${kdUser.grade}</div>
				</div>
				
				<div class="info-box">
					<div class="value-key">知识领域</div>
					<div class="value-data">${kdUser.knSysTree.name}</div>
				</div>
				
				<div class="info-box">
					<div class="value-key">爱问领域</div>
					<div class="value-data">${kdUser.reqSysTree.name}</div>
				</div>
				
				<div class="info-box">
					<div class="value-key">个性签名</div>
					<div class="value-data">${kdUser.sign}</div>
				</div>
				
				<div class="info-box">
					<div class="value-key">个人简介</div>
					<div class="value-data">${kdUser.profile}</div>
				</div>
				
				<div class="info-box">
					<div class="value-key">办公电话</div>
					<div class="value-data">${kdUser.officePhone}</div>
				</div>
				
				<div class="info-box">
					<div class="value-key">手机号码</div>
					<div class="value-data">${kdUser.mobile}</div>
				</div>
				
				<div class="info-box">
					<div class="value-key">电子邮箱</div>
					<div class="value-data">${kdUser.email}</div>
				</div>
			</div>
		</div>
		<div style="clear:both;"></div>
	</div>
	

	
	<script type="text/javascript">
	mini.parse();
		

	</script>
</body>
</html>