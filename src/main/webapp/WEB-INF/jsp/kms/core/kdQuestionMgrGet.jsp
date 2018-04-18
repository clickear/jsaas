<%-- 
    Document   : [KdQuestion]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>[KdQuestion]明细</title>
<%@include file="/commons/get.jsp"%>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/uploadpanel/jquery-uploadpanel.js" type="text/javascript"></script>

<style type="text/css">
	pre {
		white-space: pre-wrap;
		word-wrap: break-word;
	}
	
	.shadowBox90>h1{
		font-size: 18px;
		text-align: center;
	}
	
</style>
</head>
<body>
<%-- 	<rx:toolbar toolbarId="toolbar1" ></rx:toolbar> --%>
	<div style="margin: 0 auto;width:800px;padding-top: 20px;">
		<div class="shadowBox90" style="position: relative;">
			<h1>${kdQuestion.subject}</h1>
			<div style="height: 22px;font-size: 12px;color: #999;">
				<span style="float: right;text-align: right;">${kdQuestion.createTime}</span>
				<span style="float: right;">&nbsp;|&nbsp;</span>
				<span style="float: right;">收藏</span>
				<span>浏览次数：${kdQuestion.viewTimes}</span>
			</div>
			<div style="margin-top: 8px;font-size: 12px;color: #999;">
				<span>标签：${kdQuestion.tags}</span>
			</div>
			<div style="margin-top: 5px;font-size: 14px;">
				<pre>${kdQuestion.question}</pre>
			</div>
			<c:if test="${fn:length(attach)>0 }">
				<div><span style="display: block;float: left;color: #999;">附件：</span>
					<ul style="padding:0px;list-style-image: none;list-style-position: outside;list-style-type: none;overflow-x: hidden;overflow-y: hidden;">
						<c:forEach items="${attach}" var="a">
							<li style="float:left;margin-left: 5px;margin-bottom: 1px;"><a style="display: block;text-decoration: none;color: #000;height: 22px;padding: 2px 4px;background-color: #E6E6FA" href="${ctxPath}/sys/core/file/previewFile.do?fileId=${a.fileId}">${a.fileName}</a></li>
						</c:forEach>
					</ul>
				</div>
			</c:if>
		</div>
		
		<c:if test="${kdQuestion.status!='UNSOLVED'}">
			<c:if test="${kdQuestion.bestAnswer!=null}">
				<div style="margin-top:20px;position: relative;background-color: #E1FFFF;border: 2px solid #3CB371;"><!-- recommend answer container -->
					<div style="height:40px;margin:0px 60px;"><!-- answer header -->
						<span style="float:right;margin-top: 50px;font-size: 12px;color: #999;">
							<c:choose>
								<c:when test="${kdQuestion.bestAnswer.updateTime==null}">${kdQuestion.bestAnswer.createTime}</c:when>
								<c:otherwise>${kdQuestion.bestAnswer.updateTime}</c:otherwise>
							</c:choose>
						</span>
						<span style="float: left;height:40px;width:27px;margin-left: -30px;background-repeat: no-repeat;background-image: url('${ctxPath}/styles/images/recommend.png');margin-top:-8px;"></span>
						<span style="float: left;font-size: 16px;margin-top: 16px;margin-left: 0px;font-weight: normal;">提问者采纳</span>
					</div>
					<div style="font-size: 14px;margin-bottom: 20px;margin-left: 60px;margin-right: 60px;">
						<div style="font-size: 12px;height: 24px;margin-top: 10px;color: #999;"><rxc:userLabel userId="${kdQuestion.bestAnswer.authorId}"/></div><!-- answer user -->
						<div style="font-size: 14px;"><!-- answer -->
							<pre style="line-height: 24px;font-size: 14px;margin-bottom: 10px;">${kdQuestion.bestAnswer.replyContent}</pre>
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
						<i style="margin-top:-3px;height:24px;display:inline-block;height: 20px;width: 20px;margin-left:-34px;margin-right: 14px;background-image:url('${ctxPath}/styles/images/other.png');background-repeat: no-repeat;font-stretch: normal;font-style: normal;font-variant: normal;font-weight: normal;"></i>
						<c:choose>
							<c:when test="${kdQuestion.status!='UNSOLVED'}">其他${fn:length(kdQuestion.kdQuestionAnswers)-1}</c:when>
							<c:otherwise>${fn:length(kdQuestion.kdQuestionAnswers)}</c:otherwise>
						</c:choose>条回答
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
								<div style="font-size: 14px;margin-top: 3px;">
									<div style="margin-bottom: 20px;">
										<span style="word-wrap: break-word;">${answer.replyContent}</span>
									</div>
								</div>
							</div>
						</c:if>	
					</c:forEach>
				</div>
			</div>
		</c:if>

	</div>
	<rx:detailScript baseUrl="kms/core/kdQuestion" formId="form1" entityName="com.redxun.kms.core.entity.KdQuestion"/>
	<script type="text/javascript">
		$(function(){
			$("#answer-bar").click(function(){
				if($("#answer-window").css("display")=="none"){
					$("#arrow").css("background-image","url('${ctxPath}/styles/icons/up.png')");
					$("#answer-window").css("display","");
				}else{
					$("#arrow").css("background-image","url('${ctxPath}/styles/icons/down.png')");
					$("#answer-window").css("display","none");
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
		
	    function preRecord() {
	        //调用父窗口获得前一记录的PKID
	        var pkId = top['com.redxun.kms.core.entity.KdQuestion'].preRecord();
	        if (pkId == 0){
	            return;
	        }
	        window.location="${ctxPath}/kms/core/kdQuestion/mgrGet.do?pkId="+pkId;
	    }

	    function nextRecord() {
	        //调用父窗口获得下条一记录
	        var pkId = top['com.redxun.kms.core.entity.KdQuestion'].nextRecord();
	        if (pkId == 0){
	            return;
	        }
	        window.location="${ctxPath}/kms/core/kdQuestion/mgrGet.do?pkId="+pkId;
	    }
	</script>
</body>
</html>