 <%-- 
    Document   : 内部邮件编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>内部邮件编辑</title>
<%@include file="/commons/edit.jsp"%>
<script type="text/javascript">
var _enable_openOffice="<%=com.redxun.sys.core.util.SysPropertiesUtil.getGlobalProperty("openoffice")%>";
</script>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/ueditor-ext.js"></script>
<link href="${ctxPath}/styles/customform.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.js"	type="text/javascript"></script>
<script src="${ctxPath}/scripts/common/util.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/customer/mini-custom.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/form/FormCalc.js" type="text/javascript"></script>
<style type="text/css">
	.shadowBox90{
		margin: 0 auto 40px;
	}
</style>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom topBtn"><!--toolbar工具栏  -->
		<a class="mini-button" iconCls="icon-send" plain="true" onclick="sendmail">发送</a>
		<a class="mini-button" iconCls="icon-close" plain="true" onclick="closeWindow">关闭</a>
	</div>
	<div id="p1" class="form-outer shadowBox90">
		<!-- 表单数据 -->
		<form id="form1" method="post">
			<input 
				type="hidden" 
				name="crsf_token" 
				class="mini-hidden"  
				value="${sessionScope.crsf_token}"
			/>
			<div class="form-inner">
				<input 
					id="pkId" 
					name="mailId" 
					class="mini-hidden" 
					value="${innerMail.mailId}" 
				/> 
				<input 
					name="userId" 
					value="${innerMail.userId}" 
					class="mini-hidden" 
					vtype="maxLength:64" 
					style="width: 90%" 
					required="true" 
					emptyText="请输入用户ID" 
				/> 
				<input 
					name="senderId" 
					value="${innerMail.senderId}" 
					class="mini-hidden" 
					vtype="maxLength:64" 
					style="width: 90%" 
					required="true" 
					emptyText="请输入" 
				/>

				<table class="table-detail column_1_m" cellspacing="0" cellpadding="0">
					<caption>邮件信息</caption>

					<tr>
						<th width="120">
							<span class="starBox">
								收件人列表 <span class="star">*</span> 
							</span>
						</th>
						<td>
							<input 
								id="recIds" 
								class="mini-textboxlist" 
								name="recIds" 
								required="true" 
								style="width: 250px;" 
								value="${innerMail.recIds }" 
								text="${innerMail.recNames }" 
								valueField="" 
								textField="" 
								allowInput="false" 
							/> 
							<a class="mini-button" iconCls="icon-add" plain="true" onclick="addRecs"></a>
						</td>
					</tr>

					<tr>
						<th>抄送人列表 </th>
						<td>
							<input 
								id="ccIds" 
								class="mini-textboxlist" 
								name="ccIds" 
								style="width: 250px;" 
								value="" 
								text="" 
								valueField="" 
								textField="" 
								allowInput="false" 
							/> 
							<a class="mini-button" iconCls="icon-add" plain="true" onclick="addCcs"></a>
						</td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								邮件发送人<span class="star">*</span> 
							</span>
						</th>
						<td>
							<input 
								id="sender" 
								class="mini-textboxlist" 
								name="sender" 
								required="true" 
								style="width: 250px;" 
								value="${innerMail.senderId}" 
								text="${innerMail.sender}" 
								valueField="" 
								textField="" 
								allowInput="false" 
								enabled="false" 
							/>
						</td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								邮件标题 <span class="star">*</span> 
							</span>
						</th>
						<td>
							<input 
								name="subject" 
								class="mini-textbox" 
								vtype="maxLength:256" 
								value="${innerMail.subject}" 
								style="width: 70%" 
								required="true" 
								emptyText="请输入邮件标题" 
							/>
						</td>
					</tr>


					<tr>
						<th>
							<span class="starBox">
								重要程度 <span class="star">*</span> 
							</span>
						</th>
						<td><c:choose>
								<c:when test="${innerMail.urge!=null}">
									<div 
										id="urge" 
										class="mini-radiobuttonlist" 
										repeatItems="1" 
										repeatDirection="vertical" 
										value="${innerMail.urge}" 
										data="[{id:'1',text:'一般'},{id:'2',text:'重要'},{id:'3',text:'非常重要'}]" 
										required="true"
									></div>
								</c:when>
								<c:otherwise>
									<div 
										id="urge" 
										class="mini-radiobuttonlist" 
										repeatItems="1" 
										repeatDirection="vertical" 
										value="1" 
										data="[{id:'1',text:'一般'},{id:'2',text:'重要'},{id:'3',text:'非常重要'}]" 
										required="true"
									></div>
								</c:otherwise>
							</c:choose></td>
					</tr>
					
					<tr>
						<th>附　　件 </th>
						<td colspan="3" class="customform">
							<input 
								name="fileIds" 
								class="upload-panel"  
								style="width: 80%" 
								allowupload="true" 
								label="附件" 
								fname="fileNames" 
								allowlink="true" 
								zipdown="true" 
								mwidth="80" 
								wunit="%" 
								mheight="0" 
								hunit="px" 
								sizelimit="1024000" 
							/>
						</td>
					</tr>	

					<tr>
						<td colspan="4">
							<div id="content" name="content" class="mini-ueditor"   style="width:auto;"  height="400" width="100%" ></div>
						</td>
					</tr>

				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	mini.parse();
	var form=mini.get("form1");

	
	/*添加收件人*/
	function addRecs(){
		var rec=mini.get("recIds");
		var recIds=[];
		var recNames=[];
		_UserDlg(false, function(recs){     //添加收件人用户
			for(var i=0;i<recs.length;i++){
				recIds.push(recs[i].userId);
				recNames.push(recs[i].fullname);
			}
			rec.setValue(recIds.join(','));   //用逗号分隔收件人Id列表
			rec.setText(recNames.join(','));  //用逗号分隔收件人姓名列表
		});
	}
	
	/*添加抄送人*/
	function addCcs(){
		var cc=mini.get("ccIds");
		var ccIds=[];
		var ccNames=[];
		_UserDlg(false, function(ccs){  //添加抄送人用户
			for(var i=0;i<ccs.length;i++){
				ccIds.push(ccs[i].userId);
				ccNames.push(ccs[i].fullname);
			}
			cc.setValue(ccIds.join(','));  //用逗号分隔抄送人Id列表
			cc.setText(ccNames.join(',')); //用逗号分隔抄送人姓名列表
		});
	}
	
	/*发送内部邮件*/
	function sendmail() {
		var content = _GetFormJson("form1");
		
		var mailContent =  mini.getByName("content").getValue();
		content.content = mailContent;
		
		var data=mini.encode(content);
		debugger;
		var urge=mini.get("urge");
		var urgeData=urge.getValue();  //获取单选框的值
		var fileIds = mini.getByName("fileIds").getValue();
		
		
		
        form.validate();    //验证表单数据
        if (!form.isValid()) {
            return;
        }
        
        /*发送内部邮件*/
 		_SubmitJson({
			url : __rootPath + "/oa/mail/innerMail/sendMail.do",
			data : {
				data : data,
				urge:urgeData,
				fileIds:fileIds,
				operation:"<c:out value='${param.operation}' />",
				oldMailId:"<c:out value='${param.pkId}' />"
			},
			method : 'POST',
			success : function() {
				CloseWindow('ok');
			}
		}); 
	}
	
	/*关闭窗口*/
	function closeWindow(){
		CloseWindow('ok',handle);  //调用handle函数
	}
	
	/*关闭窗口处理函数*/
	function handle(){
		 if (confirm("你的邮件内容已经改变，是否将邮件保存到草稿箱？")) {  //保存到草稿箱
				var content = _GetFormJson("form1");
				var data=mini.encode(content);
				var urge=mini.get("urge");
				var urgeData=urge.getValue();
				/*将邮件保存到草稿箱*/
		 		_SubmitJson({
					url : __rootPath + "/oa/mail/innerMail/saveInnerMailToDraftFolder.do",
					data : {
						data : data,
						urge:urgeData
					},
					method : 'POST',
					success : function() {
						CloseWindow('ok');
					}
				}); 
		 }
	}
	</script>
	<rx:formScript formId="form1" baseUrl="oa/mail/innerMail" />
</body>
</html>