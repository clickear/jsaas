<%-- 
    Document   : [CalGrant]编辑页
    Created on : 2017-1-7, 10:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>日历分配编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${calGrant.grantId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="grantId" class="mini-hidden" value="${calGrant.grantId}" />
				<input  name="setting" class="mini-hidden" value="${settingId}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<tr style="display: none;">
						<th>设定ID</th>
						<td><input name="settingId" value="${calGrant.settingId}" class="mini-hidden" vtype="maxLength:64" style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>分配类型 </th>
						<td><input id="grantType" name="grantType" value="${calGrant.grantType}" class="mini-radiobuttonlist" required="true" textField="text" valueField="id" 
  data="[{'id':'GROUP','text':'用户组'},{'id':'USER','text':'用户'}]" onvaluechanged="chooseWho()"  style="width: 90%" />
						</td>
					</tr>
					<tr class="belongWhoTr" style="display: none;">
						<th>被分配者</th>
						<td><input name="belongWho" id="belongWho" required="true"  value="${calGrant.belongWho}" text="${belongWhoName}" class="mini-textboxlist" allowInput="false" style="width: 90%" />
						<a class="mini-button" iconCls="icon-grant" onclick="selectEL('belongWho')">选择</a>
						<a class="mini-button" iconCls="icon-clear" onclick="clearEL('belongWho')">清空</a>
						</td>
					</tr>
					
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/calendar/calGrant" entityName="com.redxun.oa.calendar.entity.CalGrant" />
	<script type="text/javascript">
	$(function(){
		if("${calGrant.grantType}"=="GROUP"||"${calGrant.grantType}"=="USER"){
			$(".belongWhoTr").show();
		}
	});
	var grantType=mini.get("grantType");
	function chooseWho(){
		clearEL('belongWho');
		if(grantType.getValue()=="COMMON"){
			$(".belongWhoTr").hide();
		}else if(grantType.getValue()=="GROUP"||grantType.getValue()=="USER"){
			$(".belongWhoTr").show();
		}
	}
	
	
	//双重选择框
	function selectEL(element){
		if(grantType.getValue()=="GROUP"){
			selGroups(element);
		}else if(grantType.getValue()=="USER"){
			selUsers(element)
		}else{
			alert("分配类型不正确");
		}
	}
	
	
	function clearEL(element){
			var elementSelect=mini.get(element);
			elementSelect.setText('');
			elementSelect.setValue('');
		}
		
	function selGroups(element){
		var elementSelect=mini.get(element);
		_GroupDlg(false,function(groups){
			var uIds=[];
			var uNames=[];
			for(var i=0;i<groups.length;i++){
				uIds.push(groups[i].groupId);
				uNames.push(groups[i].name);
			}
			if(elementSelect.getValue()!=''){
				uIds.unshift(elementSelect.getValue().split(','));
			}
			if(elementSelect.getText()!=''){
				uNames.unshift(elementSelect.getText().split(','));	
			}
			elementSelect.setValue(uIds.join(','));
			elementSelect.setText(uNames.join(','));
		});
	}
	
	function selUsers(element){
		var elementSelect=mini.get(element);
		_UserDlg(false,function(users){	
			var uIds=[];
			var uNames=[];
			for(var i=0;i<users.length;i++){
				uIds.push(users[i].userId);
				uNames.push(users[i].fullname);
			}
			if(elementSelect.getValue()!=''){
				uIds.unshift(elementSelect.getValue().split(','));
			}
			if(elementSelect.getText()!=''){
				uNames.unshift(elementSelect.getText().split(','));	
			}
			elementSelect.setValue(uIds.join(','));
			elementSelect.setText(uNames.join(','));
		});
	}
		
		 function selfSaveData() {
			 var form = new mini.Form("form1");
		        form.validate();
		        if (!form.isValid()) {
		            return;
		        }
		        var formData=$("#form1").serializeArray();
		       
		       //若定义了handleFormData函数，需要先调用 
		       if(isExitsFunction('handleFormData')){
		        	var result=handleFormData(formData);
		        	if(!result.isValid) return;
		        	formData=result.formData;
		        }
		        
		        var config={
		        	url:"${ctxPath}/oa/calendar/calGrant/save.do",
		        	method:'POST',
		        	data:formData,
		        	success:function(result){
		        		//如果存在自定义的函数，则回调
		        		if(isExitsFunction('successCallback')){
		        			successCallback.call(this,result);
		        			return;	
		        		}
		        		
		        		//CloseWindow('ok');
		        	}
		        }
		        
		        if(result && result["postJson"]){
		        	config.postJson=true;
		        }
		        
		        _SubmitJson(config);
		     }
	
	</script>
</body>
</html>