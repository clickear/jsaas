<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>已申请会议室申请列表</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">

	    <div title="south" region="south" showSplit="false" showHeader="false" height="100" style="border:none"  bodyStyle="padding:6px;">
	       <form id="form1" method="post" style="margin-bottom:20px;">
		       	开始时间 <span class="star">*</span> ：<input id="start" name="start" showTime="true" allowInput="false" class="mini-datepicker" value="date"  required="true" format="yyyy-MM-dd HH:mm:ss"  timeFormat="HH:mm:ss" emptyText="请输入开始时间"  />&nbsp;&nbsp;&nbsp;
		       	结束时间 <span class="end">*</span> ：<input id="end" name="end"  showTime="true" allowInput="false" class="mini-datepicker" value="date"  required="true" format="yyyy-MM-dd HH:mm:ss"  timeFormat="HH:mm:ss" emptyText="请输入结束时间"  />
		     
	     	</form>
			<div style="clear:both;content: ''"></div>
			<div class="mini-toolbar dialog-footer" style="border: none;">
				<a class="mini-button" iconCls="icon-ok" onclick="onOk()">确定</a>
				<a class="mini-button" onclick="onCancel()" iconCls="icon-cancel">取消</a>
			</div>
	    </div>
	    
	    <div title="会议列表" region="center"  showHeader="false" style="border:none">
		       <div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/oa/res/oaMeeting/dateList.do?roomId=${param['roomId']}" idField="roomId"
				multiSelect="true" showPager="false"  allowAlternating="true" >
				<div property="columns">
					<div field="oaMeetRoom.name" width="120" headerAlign="center" allowSort="true">会议室</div>
					<div field="start" width="80" dateFormat="yyyy-MM-dd" headerAlign="center" allowSort="true">开始时间</div>
					<div field="end" width="80" dateFormat="yyyy-MM-dd" headerAlign="center" allowSort="true">结束时间</div>
					<div field="hostUid" width="120" headerAlign="center" allowSort="true">主持人</div>
				</div>
			</div>
	    </div>
	</div>

	<script type="text/javascript">
			mini.parse();
			var grid=mini.get("datagrid1");
			grid.load();
			
	        grid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;

	            //格式化日期
	            if (field == "start") {
	                if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
	            }
	            
	            if (field == "end") {
	                if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
	            }
	            
	            
	            if(field=='hostUid'){
	            	if(value){
	            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
	            	}else{
	            		e.cellHtml='<span style="color:red">无</span>';
	            	}
	            }
	        });
	        
	        grid.on('update',function(){
	        	_LoadUserInfo();
	        });
	        
	        
			function onCancel() {
				CloseWindow("cancel");
			}
	        
			function onOk() {
				var flag="false";
				flag=getDateList();
				if(flag==""){
					alert("时间不能为空!");
				}
				else if(flag=="true"){
					CloseWindow("ok");
				}else if(flag=="falses"){
					alert("开始时间不能早于结束时间!");	
				}else if(flag=="false"){
					alert("你选的时间段被占用!");
				}else if(flag="false1"){
					alert("会议的时间不能小于当前时间!");
				}
				
			}
        	
			function getStart(){
				var start=mini.get("start").getValue();
				var end=mini.get("end").getValue();
				var timeName=new Array();
					timeName[0]=start;
					timeName[1]=end;
					return timeName;
				
			}
			function getDateList(){
				var form = new mini.Form("form1");
				form.validate();
		        if (!form.isValid()) {
		        	
		            return "";
		        }
				var groud=mini.get("datagrid1").getData();
				var start=mini.get("start").getValue();
				var end=mini.get("end").getValue();
				var d = new Date();
				for(var i=0;i<groud.length;i++){
					var startDate=new Date(start);
					var endDate=new Date(end);
					if((startDate>groud[i].start&&startDate<groud[i].end)||(endDate>groud[i].start&&endDate<groud[i].end)){
						return "false";
					}
				    if(startDate>endDate){
				       return "falses";
				    }
				    if(startDate<d&&endDate<d){
				    	return "false1";
				    }
				}
				return "true";
			}
			
			//禁止显示小于当前的时间段
	        function onDrawDate(e) {
	            var date = e.date;
	            var d = new Date();

	            if (date.getTime() < d.getTime()) {
	                e.allowSelect = false;
	            }
	        }
        </script>
</body>
</html>