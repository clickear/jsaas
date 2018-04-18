<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>个人中心主页</title>
<%@include file="/commons/get.jsp"%>
<link href="${ctxPath}/styles/css/personalindex.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/zszk.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/knowl/knowl.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
<link href="${ctxPath}/scripts/kms/shoufengqin/font-awesome.min.css" rel="stylesheet" />
<link href="${ctxPath}/scripts/kms/shoufengqin/shoufengqinstyle.css" rel="stylesheet" media="screen" type="text/css" />
<script src="${ctxPath}/scripts/kms/shoufengqin/index.js" type="text/javascript"></script>

</head>
<body>
	<ul style="float: left; margin-left: 50px; padding: 0;z-index: 999;margin-top: 100px;" id="accordion" class="accordion">
		<li>
			<div class="link">
				<i class="fa"></i>个人资料<i class="fa fa-chevron-down"></i>
			</div>
			<ul class="submenu" style="margin: 0; padding: 0;">
				<li><a href="${ctxPath}/kms/core/kdUser/personalGet.do?i=4&userId=${userId}">我的个人资料</a></li>
			</ul>
		</li>
		<li>
			<div class="link">
				<i class="fa"></i>我的知识文库<i class="fa fa-chevron-down"></i>
			</div>
			<ul class="submenu" style="margin: 0; padding: 0;">
				<li><a href="${ctxPath}/kms/core/kdDoc/personal.do?i=4">我的知识文库</a></li>
				<li><a href="${ctxPath}/kms/core/kdQuestion/personal.do?i=4&tab=index&type=ALL">我的知识问答</a></li>
				<li><a href="${ctxPath}/kms/core/kdDoc/mapPersonal.do?i=4">我的知识地图</a></li>
				<li><a href="#" onclick="todo()">我的知识积分</a></li>
			</ul>
		</li>
	</ul>
	<div id="banner"></div>
	<div id="main-container">
		<div id="main">
			<div id="header-menu">
				<ul id="header-tabs">
					<li class="tab-index"><a href="#" target="_self"> <span>等我回答</span>
					</a></li>
					<li class="tab-ask"><a href="#" target="_self"> <span>我的提问</span>
					</a></li>
					<li class="tab-answer"><a href="#" target="_self"> <span>我的回答</span>
					</a></li>
					<li class="tab-collection"><a href="#" target="_self"> <span>我的收藏</span>
					</a></li>
				</ul>
			</div>

			<c:if test="${tab=='index'}">
				<div class="tabs-index-content">
					<div class="index-content-filter">
						<div class="tags-operation">
							<form class="form-keyword">
								<input class="form-keyword-input" autocomplete="off" placeholder="请输入关键字" /> <a href="#">筛选</a>
							</form>

						</div>
					</div>
				</div>
			</c:if>

			<c:if test="${tab=='ask'}">
				<div class="ask-type-filter">
					<div class="ask-filter">
						<div id="ask-status" name="ask-status" class="mini-radiobuttonlist filter-choose" required="true" data="[{id:'ALL',text:'全部'},{id:'UNSOLVED',text:'待解决'},{id:'SOLVED',text:'已解决'}]" value="${type}" onvaluechanged="change"></div>
					</div>
				</div>
			</c:if>

			<c:if test="${tab=='answer'}">
				<div class="answer-type-filter">
					<div class="answer-filter">
						<div id="answer-status" name="answer-status" class="mini-radiobuttonlist filter-choose" required="true" data="[{id:'ALL',text:'全部'},{id:'YES',text:'被采纳'},{id:'NO',text:'未采纳'}]" value="${type}" onvaluechanged="change"></div>
					</div>
				</div>
			</c:if>

			<div class="question-list-container">
				<table width="821px;" style="">
					<c:if test="${tab=='index'}">
						<c:forEach items="${askMeQuestion}" var="question" varStatus="status">
							<tr>
								<td>
									<!-- <div class="user">
										<a href="#"> <img src="/jsaas/styles/images/te.png" width="40" /></a>
									</div> -->
									<div class="ask-me">
										<div class="ask-me-info">
											<div class="ask-me-info-list">
												<div class="ask-me-subject-box">
													<a href="${ctxPath}/kms/core/kdQuestion/get.do?pkId=${question.queId}" target="_blank">${question.subject}</a>
												</div>
												<div class="detail-list">
													<div class="detail">${question.createTime}</div>
													<div class="detail">${question.tags}</div>
												</div>
											</div>
											<div class="ask-status">
												<span class="ask-reward"> <i></i>100
												</span> <span class="ask-no-answer">${fn:length(question.kdQuestionAnswers)} &nbsp;回答</span>
											</div>
										</div>
									</div>
								</td>
							</tr>
						</c:forEach>

						<!-- 	<tr>
							<td>
								<div class="user">
									<a href="#"> <img src="/jsaas/styles/images/te.png" width="46" /></a>
								</div>
								<div class="ask-me">
									<div class="ask-me-info">
										<div class="ask-me-info-list">
											<div class="ask-me-subject-box">
												<a>主题</a>
											</div>
											<div class="detail-list">
												<div class="detail">时间</div>
												<div class="detail">标签</div>
											</div>
										</div>
										<div class="ask-status">
											<span class="ask-reward"> <i></i>100</span> 
											<span class="ask-no-answer">1 &nbsp;回答</span>
										</div>
									</div>
								</div>
							</td>
						</tr> -->
					</c:if>

					<c:if test="${tab=='ask'}">
						<c:forEach items="${questionList}" var="question" varStatus="status">
							<tr>
								<td>
									<div class="question-info">
										<div class="question-info-box">
											<div class="question-content">
												<div class="subject">
													<a href="${ctxPath}/kms/core/kdQuestion/get.do?pkId=${question.queId}" target="_blank">${question.subject}</a>
												</div>
												<div class="question-info-list">
													<div class="time">${question.createTime}</div>
													<div class="replycount">回答：${question.replyCounts}</div>
													<div class="viewtime">${question.viewTimes}人看过</div>
													<div class="tag">${question.tags}</div>
												</div>
											</div>
										</div>
										<c:if test="${question.status=='UNSOLVED'}">
											<div class="handle">
												<c:choose>
													<c:when test="${fn:length(question.kdQuestionAnswers)<=0 }">
														<i style="background-repeat: no-repeat;background-image: url('${ctxPath}/styles/icons/icon-unsolved.png');"></i>
													</c:when>
													<c:otherwise>
														<a href="${ctxPath}/kms/core/kdQuestion/get.do?pkId=${question.queId}" target="_blank">查看并处理</a>
													</c:otherwise>
												</c:choose>
											</div>
										</c:if>
										<c:if test="${question.status=='SOLVED'}">
											<div class="handle">
												<i style="background-repeat: no-repeat;background-image: url('${ctxPath}/styles/icons/icon-right.png');"></i>
											</div>
										</c:if>
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:if>

					<c:if test="${tab=='answer'}">
						<c:forEach items="${answerList}" var="answer" varStatus="status">
							<tr>
								<td>
									<div class="answer-info">
										<div class="answer-info-box">
											<div class="answer-title">
												<a href="${ctxPath}/kms/core/kdQuestion/get.do?pkId=${answer.kdQuestion.queId}" target="_blank">${answer.kdQuestion.subject}</a>
											</div>
											<div class="answer-content-box">
												<span> ${answer.replyContent} </span>
											</div>
											<div class="answer-tags">
												<span>${answer.kdQuestion.createTime}</span> <span>回答：${answer.kdQuestion.replyCounts}</span> <span>提问者：<rxc:userLabel userId="${answer.kdQuestion.createBy}" /></span> <span>${answer.kdQuestion.tags}</span>
											</div>
										</div>
										<div class="answer-status-box">
											<div class="answer-status">
												<c:choose>
													<c:when test="${answer.kdQuestion.status=='UNSOLVED'}">
														<i style="background-image: url('${ctxPath}/styles/icons/icon-unsolved.png')"></i>
													</c:when>
													<c:otherwise>
														<c:if test="${answer.isBest=='NO'}">
															<i style="background-image: url('${ctxPath}/styles/icons/icon-noadopt.png')"></i>
														</c:if>
														<c:if test="${answer.isBest=='YES'}">
															<i style="background-image: url('${ctxPath}/styles/icons/icon-adopt.png')"></i>
														</c:if>
													</c:otherwise>
												</c:choose>


											</div>
										</div>
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					
					<c:if test="${tab=='collection'}">
						<c:forEach items="${collectQuestion}" var="question" varStatus="status">
							<tr>
								<td>
									<div class="question-info">
										<div class="question-info-box">
											<div class="question-content">
												<div class="subject">
													<a href="${ctxPath}/kms/core/kdQuestion/get.do?pkId=${question.queId}" target="_blank">${question.subject}</a>
												</div>
												<div class="question-info-list">
													<div class="time">${question.createTime}</div>
													<div class="replycount">回答：${question.replyCounts}</div>
													<div class="viewtime">${question.viewTimes}人看过</div>
													<div class="tag">${question.tags}</div>
												</div>
											</div>
										</div>
										<c:if test="${question.status=='UNSOLVED'}">
											<div class="handle">
												<c:choose>
													<c:when test="${fn:length(question.kdQuestionAnswers)<=0 }">
														<i style="background-repeat: no-repeat;background-image: url('${ctxPath}/styles/icons/icon-unsolved.png');"></i>
													</c:when>
													<c:otherwise>
														<a href="${ctxPath}/kms/core/kdQuestion/get.do?pkId=${question.queId}" target="_blank">查看并处理</a>
													</c:otherwise>
												</c:choose>
											</div>
										</c:if>
										<c:if test="${question.status=='SOLVED'}">
											<div class="handle">
												<i style="background-repeat: no-repeat;background-image: url('${ctxPath}/styles/icons/icon-right.png');"></i>
											</div>
										</c:if>
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:if>

				</table>
				<div class="null">
					<c:if test="${questionList!=null&&fn:length(questionList)<=0 }">
						暂时没有这类提问。
					</c:if>
					<c:if test="${answerList!=null&&fn:length(answerList)<=0 }">
						暂时没有这类回答。
					</c:if>
					<c:if test="${collectQuestion!=null&&fn:length(collectQuestion)<=0 }">
						暂时没有收藏。
					</c:if>
				</div>
			</div>
		</div>

		<div class="user-info-box">
			<div class="user-info">
				<div class="user-info-list" style="height: 100%; overflow: hidden;">
					<div class="user-i">
						<a href="#"> <img class="user-link" src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${curUser.headId}" alt="" />
						</a>
						<p class="user-fullname">${curUser.fullname}</p>
						<div class="level">等级:${curUser.grade}</div>
					</div>
					<div class="user-fields">
						<div class="user-border-line">
							<div class="user-item">
								<p class="data">${curUser.point}</p>
								<p class="word">积分</p>
							</div>
							<span class="user-pipe">|</span>
							<div class="user-item">
								<p class="data">915</p>
								<p class="word">回答数</p>
							</div>
							<span class="user-pipe">|</span>
							<div class="user-item">
								<p class="data">915</p>
								<p class="word">提问数</p>
							</div>
						</div>
						<!-- <div class="user-item">
							<p class="data">915</p>
							<p class="word">回答数</p>
						</div>
						<span class="user-pipe">|</span>
						<div class="user-item">
							<p class="data">915</p>
							<p class="word">回答数</p>
						</div>
						<span class="user-pipe">|</span>
						<div class="user-item">
							<p class="data">915</p>
							<p class="word">回答数</p>
						</div> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			var tab = '${tab}';
			if (tab == 'index') {
				$("#header-tabs").find(".tab-" + tab).find("a").addClass("active");
			} else if (tab == 'ask') {
				$("#header-tabs").find(".tab-" + tab).find("a").addClass("active");
			} else if (tab == 'answer') {
				$("#header-tabs").find(".tab-" + tab).find("a").addClass("active");
			} else if (tab == 'collection') {
				$("#header-tabs").find(".tab-" + tab).find("a").addClass("active");
			}
		});

		$("#header-tabs").find("li").each(function() {
			$(this).on("click", function() {
				var tab = $(this).attr("class");
				if (tab == 'tab-index') {
					location.href = "${ctxPath}/kms/core/kdQuestion/personal.do?tab=index&i=4";
				} else if (tab == 'tab-ask') {
					location.href = "${ctxPath}/kms/core/kdQuestion/personal.do?tab=ask&type=ALL&i=4";
				} else if (tab == 'tab-answer') {
					location.href = "${ctxPath}/kms/core/kdQuestion/personal.do?tab=answer&type=ALL&i=4";
				} else if (tab == 'tab-collection') {
					location.href = "${ctxPath}/kms/core/kdQuestion/personal.do?tab=collection&i=4";
				}
			});
		});

		function change() {
			var tab = '${tab}';
			var type = "";
			if (tab == 'answer' || tab == 'ask')
				type = mini.get(tab + "-status").getValue();
			window.location.href = "${ctxPath}/kms/core/kdQuestion/refreshList.do?i=4&tab=" + tab + "&type=" + type;
		}

		$(".user-fullname").click(function() {
			window.location.href = "${ctxPath}/kms/core/kdUser/personalGet.do?i=4&userId=" + "${curUser.user.userId}";
		});

		$(".user-link").click(function() {
			window.location.href = "${ctxPath}/kms/core/kdUser/personalGet.do?i=4&userId=" + "${curUser.user.userId}";
		});

		/* function refreshList(){
			$(".question-list-container").find("table").empty();
			$(".null").remove();
			var tab='${tab}';
			var type="";
			if(tab=='answer'||tab=='ask')
				type=mini.get(tab+"-status").getValue();
			_SubmitJson({
				url:__rootPath+"/kms/core/kdQuestion/refreshList.do",
				data:{
					tab:tab,
					type:type
				},
				showMsg:false,
				method:'POST',
				success:function(result){
					var data=result.data;
					var table=$(".question-list-container").find("table");
					if(tab=='ask'){
						for (var i = 0; i < data.length; i++) {
							var li="<tr><td><div class='question-info'><div class='question-info-box'><div class='question-content'><div class='subject'><a href='${ctxPath}/kms/core/kdQuestion/get.do?pkId="
										+data[i].queId+"' target='_blank'>"
										+data[i].subject+"</a></div><div class='question-info-list'><div class='time'>"
										+data[i].createTime+"</div><div class='replycount'>回答："+data[i].replyCounts+"</div><div class='viewtime'>"
										+data[i].viewTimes+"人看过</div><div class='tag'>"+data[i].tags+"</div></div></div></div>";
							if(data[i].status=='UNSOLVED'){
								li+="<div class='handle'>";
								if(data[i].replyCounts<=0)
									li+="<i style='background-repeat: no-repeat;background-image: url(/jsaas/styles/icons/icon-unsolved.png);'></i>";
								else
									li+="<a href='${ctxPath}/kms/core/kdQuestion/get.do?pkId="+data[i].queId+"' target='_blank'>查看并处理</a>";
								li+="</div>";	
							}
							else if(data[i].status=='SOLVED'){
								li+="<div class='handle'><i style='background-repeat: no-repeat;background-image: url(/jsaas/styles/icons/icon-right.png);'></i></div>";
							}
							
							li+="</div></td></tr>";
							var tr=$(li);
							table.append(tr);
						}
						
						if(data.length<=0){
							var nullbox="<div class='null'>暂时没有这类提问。</div>";
							table.after(nullbox);
						}
					}
					else if(tab=='answer'){
						for (var i = 0; i < data.length; i++) {
							var question=data[i].kdQuestion;
							var li="<tr><td><div class='answer-info'><div class='answer-info-box'><div class='answer-title'><a href='${ctxPath}/kms/core/kdQuestion/get.do?pkId="
										+question.queId+"' target='_blank'>"
										+question.subject+"</a></div><div class='answer-content-box'><span>"
										+data[i].replyContent+"</span></div><div class='answer-tags'><span>"
										+question.createTime+"</span><span>回答："
										+question.replyCounts+"</span><span>提问者：zwj</span><span>"
										+question.tags+"</span></div></div><div class='answer-status-box'><div class='answer-status'>";
							if(question.status=='UNSOLVED'){
								li+="<i style='background-image: url(/jsaas/styles/icons/icon-unsolved.png)'></i>";
							}
							else{
								if(data[i].isBest=='NO'){
									li+="<i style='background-image: url(/jsaas/styles/icons/icon-noadopt.png)'></i>";
								}
								if(data[i].isBest=='YES'){
									li+="<i style='background-image: url(/jsaas/styles/icons/icon-adopt.png)'></i>";
								}
							}
							li+="</div></div></div></td></tr>";
							var tr=$(li);
							table.append(tr);
						}
						if(data.length<=0){
							var nullbox="<div class='null'>暂时没有这类回答。</div>";
							table.after(nullbox);
						}
					}
				}
			});
		} */

		function nodone() {
			alert("敬请期待！");
		}

		$(".ask-btn").click(function() {
			nodone();
		});

		$("#search").click(function() {
			nodone();
			return false;
		});
	</script>
</body>

</html>