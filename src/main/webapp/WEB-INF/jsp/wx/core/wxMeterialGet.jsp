
<%-- 
    Document   : [微信素材]明细页
    Created on : 2017-07-11 16:03:13
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" /> 
        <title>素材明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
						<%-- <tr>
							<th>素材名：</th>
							<td>
								${wxMeterial.name}
								
								<div style="border-radius:10px;;background-color: #C1CDCD;color: #551A8B;display: inline-block;"><c:if test="${wxMeterial.termType=='0'}">临时消息</c:if>
								<c:if test="${wxMeterial.termType=='1'}">永久消息</c:if></div>
							</td>
						</tr> --%>
						<!-- <tr><th  style="text-align: center;">详情</th></tr> -->
						<tr>
							
							<td>
							<c:if test="${wxMeterial.mediaType=='article'}">
							${wxMeterial.artConfig}
							</c:if>
							<c:if test="${wxMeterial.mediaType=='image'||wxMeterial.mediaType=='thumb'||wxMeterial.mediaType=='voice'||wxMeterial.mediaType=='video'}">
							<a target="_blank" href="${ctxPath}/sys/core/file/downloadOne.do?fileId=${wxMeterial.artConfig}"><span>下载文件</span></a>
							</c:if>	
							<c:if test="${wxMeterial.mediaType=='multiArticle'}">
							<c:forTokens items="${wxMeterial.artConfig}" delims="," var="mediaId">
								<a style="display: inline-block;border-radius:10px;background: #E8E8E8;text-decoration:none;" href="javascript:void(0);" onclick="openMeterial('${mediaId}');">点击查看单条图文</a>
							</c:forTokens>
						</c:if>
							</td>
						</tr>
						
					</table>
                 </div>
	          
        	</div>
        <rx:detailScript baseUrl="wx/core/wxMeterial" 
        entityName="com.redxun.wx.core.entity.WxMeterial"
        formId="form1"/>
        <script type="text/javascript">
        function openMeterial(mediaId){
        	_OpenWindow({
	    		 url: "${ctxPath}/wx/core/wxMeterial/openMeterial.do?mediaId="+mediaId,
	            title: "查看素材",
	            width: 900, height: 700,
	            ondestroy: function(action) {
	               
	            }
	    	});
        }
        </script>
    </body>
</html>