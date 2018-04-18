<%-- 
    Document   : [BpmTestCase]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[BpmTestCase]编辑</title>
      <%@include file="/commons/edit.jsp"%>  
    </head>
    <body>
        <rx:toolbar toolbarId="toolbar1" pkId="${bpmTestCase.testId}"/>
       <div id="p1" class="form-outer">
            <form id="form1" method="post">
                	<div class="form-inner">
                		<input id="pkId" name="testId" class="mini-hidden" value="${bpmTestCase.testId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                        	<caption>[BpmTestCase]基本信息</caption>
		                        																																																																				
																																	<tr>
												<th>
										 			测试方案ID
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="testSolId" value="${bpmTestCase.testSolId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																													required="true" emptyText="请输入测试方案ID"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			用例名称
										 														 				<span class="star">*</span>
										 														 				：
										 		</th>
												<td>
																										<input name="caseName" value="${bpmTestCase.caseName}" class="mini-textbox" vtype="maxLength:20" style="width:90%"
																													required="true" emptyText="请输入用例名称"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			参数配置
										 														 				：
										 		</th>
												<td>
																											 <textarea name="paramsConf" class="mini-textarea" vtype="maxLength:65535" style="width:90%"
														 														 >${bpmTestCase.paramsConf}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			用户干预配置
										 														 				：
										 		</th>
												<td>
																											 <textarea name="userConf" class="mini-textarea" vtype="maxLength:65535" style="width:90%"
														 														 >${bpmTestCase.userConf}
														 </textarea>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			流程实例ID
										 														 				：
										 		</th>
												<td>
																										<input name="instId" value="${bpmTestCase.instId}" class="mini-textbox" vtype="maxLength:64" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			执行最终状态
										 														 				：
										 		</th>
												<td>
																										<input name="lastStatus" value="${bpmTestCase.lastStatus}" class="mini-textbox" vtype="maxLength:20" style="width:90%"
																												/>
																										
												</td>
											</tr>
																																																																													
																																	<tr>
												<th>
										 			执行异常
										 														 				：
										 		</th>
												<td>
																											 <textarea name="exeExceptions" class="mini-textarea" vtype="maxLength:65535" style="width:90%"
														 														 >${bpmTestCase.exeExceptions}
														 </textarea>
																										
												</td>
											</tr>
																																																																																																																																																																																																									                       </table>
                     </div>
            </form>
        </div>
       <rx:formScript formId="form1" 
       baseUrl="bpm/core/bpmTestCase"
       entityName="com.redxun.bpm.core.entity.BpmTestCase" />
    </body>
</html>