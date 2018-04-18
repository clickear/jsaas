// JavaScript Document
function initCols(){
	//ie8及以下实行打开时自适应
	if(navigator.userAgent.indexOf("MSIE")>0){ 
		 if(navigator.userAgent.indexOf("MSIE 8.0")>0 && !window.innerWidth){
			 response();
		 }else if(navigator.userAgent.indexOf("MSIE 7.0")>0){
			 response();
		 }
	}
	var winHit=$(window).width(),
		top_r=$(".top_l").width();
	//子导航初始化	
	var navUlWidth = null;
	$(".nav_l_box ul").each(function(){
		navUlWidth = $(this).outerWidth();
		$(this).width(navUlWidth).css('top',-1).css('right',-navUlWidth-2);
	});
	$(".nav_l_box dd ul").css('position','absolute');
//tab
	$("#min_title_list")
		.on("click","li", function(){
			$(this).children().addClass("activeSpan");
			$(this).siblings().children().removeClass("activeSpan");
 	})
		.on("mouseenter","img", function(){
			$(this).attr("src",__rootPath+"/styles/images/index/index_Close02.png");
	})
		.on("mouseleave","img", function(){
			$(this).attr("src",__rootPath+"/styles/images/index/index_Close01.png");
	});
	$(".top_icon img").hover(
		function(){
			$(this).animate({top:-2},100);
		},
		function(){
			$(this).animate({top:0},100);
		}
	);
	$("#min_title_list span:eq(0)").addClass("activeSpan");
	
 //左导航
	//导航缩进
	$("#top_l_bottom01")
		.click(function(){
			$('body').addClass('activeBody');
		})
		.hover(
			function(){
				$(this).stop(true,true).animate({left:-4},100);
			},
			function(){
				$(this).stop(true,true).animate({left:0},100);
			}
		);
	//导航展开
	$("#top_l_bottom02")
		.click(function(){
			$('body').removeClass('activeBody');
		})
		.hover(
			function(){
				$(this).stop(true,true).animate({right:-4},100);
			},
			function(){
				$(this).stop(true,true).animate({right:0},100);
			}
		);
	
	//ie8展开缩进都执行一次实时函数
	$("#top_l_bottom img").click(
		function(){
			if(navigator.userAgent.indexOf("MSIE")>0){ 
				 if(navigator.userAgent.indexOf("MSIE 8.0")>0 && !window.innerWidth){
					 response();
				 }else if(navigator.userAgent.indexOf("MSIE 7.0")>0){
					 response();
				 }
			}
		}
	);
	
	//导航效果
	$(".nav_l_box dl").hide().eq(0).show();
	$(".nav_l_box").eq(0).addClass('nav_active');
	//导航效果函数
	function navEffect(){
		$(this).parent().siblings().find("dl").slideUp(300);
		$(this).next().slideToggle(300);
		$(this).parent().siblings(".nav_l_box").removeClass("nav_active");
		$(this).parent().toggleClass('nav_active');
	}
	if( document.addEventListener ){
		$('.nav_l_box>span').each(function(){
			bind(this, 'click', scroll);
			bind(this, 'click', navEffect);
		});
	}else{
		$('.nav_l_box>span').each(function(){
			bind(this, 'click', navEffect);
			bind(this, 'click', scroll);
		});
	}

//滚动条开始
	var n=0,
		top_h = null;
	scroll.call($('.nav_l_box :eq(0)'));
	$(".nav_l_bg").hover(
		function(){
			 $(".scrollbar").removeClass("innerOpacity");
		},
		function(){
			 $(".scrollbar").addClass("innerOpacity");
		}
	);
	function scroll(){
		if($(this).next().css("display") == "none" || n==0 ){
			n===0?dlHeight=272:dlHeight = $(this).siblings("dl").height();
			top_h = 104;
		}else{
			dlHeight = 0;
			top_h=$(".top_bg").height();
		}
		var window_h=$(window).height(),
			numSpan=$(".nav_l_box").length,
			out=document.getElementById("out"),
	    	inner=document.getElementById("inner"),
	    	scrollbtn=document.getElementById("scrollbtn"),
	    	scrollbar=document.getElementById("scrollbar"),
	    	innerH=	numSpan*36+dlHeight+20,
	    	outH=window_h-top_h,
	    	scrollbarH=window_h-top_h,
    		bili=innerH/outH,
	    	tops=0,
	    	speed=20,
	    	scrollbtnH=scrollbarH/bili,
	    	innerTop =scrollbtn.offsetTop;
	    scrollbtn.style.height=scrollbtnH+"px";
	    var lenH=scrollbarH-scrollbtnH;
	    if(bili<1){
	        scrollbar.style.display="none";
	        $("#inner").animate({top:0},300);
	         setTimeout(function(){
	        	 $("#inner").removeClass("innerScroll");
	         },300);
	    }else{
	    	$("#inner").addClass("innerScroll");
	    	if(innerTop == 0){
	    		$("#inner").css("top",0);
		    	$("#scrollbtn").css("top",0);
	    	}
	    	n = 1;
	    	scrollbar.style.display="block";
	        scrollbtn.onclick=function (e) {
	            var ev=e||window.event;
	            if(scrollbtn.stopPropagation){
	                ev.stopPropagation();
	            }else{
	                ev.cancelBubble();
	            };
	        };
	        scrollbtn.onmousedown = function (e) {
	            var ev=e||window.event;
	            var lenY=ev.clientY-this.offsetTop;
	            if(ev.preventDefault){
	                ev.preventDefault();
	            }else{
	                ev.returnValue=false;
	            }
	            document.onmousemove = function (e) {
	                var ev = e || window.event;
	                tops = ev.clientY - lenY;
	                if(tops<0){
	                    tops=0;
	                };
	                if(tops>lenH){
	                    tops=lenH;
	                };
	                scrollbtn.style.top = tops + "px";
	                var innerT=tops*bili;
	                inner.style.top=-innerT+"px";
	            };
	            document.onmouseup = function () {
	                document.onmousemove = null;
	                document.onmouseup = null;
	            };
	        };
	        mouseWheel(out,function(){
	            tops-=speed;
	            setTop();
	        },function(){
	            tops+=speed;
	            setTop();
	        });
	        scrollbar.onclick=function (e) {
	            var ev=e||window.event;
	            tops=ev.offsetY;
	            setTop();
	        };
	        function setTop(){
	            if(tops<0){
	                tops=0;
	            }
	            if(tops>=lenH){
	                tops=lenH;
	            }
	            scrollbtn.style.top=tops+"px";
	            inner.style.top=-tops*bili+"px";
	        }
	    }    
	} 
	
//鼠标经过
	//二级菜单
	$(".nav_l_box ul").hide();
	//鼠标经过二级
	$(".secondmenu").hover(
		function(){
			var Top_Dis = new TopDis(this);
			Top_Dis.Top();
		},
		function(){
			$(this).removeClass('nav_dd_active').children("ul").stop(false,true).slideUp(50);
			$(this).children("ul").css("top",0);
		}
	);
	
	//鼠标经过三级
	$(".threemenu").hover(
		function(){
			var Top_Dis = new TopDis(this);
			Top_Dis.Top();
		},
		function(){
			$(this).removeClass('nav_dd_active').children("ul").stop(false,true).slideUp(50);
			$(this).children("ul").css("top",0);
		}
	);
	
	//鼠标经过四级
	$('.fourmenu').hover(
		function(){
			var Top_Dis = new TopDis(this);
			Top_Dis.Top();
		},
		function(){
			$(this).removeClass('nav_dd_active');
		}
	)
	
	//全屏&正常  显示
	$("#Screen").children().eq(1).hide();
	$("#Screen a:eq(0)").on('click',function(){
		$("#Screen").children().hide().eq(1).show();
		$(this).parent().attr('title','正常显示');
		requestFullScreen(document.documentElement);
	});
	
	$("#Screen a:eq(1)").on('click',function(){
		$("#Screen").children().hide().eq(0).show();
		$(this).parent().attr('title','全屏显示');
		exitFull();
	});
};


function TopDis( that ){
	this.This = that;
	this.navUlTop=null;
	this.windowHit=$(window).height();
	this.navUl=$(that).children("ul");
	this.navUlHit=this.navUl.height();
	this.navUlAll=null;
	this.navUlVal=null;
}

TopDis.prototype.Top = function(){
	$(this.This).addClass('nav_dd_active').children("ul").stop(false,true).slideDown(300);
	if(this.navUl.length){this.navUlTop=this.navUl.offset().top;}else{return;}
	this.navUlAll=this.navUlTop+this.navUlHit-this.windowHit;
	this.navUlVal=this.windowHit-this.navUlTop;
	if( this.navUlVal<this.navUlHit )this.navUl.css("top",-this.navUlAll);
}
