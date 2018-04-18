<%@ page pageEncoding="UTF-8" %>
<%
	//用于ajax请求时，其响应时进行回写标识，以使前台可以跳到登录页
	response.setHeader("timeout", "true");
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/dynamic.jspf" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport"> 
<link rel="shortcut icon" href="${ctxPath}/styles/images/index/icon.ico">
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/login02.css"/>
<script src="${ctxPath}/scripts/jquery-1.11.3.js" type="text/javascript"></script> 
<script src="${ctxPath}/scripts/jquery/jquery.cookie.js" type="text/javascript"></script> 
<script src="${ctxPath}/scripts/jquery/plugins/jquery-cookie.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/common/sha256.js" type="text/javascript"></script>
<title>红迅JSAAS敏捷开发平台--用户登录</title>
<script type="text/javascript">
if(top!=this){//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面
	top.location='${ctxPath}/login.jsp';
}
</script>
</head>
<body>
	<div class="content_bg">
			<div class="content_l">
				<span>
					<img src="${ctxPath}/styles/images/login/logo.png" alt="logo">
					<p style="width:550px;text-align:left;">
					红迅JSAAS平台实现对企业的核心数据资产如主数据、数据单据及展示、
					组织架构、流程审批、数据报表等一体化运营、智能化管理。
					</p>
				</span>
				<img src="${ctxPath}/styles/images/login/login02_icon00.png" class="icon00">
				<img src="${ctxPath}/styles/images/login/login02_icon01.png" class="icon01">
				<img src="${ctxPath}/styles/images/login/login02_icon02.png" class="icon02">
				<img src="${ctxPath}/styles/images/login/login02_icon03.png" class="icon03">
				<img src="${ctxPath}/styles/images/login/login02_icon04.png" class="icon04">
				<img src="${ctxPath}/styles/images/login/login02_icon05.png" class="icon05">
				<img src="${ctxPath}/styles/images/login/login02_icon06.png" class="icon06">
			</div>
			<div class="content_r">
				<div>
					<p>用户登录</p>
					<div class="clearfix"></div>
				</div>
				<dl>
					<dd>
						<img src="${ctxPath}/styles/images/login/login02_r_icon02.png" alt="">
						<input type="text"   id="u1" value="登录账号" onfocus="if(value=='登录账号'){value=''}" onblur="if(value==''){value='登录账号'}">
						<div class="clearfix"></div>
					</dd>
					<dd style="margin: 34px 0 10px 0">
						<img src="${ctxPath}/styles/images/login/login02_r_icon03.png" alt="">
						<input type="password"   id="p1" value="">
						<div class="clearfix"></div>
					</dd>
				</dl>
				<span>
					<input type="checkbox" id="rememberMe">
					<h1>记住密码</h1>
					<h2>忘记密码?</h2>
					<div class="clearfix"></div>
				</span>
				<p>
					<input type="button" value="登   录" class="Login" onclick="onLoginClick()" id="Login">
					<input type="button" value="重   置" class="chongzhi" onclick="reset()">
					<div class="clearfix"></div>
				</p>
				<h4>
					新企业？<a href="${ctxPath}/register.jsp">注册企业账号。</a>
				</h4>
			</div>
			<div class="clearfix"></div>
		</div>


	<script type="text/javascript">
		$(function(){
			var username=$.getCookie('username');	
			if(username){
				$("#username").val(unescape(username));
			}
		});
		function onLoginClick(e) {
            var loginTime = setTimeout(function() {
            	$('.loadingBox').show();
            }, 800);
			var u1=$("#u1").val(),
		  		p1=$("#p1").val(),
		  		rememberMe=$("#rememberMe").is(':checked')?"1":"0";
		  	if( u1=='' || p1=='' ){
		  		clearTimeout(loginTime);
		  		$('.loadingBox').hide();
		  		return alert('账号 密码不能为空');
		  	}
		  	p1=sha256_digest(p1);
		  	//p1=hex_sha1(p1);
		  	//console.log(p1);
		    $.ajax({
		        url: "${ctxPath}/login.do",
		        type: "post",
		        data: {acc:u1,pd:p1,rememberMe:rememberMe},
		        success: function (result) {
		           if(result && result.success){
		        	   //$('.animation h1').html('欢迎您：'+result.data.fullname )
		        	   //$('.animation img').attr('src', result.data.photo )
		             //setTimeout(function() {
						window.location = "${ctxPath}/index.do" ;
		             //}, 200);
		             //$('.mini-mask-loading,#loading').show();
		           }else{
                	 $('.loadingBox').hide();
                	 clearTimeout(loginTime);
		             alert("登录失败！"+result.message);
		           }
		        },failture:function(){
	                 alert("登录失败！");
	                 clearTimeout(loginTime);
	                 $('.loadingBox').hide();
		        }
		    }); 
		}

		function reset(){
				$("#u1").val('');
		  		$("#p1").val('');
		}

	$(window).resize(
		marginTop
	);
	function marginTop(){
		var window_h = $(window).height(),
			window_w = $(window).width(),
			content_h = $(".content_bg").height(),
			content_margin = (window_h-content_h)/2-30;
		
		$("body").height(window_h)
		$(".content_bg").css("marginTop",content_margin);
		
		if(window_w < 1200){
			$("body").removeClass("minWidth");
		}else{
			$("body").addClass("minWidth");
		}
	}
	marginTop();
	$(".content_r p input").mouseenter(function(){
		$(this).stop(true,true).animate({top:-1},100);
	}).mouseleave(function(){
		$(this).stop(true,true).animate({top:0},100);
	});

	
	//键盘事件
	var Login = document.getElementById('Login');
	document.onkeydown = function(ev){
		var ev = ev || event;
		if(ev.keyCode === 13){
			Login=onLoginClick();
		}
	}
	
	
	
	
</script>		

	<div class="footer_bg">
		<p>粤ICP备15060722号 Copyright @ 2014-2017 <a href="http://www.redxun.cn/" target="_blank">Powered by 广州红迅软件有限公司</a>&nbsp;&nbsp;<a href="${ctxPath}/iWebOffice/Office插件.exe">下载OFFICE插件</a></p>
		
	</div>
	
	<div class="loadingBox">
		<div class="mini-mask-msg mini-mask-loading">
			<span>登录中...</span>
		</div>
	</div>
	
	
	
	
</body>
</html>