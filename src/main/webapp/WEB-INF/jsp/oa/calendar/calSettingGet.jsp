<%-- 
    Document   : [CalSetting]明细页
    Created on : 2017-1-6, 17:42:57
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[CalSetting]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1"/>
        <div id="form1" class="form-outer">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail" cellpadding="0" cellspacing="1">
                    	<caption>[CalSetting]基本信息</caption>
                        																														<tr>
						 		<th>
						 			日历名称：
						 		</th>
	                            <td>
	                                ${calSetting.calName}
	                            </td>
						</tr>
																																																																																																																																										                     </table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${calSetting.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${calSetting.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${calSetting.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${calSetting.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="oa/calendar/calSetting" 
        entityName="com.redxun.oa.calendar.entity.CalSetting"
        formId="form1"/>
    </body>
</html>