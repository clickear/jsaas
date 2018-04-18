<%-- 
    Document   : [HrDutyInst]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDutyInst]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${hrDutyInst.dutyInstId}" hideRemove="true" hideRecordNav="true" />
	<p>姓名：${hrDutyInst.userName}</p>
	<p>部门：${hrDutyInst.depName}</p>
	<p>日期：${hrDutyInst.date}</p>
	<%-- <c:if test="${hrDutyInst.vacApp!=''||hrDutyInst.otApp!=''||hrDutyInst.trApp!=''||hrDutyInst.outApp!=''}">
		
	</c:if> --%>
	<div>
		<div id="vacation"></div>
		<div id="overtime"></div>
		<div id="transrest"></div>
		<div id="out"></div>
	</div>
	
	
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="dutyInstId" class="mini-hidden" value="${hrDutyInst.dutyInstId}" />
				<table id="sectionTable" style="width: 100%" class="table-detail" cellpadding="0" cellspacing="1">
					<caption>班次信息</caption>
					
					<tr>
						<td width="200">班次</td>
						<td>简称</td>
						<td>时段</td>
						<td>上班</td>
						<td>下班</td>
					</tr>
	
					
					
				</table>
			</div>
		</form>
	</div>
	<div id="exts" style="display:none">${exts} </div>
	<rx:formScript formId="form1" baseUrl="hr/core/hrDutyInst" entityName="com.redxun.hr.core.entity.HrDutyInst" />
	<script type="text/javascript">
		

		$(function(){
			var sectionNum="${fn:length(hrDutyInst.hrDutyInstExts)}";
			var sections=jQuery.parseJSON($('#exts').html());
			var holiday='${hrDutyInst.hrHoliday}';
			var my="${param['my']}";
			if(holiday!=''){
				var row='<tr>';
				row=row+'<td rowspan="2">'+"节假日"+"（"+"节"+"）"+'<br/><input id="sectionId" '+(my=="true"?'enabled="false"':'')+' class="mini-combobox" minWidth="80"  maxWidth="100" url="${ctxPath}/hr/core/hrDutySection/getAllSectionAndRestSection.do" value="---更改---" valueField="sectionId" textField="sectionName" showNullItem="true" nullItemText="---更改---"  />'+'</td>';
				row=row+'<td rowspan="2">'+"节"+'</td>';
				row=row+'<td rowspan="2">'+'</td>';
				row=row+'<td rowspan="2">'+'</td>';
				row=row+'<td rowspan="2">'+'</td></tr>';
				$("#sectionTable").append(row);
			}else{
				if(sectionNum<=0){
					var row='<tr>';
					row=row+'<td rowspan="2">'+"${hrDutyInst.sectionName}"+"（"+"${hrDutyInst.sectionShortName}"+"）"+'<br/><input id="sectionId" '+(my=="true"?'enabled="false"':'')+' class="mini-combobox" minWidth="80"  maxWidth="100" url="${ctxPath}/hr/core/hrDutySection/getAllSectionAndRestSection.do" value="---更改---" valueField="sectionId" textField="sectionName" showNullItem="true" nullItemText="---更改---"  />'+'</td>';
					row=row+'<td rowspan="2">'+"${hrDutyInst.sectionShortName}"+'</td>';
					row=row+'<td rowspan="2">'+'</td>';
					row=row+'<td rowspan="2">'+'</td>';
					row=row+'<td rowspan="2">'+'</td></tr>';
					$("#sectionTable").append(row);
				}else{
					for(var i=0;i<sectionNum;i++){
						var row='<tr>';
						if(i==0){
							row=row+'<td rowspan="'+(sectionNum<=1?2:sectionNum)+'">'+"${hrDutyInst.sectionName}"+"（"+"${hrDutyInst.sectionShortName}"+"）"+'<br/><input id="sectionId" '+(my=="true"?'enabled="false"':'')+' class="mini-combobox" minWidth="80" maxWidth="100" url="${ctxPath}/hr/core/hrDutySection/getAllSectionAndRestSection.do" value="---更改---" valueField="sectionId" textField="sectionName" showNullItem="true" nullItemText="---更改---"  />'+'</td>';
							row=row+'<td rowspan="'+(sectionNum<=1?2:sectionNum)+'">'+"${hrDutyInst.sectionShortName}"+'</td>';
							row=row+'<td rowspan="'+(sectionNum<=1?2:0)+'">'+(i+1)+'</td>';
							row=row+'<td rowspan="'+(sectionNum<=1?2:0)+'">'+sections[i].dutyStartTime+'</td>';
							row=row+'<td rowspan="'+(sectionNum<=1?2:0)+'">'+sections[i].dutyEndTime+'</td></tr>';
						}
						else{
							row=row+'<td>'+(i+1)+'</td>';
							row=row+'<td>'+sections[i].dutyStartTime+'</td>';
							row=row+'<td>'+sections[i].dutyEndTime+'</td></tr>';
						}
						$("#sectionTable").append(row);
					}
				}
			}
			setApply();
		});
		
		function selfSaveData(){
			_SubmitJson({
				url:__rootPath+"/hr/core/hrDutyInst/updateInst.do",
				method:'POST',
				data:{
					pkId:mini.get("pkId").getValue(),
					sectionId:mini.get("sectionId").getValue()
				},
				success:function(result){
					CloseWindow('ok');
				}
			});
		}
		
		function setApply(){
			var vacData=mini.decode('${hrDutyInst.vacApp}');
			var otData=mini.decode('${hrDutyInst.otApp}');
			var trData=mini.decode('${hrDutyInst.trApp}');
			var outData=mini.decode('${hrDutyInst.outApp}');
			for(var i=0;i<vacData.length;i++){
				var p="<p>"+"请假 "+mini.formatDate(new Date(vacData[i].startTime), "yyyy-MM-dd HH:mm:ss")+"-"+mini.formatDate(new Date(vacData[i].endTime),"yyyy-MM-dd HH:mm:ss")+"<br />原因："+vacData[i].reason+"</p>";
				$("#vacation").append(p);
			}
			for(var i=0;i<otData.length;i++){
				var p="<p>"+"加班 "+mini.formatDate(new Date(otData[i].startTime), "yyyy-MM-dd HH:mm:ss")+"-"+mini.formatDate(new Date(otData[i].endTime), "yyyy-MM-dd HH:mm:ss")+"<br />原因："+otData[i].reason+"</p>";
				$("#overtime").append(p);
			}
			for(var i=0;i<trData.length;i++){
				var p="<p>"+"调休 "+mini.formatDate(new Date(trData[i].startTime), "yyyy-MM-dd HH:mm:ss")+"-"+mini.formatDate(new Date(trData[i].endTime), "yyyy-MM-dd HH:mm:ss")+"<br />原因："+trData[i].reason+"</p>";
				$("#transrest").append(p);
			}
			for(var i=0;i<outData.length;i++){
				var p="<p>"+"出差 "+mini.formatDate(new Date(outData[i].startTime), "yyyy-MM-dd HH:mm:ss")+"-"+mini.formatDate(new Date(outData[i].endTime), "yyyy-MM-dd HH:mm:ss")+"<br />原因："+outData[i].reason+"</p>";
				$("#out").append(p);
			}
		}
	</script>
</body>
</html>