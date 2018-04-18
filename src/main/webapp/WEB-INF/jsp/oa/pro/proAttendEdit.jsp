<%-- 
    Document   : [ProAttend]编辑页
    Created on : 2016-1-06, 10:11:48
    Author     : cmc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>人员编辑</title>
<%@include file="/commons/edit.jsp"%>
<style>
.table-detail1 {
	border-collapse: collapse;
	border: solid 1px #909AA6;
	width: 100%;
	margin-top: 6px;
}
.table-detail1 th {
	min-height: 25px;
	border: solid 1px #909AA6;
	text-align: right;
	vertical-align: top;
	font-weight: bold;
	min-width: 100px;
	padding: 5px;
}
.table-detail1 td {
	min-height: 25px;
	border: solid 1px #909AA6;
	text-align: left;
	padding: 5px;
}
.table-detail1 td.parttype td {
	border: none;
	padding: 2px;
}
.table-detail1 caption {
	text-align: left;
	height: 34x;
	font-size: 20px;
	padding: 6px;
}
</style>
</head>
<body>
        <rx:toolbar toolbarId="toolbar1" pkId="${proAttend.attId}"  hideRemove="true" hideRecordNav="true">
	<div class="self-toolbar">
	 <a class="mini-button" iconCls="icon-reload" plain="true" onclick="location.reload();">刷新</a>
	</div>
	</rx:toolbar>
        	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="attId" class="mini-hidden" value="${proAttend.attId}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0" >
					<caption>项目、产品人员参与信息</caption>

					<tr style="display: none;">
						<th>项目 </th>
						<td><input name="projectId" value="${proAttend.projectId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
						<th>对比字段 </th>
						<td><input name="alexist" value="${proAttend.userId},${proAttend.groupId}" class="mini-textbox" /></td><!--  这个字段把人和组存进去在后端对比检查是否有重复，用来排除本身就填写过的，避免编辑时保存提示重复-->
					</tr>

					<tr>
						<th>参与人  
						</th>
						<td title="可双击选择" style="width:6cm;">
							
							<input id="userId" class="mini-textboxlist" name="userId"   style="width:100%;"  value="${proAttend.userId}" text="${user}"  allowInput="false"   />
							
							</td><td><a class="mini-button" iconCls="icon-grant" plain="true" onclick="onSelectUser('userId')">选择</a></td>
					</tr>

					<tr>
						<th>参与组  
						</th>
						<td title="可双击选择" style="width:6cm;">
						   <input id="groupId" class="mini-textboxlist" name="groupId"   style="width:100%;" value="${proAttend.groupId}" text="${group}"  allowInput="false"  />
							
							</td><td><a class="mini-button" iconCls="icon-grant" plain="true" onclick="onSelectGroup('groupId')">选择</a></td>
					</tr>

					<tr style="display: none;">
						<th>参与类型 <span class="star">*</span> 
						</th>
						<td colspan="2" class="parttype">
							<div  name="partType" id="partType" class="mini-radiobuttonlist" repeatItems="1" repeatLayout="table" repeatDirection="vertical" required="true"     
                         value="JOIN"   >
							</td>
					</tr>
					
					
					
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/pro/proAttend"  entityName="com.redxun.oa.pro.entity.ProAttend"/>
	<script type="text/javascript">
	mini.parse();
	
	function onSelectGroup(where){
		_TenantGroupDlg('',false,false,function(groups){
			var groupId=mini.get(where);
			var id="";
			var value="";
			for(var i=0;i<groups.length;i++){
					id+=groups[i].groupId+",";
					value+=groups[i].name+",";
			}
			groupId.setValue(id.substring(0,id.length-1));
			groupId.setText(value.substring(0,value.length-1));
		},false);
	}
	
	function onSelectUser(where){
		_TenantUserDlg(tenantId,false,function(user){
			var userId=mini.get(where);
			var id="";
			var value="";
			for(var i=0;i<user.length;i++){
					id+=user[i].userId+",";
					value+=user[i].fullname+",";
			}
			
			userId.setValue(id.substring(0,id.length-1));
			userId.setText(value.substring(0,value.length-1));
			
		});
	}
	
	
	 $(function(){
		$("#userId").dblclick(function(){
			onSelectUser('userId');
		});
		$("#groupId").dblclick(function(){
			onSelectGroup('groupId');
		});
		
		
	}) 
	
	 var tip = new mini.ToolTip();
	tip.set({
	    target: document,
	    selector: '[title]'//提示
	}); 
	
	
	function  savethis(){
		var group=mini.get("groupId").getValue();
		var user=mini.get("userId").getValue();
		if(group.length>0||user.length>0)
		{
			SaveData();
		}else{
			alert("参与组和参与人不能都为空！");
		}
	}
	
	//重写了saveData方法
	function selfSaveData(issave){
		var form = new mini.Form("form1");
		form.validate();
	    if (!form.isValid()) {//验证表格
	        return; }
	    var formData=$("#form1").serializeArray();
	    //加上租用Id
	    if(tenantId!=''){
	        formData.push({
	        	name:'tenantId',
	        	value:tenantId });
	    }
	    _SubmitJson({
	    	url:"${ctxPath}/oa/pro/proAttend/save.do?",
	    	method:'POST',
	    	data:formData,
	    	success:function(result){
	    		//如果存在自定义的函数，则回调
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
	    		CloseWindow('ok');} });
	}
	
	
</script>
</body>
</html>