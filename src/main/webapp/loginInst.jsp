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
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/login.css"/>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery-1.11.3.js" type="text/javascript"></script> 
<script src="${ctxPath}/scripts/jquery/plugins/jquery-cookie.js" type="text/javascript"></script>
 

<title>JSaas云协同管理平台--用户登录</title>
<script type="text/javascript">
if(top!=this){//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面
	top.location='${ctxPath}/login.jsp';
}
$(function(){
	$('#username').val($.getCookie('username'));
	$('.jz-1').click(function(){
		$(this).toggleClass('active');
	});
	
	$('.sign_in,#password').bind('keypress',function(event){
        if(event.keyCode == "13") {
        	onLoginClick();
        }
    });
	
	
});

function onLoginClick(e) {
  	var username=$("#username").val();
  	var password=$("#password").val();
  	var rememberMe=$('.jz-1').hasClass("active")?1:0;
  	if(username=='' || password==''){
  		return ;
  	}
  	showLoading();
    $.ajax({
        url: "${ctxPath}/login.do",
        type: "post",
        data: {username:username,password:password,rememberMe:rememberMe},
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

function reset(){
		$("#username").val('');
  		$("#password").val('');
}

function showLoading() {
  $("#loading").css('display','');
  $("#content").css('display','none');
}

function hideLoading() {
	$("#loading").css('display','none');
	$("#content").css('display','');
}


</script>
</head>

<body>
	<div id="loading" class="loading" style="display:none;text-align:center;"><img src="${ctxPath}/styles/images/loading.gif"></div>
	<div id="content" class="content">
		<div id="header">
	    	<span class="logo"><!-- <img src="styles/images/login/logo.png" height="45" alt=""> --></span>
	   	</div>
	    <div id="main" >
	    		<img src="styles/images/login/login_bg.jpg" alt="" class="bg">
	    		
	        	<div class="box">
	                <div class="content">
		                <h2 class="use_name">组织登 录</h2>
		                <div class="user">
		                    <span>账号：</span>
		                    <input type="text"  class="input_box txt" placeholder="请输入用户名" id="username" name="username" value="admin@redxun.cn" type="username">
		                </div>
		                <div class="Password">
		                    <span>密码：</span>
		                    <input type="password" class="input_box" placeholder="密码" id="password" name="passward"  value="">
		                </div>
		                <div class="remember">            	
		                    <input type="checkbox" class="jz-1">
		                    <span >系统记住我</span>
		                    <a href="#" class="jz-2">忘记密码?</a>
		                </div>
		                <div class="Sign">
		                    <div class="sign_in" onclick="onLoginClick()">
		                        <a>登 录</a>
		                    </div>
		                    <div class="rest" onclick="this.form.reset();">
		                        <a>重 置</a>
		                    </div>
		                </div>
		                
		                <p class="new">新企业？<a href="register.jsp" class="register">注册企业帐号</a></p>
		                <p class="new1">如点击功能无反应，请清空浏览器缓存之后再重试。</p>
		                 
	            	</div>
	            	
	    	  </div>	
	     </div>    
	     <div id="footer">
		    	<div class="contain">            
		            <span>粤ICP备15060722号</span>
		            <span>Copyright @ 2014-2017</span>
		            <a href="http://www.redxun.cn" target="_blank"> Powered by 广州红迅软件有限公司</a>
		        </div>    	
    	</div>  
   	</div>           
    <script type="text/javascript">
    	mini.parse();
    </script>
</body>
</html>
