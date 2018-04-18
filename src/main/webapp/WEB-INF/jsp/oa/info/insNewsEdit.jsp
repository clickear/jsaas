<%-- 
    Document   : 新闻公告编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>新闻公告编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar topBtn" >
		<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveNews">保存</a>
<!-- 		<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a> -->
		<c:if test="${insNews.status !='Issued'}">
			<a class="mini-button" iconCls="icon-agree" plain="true" onclick="publish()">发布</a>
		</c:if>
	</div>		
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div style="padding: 5px;">
				<input id="pkId" name="newId" class="mini-hidden" value="${insNews.newId}" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<caption>新闻公告基本信息</caption>
					<tr>
						<th width="80">
							<span class="starBox">
								标　　题 <span class="star">*</span> 
							</span>
						</th>
						<td><input name="subject" value="${insNews.subject}" class="mini-textbox" vtype="maxLength:120" style="width: 80%" required="true" emptyText="请输入标题" /></td>
						<%-- <th>作　者 *：</th>
						<td><input name="author" value="${insNews.author}" class="mini-textbox" vtype="maxLength:50" style="width: 80%" required="true" /></td> --%>
					</tr>
					<%-- <tr>
						<th width="80">标　签 <span class="star">*</span> ：
						</th>
						<td><input name="tag" value="${insNews.tag}" class="mini-textbox" vtype="maxLength:100" style="width: 80%" required="true"/></td>
					</tr> --%>
					<th>
						<span class="starBox">
							摘　　要<span class="star">*</span>
						</span>		
					</th>
						<td><input name="keywords" value="${insNews.keywords}" class="mini-textbox" vtype="maxLength:50" style="width: 80%" required="true" emptyText="将显示在手机端公告列表中"/></td>
					
 					<tr>
						<%-- <th>是否为图片信息 </th>
						<td><input type="checkbox" name="isImg" value="YES" <c:if test="${insNews.isImg=='YES'}">checked="checked"</c:if> onclick="changeIsImg(this)" /></td> --%>
						<th>
							<span class="starBox">
								状　　态<span class="star">*</span> 
							</span>
						</th>
						<td><c:choose>
							<c:when test="${insNews.status =='Issued'}">
							<div style="color:#05b905">发　布</div>
							</c:when>
							<c:otherwise>
							<div style="color:red">草　稿</div>
							</c:otherwise>
						</c:choose>
					</tr> 
					<tr>
						<th>发布栏目</th>
						<td><input id="columnId" name="columnId" class="mini-combobox" url="${ctxPath}/oa/info/insNewsColumn/getJson.do"  textField="name" valueField="id"   style="width: 90%" showFolderCheckBox="false" expandOnLoad="true" showClose="true" showNullItem="true" allowInput="true"  oncloseclick="clearIssuedCols" popupWidth="400" value="${insNews.columnId}" /></td>
					</tr>
<%-- 					<tr id="imgRow" <c:if test="${empty insNews.isImg || insNews.isImg=='NO'}">style="display:none"</c:if>>
						<th>标题图片：</th>
						<td colspan="3"><input name="imgFileId" value="${insNews.imgFileId}" class="mini-hidden" vtype="maxLength:64" /> <img src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${insNews.imgFileId}" class="upload-file" /></td>
					</tr> --%>
					<tr id="comment">
						<th>
							<span class="starBox">
								是否允许评论 <span class="star">*</span>
							</span>
						</th>
						<td><ui:radioBoolean name="allowCmt" value="${insNews.allowCmt}" required="true" /></td>
					</tr>
					<tr>
						<th>带图公告</th>
						<td colspan="3"><input name="imgFileId" value="${insNews.imgFileId}" class="mini-hidden" vtype="maxLength:64" /> <img src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${insNews.imgFileId}" class="upload-file" />
						
						<span style="color: #c93756">PS:该图片将应用于手机端工作台轮播图片</span></td>
					</tr>
					<tr>
						<th>附　　件</th>
						<td colspan="3">
							<div id="files" name="files" class="upload-panel"  style="width:auto;" isone="false" sizelimit="50" isDown="false" isPrint="false" value='${insNews.files}' readOnly="false" ></div>   
						</td>
					</tr>
					
					
					<tr>
						<td colspan="4" class="mini-ueditor-td"><ui:UEditor height="300" name="content" id="content">${insNews.content}</ui:UEditor></td>
					</tr> 
				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	addBody();
	var form = new mini.Form('form1');
	$(function(){
		 $(".upload-file").on('click',function(){
			 var img=this;
			_UserImageDlg(true,
				function(imgs){
					if(imgs.length==0) return;
					$(img).attr('src','${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId='+imgs[0].fileId);
					$(img).siblings('input[type="hidden"]').val(imgs[0].fileId);
					
				}
			);
		 });
	});
	
	 	/* var statusRb = mini.get("status");
	 	var issuedCols= mini.get("issuedCols");
	 	statusRb.on("valuechanged", function (e) {
	        var val=this.getValue();
	        if(val=='Issued'){
	        	$("#issuedRow").css("display","");
	        }else{
	        	$("#issuedRow").css("display","none");
	        }
	        
	    }); */
	    
	
		function changeIsImg(ck){
			if(ck.checked){
				$("#imgRow").css("display","");
			}else{
				$("#imgRow").css("display","none");
			}
		}
	    function clearIssuedCols() {
			var columnId = mini.get("columnId");
			columnId.setValue("");
			columnId.setText("");
		}
	    
	    function publish(){

	    	form.validate();
			if (!form.isValid()) {
				return;
			}
			
			var formData = $("#form1").serializeArray();
			 //加上附件
	        var files = mini.get('files').getValue();
	        if(files!=''){
		        formData.push({
		        	name:'files',
		        	value:files
		        });
	        }
			console.log(formData);
	        _SubmitJson({
				url : __rootPath + '/oa/info/insNews/published.do',
				method : 'POST',
				data : formData,
				success : function(action){
					CloseWindow('ok');
					if(action=='ok'){
						grid.load();
					}
					return;
				} 
			});
	    }
	    //保存
	    function saveNews() {
	    	//若有自定义函数，则调用页面本身的自定义函数
	    	debugger;
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
	        //加上租用Id
	        if(tenantId!=''){
		        formData.push({
		        	name:'tenantId',
		        	value:tenantId
		        });
	        }
	        
	        //加上附件
	        var files = mini.get('files').getValue();
	        if(files!=''){
		        formData.push({
		        	name:'files',
		        	value:files
		        });
	        }
	        console.log(formData);
	        var config={
	        	url:__rootPath+"/oa/info/insNews/save.do",
	        	method:'POST',
	        	data:formData,
	        	success:function(result){	        		
	        		CloseWindow('ok');
	        	}
	        }
	        _SubmitJson(config);
	     }
	</script>
	<rx:formScript formId="form1" baseUrl="oa/info/insNews" entityName="com.redxun.oa.info.entity.InsNews"/>
</body>
</html>