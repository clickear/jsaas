<%-- 
    Document   : FullCalender页面
    Created on : 2016-3-2, 16:11:48
    Author     : 陈茂昌
--%>
<%@page import="com.redxun.sys.org.controller.SysOrgMgrController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>日程管理视图</title>
<%@include file="/commons/list.jsp"%>
<link href='${ctxPath}/scripts/FullCalender/fullcalendar.css' rel='stylesheet' />
<link href='${ctxPath}/scripts/FullCalender/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='${ctxPath}/scripts/FullCalender/moment.min.js'></script>
<script src='${ctxPath}/scripts/FullCalender/fullcalendar.min.js'></script>
<script src='${ctxPath}/scripts/FullCalender/lang-all.js'></script>
<script src='${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.js'></script>
<link href="${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.css" rel="stylesheet" type="text/css" />
<style>
body {
/* 	margin-top: 40px 10px; */
	padding: 0;
	font-size: 14px;
}

#calendar {
	max-width: 900px;
	margin: 0 auto;
}

#calendar{
	background: #fff;
	padding: 8px;
	margin: 20px auto;
	border-radius: 8px;
    box-shadow: 0px 5px 10px 0px rgba(179, 179, 179, 0.15);
} 

form{
	background: transparent;
}

</style>
</head>
<body>
    <div id="region1" style="float:left;width: 70%;" >
		<div id='calendar'></div>
    </div>
   
	<ul id="contextMenu" class="mini-contextmenu" >
		<li iconCls="icon-remove" onclick="removeCalendar()">删除</li>
	</ul>

	<div>
		<input 
			id="workTimeBlock" 
			class="mini-combobox" 
			url="${ctxPath}/oa/calendar/workTimeBlock/getAllWorkTimeBlock.do" 
			valueField="settingId" 
			textField="settingName"  
			ondrawcell="onDrawCells" 
			emptyText="请选择快捷时间段设定"  
			onhidepopup="selectTheTimeBlock()"
		/>
		<a class="mini-button" iconCls="icon-add_newStyle" onclick="clearTheUpdate();toggleTheDiv();">班次定义</a>
		<a class="mini-button" style="display: none;" id="saveDIY" iconCls="icon-save" onclick="saveTheDiyBlock()">保存</a>
		<div style="margin-top: 20px;display: none;" id="toggleDiv"  >
			<form id="form1" method="post">
				<table cellspacing="1" cellpadding="0" >
					<tr>
						<td>
							<div >
								<input id="grantNameToTimeBlock" name="settingName" class="mini-textbox" emptyText="时间段命名"  required="true"  />
								<a class="mini-button" iconCls="icon-add_newStyle"  onclick="addAnotherTimeBlock()" >添加</a>
								<a class="mini-button" style="display: none;" id="BtnRemoveAnother" iconCls="icon-remove" onclick="removeAnotherTimeBlock()" >清空</a>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<input id="startTime" name="startTime"  class='mini-timespinner' allowInput='true' format='HH:mm'  required="true"  />~
							<input id="endTime" name="endTime"  class='mini-timespinner' format='HH:mm' allowInput='true' required="true"  onvaluechanged="floatMsg()"/>
						</td>
					</tr>
				</table>
			</form>	
		</div>
	</div>
<script type="text/javascript">
mini.parse();

