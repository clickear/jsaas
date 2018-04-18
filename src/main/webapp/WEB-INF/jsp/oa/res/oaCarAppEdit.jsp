<%-- 
    Document   : 车辆申请编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>车辆申请编辑</title>
<%@include file="/commons/edit.jsp"%>
<script type="text/javascript">
var cateCar=[{ id: 'INBERLIN', text: '市内' }, { id: 'INSHORT', text: '省内短途'}, { id: 'INOUTSIDE', text: '省外长途'}];
</script>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${oaCarApp.appId}" excludeButtons="save">
		<div class="self-toolbar">
			<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveAllData">保存</a>
		</div>
	</rx:toolbar>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="appId" class="mini-hidden"
					value="${oaCarApp.appId}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>车辆申请基本信息</caption>

						<tr>
						<th>起始时间 <span class="star">*</span> 
						</th>
						<td>
						<input id="startTime" name="startTime" value="${oaCarApp.startTime}" required="true" class="mini-datepicker" showTime="true" style="width: 200px" format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm:ss" vtype="maxLength:19" emptyText="请输入起始时间" />

						</td>
					</tr>

					<tr>
						<th>终止时间 <span class="star">*</span> 
						</th>
						<td><input id="endTime" name="endTime" value="${oaCarApp.endTime}" required="true" class="mini-datepicker" showTime="true" style="width: 200px" format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm:ss" vtype="maxLength:19" emptyText="请输入终止时间" />
						<a class="mini-button" iconCls="icon-attachAdd" plain="true" onclick="onCar">立即选车</a>
						</td>
					</tr>
					
					<tr>
						<th>选用车辆 <span class="star">*</span> 
						</th>
						<td>
						<input id="carId" name="carId" class="mini-hidden"
							value="${oaCarApp.carId}" />
						<input id="carCat" name="carCat" value="${oaCarApp.carCat}" required="true" readonly="readonly" emptyText="车辆类型"
							class="mini-textbox" vtype="maxLength:50" style="width: 150px" />
							
						<input id="carName" name="carName" value="${oaCarApp.carName}" text="${carName}" readonly="readonly"
							class="mini-textbox" textField="name" valueField="carId" vtype="maxLength:50" style="width: 150px"
							required="true" emptyText="车辆名称" />

						</td>
					</tr>

					<tr>
						<th>驾驶员 </th>
						<td>
						<input name="driver" value="${oaCarApp.driver}"  class="mini-textbox"
							 vtype="maxLength:64" style="width: 150px" />

						</td>
					</tr>

					<tr>
						<th>行程类别 </th>
						<td><input name="category" id="category" class="mini-combobox" style="width: 150px; color: #000000;"  data="cateCar"
					 value="${oaCarApp.category}" required="false" allowInput="false" emptyText="请选择行程类别" />
						</td>
					</tr>

					<tr>
						<th>目的地点 </th>
						<td><input name="destLoc" value="${oaCarApp.destLoc}"
							class="mini-textbox" vtype="maxLength:100" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>行驶里程 </th>
						<td><input name="travMiles" value="${oaCarApp.travMiles}"
							class="mini-spinner" value="123456.123" minValue="0"
							maxValue="1000000" format="#,0.00" vtype="float"
							style="width: 150px" />&nbsp;公里

						</td>
					</tr>

					<tr>
						<th>使用人员 </th>
						<td><input name="useMans" height="50" value="${oaCarApp.useMans}"
							class="mini-textarea" vtype="maxLength:65535" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>使用说明 </th>
						<td><textarea name="memo" height="100" class="mini-textarea" vtype="maxLength:65535" style="width: 90%">${oaCarApp.memo}</textarea></td>
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
	var sysDicIdDS=mini.get('carCat');
	var carNameDS=mini.get('carName');
	/*保存车辆申请信息*/
	function saveAllData(){ 
		form.validate();
		if (!form.isValid()) {
			return;
		}
		var formData = $("#form1").serializeArray();
		$.ajax({
			   type: "POST",
			   url : __rootPath + '/oa/res/oaCarApp/saveCar.do',
			   async:false,
				data : formData,
				success : function(result) {
		    		if(isExitsFunction('successCallback')){
		    			successCallback.call(this,result);
		    			return;	
		    		}
		    		var pkId=mini.get("pkId").getValue();
		    		//为更新
		    		if (pkId!=''){
		    			CloseWindow('ok');
		    			return;
		    		}
		    		CloseWindow('ok');}
			});
	}
	//获取车辆数据
	function onDeptChanged(e){
		var sysDicId=sysDicIdDS.getValue();
		carNameDS.setValue("");
		var url = "${ctxPath}/oa/res/oaCar/getCarByDicId.do?sysDicId=" + sysDicId;
		carNameDS.setUrl(url);
        
		carNameDS.select(0);
	}
	//根据时间段获取空闲的汽车
	function onCar(){ 
		var startTime=mini.get("startTime").getFormValue();
		var endTime=mini.get("endTime").getFormValue();
		if(startTime > endTime){
			alert("终止时间不能小于起始时间！");
			return;
		}else if(startTime==""||endTime==""){
			alert("请先选着时间段！");
			return;
		}else{
			 _OpenWindow({
					url : "${ctxPath}" + "/oa/res/oaCarNew/List.do?startTime="+startTime+"&endTime="+endTime,
					title : "空闲时间车辆列表",
					iconCls:'icon-transmit',
					height : 500,
					width : 700,
					ondestroy:function(action){
						if (action == "ok") {
							var iframe = this.getIFrameEl();
							var data = iframe.contentWindow.getOaCar();
							data=mini.clone(data);
							if(data){
								mini.get("carCat").setValue(data.sysDicId);
								mini.get("carId").setValue(data.carId);
								mini.get("carName").setValue(data.name);
							}
							/* mini.get("carCat").setValue(data[0]);
							mini.get("carName").setValue(data[1]); */
						}
					}
				}); 
		}
	}
	</script>
</body>
</html>