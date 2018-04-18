<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/dynamic.jspf"%>
<title>${sysInst.nameCn}-注册成功</title>
<style type="text/css">
	p{
		font-size:14px;
		text-indent: 30px;
	}
</style>
</head>
<body>
<div style="width:100%;">
        <div id="p1" class="form-outer" style="border:solid 1px #ccc;padding:10px;height:300px;width:800px;margin-left: auto;margin-right: auto;line-height: 32px;">
      		<div>
      		<h2>注册成功</h2>
      		<p>
      			请用登录您注册的邮箱（${sysInst.email}）以完成注册，或联系管理员进行激活企业的账号。
      		</p>
      		<p>
      			然后访问以下地址登录：
      			<a href="${loginUrl}">${loginUrl}</a>
      		</p>
      		<div style="text-align: center">
      			<img src="${ctxPath}/styles/images/success.gif" alt="注册成功"/>
      		</div>
        </div>
</div>
</body>
</html>