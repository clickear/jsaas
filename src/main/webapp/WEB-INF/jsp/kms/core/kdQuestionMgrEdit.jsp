<%-- 
    Document   : [KdQuestion]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[KdQuestion]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${kdQuestion.queId}" />
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="queId" class="mini-hidden" value="${kdQuestion.queId}" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<caption>问题基本信息</caption>

					<tr>
						<th width="15%">
							<span class="starBox">
								问题内容 <span class="star">*</span>
							</span> 
						</th>
						<td>
							<input 
								name="subject" 
								value="${kdQuestion.subject}" 
								class="mini-textbox" 
								vtype="maxLength:80" 
								style="width: 90%" 
								required="true" 
								emptyText="一句话描述你的问题" 
							/>
						</td>
					</tr>

					<tr>
						<th width="15%">问题补充（选填） </th>
						<td>
							<a id="desc" style="padding: 4px 12px;border: 1px solid #ececec;border-radius: 4px;cursor: pointer;">添加</a>
						</td>
					</tr>

					<tr id="questiondesc" style="display: none;">
						<td colspan="2" class="td-grid">
							<div>
								<ui:UEditor height="200" name="question" id="question">${kdQuestion.question}</ui:UEditor>
							</div>
						</td>
					</tr>

					<tr>
						<th>附　　件 </th>
						<td>
							<input 
								name="fileIds" 
								class="upload-panel" 
								plugins="upload-panel" 
								style="width: 80%" 
								allowupload="true" 
								label="附件" 
								fname="fileNames" 
								allowlink="true" 
								zipdown="true" 
								mwidth="80" 
								wunit="%" 
								mheight="0" 
								hunit="px" 
								fileIds="${kdQuestion.fileIds}" 
								fileNames="${fileNames}" 
							/>
						</td>
					</tr>

					<tr>
						<th>分　　类 </th>
						<td>
							<input 
								id="treeId" 
								name="treeId" 
								value="${kdQuestion.treeId}" 
								text="${kdQuestion.sysTree.name}" 
								style="width: 250px;" 
								class="mini-buttonedit" 
								onbuttonclick="onButtonEdit" 
								allowInput="false" 
							/>
						</td>
					</tr>

					<tr>
						<th>标　　签 </th>
						<td><input name="tags" value="${kdQuestion.tags}" class="mini-textbox" vtype="maxLength:128" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								悬赏货币 <span class="star">*</span>
							</span> 
						</th>
						<td><c:choose>
								<c:when test="${kdQuestion.queId==null}">
									<input 
										id="rewardScore" 
										name="rewardScore" 
										class="mini-combobox" 
										style="width: 150px" 
										popwidth="150" 
										onvaluechanged="" 
										data="[{'id':'0','text':'悬赏货币0'},{'id':'5','text':'悬赏货币5'},{'id':'10','text':'悬赏货币10'},{'id':'15','text':'悬赏货币15'},{'id':'20','text':'悬赏货币20'},{'id':'30','text':'悬赏货币30'},{'id':'50','text':'悬赏货币50'},{'id':'80','text':'悬赏货币80'},{'id':'100','text':'悬赏货币100'}]" 
										textField="text" 
										valueField="id" 
										value="0" 
										required="true" 
										allowInput="false" 
									/>
								</c:when>
								<c:otherwise>
									<input 
										id="rewardScore" 
										name="rewardScore" 
										class="mini-combobox" 
										style="width: 150px" 
										popwidth="150" 
										onvaluechanged="" 
										data="[{'id':'0','text':'悬赏货币0'},{'id':'5','text':'悬赏货币5'},{'id':'10','text':'悬赏货币10'},{'id':'15','text':'悬赏货币15'},{'id':'20','text':'悬赏货币20'},{'id':'30','text':'悬赏货币30'},{'id':'50','text':'悬赏货币50'},{'id':'80','text':'悬赏货币80'},{'id':'100','text':'悬赏货币100'}]" 
										textField="text" 
										valueField="id" 
										value="${kdQuestion.rewardScore}" 
										required="true" 
										allowInput="false" 
									/>
								</c:otherwise>
							</c:choose></td>
					</tr>

					<tr>
						<th>回复人类型 </th>
						<td>
						<c:choose>
							<c:when test="${kdQuestion.queId==null}">
								<div 
									id="replyType" 
									name="replyType" 
									class="mini-radiobuttonlist" 
									required="true" 
									data="[{id:'ALL',text:'所有人'},{id:'EXPERT',text:'专家个人'},{id:'DOMAIN_EXPERT',text:'领域专家'}]" 
									value="ALL"
								></div>
							</c:when>
							<c:otherwise>
								<div 
									id="replyType" 
									name="replyType" 
									class="mini-radiobuttonlist" 
									required="true" 
									data="[{id:'ALL',text:'所有人'},{id:'EXPERT',text:'专家个人'},{id:'DOMAIN_EXPERT',text:'领域专家'}]" 
									value="${kdQuestion.replyType}"
								></div>
							</c:otherwise>
						</c:choose>
						</td>
					</tr>

					<tr>
						<th>问题状态 </th>
						<td>
						<c:choose>
							<c:when test="${kdQuestion.queId==null}">
								<div 
									id="status" 
									name="status" 
									class="mini-radiobuttonlist" 
									required="true" 
									data="[{id:'UNSOLVED',text:'待解决'},{id:'SOLVED',text:'已解决'}]" 
									value="UNSOLVED" 
									enabled="false"
								></div>
							</c:when>
							<c:otherwise>
								<div 
									id="status" 
									name="status" 
									class="mini-radiobuttonlist" 
									required="true" 
									data="[{id:'UNSOLVED',text:'待解决'},{id:'SOLVED',text:'已解决'}]" 
									value="${kdQuestion.status}" 
									enabled="false"
								></div>
							</c:otherwise>
						</c:choose>
						</td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								回  复  数 <span class="star">*</span>
							</span> 
						</th>
						<td>
						<c:choose>
							<c:when test="${kdQuestion.queId==null}">
								<input 
									name="replyCounts" 
									value="0" 
									class="mini-textbox" 
									vtype="maxLength:10" 
									style="width: 90%" 
									required="true" 
									emptyText="请输入回复数"  
									enabled="false" 
								/>
							</c:when>
							<c:otherwise>
								<input 
									name="replyCounts" 
									value="${kdQuestion.replyCounts}" 
									class="mini-textbox" 
									vtype="maxLength:10" 
									style="width: 90%" 
									required="true" 
									emptyText="请输入回复数"  
									enabled="false" 
								/>
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="kms/core/kdQuestion" entityName="com.redxun.kms.core.entity.KdQuestion" />
	<script type="text/javascript">
		var desc=mini.get("desc");
		
		$("#desc").click(function(){
			$('#questiondesc').toggle();
			if( this.text == '添加' ){
				$(this).text('关闭');
			}else{
				$(this).text('添加');
			}
		});
		
		$(function() {
			$('.upload-panel').each(function() {
				$(this).uploadPanel();
			});
		});
		
		function onButtonEdit(e) {
			var btnEdit = this;
			mini.open({
						url : "${ctxPath}/kms/core/sysTree/dialog.do",
						title : "选择问题分类",
						width : 650,
						height : 380,
						ondestroy : function(action) {
							if (action == "ok") {
								var iframe = this.getIFrameEl();
								var data = iframe.contentWindow.GetData();
								data = mini.clone(data); 
								if (data) {
									btnEdit.setValue(data.treeId);   //设置自定义SQL的Key
									_SubmitJson({
										url:"${ctxPath}/sys/core/sysTree/getNameByPath.do",
										showMsg:false,
										data:{
											path:data.path
										},
										method:'POST',
										success:function(result){
											btnEdit.setText(result.data);
										}
									});
								}
							}
						}
					});
		}
		
	    function preRecord() {
	        //调用父窗口获得前一记录的PKID
	        var pkId = top['com.redxun.kms.core.entity.KdQuestion'].preRecord();
	        if (pkId == 0){
	            return;
	        }
	        form.loading();
	        window.location="${ctxPath}/kms/core/kdQuestion/mgrEdit.do?pkId="+pkId;
	    }

	    function nextRecord() {
	        //调用父窗口获得下条一记录
	        var pkId = top['com.redxun.kms.core.entity.KdQuestion'].nextRecord();
	        if (pkId == 0){
	            return;
	        }
	        form.loading();
	        window.location="${ctxPath}/kms/core/kdQuestion/mgrEdit.do?pkId="+pkId;
	    }
	</script>
</body>
</html>