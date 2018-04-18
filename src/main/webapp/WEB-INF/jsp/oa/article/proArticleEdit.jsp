
<%-- 
    Document   : [文章]编辑页
    Created on : 2017-09-29 14:39:26
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[文章]编辑</title>
<%@include file="/commons/edit.jsp"%>
<script type="text/javascript"    src="${ctxPath}/scripts/ueditor/ueditor.config.js"></script>
<script type="text/javascript"   src="${ctxPath}/scripts/ueditor/ueditor-fd.all.js"> </script>
<script type="text/javascript"   src="${ctxPath}/scripts/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
    <div class="mini-toolbar">
   <!--  <a class="mini-button" iconCls="icon-save" plain="true" onclick="selfSaveData(true)">保存</a> -->
    <a class="mini-button" iconCls="icon-ok" plain="true" onclick="selfSaveData(false)">保存</a>
	</div>    
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			
				<input id="pkId" name="id" class="mini-hidden" value="${proArticle.id}" />
				<input id="belongProId" name="belongProId" class="mini-hidden" value="${proArticle.belongProId}" />
				<input id="parentId" name="parentId" class="mini-hidden" value="${proArticle.parentId}" />
				<table class="table-detail column_2" cellspacing="1" cellpadding="0">
					<tr>
						<th>标题：</th>
						<td colspan="3">
							
								<input name="title" value="${proArticle.title}" required="true"
							class="mini-textbox"   style="width: 90%" />
						</td>
						
					</tr>
					<tr>
					<th>作者：</th>
						<td>
							
								<input name="author" value="${proArticle.author}" 
							class="mini-textbox"   style="width: 50%" />
						</td>
						<th>序号：</th>
						<td>
							<input id="sn" name="sn" class="mini-spinner" required="true"  minValue="1" maxValue="99"  value="${proArticle.sn}" format="0"  style="width: 50%"/>
						</td>
					</tr>
					<tr class="articleEL">
						<td colspan="4">
							<script id="content" name="content"  type="text/plain">${proArticle.content}</script>
						</td>
					</tr>
				</table>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/article/proArticle"
		entityName="com.redxun.oa.article.entity.ProArticle" />
	
	<script type="text/javascript">
	mini.parse();
	var form = new mini.Form("form1");
	//var type=mini.get("type");
	var pkId=mini.get("pkId");
	
	$(function(){
		var content = UE.getEditor('content',{
			initialFrameHeight: $(document).height()-250,
			initialFrameWidth:$(document).width()-10});
		content.addListener("ready", function () {
		
		});
	})
	
	function openGet(){
		if(${!empty proArticle.id}){
			window.location.href='${ctxPath}/oa/article/proArticle/get.do?pkId=${proArticle.id}';
		}else{
			alert("请先保存");
		}
	}
	
	
	
	function selfSaveData(change){
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
	        
	       $.ajax({
	        	url:'${ctxPath}/oa/article/proArticle/save.do',
	        	type:'post',
	        	data:formData,
	        	success:function(result){
	        		if(result.success){
	        			if(change){
	        				window.parent.initIframe();
	        			}
	        			window.parent.dataSaveResult(result.data);
	        			pkId.setValue(result.data);
	        		}
	        	}
	        });
	}
	</script>
</body>
</html>