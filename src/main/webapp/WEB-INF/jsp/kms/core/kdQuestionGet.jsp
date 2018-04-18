<%-- 
    Document   : [KdQuestion]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>问答详情</title>
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/zszk.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/knowl/knowl.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/uploadpanel/jquery-uploadpanel.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
<style type="text/css">
pre {
white-space: pre-wrap;
word-wrap: break-word;
}
</style>
</head>
<body>
	
	<div style="height: 20px;">
	</div>
	
	<div style="margin: 0 auto;width:800px;"><!-- question desc container -->
		<div style="padding:20px 60px;position: relative;border: 2px solid #00FFFF;"><!-- question desc -->
			<h1>
				<i style="width:24px;height: 24px;position: absolute;left: 30px;background-repeat: no-repeat;background-image:url('${ctxPath}/styles/images/question.png');"></i>
				<span style="font-size: 18px;font-weight: 400;">${kdQuestion.subject}</span>
			</h1>
			<div style="height: 22px;font-size: 12px;color: #999;">
				<span style="float: right;text-align: right;">${kdQuestion.createTime}</span>
				<span style="float: right;">&nbsp;|&nbsp;</span>
				<span class="collect" style="float: right;cursor:pointer;">收藏</span>
				<span>浏览次数：${kdQuestion.viewTimes}</span>
			</div>
			<div style="margin-top: 8px;font-size: 12px;color: #999;">
				<span>标签：${kdQuestion.tags}</span>
			</div>
			<div style="margin-top: 8px;font-size: 12px;color: #999;">
				<span>
					悬赏：<span style="color:#ee5e0f;"><i style="background-image: url('${ctxPath}/styles/images/knowl/jb.png')"></i>${kdQuestion.rewardScore}</span>
				</span>
			</div>
			<div id="questionDesc" style="margin-top: 5px;font-size: 14px;">
				<pre>${kdQuestion.question}</pre>
			</div>
			
			<c:if test="${kdQuestion.createBy==curUser.userId&&kdQuestion.status=='UNSOLVED'}">
			<div style="margin-top: 10px;margin-bottom: 5px;line-height: 20px;font-size: 14px;">
				<div style="text-align: right;">
					<a id="addDetail" style="cursor:pointer;margin:0px 15px;text-decoration: none;color: #2d64b3;"><i style="margin-right:5px;vertical-align: middle;background-image: url('${ctxPath}/styles/icons/edit.png');background-repeat: no-repeat;"></i>补充问题</a>
					<a id="closeQuestion" style="cursor:pointer;margin:0px 15px;text-decoration: none;color: #2d64b3;"><i style="margin-right:5px;height: 16px;width: 16px;vertical-align: middle;background-image: url('/jsaas/styles/icons/remove.gif');background-repeat: no-repeat;"></i>关闭问题</a>
				</div>
				<div id="questionBox" style="display:none;border:1px solid #4ec83b;margin-top:5px;padding: 14px 16px;padding-bottom: 0px;">
					<div style="margin-bottom: 20px;"><form id="detail-form"><ui:UEditor height="200" name="questionDetail" id="questionDetail"></ui:UEditor></form></div>
					<div style="position:relative;padding-bottom: 10px;line-height: 24px;height: 36px;">
						<a id="updateDetail" style="line-height: 36px; color: white; background-color: rgb(78, 200, 59);display: block;float:right;border-bottom: 1px solid; border-right: 1px solid; cursor: pointer;  font-size: 14px; height: 36px; padding: 0px 38px; text-align: center; text-decoration: none;">确定</a>
					</div>
				</div>
			</div>
			</c:if>
			
			<c:if test="${fn:length(attach)>0 }">
				<div><span style="display: block;float: left;color: #999;">附件：</span>
					<ul style="padding:0px;list-style-image: none;list-style-position: outside;list-style-type: none;overflow-x: hidden;overflow-y: hidden;">
						<c:forEach items="${attach}" var="a">
							<li style="float:left;margin-left: 5px;margin-bottom: 1px;"><a style="display: block;text-decoration: none;color: #000;height: 22px;padding: 2px 4px;background-color: #E6E6FA" href="${ctxPath}/sys/core/file/previewFile.do?fileId=${a.fileId}">${a.fileName}</a></li>
						</c:forEach>
					</ul>
				</div>
			</c:if>
			
			<c:if test="${(kdQuestion.status=='UNSOLVED'&&kdQuestion.isSelf!='YES')&&isHasSlefAnswer=='NO'}">
				<c:if test="${kdQuestion.replierId==null||(kdQuestion.replierId!=null&&kdQuestion.replierId==curUser.userId)}">
					<span id="answer-bar" style="color: rgb(45, 100, 182); cursor: pointer; font-size: 13px; vertical-align: middle; margin-bottom: 5px; margin-top: 15px;"> <c:choose>
							<c:when test="${fn:length(kdQuestion.kdQuestionAnswers)==0}">
									我要回答
								</c:when>
							<c:otherwise>
									我有更好的答案
								</c:otherwise>
						</c:choose> <i id="arrow" style="display:inline-block;height:16px;width:16px;background-image: url('${ctxPath}/styles/icons/up.png');"></i>
					</span>
					<div id="answer-window" style="">
						<div style="margin-bottom: 21px;">
							<form id="answer-form">
								<ui:UEditor height="200" name="replyContent" id="replyContent"></ui:UEditor>
							</form>
						</div>
						<div style="height: 36px;">
							<a id="response" style="line-height: 36px; color: white; background-color: rgb(78, 200, 59); display: block; border-bottom: 1px solid; border-right: 1px solid; cursor: pointer; float: right; font-size: 14px; height: 36px; padding: 0px 38px; text-align: center; text-decoration: none;">提交回答</a>
						</div>
					</div>
				</c:if>
			</c:if>
		</div>
		
		<c:if test="${kdQuestion.status!='UNSOLVED'}">
			<c:if test="${kdQuestion.bestAnswer!=null}">
				<div style="margin-top:20px;position: relative;background-color: #E1FFFF;border: 2px solid #3CB371;"><!-- recommend answer container -->
					<div style="height:40px;margin:0px 60px;"><!-- answer header -->
						<span style="float:right;margin-top: 50px;font-size: 12px;color: #999;"><c:choose><c:when test="${kdQuestion.bestAnswer.updateTime==null}">${kdQuestion.bestAnswer.createTime}</c:when><c:otherwise>${kdQuestion.bestAnswer.updateTime}</c:otherwise></c:choose></span>
						<span style="float: left;height:40px;width:27px;margin-left: -30px;background-repeat: no-repeat;background-image: url('${ctxPath}/styles/images/recommend.png');margin-top:-8px;"></span>
						<span style="float: left;font-size: 16px;margin-top: 16px;margin-left: 0px;font-weight: normal;">提问者采纳</span>
					</div>
					<div style="font-size: 14px;margin-bottom: 20px;margin-left: 60px;margin-right: 60px;">
						<div style="font-size: 12px;height: 24px;margin-top: 10px;color: #999;"><rxc:userLabel userId="${kdQuestion.bestAnswer.authorId}"/></div><!-- answer user -->
						<div style="font-size: 14px;"><!-- answer -->
							<pre style="line-height: 24px;font-size: 14px;margin-bottom: 10px;">${kdQuestion.bestAnswer.replyContent}
							</pre>
						</div>
					</div>
				</div>
			</c:if>	
		</c:if>
		
		<c:if test="${(kdQuestion.bestAnswer!=null&&fn:length(kdQuestion.kdQuestionAnswers)-1>0)||(kdQuestion.bestAnswer==null&&fn:length(kdQuestion.kdQuestionAnswers)>0)}">
			<div style="margin-top:20px;margin-bottom: 5px;font-size: 14px; line-height: 24px;border: 2px solid #3CB371;"><!-- other answer -->
				<div style="font-size: 14px;height: 24px;line-height: 24px;padding-top: 25px;padding-right: 60px;padding-left: 60px;"><!-- header -->
					<!-- <span style="font-size: 12px;float: right;">排序</span> -->
					<h2 style="margin:0;font-size: 16px;font-weight: normal;font-stretch: normal;font-style: normal;font-variant: normal;">
						<i style="margin-top:0px;height:24px;display:inline-block;height: 20px;width: 20px;margin-left:-34px;margin-right: 14px;background-image:url('${ctxPath}/styles/images/other.png');background-repeat: no-repeat;font-stretch: normal;font-style: normal;font-variant: normal;font-weight: normal;"></i>
						<c:choose>
							<c:when test="${kdQuestion.createBy==curUser.userId&&kdQuestion.status=='UNSOLVED'}">
								<span style="font-size: 16px;line-height:24px;padding-left: 0px;color:#36a803;font-weight: 400;">您的提问共收到<span style="color:#ea3f20;">&nbsp;${fn:length(kdQuestion.kdQuestionAnswers)}条&nbsp;</span>回答，采纳一条您满意的答案吧！</span>
							</c:when>
							<c:otherwise>
								<c:choose><c:when test="${kdQuestion.status!='UNSOLVED'}">其他${fn:length(kdQuestion.kdQuestionAnswers)-1}</c:when><c:otherwise>${fn:length(kdQuestion.kdQuestionAnswers)}</c:otherwise></c:choose>条回答
							</c:otherwise>
						</c:choose>
					</h2>
				</div>
				<div style="font-size: 14px;padding: 0px 60px;"><!-- answer -->
				
				<c:forEach items="${kdQuestion.kdQuestionAnswers}" var="answer" varStatus="stauts">
					<c:if test="${answer.isBest!='YES'}">
						<div style="padding-top: 20px;padding-bottom: 20px;border-bottom-color: rgb(233, 233, 233);border-bottom-style: solid;border-bottom-width: 1px;"> 
							<div style="font-size: 12px; margin-bottom: 5px;color: #7b7b7b;">
								<span style="display: block;float: right;"><c:choose><c:when test="${answer.updateTime==null}">${answer.createTime}</c:when><c:otherwise>${answer.updateTime}</c:otherwise></c:choose></span>
								<span><rxc:userLabel userId="${answer.authorId}"/></span>
							</div>
							<div class="answerContent" style="font-size: 14px;margin-top: 3px;">
								<div style="margin-bottom: 20px;">
									<span style="word-wrap: break-word;">${answer.replyContent}</span>
								</div>
							</div>
							<c:if test="${kdQuestion.createBy==curUser.userId}">
								<c:if test="${kdQuestion.status=='UNSOLVED'}">
								<div style="font-size:14px;line-height:24px;margin-top:10px">
									<p style="position: relative;">
										<input id="answerId" name="answerId" value="${answer.answerId}" class="mini-hidden"/>
										<a id="collectQuestion" href="#" style="font-size:15px;border:1px solid #b2e5ab;color:#48c335;position:absolute;right:0px;bottom:-14px;display: block;text-decoration: none;padding: 6px 18px;">采纳答案</a>
									</p>
								</div>
								</c:if>
							</c:if>
							<c:if test="${answer.authorId==curUser.userId&&kdQuestion.status=='UNSOLVED'}">
								<div style="font-size:14px;line-height:24px;margin-top:10px">
									<p style="position: relative;">
										<a class="delAnswer" href="#"  style="font-size:15px;border:1px solid #b2e5ab;color:#48c335;position:absolute;right:0px;bottom:-14px;display: inline-block;text-decoration: none;padding: 6px 18px;">删除我的回答</a>
										<a class="editAnswer" href="#" style="font-size:15px;border:1px solid #b2e5ab;color:#48c335;position:absolute;right:130px;bottom:-14px;display: inline-block;text-decoration: none;padding: 6px 18px;">完善我的答案</a>
									</p>
									<div id="edit-window" style="display:none;position:relative;padding-top:20px;">
										<div style="margin-bottom: 8px;">
											<form id="update-answer-form">
												<ui:UEditor height="200" name="updateContent" id="updateContent"></ui:UEditor>
											</form>
										</div>
										<div style="height:36px;padding-bottom: 8px;">
											<a id="updateAnswer" style=" clear:both;line-height: 36px; color: white; background-color: rgb(78, 200, 59); display: block;float:right;border-bottom: 1px solid; border-right: 1px solid; cursor: pointer;  font-size: 14px; height: 36px; padding: 0px 38px; text-align: center; text-decoration: none;">提交修改</a>
										</div>
									</div>
								</div>
							</c:if>
						</div>
					</c:if>	
				</c:forEach>
					
				</div>
			</div>
		</c:if>

	</div>
	<rx:detailScript baseUrl="kms/core/kdQuestion" formId="form1" />
	<script type="text/javascript">
		$(function(){
			$("#answer-bar").click(function(){
				if($("#answer-window").css("display")=="none"){
					$("#arrow").css("background-image","url('${ctxPath}/styles/icons/up.png')");
					$("#answer-window").css("display","");
				}else{
					$("#arrow").css("background-image","url('${ctxPath}/styles/icons/down.png')");
					$("#answer-window").css("display","none");
					ue.setContent();
				}
			});
		});
		
		$("#collectQuestion").click(function(){
			//window.loaction.href="${ctxPath}/kms/core/kdQuestionAnswer/collectAnswer.do?answerId=${answer.answerId}";
			_SubmitJson({
				url:__rootPath+"/kms/core/kdQuestionAnswer/collectAnswer.do",
				data:{
					answerId:mini.get("answerId").getValue()
				},
				showMsg:false,
				method:'POST',
				success:function(result){
					alert("采纳成功！");
					location.reload(true);
				}
			});
			
		});
		
		
		$(".collect").click(function(){
			var userId='${curUser.userId}';
			var queId='${kdQuestion.queId}';
			if(userId==null||userId=='')
				alert("请先登录！");
			else{
				_SubmitJson({
					url:__rootPath+"/kms/core/kdQuestion/collectQuestion.do",
					data:{
						queId:queId,
						uId:userId
					},
					showMsg:false,
					method:'POST',
					success:function(result){
						alert(result.message);
					}
				});
			}
		});
		
		$("#addDetail").click(function(){
			var show=$("#questionBox").css('display');
			if(show=='none'){
				$("#questionBox").css('display','');
				var questionDetail=$("#questionDesc").find("pre").html();
				UE.getEditor("questionDetail").setContent(questionDetail);
			}else{
				$("#questionBox").css('display','none');
				UE.getEditor("questionDetail").setContent("");
			}
		});
		
		$("#updateDetail").click(function(){  
			var formJson=_GetFormJson("detail-form");
			var queId='${kdQuestion.queId}';
			var content=jQuery.parseJSON(mini.encode(formJson))['questionDetail'];
			if(content.trim()=='')return;
			_SubmitJson({
				url:"${ctxPath}/kms/core/kdQuestion/updateQuestionDetail.do",
				data:{
					content:mini.encode(formJson),
					queId:queId
				},
				method:'POST',
				success:function(){
					location.reload(true);
				}
			});
		});
		
		$("#closeQuestion").click(function(){
			if (!confirm("确定关闭你的问题？")) return;
			var queId='${kdQuestion.queId}';
			_SubmitJson({
				url:"${ctxPath}/kms/core/kdQuestion/del.do",
				data:{
					ids:queId
				},
				method:'POST',
				success:function(){
					window.location.href="${ctxPath}/kms/core/kdQuestion/index.do";
				}
			});
		});
		
		$(".editAnswer").click(function(){
			var show=$("#edit-window").css('display');
			if(show=='none'){
				$("#edit-window").css('display','');
				$(this).html("取消修改");
				var answerContent=$(this).parent().parent().parent().find(".answerContent").find("span").html();
				UE.getEditor("updateContent").setContent(answerContent);
			}
			else{
				$("#edit-window").css('display','none');
				$(this).html("完善我的回答");
				UE.getEditor("updateContent").setContent("");
			}
		});
		
		$(".delAnswer").click(function(){
			if (!confirm("确定删除你的答案？")) return;
			var queId='${kdQuestion.queId}';
			_SubmitJson({
				url:"${ctxPath}/kms/core/kdQuestionAnswer/delAnswer.do",
				data:{
					queId:queId
				},
				method:'POST',
				success:function(){
					location.reload(true);
				}
			});
		});
		
		$("#updateAnswer").click(function(){
			var formJson=_GetFormJson("update-answer-form");
			var queId='${kdQuestion.queId}';
			var content=jQuery.parseJSON(mini.encode(formJson))['updateContent'];
			if(content.trim()=='')return;
			_SubmitJson({
				url:"${ctxPath}/kms/core/kdQuestionAnswer/updateAnswer.do",
				data:{
					content:mini.encode(formJson),
					queId:queId
				},
				method:'POST',
				success:function(){
					location.reload(true);
				}
			});
		});
		
		$("#response").click(function(){
			var queId='${kdQuestion.queId}';
			var content = _GetFormJson("answer-form");
			var data=content['replyContent'];
			_SubmitJson({
				url : __rootPath + "/kms/core/kdQuestionAnswer/saveAnswer.do",
				data : {
					data : data,
					queId:queId,
				},
				method : 'POST',
				success : function() {
					alert("成功回答！");
					location.reload(true);
				}
			});
		});
		
	</script>
</body>
</html>