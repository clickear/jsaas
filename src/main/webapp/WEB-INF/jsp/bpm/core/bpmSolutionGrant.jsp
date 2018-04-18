<%-- 
    Document   : 业务流程解决方案明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务流程解决方案授权</title>
<%@include file="/commons/get.jsp"%>
<style type="text/css">.table-detail > tbody > tr > th {width:20%;}</style>
</head>
<body>
	<div class="mini-toolbar topBtn">
		<a class="mini-button" iconCls="icon-save" onclick="onSave">保存</a>  	
	</div>
	<div class="rx-grid-fit shadowBox90" style="padding-top: 8px;">
		<form id="grantForm">	
			<table class="table-detail column_1" style="width:100%;">
				<tr>
					<th width="120">[阅读权限]范围</th>
					<td colspan="2">
						<div 
							id="readGrantType" 
							name="readGrantType" 
							class="mini-radiobuttonlist" 
							onvaluechanged="changeReadGrantType()" 
							style="float:left; display:inline"
						    textField="text" 
						    valueField="id" 
						    value="${grantReadAllOrNot}"  
						    data="[{id:'0',text:'公共'},{id:'1',text:'指定'}]"
					    >
						</div>
						<!-- <div style="float:left; display:inline" id="alowReadStartor" name="alowReadStartor" class="mini-checkbox" readOnly="false" text="授权给发起人" checked='true'></div>    
						<div style="float:left; display:inline" id="alowReadAttend" name="alowReadAttend" class="mini-checkbox" readOnly="false" text="授权给参与人" checked='true'></div> -->
					</td>
				</tr>
				<tr class="rtable">
					<th width="120">用户组</th>
					<td colspan="2">
						<input id="rgroupIds" class="mini-textboxlist" allowInput="false" name="rgroupIds"  style="width:70%" value="${readGroupIds}" text="${readGroupNames}"/>
						<a class="mini-button" iconCls="icon-clear" onclick="clearGroups('rgroupIds')">清空</a>
						<a class="mini-button" iconCls="icon-group" onclick="selGroups('rgroupIds')">用户组</a>						
					</td>
				</tr>
				<tr class="rtable">
					<th width="120">用户</th>
					<td colspan="2">
						<input id="ruserIds" class="mini-textboxlist" allowInput="false" name="ruserIds"  style="width:70%" value="${readUserIds}" text="${readUserNames}"/>
						<a class="mini-button" iconCls="icon-clear" onclick="clearUsers('ruserIds')">清空</a>
						<a class="mini-button" iconCls="icon-user" onclick="selUsers('ruserIds')">用户</a>						
					</td>
				</tr>
				
				
				
				<tr class="atable">
					<th width="120" rowspan="2" style="font-size: 10px;">[附件打印]
					<div style="display:inline" id="alowAttachPrintStartor" name="alowAttachPrintStartor" class="mini-checkbox" readOnly="false" text="发起人允许授权" ></div>    
					<!-- <div style="float:right; display:inline" id="alowAttachPrintAttend" name="alowAttachPrintAttend" class="mini-checkbox" readOnly="false" text="授权给参与人" checked='true'></div> -->
					</th>
					<td>
						<input id="printAgroupIds" class="mini-textboxlist" allowInput="false" name="printAgroupIds"  style="width:70%" value="${APrintGroupIds}" text="${APrintGroupNames}"/>
						<a class="mini-button" iconCls="icon-clear" onclick="clearGroups('printAgroupIds')">清空</a>
						<a class="mini-button" iconCls="icon-group" onclick="selGroups('printAgroupIds')">用户组</a>						
					</td>
				</tr>
				<tr class="atable">
					<td>
						<input id="printAuserIds" class="mini-textboxlist" allowInput="false" name="printAuserIds"  style="width:70%" value="${APrintUserIds}" text="${APrintUserNames}"/>
						<a class="mini-button" iconCls="icon-clear" onclick="clearUsers('printAuserIds')">清空</a>
						<a class="mini-button" iconCls="icon-user" onclick="selUsers('printAuserIds')">用户</a>						
					</td>
				</tr>
				<tr class="atable">
					<th width="120" rowspan="2" style="font-size: 10px;">[附件下载]
					<div style="display:inline" id="alowAttachDownStartor" name="alowAttachDownStartor" class="mini-checkbox" readOnly="false" text="发起人允许授权" ></div>    
					<!-- <div style="float:right; display:inline" id="alowAttachDownAttend" name="alowAttachDownAttend" class="mini-checkbox" readOnly="false" text="授权给参与人" checked='true'></div> -->
					</th>
					<td>
						<input id="downAgroupIds" class="mini-textboxlist" allowInput="false" name="downAgroupIds"  style="width:70%" value="${ADownGroupIds}" text="${ADownGroupNames}"/>
						<a class="mini-button" iconCls="icon-clear" onclick="clearGroups('downAgroupIds')">清空</a>
						<a class="mini-button" iconCls="icon-group" onclick="selGroups('downAgroupIds')">用户组</a>						
					</td>
				</tr>
					<tr class="atable">
					<td>
						<input id="downAuserIds" class="mini-textboxlist" allowInput="false" name="downAuserIds"  style="width:70%" value="${ADownUserIds}" text="${ADownUserNames}"/>
						<a class="mini-button" iconCls="icon-clear" onclick="clearUsers('downAuserIds')">清空</a>
						<a class="mini-button" iconCls="icon-user" onclick="selUsers('downAuserIds')">用户</a>						
					</td>
				</tr>
				
			</table>
			
		
			
			<%-- <table class="table-detail column_1" style="width:100%;">
				<tr>
					<th width="120">[附件权限]范围</th>
					<td colspan="2">
						<div id="attachGrantType" name="attachGrantType" class="mini-radiobuttonlist" 
							 onvaluechanged="changeAttachGrantType()" style="float:left; display:inline"
						    textField="text" valueField="id" value="${grantFileAllOrNot}"  
						    data="[{id:'0',text:'公共'},{id:'1',text:'指定'}]" >
						</div>
						<div style="float:left; display:inline" id="alowAttachStartor" name="alowAttachStartor" class="mini-checkbox" readOnly="false" text="授权给发起人" checked='true'></div>    
						<div style="float:left; display:inline" id="alowAttachAttend" name="alowAttachAttend" class="mini-checkbox" readOnly="false" text="授权给参与人" checked='true'></div>
					</td>
				</tr>
				

				<tr class="atable">
					<th width="120" rowspan="2" style="background-color: #F4F4F4;font-size: 18px;">[编辑]
					<div style="float:right; display:inline" id="alowAttachEditStartor" name="alowAttachEditStartor" class="mini-checkbox" readOnly="false" text="授权给发起人" checked='true' value="${alowAttachEditStartor}"></div>    
					<div style="float:right; display:inline" id="alowAttachEditAttend" name="alowAttachEditAttend" class="mini-checkbox" readOnly="false" text="授权给参与人" checked='true' value="${alowAttachEditStartor}"></div>
					</th>
					<td>
						<input id="editAgroupIds" class="mini-textboxlist" name="editAgroupIds"  style="width:70%" value="${AEditGroupIds}" text="${AEditGroupNames}"/>
						<a class="mini-button" iconCls="icon-grant" onclick="selGroups('editAgroupIds')">用户组</a>
						<a class="mini-button" iconCls="icon-clear" onclick="clearGroups('editAgroupIds')">清空</a>
					</td>
				</tr>
				<tr class="atable">
					<td>
						<input id="editAuserIds" class="mini-textboxlist" name="editAuserIds"  style="width:70%" value="${AEditUserIds}" text="${AEditUserNames}"/>
						<a class="mini-button" iconCls="icon-grant" onclick="selUsers('editAuserIds')">用户</a>
						<a class="mini-button" iconCls="icon-clear" onclick="clearUsers('editAuserIds')">清空</a>
					</td>
				</tr>
				
				<tr class="atable">
					<th width="120" rowspan="2" style="font-size: 10px;">[附件打印]
					<div style="display:inline" id="alowAttachPrintStartor" name="alowAttachPrintStartor" class="mini-checkbox" readOnly="false" text="发起人允许授权" ></div>    
					<!-- <div style="float:right; display:inline" id="alowAttachPrintAttend" name="alowAttachPrintAttend" class="mini-checkbox" readOnly="false" text="授权给参与人" checked='true'></div> -->
					</th>
					<td>
						<input id="printAgroupIds" class="mini-textboxlist" allowInput="false" name="printAgroupIds"  style="width:70%" value="${APrintGroupIds}" text="${APrintGroupNames}"/>
						<a class="mini-button" iconCls="icon-clear" onclick="clearGroups('printAgroupIds')">清空</a>
						<a class="mini-button" iconCls="icon-group" onclick="selGroups('printAgroupIds')">用户组</a>						
					</td>
				</tr>
				<tr class="atable">
					<td>
						<input id="printAuserIds" class="mini-textboxlist" allowInput="false" name="printAuserIds"  style="width:70%" value="${APrintUserIds}" text="${APrintUserNames}"/>
						<a class="mini-button" iconCls="icon-clear" onclick="clearUsers('printAuserIds')">清空</a>
						<a class="mini-button" iconCls="icon-user" onclick="selUsers('printAuserIds')">用户</a>						
					</td>
				</tr>
				<tr class="atable">
					<th width="120" rowspan="2" style="font-size: 10px;">[附件下载]
					<div style="display:inline" id="alowAttachDownStartor" name="alowAttachDownStartor" class="mini-checkbox" readOnly="false" text="发起人允许授权" ></div>    
					<!-- <div style="float:right; display:inline" id="alowAttachDownAttend" name="alowAttachDownAttend" class="mini-checkbox" readOnly="false" text="授权给参与人" checked='true'></div> -->
					</th>
					<td>
						<input id="downAgroupIds" class="mini-textboxlist" allowInput="false" name="downAgroupIds"  style="width:70%" value="${ADownGroupIds}" text="${ADownGroupNames}"/>
						<a class="mini-button" iconCls="icon-clear" onclick="clearGroups('downAgroupIds')">清空</a>
						<a class="mini-button" iconCls="icon-group" onclick="selGroups('downAgroupIds')">用户组</a>						
					</td>
				</tr>
					<tr class="atable">
					<td>
						<input id="downAuserIds" class="mini-textboxlist" allowInput="false" name="downAuserIds"  style="width:70%" value="${ADownUserIds}" text="${ADownUserNames}"/>
						<a class="mini-button" iconCls="icon-clear" onclick="clearUsers('downAuserIds')">清空</a>
						<a class="mini-button" iconCls="icon-user" onclick="selUsers('downAuserIds')">用户</a>						
					</td>
				</tr>
				
				<tr class="atable">
					<th width="120" rowspan="2" style="background-color: #F4F4F4">[删除]</th>
					<td>
						<input id="delAgroupIds" class="mini-textboxlist" name="delAgroupIds"  style="width:70%" value="${groupIds}" text="${groupNames}"/>
						<a class="mini-button" iconCls="icon-grant" onclick="selGroups('delAgroupIds')">用户组</a>
						<a class="mini-button" iconCls="icon-clear" onclick="clearGroups('delAgroupIds')">清空</a>
					</td>
				</tr>
				<tr class="atable">
					<td>
						<input id="delAuserIds" class="mini-textboxlist" name="delAuserIds"  style="width:70%" value="${userIds}" text="${userNames}"/>
						<a class="mini-button" iconCls="icon-grant" onclick="selUsers('delAuserIds')">用户</a>
						<a class="mini-button" iconCls="icon-clear" onclick="clearUsers('delAuserIds')">清空</a>
					</td>
				</tr>
			</table> --%>
		
			
		</form>
	</div>
	<script type="text/javascript">
		addBody();
		mini.parse();
		var gform=new mini.Form('grantForm');
		var groupIds=mini.get('groupIds');
		var userIds=mini.get('userIds'); 
		//combobox针对各个权限范围的人员框   AttachGrantType
		var downAuserIds=mini.get('downAuserIds');
		var downAgroupIds=mini.get('downAgroupIds');
		var printAuserIds=mini.get('printAuserIds');
		var printAgroupIds=mini.get('printAgroupIds');

		var rgroupIds=mini.get('rgroupIds');
		var ruserIds=mini.get('ruserIds');
		
		
		var solId='${bpmSolution.solId}';
		
		/*隐藏或显示发起权限人员输入*/
		function changeGrantType(){
			/* var val=mini.get('grantType').getValue();
			if(val=='0'){
				$('.gtable').css("display","none");//hide();
			}else{
				$('.gtable').css("display","");//show();
			} */
		}
		
		/*隐藏或显示阅读权限人员输入*/
		function changeReadGrantType(){
			var val=mini.get('readGrantType').getValue();
			if(val=='0'){
				$('.rtable').css("display","none");//hide();
			}else{
				$('.rtable').css("display","");//show();
			}
		}
		
		
		/*一开始加载就通过combox的值来确定是否显示选择框*/
		$(function(){
			changeGrantType();
			changeReadGrantType();
			
			if("${alowAttachPrintStartor}"!="true"){
				mini.get("alowAttachPrintStartor").setChecked(false);
			}else{
				mini.get("alowAttachPrintStartor").setChecked(true);
			}
			
			if("${alowAttachDownStartor}"!="true"){
				mini.get("alowAttachDownStartor").setChecked(false);
			}else{
				mini.get("alowAttachDownStartor").setChecked(true);
			}
			
			
		});
		
		//保存授权
		function onSave(){
			//var grantType=mini.get('grantType').getValue();
			//var attachGrantType=mini.get('attachGrantType').getValue();
			var readGrantType=mini.get('readGrantType').getValue();
			/*如果选择了[指定]却未选择任何人员或组的话,拒绝保存并提醒*/
			/* if(grantType=='1' && groupIds.getValue()=='' && userIds.getValue()==''){
				mini.showTips({
		            content: "<b>请至少选择[发起权限]的</b><br/><b>一个用户组或用户</b>",
		            state: 'danger',
		            x: 'center',
		            y: 'middle',
		            timeout: 3000
		        });
				return;
			} */
			/* if(attachGrantType=='1' && editAgroupIds.getValue()=='' && editAuserIds.getValue()==''&& printAgroupIds.getValue()=='' && printAuserIds.getValue()==''&& downAgroupIds.getValue()=='' && downAuserIds.getValue()==''){
				mini.showTips({
		            content: "<b>请至少选择[附件权限]的</b><br/><b>一个用户组或用户</b>",
		            state: 'danger',
		            x: 'center',
		            y: 'middle',
		            timeout: 3000
		        });
				return;
			} */
			/* if(readGrantType=='1' && rgroupIds.getValue()=='' && ruserIds.getValue()==''){
				mini.showTips({
		            content: "<b>请至少选择[阅读权限]的</b><br/><b>一个用户组或用户</b>",
		            state: 'danger',
		            x: 'center',
		            y: 'middle',
		            timeout: 3000
		        });
				return;
			} */
			
			var data=gform.getData();
			data.solId=solId;
			_SubmitJson({
				url:__rootPath+'/bpm/core/bpmSolution/saveGrant.do',
				data:data,
				success:function(){
					
				}
			});
		}
		function clearGroups(element){
			var elementSelect=mini.get(element);
			elementSelect.setText('');
			elementSelect.setValue('');
		}
		function clearUsers(element){
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
	</script>
</body>
</html>