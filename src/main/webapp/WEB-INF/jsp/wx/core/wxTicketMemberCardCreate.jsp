
<%-- 
    Document   : [微信卡券]编辑页
    Created on : 2017-08-22 10:23:23
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[微信卡券]编辑</title>
<%@include file="/commons/edit.jsp"%>
<style type="text/css">
.colorBlob{
height: 25px;
width: 25px;
display: inline-block;
border-radius:5px;
margin-top: 5px;
}
.colorBlob:hover{
opacity:0.8;
border:solid 2px #EEB4B4;
margin:-2px;
}
.SelectedColorBlob{
border:outset 3px white;
margin:-3px;
}

.toggleButton{
	margin: 10px;
	display: inline;
	color: #FFFFFF;
	background-color: #99CCFF;
	border: 1px solid #6699CC;
	border-radius: 35%;
	cursor: pointer;
	}
.toggleButton:hover{
	background-color: #79CDCD;
	}
.togglePlusButton{
	margin: 10px;
	display: inline;
	color: #FFFFFF;
	background-color: #99CCFF;
	border: 1px solid #6699CC;
	border-radius: 35%;
	cursor: pointer;
	}
.togglePlusButton:hover{
	background-color: #79CDCD;
	}
</style>
</head>
<body>
	
<div id="toolbar1" class="mini-toolbar topBtn">
   <a class="mini-button" iconCls="icon-ok" plain="true" onclick="createTicket()">保存</a>
   <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="CloseWindow('cancel')">取消</a>
