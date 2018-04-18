<%-- 
    Document   : 文件编辑页
    Created on : 2015-11-21, 10:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>文件编辑</title>
<%@include file="/commons/edit.jsp"%>
<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.min.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/common/baiduTemplate.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/customer/mini-custom.js" type="text/javascript"></script>
<style type="text/css">
.mini-panel-border,.mini-panel-toolbar{
    border: none;
}
.mini-panel-toolbar, .mini-panel-footer{
	background: #fff;
}
form,.mini-tabs-bodys,
.mini-tabs-space2,
.mini-tabs-space{
	background: transparent;
	border: none;
}
.shadowBox{
	margin:20px auto 0;
	width: 90%;
}

.table-detail{
	margin-bottom: 0;
}

</style>


</head>
<body>
	<rx:toolbar toolbarId="toolbar1" hideRecordNav="true" />
	
	<div class="mini-fit rx-grid-fit">
		<form id="form1" method="post">
			<div id="tabs1" class="mini-tabs" style="width: 100%; bodyStyle="border:0;">
				
					<div title="基本信息">
					<div class="shadowBox">
						<input id="pkId" name="docId" class="mini-hidden" value="${doc.docId}" /> <input id="docType" name="docType" class="mini-hidden" value="${docType}" />
						<table class="table-detail column_2_m" cellspacing="1" cellpadding="0" style="height: 100%">
							<caption>文件基本信息</caption>
							<tr style="display: none;">
								<th>文件夹ID <span class="star">*</span>
								</th>
								<td><input name="folderId" value="${doc.folderId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true" /></td>

								<th>用户ID</th>
								<%--判断是否个人或者公司 --%>
								<td><input name="userId" value="${doc.userId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
							</tr>
							<tr>
								<th>文档名称 
									<span class="star">*</span>
								</th>
								<td colspan="3"><input name="name" value="${doc.name}" class="mini-textbox" vtype="maxLength:100" style="width: 96%" required="true" emptyText="请输入文档名称" /></td>
							</tr>

							<tr style="display: none;">
								<th>是否包括附件 </th>
								<td><input name="hasAttach" value="${doc.hasAttach}" class="mini-radiobuttonlist" style="width: 90%" required="true" textField="text" data="[{ 'id': 'YES', 'text': '是' },{ 'id': 'NO', 'text': '否' }]" /></td>
							</tr>

							<tr>
								<th>作　者</th>
								<td><input name="author" value="${doc.author}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
								<th>是否共享 <span class="star">*</span>
								</th>
								<td><input name="isShare" value="${doc.isShare}" class="mini-radiobuttonlist" style="width: 90%" required="true" textField="text" valueField="id" data="[{ 'id': 'YES', 'text': '是' },{ 'id': 'NO', 'text': '否' }]" /></td>
							</tr>
						</table>
						</div>
					</div>
					
					<div title="内容附件">
					<div style="width: 100%;">
						<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
							<tr>
								<td align="center"><input name="attach" class="upload-panel" id="attach" allowupload="true" label="附件" fname="attachName" allowlink="true" zipdown="true" fileNames="${fileNames}" fileIds="${fileIds}" /></td>
							</tr>
							<tr>
								<td> 
									<c:choose>
											<c:when test="${docType=='html'}">
												<ui:UEditor height="300" width="99%" name="content" id="content">${doc.content}</ui:UEditor>
											</c:when>
											<c:otherwise>
												<div style="width: auto; height: 480px;">
													<OBJECT id="WebOffice" name="WebOffice" width=100% height=800 classid="clsid:8B23EA28-2009-402F-92C4-59BE0E063499" codebase="<%=request.getContextPath()%>/scripts/iweboffice/iWebOffice2009.cab#version=10,1,0,0"></OBJECT>
												</div>
											</c:otherwise>
									</c:choose>
									
							</td>
							</tr>
						</table>
						</div>
					</div>
					
					<div title="权限控制">
						<div class="shadowBox" style="padding-top: 8px;">
							<table class="table-detail column_2_m" cellspacing="1" cellpadding="0" style="width: 100%;">
								<tr>
									<th>阅读权限</th>
									<td  >
										<div style="float: left;margin-bottom: 4px;">
											<textarea 
												class="mini-textboxlist" 
												allowInput="false" 
												validateOnChanged="false" 
												text="${readName}" 
												value="${readId}" 
												id="readable" 
												name="readable" 
												width="500px"
											></textarea>
										</div> 
										<a class="mini-button" iconCls="icon-addMsgPerson" onclick="addPerson(readable)">新增用户</a>
										<a class="mini-button" iconCls="icon-addMsgGroup" onclick="addGroup(readable)">新增用户组</a> 
										<a class="mini-button" iconCls="icon-cancel" onclick="clearAll()">清空用户</a>
										<div style="clear: both; color: #ff461f;">（如果为空则默认所有人都可以阅读）</div></td>
								</tr>
								<tr>
									<th>编辑权限</th>
									<td><div style="float: left;margin-bottom: 4px;">
											<textarea 
												class="mini-textboxlist" 
												allowInput="false" 
												validateOnChanged="false" 
												text="${editName}" 
												value="${editId}" 
												id="editable" 
												name="editable" 
												width="500px"
											></textarea>
										</div> 
											<a class="mini-button" iconCls="icon-addMsgPerson" onclick="addPerson(editable)">新增用户</a>
											<a class="mini-button" margin-top: 5px;" iconCls="icon-addMsgGroup" onclick="addGroup(editable)">新增用户组</a> 
											<a class="mini-button" iconCls="icon-cancel" onclick="clearAll()">清空用户</a>
										<div style="clear: both; color: #ff461f;">（如果为空则默认管理员都可以编辑）</div></td>
								</tr>
								<tr>
									<th>删除权限</th>
									<td><div style="float: left;margin-bottom: 4px;">
											<textarea 
											class="mini-textboxlist" 
											allowInput="false" 
											validateOnChanged="false" 
											text="${delName}" 
											value="${delId}" 
											id="delable" 
											name="delable" 
											width="500px"
										></textarea>
										</div> 
										<a class="mini-button"  iconCls="icon-addMsgPerson" onclick="addPerson(delable)">新增用户</a>
										<a class="mini-button"  iconCls="icon-addMsgGroup" onclick="addGroup(delable)">新增用户组</a><br /> 
										<div style="clear: both; color: #ff461f;">
											（如果为空则默认管理员可以删除）
										</div></td>
	
								</tr>
							</table>
						</div>
					</div>
				</div>
		
		</form>
	</div>
	
	<script type="text/javascript">
	mini.parse();
		<c:if test="${docType!='html'}">
		var type = ".${docType}";
		var fileName="${fileName}";
		var docPath="${docPath}";
		if(docPath.length==0){
			docPath=" ";
		}
		var WebOffice=document.getElementById('WebOffice');
		window.onload=function(){
			try{
				WebOffice.WebUrl= "<%=request.getContextPath()%>/iWebOffice/docOfficeServer.jsp?docPath="+docPath;  //处理页地址，这里用的是相对路径
				WebOffice.RecordID = "65422345"; //文档编号
				WebOffice.Template = "12345678"; //模板编号
				WebOffice.FileName ="65422345"+type; //文件名称
				WebOffice.FileType = type; //文件类型
				WebOffice.UserName = "kinggrid"; //用户名
				WebOffice.EditType = "1"; //编辑类型 
				WebOffice.PenColor = "#FF0000"; //默认手写笔迹颜色
				WebOffice.PenWidth = "1"; //默认手写笔迹宽度
				WebOffice.Print = "1"; //是否打印批注
				WebOffice.ShowToolBar = "0"; //关闭工具栏
				WebOffice.WebOpen(false);
				WebOffice.OfficeSize='100%';
				WebOffice.ShowMenu="0";
			}catch(e){
				alert("金格控件请使用IE浏览器"); }
		}	
			
			
			
		</c:if>
			
		  
		
	
	
	//增加个人权限
	function addPerson(type) {
		var infUser = mini.get(type);
		_UserDlg(false, function(users) {//打开收信人选择页面,返回时获得选择的user的Id和name
			var uIds = [];
			var uNames = [];
			//将返回的选择分别存起来并显示
			for (var i = 0; i < users.length; i++) {
				var flag = true;
				users[i].userId = users[i].userId + "_user";
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
	//增加组权限
	function addGroup(type) {
		var infGroup = mini.get(type);
		_GroupDlg(false, function(groups) {
			var uIds = [];
			var uNames = [];
			for (var i = 0; i < groups.length; i++) {
				var flag = true;
				groups[i].groupId = groups[i].groupId + "_group";
				var oldValues = infGroup.getValue().split(',');
				var oldText = infGroup.getText().split(',');
				for(var j=0;j<oldValues.length;j++){
					if(oldValues[j]==groups[i].groupId&&oldText[j]==groups[i].name){
						flag = false;
						break;
					}
				}
				if(flag==true){
				uIds.push(groups[i].groupId);
				uNames.push(groups[i].name);
				}
			}
			if (infGroup.getValue() != '') {
				uIds.unshift(infGroup.getValue().split(','));
			}
			if (infGroup.getText() != '') {
				uNames.unshift(infGroup.getText().split(','));
			}
			infGroup.setValue(uIds.join(','));
			infGroup.setText(uNames.join(','));
		});
	}
	
	
	function selfSaveData(){
		form.validate();
        if (!form.isValid()) {
            return;
        }
        
        
        var formData=$("#form1").serializeArray();
        //处理扩展控件的名称
        var extJson=_GetExtJsons("form1");
        for(var key in extJson){
        	formData.push({name:key,value:extJson[key]});
        }
       
       
       //若定义了handleFormData函数，需要先调用 
       if(isExitsFunction('handleFormData')){
        	var result=handleFormData(formData);
        	if(!result.isValid) return;
        	formData=result.formData;
        }
       if(${docType!='html'}){
    	   WebOffice.WebSave();			
 	      var docPath= WebOffice.WebGetMsgByName("docPath");
 	      formData.push({name:"docPath",value:docPath});
       }
        
        $.ajax({
        	url:"${ctxPath}/oa/doc/doc/save.do",
        	type:"POST",
        	dataType:"json",
        	data:formData,
        	success:function (result){
 				if(result.success){
        				var msgId=mini.showMessageBox({
        	            title: "提示信息",
        	            iconCls: "mini-messagebox-info",
        	            buttons: ["ok"],
        	            message: "添加文档成功!",
        	            callback: function (action) {
        	            	 if(action=="ok"){
        	            		 mini.hideMessageBox(msgId);
            	            	 CloseWindow("ok");
        	            	 }
        	            }
        	        });
        		}
        		else{
        			top._ShowErr({content:result.message})
        		}
        	}
    	});
	
	}
	addBody();
	
</script>
	<rx:formScript formId="form1" baseUrl="oa/doc/doc" entityName="com.redxun.oa.doc.entity.Doc" />
</body>
</html>