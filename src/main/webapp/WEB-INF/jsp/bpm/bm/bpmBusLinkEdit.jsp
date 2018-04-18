<%-- 
    Document   : [BpmBusLink]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmBusLink]编辑</title>
        <%@include file="/commons/edit.jsp"%>
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${bpmBusLink.bpmBusLinkId}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="bpmBusLinkId" class="mini-hidden" value="${bpmBusLink.bpmBusLinkId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[BpmBusLink]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			表单实例ID
										 														 				：
										 		</th>
												<td>
																										<input name="formInstId" value="${bpmBusLink.formInstId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			流程实例ID
										 														 				：
										 		</th>
												<td>
																										<input name="instId" value="${bpmBusLink.instId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			第三方物理表主键ID
										 														 				：
										 		</th>
												<td>
																										<input name="linkPk" value="${bpmBusLink.linkPk}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="bpm/bm/bpmBusLink"
       entityName="com.redxun.bpm.bm.entity.BpmBusLink" />
    </body>
</html>