</div>
<div class="shadowBox90" style="padding-top: 8px; margin-bottom: 40px;">
<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input class="mini-hidden"  id="logoUrl"  name="logoUrl" />
				<input class="mini-hidden"  id="logoUrlFileId"  name="logoUrlFileId" />
				<input class="mini-hidden"  id="icon_url_list" name="icon_url_list" />
				<input class="mini-hidden"  id="icon_url_listField" name="icon_url_listField" />
				<input class="mini-hidden"  id="background_pic_url" name="background_pic_url" />
				<input class="mini-hidden"  id="background_pic_Field" name="background_pic_Field" />
				<input class="mini-hidden"  id="pubId"  name="pubId" value="${pubId}"/>
				<input class="mini-hidden"  id="cardColor"  name="cardColor"  value="${wxTicket.color}"/>
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
						<tr>
							<th>
								<span class="starBox">
									商户名称<span class="star">*</span>
								</span>
							</th>
							<td><input id="brandName" name="brandName" class="mini-textbox" emptyText="12个汉字以内" maxLength="12" required="true" value="${wxTicket.brandName}"/></td>
						</tr>
						<tr>
							<th>
								<span class="starBox">
									卡  券  名<span class="star">*</span>
								</span>
							</th>
							<td><input id="title" name="title" class="mini-textbox" emptyText="9个汉字以内" maxLength="9" required="true" value="${wxTicket.title}"/></td>
						</tr>
						<tr>
							<th>商户logo</th>
							<td><img id="uploadLogo" name="uploadLogo" alt="请上传" style="width: 45px; height: 45px; margin-top: 5px; margin-left: 10px;" onclick="openUpload()"></img>
							</td>
						</tr>
						<tr>
						<th>选择卡券背景色</th>
						<td>
							<div class="colorBlob" id="Color010" style="background-color: #63b359;" onclick="chooseColor('Color010')"></div>
							<div class="colorBlob" id="Color020" style="background-color: #2c9f67;" onclick="chooseColor('Color020')"></div>
							<div class="colorBlob" id="Color030" style="background-color: #509fc9;" onclick="chooseColor('Color030')"></div>
							<div class="colorBlob" id="Color040" style="background-color: #5885cf;" onclick="chooseColor('Color040')"></div>
							<div class="colorBlob" id="Color050" style="background-color: #9062c0;" onclick="chooseColor('Color050')"></div>
							<div class="colorBlob" id="Color060" style="background-color: #d09a45;" onclick="chooseColor('Color060')"></div>
							<div class="colorBlob" id="Color070" style="background-color: #e4b138;" onclick="chooseColor('Color070')"></div>
							<div class="colorBlob" id="Color080" style="background-color: #ee903c;" onclick="chooseColor('Color080')"></div>
							<div class="colorBlob" id="Color081" style="background-color: #f08500;" onclick="chooseColor('Color081')"></div>
							<div class="colorBlob" id="Color082" style="background-color: #a9d92d;" onclick="chooseColor('Color082')"></div>
							<div class="colorBlob" id="Color090" style="background-color: #dd6549;" onclick="chooseColor('Color090')"></div>
							<div class="colorBlob" id="Color100" style="background-color: #cc463d;" onclick="chooseColor('Color100')"></div>
							<div class="colorBlob" id="Color101" style="background-color: #cf3e36;" onclick="chooseColor('Color101')"></div>
							<div class="colorBlob" id="Color102" style="background-color: #5E6671;" onclick="chooseColor('Color102')"></div>
						</td>
					</tr>
					<tr>
						<th>券  码  型</th>
						<td><div id="codeType" name="codeType" class="mini-radiobuttonlist" repeatItems="6" repeatLayout="table"  required="true"
    textField="text" valueField="id" value="CODE_TYPE_TEXT"  data="[{'id':'CODE_TYPE_TEXT','text':'文本'},{'id':'CODE_TYPE_BARCODE','text':'一维码'},{'id':'CODE_TYPE_QRCODE','text':'二维码'},{'id':'CODE_TYPE_ONLY_QRCODE','text':'二维码无code'},{'id':'CODE_TYPE_ONLY_BARCODE','text':'一维码无code'},{'id':'CODE_TYPE_NONE','text':'空类型'}]" >
						</td>
					</tr>
					<tr>
					<th>卡券库存</th>
					<td>
					<input id="quantity" name="quantity" class="mini-spinner"  minValue="0" maxValue="100000000" required="true" format="0"/>
					</td>
					</tr>
					<tr>
					<th rowspan="2">
						<span class="starBox">
							时效类型<span class="star">*</span>
						</span>
					</th>
					<td>
					<div id="type" name="type" class="mini-radiobuttonlist" repeatItems="6" repeatLayout="table" onvaluechanged="changeType" required="true"
    textField="text" valueField="id" value="DATE_TYPE_FIX_TIME_RANGE"  data="[{'id':'DATE_TYPE_FIX_TIME_RANGE','text':'固定日期区间'},{'id':'DATE_TYPE_FIX_TERM','text':'表示固定时长'}]" >
					</td>
					</tr>
					<tr class="DATE_TYPE_FIX_TIME_RANGE" style="display: none;">
					<td>
					<input id="begin_timestamp" name="begin_timestamp" class="mini-datepicker" style="width:200px;"  nullValue="null" required="true"
        format="yyyy-MM-dd H:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
        至
        <input id="end_timestamp" name="end_timestamp"  class="mini-datepicker" style="width:200px;"  nullValue="null" required="true"
        format="yyyy-MM-dd H:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
					</td>
					</tr>
					<tr class="DATE_TYPE_FIX_TERM" style="display: none;">
					<td>
					<input id="fixed_begin_term" name="fixed_begin_term" class="mini-spinner"  minValue="0" maxValue="10000" style="width:50px;" required="true"/>天后启用,
					<input id="fixed_term" name="fixed_term" class="mini-spinner"  minValue="1" maxValue="10000" style="width:50px;" required="true"/>天内有效
					</td>
					</tr>
					<tr>
					<th>
						<span class="starBox">
							使用提示<span class="star">*</span>
						</span>
					</th>
					<td><input id="notice" name="notice" class="mini-textbox" emptyText="16个汉字以内"  maxLength="16" style="width: 500px;" required="true" value="${wxTicket.notice}"/></td>
					</tr>
					<tr>
					<th>
						<span class="starBox">
							使用说明<span class="star">*</span>
						</span>
					</th>
					<td><textarea id="description" name="description" class="mini-textarea" emptyText="1024个汉字以内"  maxLength="1024" style="width: 500px;" required="true" value="${wxTicket.description}"></textarea></td>
					</tr>
					<tr>
					<th>
						<span class="starBox">
							会员卡特权<span class="star">*</span>
						</span>
					</th>
					<td><textarea id="prerogative" name="prerogative" class="mini-textarea" emptyText="1024个汉字以内"  maxLength="1024" style="width: 500px;" required="true" ></textarea></td>
					</tr>
					<!-- -------------------------------------------分隔表单--------------------------------------------------------------- -->
					<tr>
					<td colspan="2" style="text-align: center;font-size: large;"><div class="toggleButton" title="基本非必填内容" onclick="toggleForm()">︾</div><div class="togglePlusButton" title="额外内容" onclick="togglePlusForm()">︾</div></td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>激活方式</th>
					<td><div class="mini-radiobuttonlist" repeatItems="3" repeatLayout="table" repeatDirection="vertical" id="actictWay" name="actictWay"
    				textField="text" valueField="id" data="[{'id':'auto_activate','text':'自动激活'},{'id':'wx_activate','text':'一键开卡'},{'id':'activate_url','text':'传入url'}]"  onvaluechanged="activeChange"></div>
    				</td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>客服电话</th>
					<td><input id="service_phone" name="service_phone" class="mini-textbox" emptyText="请输入电话"  maxLength="15"  /></td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>顶部按钮文字</th>
					<td><input id="center_title" name="center_title" class="mini-textbox" emptyText="请填写按钮文字,如:立即使用"  maxLength="15"  />
					<input id="center_url" name="center_url" class="mini-textbox" emptyText="请填写按钮URL"  maxLength="128"  />
					</td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>卡面背景</th>
					<td><img id="background_pic_id"  alt="请点击上传卡面背景" style="width: 120px; height: 50px; margin-top: 5px;" onclick="openUploadBackground()"></td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>会员卡副标题</th>
					<td><input id="center_sub_title" name="center_sub_title" class="mini-textbox" emptyText="请输入副标题"  maxLength="15"  /></td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>下方自定义入口</th>
					<td><input id="custom_url_name" name="custom_url_name" class="mini-textbox" emptyText="请填写入口名称"  maxLength="15"  />
					<input id="custom_url" name="custom_url" class="mini-textbox" emptyText="请填写入口url"  maxLength="128"  />
					<input id="custom_url_sub_title" name="custom_url_sub_title" class="mini-textbox" emptyText="请填写入口提示语"  maxLength="18"  /></td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>分享权限</th>
					<td><div id="can_share" name="can_share" class="mini-checkbox"  value="true"  text="领卡页面是否可分享"></div>&nbsp;
					<div id="can_give_friend" name="can_give_friend" class="mini-checkbox"  value="true"  text="卡券是否可转赠"></div></td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>会员卡享有减免额度</th>
					<td><input style="width: 50px;" id="discount" name="discount" class="mini-spinner"  minValue="0" maxValue="100"  format="0"/>%</td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th rowspan="2">积分规则<br/>显示积分<div id="supply_bonus" name="supply_bonus" class="mini-checkbox"  value="true"  onvaluechanged="changeShowBonus()"></div></th>
					<td>每<input style="width: 80px;" id="cost_money_unit" name="cost_money_unit" class="mini-spinner"  minValue="0" maxValue="100000000" format="c"  />元<b style="color: red;">增加</b><input style="width: 80px;" id="increase_bonus" name="increase_bonus" class="mini-spinner"  minValue="0" maxValue="1000000"   format="0"/>分&nbsp;&nbsp;每<input style="width: 80px;" id="cost_bonus_unit" name="cost_bonus_unit" class="mini-spinner"  minValue="0" maxValue="1000000"   format="0"/>分<b style="color: blue;">抵扣</b><input style="width: 80px;" id="reduce_money" name="reduce_money" class="mini-spinner"  minValue="0" maxValue="100000000" format="c"  format="0"/>元</td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<td>每笔满<input style="width: 80px;" id="least_money_to_use_bonus" name="least_money_to_use_bonus" class="mini-spinner"  minValue="0" maxValue="1000000"  format="c"/>元可用,每次最多使用<input style="width: 80px;" id="max_reduce_bonus" name="max_reduce_bonus" class="mini-spinner"  minValue="0" maxValue="100000000"   format="0"/>分</td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>备用积分查询url</th>
					<td><input id="bonus_url" name="bonus_url" class="mini-textbox" emptyText="请输入url" style="width: 240px;"  /></td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>积分清零规则</th>
					<td><textarea class="mini-textarea" id="bonus_cleared" name="bonus_cleared" style="width: 300px" maxLength="50"  emptyText="请输入规则,五十字以内"></textarea></td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>积分规则</th>
					<td><textarea class="mini-textarea" id="bonus_rules" name="bonus_rules" style="width: 300px" maxLength="50" emptyText="请输入规则,五十字以内"></textarea></td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>储值功能</th>
					<td>
					<div id="supply_balance" name="supply_balance" class="mini-checkbox"  value="true"  text="支持储值" onvaluechanged="balanceCheck"></div></td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>备用余额查询url</th>
					<td><input id="balance_url" name="balance_url" class="mini-textbox" emptyText="请输入url" style="width: 240px;"  /></td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>储值说明</th>
					<td><textarea class="mini-textarea" id="balance_rules" name="balance_rules" style="width: 300px" emptyText="请输入规则,五十字以内"></textarea></td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>自定义类目1</th>
					<td><div id="custom_field1" name="custom_field1" class="mini-radiobuttonlist" repeatItems="8" repeatLayout="table" textField="text" valueField="id" data="[{'text':'等级','id':'FIELD_NAME_TYPE_LEVEL'},{'text':'优惠券','id':'FIELD_NAME_TYPE_COUPON'},{'text':'印花','id':'FIELD_NAME_TYPE_STAMP'},{'text':'折扣','id':'FIELD_NAME_TYPE_DISCOUNT'},{'text':'成就','id':'FIELD_NAME_TYPE_ACHIEVEMEN'},{'text':'里程','id':'FIELD_NAME_TYPE_MILEAGE'},{'text':'集点','id':'FIELD_NAME_TYPE_SET_POINTS'},{'text':'次数','id':'FIELD_NAME_TYPE_TIMS'}]"></div>
					<input id="custom_field1_url" name="custom_field1_url" class="mini-textbox" emptyText="请输入url" style="width: 240px;"  /></td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>自定义类目2</th>
					<td><div id="custom_field2" name="custom_field2" class="mini-radiobuttonlist" repeatItems="8" repeatLayout="table" textField="text" valueField="id" data="[{'text':'等级','id':'FIELD_NAME_TYPE_LEVEL'},{'text':'优惠券','id':'FIELD_NAME_TYPE_COUPON'},{'text':'印花','id':'FIELD_NAME_TYPE_STAMP'},{'text':'折扣','id':'FIELD_NAME_TYPE_DISCOUNT'},{'text':'成就','id':'FIELD_NAME_TYPE_ACHIEVEMEN'},{'text':'里程','id':'FIELD_NAME_TYPE_MILEAGE'},{'text':'集点','id':'FIELD_NAME_TYPE_SET_POINTS'},{'text':'次数','id':'FIELD_NAME_TYPE_TIMS'}]"></div>
					<input id="custom_field2_url" name="custom_field2_url" class="mini-textbox" emptyText="请输入url" style="width: 240px;"  /></td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>自定义类目2</th>
					<td><div id="custom_field3" name="custom_field3" class="mini-radiobuttonlist" repeatItems="8" repeatLayout="table" textField="text" valueField="id" data="[{'text':'等级','id':'FIELD_NAME_TYPE_LEVEL'},{'text':'优惠券','id':'FIELD_NAME_TYPE_COUPON'},{'text':'印花','id':'FIELD_NAME_TYPE_STAMP'},{'text':'折扣','id':'FIELD_NAME_TYPE_DISCOUNT'},{'text':'成就','id':'FIELD_NAME_TYPE_ACHIEVEMEN'},{'text':'里程','id':'FIELD_NAME_TYPE_MILEAGE'},{'text':'集点','id':'FIELD_NAME_TYPE_SET_POINTS'},{'text':'次数','id':'FIELD_NAME_TYPE_TIMS'}]"></div>
					<input id="custom_field3_url" name="custom_field3_url" class="mini-textbox" emptyText="请输入url" style="width: 240px;"  /></td>
					</tr>
					<tr class="advanced" style="display: none;background-color: #FFF5EE;">
					<th>自定义信息</th>
					<td>
					<input id="custom_cell1_name" name="custom_cell1_name" class="mini-textbox" emptyText="入口名称" maxLength="15" style="width: 100px;"  />
					<input id="custom_cell1_tips" name="custom_cell1_tips" class="mini-textbox" emptyText="入口提示语" maxLength="6" style="width: 100px;"  />
					<input id="custom_cell1_url" name="custom_cell1_url" class="mini-textbox" emptyText="入口url" maxLength="100" style="width: 200px;"  />
					</td>
					</tr>
					<!-- -------------------------------------------分隔表单   PLUS--------------------------------------------------------------- -->
					<tr class="plus" style="display: none;background-color: #F0FFF0;">
					<th>可否共享优惠</th>
					<td>
					<div id="can_use_with_other_discount" name="can_use_with_other_discount"  class="mini-checkbox"  value="true"  text="与其他优惠共享"></div>
					</td>
					</tr>
					<tr class="plus" style="display: none;background-color: #F0FFF0;">
					<th>封面图片</th>
					<td ><img id="abstractIcon"  alt="请点击上传封面" style="width: 120px; height: 50px; margin-top: 5px;" onclick="openUploadAbstract()">
					<input id="abstract" name="abstract" class="mini-textbox" emptyText="填写封面摘要"  maxLength="24"  style="margin-top: -30px;width: 300px;" /></td>
					</tr>
					<tr class="plus" style="display: none;background-color: #F0FFF0;">
					<th>服务类型</th>
					<td><div id="business_service" name="business_service" class="mini-checkboxlist" repeatItems="4" repeatLayout="table" data="[{'id':'BIZ_SERVICE_DELIVER','text':'外卖服务'},{'id':'BIZ_SERVICE_WITH_PET','text':'可带宠物'},{'id':'BIZ_SERVICE_FREE_PARK','text':'停车位'},{'id':'BIZ_SERVICE_FREE_WIFI','text':'免费wifi'}]"
        textField="text" valueField="id" ></td>
					</tr>
					<tr class="plus" style="display: none;background-color: #F0FFF0;">
					<th>适用周期</th>
					<td><div id="week" name="week" class="mini-checkboxlist" repeatItems="7" repeatLayout="table" data="[{'id':'MONDAY','text':'周一'},{'id':'TUESDAY','text':'周二'},{'id':'WEDNESDAY','text':'周三'},{'id':'THURSDAY','text':'周四'},{'id':'FRIDAY','text':'周五'},{'id':'SATURDAY','text':'周六'},{'id':'SUNDAY','text':'周日'}]"
        textField="text" valueField="id" ></td>
					</tr>
					<tr class="plus" style="display: none;background-color: #F0FFF0;">
					<th>适用时间段</th>
					<td>开始时分<input id="beginUseTime" name="beginUseTime" class="mini-timespinner"  format="H:mm" />-结束时分<input id="endUseTime" name="endUseTime" class="mini-timespinner"  format="H:mm" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>
	
	<script type="text/javascript">
	addBody();
	mini.parse();
	var form = new mini.Form("#form1");
	var logoUrl=mini.get("logoUrl");
	var logoUrlFileId=mini.get("logoUrlFileId");
	var icon_url_list=mini.get("icon_url_list");
	var icon_url_listField=mini.get("icon_url_listField");
	var cardColor=mini.get("cardColor");
	var type=mini.get("type");
	var begin_timestamp=mini.get("begin_timestamp");
	var end_timestamp=mini.get("end_timestamp");
	var codeType=mini.get("codeType");
	var quantity=mini.get("quantity");
	var notice=mini.get("notice");
	var begin_timestamp=mini.get("begin_timestamp");
	var end_timestamp=mini.get("end_timestamp");
	var fixed_begin_term=mini.get("fixed_begin_term");
	var fixed_term=mini.get("fixed_term");
	var title=mini.get("title");
	var brandName=mini.get("brandName");
	var service_phone=mini.get("service_phone");
	var center_url=mini.get("center_url");
	var center_title=mini.get("center_title");
	var center_sub_title=mini.get("center_sub_title");
	var custom_url_name=mini.get("custom_url_name");
	var custom_url=mini.get("custom_url");
	var custom_url_sub_title=mini.get("custom_url_sub_title");
	var can_share=mini.get("can_share");
	var can_give_friend=mini.get("can_give_friend");
	var background_pic_url=mini.get("background_pic_url");
	var background_pic_Field=mini.get("background_pic_Field");
	var supply_bonus=mini.get("supply_bonus");
	var supply_balance=mini.get("supply_balance");
	var bonus_url=mini.get("bonus_url");
	var week=mini.get("week");
	var beginUseTime=mini.get("beginUseTime");
	var endUseTime=mini.get("endUseTime");
	var business_service=mini.get("business_service");
	var abstractEL=mini.get("abstract");
	var can_use_with_other_discount=mini.get("can_use_with_other_discount");
	var toggleAdvancedFormSign=false;//非必填字段默认为收起来
	var togglePlusFormSign=false;//额外内容默认为收起来
	
	var form=new mini.Form("#form1");
	changeType();
	//initData();
	function openUpload(){ 
		_UploadFileDlg({
			from:'SELF',
			types:"Image",
			single:true,
			onlyOne:true,
			showMgr:false,
			callback:function(files){
				logoUrlFileId.setValue(files[0].fileId);
				$("#uploadLogo").attr("src","${ctxPath}/sys/core/file/download/"+files[0].fileId+".do ");
				$.ajax({
					url:"${ctxPath}/wx/core/wxMeterial/uploadImgToGetUrl.do",
					data:{pubId:"${pubId}",fileId:files[0].fileId},
					type:"post",
					success:function (result){
						logoUrl.setValue(result);
					}
				});
			}
		});
	}
	
	function openUploadAbstract(){ 
		_UploadFileDlg({
			from:'SELF',
			types:"Image",
			single:true,
			onlyOne:true,
			showMgr:false,
			callback:function(files){
				icon_url_listField.setValue(files[0].fileId);
				$("#abstractIcon").attr("src","${ctxPath}/sys/core/file/download/"+files[0].fileId+".do ");
				$.ajax({
					url:"${ctxPath}/wx/core/wxMeterial/uploadImgToGetUrl.do",
					data:{pubId:"${pubId}",fileId:files[0].fileId},
					type:"post",
					success:function (result){
						icon_url_list.setValue(result);
					}
				});
			}
		});
	}
	
	function openUploadBackground(){
		_UploadFileDlg({
			from:'SELF',
			types:"Image",
			single:true,
			onlyOne:true,
			showMgr:false,
			callback:function(files){
				background_pic_Field.setValue(files[0].fileId);
				$("#background_pic_id").attr("src","${ctxPath}/sys/core/file/download/"+files[0].fileId+".do ");
				$.ajax({
					url:"${ctxPath}/wx/core/wxMeterial/uploadImgToGetUrl.do",
					data:{pubId:"${pubId}",fileId:files[0].fileId},
					type:"post",
					success:function (result){
						background_pic_url.setValue(result);
					}
				});
			}
		});
	}
	
	function changeShowBonus(){
		var bonusValue=supply_bonus.getValue();
		if(bonusValue){
			//积分相关全部设置为必填
		}else{
			//积分相关全部设置为非必填
		}
	}
	
	function activeChange(){
		
	}
	
	function toggleForm(){
		var toggleContent=$(".advanced");
		if(toggleAdvancedFormSign){
			toggleContent.hide();
			toggleAdvancedFormSign=false;
		}else{
			toggleContent.show();
			toggleAdvancedFormSign=true;
		}
	}
	
	function togglePlusForm(){
		var toggleContent=$(".plus");
		if(togglePlusFormSign){
			toggleContent.hide();
			togglePlusFormSign=false;
		}else{
			toggleContent.show();
			togglePlusFormSign=true;
		}
	}
	

	function chooseColor(id){
		$(".SelectedColorBlob").removeClass("SelectedColorBlob");
		$("#"+id).addClass("SelectedColorBlob");
		cardColor.setValue(id);
	}
	
	function changeType(){
		var typeValue=type.getValue();
		if(typeValue=="DATE_TYPE_FIX_TIME_RANGE"){
			$(".DATE_TYPE_FIX_TIME_RANGE").show();
			$(".DATE_TYPE_FIX_TERM").hide();
		}else{
			$(".DATE_TYPE_FIX_TIME_RANGE").hide();
			$(".DATE_TYPE_FIX_TERM").show();
		}
	}
	
	function initData(){
		 if(${!empty wxTicket.id}){
			var specialConfig=mini.decode('${wxTicket.specialConfig}');
			var sku=mini.decode('${wxTicket.sku}');
			var dateInfo=mini.decode('${wxTicket.dateInfo}');
			var baseInfo=mini.decode('${wxTicket.baseInfo}');
			var advancedInfo=mini.decode('${wxTicket.advancedInfo}');
			
			if(JSON.stringify(baseInfo)!='{}'){
				toggleForm();
			}
			if(JSON.stringify(advancedInfo)!='{}'){
				togglePlusForm();
			}
			logoUrl.setValue("${wxTicket.logoUrl}");
			$("#uploadLogo").attr("src","${ctxPath}/sys/core/file/download/"+specialConfig.logoUrlFileId+".do ");
			chooseColor("${wxTicket.color}");
			codeType.setValue("${wxTicket.codeType}");
			quantity.setValue(sku.quantity);
			type.setValue(dateInfo.type);
			changeType();
			if(type.getValue()=="DATE_TYPE_FIX_TIME_RANGE"){
				begin_timestamp.setValue(new Date(dateInfo.begin_timestamp*1000));
				end_timestamp.setValue(new Date(dateInfo.end_timestamp*1000));
			}else{
				fixed_begin_term.setValue(new Date(dateInfo.fixed_begin_term*1000));
				fixed_term.setValue(new Date(dateInfo.fixed_term*1000));
			}
			
      		
      		service_phone.setValue(baseInfo.service_phone);
      		center_title.setValue(baseInfo.center_title);
      		center_url.setValue(baseInfo.center_url);
      		center_sub_title.setValue(baseInfo.center_sub_title);
      		custom_url_name.setValue(baseInfo.custom_url_name);
      		custom_url.setValue(baseInfo.custom_url);
      		custom_url_sub_title.setValue(baseInfo.custom_url_sub_title);
      		can_share.setValue(baseInfo.can_share);
      		can_give_friend.setValue(baseInfo.can_give_friend);
      		
      		try{
      			accept_category.setValue(advancedInfo.use_condition.accept_category);
          		reject_category.setValue(advancedInfo.use_condition.reject_category);
          		plus_least_cost.setValue(advancedInfo.use_condition.plus_least_cost);
          		can_use_with_other_discount.setValue(advancedInfo.use_condition.can_use_with_other_discount);
          		abstractEL.setValue(advancedInfo.abstract.abstract);
      		}catch(e){
      		}
      		business_service.setValue(advancedInfo.business_service);
      		var weekArray=[];
      		for(var date in advancedInfo.time_limit){
      			weekArray.push(advancedInfo.time_limit[date].type);
      		}
      		week.setValue(weekArray);
      		beginUseTime.setValue(specialConfig.beginUseTime);
      		endUseTime.setValue(specialConfig.endUseTime);
      		if(specialConfig.icon_url_listField!=undefined){
      			$("#abstractIcon").attr("src","${ctxPath}/sys/core/file/download/"+specialConfig.icon_url_listField+".do ");
      		}
		} 
		
	}
	
	
	function createTicket(){
		form.validate();
	    if (!form.isValid()) {
	    	mini.showTips({
	            content: "<b>提醒</b> <br/>请完善表单",
	            state: 'warning',
	            x: 'center',
	            y: 'center',
	            timeout: 3000
	        });
	        return;
	    }
	    if(!validateLogoAndColor()){
	    	mini.showTips({
	            content: "<b>提醒</b> <br/>不要忘了颜色和图片",
	            state: 'warning',
	            x: 'center',
	            y: 'center',
	            timeout: 3000
	        });
	        return;
	    }
	    if(!validateTimeLimit()){
	    	mini.showTips({
	            content: "<b>提醒</b> <br/>注意时间区间是否合理",
	            state: 'warning',
	            x: 'center',
	            y: 'center',
	            timeout: 3000
	        });
	        return;
	    }
	    var data=form.getData(true);
	    data.toggleAdvancedFormSign=toggleAdvancedFormSign;
	    data.togglePlusFormSign=togglePlusFormSign;
	   	var url="${ctxPath}/wx/core/wxTicket/createMemberCard.do"
	    $.ajax({
	    	url:url,
	    	data:data,
	    	type:"post",
	    	success:function(result){
	    		if(result.success){
	    			CloseWindow("ok");
	    		}else{
	    			mini.alert(result.errMsg);
	    		}
	    	}
	    });
	}
	
	function validateLogoAndColor(){
		var logoUrlValue=logoUrl.getValue();
		var cardColorValue=cardColor.getValue();
		var typeValue=type.getValue();
		var end_timestampValue=end_timestamp.getValue();
		var begin_timestampValue=begin_timestamp.getValue();
		if((typeValue=='DATE_TYPE_FIX_TIME_RANGE')&&(end_timestampValue<=begin_timestampValue)){//开始时间必须小于结束时间
			return false;
		}
		if((logoUrlValue==null||logoUrlValue=="")||(cardColorValue==null||cardColorValue=="")){//几个隐藏值必须要有
			return false;
		}else{
			return true;
		}
	}
	
	
	
	
	function validateTimeLimit(){
		if(togglePlusFormSign){
			if(beginUseTime.getValue()>=endUseTime.getValue()){
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	
	</script>
</body>
</html>