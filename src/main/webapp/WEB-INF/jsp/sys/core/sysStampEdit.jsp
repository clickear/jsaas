
<%-- 
    Document   : [office印章]编辑页
    Created on : 2018-02-01 09:41:39
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[office印章]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="sysStamp.id" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${sysStamp.id}" />
				<table class="table-detail column_2" cellspacing="1" cellpadding="0">
					<caption>[office印章]基本信息</caption>
					<tr>
						<th>签章名称：</th>
						<td colspan="3">
							<input id="name" name="name" value="${sysStamp.name}"
							class="mini-textbox"   style="width: 90%" required="true" />
						</td>
					</tr>
					<tr>
						<th>签章用户：</th>
						<td>
							
								<input id="signUser" name="signUser" value="${sysStamp.signUser}"
							class="mini-textbox"   style="width: 90%" required="true"/>
						</td>
						<th>签章密码：</th>
						<td>
							
								<input id="password" name="password" value="${sysStamp.password}"
							class="mini-textbox"   style="width: 90%" vtype="minLen:6" />
						</td>
					</tr>
					<tr>
						<th>印章：</th>
						<td colspan="3">
							<object id="ntkosignctl"  classid="clsid:97D0031E-4C58-4bc7-A9BA-872D5D572896"    
								codebase="${ctxPath}/iWebOffice/ntkosigntoolv3.cab#version=4,0,0,8"  width="200" height="200">   
								<param name="BackColor" value="16744576">   
								<param name="ForeColor" value="16777215">   
								<param name="IsShowStatus" value="-1">   
								<param name="EkeyType" value="14">   
								<SPAN STYLE="color:red">不能装载印章管理控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>   
							</object>  
							<c:if test="${ empty sysStamp.stampId}">
								<input type="file" id="stampFile" style="display:block;" onchange="selectFile()"/> 
							</c:if>
							<input name="stampId" value="${sysStamp.stampId}" 
								class="mini-hidden"   style="width: 90%" />
							
								
						</td>
					</tr>
					<tr>
						<th>描述：</th>
						<td colspan="3">
							
								<textarea  name="description" 
							class="mini-textarea"   style="width: 90%" />${sysStamp.description}</textarea>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	
	
	<rx:formScript formId="form1" baseUrl="sys/core/sysStamp"
		entityName="com.redxun.sys.core.entity.SysStamp" />
	
	<script type="text/javascript">
	mini.parse();
	
	var genStamp=false;
	
	var isAdd=${empty sysStamp.id};
	var stampId="${sysStamp.stampId}";
	
	$(function(){
		initStamp();
	})
	
	function initStamp(){
		if(isAdd) return;
		var stampId="${sysStamp.stampId}";
		var pwd="${sysStamp.password}";
		var url=__rootPath +"/sys/core/file/download/"+stampId+".do";
		var signObj=document.getElementById("ntkosignctl");
		signObj.OpenFromURL(url,pwd);
		
	}
	
	function selectFile(){
		var signObj=document.getElementById("ntkosignctl");
		var name=mini.get("name").getValue();
		var signUser=mini.get("signUser").getValue();
		var password=mini.get("password").getValue();
		var stampFile=document.getElementById("stampFile").value;
		signObj.CreateNew(name,signUser,password,stampFile);
		if(signObj.StatusCode==0){
			genStamp=true;
		}
	}
	
	
	function handleFormData(formData){
		var signObj=document.getElementById("ntkosignctl");
		var result={isValid:true,formData:formData};
		var  url=__rootPath +"/sys/core/sysStamp/saveStamp.do";
		
		if(isAdd){
			if(!genStamp){
				alert("还没生成印章");
				result.isValid=false;
			}
			var params="type=stamp";
			var rtn=signObj.SaveToURL(url, "stamp",params,"", "");
			var json=eval("("+rtn+")");
			for(var i=0;i<formData.length;i++){
				var obj=formData[i];
				if(obj.name=="stampId"){
					obj.value=json.data;
				}
			}
		}
		else{
			var name=mini.get("name").getValue();
			var signUser=mini.get("signUser").getValue();
			var password=mini.get("password").getValue();
			signObj.SignName=name;
			signObj.SignUser=signUser;
			signObj.Password=password;
			
			var params="type=stamp&stampId="+stampId;
			var rtn=signObj.SaveToURL(url, "stamp",params,"", "");
			
		}
			
		return result;
	}

	</script>
</body>
</html>