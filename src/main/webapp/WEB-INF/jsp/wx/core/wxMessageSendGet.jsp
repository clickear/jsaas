
<%-- 
    Document   : [WX_MESSAGE_SEND]明细页
    Created on : 2017-07-17 17:58:08
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <title>消息明细</title>
    <%@include file="/commons/get.jsp" %>
    <script src='${ctxPath}/scripts/qtip2/jquery.qtip.js'></script>
	<link href="${ctxPath}/scripts/qtip2/jquery.qtip.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
						<tr>
							<th>消息类型：</th>
							<td>
							<c:if test="${wxMessageSend.msgType=='image'}">图片</c:if>
							<c:if test="${wxMessageSend.msgType=='video'}">视频</c:if>
							<c:if test="${wxMessageSend.msgType=='thumb'}">缩略图</c:if>
							<c:if test="${wxMessageSend.msgType=='voice'}">语音</c:if>
							<c:if test="${wxMessageSend.msgType=='article'||wxMessageSend.msgType=='multiArticle'}">图文</c:if>
							</td>
							</tr><tr>
							<th>发送类型：</th>
							<td>
								<c:if test="${wxMessageSend.sendType=='openId'}">指定用户</c:if>
								<c:if test="${wxMessageSend.sendType=='tag'}">标签</c:if>
								<c:if test="${wxMessageSend.sendType=='everyone'}">所有人</c:if>
								： ${wxMessageSend.receiver}
							</td>
						</tr>
					<%-- 	<tr>
							<th>发送状态：</th>
							<td>
								${wxMessageSend.sendState}
							</td>
						</tr> --%>
						<%-- <tr>
							<th>备用配置：</th>
							<td>
								${wxMessageSend.config}
							</td>
						</tr> --%>
						<tr>
							<th>发送时间：</th>
							<td>
								<fmt:formatDate value="${wxMessageSend.createTime}" pattern="yyyy-MM-dd HH:mm" />
							</td>
						</tr>
						<tr>
							<th>发信人：</th>
							<td>
								<rxc:userLabel userId="${wxMessageSend.createBy}"/>
							</td>
						</tr>
						<tr>
							<th>消息内容：</th>
							<td>
							<c:if test="${wxMessageSend.msgType=='image'||wxMessageSend.msgType=='thumb'||wxMessageSend.msgType=='voice'||wxMessageSend.msgType=='video'}">
							<a target="_blank" href="${ctxPath}/sys/core/file/downloadOne.do?fileId=${wxMessageSend.content}"><span>下载文件</span></a>
							</c:if>	
							<c:if test="${wxMessageSend.msgType=='article'||wxMessageSend.msgType=='multiArticle'}">
							<c:forTokens items="${wxMessageSend.content}" delims=","  var="mediaId">
							<a href="javascript:void(0);" class="showContent" id="${mediaId}" style="display: inline-block;border-radius:10px;background: #E8E8E8;text-decoration:none;">单条图文</a>
							</c:forTokens>
							</c:if>
							</td>
						</tr>
					</table>
                 </div>
        	</div>
        <rx:detailScript baseUrl="wx/core/wxMessageSend" 
        entityName="com.redxun.wx.core.entity.WxMessageSend"
        formId="form1"/>
        <script type="text/javascript">
        $(".showContent").each(function(){
			var mediaId=$(this).attr("id");//每个提示的唯一标识
			var displayContent=getQtipContent(mediaId);
			$(this).qtip({
				content: {text: displayContent },
				position: {
					my: 'top center',  // Position my top left...
			        at: 'bottom center', // at the bottom right of...
			        target: $(this) // my target
			    },
		        show: {
		            effect: function() {
		                $(this).slideDown("fast");
		            }
		        },
		        hide: {
		            effect: function() {
		                $(this).slideUp("fast");
		            }
		        },
		        style: { classes: 'qtip-bootstrap' }
		    });
	 });
        
        function getQtipContent(mediaId){
        	var rtn;
        	$.ajax({
        		type:"post",
        		url:"${ctxPath}/wx/core/wxMeterial/getMediaIdContent.do",
        		async:false,
        		data:{mediaId:mediaId},
        		success:function(result){
        		rtn=result.ueditorContent;
        		}
        	});
        	return rtn;
        }
        </script>
    </body>
</html>