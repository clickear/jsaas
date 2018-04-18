
<%-- 
    Document   : [微信关注者]明细页
    Created on : 2017-06-30 08:51:08
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[微信关注者]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>
		<div class="heightBox"></div>
        <div id="form1" class="form-outer shadowBox90">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
                    	<caption>[微信关注者]基本信息</caption>
						<tr>
							<th>SUBSCRIBE</th>
							<td>
								${wxSubscribe.subscribe}
							</td>
							<th>OPENID</th>
							<td>
								${wxSubscribe.openId}
							</td>
						</tr>
						<tr>
							<th>昵　　称</th>
							<td>
								${wxSubscribe.nickName}
							</td>
							<th>语　　言</th>
							<td>
								${wxSubscribe.language}
							</td>
						</tr>
						<tr>
							<th>城　　市</th>
							<td>
								${wxSubscribe.city}
							</td>
							<th>省　　份</th>
							<td>
								${wxSubscribe.province}
							</td>
						</tr>
						<tr>
							<th>国　　家</th>
							<td>
								${wxSubscribe.country}
							</td>
							<th>绑定  ID</th>
							<td>
								${wxSubscribe.unionid}
							</td>
						</tr>
						<tr>
							<th>最后的关注时间</th>
							<td>
								<fmt:formatDate value="${wxSubscribe.subscribeTime}" pattern="yyyy-MM-dd HH:mm" />
							</td>
							<th>粉丝备注</th>
							<td>
								${wxSubscribe.remark}
							</td>
						</tr>
						<tr>
							<th>用户分组ID</th>
							<td>
								${wxSubscribe.groupid}
							</td>
							<th></th>
							<td>
							</td>
						</tr>
					</table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail column_2" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创  建  人</th>
							<td><rxc:userLabel userId="${wxSubscribe.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${wxSubscribe.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更  新  人</th>
							<td><rxc:userLabel userId="${wxSubscribe.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${wxSubscribe.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="wx/core/wxSubscribe" 
        entityName="com.redxun.wx.core.entity.WxSubscribe"
        formId="form1"/>
        <script type="text/javascript">
        	addBody();
        </script>
        
    </body>
</html>