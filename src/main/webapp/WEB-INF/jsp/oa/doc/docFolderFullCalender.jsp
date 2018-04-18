<%-- 
    Document   : FullCalender测试页面,完成版已经放在oa/pro目录下
    Created on : 2016-3-2, 16:11:48
    Author     : 陈茂昌
--%>
<%@page import="com.redxun.sys.org.controller.SysOrgMgrController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>日程管理视图</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href='${ctxPath}/scripts/FullCalender/fullcalendar.css' rel='stylesheet' />
<link href='${ctxPath}/scripts/FullCalender/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='${ctxPath}/scripts/FullCalender/moment.min.js'></script>
<script src='${ctxPath}/scripts/FullCalender/jquery.min.js'></script>
<script src='${ctxPath}/scripts/FullCalender/fullcalendar.min.js'></script>
<script src='${ctxPath}/scripts/FullCalender/lang-all.js'></script>
<script>
mini.parse();

var  planId;
var days="";
var changeAllDay="";
	$(document).ready(function() {$('#calendar').fullCalendar({
		        timezone:'local',//'Asia/Shanghai',
		        lang : 'zh-cn',//中文显示
		        header : {
					left : 'prev,next today',
					center :' title ',
					right : 'month,agendaWeek,agendaDay,basicWeek,basicDay'
				},
				/* ignoreTimezone:false, */
				selectable: true,
				selectHelper: true,
				select: function(start, end, allDay,view) {//day选择器
					console.log(view.name);
					if(view.name=="month"){
						start=start-79200000;
						end=end-79200000;
					}else if(view.name=="agendaDay"||view.name=="agendaWeek"){
						start=start-50400000;
						end=end-50400000;
					}else{
						start=start-28800000-50400000;
						end=end-28800000-50400000;
					}
				
					_OpenWindow({//创建新计划
			        	url: __rootPath + "/oa/pro/planTask/calenderEdit.do?start=" +start+"&end="+end+"&allDay="+allDay,
			            title: "创建计划", 
			            width: 880, 
			            height: 500,
			            ondestroy: function(action) {
			            	if(action == 'ok' && typeof(addCallback)!='undefined'){
			            		var iframe = this.getIFrameEl().contentWindow;
			            		addCallback.call(this,iframe);
			            	}else if (action == 'ok') {
			            		$('#calendar').fullCalendar('refetchEvents');//重新载入EVENT
			                }
			            }
			        });
					$('#calendar').fullCalendar('unselect');
				},

			eventClick: function(calEvent, jsEvent, view) {//点击事件打开明细
				planId=calEvent.id;
				 var menu = mini.get("contextMenu");
	             menu.showAtPos(jsEvent.pageX, jsEvent.pageY);
				$(this).css('border-color', 'red');
				},
				
				
			eventMouseover: function(calEvent, jsEvent, view) {//鼠标移动进去时变色
					$(this).css('border-color', 'red');
					},
			eventMouseout: function(calEvent, jsEvent, view) {//鼠标移动离开时变回色
				$(this).css('border-color', '#4F94CD');
				},

			
			
			  eventDragStop:function( event, jsEvent, ui, view ) { 
					var button=$(".fc-day-grid");
					var left=button.offset().left;
					var right=button.offset().left+button.width();
					var top=button.offset().top;
					var bottom=button.offset().top+button.height();
	                if(((jsEvent.pageX-left)>0)&&((right-jsEvent.pageX)>0)&&((jsEvent.pageY-top)>0)&&((bottom-jsEvent.pageY)>0)){
	                	changeAllDay="YES";
	                	console.log(changeAllDay+"?");
	                }
			}, 
			
			
			businessHours : true, // display business hours
			editable : true,
			eventLimit : true, // for all non-agenda views
			views : {
				agenda : {
					eventLimit : 6
				
				}
			},
			
			eventDrop : function(event, dayDelta, minuteDelta, allDay, jsEvent, ui, view) {
				days=dayDelta;
            	console.log("!!"+days+"  x"+jsEvent.pageX+"  y"+jsEvent.pageY);
				 mini.confirm("确定要移动计划？", "确定？",
            function (action) {
                if (action == "ok") {
                	var timeChange=dayDelta;
                	console.log(changeAllDay+"!");
    				$.ajax({
    	                type: "Post",
    	                url : '${ctxPath}/oa/pro/planTask/totalMove.do?timeChange='+ timeChange+"&pkId="+event.id+"&changeAllDay="+changeAllDay,
    	                data: {},
    	                beforeSend: function () { },
    	                success: function () { }
    	            });
                } else {
                	$('#calendar').fullCalendar('refetchEvents');
                }
                changeAllDay="NO";
				 }); 

				
				
			},
			eventResize : function(event,dayDelta, minuteDelta,revertFunc) {
				//alert("日期"+ event.title+ "延后了"+ dayDelta/ (1000 * 3600 * 24)+ " 天");
				mini.confirm("确定要移动计划？", "确定？",
            function (action) {
                if (action == "ok") {//
                	var timeChange=dayDelta;
    				//修改日期
    				$.ajax({
    		                type: "Post",
    		                url : '${ctxPath}/oa/pro/planTask/lastMove.do?timeChange='+ timeChange+"&pkId="+event.id,
    		                data: {},
    		                beforeSend: function () {
    		                },
    		                success: function () {
    		                	$('#calendar').fullCalendar('refetchEvents');
    		                }
    		            }); 
                } else {
                	$('#calendar').fullCalendar('refetchEvents');
                }
            }); 
			}, 
			eventSources: [
			{
			url: '${ctxPath}/oa/pro/planTask/fullCalender.do',
			type: 'POST',
			data: {},
			error: function() {
			alert('出错了');
			},
			success: function (text) {
				
			},
			color: '#4F94CD', // a non-ajax option
			textColor: '#BBFFFF' // a non-ajax option
			} ],
		});
					});

	
	
	
	
	 window.onload = function () {
         $("#region1").bind("contextmenu", function (e) {
         });
     }
	 
	 
	 
	 function openPlan(){
		 _OpenWindow({
	        	url: __rootPath + "/oa/pro/planTask/get.do?pkId=" +planId,
	            title: "计划明细", 
	            width: 880, 
	            height: 500,
	        });
	 }
	 
	 
	 function editPlan(){
		 _OpenWindow({
    		 url: __rootPath + "/oa/pro/planTask/edit.do?pkId="+planId,
            title: "编辑计划",
            width: 900, height: 730,
            ondestroy: function(action) {
                if (action == 'ok') {
           		 $('#calendar').fullCalendar('refetchEvents');
                }
            }
    	});
		 
	 }
	 
	 function removePlan(){
		 if (confirm("确定要删除此任务吗?")) {//删除操作
		 		_SubmitJson({
		         	url:__rootPath + "/oa/pro/planTask/del.do",
		         	method:'POST',
		         	data:{ids: planId},
		         	 success: function(text) {
		         		 $('#calendar').fullCalendar('refetchEvents');//重新载入EVENT
		             }
		          });
				}
	 }
	 
	 
	 
</script>
<style>
body {
	margin: 40px 10px;
	padding: 0;
	font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
	font-size: 14px;
}

#calendar {
	max-width: 900px;
	margin: 0 auto;
}
</style>
</head>
<body>
    <div id="region1" >
<div id='calendar'></div>
    </div>
   
<ul id="contextMenu" class="mini-contextmenu" >
    <li iconCls="icon-detail" onclick="openPlan()">打开计划明细</li>
	<li iconCls="icon-edit" onclick="editPlan()">编辑计划</li>
	<li iconCls="icon-remove" onclick="removePlan()">删除</li>
</ul>

</body>
</html>
