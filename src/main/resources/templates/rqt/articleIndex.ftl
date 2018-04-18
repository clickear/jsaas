<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${proItemName}</title>
<style>
*{
	color:#666;
}

h1,h2,h3,h4,h5,h6{
	font-weight:normal;
	font-size:16px;
}

ul{
	margin:0;
	padding:0;
}

html{
	height:100%;
}

body{
	margin:0;
	height:100%;
}

#header {
	height:60px;
	padding:5px;
    background-color:#333;
    text-align:center;
}

#header h1{
	font-size:20px;
}

#center {
    width:100%;
	height:calc( 100% - 60px); 
}
#nav {
    background-color:#eeeeee;
    float:left;
    height:100%;
}
#section {
    overflow:hidden;	
    height:100%;
}
#footer {
    background-color:black;
    color:white;
    clear:both;
    text-align:center;
	bottom:0px; 
	height:5%;
}
*{
	font-family: "微软雅黑";
}
	
#header h1{
	color: #fff;
	font-size: 24px;
}

#nav{
	padding: 0 8px 0 4px;
	overflow:auto;
	height:100%;
}

#nav>ul{
	line-height:30px;
	list-style-type:none;
}

#nav li ul{
	padding: 0 0 0 26px;	
	line-height:30px;
	list-style-type:none;
}
.artIndex{
cursor:pointer;
}

.artIndex:hover{
	color:#B22222;
}
.artIndex.up:before
{ 
content:"∨";
font-size:20px;
font-weight:bold;
}
.artIndex.down:before
{ 
content:">";
font-size:22px;
font-weight:bolder;
}
.redFont{
	color:#B22222;
}
</style>
<#macro menuTree menus num> 
  <#if menus?? && menus?size gt 0> 
  <ul>
   <#list menus as menu> 
  	<li><a class="artIndex <#if menu.children?? && menu.children?size gt 0>up</#if>" id="${menu.id}" onclick="changeIframe('${menu.id}')">${num}${menu_index+1} ${menu.title}</a>
  	<#if menu.children?? && menu.children?size gt 0> 
    <@menuTree menus = menu.children num=num+((menu_index+1))+'.'/> 
   </#if> 
  	</li>
   </#list> 
   </ul>
  </#if> 
 </#macro>
</head>
<body >
<div style="position:fixed;height:100%;width:100%;bottom: 0px;top:0px;">
<div id="header"><h1>${proItemName}</h1></div>

<div id="center">

<div id="nav">
  <@menuTree menus = dto  num=''/>
</div>

<div id="section">
 <iframe id="contentFrame" frameborder="0" scrolling="auto"   style="width: 100%;height: 100%;" src=""></iframe>
</div>
</div>



</div>

<script type="text/javascript">
    function changeIframe(id) {
			var EL = document.getElementById(id);
			var iframeEL = document.getElementById("contentFrame");
			iframeEL.setAttribute("src", "./html/" + id + ".html");
			if (EL.nextElementSibling) {
				if (EL.nextElementSibling.hidden) {
					EL.nextElementSibling.hidden = false;
					EL.classList.add("up");
					EL.classList.remove("down");
				} else {
					EL.nextElementSibling.hidden = true;
					EL.classList.add("down");
					EL.classList.remove("up");
				}
			}
			var redEL = document.getElementsByClassName("redFont");
			if (redEL[0]) {
				redEL[0].classList.remove("redFont");
			}
			EL.classList.add("redFont");
		}

</script>
</body>
</html>