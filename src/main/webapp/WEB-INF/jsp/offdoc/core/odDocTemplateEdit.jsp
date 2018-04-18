<%-- 
    Document   : [OdDocTemplate]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>文件编辑</title>
<%@include file="/commons/edit.jsp"%>
<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.min.js" type="text/javascript"></script>
<link href="${ctxPath}/scripts/ueditor/form-design/form-def.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/jquery/plugins/uploadpanel/jquery-uploadpanel.js" type="text/javascript"></script>
<!-- 加上扩展控件的支持 -->
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/ueditor-ext.js"></script>
<link href="${ctxPath}/scripts/ueditor/form-design/form-def.css" rel="stylesheet" type="text/css" />
</head>
<body class="Noprint" onLoad="form1.SignatureControl.ShowSignature('111');" onUnload="form1.SignatureControl.DeleteSignature();">
	<rx:toolbar toolbarId="toolbar1" pkId="${odDocTemplate.tempId}"  excludeButtons="remove"/>
	<!-- <button onclick="WebUseTemplate()">套红</button>
	<button onclick="showTrace()">显示痕迹</button>
	<button onclick="hideTrace()">隐藏痕迹</button>
	<button onclick="checkPoint()">检查输入点</button>
	<button onclick="CreateSignature('0')">电子签章</button>
	<button onclick="saveAll()">保存文件到本地</button>
	<button onclick="hasErroSign()">是否有错误的签章</button>
	<button onclick="deleteSign()">撤销印章</button> -->
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="tempId" class="mini-hidden" value="${odDocTemplate.tempId}" />
				<input name="treeId" value="${odDocTemplate.treeId}" class="mini-hidden" vtype="maxLength:64" style="width: 90%" />
				<table class="table-detail column_2" cellspacing="1" cellpadding="0">
					<caption>公文模板信息</caption>
					<tr>
						<th>
							 <span class="starBox">
								模板名称 <span class="star">*</span> 
							</span>
						</th>
						<td><input name="name" value="${odDocTemplate.name}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" required="true"
							emptyText="请输入模板名称" /></td>
						<th>
							<span class="starBox">
								模板状态<span class="star">*</span> 
							</span>
						</th>
							<td><input name="status" value="${odDocTemplate.status}"
							class="mini-radiobuttonlist" style="width: 90%"
							required="true" textField="text" valueField="id" 
							data="[{ 'id': 'ENABLED', 'text': '启用' },{ 'id': 'DISABLED', 'text': '禁用' }]"/></td>
					</tr>
                    <tr>
						<th>模板描述 </th>
						<td colspan="3"><textarea name="descp" class="mini-textarea" vtype="maxLength:512" style="width: 90%">${odDocTemplate.descp}</textarea></td>
					</tr>
					<%-- <tr>
						<th>文件ID ：</th>
						<td><input name="fileId" value="${odDocTemplate.fileId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" />
						</td>
					</tr> --%>
					 <%-- <tr style="display: none;">
						<th>模板文件路径 ：</th>
						<td><input name="filePath" value="${odDocTemplate.filePath}" class="mini-textbox" vtype="maxLength:255" style="width: 90%" />
						</td>
					</tr>  --%>
					<tr style="display: none;">
						<th>模板类型</th>
						<td><input name="tempType" value="${odDocTemplate.tempType}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" />
						<%-- <input name="fileId" value="${odDocTemplate.fileId}" class="mini-textbox"/>
						<input name="filePath" value="${odDocTemplate.filePath}" class="mini-textbox"/> --%>
						</td>
					</tr>
					<tr>
					<td colspan="4">
					<OBJECT 
						id="WebOffice" 
						name="WebOffice" 
						width=100% 
						classid="clsid:8B23EA28-2009-402F-92C4-59BE0E063499"
						codebase="<%=request.getContextPath()%>/scripts/iweboffice/iWebOffice2009.cab#version=10,1,0,0"
					>
					</OBJECT>
					</td>
					</tr>
					<OBJECT id=SignatureAPI classid="clsid:79F9A6F8-7DBE-4098-A040-E6E0C3CF2001" codebase="<%=request.getContextPath()%>/scripts/iweboffice/iSignatureAPI.ocx#version=6,0,0,46"
									width=0 height=0 align=center hspace=0 vspace=0></OBJECT>
				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	addBody();
	mini.parse();
	/*
	CreateSignature参数值列表
	*/
	var stSign = 0x00000001;     //电子签章
	var stHand = 0x00000002;     //手写签名
	var stGroup = 0x00000003;    //批量验证
	var stBarCode = 0x00000005;  //二维条码
	var Comments = 1;            //锁定批注
	var FormFields = 2;          //锁定窗体
	/*
	SelectionState返回值列表
	*/
	var  ssFailed            = -1;        //未知状态
	var  ssSucceeded         = 0x0000;     //成功
	var  ssNoInstall         = 0x0001;     //电脑未正确安装电子签章软件！
	var  ssNoActiveDocument  = 0x0002;     //不存在活动的文档或者未设置ActiveDocument！
	var  ssDocumentLocked    = 0x0003;     //文档已经锁定
	var  ssDocumentInObject  = 0x0004;     //光标置于对象之上，请处于编辑状态
	var  ssDocumentInHFooter = 0x0005;     //光标在页眉面脚上，不能签章。
	var  ssDocumentInTextbox = 0x0006;     //光标不能在文档框内签章
	var  ssDocumentInEdit    = 0x0007;     //EXCEL不能在编译模式下进行签章。
	
	
	
	/*金格*/
	var WebOffice=document.getElementById('WebOffice');
	window.onload=function(){
		var type = ".doc";
		var docPath="${odDocTemplate.filePath}";
		var fileId="${odDocTemplate.fileId}";
		if(docPath.length==0){
			docPath=" ";
		}
		if(fileId.length==0){
			fileId=" ";
		}
		try{
			WebOffice.WebUrl= "<%=request.getContextPath()%>/iWebOffice/docOfficeServer.jsp?docPath="+docPath;  //处理页地址，这里用的是相对路径
			WebOffice.RecordID = fileId; //文档编号
			WebOffice.FileName ="65422345"+type; //文件名称
			WebOffice.FileType = type; //文件类型
			WebOffice.UserName = "kinggrid"; //用户名
			WebOffice.EditType = "1"; //编辑类型 
			//WebOffice.PenColor = "#FF0000"; //默认手写笔迹颜色
			//WebOffice.PenWidth = "1"; //默认手写笔迹宽度
			WebOffice.Print = "1"; //是否打印批注
			WebOffice.ShowToolBar = "0"; //关闭工具栏
			WebOffice.WebOpen(false);
			WebOffice.OfficeSize='100%';
			WebOffice.ShowMenu="0";
		}catch(e){
			alert("金格控件请使用IE浏览器"); }
	}
	
	var SignatureAPI=document.getElementById('SignatureAPI');
	function checkPoint(){
		
		alert(SignatureAPI.SelectionState);
		alert(SignatureAPI.SignatureCount);
	}
	
	
	function SetActiveDocument(){
		SignatureAPI.ActiveDocument=WebOffice.WebObject;
	}
	//作用：创建电子签章
	function CreateSignature(id)
	{
	  SetActiveDocument();     //设置活动文档
	  if(id==0){
	    if(SignatureAPI.SelectionState==ssSucceeded)         //当前光标状态
	    {
		     SignatureAPI.CreateSignature(stSign);	//建立电子签章
	    }
	  }
	  if(id==1){
	    if(SignatureAPI.SelectionState==ssSucceeded)
	    {
		    SignatureAPI.CreateSignature(stHand);     //建立手写签名
	    }
	  }

	  if (id==2){
		   SignatureAPI.DoAction(3,"");   //建立批量验证
	  }

	  if (id==3){
		   SignatureAPI.DoAction(4,"");   //建立参数设置
	  }

	  if (id==5){
	    if(SignatureAPI.SelectionState==ssSucceeded)
	    {
		   SignatureAPI.CreateSignature(stBarCode);  //建立二维条码
	    }
	  }
	}
	
	function saveAll(){
		SetActiveDocument();
		SignatureAPI.SaveToFileNoSignature();
	}
	
	function lockDocument(){
		SignatureAPI.LockDocument(2);
	}
	
	//作用：保存文档
	function SaveDocument(){
	  //webform.WebOffice.WebSetMsgByName("MyDefine1","自定义变量值1");  //设置变量MyDefine1="自定义变量值1"，变量可以设置多个  在WebSave()时，一起提交到OfficeServer中
	  if (!WebOffice.WebSave(true)){    //交互OfficeServer的OPTION="SAVEFILE"  注：WebSave()是保存复合格式文件，包括OFFICE内容和手写批注文档；如只保存成OFFICE文档格式，那么就设WebSave(true)
	     return false;
	  }else{
	     return true;
	  }
	}
	function hasErroSign(){
		alert(SignatureAPI.HasErrorSignature());
	}
	
	function WebAcceptAllRevisions(){
		  WebOffice.WebObject.Application.ActiveDocument.AcceptAllRevisions();
		  var mCount = WebOffice.WebObject.Application.ActiveDocument.Revisions.Count;
		  if(mCount>0){
		    return false;
		  }else{
		    return true;
		  }
		}
	
	
		  function WebUseTemplate(){
			  var mObject = new Object();
			  mObject.Template = "";
			  _OpenWindow({
					title:'业务模型选择',
					url:"${ctxPath}/offdoc/core/odDocTemplate/for.do",
					width:500,
					height:500,
					ondestroy:function(action){
						if(action=='ok'){
							var iframe = this.getIFrameEl();
							mObject.Template = iframe.contentWindow.fileId;	
							//判断用户是否选择模板
							  if (mObject.Template==""){
								  alert(mObject.Template);
							    return ;
							  }else{
							    if(WebAcceptAllRevisions()==false){                         //清除正文痕迹的目的是为了避免痕迹状态下出现内容异常问题。
							    	alert("失败");
							      return false;      
							    }
							    SaveDocument();                                             //保存当前编辑的文档
							    WebOffice.WebSetMsgByName("COMMAND","INSERTFILE");  
							    WebOffice.Template=mObject.Template;                
							    WebOffice.EditType="1";                             //控制为不保留痕迹的状态
							    if (WebOffice.WebLoadTemplate()){                   //交互OfficeServer的OPTION="LOADTEMPLATE"
							    	WebOffice.WebObject.Application.Selection.GoTo(-1,0,0,"Content");
							      //SetBookmarks("Title","关于中间件研发工作会议通知");     //填充模板其它基本信息，如标题，主题词，文号，主送机关等
							      if (WebOffice.WebInsertFile()){                   //填充公文正文   交互OfficeServer的OPTION="INSERTFILE"
									//alert(WebOffice.WebGetMsgByName("POSITION"));
							        return true;
							      }else{
							       alert("不成功");
							        return false;
							      }
							    }else{
							    	alert("不成功");
							      return false;
							    }
							  }
						}
					}
				});
			  
			}

	
	
		 function showTrace(){
			 WebOffice.WebShow(true);
		 }
		 function hideTrace(){
			 WebOffice.WebShow(false);
		 }
	
	function selfSaveData(){
		form.validate();
        if (!form.isValid()) {
            return;
        }
        WebOffice.WebSetBookMarks("Content","Content");
        if(WebOffice.WebGetBookmarks("Content")!="Content"){
        	alert("保存模板前请在里面插入名为Content的书签");
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
        
       
        WebOffice.WebSave(true);			
	      var docPath= WebOffice.WebGetMsgByName("docPath");
	      var fileId= WebOffice.WebGetMsgByName("fileId");
	      formData.push({name:"filePath",value:docPath});
	      formData.push({name:"fileId",value:fileId});
        $.ajax({
        	url:"${ctxPath}/offdoc/core/odDocTemplate/save.do",
        	type:"POST",
        	data:formData,
        	success:function (result){
 			//alert(result.docId);
 			CloseWindow('ok');
        		if(result){
        			 mini.showTips({
        		            content: "<b>成功</b> <br/模板保存成功",
        		            state: 'success',
        		            x: 'center',
        		            y: 'center',
        		            timeout: 3000
        		        });
        		}
        	}
        });
	}
	
	function deleteSign(){
		SignatureAPI.DeleteSignature();
	}
	

</script>
	<rx:formScript formId="form1" baseUrl="offdoc/core/odDocTemplate" entityName="com.redxun.offdoc.core.entity.OdDocTemplate"/>
</body>
</html>