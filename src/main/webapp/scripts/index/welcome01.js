// JavaScript Document

$(document).ready(function(){

//自适应
//自适应
//自适应
	
	var top_l=$("body").width()
	var top_h=$(".top_bg").height()
	var top_r=$(".top_l").width()
	var window_h=$(window).height()
	var window_w=$(window).width()
	
	$(".top_r").width(top_l-top_r)
	$("#top_l_bottom").width(top_r-12)
	$(".nav_l_bg").height(window_h-top_h-20)
	$(".content_bg").width(window_w-top_r-1)
	
	var content_bg_w=$(".content_bg").width()
	
	//顶部卡片宽度
	$(".content_1p").width(content_bg_w-55)
	var content_1p_w=$(".content_1p").width()
	var content_1p_w_z=content_1p_w-120//内容区两边留20像素
	$(".content_1p_box").width(content_1p_w_z/7)
	
	//左边卡片宽度
	
	$(".content_2p").width(content_1p_w / 2-10 )
	var content_2p_w=$(".content_2p").width()
	var content_2p_w_z=content_2p_w-42
	$(".content_2p_box").width(content_2p_w_z / 3)
	
	//内容模块
	
	$(".content_db").width(content_2p_w-32)
	
	
	
	//定义函数
	
	function shishi(){

		var top_l=$("body").width()
		var top_h=$(".top_bg").height()
		var top_r=$(".top_l").width()
		var window_h=$(window).height()
		var window_w=$(window).width()

		$(".top_r").width(top_l-top_r)
		$("#top_l_bottom").width(top_r-12)
		$(".nav_l_bg").height(window_h-top_h-20)
		$(".content_bg").width(window_w-top_r-1)
		
		var content_bg_w=$(".content_bg").width()

		//顶部卡片宽度
		$(".content_1p").width(content_bg_w-55)
		var content_1p_w=$(".content_1p").width()
		var content_1p_w_z=content_1p_w-120//内容区两边留20像素
		$(".content_1p_box").width(content_1p_w_z/7)

		//左边卡片宽度

		$(".content_2p").width(content_1p_w / 2-10 )
		var content_2p_w=$(".content_2p").width()
		var content_2p_w_z=content_2p_w-42
		$(".content_2p_box").width(content_2p_w_z / 3)


		$(".content_db").width(content_2p_w-32)




		$(".nav_l_bg").height(window_h-top_h)


		//内容自适应

			if(window_w<1367){
				$(".content_1p_box").height(172)
				$(".content_1p_box p").css("font-size",14)
				$(".content_2p img").css("margin-left",8)
				$(".content_2p h3").css("font-size",12)
				$(".content_2p h2").css("font-size",14)
				$(".content_db_list p").css("font-size",14)
			}else if(window_w<1441){
				$(".content_1p_box").height(180)
				$(".content_1p_box p").css("font-size",16)
				$(".content_2p img").css("margin-left",20)
				$(".content_2p h3").css("font-size",14)
				$(".content_2p h2").css("font-size",16)
				$(".content_db_list p").css("font-size",14)
			}else if(window_w<1661){
				$(".content_1p_box").height(200)
				$(".content_1p_box p").css("font-size",16)
				$(".content_2p img").css("margin-left",20)
				$(".content_2p h3").css("font-size",14)
				$(".content_2p h2").css("font-size",16)
				$(".content_db_list p").css("font-size",16)
			}else if(window_w<1921){
				$(".content_1p_box").height(260)
				$(".content_1p_box p").css("font-size",18)
				$(".content_2p img").css("margin-left",40)
				$(".content_2p h3").css("font-size",16)
				$(".content_2p h2").css("font-size",18)
				$(".content_db_list p").css("font-size",16)
			}


		}
	
	
	//实时变化
	$(window).resize(
		shishi
	)
	//打开时自适应
	shishi()
	

	
	//tab
	//tab
	//tab

	$("#min_title_list")
		.on("click","li", function(){
		$(this).children().removeClass("top_nav_span02").addClass("top_nav_span01")
		$(this).siblings().children().removeClass("top_nav_span01").addClass("top_nav_span02")
		
 	})
		.on("mouseenter","img", function(){
		$(this).attr("src","image/index_Close02.png")
	})
		.on("mouseleave","img", function(){
		$(this).attr("src","image/index_Close01.png")
	})
	
	$(".top_icon img")
		.mouseenter(function(){
		$(this).animate({top:-2},100)
	})
		.mouseleave(function(){
		$(this).animate({top:0},100)
	})
	
//左导航
//左导航
//左导航
	
	
	
	//导航缩进
	var nav_l_icon=0
	$("#top_l_bottom02").hide()
	$()
	$("#top_l_bottom01")
		.click(function(){
			nav_l_icon=nav_l_icon+1
			$("#top_l_top span,#top_l_bottom p,#top_l_bottom01,.nav_l_box span h1,.nav_l_box p,.nav_l_box a img,.nav_l_icon_r").hide()
			$(".nav_l_box dd>a img:even").show()
			$("#top_l_bottom02").show()
			$(".top_l").css("width","64")
			$("#top_l_top").css("margin-left","6px")
			$("#top_l_bottom").width(40)
			$("#top_l_bottom").css("margin-bottom",12)
			$("#top_l_bottom").css("margin-left",0)
			$(".top_r").width(top_l-81)
			$(".nav_l_bg").css("width","63")
			$(".nav_l_box span").css("margin-left","20px")
			$(".nav_l_box dd a>h5").css("margin-left","21px")
			//$(".nav_l_box dd a").css("margin-left",0)
			$(".content_bg").width(top_l-64)	
	})
	
	
	//导航展开
	$("#top_l_bottom02")
	
		.click(function(){
			nav_l_icon=nav_l_icon-1
			$("#top_l_top span,#top_l_bottom p,#top_l_bottom01,.nav_l_box span h1,.nav_l_box p,.nav_l_box a,.nav_l_icon_r:even").show()
			$("#top_l_bottom02").hide()
			$(".top_l").css("width",200)
			$("#top_l_top").css("margin-left","12px")
			$("#top_l_bottom").width(top_r-12)
			$("#top_l_bottom").css("margin-bottom",8)
			$("#top_l_bottom").css("margin-left",12)
			$(".top_r").width(top_l-top_r-17)
			$(".nav_l_bg").css("width",199)
			$(".nav_l_box span").css("margin-left",12)
			$(".nav_l_box dd a>h5").css("margin-left","44px")
			//$(".nav_l_box dd a").css("margin-left",0)
			$(".content_bg").width(top_l-top_r)
	})
	
	//展开缩进都执行一次实时函数
	$("#top_l_bottom img").click(
			shishi
	)


//内容区
//内容区
//内容区

	$(".content_1p_box ,.content_2p_box")
		.mouseenter(function(){
		$(this).stop(true,true).animate({top:-2},100)
		})
		.mouseleave(function(){
		$(this).stop(true,true).animate({top:0},100)
	})
	
	
	
	
	
	//更多按钮根据内容显示或隐藏
		
		//ul里面只显示前面七个
	//$(".content_db li").hide()
	//$(".content_db li:lt(7)").show()
		
		
	$(".content_db h2").hide()
	$(".content_more").hide()
	var content_li = $(".content_db li").length

		//显示或隐藏 h2
	if(content_li == 1){
		$(".content_db h2").show()
	}else if(content_li > 1){
		$(".content_db h2").hide()
	}
		//显示或隐藏 更多按钮
	if(content_li<7){
		$(".content_more").hide()
	}else{
		$(".content_more").show()
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
})