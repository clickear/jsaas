<%-- 
    Document   : 车辆申请编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>车辆申请编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
		<div id="toolbar" class="mini-toolbar" style="padding: 2px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;" id="toolbarBody"><a
					class="mini-button" iconCls="icon-save" plain="true"
					onclick="saveAllData()">保存</a> <a class="mini-button"
					iconCls="icon-close" plain="true" onclick="onCancel">关闭</a></td>
			</tr>
		</table>
	</div>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="appId" class="mini-hidden"
					value="${oaCarApp.appId}" />
				<table class="table-detail column_2" cellspacing="1" cellpadding="0">
					<caption>车辆申请基本信息</caption>

						<tr>
					<th>起始时间</th>
					<td><fmt:formatDate value="${oaCarApp.startTime}" pattern="yyyy-MM-dd HH:mm" /></td>
					</tr>

					<tr>
						<th>延迟时间 <span class="star">*</span> 
						</th>
						<td><input id="endTime" name="endTime" value="${oaCarApp.endTime}" required="true" class="mini-datepicker" showTime="true" style="width: 200px" format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm:ss" vtype="maxLength:19" emptyText="请输入终止时间" />
						</td>
					</tr>
					
					<tr>
						<th>选用车辆 <span class="star"></span> 
						</th>
						<td>${oaCarApp.carCat}
							${oaCarApp.carName}

						</td>
					</tr>

					<tr>
						<th>驾驶员 </th>
						<td>${oaCarApp.driver}</td>
					</tr>

					<tr>
						<th>行程类别 </th>
						<td>${oaCarApp.category}
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/res/oaCarApp"
		entityName="com.redxun.oa.res.entity.OaCarApp" />
	<script type="text/javascript">
	mini.parse();
	var form = new mini.Form("form1");
	/*保存车辆申请信息*/
	 function saveAllData(){
		 form.validate();
	     if (form.isValid() == false) {
	         return;
	     }
	     var formData=$("#form1").serializeArray();

	     _SubmitJson({
	     	url:'${ctxPath}/oa/res/oaCarApp/saveCarEnd.do?',
	     	method:'POST',
	     	data:formData,
	     	success:function(result){
	 				CloseWindow('ok');
	 				return;
	 		
	     	}
	     });
	}
	</script>
</body>
</html>