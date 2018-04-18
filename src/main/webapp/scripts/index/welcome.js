//设置左分隔符为 <!
baidu.template.LEFT_DELIMITER='<#';
//设置右分隔符为 <!  
baidu.template.RIGHT_DELIMITER='#>';
var bt=baidu.template;
$(document).ready(function(){
	loadData();
	loadUI();
	initUrl();
});
//初始化Url地址
function initUrl(){
	//互联网接入开通单
	$("#hlwjrktd").bind("click",function(){
		_OpenWindow({
			url:"bpm/core/bpmInst/start.do?solId=2400000001061000",
			title:'互联网接入开通业务流程	',
			max:true,
			width:800,
			height:450,
			ondestroy:function(action){
				document.location.reload();
    		}
		});
	});
	//待处理
	$("#myTasks").bind("click",function(){
		top['index'].showTabFromPage({
			tabId:'myTasks',
			iconCls:'icon-mgr',
			title:'待处理',
			url:__rootPath+'/bpm/core/bpmTask/myList.do'
		});
	});
	//已处理
	$("#myAttends").bind("click",function(){
		top['index'].showTabFromPage({
			tabId:'myAttends',
			iconCls:'icon-mgr',
			title:'已处理',
			url:__rootPath+'/oa/personal/bpmInst/myAttends.do'
		});
	});
	//我的代理
	$("#myAgentstoMe").bind("click",function(){
		top['index'].showTabFromPage({
			tabId:'myAgentstoMe',
			iconCls:'icon-mgr',
			title:'我的代理',
			url:__rootPath+'/bpm/core/bpmTask/agents.do?agent=toMe'
		});
	});
	//我的代办
	$("#myAgentstoOther").bind("click",function(){
		top['index'].showTabFromPage({
			tabId:'myAgentstoOther',
			iconCls:'icon-mgr',
			title:'我的代办',
			url:__rootPath+'/bpm/core/bpmTask/agents.do?agent=toOther'
		});
	});
	//信息
	$("#myInsMsg").bind("click",function(){
		top['index'].showTabFromPage({
			tabId:'myInsMsg',
			iconCls:'icon-mgr',
			title:'信息',
			url:__rootPath+'/oa/info/infInbox/receive.do'
		});
	});
	//查看更多
	$("#more").bind("click",function(){
		top['index'].showTabFromPage({
			tabId:'myTasks',
			iconCls:'icon-mgr',
			title:'待处理',
			url:__rootPath+'/bpm/core/bpmTask/myList.do'
		});
	});
	//刷新
	$("#refresh").bind("click",function(){
		//加载待办List	
		$.ajax({
			url:__rootPath+"/bpm/core/bpmTask/myTasks.do",
			type:"POST",
			success:function(result){
				buildMyTask(result.data);
			}
		});
	});
}
//加载数据
function loadData(){
	//待处理
	$.ajax({
		url:__rootPath+"/bpm/core/bpmTask/myTasks.do",
		type:"POST",
		success:function(result){
			var total = result.total;
			$("#myTasks").find("h3").text(total);
		}
	});
	//已处理
	$.ajax({
		url:__rootPath+"/oa/personal/bpmInst/myAttendsData.do",
		type:"POST",
		success:function(result){
			var total = result.length;
			$("#myAttends").find("h3").text(total);
		}
	});
	//我的代理
	$.ajax({
		url:__rootPath+"/bpm/core/bpmTask/myAgents.do?agent=toMe",
		type:"POST",
		success:function(result){
			var total = result.total;
			$("#myAgentstoMe").find("h3").text(total);
		}
	});
	//我的代办
	$.ajax({
		url:__rootPath+"/bpm/core/bpmTask/myAgents.do?agent=toOther",
		type:"POST",
		success:function(result){
			var total = result.total;
			$("#myAgentstoOther").find("h3").text(total);
		}
	});
	//我的消息
	$.ajax({
		url:__rootPath+"/oa/info/infInnerMsg/count.do",
		type:"POST",
		success:function(result){
			$("#myInsMsg").find("h3").text(result.data);
		}
	});
	//加载待办List	
	$.ajax({
		url:__rootPath+"/bpm/core/bpmTask/myTasks.do",
		type:"POST",
		success:function(result){
			buildMyTask(result.data);
		}
	});
	
	
}
function buildMyTask(data,curIdx){
	var menuData=data;
	var data={"list":menuData};
	var list=bt('myTaskListTemplate',data);
	$("#myTaskList").html(list);

}
//自适应
function loadUI(){
	var window_h=$(window).height();
	var window_w=$(window).width();
	var content_bg_w=$(".content_bg").width();	
	//顶部卡片宽度
	$(".content_1p").width(content_bg_w-55);
	var content_1p_w=$(".content_1p").width();
	var content_1p_w_z=content_1p_w-120;
	$(".content_1p_box").width(content_1p_w_z/7);	
	//左边卡片宽度;
	$(".content_2p").width(content_1p_w / 2-10 );
	var content_2p_w=$(".content_2p").width();
	var content_2p_w_z=content_2p_w-42;
	$(".content_2p_box").width(content_2p_w_z / 3);
	//内容模块	
	$(".content_db").width(content_2p_w-32);
	//定义函数
	function response(){
		var top_l_w=$(".top_l").width(),
			window_h=$(window).height(),
			window_w=$(window).width();
		$(".content_bg").width(window_w-top_l_w);
		var content_bg_w=$(".content_bg").width();
		//顶部卡片宽度
		$(".content_1p").width(content_bg_w-55);
		var content_1p_w=$(".content_1p").width();
			content_1p_w_z=content_1p_w-120;
		$(".content_1p_box").width(content_1p_w_z/7);
		//左边卡片宽度
		$(".content_2p").width(content_1p_w / 2-10 );
		var content_2p_w=$(".content_2p").width(),
			content_2p_w_z=content_2p_w-42;
		$(".content_2p_box").width(content_2p_w_z / 3);
		$(".content_db").width(content_2p_w-32);
		//内容自适应
			if(window_w<1367){
				$(".content_1p_box").height(172);
				$(".content_1p_box p").css("fontSize",14);
				$(".content_2p img").css("marginLeft",30).css("marginTop",80);
				$(".content_2p h3").css("fontSize",12);
				$(".content_2p h2").css("fontSize",14);
				$(".content_db_list p").css("fontSize",14);
			}else if(window_w<1441){
				$(".content_1p_box").height(180);
				$(".content_1p_box p").css("fontSize",16);
				$(".content_2p img").css("marginLleft",35).css("marginTop",60);
				$(".content_2p h3").css("fontSize",14);
				$(".content_2p h2").css("fontSize",16);
				$(".content_db_list p").css("fontSize",14);
			}else if(window_w<1661){
				$(".content_1p_box").height(200);
				$(".content_1p_box p").css("fontSize",16);
				$(".content_2p img").css("marginLeft",35).css("marginTop",65);
				$(".content_2p h3").css("fontSize",14);
				$(".content_2p h2").css("fontSize",16);
				$(".content_db_list p").css("fontSize",16);
			}else if(window_w<1921){
				$(".content_1p_box").height(260);
				$(".content_1p_box p").css("fontSize",18);
				$(".content_2p img").css("marginLeft",40).css("marginTop",65);
				$(".content_2p h3").css("fontSize",16);
				$(".content_2p h2").css("fontSize",18);
				$(".content_db_list p").css("fontSize",16);
			}
		}
	//实时变化
	$(window).resize(
			response
	);
	//打开时自适应
	response();

//左导航
	//展开缩进都执行一次实时函数
	$("#top_l_bottom img").click(
			response
	);
//内容区
	$(".content_1p_box ,.content_2p_box")
		.mouseenter(function(){
		$(this).stop(true,true).animate({top:-2},100);
		})
		.mouseleave(function(){
		$(this).stop(true,true).animate({top:0},100);
	});
//更多按钮根据内容显示或隐藏	
	$(".content_more").hide();
		var content_li = $("#myTaskList li").length;
	//显示或隐藏 更多按钮
	if(content_li<6){
		$(".content_more").hide();
	}else{
		$(".content_more").show();
	}
	
}