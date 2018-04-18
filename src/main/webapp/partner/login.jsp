<%@ page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<%@include file="/commons/dynamic.jspf" %>
<link rel="shortcut icon" href="${ctxPath}/styles/images/login2/icon.ico">
<link rel="stylesheet" href="${ctxPath}/styles/login02.css" type="text/css">
<script src="${ctxPath}/scripts/jquery-1.11.3.js" type="text/javascript"></script> 
<script src="${ctxPath}/scripts/jquery/plugins/jquery-cookie.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/common/sha256.js" type="text/javascript"></script>
<title>红迅软件-合作伙伴平台--登录</title>
</head>
<body>
<!--top-->
	<div class="top_bg">
		<div class="top_box">
			<img src="${ctxPath}/styles/images/login2/login02_top_bg.png" alt="">
			<div class="clearfix"></div>
		</div>
	</div>
<!--content-->
	<div id="loading" class="loading" style="display:none;text-align:center;"><img src="${ctxPath}/styles/images/loading.gif"></div>
	
	<div class="content_bg" id="content">
			<div class="content_l">
				<img src="${ctxPath}/styles/images/login2/login02_icon00.png" alt="">
				<img src="${ctxPath}/styles/images/login2/login02_icon01.png" alt="icon01" class="icon01">
				<img src="${ctxPath}/styles/images/login2/login02_icon02.png" alt="icon02" class="icon02">
				<img src="${ctxPath}/styles/images/login2/login02_icon03.png" alt="icon03" class="icon03">
				<img src="${ctxPath}/styles/images/login2/login02_icon04.png" alt="icon04" class="icon04">
				<img src="${ctxPath}/styles/images/login2/login02_icon05.png" alt="icon05" class="icon05">
				<img src="${ctxPath}/styles/images/login2/login02_icon06.png" alt="icon06" class="icon06">
			</div>
			<div class="content_r">
				
					<div>
						<img src="${ctxPath}/styles/images/login2/login02_r_icon01.png" alt="">
						<p>用户登录</p>
						<div class="clearfix"></div>
					</div>
					<form id="loginForm" method="POST">
						<dl>

							<dd>
								<img src="${ctxPath}/styles/images/login2/login02_r_icon04.png" alt="">
								<input type="text" id="companyName" name="companyName" value="公司名称" onfocus="if(value=='公司名称'){value=''}" onblur="if(value==''){value='公司名称'}">
								<div class="clearfix"></div>
							</dd>
							<dd>
								<img src="${ctxPath}/styles/images/login2/login02_r_icon02.png" alt="">
								<input type="text" id="username" name="username" value="登录账号" onfocus="if(value=='登录账号'){value=''}" onblur="if(value==''){value='登录账号'}">
								<div class="clearfix"></div>
							</dd>
							<dd style="margin: 14px 0 30px 0">
								<img src="${ctxPath}/styles/images/login2/login02_r_icon03.png" alt="">
								<input id="password" type="password" name="password" value="">
								<div class="clearfix"></div>
							</dd>
						</dl>
						<span>
							<input type="checkbox">
							<h1>记住密码</h1>
							<h2>忘记密码?</h2>
							<div class="clearfix"></div>
						</span>
						<p>
							<input type="button" value="登   录" class="Login" onclick="loginAction()">
							<input type="button" value="重   置" class="chongzhi" onclick="reset()">
							<div class="clearfix"></div>
						</p>
					</form>
			</div>
			<div class="clearfix"></div>
		</div>
		
		
		


<script type="text/javascript">
		function reset(){
			document.getElementById('loginForm').reset();
		}
		$(function(){
			var username=$.getCookie('username');
			var companyName=$.getCookie('companyName');
			if(username){
				$("#username").val(unescape(username));
			}
			if(companyName){
				$("#companyName").val(unescape(companyName));
			}
		});
		function loginAction(){
			var username=$("#username").val();
			var companyName=$("#companyName").val();
			var password=$("#password").val();
			$.setCookie('username',username);
			$.setCookie('companyName',companyName);
			showLoading();
			password=sha256_digest(password);
		 	$.ajax({
		        url: "${ctxPath}/partner/login.do",
		        type: "post",
		        data: {
		        	username:username,
		        	companyName:companyName,
		        	password:password
		        },
		        success: function (result) {
		           if(result && result.success){
		             setTimeout(function() {
		            	// hideLoading();
		            	$.setCookie('username',username);
		                 window.location = "${ctxPath}/index.do";
		             }, 200);
		           }else{
		             alert("登录失败！"+result.message);
		             hideLoading();
		           }
		        },failture:function(){
		        	 alert("登录失败！");
		        	 hideLoading();
		        }
		    });
		}
		function showLoading() {
			$("#content").css('display','none');  
			$("#loading").css('display','');
		}
		

		function hideLoading() {
			$("#loading").css('display','none');
			$("#content").css('display','');
		}
 </script>
 
 
 <!--上下居中-->	
<script type="text/javascript">
	$(window).resize(
		marginTop
	);
	function marginTop(){
		var window_h = $(window).height(),
			window_w = $(window).width(),
		content_margin = (window_h-590)/2;
		$(".content_bg").css("marginTop",content_margin);
		
		if(window_w < 1200){
			$("body").removeClass("minWidth");
		}else{
			$("body").addClass("minWidth");
		}
	}
	marginTop();
	$(".content_r p input")
	.mouseenter(function(){
	$(this).stop(true,true).animate({top:-1},100);
	})
	.mouseleave(function(){
	$(this).stop(true,true).animate({top:0},100);
	});

</script>		
		
		
		
<!--footer-->
	<div class="footer_bg">
		<p>©版权所有 红迅软件</p>
	</div>
	
</body>
</html>
