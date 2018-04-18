
<%-- 
    Document   : [人员脚本]明细页
    Created on : 2017-06-01 11:33:08
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[人员脚本]明细</title>
        <%@include file="/commons/get.jsp" %>
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>
		<div class="heightBox"></div>
        <div id="form1" class="form-outer shadowBox90">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail column_2_m" cellpadding="0" cellspacing="1">
                    	<caption>[人员脚本]基本信息</caption>
						<tr>
							<th>类　名</th>
							<td>
								${bpmGroupScript.className}
							</td>
							<th>类实例名</th>
							<td>
								${bpmGroupScript.classInsName}
							</td>
						</tr>
						<tr>
							<th>方法名</th>
							<td>
								${bpmGroupScript.methodName}
							</td>
							<th>方法描述</th>
							<td>
								${bpmGroupScript.methodDesc}
							</td>
						</tr>
						<tr>
							<th>返回类型</th>
							<td>
								${bpmGroupScript.returnType}
							</td>
							<th>参　　数</th>
							<td>
								${bpmGroupScript.argument}
							</td>
						</tr>
					</table>
                 </div>
	            <div style="padding:5px">
					 <table class="table-detail column_2" cellpadding="0" cellspacing="1">
					 	<caption>更新信息</caption>
						<tr>
							<th>创建人</th>
							<td><rxc:userLabel userId="${bpmGroupScript.createBy}"/></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${bpmGroupScript.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更新人</th>
							<td><rxc:userLabel userId="${bpmGroupScript.updateBy}"/></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${bpmGroupScript.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
	        	</div>
        	</div>
        <rx:detailScript baseUrl="bpm/core/bpmGroupScript" 
        entityName="com.redxun.bpm.core.entity.BpmGroupScript"
        formId="form1"/>
        <script type="text/javascript">
        	addBody();
        </script>
    </body>
</html>