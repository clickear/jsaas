<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>知识管理系统主页</title>
<%@include file="/commons/dynamic.jspf" %>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/css/kdquestion.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/base.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<style>
	body{
		line-height: 18px;
		background: #fff;
	}
</style>

</head>
<body>	
	 <div class="container">
        <div class="side">
            <div id="leftMenu" class="menu">
                
            </div>
            <div class="linkbtn">
                <a href="#" class="fl" onclick="newQue()" style="margin-left: 25%;">我要提问</a>
            </div>
            <div class="rank">
                <ul>
                    <li class="active">知识积分排行</li>
                </ul>
                <div class="wrap">
                    <table style="display: table;">
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
  <div class="main-top">
    <div class="zuixin">
      <div class="panel-head">
        <h3 class="fl">最新问答</h3>
        <div class="fr">
            <a href="#" class="more"></a>
            <img class="selected" src="${ctxPath}/styles/images/knowl/tw.png" />
            <span class="renovate" style="cursor: pointer;">提问</span>
        </div>

      </div>
      <div class="panel-body">
        <ul class="list-mian">
          <c:forEach items="${latestQuestions}" var="latestQuestion" varStatus="status">
	          <li>
	            <p class="fl"> <em class="fl"> <a href="${ctxPath}/kms/core/kdQuestion/find.do?i=3&catId=${latestQuestion.sysTree.treeId}">${latestQuestion.sysTree.name}</a> </em> 
	            <span class="queItem" id="${latestQuestion.queId}" style="cursor: pointer;">${latestQuestion.subject}</span> </p>
	            <span class="fr"> <span class="bloder">${latestQuestion.replyCounts}</span> <span class="time"> 答</span> </span> 
	          </li>
          </c:forEach>
        </ul>
      </div>
    </div>
    <div class="zuire">
    	<div class="panel-head">
        <h3 class="fl">最热问答</h3>
        <div class="fr">
            <a href="#" class="more"></a>
            <img class="selected" src="${ctxPath}/styles/images/knowl/tw.png" />
            <span class="renovate" style="cursor: pointer;">提问</span>
        </div>
      </div>
      <div class="panel-body">
        <ul class="list-mian">
          <c:forEach items="${hostestQuestions}" var="hostestQuestion" varStatus="status">
	          <li>
	            <p class="fl"> <em class="fl"> <a href="${ctxPath}/kms/core/kdQuestion/find.do?i=3&catId=${hostestQuestion.sysTree.treeId}">${hostestQuestion.sysTree.name}</a> </em> 
	            <span class="queItem" id="${hostestQuestion.queId}" style="cursor: pointer;">${hostestQuestion.subject}</span> </p>
	            <span class="fr"> <span class="bloder">${hostestQuestion.replyCounts}</span> <span class="time"> 答</span> </span> 
	          </li>
          </c:forEach>
        </ul>
      </div>
    </div>
    <div class="gaofen">
    	<div class="panel-head">
        <h3 class="fl">高分问答</h3>
        <div class="fr">
            <a href="#" class="more"></a>
            <img class="selected" src="${ctxPath}/styles/images/knowl/tw.png" />
            <span class="renovate" style="cursor: pointer;">提问</span>
        </div>
      </div>
      <div class="panel-body">
        <ul class="list-mian">
          <c:forEach items="${highrewardQuestions}" var="highrewardQuestion" varStatus="status">
	          <li>
	            <p class="fl"> <em class="fl"> <a href="${ctxPath}/kms/core/kdQuestion/find.do?i=3&catId=${highrewardQuestion.sysTree.treeId}">${highrewardQuestion.sysTree.name}</a> </em> 
	            <span class="queItem" id="${highrewardQuestion.queId}" style="cursor: pointer;">${highrewardQuestion.subject}</span> </p>
	            <span class="fr"> <span class="bloder">${highrewardQuestion.replyCounts}</span> <span class="time"> 答</span> </span> 
	          </li>
          </c:forEach>
        </ul>
      </div>
    </div>
  </div>
  <div class="main-content">
    <div class="leftside">
      <div class="wenti">
        <div class="daohang daohang1">
          <ul class="module-title market-title clearfix">
            <li class="first">已解决问题</li>
            <li>待解决问题</li>
          </ul>
         <a href="#" class="more"></a>
        </div>
        <div class="list list1">
        	<ul class="list-mian" style="float: none;">
                <c:forEach items="${solvedQuestions}" var="question" varStatus="status">
                	<li><div class="fl"><img class="jb" src="${ctxPath}/styles/images/knowl/jb.png" /><span class="num1" style="color:#ff9900">${question.rewardScore}</span><span class="queItem" id="${question.queId}" style="cursor: pointer;">${question.subject}</span></div><div class="fr"><img class="ok" src="${ctxPath}/styles/images/knowl/ok.png" /><span class="num2">${question.replyCounts}</span> 答</div></li>
                </c:forEach>
            </ul>
            <ul class="list-mian" style="display: none;float: none;">
                <c:forEach items="${unsolvedQuestions}" var="question" varStatus="status">
                	<li><div class="fl"><img class="jb" src="${ctxPath}/styles/images/knowl/jb.png" /><span class="num1" style="color:#ff9900">${question.rewardScore}</span><span class="queItem" id="${question.queId}" style="cursor: pointer;">${question.subject}2</span></div><div class="fr"><img class="ok" src="${ctxPath}/styles/images/knowl/ok.png" /><span class="num2">${question.replyCounts}</span> 答</div></li>
                </c:forEach>
            </ul>
            
        </div>
      </div>
      <div class="best">
      	<div class="daohang">
          <img src="${ctxPath}/styles/images/knowl/a.png" />
          <span>最佳问答</span>
          <a href="#" class="more"></a>
        </div>
        <div class="list">
        	<ul>		
				<c:forEach items="${bestQuestions}" var="question" varStatus="status">
                	<li><div class="fl"><img class="jb" src="${ctxPath}/styles/images/knowl/jb.png" /><span class="num1" style="color:#ff9900">${question.rewardScore}</span><span class="queItem" id="${question.queId}" style="cursor: pointer;">${question.subject}</span></div><div class="fr"><img class="ok" src="${ctxPath}/styles/images/knowl/ok.png" /><span class="num2">${question.replyCounts}</span> 答</div></li>
                </c:forEach>
            </ul>
        </div>
      </div>
    </div>
    <div class="rightside">
    	<div class="daohang">
        	<h3 class="fl">推荐专家</h3><!-- <a href="#" class="more"></a> -->
        </div>
        <div class="zhuanjia">
            <c:forEach items="${experts}" var="expert" varStatus="status">
            	<div class="zhuanjia1">
	            	<div class="touxiang" id="${expert.user.userId}" style="background:url(${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${expert.headId});"></div>
	                <div class="xinxi">
	                	<span>专家：<span class="expert-name" style="color: orange;cursor: pointer;" id="${expert.user.userId}">${expert.fullname}</span></span><a href="${ctxPath}/kms/core/kdQuestion/expertEdit.do?uId=${expert.user.userId}" target="_blank" class="zixun">向他咨询</a>
	                    <p>等级：${expert.grade}</p>
	                    <p>类型：<c:if test="${expert.userType=='PERSON'}">专家个人</c:if><c:if test="${expert.userType=='DOMAIN'}">领域专家</c:if></p>
	                    <p>精通领域：${expert.reqSysTree.name}</p>
	                </div>
            	</div>
            </c:forEach>
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
				window.location.href = "${ctxPath}/kms/core/kdQuestion/find.do?i=3&catId=" + $(this).attr("id");
			});
		});
		
	</script>

	<script>
	//新增知识问答
	function newQue() {
		window.open("${ctxPath}/kms/core/kdQuestion/edit.do?i=3");
	}
	
	$(".renovate").click(function(){
		newQue();
	});
	
	$(".queItem").click(function(){
		window.open("${ctxPath}/kms/core/kdQuestion/get.do?i=3&pkId="+$(this).attr("id"));   
	});
	
	$(".more").click(function(){
		window.location.href="${ctxPath}/kms/core/kdQuestion/find.do?i=3";
	});
	
	$(".touxiang").click(function(){
		window.open("${ctxPath}/kms/core/kdUser/personalGet.do?i=4&userId="+$(this).attr('id'));
	});
	
	$(".expert-name").click(function(){
		window.open("${ctxPath}/kms/core/kdUser/personalGet.do?i=4&userId="+$(this).attr('id'));
	});
	
	function nodone(){
		alert("敬请期待！");
	}
	
	$(".indexMore").click(function(){
		window.location.href="${ctxPath}/kms/core/kdQuestion/find.do";
	});
	
	
    function resetTabs(){
        $("#content > div").hide(); //Hide all content
        $("#tabs a").attr("id",""); //Reset id's      
    }

    var myUrl = window.location.href; //get URL
    var myUrlTab = myUrl.substring(myUrl.indexOf("#")); // For localhost/tabs.html#tab2, myUrlTab = #tab2     
    var myUrlTabName = myUrlTab.substring(0,4); // For the above example, myUrlTabName = #tab

    (function(){
    	$("#tab1").find(".row_header").each(function(index){
    		if(index!=0){
    			$(this).removeClass("row_header");
    			$(this).addClass("row");
    		}
    	});
    	$("#tab2").find(".row_header").each(function(index){
    		if(index!=0){
    			$(this).removeClass("row_header");
    			$(this).addClass("row");
    		}
    	});
    	$("#best").find(".row_header").each(function(index){
    		if(index!=0){
    			$(this).removeClass("row_header");
    			$(this).addClass("row");
    		}
    	});
    	$("#tabs").parent().addClass("over");
    	
        $("#content > div").hide(); // Initially hide all content
        $("#tabs li:first a").attr("id","current"); // Activate first tab
        $("#content > div:first").fadeIn(); // Show first tab content
        
        $("#tabs a").on("click",function(e) {
            e.preventDefault();
            if ($(this).attr("id") == "current"){ //detection for current tab
             return       
            }
            else{             
            resetTabs();
            $(this).attr("id","current"); // Activate this
            $($(this).attr('name')).fadeIn(); // Show content for current tab
            }
        });

        for (var i = 1; i <= $("#tabs li").length; i++) {
          if (myUrlTab == myUrlTabName + i) {
              resetTabs();
              $("a[name='"+myUrlTab+"']").attr("id","current"); // Activate url tab
              $(myUrlTab).fadeIn(); // Show url tab content        
          }
        }
    })();
  </script>
  <script src="${ctxPath}/scripts/knowl/knowl.js" type="text/javascript"></script>
</body>
</html>