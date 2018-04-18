<%-- 
    Document   : 车辆编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[OaMeetRoom]车辆编辑</title>
<%@include file="/commons/edit.jsp"%>
<script type="text/javascript">
var sysDic=[{ id: '电动汽车', text: '电动汽车' }, { id: '轿车', text: '轿车'}, { id: '商务车', text: '商务车'}, { id: '越野车', text: '越野车'},{ id: '货车', text: '货车'}];
var statuCar=[{ id: 'INUSED', text: '在使用' }, { id: 'INFREE', text: '空闲'}, { id: 'SCRAP', text: '报废'}];
</script>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${oaCar.carId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="carId" class="mini-hidden"
					value="${oaCar.carId}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>车辆基本信息</caption>

					<tr>
						<th>车辆类别</th>
					 	<td>
						<ui:dicCombox id="sysDicId" name="sysDicId" dicKey="CLLB" required="true" value="${oaCar.sysDicId}" ></ui:dicCombox>
						</td>
					</tr>

					<tr>
						<th>车辆名称 <span class="star">*</span> 
						</th>
						<td><input name="name" value="${oaCar.name}"
							class="mini-textbox" vtype="maxLength:60" style="width: 200px"
							required="true" emptyText="请输入车辆名称" />

						</td>
					</tr>

					<tr>
						<th>车牌号 <span class="star">*</span> 
						</th>
						<td><input name="carNo" value="${oaCar.carNo}"
							class="mini-textbox" vtype="maxLength:20" style="width: 150px"
							required="true" emptyText="请输入车牌号" />

						</td>
					</tr>

					<tr>
						<th>行驶里程 </th>
						<td>
						<input name="travelMiles" value="${oaCar.travelMiles}"
							class="mini-spinner" value="123456.123" minValue="0"
							maxValue="1000000" format="#,0.00" vtype="float"
							style="width: 150px" />&nbsp;公里
						</td>
					</tr>

					<tr>
						<th>发动机号 </th>
						<td><input name="engineNum" value="${oaCar.engineNum}"
							class="mini-textbox" vtype="maxLength:20" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>车辆识别代号 </th>
						<td><input name="frameNo" value="${oaCar.frameNo}"
							class="mini-textbox" vtype="maxLength:20" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>品  牌 </th>
						<td><input name="brand" value="${oaCar.brand}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>型  号 </th>
						<td><input name="model" value="${oaCar.model}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>车辆载重 </th>
						<td><input name="weight" value="${oaCar.weight}"
							class="mini-spinner" value="123456.123" minValue="0"
							maxValue="1000000" format="#,0.00" vtype="float"
							style="width: 150px" />&nbsp;千克
						</td>
					</tr>

					<tr>
						<th>车辆座位 </th>
						<td><input name="seats" value="${oaCar.seats}"
							class="mini-spinner" value="123456.123" minValue="0"
							maxValue="1000000" format="#,0" vtype="float"
							style="width: 150px" />&nbsp;人

						</td>
					</tr>

					<tr>
						<th>购买日期 </th>
						<td><input id="buyDate" name="buyDate" value="${oaCar.buyDate}" style="width: 200px"
						 showTime="true" allowInput="false" class="mini-datepicker" value="date"  required="true"
						  format="yyyy-MM-dd HH:mm:ss"  timeFormat="HH:mm:ss" emptyText="请输入购买日期" />

						</td>
					</tr>

					<tr>
						<th>购买价格 </th>
						<td><input name="price" value="${oaCar.price}"
							class="mini-spinner" value="123456.123" minValue="0"
							maxValue="1000000" format="¥#,0.00" vtype="float"
							style="width: 150px" />&nbsp;元

						</td>
					</tr>
					<tr>
						<th>车辆状态 <span class="star">*</span>
							
						</th>
						<td><input name="status" id="status" class="mini-combobox" style="width: 150px; color: #000000;"  data="statuCar"
					 value="${oaCar.status}" required="true" allowInput="false" emptyText="请选择车辆状态" />
						</td>
					</tr>
					<tr>
						<th>年检情况 </th>
						<td><textarea name="annualInsp" height="100" class="mini-textarea" vtype="maxLength:65535" style="width: 90%">${oaCar.annualInsp}</textarea></td>
					</tr>

					<tr>
						<th>保险情况 </th>
						<td><textarea name="insurance" height="100" class="mini-textarea" vtype="maxLength:65535" style="width: 90%">${oaCar.insurance}</textarea></td>
					</tr>

					<tr>
						<th>保养维修情况 </th>
						<td><textarea name="maintens" height="100" class="mini-textarea" vtype="maxLength:65535" style="width: 90%">${oaCar.maintens}</textarea></td>
					</tr>

					<tr>
						<th>备注信息 </th>
						<td><textarea name="memo" height="100" class="mini-textarea" vtype="maxLength:65535" style="width: 90%">${oaCar.memo}</textarea></td>
					</tr>

					<tr>
						<th>车辆照片 </th>
						<td>
						<input name="photoIds" value="${oaCar.photoIds}" class="mini-hidden" vtype="maxLength:64" /> 
									<img src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${oaCar.photoIds}" class="upload-file" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/res/oaCar"
		entityName="com.redxun.oa.res.entity.OaCar" />
		<script type="text/javascript">
		//车辆图片
		$(function(){
			 $(".upload-file").on('click',function(){
				 var img=this;
				_UserImageDlg(true,
					function(imgs){
						if(imgs.length==0) return;
						$(img).attr('src','${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId='+imgs[0].fileId);
						$(img).siblings('input[type="hidden"]').val(imgs[0].fileId);
						
					}
				);
			 });
		});
		</script>
</body>
</html>