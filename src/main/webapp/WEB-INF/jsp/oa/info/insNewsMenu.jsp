<%-- 
    Document   : [HrDutyRegister]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[HrDutyRegister]列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>

<style>
html,body {
	width: 100%;
	height: 230px;
	border: 0;
	margin: 0;
	padding: 0;
	overflow: visible;
}

.bgbtn div {
	margin-left: 20px;
	padding-left: 20px;
}

.bgbtn span {
	margin-left: 20px;
	padding-left: 20px;
	color: #aaa;
}

.btn {
	display: inline-block;
	margin-top: 25px;
	margin-left: 25px;
	padding: 10px 24px;
	border-radius: 5px;
	background-color: #63b7ff;
	color: #36cc82;
	cursor: pointer;
	text-decoration: none;
	background: url(../../../styles/images/portal/user.png) 10% center
		no-repeat;
	border: 1px solid #c3c3c3;
	padding: 10px 24px
}

.btn:hover {
	background-color: #99c6ff;
}

.btn {
	font-style: normal;
}
</style>
</head>

<body>
	<div>
		<a href="" class="bgbtn btn">
			<div>用户管理</div> <span>user Manager</span>
		</a>
		 <a href="" class="bgbtn btn">
			<div>用户管理</div> <span>user Manager</span>
		</a>
	</div>
	<div>
		<a href="" class="bgbtn btn">
			<div>用户管理</div> <span>user Manager</span>
		</a> <a href="" class="bgbtn btn">
			<div>用户管理</div> <span>user Manager</span>
		</a>
	</div>
	<script type="text/javascript">
		mini.parse();
	</script>
</body>
</html>