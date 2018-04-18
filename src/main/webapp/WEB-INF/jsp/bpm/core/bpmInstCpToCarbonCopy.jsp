<%-- 
    Document   : 流程抄送
    Created on : 2016-10-26, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>抄送给某人</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div id="p2" class="form-outer">
		<form id="form2" method="post">
			<div class="form-inner">
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<div style="display: none;"><input name="nodeName2" class="mini-textbox" vtype="maxLength:255" value="${nodeName2}"/></div>
					<tr style="display: none;">
						<%-- <th>nodeName <span class="star">*</span> ：</th>
						<td><input name="nodeName" class="mini-textbox" vtype="maxLength:255" value="${nodeName}"  style="width: 90%" required="true" emptyText="请输入标题"/></td>
						 --%><th>nodeId <span class="star">*</span> ：</th>
						<td><input name="nodeId" class="mini-textbox" vtype="maxLength:255"  value="${nodeId}" style="width: 90%" required="true" emptyText="请输入标识Key"/>
						<th>bpmInstId <span class="star">*</span> ：</th>
						<td><input name="bpmInstId" class="mini-textbox" vtype="maxLength:255"  value="${bpmInstId}" style="width: 90%" required="true" emptyText="请输入标识Key"/></td>
					</tr>
					<tr> 
						<th>抄送标题 <span class="star">*</span> ：</th>
						<td><input name="subject" class="mini-textbox"  vtype="maxLength:255"  style="width: 500px" required="true" emptyText="请输入标题"/></td>
					</tr>
					<tr>
						<th>人员 <span class="star">*</span> ：</th>
						<td><textarea class="mini-textboxlist" allowInput="false"  style="margin-top: 5px;"  id="ccUser" name="ccUser" width="500px" height="100px" onclick="addPerson()"></textarea>
						<a class="mini-button" style="width: 30px; margin-top: 15px;" iconCls="icon-addMsgPerson" onclick="addPerson()" title="添加抄送人员"></a>
						</td>
						
					</tr>
					<tr>
					<td style="text-align: center;" colspan="2">
					<div align="center"><a  class="mini-button" style="width: 150px;" iconCls="icon-save" onclick="saveCC()" >提交</a></div>
					</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	
	
	
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
       	<script type="text/javascript">
       	mini.parse();
       	
       	function addPerson() {
    		var infUser = mini.get("ccUser");
    		_UserDlg(false, function(users) {//打开收信人选择页面,返回时获得选择的user的Id和name
    			var uIds = [];
    			var uNames = [];
    			//将返回的选择分别存起来并显示
    			for (var i = 0; i < users.length; i++) {
    				var flag = true;
    				users[i].userId = users[i].userId;
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
       	
       	function saveCC(){
       	 var formData=$("#form2").serializeArray();
       	 //console.log(formData);
       		_SubmitJson({
            	url:__rootPath+"/bpm/core/bpmInstCc/save.do",
            	method:'POST',
            	data:formData,
            	success:function(result){
            		//如果存在自定义的函数，则回调
            		if(isExitsFunction('successCallback')){
            			successCallback.call(this,result);
            			return;	
            		}
            		
            		CloseWindow('ok');
            	}
            });
       	}
       	</script>
</body>
</html>