<%-- 
    Document   : 需求明细页
    Created on : 2015-3-28, 17:42:57
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>项目需求明细</title>
<%@include file="/commons/get.jsp"%>
<style type="text/css">
.linear{ 
width:100%; 
height:100%; 
FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#CCEEFF,endColorStr=#fafafa); /*IE*/ 
background:-moz-linear-gradient(top,#CCEEFF,#fafafa);/*火狐*/ 
background:-webkit-gradient(linear, 0% 0%, 0% 100%,from(#CCEEFF), to(#fafafa));/*谷歌*/ 
background-image: -webkit-gradient(linear,left bottom,left top,color-start(0, #CCEEFF),color-stop(1, #fafafa));/* Safari & Chrome*/ 
filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#CCEEFF', endColorstr='#fafafa'); /*IE6 & IE7*/ 
-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#CCEEFF', endColorstr='#fafafa')"; /* IE8 */ 
background: -ms-linear-gradient(top, #CCEEFF,  #fafafa);/* IE 10 */
} 

.redcolor{
color:#AA0000; 
}

.divmargin{
margin-bottom: -8px;
}
</style>
</head>
<body>
        
        <rx:toolbar toolbarId="toolbar1" hideRecordNav="true" excludeButtons="remove">
       <div class="self-toolbar">
       <a class="mini-button" iconCls="icon-reload" plain="true" onclick="location.reload();">刷新</a>
       <a class="mini-button" iconCls=" icon-unlock" plain="true" onclick="payAttention()">关注</a>
       </div>
       </rx:toolbar>
        
        
       <div align="center" style="font-size: x-large;font-weight: bolder;">${reqMgr.subject}</div>
       <div class="divmargin" style="margin-left: 3px;">需求编码:<a class="redcolor">${reqMgr.reqCode}</a></div><div style="float: right; margin-right: 5px;">状态:<a class="redcolor">${reqMgr.status}</a></div>	
       <div style="margin-left: 3px;">版本号:<a class="redcolor">${reqMgr.version}</a></div>

	
	<!-- 描述  -->
	<div id="form1" style="margin-top: 10px;">
			<table style="width: 100%;height: 220px;"  cellpadding="0">
				<tr  class="linear" >
					 <td style="min-height: 50px;border: solid 1px #909AA6;text-align: left;padding: 5px;"><div  align="center" style="font-size:18px; ">${reqMgr.descp}</div></td>
				</tr>
			</table>
		</div>
	
		
		</table>
		</div>
		<div style="margin-left: 3px;">
		审批人:<a class="redcolor">${checker}</a> 负责人:<a class="redcolor">${rep}</a> 执行人:<a class="redcolor">${exe}</a> 创建人:<a class="redcolor"><rxc:userLabel
				userId="${reqMgr.createBy}" /></a> 创建时间 :<a class="redcolor"><fmt:formatDate value="${reqMgr.createTime}" pattern="yyyy-MM-dd HH:mm" /></a>
	</div>
	<div style="float: right;padding: 5px;font-size:x-small;">工作计划数量<a href="javascript:listPlanTask();" style="color:#9F79EE;font-size: large;">${tasknum}</a></div>
	<br />
	<!-- tab -->
	<div style="height: 243px;width:100%;">
		<div id="tabs1" class="mini-tabs" style="width: 100%;height: 100%;" >
		    <div title="评论内容" url="${ctxPath}/oa/pro/proWorkMat/list.do?reqId=${reqMgr.reqId}"></div>
		</div>
	</div>
	
	<br/>
	<rx:detailScript baseUrl="oa/pro/reqMgr" formId="form1"  entityName="com.redxun.oa.pro.entity.ReqMgr"/>
	<script type="text/javascript">
	//打开项目的计划列表
	function listPlanTask(){
		_OpenWindow({
    		url: __rootPath+"/oa/pro/planTask/listByReq.do?reqId="+${reqMgr.reqId},
            title: "计划列表", width: 1040, height: 600,
            ondestroy: function(action) {
            }
    	});	
	}
	
	
	//关注需求
	function payAttention(){
		$.ajax({
            type: "Post",
            async: false,
            url : '${ctxPath}/oa/pro/proWorkAtt/checkAttention.do?typePk=${reqMgr.reqId}',
            success: function (result) {
            	if(result.success==true){
            		 mini.confirm("确定要关注此需求？", "确定？",
            		            function (action) {
            		                if (action == "ok") {
            		                	$.ajax({
            		                        type: "Post",
            		                        url : '${ctxPath}/oa/pro/proWorkAtt/payAttention.do?typePk=${reqMgr.reqId}&type=REQ',
            		                        success: function (result) {
            		                        	if(result.success==true){
            		                        		mini.showTips({
            		                                    content: "<b>提示</b> <br/>关注成功",
            		                                    state: 'success',
            		                                    x: 'center',
            		                                    y: 'top',
            		                                    timeout: 3000});
            		                        		}
            		                        }
            		                            }); 
            	                                        }
            		                }
            		             );
            }else{
            	mini.showTips({
                    content: "<b>提示</b> <br/>您已经关注此需求",
                    state: 'warning',
                    x: 'center',
                    y: 'top',
                    timeout: 3000});
                  }
            
                                           }
	});
	}
	</script>
</body>
</html>