var  calendarId;
var days="";
var changeAllDay="";
	$(document).ready(function() {$('#calendar').fullCalendar({
		        timezone:'local',//'Asia/Shanghai',
		        lang : 'zh-cn',//中文显示
		        header : {
					left : 'prevYear,nextYear',
					center :'prev title next ',
					right : ''
				},
				buttonText: {
					/* agendaDay: '议程日', 
					agendaWeek: '议程周',  */
					basicWeek: '周', 
					basicDay: '日',
					prevYear:'上一年',
					nextYear:'下一年'
				}, 
				businessHours : true, // display business hours
				editable : true,
				eventLimit : true, // for all non-agenda views
				views : {
					agenda : {
						eventLimit : 6
					}
				},
				dragOpacity: {
					month: .2
					},
				/* ignoreTimezone:false, */
				selectable: true,
				selectHelper: true,
				select: function(start, end, allDay,view) {//day选择器
					 if(view.name=="month"){
						start=start-28800000;
						end=end-28800000;
					}else{
						start=start-28800000-50400000;
						end=end-28800000-50400000;
					} 
					var timeBlockValue=selectTheTimeBlock();
					
					 mini.showMessageBox({
				            title: "提示信息",
				            iconCls: "mini-messagebox-info",
				            buttons: ["设置","删除", "取消", ],
				            message: "请选择操作",
				            callback: function (action) {
				                if(action == "设置"){
				                	if(timeBlockValue.length<1){
										mini.showTips({
				  	                         content: "<b>提醒</b> <br/>请选择右边的区间",
				  	                         state: 'warning',
				  	                         x: "center",
				  	                         y: "center",
				  	                         timeout: 3000 });
										return;
									}
				                	if(timeBlockValue.length==0){
				                		timeBlockValue="0";
				                	}
				                		$.ajax({
					    	                type: "Post",
					    	                url : '${ctxPath}/oa/calendar/workCalendar/drawCalendar.do',
					    	                data: {"start":start,"end":end,"timeBlockValue":timeBlockValue,"calSettingId":'${settingId}'},
					    	                success: function (result) {
					    	                	if(result.success){
					    	                		$('#calendar').fullCalendar('refetchEvents'); 
						    	                	mini.showTips({
						   	                         content: "<b>成功</b> <br/>时间区间创建完成",
						   	                         state: 'success',
						   	                         x: "center",
						   	                         y: "top",
						   	                         timeout: 3000 });
					    	                	}else{
					    	                		$('#calendar').fullCalendar('refetchEvents'); 
						    	                	mini.showTips({
						   	                         content: "<b>失败</b> <br/>时间区间不能交错",
						   	                         state: 'danger',
						   	                         x: "center",
						   	                         y: "top",
						   	                         timeout: 3000 });
					    	                	}
					    	                	}
					    	            });
				                		$('#calendar').fullCalendar('refetchEvents');
				                }else if(action=="删除"){
				                	$.ajax({
				    	                type: "Post",
				    	                url : '${ctxPath}/oa/calendar/workCalendar/removeCalendar.do',
				    	                data: {"start":start,"end":end,"calSettingId":'${settingId}'},
				    	                success: function (result) {
				    	                	if(result.success){
				    	                		$('#calendar').fullCalendar('refetchEvents'); 
					    	                	mini.showTips({
					   	                         content: "<b>成功</b> <br/>时间区间已经删除",
					   	                         state: 'success',
					   	                         x: "center",
					   	                         y: "top",
					   	                         timeout: 3000 });
				    	                	}
				    	                	}
				    	            });
				                }else if(action=="取消"){
				                	
				                }
				                changeAllDay="NO";
				            }
				        });
					
				},

			eventClick: function(calEvent, jsEvent, view) {//点击事件打开明细
				calendarId=calEvent.id;
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
	                	if((view.name=="agendaWeek")||(view.name=="agendaDay")){
	                		changeAllDay="YES";}
	                }
			}, 
			eventDrop : function(event, dayDelta, minuteDelta, allDay, jsEvent, ui, view) {
				 days=dayDelta;
				 mini.confirm("确定要移动计划？", "确定？",
            function (action) {
                if (action == "ok") {
                	var timeChange=dayDelta;
    				$.ajax({
    	                type: "Post",
    	                url : '${ctxPath}/oa/calendar/workCalendar/totalMove.do?timeChange='+ timeChange+"&pkId="+event.id+"&changeAllDay="+changeAllDay,
    	                data: {},
    	                beforeSend: function () { },
    	                success: function (result) {
    	                	 if(result.success){
    	                		 $('#calendar').fullCalendar('refetchEvents'); 
    	    	                	mini.showTips({
    	   	                         content: "<b>成功</b> <br/>计划移动完成",
    	   	                         state: 'success',
    	   	                         x: "center",
    	   	                         y: "top",
    	   	                         timeout: 3000 });
    	                	 }else{
    	                		 $('#calendar').fullCalendar('refetchEvents'); 
    	    	                	mini.showTips({
    	   	                         content: "<b>失败</b> <br/>日历不允许时间交错",
    	   	                         state: 'danger',
    	   	                         x: "center",
    	   	                         y: "top",
    	   	                         timeout: 3000 });
    	                	 }
    	                	
    	                	}
    	            });
                } else {
                	$('#calendar').fullCalendar('refetchEvents');
                }
                changeAllDay="NO";
				 }); 
				
			}, 
			eventRender: function(event, element) {
				element.qtip({
					content: event.start._d.toLocaleString()+"开始<br/>"+event.end._d.toLocaleString()+"结束<br/> 共"+(event.end-event.start._d)/60000+"分钟",
					position: {
						 my: 'top left', // Position my top left...
						 at: 'bottom center', // at the bottom right of...
						 target: element // my target
						 }
					});
				},

			eventSources: [
			{
			url: '${ctxPath}/oa/calendar/workCalendar/getAllLimitByTime.do',
			type: 'POST',
			data: {settingId:'${settingId}'},
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


	 function removeCalendar(){
		 if (confirm("确定要删除此任务吗?")) {//删除操作
		 		_SubmitJson({
		         	url:__rootPath + "/oa/calendar/workCalendar/del.do",
		         	method:'POST',
		         	data:{ids: calendarId},
		         	 success: function(text) {
		         		 $('#calendar').fullCalendar('refetchEvents');//重新载入EVENT
		             }
		          });
				}
	 }
	 
	 var isUpdate=" ";
	 var plusTime=-1;//添加次数
	 function addAnotherTimeBlock(){
		 if(plusTime==-1){
			 plusTime=0;
		 }
		 $("#BtnRemoveAnother").show();
		 var plusStart="plusStart"+plusTime;
		 var plusEnd="plusEnd"+plusTime;
	$("#form1>table").append(
	"<tr class='plusTr'><td><input id='"+plusStart+"' name='"+plusStart
	+"' allowInput='true' style='display:none;' class='mini-timespinner' format='HH:mm'   required='true'  onvaluechanged='isRegular(&quot;"+plusStart+"&quot;)' />~<input id='"+plusEnd+"' name='"+plusEnd
	+"' allowInput='true' style='display:none;' class='mini-timespinner' format='HH:mm'   required='true'  onvaluechanged='isRegular(&quot;"+plusEnd+"&quot;)'/><td></tr>")
	mini.parse();	
	$("#"+plusStart).fadeIn();
	$("#"+plusEnd).fadeIn();
	if(plusTime>=1){
		mini.showTips({
	         content: "<b>提示</b> <br/>您已经定义"+(parseInt(plusTime)+2)+"个时段<br/>不推荐超过3个时段<br/>",
	         state: "danger",
	         x: 'center',
	         y: 'center',
	         timeout: 5000
	     });	
	}
		plusTime += 1;
	}
	 
	 //清空额外添加的自定义区间
	 function removeAnotherTimeBlock(){
		 plusTime=-1;//额外添加的输入框次序清空
		 ELArrays.splice(0,ELArrays.length);//清空额外添加的输入框名称的数组记录
		 $(".plusTr").fadeOut("fast");
		 setTimeout("$('.plusTr').remove();",200);
		 $("#BtnRemoveAnother").hide();
	 }
	 
	var crossDayFlag=false;//是否跨天
	var whereCross="";
	 function floatMsg(){
		 var endTime=mini.get("endTime");
		 var startTime=mini.get("startTime");
		 var endTimeValue=endTime.getValue();
		 var startTimeValue=startTime.getValue();
		 if(endTimeValue<startTimeValue){
			 crossDayFlag=true;
			 whereCross+="endTime,";
			 mini.showTips({
		            content: "<b>提示</b> <br/>时间安排已跨天",
		            state: "danger",
		            x: 'center',
		            y: 'center',
		            timeout: 3500
		        });
		 }
	 }
	
	 function firstRegular(){
		 if(mini.get("startTime").getValue()==mini.get("endTime").getValue()){
			 return false;
		 }else{
			 true;
		 }
	 }
	 
	 /*是否时段正常*/
	 function isRegular(EL){
		var ELnum=EL.substring(EL.length-1,EL.length);
		var ELpre=EL.substring(0,EL.length-1);
		var thisEL=mini.get(EL);//当前输入框
		var prefixEL;//前一个输入框
		var suffixEL;//后一个输入框
		var nextNum=parseInt(ELnum)+1;
		var previousNum=parseInt(ELnum)-1;
		if(ELnum==0&&ELpre=="plusStart"){//如果是新建的第一个输入框则跟默认存在的输入框作对比
			prefixEL=mini.get("endTime");
			suffixEL=mini.get("plusEnd"+ELnum);
		}else if(mini.get("plusEnd"+nextNum)==null&&ELpre=="plusEnd"){//如果到末尾了则不验证
			prefixEL=mini.get("plusStart"+ELnum);
			suffixEL=null;
		}else if(ELpre=="plusStart"){//否则是中间的输入框
			prefixEL=mini.get("plusEnd"+previousNum);
			suffixEL=mini.get("plusEnd"+ELnum);
		}else if(ELpre=="plusEnd"){
			prefixEL=mini.get("plusStart"+ELnum);
			suffixEL=mini.get("plusStart"+nextNum);
		}
		if(thisEL.getValue()<=prefixEL.getValue()){
			if(crossDayFlag==true){
				return false;
			}
			crossDayFlag=true;
			whereCross+=thisEL.id+",";
			if(thisEL.getValue()==prefixEL.getValue()){
				return false;
			}
		}
	
		return true
	 }
	 
	 function toggleTheDiv(){
		 $("#toggleDiv").toggle("fast"); 
		$("#saveDIY").toggle("fast");
	 }
	 
	 
	 //保存自定义时间段
	 function saveTheDiyBlock(){
		 var form = new mini.Form("form1");
		 form.validate();
		 if (!form.isValid()) {
			 alert("请完成表格");
	            return;
	        }
		 var validateArrays=getAllEL();
		 crossDayFlag=false;
		 whereCross="";
		 if(firstRegular()){
			 mini.showTips({
		            content: "<b>提示</b> <br/>请不要使用相同时间",
		            state: "danger",
		            x: 'center',
		            y: 'center',
		            timeout: 3500
		        });
			 return;
		 }
		 for(var i=0;i<validateArrays.length;i++){
			 if(!isRegular(validateArrays[i])){
				 mini.showTips({
			            content: "<b>提示</b> <br/>请后续添加的时间段大小递增",
			            state: "danger",
			            x: 'center',
			            y: 'center',
			            timeout: 3500
			        });
				 return;
			 }
		 }
		 var formData=$("#form1").serializeArray();
		 formData.push({"name":"isUpdate","value":isUpdate});
		  _SubmitJson({
	        	url:'${ctxPath}/oa/calendar/workTimeBlock/save.do',
	        	method:'POST',
	        	data:formData,
	        	success:function(result){
	        		//如果存在自定义的函数，则回调
	        		if(isExitsFunction('successCallback')){
	        			successCallback.call(this,result);
	        			return;	
	        		}
	        		crossDayFlag=true;
	        		whereCross="";
	        	}
	        }); 
		
		 
		 
		 
		 $("#toggleDiv").fadeOut("fast"); 
		 $("#saveDIY").fadeOut("fast");
		 //重置数据,清空
		 setTimeout("$('.plusTr').remove();",200);
		 plusTime=-1;
		 ELArrays.splice(0,ELArrays.length);
		 form.clear();
		location.reload();
		 
	 }
	 
	 /*将所有添加的输入框的id 以数组的形式return*/
	 var ELArrays=new Array();
	 function getAllEL(){
		 ELArrays.splice(0,ELArrays.length);
			 for(var i=0;i<parseInt(plusTime);i++){
				 ELArrays.push("plusStart"+i,"plusEnd"+i);
			 }
		 return ELArrays;
	 }
	 
	 //获取workTimeBlock的value
	 function selectTheTimeBlock(){
		 var workTimeBlock=mini.get("workTimeBlock");
		 var value=workTimeBlock.getValue();
		 if(value==null){
			 value="";
		 }else{
			 openTheEditItem(value);
		 }
		 return value;
	 }
	 
	 /*打开编辑页*/
	 function openTheEditItem(blockId){
		 $(".plusTr").remove();
		plusTime=-1;
			if($("#toggleDiv").is(":hidden")){
			}else{
				$("#toggleDiv").toggle("fast");
			}
			if($("#saveDIY").is(":hidden")){
			}else{
				$("#saveDIY").toggle("fast");
			}
			$.ajax({
				url:'${ctxPath}/oa/calendar/workTimeBlock/getTimeBlock.do',
				type:"post",
				data:{"blockId":blockId},
				success:function(result){
					if(result.success){
						var json = eval(result.json);
						toggleTheDiv();
						for(var item in json){
							//alert("person中"+item+"的值="+json[item]);
							if(item.indexOf("plusStart") >= 0 ) 
							{ 
								addAnotherTimeBlock();
							} 
							}
						for(var item in json){
							if(item!="name"&&item!="isUpdate"){
								mini.get(item).setValue(json[item]);
							}
							}
						
						if(mini.get("grantNameToTimeBlock")!=null){
							mini.get("grantNameToTimeBlock").setValue(result.name);
							isUpdate=blockId;//是否更新
						}
					}
					
					
				}
			});
	 }
	 
	 function onDrawCells(e) {
			var item = e.record, field = e.field, value = e.value;
	        //组织HTML设置给cellHtml
	        var textLength=value.length;
	        var ancientValue=value;
	        if(textLength>15){
	        	textLength=15;	
	        	value=value.substring(0,textLength)+'...';
	        }
	       e.cellHtml = '<span title='+ancientValue+'>'+value+'</span>'+'<span style="color:red;font-size:15px;float:right;width:15px;cursor:pointer;" title="删除" onclick="deleteBlock(\''+item.settingId+'\');event.cancelBubble=true;"> ×</span>';
	        
	    }
	 
	 function clearTheUpdate(){
		 isUpdate=" ";
		 removeAnotherTimeBlock();
		 var blockForm =new mini.Form("#form1");
		 if(blockForm!=null){
			 blockForm.clear(); 
		 }
		 
	 }
	 
	 function deleteBlock(pkId){
		 if (confirm("确定当前记录？")) {
             $.ajax({
                 url: "${ctxPath}/oa/calendar/workTimeBlock/del.do",
                 data:{
                    ids:pkId
                 },
                 success: function(text) {
                	 mini.showTips({
 			            content: "<b>成功</b> <br/>删除成功",
 			            state: "success",
 			            x: 'center',
 			            y: 'center',
 			            timeout: 2000
 			        });
                	 mini.get('workTimeBlock').load("${ctxPath}/oa/calendar/workTimeBlock/getAllWorkTimeBlock.do");
                 },
                 error: function(err) {
                     form.unmask();
                     mini.showMessageBox({
                     showHeader: false,
                     width: 450,
                     title: "操作出错",
                     buttons: ["关闭"],
                     html: err,
                     iconCls: 'mini-messagebox-error'
                 });
                 }
             });
         }
	 }
	 
</script>
</body>
</html>
