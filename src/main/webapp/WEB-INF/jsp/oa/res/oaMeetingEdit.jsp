<%-- 
    Document   : [OaMeeting]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>会议编辑</title>
<%@include file="/commons/edit.jsp"%>
<script type="text/javascript">
var impde=[{ id: 'GENERAL', text: '一般' }, { id: 'IMPORTANT', text: '重要'}, { id: 'URGENT', text: '紧急'}, { id: 'SECRECY', text: '保密'}];
</script>
</head>
<body>
		<div id="toolbar" class="mini-toolbar mini-toolbar-bottom">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;" id="toolbarBody"><a
					class="mini-button" iconCls="icon-save" plain="true"
					onclick="selfSaveData()">保存</a> <a class="mini-button"
					iconCls="icon-close" plain="true" onclick="onCancel">关闭</a></td>
			</tr>
		</table>
	</div>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="meetId" class="mini-hidden"
					value="${oaMeeting.meetId}" />
				<table class="table-detail column_2" cellspacing="1" cellpadding="0">
					<caption>会议申请基本信息</caption>

					<tr>
						<th>会议名称 <span class="star">*</span> 
						</th>
						<td colspan="3"><input name="name" value="${oaMeeting.name}"
							class="mini-textbox" vtype="maxLength:255" style="width: 90%"
							required="true" emptyText="请输入会议名称" /></td>
					</tr>
					<tr>
						<th>会议概要 </th>
						<td colspan="3"><ui:UEditor height="150" isMini="true"
								name="descp" id="descp">${oaMeeting.descp}</ui:UEditor></td>
					</tr>

					<tr>
						<th>会议地点 <span class="star">*</span> 
						</th>
						<td colspan="3">
						<input id="roomId" name="roomId" class="mini-combobox" style="width: 150px;" value="${roomId}" text="${roomName}" textField="name" valueField="roomId" emptyText="请选择..." 
						url="${ctxPath}/oa/res/oaMeetRoom/getAll.do"
						 required="true" allowInput="false" nullItemText="请选择..." onvaluechanged="genvaluechanged" />
		   
						<input id="location" name="location" value="${oaMeeting.location}" class="mini-textbox" vtype="maxLength:255" style="width: 50%" required="true" emptyText="请输入会议地点" />	
							</td>
					</tr>

					<tr>
						<th>开始时间 <span class="star">*</span> 
						</th>
						<td><input id="start" name="start" value="${oaMeeting.start}" readonly="readonly" showTime="true" allowInput="false" class="mini-datepicker" value="date"  required="true" format="yyyy-MM-dd HH:mm:ss"  timeFormat="HH:mm:ss" emptyText="请输入开始时间" /></td>
						<th>结束时间 <span class="end">*</span> 
						</th>
						<td><input id="end" name="end" value="${oaMeeting.end}" readonly="readonly" showTime="true" allowInput="false" class="mini-datepicker" value="date"  required="true" format="yyyy-MM-dd HH:mm:ss"  timeFormat="HH:mm:ss" emptyText="请输入结束时间" /></td>
					</tr>
					
					<tr>
						<th>会议参与人员</th>
						<td colspan="3"><textarea class="mini-textboxlist"
								emptyText="请选择参与人" allowInput="false" validateOnChanged="false" value="${userId}" text="${fullName}"
								style="margin-top: 5px;" id="receive" name="receive"
								width="250px" height="50px"></textarea> <a class="mini-button"
							iconCls="icon-user" onclick="addPerson()">选择参与人</a></td>
					</tr>
					<tr>
						<th>会议预算 </th>
						<td><input name="budget" value="${oaMeeting.budget}"
							class="mini-spinner" value="123456.123" minValue="0"
							maxValue="1000000" format="¥#,0.00" vtype="float"
							style="width: 90%" />元</td>
						<th>主持人 </th>
						<td><input name="hostUid" value="${oaMeeting.hostUid}"
							text="${oaMeeting.hostName}" class="mini-buttonedit"
							onclick="selHostId" vtype="maxLength:64" style="width: 90%" /></td>
							</tr>

					<tr>
						<th>会议记录员 </th>
						<td><input name="recordUid" value="${oaMeeting.recordUid}" 
							text="${oaMeeting.recordName}" class="mini-buttonedit"
							onclick="selRecordId" vtype="maxLength:64" style="width: 90%" />
						</td>
						<th>会议状态 <span class="star">*</span> 
						</th>
						<td>
							<div id="status" name="status" value="${oaMeeting.status}"
								class="mini-radiobuttonlist" repeatItems="2"
								repeatLayout="table" repeatDirection="vertical" required="true"
								emptyText="请输入会议状态"
								data="[{id:'ENABLED',text:'可用'},{id:'DISABLED',text:'禁用'}]">
							</div>
						</td>
					</tr>
					<tr>
					<th>重要程度<span class="star">*</span></th>
					<td colspan="3">
					<input name="impDegree" id="impDegree" class="mini-combobox" style="width: 150px; color: #000000;"  data="impde"
					 value="${oaMeeting.impDegree}" required="true" allowInput="false" />
						
						</td>
					</tr>
					<tr>
					<th>附件名称列表 </th>				
						<td colspan="3">
						<input name="fileIds" class="upload-panel" plugins="upload-panel" 
						allowupload="true" label="附件" fname="fileNames" allowlink="true" zipdown="true" 
							fileIds="${oaMeeting.fileIds}" fileNames="${fileNames}" /></td> 
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/res/oaMeeting"
		entityName="com.redxun.oa.res.entity.OaMeeting" />
	<script type="text/javascript">
	mini.parse();
	$(function() {
		$('.upload-panel').each(function() {
			$(this).uploadPanel();
		});
	});
	var startTime=mini.get("start");
	var endTime=mini.get("end");
	var roomIDS=mini.get('roomId');
	//console.log(roomIDS);
	function selHostId(e){
		var editor=e.sender;
		_UserDlg(true,function(user){
			editor.setValue(user.userId);
			editor.setText(user.fullname);
		});
	}
	function selRecordId(e){
		var editor=e.sender;
		_UserDlg(true,function(user){
			editor.setValue(user.userId);
			editor.setText(user.fullname);
		});
	}

	
	//增加参与人按钮
	function addPerson() {
		var infUser = mini.get('receive');

		_UserDlg(false, function(users) {//打开参与人选择页面,返回时获得选择的user的Id和name
			var uIds = [];
			var uNames = [];
			//将返回的选择分别存起来并显示
			for (var i = 0; i < users.length; i++) {
				var flag = true;
				var oldValues = infUser.getValue().split(',');
				var oldText = infUser.getText().split(',');
				for(var j=0;j<oldValues.length;j++){
					if(oldValues[j]==users[i].userId&&oldText[j]==users[i].fullname){
						flag = false;
						break;
					}
				}
				if(flag==true){
				uIds.push(users[i].userId);
				uNames.push(users[i].fullname);
				}
			}
			if (infUser.getValue() != '') {
				uIds.unshift(infUser.getValue().split(','));
			}
			if (infUser.getText() != '') {
				uNames.unshift(infUser.getText().split(','));
			}
			infUser.setValue(uIds.join(','));
			infUser.setText(uNames.join(','));
		});
	}
	
	
	
	//重写了saveData方法issave
	function selfSaveData(){
		var form = new mini.Form("form1");
		var infUser = mini.get('receive');
		
		form.validate();
	    if (!form.isValid()) {//验证表格
	        return; }
	  var formData=$("#form1").serializeArray();
	    var userId = infUser.getValue();
	    var fullName = infUser.getText();
	   if(userId!=''){
		  
	         formData.push({
	        	name:'userId',
	        	value:userId
	        });
	         formData.push({
		        	name:'fullName',
		        	value:fullName
		        });
	        
	    }
       
	    _SubmitJson({
	    	url:"${ctxPath}/oa/res/oaMeeting/save.do",
	    	method:'POST',
	    	data:formData,
	    	success:function(result){
			
	    		//如果存在自定义的函数，则回调alert(mini.encode(formData));
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
	
	//重新获取到地址
	function genvaluechanged(e){
		var roomId=roomIDS.getValue();
		var rs=roomIDS.getSelected();
		mini.get("location").setValue(rs.location);
		 _OpenWindow({
				url : "${ctxPath}" + "/oa/res/oaMeet/List.do?roomId="+roomId,
				title : "已申请会议室申请列表",
				iconCls:'icon-transmit',
				height : 500,
				width : 600,
				ondestroy:function(action){
					if (action == "ok") {
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.getStart();
						mini.get("start").setValue(data[0]);
						mini.get("end").setValue(data[1]);
					}
				}
			}); 
	}

	</script>
</body>
</html>